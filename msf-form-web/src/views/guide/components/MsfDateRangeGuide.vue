<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfDateRange"
      title="MsfDateRange"
      description="MsfDateRange 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <div style="width: fit-content">
          <MsfDateRange v-bind="props" v-model="dateRangeValue" />
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const dateRangeValue = ref()

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  id: undefined,
  showQuickButtons: undefined,
}

/**
 * 2. 가이드 초기 상태 (initialState)
 */
const componentState = {
  id: 'input-id',
  showQuickButtons: false,
}
</script>
