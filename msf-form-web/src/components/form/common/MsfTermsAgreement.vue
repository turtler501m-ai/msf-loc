<template>
  <div>
    <MsfTitleArea title="약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" description="" required />
    <MsfButtonGroup margin="1">
      <MsfButton variant="toggle" disabled>가입조건 조회</MsfButton>
      <MsfButton variant="toggle">가입조건 조회</MsfButton>
      <MsfButton variant="toggle" active>가입조건 조회</MsfButton>
    </MsfButtonGroup>
    <div class="step-result">
      <p class="result-title"><MsfIcon name="titleInfo" /><span>가입조건 조회 결과 개통 진행이 <em class="ut-color-accent">가능(3회선 중 2회선 추가 가능)</em> 합니다.</span></p>
      <p class="result-title"><MsfIcon name="titleInfo" /><span>가입조건 조회 결과 개통 진행이 <em class="ut-color-point">불가능(3회선 중 2회선 추가 가능)</em> 합니다.</span></p>
      <ul class="result-list">
        <li><span class="result-txt">가입제한</span><MsfFlag data="가능" color="accent" /></li>
        <li><span class="result-txt">가입한도</span><MsfFlag data="불가능" color="gray" /></li>
        <li><span class="result-txt">미납</span><MsfFlag data="가능" color="accent" /></li>
        <li><span class="result-txt">상습해지이력</span><MsfFlag data="가능" color="accent" /></li>
        <li><span class="result-txt">할부할인</span><MsfFlag data="불가능" color="gray" /></li>
      </ul>
    </div>
  </div>
</template>
<script setup>
import { ref, defineModel } from 'vue'
const model = defineModel({ type: Object, required: true })
const agreementRef = ref(null)
</script>