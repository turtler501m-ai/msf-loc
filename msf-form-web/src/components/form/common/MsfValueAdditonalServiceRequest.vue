<script setup>
import {reactive} from "vue";

const formData = reactive({
  /* SIM정보_상품(휴대폰) */
  hasSim: '', //SIM보유
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

<template>
  <!-- 부가서비스 신청 -->
  <MsfTitleArea title="부가서비스 신청" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="무료부가서비스" tag="div">
      <MsfChip
        v-model="formData.freeVas"
        name="inp-freeVas"
        :data="[
            { value: 'freeVas1', label: '무선데이터 차단' },
            { value: 'freeVas2', label: '국제전화발신제한' },
            { value: 'freeVas3', label: '익명호수신거부' },
            { value: 'freeVas4', label: '네트워크유해차단' },
            { value: 'freeVas5', label: '데이터로밍완전차단' },
            { value: 'freeVas6', label: '링투유알리미' },
            { value: 'freeVas7', label: '무선데이터 차단' },
            { value: 'freeVas8', label: '국제전화발신제한' },
          ]"
        multiple
        readonly
      />
    </MsfFormGroup>
    <MsfFormGroup label="유료부가서비스" tag="div">
      <MsfCheckboxGroup
        v-model="formData.paidVas"
        :options="[
            { value: 'paidVas1', label: '링투유 990 원' },
            { value: 'paidVas2', label: '캐치콜 550 원' },
            { value: 'paidVas3', label: '(신)로밍 하루종일 ON 2,200 원/1일' },
          ]"
      />
    </MsfFormGroup>
  </MsfStack>
  <!-- // 부가서비스 신청 -->
</template>

<style scoped lang="scss">

</style>
