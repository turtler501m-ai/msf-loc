<template>
  <MsfInput
    v-bind="$attrs"
    v-model="model"
    inputmode="numeric"
    :placeholder="props.placeholder"
    class="msf-number-input-value"
    @input="onInput"
  />
</template>

<script setup>
// 네이티브 속성(readonly, disabled, maxlength 등)을
// 최상위 태그가 아닌 input으로 깔끔하게 넘겨줍니다.
defineOptions({
  inheritAttrs: false,
})

const model = defineModel({ type: [String, Number], default: '' })

const props = defineProps({
  placeholder: String,
})

const emit = defineEmits(['update:modelValue'])

const onInput = (e) => {
  // 1. 입력된 값에서 숫자(0-9)가 아닌 모든 문자를 찾아내어 제거('')합니다.
  const sanitizedValue = e.target.value.replace(/[^0-9]/g, '')

  // 2. 강제로 input 창의 값을 정제된 숫자로 덮어씌웁니다.
  // (이 코드가 있어야 한글이나 특수문자가 입력창에 머무는 것을 즉시 차단합니다.)
  e.target.value = sanitizedValue

  // 3. 부모 컴포넌트로 깨끗한 숫자 문자열만 전달합니다.
  emit('update:modelValue', sanitizedValue)
}
</script>
