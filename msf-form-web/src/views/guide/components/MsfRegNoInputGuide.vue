<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfRegNoInput"
      title="MsfRegNoInput"
      description="MsfRegNoInput 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfStack type="field">
          <MsfRegNoInput
            v-bind="props"
            type="resident"
            v-model:registNo1="registNo1"
            v-model:registNo2="registNo2"
          />
        </MsfStack>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const MSF_REGNO_TYPE = ['resident', 'foreigner', 'corporate', 'res_fore']
const registNo1 = ref('')
const registNo2 = ref('')

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  id: undefined,
  type: {
    description:
      '주민등록번호(resident), 외국인등록번호(foreigner), 법인등록번호(corporate), 주민등록번호/외국인등록번호(res_fore)',
    options: [...MSF_REGNO_TYPE],
    default: 'resident',
  },
  disabled: false,
  readonly: false,
}

/**
 * 2. 가이드 초기 상태 (initialState)
 */
const componentState = {
  id: 'input-id',
  type: 'resident',
  disabled: false,
  readonly: false,
}
</script>
