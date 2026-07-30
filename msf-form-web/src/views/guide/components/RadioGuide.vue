<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfRadio"
      title="MsfRadio"
      description="여러 개 나열하는 경우 MsfRadioGroup 사용"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfRadio v-bind="props" v-model="deliveryMethod" value="radio1" :label="props.label" />
        <div style="margin-top: 20px; font-size: 14px">
          <strong>선택된 값:</strong> {{ deliveryMethod }}
        </div>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'
import { RADIO_VARIANTS } from '@/libs/ui/base/MsfRadio.vue'

const deliveryMethod = ref('')

// 1. 컨트롤러에 나타날 옵션 정의
const componentConfig = {
  variant: {
    description: '라디오 스타일 지정 (type2는 체크박스와 동일한 모양)',
    options: [...RADIO_VARIANTS],
    default: 'default',
  },
  // size: ['medium'],
  label: '레이블',
  id: '',
  value: '',
  disabled: false,
  error: false,
}

// 2. 초기값 정의
const componentState = {
  variant: undefined,
  // size: undefined,
  label: '라디오',
  id: undefined,
  value: 'radio1',
  disabled: false,
  error: false,
}
</script>
