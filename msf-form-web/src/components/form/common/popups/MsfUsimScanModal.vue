<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="USIM 정보 스캔"
    @open="onOpen"
    @close="onClose"
    auto-height
  >
    <!-- 팝업 내용 -->
    <div class="ocr-container">
      <MsfKoiUsim
        ref="ocrRef"
        class="ocr-frame"
        @ready="handleOcrReady"
        @success="handleOcrSuccess"
        @error="handleOcrError"
        @timeout="handleOcrTimeout"
      />
    </div>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'
import MsfKoiUsim from '@/components/common/koi-ocr/MsfKoiUsim.vue'
import { showConfirm } from '@/libs/utils/comp.utils.js'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const ocrRef = ref(null)
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

/*** OCR 시작 - 프레임 ready 이후 호출 권장 */
const openCamera = () => {
  ocrRef.value?.startOCR({
    useCapOcr: 1,
  })
}

const handleOcrReady = () => {
  openCamera()
}

const handleOcrSuccess = (result) => {
  if (!result?.success) {
    handleOcrError({
      message: 'USIM 인식 실패',
      raw: result,
    })
    return
  }

  docFile.value = result?.ocrResult?.resultJSON?.formResult || {}

  showConfirm('USIM 인증이 완료되었습니다. 적용하시겠습니까?', onConfirm, '', onClose)
}

const handleOcrError = (error) => {
  const errMsg = typeof error === 'string' ? error : error?.message || ''

  if (errMsg.toLowerCase() === 'timeout') {
    handleOcrTimeout()
    return
  }

  docFile.value = null

  showConfirm('USIM 인식 중 오류가 발생했습니다.\n다시 촬영하시겠습니까?', openCamera, '',onClose)
}

const handleOcrTimeout = () => {
  showConfirm('USIM 인식 시간이 초과되었습니다.\n다시 촬영하시겠습니까?', openCamera, '', onClose)
}

const onConfirm = () => {
  const raw = docFile.value || {}

  const barcodeValue = raw.fieldResults?.find((item) => item.fieldId === '401')?.value || ''

  const reqUsimSn = barcodeValue.endsWith('F') ? barcodeValue.slice(0, -1) : barcodeValue

  emit('confirm', { reqUsimSn })

  onClose()
}
</script>

<style lang="scss" scoped></style>
