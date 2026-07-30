<template>
  <iframe
    ref="ocrFrame"
    src="/koi-ocr/idcard/index.html"
    allow="camera; microphone; autoplay; fullscreen"
    class="ocr-frame"
  />
</template>

<script setup>
import { onMounted, onBeforeUnmount, ref } from 'vue'
import { postRaw } from '@/libs/api/msf.api.js'

const emit = defineEmits(['success', 'error', 'ready', 'timeout'])
const ocrFrame = ref(null)
const isFrameReady = ref(false)

const props = defineProps({
  ocrType: String,
  returnFile: Boolean,
})

const IDENTITY_TYPE_OCR_TYPE = {
  '01': 1,
  '02': 1,
  '03': 1,
  '04': 1,
  '05': 1,
  '06': 1,
}

const normalizeOcrType = (ocrType) => {
  if (!ocrType) return 1
  if (IDENTITY_TYPE_OCR_TYPE[ocrType]) return IDENTITY_TYPE_OCR_TYPE[ocrType]
  if (/^\d+$/.test(String(ocrType))) return Number(ocrType)
  return ocrType
}

const base64ToBlob = (base64Data, mimeType) => {
  const byteString = window.atob(base64Data)
  const bytes = new Uint8Array(byteString.length)

  for (let i = 0; i < byteString.length; i += 1) {
    bytes[i] = byteString.charCodeAt(i)
  }

  return new Blob([bytes], { type: mimeType })
}

const postToFrame = (source, message) => {
  source?.postMessage(message, window.location.origin)
}

const startOCR = ({ ocrType, useCapOcr } = {}) => {
  if (!isFrameReady.value) {
    console.warn('OCR 프레임이 아직 준비되지 않았습니다.')
    return
  }

  const rawType = ocrType ?? props.ocrType
  const resolvedOcrType = normalizeOcrType(rawType)

  const payload = {
    ocrType: resolvedOcrType,
    useCapOcr: useCapOcr ?? 1,
  }

  ocrFrame.value?.contentWindow?.postMessage(
    {
      type: 'START_OCR',
      payload,
    },
    window.location.origin,
  )
}

const handleOcrApiRequest = async (event, data) => {
  const { payload } = data

  try {
    const ocrType = normalizeOcrType(payload?.ocrType)
    const documentType = String(props.ocrType ?? '')
    const apiPath = '/api/koi-ocr/document/scan'

    if (!apiPath) {
      throw new Error(`지원하지 않는 OCR 타입입니다. ocrType=${payload?.ocrType}`)
    }

    if (!payload?.base64Data) {
      throw new Error('OCR 요청 이미지가 없습니다.')
    }

    const isTiff = ocrType === 16 || ocrType === 27
    const formData = new FormData()
    const mimeType = isTiff ? 'image/tiff' : 'image/jpeg'
    const fileName = payload.fileName || `${Date.now()}.${isTiff ? 'tiff' : 'jpg'}`

    formData.append('srcFile', base64ToBlob(payload.base64Data, mimeType), fileName)
    formData.append('saveOption', payload.saveOption ?? true)
    formData.append('ocrType', documentType)
    formData.append('returnFile', props.returnFile)

    Object.entries(payload.fields || {}).forEach(([key, value]) => {
      if (value !== undefined && value !== null) formData.append(key, value)
    })

    const response = await postRaw(apiPath, formData)

    postToFrame(event.source, {
      type: 'MSF_KOI_OCR_API_RESPONSE',
      requestId: data.requestId,
      ok: true,
      status: response.status,
      data: response.data?.data ?? response.data,
      message: response.data?.message,
    })
  } catch (error) {
    postToFrame(event.source, {
      type: 'MSF_KOI_OCR_API_RESPONSE',
      requestId: data.requestId,
      ok: false,
      status: error?.response?.status ?? 0,
      message: error?.response?.data?.message || error?.message,
    })
  }
}

const receiveMessage = async (event) => {
  if (event.origin !== window.location.origin) return
  const data = event.data
  if (!data) return

  switch (data.type) {
    case 'MSF_KOI_OCR_API_REQUEST':
      await handleOcrApiRequest(event, data)
      break
    case 'OCR_READY': // 프레임 로드 완료 신호 수신
      isFrameReady.value = true
      emit('ready')
      break
    case 'OCR_RESULT': {
      emit('success', data.result)
      break
    }
    case 'OCR_ERROR':
      emit('error', data.error)
      break
    case 'OCR_TIMEOUT':
      emit('timeout', data.error)
      break
  }
}

onMounted(() => {
  window.addEventListener('message', receiveMessage)
})

onBeforeUnmount(() => {
  window.removeEventListener('message', receiveMessage)
})

defineExpose({ startOCR })
</script>

<style scoped>
.ocr-frame {
  width: 100%;
  height: 505px;
}
</style>
