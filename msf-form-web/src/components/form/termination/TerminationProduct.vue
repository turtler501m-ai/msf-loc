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

const emit = defineEmits(['complete'])

const terminationStore = useMsfFormTerminationStore()
const { formData } = storeToRefs(terminationStore)

const isComplete = computed(() => {
  return (
    !!formData.value.isActive &&
    !!formData.value.usageFee &&
    !!formData.value.penaltyFee &&
    !!formData.value.finalAmount
  )
})

// [TEST] 화면 테스트용 오버라이드: '' 으로 초기화
const isCompleteOverride = ref('true')

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

const save = async () => {
  if (!isCompleteEffective.value) return false
  return true
}

defineExpose({ save })
</script>

<style scoped></style>
