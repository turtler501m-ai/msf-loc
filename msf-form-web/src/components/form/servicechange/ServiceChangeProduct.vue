<template>
  <MsfRequestComplete
    v-if="showCommonCompletePage"
    class="service-change-issue-test-page"
    :request-key="issueTestRequestKey"
  />
  <div v-else class="page-step-panel">
    <MsfLoadingComp v-if="isProductStepSaving" />
    <!-- 무선데이터차단 서비스 -->
    <MsfWirelessDataBlock
      v-model="formData"
      v-if="formData.serviceSelect?.includes('R12')"
      @ready="completeServiceAreaLoading('R12')"
    />
    <!-- 부가서비스 신청/변경 -->
    <MsfServiceChangeAdditon
      v-model="formData"
      v-if="formData.serviceSelect?.includes('R11')"
      @ready="completeServiceAreaLoading('R11')"
    />
    <!-- 요금제 변경 -->
    <MsfChargePlanChange v-model="formData" v-if="formData.serviceSelect?.includes('P11')" />
    <!-- 번호변경 -->
    <MsfNewJoinTelePhoneNumber
      v-model="formData"
      v-if="formData.serviceSelect?.includes('O11')"
      @ready="completeServiceAreaLoading('O11')"
    />
    <!-- 분실복구/일시정지해제 신청 -->
    <MsfUnpauseRequest
      v-model="formData"
      v-if="formData.serviceSelect?.includes('O12')"
      @ready="completeServiceAreaLoading('O12')"
    />
    <!-- 단말보험 가입 / 단말보험 가입 약관 동의 -->
    <MsfDeviceInsuranceJoin
      v-model="formData"
      v-model:authFlags="store.authFlags"
      :reset-key="store.cancelAuthResetKey"
      service-change
      v-if="formData.serviceSelect?.includes('R14')"
    />
    <!-- USIM 변경 -->
    <MsfProductSimInfo
      v-model="formData"
      :authFlags="store.authFlags"
      :reset-key="store.cancelAuthResetKey"
      v-if="formData.serviceSelect?.includes('O13')"
    />
    <!-- 데이터쉐어링 가입/해지 & 데이터쉐어링 가입/해지 약관 동의 -->
    <MsfDataSharingJoinAndCancel
      v-model="formData"
      v-if="formData.serviceSelect?.includes('R15')"
      @ready="completeServiceAreaLoading('R15')"
    />
    <!-- 아무나 SOLO 결합 & 아무나 SOLO 결합 약관 동의 -->
    <MsfCombineSolo v-model="formData" v-if="formData.serviceSelect?.includes('R16')" />
    <!-- // 아무나 SOLO 결합 & 아무나 SOLO 결합 약관 동의 -->
    <!-- 메모 -->
    <MsfMemo v-model="formData" prevent-focus-scroll />
    <div v-if="isLocalMode" class="service-change-product-test-actions">
      <MsfButton variant="primary" @click="onClickProductNextTestBtn">다음(TEST)</MsfButton>
      <MsfButton variant="primary" :disabled="!canIssueTest" @click="onClickProductIssueTestBtn">
        교부(TEST)
      </MsfButton>
    </div>
  </div>
</template>

<script setup>
import MsfRequestComplete from '@/components/form/common/MsfRequestComplete.vue'
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { useMsfLoadingStore } from '@/stores/msf_loading'
import { useMsfStepStore } from '@/stores/msf_step'
import { storeToRefs } from 'pinia'
import { computed, nextTick, onMounted, ref, watch } from 'vue'

const debugLog = (...args) => {
  console.debug(...args)
}

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const isComplete = ref('')
const isProductStepSaving = ref(false)
const showCommonCompletePage = ref(false)
const issueTestRequestKey = ref('')
const isLocalMode = ['loc'].includes(import.meta.env.MODE)

const store = useMsfFormSvcChgStore()
const loadingStore = useMsfLoadingStore()
const stepStore = useMsfStepStore()
const { formData } = storeToRefs(store)
const ASYNC_READY_SERVICE_CODES = ['O11', 'O12', 'R11', 'R12', 'R15']
const canIssueTest = computed(
  () =>
    !isProductStepSaving.value &&
    formData.value.completeApplicationCompleted === true &&
    !!store.requestKey,
)

const completeServiceAreaLoading = (serviceCode) => {
  const targets = Array.isArray(formData.value.serviceAreaLoadingTargets)
    ? formData.value.serviceAreaLoadingTargets
    : []

  if (!targets.includes(serviceCode)) return

  formData.value.serviceAreaLoadingTargets = targets.filter((code) => code !== serviceCode)

  if (formData.value.serviceAreaLoadingTargets.length === 0) {
    loadingStore.hideLoading()
  }
}

const addServiceAreaLoadingTarget = (serviceCode) => {
  const targets = Array.isArray(formData.value.serviceAreaLoadingTargets)
    ? formData.value.serviceAreaLoadingTargets
    : []

  if (targets.includes(serviceCode)) return

  formData.value.serviceAreaLoadingTargets = [...targets, serviceCode]
  loadingStore.showLoading()
}

onMounted(async () => {
  await nextTick()
  const selectedTypes = Array.isArray(formData.value.serviceSelect)
    ? formData.value.serviceSelect
    : []
  selectedTypes
    .filter((serviceCode) => !ASYNC_READY_SERVICE_CODES.includes(serviceCode))
    .forEach(completeServiceAreaLoading)
})

watch(
  () => (Array.isArray(formData.value.serviceSelect) ? [...formData.value.serviceSelect] : []),
  async (selectedTypes, previousTypes = []) => {
    if (formData.value.serviceSelectCompleteYn !== 'Y') return

    const addedTypes = selectedTypes.filter((serviceCode) => !previousTypes.includes(serviceCode))
    const removedTypes = previousTypes.filter((serviceCode) => !selectedTypes.includes(serviceCode))

    removedTypes.forEach(completeServiceAreaLoading)

    for (const serviceCode of addedTypes) {
      addServiceAreaLoadingTarget(serviceCode)
      if (!ASYNC_READY_SERVICE_CODES.includes(serviceCode)) {
        await nextTick()
        completeServiceAreaLoading(serviceCode)
      }
    }
  },
)

// 확인 완료 버튼이 필요한 서비스 → formData 필드 매핑
// 목록에 없는 서비스(P11/O11/O12/R14/O13/R15/R16)는 선택 자체가 완료 조건
const CONFIRM_REQUIRED_MAP = {
  P11: 'planChangeConfirmCompleted',
  O11: 'numberChgConfirmCompleted',
  O12: 'unpauseConfirmCompleted',
  R11: 'additionConfirmCompleted',
  R12: 'wirelessBlockConfirmCompleted',
  R14: 'insuranceConfirmCompleted',
  O13: 'reqUsimConfirmCompleted',
  R15: 'dataSharingConfirmCompleted',
  R16: 'combineSoloConfirmCompleted',
}

debugLog('[ServiceChangeProduct] CONFIRM_REQUIRED_MAP', CONFIRM_REQUIRED_MAP)

const syncCompleteState = () => {
  const selectedTypes = Array.isArray(formData.value.serviceSelect)
    ? formData.value.serviceSelect
    : []

  if (selectedTypes.length === 0) {
    isComplete.value = ''
    debugLog('[ServiceChangeProduct] 선택된 서비스 없음', {
      serviceSelect: formData.value.serviceSelect,
      isComplete: isComplete.value,
    })
    return
  }

  const allDone = selectedTypes.every((type) => {
    const field = CONFIRM_REQUIRED_MAP[type]
    return field ? formData.value[field] === true : true
  })

  isComplete.value = allDone ? 'true' : ''
  debugLog('[ServiceChangeProduct] 완료 상태 동기화', {
    selectedTypes,
    additionConfirmCompleted: formData.value.additionConfirmCompleted,
    wirelessBlockConfirmCompleted: formData.value.wirelessBlockConfirmCompleted,
    insuranceConfirmCompleted: formData.value.insuranceConfirmCompleted,
    isComplete: isComplete.value,
  })
}

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => [
    Array.isArray(formData.value.serviceSelect) ? formData.value.serviceSelect.join('|') : '',
    formData.value.planChangeConfirmCompleted,
    formData.value.additionConfirmCompleted,
    formData.value.wirelessBlockConfirmCompleted,
    formData.value.insuranceConfirmCompleted,
    formData.value.dataSharingConfirmCompleted,
    formData.value.numberChgConfirmCompleted,
    formData.value.unpauseConfirmCompleted,
    formData.value.combineSoloConfirmCompleted,
    formData.value.reqUsimConfirmCompleted,
  ],
  () => {
    syncCompleteState()
  },
  { immediate: true },
)

watch(
  () => isComplete.value,
  (newVal) => {
    debugLog('[ServiceChangeProduct] complete emit', newVal)
    emit('complete', newVal ? true : false)
  },
)

const validateWithAlert = () => store.validateFormFields()

const onClickProductNextTestBtn = () => {
  completeProductStepForTest()
  stepStore.setActiveIndex(2)
}

const completeProductStepForTest = () => {
  const selectedTypes = Array.isArray(formData.value.serviceSelect) ? formData.value.serviceSelect : []
  selectedTypes.forEach((type) => {
    const field = CONFIRM_REQUIRED_MAP[type]
    if (field) {
      formData.value[field] = true
    }
  })
  isComplete.value = 'true'
  emit('complete', true)
}

const onClickProductIssueTestBtn = () => {
  if (!canIssueTest.value) return
  issueTestRequestKey.value = store.requestKey || '0'
  showCommonCompletePage.value = true
}

const save = async () => {
  if (isProductStepSaving.value) return false

  isProductStepSaving.value = true

  try {
    // 데이터 임시저장
    const selectedTypes = Array.isArray(formData.value.serviceSelect)
      ? formData.value.serviceSelect
      : []
    debugLog('[ServiceChangeProduct] save 호출', {
      selectedTypes,
      isComplete: isComplete.value,
    })

    for (const [type, field] of Object.entries(CONFIRM_REQUIRED_MAP)) {
      if (selectedTypes.includes(type) && formData.value[field] !== true) {
        debugLog('[ServiceChangeProduct] 필수 확인 미완료', {
          type,
          field,
          value: formData.value[field],
        })
        return false
      }
    }

    const success = isComplete.value === 'true'
    if (success) {
      // 서비스상품 영역 다음 버튼 완료 후 서비스변경 선택 영역을 비활성화한다.
      formData.value.serviceSelectionLocked = true
    }

    return success
  } finally {
    isProductStepSaving.value = false
  }
}

defineExpose({ save, validateWithAlert })

// 퍼블 샘플
</script>

<style scoped>
.service-change-product-test-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
  margin: 24px 0 16px;
}

.service-change-issue-test-page {
  position: fixed;
  inset: 0;
  z-index: 1000;
  overflow-y: auto;
  background: var(--color-white, #fff);
  padding: 32px 24px 40px;
}
</style>
