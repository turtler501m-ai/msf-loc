<template>
  <MsfInput
    v-bind="$attrs"
    v-model="model"
    :id="inputId"
    :maxlength="props.length"
    :placeholder="defaultPlaceholder"
    inputmode="numeric"
    class="msf-birthday-input-value"
    @input="onInput"
    @blur="onBlur"
  />
</template>

<script setup>
import { computed, useId, inject } from 'vue'
import { validateDateInput, validateDate, validateSDate } from '@/libs/utils/date.utils'
import { showAlert } from '@/libs/utils/comp.utils'

// ID 설정
const injectedId = inject('form-group-id', null)
const inputId = computed(() => props.id || injectedId || useId())

// 네이티브 속성을 input 태그에 바로 적용합니다.
defineOptions({
  inheritAttrs: false,
})

const model = defineModel({ type: [String, Number], default: '' })

const props = defineProps({
  // 부모 컴포넌트에서 6 또는 8을 전달받습니다. (기본값은 6)
  length: {
    type: [Number, String],
    default: 6,
    validator: (v) => [6, 8].includes(Number(v)), // 6과 8만 허용하는 안전장치
  },
  placeholder: String,
})

const emit = defineEmits(['update:modelValue'])

// length 길이에 따라 똑똑하게 기본 placeholder를 바꿔줍니다.
// (물론 부모 컴포넌트에서 placeholder="내용"을 직접 넘기면 그게 우선 적용됩니다.)
const defaultPlaceholder = computed(() =>
  props.placeholder
    ? props.placeholder
    : Number(props.length) === 8
      ? '생년월일(8자리) 입력'
      : '생년월일(YYMMDD) 6자리 입력',
)

const onInput = (e) => {
  const sanitizedValue = e.target.value.replace(/[^0-9]/g, '')
  e.target.value = sanitizedValue

  if (!validateDateInput(sanitizedValue, props.length)) {
    e.target.value = sanitizedValue.substring(0, sanitizedValue.length - 1)
  }
  emit('update:modelValue', e.target.value)
}

const onBlur = (e) => {
  const val = e.target.value.replace(/[^0-9]/g, '')
  const len = Number(props.length)

  if (val.length > 0) {
    let isValid = false
    if (val.length === len) {
      isValid = len === 8 ? validateDate(val) : validateSDate(val)
    }

    if (!isValid) {
      showAlert('유효한 생년월일이 아닙니다.')
      e.target.value = ''
      model.value = ''
      emit('update:modelValue', '')
    } else if (e.target.value !== val) {
      e.target.value = val
      model.value = val
      emit('update:modelValue', val)
    }
  }
}
</script>

<style scoped></style>
