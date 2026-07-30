<template>
  <div class="page-step-panel">
    <MsfLoadingComp v-if="isCustomerStepSaving" />
    <!-- 고객 유형 -->
    <MsfCustomerType
      v-model="formData"
      :disabled="isApplicationConfirmed"
      :visitTypeCodes="['JP', 'GO']"
      disable-agency-when-auth-locked
      agent-value-field="agentCd"
      @change-customer-type="resetAfterCustomerTypeChange"
    />
    <!-- 신분증 확인 -->
    <MsfIdentityVerify
      v-if="!['JP', 'GO'].includes(formData.cstmrTypeCd)"
      v-model="formData"
      :disabled="isLegalAgentAuthCompleted"
      :show-identity-cert-type="false"
      @scan-confirm="onIdentityScanConfirm"
      :return-file="true"
      file-type-cd="21"
      :show-identity-detail-fields="false"
      :use-scan-verified-for-input-lock="formData.cstmrTypeCd === 'NM'"
      restore-default-identity-type-on-customer-type-change
    />
    <!-- 가입자 정보 -->
    <!-- 서비스변경/해지 전용 가입자정보 컴포넌트 -->
    <MsfSubscriberChgInfo
      v-model="formData"
      phoneLabel="해지 휴대폰번호"
      :disabled="isApplicationConfirmed"
    />
    <!-- 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo
      v-model="formData"
      :use-birth-date="true"
      editable-basic-fields
      :external-auth-flags="terminationStore.authFlags"
      :reset-key="terminationStore.cancelAuthResetKey"
      lock-fields-on-auth
    />
    <!-- 대리인 위임정보 -->
    <MsfDelegateInfo
      v-model="formData"
      v-if="formData.cstmrVisitTypeCd === 'VDP'"
      capture-relation-name
    />
    <!-- 가입유형 선택 -->
    <MsfCustomerJoinType v-model="formData" :disabled="isApplicationConfirmed" />
    <!-- 해지 후 연락처 -->
    <MsfCancelPhoneNumber v-model="formData" />
    <!-- 약관 동의  -->
    <MsfTitleArea title="약관 동의" />
    <div id="termination-customer-agreement">
      <MsfAgreementGroup
        :key="agreementGroupKey"
        policy="CLAUSE_FORM_04"
        required
        :only-required="true"
        @checked="onAgreementChecked"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { storeToRefs } from 'pinia'
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
import { validateBirthDate } from '@/libs/utils/date.utils'

const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { formData } = storeToRefs(terminationStore)

const agreementGroupKey = ref(0)
const isAgreed = ref(false)
const isCustomerStepSaving = ref(false)
const isApplicationConfirmed = computed(() => terminationStore.applicationConfirmed)
const isLegalAgentAuthCompleted = computed(
  () =>
    ['NM', 'FM'].includes(formData.value.cstmrTypeCd) &&
    terminationStore.authFlags?.repPhone === true,
)

const isRequiredAgreement = (value) => value === true || value === 'Y' || value === '2'
const isCheckedAgreement = (value) => value === true || value === 'Y'

const resetAfterCustomerTypeChange = ({ newVal }) => {
  const preserved = {
    cstmrTypeCd: newVal,
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
  terminationStore.resetStep(0)
  terminationStore.resetStep(1)
  terminationStore.resetStep(2)
  Object.assign(formData.value, preserved)
  isAgreed.value = false
  agreementGroupKey.value += 1
  checkRequiredFields()
}

const focusField = (target) => {
  setTimeout(() => {
    const element = target.startsWith('#')
      ? document.querySelector(target)
      : document.getElementById(target)
    element?.scrollIntoView({ block: 'center', behavior: 'smooth' })
    element?.focus()
  }, 0)
}

// 스캔 결과의 주민/외국인번호는 API/테스트 데이터별로 rrn, rrn1+rrn2 등 형태가 다르다.
// 해지 고객정보 필드에 넣기 위해 앞자리, 뒷자리, YYYYMMDD 생년월일로 표준화한다.
const splitScanRrn = (value) => {
  const digits = (value || '').replace(/[^0-9]/g, '')
  if (digits.length < 6) return { rrn1: '', rrn2: '', birthDate: '' }

  if (digits.length >= 15) {
    const yyyyMMdd = digits.substring(0, 8)
    const rrn2 = digits.substring(8, 15)
    return {
      rrn1: yyyyMMdd.substring(2),
      rrn2,
      birthDate: yyyyMMdd,
    }
  }

  if (digits.length === 8) {
    return {
      rrn1: digits.substring(2),
      rrn2: '',
      birthDate: digits,
    }
  }

  const rrn1 = digits.substring(0, 6)
  const rrn2 = digits.length > 6 ? digits.substring(6, 13) : ''
  const genderDigit = rrn2.charAt(0)
  let birthDate = rrn1

  if (rrn1.length === 6 && genderDigit) {
    let century = ''
    if (['1', '2', '5', '6'].includes(genderDigit)) {
      century = '19'
    } else if (['3', '4', '7', '8'].includes(genderDigit)) {
      century = '20'
    }
    birthDate = century ? `${century}${rrn1}` : rrn1
  }

  return { rrn1, rrn2, birthDate }
}

// 실제 스캔 API와 테스트 샘플에서 내려오는 식별번호 필드명이 달라 우선순위로 흡수한다.
const resolveScanRrn = (data) =>
  data.rrn ||
  data.essNo ||
  data.identityEssNo ||
  data.knoteIdentityEssNo ||
  data.realCustIdntNo ||
  data.customerNo ||
  data.cstmrNativeRrn ||
  data.cstmrForeignerRrn ||
  `${data.rrn1 || ''}${data.rrn2 || ''}`

// 주민/외국인번호 뒷자리 첫 숫자로 해지 고객정보의 성별 코드를 계산한다.
const resolveScanGender = (rrn2) => {
  const genderDigit = (rrn2 || '').charAt(0)
  if (['1', '3', '5', '7'].includes(genderDigit)) return 'M'
  if (['2', '4', '6', '8'].includes(genderDigit)) return 'F'
  return ''
}

const normalizeDigits = (value) => String(value || '').replace(/[^0-9]/g, '')

const getBirthDateFromRrn = (rrn1, rrn2) => {
  const front = normalizeDigits(rrn1)
  const back = normalizeDigits(rrn2)
  if (front.length !== 6 || !back) return ''

  const genderDigit = back.charAt(0)
  let century
  if (['1', '2', '5', '6'].includes(genderDigit)) {
    century = '19'
  } else if (['3', '4', '7', '8'].includes(genderDigit)) {
    century = '20'
  } else {
    const yy = Number(front.substring(0, 2))
    century = yy > 50 ? '19' : '20'
  }
  return `${century}${front}`
}

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

const getCustomerBirthDate = (f) => {
  const rrnBirth =
    ['FN', 'FM'].includes(f.cstmrTypeCd)
      ? getBirthDateFromRrn(f.cstmrForeignerRrn1, f.cstmrForeignerRrn2)
      : getBirthDateFromRrn(f.cstmrNativeRrn1, f.cstmrNativeRrn2)
  return rrnBirth || normalizeDigits(f.userBirthDate)
}

const getLegalAgentBirthDate = (f) => {
  return (
    getBirthDateFromRrn(f.repRegistrationNo1, f.repRegistrationNo2) ||
    getBirthDateFromRrn(f.repForeignerNo1, f.repForeignerNo2) ||
    normalizeDigits(f.repBirthDate)
  )
}

const validateAge = (f, withAlert = false, { checkLegalAgent = true } = {}) => {
  const customerBirthDate = getCustomerBirthDate(f)
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

    const legalAgentBirthDate = getLegalAgentBirthDate(f)
    if (!isAdultBirthDate(legalAgentBirthDate)) {
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

// 해지는 신분증 인증유형 UI 없이 신분증 스캔 결과를 고객정보에 직접 반영한다.
// 미성년자는 법정대리인 필드에, 일반/법인 고객은 가입자 또는 대표자 필드에 세팅한다.
const onIdentityScanConfirm = (data) => {
  if (!data) return

  const f = formData.value
  const scanName =
    data.cstmrNm ||
    data.custNm ||
    data.knoteIdentityScanCstmrNm ||
    data.customerName ||
    data.userName ||
    data.name ||
    ''
  const { rrn1, rrn2, birthDate } = splitScanRrn(resolveScanRrn(data))
  const gender = resolveScanGender(rrn2)
  console.log('[TerminationCustomer][scanConfirm] input:', {
    scanSource: data.scanSource,
    isRealOcr: data.isRealOcr,
    data,
    scanName,
    rrn1,
    rrn2,
    birthDate,
    gender,
  })

  if (['NM', 'FM'].includes(f.cstmrTypeCd)) {
    if (scanName) {
      f.repName = scanName
      f.minorAgentNm = scanName
    }
    if (birthDate) {
      f.repBirthDate = birthDate
      f.minorAgentBirth = birthDate
    }
    if (rrn1 || rrn2) f.minorAgentRrn = `${rrn1}${rrn2}`
    if (gender) f.minorAgentGenderCd = gender
    if (f.cstmrTypeCd === 'FM') {
      if (rrn1) f.repForeignerNo1 = rrn1
      if (rrn2) f.repForeignerNo2 = rrn2
    } else {
      if (rrn1) f.repRegistrationNo1 = rrn1
      if (rrn2) f.repRegistrationNo2 = rrn2
    }
    console.log('[TerminationCustomer][scanConfirm] mapped minor formData:', {
      cstmrTypeCd: f.cstmrTypeCd,
      repName: f.repName,
      minorAgentNm: f.minorAgentNm,
      repBirthDate: f.repBirthDate,
      minorAgentBirth: f.minorAgentBirth,
      minorAgentRrn: f.minorAgentRrn,
      minorAgentGenderCd: f.minorAgentGenderCd,
      repRegistrationNo1: f.repRegistrationNo1,
      repRegistrationNo2: f.repRegistrationNo2,
      repForeignerNo1: f.repForeignerNo1,
      repForeignerNo2: f.repForeignerNo2,
      identityTypeCd: f.identityTypeCd,
      identityTypeNm: f.identityTypeNm,
      identityIssuDate: f.identityIssuDate,
      identityIssuRegion: f.identityIssuRegion,
      driveLicnsNo: f.driveLicnsNo,
    })
    return
  }

  if (scanName) {
    f.cstmrNm = scanName
    if (['JP', 'GO'].includes(f.cstmrTypeCd)) {
      f.cstmrJuridicalRepNm = scanName
    }
  }
  if (birthDate) f.userBirthDate = birthDate
  if (gender) f.userGender = gender

  if (['FN', 'FM'].includes(f.cstmrTypeCd)) {
    if (rrn1) f.cstmrForeignerRrn1 = rrn1
    if (rrn2) f.cstmrForeignerRrn2 = rrn2
    if (birthDate) f.cstmrForeignerBirth = birthDate
    if (gender) f.cstmrForeignerGenderCd = gender
  } else if (['NA', 'NM'].includes(f.cstmrTypeCd)) {
    if (rrn1) f.cstmrNativeRrn1 = rrn1
    if (rrn2) f.cstmrNativeRrn2 = rrn2
    if (birthDate) f.cstmrNativeBirth = birthDate
    if (gender) f.cstmrNativeGenderCd = gender
  }

  if (data.telNo) {
    const tel = data.telNo.replace(/[^0-9]/g, '')
    if (tel.length === 11) {
      f.deviceChgTel1 = tel.substring(0, 3)
      f.deviceChgTel2 = tel.substring(3, 7)
      f.deviceChgTel3 = tel.substring(7, 11)
    } else if (tel.length === 10) {
      f.deviceChgTel1 = tel.substring(0, 3)
      f.deviceChgTel2 = tel.substring(3, 6)
      f.deviceChgTel3 = tel.substring(6, 10)
    }
  }

  console.log('[TerminationCustomer][scanConfirm] mapped formData:', {
    cstmrTypeCd: f.cstmrTypeCd,
    cstmrNm: f.cstmrNm,
    userBirthDate: f.userBirthDate,
    userGender: f.userGender,
    cstmrNativeRrn1: f.cstmrNativeRrn1,
    cstmrNativeRrn2: f.cstmrNativeRrn2,
    cstmrForeignerRrn1: f.cstmrForeignerRrn1,
    cstmrForeignerRrn2: f.cstmrForeignerRrn2,
    repName: f.repName,
    minorAgentNm: f.minorAgentNm,
    minorAgentBirth: f.minorAgentBirth,
    minorAgentGenderCd: f.minorAgentGenderCd,
    identityTypeCd: f.identityTypeCd,
    identityTypeNm: f.identityTypeNm,
    identityIssuDate: f.identityIssuDate,
    identityIssuRegion: f.identityIssuRegion,
    driveLicnsNo: f.driveLicnsNo,
  })
}

const onAgreementChecked = (result) => {
  formData.value.clauseAgreements = Array.isArray(result)
    ? result.map((r) => ({
        code: r.code,
        required: r.required,
        checked: r.checked,
        version: r.version || r.ver || r.docVer || '',
      }))
    : []
  const items = Array.isArray(result) ? result : []
  const requiredItems = items.filter((r) => isRequiredAgreement(r.required))
  isAgreed.value =
    requiredItems.length > 0 && requiredItems.every((r) => isCheckedAgreement(r.checked))
  // console.log('[해지][약관체크]', {
  //   isAgreed: isAgreed.value,
  //   resultLen: items.length,
  //   requiredLen: requiredItems.length,
  //   requiredChecked: requiredItems.every((r) => isCheckedAgreement(r.checked)),
  //   items: items.map((r) => ({ code: r.code, required: r.required, checked: r.checked })),
  // })
  checkRequiredFields()
}

// 유효성 검사 (다음 버튼 활성화 여부용 — 기본 입력 + 약관동의 확인, alert 없음)
const validate = () => {
  const f = formData.value
  const isCorporate = ['JP', 'GO'].includes(f.cstmrTypeCd)
  const requiresDelegate = isCorporate && f.cstmrVisitTypeCd === 'VDP'
  if (!f.cstmrNm) return false
  if (isCorporate) {
    if (!f.cstmrJuridicalRrn1 || !f.cstmrJuridicalRrn2) return false
    if (!f.cstmrJuridicalRepNm) return false
  } else if (!f.userBirthDate) {
    return false
  }
  if (!validateAge(f)) return false
  if (requiresDelegate) {
    if (!f.minorAgentNm) return false
    if (!validateBirthDate(String(f.agentBirthDate || '')).isValid) return false
    if (!f.agentGender) return false
    if (!f.minorAgentRelTypeCd) return false
    if (!f.minorAgentTelFnNo || !f.minorAgentTelMnNo || !f.minorAgentTelRnNo) return false
  }
  if (!f.deviceChgTel1 || !f.deviceChgTel2 || !f.deviceChgTel3) return false
  if (!terminationStore.authFlags?.cancelPhone) return false
  if (!isAgreed.value) return false
  return true
}

const isCompleteEffective = computed(() => validate())

const joinDigits = (...values) =>
  values
    .map((value) => value || '')
    .join('')
    .replace(/[^0-9]/g, '')

const OSST_CONTACT_PHONE_MESSAGE =
  '고객 연락번호를 정확하게 입력하여 주시기 바랍니다. 중간번호가 0 혹은 1 로 시작하는 번호일 시 처리 불가합니다.'

const hasInvalidOsstContactMiddleNo = (phoneNo) => {
  const normalized = joinDigits(phoneNo)
  if (!/^010\d{8}$/.test(normalized)) return false
  return ['0', '1'].includes(normalized.substring(3, 4))
}

const isSameCancelAndAfterTel = (f) => {
  const cancelPhone = joinDigits(f.deviceChgTel1, f.deviceChgTel2, f.deviceChgTel3)
  const afterTel = joinDigits(f.afterTel1, f.afterTel2, f.afterTel3)
  return cancelPhone && afterTel && cancelPhone === afterTel
}

const confirmSameCancelAndAfterTel = (f) => {
  if (!isSameCancelAndAfterTel(f)) return Promise.resolve(true)

  return new Promise((resolve) => {
    showConfirm(
      '해지후 연락번호를 확인하세요. 14일 이내 번호이동개통취소 고객 외에는 해지 대상과 동일한 연락처 번호 입력 불가합니다.\n계속 진행하시겠습니까?',
      () => resolve(true),
      undefined,
      () => {
        focusField('inp-afterTel2')
        resolve(false)
      },
    )
  })
}

// 저장 전 필수항목 검사 (alert 포함)
const validateWithAlert = () => {
  const f = formData.value
  const isCorporate = ['JP', 'GO'].includes(f.cstmrTypeCd)
  const requiresDelegate = isCorporate && f.cstmrVisitTypeCd === 'VDP'
  console.log('[해지][고객정보검증]', {
    cstmrTypeCd: f.cstmrTypeCd,
    cstmrVisitTypeCd: f.cstmrVisitTypeCd,
    isCorporate,
    requiresDelegate,
    userBirthDate: f.userBirthDate,
    agentBirthDate: f.agentBirthDate,
  })
  // if (f.identityCertTypeCd !== 'S' && !f.isVerified) {
  //   showAlert('신분증 인증을 완료해 주세요.')
  //   return false
  // }
  if (!f.agentCd) {
    showAlert('대리점을 선택해 주세요.', () => focusField('.select-trigger'))
    return false
  }
  if (!f.cstmrNm) {
    showAlert('이름을 입력해 주세요.', () => focusField('inp-cstmrNm'))
    return false
  }
  if (isCorporate) {
    if (!f.cstmrJuridicalRrn1 || !f.cstmrJuridicalRrn2) {
      const target = !f.cstmrJuridicalRrn1 ? 'inp-cstmrJuridicalRrn1' : 'inp-cstmrJuridicalRrn2'
      showAlert('법인등록번호를 입력해 주세요.', () => focusField(target))
      return false
    }
    if (!f.cstmrJuridicalRepNm) {
      showAlert('대표자명을 확인해 주세요.', () => focusField('inp-cstmrJuridicalRepNm'))
      return false
    }
  } else if (!f.userBirthDate) {
    console.log('[해지][생년월일체크]', isCorporate, f.userBirthDate)
    showAlert('생년월일을 입력해 주세요.', () => focusField('inp-userBirthDate'))
    return false
  }
  if (!validateAge(f, true)) return false
  if (requiresDelegate) {
    if (!f.minorAgentNm) {
      showAlert('위임받은 고객 이름을 입력해 주세요.', () => focusField('inp-minorAgentNm'))
      return false
    }
    const birthDateResult = validateBirthDate(String(f.agentBirthDate || ''))
    if (!birthDateResult.isValid) {
      showAlert(`위임받은 고객 생년월일을 확인해 주세요. ${birthDateResult.msg}`, () =>
        focusField('inp-agentBirthDate'),
      )
      return false
    }
    if (!f.agentGender) {
      showAlert('위임받은 고객 성별을 선택해 주세요.', () =>
        focusField('input[name="agent-gender"]'),
      )
      return false
    }
    if (!f.minorAgentRelTypeCd) {
      showAlert('신청인과의 관계를 선택해 주세요.', () => focusField('inp-minorAgentRelTypeCd'))
      return false
    }
    if (!f.minorAgentTelFnNo || !f.minorAgentTelMnNo || !f.minorAgentTelRnNo) {
      const target = !f.minorAgentTelFnNo
        ? 'inp-agentPhone1'
        : !f.minorAgentTelMnNo
          ? 'inp-agentPhone2'
          : 'inp-agentPhone3'
      showAlert('위임받은 고객 연락처를 입력해 주세요.', () => focusField(target))
      return false
    }
  }
  if (!f.deviceChgTel1 || !f.deviceChgTel2 || !f.deviceChgTel3) {
    const target = !f.deviceChgTel1
      ? 'inp-deviceChgTel1'
      : !f.deviceChgTel2
        ? 'inp-deviceChgTel2'
        : 'inp-deviceChgTel3'
    showAlert('해지 휴대폰번호를 입력해 주세요.', () => focusField(target))
    return false
  }
  if (!terminationStore.authFlags?.cancelPhone) {
    showAlert('해지 휴대폰번호 인증을 완료해 주세요.', () => focusField('inp-deviceChgTel2'))
    return false
  }
  if (!f.afterTel1 || !f.afterTel2 || !f.afterTel3) {
    const target = !f.afterTel1 ? 'inp-afterTel1' : !f.afterTel2 ? 'inp-afterTel2' : 'inp-afterTel3'
    showAlert('해지 후 연락처를 입력해 주세요.', () => focusField(target))
    return false
  }
  if (hasInvalidOsstContactMiddleNo(joinDigits(f.afterTel1, f.afterTel2, f.afterTel3))) {
    showAlert(OSST_CONTACT_PHONE_MESSAGE, () => focusField('inp-afterTel2'))
    return false
  }
  if (['NM', 'FM'].includes(f.cstmrTypeCd) && !f.repAgree) {
    showAlert('법정대리인 안내사항 확인 및 동의가 필요합니다.')
    return false
  }
  if (!isAgreed.value) {
    showAlert('약관 동의가 필요합니다.', () =>
      focusField('#termination-customer-agreement input[type="checkbox"]'),
    )
    return false
  }
  return true
}

const checkRequiredFields = () => {
  const result = isCompleteEffective.value
  emit('complete', result)
  return result
}

watch(
  () => formData.value,
  () => {
    checkRequiredFields()
  },
  { deep: true },
)

watch(
  () => [
    terminationStore.authFlags?.cancelPhone,
    terminationStore.authFlags?.requiredDocs,
  ],
  () => {
    checkRequiredFields()
  },
)

watch(
  () => terminationStore.customerAgreementResetKey,
  () => {
    isAgreed.value = false
    agreementGroupKey.value += 1
    checkRequiredFields()
  },
)

onMounted(() => {
  checkRequiredFields()
  terminationStore.validateCustomerWithAlert = validateWithAlert
  terminationStore.validateCustomerAgeWithAlert = () =>
    validateAge(formData.value, true, { checkLegalAgent: false })
})

const data = async (code /* 임시저장 코드 */) => {
  if (code) return '1'
  return '0'
}

const save = async () => {
  if (isCustomerStepSaving.value) return false

  const stepForm = formData.value
  console.log('[해지][고객정보저장] 요청 시작', {
    ncn: stepForm?.ncn,
    contractNum: stepForm?.contractNum,
  })
  if (!validateWithAlert()) {
    console.warn('[해지][고객정보저장] 진행 중단', { reason: 'validate failed' })
    return false
  }
  if (!(await confirmSameCancelAndAfterTel(stepForm))) {
    console.warn('[해지][고객정보저장] 진행 중단', { reason: 'same cancel/after phone canceled' })
    return false
  }
  isCustomerStepSaving.value = true

  try {
    const ensuredNcn = await terminationStore.ensureTerminationNcn()
    console.log('[해지][고객정보저장][계약정보]', {
      ensuredNcn,
      ncn: stepForm?.ncn,
      contractNum: stepForm?.contractNum,
    })
    if (!stepForm?.ncn) {
      console.warn('[해지][고객정보저장] 진행 중단', {
        reason: 'ncn missing',
        ncn: stepForm?.ncn,
        contractNum: stepForm?.contractNum,
      })
      return false
    }
    await terminationStore.apiGetRequestKey()
    console.log('[해지][고객정보저장] 잔여요금조회 호출', { ncn: stepForm?.ncn })
    const x18Result = await terminationStore.apiGetRemainCharge()
    console.log('[해지][고객정보저장] 화면 데이터 반영 결과', {
      x18Result,
      remainChargeLoaded: stepForm?.remainChargeLoaded,
      itemCount: Array.isArray(stepForm?.remainChargeItems) ? stepForm.remainChargeItems.length : 0,
    })
    console.log('[해지][고객정보저장] 다음 단계 이동')
    return true
  } finally {
    isCustomerStepSaving.value = false
  }
}

const reset = async () => {
  terminationStore.resetStep(0)
  formData.value.clauseAgreements = []
  isAgreed.value = false
  agreementGroupKey.value += 1
  checkRequiredFields()
}

defineExpose({ data, save, validate, validateWithAlert, reset })
</script>

<style scoped></style>
