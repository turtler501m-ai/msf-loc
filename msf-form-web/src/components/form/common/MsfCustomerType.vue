<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객 유형" tag="div" required>
        <MsfChip
          v-model="model.cstmrTypeCd"
          name="inp-customerType"
          :disabled="model.isVerified || model.isSaved"
          :data="customerTypeCodes"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="['JP', 'GO'].includes(model.cstmrTypeCd)"
        label="방문 유형"
        tag="div"
        required
      >
        <MsfChip
          v-model="model.cstmrVisitTypeCd"
          name="inp-visitType"
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
import { defineModel, defineProps, watch, defineExpose } from 'vue'
import { useCommonCode } from '@/libs/utils/comn.utils'

const props = defineProps({
  title: { type: String, default: '고객 유형' },
})
const model = defineModel({ type: Object, required: true })

const { codeList: customerTypeCodes } = useCommonCode('CSTMR_TYPE_CD', model, 'cstmrTypeCd', 'NA')

watch(
  () => model.value.cstmrTypeCd,
  (newVal) => {
    if (!['JP', 'GO'].includes(newVal)) {
      model.value.cstmrVisitTypeCd = ''
    }
  },
)

const validate = () => {
  return true
}

defineExpose({ validate })
</script>
