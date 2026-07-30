<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfSwitch"
      title="MsfSwitch"
      description="ON/OFF처럼 두 가지 상태를 즉시 전환할 때 사용하는 토글 컴포넌트입니다."
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfSwitch v-bind="props" v-model="inputValue" />
        <div style="margin-top: 20px; font-size: 14px">
          <strong>실시간 입력값:</strong> {{ inputValue }}
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const inputValue = ref(false)

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  variant: {
    description: '스타일 지정',
    options: ['default', 'point'],
    default: 'default',
  },
  size: {
    description: '사이즈 지정',
    options: ['small', 'medium'],
    default: 'medium',
  },
  label: {
    description: '왼쪽에 레이블 문구 설정',
    default: undefined,
  },
  innerLabel: {
    description: '내부 텍스트 설정',
    default: ['ON', 'OFF'],
  },
  showInnerLabel: {
    description: '내부 텍스트 설정여부 (size="small"에서는 설정불가)',
    default: false,
  },
}

// 2. 초기값 정의
const componentState = {
  variant: 'default',
  size: 'medium',
  label: undefined,
  innerLabel: ['ON', 'OFF'],
  showInnerLabel: false,
}
</script>
