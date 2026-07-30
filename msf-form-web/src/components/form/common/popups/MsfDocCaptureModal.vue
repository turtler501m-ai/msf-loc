<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="구비서류"
    @open="onOpen"
    @close="onClose"
    :key="modelValue ? 'open' : 'close'"
  >
    <!-- PC / WEB 전용 OCR iframe -->
    <div v-if="!isApp" class="ocr-container">
      <iframe
        ref="ocrFrame"
        src="/koi-ocr/idcard/index.html"
        allow="camera *; display-capture *"
        class="ocr-frame"
      />
    </div>

    <!-- PC / WEB 전용 버튼 -->
    <template v-if="!isApp" #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" :disabled="!pendingFiles.length" @click="onNext">
          사진추가
        </MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { postRaw } from '@/libs/api/msf.api.js'
import { showAlert } from '@/libs/utils/comp.utils.js'
import { showCamera } from '@/libs/utils/device.utils.js'

const props = defineProps({
  modelValue: Boolean,
  fileCategory: { type: String, default: '' },
  docId: { type: String, default: '' },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm', 'error'])

const ocrFrame = ref(null)
const isFrameReady = ref(false)
const isProcessing = ref(false)

const pendingFiles = ref([])
const uploadedList = ref([])

const isApp = computed(() => {
  const deviceType = localStorage.getItem('deviceType')
  return deviceType === 'A' || deviceType === 'I'
})

const resetState = () => {
  isProcessing.value = false
  pendingFiles.value = []
  uploadedList.value = []
  isFrameReady.value = false
}

const closeModal = () => {
  resetState()

  // PC/WEB에서 실제 자식 Dialog가 열린 경우에만 닫힘 이벤트 전달
  // APP에서는 부모가 startCameraBridge()만 호출하므로 부모 모달을 닫으면 안 됨
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const getBridgeCode = (raw) => {
  return String(raw?.code || raw?.resultCode || raw?.status || '').trim()
}

const isCancelBridgeResult = (raw) => {
  if (!raw) return true

  const code = getBridgeCode(raw)
  return code === '9999' || code === '1000'
}

const parseBridgeData = (data) => {
  try {
    if (!data) return []

    let parsed = data

    if (typeof parsed === 'string') parsed = JSON.parse(parsed)
    if (typeof parsed === 'string') parsed = JSON.parse(parsed)

    return Array.isArray(parsed) ? parsed : []
  } catch (e) {
    console.error('parseBridgeData error:', e, data)
    return []
  }
}

const extractBase64 = (item) => {
  if (!item) return null
  if (typeof item === 'string') return item

  return item.base64 || item.imageData || item.base64String || item.data || null
}

const base64ToBlob = (base64) => {
  const hasMeta = base64.includes(',')
  const meta = hasMeta ? base64.split(',')[0] : ''
  const data = hasMeta ? base64.split(',')[1] : base64

  const mime = meta.match(/:(.*?);/)?.[1] || 'image/jpeg'

  const binary = atob(data)
  const bytes = new Uint8Array(binary.length)

  for (let i = 0; i < binary.length; i++) {
    bytes[i] = binary.charCodeAt(i)
  }

  return new Blob([bytes], { type: mime })
}

const uploadSingleImage = async (file) => {
  const formData = new FormData()
  formData.append('srcFile', file)
  formData.append('fileCategory', props.fileCategory)

  const response = await postRaw('/api/koi-ocr/document/capture', formData)
  return response.data
}

const makeFileItem = (fileInfo, file, index) => {
  return {
    fileTypeCd: props.docId,
    fileNm: fileInfo.fileName,
    filePathNm: fileInfo.filePath,
    fileNum: index + 1,
    previewUrl: URL.createObjectURL(file),
    fileRaw: file,
  }
}

const onOpen = () => {
  emit('open')

  // PC/WEB에서 Dialog로 열린 경우만 open 이벤트에서 브릿지 실행
  // APP 기본 흐름은 부모에서 자식 Dialog를 열지 않고 startCameraBridge()를 직접 호출
  if (isApp.value && props.modelValue) {
    startCameraBridge()
  }
}

const onClose = () => {
  closeModal()
}

/**
 * APP 전용 브릿지 카메라
 */
const startCameraBridge = () => {
  if (isProcessing.value) return

  resetState()
  isProcessing.value = true

  showCamera('OCR_CAMERA', async (raw) => {
    try {
      if (isCancelBridgeResult(raw)) {
        closeModal()
        return
      }

      const images = parseBridgeData(raw)

      if (!images.length) {
        closeModal()
        return
      }

      const files = images
        .map((image, index) => {
          const base64 = extractBase64(image)
          if (!base64) return null

          const blob = base64ToBlob(base64)

          return new File([blob], `doc_${Date.now()}_${index}.jpg`, {
            type: blob.type,
          })
        })
        .filter(Boolean)

      if (!files.length) {
        closeModal()
        return
      }

      const fileList = []

      for (const file of files) {
        const result = await uploadSingleImage(file)
        const fileInfo = result.data.file

        fileList.push(makeFileItem(fileInfo, file, fileList.length))
      }

      emit('confirm', { fileList })
      closeModal()
    } catch (e) {
      handleError(e)
    }
  })
}

/**
 * PC / WEB 전용 OCR 시작
 */
const startWebOCR = () => {
  if (!isFrameReady.value) {
    console.warn('OCR Frame Not Ready')
    return
  }

  ocrFrame.value?.contentWindow?.postMessage(
    {
      type: 'START_OCR',
      payload: {
        ocrType: 1,
        useCapOcr: 2,
      },
    },
    window.location.origin,
  )
}

/**
 * PC / WEB - 사진추가
 */
const onNext = async () => {
  if (!pendingFiles.value.length) return

  const file = pendingFiles.value.shift()
  const result = await uploadSingleImage(file)
  const fileInfo = result.data.file

  uploadedList.value.push(makeFileItem(fileInfo, file, uploadedList.value.length))

  startWebOCR()
}

/**
 * PC / WEB - 확인
 */
const onConfirm = async () => {
  const finalList = [...uploadedList.value]

  for (const file of pendingFiles.value) {
    const result = await uploadSingleImage(file)
    const fileInfo = result.data.file

    finalList.push(makeFileItem(fileInfo, file, finalList.length))
  }

  emit('confirm', { fileList: finalList })
  closeModal()
}

/**
 * PC / WEB iframe message
 */
const receiveMessage = (event) => {
  if (event.origin !== window.location.origin) return

  const data = event.data
  if (!data) return

  switch (data.type) {
    case 'OCR_READY':
      isFrameReady.value = true
      startWebOCR()
      break

    case 'OCR_CAPTURED':
      if (data?.result?.file) {
        pendingFiles.value.push(data.result.file)
      }
      break

    case 'OCR_RESULT':
      break

    case 'OCR_ERROR':
      handleError(data.error)
      break

    case 'OCR_TIMEOUT':
      handleError(data.error)
      break

    default:
      break
  }
}

const handleError = (error) => {
  const msg = typeof error === 'string' ? error : error?.message || 'unknown error'

  emit('error', error)

  showAlert('카메라 처리 중 오류 발생', closeModal, '사유: ' + msg)
}

onMounted(() => {
  window.addEventListener('message', receiveMessage)
})

onBeforeUnmount(() => {
  window.removeEventListener('message', receiveMessage)
})

defineExpose({
  startCameraBridge,
  startWebOCR,
})
</script>

<style scoped>
.ocr-frame {
  width: 100%;
  height: 445px;
  border: 0;
}
</style>
