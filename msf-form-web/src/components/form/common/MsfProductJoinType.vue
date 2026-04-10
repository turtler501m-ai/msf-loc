<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="상품" tag="div" required>
        <MsfChip v-model="model.product" name="inp-product" :disabled="model.isVerified" :data="[{ value: 'MM', label: '휴대폰' }, { value: 'UU', label: 'USIM' }]" />
      </MsfFormGroup>
      <MsfFormGroup label="가입유형" tag="div" required>
        <MsfChip v-model="model.joinType" name="inp-joinType" :disabled="model.isVerified" :data="[{ value: 'MNP3', label: '번호이동' }, { value: 'NAC3', label: '신규가입' }, { value: 'HDN3', label: '기기변경' }]" />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'

const props = defineProps({
  title: { type: String, default: '가입유형 선택' }
})
const model = defineModel({ type: Object, required: true })
</script>