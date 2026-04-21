<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfRadioGroup"
      title="MsfRadioGroup"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfRadioGroup v-bind="props" v-model="isRadioSelected" />
        <br />
        <div style="margin-top: 20px; font-size: 14px">
          <strong>선택된 값:</strong> {{ isRadioSelected }}
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const isRadioSelected = ref('radio1')

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
  groupCode: {
    default: '',
    description:
      '공통코드 그룹코드 설정 시 해당 그룹코드로 공통코드 목록이 조회되어 옵션으로 사용됩니다. options props와 groupCode props를 함께 사용할 경우 options가 우선적으로 적용됩니다.',
  },
  // size: ['small', 'medium', 'large']
}

// 2. 초기값 정의
const componentState = {
  vertical: false,
  disabled: false,
  error: false,
  name: 'radio-name',
  options: [
    { value: 'radio1', label: '라디오1' },
    { value: 'radio2', label: '라디오2' },
    { value: 'radio3', label: '라디오3' },
    { value: 'radio4', label: '라디오4', disabled: true },
  ],
  groupCode: '',
}
</script>
