<template>
  <MsfTitleBar title="명의변경 신청서 > 상품 퍼블확인용" />
  <div class="page-step-panel">
    <!-- USIM 정보 -->
    <MsfTitleArea title="USIM 정보" />
    <MsfStack vertical type="formgroups">
      <MsfFormGroup label="SIM 보유" tag="div" required>
        <MsfChip
          v-model="formData.hasSim"
          name="inp-hasSim"
          :data="[
            { value: 'hasSim1', label: '현재 USIM 사용' },
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
          <MsfNumberInput
            v-model="formData.simNo"
            placeholder="USIM 번호 19자리"
            ariaLabel="USIM 번호 입력"
            class="ut-w-300"
          />
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
    <!-- // USIM 정보 -->
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
          <MsfNumberInput
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
    <MsfFormGroup label="메모">
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

// 퍼블 샘플
const formData = reactive({
  /* USIM 정보 */
  hasSim: '', //SIM보유
  simType: '', //USIM 선택
  simNo: '', //USIM 번호
  simPurchaseMethod: '', //USIM 구매 방식
  phoneModel: '', //휴대폰 모델병
  phoneEID: '', //EID
  phoneIMEI1: '', //IMEI1
  phoneIMEI2: '', //IMEI2
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
