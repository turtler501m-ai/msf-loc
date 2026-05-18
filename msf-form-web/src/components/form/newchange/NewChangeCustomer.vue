<template>
  <div class="page-step-panel">
    <MsfProductJoinType ref="productJoinTypeRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfCustomerType ref="customerTypeRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfIdentityVerify ref="identityVerifyRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfSubscriberInfo
      ref="subscriberInfoRef"
      v-model="formData"
      :authFlags="store.authFlags"
      phoneLabel="기기변경<br/>휴대폰번호"
    />
    <MsfLegalAgentInfo
      ref="legalAgentInfoRef"
      v-model="formData"
      v-if="['NM', 'FM'].includes(formData.cstmrTypeCd)"
      :authFlags="store.authFlags"
    />
    <MsfRealUserInfo
      ref="realUserInfoRef"
      v-model="formData"
      v-if="['JP', 'GO'].includes(formData.cstmrTypeCd)"
    />
    <MsfDelegateInfo
      ref="delegateInfoRef"
      v-model="formData"
      v-if="['JP', 'GO'].includes(formData.cstmrTypeCd) && formData.cstmrVisitTypeCd === 'V2'"
    />
    <MsfRequiredDoc ref="requiredDocRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfContactInfo ref="contactInfoRef" v-model="formData" />

    <MsfDevicePlanInfo ref="devicePlanInfoRef" v-model="formData" :customerData="formData" />

    <MsfTermsAgreement
      ref="termsAgreementRef"
      v-model="formData"
      :termsData="filteredTermsDataList"
      :specTerms="dynamicSpecTerms"
      :isSaved="formData.isSaved"
      @checked="checkRequiredFields"
      required
    />

    <MsfButtonGroup margin="1">
      <MsfButton
        variant="toggle"
        :active="eligibilityStatus === 'checked'"
        @click="onClickCheckEligibility"
      >
        가입조건 조회
      </MsfButton>
    </MsfButtonGroup>

    <!-- 가입조건 조회 결과 컴포넌트 -->
    <MsfEligibilityResult :status="eligibilityStatus" :result="eligibilityResult" />
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from 'vue'
import { storeToRefs } from 'pinia'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { useMsfStepStore } from '@/stores/msf_step'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { useScrollLock } from '@/hooks/useScrollLock'
import { setLayoutLock } from '@/hooks/useGlobalScroll'
import { post } from '@/libs/api/msf.api'
import MsfEligibilityResult from '@/components/form/common/MsfEligibilityResult.vue'

const store = useMsfFormNewChgStore()
const stepStore = useMsfStepStore()

const { isLocked } = useScrollLock()
watch(
  isLocked,
  (newVal) => {
    setLayoutLock(newVal)
  },
  { immediate: true },
)

const { customer: formData, product: productData } = storeToRefs(store)
const rawTermsList = ref([])

const saveKeyMap = {
  CLAUSE_MOVE_01: 'clauseMoveCode',
  CLAUSE_REQUIRED_02: 'clauseEssCollectYn',
  CLAUSE_REQUIRED_01: 'clausePriCollectYn',
  CLAUSE_REQUIRED_03: 'clausePriOfferYn',
  CLAUSE_FATH_01: 'clauseFathFlag01',
  CLAUSE_FATH_02: 'clauseFathFlag02',
  CLAUSE_REQUIRED_06: 'nwBlckAgrmYn',
  CLAUSE_REQUIRED_07: 'appBlckAgrmYn',
  CLAUSE_REQUIRED_5G: 'clause5gCoverageYn',
  CLAUSE_PARTNER_01: 'clauseJehuYn',
  CLAUSE_PARTNER_02: 'clausePartnerOfferYn',
  CLAUSE_SELECT_03: 'personalInfoCollectAgreeYn',
  CLAUSE_SELECT_01: 'clausePriAdYn',
  CLAUSE_SELECT_08: 'othersAdReceiveAgreeYn',
  CLAUSE_SELECT_04: 'othersTrnsAgreeYn',
  CLAUSE_SELECT_06: 'othersTrnsKtAgreeYn',
  CLAUSE_SELECT_07: 'othersAdReceiveAgreeYn',
  CLAUSE_SELECT_10: 'personalLocationAgreeYn',
  CLAUSE_INFO_01: 'clauseInfo01',
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
          { code: 'CLAUSE_PARTNER_01', title: '제휴 서비스 이용 약관 동의', required: true },
          { code: 'CLAUSE_PARTNER_02', title: '제휴사 정보 제공 동의', required: true },
          { code: 'CLAUSE_SELECT_03', title: '개인정보 수집·이용 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_01', title: '광고성 정보 수신 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_08', title: '마케팅 정보 수신 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_04', title: '제3자 정보 제공 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_06', title: 'KT 계열사 정보 제공 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_07', title: '광고성 정보 수신 동의(선택)', required: false },
          { code: 'CLAUSE_SELECT_10', title: '개인위치정보 수집·이용 동의(선택)', required: false },
          { code: 'CLAUSE_INFO_01', title: '기타 안내 사항 확인', required: true },
        ]

  // 미성년자 필수 약관이 목록에 없으면 수동 추가
  const mandatoryMinorTerms = [
    { code: 'CLAUSE_REQUIRED_06', title: '네트워크 차단 동의', required: true },
    { code: 'CLAUSE_REQUIRED_07', title: '앱 차단 동의', required: true },
  ]
  mandatoryMinorTerms.forEach((mandatory) => {
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
    if (['clauseFathFlag01', 'clauseFathFlag02', 'nwBlckAgrmYn', 'appBlckAgrmYn'].includes(termId) && !isMinor) return false
    if (termId === 'clauseJehuYn' && !productData.value.jehuPartnerTypeCd) return false
    if (termId === 'clausePartnerOfferYn' && !formData.value.partnerEventCode) return false
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

  // 요금제 구분값이 '5G'인 경우에만 5G 관련 약관 추가
  if (formData.value.prdtSctnCd === '5G') {
    list.push({ code: 'CLAUSE_REQUIRED_5G' })
  }

  if (productData.value.jehuPartnerTypeCd) {
    list.push({
      code: 'CLAUSE_PARTNER_01',
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
const requiredDocRef = ref(null)
const contactInfoRef = ref(null)
const devicePlanInfoRef = ref(null)
const termsAgreementRef = ref(null)

const isComplete = ref(false)

const eligibilityStatus = ref('none')
const eligibilityResult = ref({
  historyOfCancellationResultMessage: '',
  historyOfCancellationYn: 'Y',
  installmentDiscountResultMessage: '',
  installmentDiscountYn: 'Y',
  subscriptionLimitResultMessage: '',
  subscriptionLimitYn: 'Y',
  subscriptionRestrictionsResultMessage: '',
  subscriptionRestrictionsYn: 'Y',
  unPaidResultMessage: '',
  unPaidYn: 'Y',
})
const isEligible = computed(() => {
  const r = eligibilityResult.value
  return (
    r.subscriptionRestrictionsYn === 'Y' &&
    r.subscriptionLimitYn === 'Y' &&
    r.unPaidYn === 'Y' &&
    r.historyOfCancellationYn === 'Y' &&
    r.installmentDiscountYn === 'Y'
  )
})

const onClickCheckEligibility = async () => {
  const n1 = formData.value.cstmrNativeRrn1 || formData.value.cstmrForeignerRrn1 || formData.value.cstmrJuridicalRrn1 || ''
  const n2 = formData.value.cstmrNativeRrn2 || formData.value.cstmrForeignerRrn2 || formData.value.cstmrJuridicalRrn2 || ''
  const ssn = n1 + n2

  if (!ssn || ssn.length < 10) {
    showAlert('가입자 정보를 먼저 완성해 주세요. (주민등록번호 등 식별번호 필요)')
    return
  }

  eligibilityStatus.value = 'checking'
  try {
    const res = await post('/api/form/eligibility/check', {
      cstmrTypeCd: formData.value.cstmrTypeCd,
      customerSsn: ssn,
    })

    if (res && res.code === '0000' && res.data) {
      eligibilityResult.value = res.data
      eligibilityStatus.value = 'checked'
    } else {
      throw new Error('Invalid response')
    }
  } catch (e) {
    console.error('Eligibility check failed:', e)
    showAlert('가입조건 조회 중 오류가 발생했습니다.')
    eligibilityStatus.value = 'none'
  }
}

const validate = () => {
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
    check('requiredDoc', requiredDocRef),
    check('contactInfo', contactInfoRef),
    check('devicePlanInfo', devicePlanInfoRef),
    check('termsAgreement', termsAgreementRef),
    eligibilityStatus.value === 'checked' && isEligible.value,
  ]

  const activeValidations = validations.filter((v) => v !== undefined)
  return activeValidations.length > 0 && activeValidations.every((v) => v === true)
}

const getPendingItems = () => {
  const pending = []
  const check = (name, refObj, label) => {
    const component = refObj.value
    if (!component) return true
    if (typeof component.validate !== 'function') return true
    if (!component.validate()) {
      pending.push(label)
      return false
    }
    return true
  }

  check('productJoinType', productJoinTypeRef, '가입유형')
  check('customerType', customerTypeRef, '고객유형')
  check('identityVerify', identityVerifyRef, '본인인증')
  check('subscriberInfo', subscriberInfoRef, '가입자 정보')
  check('legalAgentInfo', legalAgentInfoRef, '법정대리인')
  check('realUserInfo', realUserInfoRef, '실사용자')
  check('delegateInfo', delegateInfoRef, '대리인')
  check('requiredDoc', requiredDocRef, '구비서류')
  check('contactInfo', contactInfoRef, '연락처 정보')
  check('devicePlanInfo', devicePlanInfoRef, '단말기/요금제')
  check('termsAgreement', termsAgreementRef, '약관동의')

  if (eligibilityStatus.value !== 'checked' || !isEligible.value) {
    pending.push('가입조건 조회')
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
  () => [formData.value, productData.value, store.authFlags, eligibilityStatus.value, eligibilityResult.value],
  () => {
    checkRequiredFieldsDebounced()
  },
  { deep: true },
)

onMounted(async () => {
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
  checkRequiredFields()
})

const data = async (code) => {
  return await store.initForm(code)
}

const save = async () => {
  if (!validate()) return false
  return await store.apiSaveDraft(1)
}

defineExpose({ data, save, validate, getPendingItems, reset: store.resetAll })
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
