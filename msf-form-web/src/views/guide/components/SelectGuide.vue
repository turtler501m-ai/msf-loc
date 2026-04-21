<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfSelect"
      title="MsfSelect"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfSelect v-bind="props" v-model="selectedCity" />
        <div style="margin-top: 20px; font-size: 14px">
          <strong>선택된 값:</strong> {{ selectedCity }}
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const selectedCity = ref('')

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  name: undefined,
  options: {
    default: '[]',
    description: '체크박스 옵션 배열입니다.',
  },
  groupCode: {
    default: '',
    description:
      '공통코드 그룹코드 설정 시 해당 그룹코드로 공통코드 목록이 조회되어 옵션으로 사용됩니다. options props와 groupCode props를 함께 사용할 경우 options가 우선적으로 적용됩니다.',
  },
  placeholder: undefined,
  disabled: false,
  readonly: false,
  error: false,
}

// 2. 초기값 정의
const componentState = {
  name: undefined,
  options: [
    { label: '선택1', value: 'select1' },
    { label: '선택2', value: 'select2' },
    { label: '선택3', value: 'select3' },
    { label: '선택4', value: 'select4' },
    { label: '선택5', value: 'select5' },
    { label: '선택6', value: 'select6' },
    { label: '선택7', value: 'select7', disabled: true },
  ],
  groupCode: '',
  placeholder: '유형',
  disabled: false,
  readonly: false,
  error: false,
}
</script>
