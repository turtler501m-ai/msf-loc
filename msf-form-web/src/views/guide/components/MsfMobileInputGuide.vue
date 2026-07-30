<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfMobileInput"
      title="MsfMobileInput"
      description="MsfMobileInput 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfStack type="field">
          <MsfMobileInput
            v-bind="props"
            v-model:number1="phone1"
            v-model:number2="phone2"
            v-model:number3="phone3"
          />
        </MsfStack>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const phone1 = ref()
const phone2 = ref()
const phone3 = ref()

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  id: undefined,
  label: '휴대폰번호',
  disabled: false,
  readonly: false,
}

/**
 * 2. 가이드 초기 상태 (initialState)
 */
const componentState = {
  id: 'input-id',
  label: '휴대폰번호',
  disabled: false,
  readonly: false,
}
</script>
