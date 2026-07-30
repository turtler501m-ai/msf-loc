<template>
  <div class="page-step-panel">
    <MsfProductJoinType
      ref="productJoinTypeRef"
      v-model="formData"
      :authFlags="store.authFlags"
      :disabled="store.preChecked"
    />
    <MsfCustomerType
      ref="customerTypeRef"
      v-model="formData"
      :authFlags="store.authFlags"
      :disabled="store.preChecked"
      @change-customer-type="resetAfterCustomerTypeChange"
    />
    <MsfIdentityVerify
      ref="identityVerifyRef"
      v-model="formData"
      :authFlags="store.authFlags"
      :disabled="store.preChecked"
      :check-before-face-auth="checkBeforeFaceAuth"
    />
    <MsfSubscriberInfo
      ref="subscriberInfoRef"
      v-model="formData"
      :authFlags="store.authFlags"
      phoneLabel="기기변경<br/>휴대폰번호"
      :disabled="store.preChecked"
    />
    <MsfLegalAgentInfo
      ref="legalAgentInfoRef"
      v-model="formData"
      :authFlags="store.authFlags"
      :disabled="store.preChecked"
    />
    <MsfRealUserInfo
      ref="realUserInfoRef"
      v-model="formData"
      v-if="
        ['JP', 'GO'].includes(formData.cstmrTypeCd) && !['HDN3', 'HCN3'].includes(formData.joinType)
      "
      :disabled="store.preChecked"
    />
    <MsfDelegateInfo
      ref="delegateInfoRef"
      v-model="formData"
      v-if="['JP', 'GO'].includes(formData.cstmrTypeCd) && formData.cstmrVisitTypeCd === 'VDP'"
      :disabled="store.preChecked"
    />

    <MsfContactInfo
      ref="contactInfoRef"
      v-model="formData"
      :cstmrBillSendTypeCd="productData.cstmrBillSendTypeCd"
      :disabled="store.preChecked"
    />

    <MsfDevicePlanInfo
      ref="devicePlanInfoRef"
      v-model="formData"
      :productData="productData"
      :join-type="formData.joinType"
      :disabled="store.preChecked"
    />

    <MsfTermsAgreement
      ref="termsAgreementRef"
      v-model="formData"
      :termsData="filteredTermsDataList"
      :specTerms="dynamicSpecTerms"
      :isSaved="formData.isSaved"
      :disabled="store.preChecked"
      @checked="checkRequiredFields"
      required
    />

    <!-- 가입조건 조회 컴포넌트 -->
    <MsfEligibilityCheck
      v-if="formData.joinType !== 'HDN3' && formData.joinType !== 'HCN3'"
      ref="eligibilityCheckRef"
      :key="`${formData.cstmrTypeCd}-${customerSsn}`"
      :cstmr-type-cd="formData.cstmrTypeCd"
      :customer-ssn="customerSsn"
      :before-check="checkBeforeEligibility"
      :disabled="store.preChecked"
      :join-type="formData.joinType"
      :biz-bulk-activation-avail-yn="formData.canBulkCorporateOpenYn"
      :bulk-activation-cnt="formData.bulkActivationCnt"
      :sim-type-cd="productData.simTypeCd"
      :agent-cd="formData.agentCd"
      @checked="onEligibilityChecked"
    />
  </div>
</template>

<script setup>
import { ref, watch, onMounted, onUnmounted, computed } from 'vue'
import { storeToRefs } from 'pinia'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { focusField } from '@/libs/utils/focus.utils'
import { useScrollLock } from '@/hooks/useScrollLock'
import { setLayoutLock } from '@/hooks/useGlobalScroll'
import MsfEligibilityCheck from '@/components/form/common/MsfEligibilityCheck.vue'
// 선택입력검증 테스트
import { useOptionalValidationAlert } from '@/hooks/useOptionalValidationAlert'

const store = useMsfFormNewChgStore()

const { isLocked } = useScrollLock()
watch(
  isLocked,
  (newVal) => {
    setLayoutLock(newVal)
  },
  { immediate: true },
)

const { customer: formData, product: productData, isDraftLoading } = storeToRefs(store)
const rawTermsList = ref([])

const saveKeyMap = {
  CLAUSE_MOVE_01: 'clauseMoveCode',
  CLAUSE_REQUIRED_02: 'clauseEssCollectYn',
  CLAUSE_REQUIRED_01: 'clausePriCollectYn',
  CLAUSE_REQUIRED_03: 'clausePriOfferYn',
  CLAUSE_FATH_01: 'clauseSensiCoverageYn',
  CLAUSE_FATH_02: 'clauseSensiOfferYn',
  CLAUSE_REQUIRED_06: 'nwBlckAgrmYn',
  CLAUSE_REQUIRED_07: 'appBlckAgrmYn',
  CLAUSE_REQUIRED_5G: 'clause5gCoverageYn',
  CLAUSE_PARTNER_01: 'clausePartnerOfferYn',
  CLAUSE_PARTNER_02: 'clauseJehuYn',
  CLAUSE_SELECT_03: 'personalInfoCollectAgreeYn',
  CLAUSE_SELECT_01: 'clausePriAdYn',
  CLAUSE_SELECT_08: 'CLAUSE_SELECT_08',
  CLAUSE_SELECT_04: 'othersTrnsAgreeYn',
  CLAUSE_SELECT_06: 'othersTrnsKtAgreeYn',
  CLAUSE_SELECT_07: 'CLAUSE_SELECT_07',
  CLAUSE_SELECT_TIT_02: 'CLAUSE_SELECT_TIT_02',
  CLAUSE_SELECT_10: 'personalLocationAgreeYn',
  CLAUSE_INFO_01: 'clauseInfo01',
  CLAUSE_PRI_TRUST_YN: 'clausePriTrustYn',
  CLAUSE_CONFIDENCE_YN: 'clauseConfidenceYn',
  CLAUSE_FATH_YN: 'clauseFathYn',
  SO_TRNS_AGRM_YN: 'soTrnsAgrmYn',
  CLAUSE_JEHU_YN: 'clauseJehuYn',
  CLAUSE_MPPS35_YN: 'clauseMpps35Yn',
  CLAUSE_FINANCE_YN: 'clauseFinanceYn',
  PERSONAL_INFO_COLLECT_AGREE_YN: 'personalInfoCollectAgreeYn',
  OTHERS_TRNS_AGREE_YN: 'othersTrnsAgreeYn',
  CLAUSE_SENSI_COLLECT_YN: 'clauseSensiCollectYn',
  CLAUSE_SENSI_OFFER_YN: 'clauseSensiOfferYn',
  CLAUSE_PARTNER_OFFER_YN: 'clausePartnerOfferYn',
  OTHERS_TRNS_KT_AGREE_YN: 'othersTrnsKtAgreeYn',
  OTHERS_AD_RECEIVE_AGREE_YN: 'othersAdReceiveAgreeYn',
}

const termsDataList = computed(() => {
  let baseList =
    rawTermsList.value.length > 0
      ? [...rawTermsList.value]
      : [
          {
            code: 'CLAUSE_MOVE_01',
            title: '번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의',
            required: true,
          },
          { code: 'CLAUSE_REQUIRED_02', title: '개인정보 수집·이용 동의(필수)', required: true },
          { code: 'CLAUSE_REQUIRED_01', title: '개인정보 수집·이용 동의(필수)', required: true },
          { code: 'CLAUSE_REQUIRED_03', title: '개인정보 제3자 제공 동의(필수)', required: true },
          { code: 'CLAUSE_REQUIRED_06', title: '네트워크 차단 동의', required: true },
          { code: 'CLAUSE_REQUIRED_07', title: '앱 차단 동의', required: true },
          { code: 'CLAUSE_FATH_01', title: '법정대리인 개인정보 수집·이용 동의', required: true },
          { code: 'CLAUSE_FATH_02', title: '법정대리인 개인정보 제3자 제공 동의', required: true },
          { code: 'CLAUSE_REQUIRED_5G', title: '5G 커버리지 및 단말기 특성 확인', required: true },
          { code: 'CLAUSE_PARTNER_01', required: true },
          { code: 'CLAUSE_PARTNER_02', required: true },
          { code: 'CLAUSE_SELECT_03', title: '개인정보 수집·이용 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_01', title: '광고성 정보 수신 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_08', title: '마케팅 정보 수신 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_04', title: '제3자 정보 제공 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_06', title: 'KT 계열사 정보 제공 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_07', title: '광고성 정보 수신 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_10', title: '개인위치정보 수집·이용 동의(선택)', required: false },
          { code: 'CLAUSE_INFO_01', title: '기타 안내 사항 확인', required: true },
        ]

  // 필수 약관이 목록에 없으면 수동 추가 (미성년자/제휴 등)
  const mandatoryAdditions = [
    { code: 'CLAUSE_REQUIRED_06', title: '네트워크 차단 동의', required: true },
    { code: 'CLAUSE_REQUIRED_07', title: '앱 차단 동의', required: true },
    { code: 'CLAUSE_PARTNER_02', required: true },
    { code: 'CLAUSE_PARTNER_01', required: true },
  ]
  mandatoryAdditions.forEach((mandatory) => {
    if (!baseList.some((item) => item.code === mandatory.code)) {
      baseList.push(mandatory)
    }
  })

  return baseList.map((item) => {
    const key = saveKeyMap[item.code] || item.code
    return { ...item, id: key }
  })
})

const filteredTermsDataList = computed(() => {
  return termsDataList.value.filter((term) => {
    const termId = term.id
    if (termId === 'clauseMoveCode' && formData.value.joinType !== 'MNP3') return false
    const isMinor = ['NM', 'FM'].includes(formData.value.cstmrTypeCd)
    if (
      ['clauseSensiCoverageYn', 'clauseSensiOfferYn', 'nwBlckAgrmYn', 'appBlckAgrmYn'].includes(
        termId,
      ) &&
      !isMinor
    )
      return false
    if (termId === 'clauseJehuYn' && !productData.value.jehuPartnerTypeCd) return false
    if (termId === 'clausePartnerOfferYn' && !productData.value.jehuPartnerTypeCd) return false
    if (
      ['combineSoloTypeYn', 'combineSoloYn'].includes(termId) &&
      !(productData.value.addonService || []).includes('SOLO_COMB')
    )
      return false
    if (termId === 'moveRefundAgreeYn' && formData.value.joinType !== 'MNP3') return false
    return true
  })
})

const dynamicSpecTerms = computed(() => {
  const list = []

  // 번호이동(MNP3)인 경우에만 번호이동 관련 약관 추가
  if (formData.value.joinType === 'MNP3') {
    list.push({ code: 'CLAUSE_MOVE_01' })
  }

  // 요금제 구분값 혹은 dataType이 '5G'인 경우에만 5G 관련 약관 추가 (명의변경 최신 정합성 전파)
  const is5G =
    formData.value.prdtSctnCd === '5G' ||
    formData.value.dataType === '5G' ||
    productData.value.prdtSctnCd === '5G' ||
    productData.value.dataType === '5G'

  if (is5G) {
    list.push({ code: 'CLAUSE_REQUIRED_5G' })
  }

  if (productData.value.jehuPartnerTypeCd) {
    list.push({
      code: 'CLAUSE_PARTNER_02',
      specType: '02',
      specCode: productData.value.jehuPartnerTypeCd,
      specName: productData.value.jehuPartnerTypeNm,
    })
  }

  const isMinor = ['NM', 'FM'].includes(formData.value.cstmrTypeCd)
  if (isMinor) {
    list.push({ code: 'CLAUSE_REQUIRED_06' })
    list.push({ code: 'CLAUSE_REQUIRED_07' })
  }

  return list
})

const emit = defineEmits(['complete'])

const productJoinTypeRef = ref(null)
const customerTypeRef = ref(null)
const identityVerifyRef = ref(null)
const subscriberInfoRef = ref(null)
const legalAgentInfoRef = ref(null)
const realUserInfoRef = ref(null)
const delegateInfoRef = ref(null)
const contactInfoRef = ref(null)
const devicePlanInfoRef = ref(null)
const termsAgreementRef = ref(null)

const isComplete = ref(false)

const resetAfterCustomerTypeChange = () => {
  // 스토어 내부 watch에서 기본적인 가입자 데이터는 초기화됨
  // 여기서는 UI 상태 및 인증 플래그, 하위 단계 데이터들을 추가로 리셋
  isComplete.value = false
  if (formData.value) {
    formData.value.eligibilityStatus = 'none'
    formData.value.isEligible = false
  }

  store.authFlags.identityCertTypeCd = false
  store.authFlags.deviceChgTel = false
  store.authFlags.repPhone = false
  store.authFlags.requiredDocs = false

  eligibilityCheckRef.value?.reset?.()
  termsAgreementRef.value?.reset?.()
  checkRequiredFields()
}

const eligibilityCheckRef = ref(null)

const customerSsn = computed(() => {
  const n1 =
    formData.value.cstmrNativeRrn1 ||
    formData.value.cstmrForeignerRrn1 ||
    formData.value.cstmrJuridicalRrn1 ||
    ''
  const n2 =
    formData.value.cstmrNativeRrn2 ||
    formData.value.cstmrForeignerRrn2 ||
    formData.value.cstmrJuridicalRrn2 ||
    ''
  return n1 + n2
})

const onEligibilityChecked = async ({ status, isEligible: eligible }) => {
  if (formData.value) {
    formData.value.eligibilityStatus = status
    formData.value.isEligible = eligible
  }
  checkRequiredFields()

  // 가입조건 조회 성공하고 신규가입(NAC3)인 경우 항상 실행
  if (status === 'checked' && eligible && formData.value.joinType === 'NAC3') {
    const isBulk =
      formData.value.canBulkCorporateOpenYn === 'Y' && formData.value.cstmrTypeCd === 'JP'
    if (isBulk) {
      // 법인 대량개통은 가입조건조회 성공 시 수동으로 preChecked = true 세팅하여 유효성 통과 보장
      store.preChecked = true
    }
    await store.apiSaveDraft(1, { requestPreCheck: 'Y' })
  }
}

const checkBeforeEligibility = () => {
  // NAC3 (신규가입)인 경우에는 다음 버튼에서 진행하는 모든 상세 입력값 유효성 검증(validateWithAlert)을 가입조건조회 전에도 적용!
  if (formData.value.joinType === 'NAC3') {
    if (!validateWithAlert(true)) {
      return false
    }
    // } else {
    //   // 기존 다른 유형(예: 번호이동 등)일 때의 최소한의 기본 유효성 검증
    //   if (identityVerifyRef.value && !identityVerifyRef.value.validate(true)) {
    //     return false
    //   }
    //   if (!termsAgreementRef.value.validate()) {
    //     showAlert('필수약관에 모두 동의해 주세요.')
    //     return false
    //   }
  }
  return checkValidation(true)
}

// 선택 입력항목 검증 테스트
// 스코프 미지정시
// const { validateOptionalWithAlert } = useOptionalValidationAlert({
//   alertId: 'new-change-customer-optional-validation',
// })

// 스코프 지정시
const optionalScope = 'new-change-customer'
const { validateOptionalWithAlert } = useOptionalValidationAlert({
  scope: optionalScope,
  alertId: `${optionalScope}-optional-validation`,
  rules: [
    {
      key: 'new-change-customer-tel',
      label: '전화번호',
      // focusTarget: 'inp-telNo1', // 단일필드용
      focusTargets: ['inp-telNo1', 'inp-telNo2', 'inp-telNo3'],
      values: () => [formData.value.telNo1, formData.value.telNo2, formData.value.telNo3],
      lengths: [[2, 3], [3, 4], 4],
      message: '전화번호를 정확히 입력해주세요.',
    },
    {
      key: 'new-change-customer-biz',
      label: '사업자등록번호',
      focusTargets: ['inp-bizNo1', 'inp-bizNo2', 'inp-bizNo3'],
      values: () => [
        formData.value.cstmrJuridicalBizNo1,
        formData.value.cstmrJuridicalBizNo2,
        formData.value.cstmrJuridicalBizNo3,
      ],
      lengths: [3, 2, 5],
      message: '사업자등록번호를 정확히 입력해주세요.',
    },
  ],
})

// 가입자 정보 변경 시 가입조건 검사 결과 초기화
watch(
  [
    () => formData.value.cstmrTypeCd,
    customerSsn,
    () => formData.value.bulkActivationCnt,
    () => formData.value.agentCd,
  ],
  () => {
    // 1단계가 이미 완료(isSaved)되었거나 2단계 이후 상태이면 가입조건 결과를 리셋하지 않는다.
    if (formData.value && formData.value.isSaved) {
      return
    }
    if (formData.value) {
      formData.value.eligibilityStatus = 'none'
      formData.value.isEligible = false
    }
    checkRequiredFields()
  },
)

const validate = (isBeforeEligibility = false) => {
  const check = (name, refObj) => {
    const component = refObj.value
    if (!component) return undefined
    return component.validate()
  }

  const validations = [
    check('productJoinType', productJoinTypeRef),
    check('customerType', customerTypeRef),
    check('identityVerify', identityVerifyRef),
    check('subscriberInfo', subscriberInfoRef),
    check('legalAgentInfo', legalAgentInfoRef),
    check('realUserInfo', realUserInfoRef),
    check('delegateInfo', delegateInfoRef),
    check('devicePlanInfo', devicePlanInfoRef),
    check('termsAgreement', termsAgreementRef),
    formData.value.joinType === 'HDN3' ||
      formData.value.joinType === 'HCN3' ||
      store.preChecked ||
      isBeforeEligibility ||
      (formData.value.eligibilityStatus === 'checked' && formData.value.isEligible),
  ]

  const activeValidations = validations.filter((v) => v !== undefined)
  return activeValidations.length > 0 && activeValidations.every((v) => v === true)
}

const getPendingItems = (isBeforeEligibility = false) => {
  const pending = []
  const check = (name, refObj, label, focusFieldId) => {
    const component = refObj.value
    if (!component) return true
    if (typeof component.validate !== 'function') return true
    if (!component.validate()) {
      pending.push([label, focusFieldId])
      return false
    }
    return true
  }

  check('productJoinType', productJoinTypeRef, '가입유형을')
  check('customerType', customerTypeRef, '고객유형을')
  check('identityVerify', identityVerifyRef, '본인인증 정보를')
  check('subscriberInfo', subscriberInfoRef, '가입자 정보를', '#inp-cstmrNm')
  check('legalAgentInfo', legalAgentInfoRef, '법정대리인 정보를', '[name=base-rep-gender]')
  check('realUserInfo', realUserInfoRef, '실사용자 정보를')
  check('delegateInfo', delegateInfoRef, '대리인 정보를')

  check('devicePlanInfo', devicePlanInfoRef, '휴대폰 및 요금제 정보를')
  check('termsAgreement', termsAgreementRef, '약관동의 정보를')

  if (formData.value.joinType !== 'HDN3' && formData.value.joinType !== 'HCN3') {
    // 사전체크(preCheckYn Y 또는 store.preChecked)가 이미 완료된 상태면 더 이상 요구하지 않음
    if (formData.value.preCheckYn !== 'Y' && !store.preChecked) {
      if (formData.value.joinType === 'NAC3' && !isBeforeEligibility) {
        // 신규가입(NAC3)의 경우 가입조건조회 성공 및 사전체크 통과가 모두 완료되어야 함
        if (formData.value.eligibilityStatus !== 'checked' || !formData.value.isEligible) {
          pending.push(['가입조건 조회 및 사전체크를'])
        }
      } else if (formData.value.joinType !== 'NAC3' && !isBeforeEligibility) {
        // 번호이동(MNP3) 등의 경우 기존 가입조건조회 성공 여부만 확인
        if (formData.value.eligibilityStatus !== 'checked' || !formData.value.isEligible) {
          pending.push(['가입조건 조회를'])
        }
      }
    }
  }

  return pending
}

const checkRequiredFields = (result) => {
  // 약관 동의 결과가 배열로 전달된 경우, 스토어 필드들에 반영
  if (result && Array.isArray(result)) {
    result.forEach((item) => {
      // 1. item.id가 있으면 우선 사용 (termsDataList에서 mapping된 값)
      // 2. item.code가 있으면 saveKeyMap을 통해 mapping 시도
      const storeKey = item.id || saveKeyMap[item.code] || item.code
      if (Object.prototype.hasOwnProperty.call(formData.value, storeKey)) {
        formData.value[storeKey] = item.checked ? 'Y' : 'N'
      }
    })
  }

  const pending = getPendingItems()
  const isReady = pending.length === 0
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
  () => [
    formData.value,
    productData.value,
    store.authFlags,
    formData.value.eligibilityStatus,
    formData.value.isEligible,
  ],
  () => {
    checkRequiredFieldsDebounced()
  },
  { deep: true },
)

// 고객유형 법인(JP) & 방문유형 대리인(VDP)인 경우 실사용자 성별 디폴트 '남'('M')으로 세팅
watch(
  () => [formData.value.cstmrTypeCd, formData.value.cstmrVisitTypeCd],
  ([cstmrTypeCd, cstmrVisitTypeCd]) => {
    if (cstmrTypeCd === 'JP' && cstmrVisitTypeCd === 'VDP') {
      if (!formData.value.userGender) {
        formData.value.userGender = 'M'
      }
    }
  },
  { immediate: true },
)

onMounted(async () => {
  if (formData.value) {
    formData.value.eligibilityStatus = formData.value.eligibilityStatus || 'none'
    formData.value.isEligible =
      formData.value.isEligible !== undefined ? formData.value.isEligible : false
  }
  const res = await getCommonCodeList(['FORMGROUP', 'FORMINFO', 'FORMREQUIRED', 'FORMSELECT'])
  const combinedList = [
    ...(res?.FORMGROUP || []),
    ...(res?.FORMINFO || []),
    ...(res?.FORMREQUIRED || []),
    ...(res?.FORMSELECT || []),
  ]
  rawTermsList.value = (
    combinedList.length > 0 ? combinedList : await getCommonCodeList('CLAUSE_FORM_01')
  ).map((item) => ({ ...item, label: item.title, value: item.code }))
  store.validateCustomer = validate
  store.validateCustomerWithAlert = validateWithAlert
  checkRequiredFields()
})

onUnmounted(() => {
  store.resetAll(true)
})

const data = async (code) => {
  return await store.initForm(code)
}

// 가입자 만 나이 계산 헬퍼 (생일 기준)
const getAge = (birthStr, rrn2 = '') => {
  if (!birthStr || birthStr.length < 6) return 0

  let yearPrefix = '19'
  const genderDigit = rrn2 && rrn2.length > 0 ? rrn2.charAt(0) : ''
  if (['1', '2', '5', '6'].includes(genderDigit)) {
    yearPrefix = '19'
  } else if (['3', '4', '7', '8'].includes(genderDigit)) {
    yearPrefix = '20'
  } else {
    const yy = Number(birthStr.substring(0, 2))
    yearPrefix = yy > 50 ? '19' : '20'
  }

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

const save = async () => {
  if (!validateWithAlert()) return false
  return await store.apiSaveDraft(1)
}

const checkRrn = () => {
  const cstmrType = formData.value.cstmrTypeCd || ''

  // 개인 및 미성년자(내/외국인)인 경우에만 생년월일, 주민번호 유효성, 나이 제한 검증 진행
  if (['NA', 'NM', 'FN', 'FM'].includes(cstmrType)) {
    const birth = formData.value.cstmrNativeRrn1 || formData.value.cstmrForeignerRrn1 || ''
    const rrn2 = formData.value.cstmrNativeRrn2 || formData.value.cstmrForeignerRrn2 || ''

    if (!birth || birth.length !== 6) {
      showAlert('가입자 생년월일(주민등록번호 앞자리)을 입력해 주세요.')
      return false
    }

    const genderDigit = rrn2 ? rrn2.charAt(0) : ''

    // 1. 내국인 / 외국인 고객유형별 뒷 첫자리 유효성 검증
    if (['NA', 'NM'].includes(cstmrType)) {
      // 내국인인 경우 뒷 첫자리는 1, 2, 3, 4 중 하나여야 함
      if (genderDigit && !['1', '2', '3', '4'].includes(genderDigit)) {
        showAlert('신청자의 주민등록번호를 다시 확인해주세요.(성인)')
        return false
      }
      // 미성년자(NM)인 경우 무조건 2000년대생이므로 뒷 첫자리는 3, 4여야 함
      if (cstmrType === 'NM' && genderDigit && !['3', '4'].includes(genderDigit)) {
        showAlert('신청자의 주민등록번호를 다시 확인해주세요.(미성년자)')
        return false
      }
    } else if (['FN', 'FM'].includes(cstmrType)) {
      // 외국인인 경우 뒷 첫자리는 5, 6, 7, 8 중 하나여야 함
      if (genderDigit && !['5', '6', '7', '8'].includes(genderDigit)) {
        showAlert('신청자의 외국인등록번호를 다시 확인해주세요.(외국인성인)')
        return false
      }
      // 외국인 미성년자(FM)인 경우 무조건 2000년대생이므로 뒷 첫자리는 7, 8이어야 함
      if (cstmrType === 'FM' && genderDigit && !['7', '8'].includes(genderDigit)) {
        showAlert('신청자의 외국인등록번호를 다시 확인해주세요.(외국인미성년자)')
        return false
      }
    }

    const age = getAge(birth, rrn2)

    // 2. 가입자 성인 / 미성년자 나이 제한 체크
    if (['NM', 'FM'].includes(cstmrType)) {
      // 미성년자 고객유형 가입 나이 제한 체크 (만 19세 미만만 가입 가능)
      if (age >= 19) {
        showAlert(
          '미성년자 고객유형은 만 19세 미만만 가입 가능합니다. 만 19세 이상인 경우 성인 유형으로 가입해 주세요.',
        )
        return false
      }
    } else if (['NA', 'FN'].includes(cstmrType)) {
      // 성인 고객유형 가입 나이 제한 체크 (만 19세 이상만 가입 가능)
      if (age < 19) {
        showAlert(
          '내국인(성인)/외국인(성인) 고객유형은 만 19세 이상만 가입 가능합니다. 만 19세 미만인 경우 미성년자 유형으로 가입해 주세요.',
        )
        return false
      }
    }

    // 3. 법정대리인 나이 검증 (미성년자 가입 시)
    if (['NM', 'FM'].includes(cstmrType)) {
      const repBirth1 = formData.value.repRegistrationNo1 || formData.value.repForeignerNo1 || ''
      const repBirth2 = formData.value.repRegistrationNo2 || formData.value.repForeignerNo2 || ''

      if (repBirth1 && repBirth1.length === 6) {
        const repAge = getAge(repBirth1, repBirth2)
        if (repAge < 19) {
          showAlert('법정대리인은 만 19세 이상 성인만 등록 가능합니다.')
          return false
        }
      }
    }

    // 시니어 요금제 가입 나이 제한 체크 (만 65세 이상)
    const selectedPlanNm = formData.value.prodNm || formData.value.planNm || ''
    if (selectedPlanNm.includes('시니어')) {
      if (age < 65) {
        showAlert('시니어 요금제는 만 65세 이상만 가입 가능합니다.')
        return false
      }
    }

    // 청소년 요금제 가입 나이 제한 체크 (만 19세 미만)
    if (['청소년', '주니어', '키즈'].some((kword) => selectedPlanNm.includes(kword))) {
      if (age >= 19) {
        showAlert('청소년 요금제는 만 19세 미만만 가입 가능합니다.')
        return false
      }
    }
  }

  return true
}

const validateWithAlert = (isBeforeEligibility = false) => {
  // 가입자 휴대폰번호 필수 및 자릿수 정밀 체크
  const mNo1 = formData.value.mobileNo1 || ''
  const mNo2 = formData.value.mobileNo2 || ''
  const mNo3 = formData.value.mobileNo3 || ''

  if (!mNo1 || !mNo2 || !mNo3 || mNo1.length < 3 || mNo2.length < 3 || mNo3.length !== 4) {
    showAlert('가입자 휴대폰번호를 정확히 입력해 주세요.', () => {
      if (!mNo2) {
        focusField('#inp-mobileNo2')
      } else if (mNo3.length !== 4) {
        focusField('#inp-mobileNo3')
      }
    })
    return false
  }

  // 신분증 상세 필수 입력 검증 (발급일자, 면허번호/지역, 유공자번호 누락 방지)
  if (identityVerifyRef.value && !identityVerifyRef.value.validate(true)) {
    return false
  }

  if (!checkRrn()) {
    return false
  }

  // 법인/공공기관 대리인 방문개통인 경우 대리인 정보(신청인과의관계 포함) 상세 검증
  if (
    ['JP', 'GO'].includes(formData.value.cstmrTypeCd) &&
    formData.value.cstmrVisitTypeCd === 'VDP'
  ) {
    if (delegateInfoRef.value && !delegateInfoRef.value.checkValidation()) {
      return false
    }
  }

  const pending = getPendingItems(isBeforeEligibility)
  if (pending.length > 0) {
    showAlert(`${pending[0][0]} 입력해 주세요.`, () => {
      if (pending[0][1]) {
        focusField(pending[0][1])
      }
    })
    return false
  }

  // 선택 입력 검증 추가 (전화번호, 사업자등록번호 등 자릿수 누락 방지)
  if (!validateOptionalWithAlert()) {
    return false
  }

  return true
}

const resetStep = () => {
  checkRequiredFields()
}

const checkBeforeFaceAuth = () => {
  if (
    ['JP', 'GO'].includes(formData.value.cstmrTypeCd) &&
    formData.value.cstmrVisitTypeCd === 'VDP'
  ) {
    if (
      subscriberInfoRef.value?.checkBizNoValidation &&
      !subscriberInfoRef.value?.checkBizNoValidation()
    ) {
      console.log('subscriberInfoRef.value.checkBizNoValidation()', false)
      return false
    }

    if (delegateInfoRef.value?.checkNameAndBirth && !delegateInfoRef.value.checkNameAndBirth()) {
      console.log('delegateInfoRef.value.checkNameAndBirth()', false)
      return false
    }
  }

  return true
}

const checkValidation = (beforeNext) => {
  if (productJoinTypeRef.value?.checkValidation && !productJoinTypeRef.value.checkValidation()) {
    console.log('productJoinTypeRef.value.checkValidation()', false)
    return false
  }
  if (customerTypeRef.value?.checkValidation && !customerTypeRef.value.checkValidation()) {
    console.log('customerTypeRef.value.checkValidation()', false)
    return false
  }
  if (identityVerifyRef.value?.checkValidation && !identityVerifyRef.value.checkValidation()) {
    console.log('identityVerifyRef.value.checkValidation()', false)
    return false
  }
  if (subscriberInfoRef.value?.checkValidation && !subscriberInfoRef.value.checkValidation()) {
    console.log('subscriberInfoRef.value.checkValidation()', false)
    return false
  }
  if (legalAgentInfoRef.value?.checkValidation && !legalAgentInfoRef.value.checkValidation()) {
    console.log('legalAgentInfoRef.value.checkValidation()', false)
    return false
  }
  if (realUserInfoRef.value?.checkValidation && !realUserInfoRef.value.checkValidation()) {
    console.log('realUserInfoRef.value.checkValidation()', false)
    return false
  }
  if (delegateInfoRef.value?.checkValidation && !delegateInfoRef.value.checkValidation()) {
    console.log('delegateInfoRef.value.checkValidation()', false)
    return false
  }
  if (contactInfoRef.value?.checkValidation && !contactInfoRef.value.checkValidation()) {
    console.log('contactInfoRef.value.checkValidation()', false)
    return false
  }
  if (devicePlanInfoRef.value?.checkValidation && !devicePlanInfoRef.value.checkValidation()) {
    console.log('devicePlanInfoRef.value.checkValidation()', false)
    return false
  }
  if (termsAgreementRef.value?.checkValidation && !termsAgreementRef.value.checkValidation()) {
    console.log('termsAgreementRef.value.checkValidation()', false)
    return false
  }
  if (beforeNext) {
    return true
  } else {
    if (
      eligibilityCheckRef.value?.checkValidation &&
      !eligibilityCheckRef.value.checkValidation()
    ) {
      console.log('eligibilityCheckRef.value.checkValidation()', false)
      return false
    }
  }

  return true
}

defineExpose({
  data,
  save,
  validate,
  getPendingItems,
  validateWithAlert,
  resetStep,
  reset: () => store.resetStep(1),
  checkValidation,
})
</script>

<style lang="scss" scoped>
.page-step-panel {
  display: flex;
  flex-direction: column;
  height: auto;
  min-height: min-content;
  flex-shrink: 0;
}
</style>
