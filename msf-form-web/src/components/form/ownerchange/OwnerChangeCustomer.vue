<template>
  <div class="page-step-panel">
    <div class="ut-flex ut-gap-10 ut-v-center ut-mb-10" style="justify-content: flex-end">
      <span style="font-size: 12px; color: #999">[테스트용]</span>
      <MsfButton variant="secondary" size="small" @click="onClickTestLoad"
        >임시저장 불러오기</MsfButton
      >
    </div>
    <!-- 고객(양도고객) 유형 -->
    <MsfCustomerType
      v-model="formData.tr_customer"
      title="고객(양도고객) 유형"
      :name="'tr'"
      ref="trCustomerTypeRef"
      :authFlags="store.authFlags"
    />
    <!-- // 고객(양도고객) 유형 -->
    <!-- 고객(양도고객) 신분증 확인 -->
    <MsfIdentityVerify
      v-model="formData.tr_customer"
      :title="trCustomerTitle.identityVerifyTitle"
      ref="trIdentityVerifyRef"
      :authFlags="store.authFlags"
    />
    <!-- // 고객(양도고객) 신분증 확인 -->
    <!-- 고객(양도고객) 정보 -->
    <MsfSubscriberInfo
      v-model="formData.tr_customer"
      :preCheckFunc="trCustomerDeviceChgVerify"
      phoneLabel="명의변경 휴대폰번호"
      :title="trCustomerTitle.subscriberInfo"
      :name="'tr'"
      :isEditable="true"
      ref="trSubscriberInfoRef"
      :authFlags="store.authFlags"
    />
    <!-- // 고객(양도고객) 정보 -->
    <!-- 고객(양도고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo
      v-model="formData.tr_customer"
      :name="'minor-tr'"
      agreementTitle="고객(양도고객) 법정대리인 안내사항 확인 및 동의"
      title="고객(양도고객) 법정대리인 정보"
      ref="trLegalAgentInfoRef"
      :authFlags="store.authFlags"
    />
    <!-- // 고객(양도고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <!-- 고객(양수고객) 유형 -->
    <MsfCustomerType
      v-model="formData.te_customer"
      title="고객(양수고객) 유형"
      :name="'te'"
      ref="teCustomerTypeRef"
      :authFlags="store.authFlags"
      :allowedCodes="teAllowedCodes"
    />
    <!-- // 고객(양수고객) 유형 -->
    <!-- 고객(양수고객) 신분증 확인 -->
    <MsfIdentityVerify
      v-model="formData.te_customer"
      :title="teCustomerTitle.identityVerifyTitle"
      ref="teIdentityVerifyRef"
      :authFlags="store.authFlags"
    />
    <!-- // 고객(양수고객) 신분증 확인 -->
    <!-- 고객(양수고객) 정보 -->
    <MsfSubscriberInfo
      v-model="formData.te_customer"
      :title="teCustomerTitle.subscriberInfo"
      phoneLabel="명의변경 휴대폰번호"
      :name="'te'"
      ref="teSubscriberInfoRef"
      :authFlags="store.authFlags"
    />
    <!-- // 고객(양수고객) 정보 -->
    <!-- 고객(양수고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo
      v-model="formData.te_customer"
      title="고객(양수고객) 법정대리인 정보"
      ref="teLegalAgentInfoRef"
      :authFlags="store.authFlags"
    />
    <!-- 고객(양수고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <!-- 고객(실사용자) 정보 -->
    <MsfRealUserInfo
      v-model="formData.realUserInfo"
      name="real"
      v-if="formData.te_customer?.cstmrVisitTypeCd === 'V2'"
      ref="realUserInfoRef"
    />
    <!-- // 고객(실사용자) 정보 -->
    <!-- 대리인 위임 정보 -->
    <MsfDelegateInfo
      v-model="formData"
      v-if="formData.te_customer?.cstmrVisitTypeCd === 'V2'"
      ref="delegateInfoRef"
    />
    <!-- // 대리인 위임 정보 -->
    <!-- 구비서류 -->
    <MsfRequiredDoc
      v-model="formData.te_customer"
      ref="requiredDocRef"
      :authFlags="store.authFlags"
    />
    <!-- // 구비서류 -->
    <!-- 고객(양수고객) 연락처 -->
    <MsfContactInfo
      v-model="formData.te_customer"
      title="고객(양수고객) 연락처"
      ref="contactInfoRef"
    />
    <!-- // 고객(양수고객) 연락처 -->
    <!-- 요금제 정보 -->
    <MsfChargePlanInfo v-model="formData.planInfo" ref="devicePlanInfoRef" />
    <!-- // 요금제 정보 -->
    <!-- 약관 동의  -->
    <MsfTermsAgreement
      ref="termsAgreementRef"
      v-model="formData"
      policy="CLAUSE_FORM_03"
      :specTerms="termList"
      :isSaved="formData.isSaved"
      @checked="termsInfoUpdate"
      required
    />
    <!-- // 약관 동의 -->

    <!-- (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
    <div class="ut-mt-50">
      <div>
        <p>- 개발해주신 부분 - 화면 프로세스</p>
        <select v-model="isComplete">
          <option value="">고객 저장</option>
          <option :value="true">성공</option>
          <option :value="false">실패</option>
        </select>
      </div>
    </div>
    <!-- // (화면테스트용 소스영역) 추후 지우셔도 되는것 -->
  </div>
</template>

<script setup>
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'
import { ref, computed, watch, nextTick } from 'vue'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const store = useMsfFormOwnChgStore()
const { formData, trCustomerTitle, teCustomerTitle } = storeToRefs(store)
const isComplete = ref(false)
const termList = ref([])

// 컴포넌트 Refs
const trCustomerTypeRef = ref(null)
const trIdentityVerifyRef = ref(null)
const trSubscriberInfoRef = ref(null)
const trLegalAgentInfoRef = ref(null)
const teCustomerTypeRef = ref(null)
const teIdentityVerifyRef = ref(null)
const teSubscriberInfoRef = ref(null)
const teLegalAgentInfoRef = ref(null)
const realUserInfoRef = ref(null)
const delegateInfoRef = ref(null)
const requiredDocRef = ref(null)
const contactInfoRef = ref(null)
const devicePlanInfoRef = ref(null)
const termsAgreementRef = ref(null)

const MINOR_CODES = ['NM', 'FM']

// 양도고객이 미성년자인 경우 양수고객도 미성년자 유형만 선택 가능
const teAllowedCodes = computed(() => {
  return MINOR_CODES.includes(formData.value.tr_customer.cstmrTypeCd) ? MINOR_CODES : []
})

// 값이 변할 때마다 상위 컴포넌트에게 필수 입력 결과를 알려준다.
watch(
  () => isComplete.value,
  (newVal) => {
    isComplete.value = newVal
    emit('complete', newVal ? true : false)
  },
)

const onClickTestLoad = async () => {
  // ===== 고객(양도고객) 유형 =====
  formData.value.tr_customer.cstmrTypeCd = 'NA'
  formData.value.tr_customer.identityCertTypeCd = 'S' // 인증예외 처리로 신분증 확인 bypass
  formData.value.tr_customer.cstmrNm = '홍길동'
  formData.value.tr_customer.userBirthDate = '19901010'
  formData.value.tr_customer.userGender = 'M'
  formData.value.tr_customer.deviceChgTel1 = '010'
  formData.value.tr_customer.deviceChgTel2 = '1234'
  formData.value.tr_customer.deviceChgTel3 = '5678'

  // ===== 고객(양수고객) 유형 =====
  formData.value.te_customer.cstmrTypeCd = 'NA'
  formData.value.te_customer.cstmrVisitTypeCd = 'V1'
  formData.value.te_customer.identityCertTypeCd = 'S' // 인증예외 처리로 신분증 확인 bypass
  formData.value.te_customer.cstmrNm = '김철수'
  formData.value.te_customer.cstmrNativeRrn1 = '850505'
  formData.value.te_customer.cstmrNativeRrn2 = '1234567'

  // ===== 고객(양수고객) 연락처 =====
  formData.value.te_customer.mobileNo1 = '010'
  formData.value.te_customer.mobileNo2 = '9876'
  formData.value.te_customer.mobileNo3 = '5432'
  formData.value.te_customer.telNo1 = '02'
  formData.value.te_customer.telNo2 = '1234'
  formData.value.te_customer.telNo3 = '5678'
  formData.value.te_customer.emailAddr1 = 'test'
  formData.value.te_customer.emailAddr2 = 'test.com'
  formData.value.te_customer.zip = '12345'
  formData.value.te_customer.zipNo = '12345' // MsfContactInfo가 zipNo를 검사
  formData.value.te_customer.address = '서울시 강남구 테헤란로 123'
  formData.value.te_customer.detailAddress = '101동 101호'

  // ===== 요금제 =====
  formData.value.planInfo.planName2 = 'TEST_PLAN_001'
  formData.value.planInfo.planNm = '테스트 요금제'
  formData.value.planInfo.agency = '테스트 대리점'

  // ===== 납부정보 =====
  formData.value.productPayment.cstmrBillSendTypeCd = 'E' // 전자명세서
  formData.value.productPayment.reqPayTypeCd = 'AA' // 자동이체
  formData.value.productPayment.othersPaymentYn = 'N'
  formData.value.productPayment.reqBankCd = '020'
  formData.value.productPayment.reqAccountNo = '1234567890'
  formData.value.productPayment.isAutoAgree = true

  // ===== USIM (Step 2) =====
  formData.value.usimInfo.hasSim = 'hasSim1' // USIM 승계 - validate 즉시 통과

  // ===== 메모 =====
  formData.value.memo = '테스트 메모'

  // ===== Step 3: 최종 동의 및 녹취 =====
  formData.value.agreeCheck1 = true
  formData.value.agreeCheck2 = true
  formData.value.agreeCheck3 = true
  formData.value.agreeCheck4 = true
  formData.value.agreeCheck5 = true
  formData.value.agreeCheck6 = true
  formData.value.recYn = 'Y'

  // ===== authFlags 설정 =====
  store.authFlags.deviceChgTel = true
  store.authFlags.autoAcct = true
  store.authFlags.requiredDocs = true

  // ===== 약관 전부 동의 (DOM 업데이트 후 실행) =====
  await nextTick()
  termsAgreementRef.value?.setAllChecked()
}

onMounted(async () => {
  const list = await getCommonCodeList('CLAUSE_FORM_03')

  const exposeTermList = [
    'CLAUSE_CNTR_DEL_01', //양도인고객정보 삭제동의여부
    'CLAUSE_REQUIRED_01', //개인정보 제3자 제공
    'CLAUSE_REQUIRED_03', //개인정보제공동의
    'CLAUSE_REQUIRED_02', //고유식별정보수집
    'CLAUSE_SELECT_01', //개인정보광고전송동의
    'CLAUSE_PARTNER_01', //금융제휴약관동의
    'CLAUSE_SELECT_TIT_01', //고객혜택제공을위한개인정보수집및이용관련동의여부
    'CLAUSE_SELECT_04', //혜택제공을위한제3자제공동의(M모바일)여부
    'CLAUSE_SELECT_06', //혜택제공을위한제3자제공동의(KT)여부
    'CLAUSE_SELECT_07', //제3자제공관련광고수신동의여부
  ]

  termList.value = list
    ?.map((item) => ({
      ...item,
      label: item.title,
      value: item.code,
    }))
    .filter((item) => exposeTermList.includes(item.value))
})

const trCustomerDeviceChgVerify = async (paramObj) => {
  try {
    const res = await post('/api/form/owner-change/validate', paramObj)

    if (res.data.resultCd === '00') {
      const { data } = res
      const { ncn, ctn, custId, userId, fstEsimYn } = data.response
      store.updateTrCustomer({ ncn, ctn, custId, userId })
      store.updateUsimInfo({ hasSim: fstEsimYn !== 'Y' ? 'hasSim1' : 'hasSim3' })
      store.updatePlanInfo({ ctn, ncn, custId, userId })
      return true
    } else {
      showAlert(res.data.message)
      return false
    }
  } catch (e) {
    console.log(e)
    return false
  }
}

// 약관 동의 정보 업데이트
const termsInfoUpdate = (result) => {
  const choiceTerm = {
    CLAUSE_SELECT_04: 'othersTrnsAgreeYn',
    CLAUSE_SELECT_06: 'othersTrnsKtAgreeYn',
    CLAUSE_SELECT_07: 'othersAdReceiveAgreeYn',
  }

  result.forEach((term) => {
    if (choiceTerm[term.code]) {
      formData.value[choiceTerm[term.code]] = term.checked ? 'Y' : 'N'
    }
  })

  validate()
}

watch(
  () => store.authFlags,
  () => {
    validate()
  },
  { deep: true },
)

watch(
  () => formData.value,
  () => {
    validate()
  },
  { deep: true },
)

const reset = async () => {
  store.resetCustomer()
  await nextTick()
  termsAgreementRef.value?.reset?.()
  validate()
}

const data = async (code /* 임시저장 코드 */) => {
  // 임시저장 정보 조회
  if (code) {
    // 임시저장된 데이터 조회 후, 임시저장단계코드 리턴
    // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
    return '1'
  }

  return '0' // 결과값 - null 또는 0: 임시저장 없음, 고객: 1, 상품: 2, 동의: 3
}

const save = async () => {
  //  데이터 임시저장
  return isComplete.value === true
}

// 현재 단계(Customer)의 모든 컴포넌트 유효성 검사
const validate = () => {
  const validations = [
    trCustomerTypeRef.value?.validate(),
    trIdentityVerifyRef.value?.validate(),
    trSubscriberInfoRef.value?.validate(),
    trLegalAgentInfoRef.value?.validate(),
    teCustomerTypeRef.value?.validate(),
    teIdentityVerifyRef.value?.validate(),
    teSubscriberInfoRef.value?.validate(),
    teLegalAgentInfoRef.value?.validate(),
    realUserInfoRef.value?.validate(),
    delegateInfoRef.value?.validate(),
    requiredDocRef.value?.validate(),
    contactInfoRef.value?.validate(),
    devicePlanInfoRef.value?.validate(),
    termsAgreementRef.value?.validate(),
  ]
  // null(비노출 컴포넌트)은 제외하고 모든 결과가 true인지 확인
  const isReady = validations.filter((v) => v !== undefined).every((v) => v === true)
  isComplete.value = isReady
  return isReady
}

defineExpose({ data, save, validate, reset })
</script>

<style scoped></style>
