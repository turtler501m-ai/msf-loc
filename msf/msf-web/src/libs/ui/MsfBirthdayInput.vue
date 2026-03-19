<template>
  <MsfInput
    v-bind="$attrs"
    v-model="model"
    :maxlength="props.length"
    :placeholder="defaultPlaceholder"
    inputmode="numeric"
    class="msf-birthday-input-value"
    @input="onInput"
  />
</template>

<script setup>
import { computed } from 'vue'
import { validateDateInput } from '@/libs/utils/date.utils'

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

  if (!validateDateInput(e.target.value, props.length)) {
    e.target.value = sanitizedValue.substring(0, sanitizedValue.length - 1)
  }
  emit('update:modelValue', e.target.value)
}
</script>

<style scoped>
@reference "tailwindcss";

.msf-birthday-input-container {
  @apply flex items-center rounded-sm bg-white pl-3 outline-1 -outline-offset-1 outline-gray-300 focus-within:outline-2 focus-within:-outline-offset-2 focus-within:outline-indigo-600;
}
/* 오류가 있을 때 입력창 테두리를 빨간색으로 변경 */
.msf-birthday-input-container.has-error {
  @apply border-red-500! outline-red-500!;
}
.msf-birthday-input-value {
  @apply block min-w-0 grow bg-white py-1.5 pr-3 pl-1 text-base text-gray-900 placeholder:text-gray-400 focus:outline-none sm:text-sm/6;
}
</style>
