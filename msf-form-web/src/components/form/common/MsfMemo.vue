<script setup>
import { reactive } from 'vue'

const formData = reactive({
  /* SIM정보_상품(휴대폰) */
  hasSim: '', //SIM보유
  usimKindsCd: '', //USIM 선택
  reqUsimSn: '', //USIM 번호
  simPurchaseMethod: '', //USIM 구매 방식
  prodNm: '', //휴대폰 모델병
  eid: '', //EID
  imei1: '', //IMEI1
  imei2: '', //IMEI2
  /* 휴대폰 정보_상품 (휴대폰) */
  imei: '', //IMEI
  /* 번호이동 할 전화번호 */
  moveCompanyCd: '', //통신사 선택
  moveMobileNo: '', //번호이동 할 전화번호
  moveAuthTypeCd: '', //번호이동 인증
  moveAuthNo: '', //번호이동 인증 : 일련번호4자리
  transferBankNum: '', //번호이동 인증 : 계좌번호4자리
  transferCardNum: '', //번호이동 인증 : 신용카드4자리
  moveThismonthPayTypeCd: false, //이번달 사용요금
  moveAllotmentSttusCd: [], //휴대폰 할부금
  moveRefundAgreeYn: [], //미환급금 요금상계(후불)
  /* 신규가입 번호 예약 */
  reqWantFnNo: '', //번호예약 앞 3자리
  reqWantMnNo: '', //번호예약 가운데 4자리
  reqWantRnNo: '', //번호예약 뒤 4자리
  wishNo: '', //희망 신규번호
  /* 부가서비스 신청 */
  //무료부가서비스
  reqAdditionListNm: [
    'freeVas1',
    'freeVas2',
    'freeVas3',
    'freeVas4',
    'freeVas5',
    'freeVas6',
    'freeVas7',
    'freeVas8',
  ],
  addtionId: ['paidVas1'], //유료부가서비스
  /* 안심 보험 */
  clauseInsuranceYn: '', //가입여부
  recCat1: '', //추천 카테고리1
  recCat2: '', //추천 카테고리2
  /* 납부 정보 */
  cstmrBillSendTypeCd: '', //수신유형
  reqPayTypeCd: '', //납부방법
  autoPayerType: '', //자동이체-납부자유형
  reqBankCd: '', //자동이체-은행선택
  reqAccountNo: '', //자동이체-계좌번호입력
  reqAccountNm: '', //자동이체-납부고객명
  reqAccountRrn: '', //자동이체-생년월일(8자리) 임력
  reqAccountRelTypeCd: '', //자동이체-관계
  isAutoAgree: false, //자동이체-동의
  cardPayerType: '', //신용카드-납부자유형
  reqCardCompanyCd: '', //신용카드-카드사선택
  reqCardNo: '', //신용카드-카드번호입력
  reqCardMm: '', //신용카드-유효기간(MM)
  reqCardYy: '', //신용카드-유효기간(YY)
  reqCardNm: '', //신용카드-납부고객명
  reqCardRrn: '', //신용카드-생년월일
  cardRelation: '', //신용카드-관계
  combId: '', //통합청구-청구계정ID
  combAgree: false, //통합청구-동의
  /* 메모 */
  memo: '', //메모
})
</script>

<template>
  <MsfTitleArea title="메모" />
  <MsfFormGroup label="메모" tag="div">
    <MsfTextarea v-model="formData.memo" placeholder="메모 입력" />
  </MsfFormGroup>
</template>
