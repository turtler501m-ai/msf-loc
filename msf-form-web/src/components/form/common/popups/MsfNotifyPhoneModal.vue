<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="데이터로밍요금알림(태블릿PC용)"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <MsfFormGroup label="<em>통보번호</em>" vertical>
      <MsfNumberInput
        v-model="formData.phoneNumber"
        placeholder="통보 휴대폰번호 입력 (필수)"
        class="ut-w100p"
        maxlength="11"
      />
    </MsfFormGroup>

    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton
          v-if="props.settingData?.showChangeCancel"
          variant="tertiary"
          @click="onReset"
          >변경취소</MsfButton
        >
        <MsfButton variant="primary" @click="onConfirm">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { reactive, watch } from 'vue'
import { showAlert } from '@/libs/utils/comp.utils'
import { normalizePhone } from '@/libs/utils/string.utils'

const isValidMobileNumber2 = (value) => /^01\d{9}$/.test(normalizePhone(value))

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  settingData: { type: Object, default: () => ({}) },
  initialSettingData: { type: Object, default: () => ({}) },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

// 폼 데이터
const formData = reactive({
  phoneNumber: '',
})

// 기존 설정값에서 초기값 복원
const initializeFromSettingData = (settingData = props.settingData) => {
  const { paramSbst, ftrNewParam, phoneNumber } = settingData

  if (ftrNewParam) {
    formData.phoneNumber = normalizePhone(ftrNewParam)
  } else if (phoneNumber) {
    formData.phoneNumber = normalizePhone(phoneNumber)
  } else {
    if (paramSbst) {
      // 키 매핑 정의
      const keyMap = {
        INFO_SBST1: 'phoneNumber',
      }
      paramSbst.split('|').forEach((item) => {
        const [key, value] = item.split('=')
        if (key && keyMap[key]) {
          const fieldName = keyMap[key]
          formData[fieldName] = (value || '').trim()
        }
      })
    } else {
      formData.phoneNumber = ''
    }
  }
}

watch(
  () => props.modelValue,
  (isOpen) => {
    if (isOpen) {
      initializeFromSettingData()
    }
  },
  { immediate: true },
)

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}

const onReset = () => {
  initializeFromSettingData(
    Object.keys(props.initialSettingData).length ? props.initialSettingData : props.settingData,
  )
}

// 확인 버튼 클릭
const onConfirm = () => {
  const phoneNumber = normalizePhone(formData.phoneNumber)

  if (!phoneNumber) {
    showAlert('통보 휴대폰번호를 입력해 주세요.')
    return
  }

  if (!isValidMobileNumber2(phoneNumber)) {
    showAlert('유효한 휴대폰번호를 입력해 주세요.')
    return
  }

  // H8 기준: ftrNewParam = 전화번호 (01011112222 형식)
  emit('confirm', { ftrNewParam: phoneNumber })
  onClose()
}
</script>

<style lang="scss" scoped></style>
