<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객 유형" tag="div" required>
        <MsfChip v-model="model.customerType" name="inp-customerType" :disabled="model.isVerified" :data="[{ value: 'NA', label: '내국인' }, { value: 'NM', label: '미성년자(19세 미만)' }, { value: 'FN', label: '외국인(Foreigner)' }, { value: 'FM', label: '외국인(Foreigner) 미성년자' }, { value: 'JP', label: '법인' }, { value: 'PU', label: '공공기관' }]" />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'

const props = defineProps({
  title: { type: String, default: '고객 유형' }
})
const model = defineModel({ type: Object, required: true })
</script>