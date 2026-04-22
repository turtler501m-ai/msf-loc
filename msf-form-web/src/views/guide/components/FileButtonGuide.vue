<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfFileButton"
      title="MsfFileButton"
      description="파일업로드 버튼"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfFileButton v-bind="props" v-model="attachedFile" accept=".jpg, .png, .pdf" />
        <div style="margin-top: 20px; font-size: 14px">
          <strong>선택된 파일명 예시:</strong> {{ attachedFile?.name }}
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'

import { GuideUnit } from '@/views/guide/components'

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  id: undefined,
  btnLabel: undefined,
  accept: '*',
  // multiple: false,
  disabled: false,
}

// 2. 초기값 정의
const componentState = {
  id: undefined,
  btnLabel: undefined,
  accept: '*',
  // multiple: false,
  disabled: false,
}

// 파일첨부버튼
const attachedFile = ref(null)
</script>
