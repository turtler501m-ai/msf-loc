<script setup>
import { defineModel } from 'vue'

const formData = defineModel({ type: Object, required: true })
</script>
<template>
  <MsfTitleArea title="데이터쉐어링 가입/해지" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="데이터쉐어링<br/>이용 여부" tag="div" required>
      <MsfChip
        v-model="formData.shareUseState"
        name="inp-shareUseState"
        :data="[
          { value: 'shareUseState1', label: '데이터쉐어링 가입' },
          { value: 'shareUseState2', label: '데이터쉐어링 해지' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="휴대폰 번호" required>
      <MsfStack type="field">
        <MsfInput
          v-model="formData.sharePhoneNum"
          placeholder="휴대폰번호 입력 (“-” 제외)"
          class="ut-w-300"
        />
        <MsfButton variant="toggle" disabled>인증</MsfButton>
        <MsfButton variant="toggle">인증</MsfButton>
        <MsfButton variant="toggle" active>인증 완료</MsfButton>
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup label="USIM 번호" required>
      <MsfStack type="field">
        <MsfInput v-model="formData.shareUsimNum" placeholder="USIM 번호 19자리" class="ut-w-300" />
        <MsfButton variant="subtle">스캔하기</MsfButton>
        <MsfButton variant="toggle" disabled>USIM 번호 유효성 체크</MsfButton>
        <MsfButton variant="toggle">USIM 번호 유효성 체크</MsfButton>
        <MsfButton variant="toggle" active>USIM 번호 유효성 체크 완료</MsfButton>
      </MsfStack>
    </MsfFormGroup>
  </MsfStack>
  <!-- 데이터쉐어링 가입/해지 약관 동의 -->
  <MsfTitleArea title="데이터쉐어링 가입/해지 약관 동의" />
  <MsfAgreementGroup policy="join" ref="agreementRef" required />
  <!-- // 데이터쉐어링 가입/해지 약관 동의 -->
</template>
