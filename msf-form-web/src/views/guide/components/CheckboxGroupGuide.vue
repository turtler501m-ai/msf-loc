<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfCheckboxGroup"
      title="MsfCheckboxGroup"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfCheckboxGroup v-model="selectedInterests" v-bind="props" />
        <br />
        <div style="margin-top: 20px; font-size: 14px">
          <strong>선택된 값:</strong> {{ selectedInterests }}
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const selectedInterests = ref(['check1'])

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  vertical: {
    default: false,
    description: '세로 정렬 여부',
  },
  disabled: {
    default: false,
    description: '비활성화 여부',
  },
  error: false,
  name: '',
  options: {
    default: '[]',
    description: '체크박스 옵션 배열입니다.',
  },
  // size: ['small', 'medium', 'large']
}

// 2. 초기값 정의
const componentState = {
  vertical: false,
  disabled: false,
  error: false,
  name: undefined,
  options: [
    { value: 'check1', label: '체크박스1' },
    { value: 'check2', label: '체크박스2' },
    { value: 'check3', label: '체크박스3' },
    { value: 'check4', label: '체크박스4', disabled: true },
  ],
}
</script>
