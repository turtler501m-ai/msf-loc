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
  title: {
    default: '선택',
    description: 'dialog로 띄울때 타이틀 지정 필요시사용',
  },
  options: {
    default: '[]',
    description: '체크박스 옵션 배열입니다.',
  },
  groupCode: {
    default: '',
    description:
      '공통코드 그룹코드 설정 시 해당 그룹코드로 공통코드 목록이 조회되어 옵션으로 사용됩니다. options props와 groupCode props를 함께 사용할 경우 options가 우선적으로 적용됩니다.',
  },
  disabledItems: {
    default: '[]',
    description:
      '비활성화할 옵션의 value 배열입니다. groupCode로 옵션이 설정되어 있을 때, 조회된 옵션들 중 value가 disabledItems 배열에 포함된 경우 해당 옵션이 비활성화됩니다.',
  },
  isFull: {
    default: false,
    description: 'true로 설정 시, 옵션 선택 시 value가 아닌 선택된 데이터 객체가 반환됩니다.',
  },
  placeholder: undefined,
  disabled: false,
  readonly: false,
  error: false,
  allChecked: {
    default: undefined,
    description:
      'allChecked가 true이면 placeholder를 참조하여 첫번째 옵션리스트에 "placeholder + 전체" 텍스트를 붙인다.',
  },
}

// 2. 초기값 정의
const componentState = {
  name: undefined,
  title: undefined,
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
  disabledItems: [],
  isFull: false,
  placeholder: '유형',
  disabled: false,
  readonly: false,
  error: false,
  allChecked: false,
}
</script>
