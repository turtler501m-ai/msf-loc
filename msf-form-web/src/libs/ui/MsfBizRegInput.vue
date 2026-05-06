<template>
  <!-- 사업자등록번호  -->
  <MsfNumberInput
    ref="input1"
    v-model="bizNo1"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="앞 3자리"
    :ariaLabel="`${props.label.replace('<br>', '')} 앞 3자리`"
    maxlength="3"
    @maxlength="input2?.focus()"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    ref="input2"
    v-model="bizNo2"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="가운데 2자리"
    :ariaLabel="`${props.label.replace('<br>', '')} 가운데 2자리`"
    maxlength="2"
    @maxlength="input3?.focus()"
  />
  <span class="unit-sep">-</span>
  <MsfNumberInput
    ref="input3"
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
import { ref } from 'vue'

// v-model 선언
const bizNo1 = defineModel('bizNo1')
const bizNo2 = defineModel('bizNo2')
const bizNo3 = defineModel('bizNo3')

const input1 = ref(null)
const input2 = ref(null)
const input3 = ref(null)

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
