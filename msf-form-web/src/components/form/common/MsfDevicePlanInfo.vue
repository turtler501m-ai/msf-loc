<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup
        v-if="model.productType === 'MM'"
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
          @click="onClickDeviceSelect"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.deviceModel"
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
          @click="onClickCapacitySelect"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.deviceModel && model.capacity"
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
          @click="onClickColorSelect"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="(model.productType === 'MM' && model.deviceModel) || model.productType === 'UU'"
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
        v-if="model.productType === 'MM' && model.deviceModel"
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
        v-if="model.productType === 'MM' && model.deviceModel"
        label="할인유형"
        required
        tag="div"
      >
        <MsfChip
          v-model="model.discountType"
          :data="refinedDiscountTypeOptions"
          :disabled="model.isSaved"
          name="inp-discountType"
          @click="onClickDiscountTypeChip"
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
import { computed, defineModel, defineProps, onBeforeMount, onMounted, ref, watch } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { post } from '@/libs/api/msf.api'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'

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
const store = useMsfFormNewChgStore()

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

// 휴대폰 개통으로 고정
onBeforeMount(() => {
  if (model.value) {
    model.value.openTypeCd = '99'
  }
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
  const isNew = props.customerData?.joinType === 'NAC3'
  const isNoContract = String(model.value.contractPeriod) === '0'
  const baseOptions = installmentMonthOptions.value

  // '0(없음/일시불)' 옵션 확보
  const noneOption = baseOptions.find((opt) => String(opt.value) === '0') || {
    label: '일시불(없음)',
    value: '0',
  }
  const otherOptions = baseOptions.filter((opt) => String(opt.value) !== '0')

  if (isNoContract) {
    // 무약정 시: 할부기간 리스트 전체 노출
    if (isNew) {
      // 신규가입은 '0' 포함
      return baseOptions.some((opt) => String(opt.value) === '0')
        ? baseOptions
        : [noneOption, ...baseOptions]
    }
    // 번호이동/기기변경은 할부 리스트만 (일시불 제외할지 여부는 비즈니스에 따라 다르나 요청에 따라 리스트 유지)
    return otherOptions
  } else {
    // 약정 선택 시: 약정기간과 동일한 할부기간만 노출
    const matchedOption = otherOptions.find(
      (opt) => String(opt.value) === String(model.value.contractPeriod),
    )
    const result = matchedOption ? [matchedOption] : []

    if (isNew) {
      return [noneOption, ...result]
    }
    return result
  }
})

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

const syncInitialOptions = () => {
  const m = model.value

  if (m.deviceModel && deviceOptions.value.length === 0) {
    deviceOptions.value = [
      {
        label: m.deviceModelNm || '기본 단말기',
        value: m.deviceModel,
        rprsPrdtId: m.deviceModel,
      },
    ]
  }

  if (m.capacity && capacityOptions.value.length === 0) {
    // 용량 명칭이 있으면 사용, 없으면 코드 뒤에 G 붙여서 임시 표시
    capacityOptions.value = [{ label: m.capacityNm || `${m.capacity}G`, value: m.capacity }]
  }

  if (m.color && colorOptions.value.length === 0) {
    // 색상 명칭(WE 등)이 있으면 사용
    colorOptions.value = [{ label: m.colorNm || '기본 색상', value: m.color }]
  }

  if (m.discountType && discountTypeOptions.value.length === 0) {
    discountTypeOptions.value = [
      { label: m.discountTypeNm || '기본 할인유형', value: m.discountType },
    ]
  }

  if (m.prodId && planOptions.value.length === 0) {
    planOptions.value = [{ label: m.prodNm || '기본 요금제', value: m.prodId }]
  }
}

// 모델 데이터 변경 감시하여 초기 옵션 동기화 (async 초기화 대응)
watch(
  () => [model.value.deviceModel, model.value.prodId, model.value.capacity, model.value.color],
  () => {
    syncInitialOptions()
  },
  { immediate: true }
)

// --- 시퀀스 로직 실행 (순차 처리) ---
/**
 * 단말기 및 요금제 정보를 순차적으로 로드하는 핵심 시퀀스
 * 1. 단말기 목록 조회
 * 2. 선택된 단말기의 판매정책 조회 (salePlcyCd 획득)
 * 3. 획득한 판매정책으로 용량, 할인유형, 할부기간, 약정기간 조회
 * 4. 선택된 용량으로 색상 목록 조회
 * 5. 요금제 카테고리 및 요금제 목록 조회 (salePlcyCd 및 sprtTp 반영)
 */
const executeLoadingSequence = async () => {
  // 1. 단말기 목록 조회 (MM인 경우)
  if (model.value.productType === 'MM') {
    await fetchDevices()
  }

  const deviceModel = model.value.deviceModel
  if (model.value.productType === 'MM' && deviceModel) {
    const selected = deviceOptions.value.find((opt) => opt.value === deviceModel)
    const rprsPrdtId = selected?.rprsPrdtId || deviceModel

    // 2. 판매정책 조회 (판매정책코드를 먼저 받아와야 다음 단계 가능)
    await fetchSalePolicy(rprsPrdtId)

    // 3. 용량, 할인유형, 할부기간, 약정기간 조회 (판매정책코드 의존)
    // 이 항목들은 서로 독립적이므로 병렬 실행 가능하나, 요금제 조회 전에는 모두 완료되어야 함
    await Promise.all([
      fetchCapacities(deviceModel),
      fetchDiscountTypes(),
      fetchInstallmentMonths(deviceModel),
      fetchContractPeriods(),
    ])

    // 4. 색상 조회 (용량 선택값에 의존)
    if (model.value.capacity) {
      await fetchColors(deviceModel, model.value.capacity)
    }
  } else {
    // USIM 단독 등의 경우에도 약정기간/할인유형 정보는 필요함
    await Promise.all([fetchContractPeriods(), fetchDiscountTypes()])
  }

  // 5. 요금제 정보 조회
  await fetchPlanCategories()
  if (model.value.prodCtgId) {
    await fetchPlans(model.value.prodCtgId)
  }
}

// --- 클릭 시 재조회 핸들러 ---
const onClickDeviceSelect = async () => {
  if (model.value.isSaved) return
  await fetchDevices()
}

const onClickCapacitySelect = async () => {
  if (model.value.isSaved || !model.value.deviceModel) return
  await fetchCapacities(model.value.deviceModel)
}

const onClickColorSelect = async () => {
  if (model.value.isSaved || !model.value.deviceModel || !model.value.capacity) return
  await fetchColors(model.value.deviceModel, model.value.capacity)
}

const onClickDiscountTypeChip = async () => {
  if (model.value.isSaved) return
  await fetchDiscountTypes()
}

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
      label: item.prdtNm || item.prodNm || item.rprsPrdtNm || '이름없음',
      value: item.prodId || item.rprsPrdtId || item.prdtId || item.modelId,
      rprsPrdtId: item.modelId || item.rprsPrdtId || item.prodId || item.prdtId, // 색상 및 할인유형 조회를 위해 modelId(대표ID) 우선 유지
      modelId: item.modelId,
      reqModelNm: item.reqModelNm,
      salePlcyCd: item.salePlcyCd || item.salePlcyCode,
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
      salePlcyCd: model.value.modelSalePolicyCd || '',
    })
    const list = extractData(res).map((item) => ({
      label: item.agrmTrmLabel || `${item.agrmTrm}개월`,
      value: String(item.agrmTrm),
    }))

    // '0개월' 항목 추가
    if (!list.some((opt) => opt.value === '0')) {
      list.unshift({ label: '0개월', value: '0' })
    }

    contractPeriodOptions.value = list

    // 자동 선택: 24개월 우선, 없으면 첫 번째 항목
    if (contractPeriodOptions.value.length > 0 && !model.value.contractPeriod) {
      const has24 = contractPeriodOptions.value.find((opt) => String(opt.value) === '24')
      model.value.contractPeriod = has24 ? has24.value : contractPeriodOptions.value[0].value
    }
  } catch (e) {
    console.error('Failed to fetch contract periods:', e)
  }
}

// 5. 할부기간 조회
const fetchInstallmentMonths = async (prodId) => {
  if (!prodId && model.value.productType === 'MM') {
    installmentMonthOptions.value = []
    return
  }
  try {
    const res = await post('/api/form/phone/monthly/list', {
      salePlcyCd: model.value.modelSalePolicyCd || '',
    })
    const list = extractData(res).map((item) => ({
      label: `${item.modelMonthly || '0'}개월`,
      value: String(item.modelMonthly || '0'),
    }))

    installmentMonthOptions.value = list

    // 자동 선택: 24개월 우선, 없으면 첫 번째 항목 (단, 이미 값이 있으면 유지)
    if (
      installmentMonthOptions.value.length > 0 &&
      (model.value.installmentMonth === undefined ||
        model.value.installmentMonth === null ||
        model.value.installmentMonth === '')
    ) {
      const has24 = installmentMonthOptions.value.find((opt) => String(opt.value) === '24')
      model.value.installmentMonth = has24 ? has24.value : installmentMonthOptions.value[0].value
    }
  } catch (e) {
    console.error('Failed to fetch installment months:', e)
  }
}

// 6. 판매정책 조회
const fetchSalePolicy = async (rprsPrdtId) => {
  try {
    const res = await post('/api/form/phone/saleplcy/list', {
      plcyTypeCd: 'N', // 고정값: 위탁온라인(N)
      reqBuyTypeCd: 'MM', // 고정값: 단말(01)
      prdtSctnCd: '',
      sprtTp: '',
      orgnId: '1100014062',
      prdtId: rprsPrdtId,
    })
    const data = extractData(res)
    if (data && data.length > 0) {
      model.value.modelSalePolicyCd = data[0].salePlcyCd || data[0].salePlcyCode || ''
    }
  } catch (e) {
    console.error('Failed to fetch sale policy:', e)
  }
}

// 7. 할인유형 조회
const fetchDiscountTypes = async () => {
  const selectedDevice = deviceOptions.value.find((opt) => opt.value === model.value.deviceModel)
  const rprsPrdtId = selectedDevice?.rprsPrdtId || model.value.deviceModel

  try {
    const res = await post('/api/form/phone/saletype/list', {
      plcySctnCd: model.value.productType || 'MM', // 휴대폰: MM, USIM: UU
      prdtId: rprsPrdtId,
      salePlcyCd: model.value.modelSalePolicyCd || '',
    })
    const availableList = extractData(res).map((item) => ({
      label: item.sprtNm || '할인유형',
      value: item.sprtTp,
    }))
    discountTypeOptions.value = availableList

    // 자동 선택
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

// 8. 요금제 카테고리 조회
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
      sprtTp: model.value.discountType || '',
      prodCtgId: ctgCd, // 카테고리 ID 추가
      reqBuyTypeCd: model.value.productType || 'MM', // 상품유형(MM/UU) 전달
      salePlcyCd: model.value.modelSalePolicyCd || '', // 단말기 판매정책 코드
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

// 요금제 선택 시 prodNm 및 prdtSctnCd 업데이트
watch(
  () => model.value.prodId,
  (newVal) => {
    if (newVal) {
      const selected = planOptions.value.find(opt => opt.value === newVal)
      if (selected) {
        model.value.prodNm = selected.label
        // 약관 필터링을 위해 prdtSctnCd 저장
        model.value.prdtSctnCd = selected.raw?.prdtSctnCd || ''
      }
    } else {
      model.value.prodNm = ''
      model.value.prdtSctnCd = ''
    }
  }
)

// 약정기간 변경 시 할부기간 자동 동기화 및 할인유형 제어
watch(
  () => model.value.contractPeriod,
  (newVal) => {
    if (model.value.isSaved) return

    const isNoContract = String(newVal) === '0'

    if (isNoContract) {
      // 무약정 시 할인유형 초기화 (항목이 숨겨지므로)
      model.value.discountType = ''
    } else {
      // 약정 선택 시: 할부기간을 약정기간과 동일하게 세팅 (단, 신규가입에서 이미 '0'인 경우는 유지 가능하나 일관성을 위해 매칭 우선)
      const hasMatch = installmentMonthOptions.value.some(
        (opt) => String(opt.value) === String(newVal),
      )
      if (hasMatch) {
        model.value.installmentMonth = newVal
      }
    }
  },
)

// 단말기가 변경될 때 전체 하위 데이터 시퀀스 실행
watch(
  () => model.value.deviceModel,
  async (newVal, oldVal) => {
    // 사용자가 직접 변경한 경우에만 하위 값 초기화
    if (oldVal && newVal !== oldVal && !model.value.isSaved) {
      model.value.capacity = ''
      model.value.color = ''
      model.value.discountType = ''
      model.value.installmentMonth = ''
      model.value.prodId = ''
    }

    // 순차적 로드 시퀀스 실행
    await executeLoadingSequence()
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

// 요금제 카테고리 또는 할인유형이 변경될 때 하위 요금제 목록 다시 불러오기
watch(
  () => [model.value.prodCtgId, model.value.discountType],
  async ([newCtgId]) => {
    if (newCtgId) {
      await fetchPlans(newCtgId)
    }
  },
)

onMounted(async () => {
  fetchAgencies() // 대리점 조회

  // 공통코드 조회: 할인유형 전체 목록
  const discountCodes = await getCommonCodeList('F002')
  allDiscountTypeCodes.value = (discountCodes || []).map((item) => ({
    label: item.title,
    value: item.code,
  }))

  if (model.value) {
    model.value.openTypeCd = '99'
  }

  // 초기값 동기화 및 순차 로드 실행
  syncInitialOptions()

  // 엄격한 순차 로드 시퀀스 실행
  await executeLoadingSequence()
})

const validate = () => {
  const m = model.value

  if (m.productType === 'MM') {
    if (!m.deviceModel || !m.capacity || !m.color || !m.contractPeriod) return false
    if (m.installmentMonth === undefined || m.installmentMonth === null || m.installmentMonth === '') return false
    if (!m.discountType) return false
  }


  if (!m.agency) {
    console.warn('Validation failed: agency is missing')
    return false
  }

  return true
}

defineExpose({ validate })
</script>
