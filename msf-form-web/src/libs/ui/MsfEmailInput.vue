<template>
  <!-- 이메일주소 -->
  <MsfInput
    v-model="emailId"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="이메일 아이디"
    :ariaLabel="`${props.label.replace('<br>', '')} 아이디`"
  />
  <span>@</span>
  <MsfInput
    v-model="emailDomain"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="이메일 도메인"
    :ariaLabel="`${props.label.replace('<br>', '')} 도메인`"
    class="ut-w-300"
  />
</template>

<script setup>
// v-model 선언
const emailId = defineModel('emailId', { default: '' })
const emailDomain = defineModel('emailDomain', { default: '' })

// Props 정의
const props = defineProps({
  label: { type: String, default: '이메일주소' }, //접근성 ariaLabel 설정용
  error: { type: Boolean, default: false },
  readonly: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
})
</script>
