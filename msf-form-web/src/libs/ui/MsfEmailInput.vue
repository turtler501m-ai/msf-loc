<template>
  <!-- 이메일주소 -->
  <MsfInput
    ref="emailIdRef"
    v-model="emailId"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="이메일 아이디"
    :ariaLabel="`${props.label.replace('<br>', '')} 아이디`"
    :maxlength="emailIdMaxlength"
  />
  <span>@</span>
  <MsfInput
    ref="emailDomainRef"
    v-model="emailDomain"
    :id="emailDomainId"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="이메일 도메인"
    :ariaLabel="`${props.label.replace('<br>', '')} 도메인`"
    class="ut-w-300"
    :maxlength="emailDomainMaxlength"
  />
</template>

<script setup>
import { computed, useId, ref } from 'vue'
import { validateEmail } from '@/libs/utils/string.utils'

// 컴포넌트 내부에서 유니크 ID 생성
const uniqueId = useId()
const emailDomainId = computed(() => `inp-domainId-${uniqueId}`)

// v-model 선언
const emailId = defineModel('emailId', { default: '' })
const emailDomain = defineModel('emailDomain', { default: '' })

// Props 정의
const props = defineProps({
  label: { type: String, default: '이메일주소' }, //접근성 ariaLabel 설정용
  error: { type: Boolean, default: false },
  readonly: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
  emailIdMaxlength: { type: [Number, String], default: 100 },
  emailDomainMaxlength: { type: [Number, String], default: 200 },
})

const emailIdRef = ref(null)
const emailDomainRef = ref(null)

defineExpose({
  focus: () => {
    if (!emailId.value) emailIdRef.value?.focus()
    else emailDomainRef.value?.focus()
  },
  isValid: computed(() => {
    return validateEmail(`${emailId.value}@${emailDomain.value}`)
  }),
  validate: () => {
    return validateEmail(`${emailId.value}@${emailDomain.value}`)
  },
})
</script>
