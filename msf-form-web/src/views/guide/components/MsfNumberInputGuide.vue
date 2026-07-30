<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfNumberInput"
      title="MsfNumberInput"
      description="MsfNumberInput 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfNumberInput v-bind="props" v-model="numberValue" />
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const numberValue = ref()

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  id: undefined,
  maxlength: undefined,
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
  maxlength: '10',
  placeholder: '숫자만 입력',
  disabled: false,
  readonly: false,
  error: false,
}
</script>
