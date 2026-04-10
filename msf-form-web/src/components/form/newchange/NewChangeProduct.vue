<template>
  <div class="page-step-panel">
    <!-- SIM정보_상품(휴대폰) -->
    <MsfSimInfo/>
    <!-- // SIM정보_상품(휴대폰) -->
    <!-- SIM정보_상품(기기변경) -->
    <MsfTitleArea title="SIM 정보_상품(휴대폰)_기기변경인 경우" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="SIM 보유" tag="div" required>
        <MsfChip
          v-model="formData.hasSim2"
          name="inp-hasSim2"
          :data="[
            { value: 'hasSim2-1', label: '현재 USIM 사용' },
            { value: 'hasSim2-2', label: 'USIM 구매' },
          ]"
        />
      </MsfFormGroup>
    </MsfStack>
    <!-- // SIM정보_상품(기기변경) -->
    <!-- 휴대폰 정보_상품 (휴대폰) -->
    <MsfMobilePhoneInfo/>
    <!-- // 휴대폰 정보_상품 (휴대폰) -->
    <!-- 번호이동 할 전화번호 -->
    <MsfTelephoneNumberToMove/>
    <!-- // 번호이동 할 전화번호 -->
    <!-- 신규가입 번호 예약 -->
    <MsfNewJoinTelePhoneNumber/>
    <!-- // 신규가입 번호 예약 -->
    <!-- 부가서비스 신청 -->
    <MsfValueAdditonalServiceRequest/>
    <!-- // 부가서비스 신청 -->
    <!-- 안심 보험 -->
    <MsfSafetyInsurance/>
    <!-- // 안심 보험 -->
    <!-- 약관 동의  -->
    <MsfTitleArea title="약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" required />
    <!-- // 약관 동의 -->
    <!-- 납부 정보 -->
    <MsfPaymentInfo/>
    <!-- // 납부 정보 -->
    <!-- 메모 -->
    <MsfMemo/>
    <!-- // 메모 -->
    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
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
import { ref, watch, reactive } from 'vue'

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

const formData = reactive({
  /* SIM정보_상품(휴대폰) */
  hasSim: '', //SIM보유
  hasSim2: '', //SIM보유(기기변경)
  simType: '', //USIM 선택
  simNo: '', //USIM 번호
  simPurchaseMethod: '', //USIM 구매 방식
  phoneModel: '', //휴대폰 모델병
  phoneEID: '', //EID
  phoneIMEI1: '', //IMEI1
  phoneIMEI2: '', //IMEI2
  /* 휴대폰 정보_상품 (휴대폰) */
  imei: '', //IMEI
  /* 번호이동 할 전화번호 */
  carrier: '', //통신사 선택
  transferPhone: '', //번호이동 할 전화번호
  transferAuth: '', //번호이동 인증
  transferAuthNum: '', //번호이동 인증 : 일련번호4자리
  transferBankNum: '', //번호이동 인증 : 계좌번호4자리
  transferCardNum: '', //번호이동 인증 : 신용카드4자리
  currentMonthFee: false, //이번달 사용요금
  deviceInstallment: [], //휴대폰 할부금
  offsetAmt: [], //미환급금 요금상계(후불)
  /* 신규가입 번호 예약 */
  reserve1: '', //번호예약 앞 3자리
  reserve2: '', //번호예약 가운데 4자리
  reserve3: '', //번호예약 뒤 4자리
  wishNo: '', //희망 신규번호
  /* 부가서비스 신청 */
  //무료부가서비스
  freeVas: [
    'freeVas1',
    'freeVas2',
    'freeVas3',
    'freeVas4',
    'freeVas5',
    'freeVas6',
    'freeVas7',
    'freeVas8',
  ],
  paidVas: ['paidVas1'], //유료부가서비스
  /* 안심 보험 */
  isInsured: '', //가입여부
  recCat1: '', //추천 카테고리1
  recCat2: '', //추천 카테고리2
  /* 납부 정보 */
  stmtType: '', //수신유형
  payMtd: '', //납부방법
  autoPayerType: '', //자동이체-납부자유형
  autoBankCode: '', //자동이체-은행선택
  autoAcctNo: '', //자동이체-계좌번호입력
  autoPayerName: '', //자동이체-납부고객명
  autoPayerBirth: '', //자동이체-생년월일(8자리) 임력
  autoRelation: '', //자동이체-관계
  isAutoAgree: false, //자동이체-동의
  cardPayerType: '', //신용카드-납부자유형
  cardCorp: '', //신용카드-카드사선택
  cardNo: '', //신용카드-카드번호입력
  cardExpMm: '', //신용카드-유효기간(MM)
  cardExpYy: '', //신용카드-유효기간(YY)
  cardPayerName: '', //신용카드-납부고객명
  cardPayerBirth: '', //신용카드-생년월일
  cardRelation: '', //신용카드-관계
  combId: '', //통합청구-청구계정ID
  combAgree: false, //통합청구-동의
  /* 메모 */
  memo: '', //메모
})
</script>

<style scoped></style>
