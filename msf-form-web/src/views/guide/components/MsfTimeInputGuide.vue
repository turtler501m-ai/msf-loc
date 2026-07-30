<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfTimeInput"
      title="MsfTimeInput"
      description="MsfTimeInput 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfTimeInput v-bind="props" v-model="timeValue" />
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const timeValue = ref()

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  id: undefined,
  placeholder: undefined,
  disabled: false,
  readonly: false,
  error: false,
}

/**
 * 2. 가이드 초기 상태 (initialState)
 */
const componentState = {
  id: 'input-id',
  placeholder: '00:00:00',
  disabled: false,
  readonly: false,
  error: false,
}
</script>
