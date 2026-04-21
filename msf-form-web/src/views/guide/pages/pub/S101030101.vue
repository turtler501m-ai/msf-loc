<template>
  <MsfTitleBar title="신규/변경 > 상품 퍼블확인용" />
  <div class="page-step-panel">
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
    <!-- 휴대폰 정보_상품 (휴대폰) -->
    <MsfTitleArea title="휴대폰 정보_상품 (휴대폰)" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="휴대폰 일련번호" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.imei" placeholder="휴대폰 일련번호 입력" class="ut-w-300" />
          <MsfButton variant="subtle">스캔하기</MsfButton>
          <MsfButton variant="toggle" disabled>일련번호 유효성 체크</MsfButton>
          <MsfButton variant="toggle">일련번호 유효성 체크</MsfButton>
          <MsfButton variant="toggle" active>일련번호 유효성 체크 완료</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 휴대폰 정보_상품 (휴대폰) -->
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
    <!-- 신규가입 번호 예약 -->
    <MsfTitleArea title="신규가입 번호 예약" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="번호예약" required>
        <MsfStack type="field">
          <MsfInput v-model="formData.reserve1" placeholder="앞 3자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.reserve2" id="inp-reserve2" placeholder="가운데 4자리" />
          <span class="unit-sep">-</span>
          <MsfInput v-model="formData.reserve3" id="inp-reserve3" placeholder="뒤 4자리" />
          <MsfButton variant="subtle">번호조회</MsfButton>
        </MsfStack>
        <p class="ut-text-desc">
          <span class="ut-text-count">조회 가능 횟수 <em>20회</em></span
          >※ 조회 가능 횟수를 초과할 경우 신청서를 재작성 해야 합니다.
        </p>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.wishNo"
            id="inp-wishNo"
            placeholder="선택된 희망 신규번호"
            class="ut-w-300"
            disabled
          />
          <MsfButton variant="toggle">선택취소</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 신규가입 번호 예약 -->
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
    <!-- 안심 보험 -->
    <MsfTitleArea title="안심 보험" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="안심 보험 가입" tag="div">
        <MsfChip
          v-model="formData.isInsured"
          name="inp-isInsured"
          :data="[
            { value: 'isInsured1', label: '가입' },
            { value: 'isInsured2', label: '미가입' },
          ]"
        />
        <MsfStack type="field" class="ut-w100p">
          <MsfSelect
            title="추천 카테고리"
            v-model="formData.recCat1"
            :options="[
              { label: '추천 카테고리1', value: 'recCat1-1' },
              { label: '추천 카테고리2', value: 'recCat1-2' },
            ]"
            placeholder="추천 카테고리"
            class="ut-w-300"
          />
          <MsfSelect
            title="추천 카테고리"
            v-model="formData.recCat2"
            :options="[
              { label: '안드로이드 플래티넘 / 월 기본료 4,900원1', value: 'recCat2-1' },
              { label: '안드로이드 플래티넘 / 월 기본료 4,900원2', value: 'recCat2-2' },
            ]"
            class="ut-flex-1"
          />
        </MsfStack>
      </MsfFormGroup>
    </MsfStack>
    <!-- // 안심 보험 -->
    <!-- 안심 보험 약관 동의  -->
    <MsfTitleArea title="안심 보험 약관 동의" />
    <MsfAgreementGroup policy="join" ref="agreementRef" required />
    <!-- // 안심 보험 약관 동의 -->
    <!-- 납부 정보 -->
    <MsfTitleArea title="납부 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="명세서 수신 유형" tag="div" required>
        <MsfChip
          v-model="formData.stmtType"
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
          v-model="formData.payMtd"
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
            v-model="formData.autoBankCode"
            :options="[
              { label: '은행 선택1', value: 'autoBankCode1' },
              { label: '은행 선택2', value: 'autoBankCode2' },
            ]"
            placeholder="은행 선택"
            class="ut-w-300"
          />
          <MsfInput
            v-model="formData.autoAcctNo"
            id="inp-autoAcctNo"
            placeholder="계좌번호 입력"
            class="ut-w-200"
          />
          <MsfButton variant="toggle">계좌번호 유효성 체크</MsfButton>
          <MsfButton variant="toggle" active>계좌번호 유효성 체크 완료</MsfButton>
        </MsfStack>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.autoPayerName"
            id="inp-autoPayerName"
            placeholder="납부 고객명"
            class="ut-w-300"
          />
          <MsfBirthdayInput
            v-model="formData.autoPayerBirth"
            id="inp-autoPayerBirth"
            length="8"
            class="ut-w-200"
          />
          <MsfSelect
            title="관계"
            v-model="formData.autoRelation"
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
            v-model="formData.cardCorp"
            :options="[
              { label: '카드사 선택1', value: 'cardCorp1' },
              { label: '카드사 선택2', value: 'cardCorp2' },
            ]"
            placeholder="카드사 선택"
            class="ut-w-300"
          />
          <MsfInput
            v-model="formData.cardNo"
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
            v-model="formData.cardExpMm"
            :options="[
              { label: '유효기간(MM)', value: 'cardExpMm1' },
              { label: '유효기간(YY)', value: 'cardExpMm2' },
            ]"
            placeholder="MM"
          />
          <MsfSelect
            title="유효기간(YY) 선택"
            v-model="formData.cardExpYy"
            :options="[
              { label: '유효기간(YY)', value: 'cardExpYy1' },
              { label: '유효기간(YY)', value: 'cardExpYy2' },
            ]"
            placeholder="YY"
          />
        </MsfStack>
        <MsfStack type="field">
          <MsfInput
            v-model="formData.cardPayerName"
            id="inp-cardPayerName"
            placeholder="납부 고객명"
            class="ut-w-300"
          />
          <MsfBirthdayInput
            v-model="formData.cardPayerBirth"
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
    <!-- 메모 -->
    <MsfTitleArea title="메모" />
    <MsfFormGroup label="메모" tag="div">
      <MsfTextarea v-model="formData.memo" placeholder="메모 입력" />
    </MsfFormGroup>
    <!-- // 메모 -->
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
