import { defineStore } from 'pinia'
import { ref } from 'vue'
import { api } from '@/libs/api/msf.api'

export const useMsfFormNewChgStore = defineStore('msf_form_new_chg', () => {
  const applicationKey = ref('TEMP_' + Math.random().toString(36).substring(7))

  // Step 1: Customer Info
  const customer = ref({
    isVerified: false, // 인증 완료 여부
    product: 'MM',
    joinType: 'MNP3',
    customerType: 'NA',
    idCard: 'K',
    idCardScan: '',
    licenseRegion: 'licenseRegion1',
    userName: '',
    residentNo1: '',
    residentNo2: '',
    foreignerNo1: '',
    foreignerNo2: '',
    corpRegNo1: '',
    corpRegNo2: '',
    bizNo1: '',
    bizNo2: '',
    bizNo3: '',
    repreName: '',
    bizType: '',
    bizItem: '',
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
    agentName: '',
    agentBirthDate: '',
    agentGender: '',
    agentRelation: '',
    agentPhone1: '',
    agentPhone2: '',
    agentPhone3: '',
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
    openingType: '',
    deviceModel: '',
    capacity: '',
    color: '',
    contractPeriod: '',
    installmentMonth: '',
    discountType: '',
    planName1: '',
    planName2: '',
    agency: '',
  })

  // 신분증 인증 방식(idCard) 변경 시 가입자 정보 초기화
  import('vue').then(({ watch }) => {
    watch(() => customer.value.idCard, () => {
      customer.value.userName = ''
      customer.value.residentNo1 = ''
      customer.value.residentNo2 = ''
      customer.value.foreignerNo1 = ''
      customer.value.foreignerNo2 = ''
      customer.value.isVerified = false // 방식 변경 시 인증도 취소
    })
  })

  // Step 2: Product & Billing Info
  const product = ref({
    hasSim: '',
    simType: '',
    simNo: '',
    simPurchaseMethod: '',
    phoneModel: '',
    phoneEID: '',
    phoneIMEI1: '',
    phoneIMEI2: '',
    imei: '',
    carrier: '',
    transferPhone: '',
    transferAuth: '',
    transferAuthNum: '',
    transferBankNum: '',
    transferCardNum: '',
    currentMonthFee: false,
    deviceInstallment: [],
    offsetAmt: [],
    reserve1: '',
    reserve2: '',
    reserve3: '',
    wishNo: '',
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
    paidVas: ['paidVas1'],
    isInsured: '',
    recCat1: '',
    recCat2: '',
    stmtType: '',
    payMtd: '',
    autoPayerType: '',
    autoBankCode: '',
    autoAcctNo: '',
    autoPayerName: '',
    autoPayerBirth: '',
    autoRelation: '',
    isAutoAgree: false,
    cardPayerType: '',
    cardCorp: '',
    cardNo: '',
    cardExpMm: '',
    cardExpYy: '',
    cardPayerName: '',
    cardPayerBirth: '',
    cardRelation: '',
    combId: '',
    combAgree: false,
    memo: '',
  })

  // Step 3: Agreements
  const agreement = ref({
    agreeCheck1: false,
    agreeCheck2: false,
    agreeCheck3: false,
  })

  // 각 인증 버튼들의 최종 완료 여부를 관리하는 플래그 모음
  const authFlags = ref({
    idCard: false,           // 신분증 인증
    deviceChgTel: false,     // 기기변경 휴대폰 인증
    repPhone: false,         // 법정대리인 휴대폰 인증
    simNo: false,            // USIM 유효성
    imei: false,             // 단말 일련번호(IMEI) 유효성
    transferAuth: false,     // 번호이동 사전동의
    reserveNo: false,        // 신규 희망번호 조회
    autoAcct: false,         // 계좌번호 유효성
    cardNo: false,           // 신용카드 유효성
    combId: false            // 청구계정 체크
  })

  // # 초기화 (해당 스텝의 데이터를 초기 상태로 되돌리고, 다른 스텝의 인증 정보는 유지)
  const resetStep = (step) => {
    if (step === 1) {
      applicationKey.value = '' // 서식지 일련번호 제거
      // TODO: customer 데이터만 초기값으로 리셋
    } else if (step === 2) {
      // 상품 영역만 리셋 (고객영역 및 고객영역의 신분증/스캔/업로드는 유지)
      // TODO: product 데이터만 초기값으로 리셋
      authFlags.value.simNo = false
      authFlags.value.imei = false
      authFlags.value.transferAuth = false
      authFlags.value.reserveNo = false
      authFlags.value.autoAcct = false
      authFlags.value.cardNo = false
      authFlags.value.combId = false
    } else if (step === 3) {
      // 고객 확인사항 체크 해제, 버튼 비활성화
      agreement.value.agreeCheck1 = false
      agreement.value.agreeCheck2 = false
      agreement.value.agreeCheck3 = false
    }
  }

  // # 임시저장 불러오기 (데이터 복원 + 각종 재인증 요구 + 약관 해제)
  const loadDraft = (savedData, draftStep) => {
    if (!savedData) return

    // 1. 데이터 복원 (고객/상품)
    if (savedData.customer) customer.value = { ...savedData.customer }
    if (savedData.product) product.value = { ...savedData.product }

    // 2. 공통 요건: 이용약관동의/고객확인사항 체크박스 해제 (재동의 요구)
    customer.value.repAgree = false // 대리인 안내사항 동의 해제
    product.value.isAutoAgree = false // 자동이체 동의 해제
    product.value.combAgree = false // 통합청구 동의 해제
    agreement.value.agreeCheck1 = false
    agreement.value.agreeCheck2 = false
    agreement.value.agreeCheck3 = false

    // 3. 재인증/재진행 요구 (기존 인증 플래그 초기화)
    customer.value.isVerified = false // 신분증 재인증 및 신분증스캔하기 재진행 요구
    authFlags.value.idCard = false
    authFlags.value.deviceChgTel = false // 기기변경일 경우 재인증
    authFlags.value.repPhone = false     // 미성년자 법정대리인 재인증

    if (draftStep >= 2) {
      authFlags.value.transferAuth = false // 번호이동 사전동의 재진행
      authFlags.value.reserveNo = false    // 신규가입 번호예약 조회/취소 재진행
      authFlags.value.imei = false         // 단말일련번호 유효성 재진행
      authFlags.value.simNo = false        // USIM 유효성 재진행
    }
    // * 구비서류 기존 업로드 파일은 유지 (별도 파일객체 상태일 경우 그대로 보존됨)
  }

  // # 작성완료(복사하기)로 진입 (새 REQUEST_KEY + 특정 인증/저장데이터 제외)
  const copyApplication = (savedData) => {
    loadDraft(savedData, 3) // 기본적으로 임시저장3과 유사하게 모든 스텝 데이터를 가져오고 인증 초기화

    // 1. 새로운 신청서 KEY 생성
    applicationKey.value = 'TEMP_' + Math.random().toString(36).substring(7)

    // 2. 민감/단발성 식별 정보는 복사하지 않고 비움
    customer.value.idCardScan = ''     // 신분증스캔 정보
    // 신분증 확인 데이터 제외

    product.value.reserve1 = ''        // 신규가입 희망번호
    product.value.reserve2 = ''
    product.value.reserve3 = ''
    product.value.wishNo = ''

    product.value.transferAuthNum = '' // 번호이동 사전동의 정보
    product.value.transferBankNum = ''
    product.value.transferCardNum = ''

    product.value.simNo = ''           // USIM/eSIM 정보
    product.value.phoneEID = ''
    product.value.imei = ''            // 단말일련번호
    product.value.phoneIMEI1 = ''
    product.value.phoneIMEI2 = ''
  }

  const apiSaveDraft = async (step) => {
    try {
      const customerData = { ...customer.value }
      // 내국인(NA)이면서 사업자등록번호가 입력된 경우 내부적으로 개인사업자(PP)로 변경
      if (customerData.customerType === 'NA' && customerData.bizNo1 && customerData.bizNo2 && customerData.bizNo3) {
        customerData.customerType = 'PP'
      }

      const payload = {
        step: String(step),
        customer: customerData,
        product: product.value
      }
      await api.post(`/api/msf/formNewChg/${applicationKey.value}/draft`, payload)
      return true
    } catch (e) {
      console.error('Draft save failed', e)
      return false
    }
  }

  const apiCompleteApplication = async () => {
    try {
      const customerData = { ...customer.value }
      // 내국인(NA)이면서 사업자등록번호가 입력된 경우 내부적으로 개인사업자(PP)로 변경
      if (customerData.customerType === 'NA' && customerData.bizNo1 && customerData.bizNo2 && customerData.bizNo3) {
        customerData.customerType = 'PP'
      }

      const payload = {
        customer: customerData,
        product: product.value,
        agreement: agreement.value
      }
      await api.post(`/api/msf/formNewChg/${applicationKey.value}/complete`, payload)
      return true
    } catch (e) {
      console.error('Completion failed', e)
      return false
    }
  }

  return {
    applicationKey,
    customer,
    product,
    agreement,
    apiSaveDraft,
    apiCompleteApplication,
  }
})

