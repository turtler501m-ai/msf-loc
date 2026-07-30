<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="eSIM 정보 스캔"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <div class="ocr-container">
      <iframe
        ref="ocrFrame"
        class="ocr-frame"
        src="/koi-ocr/esim/index.html"
        allow="camera *; display-capture *"
      />
    </div>

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm" :disabled="!docFile">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue'
import { post } from '@/libs/api/msf.api.js'

const props = defineProps({
  modelValue: Boolean,
  readonly: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const docFile = ref(null)

const onOpen = () => {
  emit('open')
}

const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const handleMessage = (event) => {
  const data = event.data

  if (!data) return

  // 문자열로 오는 경우 대응
  const message = typeof data === 'string' ? JSON.parse(data) : data

  if (message.type !== 'ESIM_SCAN_RESULT') return

  docFile.value = {
    eid: message.payload?.eid || '',
    imei1: message.payload?.imei1 || '',
    imei2: message.payload?.imei2 || '',
  }

  console.log('docFile 세팅 완료:', docFile.value)
}

const onConfirm = () => {
  emit('confirm', docFile.value)
  onClose()
}

onMounted(() => {
  window.addEventListener('message', handleMessage)
})

onBeforeUnmount(() => {
  window.removeEventListener('message', handleMessage)
})
</script>

<style lang="scss" scoped>
// ocr-container 공통 스타일
.ocr-container {
  width: 100%;
  height: rem(445px);
  .ocr-frame {
    width: 100%;
    height: 100%;
  }
}
</style>
