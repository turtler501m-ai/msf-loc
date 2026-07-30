<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfBirthdayInput"
      title="MsfBirthdayInput"
      description="MsfBirthdayInput 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfBirthdayInput v-bind="props" v-model="birthdayValue" />
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const birthdayValue = ref()

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  id: undefined,
  length: undefined,
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
  length: '8',
  placeholder: '생년월일',
  disabled: false,
  readonly: false,
  error: false,
}
</script>
