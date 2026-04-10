<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfChip"
      title="MsfChip"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfChip v-bind="props" v-model="selectedFruit" @change="handleSelectionChange" />
        <div style="margin-top: 20px; font-size: 14px">
          <strong>{{ !props.multiple ? '단일' : '다중' }} 선택 값:</strong> {{ selectedFruit }}
          <p>props multiple 설정시 다중선택으로 변경됨</p>
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const selectedFruit = ref('today')

// 선택값 변경 시 콘솔 찍기
const handleSelectionChange = (val) => {
  console.log('부모에서 받은 선택값:', val)
}

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  variant: {
    description: '스타일',
    options: ['outlined', 'period'],
    default: 'period',
  },
  multiple: {
    description: '다중선택 (체크박스 여부)',
    default: false,
  },
  name: '',
  data: {
    default: '[]',
    description: '체크박스 옵션 배열입니다.',
  },
}

// 2. 초기값 정의
const componentState = {
  variant: undefined,
  multiple: false,
  name: undefined,
  data: [
    { value: 'all', label: '전체' },
    { value: 'chip1', label: '선택1' },
    { value: 'chip2', label: '선택2' },
    { value: 'chip3', label: '선택3' },
    { value: 'chip4', label: '선택4' },
  ],
}
</script>
