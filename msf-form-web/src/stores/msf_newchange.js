import { defineStore } from 'pinia'
import { ref } from 'vue'
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
    identityIssuRegion: 'licenseRegion1',
    cstmrVisitTypeCd: 'V1',
    cstmrNm: '',
    cstmrNativeRrn1: '',
    cstmrNativeRrn2: '',
    cstmrForeignerRrn1: '',
    cstmrForeignerRrn2: '',
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
    userGender: '',
    minorAgentNm: '',
    agentBirthDate: '',
    agentGender: '',
    minorAgentRelTypeCd: '',
    minorAgentTelFnNo: '',
    minorAgentTelMnNo: '',
    minorAgentTelRnNo: '',
    mobileNo1: '010',
    mobileNo2: '',
    mobileNo3: '',
    telNo1: '',
    telNo2: '',
    telNo3: '',
    emailAddr1: '',
    emailAddr2: '',
    address1: '',
    address2: '',
    address3: '',
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
    planName1: '',
    planName2: '',
    agency: '',
    termsAgreed: false,
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
    clauseJehuJehuYn: 'N', // 제휴서비스동의여부 (선택)
    clauseRentalModelCpYn: 'N', // 단말배상금안내사항여부 (필수)
    clauseRentalModelCpPrYn: 'N', // 단말배상금(부분파손)안내사항여부 (필수)
    clauseRentalServiceYn: 'N', // 중고렌탈프로그램서비스이용에대한동의서여부 (필수)
    clauseMpps35Mpps35Yn: 'N', // 선불MPPS요금제동의여부 (필수)
    clauseFinanceFinanceYn: 'N', // 금융제휴약관동의여부 (필수)
    clause5GCoverageYn: 'N', // 5G커버리지확인및가입동의여부 (필수)
    personalInfoCollectAgreeYn: 'N', // 고객혜택제공을위한개인정보수집및이용관련동의여부 (선택)
    othersTrnsAgreeYn: 'N', // 혜택제공을위한제3자제공동의(M모바일)여부 (선택)
    clauseSensiCollectYn: 'N', // 민감정보수집동의여부 (필수)
    clauseSensiOfferYn: 'N', // 민감정보제공동의여부 (필수)
    clausePartnerOfferYn: 'N', // 약관제휴사제공동의여부
    othersTrnsKtAgreeYn: 'N', // 혜택제공을위한제3자제공동의(KT)여부 (선택)
    othersAdReceiveAgreeYn: 'N', // 제3자제공관련광고수신동의여부 (선택)
    ktCounselAgreeYn: 'N', // 인터넷가입상담을위한개인정보제3자제공동의여부 (선택)
    combineSoloTypeYn: 'N', // 아무나솔로결합신청여부
    combineSoloSoloYn: 'N', // 아무나솔로결합동의여부
    moveRefundAgreeYn: 'N', // 번호이동정보미환급액요금상계동의여부 (필수)

    // API 전달용 특별 YN 필드 추가 (불리언으로 이미 선언되지 않은 것들)
    clauseMoveCode: 'N',
    clauseFathFlag01: 'N',
    clauseFathFlag02: 'N',
    clause5gCoverage: 'N',
    clauseJehuFlag: 'N',
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
    CLAUSE_PARTNER_01: false,
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
    simPurchaseMethod: '',
    prodNm: '',
    eid: '',
    imei1: '',
    imei2: '',
    imei: '',
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
    clauseInsuranceYn: '',
    recCat1: '',
    recCat2: '',
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
    combId: '',
    combAgree: false,
    memo: '',
  }

  const DEFAULT_AGREEMENT = {
    recYn: 'N',
    recFilePathNm: '',
    recFileNm: '',
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
  const authFlags = ref({
    identityCertTypeCd: false,
    deviceChgTel: false,
    repPhone: false,
    reqUsimSn: false,
    imei: false,
    moveAuthTypeCd: false,
    reserveNo: false,
    autoAcct: false,
    reqCardNo: false,
    combId: false,
    requiredDocs: false,
  })

  // 신분증 인증 방식 변경 시 가입자 정보 초기화 (저장되지 않은 경우만)
  import('vue').then(({ watch }) => {
    watch(
      () => customer.value.identityCertTypeCd,
      () => {
        if (!customer.value.isSaved) {
          customer.value.cstmrNm = ''
          customer.value.cstmrNativeRrn1 = ''
          customer.value.cstmrNativeRrn2 = ''
          customer.value.cstmrForeignerRrn1 = ''
          customer.value.cstmrForeignerRrn2 = ''
          customer.value.isVerified = false
        }
      },
    )
  })

  // ==========================================
  // 3. API 조회 및 데이터 초기화 로직
  // ==========================================

  // 3.1. 통합 데이터 조회 API (초기값 & 임시저장값)
  const apiFetchFormData = async (key = null) => {
    // 실제 API 연동: get('/api/msf/formNewChg/data', { params: { key } })
    return new Promise((resolve) => {
      setTimeout(() => {
        // 백엔드에서 내려주는 JSON 응답 예시
        const mockResponse = {
          initial: { customer: {}, product: {}, agreement: {} },
          draft: key ? { customer: {}, product: {}, agreement: {} } : null,
        }
        resolve(mockResponse)
      }, 50)
    })
  }

  // 3.2. 폼 진입 시 데이터 초기화
  const initForm = async (savedKey = null) => {
    // 1) 통합 API 호출 (savedKey 유무에 따라 백엔드에서 draft 데이터 포함 여부를 결정하여 반환)
    const res = await apiFetchFormData(savedKey)

    // 2) 초기값(Initial) 가져오기 및 세팅
    if (res.initial) {
      if (res.initial.customer)
        initialCustomer.value = { ...DEFAULT_CUSTOMER, ...res.initial.customer }
      if (res.initial.product) initialProduct.value = { ...DEFAULT_PRODUCT, ...res.initial.product }
      if (res.initial.agreement)
        initialAgreement.value = { ...DEFAULT_AGREEMENT, ...res.initial.agreement }
    }

    // 3) 기본적으로 현재값(Current)을 초기값으로 덮어씀
    customer.value = cloneDeep(initialCustomer.value)
    product.value = cloneDeep(initialProduct.value)
    agreement.value = cloneDeep(initialAgreement.value)

    if (savedKey) {
      applicationKey.value = savedKey
    }

    // 4) 임시저장 데이터(Draft)가 존재한다면 덮어쓰기 및 락/재인증 설정
    if (res.draft) {
      loadDraft(res.draft, 2) // draftStep은 백엔드에서 넘어오는 step 진행도에 따라 처리 (예시: 2)
    }
  }

  // ==========================================
  // 4. 로직 처리 (리셋, 복원)
  // ==========================================
  const loadDraft = (savedData, draftStep) => {
    if (!savedData) return

    // 1. 임시저장값(Draft) 원본 세팅
    if (savedData.customer) draftCustomer.value = { ...DEFAULT_CUSTOMER, ...savedData.customer }
    if (savedData.product) draftProduct.value = { ...DEFAULT_PRODUCT, ...savedData.product }
    if (savedData.agreement) draftAgreement.value = { ...DEFAULT_AGREEMENT, ...savedData.agreement }

    // 2. 현재값(Current)을 임시저장값으로 복원
    customer.value = cloneDeep(draftCustomer.value)
    product.value = cloneDeep(draftProduct.value)
    agreement.value = cloneDeep(draftAgreement.value)

    // 고객단계 임시저장 완료 상태로 화면 락(Lock)
    customer.value.isSaved = true

    // 3. 재인증 요구: 동의 등 다른 값은 유지하되, 인증과 관련된 플래그만 초기화
    customer.value.isVerified = false
    authFlags.value.identityCertTypeCd = false
    authFlags.value.deviceChgTel = false
    authFlags.value.repPhone = false

    if (draftStep >= 2) {
      authFlags.value.moveAuthTypeCd = false
      authFlags.value.reserveNo = false
      authFlags.value.imei = false
      authFlags.value.reqUsimSn = false
    }
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
    resetStep(1)
    resetStep(2)
    resetStep(3)
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

      // boolean 약관 항목들을 'Y'/'N'으로 변환
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
        'clauseJehuFlag',
        'clausePartnerOfferFlag',
        'personalInfoCollectAgreeYn',
        'clausePriAdYn',
        'othersAdReceiveAgreeYn',
        'othersTrnsAgreeYn',
        'othersTrnsKtAgreeYn',
        'personalLocationAgreeYn',
        'clauseInfo01',
        'soTrnsAgrmYn',
        'clauseJehuJehuYn',
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
      termsKeys.forEach((key) => {
        if (typeof c[key] === 'boolean') {
          c[key] = c[key] ? 'Y' : 'N'
        }
      })

      // 특정 로직: 내국인이면서 사업자등록번호가 모두 있으면 PP로 변경
      if (
        c.cstmrTypeCd === 'NA' &&
        c.cstmrJuridicalBizNo1 &&
        c.cstmrJuridicalBizNo2 &&
        c.cstmrJuridicalBizNo3
      ) {
        c.cstmrTypeCd = 'PP'
      }

      // 항상 폼의 현재값(Current) 기준으로 서버에 전송 (평면 구조로 변환)
      const payload = {
        requestKey: applicationKey.value,
        tmpStepCd: String(step),
        formTypeCd: c.formTypeCd || '1',
        serviceTypeCd: c.serviceTypeCd || 'PO',
        reqBuyTypeCd: c.productType,
        operTypeCd: c.joinType,
        cstmrTypeCd: c.cstmrTypeCd,
        identityCertTypeCd: c.identityCertTypeCd,
        managerCd: c.managerCd || 'M0001',
        agentCd: c.agentCd || 'VKI0012',
        onOffTypeCd: c.onOffTypeCd || '3',
        proSttusCd: c.proSttusCd || '99',
        sbscProCd: c.sbscProCd || '99',
        phonePaymentYn: c.phonePaymentYn || '',
        fathTrgYn: c.fathTrgYn || '',
        clausePriCollectYn: c.clausePriCollectYn,
        clausePriOfferYn: c.clausePriOfferYn,
        clauseEssCollectYn: c.clauseEssCollectYn,
        clausePriTrustYn: c.clausePriTrustYn,
        clausePriAdYn: c.clausePriAdYn,
        clauseConfidenceYn: c.clauseConfidenceConfidenceYn || 'N',
        clauseFathYn: c.clauseFathFathYn || 'N',
        nwBlckAgrmYn: c.nwBlckAgrmYn,
        appBlckAgrmYn: c.appBlckAgrmYn,
        soTrnsAgrmYn: c.soTrnsAgrmYn,
        clauseJehuYn: c.clauseJehuJehuYn,
        clauseRentalModelCpYn: c.clauseRentalModelCpYn,
        clauseRentalModelCpPrYn: c.clauseRentalModelCpPrYn,
        clauseRentalServiceYn: c.clauseRentalServiceYn,
        clauseMpps35Yn: c.clauseMpps35Mpps35Yn,
        clauseFinanceYn: c.clauseFinanceFinanceYn,
        clause5gCoverageYn: c.clause5GCoverageYn,
        personalInfoCollectAgreeYn: c.personalInfoCollectAgreeYn,
        othersTrnsAgreeYn: c.othersTrnsAgreeYn,
        clauseSensiCollectYn: c.clauseSensiCollectYn,
        clauseSensiOfferYn: c.clauseSensiOfferYn,
        clausePartnerOfferYn: c.clausePartnerOfferYn,
        othersTrnsKtAgreeYn: c.othersTrnsKtAgreeYn,
        othersAdReceiveAgreeYn: c.othersAdReceiveAgreeYn,
        ktCounselAgreeYn: c.ktCounselAgreeYn,
        combineSoloTypeYn: c.combineSoloTypeYn,
        combineSoloYn: c.combineSoloSoloYn,
        recYn: agreement.value.recYn || 'N',
        appFormYn: c.appFormYn || '',
        appFormXmlYn: c.appFormXmlYn || '',
        faxYn: c.faxYn || '',
        minorAgentAgrmYn: c.minorAgentAgrmYn || '',
        minorAgentSelfInqryAgrmYn: c.minorAgentSelfInqryAgrmYn || '',
        cstmrEmailReceiveYn: c.cstmrEmailReceiveYn || '',
        recycleYn: c.recycleYn || '',
        sesplsYn: c.sesplsYn || '',
        othersPaymentYn: c.othersPaymentYn || '',
      }

      if (String(step) === '1') {
        customer.value.isSaved = true
      }

      console.log('[임시저장 전송 데이터]:', payload)

      const res = await post('/api/form/newchange/save', payload)

      // 1. 서버에서 새로 발급하거나 확정된 applicationKey가 오면 갱신
      if (res && res.applicationKey) {
        applicationKey.value = res.applicationKey
      }

      // 2. 저장이 성공하면 현재 값(Current)을 임시저장 값(Draft)으로 즉시 동기화
      // (별도의 API 재조회 없이 클라이언트 내부 상태를 최신화)
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
        'clauseJehuFlag',
        'clausePartnerOfferFlag',
        'personalInfoCollectAgreeYn',
        'clausePriAdYn',
        'othersAdReceiveAgreeYn',
        'othersTrnsAgreeYn',
        'othersTrnsKtAgreeYn',
        'personalLocationAgreeYn',
        'clauseInfo01',
        'soTrnsAgrmYn',
        'clauseJehuJehuYn',
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
      termsKeys.forEach((key) => {
        if (typeof customerData[key] === 'boolean') {
          customerData[key] = customerData[key] ? 'Y' : 'N'
        }
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

    validateCustomer,
    validateProduct,
    validateAgreement,
  }
})
