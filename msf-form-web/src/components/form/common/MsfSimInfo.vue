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
  <!-- SIM정보_상품(휴대폰) -->
  <MsfTitleArea title="SIM정보_상품(휴대폰)" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="SIM 보유" tag="div" required>
      <MsfChip
        v-model="formData.hasSim"
        name="inp-hasSim"
        :data="[
            { value: 'hasSim1', label: 'USIM 보유' },
            { value: 'hasSim2', label: 'USIM 구매' },
            { value: 'hasSim3', label: 'eSIM' },
          ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="USIM 선택" tag="div" required>
      <MsfChip
        v-model="formData.simType"
        name="inp-simType"
        :data="[
            { value: 'simType1', label: '일반 6,600원' },
            { value: 'simType2', label: 'NFC 8,800원' },
          ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="USIM 번호" required>
      <MsfStack type="field">
        <MsfInput v-model="formData.simNo" placeholder="USIM 번호 19자리" class="ut-w-300" />
        <MsfButton variant="subtle">스캔하기</MsfButton>
        <MsfButton variant="toggle" disabled>USIM 번호 유효성 체크</MsfButton>
        <MsfButton variant="toggle">USIM 번호 유효성 체크</MsfButton>
        <MsfButton variant="toggle" active>USIM 번호 유효성 체크</MsfButton>
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup label="USIM 구매 방식" tag="div" required>
      <MsfChip
        v-model="formData.simPurchaseMethod"
        name="inp-simPurchaseMethod"
        :data="[
            { value: 'simPurchaseMethod1', label: '즉시납부' },
            { value: 'simPurchaseMethod2', label: '다음달 요금에 합산' },
          ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="휴대폰 정보" required>
      <MsfInput
        v-model="formData.phoneModel"
        placeholder="휴대폰 모델명"
        class="ut-w-300"
        disabled
      />
      <MsfInput
        v-model="formData.phoneEID"
        id="inp-phoneEID"
        placeholder="EID"
        class="ut-w-608"
        disabled
      />
      <MsfStack type="field">
        <MsfInput
          v-model="formData.phoneIMEI1"
          id="inp-phoneIMEI1"
          placeholder="IMEI1"
          class="ut-w-300"
          disabled
        />
        <MsfInput
          v-model="formData.phoneIMEI2"
          id="inp-phoneIMEI2"
          placeholder="IMEI2"
          class="ut-w-300"
          disabled
        />
      </MsfStack>
      <MsfButton variant="toggle">이미지 등록</MsfButton>
      <MsfButton variant="toggle" active>이미지 등록 완료</MsfButton>
    </MsfFormGroup>
  </MsfStack>
  <!-- // SIM정보_상품(휴대폰) -->
</template>

<style scoped lang="scss">

</style>
