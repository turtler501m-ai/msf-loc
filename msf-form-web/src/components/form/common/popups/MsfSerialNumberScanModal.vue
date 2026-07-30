<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="휴대폰 일련번호 정보 스캔"
    @open="onOpen"
    @close="onClose"
    auto-height
  >
    <!-- 팝업 내용 -->
    <div class="ocr-container">
      <MsfKoiSerialNumber
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
import { showConfirm } from '@/libs/utils/comp.utils.js'
import MsfKoiSerialNumber from '@/components/common/koi-ocr/MsfKoiSerialNumber.vue'

const props = defineProps({
  modelValue: Boolean,
  readonly: Boolean,
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
      message: '일련번호 인식 실패',
      raw: result,
    })
    return
  }

  docFile.value = result?.ocrResult?.resultJSON?.formResult || {}
  showConfirm('일련번호 인증이 완료되었습니다. 적용하시겠습니까?', onConfirm, '', onClose)
}

const handleOcrError = () => {
  docFile.value = null

  showConfirm(
    '일련번호 인식 중 오류가 발생했습니다. \n다시 촬영하시겠습니까?',
    openCamera,
    '',
    onClose,
  )
}

const handleOcrTimeout = () => {
  showConfirm(
    '일련번호 인식 시간이 초과되었습니다.\n다시 촬영하시겠습니까?',
    openCamera,
    '',
    onClose,
  )
}

const onConfirm = () => {
  const raw = docFile.value || {}

  const barcodeValue = raw.fieldResults?.find((item) => item.fieldId === '401')?.value || ''

  const serialNumber = barcodeValue.replace(/[^a-zA-Z0-9가-힣]+$/, '')

  emit('confirm', { serialNumber })

  onClose()
}
</script>

<style lang="scss" scoped></style>
