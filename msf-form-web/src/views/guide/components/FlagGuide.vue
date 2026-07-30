<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfFlag"
      title="MsfFlag"
      description="화면에 사용되는 뱃지 형태의 플래그 디자인"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfFlag v-bind="props" v-model="formValue" />
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const formValue = ref('플래그')
const FLAG_COLOR = [
  'gray',
  'accent',
  'accent2',
  '--- 서비스변경에서 쓰이는 컬러 ---',
  'create',
  'close',
  'change',
  'done',
  'ready',
  'locked',
  'fail',
]

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  variant: ['filled', 'outlined'],
  data: undefined,
  size: ['small', 'medium', 'large'],
  color: [...FLAG_COLOR],
}

// 2. 초기값 정의
const componentState = {
  variant: 'filled',
  data: '플래그',
  size: 'small',
  color: 'gray',
}
</script>
