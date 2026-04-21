<script setup>
import { defineModel } from 'vue'

const model = defineModel({ type: Object, required: true })
</script>
<template>
  <MsfTitleArea title="분실복구/일시정지해제 신청" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="일시정지 해제<br/>비밀번호">
      <MsfStack type="field">
        <MsfInput
          type="password"
          v-model="model.unLockPw"
          placeholder="일시정지 해제 비밀번호"
          class="ut-w-300"
        />
        <MsfButton variant="toggle" disabled>확인</MsfButton>
        <MsfButton variant="toggle">확인</MsfButton>
        <MsfButton variant="toggle" active>확인 완료</MsfButton>
      </MsfStack>
    </MsfFormGroup>
  </MsfStack>
</template>
