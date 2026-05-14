<template>
  <div class="page-step-panel">
    <!-- 해지 신청 -->
    <MsfCancelRequest v-model="formData" />
    <!-- // 해지 신청 -->
    <!-- 해지 정산 -->
    <MsfCancelSettlement v-model="formData" />
    <!-- // 해지 정산 -->
    <!-- 메모 -->
    <MsfMemo v-model="formData" />
    <!-- // 메모 -->

    <!-- (화면테스트용 테스트영역) 추후 지워질수도 있는것-->
    <div class="ut-mt-50">
      <div>
        <p>- 개발자주석 부분- 화면 프로세스</p>
        <select v-model="isCompleteOverride">
          <option value="">해지 정산</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 테스트영역) 추후 제거 -->
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

const isComplete = computed(() => {
  return (
    !!formData.value.cancelUseCompanyCd &&
    !!formData.value.usageFee &&
    !!formData.value.penaltyFee &&
    !!formData.value.finalAmount
  )
})

// [TEST] 화면 테스트용 오버라이드: ''이면 해지 정산 입력값 기준으로 판단
const isCompleteOverride = ref('')

const isCompleteEffective = computed(() => {
  if (isCompleteOverride.value === 'true') return true
  if (isCompleteOverride.value === 'false') return false
  return isComplete.value
})

watch(
  isCompleteEffective,
  (val) => {
    emit('complete', val)
  },
  { immediate: true },
)

onMounted(() => {
  emit('complete', isCompleteEffective.value)
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
    showAlert('해지 후 사용 통신사를 선택해 주세요.', () => focusField('input[name="inp-cancelUseCompanyCd"]'))
    return false
  }
  if (!f.usageFee) {
    showAlert('사용요금을 입력해 주세요.', () => focusField('inp-usageFee'))
    return false
  }
  if (!f.penaltyFee) {
    showAlert('위약금을 입력해 주세요.', () => focusField('inp-penaltyFee'))
    return false
  }
  if (!f.finalAmount) {
    showAlert('최종 정산요금을 입력해 주세요.', () => focusField('inp-finalAmount'))
    return false
  }
  return true
}

const save = async () => {
  if (isCompleteOverride.value === 'false') return false
  if (!validateWithAlert()) return false
  return true
}

const reset = async () => {
  terminationStore.resetStep(1)
  emit('complete', isCompleteEffective.value)
}

defineExpose({ save, validateWithAlert, reset })
</script>

<style scoped></style>
