<template>
  <div class="page-step-panel">
    <!-- 해지 신청 -->
    <MsfCancelRequest v-model="formData" :disabled="isApplicationConfirmed" />
    <!-- // 해지 신청 -->
    <!-- 해지 정산 -->
    <MsfCancelSettlement v-model="formData" :disabled="isApplicationConfirmed" />
    <!-- // 해지 정산 -->
    <!-- 메모 -->
    <MsfMemo v-model="formData" />
    <!-- // 메모 -->
  </div>
</template>

<script setup>
import { computed, watch, onMounted, ref } from 'vue'
import { useMsfFormTerminationStore } from '@/stores/msf_termination'
import { storeToRefs } from 'pinia'
import { showAlert } from '@/libs/utils/comp.utils'

const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { formData } = storeToRefs(terminationStore)
const isApplicationConfirmed = computed(() => terminationStore.applicationConfirmed)
const REMAIN_CHARGE_NOTICE =
  '해지 시 까지 사용한 사용료, 위약금, 잔여 단말기 대금 등의 자세한 사용 요금은 다음달 청구서에서 확인 가능합니다.'
const remainChargeNoticeShown = ref(false)

const isComplete = computed(() => {
  return !!formData.value.cancelUseCompanyCd
})

watch(
  isComplete,
  (val) => {
    emit('complete', val)
  },
  { immediate: true },
)

onMounted(() => {
  emit('complete', isComplete.value)
  terminationStore.validateProductWithAlert = validateWithAlert
})

const focusField = (target) => {
  setTimeout(() => {
    const element = target.startsWith('#')
      ? document.querySelector(target)
      : document.getElementById(target) || document.querySelector(target)
    element?.scrollIntoView({ block: 'center', behavior: 'smooth' })
    element?.focus()
  }, 0)
}

const validateWithAlert = () => {
  const f = formData.value
  if (!f.cancelUseCompanyCd) {
    showAlert('해지 후 사용 통신사를 선택해 주세요.', () =>
      focusField('input[name="inp-cancelUseCompanyCd"]'),
    )
    return false
  }
  //20260713 PRX 요금조회 오류 오류 발생후에도 진행가능하게 수정
  //'해지 시 까지 사용한 사용료, 위약금, 잔여 단말기 대금 등의 자세한 사용 요금은 다음달 청구서에서 확인 가능합니다.' 라는 메시지를 표시
  if ((!f.usageFee || !f.penaltyFee || !f.finalAmount) && !remainChargeNoticeShown.value) {
    remainChargeNoticeShown.value = true
    showAlert(REMAIN_CHARGE_NOTICE)
  }
  return true
}

const save = async () => {
  if (!terminationStore.validateCustomerWithAlert()) return false
  if (!validateWithAlert()) return false
  return true
}

const reset = async () => {
  remainChargeNoticeShown.value = false
  terminationStore.resetStep(1)
  terminationStore.resetCustomerAgreement()
  emit('complete', isComplete.value)
}

defineExpose({ save, validateWithAlert, reset })
</script>

<style scoped></style>
