<script setup>
import { defineModel } from 'vue'

const model = defineModel({ type: Object, required: true })
</script>
<template>
  <!-- 단말보험 가입 -->
  <MsfTitleArea title="단말보험 가입" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="안심 보험 가입" tag="div">
      <MsfStack type="field" class="ut-w100p">
        <MsfSelect
          title="추천 카테고리"
          v-model="model.insuranceSelect1"
          :options="[
            { label: '추천 카테고리1', value: 'insuranceSelect1-1' },
            { label: '추천 카테고리2', value: 'insuranceSelect1-2' },
          ]"
          placeholder="추천 카테고리"
          class="ut-w-300"
        />
        <MsfSelect
          title="추천 카테고리"
          v-model="model.insuranceSelect2"
          :options="[
            { label: '안드로이드 플래티넘 / 월 기본료 4,900원1', value: 'insuranceSelect2-1' },
            { label: '안드로이드 플래티넘 / 월 기본료 4,900원2', value: 'insuranceSelect2-2' },
          ]"
          placeholder="추천 카테고리"
          class="ut-flex-1"
        />
        <MsfButton variant="toggle">확인</MsfButton>
      </MsfStack>
    </MsfFormGroup>
  </MsfStack>
  <!-- // 단말보험 가입 -->
  <!-- 단말보험 가입 약관 동의 -->
  <MsfTitleArea title="단말보험 가입 약관 동의" />
  <MsfAgreementGroup policy="join" ref="agreementRef" required />
  <!-- // 단말보험 가입 약관 동의 -->
</template>
