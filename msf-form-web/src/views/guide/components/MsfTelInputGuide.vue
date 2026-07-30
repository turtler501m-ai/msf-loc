<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfTelInput"
      title="MsfTelInput"
      description="MsfTelInput 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfStack type="field">
          <MsfTelInput
            v-bind="props"
            v-model:telNo1="telNo1"
            v-model:telNo2="telNo2"
            v-model:telNo3="telNo3"
          />
        </MsfStack>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const telNo1 = ref('')
const telNo2 = ref('')
const telNo3 = ref('')

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
