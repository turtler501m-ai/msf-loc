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
  type: {
    description: '패스워드 reveal props 상태 확인용',
    options: ['text', 'password', '등등....'],
    default: 'text',
  },
  variant: {
    description: '스타일 (underline: 로그인 에서 사용하는 스타일)',
    options: ['default', 'underline'],
    default: 'default',
  },
  id: undefined,
  clearable: {
    default: true,
    description: '비활성화 여부',
  },
  reveal: {
    default: false,
    description: '비밀번호 숨김/보임 버튼 노출여부',
  },
  placeholder: undefined,
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
  type: 'text',
  id: 'input-id',
  clearable: true,
  reveal: false,
  placeholder: '입력하세요.',
  disabled: false,
  readonly: false,
  error: false,
  displayMask: false,
  displayMaskChar: '●',
}
</script>
