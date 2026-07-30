<template>
  <MsfNumberInput
    ref="input1Ref"
    v-bind="$attrs"
    v-model="number1"
    maxlength="3"
    placeholder="앞자리"
    :readonly="readonly || !!number1"
    :disabled="disabled"
    :ariaLabel="`${cleanLabel} 앞자리`"
    @maxlength="input2Ref?.focus()"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    ref="input2Ref"
    v-bind="$attrs"
    v-model="number2"
    :id="number2Id"
    maxlength="4"
    placeholder="가운데 4자리"
    :readonly="readonly"
    :disabled="disabled"
    :ariaLabel="`${cleanLabel} 가운데 4자리`"
    @maxlength="input3Ref?.focus()"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    ref="input3Ref"
    v-bind="$attrs"
    v-model="number3"
    :id="number3Id"
    :type="secure ? 'password' : 'text'"
    maxlength="4"
    placeholder="뒤 4자리"
    :readonly="readonly"
    :disabled="disabled"
    :ariaLabel="`${cleanLabel} 뒤 4자리`"
  />
</template>

<script setup>
import { computed, watch, ref, useId } from 'vue'
import { validateMobile } from '@/libs/utils/string.utils'

// 컴포넌트 내부에서 유니크 ID 생성
const uniqueId = useId()
const number2Id = computed(() => `inp-number2-${uniqueId}`)
const number3Id = computed(() => `inp-number3-${uniqueId}`)

// 네이티브 속성(readonly, disabled, maxlength 등)을
// 최상위 태그가 아닌 input으로 깔끔하게 넘겨줍니다.
defineOptions({
  inheritAttrs: false,
})

// v-model 선언
const number1 = defineModel('number1', { type: [String, Number], default: '010' })
const number2 = defineModel('number2', { type: [String, Number], default: '' })
const number3 = defineModel('number3', { type: [String, Number], default: '' })

const input1Ref = ref(null)
const input2Ref = ref(null)
const input3Ref = ref(null)

// props 정의
const props = defineProps({
  secure: { type: Boolean, default: false },
  readonly: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
  label: { type: String, default: '휴대폰번호' },
})

const emit = defineEmits(['verify'])

const checkStrictLength = () => {
  return (
    String(number1.value || '').length === 3 &&
    String(number2.value || '').length === 4 &&
    String(number3.value || '').length === 4
  )
}

// 모든 종류의 줄바꿈과 <br>, <br/> 태그를 빈 문자열로 교체
const cleanLabel = computed(() => {
  if (!props.label) return ''
  return props.label.replace(/(\r\n|\n|\r|<br\s*\/?>)/gm, '')
})

watch(
  () => [number1.value, number2.value, number3.value],
  () => {
    const isValid = validateMobile(number1.value + '-' + number2.value + '-' + number3.value)
    const isStrictLength = checkStrictLength()
    emit('verify', isValid && isStrictLength)
  },
)

defineExpose({
  focus: () => {
    if (!number1.value) input1Ref.value?.focus()
    else if (!number2.value) input2Ref.value?.focus()
    else input3Ref.value?.focus()
  },
  isValid: computed(() =>
    validateMobile(number1.value + '-' + number2.value + '-' + number3.value),
  ),
})
</script>
