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
        <MsfCheckbox
          v-bind="props"
          v-model="isAgreed"
          :label="props.label"
          :id="props.id"
          :returndata="null"
        />
        <div style="margin-top: 10px; font-size: 14px">
          <strong>상태:</strong> {{ isAgreed ? '체크 함' : '체크 안 함' }}
        </div>
        <br />
        <br />
        <p class="ut-mb-10">
          - returndata 속성 사용 시 부모가 전달하는 { true: 'Y', false: 'N' }와 같은 객체를 받음<br />
          - :returndata="{ true: 'Y', false: 'N' }"
        </p>
        <MsfCheckbox v-bind="props" v-model="isReturnData" :label="props.label" :id="props.id" />
        <div style="margin-top: 0px; font-size: 14px">
          <p><strong>상태:</strong> {{ isReturnData === 'Y' ? '체크 함' : '체크 안 함' }}</p>
          <p><strong>디버깅용 현재값:</strong> {{ isReturnData }}</p>
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

// 기존 상태
const isAgreed = ref(false)
// 체크박스 returndata
const isReturnData = ref('N')

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  variant: ['default', 'boxed'],
  size: ['small', 'medium'],
  label: '레이블',
  id: '',
  disabled: false,
  error: false,
  returndata: '',
}

// 2. 초기값 정의
const componentState = {
  variant: '',
  size: '',
  label: '체크박스',
  id: '',
  disabled: false,
  error: false,
  returndata: { true: 'Y', false: 'N' },
}
</script>
