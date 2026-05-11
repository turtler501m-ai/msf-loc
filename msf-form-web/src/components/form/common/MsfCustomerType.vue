<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객 유형" tag="div" required>
        <MsfChip
          v-model="model.cstmrTypeCd"
          :name="`${name}-inp-customerType`"
          groupCode="CSTMR_TYPE_CD"
          :disabled="model.isVerified || model.isSaved"
          :data="[]"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="visitTypeRequired && !model.isTrCustomer"
        label="방문 유형"
        tag="div"
        required
      >
        <MsfChip
          v-model="model.cstmrVisitTypeCd"
          :name="`${name}-inp-visitType`"
          :disabled="model.isVerified || model.isSaved"
          :data="[
            { value: 'V1', label: '직접방문' },
            { value: 'V2', label: '대리인' },
          ]"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { computed, defineModel, defineProps, watch, defineExpose } from 'vue'

const props = defineProps({
  title: { type: String, default: '고객 유형' },
  name: { type: String, default: 'base' },
  visitTypeCodes: { type: Array, default: () => ['JP', 'GO'] },
})

const model = defineModel({ type: Object, required: true })

const visitTypeRequired = computed(() => props.visitTypeCodes.includes(model.value.cstmrTypeCd))

watch(
  () => model.value.cstmrTypeCd,
  (newVal) => {
    if (props.visitTypeCodes.includes(newVal)) {
      // 법인/공공기관인 경우 직접방문(V1) 기본 선택
      model.value.cstmrVisitTypeCd = 'V1'
    } else {
      model.value.cstmrVisitTypeCd = ''
    }
  },
)

const validate = () => {
  if (!model.value.cstmrTypeCd) return false
  if (visitTypeRequired.value && !model.value.cstmrVisitTypeCd) return false
  return true
}

defineExpose({ validate })
</script>
