import { defineStore } from 'pinia'
import { ref, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

// 유틸: 객체 깊은 복사
const cloneDeep = (obj) => JSON.parse(JSON.stringify(obj))

export const useMsfFormNewChgStore = defineStore('msf_form_new_chg', () => {
  const applicationKey = ref('')

  // ==========================================
  // 1. 기본값 (Default Templates) 정의
  // ==========================================
  const DEFAULT_CUSTOMER = {
    isSaved: false, // 고객스텝 저장 완료 여부 (화면 락)
    isVerified: false, // 인증 완료 여부
    productType: 'MM',
    joinType: 'MNP3',
    cstmrTypeCd: 'NA',
    identityCertTypeCd: 'K',
    identityTypeCd: '',
    identityTypeNm: '', // 스캔된 신분증 명칭 추가
    identityIssuRegion: 'licenseRegion1',
    identityIssuDate: '', // 신분증 발급일자 추가
    selfIssuNo: '', // 자가발급번호 추가
    driveLicnsNo: '', // 운전면허번호 추가
    cstmrVisitTypeCd: 'V1',
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
    userBirthDate: '',
    userGender: 'userGender1',
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
    minorAgentSelfCertTypeCd: '',
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
    openTypeCd: '99',
    deviceModel: '',
    capacity: '',
    color: '',
    contractPeriod: '',
    installmentMonth: '',
    discountType: '',
    prodCtgId: '',
    prodId: '',
    prodNm: '',
    agency: '',
    termsAgreed: false,
    // 관리 정보 (DTO 기반)
    managerCd: 'M0001',
    managerNm: '',
    agentCd: 'VKI0012',
    agentNm: '',
    shopCd: '',
    shopNm: '',
    realShopNm: '',
    cpntId: '',
    cpntNm: '',
    cntpntShopCd: '',
    cntpntShopNm: '',
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
    clauseConfidenceConfidenceYn: 'N', // 약관신용정보이용동의여부 (필수)
    clauseFathFathYn: 'N', // 안면인증동의여부 (필수)
    nwBlckAgrmYn: 'N', // 네트워크차단동의여부 (필수)
    appBlckAgrmYn: 'N', // 청소년유해매체차단동의여부 (필수)
    blckAppDivCd: '', // 청소년유해매체차단APP구분코드 (필수)
    soTrnsAgrmYn: 'N', // 사업이관동의여부
    moveRefundAgreeYn: 'N', // 번호이동정보미환급액요금상계동의여부 (필수)
    clauseJehuJehuYn: 'N', // 제휴서비스동의여부 (선택)

    // API 전달용 특별 YN 필드 추가 (불리언으로 이미 선언되지 않은 것들)
    clauseMoveCode: 'N',
    clauseFathFlag01: 'N',
    clauseFathFlag02: 'N',
    clause5gCoverage: 'N',
    clausePartnerOfferFlag: 'N',
    personalLocationAgreeYn: 'N',
    clauseInfo01: 'N',

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
    CLAUSE_SELECT_10: false,
    CLAUSE_INFO_01: false,
  }

  const DEFAULT_PRODUCT = {
    hasSim: '',
    usimKindsCd: '',
    reqUsimSn: '',
    reqUsimNm: '', // DTO 기반 추가
    simPurchaseMethod: '',
    prodNm: '',
    eid: '',
    imei1: '',
    imei2: '',
    imei: '',
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
    moveThismonthPayTypeCd: false,
    moveAllotmentSttusCd: [],
    moveRefundAgreeYn: [],
    // 번호이동 안내 관련 추가
    reqGuideYn: 'N',
    reqGuideFnNo: '',
    reqGuideRnNo: '',
    reqGuideMnNo: '',
    osstPayDate: '',
    osstPayTypeCd: '',
    movePenalty: 0,
    reqWantFnNo: '',
    reqWantMnNo: '',
    reqWantRnNo: '',
    wishNo: '',
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
    addtionId: ['paidVas1'],
    reqAdditionPrice: 0, // DTO 기반 추가
    phonePaymentYn: 'N', // DTO 기반 추가
    clauseInsuranceYn: '',
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
    recycleYn: 'N',
    usimPriceTypeCd: '',
    usimPrice: 0,
    sesplsYn: 'N',
    joinPriceTypeCd: '',
    joinPayMthdCd: '',
    joinPrice: 0,
    socCode: '',
    socNm: '',
    socBaseChrgAmt: 0,
    // 접수 환경/기타 정보
    soCd: '',
    openReqDt: '',
    reqInDay: '',
    etcSpecialSbst: '',
    cstmrBillSendTypeCd: '',
    reqPayTypeCd: '',
    autoPayerType: '',
    reqBankCd: '',
    reqAccountNo: '',
    reqAccountNm: '',
    reqAccountRrn: '',
    reqAccountRelTypeCd: '',
    isAutoAgree: false,
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
    othersPaymentReqNm: '',
    prntsBillNo: '',
    combId: '',
    combAgree: false,
    memo: '',
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
  }

  // ==========================================
  // 2. 상태 관리 (초기값 / 임시저장값 / 현재값)
  // ==========================================

  // 1) 초기값 (진입 시 최초 세팅값)
  const initialCustomer = ref(cloneDeep(DEFAULT_CUSTOMER))
  const initialProduct = ref(cloneDeep(DEFAULT_PRODUCT))
  const initialAgreement = ref(cloneDeep(DEFAULT_AGREEMENT))

  // 2) 임시저장값 (서버에서 불러오거나 저장에 성공한 마지막 상태)
  const draftApplicationKey = ref('')
  const draftCustomer = ref(cloneDeep(DEFAULT_CUSTOMER))
  const draftProduct = ref(cloneDeep(DEFAULT_PRODUCT))
  const draftAgreement = ref(cloneDeep(DEFAULT_AGREEMENT))

  // 3) 현재값 (실제 폼 컴포넌트들과 v-model로 바인딩되는 상태)
  const customer = ref(cloneDeep(DEFAULT_CUSTOMER))
  const product = ref(cloneDeep(DEFAULT_PRODUCT))
  const agreement = ref(cloneDeep(DEFAULT_AGREEMENT))

  // 각 인증 버튼들의 최종 완료 여부를 관리하는 플래그 (UI 제어용)
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

  // 고객 유형 변경 시 정보 초기화
  watch(
    () => customer.value.cstmrTypeCd,
    () => {
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
        customer.value.userBirthDate = ''
        customer.value.userGender = 'userGender1'

        // 인증 상태 및 플래그 초기화
        customer.value.isVerified = false
        Object.keys(authFlags.value).forEach((key) => {
          authFlags.value[key] = false
        })

        console.log('>>> 고객 유형 변경으로 인한 데이터 초기화 완료')
      }
    },
  )

  // 신분증 인증 방식 변경 시 정보 초기화 (저장되지 않은 경우만)
  watch(
    () => customer.value.identityCertTypeCd,
    () => {
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
      msfRequestKey: key || '',
      formTypeCd: '1', // 신규/번호이동 신청서
    }
    const res = await post('/api/form/newchange/get', payload)

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

  // 3.2. 폼 진입 시 데이터 초기화
  const initForm = async (savedKey = null) => {
    // 0) 무조건 로컬 기본값으로 초기화 시작 (신규 진입 상태)
    applicationKey.value = ''
    initialCustomer.value = cloneDeep(DEFAULT_CUSTOMER)
    initialProduct.value = cloneDeep(DEFAULT_PRODUCT)
    initialAgreement.value = cloneDeep(DEFAULT_AGREEMENT)

    // 1) 통합 API 호출 (savedKey가 없어도 초기 설정 정보를 위해 호출)
    const res = await apiFetchFormData(savedKey)

    // 2) 서버에서 내려준 초기 설정값(Initial)이 있다면 로컬 기본값 위에 덮어씀
    const initial = res.initial || {}
    if (initial.customer) initialCustomer.value = { ...initialCustomer.value, ...initial.customer }
    if (initial.product) initialProduct.value = { ...initialProduct.value, ...initial.product }
    if (initial.agreement)
      initialAgreement.value = { ...initialAgreement.value, ...initial.agreement }

    // 일단 현재 상태를 초기 설정값으로 세팅
    customer.value = cloneDeep(initialCustomer.value)
    product.value = cloneDeep(initialProduct.value)
    agreement.value = cloneDeep(initialAgreement.value)

    // requestkey가 없는 경우 여기서 종료 (기본값 세팅 완료)
    if (!savedKey) {
      console.log('>>> 신규 신청서 작성 (기본값 세팅)')
      return '0'
    }

    // 3) requestkey가 있는 경우 임시저장 데이터(Draft) 복원
    applicationKey.value = savedKey
    const draft = res.draft
    if (draft) {
      loadDraft(draft)
      // 서버에서 관리하는 마지막 저장 스텝 반환 (없으면 0)
      return draft.tmpStepCd ? String(draft.tmpStepCd) : '0'
    }

    return '0'
  }

  // ==========================================
  // 4. 로직 처리 (리셋, 복원)
  // ==========================================
  const loadDraft = (savedData) => {
    if (!savedData) return

    // 1. 임시저장값(Draft) 세팅: 초기값(기본값 포함) 위에 임시저장 데이터를 덮어씀
    // (임시저장값 ? 임시저장값 : 초기값) 로직이 spread operator를 통해 자연스럽게 처리됨
    if (savedData.customer)
      draftCustomer.value = { ...initialCustomer.value, ...savedData.customer }
    if (savedData.product) {
      const p = { ...savedData.product }
      // 서버에서 단일 값('01', '02', 'Y', 'N')으로 오는 항목들을 UI용 배열 또는 Boolean으로 변환
      if (p.moveAllotmentSttusCd) {
        p.moveAllotmentSttusCd = Array.isArray(p.moveAllotmentSttusCd)
          ? p.moveAllotmentSttusCd
          : [p.moveAllotmentSttusCd]
      } else {
        p.moveAllotmentSttusCd = []
      }

      if (p.moveRefundAgreeYn) {
        p.moveRefundAgreeYn = Array.isArray(p.moveRefundAgreeYn)
          ? p.moveRefundAgreeYn
          : [p.moveRefundAgreeYn]
      } else {
        p.moveRefundAgreeYn = []
      }

      // 이번달 사용요금 (체크박스인 경우 Boolean으로 변환)
      p.moveThismonthPayTypeCd =
        p.moveThismonthPayTypeCd === 'Y' || p.moveThismonthPayTypeCd === true

      if (p.clauseInsuranceYn === 'Y' || p.clauseInsuranceYn === 'isInsured1') {
        p.clauseInsuranceYn = 'isInsured1'
        p.recCat2 = p.insrProdCd || p.recCat2
      } else {
        p.clauseInsuranceYn = 'isInsured2'
      }
      draftProduct.value = { ...initialProduct.value, ...p }
    }
    if (savedData.agreement)
      draftAgreement.value = { ...initialAgreement.value, ...savedData.agreement }

    // 2. 현재값(Current)을 임시저장값으로 최종 복원
    customer.value = cloneDeep(draftCustomer.value)
    product.value = cloneDeep(draftProduct.value)
    agreement.value = cloneDeep(draftAgreement.value)

    // 3. 재인증 요구: 데이터는 복구하되 인증 플래그는 모두 리셋
    customer.value.isVerified = false
    customer.value.isSaved = false

    Object.keys(authFlags.value).forEach((key) => {
      authFlags.value[key] = false
    })

    console.log('>>> 임시저장 데이터 복원 완료 (인증 초기화됨)')
  }

  const resetStep = (step) => {
    if (step === 1) {
      applicationKey.value = ''
      customer.value.isSaved = false
      // 현재값을 초기값(Initial)으로 리셋
      customer.value = cloneDeep(initialCustomer.value)
    } else if (step === 2) {
      product.value = cloneDeep(initialProduct.value)
      authFlags.value.reqUsimSn = false
      authFlags.value.imei = false
      authFlags.value.moveAuthTypeCd = false
      authFlags.value.reserveNo = false
      authFlags.value.autoAcct = false
      authFlags.value.reqCardNo = false
      authFlags.value.combId = false
    } else if (step === 3) {
      agreement.value = cloneDeep(initialAgreement.value)
    }
  }

  const resetAll = async () => {
    applicationKey.value = ''
    draftApplicationKey.value = ''
    // 모든 인증 플래그 초기화
    Object.keys(authFlags.value).forEach((key) => {
      authFlags.value[key] = false
    })
    // 각 단계 데이터 초기화
    customer.value = cloneDeep(DEFAULT_CUSTOMER)
    product.value = cloneDeep(DEFAULT_PRODUCT)
    agreement.value = cloneDeep(DEFAULT_AGREEMENT)

    // 초기화 후 서버 설정값 다시 로드
    await initForm()
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
    product.value.imei1 = ''
    product.value.imei2 = ''
  }

  // ==========================================
  // 5. API 통신: 저장
  // ==========================================
  const apiSaveDraft = async (step) => {
    try {
      const c = { ...customer.value }
      const p = { ...product.value }
      const a = { ...agreement.value }

      // Step 2 이상인데 키가 없는 경우 (비정상 진입 등)에 대한 방어 로직
      if (step > 1 && !applicationKey.value) {
        console.error('>>> [apiSaveDraft] Missing applicationKey for Step:', step)
      }

      const termsKeys = [
        'clauseMoveCode',
        'clauseEssCollectYn',
        'clausePriCollectYn',
        'clausePriOfferYn',
        'nwBlckAgrmYn',
        'appBlckAgrmYn',
        'soTrnsAgrmYn',
        'clauseJehuJehuYn',
        'clauseRentalModelCpYn',
        'clauseRentalModelCpPrYn',
        'clauseRentalServiceYn',
        'clauseMpps35Mpps35Yn',
        'clauseFinanceFinanceYn',
        'clause5GCoverageYn',
        'personalInfoCollectAgreeYn',
        'othersTrnsAgreeYn',
        'clauseSensiCollectYn',
        'clauseSensiOfferYn',
        'clausePartnerOfferYn',
        'othersTrnsKtAgreeYn',
        'othersAdReceiveAgreeYn',
        'ktCounselAgreeYn',
        'combineSoloTypeYn',
        'combineSoloSoloYn',
        'moveRefundAgreeYn',
        'isAutoAgree',
        'combAgree',
        'moveThismonthPayTypeCd',
      ]

      const toYN = (val) => {
        if (val === true || val === 'Y') return 'Y'
        if (val === false || val === 'N') return 'N'
        return ''
      }

      // 약관 및 체크박스 YN 변환 수행
      termsKeys.forEach((key) => {
        if (Object.prototype.hasOwnProperty.call(c, key)) c[key] = toYN(c[key])
        if (Object.prototype.hasOwnProperty.call(p, key)) p[key] = toYN(p[key])
        if (Object.prototype.hasOwnProperty.call(a, key)) a[key] = toYN(a[key])
      })

      const payload = {
        requestKey: applicationKey.value,
        tmpStepCd: String(step),

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
        cstmrTypeCd: c.cstmrTypeCd,
        identityCertTypeCd: c.identityCertTypeCd,
        knoteIdentityScanCstmrNm: c.knoteIdentityScanCstmrNm,
        knoteIdentityEssNo: c.knoteIdentityEssNo,
        knoteIdentityTypeCd: c.knoteIdentityTypeCd,
        knoteIdentityScanDt: c.knoteIdentityScanDt,
        knoteScanId: c.knoteScanId,
        fathTrgYn: toYN(c.fathTrgYn),
        fathTrgIdentityCertTypeCd: c.fathTrgIdentityCertTypeCd,
        fathTransacId: c.fathTransacId,
        fathCmpltNtfyDate: c.fathCmpltNtfyDate,
        fathTelNo: c.fathTelNo,
        fathMobileFnNo: c.fathMobileFnNo,
        fathMobileMnNo: c.fathMobileMnNo,
        fathMobileRnNo: c.fathMobileRnNo,
        identityTypeCd: c.identityTypeCd,
        identityIssuDate: c.identityIssuDate,
        identityIssuRegion: c.identityIssuRegion,
        selfIssuNo: c.selfIssuNo,
        driveLicnsNo: c.driveLicnsNo,
        contractNum: c.contractNum,

        // Product Info (MSF_REQUEST)
        prodId: c.prodId,
        prodNm: c.prodNm,
        reqPhoneSn: p.imei,
        reqModelNm: p.deviceModel,
        sntyCapacCd: p.capacity,
        sntyColorCd: p.color,
        reqModelColor: p.reqModelColor,
        shopUsmId: p.shopUsmId,
        usimKindsCd: p.usimKindsCd,
        reqUsimSn: p.reqUsimSn,
        reqUsimNm: p.reqUsimNm,
        eid: p.eid,
        imei1: p.imei1,
        imei2: p.imei2,
        esimPhoneId: p.esimPhoneId,
        uploadPhoneSrlNo: p.uploadPhoneSrlNo,
        reqWantFnNo: p.reqWantFnNo,
        reqWantMnNo: p.reqWantMnNo,
        reqWantRnNo: p.reqWantRnNo,
        insrCd: p.insrCd,
        insrProdCd: p.insrProdCd || p.recCat2,
        clauseInsuranceYn: toYN(p.clauseInsuranceYn),
        jehuPartnerTypeCd: p.jehuPartnerTypeCd,
        jehuProdTypeCd: p.jehuProdTypeCd,
        reqAdditionListNm: JSON.stringify(p.reqAdditionList || []),
        reqAdditionPrice: p.reqAdditionPrice,
        phonePaymentYn: p.phonePaymentYn,
        onOffTypeCd: p.onOffTypeCd,
        soCd: p.soCd,
        openReqDt: p.openReqDt,
        reqInDay: p.reqInDay,
        memo: p.memo,
        etcSpecialSbst: p.etcSpecialSbst,

        // Customer Info (MSF_REQUEST_CSTMR)
        cstmrNm: c.cstmrNm,
        cstmrNativeRrn: c.cstmrNativeRrn1 + c.cstmrNativeRrn2,
        cstmrNativeBirth: c.cstmrNativeBirth,
        cstmrNativeGenderCd: c.cstmrNativeGenderCd,
        cstmrPrivateCname: c.cstmrPrivateCname,
        cstmrPrivateBizNo: c.cstmrPrivateBizNo,
        cstmrForeignerRrn: c.cstmrForeignerRrn1 + c.cstmrForeignerRrn2,
        cstmrForeignerBirth: c.cstmrForeignerBirth,
        cstmrForeignerGenderCd: c.cstmrForeignerGenderCd,
        cstmrForeignerPn: c.cstmrForeignerPn,
        cstmrForeignerCountryCd: c.cstmrForeignerCountryCd,
        cstmrForeignerNation: c.cstmrForeignerNation,
        cstmrForeignerVisaNo: c.cstmrForeignerVisaNo,
        cstmrForeignerVdateStartDate: c.cstmrForeignerVdateStartDate,
        cstmrForeignerVdateEndDate: c.cstmrForeignerVdateEndDate,
        cstmrJuridicalCname: c.cstmrJuridicalCname,
        cstmrJuridicalRrn: c.cstmrJuridicalRrn1 + c.cstmrJuridicalRrn2,
        cstmrJuridicalBizNo:
          c.cstmrJuridicalBizNo1 + c.cstmrJuridicalBizNo2 + c.cstmrJuridicalBizNo3,
        cstmrJuridicalRepNm: c.cstmrJuridicalRepNm,
        upjnCd: c.upjnCd,
        bcuSbst: c.bcuSbst,
        cstmrJuridicalUserNm: c.cstmrJuridicalUserNm,
        cstmrJuridicalBirth: c.cstmrJuridicalBirth,
        cstmrVisitTypeCd: c.cstmrVisitTypeCd,
        cstmrMobileFnNo: c.mobileNo1,
        cstmrMobileMnNo: c.mobileNo2,
        cstmrMobileRnNo: c.mobileNo3,
        cstmrTelFnNo: c.telNo1,
        cstmrTelMnNo: c.telNo2,
        cstmrTelRnNo: c.telNo3,
        cstmrAdr: c.address,
        cstmrAdrDtl: c.detailAddress,
        cstmrZipcd: c.zipNo,
        cstmrAdrBjd: c.cstmrAdrBjd,
        cstmrEmailAdr: c.emailAddr1 + '@' + c.emailAddr2,
        cstmrEmailReceiveYn: toYN(c.cstmrEmailReceiveYn),
        cstmrReceiveTelFnNo: c.cstmrReceiveTelFnNo,
        cstmrReceiveTelNmNo: c.cstmrReceiveTelNmNo,
        cstmrReceiveTelRnNo: c.cstmrReceiveTelRnNo,

        // Agent Info (MSF_REQUEST_AGENT / REPRESENTATIVE)
        minorAgentNm: c.minorAgentNm,
        minorAgentRrn: c.minorAgentRrn,
        minorAgentBirth: c.minorAgentBirth,
        minorAgentGenderCd: c.minorAgentGenderCd,
        minorAgentRelTypeCd: c.minorAgentRelTypeCd,
        minorAgentTelFnNo: c.minorAgentTelFnNo,
        minorAgentTelMnNo: c.minorAgentTelMnNo,
        minorAgentTelRnNo: c.minorAgentTelRnNo,
        minorAgentAgrmYn: toYN(c.minorAgentAgrmYn),
        minorAgentSelfInqryAgrmYn: toYN(c.minorAgentSelfInqryAgrmYn),
        minorAgentSelfCertTypeCd: c.minorAgentSelfCertTypeCd,
        minorAgentCiInfo: c.minorAgentCiInfo,
        jrdclAgentNm: c.jrdclAgentNm,
        jrdclAgentRrn: c.jrdclAgentRrn,
        jrdclAgentRelTypeCd: c.jrdclAgentRelTypeCd,
        jrdclAgentTelFnNo: c.jrdclAgentTelFnNo,
        jrdclAgentTelMnNo: c.jrdclAgentTelMnNo,
        jrdclAgentTelRnNo: c.jrdclAgentTelRnNo,

        // Sale Info (MSF_REQUEST_SALE)
        modelId: p.deviceModel,
        modelMonthly: p.installmentMonth,
        modelInstamt: p.modelInstamt,
        modelSalePolicyCd: p.modelSalePolicyCd,
        modelPriceVat: p.modelPriceVat,
        modelDiscount1: p.modelDiscount1,
        modelSprt: p.modelSprt,
        modelPrice: p.modelPrice,
        modelDiscount3: p.modelDiscount3,
        realMdlInstamt: p.realMdlInstamt,
        hndsetSalePrice: p.hndsetSalePrice,
        sprtTypeCd: p.sprtTypeCd,
        dcAmt: p.dcAmt,
        maxApdSprt: p.maxApdSprt,
        addDcAmt: p.addDcAmt,
        enggMnthCnt: p.contractPeriod,
        recycleYn: toYN(p.recycleYn),
        usimPriceTypeCd: p.usimPriceTypeCd,
        usimPrice: p.usimPrice,
        usimPayMthdCd: p.simPurchaseMethod,
        sesplsYn: toYN(p.sesplsYn),
        joinPriceTypeCd: p.joinPriceTypeCd,
        joinPayMthdCd: p.joinPayMthdCd,
        joinPrice: p.joinPrice,
        socCode: p.socCode,
        socNm: p.socNm,
        socBaseChrgAmt: p.socBaseChrgAmt,

        // Bill Info (MSF_REQUEST_BILL_REQ)
        reqPayTypeCd: p.reqPayTypeCd,
        reqBankCd: p.reqBankCd,
        reqAccountNm: p.reqAccountNm,
        reqAccountRrn: p.reqAccountRrn,
        reqAccountRelTypeCd: p.reqAccountRelTypeCd,
        reqAccountNo: p.reqAccountNo,
        reqCardNm: p.reqCardNm,
        reqCardRrn: p.reqCardRrn,
        reqCardCompanyCd: p.reqCardCompanyCd,
        reqCardNo: p.reqCardNo,
        reqCardYy: p.reqCardYy,
        reqCardMm: p.reqCardMm,
        reqWireTypeCd: p.reqWireTypeCd,
        othersPaymentYn: toYN(p.othersPaymentYn),
        othersPaymentTelFnNo: p.othersPaymentTelFnNo,
        othersPaymentTelMnNo: p.othersPaymentTelMnNo,
        othersPaymentTelRnNo: p.othersPaymentTelRnNo,
        othersPaymentNm: p.othersPaymentNm,
        othersPaymentRrn: p.othersPaymentRrn,
        othersPaymentRelTypeCd: p.othersPaymentRelTypeCd,
        othersPaymentReqNm: p.othersPaymentReqNm,
        prntsBillNo: p.prntsBillNo,
        cstmrBillSendTypeCd: p.cstmrBillSendTypeCd,
        isAutoAgree: toYN(p.isAutoAgree),

        // Move Info (MSF_REQUEST_MOVE)
        moveCompanyCd: p.moveCompanyCd,
        moveMobileFnNo: p.moveMobileNo1,
        moveMobileMnNo: p.moveMobileNo2,
        moveMobileRnNo: p.moveMobileNo3,
        moveAuthTypeCd: p.moveAuthTypeCd,
        moveAuthNo: p.moveAuthNo,
        moveThismonthPayTypeCd: toYN(p.moveThismonthPayTypeCd),
        moveAllotmentSttusCd: Array.isArray(p.moveAllotmentSttusCd)
          ? p.moveAllotmentSttusCd[0]
          : p.moveAllotmentSttusCd,
        moveRefundAgreeYn: Array.isArray(p.moveRefundAgreeYn)
          ? p.moveRefundAgreeYn[0]
          : p.moveRefundAgreeYn,
        reqGuideYn: toYN(p.reqGuideYn),
        reqGuideFnNo: p.reqGuideFnNo,
        reqGuideRnNo: p.reqGuideRnNo,
        reqGuideMnNo: p.reqGuideMnNo,
        osstPayDate: p.osstPayDate,
        osstPayTypeCd: p.osstPayTypeCd,
        movePenalty: p.movePenalty,

        // Clauses
        clausePriCollectYn: toYN(c.clausePriCollectYn),
        clausePriOfferYn: toYN(c.clausePriOfferYn),
        clauseEssCollectYn: toYN(c.clauseEssCollectYn),
        clausePriTrustYn: toYN(c.clausePriTrustYn),
        clausePriAdYn: toYN(c.clausePriAdYn),
        clauseConfidenceYn: toYN(c.clauseConfidenceConfidenceYn),
        clauseFathYn: toYN(c.clauseFathFathYn),
        nwBlckAgrmYn: toYN(c.nwBlckAgrmYn),
        appBlckAgrmYn: toYN(c.appBlckAgrmYn),
        blckAppDivCd: c.blckAppDivCd,
        soTrnsAgrmYn: toYN(c.soTrnsAgrmYn),
        clauseJehuYn: toYN(c.clauseJehuJehuYn),
        clauseRentalModelCpYn: toYN(c.clauseRentalModelCpYn),
        clauseRentalModelCpPrYn: toYN(c.clauseRentalModelCpPrYn),
        clauseRentalServiceYn: toYN(c.clauseRentalServiceYn),
        clauseMpps35Yn: toYN(c.clauseMpps35Mpps35Yn),
        clauseFinanceYn: toYN(c.clauseFinanceFinanceYn),
        clause5gCoverageYn: toYN(c.clause5GCoverageYn),
        personalInfoCollectAgreeYn: toYN(c.personalInfoCollectAgreeYn),
        othersTrnsAgreeYn: toYN(c.othersTrnsAgreeYn),
        clauseSensiCollectYn: toYN(c.clauseSensiCollectYn),
        clauseSensiOfferYn: toYN(c.clauseSensiOfferYn),
        clausePartnerOfferYn: toYN(c.clausePartnerOfferYn),
        othersTrnsKtAgreeYn: toYN(c.othersTrnsKtAgreeYn),
        othersAdReceiveAgreeYn: toYN(c.othersAdReceiveAgreeYn),
        ktCounselAgreeYn: toYN(c.ktCounselAgreeYn),
        combineSoloTypeYn: toYN(c.combineSoloTypeYn),
        combineSoloYn: toYN(c.combineSoloSoloYn),

        // Agreement / Status
        agreeCheck1: toYN(a.agreeCheck1),
        agreeCheck2: toYN(a.agreeCheck2),
        agreeCheck3: toYN(a.agreeCheck3),
        recYn: toYN(a.recYn),
        fileTypeCd: a.fileTypeCd,
        filePathNm: a.filePathNm || a.recFilePathNm,
        fileNm: a.recFileNm,
        proSttusCd: c.proSttusCd || '99',
        sbscProCd: c.sbscProCd || '99',
        appFormYn: toYN(c.appFormYn),
        appFormXmlYn: toYN(c.appFormXmlYn),
        faxYn: toYN(c.faxYn),
      }

      if (String(step) === '1') {
        customer.value.isSaved = true
      }

      console.log('[임시저장 전송 데이터]:', payload)

      const res = await post('/api/form/newchange/save', payload)

      // 1. 서버에서 새로 발급하거나 확정된 requestkey가 오면 양쪽 스토어(현재/임시) 모두 갱신
      const newKey = res?.data?.requestkey || res?.requestkey || res?.applicationKey
      if (newKey) {
        applicationKey.value = newKey
        draftApplicationKey.value = newKey
        console.log('>>> Updated applicationKey (requestkey):', newKey)
      }

      // 2. 저장이 성공하면 현재 값(Current)을 임시저장 값(Draft)으로 즉시 동기화
      draftCustomer.value = cloneDeep(customer.value)
      draftProduct.value = cloneDeep(product.value)
      draftAgreement.value = cloneDeep(agreement.value)

      return true
    } catch (e) {
      console.error('Draft save failed', e)
      return false
    }
  }

  const apiCompleteApplication = async () => {
    try {
      const customerData = { ...customer.value }
      const termsKeys = [
        'clauseMoveCode',
        'clauseEssCollectYn',
        'clausePriCollectYn',
        'clausePriOfferYn',
        'clauseFathFlag01',
        'clauseFathFlag02',
        'nwBlckAgrmYn',
        'appBlckAgrmYn',
        'clause5gCoverage',
        'clausePartnerOfferFlag',
        'personalInfoCollectAgreeYn',
        'clausePriAdYn',
        'othersAdReceiveAgreeYn',
        'othersTrnsAgreeYn',
        'othersTrnsKtAgreeYn',
        'personalLocationAgreeYn',
        'clauseInfo01',
        'soTrnsAgrmYn',
        'clauseRentalModelCpYn',
        'clauseRentalModelCpPrYn',
        'clauseRentalServiceYn',
        'clauseMpps35Mpps35Yn',
        'clauseFinanceFinanceYn',
        'clauseSensiCollectYn',
        'clauseSensiOfferYn',
        'clausePartnerOfferYn',
        'ktCounselAgreeYn',
        'combineSoloTypeYn',
        'combineSoloSoloYn',
        'moveRefundAgreeYn',
        'blckAppDivCd',
      ]
      const toYN_simple = (val) => {
        if (val === true || val === 'Y') return 'Y'
        if (val === false || val === 'N') return 'N'
        return ''
      }
      termsKeys.forEach((key) => {
        customerData[key] = toYN_simple(customerData[key])
      })
      if (
        customerData.cstmrTypeCd === 'NA' &&
        customerData.cstmrJuridicalBizNo1 &&
        customerData.cstmrJuridicalBizNo2 &&
        customerData.cstmrJuridicalBizNo3
      ) {
        customerData.cstmrTypeCd = 'PP'
      }

      // 현재값(Current) 기준으로 완료
      const payload = {
        customer: customerData,
        product: product.value,
        agreement: agreement.value,
      }
      await post(`/api/msf/formNewChg/${applicationKey.value}/complete`, payload).then().catch()
      return true
    } catch (e) {
      console.error('Completion failed', e)
      return false
    }
  }

  const loadFromTempJson = async () => {
    try {
      const response = await fetch('/temp.json')
      const data = await response.json()
      if (!data) return

      // requestKey 세팅
      applicationKey.value = data.requestKey || ''

      // Customer mapping
      const c = customer.value
      c.productType = data.reqBuyTypeCd || 'MM'
      c.joinType = data.operTypeCd || 'MNP3'
      c.cstmrTypeCd = data.cstmrTypeCd || 'NA'
      c.identityCertTypeCd = data.identityCertTypeCd || 'K'
      c.identityTypeCd = data.identityTypeCd || ''
      c.identityIssuRegion = data.identityIssuRegion || 'licenseRegion1'

      if (data.identityCertTypeCd === 'S') {
        c.isVerified = true // 인증예외/스캐너인 경우 인증된 상태로 간주
        c.cstmrNm = data.cstmrNm
        c.cstmrNativeRrn1 = data.cstmrNativeRrn.substring(0, 6)
        c.cstmrNativeRrn2 = data.cstmrNativeRrn.substring(6)
        authFlags.value.identityCertTypeCd = true
      }

      c.cstmrNm = data.cstmrNm || ''
      if (data.cstmrNativeRrn && data.cstmrNativeRrn.length >= 6) {
        c.cstmrNativeRrn1 = data.cstmrNativeRrn.substring(0, 6)
        c.cstmrNativeRrn2 = data.cstmrNativeRrn.substring(6)
      }

      c.cstmrVisitTypeCd = data.cstmrVisitTypeCd || 'V1'
      c.mobileNo1 = data.cstmrMobileFnNo || '010'
      c.mobileNo2 = data.cstmrMobileMnNo || ''
      c.mobileNo3 = data.cstmrMobileRnNo || ''
      c.address = data.cstmrAdr || ''
      c.detailAddress = data.cstmrAdrDtl || ''
      c.zipNo = data.cstmrZipcd || ''

      if (data.cstmrEmailAdr && data.cstmrEmailAdr.includes('@')) {
        const [e1, e2] = data.cstmrEmailAdr.split('@')
        c.emailAddr1 = e1
        c.emailAddr2 = e2
      }
      c.cstmrEmailReceiveYn = data.cstmrEmailReceiveYn === 'Y'

      // Agent mapping
      if (data.cstmrTypeCd == 'NM' || data.cstmrTypeCd == 'FM') {
        c.minorAgentNm = data.minorAgentNm || ''
        c.repForeignerNo1 = data.repForeignerNo1 || ''
        c.repForeignerNo2 = data.repForeignerNo2 || ''
        c.repRegistrationNo1 = data.repRegistrationNo1 || ''
        c.repRegistrationNo2 = data.repRegistrationNo2 || ''
        c.minorAgentRelTypeCd = data.minorAgentRelTypeCd || ''
        c.minorAgentTelFnNo = data.minorAgentTelFnNo || ''
        c.minorAgentTelMnNo = data.minorAgentTelMnNo || ''
        c.minorAgentTelRnNo = data.minorAgentTelRnNo || ''

        // rep* 필드에도 동기화 (기존 로직 호환성 유지)
        c.repName = c.minorAgentNm
        c.repRelation = c.minorAgentRelTypeCd
        c.repPhone1 = c.minorAgentTelFnNo
        c.repPhone2 = c.minorAgentTelMnNo
        c.repPhone3 = c.minorAgentTelRnNo
      }
      // 약관류
      const toBool = (val) => val === 'Y'
      c.clausePriCollectYn = toBool(data.clausePriCollectYn)
      c.clausePriOfferYn = toBool(data.clausePriOfferYn)
      c.clauseEssCollectYn = toBool(data.clauseEssCollectYn)
      c.clausePriTrustYn = toBool(data.clausePriTrustYn)
      c.clausePriAdYn = toBool(data.clausePriAdYn)
      c.clauseConfidenceConfidenceYn = toBool(data.clauseConfidenceYn)
      c.clauseFathFathYn = toBool(data.clauseFathYn)
      c.nwBlckAgrmYn = toBool(data.nwBlckAgrmYn)
      c.appBlckAgrmYn = toBool(data.appBlckAgrmYn)
      c.blckAppDivCd = data.blckAppDivCd || ''
      c.soTrnsAgrmYn = toBool(data.soTrnsAgrmYn)
      c.clauseJehuJehuYn = toBool(data.clauseJehuYn)
      c.clauseRentalModelCpYn = toBool(data.clauseRentalModelCpYn)
      c.clauseRentalModelCpPrYn = toBool(data.clauseRentalModelCpPrYn)
      c.clauseRentalServiceYn = toBool(data.clauseRentalServiceYn)
      c.clauseMpps35Mpps35Yn = toBool(data.clauseMpps35Yn)
      c.clauseFinanceFinanceYn = toBool(data.clauseFinanceYn)
      c.clause5GCoverageYn = toBool(data.clause5gCoverageYn)
      c.personalInfoCollectAgreeYn = toBool(data.personalInfoCollectAgreeYn)
      c.othersTrnsAgreeYn = toBool(data.othersTrnsAgreeYn)
      c.clauseSensiCollectYn = toBool(data.clauseSensiCollectYn)
      c.clauseSensiOfferYn = toBool(data.clauseSensiOfferYn)
      c.clausePartnerOfferYn = toBool(data.clausePartnerOfferYn)
      c.othersTrnsKtAgreeYn = toBool(data.othersTrnsKtAgreeYn)
      c.othersAdReceiveAgreeYn = toBool(data.othersAdReceiveAgreeYn)
      c.ktCounselAgreeYn = toBool(data.ktCounselAgreeYn)
      c.combineSoloTypeYn = toBool(data.combineSoloTypeYn)
      c.combineSoloSoloYn = toBool(data.combineSoloYn)

      // Product mapping
      const p = product.value
      c.prodCtgId = data.prodCtgId || data.planName1 || ''
      c.prodId = data.prodId || data.planName2 || ''
      c.prodNm = data.prodNm || ''
      p.deviceModel = data.reqModelNm || data.modelId || ''
      p.capacity = data.sntyCapacCd || ''
      p.color = data.sntyColorCd || ''
      p.discountType = data.sprtTp || data.discountType || ''
      p.imei = data.reqPhoneSn || ''
      p.usimKindsCd = data.usimKindsCd || ''
      p.reqUsimSn = data.reqUsimSn || ''
      p.eid = data.eid || ''
      p.imei1 = data.imei1 || ''
      p.imei2 = data.imei2 || ''
      p.reqWantFnNo = data.reqWantFnNo || ''
      p.reqWantMnNo = data.reqWantMnNo || ''
      p.reqWantRnNo = data.reqWantRnNo || ''
      p.contractPeriod = data.enggMnthCnt || ''
      p.simPurchaseMethod = data.usimPayMthdCd || ''

      p.moveCompanyCd = data.moveCompanyCd || ''
      p.moveMobileNo1 = data.moveMobileFnNo || '010'
      p.moveMobileNo2 = data.moveMobileMnNo || ''
      p.moveMobileNo3 = data.moveMobileRnNo || ''
      p.moveAuthTypeCd = data.moveAuthTypeCd || ''
      p.moveAuthNo = data.moveAuthNo || ''

      // 데이터 타입 보정 (문자열을 배열로 변환하여 UI 선택 상태 복구)
      p.moveThismonthPayTypeCd = data.moveThismonthPayTypeCd === 'Y'
      p.moveAllotmentSttusCd = data.moveAllotmentSttusCd ? [data.moveAllotmentSttusCd] : []
      p.moveRefundAgreeYn = data.moveRefundAgreeYn ? [data.moveRefundAgreeYn] : []

      p.reqPayTypeCd = data.reqPayTypeCd || ''
      p.reqBankCd = data.reqBankCd || ''
      p.reqAccountNm = data.reqAccountNm || ''
      p.reqAccountRrn = data.reqAccountRrn || ''
      p.reqAccountRelTypeCd = data.reqAccountRelTypeCd || ''
      p.reqAccountNo = data.reqAccountNo || ''
      p.reqCardNm = data.reqCardNm || ''
      p.reqCardRrn = data.reqCardRrn || ''
      p.reqCardCompanyCd = data.reqCardCompanyCd || ''
      p.reqCardNo = data.reqCardNo || ''
      p.reqCardYy = data.reqCardYy || ''
      p.reqCardMm = data.reqCardMm || ''
      p.cstmrBillSendTypeCd = data.cstmrBillSendTypeCd || ''

      p.recCat2 = data.insrProdCd || ''
      p.clauseInsuranceYn = data.clauseInsuranceYn === 'Y' ? 'isInsured1' : 'isInsured2'

      p.memo = data.memo || ''

      if (data.reqAdditionListNm) {
        try {
          p.reqAdditionList = JSON.parse(data.reqAdditionListNm)
        } catch (e) {
          p.reqAdditionList = []
        }
      }

      // Agreement mapping
      agreement.value.recYn = data.recYn || 'N'

      console.log('>>> Loaded from temp.json:', data)
      return data.tmpStepCd || 1
    } catch (e) {
      console.error('Failed to load temp.json', e)
      return 1
    }
  }

  const validateCustomer = ref(() => true)
  const validateProduct = ref(() => true)
  const validateAgreement = ref(() => true)

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
    resetStep,
    resetAll,
    copyApplication,
    loadFromTempJson,

    validateCustomer,
    validateProduct,
    validateAgreement,
  }
})
