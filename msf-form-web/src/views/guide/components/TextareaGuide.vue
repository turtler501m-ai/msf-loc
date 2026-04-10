<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfTextarea"
      title="MsfTextarea"
      description="높이를 지정 하지 않으면 기본높이로 지정됩니다."
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfTextarea v-bind="props" v-model="formValue" />
        <div style="margin-top: 20px; font-size: 14px">
          <strong>입력 내용:</strong> {{ formValue }}
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const formValue = ref('')

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  id: undefined,
  name: undefined,
  height: undefined,
  placeholder: undefined,
  disabled: false,
  readonly: false,
  error: false,
}

// 2. 초기값 정의
const componentState = {
  id: undefined,
  name: undefined,
  height: '200px',
  placeholder: '높이값 지정안하면 기본높이로 지정됨',
  disabled: false,
  readonly: false,
  error: false,
}
</script>
