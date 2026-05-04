<template>
  <div class="page-step-panel">
    <div class="ut-flex ut-gap-10 ut-v-center ut-mb-10" style="justify-content: flex-end">
      <span style="font-size: 12px; color: #999">[테스트용]</span>
      <MsfButton variant="secondary" size="small" @click="onClickTestLoad"
        >임시저장 불러오기</MsfButton
      >
      <MsfButton variant="secondary" size="small" @click="onClickJumpToAgreement"
        >동의스탭까지 강제이동</MsfButton
      >
    </div>
    <MsfProductJoinType ref="productJoinTypeRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfCustomerType ref="customerTypeRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfIdentityVerify ref="identityVerifyRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfSubscriberInfo
      ref="subscriberInfoRef"
      v-model="formData"
      :authFlags="store.authFlags"
      phoneLabel="기기변경<br/>휴대폰번호"
    />
    <MsfLegalAgentInfo ref="legalAgentInfoRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfRealUserInfo
      ref="realUserInfoRef"
      v-model="formData"
      v-if="['JP', 'GO'].includes(formData.cstmrTypeCd) && formData.cstmrVisitTypeCd === 'V2'"
    />
    <MsfDelegateInfo
      ref="delegateInfoRef"
      v-model="formData"
      v-if="['JP', 'GO'].includes(formData.cstmrTypeCd) && formData.cstmrVisitTypeCd === 'V2'"
    />
    <MsfRequiredDoc ref="requiredDocRef" v-model="formData" :authFlags="store.authFlags" />
    <MsfContactInfo ref="contactInfoRef" v-model="formData" />

    <MsfDevicePlanInfo ref="devicePlanInfoRef" v-model="formData" />

    <MsfTermsAgreement
      ref="termsAgreementRef"
      v-model="formData"
      :termsData="filteredTermsDataList"
      :isSaved="formData.isSaved"
      @checked="checkRequiredFields"
      required
    />
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from 'vue'
import { useMsfFormNewChgStore } from '@/stores/msf_newchange.js'
import { useMsfStepStore } from '@/stores/msf_step'
import { getCommonCodeList } from '@/libs/utils/comn.utils'

const store = useMsfFormNewChgStore()
const stepStore = useMsfStepStore()
const formData = store.customer
const productData = store.product

const rawTermsList = ref([])

// API로 내려온 dtlCode(예: CLAUSE_MOVE_01)를 실제 저장용 변수명으로 매핑 (없으면 그대로)
const saveKeyMap = {
  CLAUSE_MOVE_01: 'clauseMoveCode',
  CLAUSE_REQUIRED_02: 'clauseEssCollectYn',
  CLAUSE_REQUIRED_01: 'clausePriCollectYn',
  CLAUSE_REQUIRED_03: 'clausePriOfferYn',
  CLAUSE_FATH_01: 'clauseFathFlag01',
  CLAUSE_FATH_02: 'clauseFathFlag02',
  CLAUSE_REQUIRED_06: 'nwBlckAgrmYn',
  CLAUSE_REQUIRED_07: 'appBlckAgrmYn',
  CLAUSE_REQUIRED_5G: 'clause5gCoverage',
  CLAUSE_PARTNER_01: 'clauseJehuFlag',
  CLAUSE_PARTNER_02: 'clausePartnerOfferFlag',
  CLAUSE_SELECT_03: 'personalInfoCollectAgreeYn',
  CLAUSE_SELECT_01: 'clausePriAdYn',
  CLAUSE_SELECT_08: 'othersAdReceiveAgreeYn',
  CLAUSE_SELECT_04: 'othersTrnsAgreeYn',
  CLAUSE_SELECT_06: 'othersTrnsKtAgreeYn',
  CLAUSE_SELECT_07: 'othersAdReceiveAgreeYn',
  CLAUSE_SELECT_10: 'personalLocationAgreeYn',
  CLAUSE_INFO_01: 'clauseInfo01',
}

const termsDataList = computed(() => {
  const baseList =
    rawTermsList.value.length > 0
      ? rawTermsList.value
      : [
          {
            code: 'CLAUSE_MOVE_01',
            title: '번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법 동의',
            required: true,
          },
          { code: 'CLAUSE_REQUIRED_02', title: '고유식별정보 수집·이용 동의', required: true },
          { code: 'CLAUSE_REQUIRED_01', title: '개인정보/신용정보 수집·이용 동의', required: true },
          { code: 'CLAUSE_REQUIRED_03', title: '개인정보 제3자 제공 동의', required: true },
          { code: 'CLAUSE_REQUIRED_06', title: '네트워크 차단 동의', required: true },
          { code: 'CLAUSE_REQUIRED_07', title: '청소년 유해매체 차단 동의', required: true },
          { code: 'CLAUSE_SELECT_01', title: '개인정보 광고 전송 동의(선택)', required: false },
          {
            code: 'CLAUSE_SELECT_04',
            title: '혜택 제공을 위한 제3자 제공 동의(M모바일)',
            required: false,
          },
          {
            code: 'CLAUSE_SELECT_06',
            title: '혜택 제공을 위한 제3자 제공 동의(KT)',
            required: false,
          },
          { code: 'CLAUSE_SELECT_10', title: '개인위치정보 제3자 제공 동의', required: false },
          { code: 'CLAUSE_INFO_01', title: '서비스 이용약관', required: true },
        ]

  return baseList.map((item) => {
    const isReq =
      item.expnsnStrVal3 === 'Y' || item.EXPNSN_STR_VAL3 === 'Y' || item.required || false
    const dtlCode = item.code || item.dtlCd || item.value || item.DTL_CD
    const finalKey = saveKeyMap[dtlCode] || dtlCode

    return {
      id: finalKey,
      dtlCd: dtlCode,
      label: item.title || item.label,
      required: isReq,
      checked: false,
    }
  })
})

const filteredTermsDataList = computed(() => {
  return termsDataList.value.filter((term) => {
    const termId = term.id

    // 1. 번호이동
    if (termId === 'clauseMoveCode' && formData.joinType !== 'MNP3') return false

    // 2. 미성년자 조건
    if (
      ['nwBlckAgrmYn', 'appBlckAgrmYn', 'blckAppDivCd'].includes(termId) &&
      !['NM', 'FM'].includes(formData.cstmrTypeCd)
    )
      return false

    // 3. 안면인증
    if (
      ['clauseFathFlag01', 'clauseFathFlag02'].includes(termId) &&
      formData.identityCertTypeCd !== 'F'
    )
      return false

    // 4. 5G요금제
    if (termId === 'clause5gCoverage' && !(formData.planName2 || '').includes('5G')) return false

    // 5. 제휴요금
    if (['clauseJehuFlag', 'clausePartnerOfferFlag'].includes(termId) && !formData.partnerEventCode)
      return false

    // 6. 사업이관
    if (termId === 'soTrnsAgrmYn' && formData.isTransferTarget !== 'Y') return false

    // 7. 단말 렌탈
    if (
      ['clauseRentalModelCpYn', 'clauseRentalModelCpPrYn', 'clauseRentalServiceYn'].includes(
        termId,
      ) &&
      productData.modelSalePlcyCd !== 'RENTAL'
    )
      return false

    // 8. 선불 요금제
    if (termId === 'clauseMpps35Mpps35Yn' && productData.ratePlanCategory !== 'PREPAID')
      return false

    // 9. 금융제휴
    if (termId === 'clauseFinanceFinanceYn' && productData.insrYn !== 'Y') return false

    // 10. 복지할인 민감정보
    if (
      ['clauseSensiCollectYn', 'clauseSensiOfferYn'].includes(termId) &&
      formData.welfareDiscount !== 'Y'
    )
      return false

    // 11. 약관제휴사제공동의여부
    if (termId === 'clausePartnerOfferYn' && !formData.partnerEventCode) return false

    // 12. 아무나솔로결합
    if (
      ['combineSoloTypeYn', 'combineSoloSoloYn'].includes(termId) &&
      !(productData.addonService || []).includes('SOLO_COMB')
    )
      return false

    // 13. 번호이동 미환급액 상계 (가입유형 번호이동)
    if (termId === 'moveRefundAgreeYn' && formData.joinType !== 'MNP3') return false

    return true
  })
})

const emit = defineEmits(['complete'])

// 컴포넌트 Refs
const productJoinTypeRef = ref(null)
const customerTypeRef = ref(null)
const identityVerifyRef = ref(null)
const subscriberInfoRef = ref(null)
const legalAgentInfoRef = ref(null)
const realUserInfoRef = ref(null)
const delegateInfoRef = ref(null)
const requiredDocRef = ref(null)
const contactInfoRef = ref(null)
const devicePlanInfoRef = ref(null)
const termsAgreementRef = ref(null)

const isComplete = ref(false)

const onClickTestLoad = async () => {
  const result = await store.loadFromTempJson()
  if (result !== false) {
    const stepValue = parseInt(result)
    if (!isNaN(stepValue)) {
      // 1: Customer(0), 2: Product(1), 3: Agreement(2)
      const index = Math.max(0, stepValue - 1)
      stepStore.setActiveIndex(index)
    }
    alert(`temp.json 데이터를 불러왔습니다. (Step: ${result})`)
  } else {
    alert('temp.json 데이터를 불러오는데 실패했습니다.')
  }
}

const onClickJumpToAgreement = () => {
  // Agreement는 3번째 스텝이므로 인덱스 2
  stepStore.setActiveIndex(2)
}

// 현재 단계(Customer)의 모든 컴포넌트 유효성 검사
const validate = () => {
  const validations = [
    productJoinTypeRef.value?.validate(),
    customerTypeRef.value?.validate(),
    identityVerifyRef.value?.validate(),
    subscriberInfoRef.value?.validate(),
    legalAgentInfoRef.value?.validate(),
    realUserInfoRef.value?.validate(),
    delegateInfoRef.value?.validate(),
    requiredDocRef.value?.validate(),
    contactInfoRef.value?.validate(),
    devicePlanInfoRef.value?.validate(),
    termsAgreementRef.value?.validate(),
  ]
  // null(비노출 컴포넌트)은 제외하고 모든 결과가 true인지 확인
  return validations.filter((v) => v !== undefined).every((v) => v === true)
}

const checkRequiredFields = (result) => {
  // result가 있으면 (약관 동의 이벤트 등) formData에 값 반영
  if (result && Array.isArray(result)) {
    result.forEach((item) => {
      const dtlCode = item.code
      const finalKey = saveKeyMap[dtlCode] || dtlCode

      // 1. YN 필드 업데이트
      if (Object.prototype.hasOwnProperty.call(formData, finalKey)) {
        formData[finalKey] = item.checked ? 'Y' : 'N'
      }

      // 2. 불리언 필드 업데이트 (CLAUSE_... 형태 등)
      if (Object.prototype.hasOwnProperty.call(formData, dtlCode)) {
        formData[dtlCode] = item.checked
      }
    })
  }

  const pending = []

  // 1. 컴포넌트 레벨 유효성 검사 취합
  if (productJoinTypeRef.value && !productJoinTypeRef.value.validate())
    pending.push('가입 유형 선택')
  if (customerTypeRef.value && !customerTypeRef.value.validate()) pending.push('고객 유형 선택')
  if (identityVerifyRef.value && !identityVerifyRef.value.validate())
    pending.push('신분증 인증/스캔')
  if (subscriberInfoRef.value && !subscriberInfoRef.value.validate())
    pending.push('가입자 정보 입력/인증')
  if (contactInfoRef.value && !contactInfoRef.value.validate()) pending.push('연락처 정보 입력')
  if (devicePlanInfoRef.value && !devicePlanInfoRef.value.validate())
    pending.push('휴대폰/요금제 정보 선택')
  if (termsAgreementRef.value && !termsAgreementRef.value.validate()) pending.push('필수 약관 동의')

  // 2. 가입유형별/고객유형별 추가 인증 체크
  if (formData.joinType === 'HDN3' && !store.authFlags.deviceChgTel) {
    pending.push('기기변경 휴대폰 인증')
  }
  if (['NM', 'FM'].includes(formData.cstmrTypeCd) && !store.authFlags.repPhone) {
    pending.push('법정대리인 휴대폰 인증')
  }
  if (['NM', 'FM'].includes(formData.cstmrTypeCd) && !formData.repAgree) {
    pending.push('법정대리인 안내사항 동의')
  }

  // 3. 가입자 정보 기본 필드 추가 체크
  if (!formData.cstmrNm) pending.push('고객명')
  if (!formData.mobileNo2) pending.push('휴대폰 번호')

  // 3. 구비서류 체크
  if (requiredDocRef.value && !requiredDocRef.value.validate()) {
    const docNames = requiredDocRef.value.getRequiredDocNames()
    if (docNames.length > 0) {
      pending.push(`구비서류(${docNames.join(', ')})`)
    } else {
      pending.push('구비서류(필수)')
    }
  }

  // 결과 처리
  const isReady = pending.length === 0

  if (!isReady) {
    console.log('%c[미입력 항목]:', 'color: #ff4d4f; font-weight: bold;', pending.join(', '))
  } else {
    console.log('%c[모든 입력 완료!]', 'color: #52c41a; font-weight: bold;')
  }

  isComplete.value = isReady
  emit('complete', isReady)
  return isReady
}

let debounceTimer = null
const checkRequiredFieldsDebounced = () => {
  if (debounceTimer) clearTimeout(debounceTimer)
  debounceTimer = setTimeout(() => {
    checkRequiredFields()
    debounceTimer = null
  }, 100)
}

watch(
  () => [formData, productData, store.authFlags],
  () => {
    checkRequiredFieldsDebounced()
  },
  { deep: true },
)

onMounted(async () => {
  // 사용자의 공통코드 목록에 따라 서식지 약관 관련 코드들을 로드합니다. (한 번의 API 호출로 묶음)
  const res = await getCommonCodeList(['FORMGROUP', 'FORMINFO', 'FORMREQUIRED', 'FORMSELECT'])

  const list = res?.FORMGROUP || []
  const infoList = res?.FORMINFO || []
  const requiredList = res?.FORMREQUIRED || []
  const selectList = res?.FORMSELECT || []

  const combinedList = [...list, ...infoList, ...requiredList, ...selectList]

  rawTermsList.value = (
    combinedList.length > 0 ? combinedList : await getCommonCodeList('CLAUSE_FORM_01')
  ).map((item) => ({
    ...item,
    label: item.title,
    value: item.code,
  }))

  store.validateCustomer = validate
})

const data = async (code) => {
  // 화면 진입 시 초기값 및 임시저장값 세팅
  return await store.initForm(code)
}

const save = async () => {
  return await store.apiSaveDraft(1)
}

defineExpose({ data, save, validate, reset: store.resetAll })
</script>

<style lang="scss" scoped>
.page-step-panel {
  display: flex;
  flex-direction: column;
  height: auto;
  min-height: min-content;
  flex-shrink: 0;
}
</style>
