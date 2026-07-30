import { defineStore } from 'pinia'
import { ref, watch, computed } from 'vue'
import { post } from '@/libs/api/msf.api'
import { useMsfUserStore } from '@/stores/msf_user'
import { useMsfStepStore } from '@/stores/msf_step'
import { showAlert, showConfirm, showConfirmAsync } from '@/libs/utils/comp.utils'
import { extractYYYYMMDDRrn } from '@/libs/utils/string.utils'
import { getCommonCodeListAll } from '@/libs/utils/comn.utils.js'

// 유틸: 객체 깊은 복사
const cloneDeep = (obj) => JSON.parse(JSON.stringify(obj))

export const useMsfFormNewChgStore = defineStore('msf_form_new_chg', () => {
  const userStore = useMsfUserStore()
  const stepStore = useMsfStepStore()
  const applicationKey = ref('')
  const formDefaultMap = ref({})
  const draftApplicationKey = ref('') // 임시저장용 별도 보관 키 (필요시)
  const documentId = ref('')
  const originalfileName = ref('')
  const resNo = ref('')
  const rawEformsignFileData = ref([])
  const rawRecordFileData = ref(null)
  const parentScanId = ref('')
  const isDraftLoading = ref(false)
  const isDraftLoaded = ref(false)

  // 희망번호 조회 횟수 제한 (세션용, 서버 저장 안 함)
  const wishNoSearchCount = ref(0)
  const incrementWishNoSearchCount = () => {
    wishNoSearchCount.value++
  }

  // ==========================================
  // 1. 기본값 (Default Templates) 정의
  // ==========================================
  const DEFAULT_CUSTOMER = {
    isSaved: false, // 고객스텝 저장 완료 여부 (화면 락)
    isVerified: false, // 인증 완료 여부
    resNo: '', // 사전체크 예약번호
    canBulkCorporateOpenYn: 'N', // 법인대량 개통가능여부 (Y/N, default N)
    bulkActivationCnt: 1, // 개통회선수 (기본 1)
    productType: 'MM',
    joinType: 'MNP3',
    cstmrTypeCd: 'NA',
    identityCertTypeCd: 'K',
    identityTypeCd: '',
    identityTypeNm: '', // 스캔된 신분증 명칭 추가
    identityIssuRegion: '',
    identityIssuDate: '', // 신분증 발급일자 추가
    cstmrPrivateBizNoIssuDate: '', // 개인사업자사업자등록번호발급일자 추가
    cstmrJuridicalBizNoIssuDate: '', // 법인사업자사업자등록번호발급일자 추가
    selfIssuNo: '', // 자가발급번호 추가
    driveLicnsNo: '', // 운전면허번호 추가
    cstmrVisitTypeCd: 'VMY',
    msfRequestDocList: [], // 구비서류 촬영 목록
    cstmrNm: '',
    cstmrNativeRrn1: '',
    cstmrNativeRrn2: '',
    cstmrNativeBirth: '', // 가입자 생년월일 추가
    cstmrNativeGenderCd: '', // 가입자 성별코드 추가
    cstmrPrivateCname: '', // 개인사업자 상호 추가
    cstmrPrivateBizNo: '', // 개인사업자 번호 추가
    cstmrForeignerRrn1: '',
    cstmrForeignerRrn2: '',
    cstmrForeignerBirth: '',
    cstmrForeignerGenderCd: '',
    cstmrForeignerPn: '',
    cstmrForeignerCountryCd: '',
    cstmrForeignerNation: '',
    cstmrForeignerVisaNo: '',
    cstmrForeignerVdateStartDate: '',
    cstmrForeignerVdateEndDate: '',
    cstmrJuridicalRrn1: '',
    cstmrJuridicalRrn2: '',
    cstmrJuridicalBizNo1: '',
    cstmrJuridicalBizNo2: '',
    cstmrJuridicalBizNo3: '',
    cstmrJuridicalRepNm: '',
    cstmrJuridicalCname: '', // 법인 상호 추가
    cstmrJuridicalUserNm: '', // 법인 실사용자 추가
    cstmrJuridicalBirth: '', // 법인 실사용자 생일 추가
    upjnCd: '',
    bcuSbst: '',
    deviceChgTel1: '010',
    deviceChgTel2: '',
    deviceChgTel3: '',
    openNo: '', // 개통 휴대폰번호 (가입유형에 따라 자동 동기화)
    repName: '',
    repRegistrationNo1: '',
    repRegistrationNo2: '',
    repForeignerNo1: '',
    repForeignerNo2: '',
    repRelation: '',
    repPhone1: '',
    repPhone2: '',
    repPhone3: '',
    repPhoneAuth: '',
    repAgree: false,
    realUserName: '',
    realUserBirthDate: '',
    userGender: 'M',
    // 법정대리인 상세 (DTO 기반)
    minorAgentNm: '',
    minorAgentRrn: '',
    minorAgentBirth: '',
    minorAgentGenderCd: '',
    minorAgentRelTypeCd: '',
    minorAgentTelFnNo: '',
    minorAgentTelMnNo: '',
    minorAgentTelRnNo: '',
    minorAgentAgrmYn: 'N',
    minorAgentSelfInqryAgrmYn: 'N',
    minorAgentSelfCertTypeCd: '01',
    minorAgentCiInfo: '',
    // 대리인 정보 (DTO 기반)
    jrdclAgentNm: '',
    jrdclAgentRrn: '',
    jrdclAgentRelTypeCd: '',
    jrdclAgentTelFnNo: '',
    jrdclAgentTelMnNo: '',
    jrdclAgentTelRnNo: '',
    agentBirthDate: '',
    agentGender: '',
    mobileNo1: '010',
    mobileNo2: '',
    mobileNo3: '',
    telNo1: '',
    telNo2: '',
    telNo3: '',
    emailAddr1: '',
    emailAddr2: '',
    zipNo: '',
    address: '',
    detailAddress: '',
    cstmrAdrBjd: '', // 법정동 코드 추가
    country: '',
    stayPeriod: '',
    visaType: '',
    termsAgreed: false,
    formType: 'NEWCHANGE', // 폼 유형 추가
    // 관리 정보 (DTO 기반)
    managerCd: 'M0001',
    managerNm: '',
    agentCd: '',
    agentNm: '',
    agent: '', // 대리점 선택 UI용 필드 추가
    shopCd: '',
    shopNm: '',
    realShopNm: '',
    cpntId: '',
    cpntNm: '',
    cntpntShopCd: '',
    cntpntShopNm: '',
    // 단말기 상세 정보 (K-ID 및 내부 ID 구분)
    modelId: '', // 대표단말기ID (K...)
    handsetProdId: '', // 내부단말기ID (3308 등)
    // 신분증 스캔 관련 (DTO 기반)
    knoteIdentityScanCstmrNm: '',
    knoteIdentityEssNo: '',
    knoteIdentityTypeCd: '',
    knoteIdentityScanDt: '',
    knoteScanId: '',
    // 안면인증 관련 (DTO 기반)
    fathTrgYn: 'N',
    fathTrgIdentityCertTypeCd: '',
    fathTransacId: '',
    authInfo: '',
    fathCmpltNtfyDate: '',
    fathTelNo: '',
    fathMobileFnNo: '',
    fathMobileMnNo: '',
    fathMobileRnNo: '',
    // 수령 정보 (DTO 기반)
    cstmrReceiveTelFnNo: '',
    cstmrReceiveTelNmNo: '',
    cstmrReceiveTelRnNo: '',
    // 상태 정보 추가 (DTO 기반)
    proSttusCd: '99',
    sbscProCd: '99',
    onOffTypeCd: '3',
    appFormYn: 'N',
    appFormXmlYn: 'N',
    faxYn: 'N',
    // 약관 동의 항목들
    clausePriCollectYn: 'N', // 약관개인정보수집동의여부 (필수)
    clausePriOfferYn: 'N', // 약관개인정보제공동의여부 (필수)
    clauseEssCollectYn: 'N', // 약관고유식별정보수집이용제공동의여부 (필수)
    clausePriTrustYn: 'N', // 약관개인정보위탁동의여부 (필수)
    clausePriAdYn: 'N', // 약관개인정보광고전송동의여부 (선택)
    clauseConfidenceYn: 'N', // 약관신용정보이용동의여부 (필수)
    clauseFathYn: 'N', // 안면인증동의여부 (필수)
    nwBlckAgrmYn: 'N', // 네트워크차단동의여부 (필수)
    appBlckAgrmYn: 'N', // 청소년유해매체차단동의여부 (필수)
    blckAppDivCd: '', // 청소년유해매체차단APP구분코드 (필수)
    soTrnsAgrmYn: 'N', // 사업이관동의여부
    moveRefundAgreeYn: 'N', // 번호이동정보미환급액요금상계동의여부 (필수)
    clauseJehuYn: 'N', // 제휴서비스동의여부 (선택)
    clauseMpps35Yn: 'N', // 추가
    clauseFinanceYn: 'N', // 추가

    // API 전달용 특별 YN 필드 추가
    clauseMoveCode: 'N',
    clauseSensiCoverageYn: 'N',
    clause5gCoverageYn: 'N',
    clausePartnerOfferYn: 'N',
    personalLocationAgreeYn: 'N',
    clauseInfo01: 'N',
    clausePriAllYn: 'N',

    // CLAUSE_FORM_01 상세 코드 매핑용 필드 (UI용 불리언)
    CLAUSE_MOVE_01: false,
    CLAUSE_REQUIRED_02: false,
    CLAUSE_REQUIRED_01: false,
    CLAUSE_REQUIRED_03: false,
    CLAUSE_FATH_01: false,
    CLAUSE_FATH_02: false,
    CLAUSE_REQUIRED_06: false,
    CLAUSE_REQUIRED_07: false,
    CLAUSE_REQUIRED_5G: false,
    CLAUSE_PARTNER_02: false,
    CLAUSE_SELECT_03: false,
    CLAUSE_SELECT_01: false,
    CLAUSE_SELECT_08: false,
    CLAUSE_SELECT_04: false,
    CLAUSE_SELECT_06: false,
    CLAUSE_SELECT_07: false,
    CLAUSE_SELECT_TIT_02: false,
    CLAUSE_SELECT_10: false,
    CLAUSE_INFO_01: false,
    socCode: '',
    prdtSctnCd: '',
    dataType: '',
    // 동적으로 업데이트되는 추가 약관 필드들 기본값 선언
    clauseRentalModelCpYn: 'N',
    clauseRentalModelCpPrYn: 'N',
    clauseRentalServiceYn: 'N',
    personalInfoCollectAgreeYn: 'N',
    othersTrnsAgreeYn: 'N',
    clauseSensiCollectYn: 'N',
    clauseSensiOfferYn: 'N',
    othersTrnsKtAgreeYn: 'N',
    othersAdReceiveAgreeYn: 'N',
    ktCounselAgreeYn: 'N',
    combineSoloTypeYn: 'N',
    combineSoloYn: 'N',
  }

  const DEFAULT_PRODUCT = {
    hasSim: '',
    simTypeCd: '',
    usimKindsCd: '',
    reqUsimSn: '',
    reqUsimNm: '', // DTO 기반 추가
    simPurchaseMethod: 'B',
    prodNm: '',
    eid: '',
    imei1: '',
    imei2: '',
    imei: '',
    serialNumber: '',
    esimPhoneId: '', // DTO 기반 추가
    uploadPhoneSrlNo: null, // DTO 기반 추가
    moveCompanyCd: '',
    moveMobileNo: '',
    moveMobileNo1: '010',
    moveMobileNo2: '',
    moveMobileNo3: '',
    moveAuthTypeCd: '',
    moveAuthNo: '',
    transferBankNum: '',
    transferCardNum: '',
    moveThismonthPayTypeCd: true,
    moveAllotmentSttusCd: ['AD'],
    moveRefundAgreeYn: ['Y'],
    // 번호이동 안내 관련 추가
    reqGuideYn: 'N',
    reqGuideFnNo: '',
    reqGuideRnNo: '',
    reqGuideMnNo: '',
    osstPayDate: '',
    osstPayTypeCd: '',
    movePenalty: 0,
    reqWantFnNo: '010',
    reqWantMnNo: '****',
    reqWantRnNo: '',
    wishNo: '',
    additionList: [], // 부가서비스 목록 { additionId, additionNm, rantal }
    addtionId: [],
    reqAdditionPrice: 0, // DTO 기반 추가
    phonePaymentYn: 'N', // DTO 기반 추가
    clauseInsuranceYn: 'N',
    insrCd: '', // DTO 기반 추가
    insrProdCd: '',
    recCat1: '',
    recCat2: '',
    // 제휴 정보 추가
    jehuPartnerTypeCd: '',
    jehuPartnerTypeNm: '',
    jehuProdTypeCd: '',
    // 판매 정책 정보 (DTO 기반)
    modelId: '',
    modelMonthly: '',
    modelInstamt: 0,
    modelSalePolicyCd: '',
    modelPriceVat: 0,
    modelDiscount1: 0,
    modelSprt: 0,
    modelPrice: 0,
    modelDiscount3: 0,
    realMdlInstamt: 0,
    hndsetSalePrice: 0,
    sprtTypeCd: '',
    dcAmt: 0,
    maxApdSprt: 0,
    addDcAmt: 0,
    prmtAmt: 0,
    recycleYn: 'N',
    usimPriceTypeCd: '',
    usimPayMthdCd: '',
    usimPrice: 0,
    sesplsYn: 'N',
    joinPriceTypeCd: '',
    joinPayMthdCd: '',
    joinPrice: 0,
    socCode: '',
    socNm: '',
    socBaseChrgAmt: 0,
    // 접수 환경/기타 정보
    soCd: 'M',
    openReqDt: '',
    reqInDay: '',
    etcSpecialSbst: '',
    cstmrBillSendTypeCd: 'CB',
    reqPayTypeCd: 'D', // 납부방법 자동이체('D') 기본값 설정
    autoPayerType: '',
    reqBankCd: '',
    reqAccountNo: '',
    reqAccountNm: '',
    reqAccountRrn: '',
    reqAccountRelTypeCd: '',
    isAutoAgree: true,
    reqWireTypeCd: '', // DTO 기반 추가
    cardPayerType: '',
    reqCardCompanyCd: '',
    reqCardNo: '',
    reqCardMm: '',
    reqCardYy: '',
    reqCardNm: '',
    reqCardRrn: '',
    cardRelation: '',
    othersPaymentTelFnNo: '',
    othersPaymentTelMnNo: '',
    othersPaymentTelRnNo: '',
    othersPaymentNm: '',
    othersPaymentRrn: '',
    othersPaymentRelTypeCd: '',
    othersPaymentReqNm: '',
    othersPaymentYn: 'N',
    othersPaymentAgrYn: 'N',
    prntsBillNo: '',
    combId: '',
    combAgree: false,
    memo: '',
    // 예상납부금액 정보 추가
    estimatedAmtInfo: {
      hndsetAmt: 0,
      subsdAmt: 0,
      agncySubsdAmt: 0,
      instAmt: 0,
      instCmsn: 0,
      baseAmt: 0,
      dcAmt: 0,
      addDcAmt: 0,
      prmtAmt: 0,
      joinFee: 0,
      usimFee: 0,
    },
  }

  const DEFAULT_AGREEMENT = {
    agreeCheck1: false,
    agreeCheck2: false,
    agreeCheck3: false,
    recYn: 'N',
    recFilePathNm: '',
    recFileNm: '',
    fileTypeCd: '', // DTO 기반 추가
    filePathNm: '', // DTO 기반 추가
    scanId: '', // 서명 및 확인 eform documentId 매핑
    msfRequestRecList: [], // 녹취파일 정보 리스트 매핑
  }

  // ==========================================
  // 2. 상태 관리 (초기값 / 임시저장값 / 현재값)
  // ==========================================

  // 1) 초기값 (진입 시 최초 세팅값)
  const initialCustomer = ref(cloneDeep(DEFAULT_CUSTOMER))
  const initialProduct = ref(cloneDeep(DEFAULT_PRODUCT))
  const initialAgreement = ref(cloneDeep(DEFAULT_AGREEMENT))

  // 2) 임시저장값 (서버에서 불러오거나 저장에 성공한 마지막 상태)
  const draftCustomer = ref(cloneDeep(DEFAULT_CUSTOMER))
  const draftProduct = ref(cloneDeep(DEFAULT_PRODUCT))
  const draftAgreement = ref(cloneDeep(DEFAULT_AGREEMENT))

  // 3) 현재값 (실제 폼 컴포넌트들과 v-model로 바인딩되는 상태)
  const customer = ref(cloneDeep(DEFAULT_CUSTOMER))
  const product = ref(cloneDeep(DEFAULT_PRODUCT))
  const agreement = ref(cloneDeep(DEFAULT_AGREEMENT))

  // 각 인증 버튼들의 최종 완료 여부를 관리하는 플래그 (UI 제어용)
  const preChecked = ref(false)

  const authFlags = ref({
    identityCertTypeCd: false,
    deviceChgTel: false,
    repPhone: false,
    reqUsimSn: false,
    imei: false,
    esimImei: false, // eSIM 이미지 등록 완료 여부
    moveAuthTypeCd: false,
    reserveNo: false,
    autoAcct: false,
    reqCardNo: false,
    combId: false,
    requiredDocs: false,
  })

  // 개통 휴대폰번호 실시간 동기화
  watch(
    () => [
      customer.value.joinType,
      product.value.wishNo,
      product.value.moveMobileNo1,
      product.value.moveMobileNo2,
      product.value.moveMobileNo3,
      customer.value.deviceChgTel1,
      customer.value.deviceChgTel2,
      customer.value.deviceChgTel3,
    ],
    () => {
      // 임시저장 로드 중일 때는 자동 동기화 무시 (데이터 무결성 보호)
      if (isDraftLoading.value) return

      const c = customer.value
      const p = product.value
      if (c.joinType === 'HDN3' || c.joinType === 'HCN3') {
        c.openNo = [c.deviceChgTel1, c.deviceChgTel2, c.deviceChgTel3].filter(Boolean).join('-')
      } else if (c.joinType === 'NAC3') {
        c.openNo = p.wishNo || ''
      } else if (c.joinType === 'MNP3') {
        c.openNo = [p.moveMobileNo1, p.moveMobileNo2, p.moveMobileNo3].filter(Boolean).join('-')
      } else {
        c.openNo = ''
      }
    },
    { immediate: true, deep: true },
  )

  // 외부 컴포넌트 호환성을 위한 computed 노출
  const openNo = computed(() => customer.value.openNo || '')

  // 고객 유형 변경 시 정보 초기화
  watch(
    () => customer.value.cstmrTypeCd,
    () => {
      // 임시저장 로드 중일 때는 초기화 무시 (데이터 무결성 보호)
      if (isDraftLoading.value) return

      // 저장된 경우가 아니면 고객 유형 변경 시 데이터 및 인증 상태 초기화
      if (!customer.value.isSaved) {
        // 공통 가입자 정보 초기화
        customer.value.cstmrNm = ''
        customer.value.cstmrNativeRrn1 = ''
        customer.value.cstmrNativeRrn2 = ''
        customer.value.cstmrForeignerRrn1 = ''
        customer.value.cstmrForeignerRrn2 = ''
        customer.value.cstmrJuridicalRrn1 = ''
        customer.value.cstmrJuridicalRrn2 = ''
        customer.value.cstmrJuridicalBizNo1 = ''
        customer.value.cstmrJuridicalBizNo2 = ''
        customer.value.cstmrJuridicalBizNo3 = ''
        customer.value.cstmrJuridicalRepNm = ''
        customer.value.upjnCd = ''
        customer.value.bcuSbst = ''

        // 법정대리인/실사용자 정보 초기화
        customer.value.repName = ''
        customer.value.repRegistrationNo1 = ''
        customer.value.repRegistrationNo2 = ''
        customer.value.repForeignerNo1 = ''
        customer.value.repForeignerNo2 = ''
        customer.value.minorAgentNm = ''
        customer.value.minorAgentRelTypeCd = ''
        customer.value.minorAgentTelFnNo = ''
        customer.value.minorAgentTelMnNo = ''
        customer.value.minorAgentTelRnNo = ''
        customer.value.realUserName = ''
        customer.value.realUserBirthDate = ''
        customer.value.userGender = 'M'

        // 인증 상태 및 플래그 초기화
        customer.value.isVerified = false
        Object.keys(authFlags.value).forEach((key) => {
          authFlags.value[key] = false
        })

        // 약관 관련 데이터 초기화
        const termsKeys = [
          'clausePriCollectYn',
          'clausePriOfferYn',
          'clauseEssCollectYn',
          'clausePriTrustYn',
          'clausePriAdYn',
          'clauseConfidenceConfidenceYn',
          'clauseFathFathYn',
          'nwBlckAgrmYn',
          'appBlckAgrmYn',
          'soTrnsAgrmYn',
          'moveRefundAgreeYn',
          'clauseJehuJehuYn',
          'clauseMoveCode',
          'clauseSensiCoverageYn',
          'clauseSensiOfferYn',
          'clause5gCoverage',
          'clausePartnerOfferFlag',
          'personalLocationAgreeYn',
          'clauseInfo01',
        ]
        termsKeys.forEach((key) => {
          if (customer.value[key] !== undefined) customer.value[key] = 'N'
        })

        // UI용 불리언 필드 초기화
        const uiTermsKeys = [
          'CLAUSE_MOVE_01',
          'CLAUSE_REQUIRED_02',
          'CLAUSE_REQUIRED_01',
          'CLAUSE_REQUIRED_03',
          'CLAUSE_FATH_01',
          'CLAUSE_FATH_02',
          'CLAUSE_REQUIRED_06',
          'CLAUSE_REQUIRED_07',
          'CLAUSE_REQUIRED_5G',
          'CLAUSE_PARTNER_02',
          'CLAUSE_SELECT_03',
          'CLAUSE_SELECT_01',
          'CLAUSE_SELECT_08',
          'CLAUSE_SELECT_04',
          'CLAUSE_SELECT_06',
          'CLAUSE_SELECT_07',
          'CLAUSE_SELECT_10',
          'CLAUSE_INFO_01',
        ]
        uiTermsKeys.forEach((key) => {
          if (customer.value[key] !== undefined) customer.value[key] = false
        })
      }
    },
  )

  // CLAUSE_SELECT_01 약관 체크 상태를 clausePriAllYn 에 동기화
  watch(
    () => customer.value.CLAUSE_SELECT_01,
    (newVal) => {
      customer.value.clausePriAllYn = newVal === true || newVal === 'Y' ? 'Y' : 'N'
    },
  )

  // 가입유형이 기기변경(HDN3/HCN3)인 경우 요금납부 및 명세서수령방법 관련 데이터 초기화
  watch(
    () => customer.value.joinType,
    (newVal) => {
      if (newVal === 'HDN3' || newVal === 'HCN3') {
        product.value.cstmrBillSendTypeCd = ''
        product.value.reqPayTypeCd = ''
        product.value.othersPaymentYn = 'N'
      }
    },
  )

  // 신분증 인증 방식 변경 시 정보 초기화 (저장되지 않은 경우만)
  watch(
    () => customer.value.identityCertTypeCd,
    () => {
      // 임시저장 로드 중일 때는 초기화 무시 (데이터 무결성 보호)
      if (isDraftLoading.value) return

      // 저장된 경우가 아니면 인증 방식 변경 시 데이터 초기화
      if (!customer.value.isSaved) {
        const isMinor = ['NM', 'FM'].includes(customer.value.cstmrTypeCd)

        if (isMinor) {
          // 미성년자인 경우: 법정대리인 정보 초기화, 가입자 정보는 유지
          customer.value.repName = ''
          customer.value.repRegistrationNo1 = ''
          customer.value.repRegistrationNo2 = ''
          customer.value.repForeignerNo1 = ''
          customer.value.repForeignerNo2 = ''
          customer.value.minorAgentNm = ''
          customer.value.minorAgentRelTypeCd = ''
          customer.value.minorAgentTelFnNo = ''
          customer.value.minorAgentTelMnNo = ''
          customer.value.minorAgentTelRnNo = ''
          customer.value.repAgree = false
          authFlags.value.repPhone = false
        } else {
          // 일반 고객인 경우: 가입자 정보 초기화
          customer.value.cstmrNm = ''
          customer.value.cstmrNativeRrn1 = ''
          customer.value.cstmrNativeRrn2 = ''
          customer.value.cstmrForeignerRrn1 = ''
          customer.value.cstmrForeignerRrn2 = ''
        }

        customer.value.isVerified = false
      }
    },
  )

  // ==========================================
  // 3. API 조회 및 데이터 초기화 로직
  // ==========================================

  // 3.1. 통합 데이터 조회 API (초기값 & 임시저장값)
  const apiFetchFormData = async (key = null) => {
    // 실제 API 연동
    const payload = {
      requestKey: key || '',
      formTypeCd: '1', // 신규/번호이동 신청서
      agentCd: customer.value.agentCd || '',
    }
    const res = await post('/api/form/newchange/get', payload, { skipAlert: true })

    // API 결과가 없거나 실패 시 기본값 반환
    if (!res || res.code === '9999') {
      return {
        initial: { customer: {}, product: {}, agreement: {} },
        draft: null,
      }
    }

    // 데이터가 res.data 안에 담겨 오는 경우와 바로 오는 경우 모두 대응
    return res.data || res
  }

  const getLoginAgentCd = () => {
    const userInfo = userStore.getUserInfo()
    return userInfo?.organization?.agentCode || userInfo?.agentCd || userInfo?.agentCode || ''
  }

  // 3.1.1. 신규변경 최초 진입 시 초기값 조회
  const apiGetDefault = async () => {
    try {
      const payload = {
        agentCd: customer.value.agentCd || getLoginAgentCd(),
      }
      const res = await post('/api/form/newchange/getdefault', payload, { skipAlert: true })
      if (res && res.code === '0000' && res.data) {
        const data = res.data

        // 1. Customer 관련 (UI 바인딩 및 화면 표시용)
        const defaultAgent = String(data.agentCd || getLoginAgentCd() || '')
        const customerUpdate = {
          agentCd: defaultAgent,
          agent: defaultAgent, // UI Select용 기본값 세팅
          managerCd: data.managerCd || '',
          modelId: data.modelId || '', // 대표단말기 ID (K7025076)
          handsetProdId: data.prodId || '', // 내부단말기 ID (3208)
          deviceModel: data.prodId || data.modelId || '', // UI Select용 (3208 우선)
          deviceModelNm: data.prodNm || '', // 단말기 명칭 (갤럭시 A21S)
          prodId: data.socCode || '', // 요금제 코드 (PL199R187) - UI에서 prodId로 사용됨
          prodNm: data.socNm || '', // 요금제 명칭 (LTE 데이터 알뜰...)
          reqModelNm: data.reqModelNm || '', // 모델 코드 (SM-A325NK)
          contractPeriod:
            data.enggMnthCnt !== undefined && data.enggMnthCnt !== null && data.enggMnthCnt !== ''
              ? String(data.enggMnthCnt)
              : '24', // 약정 기간 (24)
          installmentMonth:
            data.modelMonthly !== undefined &&
            data.modelMonthly !== null &&
            data.modelMonthly !== ''
              ? String(data.modelMonthly)
              : '24', // 할부 기간 (24)
          discountType: data.sprtTypeCd || '', // 할인 유형 (KD)
          deviceChgTel1: '010', // 휴대폰 앞자리 기본값
          modelSalePolicyCd: '', // 임시저장 복원 방지 (실시간 단말 조회를 통한 세팅 유도)
          prdtSctnCd: data.prdtSctnCd || data.dataType || '',
          dataType: data.dataType || data.prdtSctnCd || '',
        }

        // 2. Product 관련 (서버 전송용 DTO 필드들)
        const productUpdate = {
          modelId: data.modelId || '',
          modelMonthly:
            data.modelMonthly !== undefined &&
            data.modelMonthly !== null &&
            data.modelMonthly !== ''
              ? String(data.modelMonthly)
              : '24',
          modelSalePolicyCd: '', // 임시저장 복원 방지 (실시간 단말 조회를 통한 세팅 유도)
          sprtTypeCd: data.sprtTypeCd || '',
          socCode: data.socCode || '',
          socNm: data.socNm || '',
          installmentMonth:
            data.modelMonthly !== undefined &&
            data.modelMonthly !== null &&
            data.modelMonthly !== ''
              ? String(data.modelMonthly)
              : '24',
          discountType: data.sprtTypeCd || '',
          prdtSctnCd: data.dataType || data.prdtSctnCd || '', // 서버 dataType -> 화면 prdtSctnCd
          dataType: data.prdtSctnCd || data.dataType || '', // 서버 prdtSctnCd -> 화면 dataType
        }

        // 공통코드 FORM_DEFAULT 조회 및 동적 기본값 매핑
        let commonDefaults = []
        try {
          commonDefaults = await getCommonCodeListAll('FORM_DEFAULT')
        } catch (err) {
          console.error('FORM_DEFAULT 공통코드 조회 실패:', err)
        }

        const defaultMap = {}
        if (Array.isArray(commonDefaults)) {
          commonDefaults.forEach((item) => {
            const detail = item.detail || {}
            const key = detail.etcValue1 || item.expnsnStrVal1 || item.EXPNSN_STR_VAL1
            const val = detail.etcValue2 || item.expnsnStrVal2 || item.EXPNSN_STR_VAL2
            if (key) {
              defaultMap[key.toUpperCase()] = val
            }
          })
        }
        formDefaultMap.value = defaultMap

        if (defaultMap['MOVE_THISMONTH_PAY_TYPE_CD'] !== undefined) {
          productUpdate.moveThismonthPayTypeCd =
            defaultMap['MOVE_THISMONTH_PAY_TYPE_CD'] === 'NM' ||
            defaultMap['MOVE_THISMONTH_PAY_TYPE_CD'] === 'Y'
        }
        if (defaultMap['MOVE_ALLOTMENT_STTUS_CD'] !== undefined) {
          const rawVal = String(defaultMap['MOVE_ALLOTMENT_STTUS_CD'])
          const mappedVal = ['2', '02', 'AD'].includes(rawVal) ? 'AD' : rawVal
          productUpdate.moveAllotmentSttusCd = [mappedVal]
        }
        if (defaultMap['MOVE_REFUND_AGREE_YN'] !== undefined) {
          productUpdate.moveRefundAgreeYn = [defaultMap['MOVE_REFUND_AGREE_YN']]
        }
        if (defaultMap['RRATESVC'] !== undefined) {
          productUpdate.addtionId = [defaultMap['RRATESVC']]
        }
        if (defaultMap['INSR_YN'] !== undefined) {
          productUpdate.clauseInsuranceYn = defaultMap['INSR_YN']
        }
        if (
          defaultMap['PAYMENT_BANK_YN'] !== undefined ||
          defaultMap['PAYMENT_CARD_YN'] !== undefined
        ) {
          productUpdate.isAutoAgree =
            defaultMap['PAYMENT_BANK_YN'] === 'Y' || defaultMap['PAYMENT_CARD_YN'] === 'Y'
        }
        if (defaultMap['PAYMENT_PRNTS_YN'] !== undefined) {
          productUpdate.combAgree = defaultMap['PAYMENT_PRNTS_YN'] === 'Y'
        }
        if (defaultMap['JOIN_PAY_MTHD_CD'] !== undefined) {
          productUpdate.joinPayMthdCd = String(defaultMap['JOIN_PAY_MTHD_CD'])
        }

        initialCustomer.value = { ...initialCustomer.value, ...customerUpdate }
        initialProduct.value = { ...initialProduct.value, ...productUpdate }

        // 현재 상태 업데이트
        customer.value = cloneDeep(initialCustomer.value)
        product.value = cloneDeep(initialProduct.value)
        return true
      }
    } catch (e) {
      console.error('Failed to fetch default form data:', e)
    }
    return false
  }

  // 3.2. 폼 진입 시 데이터 초기화
  const initForm = async (savedKey = null) => {
    // 0) 무조건 로컬 기본값으로 초기화 시작 (상태 초기화)
    applicationKey.value = ''
    resNo.value = ''
    wishNoSearchCount.value = 0 // 조회 횟수 초기화
    initialCustomer.value = cloneDeep(DEFAULT_CUSTOMER)
    initialProduct.value = cloneDeep(DEFAULT_PRODUCT)
    initialAgreement.value = cloneDeep(DEFAULT_AGREEMENT)

    // 1) 서버 기본값(getDefault)을 항상 먼저 로드하여 베이스라인 확보
    await apiGetDefault()

    // 오늘 날짜 구하기 및 개통희망일/당일개통여부 디폴트 세팅
    const date = new Date()
    const yToday = date.getFullYear()
    const mToday = String(date.getMonth() + 1).padStart(2, '0')
    const dToday = String(date.getDate()).padStart(2, '0')
    const todayStr = `${yToday}${mToday}${dToday}`

    // requestkey가 없는 경우 (신규 진입)
    if (!savedKey) {
      if (customer.value.joinType === 'HDN3' || customer.value.joinType === 'HCN3') {
        product.value.cstmrBillSendTypeCd = ''
        product.value.reqPayTypeCd = ''
        product.value.othersPaymentYn = 'N'
      } else {
        if (!product.value.cstmrBillSendTypeCd) product.value.cstmrBillSendTypeCd = 'CB'
        if (!product.value.reqPayTypeCd) product.value.reqPayTypeCd = 'D'
      }
      return '0'
    }

    // 2) requestkey가 있는 경우 (임시저장/수정 진입)
    const res = await apiFetchFormData(savedKey)

    // 3) 서버에서 내려준 설정값(Initial) 처리
    // (현재 API 구조상 평면 데이터이므로 initial이 없을 수 있음)
    const initial = res.initial || {}
    if (initial.customer) initialCustomer.value = { ...initialCustomer.value, ...initial.customer }
    if (initial.product) initialProduct.value = { ...initialProduct.value, ...initial.product }
    if (initial.agreement)
      initialAgreement.value = { ...initialAgreement.value, ...initial.agreement }

    // 일단 현재 상태를 초기 설정값으로 세팅
    customer.value = cloneDeep(initialCustomer.value)
    product.value = cloneDeep(initialProduct.value)
    agreement.value = cloneDeep(initialAgreement.value)

    // 4) 임시저장 데이터(Draft) 복원
    applicationKey.value = savedKey
    // res 자체가 데이터이거나 res.data에 데이터가 담겨오는 경우 모두 대응
    const draft = res.draft || (res.socCode ? res : res.data)

    if (draft) {
      loadDraft(draft)

      // 신분증 인증 정보 재사용 불가에 따른 초기화 및 알림
      authFlags.value.identityCertTypeCd = false
      customer.value.isVerified = false

      if (customer.value.joinType === 'HDN3' || customer.value.joinType === 'HCN3') {
        product.value.cstmrBillSendTypeCd = ''
        product.value.reqPayTypeCd = ''
        product.value.othersPaymentYn = 'N'
      }

      // 서버에서 관리하는 마지막 저장 스텝 반환 (tmpStepCd가 2 이상이면 상품 스탭까지만 열리도록 '2'로 제한)
      const rawStep = draft.tmpStepCd || res.tmpStepCd || '0'
      const stepValue = parseInt(rawStep) >= 2 ? '2' : String(rawStep)
      showAlert(
        '이전에 인증하신 신분증 인증 정보는 재사용이 불가하므로, 신분증 인증을 다시 진행해 주세요.',
        () => {},
        () => {},
      )
      return stepValue
    }

    return '0'
  }

  // ==========================================
  // 4. 로직 처리 (리셋, 복원)
  // ==========================================

  /**
   * 유틸: 빈 값이 아닌 경우에만 덮어쓰는 안전한 병합
   */
  const safeMerge = (target, source) => {
    const result = { ...target }
    Object.keys(source).forEach((key) => {
      const val = source[key]
      // null, undefined, 빈 문자열이 아닌 경우에만 덮어씀
      if (val !== undefined && val !== null && val !== '') {
        result[key] = val
      }
    })
    return result
  }

  const loadDraft = (savedData) => {
    if (!savedData) return
    isDraftLoading.value = true
    isDraftLoaded.value = true

    // 0. 평면 구조 데이터를 스토어 구조(customer/product/agreement)에 맞게 매핑할 임시 객체
    const d = savedData?.data || savedData || {} // res.data 구조 대응 및 nullish 방어 추가

    const devTel = d.openNo || d.cstmrMobileNo || ''
    let dVal1 = '010',
      dVal2 = '',
      dVal3 = ''
    if (devTel && devTel.length >= 9) {
      const cleanDevTel = devTel.replace(/\D/g, '')
      dVal1 = cleanDevTel.substring(0, 3)
      dVal2 = cleanDevTel.length === 11 ? cleanDevTel.substring(3, 7) : cleanDevTel.substring(3, 6)
      dVal3 = cleanDevTel.substring(cleanDevTel.length - 4)
    }

    // [1] Customer 매핑 (주요 가입자 정보 및 결제/관리 정보)
    const c = {
      productType: d.reqBuyTypeCd || 'MM',
      joinType: d.operTypeCd || d.joinType || '', // 가입유형 복원 (NAC3 등) - 매우 중요!
      openTypeCd: d.openTypeCd || '',
      serviceTypeCd: d.serviceTypeCd || '',
      cstmrTypeCd: d.cstmrTypeCd,
      cstmrNm: d.cstmrNm || d.userName || '',
      cstmrNativeBirth: d.cstmrNativeBirth,
      cstmrNativeGenderCd: d.cstmrNativeGenderCd,
      address: d.cstmrAdr,
      detailAddress: d.cstmrAdrDtl,
      zipNo: d.cstmrZipcd,
      cstmrAdrBjd: d.cstmrAdrBjd || '',
      mobileNo1: d.cstmrMobileFnNo || '010',
      mobileNo2: d.cstmrMobileMnNo || '',
      mobileNo3: d.cstmrMobileRnNo || '',
      telNo1: d.cstmrTelFnNo || '',
      telNo2: d.cstmrTelMnNo || '',
      telNo3: d.cstmrTelRnNo || '',
      deviceChgTel1: dVal1,
      deviceChgTel2: dVal2,
      deviceChgTel3: dVal3,
      // 관리/대리점 정보
      agentCd: d.agentCd,
      agentNm: d.agentNm,
      agent: d.agentCd,
      shopCd: d.shopCd,
      shopNm: d.shopNm,
      realShopNm: d.realShopNm || '',
      cpntId: d.cpntId || '',
      cpntNm: d.cpntNm || '',
      cntpntShopCd: d.cntpntShopCd || '',
      cntpntShopNm: d.cntpntShopNm || '',
      managerCd: d.managerCd,
      managerNm: d.managerNm,
      // 신분증 인증 및 상세 정보 복원
      identityCertTypeCd: d.identityCertTypeCd || '',
      identityTypeCd: d.identityTypeCd || '',
      identityIssuDate: d.identityIssuDate || '',
      cstmrPrivateBizNoIssuDate: d.cstmrPrivateBizNoIssuDate || '',
      cstmrJuridicalBizNoIssuDate: d.cstmrJuridicalBizNoIssuDate || '',
      identityIssuRegion: d.identityIssuRegion || '',
      selfIssuNo: d.selfIssuNo || '',
      driveLicnsNo: d.driveLicnsNo || '',
      contractNum: d.contractNum || '',
      resNo: d.resNo || '',
      cstmrVisitTypeCd: d.cstmrVisitTypeCd || 'VMY',
      cstmrEmailReceiveYn: d.cstmrEmailReceiveYn || ' ',
      upjnCd: d.upjnCd || '',
      bcuSbst: d.bcuSbst || '',
      cstmrPrivateCname: d.cstmrPrivateCname || '',
      cstmrJuridicalCname:
        ['JP', 'GO'].includes(d.cstmrTypeCd) && ['VMY', 'VDP'].includes(d.cstmrVisitTypeCd)
          ? d.cstmrJuridicalCname || d.cstmrNm || ''
          : '',
      cstmrJuridicalRepNm: d.cstmrJuridicalRepNm || '',
      cstmrJuridicalUserNm: d.cstmrJuridicalUserNm || d.realUserName || '',
      cstmrJuridicalBirth: d.cstmrJuridicalBirth || d.realUserBirthDate || '',
      realUserName: d.realUserName || d.cstmrJuridicalUserNm || '',
      realUserBirthDate: d.realUserBirthDate || d.cstmrJuridicalBirth || '',
      cstmrReceiveTelFnNo: d.cstmrReceiveTelFnNo || '',
      cstmrReceiveTelNmNo: d.cstmrReceiveTelNmNo || '',
      cstmrReceiveTelRnNo: d.cstmrReceiveTelRnNo || '',
      openNo: d.joinType === 'NAC3' || d.operTypeCd === 'NAC3' ? '' : d.openNo || '',
      msfRequestDocList: [], //  임시저장 불러오기 시 구비서류 목록 초기화 (매번 다시 업로드해야 함)
      // 단말/요금제 상세 정보 (K-ID 및 내부 ID 구분)
      modelId: d.modelId, // 대표단말기ID (K...)
      handsetProdId: d.prodId, // 내부단말기ID (3308 등)
      deviceModel: d.prodId || d.modelId, // UI Select용 (기존 3308 대응)
      deviceModelNm: d.prodNm,
      reqModelNm: d.reqModelNm || '', // 모델 코드 복원 추가 (SM-...)
      // 요약 정보 (기존 필드 유지)
      prodId: d.socCode,
      prodNm: d.socNm,
      socCode: d.socCode || '', // socCode 복원 추가
      prodCtgId: d.prodCtgId || '', // prodCtgId 복원 추가
      prdtSctnCd: d.prdtSctnCd || d.dataType || '', // prdtSctnCd 복원 추가
      dataType: d.dataType || d.prdtSctnCd || '', // dataType 복원 추가
      discountType: d.sprtTypeCd,
      modelSalePolicyCd: '', // 임시저장 복원 방지 (실시간 단말 조회를 통한 세팅 유도)
      // 빌링 정보
      cstmrBillSendTypeCd: (d.cstmrBillSendTypeCd || '').trim() || 'CB',
      reqPayTypeCd: d.reqPayTypeCd,
      othersPaymentYn: d.othersPaymentYn,
      autoPayerType:
        d.othersPaymentYn === 'Y' || d.othersPaymentYn === true
          ? 'autoPayerType2'
          : 'autoPayerType1',
      cardPayerType:
        d.othersPaymentYn === 'Y' || d.othersPaymentYn === true
          ? 'cardPayerType2'
          : 'cardPayerType1',
      reqBankCd: d.reqBankCd,
      reqAccountNo: d.reqAccountNo,
      reqAccountNm: d.reqAccountNm || d.othersPaymentNm || '',
      reqAccountRrn: d.reqAccountRrn || d.othersPaymentRrn || '',
      reqCardCompanyCd: d.reqCardCompanyCd,
      reqCardNo: d.reqCardNo,
      reqCardMm: d.reqCardMm,
      reqCardYy: d.reqCardYy,
      reqCardNm: d.reqCardNm || d.othersPaymentNm || '',
      reqCardRrn: d.reqCardRrn || d.othersPaymentRrn || '',
      // 외국인 정보 복원
      cstmrForeignerPn: d.cstmrForeignerPn || '',
      cstmrForeignerCountryCd: d.cstmrForeignerCountryCd || '',
      cstmrForeignerNation: d.cstmrForeignerNation || '',
      cstmrForeignerVisaNo: d.cstmrForeignerVisaNo || '',
      cstmrForeignerVdateStartDate: d.cstmrForeignerVdateStartDate || '',
      cstmrForeignerVdateEndDate: d.cstmrForeignerVdateEndDate || '',
      country: d.cstmrForeignerCountryCd || d.cstmrForeignerNation || '',
      visaType: d.cstmrForeignerVisaNo || '',
      stayPeriod: d.cstmrForeignerVdateEndDate || '',
      // 대리인 및 법정대리인 정보 복원 (데이터의 안정적 상호 백업)
      minorAgentNm: d.minorAgentNm || d.jrdclAgentNm || '',
      minorAgentRrn: d.minorAgentRrn || d.jrdclAgentRrn || '',
      minorAgentRelTypeCd: d.minorAgentRelTypeCd || d.jrdclAgentRelTypeCd || '',
      minorAgentTelFnNo: d.minorAgentTelFnNo || d.jrdclAgentTelFnNo || '',
      minorAgentTelMnNo: d.minorAgentTelMnNo || d.jrdclAgentTelMnNo || '',
      minorAgentTelRnNo: d.minorAgentTelRnNo || d.jrdclAgentTelRnNo || '',
      agentBirthDate:
        d.cstmrTypeCd === 'JP' || d.cstmrTypeCd === 'GO'
          ? (() => {
              const cleanRrn = (d.jrdclAgentRrn || '').replace(/\D/g, '')
              if (cleanRrn && cleanRrn.length >= 6) {
                if (cleanRrn.length >= 8) return cleanRrn.substring(0, 8)
                const yy = Number(cleanRrn.substring(0, 2))
                const yearPrefix = yy > 50 ? '19' : '20'
                return yearPrefix + cleanRrn.substring(0, 6)
              }
              return ''
            })()
          : d.agentBirthDate || d.minorAgentBirth || '',
      agentGender:
        d.cstmrTypeCd === 'JP' || d.cstmrTypeCd === 'GO'
          ? d.jrdclAgentRrn &&
            d.jrdclAgentRrn.length >= 9 &&
            ['2', '4', '6', '8'].includes(d.jrdclAgentRrn.substring(8, 9))
            ? 'F'
            : 'M'
          : d.agentGender || d.minorAgentGenderCd || 'M',
      jrdclAgentNm: d.jrdclAgentNm || '',
      jrdclAgentRrn: d.jrdclAgentRrn || '',
      jrdclAgentRelTypeCd: d.jrdclAgentRelTypeCd || '',
      jrdclAgentTelFnNo: d.jrdclAgentTelFnNo || '',
      jrdclAgentTelMnNo: d.jrdclAgentTelMnNo || '',
      jrdclAgentTelRnNo: d.jrdclAgentTelRnNo || '',

      // 약관 정보 복원 (Customer 측 약관 필드 매핑)
      clausePriCollectYn: d.clausePriCollectYn || 'N',
      clausePriOfferYn: d.clausePriOfferYn || 'N',
      clauseEssCollectYn: d.clauseEssCollectYn || 'N',
      clausePriTrustYn: d.clausePriTrustYn || 'N',
      clausePriAdYn: d.clausePriAdYn || 'N',
      clauseConfidenceYn: d.clauseConfidenceYn || 'N',
      clauseFathYn: d.clauseFathYn || 'N',
      nwBlckAgrmYn: d.nwBlckAgrmYn || 'N',
      appBlckAgrmYn: d.appBlckAgrmYn || 'N',
      blckAppDivCd: d.blckAppDivCd || '',
      soTrnsAgrmYn: d.soTrnsAgrmYn || 'N',
      clauseJehuYn: d.clauseJehuYn || 'N',
      clauseRentalModelCpYn: d.clauseRentalModelCpYn || 'N',
      clauseRentalModelCpPrYn: d.clauseRentalModelCpPrYn || 'N',
      clauseRentalServiceYn: d.clauseRentalServiceYn || 'N',
      clauseMpps35Yn: d.clauseMpps35Yn || 'N',
      clauseFinanceYn: d.clauseFinanceYn || 'N',
      clause5gCoverageYn: d.clause5gCoverageYn || 'N',
      personalInfoCollectAgreeYn: d.personalInfoCollectAgreeYn || 'N',
      othersTrnsAgreeYn: d.othersTrnsAgreeYn || 'N',
      clauseSensiCollectYn: d.clauseSensiCollectYn || 'N',
      clauseSensiOfferYn: d.clauseSensiOfferYn || 'N',
      clausePartnerOfferYn: d.clausePartnerOfferYn || 'N',
      othersTrnsKtAgreeYn: d.othersTrnsKtAgreeYn || 'N',
      othersAdReceiveAgreeYn: d.othersAdReceiveAgreeYn || 'N',
      ktCounselAgreeYn: d.ktCounselAgreeYn || 'N',
      combineSoloTypeYn: d.combineSoloTypeYn || 'N',
      combineSoloYn: d.combineSoloYn || 'N',
      personalLocationAgreeYn: d.personalLocationAgreeYn || 'N',
      clauseInfo01: d.clauseInfo01 || 'N',

      // UI용 약관 불리언 복원 (임시저장 복원 시 보안 정책상 약관 동의 여부는 복원하지 않고 초기화)
      CLAUSE_MOVE_01: false,
      CLAUSE_SELECT_08: false,
      CLAUSE_SELECT_07: false,
      CLAUSE_SELECT_TIT_02: false,
      CLAUSE_SELECT_10: false,

      // 약정 및 할부기간 UI 바인딩용 복원 추가
      contractPeriod:
        d.enggMnthCnt !== undefined && d.enggMnthCnt !== null ? String(d.enggMnthCnt) : '24',
      installmentMonth:
        d.modelMonthly !== undefined && d.modelMonthly !== null ? String(d.modelMonthly) : '',
    }

    // 법인/공공기관 주민등록번호 분리
    if (d.cstmrJuridicalRrn && d.cstmrJuridicalRrn.length >= 6) {
      c.cstmrJuridicalRrn1 = d.cstmrJuridicalRrn.substring(0, 6)
      c.cstmrJuridicalRrn2 = d.cstmrJuridicalRrn.substring(6)
    }

    // 내국인(일반 개인 및 미성년자) 주민등록번호 분리
    if (['NA', 'NM'].includes(d.cstmrTypeCd) && d.cstmrNativeRrn && d.cstmrNativeRrn.length >= 6) {
      c.cstmrNativeRrn1 = d.cstmrNativeRrn.substring(0, 6)
      c.cstmrNativeRrn2 = d.cstmrNativeRrn.substring(6)
    }

    // 외국인(일반 개인 및 미성년자) 외국인등록번호 분리
    if (['FN', 'FM'].includes(d.cstmrTypeCd)) {
      const fRrn = d.cstmrForeignerRrn || d.cstmrNativeRrn || ''
      if (fRrn && fRrn.length >= 6) {
        c.cstmrForeignerRrn1 = fRrn.substring(0, 6)
        c.cstmrForeignerRrn2 = fRrn.substring(6)
      }
    }

    // 법정대리인(미성년자용) / 대리인(법인용) 정보 UI 복원
    const repRrn = d.minorAgentRrn || d.jrdclAgentRrn || ''
    c.repName = d.minorAgentNm || d.jrdclAgentNm || ''
    c.repRelation = d.minorAgentRelTypeCd || d.jrdclAgentRelTypeCd || ''
    c.repPhone1 = d.minorAgentTelFnNo || d.jrdclAgentTelFnNo || ''
    c.repPhone2 = d.minorAgentTelMnNo || d.jrdclAgentTelMnNo || ''
    c.repPhone3 = d.minorAgentTelRnNo || d.jrdclAgentTelRnNo || ''

    if (repRrn && repRrn.length >= 6) {
      c.repRegistrationNo1 = repRrn.substring(0, 6)
      c.repRegistrationNo2 = repRrn.substring(6)
      c.repForeignerNo1 = repRrn.substring(0, 6)
      c.repForeignerNo2 = repRrn.substring(6)
    }

    // 사업자등록번호 분리 (법인/개인사업자 공통 대응)
    const bizNo = d.cstmrJuridicalBizNo || d.cstmrPrivateBizNo || ''
    if (bizNo && bizNo.length === 10) {
      c.cstmrJuridicalBizNo1 = bizNo.substring(0, 3)
      c.cstmrJuridicalBizNo2 = bizNo.substring(3, 5)
      c.cstmrJuridicalBizNo3 = bizNo.substring(5, 10)
    }

    // 이메일 분리
    if (d.cstmrEmailAdr && d.cstmrEmailAdr.includes('@')) {
      const emailParts = d.cstmrEmailAdr.split('@')
      c.emailAddr1 = emailParts[0]
      c.emailAddr2 = emailParts[1]
    }

    // [2] Product 매핑 (서버 DTO 필드들)
    // 번호이동 번호 복원 (d.moveMobileFnNo 등이 없으면 d.openNo로부터 파싱)
    let mFn = d.moveMobileFnNo || ''
    let mMn = d.moveMobileMnNo || ''
    let mRn = d.moveMobileRnNo || ''
    if (
      (!mFn || !mMn || !mRn) &&
      d.openNo &&
      (d.operTypeCd === 'MNP3' ||
        d.joinType === 'MNP3' ||
        c.joinType === 'MNP3' ||
        d.cstmrTypeCd === 'MNP3' ||
        d.joinType === '02')
    ) {
      const cleanOpen = d.openNo.replace(/\D/g, '')
      if (cleanOpen.length >= 9) {
        mFn = cleanOpen.substring(0, 3)
        mMn = cleanOpen.length === 11 ? cleanOpen.substring(3, 7) : cleanOpen.substring(3, 6)
        mRn = cleanOpen.substring(cleanOpen.length - 4)
      }
    }

    // SIM 정보 역매핑 분석 (loadDraft 전용) - 대문자 및 불리언 형 변환 적용
    let sType
    let hSim
    let sPay

    if (d.usimKindsCd === '09') {
      sType = 'ESIM'
      hSim = true
      sPay = 'N'
    } else {
      sType = 'USIM'
      const payMthd = d.usimPayMthdCd || d.usimPriceTypeCd || ''
      if (payMthd && !['N', '06', '09'].includes(payMthd)) {
        hSim = false // USIM 구매
        sPay = payMthd // 수납방식 그대로 대입
      } else {
        hSim = true // USIM 보유
        sPay = 'N'
      }
    }

    const p = {
      modelId: d.modelId,
      modelMonthly: d.modelMonthly,
      installmentMonth:
        d.modelMonthly !== undefined && d.modelMonthly !== null ? String(d.modelMonthly) : '',
      modelSalePolicyCd: '', // 임시저장 복원 방지 (실시간 단말 조회를 통한 세팅 유도)
      sprtTypeCd: d.sprtTypeCd,
      usimKindsCd: d.usimKindsCd,
      reqUsimSn: d.reqUsimSn,
      reqUsimNm: d.reqUsimNm || '',
      simTypeCd: sType,
      hasSim: hSim,
      simPurchaseMethod: sPay,
      usimPayMthdCd:
        sType === 'ESIM'
          ? '3'
          : hSim === true
          ? '1'
          : d.usimPayMthdCd || (['R', '1', '01'].includes(sPay) ? '1' : '2'),
      usimPriceTypeCd:
        sType === 'ESIM'
          ? 'B'
          : hSim === true
          ? 'N'
          : d.usimPriceTypeCd || (['R', '1', '01'].includes(sPay) ? 'R' : 'B'),
      eid: d.eid,
      imei1: d.imei1,
      imei2: d.imei2,
      imei: d.imei || '',
      serialNumber: d.reqPhoneSn || '',
      esimPhoneId: d.esimPhoneId || '',
      uploadPhoneSrlNo: d.uploadPhoneSrlNo || null,
      joinPriceTypeCd: d.joinPriceTypeCd || '',
      joinPayMthdCd: d.joinPayMthdCd || '',
      joinPrice: d.joinPrice || 0,
      moveCompanyCd: d.moveCompanyCd || '', // 번호이동 전전 통신사 복원 추가
      moveMobileNo: `${mFn}${mMn}${mRn}`,
      moveMobileNo1: mFn || '010',
      moveMobileNo2: mMn || '',
      moveMobileNo3: mRn || '',
      moveAuthTypeCd: d.moveAuthTypeCd,
      moveAuthNo: d.moveAuthNo,
      moveAllotmentSttusCd: d.moveAllotmentSttusCd,
      moveRefundAgreeYn: d.moveRefundAgreeYn,
      moveThismonthPayTypeCd: d.moveThismonthPayTypeCd,
      // 요금제 정보 복원 추가 (getdefault로 덮어쓰이지 않도록)
      socCode: d?.socCode || '',
      socNm: d?.socNm || '',
      socBaseChrgAmt: Number(d?.socBaseChrgAmt || 0),
      // 단말기 정보 복원 추가 (getdefault로 덮어쓰이지 않도록)
      reqModelNm: d?.reqModelNm || '',
      deviceModel: d?.reqModelNm || d?.modelId || '',
      // 단말기 요금 상세 정보
      modelInstamt: d.modelInstamt || 0,
      modelPriceVat: d.modelPriceVat || 0,
      modelDiscount1: d.modelDiscount1 || 0,
      modelSprt: d.modelSprt || 0,
      modelPrice: d.modelPrice || 0,
      modelDiscount3: d.modelDiscount3 || 0,
      realMdlInstamt: d.realMdlInstamt || 0,
      hndsetSalePrice: d.hndsetSalePrice || 0,
      dcAmt: d.dcAmt || 0,
      maxApdSprt: d.maxApdSprt || 0,
      addDcAmt: d.addDcAmt || 0,
      prmtAmt: Math.abs(Number(d.prmtAmt || d.PRMT_AMT || 0)),
      recycleYn: d.recycleYn || 'N',
      usimPrice: d.usimPrice || 0,
      sesplsYn: d.sesplsYn || 'N',
      phonePaymentYn: d.phonePaymentYn || 'N',
      reqAdditionPrice: d.reqAdditionPrice || 0,

      // 번호이동 안내 관련
      reqGuideYn: d.reqGuideYn || 'N',
      reqGuideFnNo: d.reqGuideFnNo || '',
      reqGuideRnNo: d.reqGuideRnNo || '',
      reqGuideMnNo: d.reqGuideMnNo || '',
      osstPayDate: d.osstPayDate || '',
      osstPayTypeCd: d.osstPayTypeCd || '',
      movePenalty: d.movePenalty || 0,
      reqWantFnNo: d.reqWantFnNo || '010',
      reqWantMnNo: d.reqWantMnNo || '****',
      reqWantRnNo: d.reqWantRnNo || '',
      wishNo: d.joinType === 'NAC3' || d.operTypeCd === 'NAC3' ? '' : d.wishNo || '',

      // 안심보험 복원 동의여부 세팅 (하드코딩 제거)
      clauseInsuranceYn: d.clauseInsuranceYn || 'N',
      insrCd: d.insrCd || '',
      insrProdCd: d.insrProdCd,
      recCat2: d.insrProdCd || '', // 임시저장 보험 코드를 화면 바인딩용 recCat2 로 복원!
      jehuPartnerTypeCd: d.jehuPartnerTypeCd || '',
      jehuProdTypeCd: d.jehuProdTypeCd || '',
      soCd: d.soCd || 'M',
      openReqDt: d.openReqDt || '',
      etcSpecialSbst: d.etcSpecialSbst || '',

      // 납부정보 복원 세팅 (청구서수신유형, 납부방법, 계좌/카드 정보 등)
      cstmrBillSendTypeCd: d.cstmrBillSendTypeCd,
      reqPayTypeCd: d.reqPayTypeCd,
      othersPaymentYn: d.othersPaymentYn,
      autoPayerType:
        d.othersPaymentYn === 'Y' || d.othersPaymentYn === true
          ? 'autoPayerType2'
          : 'autoPayerType1',
      cardPayerType:
        d.othersPaymentYn === 'Y' || d.othersPaymentYn === true
          ? 'cardPayerType2'
          : 'cardPayerType1',
      reqBankCd: d.reqBankCd,
      reqAccountNo: d.reqAccountNo,
      reqAccountNm: d.reqAccountNm || d.othersPaymentNm || '',
      reqAccountRrn: d.reqAccountRrn || d.othersPaymentRrn || '',
      reqAccountRelTypeCd:
        d.reqAccountRelTypeCd || d.cardRelation || d.othersPaymentRelTypeCd || '',
      reqCardCompanyCd: d.reqCardCompanyCd,
      reqCardNo: d.reqCardNo,
      reqCardMm: d.reqCardMm,
      reqCardYy: d.reqCardYy,
      reqCardNm: d.reqCardNm || d.othersPaymentNm || '',
      reqCardRrn: d.reqCardRrn || d.othersPaymentRrn || '',
      cardRelation: d.cardRelation || d.reqAccountRelTypeCd || d.othersPaymentRelTypeCd || '',
      combId: d.jointBillWithKt || d.prntsBillNo || d.combId || d.combineId || '',
      combAgree: true,
      isAutoAgree: true,
      memo: d.memo || '',
    }

    // 특수 필드 변환 (Array/Boolean)
    if (p.moveAllotmentSttusCd) {
      p.moveAllotmentSttusCd = Array.isArray(p.moveAllotmentSttusCd)
        ? p.moveAllotmentSttusCd
        : [p.moveAllotmentSttusCd]
    }
    if (p.moveRefundAgreeYn) {
      p.moveRefundAgreeYn = Array.isArray(p.moveRefundAgreeYn)
        ? p.moveRefundAgreeYn
        : [p.moveRefundAgreeYn]
    }
    if (p.moveThismonthPayTypeCd !== undefined) {
      p.moveThismonthPayTypeCd =
        d.moveThismonthPayTypeCd === 'Y' || d.moveThismonthPayTypeCd === true
    }

    // 부가서비스 복원 (store.product.additionList에 직접 담지 않고 선택 상태 ID만 매핑)
    p.additionList = []
    p.addtionId = []
    p.reqAdditionListNm = []

    if (d.additionList && Array.isArray(d.additionList)) {
      // 유료 부가서비스 ID 추출
      p.addtionId = d.additionList
        .filter((v) => Number(v.rantal || v.baseAmt || 0) > 0)
        .map((v) => v.additionId || v.rateCd || v.prodId)
        .filter(Boolean)

      // 무료 부가서비스 ID 추출
      p.reqAdditionListNm = d.additionList
        .filter((v) => Number(v.rantal || v.baseAmt || 0) === 0)
        .map((v) => v.additionId || v.rateCd || v.prodId)
        .filter(Boolean)
    } else if (d.reqAdditionListNm) {
      try {
        if (String(d.reqAdditionListNm).trim().startsWith('[')) {
          const parsed = JSON.parse(d.reqAdditionListNm)
          if (Array.isArray(parsed)) {
            p.addtionId = parsed
              .filter((v) => Number(v.rantal || v.baseAmt || 0) > 0)
              .map((v) => v.additionId || v.rateCd || v.prodId)
              .filter(Boolean)

            p.reqAdditionListNm = parsed
              .filter((v) => Number(v.rantal || v.baseAmt || 0) === 0)
              .map((v) => v.additionId || v.rateCd || v.prodId)
              .filter(Boolean)
          }
        } else {
          p.addtionId = []
          p.reqAdditionListNm = []
        }
      } catch (e) {
        console.error('Failed to parse reqAdditionListNm', e)
      }
    }

    // [3] Agreement 매핑 (약관 동의)
    const a = {
      clausePriCollectYn: d.clausePriCollectYn,
      clausePriOfferYn: d.clausePriOfferYn,
      clauseEssCollectYn: d.clauseEssCollectYn,
      clausePriTrustYn: d.clausePriTrustYn,
      clausePriAdYn: d.clausePriAdYn,
      nwBlckAgrmYn: d.nwBlckAgrmYn,
      appBlckAgrmYn: d.appBlckAgrmYn,
      moveRefundAgreeYn: d.moveRefundAgreeYn === 'Y',
      clauseJehuYn: d.clauseJehuYn,
    }

    // 1. 임시저장값(Draft) 세팅: 초기값(GetDefault 포함) 위에 API 데이터를 안전하게 병합
    draftCustomer.value = safeMerge(initialCustomer.value, c)
    draftProduct.value = safeMerge(initialProduct.value, p)
    draftAgreement.value = safeMerge(initialAgreement.value, a)

    // 2. 현재값(Current)을 임시저장값으로 최종 복원
    customer.value = cloneDeep(draftCustomer.value)
    product.value = cloneDeep(draftProduct.value)
    agreement.value = cloneDeep(draftAgreement.value)

    // 3. 재인증 요구 및 고정 필드 처리
    // 임시저장 데이터를 불러온 경우 가입유형/고객유형 변경 불가 처리
    customer.value.isSaved = true
    parentScanId.value = d.parentScanId || stepStore.parentScanId || ''
    customer.value.repAgree = false // 법정대리인 동의 초기화

    if (d.resNo) {
      resNo.value = String(d.resNo)
    }

    // 데이터는 복구하되 인증 플래그는 모두 리셋 (신분증 인증 등 다시 수행 필요)
    customer.value.isScanVerified = false
    if (customer.value.identityCertTypeCd !== 'S') {
      customer.value.isVerified = false
    } else {
      customer.value.isVerified = true
    }

    Object.keys(authFlags.value).forEach((key) => {
      authFlags.value[key] = false
    })
    if (customer.value.identityCertTypeCd === 'S') {
      authFlags.value.identityCertTypeCd = true
    }
    setTimeout(() => {
      isDraftLoading.value = false
    }, 100)
  }

  const resetStep = (step) => {
    if (step === 1) {
      // 신규개통 사전체크 완료(preChecked = true) 상태이면 고객단계 초기화 불가
      if (preChecked.value && customer.value.joinType === 'NAC3') {
        console.warn('신규개통 사전체크가 완료되어 고객단계를 초기화할 수 없습니다.')
        return
      }
      applicationKey.value = ''
      customer.value.isSaved = false
      preChecked.value = false
      // K-Note 정보 백업 (초기화 시 인증 유지를 위해)
      const backupKnote = {
        knoteIdentityScanCstmrNm: customer.value.knoteIdentityScanCstmrNm,
        knoteIdentityEssNo: customer.value.knoteIdentityEssNo,
        knoteIdentityTypeCd: customer.value.knoteIdentityTypeCd,
        knoteIdentityScanDt: customer.value.knoteIdentityScanDt,
        knoteScanId: customer.value.knoteScanId,
      }
      // 현재값을 초기값(Initial)으로 리셋
      customer.value = cloneDeep(initialCustomer.value)
      // K-Note 정보 복원
      Object.assign(customer.value, backupKnote)
    } else if (step === 2) {
      const joinType = customer.value.joinType

      // 초기화 대상 필드 목록을 컴포넌트 단위로 명시적으로 작성 (White-list 방식)
      const devicePlanFields = [
        'prodNm',
        'prodCtgId',
        'prodId',
        'deviceModel',
        'contractPeriod',
        'installmentMonth',
        'discountType',
        'socCode',
        'socNm',
        'socBaseChrgAmt',
      ]

      const simFields = [
        'hasSim',
        'simTypeCd',
        'usimKindsCd',
        'reqUsimSn',
        'reqUsimNm',
        'simPurchaseMethod',
        'eid',
        'imei1',
        'imei2',
        'imei',
        'esimPhoneId',
      ]

      const serialNumberFields = ['serialNumber', 'uploadPhoneSrlNo']

      const insuranceFields = [
        'clauseInsuranceYn',
        'insrCd',
        'insrProdCd',
        'recCat1',
        'recCat2',
        'insuranceAgree',
      ]

      const billingFields = [
        'cstmrBillSendTypeCd',
        'reqPayTypeCd',
        'autoPayerType',
        'reqBankCd',
        'reqAccountNo',
        'reqAccountNm',
        'reqAccountRrn',
        'reqAccountRelTypeCd',
        'isAutoAgree',
        'reqWireTypeCd',
        'cardPayerType',
        'reqCardCompanyCd',
        'reqCardNo',
        'reqCardMm',
        'reqCardYy',
        'reqCardNm',
        'reqCardRrn',
        'cardRelation',
        'othersPaymentTelFnNo',
        'othersPaymentTelMnNo',
        'othersPaymentTelRnNo',
        'othersPaymentNm',
        'othersPaymentRrn',
        'othersPaymentRelTypeCd',
        'othersPaymentReqNm',
        'othersPaymentYn',
        'othersPaymentAgrYn',
        'prntsBillNo',
        'combId',
        'combAgree',
      ]

      const vasFields = ['additionList', 'addtionId', 'reqAdditionPrice']

      const otherProductFields = [
        'jehuPartnerTypeCd',
        'jehuPartnerTypeNm',
        'jehuProdTypeCd',
        'modelId',
        'modelMonthly',
        'modelInstamt',
        'modelSalePolicyCd',
        'modelPriceVat',
        'modelDiscount1',
        'modelSprt',
        'modelPrice',
        'modelDiscount3',
        'realMdlInstamt',
        'hndsetSalePrice',
        'sprtTypeCd',
        'dcAmt',
        'maxApdSprt',
        'addDcAmt',
        'prmtAmt',
        'recycleYn',
        'usimPriceTypeCd',
        'usimPrice',
        'sesplsYn',
        'joinPriceTypeCd',
        'joinPayMthdCd',
        'joinPrice',
        'estimatedAmtInfo',
      ]

      // 기본적으로 모든 상품스텝 컴포넌트 필드들을 초기화 대상에 추가
      const fieldsToReset = [
        ...devicePlanFields,
        ...simFields,
        ...serialNumberFields,
        ...vasFields,
        ...insuranceFields,
        ...billingFields,
        ...otherProductFields,
      ]

      // 신규개통 예약 희망번호 컴포넌트 (MsfNumberReservation.vue) 초기화 명시
      // 단, 신규개통이면서 희망번호 예약 완료 상태인 경우에는 초기화 대상 필드에서 완전 제외
      const isNacReserved = joinType === 'NAC3' && product.value.wishNo
      if (!isNacReserved) {
        fieldsToReset.push('reqWantFnNo', 'reqWantMnNo', 'reqWantRnNo', 'wishNo', 'wishNoc')
      }

      // 번호이동 사전동의 컴포넌트 (MsfMnpInfo.vue) 초기화 명시
      // 단, 번호이동이면서 사전체크 성공/사전동의 완료 상태인 경우에는 초기화 대상 필드에서 완전 제외
      const isMnpPrechecked =
        joinType === 'MNP3' && (preChecked.value || authFlags.value.moveAuthTypeCd)
      if (!isMnpPrechecked) {
        fieldsToReset.push(
          'moveCompanyCd',
          'moveMobileNo',
          'moveMobileNo1',
          'moveMobileNo2',
          'moveMobileNo3',
          'moveAuthTypeCd',
          'moveAuthNo',
          'moveAllotmentSttusCd',
          'moveRefundAgreeYn',
          'moveThismonthPayTypeCd',
        )
      }

      // 초기화값 덮어쓰기 수행
      const initial = cloneDeep(initialProduct.value)
      fieldsToReset.forEach((key) => {
        if (key in initial) {
          product.value[key] = initial[key]
        }
      })

      // 요금 납부 방법의 기본값을 자동이체('D')로 강제 설정
      product.value.reqPayTypeCd = 'D'

      product.value.memo = ''
      initialProduct.value.memo = ''
      // 고객단계가 아니므로 인증정보는 초기화하지 않고 남겨둡니다.
      // authFlags.value.reqUsimSn = false
      // authFlags.value.imei = false
      // authFlags.value.moveAuthTypeCd = false
      // authFlags.value.reserveNo = false
      // authFlags.value.autoAcct = false
      // authFlags.value.reqCardNo = false
      // authFlags.value.combId = false
    } else if (step === 3) {
      agreement.value = cloneDeep(initialAgreement.value)
    }
  }

  const resetProductStep = () => {
    // 납부 정보 약관 동의 및 제3자 납부 동의 리셋
    product.value.isAutoAgree =
      formDefaultMap.value['PAYMENT_BANK_YN'] === 'Y' ||
      formDefaultMap.value['PAYMENT_CARD_YN'] === 'Y'
    product.value.combAgree = formDefaultMap.value['PAYMENT_PRNTS_YN'] === 'Y'
    product.value.othersPaymentAgrYn = 'N'
    product.value.othersPaymentYn = 'N'
    product.value.memo = ''

    // 번호이동 기본 설정 리셋 (FORM_DEFAULT 공통코드 기반 동적 세팅)
    if (formDefaultMap.value['MOVE_THISMONTH_PAY_TYPE_CD'] !== undefined) {
      product.value.moveThismonthPayTypeCd =
        formDefaultMap.value['MOVE_THISMONTH_PAY_TYPE_CD'] === 'NM' ||
        formDefaultMap.value['MOVE_THISMONTH_PAY_TYPE_CD'] === 'Y'
    }
    if (formDefaultMap.value['MOVE_ALLOTMENT_STTUS_CD'] !== undefined) {
      const rawVal = String(formDefaultMap.value['MOVE_ALLOTMENT_STTUS_CD'])
      const mappedVal = ['2', '02', 'AD'].includes(rawVal) ? 'AD' : rawVal
      product.value.moveAllotmentSttusCd = [mappedVal]
    }
    if (formDefaultMap.value['MOVE_REFUND_AGREE_YN'] !== undefined) {
      product.value.moveRefundAgreeYn = [formDefaultMap.value['MOVE_REFUND_AGREE_YN']]
    }
    if (formDefaultMap.value['JOIN_PAY_MTHD_CD'] !== undefined) {
      product.value.joinPayMthdCd = String(formDefaultMap.value['JOIN_PAY_MTHD_CD'])
    }

    // 납부 정보 인증 플래그 리셋
    authFlags.value.autoAcct = false
    authFlags.value.reqCardNo = false
    authFlags.value.combId = false
  }

  const resetAll = async (clear = false) => {
    const currentKey = applicationKey.value // 현재 키 보관
    applicationKey.value = ''
    draftApplicationKey.value = ''
    isDraftLoaded.value = false
    documentId.value = ''
    originalfileName.value = ''
    resNo.value = ''
    parentScanId.value = ''
    preChecked.value = false
    wishNoSearchCount.value = 0 // 조회 횟수 초기화
    // 모든 인증 플래그 초기화
    Object.keys(authFlags.value).forEach((key) => {
      authFlags.value[key] = false
    })
    // 각 단계 데이터 초기화
    customer.value = cloneDeep(DEFAULT_CUSTOMER)
    product.value = cloneDeep(DEFAULT_PRODUCT)
    agreement.value = cloneDeep(DEFAULT_AGREEMENT)

    if (clear) {
      // 완전히 스토어를 비우는 것이 목적이므로 초기값 및 draft 로드 생략
      return
    }

    // 초기화 후 서버 설정값 및 (현재 키가 있다면) 임시저장 데이터 다시 로드
    await initForm(currentKey)
  }

  const copyApplication = (savedData) => {
    loadDraft(savedData, 3)
    applicationKey.value = ''

    // 식별 정보 등 민감 데이터 비우기
    customer.value.identityTypeCd = ''
    product.value.reqWantFnNo = ''
    product.value.reqWantMnNo = ''
    product.value.reqWantRnNo = ''
    product.value.wishNo = ''
    product.value.moveAuthNo = ''
    product.value.transferBankNum = ''
    product.value.transferCardNum = ''
    product.value.reqUsimSn = ''
    product.value.eid = ''
    product.value.imei = ''
    product.value.serialNumber = ''
    product.value.imei1 = ''
    product.value.imei2 = ''

    //  복사하기 시 사전체크 및 본인인증 관련 인증상태 강제 초기화 (수정 가능하도록 잠금 해제)
    preChecked.value = false
    customer.value.isSaved = false
    customer.value.eligibilityStatus = ''
    customer.value.isEligible = false
    customer.value.isScanVerified = false
    customer.value.isVerified = false
    customer.value.termsAgreed = false
    customer.value.repAgree = false

    // authFlags 전체 초기화
    Object.keys(authFlags.value).forEach((key) => {
      authFlags.value[key] = false
    })
  }

  // ==========================================
  // 5. API 통신: 저장
  // ==========================================
  const apiSaveDraft = async (step, additionalPayload = {}) => {
    try {
      // 원본 데이터 보호를 위해 깊은 복사 수행
      const c = cloneDeep(customer.value)
      const p = cloneDeep(product.value)
      const a = cloneDeep(agreement.value)

      // 대리점 정보 유실 방지를 위한 폴백 적용
      if (!c.agentCd) {
        if (c.agent && String(c.agent).startsWith('V') && String(c.agent).length >= 8) {
          c.agentCd = c.agent
        } else {
          const loginAgentCd = getLoginAgentCd()
          if (loginAgentCd) {
            c.agentCd = loginAgentCd
          }
        }
      }
      if (c.agentCd) {
        if (!c.agent) c.agent = c.agentCd
        if (!c.shopCd) c.shopCd = c.agentCd
        if (!c.cntpntShopCd) c.cntpntShopCd = c.agentCd
        if (!c.cpntId) c.cpntId = c.agentCd
      }

      const termsKeys = [
        'clauseMoveCode',
        'clausePriCollectYn',
        'clausePriOfferYn',
        'clauseEssCollectYn',
        'clausePriTrustYn',
        'clausePriAdYn',
        'clauseConfidenceYn',
        'clauseFathYn',
        'clauseSensiCoverageYn',
        'nwBlckAgrmYn',
        'appBlckAgrmYn',
        'soTrnsAgrmYn',
        'clauseJehuYn',
        'clauseRentalModelCpYn',
        'clauseRentalModelCpPrYn',
        'clauseRentalServiceYn',
        'clauseMpps35Yn',
        'clauseFinanceYn',
        'clause5gCoverageYn',
        'personalInfoCollectAgreeYn',
        'othersTrnsAgreeYn',
        'clauseSensiCollectYn',
        'clauseSensiOfferYn',
        'clausePartnerOfferYn',
        'othersTrnsKtAgreeYn',
        'othersAdReceiveAgreeYn',
        'ktCounselAgreeYn',
        'combineSoloTypeYn',
        'combineSoloYn',
        'isAutoAgree',
        'combAgree',
        'moveThismonthPayTypeCd',
        'personalLocationAgreeYn',
        'clauseInfo01',
      ]

      const toYN = (val) => {
        if (val === true || val === 'Y') return 'Y'
        if (val === false || val === 'N') return 'N'
        return ''
      }

      // 가입자 생년월일 8자리 추출
      const customerSsnForBirth = (
        (c.cstmrNativeRrn1 || '') + (c.cstmrNativeRrn2 || '') ||
        (c.cstmrForeignerRrn1 || '') + (c.cstmrForeignerRrn2 || '') ||
        (c.cstmrJuridicalRrn1 || '') + (c.cstmrJuridicalRrn2 || '') ||
        ''
      ).replace(/[^0-9]/g, '')

      let cstmrBirth8 = ''
      if (customerSsnForBirth.length >= 7) {
        cstmrBirth8 = extractYYYYMMDDRrn(customerSsnForBirth) || ''
      }
      if (!cstmrBirth8) {
        cstmrBirth8 = (c.cstmrNativeBirth || c.realUserBirthDate || '').replace(/[^0-9]/g, '')
      }

      // 약관 및 체크박스 YN 변환 수행
      termsKeys.forEach((key) => {
        if (Object.prototype.hasOwnProperty.call(c, key)) c[key] = toYN(c[key])
        if (Object.prototype.hasOwnProperty.call(p, key)) p[key] = toYN(p[key])
        if (Object.prototype.hasOwnProperty.call(a, key)) a[key] = toYN(a[key])
      })

      const payload = {
        requestKey: applicationKey.value || null,
        resNo: customer.value.resNo || resNo.value || null,
        tmpStepCd: String(step),
        parentScanId: parentScanId.value || stepStore.parentScanId || '',

        // 공통 정보 (NewChangeInfoRequest 매핑)
        managerCd: c.managerCd,
        managerNm: c.managerNm,
        agentCd: c.agentCd,
        agentNm: c.agentNm,
        shopCd: c.shopCd,
        shopNm: c.shopNm,
        realShopNm: c.realShopNm,
        cpntId: c.cpntId,
        cpntNm: c.cpntNm,
        cntpntShopCd: c.cntpntShopCd,
        cntpntShopNm: c.cntpntShopNm,
        reqBuyTypeCd: c.productType,
        openTypeCd: c.openTypeCd,
        serviceTypeCd: c.serviceTypeCd,
        operTypeCd: c.joinType,
        openNo: (c.openNo || '').replace(/-/g, ''),
        cstmrTypeCd: c.cstmrTypeCd,
        identityCertTypeCd: c.identityCertTypeCd,
        knoteIdentityScanCstmrNm: c.knoteIdentityScanCstmrNm,
        knoteIdentityEssNo: c.knoteIdentityEssNo,
        knoteIdentityTypeCd: c.knoteIdentityTypeCd,
        knoteIdentityScanDt: c.knoteIdentityScanDt,
        knoteScanId: c.identityCertTypeCd === 'S' ? '' : c.knoteScanId,
        fathTrgYn: toYN(c.fathTrgYn),
        fathTrgIdentityCertTypeCd: c.fathTrgIdentityCertTypeCd,
        fathTransacId:
          c.identityCertTypeCd === 'S' && !c.fathTransacId ? 'MIS00001234' : c.fathTransacId || '',
        fathCmpltNtfyDate: c.fathCmpltNtfyDate,
        fathTelNo: c.fathTelNo,
        fathMobileFnNo: c.fathMobileFnNo,
        fathMobileMnNo: c.fathMobileMnNo,
        fathMobileRnNo: c.fathMobileRnNo,
        authInfo: c.authInfo,
        identityTypeCd: c.identityTypeCd,
        identityIssuDate: (c.identityIssuDate || '').replace(/[^0-9]/g, ''),
        cstmrPrivateBizNoIssuDate: (c.cstmrPrivateBizNoIssuDate || '').replace(/[^0-9]/g, ''),
        cstmrJuridicalBizNoIssuDate: (c.cstmrJuridicalBizNoIssuDate || '').replace(/[^0-9]/g, ''),
        identityIssuRegion: c.identityIssuRegion,
        selfIssuNo: c.selfIssuNo,
        driveLicnsNo: c.driveLicnsNo,
        contractNum: c.contractNum,
        formType: c.formType || 'NEWCHANGE',
        canBulkCorporateOpenYn: c.canBulkCorporateOpenYn || 'N',
        bulkActivationCnt: Number(c.bulkActivationCnt || 1),

        // Product Info (MSF_REQUEST)
        prodId:
          c.productType === 'UU' || (c.handsetProdId && String(c.handsetProdId).startsWith('K'))
            ? ''
            : c.handsetProdId || '', // 내부단말기ID (숫자)
        prodNm: c.productType === 'UU' ? '' : c.deviceModelNm || '',
        reqPhoneSn: c.productType === 'UU' ? '' : p.serialNumber,
        reqModelNm: c.productType === 'UU' ? '' : c.reqModelNm || '', // 모델 코드 (SM-...)
        reqModelColor: c.productType === 'UU' ? '' : p.reqModelColor || '',
        shopUsmId: p.shopUsmId || '',
        usimKindsCd: p.simTypeCd === 'ESIM' ? '09' : p.hasSim === false ? '02' : '06',
        reqUsimSn: p.simTypeCd === 'ESIM' ? '' : p.reqUsimSn,
        volumeMobileNoQnty:
          c.canBulkCorporateOpenYn === 'Y' && c.cstmrTypeCd === 'JP' && c.joinType === 'NAC3'
            ? Number(c.bulkActivationCnt || 1)
            : null,
        volumeRepMobileNoYn:
          c.canBulkCorporateOpenYn === 'Y' && c.cstmrTypeCd === 'JP' && c.joinType === 'NAC3' && Number(c.bulkActivationCnt || 0) > 0
            ? 'Y'
            : 'N',
        reqUsimNm: p.reqUsimNm || '',
        eid: p.simTypeCd === 'ESIM' ? p.eid : '',
        imei1: p.simTypeCd === 'ESIM' ? p.imei1 : '',
        imei2: p.simTypeCd === 'ESIM' ? p.imei2 : '',
        esimPhoneId: p.esimPhoneId || '',
        uploadPhoneSrlNo: p.uploadPhoneSrlNo || null,
        reqWantFnNo: c.joinType === 'MNP3' ? '' : p.reqWantFnNo,
        reqWantMnNo: c.joinType === 'MNP3' ? '' : p.reqWantMnNo,
        reqWantRnNo: p.reqWantRnNo,
        insrCd: p.insrCd || '',
        insrProdCd: p.recCat2 || p.insrProdCd || '',
        clauseInsuranceYn: toYN(p.clauseInsuranceYn),
        jehuPartnerTypeCd: p.jehuPartnerTypeCd || '',
        jehuProdTypeCd: p.jehuProdTypeCd || '',
        // 부가서비스 목록 규격화 (additionId, additionNm, rantal, additionKey)
        additionList: (p.additionList || []).map((vas) => ({
          additionId: vas.additionId || vas.rateCd || vas.prodId,
          additionNm: vas.additionNm || vas.rateNm || vas.prodNm,
          rantal: Number(vas.rantal || vas.baseAmt || 0),
          additionKey: vas.additionKey || '',
        })),
        reqAdditionListNm:
          p.additionList && p.additionList.length > 0
            ? p.additionList
                .map((vas) => vas.additionNm || vas.rateNm || vas.prodNm || '')
                .filter(Boolean)
                .join(',')
            : '',
        reqAdditionPrice: Number(p.reqAdditionPrice || 0),
        phonePaymentYn: p.phonePaymentYn || 'N',
        onOffTypeCd: p.onOffTypeCd || '3',
        soCd: p.soCd || '',
        memo: p.memo || '',
        etcSpecialSbst: p.etcSpecialSbst || '',

        // Customer Info (MSF_REQUEST_CSTMR)
        cstmrNm: (c.cstmrNm || c.userName || c.realUserName || '').trim(),
        userName: (c.userName || c.cstmrNm || c.realUserName || '').trim(),
        cstmrNativeRrn:
          (c.cstmrNativeRrn1 || '') + (c.cstmrNativeRrn2 || '') ||
          (c.cstmrForeignerRrn1 || '') + (c.cstmrForeignerRrn2 || '') ||
          (c.cstmrJuridicalRrn1 || '') + (c.cstmrJuridicalRrn2 || '') ||
          '',
        cstmrNativeBirth: (
          (['NA', 'NM'].includes(c.cstmrTypeCd) && c.cstmrNativeRrn1 && c.cstmrNativeRrn2
            ? (['3', '4', '7', '8'].includes(c.cstmrNativeRrn2.charAt(0)) ? '20' : '19') +
              c.cstmrNativeRrn1
            : '') ||
          (['NA', 'NM'].includes(c.cstmrTypeCd) ? c.cstmrNativeBirth || c.realUserBirthDate : '') ||
          ''
        ).replace(/[^0-9]/g, ''),
        cstmrNativeGenderCd:
          ['NA', 'NM'].includes(c.cstmrTypeCd) && c.cstmrNativeRrn2
            ? Number(c.cstmrNativeRrn2.charAt(0)) % 2 === 1
              ? 'M'
              : 'F'
            : '',
        cstmrPrivateCname: c.cstmrPrivateCname,
        cstmrPrivateBizNo:
          c.cstmrPrivateBizNo ||
          (c.cstmrJuridicalBizNo1 || '') +
            (c.cstmrJuridicalBizNo2 || '') +
            (c.cstmrJuridicalBizNo3 || '') ||
          '',
        cstmrForeignerRrn: (c.cstmrForeignerRrn1 || '') + (c.cstmrForeignerRrn2 || ''),
        cstmrForeignerBirth:
          c.cstmrForeignerBirth ||
          (c.userBirthDate || '').replace(/[^0-9]/g, '') ||
          (c.cstmrForeignerRrn1 && c.cstmrForeignerRrn2
            ? extractYYYYMMDDRrn(c.cstmrForeignerRrn1 + c.cstmrForeignerRrn2)
            : '') ||
          '',
        cstmrForeignerGenderCd:
          c.cstmrForeignerGenderCd ||
          (['FN', 'FM'].includes(c.cstmrTypeCd) && c.cstmrForeignerRrn2
            ? Number(c.cstmrForeignerRrn2.charAt(0)) % 2 === 1
              ? 'M'
              : 'F'
            : ''),
        cstmrForeignerPn: c.cstmrForeignerPn || '',
        cstmrForeignerCountryCd: c.cstmrForeignerCountryCd || c.country || '',
        cstmrForeignerNation: c.cstmrForeignerNation || c.country || '',
        cstmrForeignerVisaNo: c.cstmrForeignerVisaNo || c.visaType || '',
        cstmrForeignerVdateStartDate: c.cstmrForeignerVdateStartDate,
        cstmrForeignerVdateEndDate: c.cstmrForeignerVdateEndDate,
        cstmrJuridicalCname:
          ['JP', 'GO'].includes(c.cstmrTypeCd) && ['VMY', 'VDP'].includes(c.cstmrVisitTypeCd)
            ? c.cstmrJuridicalCname || c.cstmrNm || ''
            : '',
        cstmrJuridicalRrn: (c.cstmrJuridicalRrn1 || '') + (c.cstmrJuridicalRrn2 || ''),
        cstmrJuridicalBizNo:
          (c.cstmrJuridicalBizNo1 || '') +
          (c.cstmrJuridicalBizNo2 || '') +
          (c.cstmrJuridicalBizNo3 || ''),
        cstmrJuridicalRepNm: c.cstmrJuridicalRepNm,
        upjnCd: c.upjnCd,
        bcuSbst: c.bcuSbst,
        cstmrJuridicalUserNm: c.cstmrJuridicalUserNm || c.realUserName || '',
        cstmrJuridicalBirth: (c.cstmrJuridicalBirth || c.realUserBirthDate || '').replace(
          /[^0-9]/g,
          '',
        ),
        cstmrVisitTypeCd: c.cstmrVisitTypeCd,
        cstmrMobileFnNo: c.mobileNo1,
        cstmrMobileMnNo: c.mobileNo2,
        cstmrMobileRnNo: c.mobileNo3,
        cstmrMobileNo: (c.mobileNo1 || '') + (c.mobileNo2 || '') + (c.mobileNo3 || ''),
        cstmrTelFnNo: c.telNo1,
        cstmrTelMnNo: c.telNo2,
        cstmrTelRnNo: c.telNo3,
        cstmrTelNo: (c.telNo1 || '') + (c.telNo2 || '') + (c.telNo3 || ''),
        cstmrAdr: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : c.address,
        cstmrAdrDtl: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : c.detailAddress,
        cstmrZipcd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : c.zipNo,
        cstmrAdrBjd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : c.cstmrAdrBjd,
        cstmrEmailAdr: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : c.emailAddr1 && c.emailAddr2
            ? c.emailAddr1 + '@' + c.emailAddr2
            : '',
        cstmrEmailReceiveYn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? 'N'
          : toYN(c.cstmrEmailReceiveYn),
        country: c.country,
        stayPeriod: (c.stayPeriod || '').replace(/[^0-9]/g, ''),
        visaType: c.visaType,
        cstmrReceiveTelFnNo: c.cstmrReceiveTelFnNo,
        cstmrReceiveTelNmNo: c.cstmrReceiveTelNmNo,
        cstmrReceiveTelRnNo: c.cstmrReceiveTelRnNo,
        msfRequestDocList: c.msfRequestDocList || [],

        // Agent Info (MSF_REQUEST_AGENT / REPRESENTATIVE)
        // Agent Info - 법인/공공기관(JP/GO) 위임대리인 vs 미성년자 법정대리인 분기
        minorAgentNm: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentNm,
        minorAgentRrn: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : (c.repRegistrationNo1 || '') + (c.repRegistrationNo2 || '') ||
            (c.repForeignerNo1 || '') + (c.repForeignerNo2 || '') ||
            c.minorAgentRrn,
        minorAgentBirth: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : extractYYYYMMDDRrn(
              (c.repRegistrationNo1 || c.repForeignerNo1 || '') +
                (c.repRegistrationNo2 || c.repForeignerNo2 || ''),
            ) ||
            c.minorAgentBirth ||
            (c.repBirthDate || '').replace(/[^0-9]/g, '') ||
            '',
        minorAgentGenderCd: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : c.agentGender ||
            c.minorAgentGenderCd ||
            (c.repRegistrationNo2 || c.repForeignerNo2
              ? Number((c.repRegistrationNo2 || c.repForeignerNo2).charAt(0)) % 2 === 1
                ? 'M'
                : 'F'
              : ''),
        minorAgentRelTypeCd: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentRelTypeCd,
        minorAgentTelFnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentTelFnNo,
        minorAgentTelMnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentTelMnNo,
        minorAgentTelRnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentTelRnNo,
        minorAgentAgrmYn: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : toYN(c.minorAgentAgrmYn),
        minorAgentSelfInqryAgrmYn: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : toYN(c.minorAgentSelfInqryAgrmYn),
        minorAgentSelfCertTypeCd: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : c.minorAgentSelfCertTypeCd || '01',
        minorAgentCiInfo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentCiInfo,
        // 법인/공공기관 위임대리인 필드 - JP/GO일 때 화면의 minorAgent 바인딩값을 jrdclAgent로 세팅
        jrdclAgentNm: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? c.minorAgentNm || c.jrdclAgentNm || ''
          : '',
        jrdclAgentRrn: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? (c.agentBirthDate || '').replace(/[^0-9]/g, '').length === 8
            ? (c.agentBirthDate || '').replace(/[^0-9]/g, '') + (c.agentGender === 'F' ? '2' : '1')
            : (c.agentBirthDate || '').replace(/[^0-9]/g, '')
          : '',
        jrdclAgentRelTypeCd: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.minorAgentRelTypeCd : '',
        jrdclAgentTelFnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.minorAgentTelFnNo : '',
        jrdclAgentTelMnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.minorAgentTelMnNo : '',
        jrdclAgentTelRnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.minorAgentTelRnNo : '',
        agentBirthDate: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? (c.agentBirthDate || '').replace(/[^0-9]/g, '')
          : '',
        agentGender: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.agentGender || '' : '',

        // Sale Info (MSF_REQUEST_SALE)
        modelId: c.productType === 'UU' ? '' : c.modelId, // 대표단말기ID (K...)
        modelMonthly: c.productType === 'UU' ? '0' : c.installmentMonth,
        modelInstamt: c.productType === 'UU' ? 0 : Number(p.modelInstamt || 0),
        modelSalePolicyCd: c.productType === 'UU' ? '' : c.modelSalePolicyCd || '', // c에서 참조
        modelPriceVat: c.productType === 'UU' ? 0 : Number(p.modelPriceVat || 0),
        modelDiscount1: c.productType === 'UU' ? 0 : Number(p.modelDiscount1 || 0),
        modelSprt: c.productType === 'UU' ? 0 : Number(p.modelSprt || 0),
        modelPrice: c.productType === 'UU' ? 0 : Number(p.modelPrice || 0),
        modelDiscount3: c.productType === 'UU' ? 0 : Number(p.modelDiscount3 || 0),
        realMdlInstamt: c.productType === 'UU' ? 0 : Number(p.realMdlInstamt || 0),
        hndsetSalePrice: c.productType === 'UU' ? 0 : Number(p.hndsetSalePrice || 0),
        sprtTypeCd: c.productType === 'UU' ? '' : c.discountType || '', // c에서 참조
        dcAmt: c.productType === 'UU' ? 0 : Number(p.dcAmt || 0),
        maxApdSprt: c.productType === 'UU' ? 0 : Number(p.maxApdSprt || 0),
        addDcAmt: c.productType === 'UU' ? 0 : Number(p.addDcAmt || 0),
        prmtAmt: Number(p.prmtAmt || 0),
        enggMnthCnt:
          c.productType === 'UU'
            ? 0
            : Number(
                c.contractPeriod !== undefined &&
                  c.contractPeriod !== null &&
                  c.contractPeriod !== ''
                  ? c.contractPeriod
                  : 24,
              ),
        recycleYn: toYN(p.recycleYn),
        usimPriceTypeCd: p.simTypeCd === 'ESIM' ? 'B' : (p.usimPriceTypeCd || 'N'),
        usimPrice: Number(p.usimPrice || 0),
        usimPayMthdCd: p.simTypeCd === 'ESIM' ? '3' : (p.usimPayMthdCd || '1'),
        sesplsYn: toYN(p.sesplsYn),
        joinPriceTypeCd: p.joinPriceTypeCd || '',
        joinPayMthdCd: p.joinPayMthdCd || '',
        joinPrice: Number(p.joinPrice || 0),
        socCode: c.socCode || c.prodId || p.socCode || '', // 화면 바인딩 요금제 코드(c) 우선 참조
        socNm: c.socNm || c.prodNm || p.socNm || '', // 화면 바인딩 요금제 명칭(c) 우선 참조
        prodCtgId: c.prodCtgId || '', // 요금제 카테고리 ID 저장 추가
        prdtSctnCd: c.prdtSctnCd || '', // API prdtSctnCd -> c.prdtSctnCd (화면상 5G, LTE, 3G)
        dataType: c.dataType || '', // API dataType -> c.dataType (화면상 5G, LTE5G, LTE, 3G)
        socBaseChrgAmt: Number(p.socBaseChrgAmt || 0),

        // Bill Info (MSF_REQUEST_BILL_REQ)
        reqPayTypeCd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : p.reqPayTypeCd,
        reqBankCd: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.reqBankCd || ''
            : '',
        reqAccountNm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.othersPaymentYn === 'N' || p.othersPaymentYn === false
              ? c.cstmrNm
              : p.reqAccountNm || ''
            : '',
        reqAccountRrn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.othersPaymentYn === 'N' || p.othersPaymentYn === false
              ? cstmrBirth8
              : (p.reqAccountRrn || '').replace(/[^0-9]/g, '')
            : '',
        reqAccountRelTypeCd: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.reqAccountRelTypeCd || ''
            : '',
        reqAccountNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.reqAccountNo || ''
            : '',
        reqCardNm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.othersPaymentYn === 'N' || p.othersPaymentYn === false
              ? c.cstmrNm
              : p.reqCardNm || ''
            : '',
        reqCardRrn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.othersPaymentYn === 'N' || p.othersPaymentYn === false
              ? cstmrBirth8
              : (p.reqCardRrn || '').replace(/[^0-9]/g, '')
            : '',
        reqCardCompanyCd: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.reqCardCompanyCd || ''
            : '',
        reqCardNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.reqCardNo || ''
            : '',
        reqCardYy: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.reqCardYy || ''
            : '',
        reqCardMm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.reqCardMm || ''
            : '',
        reqWireTypeCd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : p.reqWireTypeCd || '',
        othersPaymentYn: ['HDN3', 'HCN3'].includes(c.joinType) ? 'N' : toYN(p.othersPaymentYn),
        othersPaymentTelFnNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.othersPaymentTelFnNo || ''
            : '',
        othersPaymentTelMnNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.othersPaymentTelMnNo || ''
            : '',
        othersPaymentTelRnNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.othersPaymentTelRnNo || ''
            : '',
        othersPaymentNm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.reqAccountNm || p.reqCardNm || p.othersPaymentNm || ''
            : '',
        othersPaymentRrn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? (p.reqAccountRrn || p.reqCardRrn || p.othersPaymentRrn || '').replace(/[^0-9]/g, '')
            : '',
        othersPaymentRelTypeCd: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.reqAccountRelTypeCd || p.cardRelation || p.othersPaymentRelTypeCd || ''
            : '',
        othersPaymentReqNm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.othersPaymentReqNm || ''
            : '',
        othersPaymentAgrYn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? 'N'
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? toYN(p.othersPaymentAgrYn)
            : 'N',
        prntsBillNo: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : p.combId || p.prntsBillNo || '',
        cstmrBillSendTypeCd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : p.cstmrBillSendTypeCd,
        isAutoAgree: ['HDN3', 'HCN3'].includes(c.joinType) ? 'N' : toYN(p.isAutoAgree),

        // Move Info (MSF_REQUEST_MOVE)
        moveCompanyCd: p.moveCompanyCd,
        moveMobileFnNo: p.moveMobileNo1,
        moveMobileMnNo: p.moveMobileNo2,
        moveMobileRnNo: p.moveMobileNo3,
        moveAuthTypeCd: p.moveAuthTypeCd,
        moveAuthNo: p.moveAuthNo,
        moveThismonthPayTypeCd: toYN(p.moveThismonthPayTypeCd),
        moveAllotmentSttusCd: Array.isArray(p.moveAllotmentSttusCd)
          ? p.moveAllotmentSttusCd[0] || 'AD'
          : p.moveAllotmentSttusCd || 'AD',
        moveRefundAgreeYn: Array.isArray(p.moveRefundAgreeYn)
          ? p.moveRefundAgreeYn[0] || 'N'
          : p.moveRefundAgreeYn || 'N',
        reqGuideYn: toYN(p.reqGuideYn),
        reqGuideFnNo: p.reqGuideFnNo,
        reqGuideRnNo: p.reqGuideRnNo,
        reqGuideMnNo: p.reqGuideMnNo,
        osstPayDate: p.osstPayDate,
        osstPayTypeCd: p.osstPayTypeCd,
        movePenalty: p.movePenalty,

        // Clauses
        clauseMoveCode: c.CLAUSE_MOVE_01 ? 'Y' : 'N',
        clauseSensiCoverageYn: toYN(c.clauseSensiCoverageYn),
        clausePriCollectYn: toYN(c.clausePriCollectYn),
        clausePriOfferYn: toYN(c.clausePriOfferYn),
        clauseEssCollectYn: toYN(c.clauseEssCollectYn),
        clausePriTrustYn: toYN(c.clausePriAllYn),
        clausePriAdYn: toYN(c.clausePriAllYn),
        clauseConfidenceYn: toYN(c.clausePriCollectYn) === 'Y' ? 'Y' : toYN(c.clauseConfidenceYn),
        clauseFathYn: toYN(c.clauseFathYn),
        nwBlckAgrmYn: toYN(c.nwBlckAgrmYn),
        appBlckAgrmYn: toYN(c.appBlckAgrmYn),
        blckAppDivCd: c.blckAppDivCd,
        soTrnsAgrmYn: toYN(c.soTrnsAgrmYn),
        clauseJehuYn: toYN(c.clauseJehuYn),
        clauseRentalModelCpYn: toYN(c.clauseRentalModelCpYn),
        clauseRentalModelCpPrYn: toYN(c.clauseRentalModelCpPrYn),
        clauseRentalServiceYn: toYN(c.clauseRentalServiceYn),
        clauseMpps35Yn: toYN(c.clauseMpps35Yn),
        clauseFinanceYn: toYN(c.clauseFinanceYn),
        clause5gCoverageYn: toYN(c.clause5gCoverageYn),
        personalInfoCollectAgreeYn: toYN(c.personalInfoCollectAgreeYn),
        othersTrnsAllAgreeYn: c.CLAUSE_SELECT_08 === true || c.CLAUSE_SELECT_08 === 'Y' ? 'Y' : 'N',
        othersTrnsAgreeYn: c.CLAUSE_SELECT_08 === true || c.CLAUSE_SELECT_08 === 'Y' ? 'Y' : 'N',
        clauseSensiCollectYn: toYN(c.clausePriCollectYn || c.clauseSensiCollectYn),
        clauseSensiOfferYn: toYN(c.clausePriCollectYn || c.clauseSensiOfferYn),
        clausePartnerOfferYn: toYN(c.clausePartnerOfferYn),
        othersTrnsKtAgreeYn: c.CLAUSE_SELECT_08 === true || c.CLAUSE_SELECT_08 === 'Y' ? 'Y' : 'N',
        othersAdReceiveAgreeYn:
          c.CLAUSE_SELECT_07 === true || c.CLAUSE_SELECT_07 === 'Y' ? 'Y' : 'N',
        ktCounselAgreeYn: toYN(c.ktCounselAgreeYn),
        combineSoloTypeYn: toYN(c.combineSoloTypeYn),
        combineSoloYn: toYN(c.combineSoloYn),
        personalLocationAgreeYn: toYN(c.personalLocationAgreeYn),
        indvLocaPrvAgreeYn: c.CLAUSE_SELECT_10 ? 'Y' : 'N',
        clauseInfo01: toYN(c.clauseInfo01),

        // Agreement / Status
        agreeCheck1: toYN(a.agreeCheck1),
        agreeCheck2: toYN(a.agreeCheck2),
        agreeCheck3: toYN(a.agreeCheck3),
        recYn: toYN(a.recYn),
        fileTypeCd: a.fileTypeCd,
        filePathNm: documentId.value || a.filePathNm || a.recFilePathNm || '',
        fileNm: originalfileName.value || a.recFileNm || '',
        scanId: documentId.value || a.scanId || '',
        msfRequestRecList: a.msfRequestRecList || [],
        eformsignFileData:
          rawEformsignFileData.value && rawEformsignFileData.value.length > 0
            ? rawEformsignFileData.value.map((item) => ({
                documentId: item.documentId,
                file: {
                  filePathName: item.file?.filePathName || '',
                },
              }))
            : [],
        recordFileData: rawRecordFileData.value
          ? {
              filePathName: rawRecordFileData.value.filePathName || '',
              filePath: rawRecordFileData.value.filePath || '',
              fileName: rawRecordFileData.value.fileName || '',
              rawFile: rawRecordFileData.value.rawFile || null,
            }
          : null,
        proSttusCd: c.proSttusCd || '99',
        sbscProCd: c.sbscProCd || '99',
        appFormYn: toYN(c.appFormYn),
        appFormXmlYn: toYN(c.appFormXmlYn),
        faxYn: toYN(c.faxYn),
        requestPreCheck: additionalPayload.requestPreCheck || 'N',
        ...additionalPayload,
      }

      const res = await post('/api/form/newchange/save', payload, { skipAlert: true })

      if (res && res.code === '0000') {
        if (res?.data?.resCode !== '0000') {
          showAlert(res?.data.resMessage)
          preChecked.value = false
        } else {
          // resCode === '0000' 인 성공적인 사전체크 완료 결과 판정
          const resData = res?.data?.resData
          if (resData?.preCheckCd) {
            if (resData.preCheckCd === 'S') {
              preChecked.value = true
            } else {
              // 'S' 가 아닌 경우(예: 'F') confirm을 띄워 사용자에게 강제 우회 여부를 묻는다
              const proceed = await showConfirmAsync(resData.preCheckMsg, '취소 시 사전체크 실패')
              if (proceed) {
                preChecked.value = true
              } else {
                preChecked.value = false
                customer.value.isSaved = false // 가입조건조회를 다시 진행할 수 있도록 화면 잠금 해제
                customer.value.preCheckYn = 'N'
                product.value.preCheckYn = 'N'

                // 약관 동의 전체 리셋 (1단계 고객동의약관 리셋)
                const clauseKeys = [
                  'clausePriCollectYn',
                  'clausePriOfferYn',
                  'clauseEssCollectYn',
                  'clausePriTrustYn',
                  'clausePriAdYn',
                  'clauseConfidenceYn',
                  'clauseFathYn',
                  'nwBlckAgrmYn',
                  'appBlckAgrmYn',
                  'soTrnsAgrmYn',
                  'moveRefundAgreeYn',
                  'clauseJehuYn',
                  'clauseMpps35Yn',
                  'clauseFinanceYn',
                  'clauseMoveCode',
                  'clauseSensiCoverageYn',
                  'clause5gCoverageYn',
                  'clausePartnerOfferYn',
                  'personalLocationAgreeYn',
                  'clauseInfo01',
                ]
                clauseKeys.forEach((key) => {
                  if (key in customer.value) customer.value[key] = 'N'
                })

                const clauseUIKeys = [
                  'CLAUSE_MOVE_01',
                  'CLAUSE_REQUIRED_02',
                  'CLAUSE_REQUIRED_01',
                  'CLAUSE_REQUIRED_03',
                  'CLAUSE_FATH_01',
                  'CLAUSE_FATH_02',
                  'CLAUSE_REQUIRED_06',
                  'CLAUSE_REQUIRED_07',
                  'CLAUSE_REQUIRED_5G',
                  'CLAUSE_PARTNER_02',
                  'CLAUSE_SELECT_03',
                  'CLAUSE_SELECT_01',
                  'CLAUSE_SELECT_08',
                  'CLAUSE_SELECT_04',
                  'CLAUSE_SELECT_06',
                  'CLAUSE_SELECT_07',
                  'CLAUSE_SELECT_10',
                  'CLAUSE_INFO_01',
                ]
                clauseUIKeys.forEach((key) => {
                  if (key in customer.value) customer.value[key] = false
                })

                // 약관 동의 리셋 (3단계 고객안내사항 동의 및 녹취 상태 리셋)
                if (agreement.value) {
                  agreement.value.agreeCheck1 = false
                  agreement.value.agreeCheck2 = false
                  agreement.value.agreeCheck3 = false
                  agreement.value.recYn = 'N'
                  agreement.value.scanId = ''
                  agreement.value.recFileNm = ''
                  agreement.value.recFilePathNm = ''
                  agreement.value.msfRequestRecList = []
                }

                // 수정 누른 것 같은 효과 (가입 관련 전체 인증 잠금 해제)
                if (authFlags.value) {
                  authFlags.value.autoAcct = false
                  authFlags.value.reqCardNo = false
                  authFlags.value.combId = false
                  authFlags.value.moveAuthTypeCd = false
                  authFlags.value.reqUsimSn = false
                  authFlags.value.imei = false
                  authFlags.value.esimImei = false
                }

                return false // 다음 단계 전환 방지를 위해 false 리턴
              }
            }
          }
        }

        // 1. 서버에서 발급된 requestKey 저장 (다음 단계 update를 위해 필수)
        const newKey =
          res?.data?.resData?.requestKey ||
          res?.data?.resData?.requestkey ||
          res?.data?.requestKey ||
          res?.requestKey ||
          res?.data?.requestkey ||
          res?.requestkey
        if (newKey) {
          applicationKey.value = String(newKey)
          draftApplicationKey.value = String(newKey)
        }

        const newResNo = res?.data?.resData?.resNo || res?.data?.resNo || res?.resNo
        if (newResNo) {
          resNo.value = String(newResNo)
        }

        // 2. 저장이 성공하면 현재 값(Current)을 임시저장 값(Draft) 및 기본 값(Initial)으로 즉시 동기화
        draftCustomer.value = cloneDeep(customer.value)
        draftProduct.value = cloneDeep(product.value)
        draftAgreement.value = cloneDeep(agreement.value)

        initialCustomer.value = cloneDeep(customer.value)
        initialProduct.value = cloneDeep(product.value)
        initialAgreement.value = cloneDeep(agreement.value)

        // 고객단계(Step 1) 저장이 성공한 경우에만 isSaved 플래그 확정
        if (String(step) === '1') {
          customer.value.isSaved = true
        }

        return true
      } else {
        preChecked.value = false
        console.error('Draft save failed with response:', res)
        return false
      }
    } catch (e) {
      preChecked.value = false
      console.error('Draft save failed with exception:', e)
      return false
    }
  }

  const apiCompleteApplication = async () => {
    try {
      const c = cloneDeep(customer.value)
      const p = cloneDeep(product.value)
      const a = cloneDeep(agreement.value)

      const toYN = (val) => {
        if (val === true || val === 'Y') return 'Y'
        if (val === false || val === 'N') return 'N'
        return ''
      }

      // 가입자 생년월일 8자리 추출
      const customerSsnForBirth = (
        (c.cstmrNativeRrn1 || '') + (c.cstmrNativeRrn2 || '') ||
        (c.cstmrForeignerRrn1 || '') + (c.cstmrForeignerRrn2 || '') ||
        (c.cstmrJuridicalRrn1 || '') + (c.cstmrJuridicalRrn2 || '') ||
        ''
      ).replace(/[^0-9]/g, '')

      let cstmrBirth8 = ''
      if (customerSsnForBirth.length >= 7) {
        cstmrBirth8 = extractYYYYMMDDRrn(customerSsnForBirth) || ''
      }
      if (!cstmrBirth8) {
        cstmrBirth8 = (c.cstmrNativeBirth || c.realUserBirthDate || '').replace(/[^0-9]/g, '')
      }

      const termsKeys = [
        'clauseMoveCode',
        'clausePriCollectYn',
        'clausePriOfferYn',
        'clauseEssCollectYn',
        'clausePriTrustYn',
        'clausePriAdYn',
        'clauseConfidenceYn',
        'clauseFathYn',
        'clauseSensiCoverageYn',
        'nwBlckAgrmYn',
        'appBlckAgrmYn',
        'soTrnsAgrmYn',
        'clauseJehuYn',
        'clauseRentalModelCpYn',
        'clauseRentalModelCpPrYn',
        'clauseRentalServiceYn',
        'clauseMpps35Yn',
        'clauseFinanceYn',
        'clause5gCoverageYn',
        'personalInfoCollectAgreeYn',
        'othersTrnsAgreeYn',
        'clauseSensiCollectYn',
        'clauseSensiOfferYn',
        'clausePartnerOfferYn',
        'othersTrnsKtAgreeYn',
        'othersAdReceiveAgreeYn',
        'ktCounselAgreeYn',
        'combineSoloTypeYn',
        'combineSoloYn',
        'moveRefundAgreeYn',
        'isAutoAgree',
        'combAgree',
        'moveThismonthPayTypeCd',
        'personalLocationAgreeYn',
        'clauseInfo01',
      ]

      // 약관 및 체크박스 YN 변환 수행
      termsKeys.forEach((key) => {
        if (Object.prototype.hasOwnProperty.call(c, key)) c[key] = toYN(c[key])
        if (Object.prototype.hasOwnProperty.call(p, key)) p[key] = toYN(p[key])
        if (Object.prototype.hasOwnProperty.call(a, key)) a[key] = toYN(a[key])
      })

      // 1. 페이로드 생성 (apiSaveDraft와 동일한 로직 적용)
      const payload = {
        requestKey: applicationKey.value || null,
        resNo: customer.value.resNo || resNo.value || null,
        tmpStepCd: '3', // 완료 단계
        parentScanId: parentScanId.value || stepStore.parentScanId || '',

        // 공통 정보
        managerCd: c.managerCd,
        managerNm: c.managerNm,
        agentCd: c.agentCd,
        agentNm: c.agentNm,
        shopCd: c.shopCd,
        shopNm: c.shopNm,
        soCd: p.soCd || 'M',
        reqBuyTypeCd: c.productType,
        openTypeCd: c.openTypeCd,
        operTypeCd: c.joinType,
        openNo: (c.openNo || '').replace(/-/g, ''),
        cstmrTypeCd: c.cstmrTypeCd,
        identityCertTypeCd: c.identityCertTypeCd,
        identityTypeCd: c.identityTypeCd,
        identityIssuDate: (c.identityIssuDate || '').replace(/[^0-9]/g, ''),
        cstmrPrivateBizNoIssuDate: (c.cstmrPrivateBizNoIssuDate || '').replace(/[^0-9]/g, ''),
        cstmrJuridicalBizNoIssuDate: (c.cstmrJuridicalBizNoIssuDate || '').replace(/[^0-9]/g, ''),
        identityIssuRegion: c.identityIssuRegion,
        selfIssuNo: c.selfIssuNo,
        driveLicnsNo: c.driveLicnsNo,
        contractNum: c.contractNum,
        formType: c.formType || 'NEWCHANGE',
        fathTrgYn: toYN(c.fathTrgYn),
        fathTrgIdentityCertTypeCd: c.fathTrgIdentityCertTypeCd,
        fathTransacId:
          c.identityCertTypeCd === 'S' && !c.fathTransacId ? 'MIS00001234' : c.fathTransacId || '',
        fathCmpltNtfyDate: c.fathCmpltNtfyDate,
        fathTelNo: c.fathTelNo,
        fathMobileFnNo: c.fathMobileFnNo,
        fathMobileMnNo: c.fathMobileMnNo,
        fathMobileRnNo: c.fathMobileRnNo,
        authInfo: c.authInfo,
        canBulkCorporateOpenYn: c.canBulkCorporateOpenYn || 'N',
        bulkActivationCnt: Number(c.bulkActivationCnt || 1),

        // Product Info
        prodId:
          c.productType === 'UU' || (c.handsetProdId && String(c.handsetProdId).startsWith('K'))
            ? ''
            : c.handsetProdId || '', // 내부단말기ID (숫자)
        prodNm: c.productType === 'UU' ? '' : c.deviceModelNm || '',
        reqPhoneSn: c.productType === 'UU' ? '' : p.serialNumber,
        reqModelNm: c.productType === 'UU' ? '' : c.reqModelNm || '', // 모델 코드 (SM-...)
        usimKindsCd: p.simTypeCd === 'ESIM' ? '09' : p.hasSim === false ? '02' : '06',
        reqUsimSn: p.simTypeCd === 'ESIM' ? '' : p.reqUsimSn,
        volumeMobileNoQnty:
          c.canBulkCorporateOpenYn === 'Y' && c.cstmrTypeCd === 'JP' && c.joinType === 'NAC3'
            ? Number(c.bulkActivationCnt || 1)
            : null,
        volumeRepMobileNoYn:
          c.canBulkCorporateOpenYn === 'Y' && c.cstmrTypeCd === 'JP' && c.joinType === 'NAC3' && Number(c.bulkActivationCnt || 0) > 0
            ? 'Y'
            : 'N',
        eid: p.simTypeCd === 'ESIM' ? p.eid : '',
        imei1: p.simTypeCd === 'ESIM' ? p.imei1 : '',
        imei2: p.simTypeCd === 'ESIM' ? p.imei2 : '',
        reqWantFnNo: c.joinType === 'MNP3' ? '' : p.reqWantFnNo,
        reqWantMnNo: c.joinType === 'MNP3' ? '' : p.reqWantMnNo,
        reqWantRnNo: p.reqWantRnNo,
        reqAdditionListNm:
          p.additionList && p.additionList.length > 0
            ? p.additionList
                .map((vas) => vas.additionNm || vas.rateNm || vas.prodNm || '')
                .filter(Boolean)
                .join(',')
            : '',
        insrProdCd: p.recCat2 || p.insrProdCd || '',
        clauseInsuranceYn: toYN(p.clauseInsuranceYn),
        // 부가서비스 목록 규격화 (additionId, additionNm, rantal, additionKey)
        additionList: (p.additionList || []).map((vas) => ({
          additionId: vas.additionId || vas.rateCd || vas.prodId,
          additionNm: vas.additionNm || vas.rateNm || vas.prodNm,
          rantal: Number(vas.rantal || vas.baseAmt || 0),
          additionKey: vas.additionKey || '',
        })),
        reqAdditionPrice: Number(p.reqAdditionPrice || 0),
        phonePaymentYn: p.phonePaymentYn || 'N',
        memo: p.memo || '',

        // Customer Info
        cstmrNm: (c.cstmrNm || c.userName || c.realUserName || '').trim(),
        userName: (c.userName || c.cstmrNm || c.realUserName || '').trim(),
        cstmrNativeRrn:
          (c.cstmrNativeRrn1 || '') + (c.cstmrNativeRrn2 || '') ||
          (c.cstmrForeignerRrn1 || '') + (c.cstmrForeignerRrn2 || '') ||
          (c.cstmrJuridicalRrn1 || '') + (c.cstmrJuridicalRrn2 || '') ||
          '',
        cstmrNativeBirth: (
          (['NA', 'NM'].includes(c.cstmrTypeCd) && c.cstmrNativeRrn1 && c.cstmrNativeRrn2
            ? (['3', '4', '7', '8'].includes(c.cstmrNativeRrn2.charAt(0)) ? '20' : '19') +
              c.cstmrNativeRrn1
            : '') ||
          (['NA', 'NM'].includes(c.cstmrTypeCd) ? c.cstmrNativeBirth || c.realUserBirthDate : '') ||
          ''
        ).replace(/[^0-9]/g, ''),
        cstmrNativeGenderCd:
          ['NA', 'NM'].includes(c.cstmrTypeCd) && c.cstmrNativeRrn2
            ? Number(c.cstmrNativeRrn2.charAt(0)) % 2 === 1
              ? 'M'
              : 'F'
            : '',
        cstmrPrivateCname: c.cstmrPrivateCname,
        cstmrPrivateBizNo:
          c.cstmrPrivateBizNo ||
          (c.cstmrJuridicalBizNo1 || '') +
            (c.cstmrJuridicalBizNo2 || '') +
            (c.cstmrJuridicalBizNo3 || '') ||
          '',
        cstmrForeignerRrn: (c.cstmrForeignerRrn1 || '') + (c.cstmrForeignerRrn2 || ''),
        cstmrForeignerBirth:
          c.cstmrForeignerBirth ||
          (c.userBirthDate || '').replace(/[^0-9]/g, '') ||
          (c.cstmrForeignerRrn1 && c.cstmrForeignerRrn2
            ? extractYYYYMMDDRrn(c.cstmrForeignerRrn1 + c.cstmrForeignerRrn2)
            : '') ||
          '',
        cstmrForeignerGenderCd:
          c.cstmrForeignerGenderCd ||
          (['FN', 'FM'].includes(c.cstmrTypeCd) && c.cstmrForeignerRrn2
            ? Number(c.cstmrForeignerRrn2.charAt(0)) % 2 === 1
              ? 'M'
              : 'F'
            : ''),
        cstmrForeignerPn: c.cstmrForeignerPn || '',
        cstmrForeignerCountryCd: c.cstmrForeignerCountryCd || c.country || '',
        cstmrForeignerNation: c.cstmrForeignerNation || c.country || '',
        cstmrForeignerVisaNo: c.cstmrForeignerVisaNo || c.visaType || '',
        cstmrForeignerVdateStartDate: c.cstmrForeignerVdateStartDate,
        cstmrForeignerVdateEndDate: c.cstmrForeignerVdateEndDate,
        cstmrJuridicalCname:
          ['JP', 'GO'].includes(c.cstmrTypeCd) && ['VMY', 'VDP'].includes(c.cstmrVisitTypeCd)
            ? c.cstmrJuridicalCname || c.cstmrNm || ''
            : '',
        cstmrJuridicalRrn: (c.cstmrJuridicalRrn1 || '') + (c.cstmrJuridicalRrn2 || ''),
        cstmrJuridicalBizNo:
          (c.cstmrJuridicalBizNo1 || '') +
          (c.cstmrJuridicalBizNo2 || '') +
          (c.cstmrJuridicalBizNo3 || ''),
        cstmrJuridicalRepNm: c.cstmrJuridicalRepNm,
        upjnCd: c.upjnCd,
        bcuSbst: c.bcuSbst,
        cstmrJuridicalUserNm: c.cstmrJuridicalUserNm || c.realUserName || '',
        cstmrJuridicalBirth: (c.cstmrJuridicalBirth || c.realUserBirthDate || '').replace(
          /[^0-9]/g,
          '',
        ),
        cstmrVisitTypeCd: c.cstmrVisitTypeCd,
        msfRequestDocList: c.msfRequestDocList || [],
        cstmrMobileFnNo: c.mobileNo1,
        cstmrMobileMnNo: c.mobileNo2,
        cstmrMobileRnNo: c.mobileNo3,
        cstmrMobileNo: (c.mobileNo1 || '') + (c.mobileNo2 || '') + (c.mobileNo3 || ''),
        cstmrTelFnNo: c.telNo1,
        cstmrTelMnNo: c.telNo2,
        cstmrTelRnNo: c.telNo3,
        cstmrTelNo: (c.telNo1 || '') + (c.telNo2 || '') + (c.telNo3 || ''),
        cstmrAdr: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : c.address,
        cstmrAdrDtl: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : c.detailAddress,
        cstmrZipcd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : c.zipNo,
        cstmrAdrBjd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : c.cstmrAdrBjd,
        cstmrEmailAdr: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : c.emailAddr1 && c.emailAddr2
            ? c.emailAddr1 + '@' + c.emailAddr2
            : '',
        cstmrEmailReceiveYn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? 'N'
          : toYN(c.cstmrEmailReceiveYn),
        country: c.country,
        stayPeriod: (c.stayPeriod || '').replace(/[^0-9]/g, ''),
        visaType: c.visaType,

        // Agent Info
        // Agent Info - 법인/공공기관(JP/GO) 위임대리인 vs 미성년자 법정대리인 분기
        minorAgentNm: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentNm,
        minorAgentRrn: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : (c.repRegistrationNo1 || '') + (c.repRegistrationNo2 || '') ||
            (c.repForeignerNo1 || '') + (c.repForeignerNo2 || '') ||
            c.minorAgentRrn,
        minorAgentBirth: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : extractYYYYMMDDRrn(
              (c.repRegistrationNo1 || c.repForeignerNo1 || '') +
                (c.repRegistrationNo2 || c.repForeignerNo2 || ''),
            ) ||
            c.minorAgentBirth ||
            (c.repBirthDate || '').replace(/[^0-9]/g, '') ||
            '',
        minorAgentGenderCd: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : c.agentGender ||
            c.minorAgentGenderCd ||
            (c.repRegistrationNo2 || c.repForeignerNo2
              ? Number((c.repRegistrationNo2 || c.repForeignerNo2).charAt(0)) % 2 === 1
                ? 'M'
                : 'F'
              : ''),
        minorAgentRelTypeCd: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentRelTypeCd,
        minorAgentTelFnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentTelFnNo,
        minorAgentTelMnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentTelMnNo,
        minorAgentTelRnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentTelRnNo,
        minorAgentAgrmYn: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : toYN(c.minorAgentAgrmYn),
        minorAgentSelfInqryAgrmYn: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : toYN(c.minorAgentSelfInqryAgrmYn),
        minorAgentSelfCertTypeCd: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? ''
          : c.minorAgentSelfCertTypeCd || '01',
        minorAgentCiInfo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? '' : c.minorAgentCiInfo,
        // 법인/공공기관 위임대리인 필드 - JP/GO일 때 화면의 minorAgent 바인딩값을 jrdclAgent로 세팅
        jrdclAgentNm: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? c.minorAgentNm || c.jrdclAgentNm || ''
          : '',
        jrdclAgentRrn: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? (c.agentBirthDate || '').replace(/[^0-9]/g, '').length === 8
            ? (c.agentBirthDate || '').replace(/[^0-9]/g, '') + (c.agentGender === 'F' ? '2' : '1')
            : (c.agentBirthDate || '').replace(/[^0-9]/g, '')
          : '',
        jrdclAgentRelTypeCd: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.minorAgentRelTypeCd : '',
        jrdclAgentTelFnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.minorAgentTelFnNo : '',
        jrdclAgentTelMnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.minorAgentTelMnNo : '',
        jrdclAgentTelRnNo: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.minorAgentTelRnNo : '',
        agentBirthDate: ['JP', 'GO'].includes(c.cstmrTypeCd)
          ? (c.agentBirthDate || '').replace(/[^0-9]/g, '')
          : '',
        agentGender: ['JP', 'GO'].includes(c.cstmrTypeCd) ? c.agentGender || '' : '',

        // Sale Info
        modelId: c.productType === 'UU' ? '' : c.modelId, // 대표단말기ID (K...)
        modelMonthly: c.productType === 'UU' ? '0' : c.installmentMonth,
        enggMnthCnt:
          c.productType === 'UU'
            ? 0
            : Number(
                c.contractPeriod !== undefined &&
                  c.contractPeriod !== null &&
                  c.contractPeriod !== ''
                  ? c.contractPeriod
                  : 24,
              ),
        sprtTypeCd: c.productType === 'UU' ? '' : c.discountType || '', // c에서 참조
        usimPriceTypeCd: p.simTypeCd === 'ESIM' ? 'B' : (p.usimPriceTypeCd || 'N'),
        usimPrice: Number(p.usimPrice || 0),
        usimPayMthdCd: p.simTypeCd === 'ESIM' ? '3' : (p.usimPayMthdCd || '1'),
        modelSalePolicyCd: c.productType === 'UU' ? '' : c.modelSalePolicyCd || '', // c에서 참조
        joinPriceTypeCd: p.joinPriceTypeCd || '',
        joinPayMthdCd: p.joinPayMthdCd || '',
        joinPrice: Number(p.joinPrice || 0),
        socCode: c.socCode || c.prodId || p.socCode || '', // 화면 바인딩 요금제 코드(c) 우선 참조
        socNm: c.socNm || c.prodNm || p.socNm || '', // 화면 바인딩 요금제 명칭(c) 우선 참조
        prdtSctnCd: c.prdtSctnCd || '', // API prdtSctnCd -> c.prdtSctnCd (화면상 5G, LTE, 3G)
        dataType: c.dataType || '', // API dataType -> c.dataType (화면상 5G, LTE5G, LTE, 3G)
        socBaseChrgAmt: Number(c.socBaseChrgAmt || p.socBaseChrgAmt || 0),

        // Bill Info
        reqPayTypeCd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : p.reqPayTypeCd,
        reqBankCd: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.reqBankCd || ''
            : '',
        reqAccountNm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.othersPaymentYn === 'N' || p.othersPaymentYn === false
              ? c.cstmrNm
              : p.reqAccountNm || ''
            : '',
        reqAccountRrn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.othersPaymentYn === 'N' || p.othersPaymentYn === false
              ? cstmrBirth8
              : (p.reqAccountRrn || '').replace(/[^0-9]/g, '')
            : '',
        reqAccountRelTypeCd: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.reqAccountRelTypeCd || ''
            : '',
        reqAccountNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : ['D', 'AA'].includes(p.reqPayTypeCd)
            ? p.reqAccountNo || ''
            : '',
        reqCardNm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.othersPaymentYn === 'N' || p.othersPaymentYn === false
              ? c.cstmrNm
              : p.reqCardNm || ''
            : '',
        reqCardRrn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.othersPaymentYn === 'N' || p.othersPaymentYn === false
              ? cstmrBirth8
              : (p.reqCardRrn || '').replace(/[^0-9]/g, '')
            : '',
        reqCardCompanyCd: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.reqCardCompanyCd || ''
            : '',
        reqCardNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.reqCardNo || ''
            : '',
        reqCardYy: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.reqCardYy || ''
            : '',
        reqCardMm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.reqPayTypeCd === 'C'
            ? p.reqCardMm || ''
            : '',
        reqWireTypeCd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : p.reqWireTypeCd || '',
        othersPaymentYn: ['HDN3', 'HCN3'].includes(c.joinType) ? 'N' : toYN(p.othersPaymentYn),
        othersPaymentTelFnNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.othersPaymentTelFnNo || ''
            : '',
        othersPaymentTelMnNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.othersPaymentTelMnNo || ''
            : '',
        othersPaymentTelRnNo: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.othersPaymentTelRnNo || ''
            : '',
        othersPaymentNm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.reqAccountNm || p.reqCardNm || p.othersPaymentNm || ''
            : '',
        othersPaymentRrn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? (p.reqAccountRrn || p.reqCardRrn || p.othersPaymentRrn || '').replace(/[^0-9]/g, '')
            : '',
        othersPaymentRelTypeCd: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.reqAccountRelTypeCd || p.cardRelation || p.othersPaymentRelTypeCd || ''
            : '',
        othersPaymentReqNm: ['HDN3', 'HCN3'].includes(c.joinType)
          ? ''
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? p.othersPaymentReqNm || ''
            : '',
        othersPaymentAgrYn: ['HDN3', 'HCN3'].includes(c.joinType)
          ? 'N'
          : p.othersPaymentYn === 'Y' || p.othersPaymentYn === true
            ? toYN(p.othersPaymentAgrYn)
            : 'N',
        prntsBillNo: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : p.combId || p.prntsBillNo || '',
        cstmrBillSendTypeCd: ['HDN3', 'HCN3'].includes(c.joinType) ? '' : p.cstmrBillSendTypeCd,
        isAutoAgree: ['HDN3', 'HCN3'].includes(c.joinType) ? 'N' : toYN(p.isAutoAgree),

        // Move Info
        moveCompanyCd: p.moveCompanyCd,
        moveMobileFnNo: p.moveMobileNo1,
        moveMobileMnNo: p.moveMobileNo2,
        moveMobileRnNo: p.moveMobileNo3,
        moveAuthTypeCd: p.moveAuthTypeCd,
        moveAuthNo: p.moveAuthNo,
        moveThismonthPayTypeCd: toYN(p.moveThismonthPayTypeCd),
        moveAllotmentSttusCd: Array.isArray(p.moveAllotmentSttusCd)
          ? p.moveAllotmentSttusCd[0] || 'AD'
          : p.moveAllotmentSttusCd || 'AD',
        moveRefundAgreeYn: Array.isArray(p.moveRefundAgreeYn)
          ? p.moveRefundAgreeYn[0] || 'N'
          : p.moveRefundAgreeYn || 'N',

        // Clauses
        clauseMoveCode: c.CLAUSE_MOVE_01 ? 'Y' : 'N',
        clauseSensiCoverageYn: toYN(c.clauseSensiCoverageYn),
        clausePriCollectYn: toYN(c.clausePriCollectYn),
        clausePriOfferYn: toYN(c.clausePriOfferYn),
        clauseEssCollectYn: toYN(c.clauseEssCollectYn),
        clausePriTrustYn: toYN(c.clausePriAllYn),
        clausePriAdYn: toYN(c.clausePriAllYn),
        clauseConfidenceYn: toYN(c.clausePriCollectYn) === 'Y' ? 'Y' : toYN(c.clauseConfidenceYn),
        clauseFathYn: toYN(c.clauseFathYn),
        nwBlckAgrmYn: toYN(c.nwBlckAgrmYn),
        appBlckAgrmYn: toYN(c.appBlckAgrmYn),
        soTrnsAgrmYn: toYN(c.soTrnsAgrmYn),
        clauseJehuYn: toYN(c.clauseJehuYn),
        clauseRentalModelCpYn: toYN(c.clauseRentalModelCpYn),
        clauseRentalModelCpPrYn: toYN(c.clauseRentalModelCpPrYn),
        clauseRentalServiceYn: toYN(c.clauseRentalServiceYn),
        clauseMpps35Yn: toYN(c.clauseMpps35Yn),
        clauseFinanceYn: toYN(c.clauseFinanceYn),
        clause5gCoverageYn: toYN(c.clause5gCoverageYn),
        personalInfoCollectAgreeYn: toYN(c.personalInfoCollectAgreeYn),
        othersTrnsAllAgreeYn: c.CLAUSE_SELECT_08 === true || c.CLAUSE_SELECT_08 === 'Y' ? 'Y' : 'N',
        othersTrnsAgreeYn: c.CLAUSE_SELECT_08 === true || c.CLAUSE_SELECT_08 === 'Y' ? 'Y' : 'N',
        clauseSensiCollectYn: toYN(c.clausePriCollectYn || c.clauseSensiCollectYn),
        clauseSensiOfferYn: toYN(c.clausePriCollectYn || c.clauseSensiOfferYn),
        clausePartnerOfferYn: toYN(c.clausePartnerOfferYn),
        othersTrnsKtAgreeYn: c.CLAUSE_SELECT_08 === true || c.CLAUSE_SELECT_08 === 'Y' ? 'Y' : 'N',
        othersAdReceiveAgreeYn:
          c.CLAUSE_SELECT_07 === true || c.CLAUSE_SELECT_07 === 'Y' ? 'Y' : 'N',
        ktCounselAgreeYn: toYN(c.ktCounselAgreeYn),
        combineSoloTypeYn: toYN(c.combineSoloTypeYn),
        combineSoloYn: toYN(c.combineSoloYn),
        personalLocationAgreeYn: toYN(c.personalLocationAgreeYn),
        indvLocaPrvAgreeYn: c.CLAUSE_SELECT_10 ? 'Y' : 'N',
        clauseInfo01: toYN(c.clauseInfo01),

        // Agreement / Status
        agreeCheck1: toYN(a.agreeCheck1),
        agreeCheck2: toYN(a.agreeCheck2),
        agreeCheck3: toYN(a.agreeCheck3),
        recYn: toYN(a.recYn),
        fileNm: originalfileName.value || a.recFileNm || '',
        filePathNm: documentId.value || a.filePathNm || a.recFilePathNm || '',
        scanId: documentId.value || a.scanId || '',
        msfRequestRecList: a.msfRequestRecList || [],
        eformsignFileData:
          rawEformsignFileData.value && rawEformsignFileData.value.length > 0
            ? rawEformsignFileData.value.map((item) => ({
                documentId: item.documentId,
                file: {
                  filePathName: item.file?.filePathName || '',
                },
              }))
            : [],
        recordFileData: rawRecordFileData.value
          ? {
              filePathName: rawRecordFileData.value.filePathName || '',
              filePath: rawRecordFileData.value.filePath || '',
              fileName: rawRecordFileData.value.fileName || '',
              rawFile: rawRecordFileData.value.rawFile || null,
            }
          : null,
        proSttusCd: '01', // 작성완료 상태
        sbscProCd: '01',
      }

      const res = await post('/api/form/newchange/complete', payload, { skipAlert: true })
      if (res && res.code === '0000') {
        // 사전체크 결과값 판정 (res.data.preCheckResultCd가 'S'가 아니면 사전체크 실패 처리)
        const preCheckResultCd = res.data?.resData?.preCheckResultCd
        const preCheckResultMsg = res.data?.resData?.preCheckResultMsg

        if (preCheckResultCd && preCheckResultCd !== 'S') {
          const confirmMsg = `${preCheckResultMsg || '사전체크에 실패했습니다.'}\n\n그래도 가입 신청 등록을 계속 진행하시겠습니까?`
          const proceed = await showConfirmAsync(confirmMsg, '사전체크 결과 안내')

          if (proceed) {
            // 확인 시: 사전체크 오류를 무시하고 그대로 통과 처리 (아무것도 리셋하지 않음)
          } else {
            // 취소 시: 정보 수정 모드로 전환
            customer.value.isSaved = false // 고객단계 잠금 해제
            customer.value.preCheckYn = 'N'
            product.value.preCheckYn = 'N'
            preChecked.value = false

            // 가입 유형별 전수 수정 가능 복구 처리
            const joinType = customer.value.joinType

            // 1. 신규개통 (NAC3) -> 희망번호 예약 잠금 해제
            if (joinType === 'NAC3') {
              if (authFlags.value) {
                authFlags.value.reserveNo = false
              }
            }

            // 2. 번호이동 (MNP3) -> 번호이동 사전동의 잠금 해제
            if (joinType === 'MNP3') {
              if (authFlags.value) {
                authFlags.value.moveAuthTypeCd = false
              }
            }

            // 신청서 확인에서 수정을 누른 효과 (인증 해제 및 녹취/서명 초기화)
            if (authFlags.value) {
              authFlags.value.autoAcct = false
              authFlags.value.reqCardNo = false
              authFlags.value.combId = false
              authFlags.value.moveAuthTypeCd = false
              authFlags.value.reqUsimSn = false
              authFlags.value.imei = false
              authFlags.value.esimImei = false
            }
            agreement.value.recYn = 'N'
            agreement.value.scanId = ''
            agreement.value.recFileNm = ''
            agreement.value.recFilePathNm = ''
            agreement.value.msfRequestRecList = []

            showAlert(`사전체크가 실패했습니다.\n[${preCheckResultMsg || ''}]`)
            return false
          }
        }

        // 성공 시 응답 데이터 적재
        if (res.data) {
          documentId.value = res.data.documentId || ''
          originalfileName.value = res.data.fileName || ''
        }
        // 성공 시 requestKey 업데이트 (필요 시)
        const newKey =
          res?.data?.resData?.requestKey ||
          res?.data?.resData?.requestkey ||
          res?.data?.requestKey ||
          res?.requestKey ||
          res?.data?.requestkey ||
          res?.requestkey
        if (newKey) {
          applicationKey.value = String(newKey)
          draftApplicationKey.value = String(newKey)
        }
        const newResNo = res?.data?.resData?.resNo || res?.data?.resNo || res?.resNo
        if (newResNo) {
          resNo.value = String(newResNo)
        }
        return true
      } else {
        console.error('Final completion failed:', res)
        return false
      }
    } catch (e) {
      console.error('Completion failed with exception:', e)
      return false
    }
  }

  const apiCheckMnpAgreeResult = async () => {
    const c = customer.value
    const p = product.value

    const payload = {
      requestKey: applicationKey.value,
      slsCmpnCd: c.cntpntShopCd || '',
      agentCd: c.agentCd || '',
      npTlphNo: (p.moveMobileNo1 || '') + (p.moveMobileNo2 || '') + (p.moveMobileNo3 || ''),
      bchngNpCommCmpnCd: p.moveCompanyCd,
      cstmrTypeCd: c.cstmrTypeCd,
      custIdntNoIndCd: ['JP', 'GO'].includes(c.cstmrTypeCd)
        ? '02'
        : ['FN', 'FM'].includes(c.cstmrTypeCd)
          ? '05'
          : '01',
      custIdntNo:
        (c.cstmrNativeRrn1 || '') + (c.cstmrNativeRrn2 || '') ||
        (c.cstmrForeignerRrn1 || '') + (c.cstmrForeignerRrn2 || '') ||
        (c.cstmrJuridicalBizNo1 || '') +
          (c.cstmrJuridicalBizNo2 || '') +
          (c.cstmrJuridicalBizNo3 || '') ||
        c.cstmrJuridicalBizNo ||
        c.cstmrPrivateBizNo ||
        '',
      custNm: c.cstmrNm,
      crprNo: (c.cstmrJuridicalRrn1 || '') + (c.cstmrJuridicalRrn2 || '') || '',
      indvBizrYn:
        ['NA', 'NM', 'FN', 'FM'].includes(c.cstmrTypeCd) &&
        ((c.cstmrJuridicalBizNo1 && c.cstmrJuridicalBizNo2 && c.cstmrJuridicalBizNo3) ||
          c.cstmrJuridicalBizNo ||
          c.cstmrPrivateBizNo)
          ? 'Y'
          : 'N',
    }

    try {
      const res = await post('/api/form/portnumber/precheck/result', payload, { skipAlert: true })
      if (res && res.code === '0000' && res.data?.resCode === '0000') {
        if (authFlags.value) {
          authFlags.value.moveAuthTypeCd = true
        }
        preChecked.value = true
        return true
      }
      return {
        success: false,
        message:
          res?.data?.resMessage || res?.message || '번호이동 사전동의 결과조회에 실패했습니다.',
      }
    } catch (error) {
      console.error('Check mnp agree result api error:', error)
      return {
        success: false,
        message: '번호이동 사전동의 결과조회 통신 오류가 발생했습니다.',
      }
    }
  }

  const apiLoadDraft = async (key) => {
    isDraftLoading.value = true
    isDraftLoaded.value = true
    try {
      const res = await apiFetchFormData(key)
      const data = res.draft || res.data?.draft || res
      if (!data) return false

      // requestKey 세팅
      applicationKey.value = String(key)

      // Customer mapping (평면 구조를 UI 스토어 구조로 변환)
      const c = customer.value
      c.productType = data.reqBuyTypeCd || 'MM'
      c.joinType = data.operTypeCd || data.joinType || ''
      c.cstmrTypeCd = data.cstmrTypeCd || 'NA'
      c.identityCertTypeCd = data.identityCertTypeCd || 'K'
      c.identityTypeCd = data.identityTypeCd || ''
      c.identityIssuRegion = data.identityIssuRegion || ''
      c.identityIssuDate = data.identityIssuDate || ''
      c.cstmrPrivateBizNoIssuDate = data.cstmrPrivateBizNoIssuDate || ''
      c.cstmrJuridicalBizNoIssuDate = data.cstmrJuridicalBizNoIssuDate || ''
      c.selfIssuNo = data.selfIssuNo || ''
      c.driveLicnsNo = data.driveLicnsNo || ''
      c.contractNum = data.contractNum || ''

      if (data.identityCertTypeCd === 'S') {
        c.isVerified = true // 인증예외/스캐너인 경우 인증된 상태로 간주
        authFlags.value.identityCertTypeCd = true
      }

      c.cstmrNm = data.cstmrNm || data.userName || ''

      // 기기변경의 경우 이전 전화번호 복원
      const devTel = data.openNo || data.cstmrMobileNo || ''
      if (devTel && devTel.length >= 9) {
        const cleanDevTel = devTel.replace(/\D/g, '')
        c.deviceChgTel1 = cleanDevTel.substring(0, 3)
        c.deviceChgTel2 =
          cleanDevTel.length === 11 ? cleanDevTel.substring(3, 7) : cleanDevTel.substring(3, 6)
        c.deviceChgTel3 = cleanDevTel.substring(cleanDevTel.length - 4)
      }

      // cstmrJuridicalCname은 법인/공공기관만 세팅하고 그 외에는 null 처리
      c.cstmrJuridicalCname =
        ['JP', 'GO'].includes(data.cstmrTypeCd) && ['VMY', 'VDP'].includes(data.cstmrVisitTypeCd)
          ? data.cstmrJuridicalCname || data.cstmrNm || ''
          : null

      // 법인/공공기관 주민등록번호 및 실사용자 정보 복원
      if (data.cstmrJuridicalRrn && data.cstmrJuridicalRrn.length >= 6) {
        c.cstmrJuridicalRrn1 = data.cstmrJuridicalRrn.substring(0, 6)
        c.cstmrJuridicalRrn2 = data.cstmrJuridicalRrn.substring(6)
      }
      c.cstmrJuridicalUserNm = data.cstmrJuridicalUserNm || data.realUserName || ''
      c.cstmrJuridicalBirth = data.cstmrJuridicalBirth || data.realUserBirthDate || ''
      c.realUserName = data.realUserName || data.cstmrJuridicalUserNm || ''
      c.realUserBirthDate = data.realUserBirthDate || data.cstmrJuridicalBirth || ''

      // 내국인(일반 개인 및 미성년자) 주민등록번호 복원
      if (
        ['NA', 'NM'].includes(data.cstmrTypeCd) &&
        data.cstmrNativeRrn &&
        data.cstmrNativeRrn.length >= 6
      ) {
        c.cstmrNativeRrn1 = data.cstmrNativeRrn.substring(0, 6)
        c.cstmrNativeRrn2 = data.cstmrNativeRrn.substring(6)
      }

      // 외국인(일반 개인 및 미성년자) 외국인등록번호 복원
      if (['FN', 'FM'].includes(data.cstmrTypeCd)) {
        const fRrn = data.cstmrForeignerRrn || data.cstmrNativeRrn || ''
        if (fRrn && fRrn.length >= 6) {
          c.cstmrForeignerRrn1 = fRrn.substring(0, 6)
          c.cstmrForeignerRrn2 = fRrn.substring(6)
        }
      }

      c.cstmrVisitTypeCd = data.cstmrVisitTypeCd || 'VMY'
      c.mobileNo1 = data.cstmrMobileFnNo || '010'
      c.mobileNo2 = data.cstmrMobileMnNo || ''
      c.mobileNo3 = data.cstmrMobileRnNo || ''
      c.address = data.cstmrAdr || ''
      c.detailAddress = data.cstmrAdrDtl || ''
      c.zipNo = data.cstmrZipcd || ''

      // 외국인 정보 복원
      c.cstmrForeignerPn = data.cstmrForeignerPn || ''
      c.cstmrForeignerCountryCd = data.cstmrForeignerCountryCd || ''
      c.cstmrForeignerNation = data.cstmrForeignerNation || ''
      c.cstmrForeignerVisaNo = data.cstmrForeignerVisaNo || ''
      c.cstmrForeignerVdateStartDate = data.cstmrForeignerVdateStartDate || ''
      c.cstmrForeignerVdateEndDate = data.cstmrForeignerVdateEndDate || ''
      c.country = data.cstmrForeignerCountryCd || data.cstmrForeignerNation || ''
      c.visaType = data.cstmrForeignerVisaNo || ''
      c.stayPeriod = data.cstmrForeignerVdateEndDate || ''

      if (data.cstmrEmailAdr && data.cstmrEmailAdr.includes('@')) {
        const [e1, e2] = data.cstmrEmailAdr.split('@')
        c.emailAddr1 = e1
        c.emailAddr2 = e2
      }
      c.cstmrEmailReceiveYn = data.cstmrEmailReceiveYn === 'Y'

      // Agent mapping (미성년자 또는 대리인 방문개통 VMH 복원)
      if (data.cstmrTypeCd == 'NM' || data.cstmrTypeCd == 'FM' || data.cstmrVisitTypeCd == 'VMH') {
        c.minorAgentNm = data.minorAgentNm || data.repName || ''
        c.repName = data.repName || data.minorAgentNm || ''
        c.repRelation = data.repRelation || data.minorAgentRelTypeCd || ''
        c.minorAgentRelTypeCd = data.minorAgentRelTypeCd || data.repRelation || ''

        c.minorAgentTelFnNo = data.minorAgentTelFnNo || data.repPhone1 || ''
        c.minorAgentTelMnNo = data.minorAgentTelMnNo || data.repPhone2 || ''
        c.minorAgentTelRnNo = data.minorAgentTelRnNo || data.repPhone3 || ''
        c.repPhone1 = data.repPhone1 || data.minorAgentTelFnNo || ''
        c.repPhone2 = data.repPhone2 || data.minorAgentTelMnNo || ''
        c.repPhone3 = data.repPhone3 || data.minorAgentTelRnNo || ''

        const repRrn = data.minorAgentRrn || data.repRegistrationNo || ''
        if (repRrn && repRrn.length >= 6) {
          c.repRegistrationNo1 = repRrn.substring(0, 6)
          c.repRegistrationNo2 = repRrn.substring(6)
          c.repForeignerNo1 = repRrn.substring(0, 6)
          c.repForeignerNo2 = repRrn.substring(6)
        }
      }

      // 법인/공공기관 대리인 복원 (jrdclAgent* ➡️ minorAgent* & agentBirthDate/agentGender 파싱)
      if (data.cstmrTypeCd === 'JP' || data.cstmrTypeCd === 'GO') {
        c.jrdclAgentNm = data.jrdclAgentNm || ''
        c.jrdclAgentRelTypeCd = data.jrdclAgentRelTypeCd || ''
        c.jrdclAgentRrn = data.jrdclAgentRrn || ''
        c.jrdclAgentTelFnNo = data.jrdclAgentTelFnNo || ''
        c.jrdclAgentTelMnNo = data.jrdclAgentTelMnNo || ''
        c.jrdclAgentTelRnNo = data.jrdclAgentTelRnNo || ''

        c.minorAgentNm = data.jrdclAgentNm || data.minorAgentNm || ''
        c.minorAgentRelTypeCd =
          data.jrdclAgentRelTypeCd || data.minorAgentRelTypeCd || data.repRelation || ''
        c.minorAgentTelFnNo = data.jrdclAgentTelFnNo || data.minorAgentTelFnNo || ''
        c.minorAgentTelMnNo = data.jrdclAgentTelMnNo || data.minorAgentTelMnNo || ''
        c.minorAgentTelRnNo = data.jrdclAgentTelRnNo || data.minorAgentTelRnNo || ''

        const cleanJrdclRrn = (data.jrdclAgentRrn || '').replace(/\D/g, '')
        if (cleanJrdclRrn && cleanJrdclRrn.length >= 6) {
          if (cleanJrdclRrn.length >= 8) {
            c.agentBirthDate = cleanJrdclRrn.substring(0, 8)
          } else {
            const yy = Number(cleanJrdclRrn.substring(0, 2))
            const yearPrefix = yy > 50 ? '19' : '20'
            c.agentBirthDate = yearPrefix + cleanJrdclRrn.substring(0, 6)
          }
          if (cleanJrdclRrn.length >= 7) {
            const genderDigit = cleanJrdclRrn.charAt(6)
            c.agentGender = ['2', '4', '6', '8'].includes(genderDigit) ? 'F' : 'M'
          } else {
            c.agentGender = 'M'
          }
        } else {
          c.agentBirthDate = data.minorAgentBirth || ''
          c.agentGender = ''
        }
      }

      // 약관류
      const toBool = (val) => val === 'Y'
      c.clausePriCollectYn = toBool(data.clausePriCollectYn)
      c.clausePriOfferYn = toBool(data.clausePriOfferYn)
      c.clauseEssCollectYn = toBool(data.clauseEssCollectYn)
      c.clausePriTrustYn = toBool(data.clausePriTrustYn)
      c.clausePriAdYn = toBool(data.clausePriAdYn)
      c.clauseConfidenceYn = toBool(data.clauseConfidenceYn)
      c.clauseFathYn = toBool(data.clauseFathYn)
      c.nwBlckAgrmYn = toBool(data.nwBlckAgrmYn)
      c.appBlckAgrmYn = toBool(data.appBlckAgrmYn)
      c.blckAppDivCd = data.blckAppDivCd || ''
      c.soTrnsAgrmYn = toBool(data.soTrnsAgrmYn)
      c.clauseJehuYn = toBool(data.clauseJehuYn)
      c.clause5gCoverageYn = toBool(data.clause5gCoverageYn)
      c.combineSoloYn = toBool(data.combineSoloYn)

      // Product mapping
      const p = product.value

      // 단말기 정보 매핑 바로잡기
      c.modelId = data.modelId || ''
      c.handsetProdId = data.prodId || ''
      c.deviceModel = data.modelId || ''
      c.deviceModelNm = data.prodNm || ''

      // 요금제 정보 매핑 복원 (socCode, socNm, prdtSctnCd, dataType 추가)
      c.socCode = data.socCode || ''
      c.socNm = data.socNm || ''
      // 신규개통(NAC3)인 경우 임시저장 불러오기 시 개통번호(openNo)를 복원하지 않고 비움 (희망번호 재선택 필수)
      c.openNo = c.joinType === 'NAC3' ? '' : data.openNo || ''
      c.prodId = data.socCode || '' // UI 컴포넌트 호환용
      c.prodNm = data.socNm || '' // UI 컴포넌트 호환용
      c.prodCtgId = data.prodCtgId || '' // prodCtgId 복원 추가
      c.prdtSctnCd = data.dataType || data.prdtSctnCd || '' // 서버 dataType -> 화면 prdtSctnCd
      c.dataType = data.prdtSctnCd || data.dataType || '' // 서버 prdtSctnCd -> 화면 dataType

      p.deviceModel = data.modelId || ''
      p.modelSalePolicyCd = '' // 임시저장 복원 방지 (실시간 단말 조회를 통한 세팅 유도)
      p.imei = data.reqPhoneSn || ''
      // SIM 정보 복원 및 simTypeCd, hasSim 역매핑
      const ukCd = data.usimKindsCd || ''
      p.usimKindsCd = ukCd
      if (ukCd === '09') {
        p.simTypeCd = 'ESIM'
        p.hasSim = true
        p.simPurchaseMethod = 'N'
        p.usimPayMthdCd = '3'
        p.usimPriceTypeCd = 'B'
      } else {
        p.simTypeCd = 'USIM'
        const payMthd = data.usimPayMthdCd || data.usimPriceTypeCd || ''
        if (payMthd && !['N', '06', '09'].includes(payMthd)) {
          p.hasSim = false // USIM 구매
          p.simPurchaseMethod = payMthd
          p.usimPayMthdCd = data.usimPayMthdCd || (['R', '1', '01'].includes(payMthd) ? '1' : '2')
          p.usimPriceTypeCd =
            data.usimPriceTypeCd || (['R', '1', '01'].includes(payMthd) ? 'R' : 'B')
        } else {
          p.hasSim = true // USIM 보유
          p.simPurchaseMethod = 'N'
          p.usimPayMthdCd = '1'
          p.usimPriceTypeCd = 'N'
        }
      }
      p.reqUsimSn = data.reqUsimSn || ''
      p.eid = data.eid || ''
      p.imei1 = data.imei1 || ''
      p.imei2 = data.imei2 || ''
      p.reqWantFnNo = data.reqWantFnNo || ''
      p.reqWantMnNo = data.reqWantMnNo || ''
      p.reqWantRnNo = data.reqWantRnNo || ''
      p.wishNo =
        data.wishNo ||
        (data.operTypeCd === 'NAC3' || data.joinType === 'NAC3' || c.joinType === 'NAC3'
          ? data.openNo
          : '') ||
        ''
      c.contractPeriod =
        data.enggMnthCnt !== undefined && data.enggMnthCnt !== null
          ? String(data.enggMnthCnt)
          : '24'
      c.installmentMonth =
        data.modelMonthly !== undefined && data.modelMonthly !== null
          ? String(data.modelMonthly)
          : ''
      p.installmentMonth =
        data.modelMonthly !== undefined && data.modelMonthly !== null
          ? String(data.modelMonthly)
          : ''
      p.modelMonthly =
        data.modelMonthly !== undefined && data.modelMonthly !== null
          ? String(data.modelMonthly)
          : ''
      p.discountType = data.sprtTypeCd || ''

      // 번호이동 번호 복원 (data.moveMobileFnNo 등이 없으면 data.openNo로부터 파싱)
      let mFn = data.moveMobileFnNo || ''
      let mMn = data.moveMobileMnNo || ''
      let mRn = data.moveMobileRnNo || ''
      if (
        (!mFn || !mMn || !mRn) &&
        data.openNo &&
        (data.operTypeCd === 'MNP3' ||
          data.joinType === 'MNP3' ||
          c.joinType === 'MNP3' ||
          data.cstmrTypeCd === 'MNP3' ||
          data.joinType === '02')
      ) {
        const cleanOpen = data.openNo.replace(/\D/g, '')
        if (cleanOpen.length >= 9) {
          mFn = cleanOpen.substring(0, 3)
          mMn = cleanOpen.length === 11 ? cleanOpen.substring(3, 7) : cleanOpen.substring(3, 6)
          mRn = cleanOpen.substring(cleanOpen.length - 4)
        }
      }

      p.moveCompanyCd = data.moveCompanyCd || ''
      p.moveMobileNo1 = mFn || '010'
      p.moveMobileNo2 = mMn || ''
      p.moveMobileNo3 = mRn || ''
      p.moveAuthTypeCd = data.moveAuthTypeCd || ''
      p.moveAuthNo = data.moveAuthNo || ''

      // 데이터 타입 보정
      p.moveThismonthPayTypeCd = data.moveThismonthPayTypeCd === 'Y'
      p.moveAllotmentSttusCd = data.moveAllotmentSttusCd ? [data.moveAllotmentSttusCd] : []
      p.moveRefundAgreeYn = data.moveRefundAgreeYn ? [data.moveRefundAgreeYn] : []

      const isOthersPay = data.othersPaymentYn === 'Y' || data.othersPaymentYn === true
      p.othersPaymentYn = isOthersPay
      p.autoPayerType = isOthersPay ? 'autoPayerType2' : 'autoPayerType1'
      p.cardPayerType = isOthersPay ? 'cardPayerType2' : 'cardPayerType1'

      p.reqPayTypeCd = data.reqPayTypeCd || ''
      p.reqBankCd = data.reqBankCd || ''
      p.reqAccountNm = data.reqAccountNm || data.othersPaymentNm || data.othersPaymentReqNm || ''
      p.reqAccountRrn = data.reqAccountRrn || data.othersPaymentRrn || ''
      p.reqAccountRelTypeCd = data.reqAccountRelTypeCd || data.othersPaymentRelTypeCd || ''
      p.reqAccountNo = data.reqAccountNo || ''
      p.reqCardNm = data.reqCardNm || data.othersPaymentNm || data.othersPaymentReqNm || ''
      p.reqCardRrn = data.reqCardRrn || data.othersPaymentRrn || ''
      p.reqCardCompanyCd = data.reqCardCompanyCd || ''
      p.reqCardNo = data.reqCardNo || ''
      p.reqCardYy = data.reqCardYy || ''
      p.reqCardMm = data.reqCardMm || ''
      p.cardRelation = data.cardRelation || data.othersPaymentRelTypeCd || ''
      p.reqWireTypeCd = data.reqWireTypeCd || ''
      p.othersPaymentTelFnNo = data.othersPaymentTelFnNo || ''
      p.othersPaymentTelMnNo = data.othersPaymentTelMnNo || ''
      p.othersPaymentTelRnNo = data.othersPaymentTelRnNo || ''
      p.othersPaymentNm = data.othersPaymentNm || data.reqAccountNm || data.reqCardNm || ''
      p.othersPaymentRrn = data.othersPaymentRrn || data.reqAccountRrn || data.reqCardRrn || ''
      p.othersPaymentRelTypeCd = data.othersPaymentRelTypeCd || ''
      p.othersPaymentReqNm = data.othersPaymentReqNm || ''
      p.othersPaymentAgrYn = data.othersPaymentAgrYn || 'N'
      p.prntsBillNo = data.prntsBillNo || ''
      p.combId = data.jointBillWithKt || data.prntsBillNo || data.combId || data.combineId || ''
      p.combAgree = true
      if (p.combId) {
        authFlags.value.combId = true // 통합청구 아이디가 복원된 경우 인증플래그 복원
      }
      p.cstmrBillSendTypeCd = (data.cstmrBillSendTypeCd || '').trim() || 'CB'
      p.isAutoAgree = true

      p.insrProdCd = data.insrProdCd || ''
      // 임시저장 불러오기할 때 안심보험 동의여부 복원 (하드코딩 제거)
      p.clauseInsuranceYn = data.clauseInsuranceYn || 'N'

      p.memo = data.memo || ''

      // Agreement mapping
      agreement.value.recYn = data.recYn || 'N'
      agreement.value.scanId = data.scanId || ''
      agreement.value.msfRequestRecList = data.msfRequestRecList || []
      if (data.fileNm) agreement.value.recFileNm = data.fileNm
      if (data.filePathNm) agreement.value.recFilePathNm = data.filePathNm

      // 임시저장 불러오기 시 사전체크 플래그(Y) 복원
      if (data.preCheckYn === 'Y' || data.requestPreCheck === 'Y' || data.preCheck === 'Y') {
        preChecked.value = true
      }

      // 3. 단계에 따른 상태값 복원 (단, tmpStepCd가 2 이상이더라도 상품 스탭까지만 열도록 제한하므로 복원 조건은 유지하되 리턴은 최대 2로 제한)
      const step = parseInt(data.tmpStepCd || 1)
      if (step >= 1) {
        customer.value.isSaved = false //  임시저장 불러온 상태는 아직 수정 가능 상태이므로 false로 복원 (저장 수행 시에만 true)
        customer.value.eligibilityStatus = '' //  불러오기 시 사전체크는 리셋되므로 빈값으로 초기화
        customer.value.isEligible = false //  가입적격 플래그도 false로 초기화
        customer.value.isScanVerified = false
        authFlags.value.requiredDocs = false //  임시저장 불러오기 시 구비서류 완료인증 초기화
        customer.value.repAgree = false // 법정대리인 동의 초기화
        if (customer.value.identityCertTypeCd !== 'S') {
          customer.value.isVerified = false
        } else {
          customer.value.isVerified = true
        }
        customer.value.termsAgreed = false // 임시저장 불러오면 약관동의는 초기값(false)으로 풀림
        // 신분증 인증 방식이 있으면 플래그만 세팅 (실제 인증 여부와 별개)
        if (customer.value.identityCertTypeCd) authFlags.value.identityCertTypeCd = true
      }
      if (step >= 2) {
        // MNP 인증번호가 있어도 다시 인증받아야 함
        authFlags.value.moveAuthTypeCd = false
        if (product.value.reqBankCd && product.value.reqAccountNo) authFlags.value.autoAcct = true
        if (product.value.reqCardCompanyCd && product.value.reqCardNo)
          authFlags.value.reqCardNo = true
      }

      // 2 이상이면 항상 상품 스탭까지만 리턴 (최대 2)
      return step >= 2 ? 2 : step
    } catch (e) {
      console.error(`Failed to load from API (Key: ${key})`, e)
      return false
    } finally {
      setTimeout(() => {
        isDraftLoading.value = false
      }, 100)
    }
  }

  const apiGetMyinfoViewForDeviceChange = async (contractNum, phoneNo) => {
    isDraftLoading.value = true
    try {
      const data = await post(
        '/api/msf/formServiceChange/changinfo/view',
        {
          ncn: contractNum,
          ctn: phoneNo,
          contractNum: contractNum,
          custId: customer.value.custId || '',
          roadAddrChk: false,
          skipPerMyktfInfo: true,
          agentCd: customer.value.agentCd || '',
        },
        { silent: true },
      )

      const formResponse = data?.data
      const changInfo = formResponse?.resData

      if (formResponse?.resCode === '0000' && changInfo) {
        // 1. 가입자 연락처 세팅
        // 주소
        if (changInfo.zipNo && changInfo.zipNo !== '-') {
          customer.value.zipNo = changInfo.zipNo
        }
        if (changInfo.address && changInfo.address !== '-') {
          customer.value.address = changInfo.address
        } else if (changInfo.addr && changInfo.addr !== '-') {
          customer.value.address = changInfo.addr
        }
        if (changInfo.detailAddress && changInfo.detailAddress !== '-') {
          customer.value.detailAddress = changInfo.detailAddress
        }

        // 국가
        if (changInfo.country && changInfo.country !== '-') {
          customer.value.country = changInfo.country
          customer.value.cstmrForeignerCountryCd = changInfo.country
        } else if (changInfo.cstmrNation && changInfo.cstmrNation !== '-') {
          customer.value.country = changInfo.cstmrNation
          customer.value.cstmrForeignerCountryCd = changInfo.cstmrNation
        }

        // 체류기간
        if (changInfo.stayPeriod && changInfo.stayPeriod !== '-') {
          customer.value.stayPeriod = changInfo.stayPeriod
          customer.value.cstmrForeignerVdateEndDate = changInfo.stayPeriod
        } else if (
          changInfo.cstmrForeignerVdateEndDate &&
          changInfo.cstmrForeignerVdateEndDate !== '-'
        ) {
          customer.value.stayPeriod = changInfo.cstmrForeignerVdateEndDate
          customer.value.cstmrForeignerVdateEndDate = changInfo.cstmrForeignerVdateEndDate
        }

        // 비자
        if (changInfo.visaType && changInfo.visaType !== '-') {
          customer.value.visaType = changInfo.visaType
          customer.value.cstmrForeignerVisaNo = changInfo.visaType
        } else if (changInfo.cstmrForeignerVisaNo && changInfo.cstmrForeignerVisaNo !== '-') {
          customer.value.visaType = changInfo.cstmrForeignerVisaNo
          customer.value.cstmrForeignerVisaNo = changInfo.cstmrForeignerVisaNo
        }

        // 이메일 주소
        if (changInfo.email && changInfo.email.includes('@')) {
          const [id, domain] = changInfo.email.split('@')
          customer.value.emailAddr1 = id || ''
          customer.value.emailAddr2 = domain || ''
        } else if (changInfo.cstmrEmailAdr && changInfo.cstmrEmailAdr.includes('@')) {
          const [id, domain] = changInfo.cstmrEmailAdr.split('@')
          customer.value.emailAddr1 = id || ''
          customer.value.emailAddr2 = domain || ''
        }

        // 연락처 (휴대폰번호, 일반전화번호)
        if (phoneNo && phoneNo.length >= 10) {
          customer.value.mobileNo1 = phoneNo.substring(0, 3)
          customer.value.mobileNo2 = phoneNo.substring(3, phoneNo.length - 4)
          customer.value.mobileNo3 = phoneNo.substring(phoneNo.length - 4)
        }

        const telNo = changInfo.homeTel || changInfo.telNo || ''
        if (telNo) {
          const rawTel = String(telNo).replace(/\D/g, '')
          if (rawTel.length >= 9) {
            customer.value.telNo1 = rawTel.substring(0, 3)
            customer.value.telNo2 = rawTel.substring(3, rawTel.length - 4)
            customer.value.telNo3 = rawTel.substring(rawTel.length - 4)
          }
        }

        // 2. 휴대폰 및 요금제 정보 세팅
        if (changInfo.socCode) {
          product.value.socCode = changInfo.socCode
          product.value.socNm = changInfo.socNm || changInfo.ratePlanName || ''
          product.value.prodNm = changInfo.prodNm || changInfo.ratePlanName || ''
          // 가입자 정보에도 동일하게 요금제 복사 (화면 표시용)
          customer.value.prodId = changInfo.socCode
          customer.value.prodNm = changInfo.socNm || changInfo.ratePlanName || ''
        }

        // 3. 부가서비스 신청 세팅
        if (changInfo.additionList && Array.isArray(changInfo.additionList)) {
          product.value.additionList = changInfo.additionList.map((item) => ({
            additionId: item.additionId || item.serviceCode || '',
            additionNm: item.additionNm || item.serviceName || '',
            rantal: item.rantal || '',
          }))
          product.value.addtionId = changInfo.additionList.map(
            (item) => item.additionId || item.serviceCode || '',
          )
        }

        // 4. 납부 정보 세팅
        const billSendType =
          changInfo.cstmrBillSendTypeCd ||
          changInfo.billData?.cstmrBillSendTypeCd ||
          changInfo.billData?.billSendTypeCd ||
          ''
        product.value.cstmrBillSendTypeCd = billSendType || 'CB'

        const payType =
          changInfo.reqPayTypeCd ||
          changInfo.payData?.reqPayTypeCd ||
          changInfo.payData?.payTypeCd ||
          ''
        product.value.reqPayTypeCd = payType || 'D'

        const othersPayYn = changInfo.othersPaymentYn || changInfo.payData?.othersPaymentYn || ''
        product.value.othersPaymentYn = othersPayYn || 'N'
      }
    } catch (e) {
      console.error('[기기변경][MyinfoView] 가입정보 조회 오류', e)
    } finally {
      setTimeout(() => {
        isDraftLoading.value = false
      }, 100)
    }
  }

  const validateCustomer = ref(() => true)
  const validateCustomerWithAlert = ref(() => true)
  const validateProduct = ref(() => true)
  const validateAgreement = ref(() => true)

  // 서명 및 녹취 완료 후 가입자/상품 정보 수정 시 완료 상태 무효화
  watch(
    [() => customer.value, () => product.value],
    () => {
      if (!isDraftLoading.value && agreement.value.recYn === 'Y') {
        agreement.value.recYn = 'N'
        agreement.value.scanId = ''
        agreement.value.recFileNm = ''
        agreement.value.recFilePathNm = ''
        agreement.value.msfRequestRecList = []
      }
    },
    { deep: true },
  )

  return {
    applicationKey,

    // 3가지 계층의 상태 Export (필요 시 조회용)
    initialCustomer,
    initialProduct,
    initialAgreement,
    draftCustomer,
    draftProduct,
    draftAgreement,

    // 화면과 바인딩 되는 현재(Current) 상태
    customer,
    product,
    agreement,
    authFlags,

    // 함수
    initForm,
    apiFetchFormData,
    apiSaveDraft,
    apiCompleteApplication,
    apiCheckMnpAgreeResult,
    resetStep,
    resetAll,
    resetProductStep,
    copyApplication,
    apiLoadDraft,
    apiGetMyinfoViewForDeviceChange,
    incrementWishNoSearchCount,

    validateCustomer,
    validateCustomerWithAlert,
    validateProduct,
    validateAgreement,

    // 추가 상태
    preChecked,
    wishNoSearchCount,
    documentId,
    originalfileName,
    resNo,
    rawEformsignFileData,
    rawRecordFileData,
    parentScanId,
    isDraftLoading,
    isDraftLoaded,
    openNo,
  }
})
