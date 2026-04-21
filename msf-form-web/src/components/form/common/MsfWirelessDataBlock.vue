<script setup>
import { defineModel } from 'vue'

const model = defineModel({ type: Object, required: true })
</script>
<template>
  <!-- 무선데이터차단 서비스 -->
  <MsfTitleArea title="무선데이터차단 서비스" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="무선데이터차단<br/>이용 여부" tag="div" required>
      <MsfChip
        v-model="model.blockService"
        name="inp-blockService"
        :data="[
          { value: 'blockService1', label: '무선데이터 이용' },
          { value: 'blockService2', label: '무선데이터차단 서비스 이용' },
        ]"
      >
        <template #endSlot>
          <MsfButton variant="toggle">확인</MsfButton>
        </template>
      </MsfChip>
    </MsfFormGroup>
  </MsfStack>
  <!-- // 무선데이터차단 서비스 -->
</template>
