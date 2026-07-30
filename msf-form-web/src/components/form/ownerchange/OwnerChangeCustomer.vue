<template>
  <div class="page-step-panel">
    <!-- 고객(양도고객) 유형 -->
    <MsfCustomerType
      v-model="formData.tr_customer"
      title="고객(양도고객) 유형"
      :name="'tr'"
      ref="trCustomerTypeRef"
      :authFlags="store.trAuthFlags"
    />
    <!-- // 고객(양도고객) 유형 -->
    <!-- 고객(양도고객) 신분증 확인 -->
    <MsfIdentityVerify
      v-if="!['JP', 'GO'].includes(formData.tr_customer.cstmrTypeCd)"
      v-model="formData.tr_customer"
      :title="trCustomerTitle.identityVerifyTitle"
      ref="trIdentityVerifyRef"
      :authFlags="store.trAuthFlags"
      @scanConfirm="trIdVerifyEmitAfterScan"
      :return-file="true"
      file-type-cd="21"
      join-type="MCN3"
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
      :authFlags="store.trAuthFlags"
    />
    <!-- // 고객(양도고객) 정보 -->
    <!-- 고객(양도고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <MsfLegalAgentInfo
      v-model="formData.tr_customer"
      :name="'minor-tr'"
      agreementTitle="고객(양도고객) 법정대리인 안내사항 확인 및 동의"
      title="고객(양도고객) 법정대리인 정보"
      ref="trLegalAgentInfoRef"
      :preCheckAuthFunc="legalCheck"
      :authFlags="store.trAuthFlags"
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
      v-model:juridical="formData.juridical"
      :title="teCustomerTitle.identityVerifyTitle"
      ref="teIdentityVerifyRef"
      :authFlags="store.authFlags"
      join-type="MCN3"
      :check-before-face-auth="checkTeBeforeFaceAuth"
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
      :preCheckAuthFunc="teBirthCheck"
    />
    <!-- 고객(양수고객) 법정대리인 정보 / 안내사항 확인 및 동의 -->
    <!-- 고객(실사용자) 정보 -->
    <MsfRealUserInfo
      v-model="formData.te_customer"
      name="real"
      v-if="['JP', 'GO'].includes(formData.te_customer?.cstmrTypeCd)"
      ref="realUserInfoRef"
    />
    <!-- // 고객(실사용자) 정보 -->
    <!-- 대리인 위임 정보 -->
    <MsfDelegateInfo
      v-model="formData.juridical"
      v-if="formData.te_customer?.cstmrVisitTypeCd === 'VDP'"
      ref="delegateInfoRef"
    />
    <!-- // 대리인 위임 정보 -->
    <!-- 구비서류 -->
    <!-- <MsfRequiredDoc
      v-model="formData.te_customer"
      ref="requiredDocRef"
      v-model:authFlags="store.authFlags"
    /> -->
    <!-- // 구비서류 -->
    <!-- 고객(양수고객) 연락처 -->
    <MsfContactInfo
      v-model="formData.te_customer"
      title="고객(양수고객) 연락처"
      ref="contactInfoRef"
      :cstmrBillSendTypeCd="formData.productPayment?.cstmrBillSendTypeCd"
    />
    <!-- // 고객(양수고객) 연락처 -->
    <!-- 요금제 정보 -->
    <MsfChargePlanInfo
      v-model="formData.planInfo"
      v-model:customerData="formData.te_customer"
      ref="devicePlanInfoRef"
    />
    <!-- // 요금제 정보 -->
    <!-- 약관 동의  -->
    <MsfTermsAgreement
      ref="termsAgreementRef"
      v-model="formData"
      policy="CLAUSE_FORM_03"
      :termsData="termList"
      :specTerms="dynamicSpecTerms"
      :isSaved="formData.isSaved"
      @checked="termsInfoUpdate"
      required
    />
    <!-- // 약관 동의 -->
  </div>
</template>

<script setup>
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { extractYYYYMMDDRrn } from '@/libs/utils/string.utils'
import { useMsfFormOwnChgStore } from '@/stores/msf_ownerChange'
import { useMsfStepStore } from '@/stores/msf_step'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'
import { ref, computed, watch, nextTick } from 'vue'
import { useRoute } from 'vue-router'

// 필수 항목 입력 완료여부 리턴
const emit = defineEmits(['complete'])

const store = useMsfFormOwnChgStore()
const { formData, trCustomerTitle, teCustomerTitle } = storeToRefs(store)
const isComplete = ref(false)
const rawTermList = ref([])

const termList = computed(() => {
  const exposeTermList = [
    'CLAUSE_INFO_09', // 혜택소멸 안내
    'CLAUSE_CNTR_DEL_01', // 명의변경 양도고객 정보 삭제
    'CLAUSE_INFO_08', // 기타 안내
    'CLAUSE_INFO_10', // 명의변경 주요안내
    'CLAUSE_REQUIRED_02', // 고유식별정보 처리동의
    'CLAUSE_REQUIRED_01', // 개인정보/신용정보수집·이용동의
    'CLAUSE_REQUIRED_03', // 개인정보 제3자 제공 동의
    'CLAUSE_FATH_01', // 민감정보(생체인식정보) 수집 및 이용 동의
    'CLAUSE_FATH_02', // 민감정보(생체인식정보) 조희 및 이용 / 3자 제공에 대한 동의
    'CLAUSE_INFO_01', // 서비스 이용약관
    'CLAUSE_SELECT_10', // 개인위치정보 제 3자 제공 동의
    'CLAUSE_SELECT_TIT_01', // 고객 혜택 제공을 위한 정보수집 이용 동의 및 혜택 광고의 수신 위탁 동의
    'CLAUSE_SELECT_03', // 고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
    'CLAUSE_SELECT_01', // 개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의
    'CLAUSE_SELECT_TIT_02', // 혜택 제공을 위한 제3자 제공 및 광고 수신 동의
    'CLAUSE_SELECT_08', // 혜택 제공을 위한 제3자 제공 동의
    'CLAUSE_SELECT_07', // 제3자 제공관련 광고 수신 동의
    'CLAUSE_REQUIRED_5G',
    'CLAUSE_REQUIRED_06',
    'CLAUSE_REQUIRED_07',
    'CLAUSE_PARTNER_02',
  ]

  return (rawTermList.value || [])
    ?.map((item) => ({
      ...item,
      label: item.title,
      value: item.code,
      checked: false,
    }))
    .filter((item) => exposeTermList.includes(item.value))
})

const dynamicSpecTerms = computed(() => {
  const list = []
  const isTeMinor = ['NM', 'FM'].includes(formData.value.te_customer.cstmrTypeCd)

  // 요금제 구분값이 '5G'인 경우에만 5G 관련 약관 추가
  if (isTeMinor) {
    if (formData.value.planInfo.dataType === '5G') {
      list.push({ code: 'CLAUSE_REQUIRED_5G' })
    }
    list.push({ code: 'CLAUSE_REQUIRED_06' }) // 청소년 네트워크 유해 차단 동의
    list.push({ code: 'CLAUSE_REQUIRED_07' }) // 청소년 유해정보 차단 앱 동의
  }

  if (formData.value.planInfo.jehuPartnerTypeCd) {
    list.push({
      code: 'CLAUSE_PARTNER_02',
      specType: '02',
      specCode: formData.value.planInfo.jehuPartnerTypeCd,
      specName: formData.value.planInfo.jehuPartnerTypeNm,
    })
  }

  return list
})

const stepStore = useMsfStepStore()
const route = useRoute()

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
// const requiredDocRef = ref(null)
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

const trIdVerifyEmitAfterScan = (data) => {
  const {
    identityIssuDate,
    identityIssuRegion,
    driveLicnsNo,
    cstmrNm,
    trnsNm,
    cstmrNativeRrn,
    cstmrForeignerRrn,
  } = data

  const cstmrNativeRrn1 = cstmrNativeRrn?.slice(0, 6)
  const cstmrNativeRrn2 = cstmrNativeRrn?.slice(6)
  const cstmrForeignerRrn1 = cstmrForeignerRrn?.slice(0, 6)
  const cstmrForeignerRrn2 = cstmrForeignerRrn?.slice(6)
  const userBirthDate = cstmrNativeRrn1
    ? extractYYYYMMDDRrn(`${cstmrNativeRrn1}${cstmrNativeRrn2}`)
    : extractYYYYMMDDRrn(`${cstmrForeignerRrn1}${cstmrForeignerRrn2}`)
  const cstmrTypeCd = formData.value.tr_customer.cstmrTypeCd
  const minor = ['NM', 'FM']
  const govern = ['JP', 'GO']

  if (cstmrNm) {
    if (minor.includes(cstmrTypeCd)) {
      formData.value.tr_customer.repName = cstmrNm
      formData.value.tr_customer.minorUserBirthDate = userBirthDate
      formData.value.tr_customer.repRegistrationNo1 = cstmrNativeRrn?.slice(0, 6)
      formData.value.tr_customer.repRegistrationNo2 = cstmrNativeRrn?.slice(6)
      formData.value.tr_customer.repForeignerNo1 = cstmrForeignerRrn?.slice(0, 6)
      formData.value.tr_customer.repForeignerNo2 = cstmrForeignerRrn?.slice(6)
    } else if (govern.includes(cstmrTypeCd)) {
      formData.value.tr_customer.cstmrJuridicalRepNm = cstmrNm
    } else {
      formData.value.tr_customer.cstmrNm = cstmrNm
      formData.value.tr_customer.userBirthDate = userBirthDate
      formData.value.tr_customer.cstmrNativeRrn1 = cstmrNativeRrn?.slice(0, 6)
      formData.value.tr_customer.cstmrNativeRrn2 = cstmrNativeRrn?.slice(6)
      formData.value.tr_customer.cstmrForeignerRrn1 = cstmrForeignerRrn?.slice(0, 6)
      formData.value.tr_customer.cstmrForeignerRrn2 = cstmrForeignerRrn?.slice(6)
    }
  }

  // 양도인의 신분증 스캔 이미지 파일 메타데이터를 양수인(te_customer)의 구비서류 목록(msfRequestDocList)에 추가합니다.
  if (data.fileInfo || data.maskImageFile) {
    const fileInfo = data.fileInfo || {}
    const filePath = fileInfo.filePath || ''
    const newDoc = {
      fileTypeCd: '21', // 명의자 신분증 코드
      filePathNm: filePath || data.maskImageFile || '',
      fileNm:
        fileInfo.fileName ||
        fileInfo.fileNm ||
        (filePath ? filePath.split('/').pop() : '') ||
        '명의자신분증_마스킹.png',
      filePageNo: 1,
      previewUrl: data.maskImageFile || '',
      maskImageFile: data.maskImageFile || '',
    }

    const list = [...(formData.value.te_customer.msfRequestDocList || [])]
    const idx = list.findIndex((item) => item.fileTypeCd === '21')
    if (idx >= 0) {
      list[idx] = newDoc
    } else {
      list.push(newDoc)
    }

    // 반응성 전파를 위해 te_customer 객체 레퍼런스 재생성
    formData.value.te_customer = {
      ...formData.value.te_customer,
      msfRequestDocList: list,
    }
    console.log(
      '>>> [trIdVerifyEmitAfterScan] Injected scan doc into te_customer.msfRequestDocList:',
      list,
    )
  }
}

onMounted(async () => {
  rawTermList.value = (await getCommonCodeList('CLAUSE_FORM_03')).map((item) => ({
    ...item,
    label: item.title,
    value: item.code,
    checked: false,
  }))
})

const legalCheck = () => {
  if (trSubscriberInfoRef.value?.checkValidation && !trSubscriberInfoRef.value?.checkValidation()) {
    return false
  }
  if (
    trLegalAgentInfoRef.value?.checkValidation &&
    !trLegalAgentInfoRef.value?.checkValidation(true)
  ) {
    return false
  }
  return true

  // const government = ['JP', 'GO'].includes(formData.value.tr_customer.cstmrTypeCd)

  // if (government) return true

  // const flag = validateCustomerAge('NA', formData.value.tr_customer.minorUserBirthDate)
  // if (!flag) {
  //   showAlert('양도인 법정대리인 생년월일을 확인해주세요.')
  //   return false
  // }
  // return true
}

const teBirthCheck = () => {
  if (teSubscriberInfoRef.value?.checkValidation && !teSubscriberInfoRef.value?.checkValidation()) {
    return false
  }
  if (
    teLegalAgentInfoRef.value?.checkValidation &&
    !teLegalAgentInfoRef.value?.checkValidation(true)
  ) {
    return false
  }
  return true

  // const government = ['JP', 'GO'].includes(formData.value.tr_customer.cstmrTypeCd)
  // const isForeigner = ['FN', 'FM'].includes(formData.value.te_customer.cstmrTypeCd)

  // if (government) return true

  // if (isForeigner) {
  //   const check = store.verifyJuminAndAge(
  //     formData.value.te_customer.cstmrForeignerRrn1,
  //     formData.value.te_customer.cstmrForeignerRrn2,
  //     true,
  //   )
  //   if (!check?.isValid || check?.isAdult) {
  //     showAlert('양수인 생년월일을 확인해주세요.')
  //     return false
  //   } else {
  //     // 법정대리인 성인 여부 & 주민번호 유효 검증
  //     const legalCheck = store.verifyJuminAndAge(
  //       formData.value.te_customer.repForeignerNo1,
  //       formData.value.te_customer.repForeignerNo2,
  //       true,
  //     )

  //     if (!legalCheck?.isValid || !legalCheck?.isAdult) {
  //       showAlert('양수인 법정대리인 생년월일을 확인해주세요.')
  //       return false
  //     }

  //     return true
  //   }
  // } else {
  //   const checkNative = store.verifyJuminAndAge(
  //     formData.value.te_customer.cstmrNativeRrn1,
  //     formData.value.te_customer.cstmrNativeRrn2,
  //     false,
  //   )
  //   if (!checkNative?.isValid || checkNative?.isAdult) {
  //     showAlert('양수인 생년월일을 확인해주세요.')
  //     return false
  //   } else {
  //     const legalCheckNative = store.verifyJuminAndAge(
  //       formData.value.te_customer.repRegistrationNo1,
  //       formData.value.te_customer.repRegistrationNo2,
  //       false,
  //     )

  //     if (!legalCheckNative?.isValid || !legalCheckNative?.isAdult) {
  //       showAlert('양수인 법정대리인 생년월일을 확인해주세요.')
  //       return false
  //     }
  //     return true
  //   }
  // }
}

/**
 * 고객 유형과 생년월일의 모순 여부 및 성인/미성년자 판별 함수
 * @param {string} cstmrTypeCd - 고객유형코드 (NM, FM 등)
 * @param {string} birthStr - 생년월일 8자리 (YYYYMMDD)
 * @returns {string} - 'ADULT'(성인), 'MINOR'(미성년자), 'INVALID'(데이터 오류)
 */
const validateCustomerAge = (cstmrTypeCd, birthStr) => {
  // 1. 형식 체크
  if (!birthStr || birthStr.length !== 8 || isNaN(birthStr)) {
    return false
  }

  const birthYear = parseInt(birthStr.substring(0, 4), 10)
  const birthMonth = parseInt(birthStr.substring(4, 6), 10)
  const birthDay = parseInt(birthStr.substring(6, 8), 10)

  // 2. 실제 만 나이 계산
  const today = new Date()
  const currentYear = today.getFullYear()
  const currentMonth = today.getMonth() + 1
  const currentDaily = today.getDate()

  let age = currentYear - birthYear
  if (currentMonth < birthMonth || (currentMonth === birthMonth && currentDaily < birthDay)) {
    age--
  }

  // 3. 상태 정의
  const isActualAdult = age >= 19 // 실제 날짜 기준 성인 여부
  const isCodeMinor = cstmrTypeCd === 'NM' || cstmrTypeCd === 'FM' // 코드 기준 미성년자 여부

  // 4. 모순 검증 (Cross-Check)

  // [케이스 A] 코드는 미성년자인데, 날짜는 성인인 경우
  if (isCodeMinor && isActualAdult) {
    return false
  }

  // [케이스 B] 코드는 성인인데, 날짜는 미성년자인 경우
  if (!isCodeMinor && !isActualAdult) {
    return false
  }

  // 5. 정상이면 결과 반환
  return true
}

const trCustomerDeviceChgVerify = async (paramObj) => {
  const government = ['JP', 'GO'].includes(formData.value.tr_customer.cstmrTypeCd)

  if (
    !government &&
    !validateCustomerAge(
      formData.value.tr_customer.cstmrTypeCd,
      formData.value.tr_customer.userBirthDate,
    )
  ) {
    showAlert('양도인 생년월일을 확인해주세요.')
    return false
  }

  try {
    const res = await post('/api/form/owner-change/validate', paramObj)

    if (res.data.resultCd === '00') {
      const { data } = res
      const {
        ncn,
        ctn,
        custId,
        esimYn,
        banAdrZip,
        banAdrPrimaryLn,
        banAdrSecondaryLn,
        blBillingMethod,
        email,
        homeTel,
        prodId,
        prodNm,
        prodAmt,
        billTypeCd,
        cntpntCdNm,
        userNm,
        requestKey,
        jehuProdNm,
        jehuProdType,
      } = data.response
      const [emailAddr1, emailAddr2] = (email ?? '').split('@')
      const [_, mobileNo1, mobileNo2, mobileNo3] =
        (homeTel ?? '').match(/^(\d{3})(\d{3,4})(\d{4})$/) || []
      store.setApplicationKey(requestKey)
      store.updateTrCustomer({ ncn, ctn, custId })

      // 우편번호 형식 안맞으면 적용X
      const zipNoCheck = /^\d{5}$/.test(String(banAdrZip))

      // 인증시 가입자 정보 조회
      if (
        zipNoCheck &&
        !formData.value.te_customer.zipNo &&
        !formData.value.te_customer.address &&
        !formData.value.te_customer.detailAddress
      ) {
        store.updateTeCustomer({
          zipNo: banAdrZip,
          address: banAdrPrimaryLn,
          detailAddress: banAdrSecondaryLn,
          emailAddr1,
          emailAddr2,
          mobileNo2,
          mobileNo3,
          cntpntCdNm,
          userNm,
          cstmrBillSendTypeCd: billTypeCd,
        })
      }

      store.updateUsimInfo({ simTypeCd: esimYn !== 'Y' ? 'USIM' : 'ESIM' })
      store.updatePlanInfo({
        ctn,
        ncn,
        custId,
        orgProdId: prodId,
        orgProdNm: prodNm,
        orgPlanAmt: prodAmt,
        planName2: prodId,
        planNm: prodNm,
        planAmt: prodAmt,
        jehuProdTypeCd: jehuProdType,
        jehuPartnerTypeCd: jehuProdType,
        jehuPartnerTypeNm: jehuProdNm,
      })
      store.updateProductPayment({ reqPayTypeCd: blBillingMethod, cstmrBillSendTypeCd: billTypeCd })
      store.updateTrAuthFlags({ deviceChgTel: true })
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
    CLAUSE_SELECT_10: 'indvLocaPrvAgreeYn', // 개인위치정보 제 3자 제공 동의
    /* 고객 혜택 제공 관련 */
    // CLAUSE_SELECT_TIT_01: 'personalInfoCollectAgreeYn', // 고객 혜택 제공을 위한 정보수집 이용 동의 및 혜택 광고의 수신 위탁 동의
    CLAUSE_SELECT_01: 'clausePriTrustYn', // 개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의
    CLAUSE_SELECT_03: 'personalInfoCollectAgreeYn', // 고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
    /* 고객 혜택 제공 관련 */
    /* 혜택 제공 관련 */
    // CLAUSE_SELECT_TIT_02: 'othersTrnsAllAgreeYn', // 혜택 제공을 위한 제3자 제공 및 광고 수신 동의
    CLAUSE_SELECT_08: 'othersTrnsAllAgreeYn', // 혜택 제공을 위한 제3자 제공 동의
    CLAUSE_SELECT_07: 'othersAdReceiveAgreeYn', // 제3자 제공관련 광고 수신 동의
    /* 혜택 제공 관련 */
    CLAUSE_REQUIRED_5G: 'clause5gCoverageYn', // 5G커버리지확인및가입동의여부
    CLAUSE_REQUIRED_06: 'nwBlckAgrmYn', // 네트워크차단동의여부
    CLAUSE_REQUIRED_07: 'appBlckAgrmYn', // 청소년유해매체차단동의여부
    // CLAUSE_PARTNER_01: 'clausePartnerOfferYn',
    CLAUSE_PARTNER_02: 'clauseJehuYn',
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
  async () => {
    await nextTick()
    validate()
  },
  { deep: true },
)

watch(
  () => formData.value,
  async () => {
    await nextTick()
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

const validateWithAlert = () => checkTellNo() && store.checkFieldValidation('CUSTOMER')

const data = async (code /* 임시저장 코드 */) => {
  return await store.initForm(code)
}

const save = async () => {
  //  데이터 임시저장
  return isComplete.value === true
}

const checkTellNo = () => {
  const trCustomer = formData.value.tr_customer
  const teCustomer = formData.value.te_customer
  const trDeviceChgTel = trCustomer.deviceChgTel2 + trCustomer.deviceChgTel3
  const trMinorAgentTelNo = trCustomer.minorAgentTelMnNo + trCustomer.minorAgentTelRnNo
  const teMinorAgentTelNo = teCustomer.minorAgentTelMnNo + teCustomer.minorAgentTelRnNo
  const mobileNo = teCustomer.mobileNo2 + teCustomer.mobileNo3

  const isMinor = ['NM', 'FM'].includes(trCustomer.cstmrTypeCd)

  if (isMinor && trDeviceChgTel && trMinorAgentTelNo && trDeviceChgTel === trMinorAgentTelNo) {
    showAlert('명의변경 회선 번호와 법정대리인 연락처는 달라야합니다.')
    formData.value.tr_customer.minorAgentTelMnNo = ''
    formData.value.tr_customer.minorAgentTelRnNo = ''
    return false
  }
  if (isMinor && trDeviceChgTel && teMinorAgentTelNo && trDeviceChgTel === teMinorAgentTelNo) {
    showAlert('명의변경 회선 번호와 법정대리인 연락처는 달라야합니다.')
    formData.value.te_customer.minorAgentTelMnNo = ''
    formData.value.te_customer.minorAgentTelRnNo = ''
    return false
  }
  if (trDeviceChgTel && mobileNo && trDeviceChgTel === mobileNo) {
    showAlert('명의변경 회선 번호와 연락가능 연락처는 달라야합니다.')
    formData.value.te_customer.mobileNo2 = ''
    formData.value.te_customer.mobileNo3 = ''
    return false
  }
  if (
    isMinor &&
    mobileNo &&
    ((trMinorAgentTelNo && trMinorAgentTelNo === mobileNo) ||
      (teMinorAgentTelNo && teMinorAgentTelNo === mobileNo))
  ) {
    showAlert('법정대리인 연락처와 연락가능 연락처는 달라야합니다.')
    formData.value.te_customer.mobileNo2 = ''
    formData.value.te_customer.mobileNo3 = ''
    return false
  }

  return true
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
    // requiredDocRef.value?.validate(),
    contactInfoRef.value?.validate(),
    devicePlanInfoRef.value?.validate(),
    termsAgreementRef.value?.validate(),
  ]

  // null(비노출 컴포넌트)은 제외하고 모든 결과가 true인지 확인
  const isReady = validations.filter((v) => v !== undefined).every((v) => v === true)
  isComplete.value = isReady
  return isReady
}

const checkTeBeforeFaceAuth = () => {
  if (
    ['JP', 'GO'].includes(formData.value.te_customer.cstmrTypeCd) &&
    formData.value.te_customer.cstmrVisitTypeCd === 'VDP'
  ) {
    if (
      teSubscriberInfoRef.value?.checkBizNoValidation &&
      !teSubscriberInfoRef.value?.checkBizNoValidation()
    ) {
      console.log('teSubscriberInfoRef.value.checkBizNoValidation()', false)
      return false
    }

    if (delegateInfoRef.value?.checkNameAndBirth && !delegateInfoRef.value.checkNameAndBirth()) {
      console.log('delegateInfoRef.value.checkNameAndBirth()', false)
      return false
    }
  }

  return true
}

const checkValidation = () => {
  if (trCustomerTypeRef.value?.checkValidation && !trCustomerTypeRef.value.checkValidation()) {
    console.log('trCustomerTypeRef.value.checkValidation()', false)
    return false
  }
  if (trIdentityVerifyRef.value?.checkValidation && !trIdentityVerifyRef.value.checkValidation()) {
    console.log('trIdentityVerifyRef.value.checkValidation()', false)
    return false
  }
  if (trSubscriberInfoRef.value?.checkValidation && !trSubscriberInfoRef.value.checkValidation()) {
    console.log('trSubscriberInfoRef.value.checkValidation()', false)
    return false
  }
  if (trLegalAgentInfoRef.value?.checkValidation && !trLegalAgentInfoRef.value.checkValidation()) {
    console.log('trLegalAgentInfoRef.value.checkValidation()', false)
    return false
  }
  if (teCustomerTypeRef.value?.checkValidation && !teCustomerTypeRef.value.checkValidation()) {
    console.log('teCustomerTypeRef.value.checkValidation()', false)
    return false
  }
  if (teIdentityVerifyRef.value?.checkValidation && !teIdentityVerifyRef.value.checkValidation()) {
    console.log('teIdentityVerifyRef.value.checkValidation()', false)
    return false
  }
  if (teSubscriberInfoRef.value?.checkValidation && !teSubscriberInfoRef.value.checkValidation()) {
    console.log('teSubscriberInfoRef.value.checkValidation()', false)
    return false
  }
  if (teLegalAgentInfoRef.value?.checkValidation && !teLegalAgentInfoRef.value.checkValidation()) {
    console.log('teLegalAgentInfoRef.value.checkValidation()', false)
    return false
  }
  if (realUserInfoRef.value?.checkValidation && !realUserInfoRef.value.checkValidation()) {
    console.log('realUserInfoRef.value.checkValidation()', false)
    return false
  }
  if (delegateInfoRef.value?.checkValidation && !delegateInfoRef.value.checkValidation()) {
    console.log('delegateInfoRef.value.checkValidation()', false)
    return false
  }
  // if (requiredDocRef.value?.checkValidation && !requiredDocRef.value.checkValidation()) {
  //   console.log('requiredDocRef.value.checkValidation()', false)
  //   return false
  // }
  if (contactInfoRef.value?.checkValidation && !contactInfoRef.value.checkValidation()) {
    console.log('contactInfoRef.value.checkValidation()', false)
    return false
  }
  if (devicePlanInfoRef.value?.checkValidation && !devicePlanInfoRef.value.checkValidation()) {
    console.log('devicePlanInfoRef.value.checkValidation()', false)
    return false
  }
  if (termsAgreementRef.value?.checkValidation && !termsAgreementRef.value.checkValidation()) {
    console.log('termsAgreementRef.value.checkValidation()', false)
    return false
  }

  return true
}

defineExpose({ data, save, validate, reset, validateWithAlert, checkValidation })
</script>

<style scoped></style>
