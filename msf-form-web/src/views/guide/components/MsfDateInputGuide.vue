<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfDateInput"
      title="MsfDateInput"
      description="MsfDateInput 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <div style="width: fit-content">
          <MsfDateInput v-bind="props" v-model="dateValue" />
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const dateValue = ref()

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  id: undefined,
  disabled: false,
  readonly: false,
  error: false,
}

/**
 * 2. 가이드 초기 상태 (initialState)
 */
const componentState = {
  id: 'input-id',
  disabled: false,
  readonly: false,
  error: false,
}
</script>
