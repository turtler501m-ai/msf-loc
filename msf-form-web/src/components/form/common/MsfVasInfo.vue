<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="무료부가서비스" tag="div">
        <MsfChip v-model="model.freeVas" name="inp-freeVas" :data="[{ value: 'freeVas1', label: '무선데이터 차단' }, { value: 'freeVas2', label: '국제전화발신제한' }, { value: 'freeVas3', label: '익명호수신거부' }, { value: 'freeVas4', label: '네트워크유해차단' }, { value: 'freeVas5', label: '데이터로밍완전차단' }, { value: 'freeVas6', label: '링투유알리미' }, { value: 'freeVas7', label: '무선데이터 차단' }, { value: 'freeVas8', label: '국제전화발신제한' }]" multiple readonly />
      </MsfFormGroup>
      <MsfFormGroup label="유료부가서비스" tag="div">
        <MsfCheckboxGroup v-model="model.paidVas" :options="[{ value: 'paidVas1', label: '링투유 990 원' }, { value: 'paidVas2', label: '캐치콜 550 원' }, { value: 'paidVas3', label: '(신)로밍 하루종일 ON 2,200 원/1일' }]" />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps } from 'vue'

const props = defineProps({
  title: { type: String, default: '부가서비스 신청' }
})
const model = defineModel({ type: Object, required: true })
</script>