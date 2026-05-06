<template>
  <div class="page-step-panel">
    <!-- 고객 유형 -->
    <MsfCustomerType v-model="formData" :visitTypeCodes="['JP']" />
    <!-- // 고객 유형 -->
    <!-- 신분증 확인 -->
    <MsfIdentityVerify v-model="formData" />
    <!-- // 신분증 확인 -->
    <!-- 가입자 정보 -->
    <MsfSubcriberChgInfo v-model="formData" phoneLabel="해지 휴대폰번호" />
    <!-- // 가입자 정보 -->
    <!-- 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo v-model="formData" />
    <!-- // 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <!-- 대리인 위임정보 -->
    <MsfDelegateInfo v-model="formData" v-if="formData.cstmrVisitTypeCd === 'V2'" />
    <!-- // 대리인 위임정보 -->
    <!-- 구비서류 -->
    <MsfRequiredDoc v-model="formData" />
    <!-- // 구비서류 -->
    <!-- 해지 후 연락처 -->
    <MsfCancelPhoneNumber v-model="formData" />
    <!-- // 해지 후 연락처 -->
    <!-- 가입유형 선택 -->
    <MsfCustomerJoinType v-model="formData" />
    <!-- // 가입유형 선택 -->
    <!-- 약관 동의  -->
    <MsfTitleArea title="약관 동의" />
    <div id="termination-customer-agreement">
      <MsfAgreementGroup
        policy="CLAUSE_FORM_01"
        ref="agreementRef"
        required
        :only-required="true"
        @checked="onAgreementChecked"
      />
    </div>
    <!-- // 약관 동의 -->

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
import { ref, watch, onMounted } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { storeToRefs } from 'pinia'
import { showAlert } from '@/libs/utils/comp.utils'

const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { formData } = storeToRefs(terminationStore)

const agreementRef = ref(null)
const isComplete = ref(false)
const isAgreed = ref(false)

const isCompleteOverride = ref('')

const isRequiredAgreement = (value) => value === true || value === 'Y' || value === '2'
const isCheckedAgreement = (value) => value === true || value === 'Y'

const focusField = (target) => {
  setTimeout(() => {
    const element = target.startsWith('#')
      ? document.querySelector(target)
      : document.getElementById(target)
    element?.scrollIntoView({ block: 'center', behavior: 'smooth' })
    element?.focus()
  }, 0)
}

const onAgreementChecked = (result) => {
  const requiredItems = result.filter((r) => isRequiredAgreement(r.required))
  isAgreed.value =
    requiredItems.length > 0 && requiredItems.every((r) => isCheckedAgreement(r.checked))
  console.log('[해지][약관체크]', {
    isAgreed: isAgreed.value,
    resultLen: result.length,
    requiredLen: requiredItems.length,
    requiredChecked: requiredItems.every((r) => isCheckedAgreement(r.checked)),
    items: result.map((r) => ({ code: r.code, required: r.required, checked: r.checked })),
  })
  checkRequiredFields()
}

// 유효성 검사 (다음 버튼 활성화 여부용 — 기본 입력 + 약관동의 확인, alert 없음)
const validate = () => {
  const f = formData.value
  const checks = {
    cstmrNm: !!f.cstmrNm,
    userBirthDate: !!f.userBirthDate,
    phone: !!(f.deviceChgTel1 && f.deviceChgTel2 && f.deviceChgTel3),
    isAgreed: isAgreed.value,
  }
  const result = Object.values(checks).every(Boolean)
  console.log('[해지][validate]', { ...checks, result })
  if (!f.cstmrNm) return false
  if (!f.userBirthDate) return false
  if (!f.deviceChgTel1 || !f.deviceChgTel2 || !f.deviceChgTel3) return false
  if (!isAgreed.value) return false
  return true
}

// 저장 전 필수항목 검사 (alert 포함)
const validateWithAlert = () => {
  const f = formData.value
  // if (f.identityCertTypeCd !== 'S' && !f.isVerified) {
  //   showAlert('신분증 인증을 완료해 주세요.')
  //   return false
  // }
  if (!f.cstmrNm) {
    showAlert('이름을 입력해 주세요.', () => focusField('inp-cstmrNm'))
    return false
  }
  if (!f.userBirthDate) {
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
  if (isCompleteOverride.value === 'true') {
    emit('complete', true)
    return true
  }
  if (isCompleteOverride.value === 'false') {
    emit('complete', false)
    return false
  }
  const result = validate()
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
  const stepForm = formData.value
  console.log('[서비스해지][고객정보저장] 요청 시작', {
    isCompleteOverride: isCompleteOverride.value,
    ncn: stepForm?.ncn,
    contractNum: stepForm?.contractNum,
  })
  if (isCompleteOverride.value === 'false') {
    console.warn('[서비스해지][고객정보저장] 진행 중단', { reason: 'override false' })
    return false
  }
  if (!validateWithAlert()) {
    console.warn('[서비스해지][고객정보저장] 진행 중단', { reason: 'validate failed' })
    return false
  }
  const ensuredNcn = await terminationStore.ensureTerminationNcn()
  console.log('[서비스해지][고객정보저장][계약정보]', {
    ensuredNcn,
    ncn: stepForm?.ncn,
    contractNum: stepForm?.contractNum,
  })
  if (!stepForm?.ncn) {
    console.warn('[서비스해지][고객정보저장] 진행 중단', {
      reason: 'ncn missing',
      ncn: stepForm?.ncn,
      contractNum: stepForm?.contractNum,
    })
    return false
  }
  console.log('[서비스해지][고객정보저장] 잔여요금조회 호출', { ncn: stepForm?.ncn })
  const x18Result = await terminationStore.apiGetRemainCharge()
  console.log('[서비스해지][고객정보저장] 화면 데이터 반영 결과', {
    x18Result,
    remainChargeLoaded: stepForm?.remainChargeLoaded,
    itemCount: Array.isArray(stepForm?.remainChargeItems) ? stepForm.remainChargeItems.length : 0,
  })
  console.log('[서비스해지][고객정보저장] 다음 단계 이동')
  return true
}

defineExpose({ data, save, validate, validateWithAlert })
</script>

<style scoped></style>
