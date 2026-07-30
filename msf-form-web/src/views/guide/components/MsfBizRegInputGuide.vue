<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfBizRegInput"
      title="MsfBizRegInput"
      description="MsfBizRegInput 설명"
      :config="componentConfig"
      :initialState="componentState"
    >
      <template #default="{ props }">
        <MsfStack type="field">
          <MsfBizRegInput
            v-bind="props"
            v-model:bizNo1="bizNo1"
            v-model:bizNo2="bizNo2"
            v-model:bizNo3="bizNo3"
          />
        </MsfStack>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit } from '@/views/guide/components'

const MSF_REGNO_TYPE = ['resident', 'foreigner', 'corporate']
const bizNo1 = ref('')
const bizNo2 = ref('')
const bizNo3 = ref('')

/**
 * 1. 컨트롤러 설정 (config)
 */
const componentConfig = {
  id: undefined,
  type: {
    description: '주민등록번호(resident), 외국인등록번호(foreigner), 법인등록번호(corporate)',
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
