<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="상품" tag="div" required>
        <MsfChip
          v-model="model.productType"
          name="inp-product"
          :disabled="model.isVerified || model.isSaved"
          :data="productCodes"
        />
      </MsfFormGroup>
      <MsfFormGroup label="가입유형" tag="div" required>
        <MsfChip
          v-model="model.joinType"
          name="inp-joinType"
          :disabled="model.isVerified || model.isSaved"
          :data="filteredJoinTypeCodes"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>
<script setup>
import { defineModel, defineProps, computed, watch } from 'vue'
import { useCommonCode } from '@/libs/utils/comn.utils'

const props = defineProps({
  title: { type: String, default: '가입유형 선택' },
  authFlags: { type: Object, default: () => ({}) },
})
const model = defineModel({ type: Object, required: true })

// 공통코드 호출 및 모델의 초기값이 비어있을 경우 자동 할당
const { codeList: productCodes } = useCommonCode('REQ_BUY_TYPE_CD', model, 'productType', 'MM')
const { codeList: joinTypeCodes } = useCommonCode('OPER_TYPE_CD', model, 'joinType', 'MNP3')

// USIM 상품 선택 시 기기변경(HDN3) 옵션 제외
const filteredJoinTypeCodes = computed(() => {
  if (model.value.productType === 'UU') {
    return joinTypeCodes.value.filter((code) => code.value !== 'HDN3')
  }
  return joinTypeCodes.value
})

// 상품 변경 시 가입유형 기본값으로 초기화
watch(
  () => model.value.productType,
  (newVal, oldVal) => {
    // 마운트 시 최초 세팅은 무시하고 실제로 값을 변경했을 때만 동작
    if (oldVal !== undefined && oldVal !== newVal) {
      model.value.joinType = 'MNP3' // 기본값 번호이동으로 리셋
    }
  },
)

const validate = () => {
  return true
}

defineExpose({ validate })
</script>
