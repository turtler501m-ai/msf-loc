<script setup>
const formData = defineModel({ type: Object, required: true })
</script>
<template>
  <MsfTitleArea title="가입유형 선택" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="개통일자 확인">
      <MsfInput v-model="formData.openingDate" class="ut-w-300" disabled />
    </MsfFormGroup>
    <MsfFormGroup label="대리점" tag="div" required>
      <MsfSelect
        title="대리점 선택"
        v-model="formData.agencyName"
        :options="[
          { label: '대리점1', value: 'agencyName1' },
          { label: '대리점2', value: 'agencyName2' },
        ]"
        class="ut-w-300"
        placeholder="대리점 선택"
      />
    </MsfFormGroup>
  </MsfStack>
</template>
