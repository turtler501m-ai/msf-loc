<template>
  <div class="page-step-panel">
    <MsfProductJoinType ref="productJoinTypeRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfCustomerType ref="customerTypeRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfIdentityVerify ref="identityVerifyRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfSubscriberInfo ref="subscriberInfoRef" v-model="formData" :authFlags="store.authFlags" phoneLabel="기기변경<br/>휴대폰번호" />
    <MsfLegalAgentInfo ref="legalAgentInfoRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfRealUserInfo
      ref="realUserInfoRef"
      v-model="formData"
      v-if="['JP', 'GO'].includes(formData.cstmrTypeCd) && formData.cstmrVisitTypeCd === 'V2'"
    />
    <MsfDelegateInfo
      ref="delegateInfoRef"
      v-model="formData"
      v-if="['JP', 'GO'].includes(formData.cstmrTypeCd) && formData.cstmrVisitTypeCd === 'V2'"
    />
    <MsfRequiredDoc ref="requiredDocRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfContactInfo ref="contactInfoRef" v-model="formData" />

    <MsfDevicePlanInfo ref="devicePlanInfoRef" v-model="formData" />

    <MsfTermsAgreement
      ref="termsAgreementRef"
      v-model="formData"
      :termsData="filteredTermsDataList"
      :isSaved="formData.isSaved"
      required
    />
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { getCommonCodeList } from '@/libs/utils/comn.utils'

const rawTermsList = ref([])

const termsDataList = computed(() => {
  if (rawTermsList.value.length === 0) {
    // API 조회 지연 혹은 에러 시 임시 하드코딩 폴백 (오류 방지)
    return [
      {
        id: 'clauseMoveCode',
        label: '번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의',
        required: true,
        checked: false,
        dtlCd: 'clauseMoveCode',
      },
      {
        id: 'clauseEssCollectYn',
        label: '고유식별정보 수집·이용 동의',
        required: true,
        checked: false,
        dtlCd: 'clauseEssCollectYn',
      },
      {
        id: 'clausePriCollectYn',
        label: '개인정보/신용정보 수집·이용 동의',
        required: true,
        checked: false,
        dtlCd: 'clausePriCollectYn',
      },
      {
        id: 'clausePriOfferYn',
        label: '개인정보 제3자 제공 동의',
        required: true,
        checked: false,
        dtlCd: 'clausePriOfferYn',
      },
      {
        id: 'clauseFathFlag01',
        label: '민감정보(생체인식정보) 수집 및 이용 동의',
        required: true,
        checked: false,
        dtlCd: 'clauseFathFlag01',
      },
      {
        id: 'clauseFathFlag02',
        label: '민감정보(생체인식정보) 조회 및 이용 / 3자 제공에 대한 동의',
        required: true,
        checked: false,
        dtlCd: 'clauseFathFlag02',
      },
      {
        id: 'nwBlckAgrmYn',
        label: '청소년 유해정보 네트워크차단 동의',
        required: true,
        checked: false,
        dtlCd: 'nwBlckAgrmYn',
      },
      {
        id: 'appBlckAgrmYn',
        label: '청소년 유해정보차단 APP 설치 동의',
        required: true,
        checked: false,
        dtlCd: 'appBlckAgrmYn',
      },
      {
        id: 'clause5gCoverage',
        label: '5g 커버리지 확인 및 가입 동의',
        required: true,
        checked: false,
        dtlCd: 'clause5gCoverage',
      },
      {
        id: 'clauseJehuFlag',
        label: '개인정보 제3자 제공 동의',
        required: true,
        checked: false,
        dtlCd: 'clauseJehuFlag',
      },
      {
        id: 'clausePartnerOfferFlag',
        label: '개인정보 제3자제공 동의',
        required: true,
        checked: false,
        dtlCd: 'clausePartnerOfferFlag',
      },
      {
        id: 'personalInfoCollectAgreeYn',
        label: '고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의',
        required: false,
        checked: false,
        dtlCd: 'personalInfoCollectAgreeYn',
      },
      {
        id: 'clausePriAdYn',
        label: '개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의',
        required: false,
        checked: false,
        dtlCd: 'clausePriAdYn',
      },
      {
        id: 'othersAdReceiveAgreeYn',
        label: '혜택 제공을 위한 제3자 제공 동의',
        required: false,
        checked: false,
        dtlCd: 'othersAdReceiveAgreeYn',
      },
      {
        id: 'othersTrnsAgreeYn',
        label: '혜택 제공을 위한 제3자 제공 동의(M모바일)',
        required: false,
        checked: false,
        dtlCd: 'othersTrnsAgreeYn',
      },
      {
        id: 'othersTrnsKtAgreeYn',
        label: '혜택 제공을 위한 제3자 제공 동의(KT)',
        required: false,
        checked: false,
        dtlCd: 'othersTrnsKtAgreeYn',
      },
      {
        id: 'personalLocationAgreeYn',
        label: '개인위치정보 제3자 제공 동의',
        required: false,
        checked: false,
        dtlCd: 'personalLocationAgreeYn',
      },
      {
        id: 'clauseInfo01',
        label: '서비스 이용약관',
        required: true,
        checked: false,
        dtlCd: 'clauseInfo01',
      },
    ]
  }

  return rawTermsList.value.map((item) => {
    const isReq =
      item.expnsnStrVal3 === 'Y' || item.EXPNSN_STR_VAL3 === 'Y' || item.required || false
    const dtlCode = item.code || item.dtlCd || item.value || item.DTL_CD

    // API로 내려온 dtlCode(예: CLAUSE_MOVE_01)를 실제 저장용 변수명으로 매핑 (없으면 그대로)
    const saveKeyMap = {
      CLAUSE_MOVE_01: 'clauseMoveCode',
      CLAUSE_REQUIRED_02: 'clauseEssCollectYn',
      CLAUSE_REQUIRED_01: 'clausePriCollectYn',
      CLAUSE_REQUIRED_03: 'clausePriOfferYn',
      CLAUSE_FATH_01: 'clauseFathFlag01',
      CLAUSE_FATH_02: 'clauseFathFlag02',
      CLAUSE_REQUIRED_06: 'nwBlckAgrmYn',
      CLAUSE_REQUIRED_07: 'appBlckAgrmYn',
      CLAUSE_REQUIRED_5G: 'clause5gCoverage',
      CLAUSE_PARTNER_01: 'clauseJehuFlag',
      CLAUSE_PARTNER_02: 'clausePartnerOfferFlag',
      CLAUSE_SELECT_03: 'personalInfoCollectAgreeYn',
      CLAUSE_SELECT_01: 'clausePriAdYn',
      CLAUSE_SELECT_08: 'othersAdReceiveAgreeYn',
      CLAUSE_SELECT_04: 'othersTrnsAgreeYn',
      CLAUSE_SELECT_06: 'othersTrnsKtAgreeYn',
      CLAUSE_SELECT_07: 'othersAdReceiveAgreeYn',
      CLAUSE_SELECT_10: 'personalLocationAgreeYn',
      CLAUSE_INFO_01: 'clauseInfo01',
    }
    const finalKey = saveKeyMap[dtlCode] || dtlCode

    return {
      id: finalKey,
      dtlCd: finalKey,
      label: item.title,
      required: isReq,
      checked: false,
    }
  })
})

const filteredTermsDataList = computed(() => {
  return termsDataList.value.filter((term) => {
    const termId = term.id

    // 1. 번호이동
    if (termId === 'clauseMoveCode' && formData.joinType !== 'MNP3') return false

    // 2. 미성년자 조건
    if (
      ['nwBlckAgrmYn', 'appBlckAgrmYn', 'blckAppDivCd'].includes(termId) &&
      !['NM', 'FM'].includes(formData.cstmrTypeCd)
    )
      return false

    // 3. 안면인증
    if (
      ['clauseFathFlag01', 'clauseFathFlag02'].includes(termId) &&
      formData.identityCertTypeCd !== 'F'
    )
      return false

    // 4. 5G요금제
    if (termId === 'clause5gCoverage' && !(formData.planName2 || '').includes('5G')) return false

    // 5. 제휴요금
    if (['clauseJehuFlag', 'clausePartnerOfferFlag'].includes(termId) && !formData.partnerEventCode)
      return false

    // 6. 사업이관
    if (termId === 'soTrnsAgrmYn' && formData.isTransferTarget !== 'Y') return false

    // 7. 단말 렌탈
    if (
      ['clauseRentalModelCpYn', 'clauseRentalModelCpPrYn', 'clauseRentalServiceYn'].includes(
        termId,
      ) &&
      productData.modelSalePlcyCd !== 'RENTAL'
    )
      return false

    // 8. 선불 요금제
    if (termId === 'clauseMpps35Mpps35Yn' && productData.ratePlanCategory !== 'PREPAID')
      return false

    // 9. 금융제휴
    if (termId === 'clauseFinanceFinanceYn' && productData.insrYn !== 'Y') return false

    // 10. 복지할인 민감정보
    if (
      ['clauseSensiCollectYn', 'clauseSensiOfferYn'].includes(termId) &&
      formData.welfareDiscount !== 'Y'
    )
      return false

    // 11. 약관제휴사제공동의여부
    if (termId === 'clausePartnerOfferYn' && !formData.partnerEventCode) return false

    // 12. 아무나솔로결합
    if (
      ['combineSoloTypeYn', 'combineSoloSoloYn'].includes(termId) &&
      !(productData.addonService || []).includes('SOLO_COMB')
    )
      return false

    // 13. 번호이동 미환급액 상계 (가입유형 번호이동)
    if (termId === 'moveRefundAgreeYn' && formData.joinType !== 'MNP3') return false

    return true
  })
})

const emit = defineEmits(['complete'])
const store = useMsfFormNewChgStore()
const formData = store.customer
const productData = store.product

// 컴포넌트 Refs
const productJoinTypeRef = ref(null)
const customerTypeRef = ref(null)
const identityVerifyRef = ref(null)
const subscriberInfoRef = ref(null)
const legalAgentInfoRef = ref(null)
const realUserInfoRef = ref(null)
const delegateInfoRef = ref(null)
const requiredDocRef = ref(null)
const contactInfoRef = ref(null)
const devicePlanInfoRef = ref(null)
const termsAgreementRef = ref(null)

const isComplete = ref(false)

// 현재 단계(Customer)의 모든 컴포넌트 유효성 검사
const validate = () => {
  const validations = [
    productJoinTypeRef.value?.validate(),
    customerTypeRef.value?.validate(),
    identityVerifyRef.value?.validate(),
    subscriberInfoRef.value?.validate(),
    legalAgentInfoRef.value?.validate(),
    realUserInfoRef.value?.validate(),
    delegateInfoRef.value?.validate(),
    requiredDocRef.value?.validate(),
    contactInfoRef.value?.validate(),
    devicePlanInfoRef.value?.validate(),
    termsAgreementRef.value?.validate(),
  ]
  // null(비노출 컴포넌트)은 제외하고 모든 결과가 true인지 확인
  return validations.filter((v) => v !== undefined).every((v) => v === true)
}

const checkRequiredFields = () => {
  const pending = []

  // 1. 기본 정보 및 신분증 인증
  if (formData.identityCertTypeCd !== 'S' && !store.customer.isVerified) {
    pending.push('신분증 인증(조회/인증)')
  }
  if (!formData.cstmrNm) pending.push('고객명')
  if (!formData.mobileNo2) pending.push('휴대폰 번호')

  // 2. 가입유형별 추가 인증
  if (formData.joinType === 'HDN3' && !store.authFlags.deviceChgTel) {
    pending.push('기기변경 휴대폰 인증')
  }
  if (['NM', 'FM'].includes(formData.cstmrTypeCd) && !store.authFlags.repPhone) {
    pending.push('법정대리인 휴대폰 인증')
  }

  // 3. 구비서류 체크
  const cstmrTypeCd = formData.cstmrTypeCd
  const hasBizNo =
    (formData.cstmrJuridicalBizNo1 &&
      formData.cstmrJuridicalBizNo2 &&
      formData.cstmrJuridicalBizNo3) ||
    (formData.tr_bizNo1 && formData.tr_bizNo2 && formData.tr_bizNo3) ||
    (formData.te_bizNo1 && formData.te_bizNo2 && formData.te_bizNo3)

  let requiresDocs = false
  if (['FN', 'FM'].includes(cstmrTypeCd)) requiresDocs = true
  if (['PP', 'JP', 'GO'].includes(cstmrTypeCd) || hasBizNo) requiresDocs = true
  if (formData.cstmrVisitTypeCd === 'V2') requiresDocs = true
  if (!['NM', 'FM'].includes(cstmrTypeCd) && (formData.minorAgentNm || formData.repRelation))
    requiresDocs = true

  if (requiresDocs && !store.authFlags.requiredDocs) {
    pending.push('구비서류 확인/업로드')
  }

  // 4. 단말기 및 요금제 정보 (MsfDevicePlanInfo)
  if (devicePlanInfoRef.value && !devicePlanInfoRef.value.validate()) {
    pending.push('휴대폰/요금제 정보 필수 선택')
  }

  // 5. 약관 동의 (MsfTermsAgreement)
  if (termsAgreementRef.value && !termsAgreementRef.value.validate()) {
    pending.push('필수 약관 동의')
  }

  // 결과 처리
  const isReady = pending.length === 0

  if (!isReady) {
    console.log('%c[미입력 항목]:', 'color: #ff4d4f; font-weight: bold;', pending.join(', '))
  } else {
    console.log('%c[모든 입력 완료!]', 'color: #52c41a; font-weight: bold;')
  }

  isComplete.value = isReady
  emit('complete', isReady)
  return isReady
}

let debounceTimer = null
const checkRequiredFieldsDebounced = () => {
  if (debounceTimer) clearTimeout(debounceTimer)
  debounceTimer = setTimeout(() => {
    checkRequiredFields()
    debounceTimer = null
  }, 100)
}

watch(
  () => [formData, productData, store.authFlags],
  () => {
    checkRequiredFieldsDebounced()
  },
  { deep: true },
)

onMounted(async () => {
  const list = await getCommonCodeList('CLAUSE_FORM_01')
  rawTermsList.value = (list || []).map((item) => ({
    ...item,
    label: item.title,
    value: item.code,
  }))

  // checkRequiredFields() // watch에서 초기 실행되거나 데이터 변경 시 처리됨
  store.validateCustomer = validate
})

const data = async (code) => {
  // 화면 진입 시 초기값 및 임시저장값 세팅
  await store.initForm(code)

  if (code) return '1'
  return '0'
}

const save = async () => {
  // 컴포넌트 레벨 유효성 검사 우회 (임시/테스트)
  // if (!validate()) {
  //   console.error('Customer step validation failed')
  //   return false
  // }
  return await store.apiSaveDraft(1)
}

defineExpose({ data, save, validate, reset: store.resetAll })
</script>

<style lang="scss" scoped></style>
