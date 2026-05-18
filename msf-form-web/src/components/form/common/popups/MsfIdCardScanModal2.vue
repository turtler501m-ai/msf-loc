<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="신분증 스캔"
    @open="onOpen"
    @close="onClose"
  >
    <!-- OCR iframe을 감싸는 컴포넌트 -->
    <div class="ocr-container">
      <MsfKoiOcr
          ref="ocrRef"
          class="ocr-frame"
          :ocr-type="props.identityTypeCd"
          @ready="handleOcrReady"
          @success="handleOcrSuccess"
          @error="handleOcrError"
          @timeout="handleOcrTimeout"
      />
    </div>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'
import MsfKoiOcr from '@/components/common/MsfKoiOcr.vue'
import { showConfirm } from '@/libs/utils/comp.utils.js';

const props = defineProps({
  modelValue: Boolean,
  identityTypeCd: String,
  identityTypeNm: String,
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

const ocrRef = ref(null)
const docFile = ref(null)
const previewUrl = ref(null)

const onOpen = () => {
  emit('open')
}

// 닫힘 이벤트
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

// MsfKoiOcr의 @ready 이벤트와 연결하여 자동 실행 가능
const handleOcrReady = () => {
  openCamera();
}

/*** OCR 성공 처리 */
const handleOcrSuccess = (result) => {
  docFile.value = result;
  previewUrl.value = result?.resultJSON?.base64Data || result?.base64Data || null;
}

/*** OCR 실패*/
const handleOcrError = (error) => {
  console.error(error)
}

const handleOcrTimeout = () => {
  showConfirm('신분증 인식 시간이 초과되었습니다.\n다시 촬영하시겠습니까?',openCamera,'',onClose)
}

/*** 확인 버튼*/
const onConfirm = () => {
  // 촬영된 파일 전송
  emit('confirm', docFile.value)
  onClose()
}
</script>

<style lang="scss" scoped></style>
