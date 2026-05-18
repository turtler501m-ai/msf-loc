<script setup>
import { post } from '@/libs/api/msf.api'
import { computed, onMounted, ref, watch } from 'vue'

const model = defineModel({ type: Object, required: true })
const agencyOptions = ref([])
const planCategoryOptions = ref([])
const planOptions = ref([])

/**
 * API 응답에서 데이터(리스트) 추출
 */
const extractData = (res) => {
  if (!res) return []
  if (Array.isArray(res)) return res
  if (res.data) {
    if (Array.isArray(res.data)) return res.data
    return [res.data]
  }
  return []
}

const props = defineProps({
  title: { type: String, default: '휴대폰 및 요금제 정보' },
  customerData: { type: Object, default: () => ({}) },
})

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
      sprtTp: model.value.discountType || '',
      prodCtgId: ctgCd, // 카테고리 ID 추가
      reqBuyTypeCd: model.value.productType || 'MM', // 상품유형(MM/UU) 전달
      salePlcyCd: model.value.modelSalePolicyCd || '', // 단말기 판매정책 코드
    })
    planOptions.value = res?.data?.map((item) => ({
      label: item.rateNm || item.ctgNm,
      value: item.rateCd || item.ctgCd || item.prodId,
    }))

    // 가입자 나이 계산 (만 65세 이상 여부)
    const getAge = (birthStr) => {
      if (!birthStr || birthStr.length < 6) return 0
      const yearPrefix =
        birthStr.length === 8 ? '' : Number(birthStr.substring(0, 2)) > 50 ? '19' : '20'
      const fullBirth = yearPrefix + birthStr
      const birthDate = new Date(
        fullBirth.substring(0, 4),
        Number(fullBirth.substring(4, 6)) - 1,
        fullBirth.substring(6, 8),
      )
      const today = new Date()
      let age = today.getFullYear() - birthDate.getFullYear()
      const m = today.getMonth() - birthDate.getMonth()
      if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--
      }
      return age
    }

    const userBirth = props.customerData?.cstmrNativeRrn1 || props.customerData?.userBirthDate || ''
    const userAge = getAge(userBirth)
    const isSenior = userAge >= 65

    planOptions.value = extractData(res)
      .filter((item) => {
        // 1. 상품 유형(MM/UU)에 따른 필터링 (API에서 처리되지 않았을 경우를 대비한 추가 필터)
        // item.prodSctnCd 등이 상품 유형을 나타낸다고 가정 (프로젝트 스펙에 따라 조정 필요)
        if (model.value.productType === 'UU' && item.prodSctnCd === 'MM') return false
        if (model.value.productType === 'MM' && item.prodSctnCd === 'UU') return false

        // 2. 시니어 요금제 제한
        if ((item.rateNm || '').includes('시니어') && !isSenior) {
          return false
        }
        return true
      })
      .sort((a, b) => {
        // 3. 우선순위 정렬 (dispSeq 또는 유사 필드 기준)
        return (a.dispSeq || 999) - (b.dispSeq || 999)
      })
      .map((item) => ({
        label: item.rateNm || item.ctgNm,
        value: item.rateCd || item.ctgCd || item.prodId,
        raw: item,
      }))
  } catch (e) {
    console.error('Failed to fetch plans:', e)
  }
}

// 현재 사용중인 요금제 조회
const fetchCurrentPlan = async () => {
  try {
    const { ctn, ncn, custId } = model.value
    const res = await post('/api/form/active-charge-plan/get', {
      ctn,
      ncn,
      custId,
    })
    planOptions.value = [{ value: res?.data?.prodId, label: res?.data?.prodNm }]
    model.value.planNm = res?.data?.prodNm
    model.value.planName2 = res?.data?.prodId
    model.value.orgProdId = res?.data?.prodId
    model.value.orgProdNm = res?.data?.prodNm
    model.value.planAmt = res?.data?.famtTarifAmt
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
  () => model.value.planSelectType,
  (newVal) => {
    if (newVal == 'CURRENT') {
      model.value.planName1 = ''
      model.value.planName2 = model.value.orgProdId
      model.value.planNm = model.value.orgProdNm
      planOptions.value = [{ value: model.value.orgProdId, label: model.value.orgProdNm }]
    } else {
      model.value.planName1 = ''
      model.value.planName2 = ''
    }
  },
)

watch(
  () => model.value.ncn,
  () => {
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
        v-if="model.planSelectType !== 'CURRENT'"
      />
      <MsfSelect
        title="요금제"
        v-model="model.planName2"
        :options="planOptions"
        class="ut-w100p"
        v-if="model.planSelectType !== 'CURRENT'"
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
