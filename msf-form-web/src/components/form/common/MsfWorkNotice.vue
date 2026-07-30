<template>
  <!-- 안내 -->
  <div class="error-wrap">
    <img src="@/assets/images/errorIcon.svg" alt="에러" class="error-icon" />
    <div class="error-msg">
      <strong class="error-tit">시스템 작업 중입니다.</strong>
      <p class="error-info">예정시간 : {{ formatStartDate }} ~ {{ formatEndDate }}</p>
    </div>
    <div class="error-desc"></div>
  </div>
  <!-- // 안내 -->
</template>

<script setup>
import { computed } from 'vue'
import { formatDatetimeMinutes } from '@/libs/utils/date.utils'

const model = defineModel({ type: Object, required: true })

const formatStartDate = computed(() => formatDatetimeMinutes(model.value?.startDate))
const formatEndDate = computed(() => formatDatetimeMinutes(model.value?.endDate))
</script>

<style lang="scss" scoped></style>
