<template>
  <div class="page-step-panel">
    <!-- 고객 유형 -->
    <MsfCustomerType
      v-model="formData"
      :visitTypeCodes="['JP', 'GO']"
      disable-agency-when-auth-locked
      @change-customer-type="resetAfterCustomerTypeChange"
    />
    <!-- 신분증 확인 -->
    <MsfIdentityVerify
      v-model="formData"
      :show-identity-cert-type="false"
      @scan-confirm="onIdentityScanConfirm"
    />
    <!-- 가입자 정보 -->
    <!-- 서비스변경/해지 전용 가입자정보 컴포넌트 -->
    <MsfSubscriberChgInfo v-model="formData" phoneLabel="해지 휴대폰번호" />
    <!-- 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo v-model="formData" :use-birth-date="true" />
    <!-- 대리인 위임정보 -->
    <MsfDelegateInfo v-model="formData" v-if="formData.cstmrVisitTypeCd === 'V2'" />
    <!-- 구비서류 -->
    <MsfRequiredDoc v-model="formData" />
    <!-- 가입유형 선택 -->
    <MsfCustomerJoinType v-model="formData" />
    <!-- 해지 후 연락처 -->
    <MsfCancelPhoneNumber v-model="formData" />
    <!-- 약관 동의  -->
    <MsfTitleArea title="약관 동의" />
    <div id="termination-customer-agreement">
      <MsfAgreementGroup
        policy="CLAUSE_FORM_04"
        ref="agreementRef"
        required
        :only-required="true"
        @checked="onAgreementChecked"
      />
    </div>

    <!-- (화면테스트용 테스트영역) 추후 지워질수도 있는것-->
    <div class="ut-mt-50">
      <div>
        <p>- 개발자주석 부분- 화면 프로세스</p>
        <select v-model="isCompleteOverride">
          <option value="">고객 정보</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 테스트영역) 추후 지워질수도 있는것-->
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { storeToRefs } from 'pinia'
import { showAlert } from '@/libs/utils/comp.utils'

const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { formData } = storeToRefs(terminationStore)

const agreementRef = ref(null)
const isAgreed = ref(false)
const isCompleteOverride = ref('')

const isRequiredAgreement = (value) => value === true || value === 'Y' || value === '2'
const isCheckedAgreement = (value) => value === true || value === 'Y'

const resetAfterCustomerTypeChange = ({ newVal }) => {
  const preserved = {
    cstmrTypeCd: newVal,
    agency: formData.value.agency,
    agentCd: formData.value.agentCd,
    managerCd: formData.value.managerCd,
  }
  terminationStore.resetStep(0)
  terminationStore.resetStep(1)
  terminationStore.resetStep(2)
  Object.assign(formData.value, preserved)
  isAgreed.value = false
  isCompleteOverride.value = ''
  agreementRef.value?.reset?.()
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
  `${data.rrn1 || ''}${data.rrn2 || ''}`

// 주민/외국인번호 뒷자리 첫 숫자로 해지 고객정보의 성별 코드를 계산한다.
const resolveScanGender = (rrn2) => {
  const genderDigit = (rrn2 || '').charAt(0)
  if (['1', '3', '5', '7'].includes(genderDigit)) return 'M'
  if (['2', '4', '6', '8'].includes(genderDigit)) return 'F'
  return ''
}

// 해지는 신분증 인증유형 UI 없이 신분증 스캔 결과를 고객정보에 직접 반영한다.
// 미성년자는 법정대리인 필드에, 일반/법인 고객은 가입자 또는 대표자 필드에 세팅한다.
const onIdentityScanConfirm = (data) => {
  if (!data) return

  const f = formData.value
  const scanName = data.cstmrNm || data.customerName || data.userName || data.name || ''
  const { rrn1, rrn2, birthDate } = splitScanRrn(resolveScanRrn(data))
  const gender = resolveScanGender(rrn2)

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
  console.log('[해지][약관체크]', {
    isAgreed: isAgreed.value,
    resultLen: items.length,
    requiredLen: requiredItems.length,
    requiredChecked: requiredItems.every((r) => isCheckedAgreement(r.checked)),
    items: items.map((r) => ({ code: r.code, required: r.required, checked: r.checked })),
  })
  checkRequiredFields()
}

// 유효성 검사 (다음 버튼 활성화 여부용 — 기본 입력 + 약관동의 확인, alert 없음)
const validate = () => {
  const f = formData.value
  const isCorporate = ['JP', 'GO'].includes(f.cstmrTypeCd)
  if (!f.cstmrNm) return false
  if (isCorporate) {
    if (!f.cstmrJuridicalRrn1 || !f.cstmrJuridicalRrn2) return false
    if (!f.cstmrJuridicalRepNm) return false
  } else if (!f.userBirthDate) {
    return false
  }
  if (!f.deviceChgTel1 || !f.deviceChgTel2 || !f.deviceChgTel3) return false
  if (!isAgreed.value) return false
  return true
}

const isCompleteEffective = computed(() => {
  if (isCompleteOverride.value === 'true') return true
  if (isCompleteOverride.value === 'false') return false
  return validate()
})

// 저장 전 필수항목 검사 (alert 포함)
const validateWithAlert = () => {
  const f = formData.value
  const isCorporate = ['JP', 'GO'].includes(f.cstmrTypeCd)
  // if (f.identityCertTypeCd !== 'S' && !f.isVerified) {
  //   showAlert('신분증 인증을 완료해 주세요.')
  //   return false
  // }
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
    showAlert('생년월일을 입력해 주세요.', () => focusField('inp-userBirthDate'))
    return false
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
  () => { checkRequiredFields() },
  { deep: true },
)

watch(isCompleteOverride, () => { checkRequiredFields() })

onMounted(() => {
  checkRequiredFields()
  terminationStore.validateCustomer = validate
})

const data = async (code /* 임시저장 코드 */) => {
  if (code) return '1'
  return '0'
}

const save = async () => {
  if (isCompleteOverride.value === 'false') return false
  const stepForm = formData.value
  console.log('[해지][고객정보저장] 요청 시작', {
    ncn: stepForm?.ncn,
    contractNum: stepForm?.contractNum,
  })
  if (!validateWithAlert()) {
    console.warn('[해지][고객정보저장] 진행 중단', { reason: 'validate failed' })
    return false
  }
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
  console.log('[해지][고객정보저장] 잔여요금조회 호출', { ncn: stepForm?.ncn })
  const x18Result = await terminationStore.apiGetRemainCharge()
  console.log('[해지][고객정보저장] 화면 데이터 반영 결과', {
    x18Result,
    remainChargeLoaded: stepForm?.remainChargeLoaded,
    itemCount: Array.isArray(stepForm?.remainChargeItems) ? stepForm.remainChargeItems.length : 0,
  })
  console.log('[해지][고객정보저장] 다음 단계 이동')
  return true
}

const reset = async () => {
  terminationStore.resetStep(0)
  checkRequiredFields()
}

defineExpose({ data, save, validate, validateWithAlert, reset })
</script>

<style scoped></style>
