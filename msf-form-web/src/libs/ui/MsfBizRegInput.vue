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
    :id="bizNo2Id"
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
    type="password"
    v-model="bizNo3"
    :id="bizNo3Id"
    :error="error"
    :readonly="readonly"
    :disabled="disabled"
    placeholder="뒤 5자리"
    :ariaLabel="`${props.label.replace('<br>', '')} 뒤 5자리`"
    maxlength="5"
  />
</template>

<script setup>
import { ref, useId, computed } from 'vue'
import { validateBizRegNo } from '@/libs/utils/string.utils'

// 컴포넌트 내부에서 유니크 ID 생성
const uniqueId = useId()
const bizNo2Id = computed(() => `inp-bizNo2-${uniqueId}`)
const bizNo3Id = computed(() => `inp-bizNo3-${uniqueId}`)

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

defineExpose({
  focus: () => {
    if (!bizNo1.value) input1.value?.focus()
    else if (!bizNo2.value) input2.value?.focus()
    else input3.value?.focus()
  },
  isValid: computed(() => {
    const fullBizNo = `${bizNo1.value}-${bizNo2.value}-${bizNo3.value}`
    return validateBizRegNo(fullBizNo)
  }),
})
</script>
