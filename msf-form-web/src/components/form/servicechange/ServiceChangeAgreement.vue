<template>
  <div class="page-step-panel">
    <MsfLoadingComp v-if="isAgreementSaving || isAgreementProcessing" />

    <!-- 신청서 확인 (서비스변경 전용: 확인 → 작성완료 → 업로드 순서) -->
    <MsfAppConfirm
      :key="appConfirmKey"
      ref="appConfirmRef"
      :defer-upload="true"
      formTypeCode="servicechange"
      :request-key="store.requestKey || ''"
      :cstmr-nm="appConfirmCstmrNm"
      :phone-no="appConfirmPhoneNo"
      :form-parameters="eformFormParameters"
      :device-os="deviceOs"
      :use-new-change-template="useNewChangeTemplate"
      :disabled="confirmStep !== 1"
      :edit-disabled="isSuccessOnlyReview"
      :success-only-review="isSuccessOnlyReview"
      @extract-complete="onExtractComplete"
      @click="onBeforeConfirmApp"
      @confirm="onConfirmApp"
      @edit="onEditApp"
      @close="onCloseApp"
    />

    <!-- 일부 성공 재확인 영역 -->
    <MsfBox v-if="showReconfirmArea" variant="outline" class="partial-fail-box">
      <div
        class="partial-fail-msg"
        v-html="finalSubmitNotice || store.getCompleteNoticeMessage()"
      />
      <MsfButtonGroup align="center" margin="2">
        <MsfButton
          variant="primary"
          :disabled="!canReconfirmSuccessfulServices"
          @click="onFinalSubmit"
        >
          신청서 재확인
        </MsfButton>
      </MsfButtonGroup>
    </MsfBox>
  </div>
</template>

<script setup>
import { useMsfFormSvcChgStore } from '@/stores/msf_serviceChange'
import { showAlert } from '@/libs/utils/comp.utils'
import { post } from '@/libs/api/msf.api'
import { getCommonCodeList } from '@/libs/utils/comn.utils'
import { storeToRefs } from 'pinia'
import { ref, watch, computed, nextTick, onMounted } from 'vue'

const emit = defineEmits(['complete', 'final-complete'])

const store = useMsfFormSvcChgStore()
const { formData } = storeToRefs(store)
const legalAgentRelationCodes = ref([])

const joinDigits = (...parts) =>
  parts
    .map((p) => p || '')
    .join('')
    .replace(/[^0-9]/g, '')

const appConfirmCstmrNm = computed(() => formData.value.cstmrNm || '')
const appConfirmPhoneNo = computed(() =>
  joinDigits(
    formData.value.deviceChgTel1,
    formData.value.deviceChgTel2,
    formData.value.deviceChgTel3,
  ),
)

const pickFirst = (...values) =>
  values.find((value) => value !== undefined && value !== null && String(value) !== '') || ''

const getCodeTitle = (codes, value) => {
  const stringValue = String(value || '')
  if (!stringValue) return ''
  const item = (codes || []).find((code) => String(code.code || code.value || '') === stringValue)
  return item?.title || item?.label || stringValue
}

const getTodayYmd = () => {
  const d = new Date()
  return `${d.getFullYear()}${String(d.getMonth() + 1).padStart(2, '0')}${String(d.getDate()).padStart(2, '0')}`
}

const getAdditionServiceName = (svc = {}) =>
  svc.serviceName || svc.socDescription || svc.rateNm || svc.prodNm || svc.addSvcNm || svc.soc || ''

const getAdditionServiceLabel = (svc = {}, procLabel = '') => {
  const name = getAdditionServiceName(svc)
  return name && procLabel ? `${name}(${procLabel})` : name
}

const resolvePayField = (key, ...alts) => {
  const sources = [formData.value.payData || {}, formData.value.billData || {}, formData.value]
  for (const src of sources) {
    for (const k of [key, ...alts]) {
      if (src?.[k] !== undefined && src?.[k] !== null && String(src[k]) !== '') return src[k]
    }
  }
  return ''
}

const SUCCESS_REVIEW_SERVICE_FIELDS = {
  P11: [
    'planName1',
    'planName2',
    'planNm',
    'prodNm',
    'socNm',
    'planName',
    'reservedProdNm',
    'planFtrNewParam',
    'changeDate',
    'actCode',
    'lstComActvDate',
    'moveSocCode',
    'monthFee',
    'monthFee2',
    'commMonthPayAmt',
    'baseMonthPayAmt',
  ],
  O11: [
    'reqWantFnNo',
    'reqWantMnNo',
    'reqWantRnNo',
    'wishNo',
    'wishNoc',
    'wishMarket',
    'telNoChg',
    'noLinkSvc',
    'numberChgConfirmCompleted',
  ],
  O12: [
    'unLockPw',
    'pauseRel',
    'longStopRsn',
    'autoRelRsvYn',
    'lostModelNm',
    'lostPhoneSn',
    'lossAuthSuccYn',
    'lossAuthSuccNm',
    'lossAuthBirthDt',
    'lossAuthEtc',
    'unpauseConfirmCompleted',
  ],
  R12: ['reqWireTypeCd', 'wirelessBlockConfirmCompleted'],
  R14: [
    'clauseInsuranceYn',
    'recCat1',
    'recCat2',
    'insrProdCd',
    'androidInsrProdCd',
    'iosInsrProdCd',
  ],
  O13: [
    'hasSim',
    'usimKindsCd',
    'reqUsimNm',
    'reqUsimName',
    'reqUsimSn',
    'usimPriceTypeCd',
    'usimPrice',
    'simPurchaseMethod',
    'simTypeCd',
    'eid',
    'imei',
    'imei1',
    'imei2',
    'simInfoConfirmCompleted',
  ],
  R15: [
    'shareUseState',
    'sharePhoneNum',
    'shareUsimNum',
    'dataSharingTargetNo',
    'dataSharingAuthCompleted',
    'dataSharingUsimCheckCompleted',
    'dataSharingAvailableChecked',
    'dataSharingAgreementCompleted',
    'dataSharingConfirmCompleted',
  ],
  R16: ['soloData', 'combineSoloConfirmCompleted'],
}

const SUCCESS_REVIEW_ADDITION_SERVICE_CODES = ['R11', 'R12']
const SUCCESS_REVIEW_DEFAULT_ADDITION_SERVICE_CODE = 'R11'
const SUCCESS_REVIEW_ADDITION_ACTIONS = {
  add: ['ADD', 'INSR', ''],
  cancel: ['CANCEL'],
}

const USIM_KIND_PRICES = {
  '01': 6600,
  '02': 8800,
  '08': 8800,
}

const getSelectedServiceCodes = (f) => (Array.isArray(f.serviceSelect) ? f.serviceSelect : [])

const hasSelectedService = (f, code) => getSelectedServiceCodes(f).includes(code)

const getServiceTargetCode = (f) => {
  const code = f.svcTgtCd || ''
  return ['HDN3', 'HCN3', 'XXXX', 'ETC'].includes(code) ? code : 'ETC'
}

const getUsimModelName = (f) => {
  if (f.simTypeCd === 'ESIM' || f.usimKindsCd === '09') return ''
  return f.reqUsimNm || ''
}

const getUsimPriceTypeCode = (f) => {
  if (f.hasSim !== false) return 'N'

  const value = f.usimPriceTypeCd || f.simPurchaseMethod || ''
  if (['I', 'R', '즉시납부', '선납'].includes(value)) return 'R'
  if (['B', '후청구', '다음달 요금합산'].includes(value)) return 'B'
  return ''
}

const getUsimPrice = (f) => {
  if (f.usimPrice !== undefined && f.usimPrice !== null && String(f.usimPrice) !== '') {
    return f.usimPrice
  }

  if (f.hasSim === false) {
    return USIM_KIND_PRICES[f.usimKindsCd] || ''
  }

  return ''
}

const getServiceChangeUsimSn = (f, isDataSharingJoin) => {
  return isDataSharingJoin ? f.shareUsimNum || '' : f.reqUsimSn || ''
}

const getMoveSocName = (f) => {
  return hasSelectedService(f, 'P11')
    ? f.planNm || f.prodNm || f.socNm || f.planName || f.reservedProdNm || ''
    : ''
}

const getDataSharingPlanName = (f) => {
  return pickFirst(
    f.dataSharingPlanName,
    f.opmdSvcSocNm,
    f.opmdSvcSocName,
    '알뜰폰 LTE 데이터쉐어링',
  )
}

const isCorporateOrGovernmentCustomer = (f) => ['JP', 'GO'].includes(f.cstmrTypeCd)

const isMinorCustomer = (f) => ['NM', 'FM'].includes(f.cstmrTypeCd)

const getPauseRel = (f) => {
  if (f.pauseRel) return f.pauseRel
  return hasSelectedService(f, 'O12') ? 'RSP' : ''
}

const getAutoRelRsvYn = (f) => {
  if (f.autoRelRsvYn) return f.autoRelRsvYn
  return hasSelectedService(f, 'O12') ? 'N' : ''
}

const getContactPhoneNo = (f) => {
  const targetNo = joinDigits(f.deviceChgTel1, f.deviceChgTel2, f.deviceChgTel3)
  const mobileNo = joinDigits(f.mobileNo1, f.mobileNo2, f.mobileNo3)
  const telNo = joinDigits(f.telNo1, f.telNo2, f.telNo3)

  if (!targetNo) return mobileNo || telNo
  return mobileNo && mobileNo !== targetNo ? mobileNo : telNo
}

const getDataSharingNewChangeCustomerTypeCd = (f) => {
  const cstmrTypeCd = f.cstmrTypeCd || ''
  if (['I', 'O', 'B', 'E'].includes(cstmrTypeCd)) return cstmrTypeCd
  if (cstmrTypeCd === 'JP') return 'B'
  if (cstmrTypeCd === 'GO') return 'E'

  const bizNo = joinDigits(f.cstmrJuridicalBizNo1, f.cstmrJuridicalBizNo2, f.cstmrJuridicalBizNo3)
  if (bizNo) return 'O'

  if (['NA', 'NM', 'FN', 'FM'].includes(cstmrTypeCd)) return 'I'
  return 'E'
}

const getInsuranceProductCode = (f) => {
  return f.insrProdCd || f.recCat2 || ''
}

// ASIS는 단말보험 OS 값을 API로 받지 않고 상품코드별 화면 매핑으로 구분했다.
// TOBE도 카테고리(recCat1)가 아닌 선택 상품코드(insrProdCd/recCat2)를 기준으로 교부 양식을 결정한다.
const IOS_INSURANCE_PRODUCT_CODES = new Set([
  'PL245L235',
  'PL245L236',
  'PL245L237',
  'PL214L317',
  'PL214L319',
  'PL214L316',
  // ASIS 화면은 중고/USIM 상품을 양쪽 아이콘으로 표시하지만, 기존 eform 분기는 iOS 양식으로 처리한다.
  'PL245L233',
  'PL245L234',
  'PL213M175',
  'PL212O953',
])

const BOTH_OS_INSURANCE_PRODUCT_CODES = new Set([
  'PL245L233',
  'PL245L234',
  'PL213M175',
  'PL212O953',
])

const ANDROID_INSURANCE_PRODUCT_CODES = new Set([
  'PL245L228',
  'PL245L229',
  'PL245L230',
  'PL245L231',
  'PL245L232',
  'PL248N660',
  'PL248N661',
  'PL248N662',
  'PL248M658',
  'PL248N659',
  'PL214L310',
  'PL214L312',
])

const getInsuranceDeviceOs = (f) => {
  const insrProdCd = getInsuranceProductCode(f)
  const selectedOs = String(f.insuranceDeviceOs || '').toLowerCase()

  // 중고/USIM 보험은 ASIS 서비스변경 목록에서는 양쪽 OS로 노출될 수 있지만,
  // 신청서 리포트 정의상 PL245L233/234는 iosInsrProdCd(5090101) 항목에 매핑되어야 한다.
  if (['PL245L233', 'PL245L234'].includes(insrProdCd)) return 'ios'

  if (BOTH_OS_INSURANCE_PRODUCT_CODES.has(insrProdCd)) {
    return ['ios', 'android'].includes(selectedOs) ? selectedOs : 'android'
  }

  if (IOS_INSURANCE_PRODUCT_CODES.has(insrProdCd)) return 'ios'
  if (ANDROID_INSURANCE_PRODUCT_CODES.has(insrProdCd)) return 'android'

  return ''
}

const getCorporateUserName = (f) => {
  if (!isCorporateOrGovernmentCustomer(f) || f.cstmrVisitTypeCd !== 'VDP') return ''
  return f.minorAgentNm || ''
}

const getBirthDateFromRrn = (value) => {
  const digits = String(value || '').replace(/[^0-9]/g, '')
  if (digits.length === 8 || digits.length === 9) return digits.slice(0, 8)
  if (digits.length < 7) return digits

  const yy = digits.slice(0, 2)
  const mmdd = digits.slice(2, 6)
  const genderDigit = digits.charAt(6)
  if (['1', '2', '5', '6'].includes(genderDigit)) return `19${yy}${mmdd}`
  if (['3', '4', '7', '8'].includes(genderDigit)) return `20${yy}${mmdd}`
  return digits.slice(0, 6)
}

const getGenderFromRrn = (value) => {
  const digits = String(value || '').replace(/[^0-9]/g, '')
  if (digits.length === 8 || digits.length < 7) return ''
  const genderDigit = digits.length === 9 ? digits.charAt(8) : digits.charAt(6)
  if (['1', '3', '5', '7'].includes(genderDigit)) return 'M'
  if (['2', '4', '6', '8'].includes(genderDigit)) return 'F'
  return ''
}

const getLegalAgentBirth = (f) => {
  return pickFirst(
    f.repBirthDate,
    getBirthDateFromRrn(joinDigits(f.repRegistrationNo1, f.repRegistrationNo2)),
    getBirthDateFromRrn(joinDigits(f.repForeignerNo1, f.repForeignerNo2)),
    getBirthDateFromRrn(f.minorAgentRrn),
  )
}

const getLegalAgentGender = (f) => {
  return pickFirst(
    f.repGender,
    getGenderFromRrn(joinDigits(f.repRegistrationNo1, f.repRegistrationNo2)),
    getGenderFromRrn(joinDigits(f.repForeignerNo1, f.repForeignerNo2)),
    getGenderFromRrn(f.minorAgentRrn),
  )
}

const getCorporateDelegateBirth = (f) => {
  return pickFirst(
    f.agentBirthDate,
    getBirthDateFromRrn(f.jrdclAgentRrn),
    getBirthDateFromRrn(f.minorAgentRrn),
  )
}

const getCorporateDelegateGender = (f) => {
  return pickFirst(
    f.agentGender,
    getGenderFromRrn(f.jrdclAgentRrn),
    getGenderFromRrn(f.minorAgentRrn),
  )
}

const getCorporateUserBirth = (f) => {
  if (isCorporateOrGovernmentCustomer(f) && f.cstmrVisitTypeCd === 'VDP') {
    return getCorporateDelegateBirth(f)
  }

  return (
    f.cstmrJuridicalBirth || f.realUserBirth || joinDigits(f.realUserRrn1, f.realUserRrn2) || ''
  )
}

const createJsonData = (source, keys) =>
  keys.reduce((acc, key) => {
    acc[key] = source[key] ?? ''
    return acc
  }, {})

const createJsonDataParameter = (jsonData) => [
  {
    name: 'jsondata',
    value: JSON.stringify(jsonData),
  },
]

const logEformParameters = ({
  mode,
  params,
  servicechangeJsonData,
  newchangeJsonData,
  baseJsonData,
}) => {
  const label = mode === 'success-review' ? '재확인 성공건' : '일반'

  console.log(
    `[ServiceChangeAgreement][${label}] eformFormParameters:\n` + JSON.stringify(params, null, 2),
  )
  console.log(
    `[ServiceChangeAgreement][${label}] servicechange jsondata:\n` +
      JSON.stringify(servicechangeJsonData, null, 2),
  )

  if (useNewChangeTemplate.value) {
    console.log(
      `[ServiceChangeAgreement][${label}] newchange jsondata:\n` +
        JSON.stringify(newchangeJsonData, null, 2),
    )
  }

  if (deviceOs.value === 'ios') {
    console.log(
      `[ServiceChangeAgreement][${label}] ios jsondata:\n` +
        JSON.stringify(createJsonData(baseJsonData, iosInsuranceJsonDataKeys), null, 2),
    )
  }

  if (deviceOs.value === 'android') {
    console.log(
      `[ServiceChangeAgreement][${label}] android jsondata:\n` +
        JSON.stringify(createJsonData(baseJsonData, androidInsuranceJsonDataKeys), null, 2),
    )
  }
}

const isSuccessOnlyReview = ref(false)

const getSuccessServiceCodes = () =>
  isSuccessOnlyReview.value
    ? store.getSuccessfulServiceSelect?.() || []
    : getSelectedServiceCodes(formData.value)

const getSuccessProcessResults = () =>
  isSuccessOnlyReview.value ? store.getSuccessfulProcessResults?.() || [] : []

const normalizeSuccessCodes = (codes = []) => [
  ...new Set((Array.isArray(codes) ? codes : []).filter(Boolean)),
]

const hasSuccessService = (successCodes = [], code) =>
  normalizeSuccessCodes(successCodes).includes(code)

const isSameSoc = (a, b) => String(a || '').trim() === String(b || '').trim()

const isSameProdSeq = (a, b) => String(a || '').trim() === String(b || '').trim()

const toSuccessReviewAdditionService = (item = {}) => ({
  soc: item.soc || '',
  serviceCode: item.soc || '',
  serviceName: item.serviceName || item.soc || '',
  prodHstSeq: item.prodHstSeq || '',
  svcTgtCd: item.svcTgtCd || SUCCESS_REVIEW_DEFAULT_ADDITION_SERVICE_CODE,
  flag: item.action === 'CANCEL' ? 'N' : '',
})

const getSuccessReviewAdditionServices = (actionGroup = 'add') => {
  const validActions = SUCCESS_REVIEW_ADDITION_ACTIONS[actionGroup] || []
  return getSuccessProcessResults()
    .filter((item) => SUCCESS_REVIEW_ADDITION_SERVICE_CODES.includes(item.svcTgtCd))
    .filter((item) => validActions.includes(item.action || ''))
    .map(toSuccessReviewAdditionService)
}

const isSuccessfulAddition = (svc = {}, actionGroup = 'add') => {
  if (!isSuccessOnlyReview.value) return true

  const successResults = getSuccessProcessResults()
  if (successResults.length === 0) return false

  const svcTgtCd = svc.svcTgtCd || SUCCESS_REVIEW_DEFAULT_ADDITION_SERVICE_CODE
  const soc = svc.soc || svc.serviceCode || svc.svcCd || ''
  const prodHstSeq = svc.prodHstSeq || svc.productSeqNo || svc.prodSeqNo || ''
  const validActions = SUCCESS_REVIEW_ADDITION_ACTIONS[actionGroup] || []

  return successResults.some((item) => {
    if (!SUCCESS_REVIEW_ADDITION_SERVICE_CODES.includes(item.svcTgtCd)) return false
    if (item.svcTgtCd !== svcTgtCd) return false

    const action = item.action || ''
    if (!validActions.includes(action)) return false
    if (soc && item.soc && !isSameSoc(item.soc, soc)) return false
    if (prodHstSeq && item.prodHstSeq && !isSameProdSeq(item.prodHstSeq, prodHstSeq)) return false

    return Boolean(soc || prodHstSeq)
  })
}

const clearFields = (target, fields) => {
  fields.forEach((field) => {
    target[field] = ''
  })
}

const clearFailedServiceFields = (target, successCodes) => {
  Object.entries(SUCCESS_REVIEW_SERVICE_FIELDS).forEach(([serviceCode, fields]) => {
    if (!hasSuccessService(successCodes, serviceCode)) {
      clearFields(target, fields)
    }
  })
}

const pruneFailedServiceFields = (source, successCodes) => {
  const normalizedSuccessCodes = normalizeSuccessCodes(successCodes)
  const successAdditionList = getSuccessReviewAdditionServices('add')
  const successAdditionCancelList = getSuccessReviewAdditionServices('cancel')
  const filteredAdditionList = (source.additionList || []).filter((svc) =>
    isSuccessfulAddition(svc, 'add'),
  )
  const filteredAdditionCancelList = (source.additionCancelList || []).filter((svc) =>
    isSuccessfulAddition(svc, 'cancel'),
  )
  const data = {
    ...source,
    serviceSelect: normalizedSuccessCodes,
    additionList: filteredAdditionList.length ? filteredAdditionList : successAdditionList,
    additionCancelList: filteredAdditionCancelList.length
      ? filteredAdditionCancelList
      : successAdditionCancelList,
  }

  clearFailedServiceFields(data, normalizedSuccessCodes)

  return data
}

const eformSourceData = computed(() => {
  if (!isSuccessOnlyReview.value) return formData.value

  const successCodes = getSuccessServiceCodes()
  return pruneFailedServiceFields(formData.value, successCodes)
})

const newChangeJsonDataKeys = [
  'operTypeCd', // 1010101 업무구분
  'serviceTypeCd', // 1010102 선후불구분
  'cstmrTypeCd', // 1010103 고객구분
  'agentCd', // 1010104 판매점코드/판매점명
  'telnum', // 1010105 연락처
  'modelMonthlyPricdCd', // 1010201 구분
  'modelPrice', // 1010202 출고가
  'modelSprt', // 1010203 공통지원금
  'addDcAmt', // 1010204 추가지원금
  'etcDcAmt', // 1010205 기타할인
  'custPayAmt', // 1010206 고객수납금(현금/카드)
  'modelInstallment', // 1010207 할부원금
  'modelMonthly', // 1010208 월 할부금 개월수
  'realMdlInstamt', // 1010209 월 할부금
  'avgInstFee', // 1010210 월 평균할부수수료
  'reqModelName', // 1010211 핸드폰모델명
  'reqPhoneSn', // 1010212 일련번호
  'phoneMonthPayAmt', // 1010213 핸드폰 월 납부액
  'phoneTotSubsidyAmt', // 1010214 총 지원금
  'deviceDiscountAmt', // 1010215 단말할인
  'planDiscountAmt', // 1010216 요금할인(지원금)
  'penaltySupportAmt', // 1010217 위약금 대납
  'joinFeeSupportAmt', // 1010218 가입비 대납
  'etcSupportAmt', // 1010219 기타
  'socCode', // 1010301 선택한 요금상품
  'monthFeeVat', // 1010302 월정액 요금(VAT 포함)
  'monthFeeDiscountAmt', // 1010303 월 요금할인액
  'telecomMonthPay', // 1010304 통신요금 월 납부액
  'baseMonthPay', // 1010401 월 기본 납부액
  'cstmrName', // 1010501 고객명(법인명)
  'cstmrNativeRrn', // 1010502 개인(생년월일)/법인(등록번호)
  'gender', // 1010503 성별
  'cstmrForeignerRrn', // 1010504 외국인등록번호(외국인의 경우)
  'cstmrForeignerNation', // 1010505 국적(외국인의 경우)
  'cstmrForeignerPn', // 1010506 여권번호(외국인의 경우)
  'cstmrReceiveTelNo', // 1010507 연락받을 전화번호
  'cstmrAddr', // 1010508 주소
  'cstmrBillSendCode', // 1010509 명세서 종류
  'cstmrMail', // 1010510 이메일 주소
  'selfCertType', // 1010511 본인인증
  'cstmrForeignerSdate', // 1010512 체류기간(외국인의 경우) 시작일자
  'cstmrForeignerEdate', // 1010513 체류기간(외국인의 경우) 종료일자
  'reqPayTypeCd', // 1010601 요금자동납부_구분
  'reqPayType', // 1010602 자동납부_구분
  'autoPayOrgNm', // 1010603 자동납부_은행/카드사 명
  'autoPayAcctCardNo', // 1010604 자동납부_계좌/카드 번호
  'autoPayCardExp', // 1010605 자동납부_카드유효기간
  'combineId', // 1010606 통합청구_통합청구계정ID
  'othersPaymentNm', // 1010607 타인납부동의_납부고객명
  'othersPaymentRelation', // 1010608 타인납부동의_관계
  'othersPaymentRrn', // 1010609 타인납부동의_생년월일
  'additionNm', // 1010701 데이터/부가상품
  'rantal', // 1010702 합계
  'billSvc', // 1010703 통신과금서비스
  'smPayPwdUseCd', // 1010704 통신과금서비스_휴대폰소액결제 비밀번호 이용_구분
  'wishNoLinkSvc', // 1010705 가입희망번호/번호연결서비스
  'usimKindsCd', // 1010706 SIM모델명_USIM/eSIM_구분
  'reqUsimName', // 1010707 SIM모델명
  'imei', // 1010708 IMEI(일련번호)
  'reqUsimSn', // 1010709 SIM일련번호
  'usimPriceType', // 1010710 SIM비용_구분
  'usimPrice', // 1010711 SIM비용
  'usimPayMthdCd', // 1010712 가입비
  'moveMobileNo', // 1010801 번호이동할 전화번호
  'npBcntrTypeCd', // 1010802 변경 전 통신사_선후불구분
  'moveCompany', // 1010803 변경 전 통신사
  'moveCompanyCd', // 1010804 변경 전 통신사_MVNO
  'moveThismonthPayType', // 1010805 이번달 사용요금
  'moveAllotmentStat', // 1010806 핸드폰 할부금
  'moveRefundAgreeFlag', // 1010807 미환급액 요금상계(후불)
  'trnsNm', // 1010901 고객명(법인명)
  'trnsMobileNo', // 1010902 변경대상 전화번호
  'cstmrNativeBirth', // 1010903 생년월일(법인/사업자등록번호)
  'trnsGender', // 1010904 성별
  'remainPayDivCd', // 1010905 핸드폰 할부금
  'appFormReqDt', // 1011001 신청일자
  'authInfo', // 1011001 인증정보
  'appBlckAgrmYn', // 1060101 법정대리인동의서_유해정보 차단_네트워크 유해차단
  'blckAppDivCd', // 1060102 법정대리인동의서_유해정보 차단_청소년 유해정보 차단
  'minorAgentNm', // 1060103 법정대리인동의서_법정대리인 성명
  'minorAgentRelTypeCd', // 1060104 법정대리인동의서_신청고객과의 관계
  'minorAgentRrn', // 1060105 법정대리인동의서_생년월일
  'minorAgentGender', // 1060106 법정대리인동의서_성별
  'minorAgentTelNo', // 1060107 법정대리인동의서_연락받을 전화번호
  'minorDelegator', // 1060201 위임장_위임하시는 분
  'minorAgent', // 1060202 위임장_위임받는 분
  'minorAgentRelTypeCd2', // 1060203 위임장_위임하는 분과의 관계
  'minorAgentRrn2', // 1060204 위임장_생년월일
  'minorAgentGender2', // 1060205 위임장_성별
  'minorAgentTelNo2', // 1060206 위임장_연락받을 전화번호
  'gdnFormReqDt', // 1060301 법정대리인동의서/위임장_하단 신청일자
]

const serviceChangeJsonDataKeys = [
  'agentCd', // 2010101 판매점코드/판매점명
  'telnum', // 2010102 연락처
  'saleManagerNm', // 로그인 사용자명
  'svcTgtCd', // 2010103 업무구분
  'etcSvcTgtCd', // 2010104 업무구분_기타
  'mobilePhoneAmt', // 2010201 핸드폰 대금
  'modelPrice', // 2010202 출고가
  'modelSprt', // 2010203 공시지원금
  'etcDcAmt', // 2010204 기타할인
  'modelInstallment', // 2010205 할부원금
  'modelMonthly', // 2010206 월 할부금(실구매가) 할부 개월수
  'modelInstamt', // 2010207 월 할부금 개월
  'monInstFee', // 2010208 월 할부수수료
  'reqModelNm', // 2010209 핸드폰모델명
  'reqPhoneSn', // 2010210 일련번호
  'phoneMonthPayAmt', // 2010211 핸드폰 월 납부액(월 할부금 + 월 할부수수료)
  'subsidy', // 2010212 보조금
  'deviceDcAmt', // 2010213 단말 할인
  'penaltySupportAmt', // 2010214 위약금 대납
  'payBack', // 2010215 페이백
  'joinFeeSupportAmt', // 2010216 가입비 대납
  'etcSupportAmt', // 2010217 기타
  'moveSocCode', // 2010301 선택 요금상품
  'monthFee', // 2010302 월정액 요금
  'monthFee2', // 2010303 월정액 요금
  'commMonthPayAmt', // 2010304 통신요금 월 납부액
  'baseMonthPayAmt', // 2010305 월 기본 납부액
  'cstmrNm', // 2010401 고객명(법인명)
  'cstmrNativeBirth', // 2010402 개인(생년월일)
  'gender', // 2010403 성별
  'cstmrPrivateBizNo', // 2010404 등록번호(법인/사업자/외국인)
  'cstmrMobileNo', // 2010405 연락처
  'cstmrTelNo', // 2010406 신청대상 전화번호
  'cstmrEmailAdr', // 2010407 e-mail 주소
  'othersPaymentYn', // 2010501 요금자동납부_구분
  'reqPayTypeCd', // 2010502 자동납부_구분
  'autoPayOrgNm', // 2010503 자동납부_은행/카드사명
  'autoPayAcctCardNo', // 2010504 자동납부_계좌/카드 번호
  'autoPayCardExpDt', // 2010505 자동납부_카드유효기간
  'autoChrgTypeCd', // 2010506 자동충전_구분
  'balReachAmt', // 2010507 충전잔여액_도달금액
  'balChrgAmt', // 2010508 충전잔여액 충전금액
  'remainDayChrgDt', // 2010509 충전잔여일_충전일
  'remainDayChrgAmt', // 2010510 충전잔여일 충전금액
  'othersPaymentNm', // 2010511 타인납부동의_납부고객명
  'othersPaymentRelTypeCd', // 2010512 타인납부동의_관계
  'othersPaymentRrn', // 2010513 타인납부동의_생년월일
  'telNoChg', // 2010601 번호 변경
  'noLinkSvc', // 2010602 번호연결서비스
  'reqUsimNm', // 2010603 USIM모델명
  'reqUsimSn', // 2010604 USIM일련번호
  'usimPriceTypeCd', // 2010605 USIM비용_구분
  'usimPrice', // 2010606 USIM비용_USIM비용
  'billEmailAdr', // 2010607 e-mail 주소
  'cstmrAdr', // 2010608 요금청구 주소
  'socCode', // 2010609 요금 상품
  'addtionInfo', // 2010610 데이터/부가상품
  'reqWireTypeCd', // 2010611 무선데이터
  'pauseRel', // 2010612 일시정지/해제
  'longStopRsn', // 2010613 일시정지/해제_장기이용정지 사유
  'autoRelRsvYn', // 2010614 일시정지/해제_자동해제예약
  'lostModelNm', // 2010615 핸드폰 분실신고 권한_핸드폰모델명
  'lostPhoneSn', // 2010616 핸드폰 분실신고 권한_일련번호
  'lossAuthSuccYn', // 2010617 핸드폰 분실신고 권한_권한승계
  'lossAuthSuccNm', // 2010618 핸드폰 분실신고 권한_권한승계자 성명
  'lossAuthBirthDt', // 2010619 핸드폰 분실신고 권한_생년월일
  'lossAuthEtc', // 2010620 기타
  'cstmrJuridicalUserNm', // 2010621 법인폰 실사용자 등록_성명
  'cstmrJuridicalBirth', // 2010622 법인폰 실사용자 등록_주민번호
  'svcChgReqDt', // 2010701 신청일자
  'shopNm', // 2010702 신청서 접수점
  'authInfo', // 2010703 인증정보
]

const androidInsuranceJsonDataKeys = [
  'androidInsrProdCd', // 4090101 보험코드
  'androidReqDt', // 4090201 신청일자
  'androidCstmrNativeBirth', // 4090202 신청인 생년월일
  'authInfo', // 4090203 인증정보
]

const iosInsuranceJsonDataKeys = [
  'iosInsrProdCd', // 5090101 보험코드
  'iosReqDt', // 5090201 신청일자
  'iosCstmrNativeBirth', // 5090202 신청인 생년월일
  'authInfo', // 5090203 인증정보
]

const eformJsonData = computed(() => {
  const f = eformSourceData.value
  const successOnly = isSuccessOnlyReview.value
  const changeMobileNo = joinDigits(f.deviceChgTel1, f.deviceChgTel2, f.deviceChgTel3)
  const contactPhoneNo = getContactPhoneNo(f)
  const agencyContactNo = pickFirst(f.telephone, f.representativeTelephone)
  const email = f.emailAddr1 && f.emailAddr2 ? `${f.emailAddr1}@${f.emailAddr2}` : ''
  const shopCd = f.shopCd || ''
  const shopNm = f.shopNm || ''
  const selectedServiceCodes = getSelectedServiceCodes(f)
  const isDataSharingSelected = selectedServiceCodes.includes('R15')
  const isDataSharingJoin = isDataSharingSelected && f.shareUseState === 'shareUseState1'
  const isDataSharingCancel = isDataSharingSelected && f.shareUseState === 'shareUseState2'
  const dataSharingCancelNo = f.dataSharingTargetNo || f.sharePhoneNum || ''
  const dataSharingName =
    isDataSharingJoin && f.sharePhoneNum
      ? `알뜰폰 LTE 데이터쉐어링 가입(${f.sharePhoneNum})`
      : isDataSharingCancel
        ? `알뜰폰 LTE 데이터쉐어링 해지${dataSharingCancelNo ? `(${dataSharingCancelNo})` : ''}`
        : ''
  const newChangeAdditionNm = isDataSharingJoin ? dataSharingName : ''
  const dataSharingCancelInfo = isDataSharingCancel ? '데이터쉐어링(해지)' : ''
  const additionApplyInfo = (f.additionList || []).map((svc) =>
    getAdditionServiceLabel(svc, svc.flag === 'Y' ? '변경' : '가입'),
  )
  const additionCancelInfo = (f.additionCancelList || []).map((svc) =>
    getAdditionServiceLabel(svc, '해지'),
  )
  const canShowAdditionInfo =
    !successOnly ||
    SUCCESS_REVIEW_ADDITION_SERVICE_CODES.some((code) => selectedServiceCodes.includes(code)) ||
    selectedServiceCodes.includes('R15')
  const addtionInfo = canShowAdditionInfo
    ? [...additionApplyInfo, ...additionCancelInfo, dataSharingCancelInfo].filter(Boolean).join(',')
    : ''
  const serviceChangeAddtionInfo = canShowAdditionInfo
    ? [...additionApplyInfo, ...additionCancelInfo, dataSharingCancelInfo].filter(Boolean).join(',')
    : ''
  const noDedicatedFieldServiceInfo = [
    hasSelectedService(f, 'R14') ? '안심보험(가입)' : '',
    hasSelectedService(f, 'R16') ? '아무나 SOLO 결합(가입)' : '',
    hasSelectedService(f, 'O13')
      ? f.simTypeCd === 'ESIM' || f.usimKindsCd === '09'
        ? 'eSIM변경'
        : 'USIM변경'
      : '',
  ]
    .filter(Boolean)
    .join(', ')
  const hiddenAdditionChangeText = (f.additionCancelList || [])
    .map(getAdditionServiceName)
    .filter((name) => name && !addtionInfo.includes(name))
    .map((name) => `부가서비스 해지: ${name}`)
    .join('\n')
  const lossAuthEtc = [f.lossAuthEtc, noDedicatedFieldServiceInfo, hiddenAdditionChangeText]
    .filter(Boolean)
    .join('\n')
  const rawReqWireTypeCd =
    f.blockService === 'blockService2'
      ? 'Y'
      : f.blockService === 'blockService1'
        ? 'N'
        : f.reqWireTypeCd || ''
  const reqWireTypeCd =
    hasSelectedService(f, 'R12') && ['N', 'Y', 'R'].includes(rawReqWireTypeCd)
      ? rawReqWireTypeCd
      : ''
  const privateNo = joinDigits(f.cstmrJuridicalRrn1, f.cstmrJuridicalRrn2)
  const foreignerNo = joinDigits(f.cstmrForeignerRrn1, f.cstmrForeignerRrn2)
  const bizNo = joinDigits(f.cstmrJuridicalBizNo1, f.cstmrJuridicalBizNo2, f.cstmrJuridicalBizNo3)
  const isCorporateOrGovernment = isCorporateOrGovernmentCustomer(f)
  const cstmrPrivateBizNo = isCorporateOrGovernment
    ? privateNo || bizNo
    : ['FN', 'FM'].includes(f.cstmrTypeCd)
      ? foreignerNo || bizNo
      : privateNo || bizNo
  const cstmrNativeBirth = isCorporateOrGovernment ? privateNo : f.userBirthDate || ''
  const isMinor = isMinorCustomer(f)
  const isCorporateDelegate = isCorporateOrGovernmentCustomer(f) && f.cstmrVisitTypeCd === 'VDP'
  const todayYmd = getTodayYmd()
  const agentPhoneNo = joinDigits(
    f.minorAgentTelFnNo || f.repPhone1,
    f.minorAgentTelMnNo || f.repPhone2,
    f.minorAgentTelRnNo || f.repPhone3,
  )
  const legalAgentRelationName = getCodeTitle(
    legalAgentRelationCodes.value,
    f.minorAgentRelTypeCd || f.repRelation,
  )
  const delegateRelationName = f.minorAgentRelTypeNm || ''
  const legalAgentRelationCode = f.minorAgentRelTypeCd || f.repRelation || ''
  const fillLegalAgent = isMinor
  const fillDelegation = isCorporateDelegate
  const fillLegalAgentOrDelegation = fillLegalAgent || fillDelegation
  const selectedMoveSocName = getMoveSocName(f)
  const usimModelName = getUsimModelName(f)
  const usimPriceTypeCode = hasSelectedService(f, 'O13') ? getUsimPriceTypeCode(f) : ''
  const serviceChangeUsimSn = getServiceChangeUsimSn(f, isDataSharingJoin)
  const insuranceProductCode = getInsuranceProductCode(f)

  return {
    // 서비스변경신청서(H1 201xxxx) - reference/H1 정의서 순서
    agentCd: shopNm && shopCd ? `${shopNm} / ${shopCd}` : shopNm || shopCd, // 2010101 판매점코드/판매점명
    telnum: agencyContactNo, // 2010102 연락처
    saleManagerNm: f.managerNm || '', // 로그인 사용자명
    svcTgtCd: getServiceTargetCode(f), // 2010103 업무구분
    etcSvcTgtCd: '', // 2010104 업무구분_기타
    mobilePhoneAmt: '', // 2010201 핸드폰 대금
    modelPrice: '', // 2010202 출고가
    modelSprt: '', // 2010203 공시지원금
    etcDcAmt: '', // 2010204 기타할인
    modelInstallment: '', // 2010205 할부원금
    modelMonthly: '', // 2010206 월 할부금(실구매가) 할부 개월수
    modelInstamt: '', // 2010207 월 할부금 개월
    monInstFee: '', // 2010208 월 할부수수료
    reqModelNm: '', // 2010209 핸드폰모델명
    reqPhoneSn: '', // 2010210 일련번호
    phoneMonthPayAmt: '', // 2010211 핸드폰 월 납부액(월 할부금 + 월 할부수수료)
    subsidy: '', // 2010212 보조금
    deviceDcAmt: '', // 2010213 단말 할인
    penaltySupportAmt: '', // 2010214 위약금 대납
    payBack: '', // 2010215 페이백
    joinFeeSupportAmt: '', // 2010216 가입비 대납
    etcSupportAmt: '', // 2010217 기타
    moveSocCode: '', // 2010301 선택 요금상품  20260714 요금제 변경이 경우 표시 제외 -> 휴대폰과 같이 변경하는 경우 세팅
    monthFee: f.monthFee || '', // 2010302 월정액 요금
    monthFee2: f.monthFee2 || f.monthFee || '', // 2010303 월정액 요금
    commMonthPayAmt: f.commMonthPayAmt || '', // 2010304 통신요금 월 납부액
    baseMonthPayAmt: f.baseMonthPayAmt || '', // 2010305 월 기본 납부액
    cstmrNm: f.cstmrNm || '', // 2010401 고객명(법인명)
    cstmrNativeBirth, // 2010402 개인(생년월일)
    gender: f.userGender || '', // 2010403 성별
    cstmrPrivateBizNo, // 2010404 등록번호(법인/사업자/외국인)
    cstmrMobileNo: joinDigits(f.mobileNo1, f.mobileNo2, f.mobileNo3), // 2010405 연락처
    cstmrTelNo: changeMobileNo, // 2010406 신청대상 전화번호
    cstmrEmailAdr: email, // 2010407 e-mail 주소
    othersPaymentYn: resolvePayField('othersPaymentYn'), // 2010501 요금자동납부_구분
    reqPayTypeCd: resolvePayField('reqPayTypeCd'), // 2010502 자동납부_구분
    autoPayOrgNm: resolvePayField('autoPayOrgNm', 'bankNm'), // 2010503 자동납부_은행/카드사명
    autoPayAcctCardNo: resolvePayField('autoPayAcctCardNo', 'acctNo'), // 2010504 자동납부_계좌/카드 번호
    autoPayCardExpDt: resolvePayField('autoPayCardExpDt'), // 2010505 자동납부_카드유효기간
    autoChrgTypeCd: resolvePayField('autoChrgTypeCd'), // 2010506 자동충전_구분
    balReachAmt: resolvePayField('balReachAmt'), // 2010507 충전잔여액_도달금액
    balChrgAmt: resolvePayField('balChrgAmt'), // 2010508 충전잔여액 충전금액
    remainDayChrgDt: resolvePayField('remainDayChrgDt'), // 2010509 충전잔여일_충전일
    remainDayChrgAmt: resolvePayField('remainDayChrgAmt'), // 2010510 충전잔여일 충전금액
    othersPaymentNm: resolvePayField('othersPaymentNm'), // 2010511 타인납부동의_납부고객명
    othersPaymentRelTypeCd: resolvePayField('othersPaymentRelTypeCd'), // 2010512 타인납부동의_관계
    othersPaymentRrn: resolvePayField('othersPaymentRrn'), // 2010513 타인납부동의_생년월일
    telNoChg: f.wishNo || joinDigits(f.reqWantFnNo, f.reqWantMnNo, f.reqWantRnNo) || '', // 2010601 번호 변경
    noLinkSvc: f.noLinkSvc || '', // 2010602 번호연결서비스
    reqUsimNm: usimModelName, // 2010603 USIM모델명
    reqUsimSn: serviceChangeUsimSn, // 2010604 USIM일련번호
    usimPriceTypeCd: usimPriceTypeCode, // 2010605 USIM비용_구분
    usimPrice: getUsimPrice(f), // 2010606 USIM비용_USIM비용
    billEmailAdr: '', // 2010607 e-mail 주소
    cstmrAdr: '', // 2010608 요금청구 주소
    socCode: isDataSharingJoin ? 'KISOPMDSB' : selectedMoveSocName, // 2010609 요금 상품
    addtionInfo, // 2010610 데이터/부가상품
    reqWireTypeCd, // 2010611 무선데이터
    pauseRel: getPauseRel(f), // 2010612 일시정지/해제
    longStopRsn: f.longStopRsn || '', // 2010613 일시정지/해제_장기이용정지 사유
    autoRelRsvYn: getAutoRelRsvYn(f), // 2010614 일시정지/해제_자동해제예약
    lostModelNm: f.lostModelNm || '', // 2010615 핸드폰 분실신고 권한_핸드폰모델명
    lostPhoneSn: f.lostPhoneSn || '', // 2010616 핸드폰 분실신고 권한_일련번호
    lossAuthSuccYn: f.lossAuthSuccYn || '', // 2010617 핸드폰 분실신고 권한_권한승계
    lossAuthSuccNm: f.lossAuthSuccNm || '', // 2010618 핸드폰 분실신고 권한_권한승계자 성명
    lossAuthBirthDt: f.lossAuthBirthDt || '', // 2010619 핸드폰 분실신고 권한_생년월일
    lossAuthEtc, // 2010620 기타
    cstmrJuridicalUserNm: getCorporateUserName(f), // 2010621 법인폰 실사용자 등록_성명
    cstmrJuridicalBirth: getCorporateUserBirth(f), // 2010622 법인폰 실사용자 등록_주민번호
    svcChgReqDt: todayYmd, // 2010701 신청일자
    shopNm: f.realShopNm || f.shopNm || f.cntpntShopNm || f.cpntNm || f.agentNm || '', // 2010702 신청서 접수점
    authInfo: f.authInfo || '', // 2010703 인증정보

    // 가입신청서(H1 101xxxx) - 신규 전용/보정 데이터
    operTypeCd: isDataSharingCancel ? '' : 'NAC3', // 1010101 업무구분
    serviceTypeCd: isDataSharingCancel ? '' : f.serviceTypeCd || 'PO', // 1010102 선후불구분
    cstmrTypeCd: f.cstmrTypeCd || 'I', // 1010103 고객구분
    modelMonthlyPricdCd: f.modelMonthlyPricdCd || '', // 1010201 구분
    addDcAmt: f.addDcAmt || '', // 1010204 추가지원금
    custPayAmt: f.custPayAmt || '', // 1010206 고객수납금(현금/카드)
    realMdlInstamt: f.realMdlInstamt || '', // 1010209 월 할부금
    avgInstFee: f.avgInstFee || '', // 1010210 월 평균할부수수료
    reqModelName: f.reqModelName || '', // 1010211 핸드폰모델명
    phoneTotSubsidyAmt: f.phoneTotSubsidyAmt || '', // 1010214 총 지원금
    deviceDiscountAmt: f.deviceDiscountAmt || '', // 1010215 단말할인
    planDiscountAmt: f.planDiscountAmt || '', // 1010216 요금할인(지원금)
    monthFeeVat: f.monthFeeVat || '', // 1010302 월정액 요금(VAT 포함)
    monthFeeDiscountAmt: f.monthFeeDiscountAmt || '', // 1010303 월 요금할인액
    telecomMonthPay: f.telecomMonthPay || '', // 1010304 통신요금 월 납부액
    baseMonthPay: f.baseMonthPay || '', // 1010401 월 기본 납부액
    cstmrName: f.cstmrNm || '', // 1010501 고객명(법인명)
    cstmrNativeRrn: f.userBirthDate || '', // 1010502 개인(생년월일)/법인(등록번호)
    cstmrForeignerRrn: joinDigits(f.cstmrForeignerRrn1, f.cstmrForeignerRrn2), // 1010504 외국인등록번호(외국인의 경우)
    cstmrForeignerNation: f.cstmrForeignerNation || '', // 1010505 국적(외국인의 경우)
    cstmrForeignerPn: f.cstmrForeignerPn || '', // 1010506 여권번호(외국인의 경우)
    cstmrReceiveTelNo: contactPhoneNo || '', // 1010507 연락받을 전화번호
    cstmrAddr: f.address ? `${f.address} ${f.detailAddress || ''}`.trim() : f.addr || '', // 1010508 주소
    cstmrBillSendCode: f.cstmrBillSendCode || 'CB', // 1010509 명세서 종류
    cstmrMail: email, // 1010510 이메일 주소
    selfCertType: f.selfCertType || '', // 1010511 본인인증
    cstmrForeignerSdate: f.cstmrForeignerSdate || '', // 1010512 체류기간(외국인의 경우) 시작일자
    cstmrForeignerEdate: f.cstmrForeignerEdate || '', // 1010513 체류기간(외국인의 경우) 종료일자
    autoPayCardExp: resolvePayField('autoPayCardExpDt'), // 1010605 자동납부_카드유효기간
    reqPayType: resolvePayField('reqPayType', 'reqPayTypeCd'), // 1010602 자동납부_구분
    combineId: f.combineId || '', // 1010606 통합청구_통합청구계정ID
    othersPaymentRelation: resolvePayField('othersPaymentRelation', 'othersPaymentRelTypeCd'), // 1010608 타인납부동의_관계
    additionNm: newChangeAdditionNm, // 1010701 데이터/부가상품
    rantal: f.rantal || '', // 1010702 합계
    billSvc: f.billSvc || '', // 1010703 통신과금서비스
    smPayPwdUseCd: f.smPayPwdUseCd || '', // 1010704 통신과금서비스_휴대폰소액결제 비밀번호 이용_구분
    wishNoLinkSvc: f.wishNoLinkSvc || f.wishNo || '', // 1010705 가입희망번호/번호연결서비스
    usimKindsCd: f.usimKindsCd || '', // 1010706 SIM모델명_USIM/eSIM_구분
    reqUsimName: usimModelName, // 1010707 SIM모델명
    imei: f.imei || f.imei1 || '', // 1010708 IMEI(일련번호)
    usimPriceType: usimPriceTypeCode, // 1010710 SIM비용_구분
    usimPayMthdCd: f.usimPayMthdCd || '', // 1010712 가입비
    moveMobileNo: f.moveMobileNo || '', // 1010801 번호이동할 전화번호
    npBcntrTypeCd: f.npBcntrTypeCd || '', // 1010802 변경 전 통신사_선후불구분
    moveCompany: f.moveCompany || '', // 1010803 변경 전 통신사
    moveCompanyCd: f.moveCompanyCd || '', // 1010804 변경 전 통신사_MVNO
    moveThismonthPayType: f.moveThismonthPayType || '', // 1010805 이번달 사용요금
    moveAllotmentStat: f.moveAllotmentStat || '', // 1010806 핸드폰 할부금
    moveRefundAgreeFlag: f.moveRefundAgreeFlag || '', // 1010807 미환급액 요금상계(후불)
    trnsNm: f.trnsNm || '', // 1010901 고객명(법인명)
    trnsMobileNo: f.trnsMobileNo || '', // 1010902 변경대상 전화번호
    trnsGender: f.trnsGender || '', // 1010904 성별
    remainPayDivCd: f.remainPayDivCd || '', // 1010905 핸드폰 할부금
    appFormReqDt: todayYmd, // 1011001 신청일자
    appBlckAgrmYn: f.appBlckAgrmYn || '', // 1060101 법정대리인동의서_유해정보 차단_네트워크 유해차단
    blckAppDivCd: f.blckAppDivCd || '', // 1060102 법정대리인동의서_유해정보 차단_청소년 유해정보 차단
    minorAgentNm: fillLegalAgent ? f.repName || f.minorAgentNm || '' : '', // 1060103 법정대리인동의서_법정대리인 성명
    minorAgentRelTypeCd: fillLegalAgent ? legalAgentRelationName : '', // 1060104 법정대리인동의서_신청고객과의 관계
    minorAgentRrn: fillLegalAgent ? getLegalAgentBirth(f) : '', // 1060105 법정대리인동의서_생년월일
    minorAgentGender: fillLegalAgent ? getLegalAgentGender(f) : '', // 1060106 법정대리인동의서_성별
    minorAgentTelNo: fillLegalAgent ? agentPhoneNo : '', // 1060107 법정대리인동의서_연락받을 전화번호
    minorDelegator: fillDelegation ? f.cstmrJuridicalRepNm || f.cstmrNm || '' : '', // 1060201 위임장_위임하시는 분
    minorAgent: fillDelegation ? f.minorAgentNm || f.jrdclAgentNm || f.repName || '' : '', // 1060202 위임장_위임받는 분
    minorAgentRelTypeCd2: fillLegalAgent
      ? legalAgentRelationCode
      : fillDelegation
        ? delegateRelationName
        : '', // 1060203 법정대리인/위임장_관계
    minorAgentRrn2: fillLegalAgent
      ? getLegalAgentBirth(f)
      : fillDelegation
        ? getCorporateDelegateBirth(f)
        : '', // 1060204 법정대리인/위임장_생년월일
    minorAgentGender2: fillLegalAgent
      ? getLegalAgentGender(f)
      : fillDelegation
        ? getCorporateDelegateGender(f)
        : '', // 1060205 법정대리인/위임장_성별
    minorAgentTelNo2: fillLegalAgentOrDelegation ? agentPhoneNo : '', // 1060206 법정대리인/위임장_연락받을 전화번호
    gdnFormReqDt: fillLegalAgentOrDelegation ? todayYmd : '', // 1060301 법정대리인동의서/위임장_하단 신청일자

    // 서비스변경 보정 데이터
    serviceChangeAddtionInfo,

    // 단말보험(안드로이드) - H1 409xxxx
    androidInsrProdCd: insuranceProductCode, // 4090101 보험코드
    androidReqDt: todayYmd, // 4090201 신청일자
    androidCstmrNativeBirth: f.userBirthDate || '', // 4090202 신청인 생년월일

    // 단말보험(아이폰) - H1 509xxxx
    iosInsrProdCd: insuranceProductCode, // 5090101 보험코드
    iosReqDt: todayYmd, // 5090201 신청일자
    iosCstmrNativeBirth: f.userBirthDate || '', // 5090202 신청인 생년월일
  }
})

const eformFormParameters = computed(() => {
  const baseJsonData = eformJsonData.value
  const servicechangeJsonData = createJsonData(baseJsonData, serviceChangeJsonDataKeys)
  const newchangeJsonData = createJsonData(baseJsonData, newChangeJsonDataKeys)
  const sourceData = eformSourceData.value
  const isDataSharingJoin =
    getSelectedServiceCodes(sourceData).includes('R15') &&
    sourceData.shareUseState === 'shareUseState1'
  const isCorporateOrGovernment = isCorporateOrGovernmentCustomer(sourceData)

  newchangeJsonData.addtionInfo = baseJsonData.additionNm
  if (isCorporateOrGovernment) {
    delete servicechangeJsonData.cstmrNativeBirth
    delete servicechangeJsonData.gender
    delete newchangeJsonData.gender
  }

  if (isDataSharingJoin) {
    newchangeJsonData.cstmrTypeCd = getDataSharingNewChangeCustomerTypeCd(sourceData)
    newchangeJsonData.socCode = getDataSharingPlanName(sourceData)
    if (!isCorporateOrGovernment) {
      delete newchangeJsonData.cstmrNativeBirth
    }
  }

  if (useNewChangeTemplate.value) {
    servicechangeJsonData.addtionInfo = baseJsonData.serviceChangeAddtionInfo
  }

  if (isDataSharingJoin) {
    servicechangeJsonData.reqUsimSn = ''
    servicechangeJsonData.socCode = ''
    servicechangeJsonData.usimPriceTypeCd = ''
    servicechangeJsonData.usimPrice = ''
  }

  console.log('[ServiceChangeAgreement] eform parameter context:', {
    mode: isSuccessOnlyReview.value ? 'success-review' : 'normal',
    isSuccessOnlyReview: isSuccessOnlyReview.value,
    sourceServiceSelect: getSelectedServiceCodes(sourceData),
    successServiceSelect: store.getSuccessfulServiceSelect?.() || [],
    successProcessResults: store.getSuccessfulProcessResults?.() || [],
    servicechangeAddtionInfo: servicechangeJsonData.addtionInfo,
    reqWireTypeCd: servicechangeJsonData.reqWireTypeCd,
  })

  const params = {
    servicechange: createJsonDataParameter(servicechangeJsonData),
    newchange: useNewChangeTemplate.value ? createJsonDataParameter(newchangeJsonData) : [],
    ios:
      deviceOs.value === 'ios'
        ? createJsonDataParameter(createJsonData(baseJsonData, iosInsuranceJsonDataKeys))
        : [],
    android:
      deviceOs.value === 'android'
        ? createJsonDataParameter(createJsonData(baseJsonData, androidInsuranceJsonDataKeys))
        : [],
  }

  logEformParameters({
    mode: isSuccessOnlyReview.value ? 'success-review' : 'normal',
    params,
    servicechangeJsonData,
    newchangeJsonData,
    baseJsonData,
  })

  return params
})

const getCurrentSignTargetPayload = () => JSON.stringify(eformFormParameters.value || {})

const syncSignTargetPayload = () => {
  formData.value.signTgtSbst = getCurrentSignTargetPayload()
  return formData.value.signTgtSbst
}

// R14 단말보험 가입 시 ASIS와 동일하게 보험 상품코드 하드코딩 매핑으로 iOS/Android 양식을 선택한다.
// recCat1은 TOBE 보험 카테고리(I000001/I000002)이므로 OS 판단에 사용하지 않는다.
const deviceOs = computed(() => {
  const f = eformSourceData.value
  if (getSelectedServiceCodes(f).includes('R14') && f.clauseInsuranceYn === 'Y') {
    return getInsuranceDeviceOs(f)
  }
  return ''
})

// R15 데이터쉐어링 가입만 신규변경 신청서를 함께 생성한다.
const useNewChangeTemplate = computed(() => {
  const f = eformSourceData.value
  return getSelectedServiceCodes(f).includes('R15') && f.shareUseState === 'shareUseState1'
})

const activeDocumentKeys = computed(() => {
  const keys = ['servicechange']
  if (useNewChangeTemplate.value) keys.push('newchange')
  if (deviceOs.value === 'ios') keys.push('ios')
  if (deviceOs.value === 'android') keys.push('android')
  return keys
})

const getSignatureTemplateKey = (documentKey) => {
  if (documentKey === 'ios') return 'insurance_ios'
  if (documentKey === 'android') return 'insurance_android'
  return documentKey
}

const hasRequiredReportSignatures = (result) => {
  const signatureValidation = result?.rawResult?.signatureValidation
  const validationResults = signatureValidation?.results

  if (signatureValidation?.signed !== true || !Array.isArray(validationResults)) {
    return false
  }

  return activeDocumentKeys.value.every((documentKey) => {
    const templateKey = getSignatureTemplateKey(documentKey)
    const validationResult = validationResults.find((item) => item?.templateKey === templateKey)
    const requiredSignatureCount = templateKey.startsWith('insurance_') ? 1 : 2

    return (
      validationResult?.signed === true &&
      Array.isArray(validationResult.signatureValues) &&
      validationResult.signatureValues.length >= requiredSignatureCount
    )
  })
}

const appConfirmRef = ref(null)
const appConfirmKey = ref(0)
// confirmStep: 1=확인 가능, 2=작성완료 처리 중, 3=완료, 4=일부 성공 재확인
const confirmStep = ref(1)
const isAgreementProcessing = ref(false)
const finalSubmitNotice = ref('')
const isReconfirmPending = ref(false)
const scanIdUpdateCompleted = ref(false)
const imageSystemUploadCompleted = ref(false)
const isComplete = ref(
  formData.value.appConfirmCompleted && formData.value.reportSignatureCompleted ? 'true' : '',
)
const isAgreementSaving = ref(false)
const deferredEformResult = ref(null)

const showReconfirmArea = computed(
  () => isReconfirmPending.value || confirmStep.value === 4 || isSuccessOnlyReview.value,
)

const canReconfirmSuccessfulServices = computed(() => {
  if (
    formData.value.completeApplicationCompleted === true &&
    Array.isArray(store.documentId) &&
    store.documentId.length > 0
  ) {
    return true
  }

  return (store.getSuccessfulServiceSelect?.() || []).length > 0
})

const getCurrentDocumentIds = () => {
  const ids = [
    ...(Array.isArray(store.documentId) ? store.documentId : []),
    ...(Array.isArray(store.eformsignFileData)
      ? store.eformsignFileData.map((file) => file?.documentId)
      : []),
  ].filter(Boolean)

  return [...new Set(ids)]
}

const cancelCurrentEformDocuments = async (targetDocumentIds = null) => {
  const documentIds = Array.isArray(targetDocumentIds)
    ? [...new Set(targetDocumentIds.filter(Boolean))]
    : getCurrentDocumentIds()

  console.log('[ServiceChangeAgreement] cancel documentIds', documentIds)

  if (documentIds.length === 0) return true

  try {
    await post('/api/form/common/eform/documents/cancel', { documentIds }, { skipAlert: true })
    return true
  } catch (e) {
    console.warn('[ServiceChangeAgreement] cancel previous eform documents failed', e)
    return false
  }
}

const clearCurrentEformDocuments = () => {
  store.documentId = []
  store.eformsignFileData = []
  formData.value.reportSignatureCompleted = false
  deferredEformResult.value = null
  scanIdUpdateCompleted.value = false
  imageSystemUploadCompleted.value = false
}

const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms))

const saveDeferredFilesOnBackend = async (rawResult, retryCount) => {
  const accessToken = rawResult?.accessToken
  const fileData = Array.isArray(rawResult?.fileData)
    ? rawResult.fileData.slice(0, activeDocumentKeys.value.length)
    : []

  if (!accessToken) {
    throw new Error('eformsign 다운로드 토큰 정보가 없습니다.')
  }

  if (fileData.length === 0) {
    throw new Error('처리할 eformsign 문서 정보가 없습니다.')
  }

  const eformsignFileData = []

  for (let index = 0; index < fileData.length; index += 1) {
    const item = fileData[index]
    const documentId = item.id
    const title = item.title || `신청서_${index + 1}`
    const fileName = `${title}.pdf`

    const res = await post(
      '/api/form/common/eform/documents/files/create',
      {
        accessToken,
        refreshToken: rawResult?.refreshToken,
        documentId,
        retryCount,
        fileName,
        fileCategory: 'servicechange',
        requestKey: store.requestKey || '',
        fileType: 'document',
      },
      { skipAlert: true },
    )

    if (!res || res.code !== '0000') {
      throw new Error(res?.message || '파일 저장 실패')
    }

    eformsignFileData.push(res.data)
  }

  return {
    documentIds: eformsignFileData.map((file) => file.documentId).filter(Boolean),
    eformsignFileData,
  }
}

const retrySaveDeferredFilesOnBackend = async (rawResult) => {
  const maxRetry = 30
  const retryInterval = 1000
  const retryState = { count: 0 }
  let lastError

  for (let attempt = 1; attempt <= maxRetry; attempt += 1) {
    if (attempt > 1) {
      await sleep(retryInterval)
    }

    retryState.count = attempt

    try {
      const result = await saveDeferredFilesOnBackend(rawResult, retryState.count)
      retryState.count = 0
      return result
    } catch (e) {
      lastError = e
    }
  }

  throw lastError
}

const finalizeDeferredEformDocuments = async () => {
  const rawResult = deferredEformResult.value

  if (!rawResult?.backendCreateSkipped) {
    console.warn('[ServiceChangeAgreement] deferred eform result missing', rawResult)
    scanIdUpdateCompleted.value = false
    imageSystemUploadCompleted.value = false
    setComplete(false)
    return false
  }

  const backendResult = await retrySaveDeferredFilesOnBackend(rawResult)
  store.documentId = backendResult.documentIds
  store.eformsignFileData = backendResult.eformsignFileData
  deferredEformResult.value = null

  if (!(await completeDocumentPostProcessing())) return false

  finalSubmitNotice.value = ''
  setComplete(true)
  return true
}

const getScanIdUpdateOptions = () => {
  const signTgtSbst = syncSignTargetPayload()

  if (!isSuccessOnlyReview.value) {
    return { signTgtSbst }
  }

  const successServiceSelect = store.getSuccessfulServiceSelect?.() || []

  return {
    serviceSelect: successServiceSelect,
    successServiceSelect,
    successProcessResults: store.getSuccessfulProcessResults?.() || [],
    signTgtSbst,
  }
}

const setComplete = (value) => {
  const completed = value === true && formData.value.reportSignatureCompleted === true
  isComplete.value = completed ? 'true' : ''
  formData.value.appConfirmCompleted = completed
  emit('complete', completed)
}

const completeDocumentPostProcessing = async () => {
  const options = getScanIdUpdateOptions()

  if (!scanIdUpdateCompleted.value) {
    const scanIdUpdateSuccess = await store.apiUpdateScanId(options)
    if (!scanIdUpdateSuccess) {
      finalSubmitNotice.value =
        '신청서 정보 반영에 실패했습니다. 잠시 후 최종 제출하기를 다시 눌러 주세요.'
      showAlert(finalSubmitNotice.value)
      scanIdUpdateCompleted.value = false
      setComplete(false)
      confirmStep.value = 4
      return false
    }
    scanIdUpdateCompleted.value = true
  }

  if (!imageSystemUploadCompleted.value) {
    const imageSystemUploadSuccess = await store.apiUploadImageSystem(options)
    if (!imageSystemUploadSuccess) {
      finalSubmitNotice.value =
        '이미징시스템 전송에 실패했습니다. 잠시 후 최종 제출하기를 다시 눌러 주세요.'
      showAlert(finalSubmitNotice.value)
      imageSystemUploadCompleted.value = false
      setComplete(false)
      confirmStep.value = 4
      return false
    }
    imageSystemUploadCompleted.value = true
  }

  return true
}

const emitFinalComplete = () => {
  emit('final-complete', getCompleteData())
}

const isCustomerAuthCompleted = () => {
  if (!store.authFlags?.deviceChgTel) return false

  if (['NM', 'FM'].includes(formData.value.cstmrTypeCd) && !store.authFlags?.repPhone) {
    return false
  }

  return true
}

const onBeforeConfirmApp = (event) => {
  if (isSuccessOnlyReview.value) return

  if (!isCustomerAuthCompleted()) {
    event?.preventDefault?.()
    showAlert('고객 인증완료 후 신청서 확인을 진행해 주세요.')
    return
  }

  const isProductReady = store.isReadyToComplete?.() === true

  if (formData.value.serviceSelectCompleteYn !== 'Y' || !isProductReady) {
    console.warn('[서비스변경][신청서확인] 상품 확인완료 조건 미충족', {
      serviceSelect: formData.value.serviceSelect,
      serviceSelectCompleteYn: formData.value.serviceSelectCompleteYn,
      isProductReady,
      planChangeConfirmCompleted: formData.value.planChangeConfirmCompleted,
      numberChgConfirmCompleted: formData.value.numberChgConfirmCompleted,
      unpauseConfirmCompleted: formData.value.unpauseConfirmCompleted,
      additionConfirmCompleted: formData.value.additionConfirmCompleted,
      wirelessBlockConfirmCompleted: formData.value.wirelessBlockConfirmCompleted,
      insuranceConfirmCompleted: formData.value.insuranceConfirmCompleted,
      reqUsimConfirmCompleted: formData.value.reqUsimConfirmCompleted,
      dataSharingConfirmCompleted: formData.value.dataSharingConfirmCompleted,
      combineSoloConfirmCompleted: formData.value.combineSoloConfirmCompleted,
      blockService: formData.value.blockService,
    })
    event?.preventDefault?.()
    showAlert('서비스변경 상품 확인완료 후 신청서 확인을 진행해 주세요.')
  }
}

onMounted(async () => {
  legalAgentRelationCodes.value = (await getCommonCodeList('AGR')) || []
})

watch(
  () => isComplete.value,
  (newVal) => {
    const completed = newVal === true || newVal === 'true'
    formData.value.appConfirmCompleted = completed
    emit('complete', completed)
  },
  { immediate: true },
)

const onConfirmApp = async (result) => {
  if (!hasRequiredReportSignatures(result)) {
    formData.value.reportSignatureCompleted = false
    setComplete(false)
    showAlert('신청서 확인 버튼을 눌러 판매자 및 가입자 서명을 완료해 주세요.')
    return false
  }

  formData.value.reportSignatureCompleted = true

  const sourceIds = [
    ...(Array.isArray(result?.documentIds) ? result.documentIds : []),
    ...(Array.isArray(result?.eformsignFileData)
      ? result.eformsignFileData.map((r) => r.documentId)
      : []),
    ...(Array.isArray(result?.uploadResults) ? result.uploadResults.map((r) => r.documentId) : []),
    ...(result?.documentId ? [result.documentId] : []),
  ].filter(Boolean)

  const ids = [...new Set(sourceIds)].slice(0, activeDocumentKeys.value.length)
  const sourceFiles = Array.isArray(result?.eformsignFileData) ? result.eformsignFileData : []
  const orderedFiles = ids
    .map((id) => sourceFiles.find((file) => file?.documentId === id))
    .filter(Boolean)
  const backendCreateSkipped = result?.rawResult?.backendCreateSkipped === true

  if (ids.length > 0) {
    store.documentId = ids
    store.eformsignFileData = orderedFiles

    // 최초 신청서 확인은 eformsign 문서 전송/서명검증까지만 진행하고 create는 지연한다.
    // 이후 작성완료 API 결과가 일부 성공이면 이 documentId를 취소하고 성공건 재확인을 진행한다.
    if (backendCreateSkipped && !isSuccessOnlyReview.value) {
      deferredEformResult.value = result.rawResult
      scanIdUpdateCompleted.value = false
      imageSystemUploadCompleted.value = false
      finalSubmitNotice.value = ''
      setComplete(true)
      confirmStep.value = 2
      return true
    }

    // SCAN_ID 업데이트와 이미징 시스템 업로드를 각각 완료한다.
    if (!(await completeDocumentPostProcessing())) return false
  } else {
    console.warn('[ServiceChangeAgreement] missing documentId in confirm result', result)
    scanIdUpdateCompleted.value = false
    imageSystemUploadCompleted.value = false
    setComplete(false)
    return false
  }
  setComplete(true)
  finalSubmitNotice.value = ''

  if (isSuccessOnlyReview.value) {
    // 재확인 모달에서 성공건 서류 생성과 scanId 반영까지 끝나면 완료 화면으로 이동한다.
    confirmStep.value = 3
    isSuccessOnlyReview.value = false
    emitFinalComplete()
  }

  return true
}

const onEditApp = () => {
  if (isSuccessOnlyReview.value) {
    isSuccessOnlyReview.value = false
    clearCurrentEformDocuments()
    setComplete(false)
    confirmStep.value = 4
    return
  }

  store.resetAuthForEdit()
  isSuccessOnlyReview.value = false
  confirmStep.value = 1
  finalSubmitNotice.value = ''
  clearCurrentEformDocuments()
  setComplete(false)
}

const onCloseApp = () => {
  if (
    isSuccessOnlyReview.value &&
    !isAgreementProcessing.value &&
    !formData.value.appConfirmCompleted
  ) {
    confirmStep.value = 4
  }
}

// 확인 완료 후 호출 — 작성완료 API는 화면의 작성완료 버튼(save)에서 처리
const onExtractComplete = async () => {
  if (isAgreementProcessing.value) return

  formData.value.reportSignatureCompleted = false

  if (isSuccessOnlyReview.value) {
    confirmStep.value = 4
    finalSubmitNotice.value = ''
    setComplete(false)
    return
  }

  confirmStep.value = 2
  finalSubmitNotice.value = ''
  setComplete(false)
}

// 일부 성공 후 최종 제출 — complete 재호출 없이 문서 후처리만 실행
const onFinalSubmit = async () => {
  if (isAgreementProcessing.value) return

  // 재확인 팝업의 확인 메시지가 표시되는 동안에도 일부 성공 재확인 영역을 유지한다.
  isReconfirmPending.value = true

  if (
    formData.value.completeApplicationCompleted === true &&
    Array.isArray(store.documentId) &&
    store.documentId.length > 0
  ) {
    isAgreementProcessing.value = true
    try {
      confirmStep.value = 3
      if (!(await completeDocumentPostProcessing())) return

      setComplete(true)
      isSuccessOnlyReview.value = false
      finalSubmitNotice.value = ''
      emitFinalComplete()
    } catch (e) {
      confirmStep.value = 4
      showAlert(e?.message || '제출 중 오류가 발생했습니다.')
    } finally {
      isAgreementProcessing.value = false
    }
    return
  }

  const successCodes = store.getSuccessfulServiceSelect?.() || []

  if (successCodes.length === 0) {
    showAlert('재확인할 처리 성공건이 없습니다. 신청서 확인부터 다시 진행해 주세요.')
    confirmStep.value = 1
    isSuccessOnlyReview.value = false
    return
  }

  isSuccessOnlyReview.value = true
  clearCurrentEformDocuments()
  setComplete(false)
  confirmStep.value = 1
  appConfirmKey.value += 1
  await nextTick()
  if (!appConfirmRef.value?.openDeferredReview?.()) {
    appConfirmRef.value?.open?.()
  }
}

const uploadDeferredAndFinalize = async () => {
  const wasSuccessOnlyReview = isSuccessOnlyReview.value
  const completed = wasSuccessOnlyReview
    ? await onConfirmApp(await appConfirmRef.value?.uploadDeferred())
    : await finalizeDeferredEformDocuments()
  if (!completed) {
    confirmStep.value = 4
  } else {
    confirmStep.value = wasSuccessOnlyReview ? 3 : confirmStep.value
    isSuccessOnlyReview.value = false
  }
  return completed
}

const save = async () => {
  if (isAgreementSaving.value) return false

  if (!validateWithAlert()) {
    console.warn('[ServiceChangeAgreement] complete blocked', {
      reason: 'app confirm or signature incomplete',
    })
    return false
  }

  if (formData.value.completeApplicationCompleted === true) {
    if (!Array.isArray(store.documentId) || store.documentId.length === 0) {
      showAlert('처리 성공건의 신청서를 재확인한 후 최종 제출해 주세요.')
      confirmStep.value = 4
      setComplete(false)
      return false
    }

    isAgreementSaving.value = true
    isAgreementProcessing.value = true
    try {
      if (!(await completeDocumentPostProcessing())) return false

      finalSubmitNotice.value = ''
      confirmStep.value = 3
      setComplete(true)
      return true
    } finally {
      isAgreementSaving.value = false
      isAgreementProcessing.value = false
    }
  }

  isAgreementSaving.value = true
  isAgreementProcessing.value = true
  isSuccessOnlyReview.value = false
  finalSubmitNotice.value = ''
  scanIdUpdateCompleted.value = false
  imageSystemUploadCompleted.value = false
  confirmStep.value = 2

  try {
    syncSignTargetPayload()
    const beforeCompleteDocumentIds = getCurrentDocumentIds()
    console.log('[STEP1] beforeCompleteDocumentIds', beforeCompleteDocumentIds)
    const result = await store.apiCompleteAdditionApplication()

    if (!result) {
      confirmStep.value = 2
      return false
    }

    const noticeMessage = store.getCompleteNoticeMessage()
    if (noticeMessage) {
      const hasSuccessTargets = (store.getSuccessfulServiceSelect?.() || []).length > 0

      // 성공/실패 결과가 포함된 최초 신청서 documentId는 더 이상 사용하지 않으므로 취소/초기화한다.
      // 재확인 버튼을 누르면 성공 서비스만 남긴 신청서를 새로 생성한다.
      console.log('[STEP2] cancel target', beforeCompleteDocumentIds)
      const canceled = await cancelCurrentEformDocuments(beforeCompleteDocumentIds)

      if (!canceled) {
        showAlert('기존 신청서 취소에 실패했습니다. 잠시 후 다시 시도해 주세요.')
        confirmStep.value = 2
        return false
      }

      clearCurrentEformDocuments()

      finalSubmitNotice.value = hasSuccessTargets
        ? noticeMessage
        : `${noticeMessage}<br>처리 성공건 목록이 없어 신청서 재확인을 진행할 수 없습니다.`
      isReconfirmPending.value = true
      confirmStep.value = 4
      setComplete(false)
      if (!hasSuccessTargets) {
        showAlert(
          '처리 성공건 목록이 없어 신청서 재확인을 진행할 수 없습니다. 처리 결과를 확인해 주세요.',
        )
      }
      return false
    }

    confirmStep.value = 3
    return await uploadDeferredAndFinalize()
  } catch (e) {
    confirmStep.value = 2
    showAlert(e?.message || '처리 중 오류가 발생했습니다.')
    return false
  } finally {
    isAgreementSaving.value = false
    isAgreementProcessing.value = false
  }
}

const getCompleteErrorMessage = () =>
  store.getCompleteNoticeMessage() || store.getCompleteErrorMessage()
const getCompleteNoticeMessage = () => store.getCompleteNoticeMessage()

const validateWithAlert = () => {
  if (!store.validateCustomerWithAlert()) return false

  if (
    formData.value.appConfirmCompleted !== true ||
    formData.value.reportSignatureCompleted !== true ||
    appConfirmRef.value?.validate?.() !== true
  ) {
    showAlert('신청서 확인 버튼을 눌러 판매자 및 가입자 서명을 완료해 주세요.')
    return false
  }

  return true
}

const joinPhone = (...parts) => (parts.every(Boolean) ? parts.join('-') : '')

// 공통 완료 화면(MsfRequestComplete)이 신청서 열람/발송에 사용하는 데이터 형식
const getCompleteData = () => {
  const f = formData.value
  return {
    // 신청서 열람/발송 API 호출에 사용할 접수번호
    requestKey: store.requestKey || '',

    // 신규/변경, 서비스변경 메뉴일 경우, 가입 고객 이름
    name: f.cstmrNm || '',

    // 고객이 미성년자가 아니고 신규/변경, 서비스변경 메뉴일 경우, 가입 고객 이름과 가입 휴대폰번호
    // 신규/변경, 서비스변경 메뉴일 경우, 고객 이름과 가입자 연락처의 휴대폰번호
    mobiles: [
      {
        name: f.cstmrNm || '',
        mobile: joinPhone(f.deviceChgTel1, f.deviceChgTel2, f.deviceChgTel3),
      },
      {
        name: f.cstmrNm || '',
        mobile: joinPhone(f.mobileNo1, f.mobileNo2, f.mobileNo3),
      },
    ],
  }
}

defineExpose({
  save,
  validateWithAlert,
  getCompleteErrorMessage,
  getCompleteNoticeMessage,
  getCompleteData,
})
</script>

<style scoped>
.partial-fail-box {
  margin-top: 16px;
}

.partial-fail-msg {
  font-size: 13px;
  line-height: 1.6;
  color: #e53935;
  margin-bottom: 12px;
}

.partial-fail-msg :deep(.partial-result-title) {
  font-weight: 600;
}

.partial-fail-msg :deep(.partial-result-summary) {
  color: #666;
}

.partial-fail-msg :deep(.partial-result-list) {
  margin-top: 8px;
}

.partial-fail-msg :deep(.partial-result-item) {
  margin-top: 4px;
}

.partial-fail-msg :deep(.partial-result-item.is-success) {
  color: #2e7d32;
}

.partial-fail-msg :deep(.partial-result-item.is-fail) {
  color: #e53935;
}
</style>
