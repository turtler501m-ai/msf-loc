<template>
  <iframe
    v-show="showFrame"
    ref="ocrFrame"
    src="/koi-ocr/document/index.html"
    allow="camera *; display-capture *"
    class="ocr-frame"
  />
</template>

<script setup>
import { onMounted, onBeforeUnmount, ref } from 'vue'

const emit = defineEmits(['success', 'error', 'ready', 'timeout'])

const ocrFrame = ref(null)
const isFrameReady = ref(false)
const showFrame = ref(true)

const OCR_TYPE_USIM = 30

/**
 * OCR 시작
 */
const startOCR = ({ useCapOcr } = {}) => {
  if (!isFrameReady.value) {
    console.warn('OCR 프레임이 아직 준비되지 않았습니다.')
    return
  }

  ocrFrame.value?.contentWindow?.postMessage(
    {
      type: 'START_OCR',
      payload: {
        ocrType: OCR_TYPE_USIM,
        useCapOcr: useCapOcr ?? 1,
      },
    },
    window.location.origin,
  )
}

/**
 * iframe message 수신
 */
const receiveMessage = async (event) => {
  if (event.origin !== window.location.origin) {
    return
  }

  const data = event.data

  if (!data) {
    return
  }

  switch (data.type) {
    case 'OCR_READY':
      isFrameReady.value = true
      emit('ready')
      break

    case 'OCR_RESULT':
      try {
        showFrame.value = false
        emit('success', data.result)
      } catch (e) {
        console.error('emit error', e)
      }
      break

    case 'OCR_ERROR': {
      const err = data.error

      const isTimeout = typeof err === 'string' && err.toLowerCase() === 'timeout'

      if (isTimeout) {
        emit('timeout', err)
        break
      }

      emit('error', err)
      break
    }

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
  height: 205px;
  border: 0;
}
</style>
