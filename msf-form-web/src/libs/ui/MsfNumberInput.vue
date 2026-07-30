<template>
  <MsfInput
    ref="inputRef"
    v-bind="$attrs"
    v-model="model"
    inputmode="numeric"
    :placeholder="props.placeholder"
    :maxlength="props.maxlength"
    class="msf-number-input-value"
    @input="onInput"
  />
</template>

<script setup>
import { ref } from 'vue'

// 네이티브 속성(readonly, disabled, maxlength 등)을
// 최상위 태그가 아닌 input으로 깔끔하게 넘겨줍니다.
defineOptions({
  inheritAttrs: false,
})

const model = defineModel({ type: [String, Number], default: '' })

const props = defineProps({
  placeholder: String,
  maxlength: [String, Number],
})

const emit = defineEmits(['update:modelValue', 'maxlength'])

const inputRef = ref(null)

const onInput = (e) => {
  // 1. 입력된 값에서 숫자(0-9)가 아닌 모든 문자를 찾아내어 제거('')합니다.
  const sanitizedValue = e.target.value.replace(/[^0-9]/g, '')

  // 2. 강제로 input 창의 값을 정제된 숫자로 덮어씌웁니다.
  // (이 코드가 있어야 한글이나 특수문자가 입력창에 머무는 것을 즉시 차단합니다.)
  e.target.value = sanitizedValue

  // 3. 부모 컴포넌트로 깨끗한 숫자 문자열만 전달합니다.
  emit('update:modelValue', sanitizedValue)

  // 4. maxlength에 도달하면 이벤트를 발생시킵니다.
  if (props.maxlength && e.target.value.length >= parseInt(props.maxlength, 10)) {
    emit('maxlength')
  }
}

// 외부 노출 메서드
defineExpose({
  focus: () => {
    inputRef.value?.focus()
  },
})
</script>
