import { defineStore } from 'pinia'
import { computed, nextTick, ref, watch } from 'vue'
import { post } from '@/libs/api/msf.api'
import {
  concatStrings,
  extractDigits,
  extractYYYYMMDDRrn,
  getGenderByRrnBack,
} from '@/libs/utils/string.utils'
import { showAlert } from '@/libs/utils/comp.utils'
import { useMsfStepStore } from './msf_step'
import { formatDate } from '@/libs/utils/date.utils'
import { getCommonCodeList, getCommonCodeListWithDetail } from '@/libs/utils/comn.utils'

const DEFAULT_ERROR_MESSAGE = '신청서 등록이 실패하였습니다. 다시 시도해 주세요.'

const getInitialFormData = () => ({
  /********** 양도고객 (tr) **********/
  /** 고객(양도고객)유형 */
  tr_customer: {
    cstmrTypeCd: 'NA', //고객유형
    /** 고객(양도고객)신분증 확인 */
    identityCertTypeCd: '',
    fathTransacId: '',
    tr_idCard: '', //신분증
    identityTypeCd: '', //신분증 스캔
    identityTypeNm: '',
    isSaved: false,
    identityIssuDate: '', //발급일자
    driveLicnsNo: '', //면허번호
    identityIssuRegion: '', //면허 지역
    cstmrJuridicalRrn1: '', //법인등록번호1
    cstmrJuridicalRrn2: '', //법인등록번호2
    cstmrJuridicalBizNo1: '', //사업자등록번호1
    cstmrJuridicalBizNo2: '', //사업자등록번호2
    cstmrJuridicalBizNo3: '', //사업자등록번호3
    cstmrJuridicalRepNm: '', //대표자명
    cstmrJuridicalBizNoIssuDate: null, //교부일자
    cstmrPrivateBizNoIssuDate: '', //사업자 발급일자
    upjnCd: '', //업종
    bcuSbst: '', //업태
    deviceChgTel1: '010', //휴대폰 처음3자리
    deviceChgTel2: '', //휴대폰 가운데4자리
    deviceChgTel3: '', //휴대폰 마지막4자리
    /* 법정대리인 정보 */
    repName: '',
    minorAgentNm: '', //위임받은고객이름
    minorAgentRelTypeCd: '', //신청인과의 관계
    minorAgentTelFnNo: '', //연락처1
    minorAgentTelMnNo: '', //연락처2
    minorAgentTelRnNo: '', //연락처3
    repRegistrationNo1: '',
    repRegistrationNo2: '',
    repForeignerNo1: '',
    repForeignerNo2: '',
    minorUserBirthDate: '',
    minorUserGender: 'M',
    repAgree: false, // 동의
    /* 법정대리인 정보 */
    /** 고객(양도고객)정보 */
    cstmrNm: '', //이름
    cstmrNativeRrn1: '', //내국인 주민번호1
    cstmrNativeRrn2: '', //내국인 주민번호2
    cstmrForeignerRrn1: '', //외국인 주민번호1
    cstmrForeignerRrn2: '', //외국인 주민번호2
    userBirthDate: '', //생년월일
    userGender: 'M', //성별
    isTrCustomer: true,
    formType: 'OWN',
    serviceType: 'TR_CUSTOMER',
    userId: '',
    isVerified: false,
    isScanVerified: false,
    esimYn: null,
    msfRequestDocList: [],
  },
  /********** 양수고객 (te) **********/
  /** 고객(양수고객)유형 */
  te_customer: {
    /* 고객(양수)정보 */
    cstmrTypeCd: 'NA', //고객유형
    cstmrNm: '', //이름
    cstmrNativeRrn1: '', //내국인 주민번호1
    cstmrNativeRrn2: '', //내국인 주민번호2
    cstmrForeignerRrn1: '', //외국인 주민번호1
    cstmrForeignerRrn2: '', //외국인 주민번호2
    upjnCd: '', //업종
    bcuSbst: '', //업태
    deviceChgTel1: '010', //휴대폰 처음3자리
    deviceChgTel2: '', //휴대폰 가운데4자리
    deviceChgTel3: '', //휴대폰 마지막4자리
    /** 고객(양수고객)신분증 확인 */
    identityCertTypeCd: 'K',
    tr_idCard: '', //신분증
    identityTypeCd: '', //신분증 스캔
    identityIssuDate: '', //발급일자
    identityIssuRegion: '', //발급지역 (면허지역)
    driveLicnsNo: '', //면허번호
    cstmrJuridicalRrn1: '', //법인등록번호1
    cstmrJuridicalRrn2: '', //법인등록번호2
    cstmrJuridicalBizNo1: '', //사업자등록번호1
    cstmrJuridicalBizNo2: '', //사업자등록번호2
    cstmrJuridicalBizNo3: '', //사업자등록번호3
    cstmrJuridicalRepNm: '', //대표자명
    cstmrJuridicalBizNoIssuDate: null, //교부일자
    cstmrPrivateBizNoIssuDate: null, //사업자 발급일자
    cstmrVisitTypeCd: 'VMY', //방문유형코드
    /* 법정대리인 정보 */
    repName: null,
    minorAgentNm: null, //위임받은고객이름
    minorAgentRelTypeCd: null, //신청인과의 관계
    minorAgentTelFnNo: null, //연락처1
    minorAgentTelMnNo: null, //연락처2
    minorAgentTelRnNo: null, //연락처3
    repRegistrationNo1: null, // 법정대리인 등록번호 앞자리
    repRegistrationNo2: null, // 법정대리인 등록번호 뒷자리
    repForeignerNo1: null, // 법정대리인 외국인 등록번호 앞자리
    repForeignerNo2: null, // 법정대리인 외국인 등록번호 뒷자리
    minorUserBirthDate: null,
    minorUserGender: 'M',
    repAgree: false, // 동의
    /* 법정대리인 정보 */
    /* 실사용자 & 대리인 */
    realUserName: '', //실사용자 이름
    agentGender: 'M', //성별
    agentBirthDate: '', //대리인 생년월일
    realUserBirthDate: '',
    /* 실사용자 & 대리인 */
    /** 고객(양수고객) 연락처 */
    mobileNo1: '010', //휴대폰번호1
    mobileNo2: '', //휴대폰번호2
    mobileNo3: '', //휴대폰번호3
    telNo1: '', //전화번호1
    telNo2: '', //전화번호2
    telNo3: '', //전화번호3
    emailAddr1: '', //이메일주소1
    emailAddr2: '', //이메일주소2
    zipNo: '', //주소1
    address: '', //주소2
    detailAddress: '', //주소3
    country: '', //국가
    te_stayPeriod: '', //체류기간
    visaType: '', //비자
    cstmrForeignerVdateStartDate: null,
    cstmrForeignerVdateEndDate: null,
    /** 대리점 관련 **/
    agency: '', //대리점
    agentCd: '', //대리점 코드
    agentNm: '',
    shopCd: '',
    shopNm: '',
    realShopNm: '',
    cpntId: '',
    cpntNm: '',
    cntpntShopCd: '',
    cntpntShopNm: '',
    managerCd: '',
    managerNm: null,
    representativeTelephone: '',
    telephone: null,
    /** 대리점 관련 **/
    /** knote 정보 **/
    knoteIdentityScanCstmrNm: null, // KNOTE신분증고객명
    knoteIdentityEssNo: null, // KNOTE신분증식별번호
    knoteIdentityTypeCd: null, // KNOTE신분증유형코드
    knoteIdentityScanDt: null, // KNOTE신분증스캔일시
    knoteScanId: null, // KNOTE신분증스캔번호
    iselfFrmpapYn: null,
    /** knote 정보 **/
    /** 안면인증 **/
    fathTrgYn: null, // 안면인증대상여부
    fathTrgIdentityCertTypeCd: null, // 안면인증대상신분증유형코드
    fathTransacId: null, // 안면인증트랜잭션ID
    fathCmpltNtfyDate: null, // 안면인증완료일자
    fathTelNo: null, // 안면인증URL전송전화번호
    fathMobileFnNo: null, // 안면인증정보휴대폰번호앞자리번호
    fathMobileMnNo: null, // 안면인증정보휴대폰번호중간자리번호
    fathMobileRnNo: null, // 안면인증정보휴대폰번호뒷자리번호
    /** 안면인증 **/
    isTeCustomer: true,
    formType: 'OWN',
    serviceType: 'TE_CUSTOMER',
    userId: '',
    isVerified: false,
    isScanVerified: false,
    isSaved: false,
    msfRequestDocList: null,
    /** 녹취스크립트용 정보 **/
    cntpntCdNm: null,
    userNm: null,
    /** 녹취스크립트용 정보 **/
  },
  juridical: {
    minorAgentNm: null,
    agentBirthDate: null,
    agentGender: null,
    minorAgentRelTypeCd: null,
    minorAgentTelFnNo: null,
    minorAgentTelMnNo: null,
    minorAgentTelRnNo: null,
  },
  memo: '',
  // 실사용자 & 대리인
  /** 고객(실사용자) 정보 */
  cstmrNm: '', //실사용자이름
  /** 대리인 위임 정보 */
  /** 요금제 정보 */
  planInfo: {
    planName1: '', // 요금제1
    planName2: '', // 요금제2
    planName3: '', // 요금제3
    planAmt: '', //요금
    planNm: '',
    // 제휴 정보 추가
    jehuPartnerTypeCd: '',
    jehuPartnerTypeNm: '',
    jehuProdTypeCd: '',
    dataType: null,
    jehuProdName: '',
    plcySctnCd: null,
    prdtSctnCd: null,
    planSelectType: 'CURRENT',
    userId: '',
    ncn: '',
    ctn: '',
    custId: '',
    orgProdId: '',
    orgPordNm: '',
    orgPlanAmt: '',
    ktOrgId: '',
  },
  /** 요금제 정보 */
  /** USIM 정보 */
  usimInfo: {
    hasSim: true, //SIM보유
    simTypeCd: '', //SIM타입
    usimKindsCd: '', //USIM 선택
    reqUsimSn: '', //USIM 번호
    reqUsimNm: null,
    simPurchaseMethod: '', //USIM 구매 방식
    prodNm: '', //휴대폰 모델병
    eid: '', //EID
    imei1: '', //IMEI1
    imei2: '', //IMEI2
    modelNm: '',
    usimPrice: null,
  },
  /** 납부정보 */
  productPayment: {
    /* 납부 정보 */
    cstmrBillSendTypeCd: '', //수신유형
    reqPayTypeCd: 'D', //납부방법
    autoPayerType: '', //자동이체-납부자유형
    reqBankCd: '', //자동이체-은행선택
    reqAccountNo: '', //자동이체-계좌번호입력
    reqAccountNm: '', //자동이체-납부고객명
    reqAccountRrn: '', //자동이체-생년월일(8자리) 임력
    reqAccountRelTypeCd: '', //자동이체-관계
    isAutoAgree: true, //자동이체-동의
    cardPayerType: '', //신용카드-납부자유형
    reqCardCompanyCd: '', //신용카드-카드사선택
    reqCardNo: '', //신용카드-카드번호입력
    reqCardMm: '', //신용카드-유효기간(MM)
    reqCardYy: '', //신용카드-유효기간(YY)
    reqCardNm: '', //신용카드-납부고객명
    reqCardRrn: '', //신용카드-생년월일
    cardRelation: '', //신용카드-관계
    othersPaymentYn: 'N', //타인납부-동의
    othersPaymentAgrYn: 'N',
    combId: '', //통합청구-청구계정ID
    combAgree: false, //통합청구-동의
  },
  /** 납부정보 */
  /** USIM 정보 */
  /** 동의 영역 정보 **/
  agreement: {
    agreeCheck1: false,
    agreeCheck2: false,
    agreeCheck3: false,
    agreeCheck4: false,
    agreeCheck5: false,
    agreeCheck6: false,
  },
  /** 동의 영역 정보 **/
  termsAgreed: false,
  indvLocaPrvAgreeYn: 'N', // 개인위치정보 제 3자 제공 동의
  /* 고객 혜택 제공 관련 */
  personalInfoCollectAgreeYn: 'N', // 고객 혜택 제공을 위한 정보수집 이용 동의 및 혜택 광고의 수신 위탁 동의
  clausePriTrustYn: 'N',
  /** 혜택 제공 관련 */
  othersAdReceiveAgreeYn: 'N', // 혜택 제공을 위한 제3자 제공 및 광고 수신 동의
  othersTrnsAllAgreeYn: 'N', // 혜택 제공을 위한 제3자 제공 동의
  /** 혜택 제공 관련 */
  clause5gCoverageYn: 'N', // 5G커버리지확인및가입동의여부
  clausePartnerOfferYn: 'N',
  clauseJehuYn: 'N',
  nwBlckAgrmYn: 'N', // 네트워크차단동의여부
  appBlckAgrmYn: 'N', // 청소년유해매체차단동의여부
  /** 파일 정보 **/
  /** 녹취 여부 **/
  fileInfo: {
    recYn: null,
    recFileNm: null,
    recFilePathNm: null,
    /** 신청서 파일 정보 **/
    fileNm: null,
    fileMaskNm: null,
    /** 파일 정보 **/
  },
})

const getInitialTrAuthFlags = () => ({
  identityCertTypeCd: false,
  deviceChgTel: false,
  repPhone: false,
})

const getInitialAuthFlags = () => ({
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

export const useMsfFormOwnChgStore = defineStore('msf_form_own_chg', () => {
  const applicationKey = ref(null)
  const documentId = ref([])
  const completeErrorMessage = ref('')
  const stepStore = useMsfStepStore()
  // 각 인증 버튼들의 최종 완료 여부를 관리하는 플래그 (UI 제어용)
  const trAuthFlags = ref(getInitialTrAuthFlags())
  const authFlags = ref(getInitialAuthFlags())

  const apiFetchFormData = async (key = null) => {
    const payload = { requestKey: key || '' }
    const res = await post('/api/form/owner-change/form/get', payload)
    if (res.code === '0000') return res.data
    return null
  }

  // Step 1: Customer Info — initForm보다 먼저 선언해야 watcher 기반 초기화 순서가 보장됩니다.
  const formData = ref(getInitialFormData())
  applicationKey.value = null

  $reset()

  // 비동기 데이터 주입
  const initForm = async (requestKey) => {
    if (!requestKey) {
      // formData.value = getInitialFormData()
      return '0'
    }

    const resData = await apiFetchFormData(requestKey)
    if (!resData) return '0'

    stepStore.setActiveIndex(2)

    const trCstmrTypeCd = resData.trCustomer?.cstmrTypeCd
    const teCstmrTypeCd = resData.teCustomer?.cstmrTypeCd
    const trCertType = resData.trCustomer?.identityCertTypeCd
    const teCertType = resData.teCustomer?.identityCertTypeCd
    const upjnCd = resData.teCustomer?.upjnCd
    const hasSim = resData.usimInfo?.hasSim
    const soc = resData.planInfo?.planName2

    // [1단계] tr_customer.cstmrTypeCd 설정
    // handleTrCustomerTypeChange → resetTrCustomer + (미성년자일 경우) te_customer.cstmrTypeCd도 덮어씀
    // te를 함께 설정하면 tr watcher 내부의 side effect가 te를 다시 바꿔 Vue가 잘못된 값으로 te watcher를
    // 실행하므로, tr 완료 후 te를 별도 nextTick에서 설정해야 합니다.
    if (trCstmrTypeCd !== undefined) formData.value.tr_customer.cstmrTypeCd = trCstmrTypeCd
    await nextTick() // handleTrCustomerTypeChange 완료 대기

    // [2단계] te_customer.cstmrTypeCd 설정
    // 1단계에서 handleTrCustomerTypeChange가 te를 잘못된 값(tr과 동일한 미성년 코드)으로 세팅했을 수 있으므로
    // nextTick 이후 서버 데이터의 값으로 덮어씁니다.
    if (teCstmrTypeCd !== undefined) formData.value.te_customer.cstmrTypeCd = teCstmrTypeCd
    await nextTick() // handleTeCustomerTypeChange 완료 대기

    // [3단계] identityCertTypeCd 설정
    // resetTr/TeCustomer가 '' 으로 초기화한 후 서버값으로 변경 → MsfIdentityVerify watcher 발화
    // → repName·minorAgentRelTypeCd·전화번호 등 초기화 (minorUserBirthDate는 목록에 없어 유지)
    if (trCertType !== undefined) formData.value.tr_customer.identityCertTypeCd = trCertType
    if (teCertType !== undefined) formData.value.te_customer.identityCertTypeCd = teCertType

    if (upjnCd !== undefined) formData.value.te_customer.upjnCd = upjnCd
    if (hasSim !== undefined) formData.value.usimInfo.hasSim = hasSim
    if (soc !== undefined) formData.value.planInfo.planSelectType = 'CHANGE'

    await nextTick() // MsfIdentityVerify watcher 완료 대기

    // [4단계] 전체 필드 덮어쓰기
    // cstmrTypeCd·identityCertTypeCd는 이미 같은 값이므로 두 watcher 모두 재발화하지 않습니다.
    // → repName·minorAgentRelTypeCd·전화번호 등이 그대로 유지됩니다.
    if (resData.trCustomer) {
      Object.assign(formData.value.tr_customer, resData.trCustomer)
    }
    if (resData.teCustomer) {
      resData.teCustomer.cstmrVisitTypeCd = resData.teCustomer?.cstmrVisitTypeCd
        ? resData.teCustomer.cstmrVisitTypeCd
        : 'VMY'
      Object.assign(formData.value.te_customer, resData.teCustomer)
      formData.value.te_customer.zipNo = resData.teCustomer.zip || resData.teCustomer.zipNo || ''
      formData.value.te_customer.address = resData.teCustomer.address || ''
      formData.value.te_customer.detailAddress = resData.teCustomer.detailAddress || ''
    }
    if (resData.usimInfo) {
      Object.assign(formData.value.usimInfo, resData.usimInfo)
    }
    if (resData.planInfo) {
      Object.assign(formData.value.planInfo, resData.planInfo)
    }
    if (resData.productPayment) {
      Object.assign(formData.value.productPayment, resData.productPayment)
    }
    if (resData.juridical) {
      Object.assign(formData.value.juridical, resData.juridical)
    }

    if (resData.memo) {
      formData.value.memo = resData.memo
    }

    showAlert(
      '이전에 인증하신 신분증 인증 정보는 재사용이 불가하므로, 신분증 인증을 다시 진행해 주세요.',
    )

    return resData?.currentStep || '0'
  }

  const isTrMinor = computed(() => ['NM', 'FM'].includes(formData.value.tr_customer.cstmrTypeCd))
  const isTeMinor = computed(() => ['NM', 'FM'].includes(formData.value.te_customer.cstmrTypeCd))
  const isTrGovernment = computed(() =>
    ['JP', 'GO'].includes(formData.value.tr_customer.cstmrTypeCd),
  )
  const isTeGovernment = computed(() =>
    ['JP', 'GO'].includes(formData.value.te_customer.cstmrTypeCd),
  )
  const isTrForeigner = computed(() =>
    ['FN', 'FM'].includes(formData.value.tr_customer.cstmrTypeCd),
  )
  const isTeForeigner = computed(() =>
    ['FN', 'FM'].includes(formData.value.te_customer.cstmrTypeCd),
  )
  const hasSimFlag = computed(() => !formData.value.usimInfo.hasSim)
  const currentPlanUse = computed(() => formData.value.planInfo.planSelectType === 'CURRENT')
  const othersPaymentYn = computed(() => formData.value.productPayment.othersPaymentYn === 'Y')
  const isAccount = computed(() => ['D'].includes(formData.value.productPayment.reqPayTypeCd))
  const isCard = computed(() => ['C'].includes(formData.value.productPayment.reqPayTypeCd))
  const isComb = computed(() => ['0'].includes(formData.value.productPayment.reqPayTypeCd))
  const isFath = computed(() => ['F'].includes(formData.value.te_customer?.identityCertTypeCd))
  const isEmailRequired = computed(() =>
    ['CB'].includes(formData.value.productPayment.cstmrBillSendTypeCd),
  )

  const buildCompletePayload = () => ({
    requestKey: applicationKey.value,
    ncn: formData.value.planInfo.ncn,
    ctn: formData.value.planInfo.ctn,
    custId: formData.value.planInfo.custId,
    parentScanId: stepStore.parentScanId || '',
    userBirth: formData.value.tr_customer.userBirthDate,
    /* MSF_REQEST_NAME_TRNS */
    trnsCstmrTypeCd: formData.value.tr_customer.cstmrTypeCd, // 양도인고객구분유형코드

    trnsTrnsfeMobileNo: concatStrings([
      formData.value.te_customer.mobileNo1,
      formData.value.te_customer.mobileNo2,
      formData.value.te_customer.mobileNo3, // 양수인모바일번호
    ]),

    trnsTrnsfeMobileNo1: formData.value.te_customer.mobileNo1,
    trnsTrnsfeMobileNo2: formData.value.te_customer.mobileNo2,
    trnsTrnsfeMobileNo3: formData.value.te_customer.mobileNo3,

    trIdentityCertTypeCd: formData.value.tr_customer.identityCertTypeCd, // 신분증인증유형코드

    trCstmrJuridicalRrn1: formData.value.tr_customer.cstmrJuridicalRrn1, //양도인 법인등록번호1
    trCstmrJuridicalRrn2: formData.value.tr_customer.cstmrJuridicalRrn2, //양도인 법인등록번호2

    trCstmrNativeRrn: !isTrForeigner.value
      ? concatStrings([
          formData.value.tr_customer.cstmrNativeRrn1,
          formData.value.tr_customer.cstmrNativeRrn2,
        ])
      : concatStrings([
          formData.value.tr_customer.cstmrForeignerRrn1,
          formData.value.tr_customer.cstmrForeignerRrn2,
        ]), // 고객정보내국인주민등록번호

    trFathTrgYn: 'N', // 안면인증대상여부
    trFathTrgIdentityCertTypeCd: null, // 안면인증대상신분증유형코드
    trFathTransacId: formData.value.tr_customer?.fathTransacId || null, // 안면인증트랜잭션ID
    trFathCmpltNtfyDate: null, // 안면인증완료일자
    trFathTelNo: null, // 안면인증URL전송전화번호
    trFathMobileFnNo: null, // 안면인증정보휴대폰번호앞자리번호
    trFathMobileMnNo: null, // 안면인증정보휴대폰번호중간자리번호
    trFathMobileRnNo: null, // 안면인증정보휴대폰번호뒷자리번호

    trAuthInfo: null, // 인증정보

    trIdentityTypeCd: formData.value.tr_customer.identityTypeCd, // 신분증유형코드
    trIdentityIssuDate: formData.value.tr_customer.identityIssuDate?.replaceAll(/[^0-9]/g, ''), // 신분증발급일자
    trIdentityIssuRegion: formData.value.tr_customer.identityIssuRegion, // 신분증발급지역

    trSelfIssuNo: null, // 발급번호
    trDriveLicnsNo: formData.value.tr_customer.driveLicnsNo, // 운전면허번호

    trnsNm: formData.value.tr_customer.cstmrNm, // 양도인명
    trnsMobileNo: concatStrings([
      formData.value.tr_customer.deviceChgTel1,
      formData.value.tr_customer.deviceChgTel2,
      formData.value.tr_customer.deviceChgTel3, // 명의변경대상모바일번호
    ]),
    trnsMobileFnNo: formData.value.tr_customer.deviceChgTel1,
    trnsMobileMnNo: formData.value.tr_customer.deviceChgTel2,
    trnsMobileRnNo: formData.value.tr_customer.deviceChgTel3,
    trnsPhoneNo: null, // 명의자연락처번호
    trnsPwd: null, // 명의변경용비밀번호
    trnsMyslfConfMethCd: null, // 양도인본인확인방법코드
    trnsTrnsfeNm: formData.value.te_customer.cstmrNm, // 양도인이입력한양수인명

    trnsBirth: formData.value.tr_customer?.userBirthDate,
    trnsGenderCd: formData.value.tr_customer?.userGender,
    trnsCname: formData.value.tr_customer?.cstmrNm,
    trnsBizNo: concatStrings([
      formData.value.tr_customer?.cstmrJuridicalBizNo1,
      formData.value.tr_customer?.cstmrJuridicalBizNo2,
      formData.value.tr_customer?.cstmrJuridicalBizNo3,
    ]),
    trnsJuridicalRrn: concatStrings([
      formData.value.tr_customer?.cstmrJuridicalRrn1,
      formData.value.tr_customer?.cstmrJuridicalRrn2,
    ]),
    trnsJuridicalRepNm: formData.value.tr_customer?.cstmrJuridicalRepNm,

    trCstmrVisitTypeCd: formData.value.te_customer.cstmrVisitTypeCd, // 방문고객유형코드

    trMinorAgentNm: formData.value.tr_customer.minorAgentNm, // 미성년자법정대리인성명
    trMinorAgentRrn: concatStrings([
      formData.value.tr_customer.repRegistrationNo1,
      formData.value.tr_customer.repRegistrationNo2,
    ]), // 미성년자법정대리인등록번호
    trMinorAgentBirth: formData.value.tr_customer.minorUserBirthDate, // 미성년자법정대리인생년월일
    trMinorAgentGenderCd: formData.value.tr_customer.minorUserGender, // 미성년자법정대리인성별
    trMinorAgentRelTypeCd: formData.value.tr_customer.minorAgentRelTypeCd, // 미성년자법정대리인관계유형코드
    trMinorAgentTelFnNo: formData.value.tr_customer.minorAgentTelFnNo, // 미성년자법정대리인연락처앞자리번호
    trMinorAgentTelMnNo: formData.value.tr_customer.minorAgentTelMnNo, // 미성년자법정대리인연락처중간자리번호
    trMinorAgentTelRnNo: formData.value.tr_customer.minorAgentTelRnNo, // 미성년자법정대리인끝자리번호
    trMinorAgentTelNo: concatStrings([
      formData.value.tr_customer.minorAgentTelFnNo,
      formData.value.tr_customer.minorAgentTelMnNo,
      formData.value.tr_customer.minorAgentTelRnNo,
    ]), // 미성년자법정대리인번호
    trMinorAgentAgrmYn: 'Y', // 미성년자법정대리인안내사항및동의여부
    trMinorAgentSelfInqryAgrmYn: 'Y', // 미성년자법정대리인본인인증조회동의여부
    trMinorAgentSelfCertTypeCd: null, // 미성년자법정대리인본인인증유형코드
    trMinorAgentSelfIssuExprDate: isTrMinor.value
      ? extractDigits(formData.value.tr_customer.identityIssuDate)
      : null, // 미성년자법정대리인발급/만료일자
    trMinorAgentSelfIssuNo: null, // 미성년자법정대리인발급번호

    trnsSttusCd: 'N', // 처리상태코드
    authDelYn: 'Y', // 개인정보삭제여부
    confirmMemo: null, // 처리메모
    /* MSF_REQEST_NAME_TRNS */

    /* MSF_REQEST_NAME_CHG */
    managerCd: formData.value.te_customer.managerCd, // 매니저코드
    managerNm: formData.value.te_customer.managerNm, // 매니저명
    agentCd: formData.value.te_customer.agentCd, // 대리점코드
    agentNm: formData.value.te_customer.agentNm, // 대리점명
    shopCd: formData.value.te_customer.shopCd, // 판매점코드
    shopNm: formData.value.te_customer.shopNm, // 판매점명
    realShopNm: formData.value.te_customer.realShopNm, // 실판매점명
    cpntId: formData.value.te_customer.cpntId, // 접점ID
    cpntNm: formData.value.te_customer.cpntNm, // 접점명
    cntpntShopCd: formData.value.te_customer.cntpntShopCd, // 채널판매점코드
    cntpntShopNm: formData.value.te_customer.cntpntShopNm, // 채널판매점명

    operTypeCd: 'MCN3', // 업무구분유형코드

    cstmrTypeCd: formData.value.te_customer.cstmrTypeCd, // 고객구분유형코드

    mcnResNo: null, // 명의변경예약번호

    teIdentityCertTypeCd: formData.value.te_customer.identityCertTypeCd, // 신분증인증유형코드

    knoteIdentityScanCstmrNm: formData.value.te_customer?.knoteIdentityScanCstmrNm, // KNOTE신분증고객명
    knoteIdentityEssNo: formData.value.te_customer?.knoteIdentityEssNo, // KNOTE신분증식별번호
    knoteIdentityTypeCd: formData.value.te_customer?.knoteIdentityTypeCd, // KNOTE신분증유형코드
    knoteIdentityScanDt: formData.value.te_customer?.knoteIdentityScanDt, // KNOTE신분증스캔일시
    knoteScanId: formData.value.te_customer?.knoteScanId, // KNOTE신분증스캔번호

    teCstmrJuridicalRrn1: formData.value.te_customer.cstmrJuridicalRrn1, //양도인 법인등록번호1
    teCstmrJuridicalRrn2: formData.value.te_customer.cstmrJuridicalRrn2, //양도인 법인등록번호2

    teFathTrgYn: isFath.value ? 'Y' : 'N', // 안면인증대상여부
    teFathTrgIdentityCertTypeCd: isFath.value
      ? formData.value.te_customer?.fathTrgIdentityCertTypeCd
      : null, // 안면인증대상신분증유형코드
    teFathTransacId: isFath.value ? formData.value.te_customer?.fathTransacId : null, // 안면인증트랜잭션ID
    // teFathTransacId: 'MIS0202202607151029568795375', // 안면인증트랜잭션ID
    teFathCmpltNtfyDate: isFath.value ? formData.value.te_customer?.fathCmpltNtfyDate : null, // 안면인증완료일자
    teFathTelNo: isFath.value ? formData.value.te_customer?.fathTelNo : null, // 안면인증URL전송전화번호
    teFathMobileFnNo: isFath.value ? formData.value.te_customer?.fathMobileFnNo : null, // 안면인증정보휴대폰번호앞자리번호
    teFathMobileMnNo: isFath.value ? formData.value.te_customer?.fathMobileMnNo : null, // 안면인증정보휴대폰번호중간자리번호
    teFathMobileRnNo: isFath.value ? formData.value.te_customer?.fathMobileRnNo : null, // 안면인증정보휴대폰번호뒷자리번호

    teAuthInfo: null, // 인증정보

    teIdentityTypeCd: formData.value.te_customer.identityTypeCd, // 신분증유형코드

    teIdentityIssuDate: formData.value.te_customer.identityIssuDate?.replaceAll(/[^0-9]/g, ''), // 신분증발급일자

    teIdentityIssuRegion: formData.value.te_customer.identityIssuRegion, // 신분증발급지역

    teSelfIssuNo: formData.value.te_customer?.driveLicnsNo, // 발급번호

    teDriveLicnsNo: formData.value.te_customer.driveLicnsNo, // 운전면허번호

    reqInfoChgYn: 'N', // 가입정보변경여부

    soc: currentPlanUse.value
      ? formData.value.planInfo.orgProdId
      : formData.value.planInfo.planName2, // 요금제
    socNm: currentPlanUse.value
      ? formData.value.planInfo.orgProdNm
      : formData.value.planInfo.planNm, // 요금제명
    socBaseChrgAmt: currentPlanUse.value
      ? formData.value.planInfo.orgPlanAmt
      : formData.value.planInfo.planAmt, // 요금제기본료
    planSelectType: formData.value.planInfo.planSelectType, // 요금제 선택 타입

    jehuProdTypeCd: formData.value.planInfo.jehuProdTypeCd, // 요금제제휴처코드
    prdtSctnCd: formData.value.planInfo.prdtSctnCd, // 요금제제휴처코드
    plcySctnCd: formData.value.planInfo.plcySctnCd, // 요금제정책코드
    dataType: formData.value.planInfo.dataType, // 데이터타입(LTE, 5G)
    simTypeCd: formData.value.usimInfo.simTypeCd,
    usimSuccYn:
      formData.value.usimInfo.simTypeCd === 'ESIM'
        ? 'Y'
        : formData.value.usimInfo.hasSim === false
          ? 'N'
          : 'Y', // USIM승계여부

    usimSn: formData.value.usimInfo.reqUsimSn, // USIM번호
    usimNm: formData.value.usimInfo.reqUsimNm,
    iccId: formData.value.usimInfo.simTypeCd === 'ESIM' ? null : formData.value.usimInfo.reqUsimSn, // ICCID
    simPurchaseMethod: formData.value.usimInfo.simPurchaseMethod, // USIM 구매방식
    eid: formData.value.usimInfo.eid, // EID
    imei1: formData.value.usimInfo.imei1, // IMEI1
    imei2: formData.value.usimInfo.imei2, // IMEI2
    esimPhoneId: formData.value.usimInfo.prodNm, // eSIM휴대폰모델ID

    uploadPhoneSrlNo: null, // 업로드휴대폰일련번호
    remainPayDivCd: null, // 완납/승계구분코드

    clauseCntrDelYn: 'Y', // 양도인고객정보삭제동의여부
    clausePriCollectYn: 'Y', // 약관개인정보수집동의여부
    clausePriOfferYn: 'Y', // 약관개인정보제공동의여부
    clauseEssCollectYn: 'Y', // 약관고유식별정보수집이용제공동의여부
    clauseConfidenceYn: 'Y', // 약관신용정보이용동의여부
    indvLocaPrvAgreeYn: formData.value.indvLocaPrvAgreeYn, // 개인위치정보제3자제공동의여부
    cnsgInfoAdvrRcvAgreYn: formData.value.personalInfoCollectAgreeYn ?? 'N', // 약관개인정보광고전송동의여부
    /** 고객 혜택 제공 **/
    clausePriAdYn: formData.value.clausePriTrustYn ?? 'N', // 약관개인정보광고전송동의여부
    clausePriTrustYn: formData.value.clausePriTrustYn ?? 'N', // 약관개인정보광고전송동의여부, // 약관 개인정보위탁동의여부
    personalInfoCollectAgreeYn: formData.value.personalInfoCollectAgreeYn ?? 'N', // 고객혜택제공을위한개인정보수집및이용관련동의여부
    /** 고객 혜택 제공 **/
    clauseJehuYn: formData.value.planInfo.jehuProdTypeCd ? 'Y' : 'N', // 제휴서비스동의여부
    clauseFinanceYn: 'N', // 금융제휴약관동의여부
    /** 혜택 제공 **/
    othersTrnsAllAgreeYn: formData.value.othersTrnsAllAgreeYn, // 혜택 제공을 위한 제3자 제공 동의
    othersAdReceiveAgreeYn: formData.value.othersAdReceiveAgreeYn, // 제3자제공관련광고수신동의여부
    /** 혜택 제공 **/
    clause5gCoverageYn: formData.value.clause5gCoverageYn, // 5G커버리지확인및가입동의여부
    nwBlckAgrmYn: formData.value.nwBlckAgrmYn, // 네트워크차단동의여부
    appBlckAgrmYn: formData.value.appBlckAgrmYn, // 청소년유해매체차단동의여부

    clauseFathYn: 'Y', // 안면인증동의여부

    // mcnStatRsnCd: 'RCMCMCN', // 명변사유코드

    memo: formData.value.memo, // 메모

    mcnStateCd: null, // 진행상태코드
    rcvCustNo: null, // 양수인고객번호
    rcvBillAcntNo: null, // 양수인청구번호

    recYn: formData.value.fileInfo?.recYn, // 녹취여부
    recFileNm: formData.value.fileInfo?.recFileNm,
    recFilePathNm: formData.value.fileInfo?.recFilePathNm,

    resCd: null, // 예약등록코드
    resMsg: null, // 예약등록메세지

    scanId: documentId.value?.[0], // 리포트문서ID

    appFormYn: 'N', // 스캔이미지여부
    appFormXmlYn: 'N', // 서식지XML여부

    fileNm: formData.value.fileInfo?.fileNm, // 파일명
    fileMaskNm: formData.value.fileInfo?.fileMaskNm, // 마스크파일명
    /* MSF_REQEST_NAME_CHG */

    /* MSF_REQUEST_CSTMR */
    cstmrNm: formData.value.te_customer.cstmrNm, // 고객명
    cstmrNativeRrn: concatStrings([
      formData.value.te_customer.cstmrNativeRrn1,
      formData.value.te_customer.cstmrNativeRrn2,
    ]), // 고객정보내국인주민등록번호
    cstmrNativeBirth: extractYYYYMMDDRrn(
      concatStrings([
        formData.value.te_customer.cstmrNativeRrn1,
        formData.value.te_customer.cstmrNativeRrn2,
      ]),
    ), // 고객정보내국인생년월일
    cstmrNativeGenderCd: getGenderByRrnBack(formData.value.te_customer.cstmrNativeRrn2), // 고객정보내국인성별
    cstmrPrivateCname: null, // 고객정보개인사업자상호명
    cstmrPrivateBizNo: concatStrings([
      formData.value.te_customer.cstmrJuridicalBizNo1,
      formData.value.te_customer.cstmrJuridicalBizNo2,
      formData.value.te_customer.cstmrJuridicalBizNo3, // 개인사업자사업자등록번호
    ]),
    cstmrForeignerRrn: concatStrings([
      formData.value.te_customer?.cstmrForeignerRrn1,
      formData.value.te_customer?.cstmrForeignerRrn2, // 고객정보외국인외국인등록번호
    ]),
    cstmrForeignerBirth: extractYYYYMMDDRrn(
      concatStrings([
        formData.value.te_customer.cstmrForeignerRrn1,
        formData.value.te_customer.cstmrForeignerRrn2,
      ]),
    ), // 고객정보외국인생년월일
    cstmrForeignerGenderCd: getGenderByRrnBack(formData.value.te_customer.cstmrForeignerRrn2), // 고객정보외국인성별
    cstmrForeignerPn: null, // 고객정보외국인여권번호
    cstmrForeignerCountryCd: formData.value.te_customer?.country, // 고객정보외국인국가코드
    cstmrForeignerNation: formData.value.te_customer?.country, // 고객정보외국인국적
    cstmrForeignerVisaNo: formData.value.te_customer?.visaType, // 고객정보외국인비자번호
    cstmrForeignerVdateStartDate: extractDigits(
      formData.value.te_customer?.cstmrForeignerVdateStartDate,
    ), // 고객정보외국인체류기간시작일자
    cstmrForeignerVdateEndDate: extractDigits(
      formData.value.te_customer?.cstmrForeignerVdateEndDate,
    ), // 고객정보외국인체류기간종료일자
    cstmrJuridicalCname: null, // 고객정보법인사업자법인명
    cstmrJuridicalRrn: concatStrings([
      formData.value.te_customer?.cstmrJuridicalRrn1,
      formData.value.te_customer?.cstmrJuridicalRrn2,
    ]), // 고객정보법인사업자법인번호
    cstmrJuridicalBizNo: concatStrings([
      formData.value.te_customer?.cstmrJuridicalBizNo1,
      formData.value.te_customer?.cstmrJuridicalBizNo2,
      formData.value.te_customer?.cstmrJuridicalBizNo3,
    ]), // 고객정보법인사업자사업자등록번호
    cstmrJuridicalRepNm: formData.value.te_customer?.cstmrJuridicalRepNm, // 고객정보법인대표자명
    cstmrJuridicalBizNoIssuDate: extractDigits(
      formData.value.te_customer?.cstmrJuridicalBizNoIssuDate,
    ), //교부일자
    cstmrPrivateBizNoIssuDate: extractDigits(formData.value.te_customer?.cstmrPrivateBizNoIssuDate), //사업자 발급일자
    upjnCd: formData.value.te_customer?.upjnCd, // 업종코드
    bcuSbst: formData.value.te_customer?.bcuSbst, // 업태내용
    cstmrJuridicalUserNm: formData.value.te_customer.realUserName, // 법인실사용자명
    cstmrJuridicalBirth: formData.value.te_customer.realUserBirthDate, // 법인실사용자생년월일
    cstmrVisitTypeCd: formData.value.te_customer?.cstmrVisitTypeCd, // 방문고객유형코드
    cstmrTelFnNo: formData.value.te_customer?.telNo1, // 고객정보전화번호앞자리번호
    cstmrTelMnNo: formData.value.te_customer?.telNo2, // 고객정보전화번호가운데자리번호
    cstmrTelRnNo: formData.value.te_customer?.telNo3, // 고객정보전화번호끝자리번호
    cstmrTelNo: concatStrings([
      formData.value.te_customer?.telNo1,
      formData.value.te_customer?.telNo2,
      formData.value.te_customer?.telNo3,
    ]), // 고객정보전화번호
    cstmrMobileFnNo: formData.value.te_customer?.mobileNo1, // 고객정보휴대폰번호앞자리번호
    cstmrMobileMnNo: formData.value.te_customer?.mobileNo2, // 고객정보휴대폰번호중간자리번호
    cstmrMobileRnNo: formData.value.te_customer?.mobileNo3, // 고객정보휴대폰번호끝자리번호
    cstmrMobileNo: concatStrings([
      formData.value.te_customer?.cstmrMobileFnNo,
      formData.value.te_customer?.cstmrMobileMnNo,
      formData.value.te_customer?.cstmrMobileRnNo,
    ]), // 고객정보휴대폰번호
    cstmrZipcd: formData.value.te_customer?.zipNo, // 고객정보우편번호
    cstmrAdr: formData.value.te_customer?.address, // 고객정보주소
    cstmrAdrDtl: formData.value.te_customer?.detailAddress, // 고객정보상세주소
    cstmrAdrBjd: null, // 고객정보법정동주소
    cstmrEmailAdr: concatStrings(
      [formData.value.te_customer?.emailAddr1, formData.value.te_customer?.emailAddr2],
      '@',
    ), // 고객정보이메일
    cstmrEmailReceiveYn: 'N', // 고객정보이메일수신여부
    cstmrReceiveTelNo: concatStrings([
      formData.value.te_customer?.mobileNo1,
      formData.value.te_customer?.mobileNo2,
      formData.value.te_customer?.mobileNo3,
    ]),
    cstmrReceiveTelFnNo: formData.value.te_customer?.mobileNo1, // 고객정보연락받을번호앞자리번호
    cstmrReceiveTelNmNo: formData.value.te_customer?.mobileNo2, // 고객정보연락번호중간자리번호
    cstmrReceiveTelRnNo: formData.value.te_customer?.mobileNo3, // 고객정보연락받을번호끝자리번호
    /* MSF_REQUEST_CSTMR */

    /* MSF_REQUEST_BILL_REQ */
    reqPayTypeCd: formData.value.productPayment.reqPayTypeCd, // 요금납부방법유형코드
    reqBankCd: formData.value.productPayment.reqBankCd, // 신청정보계좌이체은행코드
    reqAccountNm: othersPaymentYn.value
      ? formData.value.productPayment.reqAccountNm
      : formData.value.te_customer.cstmrNm, // 계좌예금주명
    reqAccountRrn: formData.value.productPayment.reqAccountRrn, // 신청정보계좌이체예금주주민번호
    reqAccountRelTypeCd: formData.value.productPayment.reqAccountRelTypeCd, // 신청정보계좌이체예금주와관계유형코드
    reqAccountNo: formData.value.productPayment.reqAccountNo, // 계좌번호
    reqCardNm: othersPaymentYn.value
      ? formData.value.productPayment.reqCardNm
      : isTeMinor.value
        ? formData.value?.te_customer?.repName
        : formData.value.te_customer?.cstmrNm, // 신용카드명의자명
    reqCardRrn: formData.value.productPayment.reqCardRrn, // 신청정보신용카드명의자주민번호
    reqCardCompanyCd: formData.value.productPayment.reqCardCompanyCd, // 신청정보신용카드카드사코드
    reqCardNo: formData.value.productPayment.reqCardNo, // 신용카드번호
    reqCardYy: concatStrings(['20', formData.value.productPayment.reqCardYy]), // 신청정보신용카드유효년
    reqCardMm: formData.value.productPayment.reqCardMm, // 신청정보신용카드유효월
    reqWireTypeCd: null, // 무선데이터이용타입유형코드
    othersPaymentYn: formData.value.productPayment.othersPaymentYn, // 타인납부여부
    othersPaymentAgrYn: formData.value.productPayment.othersPaymentYn, // 타인납부여부
    othersPaymentTelFnNo: null, // 타인납부전화번호앞자리번호
    othersPaymentTelMnNo: null, // 타인납부전화번호중간자리번호
    othersPaymentTelRnNo: null, // 타인납부전화번호끝자리번호
    othersPaymentNm: isCard.value
      ? formData.value.productPayment.reqCardNm
      : formData.value.productPayment.reqAccountNm, // 타인납부고객명
    othersPaymentRrn: isCard.value
      ? formData.value.productPayment.reqCardRrn
      : formData.value.productPayment.reqAccountRrn, // 타인납부주민번호
    othersPaymentRelTypeCd: formData.value.productPayment.reqAccountRelTypeCd, // 타인납부명의자와의관계유형코드
    othersPaymentReqNm: formData.value.te_customer.cstmrNm, // 타인납부신청인명
    prntsBillNo: null, // 모회선청구번호
    cstmrBillSendTypeCd: formData.value.productPayment.cstmrBillSendTypeCd, // 명세서종류유형코드
    /* MSF_REQUEST_BILL_REQ */

    /* MSF_REQUEST_AGENT */
    minorAgentNm: formData.value.te_customer?.minorAgentNm, // 미성년자법정대리인성명
    minorAgentRrn: !isTeForeigner.value
      ? concatStrings([
          formData.value.te_customer?.repRegistrationNo1,
          formData.value.te_customer?.repRegistrationNo2, // 미성년자법정대리인등록번호
        ])
      : concatStrings([
          formData.value.te_customer?.repForeignerNo1,
          formData.value.te_customer?.repForeignerNo2, // 미성년자법정대리인등록번호
        ]),
    minorAgentBirth: !isTeForeigner.value
      ? extractYYYYMMDDRrn(
          concatStrings([
            formData.value.te_customer?.repRegistrationNo1,
            formData.value.te_customer?.repRegistrationNo2,
          ]),
        )
      : extractYYYYMMDDRrn(
          concatStrings([
            formData.value.te_customer?.repForeignerNo1,
            formData.value.te_customer?.repForeignerNo2,
          ]),
        ), // 미성년자법정대리인생년월일
    minorAgentGenderCd: isTeForeigner.value
      ? getGenderByRrnBack(formData.value.te_customer?.repForeignerNo2)
      : getGenderByRrnBack(formData.value.te_customer?.repRegistrationNo2), // 미성년자법정대리인성별
    minorAgentRelTypeCd: formData.value.te_customer?.minorAgentRelTypeCd, // 미성년자법정대리인관계유형코드
    minorAgentTelFnNo: formData.value.te_customer?.minorAgentTelFnNo, // 미성년자법정대리인연락처앞자리번호
    minorAgentTelMnNo: formData.value.te_customer?.minorAgentTelMnNo, // 미성년자법정대리인연락처중간자리번호
    minorAgentTelRnNo: formData.value.te_customer?.minorAgentTelRnNo, // 미성년자법정대리인끝자리번호
    minorAgentTelNo: concatStrings([
      formData.value.te_customer?.minorAgentTelFnNo,
      formData.value.te_customer?.minorAgentTelMnNo,
      formData.value.te_customer?.minorAgentTelRnNo,
    ]), // 미성년자법정대리인번호
    minorAgentAgrmYn: 'Y', // 미성년자법정대리인안내사항및동의여부
    minorAgentSelfInqryAgrmYn: 'Y', // 미성년자법정대리인본인인증조회동의여부
    minorAgentSelfCertTypeCd: formData.value.te_customer.identityCertTypeCd, // 미성년자법정대리인본인인증유형코드
    minorAgentCiInfo: null, // 미성년자법정대리인CI정보
    jrdclAgentNm: formData.value.juridical?.minorAgentNm, // 법인대리인명
    jrdclAgentGender: formData.value.juridical?.agentGender, // 법인대리인 성별
    jrdclAgentRrn: concatStrings([
      formData.value.juridical?.agentBirthDate,
      // formData.value.te_customer?.cstmrJuridicalRrn2, // 법인대리인등록번호
    ]),
    jrdclAgentRelTypeCd: formData.value.juridical?.minorAgentRelTypeCd, // 법정대리인관계유형코드
    jrdclAgentTelFnNo: formData.value.juridical?.minorAgentTelFnNo, // 법인대리인연락처앞자리번호
    jrdclAgentTelMnNo: formData.value.juridical?.minorAgentTelMnNo, // 법인대리인연락처중간자리번호
    jrdclAgentTelRnNo: formData.value.juridical?.minorAgentTelRnNo, // 법인대리인연락처끝자리번호
    jrdclAgentTelNo: concatStrings([
      formData.value.juridical?.minorAgentTelFnNo,
      formData.value.juridical?.minorAgentTelMnNo,
      formData.value.juridical?.minorAgentTelRnNo,
    ]), // 법인대리인연락처끝자리번호
    /* MSF_REQUEST_AGENT */
    /* MSF_REQUEST_DOC */
    msfRequestDocList: (formData.value.te_customer.msfRequestDocList || []).map((doc) => ({
      fileTypeCd: doc.fileTypeCd,
      filePathNm: doc.filePathNm || '',
      fileNm: doc.fileNm || '',
      filePageNo: doc.filePageNo !== undefined ? Number(doc.filePageNo) : 1,
    })),
    /* MSF_REQUEST_DOC */
    /* MSF_REQUEST_REC */

    /* MSF_REQUEST_DOC */
  })

  watch(
    () => formData.value.tr_customer.cstmrTypeCd,
    (newVal, oldVal) => {
      if (newVal === oldVal) return

      handleTrCustomerTypeChange(newVal)
    },
  )

  watch(
    () => formData.value.te_customer.cstmrTypeCd,
    (newVal, oldVal) => {
      if (newVal === oldVal) return

      handleTeCustomerTypeChange(newVal)
    },
  )

  const handleTrCustomerTypeChange = (typeCd) => {
    resetTrCustomer()

    formData.value.tr_customer.cstmrTypeCd = typeCd

    const minorCd = ['NM', 'FM']

    if (minorCd.includes(typeCd)) {
      formData.value.te_customer.cstmrTypeCd = typeCd
    }
  }

  const handleTeCustomerTypeChange = (typeCd) => {
    resetTeCustomer()

    formData.value.te_customer.cstmrTypeCd = typeCd
  }

  // ✨ cstmrTypeCd의 변화에 반응하는 computed 타이틀
  const trCustomerTitle = computed(() => {
    const type = formData.value.tr_customer.cstmrTypeCd
    const minor = ['NM', 'FM']
    const dynamicTitle = {
      identityVerifyTitle: !minor.includes(type)
        ? '고객(양도고객) 신분증 확인'
        : '고객(양도고객) 법정대리인 신분증 확인',
      subscriberInfo: !minor.includes(type)
        ? '고객(양도고객) 정보'
        : '고객(양도고객) 미성년자 정보',
    }
    return dynamicTitle
  })

  const teCustomerTitle = computed(() => {
    const type = formData.value.te_customer.cstmrTypeCd
    const minor = ['NM', 'FM']
    const dynamicTitle = {
      identityVerifyTitle: !minor.includes(type)
        ? '고객(양수고객) 신분증 확인'
        : '고객(양수고객) 법정대리인 신분증 확인',
      subscriberInfo: !minor.includes(type)
        ? '고객(양수고객) 정보'
        : '고객(양수고객) 미성년자 정보',
    }
    return dynamicTitle
  })

  // 작성완료 API
  const apiCompleteApplication = async () => {
    try {
      const payload = buildCompletePayload()
      console.log('[payload] =========================================')
      console.log(payload)

      const data = await post(`/api/form/owner-change/form/save`, payload)
      console.debug('[apiCompleteApplication] response', data)
      if (data?.data?.success) {
        completeErrorMessage.value = ''
        applicationKey.value = data.data?.requestKey
        console.info('[apiCompleteApplication] success', { applicationNo: data?.applicationNo })
        return true
      }
      completeErrorMessage.value = data?.message || DEFAULT_ERROR_MESSAGE
      console.warn('[apiCompleteApplication] failed response', data)
      return false
    } catch (e) {
      completeErrorMessage.value = e?.response?.data?.message || DEFAULT_ERROR_MESSAGE
      console.error('[apiCompleteApplication] exception', {
        message: e?.message,
        status: e?.response?.status,
        response: e?.response?.data,
      })
      return false
    }
  }

  const onlyBirthCheck = (targetCustomer, front, isRep) => {
    let isMinor = ['NM', 'FM'].includes(targetCustomer.cstmrTypeCd)
    const isTr = targetCustomer.isTrCustomer
    let msg = isTr ? '양도인' : '양수인'

    if (isRep) {
      isMinor = !isRep
      msg = msg + ' 법정대리인'
    }

    const yy = parseInt(front.substring(0, 4), 10)
    const mm = parseInt(front.substring(4, 6), 10) - 1
    const dd = parseInt(front.substring(6, 8), 10)

    const birthDate = new Date(yy, mm, dd)
    if (
      birthDate.getFullYear() !== yy ||
      birthDate.getMonth() !== mm ||
      birthDate.getDate() !== dd
    ) {
      showAlert(msg + ' ' + '유효하지 않은 생년월일입니다.')
      return false
    }

    // 만 나이 계산
    const today = new Date()
    let age = today.getFullYear() - birthDate.getFullYear()
    if (today.getMonth() < mm || (today.getMonth() === mm && today.getDate() < dd)) {
      age--
    }

    if (age < 0 || age > 100) {
      showAlert(msg + ' ' + '생년월일이 현재 날짜 기준 유효하지 않습니다.')
      return false
    }

    const isAdult = age >= 19

    //console.log('onlyBirthCheck', front, isMinor, age, isAdult, isMinor, isRep)
    // 2. 미성년자 체크박스 상태와 실제 계산된 성인 여부 비교
    if (isMinor) {
      // 미성년자라고 체크했는데, 계산 결과 성인인 경우
      if (isAdult) {
        showAlert(msg + ' ' + '미성년자 체크를 하셨으나, 입력하신 정보는 성인입니다.')
        return false
      }
    } else {
      // 일반(성인) 상태인데, 계산 결과 미성년자인 경우
      if (!isAdult) {
        showAlert(`${msg}이 미성년자입니다. 생년월일을 확인해주세요.`)
        return false
      }
    }

    return true
  }

  const birthCheck = (targetCustomer) => {
    const isForeignerSelected = ['FN', 'FM'].includes(targetCustomer.cstmrTypeCd)
    const isMinor = ['NM', 'FM'].includes(targetCustomer.cstmrTypeCd)
    const isTr = targetCustomer.isTrCustomer
    const msg = isTr ? '양도인' : '양수인'
    const front = isForeignerSelected
      ? targetCustomer?.cstmrForeignerRrn1
      : targetCustomer?.cstmrNativeRrn1
    const back = isForeignerSelected
      ? targetCustomer?.cstmrForeignerRrn2
      : targetCustomer?.cstmrNativeRrn2

    console.log('-----------', front, back, isForeignerSelected)

    // 1. 국적 일치 여부 및 날짜 포맷 기본 검증
    const result = verifyJuminAndAge(front, back, isForeignerSelected)
    if (!result.isValid) {
      showAlert(msg + ' ' + result.message) // "외국인 선택 시 내국인 번호는 사용할 수 없습니다" 등이 출력됨
      return false
    }

    // 2. 미성년자 체크박스 상태와 실제 계산된 성인 여부 비교
    if (isMinor) {
      // 미성년자라고 체크했는데, 계산 결과 성인인 경우
      if (result.isAdult) {
        showAlert(msg + ' ' + '미성년자 체크를 하셨으나, 입력하신 정보는 성인입니다.')
        return false
      }
    } else {
      // 일반(성인) 상태인데, 계산 결과 미성년자인 경우
      if (!result.isAdult) {
        showAlert(`${msg}이 미성년자입니다. 미성년자 체크를 확인해주세요.`)
        return false
      }
    }

    return true
  }

  /**
   * 주민등록번호 기반 성인 및 내/외국인 일치 여부 검증
   * @param {string} front - 주민번호 앞 6자리
   * @param {string} back - 주민번호 뒤 7자리
   * @param {boolean} isForeignerSelected - 화면에서 사용자가 '외국인'을 선택했는지 여부
   */
  const verifyJuminAndAge = (front, back, isForeignerSelected) => {
    if (front.length !== 6 || back.length < 1) {
      return { isValid: false, message: '주민등록번호 형식을 확인해주세요.' }
    }

    const backFirst = back[0]
    const yy = parseInt(front.substring(0, 2), 10)
    const mm = parseInt(front.substring(2, 4), 10) - 1
    const dd = parseInt(front.substring(4, 6), 10)

    let century = 1900
    let isForeignerNumber = false // 주민번호 자체로 판단한 외국인 여부

    switch (backFirst) {
      case '1':
      case '2':
        century = 1900
        isForeignerNumber = false
        break
      case '3':
      case '4':
        century = 2000
        isForeignerNumber = false
        break
      case '5':
      case '6':
        century = 1900
        isForeignerNumber = true
        break // 외국인등록번호
      case '7':
      case '8':
        century = 2000
        isForeignerNumber = true
        break // 외국인등록번호
      default:
        return { isValid: false, message: '올바르지 않은 주민등록번호 뒷자리입니다.' }
    }

    // [핵심 차단 로직] 사용자가 선택한 국적 정보와 입력한 주민번호 종류가 다를 때
    if (isForeignerSelected !== isForeignerNumber) {
      if (isForeignerSelected) {
        return {
          isValid: false,
          message: '외국인 선택 시 내국인 주민등록번호는 사용할 수 없습니다.',
        }
      } else {
        return { isValid: false, message: '내국인 선택 시 외국인등록번호는 사용할 수 없습니다.' }
      }
    }

    // 날짜 유효성 검증
    const birthDate = new Date(century + yy, mm, dd)
    if (
      birthDate.getFullYear() !== century + yy ||
      birthDate.getMonth() !== mm ||
      birthDate.getDate() !== dd
    ) {
      return { isValid: false, message: '유효하지 않은 생년월일입니다.' }
    }

    // 만 나이 계산
    const today = new Date()
    let age = today.getFullYear() - birthDate.getFullYear()
    if (today.getMonth() < mm || (today.getMonth() === mm && today.getDate() < dd)) {
      age--
    }

    if (age < 0 || age > 100) {
      return { isValid: false, message: '생년월일이 현재 날짜 기준 유효하지 않습니다.' }
    }

    return {
      isValid: true,
      isAdult: age >= 19,
      age: age,
    }
  }

  const fieldRules = computed(() => {
    return {
      tr_customer: {
        cstmrTypeCd: { is: true, msg: '고객유형을 선택해주세요.' },
        /** 고객(양도고객)신분증 확인 */
        identityTypeCd: { is: !isTrGovernment.value, msg: '양도인 신분증 종류를 선택해주세요.' },
        // identityTypeNm: { is: false, msg: '신분증 이름 정보가 없습니다.' },
        identityIssuDate: {
          is: false,
          msg: '양도고객 신분증 발급일자를 입력해주세요.',
        },
        driveLicnsNo: { is: false, msg: '양도고객 운전면허 번호를 입력해주세요.' }, // 주민증/면허증 분기에 따라 true 처리
        identityIssuRegion: { is: false, msg: '양도고객 면허 발급 지역을 선택해주세요.' },
        cstmrJuridicalRrn1: { is: false, msg: '양도고객 법인등록번호 앞자리를 입력해주세요.' },
        cstmrJuridicalRrn2: { is: false, msg: '양도고객 법인등록번호 뒷자리를 입력해주세요.' },
        cstmrJuridicalRepNm: { is: false, msg: '양도고객 법인 대표자명을 입력해주세요.' },
        cstmrJuridicalBizNoIssuDate: { is: false, msg: '양도고객 교부일자를 입력해주세요.' },
        cstmrPrivateBizNoIssuDate: { is: false, msg: '양도고객 발급일자를 입력해주세요.' },
        upjnCd: { is: false, msg: '양도고객 업종 코드를 입력해주세요.' },
        bcuSbst: { is: false, msg: '양도고객 업태 명을 입력해주세요.' },
        deviceChgTel1: { is: true, msg: '양도고객 연락처 앞자리를 선택해주세요.' },
        deviceChgTel2: { is: true, msg: '양도고객 연락처 가운데 자리를 입력해주세요.' },
        deviceChgTel3: { is: true, msg: '양도고객 연락처 뒷자리를 입력해주세요.' },
        /* 법정대리인 정보 (미성년자 가입 시 분기 필요) */
        repName: { is: isTrMinor.value, msg: '양도고객 법정대리인 이름을 입력해주세요.' },
        minorAgentNm: { is: false, msg: '양도고객 위임받은 대리인 이름을 입력해주세요.' },
        minorAgentRelTypeCd: { is: false, msg: '신청인과의 관계를 선택해주세요.' },
        minorAgentTelFnNo: { is: isTrMinor.value, msg: '대리인 연락처 첫 자리를 입력해주세요.' },
        minorAgentTelMnNo: {
          is: isTrMinor.value,
          msg: '대리인 연락처 가운데 자리를 입력해주세요.',
        },
        minorAgentTelRnNo: {
          is: isTrMinor.value,
          msg: '대리인 연락처 마지막 자리를 입력해주세요.',
        },
        repRegistrationNo1: { is: false, msg: '법정대리인 주민번호 앞자리를 입력해주세요.' },
        repRegistrationNo2: { is: false, msg: '법정대리인 주민번호 뒷자리를 입력해주세요.' },
        repForeignerNo1: { is: false, msg: '법정대리인 외국인등록번호 앞자리를 입력해주세요.' },
        repForeignerNo2: { is: false, msg: '법정대리인 외국인등록번호 뒷자리를 입력해주세요.' },
        minorUserBirthDate: {
          is: ['NM', 'FM'].includes(formData.value.tr_customer.cstmrTypeCd),
          // msg: '미성년 대리인 사용자 생년월일을 입력해주세요.',
          validate: () =>
            onlyBirthCheck(
              formData.value.tr_customer,
              formData.value.tr_customer.minorUserBirthDate,
              true,
            ),
        },
        minorUserGender: { is: false, msg: '미성년 사용자 성별을 선택해주세요.' },
        repAgree: { is: false, msg: '법정대리인 동의가 필요합니다.' },
        /** 고객(양도고객)정보 */
        cstmrNm: { is: true, msg: '양도고객 이름을 입력해주세요.' },
        cstmrNativeRrn1: { is: false, msg: '내국인 주민등록번호 앞자리를 입력해주세요.' },
        cstmrNativeRrn2: { is: false, msg: '내국인 주민등록번호 뒷자리를 입력해주세요.' },
        cstmrForeignerRrn1: { is: false, msg: '외국인 등록번호 앞자리를 입력해주세요.' },
        cstmrForeignerRrn2: { is: false, msg: '외국인 등록번호 뒷자리를 입력해주세요.' },
        userBirthDate: {
          is: !['JP', 'GO'].includes(formData.value.tr_customer.cstmrTypeCd),
          // msg: '양도인 생년월일을 입력해주세요.',
          //validate: () => birthCheck(formData.value.tr_customer),
          validate: () =>
            onlyBirthCheck(
              formData.value.tr_customer,
              formData.value.tr_customer.userBirthDate,
              false,
            ),
        },
        userGender: { is: true, msg: '성별을 선택해주세요.' },
        // isVerified: { is: true, msg: '고객 본인인증이 필요합니다.' },
        isScanVerified: { is: false, msg: '신분증 스캔 진위확인이 필요합니다.' },
      },
      te_customer: {
        /* 고객(양수)정보 */
        cstmrTypeCd: { is: true, msg: '양수고객 유형을 선택해주세요.' },
        cstmrNm: { is: true, msg: '양수고객 이름을 입력해주세요.' },
        cstmrNativeRrn1: {
          is: !isTeForeigner.value && !isTeGovernment.value,
          msg: '양수고객 주민번호 앞자리를 입력해주세요.',
        },
        cstmrNativeRrn2: {
          is: !isTeForeigner.value && !isTeGovernment.value,
          // msg: '양수고객 주민번호 뒷자리를 입력해주세요.',
          validate: () => birthCheck(formData.value.te_customer),
        },
        cstmrForeignerRrn1: {
          is: isTeForeigner.value && !isTeGovernment.value,
          msg: '양수고객 외국인번호 앞자리를 입력해주세요.',
        },
        cstmrForeignerRrn2: {
          is: isTeForeigner.value && !isTeGovernment.value,
          // msg: '양수고객 외국인번호 뒷자리를 입력해주세요.',
          validate: () => birthCheck(formData.value.te_customer),
        },
        upjnCd: { is: false, msg: '양수법인 업종코드를 입력해주세요.' },
        bcuSbst: { is: false, msg: '양수법인 업태명을 입력해주세요.' },
        deviceChgTel1: { is: false, msg: '양수고객 휴대폰 첫 자리를 선택해주세요.' },
        deviceChgTel2: { is: false, msg: '양수고객 휴대폰 가운데 자리를 입력해주세요.' },
        deviceChgTel3: { is: false, msg: '양수고객 휴대폰 마지막 자리를 입력해주세요.' },
        /** 고객(양수고객)신분증 확인 */
        identityCertTypeCd: { is: false, msg: '양수인 신분증 인증 유형을 선택해주세요.' },
        identityTypeCd: { is: !isTeGovernment.value, msg: '양수인 신분증 종류를 선택해주세요.' },
        identityIssuDate: {
          is: !isTeGovernment.value,
          msg: '양수인 신분증 발급일자를 입력해주세요.',
        },
        identityIssuRegion: { is: false, msg: '양수인 면허 발급지역을 선택해주세요.' },
        driveLicnsNo: { is: false, msg: '양수인 면허번호를 입력해주세요.' },
        cstmrJuridicalRrn1: { is: false, msg: '양수법인 등록번호 앞자리를 입력해주세요.' },
        cstmrJuridicalRrn2: { is: false, msg: '양수법인 등록번호 뒷자리를 입력해주세요.' },
        cstmrJuridicalRepNm: { is: false, msg: '양수법인 대표자명을 입력해주세요.' },
        cstmrJuridicalBizNoIssuDate: { is: false, msg: '교부일자를 입력해주세요.' },
        cstmrPrivateBizNoIssuDate: { is: false, msg: '발급일자를 입력해주세요.' },
        cstmrVisitTypeCd: { is: false, msg: '방문 유형을 선택해주세요.' },
        /* 법정대리인 정보 */
        repName: { is: isTeMinor.value, msg: '양수 대리인 이름을 입력해주세요.' },
        minorAgentNm: { is: false, msg: '양수 위임 대리인 이름을 입력해주세요.' },
        minorAgentRelTypeCd: { is: isTeMinor.value, msg: '양수 신청인과의 관계를 선택해주세요.' },
        minorAgentTelFnNo: { is: false, msg: '양수 대리인 연락처 앞자리를 입력해주세요.' },
        minorAgentTelMnNo: {
          is: isTeMinor.value,
          msg: '양수 대리인 연락처 가운데 자리를 입력해주세요.',
        },
        minorAgentTelRnNo: {
          is: isTeMinor.value,
          msg: '양수 대리인 연락처 마지막 자리를 입력해주세요.',
        },
        repRegistrationNo1: { is: false, msg: '양수 대리인 주민번호 앞자리를 입력해주세요.' },
        repRegistrationNo2: { is: false, msg: '양수 대리인 주민번호 뒷자리를 입력해주세요.' },
        repForeignerNo1: { is: false, msg: '양수 대리인 외국인번호 앞자리를 입력해주세요.' },
        repForeignerNo2: { is: false, msg: '양수 대리인 외국인번호 뒷자리를 입력해주세요.' },
        minorUserBirthDate: { is: false, msg: '양수 미성년자 생년월일을 입력해주세요.' },
        minorUserGender: { is: false, msg: '양수 미성년자 성별을 선택해주세요.' },
        repAgree: { is: false, msg: '양수 법정대리인 동의가 필요합니다.' },
        /** 고객(양수고객) 연락처 */
        mobileNo1: { is: true, msg: '연락받을 휴대폰 번호 앞자리를 선택해주세요.' },
        mobileNo2: { is: true, msg: '연락받을 휴대폰 번호 가운데 자리를 입력해주세요.' },
        mobileNo3: { is: true, msg: '연락받을 휴대폰 번호 마지막 자리를 입력해주세요.' },
        telNo1: { is: false, msg: '일반 전화번호 앞자리를 입력해주세요.' },
        telNo2: { is: false, msg: '일반 전화번호 가운데 자리를 입력해주세요.' },
        telNo3: { is: false, msg: '일반 전화번호 마지막 자리를 입력해주세요.' },
        // emailAddr1: { is: isEmailRequired.value, msg: '이메일 아이디를 입력해주세요.' },
        // emailAddr2: { is: isEmailRequired.value, msg: '이메일 도메인을 입력해주세요.' },
        zipNo: { is: true, msg: '우편번호를 검색해주세요.' },
        address: { is: true, msg: '주소를 입력해주세요.' },
        detailAddress: { is: true, msg: '상세 주소를 입력해주세요.' },
        country: { is: isTeForeigner.value, msg: '국가를 선택해주세요.' },
        cstmrForeignerVdateStartDate: {
          is: isTeForeigner.value,
          msg: '체류 기간 시작일자를 입력해주세요.',
        },
        cstmrForeignerVdateEndDate: {
          is: isTeForeigner.value,
          msg: '체류 기간 종료일자를 입력해주세요.',
        },
        visaType: { is: isTeForeigner.value, msg: '비자 유형을 선택해주세요.' },
        /** 대리점 관련 (보통 내부 자동 입력값이므로 기본 false 처리) **/
        agency: { is: false, msg: '대리점 정보를 입력해주세요.' },
        agentCd: { is: false, msg: '대리점 코드를 입력해주세요.' },
        agentNm: { is: false, msg: '대리점명을 입력해주세요.' },
        shopCd: { is: false, msg: '매장 코드를 입력해주세요.' },
        shopNm: { is: false, msg: '매장명을 입력해주세요.' },
        realShopNm: { is: false, msg: '실제 매장명을 입력해주세요.' },
        cpntId: { is: false, msg: '접수자 ID를 입력해주세요.' },
        cpntNm: { is: false, msg: '접수자명을 입력해주세요.' },
        cntpntShopCd: { is: false, msg: '연계 매장 코드를 입력해주세요.' },
        cntpntShopNm: { is: false, msg: '연계 매장명을 입력해주세요.' },
        managerCd: { is: false, msg: '담당자 코드를 입력해주세요.' },
        // isVerified: { is: true, msg: '양수고객 본인인증이 완료되지 않았습니다.' },
        msfRequestDocList: {
          is: false,
          msg: '구비서류를 첨부해주세요.',
          validate: () => formData.value.te_customer.msfRequestDocList?.length > 0,
        },
        isScanVerified: { is: false, msg: '양수고객 신분증 검증이 완료되지 않았습니다.' },
      },
      planInfo: {
        planName1: { is: false, msg: '첫 번째 요금제를 선택해주세요.' },
        planName2: { is: true, msg: '요금제를 선택해주세요.' },
      },
      usimInfo: {
        hasSim: { is: false, msg: 'SIM 보유 여부를 선택해주세요.' },
        usimKindsCd: { is: false, msg: 'USIM 종류를 선택해주세요.' },
        reqUsimSn: { is: hasSimFlag.value, msg: 'USIM 일련번호를 입력해주세요.' },
        simPurchaseMethod: { is: hasSimFlag.value, msg: 'USIM 구매 방식을 선택해주세요.' },
        prodNm: { is: false, msg: '휴대폰 모델명을 입력해주세요.' },
        eid: { is: false, msg: 'EID 번호를 입력해주세요.' },
        imei1: { is: false, msg: 'IMEI1 번호를 입력해주세요.' },
        imei2: { is: false, msg: 'IMEI2 번호를 입력해주세요.' },
        modelNm: { is: false, msg: '모델 코드를 입력해주세요.' },
      },
      productPayment: {
        // cstmrBillSendTypeCd: { is: true, msg: '청구서 수신유형을 선택해주세요.' },
        cstmrBillSendTypeCd: {
          is: isEmailRequired.value,
          msg: '이메일을 입력해주세요.',
          validate: () =>
            formData.value.te_customer.emailAddr1 && formData.value.te_customer.emailAddr2,
        },
        reqPayTypeCd: { is: true, msg: '납부 방법을 선택해주세요.' },
        autoPayerType: { is: false, msg: '자동이체 납부자 유형을 선택해주세요.' },
        reqBankCd: { is: isAccount.value, msg: '자동이체 은행을 선택해주세요.' },
        reqAccountNo: { is: isAccount.value, msg: '자동이체 계좌번호를 입력해주세요.' },
        reqAccountNm: {
          is: isAccount.value && othersPaymentYn.value,
          msg: '계좌 납부고객명을 입력해주세요.',
        },
        reqAccountRrn: {
          is: isAccount.value && othersPaymentYn.value,
          msg: '계좌 납부자 생년월일 8자리를 입력해주세요.',
        },
        reqAccountRelTypeCd: {
          is: isAccount.value && othersPaymentYn.value,
          msg: '관계 코드를 선택해주세요.',
        },
        isAutoAgree: { is: isAccount.value, msg: '자동이체(카드) 약관에 동의해주세요.' },
        cardPayerType: { is: false, msg: '신용카드 납부자 유형을 선택해주세요.' },
        reqCardCompanyCd: { is: isCard.value, msg: '카드사를 선택해주세요.' },
        reqCardNo: { is: isCard.value, msg: '카드번호를 입력해주세요.' },
        reqCardMm: { is: isCard.value, msg: '카드 유효기간(월)을 입력해주세요.' },
        reqCardYy: { is: isCard.value, msg: '카드 유효기간(년)을 입력해주세요.' },
        reqCardNm: {
          is: isCard.value && othersPaymentYn.value,
          msg: '카드 명의자 이름을 입력해주세요.',
        },
        reqCardRrn: {
          is: isCard.value && othersPaymentYn.value,
          msg: '카드 명의자 생년월일을 입력해주세요.',
        },
        cardRelation: {
          is: isCard.value && othersPaymentYn.value,
          msg: '카드 명의자와의 관계를 선택해주세요.',
        },
        othersPaymentYn: { is: false, msg: '타인납부 동의가 필요합니다.' },
        combId: { is: isComb.value, msg: '통합청구 계정 ID를 입력해주세요.' },
        combAgree: { is: isComb.value, msg: '통합청구 동의가 필요합니다.' },
      },
      agreement: {
        agreeCheck1: { is: true, msg: '개통정보 녹음거부 동의에 동의해주세요.' },
        agreeCheck2: { is: true, msg: ' 명의 대여 위험 안내에 동의해주세요.' },
        agreeCheck3: { is: true, msg: '통신범죄예방 안내에 동의해주세요.' },
        agreeCheck4: { is: true, msg: ' 명의도용방지 서비스(M-Safer) 안내에 동의해주세요.' },
        agreeCheck5: { is: true, msg: '판매자 확인 안내에 동의해주세요.' },
        agreeCheck6: { is: true, msg: '가입자 확인 안내에 동의해주세요.' },
        // termsAgreed: { is: true, msg: '종합 이용약관에 동의해주세요.' },
      },
      fileInfo: {
        recYn: { is: true, msg: '신청서 확인 버튼을 눌러 서명 및 녹취를 진행해주세요.' },
        recFileNm: { is: true, msg: '신청서 확인 버튼을 눌러 서명 및 녹취를 진행해주세요.' },
        recFilePathNm: { is: true, msg: '신청서 확인 버튼을 눌러 서명 및 녹취를 진행해주세요.' },
        fileNm: { is: true, msg: '신청서 확인 버튼을 눌러 서명 및 녹취를 진행해주세요.' },
        fileMaskNm: { is: true, msg: '신청서 확인 버튼을 눌러 서명 및 녹취를 진행해주세요.' },
      },
    }
  })

  // 하위 객체 필드 전체 검사 헬퍼 (selectValidate 필터 없이 재귀)
  // onInvalid(group, key)가 주어지면 검증 실패 필드를 알려줘서 호출부에서 포커스 이동에 사용할 수 있다.
  const validateSubFields = (data, rules, group, onInvalid) => {
    for (const key in rules) {
      if (!Object.prototype.hasOwnProperty.call(rules, key)) continue

      const rule = rules[key]
      const value = data?.[key]

      if (rule?.is) {
        const isEmpty =
          value === '' ||
          value === null ||
          value === undefined ||
          (typeof value === 'boolean' && !value)

        if (isEmpty) {
          showAlert(rule.msg, () => onInvalid?.(group, key))
          return false
        }

        if (rule?.validate && !rule.validate()) {
          if (rule?.msg) showAlert(rule.msg)
          onInvalid?.(group, key)
          return false
        }
      }
    }
    return true
  }

  const validateFormFields = (type, onInvalid) => {
    if (!formData.value.termsAgreed) {
      showAlert('필수 약관에 모두 동의해주세요.')
      return false
    }

    if (type === 'CUSTOMER') {
      return (
        validateSubFields(
          formData.value.tr_customer,
          fieldRules.value.tr_customer,
          'tr_customer',
          onInvalid,
        ) &&
        validateSubFields(
          formData.value.te_customer,
          fieldRules.value.te_customer,
          'te_customer',
          onInvalid,
        ) &&
        validateSubFields(formData.value.planInfo, fieldRules.value.planInfo, 'planInfo', onInvalid)
      )
    }
    if (type === 'PRODUCT') {
      return (
        validateSubFields(
          formData.value.usimInfo,
          fieldRules.value.usimInfo,
          'usimInfo',
          onInvalid,
        ) &&
        validateSubFields(
          formData.value.te_customer,
          fieldRules.value.te_customer,
          'te_customer',
          onInvalid,
        ) &&
        validateSubFields(
          formData.value.productPayment,
          fieldRules.value.productPayment,
          'productPayment',
          onInvalid,
        )
      )
    }

    if (type === 'AGREE') {
      return (
        validateSubFields(
          formData.value.tr_customer,
          fieldRules.value.tr_customer,
          'tr_customer',
          onInvalid,
        ) &&
        validateSubFields(
          formData.value.te_customer,
          fieldRules.value.te_customer,
          'te_customer',
          onInvalid,
        ) &&
        validateSubFields(
          formData.value.planInfo,
          fieldRules.value.planInfo,
          'planInfo',
          onInvalid,
        ) &&
        validateSubFields(
          formData.value.productPayment,
          fieldRules.value.productPayment,
          'productPayment',
          onInvalid,
        ) &&
        validateSubFields(
          formData.value.agreement,
          fieldRules.value.agreement,
          'agreement',
          onInvalid,
        ) &&
        validateSubFields(formData.value.fileInfo, fieldRules.value.fileInfo, 'fileInfo', onInvalid)
      )
    }

    return true
  }

  const checkFieldValidation = (type, onInvalid) => {
    // 내부 자원(formData, validationRules)을 엮어서 실행 후 true/false 반환
    // onInvalid(group, key)를 넘기면 검증 실패한 필드의 그룹/키를 전달받아 포커스 이동 등에 활용할 수 있다.
    return validateFormFields(type, onInvalid)
  }

  const resetTrCustomer = () => {
    const currentType = formData.value.tr_customer.cstmrTypeCd

    Object.assign(formData.value.tr_customer, {
      cstmrTypeCd: currentType,
      identityCertTypeCd: '',
      tr_idCard: '',
      identityTypeCd: '',
      identityTypeNm: '',
      isSaved: false,
      identityIssuDate: '',
      driveLicnsNo: '',
      identityIssuRegion: '',
      cstmrJuridicalRrn1: '',
      cstmrJuridicalRrn2: '',
      cstmrJuridicalBizNo1: '',
      cstmrJuridicalBizNo2: '',
      cstmrJuridicalBizNo3: '',
      cstmrJuridicalRepNm: '',
      upjnCd: '',
      bcuSbst: '',
      deviceChgTel1: '010',
      deviceChgTel2: '',
      deviceChgTel3: '',
      repName: '',
      minorAgentNm: '',
      minorAgentRelTypeCd: '',
      minorAgentTelFnNo: '',
      minorAgentTelMnNo: '',
      minorAgentTelRnNo: '',
      repRegistrationNo1: '',
      repRegistrationNo2: '',
      repForeignerNo1: '',
      repForeignerNo2: '',
      minorUserBirthDate: '',
      minorUserGender: 'M',
      repAgree: false,
      cstmrNm: '',
      cstmrNativeRrn1: '',
      cstmrNativeRrn2: '',
      cstmrForeignerRrn1: '',
      cstmrForeignerRrn2: '',
      userBirthDate: '',
      userGender: 'M',
      userId: '',
      isVerified: false,
      isScanVerified: false,
      esimYn: null,
      msfRequestDocList: [],
    })
  }

  const resetTeCustomer = () => {
    Object.assign(formData.value.te_customer, {
      cstmrTypeCd: 'NA',
      cstmrNm: '',
      cstmrNativeRrn1: '',
      cstmrNativeRrn2: '',
      cstmrForeignerRrn1: '',
      cstmrForeignerRrn2: '',
      upjnCd: '',
      bcuSbst: '',
      deviceChgTel1: '010',
      deviceChgTel2: '',
      deviceChgTel3: '',
      identityCertTypeCd: 'K',
      tr_idCard: '',
      identityTypeCd: '',
      identityIssuDate: '',
      identityIssuRegion: '',
      driveLicnsNo: '',
      cstmrJuridicalRrn1: null,
      cstmrJuridicalRrn2: null,
      cstmrJuridicalBizNo1: null,
      cstmrJuridicalBizNo2: null,
      cstmrJuridicalBizNo3: null,
      cstmrJuridicalRepNm: null,
      cstmrVisitTypeCd: 'VMY',
      repName: '',
      minorAgentNm: '',
      minorAgentRelTypeCd: '',
      minorAgentTelFnNo: '',
      minorAgentTelMnNo: '',
      minorAgentTelRnNo: '',
      repRegistrationNo1: '',
      repRegistrationNo2: '',
      repForeignerNo1: '',
      repForeignerNo2: '',
      minorUserBirthDate: '',
      minorUserGender: 'M',
      repAgree: false,
      realUserName: '', //실사용자 이름
      agentGender: 'M', //성별
      agentBirthDate: '', //대리인 생년월일
      userBirthDate: '',
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
      country: '',
      te_stayPeriod: '',
      visaType: '',
      userId: '',
      isVerified: false,
      isScanVerified: false,
      isSaved: false,
      msfRequestDocList: [],
    })
  }

  const resetCustomer = () => {
    resetTrCustomer()
    resetTeCustomer()

    Object.assign(formData.value.planInfo, {
      planName1: '',
      planName2: '',
      planName3: '',
      planAmt: '',
      planNm: '',
      planSelectType: 'CURRENT',
      userId: '',
      ncn: '',
      ctn: '',
      custId: '',
      orgProdId: '',
      orgPordNm: '',
      ktOrgId: '',
      jehuProdName: '',
    })

    formData.value.termsAgreed = false
    formData.value.indvLocaPrvAgreeYn = 'N'
    formData.value.personalInfoCollectAgreeYn = 'N'
    formData.value.othersAdReceiveAgreeYn = 'N'
    formData.value.othersTrnsAllAgreeYn = 'N'
    formData.value.clause5gCoverageYn = 'N'
    formData.value.nwBlckAgrmYn = 'N'
    formData.value.appBlckAgrmYn = 'N'

    authFlags.value.identityCertTypeCd = false
    authFlags.value.deviceChgTel = false
    authFlags.value.repPhone = false
    authFlags.value.requiredDocs = false
  }

  const resetProduct = () => {
    Object.assign(formData.value.usimInfo, {
      hasSim: '',
      simTypeCd: '',
      usimKindsCd: '',
      reqUsimSn: '',
      simPurchaseMethod: '',
      prodNm: '',
      eid: '',
      imei1: '',
      imei2: '',
      modelNm: '',
    })

    Object.assign(formData.value.productPayment, {
      cstmrBillSendTypeCd: '',
      reqPayTypeCd: '',
      autoPayerType: '',
      reqBankCd: '',
      reqAccountNo: '',
      reqAccountNm: '',
      reqAccountRrn: '',
      reqAccountRelTypeCd: '',
      isAutoAgree: false,
      cardPayerType: '',
      reqCardCompanyCd: '',
      reqCardNo: '',
      reqCardMm: '',
      reqCardYy: '',
      reqCardNm: '',
      reqCardRrn: '',
      cardRelation: '',
      othersPaymentYn: 'N',
      combId: '',
      combAgree: false,
    })

    formData.value.memo = ''

    authFlags.value.reqUsimSn = false
    authFlags.value.imei = false
    authFlags.value.esimImei = false
    authFlags.value.autoAcct = false
    authFlags.value.reqCardNo = false
    authFlags.value.combId = false
  }

  const resetAgreement = () => {
    formData.value.agreement.agreeCheck1 = false
    formData.value.agreement.agreeCheck2 = false
    formData.value.agreement.agreeCheck3 = false
    formData.value.agreement.agreeCheck4 = false
    formData.value.agreement.agreeCheck5 = false
    formData.value.agreement.agreeCheck6 = false
    formData.value.fileInfo.recYn = null
    formData.value.fileInfo.recFileNm = null
    formData.value.fileInfo.recFilePathNm = null
    formData.value.fileInfo.fileNm = null
    formData.value.fileInfo.fileMaskNm = null
  }

  const validateCustomer = ref(() => true)
  const validateProduct = ref(() => true)
  const validateAgreement = ref(() => true)

  const setApplicationKey = (key) => {
    applicationKey.value = key
  }

  const updateTrCustomer = (payload) => {
    if (!formData.value.tr_customer) return

    Object.assign(formData.value.tr_customer, payload)
  }

  const updateTeCustomer = (payload) => {
    if (!formData.value.te_customer) return

    Object.assign(formData.value.te_customer, payload)
  }

  const updatePlanInfo = (payload) => {
    if (!formData.value.planInfo) return

    Object.assign(formData.value.planInfo, payload)
  }

  const updateUsimInfo = (payload) => {
    if (!formData.value.planInfo) return

    Object.assign(formData.value.usimInfo, payload)
  }

  const updateAuthFlags = (payload) => {
    if (!authFlags.value) return

    Object.assign(authFlags.value, payload)
  }

  const updateTrAuthFlags = (payload) => {
    if (!trAuthFlags.value) return

    Object.assign(trAuthFlags.value, payload)
  }

  const updateProductPayment = (payload) => {
    if (!formData.value.productPayment) return

    Object.assign(formData.value.productPayment, payload)
  }

  const resetProductStep = () => {
    // 납부 정보 약관 동의 및 제3자 납부 동의 리셋
    formData.value.productPayment.isAutoAgree = false
    formData.value.productPayment.combAgree = false
    formData.value.productPayment.othersPaymentAgrYn = 'N'
    formData.value.productPayment.othersPaymentYn = 'N'

    // 납부 정보 인증 플래그 리셋
    authFlags.value.autoAcct = false
    authFlags.value.reqCardNo = false
    authFlags.value.combId = false
    authFlags.value.reqUsimSn = false
  }

  const modifyProductStep = () => {
    // 납부 정보 약관 동의 및 제3자 납부 동의 리셋
    formData.value.productPayment.isAutoAgree = false
    formData.value.productPayment.combAgree = false

    // 납부 정보 인증 플래그 리셋
    authFlags.value.autoAcct = false
    authFlags.value.reqCardNo = false
    authFlags.value.combId = false
    authFlags.value.reqUsimSn = false
  }

  function $reset() {
    Object.assign(formData.value, getInitialFormData())
    Object.assign(trAuthFlags.value, getInitialTrAuthFlags())
    Object.assign(authFlags.value, getInitialAuthFlags())
  }

  return {
    applicationKey,
    formData,
    setApplicationKey,
    updateTrCustomer,
    updateTeCustomer,
    updatePlanInfo,
    updateUsimInfo,
    updateProductPayment,
    updateAuthFlags,
    updateTrAuthFlags,
    apiCompleteApplication,
    buildCompletePayload,
    trCustomerTitle,
    teCustomerTitle,
    authFlags,
    trAuthFlags,
    validateCustomer,
    validateProduct,
    validateAgreement,
    checkFieldValidation,
    resetCustomer,
    resetProduct,
    resetAgreement,
    resetProductStep,
    modifyProductStep,
    $reset,
    documentId,
    initForm,
    verifyJuminAndAge,
  }
})
