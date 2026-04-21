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

const validate = () => {
  if (!formData.cstmrBillSendTypeCd) return false
  if (!formData.reqPayTypeCd) return false

  if (formData.reqPayTypeCd === 'payMtd1') {
    if (!formData.autoPayerType || !formData.reqBankCd || !formData.reqAccountNo) return false
    if (!formData.reqAccountNm || !formData.reqAccountRrn || !formData.reqAccountRelTypeCd)
      return false
    if (!formData.isAutoAgree) return false
  } else if (formData.reqPayTypeCd === 'payMtd2') {
    if (!formData.cardPayerType || !formData.reqCardCompanyCd || !formData.reqCardNo) return false
    if (
      !formData.reqCardMm ||
      !formData.reqCardYy ||
      !formData.reqCardNm ||
      !formData.reqCardRrn ||
      !formData.cardRelation
    )
      return false
  } else if (formData.reqPayTypeCd === 'payMtd3') {
    if (!formData.combId) return false
    if (!formData.combAgree) return false
  }

  return true
}

defineExpose({ validate })
</script>

<template>
  <!-- 납부 정보 -->
  <MsfTitleArea title="납부 정보" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="명세서 수신 유형" tag="div" required>
      <MsfChip
        v-model="formData.cstmrBillSendTypeCd"
        name="inp-stmtType"
        :data="[
          { value: 'stmtType1', label: '모바일 명세서' },
          { value: 'stmtType2', label: '이메일 명세서' },
          { value: 'stmtType3', label: '우편 명세서' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="요금 납부 방법" tag="div" required>
      <MsfChip
        v-model="formData.reqPayTypeCd"
        name="inp-payMtd"
        :data="[
          { value: 'payMtd1', label: '자동이체' },
          { value: 'payMtd2', label: '신용카드' },
          { value: 'payMtd3', label: '통합청구' },
        ]"
      />
      <!-- 자동이체 -->
      <hr class="ut-line" />
      <MsfStack type="field" class="ut-w100p">
        <MsfChip
          v-model="formData.autoPayerType"
          name="inp-autoPayerType"
          :data="[
            { value: 'autoPayerType1', label: '본인납부' },
            { value: 'autoPayerType2', label: '타인납부' },
          ]"
        />
      </MsfStack>
      <MsfStack type="field">
        <MsfSelect
          title="은행 선택"
          v-model="formData.reqBankCd"
          :options="[
            { label: '은행 선택1', value: 'autoBankCode1' },
            { label: '은행 선택2', value: 'autoBankCode2' },
          ]"
          placeholder="은행 선택"
          class="ut-w-300"
        />
        <MsfNumberInput
          v-model="formData.reqAccountNo"
          id="inp-autoAcctNo"
          placeholder="계좌번호 입력"
          class="ut-w-200"
        />
        <MsfButton variant="toggle">계좌번호 유효성 체크</MsfButton>
        <MsfButton variant="toggle" active>계좌번호 유효성 체크 완료</MsfButton>
      </MsfStack>
      <MsfStack type="field">
        <MsfInput
          v-model="formData.reqAccountNm"
          id="inp-autoPayerName"
          placeholder="납부 고객명"
          class="ut-w-300"
        />
        <MsfBirthdayInput
          v-model="formData.reqAccountRrn"
          id="inp-autoPayerBirth"
          length="8"
          class="ut-w-200"
        />
        <MsfSelect
          title="관계"
          v-model="formData.reqAccountRelTypeCd"
          :options="[
            { label: '가족 선택1', value: 'autoRelation1' },
            { label: '가족 선택2', value: 'autoRelation2' },
          ]"
          placeholder="관계"
        />
      </MsfStack>
      <MsfCheckbox
        v-model="formData.isAutoAgree"
        label="본인(예금주 또는 가입고객)은 납부해야 할 요금에 대해 위 계좌(카드)에서 지정된 출금(결제)일에 인출(결제)되는 것에 동의합니다."
        :invalid="!formData.isAutoAgree"
        class="ut-mt-8"
      />
      <!-- // 자동이체 -->
      <!-- 신용카드 -->
      <hr class="ut-line" />
      <MsfStack type="field" class="ut-w100p">
        <MsfChip
          v-model="formData.cardPayerType"
          name="inp-cardPayerType"
          :data="[
            { value: 'cardPayerType1', label: '본인납부' },
            { value: 'cardPayerType2', label: '타인납부' },
          ]"
        />
      </MsfStack>
      <MsfStack type="field">
        <MsfSelect
          title="카드사 선택"
          v-model="formData.reqCardCompanyCd"
          :options="[
            { label: '카드사 선택1', value: 'cardCorp1' },
            { label: '카드사 선택2', value: 'cardCorp2' },
          ]"
          placeholder="카드사 선택"
          class="ut-w-300"
        />
        <MsfNumberInput
          v-model="formData.reqCardNo"
          id="inp-cardNo"
          placeholder="카드번호 입력"
          class="ut-w-200"
        />
        <MsfButton variant="toggle">신용카드 유효성 체크</MsfButton>
        <MsfButton variant="toggle" active>신용카드 유효성 체크 완료</MsfButton>
      </MsfStack>
      <MsfStack type="field">
        <MsfSelect
          title="유효기간(MM) 선택"
          v-model="formData.reqCardMm"
          :options="[
            { label: '유효기간(MM)', value: 'cardExpMm1' },
            { label: '유효기간(YY)', value: 'cardExpMm2' },
          ]"
          placeholder="MM"
        />
        <MsfSelect
          title="유효기간(YY) 선택"
          v-model="formData.reqCardYy"
          :options="[
            { label: '유효기간(YY)', value: 'cardExpYy1' },
            { label: '유효기간(YY)', value: 'cardExpYy2' },
          ]"
          placeholder="YY"
        />
      </MsfStack>
      <MsfStack type="field">
        <MsfInput
          v-model="formData.reqCardNm"
          id="inp-cardPayerName"
          placeholder="납부 고객명"
          class="ut-w-300"
        />
        <MsfBirthdayInput
          v-model="formData.reqCardRrn"
          id="inp-cardPayerBirth"
          length="8"
          class="ut-w-200"
        />
        <MsfSelect
          title="관계"
          v-model="formData.cardRelation"
          :options="[
            { label: '관계1', value: 'cardRelation1' },
            { label: '관계2', value: 'cardRelation2' },
          ]"
          placeholder="관계"
        />
      </MsfStack>
      <!-- // 신용카드 -->
      <!-- 통합청구 -->
      <MsfStack type="field">
        <MsfInput
          v-model="formData.combId"
          id="inp-combId"
          placeholder="청구계정ID 입력"
          class="ut-w-300"
        />
        <MsfButton variant="toggle">청구계정 체크</MsfButton>
        <MsfButton variant="toggle" active>청구계정 체크 완료</MsfButton>
      </MsfStack>
      <MsfCheckbox
        v-model="formData.combAgree"
        label="본인은 신청한 회선과 통합하여 요금이 청구되는 것에 동의합니다."
        :invalid="!formData.combAgree"
        class="ut-mt-8"
      />
      <!-- // 통합청구 -->
    </MsfFormGroup>
  </MsfStack>
  <!-- // 납부 정보 -->
</template>

<style scoped lang="scss"></style>
