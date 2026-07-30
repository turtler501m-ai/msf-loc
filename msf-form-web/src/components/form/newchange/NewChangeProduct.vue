<template>
  <div class="page-step-panel">
    <MsfMnpInfo
      ref="mnpInfoRef"
      v-model="formData"
      :customerData="customerData"
      :authFlags="store.authFlags"
      :disabled="store.preChecked && customerData.joinType === 'MNP3'"
    />
    <MsfNumberReservation
      ref="numberReservationRef"
      v-model="formData"
      :customerData="customerData"
      :authFlags="store.authFlags"
      :disabled="isAllDisabled"
    />
    <MsfSimInfo
      ref="simInfoRef"
      v-model="formData"
      :customerData="customerData"
      :authFlags="store.authFlags"
      :disabled="isAllDisabled"
    />
    <MsfDeviceSerialNumber
      v-if="customerData.productType === 'MM'"
      ref="deviceSerialNumberRef"
      v-model="formData"
      :customerData="customerData"
      :authFlags="store.authFlags"
      :disabled="isAllDisabled"
    />
    <MsfVasInfo
      v-if="!['HDN3', 'HCN3'].includes(customerData.joinType)"
      ref="vasInfoRef"
      v-model="formData"
      :customerData="customerData"
      :disabled="isAllDisabled"
    />
    <MsfInsuranceInfo
      ref="insuranceInfoRef"
      v-model="formData"
      :customerData="customerData"
      :disabled="isAllDisabled"
    />
    <MsfBillingInfo
      v-if="!['HDN3', 'HCN3'].includes(customerData.joinType)"
      ref="billingInfoRef"
      v-model="formData"
      :customerData="customerData"
      v-model:authFlags="store.authFlags"
      :disabled="isAllDisabled"
    />
    <MsfMemoInfo ref="memoInfoRef" v-model="formData" :disabled="isAllDisabled" />
    <MsfMnpAuthFailModal v-model="isMnpFailModalOpen" />
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { useMsfStepStore } from '@/stores/msf_step.js'
import { post } from '@/libs/api/msf.api'
import { showAlert } from '@/libs/utils/comp.utils'

// 하위 컴포넌트 임포트
import MsfSimInfo from '@/components/form/common/MsfSimInfo.vue'
import MsfMnpInfo from '@/components/form/common/MsfMnpInfo.vue'
import MsfNumberReservation from '@/components/form/common/MsfNumberReservation.vue'
import MsfDeviceSerialNumber from '@/components/form/common/MsfDeviceSerialNumber.vue'
import MsfVasInfo from '@/components/form/common/MsfVasInfo.vue'
import MsfInsuranceInfo from '@/components/form/common/MsfInsuranceInfo.vue'
import MsfBillingInfo from '@/components/form/common/MsfBillingInfo.vue'
import MsfMemoInfo from '@/components/form/common/MsfMemoInfo.vue'
import MsfMnpAuthFailModal from '@/components/form/common/popups/MsfMnpAuthFailModal.vue'

const props = defineProps({
  prevStepValidate: { type: Function, default: () => true },
})

const emit = defineEmits(['complete'])
const store = useMsfFormNewChgStore()
const stepStore = useMsfStepStore()

const formData = store.product
const customerData = store.customer

const isAllDisabled = computed(
  () => store.preChecked && ['HDN3', 'HCN3'].includes(customerData.joinType),
)

// 컴포넌트 Refs
const mnpInfoRef = ref(null)
const numberReservationRef = ref(null)
const simInfoRef = ref(null)
const deviceSerialNumberRef = ref(null)
const vasInfoRef = ref(null)
const insuranceInfoRef = ref(null)
const billingInfoRef = ref(null)
const memoInfoRef = ref(null)

const isComplete = ref(false)
const isMnpFailModalOpen = ref(false)

// 현재 단계(Product)의 모든 컴포넌트 유효성 검사
const validate = () => {
  // 이메일 명세서 신청 시 가입자 이메일 입력 여부 검증
  const isEmailMissing =
    formData &&
    formData.cstmrBillSendTypeCd === 'CB' &&
    (!customerData.emailAddr1 || !customerData.emailAddr2)
  if (isEmailMissing) return false

  const getVal = (refObj) => {
    return refObj.value?.validate ? refObj.value.validate() : true
  }

  const validations = [
    getVal(mnpInfoRef),
    getVal(numberReservationRef),
    getVal(simInfoRef),
    getVal(deviceSerialNumberRef),
    getVal(vasInfoRef),
    getVal(insuranceInfoRef),
    getVal(billingInfoRef),
    getVal(memoInfoRef),
  ]
  return validations.every((v) => v === true)
}

const getPendingItems = () => {
  const pending = []

  // 이메일 명세서 신청 시 가입자 이메일 입력 여부 먼저 체크
  const isEmailMissing =
    formData &&
    formData.cstmrBillSendTypeCd === 'CB' &&
    (!customerData.emailAddr1 || !customerData.emailAddr2)

  if (isEmailMissing) {
    pending.push('가입자 정보의 이메일을')
  }

  const check = (refObj, label) => {
    if (refObj.value?.validate) {
      if (!refObj.value.validate()) {
        pending.push(label)
        return false
      }
    }
    return true
  }

  const simTypeLabel = formData && formData.simTypeCd === 'ESIM' ? 'eSIM 정보를' : '유심 정보를'
  check(simInfoRef, simTypeLabel)
  check(mnpInfoRef, '번호이동 정보를')
  check(numberReservationRef, '희망번호 정보를')
  check(deviceSerialNumberRef, '단말기 일련번호를')
  check(vasInfoRef, '부가서비스를')
  check(insuranceInfoRef, '보험 정보를')
  check(billingInfoRef, '납부 정보를')
  check(memoInfoRef, '메모 정보를')

  return pending
}

const checkRequiredFields = () => {
  const pending = getPendingItems()
  const isReady = pending.length === 0

  isComplete.value = isReady
  emit('complete', isReady)
  return isReady
}

// 강제로 이전 단계로 이동 (개발용)
const handleForcePrev = () => {
  stepStore.prevStep()
}

watch(
  () => [formData, customerData, store.authFlags],
  () => {
    checkRequiredFields()
  },
  { deep: true },
)

onMounted(() => {
  checkRequiredFields()
  store.validateProduct = validate
  store.resetProductStep = resetStep
})

const save = async () => {
  // 이전 단계(Customer) 검증
  if (!store.validateCustomerWithAlert(true)) {
    console.error('Previous step (Customer) validation failed')
    return false
  }

  // 현재 단계(Product) 검증
  if (!validate()) {
    console.error('Product step validation failed')
    return false
  }

  // 개통전 사전체크 제외하고 바로 임시저장 수행 (가입유형이 HDN3 인 경우 requestPreCheck: 'Y' 추가)
  const isHDN3 = customerData.joinType === 'HDN3'
  const saveResult = await store.apiSaveDraft(2, isHDN3 ? { requestPreCheck: 'Y' } : undefined)
  return saveResult
}

const validateWithAlert = () => {
  // 이전 단계(Customer) 가입자 상세 정보 최종 검증 (isBeforeEligibility = false 정밀 체킹)
  if (typeof store.validateCustomerWithAlert === 'function') {
    if (!store.validateCustomerWithAlert(false)) {
      return false
    }
  }

  const pending = getPendingItems()
  if (pending.length > 0) {
    showAlert(`${pending[0]} 입력해 주세요.`)
    return false
  }
  return true
}

const resetStep = () => {
  simInfoRef.value?.reset?.()
  deviceSerialNumberRef.value?.reset?.()
  billingInfoRef.value?.reset?.()
  checkRequiredFields()
}

const reset = () => {
  store.resetStep(2)
  simInfoRef.value?.reset?.()
  deviceSerialNumberRef.value?.reset?.()
  billingInfoRef.value?.reset?.()
}

const checkValidation = () => {
  if (mnpInfoRef.value?.checkValidation && !mnpInfoRef.value.checkValidation()) {
    console.log('mnpInfoRef.value.checkValidation()', false)
    return false
  }
  if (
    numberReservationRef.value?.checkValidation &&
    !numberReservationRef.value.checkValidation()
  ) {
    console.log('numberReservationRef.value.checkValidation()', false)
    return false
  }
  if (simInfoRef.value?.checkValidation && !simInfoRef.value.checkValidation()) {
    console.log('simInfoRef.value.checkValidation()', false)
    return false
  }
  if (
    customerData.productType === 'MM' &&
    deviceSerialNumberRef.value?.checkValidation &&
    !deviceSerialNumberRef.value.checkValidation()
  ) {
    console.log('deviceSerialNumberRef.value.checkValidation()', false)
    return false
  }
  if (
    !['HDN3', 'HCN3'].includes(customerData.joinType) &&
    vasInfoRef.value?.checkValidation &&
    !vasInfoRef.value.checkValidation()
  ) {
    console.log('vasInfoRef.value.checkValidation()', false)
    return false
  }
  if (insuranceInfoRef.value?.checkValidation && !insuranceInfoRef.value.checkValidation()) {
    console.log('insuranceInfoRef.value.checkValidation()', false)
    return false
  }
  if (
    !['HDN3', 'HCN3'].includes(customerData.joinType) &&
    billingInfoRef.value?.checkValidation &&
    !billingInfoRef.value.checkValidation()
  ) {
    console.log('billingInfoRef.value.checkValidation()', false)
    return false
  }
  if (memoInfoRef.value?.checkValidation && !memoInfoRef.value.checkValidation()) {
    console.log('memoInfoRef.value.checkValidation()', false)
    return false
  }

  return true
}

defineExpose({
  save,
  validate,
  getPendingItems,
  validateWithAlert,
  resetStep,
  reset,
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
