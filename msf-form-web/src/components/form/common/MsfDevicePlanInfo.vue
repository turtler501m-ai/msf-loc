<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup v-if="model.productType === 'MM'" label="개통 유형" tag="div" required>
        <MsfChip
          v-model="model.openTypeCd"
          :data="openTypeCdCodes"
          :disabled="model.isSaved"
          name="inp-openTypeCd"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.openTypeCd === '99'"
        label="휴대폰"
        required
        tag="div"
      >
        <MsfSelect
          v-model="model.deviceModel"
          :disabled="model.isSaved"
          :options="deviceOptions"
          class="ut-w-300"
          placeholder="휴대폰 선택"
          title="휴대폰"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.openTypeCd === '99'"
        label="용량"
        required
        tag="div"
      >
        <MsfSelect
          v-model="model.capacity"
          :disabled="model.isSaved"
          :options="capacityOptions"
          class="ut-w-300"
          placeholder="용량 선택"
          title="용량"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.openTypeCd === '99'"
        label="색상"
        required
        tag="div"
      >
        <MsfSelect
          v-model="model.color"
          :disabled="model.isSaved"
          :options="colorOptions"
          class="ut-w-300"
          placeholder="색상 선택"
          title="색상"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.openTypeCd === '99'"
        label="약정기간"
        required
        tag="div"
      >
        <MsfChip
          v-model="model.contractPeriod"
          :data="contractPeriodOptions"
          :disabled="model.isSaved"
          name="inp-contractPeriod"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.openTypeCd === '99'"
        label="단말기 할부기간"
        required
        tag="div"
      >
        <MsfChip
          v-model="model.installmentMonth"
          :data="installmentMonthOptions"
          :disabled="model.isSaved"
          name="inp-installmentMonth"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.openTypeCd === '99'"
        label="할인유형"
        required
        tag="div"
      >
        <MsfChip
          v-model="model.discountType"
          :data="discountTypeOptions"
          :disabled="model.isSaved"
          name="inp-discountType"
        />
      </MsfFormGroup>
      <MsfFormGroup label="요금제" tag="div" required>
        <MsfSelect
          v-model="model.planName1"
          :disabled="model.isSaved"
          :options="planCategoryOptions"
          class="ut-w100p"
          placeholder="요금제 카테고리"
          title="요금제 카테고리"
        />
        <MsfSelect
          v-model="model.planName2"
          :disabled="model.isSaved"
          :options="planOptions"
          class="ut-w100p"
          placeholder="요금제 선택"
          title="요금제 상세"
        />
      </MsfFormGroup>
      <MsfFormGroup label="대리점" tag="div" required>
        <MsfSelect
          v-model="model.agency"
          :disabled="model.isSaved"
          :options="agencyOptions"
          class="ut-w-300"
          placeholder="대리점 선택"
          title="대리점 선택"
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { defineModel, defineProps, onMounted, ref, watch } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { post } from '@/libs/api/msf.api'

/**
 * API 응답에서 데이터(리스트) 추출
 */
const extractData = (res) => {
  if (!res) return []
  if (Array.isArray(res)) return res
  if (res.code === '0000' && Array.isArray(res.data)) return res.data
  if (Array.isArray(res.data)) return res.data
  return []
}

const props = defineProps({
  title: { type: String, default: '휴대폰 및 요금제 정보' },
})
const model = defineModel({ type: Object, required: true })

// 옵션 상태 관리
const openTypeCdCodes = ref([])
const deviceOptions = ref([])
const capacityOptions = ref([])
const colorOptions = ref([])
const contractPeriodOptions = ref([])
const installmentMonthOptions = ref([])
const discountTypeOptions = ref([])
const planCategoryOptions = ref([])
const planOptions = ref([])
const agencyOptions = ref([])

// 1. 단말기 목록 조회
const fetchDevices = async () => {
  try {
    const res = await post('/api/form/phone/list', {
      prodCtgId: '',
      makrCd: '',
      shandType: '',
      reqBuyTypeCd: model.value.productType || 'MM',
    })
    deviceOptions.value = extractData(res).map((item) => ({
      label: item.prodNm || item.rprsPrdtNm || '이름없음',
      value: item.prodId || item.rprsPrdtId || item.prdtId,
    }))
  } catch (e) {
    console.error('Failed to fetch devices:', e)
  }
}

// 2. 용량 목록 조회
const fetchCapacities = async (prodId) => {
  if (!prodId) {
    capacityOptions.value = []
    return
  }
  try {
    // 요청하신 임시값 "2525" 사용 (필요시 prodId로 대체 가능)
    const res = await post('/api/form/phone/capacity/list', { prodId: '2525' })
    capacityOptions.value = extractData(res).map((item) => ({
      label: item.modelCapacityNm || item.ctgNm || '용량정보 없음',
      value: item.modelCapacityCd || item.ctgCd || item.prdtId,
    }))
  } catch (e) {
    console.error('Failed to fetch capacities:', e)
  }
}

// 3. 색상 목록 조회
const fetchColors = async (prodId) => {
  if (!prodId) {
    colorOptions.value = []
    return
  }
  try {
    // 요청하신 임시값 "K7031527" 사용
    const res = await post('/api/form/phone/color/list', { rprsPrdtId: 'K7031527' })
    colorOptions.value = extractData(res).map((item) => ({
      label: item.modelColorNm || item.ctgNm || '색상정보 없음',
      value: item.modelColorCd || item.ctgCd || item.prdtId,
    }))
  } catch (e) {
    console.error('Failed to fetch colors:', e)
  }
}

// 4. 약정기간 조회
const fetchContractPeriods = async () => {
  try {
    const res = await post('/api/form/rate/engg/list', {
      salePlcyCd: 'D2022041306417',
    })
    contractPeriodOptions.value = extractData(res).map((item) => ({
      label: item.agrmTrmLabel || `${item.agrmTrm}개월`,
      value: item.agrmTrm,
    }))
  } catch (e) {
    console.error('Failed to fetch contract periods:', e)
  }
}

// 5. 할부기간 조회
const fetchInstallmentMonths = async () => {
  try {
    const res = await post('/api/form/phone/monthly/list', {
      modelSalePolicyCode: 'D2022041306417',
    })
    installmentMonthOptions.value = extractData(res).map((item) => ({
      label: item.modelMonthly ? `${item.modelMonthly}개월` : '일시불(없음)',
      value: item.modelMonthly,
    }))
  } catch (e) {
    console.error('Failed to fetch installment months:', e)
  }
}

// 6. 할인유형 조회 (판매정책)
const fetchDiscountTypes = async () => {
  try {
    const res = await post('/api/form/phone/saletype/list', {
      plcyTypeCd: 'N',
      plcySctnCd: '01',
      orgnId: '1100033726',
      prdtId: 'K7020692',
      salePlcyCd: 'N2022011018381',
      prdtSctnCd: '',
    })
    discountTypeOptions.value = extractData(res).map((item) => ({
      label: item.salePlcyNm || '할인유형',
      value: item.salePlcyCd,
    }))
  } catch (e) {
    console.error('Failed to fetch discount types:', e)
  }
}

// 7. 요금제 카테고리 조회
const fetchPlanCategories = async () => {
  try {
    const res = await post('/api/form/rate/category/list', {
      searchProductCategoryTypeCd: 'P',
    })
    planCategoryOptions.value = extractData(res).map((item) => ({
      label: item.ctgNm,
      value: item.ctgCd,
    }))
  } catch (e) {
    console.error('Failed to fetch plan categories:', e)
  }
}

// 8. 요금제 목록 조회 (카테고리 선택 시)
const fetchPlans = async (ctgCd) => {
  try {
    const res = await post('/api/form/rate/list', {
      orgnId: '1100033726',
      sprtTp: 'KD',
      plcySctnCd: '01',
      salePlcyCd: 'N2022011018381',
    })
    planOptions.value = extractData(res).map((item) => ({
      label: item.rateNm || item.ctgNm,
      value: item.rateCd || item.ctgCd || item.prodId,
    }))
  } catch (e) {
    console.error('Failed to fetch plans:', e)
  }
}

// 9. 대리점 목록 조회
const fetchAgencies = async () => {
  try {
    const res = await post('/api/form/agent/list', { shopOrgnId: 'V000001083' })
    const list = extractData(res)

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

// 대리점 선택 시 agentCd 업데이트
watch(
  () => model.value.agency,
  (newVal) => {
    if (newVal) {
      model.value.agentCd = newVal
      console.log('>>> Selected Agency (agentCd):', newVal)
    }
  },
)

// 단말기가 변경될 때 하위 의존성(용량, 색상) 다시 불러오기
watch(
  () => model.value.deviceModel,
  (newVal) => {
    if (!model.value.isSaved) {
      model.value.capacity = ''
      model.value.color = ''
    }
    if (newVal) {
      fetchCapacities(newVal)
      fetchColors(newVal)
    } else {
      capacityOptions.value = []
      colorOptions.value = []
    }
  },
)

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

onMounted(async () => {
  fetchAgencies() // 대리점 조회

  // 공통코드 조회: 개통 유형
  const list = await getCommonCodeList('OPEN_TYPE_CD')
  openTypeCdCodes.value = (list || []).map((item) => ({
    ...item,
    label: item.title,
    value: item.code,
  }))
  if (model.value && !model.value.openTypeCd) {
    model.value.openTypeCd = '99'
  }

  // 개통유형이 휴대폰(MM)일 때만 단말기 관련 API 호출
  if (model.value.productType === 'MM') {
    fetchDevices()
    fetchContractPeriods()
    fetchInstallmentMonths()
    fetchDiscountTypes()
  }

  fetchPlanCategories()
  fetchPlans(model.value.planName1) // 초기 로드
})

const validate = () => {
  if (model.value.productType === 'MM') {
    if (!model.value.openTypeCd) return false
    if (model.value.openTypeCd === '99') {
      if (
        !model.value.deviceModel ||
        !model.value.capacity ||
        !model.value.color ||
        !model.value.contractPeriod ||
        !model.value.installmentMonth ||
        !model.value.discountType
      )
        return false
    }
  }
  if (!model.value.planName1 || !model.value.planName2) return false
  if (!model.value.agency) return false
  return true
}

defineExpose({ validate })
</script>
