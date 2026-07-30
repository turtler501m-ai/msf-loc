<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="로밍 해외도착알리미"
    @open="emit('open')"
    @close="onClose"
    size="medium"
  >
    <!-- 팝업 내용 -->
    <MsfFormGroup label="<em>알림번호</em>" vertical tag="div">
      <MsfStack type="field" class="ut-w100p">
        <MsfNumberInput
          v-model="formData.numberValue1"
          placeholder="알림 휴대폰번호 입력 (필수)"
          class="ut-w100p"
          maxlength="11"
        />
        <MsfNumberInput
          v-model="formData.numberValue2"
          placeholder="알림 휴대폰번호 입력 (선택)"
          class="ut-w100p"
          maxlength="11"
        />
        <MsfNumberInput
          v-model="formData.numberValue3"
          placeholder="알림 휴대폰번호 입력 (선택)"
          class="ut-w100p"
          maxlength="11"
        />
        <MsfNumberInput
          v-model="formData.numberValue4"
          placeholder="알림 휴대폰번호 입력 (선택)"
          class="ut-w100p"
          maxlength="11"
        />
        <MsfNumberInput
          v-model="formData.numberValue5"
          placeholder="알림 휴대폰번호 입력 (선택)"
          class="ut-w100p"
          maxlength="11"
        />
      </MsfStack>
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

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  settingData: { type: Object, default: () => ({}) },
  initialSettingData: { type: Object, default: () => ({}) },
})

const emit = defineEmits(['update:modelValue', 'open', 'close', 'confirm'])

// 폼 데이터 초기화
const formData = reactive({
  numberValue1: '',
  numberValue2: '',
  numberValue3: '',
  numberValue4: '',
  numberValue5: '',
})

// 기존 설정값에서 초기값 복원
const initializeFromSettingData = (settingData = props.settingData) => {
  const {
    paramSbst,
    ftrNewParam,
    numberValue1,
    numberValue2,
    numberValue3,
    numberValue4,
    numberValue5,
  } = settingData

  if (ftrNewParam) {
    const numbers = String(ftrNewParam)
      .split(':')
      .map((n) => n.trim())
      .filter(Boolean)
    formData.numberValue1 = numbers[0] || ''
    formData.numberValue2 = numbers[1] || ''
    formData.numberValue3 = numbers[2] || ''
    formData.numberValue4 = numbers[3] || ''
    formData.numberValue5 = numbers[4] || ''
  } else {
    if (paramSbst) {
      paramSbst.split('|').forEach((item) => {
        const [key, value] = item.split('=')
        // APNT_NO + 숫자 패턴 확인 (정규식: APNT_NO(\d+))
        const match = key && key.match(/APNT_NO(\d+)/)
        if (match) {
          const index = match[1] // 1, 2, 3, 4, 5 추출
          const fieldName = `numberValue${index}`
          formData[fieldName] = (value || '').trim()
        }
      })
    } else {
      formData.numberValue1 = numberValue1 || ''
      formData.numberValue2 = numberValue2 || ''
      formData.numberValue3 = numberValue3 || ''
      formData.numberValue4 = numberValue4 || ''
      formData.numberValue5 = numberValue5 || ''
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

const isValidMobileNumber2 = (value) => /^01\d{9}$/.test(normalizePhone(value))

// 확인 이벤트
const onConfirm = () => {
  const allValues = [
    normalizePhone(formData.numberValue1),
    normalizePhone(formData.numberValue2),
    normalizePhone(formData.numberValue3),
    normalizePhone(formData.numberValue4),
    normalizePhone(formData.numberValue5),
  ]

  // 유효성 검사: 첫 번째 번호는 필수
  if (!allValues[0]) {
    showAlert('첫 번째 수신번호는 필수입니다.')
    return
  }

  const invalidNumber = allValues.find((number) => number && !isValidMobileNumber2(number))
  if (invalidNumber) {
    showAlert('유효한 휴대폰번호를 입력해 주세요.')
    return
  }

  // 번호 중복 검사
  const numbers = allValues.filter((n) => n)

  const uniqueNumbers = new Set(numbers)
  if (numbers.length !== uniqueNumbers.size) {
    showAlert('같은 번호를 중복으로 입력할 수 없습니다.')
    return
  }

  // 순서대로 입력 검사 (중간 null 불가)
  let lastFilledIndex = -1
  for (let i = 0; i < allValues.length; i++) {
    if (allValues[i]) {
      lastFilledIndex = i
    }
  }

  // 첫 입력 이후 빈 항목이 있는지 확인
  let foundEmpty = false
  for (let i = 0; i < allValues.length; i++) {
    if (!allValues[i]) {
      foundEmpty = true
    } else if (foundEmpty) {
      showAlert('수신번호는 1번부터 순서대로 입력하세요.')
      return
    }
  }

  // ftrNewParam 생성: 입력된 항목까지만 포함
  const ftrNewParam = allValues.slice(0, lastFilledIndex + 1).join(':')

  emit('confirm', { ftrNewParam })
  onClose()
}
</script>

<style lang="scss" scoped></style>
