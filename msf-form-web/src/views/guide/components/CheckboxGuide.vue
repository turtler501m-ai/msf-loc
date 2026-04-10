<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfCheckbox"
      title="MsfCheckbox"
      description="여러 개 나열하는 경우 MsfCheckboxGroup 사용합니다."
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfCheckbox v-bind="props" v-model="isAgreed" :label="props.label" :id="props.id" />
        <div style="margin-top: 20px; font-size: 14px">
          <strong>상태:</strong> {{ isAgreed ? '체크 함' : '체크 안 함' }}
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const isAgreed = ref(false)

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  variant: ['default', 'boxed'],
  size: ['small', 'medium'],
  label: '레이블',
  id: '',
  disabled: false,
  error: false,
}

// 2. 초기값 정의
const componentState = {
  variant: '',
  size: '',
  label: '체크박스',
  id: '',
  disabled: false,
  error: false,
}
</script>
