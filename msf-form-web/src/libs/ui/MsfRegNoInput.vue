<template>
  <!-- 주민등록번호, 외국인등록번호, 법인등록번호 (type으로 지정) -->
  <MsfNumberInput
    ref="registNo1Ref"
    v-model="registNo1"
    placeholder="앞 6자리"
    :ariaLabel="`${computedFormProps.label.replace('<br>', '')} 앞 6자리`"
    maxlength="6"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    @maxlength="input2?.focus()"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    ref="registNo2Ref"
    v-model="registNo2"
    :id="registNo2Id"
    :type="inputType"
    placeholder="뒤 7자리"
    :ariaLabel="`${computedFormProps.label.replace('<br>', '')} 뒤 7자리`"
    maxlength="7"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
  />
</template>

<script setup>
import { computed, useAttrs, ref, useId, watch } from 'vue'
import {
  validateResidentRegNo,
  validateForeignerRegNo,
  validateCorporateRegNo,
} from '@/libs/utils/string.utils'
import { calcAgeFromBirth, calcAgeFromRrn } from '@/libs/utils/date.utils'

// 컴포넌트 내부에서 유니크 ID 생성
const uniqueId = useId()
const registNo2Id = computed(() => `inp-registNo2-${uniqueId}`)

// v-model 선언
const registNo1 = defineModel('registNo1', { default: '' })
const registNo2 = defineModel('registNo2', { default: '' })

const registNo1Ref = ref(null)
const registNo2Ref = ref(null)

// props 정의
const props = defineProps({
  type: {
    type: String,
    default: 'resident',
    // 주민등록번호(resident), 외국인등록번호(foreigner), 법인등록번호(corporate), 주민등록번호/외국인등록번호(res_fore)
    validator: (v) => ['resident', 'foreigner', 'corporate', 'res_fore'].includes(v),
  },
  label: { type: String, default: '' }, //접근성 ariaLabel 설정용
  error: { type: Boolean, default: false },
  readonly: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
})

// 3. 정의되지 않은 나머지 속성들(attrs) 낚아채기
const attrs = useAttrs()

// 타입별 설정 정보
const TYPE_CONFIG = {
  resident: { label: '주민등록번호', isPassword: true },
  foreigner: { label: '외국인등록번호', isPassword: true },
  corporate: { label: '법인등록번호', isPassword: true },
  res_fore: { label: '주민등록번호/<br/>외국인등록번호', isPassword: true },
}

// MsfFormGroup에 넘길 속성 병합
const computedFormProps = computed(() => ({
  label: props.label || TYPE_CONFIG[props.type]?.label || '등록번호',
  ...attrs, // 부모가 보낸 help-text, message 등을 그대로 통과시킴
}))

// 뒷자리 마스킹 여부 계산
const inputType = computed(() => {
  return TYPE_CONFIG[props.type]?.isPassword ? 'password' : 'text'
})

const isValid = computed(() => {
  const fullRegistNo = `${registNo1.value}${registNo2.value}`
  if (props.type === 'resident') {
    return validateResidentRegNo(fullRegistNo)
  } else if (props.type === 'foreigner') {
    return validateForeignerRegNo(fullRegistNo)
  } else if (props.type === 'corporate') {
    return validateCorporateRegNo(fullRegistNo)
  } else if (props.type === 'res_fore') {
    return validateResidentRegNo(fullRegistNo) || validateForeignerRegNo(fullRegistNo)
  }
  return false
})

const isMinor = computed(() => {
  if (!isValid.value) {
    return false
  }

  const fullRegistNo = `${registNo1.value}${registNo2.value}`
  const age = calcAgeFromRrn(fullRegistNo)
  if (age < 0) {
    return false
  }

  if (props.type === 'corporate') {
    return true
  }

  return age < 19
})

const isAdult = computed(() => {
  if (!isValid.value) {
    return false
  }

  const fullRegistNo = `${registNo1.value}${registNo2.value}`
  const age = calcAgeFromRrn(fullRegistNo)
  if (age < 0) {
    return false
  }

  if (props.type === 'corporate') {
    return true
  }

  return age >= 19
})

watch(
  () => registNo2.value,
  (newVal) => {
    if (!newVal) {
      return
    }
    const age = calcAgeFromBirth(registNo1.value, ['3', '4', '7', '8'].includes(newVal.charAt(0)))
    if (
      props.type === 'resident' &&
      (!['1', '2', '3', '4'].includes(newVal.charAt(0)) || age < 0)
    ) {
      registNo2.value = ''
    } else if (
      props.type === 'foreigner' &&
      (!['5', '6', '7', '8'].includes(newVal.charAt(0)) || age < 0)
    ) {
      registNo2.value = ''
    } else if (props.type === 'res_fore' && (['0', '9'].includes(newVal.charAt(0)) || age < 0)) {
      registNo2.value = ''
    }
  },
  {
    immediate: true,
  },
)

defineExpose({
  isValid,
  isMinor,
  isAdult,
  focus: () => {
    if (!registNo1.value) registNo1Ref.value?.focus()
    else registNo2Ref.value?.focus()
  },
})
</script>
