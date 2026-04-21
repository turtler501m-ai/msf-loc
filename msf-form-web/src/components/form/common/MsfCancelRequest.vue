<script setup>
const formData = defineModel({ type: Object, required: true })
</script>
<template>
  <MsfTitleArea title="해지 신청" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="사용여부" tag="div" required>
      <MsfChip
        v-model="formData.isActive"
        name="inp-isActive"
        :data="[
          { value: 'isActive1', label: 'kt M mobile 재사용' },
          { value: 'isActive2', label: 'kt 재사용' },
          { value: 'isActive3', label: 'skt 사용' },
          { value: 'isActive4', label: 'lgt 사용' },
          { value: 'isActive5', label: '기타' },
        ]"
      />
    </MsfFormGroup>
  </MsfStack>
</template>
