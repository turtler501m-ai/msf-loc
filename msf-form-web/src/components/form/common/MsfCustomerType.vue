<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="고객 유형" tag="div" required>
        <MsfChip
          v-model="model.cstmrTypeCd"
          :name="`${name}-inp-customerType`"
          :data="customerTypeData"
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
          :data="visitTypeData"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { computed, defineModel, defineProps, watch, defineExpose, ref, onMounted } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'

const props = defineProps({
  title: { type: String, default: '고객 유형' },
  name: { type: String, default: 'base' },
  visitTypeCodes: { type: Array, default: () => ['JP', 'GO'] },
  allowedCodes: { type: Array, default: () => [] },
})

const model = defineModel({ type: Object, required: true })

const rawCustomerTypes = ref([])

const test = async () => {
  const list = await getCommonCodeList('CSTMR_TYPE_CD')
  rawCustomerTypes.value = list?.map((item) => ({ value: item.code, label: item.title })) || []
}

const isAuthLocked = computed(() => model.value?.isVerified || model.value?.isSaved)

const customerTypeData = computed(() =>
  rawCustomerTypes.value.map((item) => ({
    ...item,
    disabled:
      isAuthLocked.value ||
      (props.allowedCodes.length > 0 ? !props.allowedCodes.includes(item.value) : false),
  })),
)

const visitTypeData = computed(() =>
  [
    { value: 'V1', label: '직접방문' },
    { value: 'V2', label: '대리인' },
  ].map((item) => ({ ...item, disabled: isAuthLocked.value })),
)

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

onMounted(() => {
  test()
})

const validate = () => {
  if (!model.value.cstmrTypeCd) return false
  if (visitTypeRequired.value && !model.value.cstmrVisitTypeCd) return false
  return true
}

defineExpose({ validate })
</script>
