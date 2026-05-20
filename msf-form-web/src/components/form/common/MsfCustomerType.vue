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
      <MsfFormGroup label="대리점" tag="div" required>
        <MsfSelect
          v-model="model.agency"
          :options="agencyOptions"
          :disabled="disableAgencyWhenAuthLocked && isAuthLocked"
          class="ut-w-300"
          placeholder="대리점 선택"
          title="대리점 선택"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { computed, defineModel, defineProps, watch, defineExpose, ref, onMounted } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { post } from '@/libs/api/msf.api'

const props = defineProps({
  title: { type: String, default: '고객 유형' },
  name: { type: String, default: 'base' },
  visitTypeCodes: { type: Array, default: () => ['JP', 'GO'] },
  allowedCodes: { type: Array, default: () => [] },
  disableAgencyWhenAuthLocked: { type: Boolean, default: false },
})
const emit = defineEmits(['change-customer-type'])

const model = defineModel({ type: Object, required: true })

const rawCustomerTypes = ref([])
const agencyOptions = ref([])

const test = async () => {
  const list = await getCommonCodeList('CSTMR_TYPE_CD')
  rawCustomerTypes.value = list?.map((item) => ({ value: item.code, label: item.title })) || []
}

/**
 * 대리점 목록 조회
 */
const fetchAgencies = async () => {
  try {
    const res = await post('/api/form/agent/list', {})
    const extractData = (res) => {
      if (!res) return []
      if (Array.isArray(res)) return res
      if (res.data) {
        if (Array.isArray(res.data)) return res.data
        return [res.data]
      }
      return []
    }
    const list = extractData(res)

    agencyOptions.value = list.map((item) => ({
      label: item.orgnNm || item.cntpntNm || '대리점명 없음',
      value: item.shopOrgnId || '',
    }))

    // 결과가 1개뿐이면 자동 선택
    if (model.value.agentCd && !model.value.agency) {
      model.value.agency = model.value.agentCd
    } else if (agencyOptions.value.length === 1) {
      model.value.agency = agencyOptions.value[0].value
      model.value.agentCd = agencyOptions.value[0].value
    }
  } catch (error) {
    console.error('Failed to fetch agencies:', error)
  }
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
  (newVal, oldVal) => {
    if (oldVal !== undefined && oldVal !== newVal) {
      emit('change-customer-type', { newVal, oldVal, name: props.name })
    }

    if (props.visitTypeCodes.includes(newVal)) {
      // 법인/공공기관인 경우 직접방문(V1) 기본 선택
      model.value.cstmrVisitTypeCd = 'V1'
    } else {
      model.value.cstmrVisitTypeCd = ''
    }
  },
)

// 대리점 선택 시 agentCd 업데이트
watch(
  () => model.value.agency,
  (newVal) => {
    if (newVal) {
      model.value.agentCd = newVal
    }
  },
)

onMounted(() => {
  test()
  fetchAgencies()
})

const validate = () => {
  if (!model.value.cstmrTypeCd) return false
  if (visitTypeRequired.value && !model.value.cstmrVisitTypeCd) return false
  if (!model.value.agency) return false
  return true
}

defineExpose({ validate })
</script>
