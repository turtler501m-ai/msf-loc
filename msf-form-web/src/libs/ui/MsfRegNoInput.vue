<template>
  <!-- 주민등록번호, 외국인등록번호, 법인등록번호 (type으로 지정) -->
  <MsfNumberInput
    v-model="registNo1"
    placeholder="앞 6자리"
    :ariaLabel="`${computedFormProps.label.replace('<br>', '')} 앞 6자리`"
    maxlength="6"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    v-model="registNo2"
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
import { computed, useAttrs } from 'vue'

// v-model 선언
const registNo1 = defineModel('registNo1', { default: '' })
const registNo2 = defineModel('registNo2', { default: '' })

// props 정의
const props = defineProps({
  type: {
    type: String,
    default: 'resident',
    // 주민등록번호(resident), 외국인등록번호(foreigner), 법인등록번호(corporate)
    validator: (v) => ['resident', 'foreigner', 'corporate'].includes(v),
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
  resident: { label: '주민등록번호', isPassword: false },
  foreigner: { label: '외국인등록번호', isPassword: false },
  corporate: { label: '법인등록번호', isPassword: false },
}

// MsfFormGroup에 넘길 속성 병합
const computedFormProps = computed(() => ({
  label: props.label || TYPE_CONFIG[props.type]?.label || '등록번호',
  ...attrs, // 부모가 보낸 help-text, message 등을 그대로 통과시킴
}))

// 뒷자리 마스킹 여부 계산
const inputType = computed(() => {
  const isMask = TYPE_CONFIG[props.type]?.isPassword
  return isMask && !props.readonly ? 'password' : 'text'
})
</script>
