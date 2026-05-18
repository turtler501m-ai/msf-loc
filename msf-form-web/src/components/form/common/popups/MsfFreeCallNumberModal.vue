<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="망내 1회선 무료통화"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <MsfFormGroup label="<em>무료 통화번호</em>" vertical>
      <MsfNumberInput
        v-model="formData.phoneNumber"
        placeholder="무료 통화 휴대폰번호 입력 (필수)"
        class="ut-w100p"
      />
    </MsfFormGroup>

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton v-if="props.settingData?.addSvcSettingCompleted" variant="tertiary" @click="onReset">초기화</MsfButton>
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { reactive, ref, watch } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'
import { normalizePhone, isValidMobileNumber } from '@/libs/utils/string.utils'

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  settingData: { type: Object, default: () => ({}) },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

// 폼 데이터
const formData = reactive({
  phoneNumber: '',
})

// 기존 설정값에서 초기값 복원
const initializeFromSettingData = () => {
  const { ftrNewParam, phoneNumber } = props.settingData

  if (ftrNewParam) {
    formData.phoneNumber = normalizePhone(ftrNewParam)
  } else if (phoneNumber) {
    formData.phoneNumber = normalizePhone(phoneNumber)
  } else {
    formData.phoneNumber = ''
  }
}

const isFormReset = ref(false)

watch(() => props.modelValue, (isOpen) => {
  if (isOpen) {
    isFormReset.value = false
    initializeFromSettingData()
  }
}, { immediate: true })

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onReset = () => {
  isFormReset.value = true
  formData.phoneNumber = ''
}

// 확인 버튼 클릭
const onConfirm = () => {
  if (isFormReset.value) {
    emit('confirm', { isReset: true })
    onClose()
    return
  }
  const phoneNumber = normalizePhone(formData.phoneNumber)

  if (!phoneNumber) {
    showAlert('무료 통화 휴대폰번호를 입력해 주세요.')
    return
  }

  if (!isValidMobileNumber(phoneNumber)) {
    showAlert('유효한 휴대폰번호를 입력해 주세요.')
    return
  }

  // H8 기준: ftrNewParam = 전화번호 (01011112222 형식)
  emit('confirm', { ftrNewParam: phoneNumber })
  onClose()
}
</script>

<style lang="scss" scoped></style>
