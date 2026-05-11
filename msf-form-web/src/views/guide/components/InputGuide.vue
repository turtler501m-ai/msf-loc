<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfInput"
      title="MsfInput"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfInput v-bind="props" v-model="inputValue" />
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

const inputValue = ref('')

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  id: undefined,
  clearable: {
    default: true,
    description: '비활성화 여부',
  },
  disabled: false,
  readonly: false,
  error: false,
  displayMask: {
    default: false,
    description:
      '입력값은 그대로 두고, 화면상에서 글자를 가림 (중간수정방지, 복사금지, 단방향 입력 보장, type="text")',
  },
  displayMaskChar: {
    default: '●',
    description: 'displayMask가 true일 때 화면에 표시할 문자',
  },
}

// 2. 초기값 정의
const componentState = {
  id: 'input-id',
  clearable: false,
  disabled: false,
  readonly: false,
  error: false,
  displayMask: false,
  displayMaskChar: '●',
}
</script>
