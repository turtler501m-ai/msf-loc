<script setup>
const formData = defineModel({ type: Object, required: true })
</script>
<template>
  <MsfTitleArea title="해지 후 연락처" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="연락 전화번호" required helpText="※ 개통신청서 발송">
      <MsfStack type="field">
        <MsfNumberInput v-model="formData.afterTel1" placeholder="010/지역번호" />
        <span class="unit-sep">-</span>
        <MsfNumberInput
          v-model="formData.afterTel2"
          id="inp-afterTel2"
          placeholder="가운데 4자리"
        />
        <span class="unit-sep">-</span>
        <MsfNumberInput v-model="formData.afterTel3" id="inp-afterTel3" placeholder="뒤 4자리" />
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup label="해지 후 연락 수단" tag="div">
      <MsfChip
        v-model="formData.postMethod"
        name="inp-postMethod"
        :data="[
          { value: 'postMethod1', label: '우편' },
          { value: 'postMethod2', label: '이메일' },
        ]"
      />
    </MsfFormGroup>
  </MsfStack>
</template>
