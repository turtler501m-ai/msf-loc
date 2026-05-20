<script setup>
import { computed } from 'vue'
import { formatDate } from '@/libs/utils/date.utils'

const formData = defineModel({ type: Object, required: true })

const formattedLstComActvDate = computed({
  get() {
    return formatDate(formData.value.lstComActvDate) || formData.value.lstComActvDate || ''
  },
  set(value) {
    formData.value.lstComActvDate = value
  },
})
</script>

<template>
  <MsfTitleArea title="해지 휴대폰 정보" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="개통일자 확인">
      <MsfInput v-model="formattedLstComActvDate" class="ut-w-300" disabled />
    </MsfFormGroup>
  </MsfStack>
</template>
