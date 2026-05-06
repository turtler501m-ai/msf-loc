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
          :data="refinedInstallmentMonthOptions"
          :disabled="model.isSaved"
          name="inp-installmentMonth"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.openTypeCd === '99' && model.contractPeriod !== '0'"
        label="할인유형"
        required
        tag="div"
      >
        <MsfChip
          v-model="model.discountType"
          :data="refinedDiscountTypeOptions"
          :disabled="model.isSaved"
          name="inp-discountType"
        />
      </MsfFormGroup>
      <MsfFormGroup label="요금제" tag="div" required>
        <MsfSelect
          v-model="model.prodCtgId"
          :disabled="model.isSaved"
          :options="planCategoryOptions"
          class="ut-w100p"
          placeholder="요금제 카테고리"
          title="요금제 카테고리"
        />
        <MsfSelect
          v-model="model.prodId"
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
import { computed, defineModel, defineProps, onMounted, ref, watch } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { post } from '@/libs/api/msf.api'

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
const model = defineModel({ type: Object, required: true })

// 옵션 상태 관리
const openTypeCdCodes = ref([])
const deviceOptions = ref([])
const capacityOptions = ref([])
const colorOptions = ref([])
const contractPeriodOptions = ref([])
const installmentMonthOptions = ref([])
const discountTypeOptions = ref([])
const allDiscountTypeCodes = ref([]) // 공통코드 F002 전체 목록

const planCategoryOptions = ref([])

// 가입유형 판별
const isNewJoin = computed(() => {
  const joinType = props.customerData?.joinType || ''
  return joinType === 'NEW' || joinType === '01' // 신규가입 코드 (프로젝트 스펙에 따라 01 또는 NEW)
})

// 가입자 생년월일 변경 시 요금제 목록 재조회 (시니어 요금제 노출 여부 갱신)
watch(
  () => props.customerData?.cstmrNativeRrn1 || props.customerData?.userBirthDate,
  () => {
    if (model.value.prodCtgId) {
      fetchPlans(model.value.prodCtgId)
    }
  }
)

// 약정기간에 따른 할부기간 필터링
const refinedInstallmentMonthOptions = computed(() => {
  const isNoContract = model.value.contractPeriod === '0'

  if (isNoContract) {
    // 무약정인 경우
    if (isNewJoin.value) {
      // 신규가입: 0개월(일시불) + 전체 리스트
      return installmentMonthOptions.value
    } else {
      // 번호이동/기기변경: 전체 리스트
      return installmentMonthOptions.value
    }
  } else {
    // 약정인 경우 (12, 24개월 등)
    const period = model.value.contractPeriod
    if (isNewJoin.value) {
      // 신규가입: 0개월(일시불) + 약정기간과 동일한 기간
      return installmentMonthOptions.value.filter(
        (opt) => String(opt.value) === '0' || String(opt.value) === String(period),
      )
    } else {
      // 번호이동/기기변경: 약정기간과 동일한 기간만
      return installmentMonthOptions.value.filter((opt) => String(opt.value) === String(period))
    }
  }
})

// 최종적으로 화면에 뿌려질 칩 데이터 (가공됨)
const refinedDiscountTypeOptions = computed(() => {
  if (allDiscountTypeCodes.value.length === 0) return discountTypeOptions.value

  const availableIds = discountTypeOptions.value.map((opt) => opt.value)

  const refined = allDiscountTypeCodes.value.map((opt) => ({
    ...opt,
    disabled: !availableIds.includes(opt.value),
  }))

  // 정렬: 가능한 것 먼저, disabled는 뒤로
  return refined.sort((a, b) => {
    if (a.disabled === b.disabled) return 0
    return a.disabled ? 1 : -1
  })
})
const planOptions = ref([])
const agencyOptions = ref([])

// 1. 단말기 목록 조회
const fetchDevices = async () => {
  try {
    const res = await post('/api/form/phone/list', {
      prodCtgId: '',
      makrCd: '',
      shandType: '',
      orgnId: '1100014062', // 매장재고조회를 위한 대리점 코드 임시값
      reqBuyTypeCd: model.value.productType || 'MM',
    })
    const list = extractData(res)
    deviceOptions.value = list.map((item) => ({
      label: item.prodNm || item.rprsPrdtNm || '이름없음',
      value: item.prodId || item.rprsPrdtId || item.prdtId,
      rprsPrdtId: item.rprsPrdtId, // 할인유형 조회를 위해 원본 ID 유지
    }))
    return list
  } catch (e) {
    console.error('Failed to fetch devices:', e)
    return []
  }
}

// 2. 용량 목록 조회
const fetchCapacities = async (prodId) => {
  if (!prodId) {
    capacityOptions.value = []
    return []
  }
  try {
    const res = await post('/api/form/phone/capacity/list', { prodId })
    const list = extractData(res).map((item) => ({
      label: item.modelCapacityNm || item.ctgNm || '용량정보 없음',
      value: item.modelCapacityCd || item.ctgCd || item.prdtId,
    }))
    capacityOptions.value = list
    return list
  } catch (e) {
    console.error('Failed to fetch capacities:', e)
    return []
  }
}

// 3. 색상 목록 조회
const fetchColors = async (prodId, capacityCd) => {
  if (!prodId) {
    colorOptions.value = []
    return []
  }
  try {
    const selectedDevice = deviceOptions.value.find((opt) => opt.value === prodId)
    const rprsPrdtId = selectedDevice?.rprsPrdtId || prodId

    const res = await post('/api/form/phone/color/list', {
      rprsPrdtId,
      modelCapacityCd: capacityCd,
    })
    const list = extractData(res).map((item) => ({
      label: item.modelColorNm || item.ctgNm || '색상정보 없음',
      value: item.modelColorCd || item.ctgCd || item.prdtId,
    }))
    colorOptions.value = list
    return list
  } catch (e) {
    console.error('Failed to fetch colors:', e)
    return []
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
    // 자동 선택
    if (contractPeriodOptions.value.length > 0 && !model.value.contractPeriod) {
      model.value.contractPeriod = contractPeriodOptions.value[0].value
    }
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
    // 자동 선택
    if (installmentMonthOptions.value.length > 0 && (model.value.installmentMonth === undefined || model.value.installmentMonth === null || model.value.installmentMonth === '')) {
      model.value.installmentMonth = installmentMonthOptions.value[0].value
    }
  } catch (e) {
    console.error('Failed to fetch installment months:', e)
  }
}

// 6. 할인유형 조회 (판매정책)
const fetchDiscountTypes = async () => {
  const selectedDevice = deviceOptions.value.find((opt) => opt.value === model.value.deviceModel)
  const rprsPrdtId = selectedDevice?.rprsPrdtId || 'K7025076' // 선택된 단말의 rprsPrdtId 사용

  try {
    const res = await post('/api/form/phone/saletype/list', {
      plcyTypeCd: 'N',
      plcySctnCd: '01',
      orgnId: '1100014062',
      prdtId: rprsPrdtId,
      salePlcyCd: 'N2022011018381',
      prdtSctnCd: '',
      reqBuyTypeCd: model.value.productType || 'MM',
    })
    const availableList = extractData(res).map((item) => ({
      label: item.sprtNm || '할인유형',
      value: item.sprtTp,
    }))
    discountTypeOptions.value = availableList

    // 자동 선택: 사용 가능한 목록 중 첫 번째 항목을 기본값으로 설정 (현재 선택값이 유효하지 않은 경우에만)
    if (availableList.length > 0) {
      const isCurrentValid = availableList.some((opt) => opt.value === model.value.discountType)
      if (!isCurrentValid) {
        model.value.discountType = availableList[0].value
      }
    }
  } catch (e) {
    console.error('Failed to fetch discount types:', e)
  }
}

// 7. 요금제 카테고리 조회
const fetchPlanCategories = async () => {
  try {
    const res = await post('/api/form/rate/category/list', {
      prodCtgTypeCd: 'P',
    })
    planCategoryOptions.value = extractData(res).map((item) => ({
      label: item.prodCtgNm || item.ctgNm,
      value: item.prodCtgId || item.ctgCd,
    }))
  } catch (error) {
    console.error('Failed to fetch plan categories:', error)
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
      prodCtgId: ctgCd, // 카테고리 ID 추가
      reqBuyTypeCd: model.value.productType || 'MM', // 상품유형(MM/UU) 전달
    })

    // 가입자 나이 계산 (만 65세 이상 여부)
    const getAge = (birthStr) => {
      if (!birthStr || birthStr.length < 6) return 0
      const yearPrefix = birthStr.length === 8 ? '' : (Number(birthStr.substring(0, 2)) > 50 ? '19' : '20')
      const fullBirth = yearPrefix + birthStr
      const birthDate = new Date(
        fullBirth.substring(0, 4),
        Number(fullBirth.substring(4, 6)) - 1,
        fullBirth.substring(6, 8)
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
  } catch (error) {
    console.error('Failed to fetch plans:', error)
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
    }
  },
)

// 요금제 선택 시 prodNm 업데이트
watch(
  () => model.value.prodId,
  (newVal) => {
    if (newVal) {
      const selected = planOptions.value.find(opt => opt.value === newVal)
      if (selected) {
        model.value.prodNm = selected.label
      }
    } else {
      model.value.prodNm = ''
    }
  }
)

// 약정기간 변경 시 할부기간 자동 동기화
watch(
  () => model.value.contractPeriod,
  (newVal) => {
    if (newVal && newVal !== '0' && !model.value.isSaved) {
      // 약정 선택 시 할부기간을 약정기간과 동일하게 세팅 (규칙: 약정기간과 할부기간 일치)
      const hasMatch = installmentMonthOptions.value.some((opt) => String(opt.value) === String(newVal))
      if (hasMatch) {
        model.value.installmentMonth = newVal
      }
    }
  },
)

// 단말기가 변경될 때 하위 의존성(용량, 할인유형) 다시 불러오기
watch(
  () => model.value.deviceModel,
  async (newVal, oldVal) => {
    if (oldVal && newVal !== oldVal && !model.value.isSaved) {
      model.value.capacity = ''
      model.value.color = ''
      model.value.discountType = ''
    }

    if (newVal) {
      await fetchCapacities(newVal)
      fetchDiscountTypes()
    } else {
      capacityOptions.value = []
      colorOptions.value = []
      discountTypeOptions.value = []
    }
  },
)

// 용량이 변경될 때 색상 목록 다시 불러오기
watch(
  () => model.value.capacity,
  async (newVal, oldVal) => {
    if (oldVal && newVal !== oldVal && !model.value.isSaved) {
      model.value.color = ''
    }
    if (newVal && model.value.deviceModel) {
      await fetchColors(model.value.deviceModel, newVal)
    } else {
      colorOptions.value = []
    }
  },
)

// 요금제 카테고리가 변경될 때 하위 요금제 목록 다시 불러오기
watch(
  () => model.value.prodCtgId,
  (newVal) => {
    if (!model.value.isSaved) {
      model.value.prodId = ''
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
  // 공통코드 조회: 할인유형 전체 목록
  const discountCodes = await getCommonCodeList('F002')
  allDiscountTypeCodes.value = (discountCodes || []).map((item) => ({
    label: item.title,
    value: item.code,
  }))

  if (model.value && !model.value.openTypeCd) {
    model.value.openTypeCd = '99'
  }

  // 개통유형이 휴대폰(MM)일 때만 단말기 관련 API 호출
  if (model.value.productType === 'MM') {
    const savedModel = model.value.deviceModel
    const savedCapacity = model.value.capacity
    const savedColor = model.value.color

    await fetchDevices()
    fetchContractPeriods()
    fetchInstallmentMonths()

    if (savedModel) {
      model.value.deviceModel = savedModel
      await fetchCapacities(savedModel)
      if (savedCapacity) {
        model.value.capacity = savedCapacity
        await fetchColors(savedModel, savedCapacity)
        if (savedColor) {
          model.value.color = savedColor
        }
      }
    }
    fetchDiscountTypes()
  }

  fetchPlanCategories()
  fetchPlans(model.value.prodCtgId) // 초기 로드
})

const validate = () => {
  const m = model.value

  if (m.productType === 'MM') {
    if (!m.openTypeCd) return false
    if (m.openTypeCd === '99') {
      if (!m.deviceModel || !m.capacity || !m.color || !m.contractPeriod) return false
      if (m.installmentMonth === undefined || m.installmentMonth === null || m.installmentMonth === '') return false
      if (!m.discountType) return false
    }
  }

  if (!m.prodCtgId) {
    console.warn('Validation failed: prodCtgId is missing')
    return false
  }
  if (!m.prodId) {
    console.warn('Validation failed: prodId is missing')
    return false
  }
  if (!m.agency) {
    console.warn('Validation failed: agency is missing')
    return false
  }

  return true
}

defineExpose({ validate })
</script>
