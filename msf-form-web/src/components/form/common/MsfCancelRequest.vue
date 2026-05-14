<script setup>
const formData = defineModel({ type: Object, required: true })

const CANCEL_USE_COMPANY_OPTIONS = [
  { value: 'KTM', label: 'kt M mobile 재사용' },
  { value: 'KT', label: 'kt 재사용' },
  { value: 'SKT', label: 'skt 사용' },
  { value: 'LGT', label: 'lgt 사용' },
  { value: 'ETC', label: '기타' },
]
</script>
<template>
  <MsfTitleArea title="해지 신청" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="해지 후 사용 통신사" tag="div" required>
      <MsfChip
        v-model="formData.cancelUseCompanyCd"
        name="inp-cancelUseCompanyCd"
        :data="CANCEL_USE_COMPANY_OPTIONS"
      />
    </MsfFormGroup>
  </MsfStack>
</template>
