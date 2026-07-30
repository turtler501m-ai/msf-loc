<template>
  <div>
    <MsfTitleArea :title="title" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup v-if="model.productType === 'MM'" label="휴대폰" required tag="div">
        <MsfSelect
          ref="deviceModelRef"
          v-model="model.deviceModel"
          :options="deviceOptions"
          class="ut-w-300"
          placeholder="휴대폰 선택"
          title="휴대폰"
          :disabled="props.disabled"
          @click="onClickDeviceSelect"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.deviceModel"
        label="약정기간"
        required
        tag="div"
      >
        <MsfChip
          ref="contractPeriodRef"
          v-model="model.contractPeriod"
          :data="contractPeriodOptions"
          name="inp-contractPeriod"
          :readonly="props.disabled"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="model.productType === 'MM' && model.deviceModel"
        label="단말기 할부기간"
        required
        tag="div"
      >
        <MsfChip
          ref="installmentMonthRef"
          v-model="model.installmentMonth"
          :data="refinedInstallmentMonthOptions"
          name="inp-installmentMonth"
          :readonly="props.disabled"
        />
      </MsfFormGroup>
      <MsfFormGroup
        v-if="
          model.productType === 'MM' && model.deviceModel && String(model.contractPeriod) !== '0'
        "
        label="할인유형"
        required
        tag="div"
      >
        <MsfChip
          ref="discountTypeRef"
          v-model="model.discountType"
          :data="refinedDiscountTypeOptions"
          name="inp-discountType"
          :readonly="props.disabled"
          @click="onClickDiscountTypeChip"
        />
      </MsfFormGroup>
      <MsfFormGroup label="요금제" tag="div" required>
        <MsfSelect
          ref="prodCtgIdRef"
          v-model="model.prodCtgId"
          :options="planCategoryOptions"
          class="ut-w100p"
          placeholder="요금제 카테고리"
          title="요금제 카테고리"
          :disabled="props.disabled"
        />
        <MsfSelect
          ref="prodIdRef"
          v-model="model.prodId"
          :options="planOptions"
          :key="`plan-select-${model.prodCtgId}`"
          class="ut-w100p"
          placeholder="요금제 선택"
          title="요금제 상세"
          selectPopYn
        />
      </MsfFormGroup>
    </MsfStack>
  </div>
</template>

<script setup>
import { computed, defineModel, defineProps, onBeforeMount, onMounted, ref, watch } from 'vue'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { post } from '@/libs/api/msf.api'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { extractYYYYMMDDRrn, formatCurrency } from '@/libs/utils/string.utils'

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
  productData: { type: Object, default: () => ({}) },
  joinType: { type: String, default: '' },
  disabled: Boolean,
})
const model = defineModel({ type: Object, required: true })
const newChgStore = useMsfFormNewChgStore()

const getAgentCd = () => {
  const cd = model.value?.agentCd || ''
  if (cd) {
    return cd
  }
  const fallback = model.value?.agent || ''
  if (fallback) {
    return fallback
  }
  return ''
}

const withAgentCd = (payload = {}) => ({
  ...payload,
  agentCd: getAgentCd(),
})

const deviceModelRef = ref(null)
const contractPeriodRef = ref(null)
const installmentMonthRef = ref(null)
const discountTypeRef = ref(null)
const prodCtgIdRef = ref(null)
const prodIdRef = ref(null)
// 시퀀스 로더 중복 방지 플래그
const isSequenceLoading = ref(false)

// 옵션 상태 관리
const deviceOptions = ref([])
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
  () => model.value?.cstmrNativeRrn1 || model.value?.userBirthDate,
  () => {
    if (model.value.prodCtgId) {
      fetchPlans(model.value.prodCtgId)
    }
  },
)

const refinedInstallmentMonthOptions = computed(() => {
  const baseOptions = installmentMonthOptions.value

  const noneOption = { label: '일시불(0개월)', value: '0' }
  const otherOptions = baseOptions.filter((opt) => String(opt.value) !== '0')

  // 신규가입(NAC3) 여부에 따라 일시불(0개월) 옵션 포함 여부 결정
  const joinType = props.joinType || model.value?.joinType || ''
  const hasNoneOption = joinType === 'NAC3'

  // 약정기간에 한정 짓지 않고 전체 할부기간 옵션을 온전히 리턴
  return hasNoneOption ? [noneOption, ...otherOptions] : otherOptions
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

const syncInitialOptions = () => {
  // 사용자의 요청에 따라 '기본 요금제', '기본 단말기' 등의 임시 옵션 생성을 중단합니다.
  // 실제 API에서 조회된 목록만 노출되도록 보장합니다.
}

// 모델 데이터 변경 감시하여 초기 옵션 동기화 (async 초기화 대응)
watch(
  () => [model.value.deviceModel, model.value.prodId],
  () => {
    syncInitialOptions()
  },
  { immediate: true },
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
  isSequenceLoading.value = true
  try {
    // 1. 단말기 목록 조회 (MM인 경우, 목록이 비어있을 때만 최초 조회)
    if (model.value.productType === 'MM' && deviceOptions.value.length === 0) {
      await fetchDevices()
    }

    const deviceModel = model.value.deviceModel
    if (model.value.productType === 'MM' && deviceModel) {
      const selected = deviceOptions.value.find((opt) => opt.value === deviceModel)
      const rprsPrdtId = selected?.rprsPrdtId || deviceModel

      // 대표단말기ID(K...)와 내부단말기ID(숫자)를 명확히 구분하여 보관
      if (selected) {
        model.value.modelId = selected.rprsPrdtId // e.g. K7025076
        model.value.handsetProdId = selected.prodId // e.g. 3308
        model.value.deviceModelNm = selected.label
        model.value.reqModelNm = selected.reqModelNm || '' // e.g. SM-A217NK (추가)
      }

      // 단말기 목록에 이미 판매정책코드가 있다면 우선 적용
      if (selected?.salePlcyCd && !model.value.modelSalePolicyCd) {
        model.value.modelSalePolicyCd = selected.salePlcyCd
      }

      // 2. 판매정책 조회 (판매정책코드를 먼저 받아와야 다음 단계 가능)
      await fetchSalePolicy(rprsPrdtId)

      // 3. 단말기 관련 옵션들을 병렬로 조회
      await Promise.all([
        fetchDiscountTypes(),
        fetchInstallmentMonths(deviceModel),
        fetchContractPeriods(),
      ])
    } else {
      // USIM 단독 등의 경우에도 약정기간/할인유형 정보는 필요함
      await Promise.all([fetchContractPeriods(), fetchDiscountTypes()])
    }

    // 5. 요금제 정보 조회
    await fetchPlanCategories()
    await fetchPlans(model.value.prodCtgId)
  } finally {
    isSequenceLoading.value = false
  }
}

// --- 클릭 시 재조회 핸들러 ---
const onClickDeviceSelect = async () => {
  if (model.value.isSaved) return
  await fetchDevices()
}

const onClickDiscountTypeChip = async () => {
  if (model.value.isSaved) return
  await fetchDiscountTypes()
}

// 1. 단말기 목록 조회
const fetchDevices = async () => {
  try {
    const res = await post(
      '/api/form/phone/list',
      withAgentCd({
        prodCtgId: '',
        makrCd: '',
        shandType: '',
        reqBuyTypeCd: model.value.productType || 'MM',
        operTypeCd: model.value.operTypeCd || 'MNP3',
      }),
    )
    const list = extractData(res)
    deviceOptions.value = list.map((item) => ({
      label: item.prdtNm || item.prodNm || item.rprsPrdtNm || '이름없음',
      value: item.prodId || item.prdtId || item.modelId || item.rprsPrdtId, // 내부 숫자 ID 우선 (UI Select용)
      rprsPrdtId: item.modelId || item.rprsPrdtId || item.prodId || item.prdtId, // K... 대표 ID
      prodId: item.prodId || item.prdtId || '', // 내부 숫자 ID (prodId가 없으면 modelId 등으로 세팅하지 않고 빈값)
      reqModelNm: item.reqModelNm,
      salePlcyCd: item.salePlcyCd || item.salePlcyCode,
    }))

    // Draft/Default 값이 목록에 있는지 확인 후 디폴트 세팅 또는 적용불가 시 기본값(첫 번째 항목) 세팅
    if (deviceOptions.value.length > 0) {
      const isExist = deviceOptions.value.some(
        (opt) => String(opt.value) === String(model.value.deviceModel),
      )
      if (
        model.value.deviceModel === undefined ||
        model.value.deviceModel === null ||
        model.value.deviceModel === '' ||
        !isExist
      ) {
        // 1. formDefaultMap의 기본값 또는 스펙상 기본값이 목록에 존재하는지 확인
        const defaultVal =
          newChgStore.formDefaultMap?.['MODEL_ID'] ||
          newChgStore.formDefaultMap?.['DEVICE_MODEL'] ||
          newChgStore.formDefaultMap?.['PROD_ID']
        const hasDefault = deviceOptions.value.find(
          (opt) => String(opt.value) === String(defaultVal),
        )
        if (hasDefault) {
          model.value.deviceModel = hasDefault.value
          model.value.deviceModelNm = hasDefault.label
          model.value.handsetProdId = hasDefault.prodId
          model.value.modelId = hasDefault.rprsPrdtId
          model.value.reqModelNm = hasDefault.reqModelNm
          model.value.modelSalePolicyCd = hasDefault.salePlcyCd
        } else {
          // 2. 기본값마저 없다면 첫 번째 값으로 세팅
          model.value.deviceModel = deviceOptions.value[0].value
          model.value.deviceModelNm = deviceOptions.value[0].label
          model.value.handsetProdId = deviceOptions.value[0].prodId
          model.value.modelId = deviceOptions.value[0].rprsPrdtId
          model.value.reqModelNm = deviceOptions.value[0].reqModelNm
          model.value.modelSalePolicyCd = deviceOptions.value[0].salePlcyCd
        }
      }
    }

    return list
  } catch (e) {
    console.error('Failed to fetch devices:', e)
    return []
  }
}

// 4. 약정기간 조회
const fetchContractPeriods = async () => {
  try {
    const isUsimOnly = model.value.productType === 'UU'
    const payload = {
      salePlcyCd: isUsimOnly ? '' : model.value.modelSalePolicyCd || '',
    }

    if (model.value.productType === 'MM' && model.value.deviceModel) {
      const selectedDevice = deviceOptions.value.find(
        (opt) => opt.value === model.value.deviceModel,
      )
      payload.prdtId = selectedDevice?.rprsPrdtId || model.value.deviceModel
    }

    const res = await post('/api/form/rate/engg/list', withAgentCd(payload), { skipAlert: true })
    const list = extractData(res).map((item) => ({
      label: item.agrmTrmLabel || `${item.agrmTrm}개월`,
      value: String(item.agrmTrm),
    }))

    contractPeriodOptions.value = list

    if (contractPeriodOptions.value.length > 0) {
      const isExist = contractPeriodOptions.value.some(
        (opt) => String(opt.value) === String(model.value.contractPeriod),
      )
      if (!model.value.contractPeriod || !isExist) {
        // 1. formDefaultMap의 기본값 또는 하드코딩된 '24'가 목록에 존재하는지 확인
        const defaultVal = newChgStore.formDefaultMap?.['AGRM_TRM'] || '24'
        const hasDefault = contractPeriodOptions.value.find(
          (opt) => String(opt.value) === String(defaultVal),
        )
        if (hasDefault) {
          model.value.contractPeriod = hasDefault.value
        } else {
          // 2. 기본값마저 없다면 옵션 목록의 첫 번째 값으로 세팅
          model.value.contractPeriod = contractPeriodOptions.value[0].value
        }
      }
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
    const res = await post(
      '/api/form/phone/monthly/list',
      withAgentCd({
        salePlcyCd: model.value.modelSalePolicyCd || '',
      }),
    )
    const list = extractData(res).map((item) => ({
      label: `${item.modelMonthly || '0'}개월`,
      value: String(item.modelMonthly || '0'),
    }))

    installmentMonthOptions.value = list

    if (installmentMonthOptions.value.length > 0) {
      const isExist = installmentMonthOptions.value.some(
        (opt) => String(opt.value) === String(model.value.installmentMonth),
      )
      if (!model.value.installmentMonth || !isExist) {
        // 1. 기본값 '24'가 옵션 목록에 존재한다면 세팅
        const hasDefault = installmentMonthOptions.value.find((opt) => String(opt.value) === '24')
        if (hasDefault) {
          model.value.installmentMonth = hasDefault.value
        } else {
          // 2. 기본값마저 없다면 옵션 목록의 첫 번째 값으로 세팅
          model.value.installmentMonth = installmentMonthOptions.value[0].value
        }
      }
    }
  } catch (e) {
    console.error('Failed to fetch installment months:', e)
  }
}

// 6. 판매정책 조회
const fetchSalePolicy = async (rprsPrdtId) => {
  try {
    const res = await post(
      '/api/form/phone/saleplcy/list',
      withAgentCd({
        plcyTypeCd: 'N', // 고정값: 위탁온라인(N)
        reqBuyTypeCd: 'MM', // 고정값: 단말(01)
        prdtId: rprsPrdtId,
        operTypeCd: model.value.operTypeCd || 'MNP3',
      }),
    )
    const data = extractData(res)
    if (data && data.length > 0) {
      // API에서 실시간 조회된 판매정책 목록의 첫 번째 값을 항상 강제(우선) 선택합니다.
      model.value.modelSalePolicyCd = data[0].salePlcyCd || data[0].salePlcyCode || ''
    }
  } catch (e) {
    console.error('Failed to fetch sale policy:', e)
  }
}

// 7. 할인유형 조회
const fetchDiscountTypes = async () => {
  if (model.value.productType === 'UU') {
    discountTypeOptions.value = []
    model.value.discountType = ''
    return
  }

  const selectedDevice = deviceOptions.value.find((opt) => opt.value === model.value.deviceModel)
  const rprsPrdtId = selectedDevice?.rprsPrdtId || model.value.deviceModel

  try {
    const res = await post(
      '/api/form/phone/saletype/list',
      withAgentCd({
        reqBuyTypeCd: model.value.productType || 'MM', // 휴대폰: MM, USIM: UU
        plcySctnCd: model.value.productType || 'MM', // 휴대폰: MM, USIM: UU
        prdtId: rprsPrdtId,
        salePlcyCd: model.value.modelSalePolicyCd || '',
      }),
    )
    const availableList = extractData(res).map((item) => ({
      label: item.sprtNm || '할인유형',
      value: item.sprtTp,
    }))
    discountTypeOptions.value = availableList

    // 자동 선택
    if (availableList.length > 0) {
      const isCurrentValid = availableList.some(
        (opt) => String(opt.value) === String(model.value.discountType),
      )
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
    const isUsimOnly = model.value.productType === 'UU'
    const res = await post('/api/form/price/category/list', {
      rateAdsvcDivCd: 'P',
      agentCd: getAgentCd(),
      sprtTp: isUsimOnly ? '' : model.value.discountType || '',
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
  // 조회 시작 전 목록 및 선택값 명시적 초기화
  planOptions.value = []

  try {
    const isUsimOnly = model.value.productType === 'UU'

    // 단말기 형태일 때 판매정책코드가 비어있다면 먼저 판매정책 조회 및 첫 번째 값 세팅을 보장
    if (!isUsimOnly && !model.value.modelSalePolicyCd && model.value.deviceModel) {
      const selectedDevice = deviceOptions.value.find(
        (opt) => String(opt.value) === String(model.value.deviceModel),
      )
      const rprsPrdtId = selectedDevice?.rprsPrdtId || model.value.deviceModel
      if (rprsPrdtId) {
        await fetchSalePolicy(rprsPrdtId)
      }
    }
    const res = await post(
      '/api/form/rate/list',
      withAgentCd({
        sprtTp: isUsimOnly ? '' : model.value.discountType || '',
        prodCtgId: ctgCd || '',
        reqBuyTypeCd: model.value.productType || 'MM',
        salePlcyCd: isUsimOnly ? '' : model.value.modelSalePolicyCd || '',
      }),
    )

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

    const rrn1 = model.value?.cstmrNativeRrn1 || ''
    const rrn2 = model.value?.cstmrNativeRrn2 || ''
    const userBirth =
      rrn1 && rrn2 ? extractYYYYMMDDRrn(rrn1 + rrn2) : model.value?.userBirthDate || rrn1
    const userAge = getAge(userBirth)
    const isSenior = userAge >= 65
    const isYouth = userAge < 19

    const list = extractData(res)
      .filter((item) => {
        if ((item.rateNm || '').includes('시니어') && !isSenior) {
          return false
        }
        // 청소년 요금제 필터링 (만 19세 미만만 노출)
        const isYouthPlan = ['청소년', '주니어', '키즈'].some((kword) =>
          (item.rateNm || '').includes(kword),
        )
        if (isYouthPlan && !isYouth) {
          return false
        }
        return true
      })
      .sort((a, b) => {
        return (a.dispSeq || 999) - (b.dispSeq || 999)
      })
      .map((item) => ({
        label: item.rateNm + ' (' + formatCurrency(item.baseAmt) + '원)' || item.ctgNm,
        value: item.rateCd || item.ctgCd || item.prodId,
        raw: item,
      }))

    planOptions.value = list

    // 요금제 결과가 있고, 현재 선택된 요금제가 목록에 없으면 첫 번째 요금제로 강제 선택
    if (list.length > 0) {
      const currentProdId = model.value.socCode || ''
      const isExist = list.some((opt) => String(opt.value) === String(currentProdId))
      if (currentProdId && isExist) {
        const selectedOpt = list.find((opt) => opt.value === currentProdId)
        if (!model.value.prodId) model.value.prodId = currentProdId
        if (!model.value.socCode) model.value.socCode = currentProdId

        //  [유심단독 UU 보완] 기존 요금제를 목록에서 찾았을 때, 해당 요금제의 카테고리 ID가 비어있다면 역으로 주입/동기화 수행
        if (selectedOpt && selectedOpt.raw) {
          const matchedCtgId = selectedOpt.raw.prodCtgId || selectedOpt.raw.ctgCd || ''
          if (matchedCtgId && !model.value.prodCtgId) {
            model.value.prodCtgId = matchedCtgId
          }
        }
      } else {
        const isExist = list.some((opt) => opt.value === currentProdId)
        //  오직 기존 요금제 선택 값이 완전히 비어있을 때만 첫 번째 항목을 디폴트로 자동 체크
        // 이미 요금제가 선택되어 있는 상태라면 목록에 없더라도 리셋하거나 덮어쓰지 않고 기존 선택을 보존합니다.
        if (!currentProdId || !isExist) {
          model.value.prodId = list[0].value
          model.value.prodNm = list[0].label
          model.value.socCode = list[0].value
          model.value.socNm = list[0].label
          model.value.prdtSctnCd = list[0].raw?.prdtSctnCd || ''
          model.value.dataType = list[0].raw?.dataType || ''
          model.value.socBaseChrgAmt = Number(list[0].raw?.baseAmt || 0)

          //  첫 번째 요금제로 디폴트 체크 시, 해당 요금제의 카테고리 ID도 자동 동기화 주입
          const defaultCtgId = list[0].raw?.prodCtgId || list[0].raw?.ctgCd || ''
          if (defaultCtgId && !model.value.prodCtgId) {
            model.value.prodCtgId = defaultCtgId
          }
        }
      }
    } else {
      if (!newChgStore.isDraftLoading) {
        model.value.prodId = ''
        model.value.prodNm = ''
        model.value.prdtSctnCd = ''
        model.value.dataType = ''
        model.value.socCode = ''
        model.value.socBaseChrgAmt = 0
      }
    }

    // 이미 선택된 요금제가 목록에 존재하여 prodId가 변하지 않았을 때도 prdtSctnCd와 dataType을 동기화
    if (model.value.prodId) {
      const selected = list.find((opt) => opt.value === model.value.prodId)
      if (selected) {
        const name = (
          selected.label ||
          selected.raw?.rateNm ||
          selected.raw?.prodNm ||
          ''
        ).toUpperCase()
        const parsedCd = name.includes('5G')
          ? '5G'
          : name.includes('USIM') || name.includes('유심')
            ? 'USIM'
            : 'LTE'

        // 5G 요금제 유무 및 데이터 타입 정방향 매핑 주입!
        const rawDataType = selected.raw?.dataType || '' // e.g., LTE
        const rawPrdtSctnCd = selected.raw?.prdtSctnCd || selected.raw?.prodSctnCd || '' // e.g., LTE5G

        model.value.prdtSctnCd = rawPrdtSctnCd || rawDataType || parsedCd
        model.value.dataType = rawDataType || (rawPrdtSctnCd.includes('5G') ? '5G' : parsedCd)
        model.value.socCode = model.value.prodId

        const baseChrgAmt = Number(
          selected.raw?.baseAmt || selected.raw?.baseChrgAmt || selected.raw?.socBaseChrgAmt || 0,
        )
        model.value.socBaseChrgAmt = baseChrgAmt

        const pData = props.productData
        if (pData) {
          pData.socCode = model.value.prodId
          pData.socNm = selected.label || ''
          pData.jehuPartnerTypeCd = selected.raw?.jehuProdType || ''
          pData.jehuPartnerTypeNm = selected.raw?.jehuProdNm || ''
          pData.jehuProdTypeCd = selected.raw?.jehuProdType || ''
          pData.socBaseChrgAmt = baseChrgAmt
        }

        // Pinia Store 원본 product 객체 동기화 보장
        if (newChgStore.product) {
          newChgStore.product.socCode = model.value.prodId
          newChgStore.product.socNm = selected.label || ''
          newChgStore.product.socBaseChrgAmt = baseChrgAmt
          newChgStore.product.jehuPartnerTypeCd = selected.raw?.jehuProdType || ''
          newChgStore.product.jehuPartnerTypeNm = selected.raw?.jehuProdNm || ''
          newChgStore.product.jehuProdTypeCd = selected.raw?.jehuProdType || ''
        }
      }
    }
  } catch (error) {
    console.error('Failed to fetch plans:', error)
    planOptions.value = []
    model.value.prodId = ''
    model.value.prodNm = ''
    model.value.prdtSctnCd = ''
    model.value.dataType = ''
    model.value.socCode = ''
    model.value.socBaseChrgAmt = 0
    const pDataCatch = props.productData
    if (pDataCatch) {
      pDataCatch.socCode = ''
      pDataCatch.socNm = ''
      pDataCatch.jehuPartnerTypeCd = ''
      pDataCatch.jehuPartnerTypeNm = ''
      pDataCatch.jehuProdTypeCd = ''
      pDataCatch.socBaseChrgAmt = 0
    }
  }
}

// 요금제 선택 시 prodNm 및 prdtSctnCd 업데이트
watch(
  () => model.value.prodId,
  (newVal) => {
    if (newVal) {
      //  타이밍 이슈 방지: 요금제 코드가 들어오는 즉시 스토어와 모델에 무조건 1차 동기화
      model.value.socCode = newVal
      if (newChgStore.product) {
        newChgStore.product.socCode = newVal
      }

      const selected = planOptions.value.find((opt) => opt.value === newVal)
      if (selected) {
        model.value.prodNm = selected.label

        // 요금제명 기반 구분코드 판별 (5G / USIM / LTE)
        const name = (
          selected.label ||
          selected.raw?.rateNm ||
          selected.raw?.prodNm ||
          ''
        ).toUpperCase()
        const parsedCd = name.includes('5G')
          ? '5G'
          : name.includes('USIM') || name.includes('유심')
            ? 'USIM'
            : 'LTE'

        // 5G 요금제 유무 및 데이터 타입 정방향 매핑 주입!
        const rawDataType = selected.raw?.dataType || '' // e.g., LTE
        const rawPrdtSctnCd = selected.raw?.prdtSctnCd || selected.raw?.prodSctnCd || '' // e.g., LTE5G

        model.value.prdtSctnCd = rawPrdtSctnCd || rawDataType || parsedCd
        model.value.dataType = rawDataType || (rawPrdtSctnCd.includes('5G') ? '5G' : parsedCd)

        const baseChrgAmt = Number(
          selected.raw?.baseAmt || selected.raw?.baseChrgAmt || selected.raw?.socBaseChrgAmt || 0,
        )
        model.value.socBaseChrgAmt = baseChrgAmt

        const pDataWatch = props.productData
        if (pDataWatch) {
          pDataWatch.socCode = newVal
          pDataWatch.socNm = selected.label || ''
          pDataWatch.jehuPartnerTypeCd = selected.raw?.jehuProdType || ''
          pDataWatch.jehuPartnerTypeNm = selected.raw?.jehuProdNm || ''
          pDataWatch.jehuProdTypeCd = selected.raw?.jehuProdType || ''
          pDataWatch.socBaseChrgAmt = baseChrgAmt
        }

        // Pinia Store 원본 product 객체 동기화 보장 (저장 시 구버전 값 유실 방지)
        if (newChgStore.product) {
          newChgStore.product.socNm = selected.label || ''
          newChgStore.product.socBaseChrgAmt = baseChrgAmt
          newChgStore.product.jehuPartnerTypeCd = selected.raw?.jehuProdType || ''
          newChgStore.product.jehuPartnerTypeNm = selected.raw?.jehuProdNm || ''
          newChgStore.product.jehuProdTypeCd = selected.raw?.jehuProdType || ''
        }
      }
    } else {
      model.value.prodNm = ''
      model.value.prdtSctnCd = ''
      model.value.dataType = ''
      model.value.socCode = '' // 초기화
        const pDataWatchElse = props.productData
        if (pDataWatchElse) {
          pDataWatchElse.socCode = ''
          pDataWatchElse.socNm = ''
          pDataWatchElse.jehuPartnerTypeCd = ''
          pDataWatchElse.jehuPartnerTypeNm = ''
          pDataWatchElse.jehuProdTypeCd = ''
        }
      if (newChgStore.product) {
        newChgStore.product.socCode = ''
        newChgStore.product.socNm = ''
        newChgStore.product.socBaseChrgAmt = 0
        newChgStore.product.jehuPartnerTypeCd = ''
        newChgStore.product.jehuPartnerTypeNm = ''
        newChgStore.product.jehuProdTypeCd = ''
      }
    }
  },
)

// 약정기간 변경 시 할부기간 자동 동기화 및 할인유형 제어 (임시저장 불러오기 시 오동작 리셋 방지)
watch(
  () => model.value.contractPeriod,
  (newVal) => {
    if (newChgStore.isDraftLoading) return
    if (model.value.isSaved) return

    const isNoContract = String(newVal) === '0'

    const joinType = props.joinType || model.value?.joinType || ''
    const hasNoneOption = joinType === 'NAC3'

    if (isNoContract) {
      model.value.discountType = ''

      if (hasNoneOption) {
        // 약정기간 '무약정' 선택 시, 신규가입(NAC3)은 단말기 할부기간을 '0' (0개월/일시불)으로 디폴트 세팅
        model.value.installmentMonth = '0'
      } else {
        // 일시불(0개월)이 허용되지 않는 경우(번호이동/기변) 할부기간이 '0'이면 0이 아닌 다른 유효한 값으로 보정
        if (String(model.value.installmentMonth) === '0') {
          const otherOpts = installmentMonthOptions.value.filter((opt) => String(opt.value) !== '0')
          if (otherOpts.length > 0) {
            model.value.installmentMonth = otherOpts[0].value
          }
        }
      }
    } else {
      // 무약정 → 유약정 전환 시 discountType이 비어있으면 첫 번째 항목 자동 선택
      if (!model.value.discountType) {
        if (discountTypeOptions.value.length > 0) {
          model.value.discountType = discountTypeOptions.value[0].value
        } else {
          // 목록이 없으면 재조회 후 자동 선택 (fetchDiscountTypes 내부에서 처리)
          fetchDiscountTypes()
        }
      }
    }
  },
)

// 상품유형(휴대폰/USIM)이 변경될 때 단말기 선택 초기화 및 데이터 재조회
watch(
  () => model.value.productType,
  async (newVal, oldVal) => {
    if (
      oldVal !== undefined &&
      newVal !== oldVal &&
      !model.value.isSaved &&
      !newChgStore.isDraftLoading
    ) {
      console.log(
        '>>> [MsfDevicePlanInfo] productType changed, re-fetching devices & executing sequence:',
        newVal,
      )
      model.value.deviceModel = ''
      model.value.discountType = ''
      model.value.prodCtgId = ''
      model.value.prodId = ''
      model.value.prodNm = ''

      if (newVal === 'MM') {
        await fetchDevices()
      }
      await executeLoadingSequence()
    }
  },
)

// 단말기가 변경될 때 전체 하위 데이터 시퀀스 실행
watch(
  () => model.value.deviceModel,
  async (newVal, oldVal) => {
    // 유심 단독(UU)인 경우 단말기 변경 감시 로직을 완전히 차단하여 요금제 리셋 방지
    if (model.value.productType === 'UU') return

    // 값이 변경되었을 때 실행 (isSaved 여부와 관계없이 목록 데이터는 로드해야 함)
    if (newVal !== oldVal) {
      // 사용자가 직접 변경한 경우(oldVal이 존재함)에만 하위 값 초기화
      if (
        oldVal !== undefined &&
        oldVal !== '' &&
        !model.value.isSaved &&
        !newChgStore.isDraftLoading
      ) {
        model.value.discountType = ''
        model.value.installmentMonth = ''
        model.value.prodId = ''
      }

      await executeLoadingSequence()
    }
  },
)

// 대리점이 뒤늦게 세팅되거나 변경될 때 단말기 목록 재조회
watch(
  () => model.value?.agentCd,
  async (newVal, oldVal) => {
    // agentCd가 들어오면 isSaved 여부와 관계없이 단말기 목록을 가져와야 함
    if (newVal && newVal !== oldVal) {
      console.log('>>> [MsfDevicePlanInfo] Agent changed, re-fetching devices:', newVal)
      await fetchDevices()
      if (model.value.deviceModel) {
        await executeLoadingSequence()
      }
    }
  },
)

// 가입유형(operTypeCd)이 변경될 때 단말기 목록 재조회
watch(
  () => model.value.operTypeCd,
  async (newVal, oldVal) => {
    // operTypeCd가 변경되면 isSaved 여부와 관계없이 단말기 목록을 가져와야 함
    if (newVal && newVal !== oldVal) {
      console.log('>>> [MsfDevicePlanInfo] operTypeCd changed, re-fetching devices:', newVal)
      await fetchDevices()
      if (model.value.deviceModel) {
        await executeLoadingSequence()
      }
    }
  },
)

// 요금제 카테고리 또는 할인유형이 변경될 때 하위 요금제 목록 다시 불러오기
watch(
  () => [model.value.prodCtgId, model.value.discountType],
  async ([newCtgId], [oldCtgId]) => {
    // 임시저장 로딩 중, 순차 시퀀스 실행 중, 혹은 저장 완료 상태일 때는 감시자에 의한 이중 조회 완전 차단!
    if (newChgStore.isDraftLoading || isSequenceLoading.value || model.value.isSaved) return

    // 카테고리가 변경된 경우에만 초기화 (최초 비동기 할당을 제외하고, 사용자 조작으로 이전 값이 실존할 때만 초기화)
    if (newCtgId !== oldCtgId && oldCtgId !== undefined && oldCtgId !== '') {
      model.value.prodId = ''
      model.value.prodNm = ''
    }
    await fetchPlans(newCtgId)
  },
)

onMounted(async () => {
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
    if (!m.deviceModel || !m.contractPeriod) return false
    if (
      m.installmentMonth === undefined ||
      m.installmentMonth === null ||
      m.installmentMonth === ''
    )
      return false

    const isNoContract = String(m.contractPeriod) === '0'
    // 무약정이 아닌 경우에만 할인유형을 필수 체크
    if (!isNoContract) {
      if (!m.discountType) return false
    }
  }

  return true
}

const checkValidation = () => {
  if (model.value.productType === 'MM') {
    if (!model.value.deviceModel) {
      showAlert(`${props.title} 휴대폰을 선택하세요`, () => {
        deviceModelRef.value?.focus()
      })
      return false
    }
    if (model.value.deviceModel) {
      if (!model.value.contractPeriod) {
        showAlert(`${props.title} 약정기간을 선택하세요`, () => {
          contractPeriodRef.value?.focus()
        })
        return false
      }

      if (!model.value.installmentMonth) {
        showAlert(`${props.title} 단말기 할부기간을 선택하세요`, () => {
          installmentMonthRef.value?.focus()
        })
        return false
      }

      if (String(model.value.contractPeriod) !== '0' && !model.value.discountType) {
        showAlert(`${props.title} 할인유형을 선택하세요`, () => {
          discountTypeRef.value?.focus()
        })
        return false
      }
    }
  }
  if (!model.value.prodId) {
    showAlert(`${props.title} 요금제를 선택하세요`, () => {
      prodIdRef.value?.focus()
    })
    return false
  }

  return true
}

defineExpose({ validate, checkValidation })
</script>
