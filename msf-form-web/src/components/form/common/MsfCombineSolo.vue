<script setup>
import { defineModel } from 'vue'

const formData = defineModel({ type: Object, required: true })
</script>
<template>
  <!-- 아무나 SOLO 결합 -->
  <MsfTitleArea title="아무나 SOLO 결합" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="데이터" required>
      <MsfStack type="field">
        <MsfInput v-model="formData.soloData" placeholder="0 MB" />
        <MsfButton variant="toggle">확인</MsfButton>
      </MsfStack>
    </MsfFormGroup>
  </MsfStack>
  <!-- // 아무나 SOLO 결합 -->
  <!-- 아무나 SOLO 결합 약관 동의 -->
  <MsfTitleArea title="아무나 SOLO 결합 약관 동의" />
  <MsfAgreementGroup policy="join" ref="agreementRef" required />
  <!-- // 아무나 SOLO 결합 약관 동의 -->
</template>
