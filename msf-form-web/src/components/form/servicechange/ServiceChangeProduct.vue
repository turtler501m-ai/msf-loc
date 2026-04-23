<template>
  <div class="page-step-panel">
    <!-- 무선데이터차단 서비스 -->
    <MsfWirelessDataBlock
      v-model="formData"
      v-if="formData.serviceSelect?.includes('serviceSelect1')"
    />
    <!-- // 무선데이터차단 서비스 -->
    <!-- 정보료 상한금액 설정/변경 -->
    <MsfInfoChargeLimit
      v-model="formData"
      v-if="formData.serviceSelect?.includes('serviceSelect2')"
    />
    <!-- // 정보료 상한금액 설정/변경 -->
    <!-- 부가서비스 신청/변경 -->
    <MsfValueAdditonalServiceReqChg
      v-model="formData"
      v-if="formData.serviceSelect?.includes('serviceSelect3')"
    />
    <!-- // 부가서비스 신청/변경 -->
    <!-- 요금제 변경 -->
    <MsfChargePlanChange
      v-model="formData"
      v-if="formData.serviceSelect?.includes('serviceSelect4')"
    />
    <!-- // 요금제 변경 -->
    <!-- 번호변경 -->
    <MsfNewJoinTelePhoneNumber
      v-model="formData"
      v-if="formData.serviceSelect?.includes('serviceSelect5')"
    />
    <!-- // 번호변경 -->
    <!-- 분실복구/일시정지해제 신청 -->
    <MsfUnpauseRequest
      v-model="formData"
      v-if="formData.serviceSelect?.includes('serviceSelect6')"
    />
    <!-- // 분실복구/일시정지해제 신청 -->
    <!-- 단말보험 가입 / 단말보험 가입 약관 동의 -->
    <MsfDeviceInsuranceJoin
      v-model="formData"
      v-if="formData.serviceSelect?.includes('serviceSelect7')"
    />
    <!-- // 단말보험 가입 / 단말보험 가입 약관 동의 -->
    <!-- USIM 변경 -->
    <MsfSimInfo v-model="formData" v-if="formData.serviceSelect?.includes('serviceSelect8')" />
    <!-- // USIM 변경 -->
    <!-- 데이터쉐어링 가입/해지 & 데이터쉐어링 가입/해지 약관 동의 -->
    <MsfDataSharingJoinAndCancel
      v-model="formData"
      v-if="formData.serviceSelect?.includes('serviceSelect9')"
    />
    <!-- // 데이터쉐어링 가입/해지 & 데이터쉐어링 가입/해지 약관 동의 -->
    <!-- 아무나 SOLO 결합 & 아무나 SOLO 결합 약관 동의 -->
    <MsfCombineSolo v-model="formData" v-if="formData.serviceSelect?.includes('serviceSelect10')" />
    <!-- // 아무나 SOLO 결합 & 아무나 SOLO 결합 약관 동의 -->
    <!-- 메모 -->
    <MsfMemo />
    <!-- // 메모 -->

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p @click="test">- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">상품 저장</option>
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
const emit = defineEmits(['complete'])

const isComplete = ref('')

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal ? true : false)
  },
)

const save = async () => {
  //  데이터 임시저장
  return isComplete.value === 'true'
}

defineExpose({ save })

// 퍼블 샘플
const store = useMsfFormSvcChgStore()
const { formData } = storeToRefs(store)

const test = function () {
  console.log(formData.value.serviceList)
}
</script>

<style scoped></style>
