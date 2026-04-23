<template>
  <div class="page-step-panel">
    <!-- SIM 정보 -->
    <MsfSIMInfo v-model="formData" title="SIM 정보" />
    <!-- // SIM 정보 -->
    <!-- 납부 정보 -->
    <MsfPaymentInfo v-model="formData" />
    <!-- // 납부 정보 -->
    <!-- 메모 -->
    <MsfMemo v-model="formData" />
    <!-- // 메모 -->

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
import { ref, watch } from 'vue'
import MsfMemo from '../common/MsfMemo.vue'

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
const formData = useMsfFormOwnChgStore()
// const formData = reactive({
//   /* USIM 정보 */
//   hasSim: '', //SIM보유
//   usimKindsCd: '', //USIM 선택
//   reqUsimSn: '', //USIM 번호
//   simPurchaseMethod: '', //USIM 구매 방식
//   prodNm: '', //휴대폰 모델병
//   eid: '', //EID
//   imei1: '', //IMEI1
//   imei2: '', //IMEI2
//   /* 납부 정보 */
//   cstmrBillSendTypeCd: '', //수신유형
//   reqPayTypeCd: '', //납부방법
//   autoPayerType: '', //자동이체-납부자유형
//   reqBankCd: '', //자동이체-은행선택
//   reqAccountNo: '', //자동이체-계좌번호입력
//   reqAccountNm: '', //자동이체-납부고객명
//   reqAccountRrn: '', //자동이체-생년월일(8자리) 임력
//   reqAccountRelTypeCd: '', //자동이체-관계
//   isAutoAgree: false, //자동이체-동의
//   cardPayerType: '', //신용카드-납부자유형
//   reqCardCompanyCd: '', //신용카드-카드사선택
//   reqCardNo: '', //신용카드-카드번호입력
//   reqCardMm: '', //신용카드-유효기간(MM)
//   reqCardYy: '', //신용카드-유효기간(YY)
//   reqCardNm: '', //신용카드-납부고객명
//   reqCardRrn: '', //신용카드-생년월일
//   cardRelation: '', //신용카드-관계
//   combId: '', //통합청구-청구계정ID
//   combAgree: false, //통합청구-동의
//   /* 메모 */
//   memo: '', //메모
// })
</script>

<style scoped></style>
