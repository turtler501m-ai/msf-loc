<template>
  <iframe
    v-if="!isApp"
    ref="ocrFrame"
    src="/koi-ocr/idcard/index.html"
    allow="camera *; display-capture *"
    class="ocr-frame"
  />
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { showCamera } from '@/libs/utils/device.utils.js'

const isApp = ['A', 'I'].includes(localStorage.getItem('deviceType'))

const emit = defineEmits(['success', 'error', 'ready', 'timeout', 'captured'])

const ocrFrame = ref(null)
const isFrameReady = ref(false)

const OCR_TYPE_DOC = 1

/**
 * BASE64 → Blob
 */
const base64ToBlob = (base64) => {
  const [meta, data] = base64.split(',')
  const mime = meta.match(/:(.*?);/)?.[1] || 'image/jpeg'

  const binary = atob(data)
  const bytes = new Uint8Array(binary.length)

  for (let i = 0; i < binary.length; i++) {
    bytes[i] = binary.charCodeAt(i)
  }

  return new Blob([bytes], { type: mime })
}

/**
 * APP raw parsing
 */
const parseBridgeData = (data) => {
  try {
    if (!data) return []

    let parsed = data

    if (typeof parsed === 'string') parsed = JSON.parse(parsed)
    if (typeof parsed === 'string') parsed = JSON.parse(parsed)

    return parsed
  } catch (e) {
    console.error('parseBridgeData error:', e, data)
    return []
  }
}

/**
 * extract base64
 */
const extractBase64 = (item) => {
  if (!item) return null
  if (typeof item === 'string') return item

  return item.base64 || item.imageData || item.base64String || item.data || null
}

/**
 * OCR START
 */
const startOCR = () => {
  const deviceType = localStorage.getItem('deviceType')

  // =========================
  // APP
  // =========================
  if (deviceType === 'A' || deviceType === 'I') {
    showCamera('OCR_CAMERA', (raw) => {
      try {
        if (!raw) throw new Error('촬영된 이미지가 없습니다.')

        const images = parseBridgeData(raw)

        if (!Array.isArray(images) || !images.length) {
          throw new Error('이미지 배열이 비어있음')
        }

        const files = images
          .map((image, index) => {
            const base64 = extractBase64(image)
            if (!base64) return null

            const blob = base64ToBlob(base64)

            return new File([blob], `ocr_${Date.now()}_${index}.jpg`, { type: blob.type })
          })
          .filter(Boolean)

        if (!files.length) {
          throw new Error('변환 가능한 이미지가 없습니다.')
        }


        files.forEach((file) => {
          emit('captured', { file })
          emit('success', { file })
        })
      } catch (e) {
        console.error('OCR APP ERROR:', e)
        emit('error', e)
      }
    })

    return
  }

  // =========================
  // WEB (그대로 유지)
  // =========================
  if (!isFrameReady.value) {
    console.warn('OCR Frame Not Ready')
    return
  }

  ocrFrame.value?.contentWindow?.postMessage(
    {
      type: 'START_OCR',
      payload: {
        ocrType: OCR_TYPE_DOC,
        useCapOcr: 2,
      },
    },
    window.location.origin,
  )
}

/**
 * WEB message
 */
const receiveMessage = (event) => {
  if (event.origin !== window.location.origin) return

  const data = event.data
  if (!data) return

  switch (data.type) {
    case 'OCR_READY':
      isFrameReady.value = true
      emit('ready')
      break

    case 'OCR_CAPTURED':
      if (data?.result?.file) {
        emit('captured', { file: data.result.file })
      }
      break

    case 'OCR_RESULT':
      emit('success', data.result)
      break

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

  if (isApp) emit('ready')
})

onBeforeUnmount(() => {
  window.removeEventListener('message', receiveMessage)
})

defineExpose({
  startOCR,
})
</script>

<style scoped>
.ocr-frame {
  width: 100%;
  height: 445px;
  border: 0;
}
</style>
