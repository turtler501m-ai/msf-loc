<script setup>
import { post } from '@/libs/api/msf.api'
import { computed, onMounted, ref, watch } from 'vue'

const model = defineModel({ type: Object, required: true })
const agencyOptions = ref([])
const planCategoryOptions = ref([])
const planOptions = ref([])
const isEditable = computed(() => (model.value.planSelectType === 'CURRENT' ? false : true))

// 요금제 카테고리 조회
const fetchPlanCategories = async () => {
  try {
    const res = await post('/api/form/rate/category/list', {
      prodCtgTypeCd: 'P',
    })

    planCategoryOptions.value = res?.data?.map((item) => ({
      label: item.ctgNm,
      value: item.ctgCd,
    }))
  } catch (e) {
    console.error('Failed to fetch plan categories:', e)
  }
}

// 요금제 목록 조회 (카테고리 선택 시)
const fetchPlans = async (ctgCd) => {
  try {
    const res = await post('/api/form/rate/list', {
      orgnId: '1100033726',
      sprtTp: 'KD',
      plcySctnCd: '01',
      salePlcyCd: 'N2022011018381',
    })
    planOptions.value = res?.data?.map((item) => ({
      label: item.rateNm || item.ctgNm,
      value: item.rateCd || item.ctgCd || item.prodId,
    }))
  } catch (e) {
    console.error('Failed to fetch plans:', e)
  }
}

// 현재 사용중인 요금제 조회
const fetchCurrentPlan = async () => {
  try {
    const { ctn, ncn, custId, userId } = model.value
    const res = await post('/api/form/active-charge-plan/get', {
      ctn,
      ncn,
      custId,
      userId,
    })
    planOptions.value = [{ value: res?.data?.prodId, label: res?.data?.prodNm }]
    model.value.planName2 = res?.data?.prodId
    model.value.orgProdId = res?.data?.prodId
  } catch (e) {
    console.error('Failed to fetch plans:', e)
  }
}

// 대리점 목록 조회
const fetchAgencies = async () => {
  try {
    const res = await post('/api/form/agent/list', { shopOrgnId: 'V000001083' })
    const data = res.data || res
    const list = Array.isArray(data) ? data : data && typeof data === 'object' ? [data] : []

    agencyOptions.value = list.map((item) => ({
      label: item.orgnNm || item.cntpntNm || '대리점명 없음',
      value: item.ktOrgId || item.shopOrgnId || '',
    }))

    // 결과가 1개뿐이거나, 현재 선택된 값이 없으면 첫 번째 항목 자동 선택
    if (agencyOptions.value.length > 0) {
      if (!model.value.agency || agencyOptions.value.length === 1) {
        model.value.agency = agencyOptions.value[0].value
        model.value.agentCd = agencyOptions.value[0].value
      }
    }
  } catch (error) {
    console.error('Failed to fetch agencies:', error)
  }
}

const validate = () => {
  if (!model.value.planName2 || !model.value.agency) return false

  return true
}

defineExpose({ validate })

// 요금제 카테고리가 변경될 때 하위 요금제 목록 다시 불러오기
watch(
  () => model.value.planName1,
  (newVal) => {
    if (!model.value.isSaved) {
      model.value.planName2 = ''
    }
    fetchPlans(newVal)
  },
)

watch(
  () => model.value.userId,
  (newVal) => {
    fetchCurrentPlan()
  },
)

onMounted(() => {
  fetchPlanCategories()
  fetchAgencies()
})
</script>
<template>
  <MsfTitleArea title="요금제 정보" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="요금제" tag="div" required>
      <MsfChip
        v-model="model.planSelectType"
        name="inp-product"
        :data="[
          { value: 'CHANGE', label: '요금제 변경' },
          { value: 'CURRENT', label: '현재 요금제 사용' },
        ]"
      />
      <MsfSelect
        title="요금제"
        v-model="model.planName1"
        :options="planCategoryOptions"
        class="ut-w100p"
        placeholder="추천 요금제"
        :disabled="!isEditable"
      />
      <MsfSelect
        title="요금제"
        v-model="model.planName2"
        :options="planOptions"
        class="ut-w100p"
        :disabled="!isEditable"
      />
    </MsfFormGroup>
    <MsfFormGroup label="대리점" tag="div" required>
      <MsfSelect
        title="대리점 선택"
        v-model="model.agency"
        :options="agencyOptions"
        class="ut-w-300"
        placeholder="대리점 선택"
      />
    </MsfFormGroup>
  </MsfStack>
</template>
