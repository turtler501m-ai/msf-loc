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
  <!-- 번호이동 할 전화번호 -->
  <MsfTitleArea title="번호이동 할 전화번호" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="번호이동 할<br/>전화번호" required>
      <MsfStack type="field">
        <MsfSelect
          title="통신사 선택"
          v-model="formData.moveCompanyCd"
          :options="[
            { label: '통신사1', value: 'agency1' },
            { label: '통신사2', value: 'agency2' },
          ]"
          class="ut-w-300"
          placeholder="통신사 선택"
        />
        <MsfStack type="field">
          <MsfNumberInput v-model="formData.moveMobileNo1" placeholder="앞자리" maxlength="3" />
          <span class="unit-sep">-</span>
          <MsfNumberInput
            v-model="formData.moveMobileNo2"
            placeholder="가운데 4자리"
            maxlength="4"
          />
          <span class="unit-sep">-</span>
          <MsfNumberInput v-model="formData.moveMobileNo3" placeholder="뒤 4자리" maxlength="4" />
        </MsfStack>
      </MsfStack>
    </MsfFormGroup>
    <MsfFormGroup label="번호이동 인증" tag="div" required>
      <MsfStack type="field">
        <MsfChip
          v-model="formData.moveAuthTypeCd"
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
        <MsfNumberInput
          v-model="formData.moveAuthNo"
          placeholder="휴대폰 일련번호 뒤 4자리"
          class="ut-w-300"
          maxlength="4"
        />
        <MsfButton variant="subtle">번호이동 사전동의</MsfButton>
      </MsfStack>
      <MsfNumberInput
        v-model="formData.transferBankNum"
        placeholder="요금납부 계좌번호 뒤 4자리"
        class="ut-w-300"
        maxlength="4"
      />
      <MsfNumberInput
        v-model="formData.transferCardNum"
        placeholder="요금납부 신용카드 뒤 4자리"
        class="ut-w-300"
        maxlength="4"
      />
    </MsfFormGroup>
    <MsfFormGroup label="이번달 사용요금" tag="div" required>
      <MsfCheckbox
        v-model="formData.moveThismonthPayTypeCd"
        label="다음달 요금 합산 납부 (※ 번호이동 수수료 800원)"
      />
    </MsfFormGroup>
    <MsfFormGroup label="휴대폰 할부금" tag="div" required>
      <MsfCheckboxGroup
        v-model="formData.moveAllotmentSttusCd"
        :options="[
          { value: 'deviceInstallment1', label: '완납' },
          { value: 'deviceInstallment2', label: '지속(이전 통신회사에 납부)' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="미환급금<br/>요금상계(후불)" tag="div" required>
      <MsfCheckboxGroup
        v-model="formData.moveRefundAgreeYn"
        :options="[
          { value: 'offsetAmt1', label: '동의' },
          { value: 'offsetAmt2', label: '미동의' },
        ]"
      />
    </MsfFormGroup>
  </MsfStack>
  <!-- // 번호이동 할 전화번호 -->
</template>

<style scoped lang="scss"></style>
