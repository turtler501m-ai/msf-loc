<template>
  <div class="page-step-panel">
    <!-- 고객(양도고객) 유형 -->
    <MsfCustomerType v-model="formData.tr_customer" title="고객(양도고객) 유형" />
    <!-- // 고객(양도고객) 유형 -->
    <!-- 고객(양도고객) 신분증 확인 -->
    <MsfIdentityVerify v-model="formData.tr_customer" title="고객(양도고객) 신분증 확인" />
    <!-- // 고객(양도고객) 신분증 확인 -->
    <!-- 고객(양도고객) 정보 -->
    <MsfSubscriberInfo v-model="formData.tr_customer" title="고객(양도고객) 정보" />
    <!-- // 고객(양도고객) 정보 -->
    <!-- 고객(양도고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo v-model="formData.tr_customer" title="고객(양도고객) 법정대리인 정보" />
    <!-- // 고객(양도고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <!-- 고객(양수고객) 유형 -->
    <MsfCustomerType v-model="formData.te_customer" title="고객(양수고객) 유형" />
    <!-- // 고객(양수고객) 유형 -->
    <!-- 고객(양수고객) 신분증 확인 -->
    <MsfIdentityVerify v-model="formData.te_customer" title="고객(양수고객) 신분증 확인" />
    <!-- // 고객(양수고객) 신분증 확인 -->
    <!-- 고객(양수고객) 정보 -->
    <MsfSubscriberInfo v-model="formData.te_customer" title="고객(양수고객) 정보" />
    <!-- // 고객(양수고객) 정보 -->
    <!-- 고객(양수고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo v-model="formData.te_customer" title="고객(양수고객) 법정대리인 정보" />
    <!-- 고객(양수고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <!-- 고객(실사용자) 정보 -->
    <MsfRealUserInfo v-model="formData" />
    <!-- // 고객(실사용자) 정보 -->
    <!-- 대리인 위임 정보 -->
    <MsfDelegateInfo v-model="formData" v-if="formData.te_customer?.cstmrVisitTypeCd === 'V2'" />
    <!-- // 대리인 위임 정보 -->
    <!-- 구비서류 -->
    <MsfRequiredDoc v-model="formData" />
    <!-- // 구비서류 -->
    <!-- 고객(양수고객) 연락처 -->
    <MsfContactInfo v-model="formData.te_customer" title="고객(양수고객) 연락처" />
    <!-- // 고객(양수고객) 연락처 -->
    <!-- 요금제 정보 -->
    <MsfChargePlanInfo v-model="formData" />
    <!-- // 요금제 정보 -->
    <!-- 약관 동의  -->
    <MsfTitleArea title="약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" description="" required />
    <!-- // 약관 동의 -->

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">고객 저장</option>
          <option value="true">성공</option>
          <option value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
  </div>
</template>

<script setup>
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
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

const data = async (code /* 임시저장 코드 */) => {
  // 임시저장 정보 조회
  if (code) {
    // 임시저장된 데이터 조회 후, 임시저장단계코드 리턴
    // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
    return '1'
  }

  return '0' // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
}

const save = async () => {
  //  데이터 임시저장
  return isComplete.value === 'true'
}

defineExpose({ data, save })

// 퍼블 샘플
// vue-datepicker
const datePickerValue = ref() // 날짜선택 (MsfDateInput)
const rangeDatePickerValue = ref({ start: '', end: '' })
const licenseNumber = ref() // 면허 번호 (MsfNumberInput)

const store = useMsfFormOwnChgStore()
const { formData } = storeToRefs(store)
</script>

<style scoped></style>
