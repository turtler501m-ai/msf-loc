<template>
  <MsfNumberInput
    v-bind="$attrs"
    v-model="number1"
    maxlength="3"
    placeholder="앞자리"
    :readonly="readonly || !!number1"
    :disabled="disabled"
    :ariaLabel="`${cleanLabel} 앞자리`"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    v-bind="$attrs"
    v-model="number2"
    maxlength="4"
    placeholder="가운데 4자리"
    :readonly="readonly"
    :disabled="disabled"
    :ariaLabel="`${cleanLabel} 가운데 4자리`"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    v-bind="$attrs"
    v-model="number3"
    :type="secure ? 'password' : 'text'"
    maxlength="4"
    placeholder="뒤 4자리"
    :readonly="readonly"
    :disabled="disabled"
    :ariaLabel="`${cleanLabel} 뒤 4자리`"
  />
</template>

<script setup>
import { computed, watch } from 'vue'
import { validateMobile } from '@/libs/utils/string.utils'

// 네이티브 속성(readonly, disabled, maxlength 등)을
// 최상위 태그가 아닌 input으로 깔끔하게 넘겨줍니다.
defineOptions({
  inheritAttrs: false,
})

// v-model 선언
const number1 = defineModel('number1', { type: [String, Number], default: '010' })
const number2 = defineModel('number2', { type: [String, Number], default: '' })
const number3 = defineModel('number3', { type: [String, Number], default: '' })

// props 정의
const props = defineProps({
  secure: { type: Boolean, default: false },
  readonly: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
  label: { type: String, default: '휴대폰번호' },
})

const emit = defineEmits(['verify'])

// 모든 종류의 줄바꿈과 <br>, <br/> 태그를 빈 문자열로 교체
const cleanLabel = computed(() => {
  if (!props.label) return ''
  return props.label.replace(/(\r\n|\n|\r|<br\s*\/?>)/gm, '')
})

watch(
  () => number1.value,
  (newVal) => {
    emit('verify', validateMobile(newVal + '-' + number2.value + '-' + number3.value))
  },
)
watch(
  () => number2.value,
  (newVal) => {
    emit('verify', validateMobile(number1.value + '-' + newVal + '-' + number3.value))
  },
)
watch(
  () => number3.value,
  (newVal) => {
    emit('verify', validateMobile(number1.value + '-' + number2.value + '-' + newVal))
  },
)
</script>
