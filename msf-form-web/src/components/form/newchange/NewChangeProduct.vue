<template>
  <div class="page-step-panel">
    <MsfSimInfo
      ref="simInfoRef"
      v-model="formData"
      :customerData="customerData"
      :authFlags="store.authFlags"
    />
    <MsfMnpInfo
      ref="mnpInfoRef"
      v-model="formData"
      :customerData="customerData"
      :authFlags="store.authFlags"
    />
    <MsfNumberReservation
      ref="numberReservationRef"
      v-model="formData"
      :customerData="customerData"
      :authFlags="store.authFlags"
    />
    <MsfDeviceSerialNumber
      ref="deviceSerialNumberRef"
      v-model="formData"
      :customerData="customerData"
      :authFlags="store.authFlags"
    />
    <MsfVasInfo ref="vasInfoRef" v-model="formData" />
    <MsfInsuranceInfo ref="insuranceInfoRef" v-model="formData" :customerData="customerData" />
    <MsfBillingInfo
      ref="billingInfoRef"
      v-model="formData"
      :customerData="customerData"
      :authFlags="store.authFlags"
    />
    <MsfMemoInfo ref="memoInfoRef" v-model="formData" />
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { useMsfStepStore } from '@/stores/msf_step.js'
import { post } from '@/libs/api/msf.api'

// 하위 컴포넌트 임포트
import MsfSimInfo from '@/components/form/common/MsfSimInfo.vue'
import MsfMnpInfo from '@/components/form/common/MsfMnpInfo.vue'
import MsfNumberReservation from '@/components/form/common/MsfNumberReservation.vue'
import MsfDeviceSerialNumber from '@/components/form/common/MsfDeviceSerialNumber.vue'
import MsfVasInfo from '@/components/form/common/MsfVasInfo.vue'
import MsfInsuranceInfo from '@/components/form/common/MsfInsuranceInfo.vue'
import MsfBillingInfo from '@/components/form/common/MsfBillingInfo.vue'
import MsfMemoInfo from '@/components/form/common/MsfMemoInfo.vue'

const props = defineProps({
  prevStepValidate: { type: Function, default: () => true },
})

const emit = defineEmits(['complete'])
const store = useMsfFormNewChgStore()
const stepStore = useMsfStepStore()

const formData = store.product
const customerData = store.customer

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

// 현재 단계(Product)의 모든 컴포넌트 유효성 검사
const validate = () => {
  const getVal = (refObj) => {
    if (refObj.value && typeof refObj.value.validate === 'function') {
      return refObj.value.validate()
    }
    return true
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
  const check = (refObj, label) => {
    if (refObj.value && typeof refObj.value.validate === 'function') {
      if (!refObj.value.validate()) {
        pending.push(label)
        return false
      }
    }
    return true
  }

  const simTypeLabel =
    formData.value && formData.value.hasSim === 'hasSim3' ? 'eSIM 정보' : '유심 정보'
  check(simInfoRef, simTypeLabel)
  check(mnpInfoRef, '번호이동 정보')
  check(numberReservationRef, '희망번호 정보')
  check(deviceSerialNumberRef, '단말기 일련번호')
  check(vasInfoRef, '부가서비스')
  check(insuranceInfoRef, '보험 정보')
  check(billingInfoRef, '납부 정보')
  check(memoInfoRef, '메모 정보')

  return pending
}

const checkRequiredFields = () => {
  const pending = getPendingItems()
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

// 강제로 이전 단계로 이동 (개발용)
const handleForcePrev = () => {
  stepStore.prevStep()
}

watch(
  () => [formData, store.authFlags],
  () => {
    checkRequiredFields()
  },
  { deep: true },
)

onMounted(() => {
  checkRequiredFields()
  store.validateProduct = validate
})

const save = async () => {
  // 이전 단계(Customer) 검증
  if (!store.validateCustomer()) {
    console.error('Previous step (Customer) validation failed')
    return false
  }

  // 현재 단계(Product) 검증
  if (!validate()) {
    console.error('Product step validation failed')
    return false
  }

  // 개통전 사전체크 제외하고 바로 임시저장 수행
  console.log('[NewChangeProduct] Saving draft (Step 2)...')
  const saveResult = await store.apiSaveDraft(2)
  console.log('[NewChangeProduct] apiSaveDraft(2) result:', saveResult)
  return saveResult
}

defineExpose({ save, validate, getPendingItems, reset: store.resetAll })
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
