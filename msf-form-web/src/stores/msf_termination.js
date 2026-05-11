import { defineStore } from 'pinia'
import { ref, reactive } from 'vue'
import { post } from '@/libs/api/msf.api'

const DEFAULT_ERROR_MESSAGE = '신청서 등록이 실패하였습니다. 다시 시도해 주세요.'

export const useMsfFormTerminationStore = defineStore('msf_form_termination', () => {
  const applicationKey = ref('TEMP_' + Math.random().toString(36).substring(7))
  const completeErrorMessage = ref('')

  // formData 단일 소스
  // - 화면 공통 컴포넌트 바인딩
  // - API 전송 전 customer/product/agreement 구조로 매핑
  const formData = reactive({
    /* 고객 유형 */
    cstmrTypeCd: 'NA', // 고객유형
    visitCustomer: '',
    cstmrVisitTypeCd: '', // 방문고객 유형

    /* 신분증 확인 */
    identityCertTypeCd: '', // 신분증 인증유형
    identityIssuDate: '', // 발급일자
    driveLicnsNo: '', // 운전면허번호
    selfIssuNo: '', // 자필서명/자체발급번호
    identityTypeCd: '', // 신분증 타입
    identityIssuRegion: '', // 발급지역
    isVerified: false, // 본인확인 완료 여부
    isSaved: false, // 임시저장 여부

    /* 가입자 정보 */
    cstmrNm: '전용식', // 이름
    userBirthDate: '19800101', // 생년월일(YYYYMMDD)
    userGender: '', // 성별
    cstmrNativeRrn1: '', // 내국인 주민번호 앞
    cstmrNativeRrn2: '', // 내국인 주민번호 뒤
    cstmrForeignerRrn1: '', // 외국인등록번호 앞
    cstmrForeignerRrn2: '', // 외국인등록번호 뒤
    cstmrJuridicalRrn1: '', // 법인등록번호 앞
    cstmrJuridicalRrn2: '', // 법인등록번호 뒤
    cstmrJuridicalBizNo1: '', // 사업자번호 1
    cstmrJuridicalBizNo2: '', // 사업자번호 2
    cstmrJuridicalBizNo3: '', // 사업자번호 3
    cstmrJuridicalRepNm: '', // 대표자명
    upjnCd: '', // 업종코드
    bcuSbst: '', // 업태

    /* 해지 휴대폰 인증 */
    deviceChgTel1: '010',
    deviceChgTel2: '5358',
    deviceChgTel3: '6069',
    cancelPhoneAuth: '', // 인증번호
    contractNum: '', // 인증 응답 계약번호
    lstComActvDate: '', // 개통일자
    formType: 'TERMINATION',

    /* 법정대리인 정보 */
    repName: '',
    repRegistrationNo1: '',
    repRegistrationNo2: '',
    repRelation: '',
    repPhone1: '',
    repPhone2: '',
    repPhone3: '',
    repPhoneAuth: '',
    repAgree: false,

    /* 대리인 위임정보 */
    minorAgentNm: '',
    agentBirthDate: '',
    agentGender: '',
    minorAgentRelTypeCd: '',
    minorAgentTelFnNo: '',
    minorAgentTelMnNo: '',
    minorAgentTelRnNo: '',

    /* 해지 후 연락처 */
    afterTel1: '',
    afterTel2: '',
    afterTel3: '',
    postMethod: 'postMethod2', // 해지 후 연락 수단

    /* 가입유형/매장 정보 */
    agencyName: '',
    managerNm: '',
    agentNm: '',
    cpntId: '',
    cpntNm: '',
    cntpntShopCd: '',
    cntpntShopNm: '',

    /* 해지 정산 */
    isActive: '', // 사용여부
    usageFee: '', // 사용요금
    penaltyFee: '', // 위약금
    finalAmount: '', // 최종 정산요금
    remainPeriod: '', // 잔여분할상환 기간
    remainAmount: '', // 잔여분할상환 금액
    memo: '',
    uploadedDocs: [],

    /* 연동키/기본코드 */
    agentCd: '',
    managerCd: '',
    ncn: '',
    custId: '',

    /* X18 조회 원본 */
    remainChargeLoaded: false,
    remainChargeItems: [],

    /* 가입정보 조회(getMyinfoView) 결과 */
    prvRateGrpNm: '',       // 현재 요금제명
    initActivationDate: '', // 개통일자
    addr: '',               // 주소
    remindBlckYn: '',       // 해지 제한 여부
    payData: null,          // 납부방법
    billData: null,         // 명세서

    /* 동의 */
    agreeCheck1: false,
    agreeCheck2: false,
    agreeCheck3: false,
  })

  // 인증 버튼 완료 여부 플래그
  const authFlags = ref({
    cancelPhone: false, // 해지 휴대폰 인증
    repPhone: false, // 법정대리인 휴대폰 인증
  })

  // 인증 응답의 계약번호를 해지 스토어 키(formData.ncn/contractNum)에 매핑
  const setTerminationContract = (contractNum, source = 'unknown') => {
    if (!contractNum) return false
    formData.contractNum = contractNum
    formData.ncn = contractNum
    sessionStorage.setItem('terminationContractNum', contractNum)
    console.log('[TerminationStore] contract mapped', { source, contractNum })
    return true
  }

  // ncn 확보
  // 우선순위:
  // 1) formData.ncn
  // 2) formData.contractNum
  // 3) sessionStorage 캐시
  // 4) 인증 API 재호출
  const ensureTerminationNcn = async () => {
    if (formData.ncn) return formData.ncn
    if (formData.contractNum) {
      setTerminationContract(formData.contractNum, 'formData.contractNum')
      return formData.ncn
    }

    const cachedContractNum = sessionStorage.getItem('terminationContractNum')
    if (cachedContractNum) {
      setTerminationContract(cachedContractNum, 'sessionStorage')
      return formData.ncn
    }

    const subscriberNo = `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`
    const customerLinkName = (formData.cstmrNm || '').trim()
    if (!subscriberNo || !customerLinkName) return ''

    try {
      const authResult = await post('/api/form/ktmmember/auth', { subscriberNo, customerLinkName })
      const authData = authResult?.data?.resData || authResult?.data || {}
      const contractNum = authData.contractNum || authData.contract_num || authData.ncn
      const lstComActvDate =
        authData.lstComActvDate ||
        authData.lst_com_actv_date ||
        authData.initActivationDate ||
        ''
      if (authResult?.code === '0000' && contractNum) {
        setTerminationContract(contractNum, 'auth-api')
        formData.custId = authData.customerId || authData.customer_id || formData.custId || ''
        formData.lstComActvDate = lstComActvDate
        if (authData.customerLinkName) formData.cstmrNm = authData.customerLinkName
        if (authData.gender) formData.userGender = authData.gender
        return formData.ncn
      }
      return ''
    } catch (e) {
      console.error('[TerminationStore] auth failed', e)
      return ''
    }
  }

  // X01 가입정보 조회 (핸드폰 인증 완료 후 호출)
  const apiGetMyinfoView = async () => {
    const ncn = formData.ncn || formData.contractNum
    const ctn = `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`
    console.log('[MyinfoView] 가입정보 조회 요청', { ncn, ctn })
    try {
      const data = await post('/api/msf/formServiceChange/changinfo/view', {
        ncn,
        ctn,
        contractNum: ncn,
        custId: formData.custId || '',
      }, { silent: true })
      console.log('[MyinfoView] 가입정보 조회 응답', data)
      const formResponse = data?.data
      const changInfo = formResponse?.resData
      if (formResponse?.resCode === '0000' && changInfo) {
        if (changInfo.custId !== undefined) formData.custId = changInfo.custId || formData.custId || ''
        if (changInfo.ctn !== undefined) {
          const rawCtn = (changInfo.ctn || '').replace(/\D/g, '')
          if (rawCtn.length >= 10) {
            formData.deviceChgTel1 = rawCtn.substring(0, 3)
            formData.deviceChgTel2 = rawCtn.substring(3, rawCtn.length - 4)
            formData.deviceChgTel3 = rawCtn.substring(rawCtn.length - 4)
          }
        }
        if (changInfo.ncn !== undefined) formData.ncn = changInfo.ncn || formData.ncn
        if (changInfo.contractNum !== undefined) formData.contractNum = changInfo.contractNum || formData.contractNum
        if (changInfo.prvRateGrpNm !== undefined) formData.prvRateGrpNm = changInfo.prvRateGrpNm || ''
        if (changInfo.initActivationDate && changInfo.initActivationDate !== '-') {
          formData.initActivationDate = changInfo.initActivationDate
          formData.lstComActvDate = changInfo.initActivationDate
        }
        if (changInfo.addr && changInfo.addr !== '-') formData.addr = changInfo.addr
        // homeTel 있으면 해지 후 연락처, 없으면 해지 휴대폰번호를 해지 후 연락처로 셋팅
        const rawTel = (changInfo.homeTel || '').replace(/\D/g, '')
        if (rawTel) {
          formData.afterTel1 = rawTel.substring(0, 3)
          formData.afterTel2 = rawTel.substring(3, rawTel.length - 4)
          formData.afterTel3 = rawTel.substring(rawTel.length - 4)
        } else {
          formData.afterTel1 = formData.deviceChgTel1 || ''
          formData.afterTel2 = formData.deviceChgTel2 || ''
          formData.afterTel3 = formData.deviceChgTel3 || ''
        }
        if (changInfo.remindBlckYn !== undefined) formData.remindBlckYn = changInfo.remindBlckYn || ''
        if (changInfo.payData !== undefined) formData.payData = changInfo.payData
        if (changInfo.billData !== undefined) formData.billData = changInfo.billData
      } else {
        console.warn('[MyinfoView] 조회 실패', {
          resCode: formResponse?.resCode,
          resMessage: formResponse?.resMessage,
        })
      }
      return changInfo || null
    } catch (e) {
      console.warn('[MyinfoView] 가입정보 조회 실패 (무시하고 진행)', e?.message)
      return null
    }
  }

  // X18 잔여요금·위약금 실시간 조회
  const apiGetRemainCharge = async () => {
    const ncn = formData.ncn || formData.contractNum
    console.log('[X18] 잔여요금 조회 요청', { ncn })
    try {
      // ctn·custId는 백엔드에서 세션 계약 목록으로 조회, ncn만 전송
      const data = await post('/remainCharge/list', { ncn })
      console.log('[X18] 잔여요금 조회 응답', data)
      const formResponse = data?.data
      const remainCharge = formResponse?.resData || {}
      if (formResponse?.resCode === '0000') {
        formData.usageFee = remainCharge.sumAmt || ''
        formData.remainChargeItems = remainCharge.items || []
        formData.remainChargeLoaded = true
        formData.penaltyFee = remainCharge.penaltyFee || ''
        formData.finalAmount = remainCharge.settlementFee || ''
        formData.remainPeriod = remainCharge.remainPeriod || ''
        formData.remainAmount = remainCharge.remainAmount
        formData.lstComActvDate = remainCharge.lstComActvDate || remainCharge.initActivationDate || formData.lstComActvDate
      } else {
        console.warn('[X18] 조회 실패', {
          resCode: formResponse?.resCode,
          resMessage: formResponse?.resMessage,
        })
      }
      return data
    } catch (e) {
      console.error('[X18] 잔여요금 조회 실패 (네트워크/서버 오류)', e)
      formData.remainChargeLoaded = false
      return null
    }
  }

  // 단계별 초기화
  const resetStep = (step) => {
    if (step === 1) {
      applicationKey.value = ''
      return
    }

    if (step === 2) {
      formData.usageFee = ''
      formData.penaltyFee = ''
      formData.finalAmount = ''
      formData.remainPeriod = ''
      formData.remainAmount = ''
      formData.remainChargeLoaded = false
      formData.remainChargeItems = []
      formData.memo = ''
      authFlags.value.cancelPhone = false
      authFlags.value.repPhone = false
      return
    }

    if (step === 3) {
      formData.agreeCheck1 = false
      formData.agreeCheck2 = false
      formData.agreeCheck3 = false
    }
  }

  const normalizeReceiveWayCd = (value) => {
    const normalized = (value || '').trim()
    if (normalized === 'postMethod1' || normalized.toUpperCase() === 'P' || normalized.toLowerCase() === 'mail') return 'P'
    if (normalized === 'postMethod2' || normalized.toUpperCase() === 'E' || normalized.toLowerCase() === 'email') return 'E'
    return ''
  }

  // 백엔드 요청 스키마(customer/product/agreement) 매핑
  const buildCompletePayload = () => ({
    receiveWayCd: normalizeReceiveWayCd(formData.postMethod),
    customer: {
      managerCd: formData.managerCd,
      managerNm: formData.managerNm,
      agentCd: formData.agentCd,
      agentNm: formData.agentNm,
      customerType: formData.cstmrTypeCd,
      identityCertTypeCd: formData.identityCertTypeCd,
      identityTypeCd: formData.identityTypeCd,
      identityIssuDate: formData.identityIssuDate,
      identityIssuRegion: formData.identityIssuRegion,
      driveLicnsNo: formData.driveLicnsNo,
      selfIssuNo: formData.selfIssuNo,
      userName: formData.cstmrNm,
      userBirthDate: formData.userBirthDate,
      cancelPhone1: formData.deviceChgTel1,
      cancelPhone2: formData.deviceChgTel2,
      cancelPhone3: formData.deviceChgTel3,
      afterTel1: formData.afterTel1,
      afterTel2: formData.afterTel2,
      afterTel3: formData.afterTel3,
      postMethod: formData.postMethod,
      agencyName: formData.agencyName,
      cpntId: formData.cpntId,
      cpntNm: formData.cpntNm,
      cntpntShopCd: formData.cntpntShopCd,
      cntpntShopNm: formData.cntpntShopNm,
      ncn: formData.ncn || formData.contractNum,
      custId: formData.custId,
    },
    product: {
      isActive: formData.isActive,
      usageFee: formData.usageFee,
      penaltyFee: formData.penaltyFee,
      finalAmount: formData.finalAmount,
      remainPeriod: formData.remainPeriod,
      remainAmount: formData.remainAmount,
      memo: formData.memo,
    },
    agreement: {
      agreeCheck1: !!formData.agreeCheck1,
      agreeCheck2: !!formData.agreeCheck2,
      agreeCheck3: !!formData.agreeCheck3,
    },
  })

  // 작성완료 API
  const apiCompleteApplication = async () => {
    try {
      const payload = buildCompletePayload()
      console.debug('[apiCompleteApplication] request', {
        applicationKey: applicationKey.value,
        ncn: payload?.customer?.ncn,
        customerType: payload?.customer?.customerType,
        isActive: payload?.product?.isActive,
      })
      const data = await post(`/api/msf/formTermination/${applicationKey.value}/complete`, payload)
      console.debug('[apiCompleteApplication] response', data)
      const formResponse = data?.data
      if (formResponse?.resCode === '0000') {
        completeErrorMessage.value = ''
        console.info('[apiCompleteApplication] success', { applicationNo: formResponse?.resData?.applicationNo })
        return true
      }
      completeErrorMessage.value = formResponse?.resMessage || DEFAULT_ERROR_MESSAGE
      console.warn('[apiCompleteApplication] failed response', data)
      return false
    } catch (e) {
      completeErrorMessage.value = e?.response?.data?.data?.resMessage || DEFAULT_ERROR_MESSAGE
      console.error('[apiCompleteApplication] exception', {
        message: e?.message,
        status: e?.response?.status,
        response: e?.response?.data,
      })
      return false
    }
  }

  const getCompleteErrorMessage = () => completeErrorMessage.value || DEFAULT_ERROR_MESSAGE

  return {
    applicationKey,
    formData,
    authFlags,
    completeErrorMessage,
    getCompleteErrorMessage,
    setTerminationContract,
    ensureTerminationNcn,
    resetStep,
    apiGetMyinfoView,
    apiGetRemainCharge,
    apiCompleteApplication,
  }
})
