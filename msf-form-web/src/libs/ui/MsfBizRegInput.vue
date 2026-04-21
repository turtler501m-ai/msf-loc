<template>
  <!-- 사업자등록번호  -->
  <MsfNumberInput
    v-model="bizNo1"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="앞 3자리"
    :ariaLabel="`${props.label.replace('<br>', '')} 앞 3자리`"
    maxlength="3"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    v-model="bizNo2"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="가운데 2자리"
    :ariaLabel="`${props.label.replace('<br>', '')} 가운데 2자리`"
    maxlength="2"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    v-model="bizNo3"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="뒤 5자리"
    :ariaLabel="`${props.label.replace('<br>', '')} 뒤 5자리`"
    maxlength="5"
  />
</template>

<script setup>
// v-model 선언
const bizNo1 = defineModel('bizNo1')
const bizNo2 = defineModel('bizNo2')
const bizNo3 = defineModel('bizNo3')

// Props 정의
const props = defineProps({
  label: { type: String, default: '사업자등록번호' },
  required: { type: Boolean, default: false },
  helpText: { type: String, default: '' },
  error: { type: Boolean, default: false },
  readonly: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
})
</script>
