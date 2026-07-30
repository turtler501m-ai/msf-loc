<script setup>
import { post } from '@/libs/api/msf.api'
import { computed, onMounted, ref, watch } from 'vue'
import { extractYYYYMMDDRrn, formatCurrency } from '@/libs/utils/string.utils'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { getCommonCodeList } from '@/libs/utils/comn.utils'

const model = defineModel({ type: Object, required: true })
const newChgStore = useMsfFormNewChgStore()
const customerData = defineModel('customerData', { type: Object, required: true })
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
})

// 요금제 카테고리 조회
const fetchPlanCategories = async () => {
  try {
    const res = await post('/api/form/price/category/list', {
      rateAdsvcDivCd: 'P',
      agentCd: customerData.value?.agentCd || '',
      sprtTp: model.value.discountType || '',
    })

    planCategoryOptions.value = res?.data?.map((item) => ({
      label: item.ctgNm,
      value: item.ctgCd,
    }))
  } catch (e) {
    console.error('Failed to fetch plan categories:', e)
  }
}

const getAgentCd = () => {
  const cd = customerData.value?.agentCd || model.value?.agentCd || ''
  if (cd) {
    return cd
  }
  const fallback = customerData.value?.agent || model.value?.agent || ''
  if (fallback) {
    return fallback
  }
  return ''
}

const withAgentCd = (payload = {}) => ({
  ...payload,
  agentCd: getAgentCd(),
})

// 8. 요금제 목록 조회 (카테고리 선택 시)
const fetchPlans = async (ctgCd) => {
  // 조회 시작 전 목록 및 선택값 명시적 초기화
  planOptions.value = []

  try {
    const isUsimOnly = model.value.productType === 'UU'
    const res = await post(
      '/api/form/rate/list',
      withAgentCd({
        sprtTp: isUsimOnly ? '' : model.value.discountType || '',
        prodCtgId: ctgCd || '',
        reqBuyTypeCd: 'UU',
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

    const rrn1 = props.customerData?.cstmrNativeRrn1 || ''
    const rrn2 = props.customerData?.cstmrNativeRrn2 || ''
    const userBirth =
      rrn1 && rrn2 ? extractYYYYMMDDRrn(rrn1 + rrn2) : props.customerData?.userBirthDate || rrn1
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

    // 요금제 결과가 없거나, 현재 선택된 요금제가 목록에 없으면 초기화 (로딩 중에는 리셋 방지)
    if (list.length > 0) {
      const isExist = list.some((opt) => opt.value === model.value.planName2)
      if (!model.value.planName2) {
        model.value.planName2 = list[0].value
        model.value.planNm = list[0].label
      } else if (!isExist) {
        if (!newChgStore.isDraftLoading) {
          model.value.planName2 = ''
          model.value.planNm = ''
          model.value.prdtSctnCd = ''
          model.value.dataType = ''
          model.value.socCode = ''
          model.value.planAmt = 0
        }
      }
    } else {
      if (!newChgStore.isDraftLoading) {
        model.value.planName2 = ''
        model.value.planNm = ''
        model.value.prdtSctnCd = ''
        model.value.dataType = ''
        model.value.socCode = ''
        model.value.planAmt = 0
      }
    }

    // 이미 선택된 요금제가 목록에 존재하여 planName2가 변하지 않았을 때도 prdtSctnCd와 dataType을 동기화
    if (model.value.planName2) {
      const selected = list.find((opt) => opt.value === model.value.planName2)
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

        model.value.prdtSctnCd =
          selected.raw?.prdtSctnCd || selected.raw?.prodSctnCd || selected.raw?.dataType || parsedCd
        model.value.dataType =
          selected.raw?.dataType || selected.raw?.prdtSctnCd || selected.raw?.prodSctnCd || parsedCd

        const baseChrgAmt = Number(
          selected.raw?.baseAmt || selected.raw?.baseChrgAmt || selected.raw?.socBaseChrgAmt || 0,
        )
        model.value.planAmt = baseChrgAmt
        model.value.planNm = selected.label || ''
        model.value.jehuPartnerTypeCd = selected.raw?.jehuProdType || ''
        model.value.jehuPartnerTypeNm = selected.raw?.jehuProdNm || ''
        model.value.jehuProdTypeCd = selected.raw?.jehuProdType || ''
      }
    }
  } catch (error) {
    console.error('Failed to fetch plans:', error)
    planOptions.value = []
    model.value.planName2 = ''
    model.value.socNm = ''
    model.value.jehuPartnerTypeCd = ''
    model.value.jehuPartnerTypeNm = ''
    model.value.jehuProdTypeCd = ''
    model.value.planAmt = 0
  }
}

const validate = () => {
  if (!model.value.planName2 || !customerData.value.agent) return false

  return true
}

defineExpose({ validate })

// 요금제 카테고리가 변경될 때 하위 요금제 목록 다시 불러오기
watch(
  () => model.value.planName1,
  (newVal) => {
    fetchPlans(newVal)
  },
)

watch(
  () => model.value.planSelectType,
  async (newVal) => {
    if (newVal == 'CURRENT') {
      model.value.planName1 = ''
      model.value.planName2 = model.value.orgProdId
      model.value.planNm = model.value.orgProdNm
      model.value.planAmt = model.value.orgPlanAmt
      planOptions.value = [{ value: model.value.orgProdId, label: model.value.orgProdNm }]
    } else {
      await fetchPlanCategories()
      await fetchPlans(model.value.prodCtgId)
    }
  },
)

// 요금제 선택 시 prodNm 및 prdtSctnCd 업데이트
watch(
  () => model.value.planName2,
  (newVal) => {
    if (newVal) {
      const selected = planOptions.value.find((opt) => opt.value === newVal)
      console.log(selected)
      if (selected) {
        model.value.prodNm = selected.label
        model.value.planAmt = selected.raw?.baseAmt
        model.value.planNm = selected.label

        // 5G 요금제 유무 및 데이터 타입 역방향 매핑 주입!
        const rawDataType = selected.raw?.dataType || '' // e.g., LTE
        const rawPrdtSctnCd = selected.raw?.prdtSctnCd || selected.raw?.prodSctnCd || '' // e.g., LTE5G

        model.value.prdtSctnCd = rawPrdtSctnCd || rawDataType || 'LTE'
        model.value.dataType = rawDataType || (rawPrdtSctnCd.includes('5G') ? '5G' : 'LTE')

        // 중앙 스토어의 5G 약관 노출 유무 플래그 자동 제어 트리거!
        const is5G =
          String(model.value.prdtSctnCd).toUpperCase() === '5G' ||
          String(model.value.dataType).toUpperCase().includes('5G')
        newChgStore.customer.CLAUSE_REQUIRED_5G = is5G

        model.value.jehuPartnerTypeCd = selected.raw?.jehuProdType || ''
        model.value.jehuPartnerTypeNm = selected.raw?.jehuProdNm || ''
        model.value.jehuProdTypeCd = selected.raw?.jehuProdType || ''
      }
    } else {
      model.value.prodNm = null
      model.value.planAmt = null
      model.value.planNm = null
      model.value.dataType = null
      model.value.prdtSctnCd = null
      newChgStore.customer.CLAUSE_REQUIRED_5G = false
      model.value.jehuPartnerTypeCd = null
      model.value.jehuPartnerTypeNm = null
      model.value.jehuProdTypeCd = null
    }
  },
)
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
        selectPopYn
      />
      <MsfSelect
        title="요금제"
        v-model="model.planName2"
        :options="planOptions"
        class="ut-w100p"
        v-if="model.planSelectType !== 'CURRENT'"
        selectPopYn
      />
    </MsfFormGroup>
  </MsfStack>
</template>
