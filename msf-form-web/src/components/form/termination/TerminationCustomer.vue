<template>
  <div class="page-step-panel">
    <!-- 고객 유형 -->
    <MsfCustomerType v-model="formData" :visitTypeCodes="['JP']" />
    <!-- // 고객 유형 -->
    <!-- 신분증 확인 -->
    <MsfIdentityVerify v-model="formData" />
    <!-- // 신분증 확인 -->
    <!-- 가입자 정보 -->
    <MsfSubscriberInfo v-model="formData" phoneLabel="해지 휴대폰번호" />
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
    <MsfAgreementGroup policy="join" ref="agreementRef" required />
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

const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { formData } = storeToRefs(terminationStore)

const agreementRef = ref(null)
const isComplete = ref(false)

// [TEST] 화면 테스트용 : 개발/검증 완료 시 '' 로 초기화
const isCompleteOverride = ref('true')

// 유효성 검사
const validate = () => {
  const f = formData.value

  // 신분증 인증
  if (f.identityCertTypeCd !== 'S' && !f.isVerified) return false

  // 고객명
  if (!f.cstmrNm) return false

  // 해지 휴대폰번호
  if (!f.deviceChgTel1 || !f.deviceChgTel2 || !f.deviceChgTel3) return false

  // 해지 후 연락처
  if (!f.afterTel1 || !f.afterTel2 || !f.afterTel3) return false

  // 미성년자 — 법정대리인 인증
  if (['NM', 'FM'].includes(f.cstmrTypeCd) && !f.repAgree) return false

  return true
}

const checkRequiredFields = () => {
  if (isCompleteOverride.value === 'true') {
    isComplete.value = true
    emit('complete', true)
    return true
  }
  if (isCompleteOverride.value === 'false') {
    isComplete.value = false
    emit('complete', false)
    return false
  }
  const isReady = validate()
  isComplete.value = isReady
  emit('complete', isReady)
  return isReady
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
  console.log('[Step1] save start', {
    isCompleteOverride: isCompleteOverride.value,
    ncn: stepForm?.ncn,
    contractNum: stepForm?.contractNum,
  })
  if (isCompleteOverride.value === 'false') {
    console.warn('[Step1] blocked: override false')
    return false
  }
  if (isCompleteOverride.value !== 'true' && !validate()) {
    console.warn('[Step1] blocked: validate failed')
    return false
  }
  const ensuredNcn = await terminationStore.ensureTerminationNcn()
  console.log('[Step1] ncn ensure result', {
    ensuredNcn,
    ncn: stepForm?.ncn,
    contractNum: stepForm?.contractNum,
  })
  if (!stepForm?.ncn) {
    console.warn('[Step1] blocked: ncn missing', {
      ncn: stepForm?.ncn,
      contractNum: stepForm?.contractNum,
    })
    return false
  }
  console.log('[Step1] X18 조회 시작', { ncn: stepForm?.ncn })
  const x18Result = await terminationStore.apiGetRemainCharge()
  console.log('[Step1] X18 result', {
    x18Result,
    remainChargeLoaded: stepForm?.remainChargeLoaded,
    itemCount: Array.isArray(stepForm?.remainChargeItems) ? stepForm.remainChargeItems.length : 0,
  })
  console.log('[Step1] X18 조회 완료 → Step2 이동')
  return true
}

defineExpose({ data, save, validate })
</script>

<style scoped></style>
