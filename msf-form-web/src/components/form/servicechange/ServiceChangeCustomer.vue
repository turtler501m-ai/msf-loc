<template>
  <div class="page-step-panel">
    <MsfLoadingComp v-if="isCustomerStepSaving" />
    <!-- 고객 유형 -->
    <MsfCustomerType
      v-model="formData"
      disable-agency-when-auth-locked
      agent-value-field="agentCd"
      @change-customer-type="resetAfterCustomerTypeChange"
    />
    <!-- // 고객 유형 -->
    <!-- 가입자 정보 -->
    <!-- 서비스변경/해지 전용 가입자정보 컴포넌트 -->
    <MsfSubscriberChgInfo v-model="formData" phoneLabel="변경 휴대폰번호" />
    <!-- // 가입자 정보 -->
    <!-- 법정대리인 정보 / 법정대리인 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo
      v-model="formData"
      :use-birth-date="true"
      show-birth-date-gender
      editable-basic-fields
      :external-auth-flags="store.authFlags"
      :reset-key="store.cancelAuthResetKey"
      lock-fields-on-auth
    />
    <!-- // 법정대리인 정보 / 법정대리인 안내사항 확인 및 동의 -->
    <!-- 대리인 위임 정보 -->
    <MsfDelegateInfo
      v-model="formData"
      v-if="formData.cstmrVisitTypeCd === 'VDP'"
      capture-relation-name
    />
    <MsfRequiredDoc
      ref="requiredDocRef"
      v-model="formData"
      v-model:authFlags="store.authFlags"
      :refresh-key="store.cancelAuthResetKey"
    />
    <!-- // 대리인 위임 정보 -->
    <!-- 가입자 연락처 -->
    <MsfContactInfo
      v-model="formData"
      :email-id-maxlength="100"
      :email-domain-maxlength="100"
      :detail-address-required="false"
      :show-tel-no="false"
      :show-address="false"
      :show-foreigner-info="false"
    />
    <!-- // 가입자 연락처 -->
    <!-- 서비스 변경 선택 -->
    <MsfServiceChangeSelection v-model="formData" />
    <!-- // 서비스 변경 선택 -->
  </div>
</template>

<script setup>
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { useMsfLoadingStore } from '@/stores/msf_loading'
import { showAlert } from '@/libs/utils/comp.utils'
import { validateBirthDate } from '@/libs/utils/date.utils'
import { storeToRefs } from 'pinia'
import { ref, watch, onMounted } from 'vue'
import { useMsfStepStore } from '@/stores/msf_step'

const emit = defineEmits(['complete'])

const stepStore = useMsfStepStore()
const store = useMsfFormSvcChgStore()
const loadingStore = useMsfLoadingStore()
const { formData } = storeToRefs(store)
const isComplete = ref(formData.value.serviceCheckYn === 'Y' ? 'true' : '')
const isCustomerStepSaving = ref(false)
const requiredDocRef = ref(null)

const resetAfterCustomerTypeChange = ({ newVal } = {}) => {
  const preserved = {
    cstmrTypeCd: newVal || formData.value.cstmrTypeCd,
    agentCd: formData.value.agentCd,
    ktOrgId: formData.value.ktOrgId,
    agentNm: formData.value.agentNm,
    shopCd: formData.value.shopCd,
    shopNm: formData.value.shopNm,
    realShopNm: formData.value.realShopNm,
    telephone: formData.value.telephone,
    representativeTelephone: formData.value.representativeTelephone,
    managerCd: formData.value.managerCd,
    managerNm: formData.value.managerNm,
    cpntId: formData.value.cpntId,
    cpntNm: formData.value.cpntNm,
    cntpntShopCd: formData.value.cntpntShopCd,
    cntpntShopNm: formData.value.cntpntShopNm,
  }
  Object.assign(formData.value, {
    ...preserved,
    customerTypeLocked: false,
    isVerified: false,
    isScanVerified: false,
    isSaved: false,
    cstmrNm: '',
    userBirthDate: '',
    userGender: 'M',
    cstmrForeignerRrn1: '',
    cstmrForeignerRrn2: '',
    cstmrJuridicalRrn1: '',
    cstmrJuridicalRrn2: '',
    cstmrJuridicalBizNo1: '',
    cstmrJuridicalBizNo2: '',
    cstmrJuridicalBizNo3: '',
    cstmrJuridicalRepNm: '',
    deviceChgTel1: '010',
    deviceChgTel2: '',
    deviceChgTel3: '',
    contractNum: '',
    ncn: '',
    custId: '',
    prvRateGrpNm: '',
    initActivationDate: '',
    lstComActvDate: '',
    addr: '',
    remindBlckYn: '',
    subStatus: '',
    payData: null,
    billData: null,
    repName: '',
    repBirthDate: '',
    repGender: 'M',
    repRelation: '',
    repPhone1: '',
    repPhone2: '',
    repPhone3: '',
    repPhoneAuth: '',
    repAgree: false,
    minorAgentNm: '',
    agentBirthDate: '',
    agentGender: '',
    minorAgentRelTypeCd: '',
    minorAgentRelTypeNm: '',
    minorAgentTelFnNo: '',
    minorAgentTelMnNo: '',
    minorAgentTelRnNo: '',
    mobileNo1: '010',
    mobileNo2: '',
    mobileNo3: '',
    telNo1: '',
    telNo2: '',
    telNo3: '',
    emailAddr1: '',
    emailAddr2: '',
    zipNo: '',
    address: '',
    detailAddress: '',
    country: '',
    visaType: '',
    cstmrForeignerCountryCd: '',
    cstmrForeignerNation: '',
    cstmrForeignerVisaNo: '',
    cstmrForeignerVdateStartDate: '',
    cstmrForeignerVdateEndDate: '',
    allCheck: '',
    serviceSelect: [],
    serviceCheckYn: 'N',
    serviceChecked: false,
    serviceSelectCompleteYn: 'N',
    serviceSelectCompleted: false,
    serviceSelectionLocked: false,
    serviceAreaLoadingTargets: [],
    additionList: [],
    additionCancelList: [],
    additionConfirmCompleted: false,
    additionInitialLoading: false,
    appConfirmCompleted: false,
    blockService: null,
    addonService: '',
    combinedService: '',
    loseLock: '',
    joinInfoChange: '',
    wirelessBlockConfirmCompleted: false,
    planName1: '',
    planName2: '',
    changeDate: '',
    reqWantFnNo: '',
    reqWantMnNo: '',
    reqWantRnNo: '',
    wishNo: '',
    wishNoc: '',
    wishMarket: '',
    numberChgConfirmCompleted: false,
    unLockPw: '',
    unpauseConfirmCompleted: false,
    planChangeConfirmCompleted: false,
    clauseInsuranceYn: '',
    recCat1: '',
    recCat2: '',
    reqBuyType: '',
    insuranceDeviceOs: '',
    insuranceAgree: false,
    insuranceConfirmCompleted: false,
    hasSim: '',
    simTypeCd: '',
    usimKindsCd: '',
    reqUsimSn: '',
    reqUsimConfirmCompleted: false,
    eid: '',
    imei1: '',
    imei2: '',
    simPurchaseMethod: '',
    shareUseState: '',
    sharePhoneNum: '',
    shareUsimNum: '',
    dataSharingSubscribed: false,
    dataSharingTargetNo: '',
    dataSharingAuthCompleted: false,
    dataSharingUsimCheckCompleted: false,
    dataSharingAvailableChecked: false,
    dataSharingAgreementCompleted: false,
    dataSharingConfirmCompleted: false,
    dataSharingMessage: '',
    dataSharingPlanName: '',
    soloData: '',
    combineSoloConfirmCompleted: false,
    termsAgreed: false,
    uploadedDocs: [],
    msfRequestDocList: [],
    memo: '',
  })
  Object.keys(store.authFlags || {}).forEach((key) => {
    store.authFlags[key] = false
  })
  store.cancelAuthResetKey++
  store.wishNoSearchCount = 0

  formData.value.parentScanId = stepStore.parentScanId
  isComplete.value = ''
  emit('complete', false)
}

const focusField = (target) => {
  setTimeout(() => {
    let element =
      target.startsWith('#') || target.startsWith('input') || target.startsWith('button')
        ? document.querySelector(target)
        : document.getElementById(target)

    if (element && !['INPUT', 'BUTTON', 'SELECT', 'TEXTAREA'].includes(element.tagName)) {
      element = element.querySelector('input, button, select, textarea') || element
    }

    element?.scrollIntoView({ block: 'center', behavior: 'smooth' })
    element?.focus()
  }, 0)
}

const getMissingPhoneTarget = (prefix) => {
  const f = formData.value
  if (!f[`${prefix}1`]) return `inp-${prefix}1`
  if (!f[`${prefix}2`]) return `inp-${prefix}2`
  return `inp-${prefix}3`
}

const getMissingAgentPhoneTarget = (f) => {
  if (!f.minorAgentTelFnNo) return 'inp-agentPhone1'
  if (!f.minorAgentTelMnNo) return 'inp-agentPhone2'
  return 'inp-agentPhone3'
}

const validateBirthDateWithAlert = (value, label, target) => {
  const result = validateBirthDate(String(value ?? ''))
  if (result.isValid) return true

  showAlert(`${label}을 확인해 주세요. ${result.msg}`, () => focusField(target))
  return false
}

const normalizeDigits = (value) => String(value || '').replace(/[^0-9]/g, '')

const getAge = (birthDate) => {
  const birth = normalizeDigits(birthDate)
  if (birth.length !== 8 || !validateBirthDate(birth).isValid) return null

  const yyyy = Number(birth.substring(0, 4))
  const mm = Number(birth.substring(4, 6))
  const dd = Number(birth.substring(6, 8))
  const today = new Date()
  const date = new Date(yyyy, mm - 1, dd)

  let age = today.getFullYear() - date.getFullYear()
  const monthDiff = today.getMonth() - date.getMonth()
  if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < date.getDate())) {
    age--
  }
  return age
}

const isAdultBirthDate = (birthDate) => {
  const age = getAge(birthDate)
  return age !== null && age >= 19
}

const isMinorBirthDate = (birthDate) => {
  const age = getAge(birthDate)
  return age !== null && age < 19
}

const validateAge = (f, withAlert = false, { checkLegalAgent = true } = {}) => {
  const customerBirthDate = normalizeDigits(f.userBirthDate)
  if (['NA', 'FN'].includes(f.cstmrTypeCd) && !isAdultBirthDate(customerBirthDate)) {
    if (withAlert) {
      showAlert(
        '생년월일이 만 19세 이상 성인이 아닙니다. 고객유형을 확인해 주세요.',
        () => focusField('inp-userBirthDate'),
      )
    }
    return false
  }

  if (['NM', 'FM'].includes(f.cstmrTypeCd)) {
    if (!isMinorBirthDate(customerBirthDate)) {
      if (withAlert) {
        showAlert(
          '생년월일이 만 19세 미만 미성년자가 아닙니다. 고객유형을 확인해 주세요.',
          () => focusField('inp-userBirthDate'),
        )
      }
      return false
    }

    if (!checkLegalAgent) return true

    if (!isAdultBirthDate(f.repBirthDate)) {
      if (withAlert) {
        showAlert('법정대리인은 만 19세 이상 성인만 등록 가능합니다.', () =>
          focusField('inp-repBirthDate'),
        )
      }
      return false
    }
  }

  return true
}

const validateWithAlert = () => {
  const f = formData.value

  if (!f.cstmrTypeCd) {
    showAlert('고객 유형을 선택해 주세요.', () => focusField('input[name="base-inp-customerType"]'))
    return false
  }
  if (['JP', 'GO'].includes(f.cstmrTypeCd) && !f.cstmrVisitTypeCd) {
    showAlert('방문 유형을 선택해 주세요.', () => focusField('input[name="base-inp-visitType"]'))
    return false
  }
  if (!f.cstmrNm) {
    showAlert('이름을 입력해 주세요.', () => focusField('inp-cstmrNm'))
    return false
  }
  if (['JP', 'GO'].includes(f.cstmrTypeCd)) {
    if (!f.cstmrJuridicalRrn1 || !f.cstmrJuridicalRrn2) {
      showAlert('법인등록번호를 입력해 주세요.', () => focusField('inp-cstmrJuridicalRrn1'))
      return false
    }
    if (!f.cstmrJuridicalRepNm) {
      showAlert('대표자명을 확인해 주세요.', () => focusField('inp-cstmrJuridicalRepNm'))
      return false
    }
  } else if (!f.userBirthDate) {
    showAlert('생년월일을 입력해 주세요.', () => focusField('inp-userBirthDate'))
    return false
  } else if (!validateBirthDateWithAlert(f.userBirthDate, '생년월일', 'inp-userBirthDate')) {
    return false
  }
  if (!validateAge(f, true, { checkLegalAgent: false })) return false
  if (!['JP', 'GO'].includes(f.cstmrTypeCd) && !f.userGender) {
    showAlert('성별을 선택해 주세요.', () => focusField('input[name="base-user-gender"]'))
    return false
  }
  if (!f.deviceChgTel1 || !f.deviceChgTel2 || !f.deviceChgTel3) {
    showAlert('변경 휴대폰번호를 입력해 주세요.', () =>
      focusField(getMissingPhoneTarget('deviceChgTel')),
    )
    return false
  }
  if (!store.authFlags?.deviceChgTel) {
    showAlert('변경 휴대폰번호 인증을 완료해 주세요.', () => focusField('btn-deviceChgTelAuth'))
    return false
  }
  if (['NM', 'FM'].includes(f.cstmrTypeCd)) {
    if (!f.repName) {
      showAlert('법정대리인 이름을 입력해 주세요.', () => focusField('inp-repName'))
      return false
    }
    if (!f.repBirthDate) {
      showAlert('법정대리인 생년월일을 입력해 주세요.', () => focusField('inp-repBirthDate'))
      return false
    }
    if (!validateBirthDateWithAlert(f.repBirthDate, '법정대리인 생년월일', 'inp-repBirthDate')) {
      return false
    }
    if (!f.repGender) {
      showAlert('법정대리인 성별을 선택해 주세요.', () =>
        focusField('input[name="basic-rep-gender"]'),
      )
      return false
    }
    if (!f.minorAgentRelTypeCd) {
      showAlert('신청인과의 관계를 선택해 주세요.', () => focusField('inp-repRelation'))
      return false
    }
    if (!f.minorAgentTelFnNo || !f.minorAgentTelMnNo || !f.minorAgentTelRnNo) {
      showAlert('법정대리인 연락처를 입력해 주세요.', () => focusField('inp-repPhone'))
      return false
    }
    if (!validateAge(f, true)) return false
    if (!store.authFlags?.repPhone) {
      showAlert('법정대리인 연락처 인증을 완료해 주세요.', () => focusField('inp-repPhone'))
      return false
    }
    if (!f.repAgree) {
      showAlert('법정대리인 안내사항 확인 및 동의가 필요합니다.')
      return false
    }
  }
  if (!requiredDocRef.value?.validate?.()) {
    showAlert('援щ퉬?쒕쪟瑜??뺤씤??二쇱꽭??')
    return false
  }
  if (f.cstmrVisitTypeCd === 'VDP') {
    if (!f.minorAgentNm) {
      showAlert('위임받은 고객 이름을 입력해 주세요.', () => focusField('inp-minorAgentNm'))
      return false
    }
    if (!f.agentBirthDate) {
      showAlert('위임받은 고객 생년월일을 입력해 주세요.', () => focusField('inp-agentBirthDate'))
      return false
    }
    if (
      !validateBirthDateWithAlert(f.agentBirthDate, '위임받은 고객 생년월일', 'inp-agentBirthDate')
    ) {
      return false
    }
    if (!f.minorAgentRelTypeCd) {
      showAlert('신청인과의 관계를 선택해 주세요.', () => focusField('inp-minorAgentRelTypeCd'))
      return false
    }
    if (!f.minorAgentTelFnNo || !f.minorAgentTelMnNo || !f.minorAgentTelRnNo) {
      showAlert('위임받은 고객 연락처를 입력해 주세요.', () =>
        focusField(getMissingAgentPhoneTarget(f)),
      )
      return false
    }
  }
  if (!f.mobileNo1 || !f.mobileNo2 || !f.mobileNo3) {
    showAlert('휴대폰번호를 입력해 주세요.', () => focusField(getMissingPhoneTarget('mobileNo')))
    return false
  }
  if (!(f.serviceSelect || []).length) {
    showAlert('서비스를 선택해 주세요.', () => focusField('input[name="inp-serviceSelect"]'))
    return false
  }
  if (f.serviceCheckYn !== 'Y') {
    showAlert('서비스 체크를 완료해 주세요.', () => focusField('input[name="inp-serviceSelect"]'))
    return false
  }
  if (!f.agentCd) {
    showAlert('대리점을 선택해 주세요.', () => focusField('.select-trigger'))
    return false
  }

  return true
}

watch(
  () => formData.value.serviceCheckYn,
  (newVal) => {
    const completed = newVal === 'Y'
    isComplete.value = completed ? 'true' : ''
    emit('complete', completed)
  },
  { immediate: true },
)

watch(
  () => isComplete.value,
  (newVal) => {
    emit('complete', newVal === 'true')
  },
)

const data = async (code /* 임시저장 코드 */) => {
  // 임시저장 정보 조회
  if (code) {
    // 임시저장된 데이터 조회 후, 임시저장단계코드 리턴
    // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
    return '1'
  }

  return '0' // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
}

const save = async () => {
  if (isCustomerStepSaving.value) return false

  if (!validateWithAlert()) {
    console.warn('[서비스변경][고객정보저장] 진행 중단', { reason: 'validate failed' })
    return false
  }

  isCustomerStepSaving.value = true

  try {
    formData.value.serviceSelectCompleteYn = 'Y'
    formData.value.serviceAreaLoadingTargets = Array.isArray(formData.value.serviceSelect)
      ? [...formData.value.serviceSelect]
      : []
    if (formData.value.serviceAreaLoadingTargets.length > 0) {
      loadingStore.showLoading()
    }
    if (formData.value.serviceSelect?.includes('R11')) {
      formData.value.additionInitialLoading = true
    }

    // 신청서키 사전 채번 (서비스해지 잔여요금 조회 시 채번 패턴과 동일)
    await store.apiGetRequestKey()

    emit('complete', true)

    console.debug('[ServiceChangeCustomer] service selection completed', {
      selected: formData.value.serviceSelect,
      serviceCheckYn: formData.value.serviceCheckYn,
      serviceSelectCompleteYn: formData.value.serviceSelectCompleteYn,
      requestKey: store.requestKey,
    })

    return true
  } finally {
    isCustomerStepSaving.value = false
  }
}

onMounted(() => {
  formData.value.parentScanId = stepStore.parentScanId
  store.validateCustomerWithAlert = validateWithAlert
  store.validateCustomerAgeWithAlert = () =>
    validateAge(formData.value, true, { checkLegalAgent: false })
})

defineExpose({ data, save })
</script>

<style scoped></style>
