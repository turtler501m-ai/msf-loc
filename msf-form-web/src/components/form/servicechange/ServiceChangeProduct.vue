<template>
  <div class="page-step-panel">
    <!-- 무선데이터차단 서비스 -->
    <MsfWirelessDataBlock
      v-model="formData"
      v-if="formData.serviceSelect?.includes('R12')"
    />
    <!-- 부가서비스 신청/변경 -->
    <MsfServiceChangeAdditon
      v-model="formData"
      v-if="formData.serviceSelect?.includes('R11')"
    />
    <!-- 요금제 변경 -->
    <MsfChargePlanChange
      v-model="formData"
      v-if="formData.serviceSelect?.includes('P11')"
    />
    <!-- 번호변경 -->
    <MsfNewJoinTelePhoneNumber
      v-model="formData"
      v-if="formData.serviceSelect?.includes('O11')"
    />
    <!-- 분실복구/일시정지해제 신청 -->
    <MsfUnpauseRequest
      v-model="formData"
      v-if="formData.serviceSelect?.includes('O12')"
    />
    <!-- 단말보험 가입 / 단말보험 가입 약관 동의 -->
    <MsfDeviceInsuranceJoin
      v-model="formData"
      v-if="formData.serviceSelect?.includes('R14')"
    />
    <!-- USIM 변경 -->
    <MsfSimInfo v-model="formData" v-if="formData.serviceSelect?.includes('O13')" />
    <!-- 데이터쉐어링 가입/해지 & 데이터쉐어링 가입/해지 약관 동의 -->
    <MsfDataSharingJoinAndCancel
      v-model="formData"
      v-if="formData.serviceSelect?.includes('R15')"
    />
    <!-- 아무나 SOLO 결합 & 아무나 SOLO 결합 약관 동의 -->
    <MsfCombineSolo v-model="formData" v-if="formData.serviceSelect?.includes('R16')" />
    <!-- // 아무나 SOLO 결합 & 아무나 SOLO 결합 약관 동의 -->
    <!-- 메모 -->
    <MsfMemo />

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p @click="test">- 개발자용 부가서비스 프로세스</p>
        <select v-model="isComplete">
          <option value="">상품 선택</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
  </div>
</template>

<script setup>
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { storeToRefs } from 'pinia'
import { ref, watch } from 'vue'

// 필수 항목 입력 완료여부 리턴
console.log('[ServiceChangeProduct] complete emit 준비')
const emit = defineEmits(['complete'])

const isComplete = ref('')

const store = useMsfFormSvcChgStore()
const { formData } = storeToRefs(store)

// 확인 완료 버튼이 필요한 서비스 → formData 필드 매핑
// 목록에 없는 서비스(P11/O11/O12/R14/O13/R15/R16)는 선택 자체가 완료 조건
const CONFIRM_REQUIRED_MAP = {
  R11: 'additionConfirmCompleted',
  R12: 'wirelessBlockConfirmCompleted',
}

console.log('[ServiceChangeProduct] CONFIRM_REQUIRED_MAP', CONFIRM_REQUIRED_MAP)

const syncCompleteState = () => {
  const selectedTypes = Array.isArray(formData.value.serviceSelect) ? formData.value.serviceSelect : []

  if (selectedTypes.length === 0) {
    isComplete.value = ''
    console.log('[ServiceChangeProduct] 선택된 서비스 없음', {
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
  console.log('[ServiceChangeProduct] 완료 상태 동기화', {
    selectedTypes,
    additionConfirmCompleted: formData.value.additionConfirmCompleted,
    wirelessBlockConfirmCompleted: formData.value.wirelessBlockConfirmCompleted,
    isComplete: isComplete.value,
  })
}

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => [
    Array.isArray(formData.value.serviceSelect) ? formData.value.serviceSelect.join('|') : '',
    formData.value.additionConfirmCompleted,
    formData.value.wirelessBlockConfirmCompleted,
  ],
  () => {
    syncCompleteState()
  },
  { immediate: true },
)

watch(
  () => isComplete.value,
  (newVal) => {
    console.log('[ServiceChangeProduct] complete emit', newVal)
    emit('complete', newVal ? true : false)
  },
)

const save = async () => {
  // 데이터 임시저장
  const selectedTypes = Array.isArray(formData.value.serviceSelect) ? formData.value.serviceSelect : []
  console.log('[ServiceChangeProduct] save 호출', {
    selectedTypes,
    isComplete: isComplete.value,
  })

  for (const [type, field] of Object.entries(CONFIRM_REQUIRED_MAP)) {
    if (selectedTypes.includes(type) && formData.value[field] !== true) {
      console.log('[ServiceChangeProduct] 필수 확인 미완료', {
        type,
        field,
        value: formData.value[field],
      })
      return false
    }
  }

  return isComplete.value === 'true'
}

defineExpose({ save })

// 퍼블 샘플
const test = function () {
  console.log('[ServiceChangeProduct] serviceList', formData.value.serviceList)
}
</script>

<style scoped></style>
