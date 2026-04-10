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
  <!-- 번호이동 할 전화번호 -->
  <MsfTitleArea title="번호이동 할 전화번호" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="번호이동 할<br/>전화번호" required>
      <MsfStack type="field">
        <MsfSelect
          title="통신사 선택"
          v-model="formData.carrier"
          :options="[
              { label: '통신사1', value: 'agency1' },
              { label: '통신사2', value: 'agency2' },
            ]"
          class="ut-w-300"
          placeholder="통신사 선택"
        />
        <MsfInput
          v-model="formData.transferPhone"
          placeholder="휴대폰 번호 ‘-’ 없이 입력"
          class="ut-w-300"
        />
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup label="번호이동 인증" tag="div" required>
      <MsfStack type="field">
        <MsfChip
          v-model="formData.transferAuth"
          name="inp-transferAuth"
          :data="[
              { value: 'transferAuth1', label: '휴대폰 일련번호' },
              { value: 'transferAuth2', label: '요금납부 계좌번호' },
              { value: 'transferAuth3', label: '요금납부 신용카드' },
              { value: 'transferAuth4', label: '지로' },
            ]"
        />
      </MsfStack>
      <MsfStack type="field">
        <MsfInput
          v-model="formData.transferAuthNum"
          placeholder="휴대폰 일련번호 뒤 4자리"
          class="ut-w-300"
        />
        <MsfButton variant="subtle">번호이동 사전동의</MsfButton>
      </MsfStack>
      <MsfInput
        v-model="formData.transferBankNum"
        placeholder="요금납부 계좌번호 뒤 4자리"
        class="ut-w-300"
      />
      <MsfInput
        v-model="formData.transferCardNum"
        placeholder="요금납부 신용카드 뒤 4자리"
        class="ut-w-300"
      />
    </MsfFormGroup>
    <MsfFormGroup label="이번달 사용요금" tag="div" required>
      <MsfCheckbox
        v-model="formData.currentMonthFee"
        label="다음달 요금 합산 납부 (※ 번호이동 수수료 800원)"
      />
    </MsfFormGroup>
    <MsfFormGroup label="휴대폰 할부금" tag="div" required>
      <MsfCheckboxGroup
        v-model="formData.deviceInstallment"
        :options="[
            { value: 'deviceInstallment1', label: '완납' },
            { value: 'deviceInstallment2', label: '지속(이전 통신회사에 납부)' },
          ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="미환급금<br/>요금상계(후불)" tag="div" required>
      <MsfCheckboxGroup
        v-model="formData.offsetAmt"
        :options="[
            { value: 'offsetAmt1', label: '동의' },
            { value: 'offsetAmt2', label: '미동의' },
          ]"
      />
    </MsfFormGroup>
  </MsfStack>
  <!-- // 번호이동 할 전화번호 -->
</template>

<style scoped lang="scss">

</style>
