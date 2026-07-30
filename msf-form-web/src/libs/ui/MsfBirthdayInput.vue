<template>
  <MsfInput
    ref="inputRef"
    v-bind="$attrs"
    v-model="model"
    :maxlength="props.length"
    :placeholder="defaultPlaceholder"
    inputmode="numeric"
    class="msf-birthday-input-value"
    @input="onInput"
    @blur="onBlur"
  />
</template>

<script setup>
import { computed, ref } from 'vue'
import { validateBirthDate, calcAgeFromBirth } from '@/libs/utils/date.utils'
import { showAlert } from '@/libs/utils/comp.utils'
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

const inputRef = ref(null)

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

  // if (!validateDateInput(sanitizedValue, props.length)) {
  //   e.target.value = sanitizedValue.substring(0, sanitizedValue.length - 1)
  // }
  emit('update:modelValue', e.target.value)
}

const onBlur = (e) => {
  const val = e.target.value.replace(/[^0-9]/g, '')

  if (val.length >= props.length) {
    let { isValid, msg } = validateBirthDate(val)
    // if (val.length === len) {
    //   isValid = validateBirthDate(val)
    // }

    if (!isValid) {
      showAlert(msg, () => e.target?.focus())
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

const isValid = computed(() => {
  return validateBirthDate(model.value)?.isValid
})

const isMinor = computed(() => {
  if (!isValid.value) {
    return false
  }

  const age = calcAgeFromBirth(model.value)
  if (age < 0) {
    return false
  }

  return age < 19
})

const isAdult = computed(() => {
  if (!isValid.value) {
    return false
  }

  const age = calcAgeFromBirth(model.value)
  if (age < 0) {
    return false
  }

  return age >= 19
})

defineExpose({
  isValid,
  isMinor,
  isAdult,
  length: computed(() => props.length),
  focus: () => {
    inputRef.value?.focus()
  },
})
</script>

<style scoped></style>
