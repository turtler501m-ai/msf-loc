import { defineStore } from "pinia";
import { computed, reactive, ref } from "vue";
import { post, postRaw } from "@/libs/api/msf.api";
import { showAlert } from "@/libs/utils/comp.utils";
import { isLocal } from "@/libs/utils/env.utils";

export const useMsfFormSvcChgStore = defineStore('msf_form_svc_chg', () => {
  const DEFAULT_COMPLETE_ERROR_MESSAGE = '서비스 처리 중 오류가 발생했습니다.'
  // 작성완료 후 공통 완료 화면의 신청서 열람/발송에 사용할 접수번호
  const requestKey = ref('')
  const documentId = ref([])
  const eformsignFileData = ref([])
  const completeErrorMessage = ref('')
  const completeNoticeMessage = ref('')
  const completeResultData = ref({})
  const completeFinalFailed = ref(false)
  const cancelAuthResetKey = ref(0)
  const validateCustomerWithAlert = ref(() => true)
  const validateCustomerAgeWithAlert = ref(() => true)

  // 희망번호 조회 횟수 제한 (세션용, 서버 저장 안 함)
  const wishNoSearchCount = ref(0)
  const incrementWishNoSearchCount = () => {
    wishNoSearchCount.value++
  }

  // Step 1: Customer Info
  const formData = reactive({
    parentScanId: '',

    /** 고객유형: NA 내국인 | FN 외국인 | NM 미성년자 | FM 외국인미성년자 | JP 법인 | GO 공공기관 */
    /** 서비스대상코드: R12.무선데이터차단 R11.부가서비스 P11.요금제변경 O11.번호변경 O12.분실복구 R14.단말보험 O13.USIM변경 R15.데이타쉐어링 R16.아무나SOLO */
    /** 방문유형: VMY 본인 | VDP 법정대리인  */
    cstmrTypeCd: 'NA', // 고객유형
    customerTypeLocked: false,

    /* 스마트서식지 테스트 데이타
백경빈 19671009 010-8370-3868
김성인 19810217 010-2594-7538
김아람 19901122 010-4883-3628
박해준 19760911 010-3001-2343 데이타쉐어링 해지성공 (서식지X)
이경준 19900731 010-9694-5432 데이타쉐어링 실패
최윤경 19581007 010-8482-8501 이메일없음/상세주소x  무선데이타 명의변경 아무나SOLO
곽영환 19831202 010-2714-8794 일시정지
김현진 19981217 010-3368-7403 번호변경
양우철 19741219 010-2658-3860 이메일없음 >> 010-2872-3860 번호 변경
김동혁 19911011 010-6284-0085

황인선 19970701 010-4905-7521
황순철 19861111 010-4249-6249
박시아 19901020 010-8079-9071
이욱이 19811207 010-8252-5582 이메일없음

김세희 19860830 010-2646-0935 USIM단독개통
박민건 19810202 010-2276-9013 전화번호 X
     */

    /* 가입자 정보 */
    cstmrNm: isLocal() ? '김현진' : '', //이름
    userBirthDate: isLocal() ? '19981217' : '', //생년월일
    deviceChgTel1: isLocal() ? '010' : '', //변경휴대폰번호1
    deviceChgTel2: isLocal() ? '3368' : '', //변경휴대폰번호2
    deviceChgTel3: isLocal() ? '7403' : '', //변경휴대폰번호3
    userGender: 'M', //성별
    cstmrForeignerRrn1: '',
    cstmrForeignerRrn2: '',
    cstmrJuridicalRrn1: '', //법인등록번호1
    cstmrJuridicalRrn2: '', //법인등록번호2
    cstmrJuridicalBizNo1: '', //사업자등록번호1
    cstmrJuridicalBizNo2: '', //사업자등록번호2
    cstmrJuridicalBizNo3: '', //사업자등록번호3
    cstmrPrivateBizNoIssuDate: '', //개인사업자 사업자등록번호 발급일자
    cstmrJuridicalRepNm: '', //대표자명
    contractNum: '', // 인증 응답 계약번호
    ncn: '',
    custId: '',
    cstmrVisitTypeCd: '',
    formType: 'SERVICECHANGE',

    /* 가입정보 조회(getMyinfoView) 결과 */
    prvRateGrpNm: '', // 현재 요금제명
    initActivationDate: '', // 개통일자
    lstComActvDate: '', // 개통일자(동기화용)
    addr: '', // 주소
    remindBlckYn: '', // 해지 제한 여부
    subStatus: '', // 회선 상태(A: 사용중, S: 정지)
    payData: null, // 납부방법
    billData: null, // 명세서
    /* 법정대리인 정보 */
    repName: '', //이름
    repBirthDate: '', //생년월일
    repGender: 'M', //성별
    repRelation: '', //관계
    repPhone1: '', // 연락처1
    repPhone2: '', // 연락처2
    repPhone3: '', // 연락처3
    repPhoneAuth: '', //인증번호입력
    repAgree: false, //동의
    /** 대리인 위임 정보 */
    minorAgentNm: '', //위임받은고객이름
    agentBirthDate: '', //생년월일
    agentGender: '', //성별
    minorAgentRelTypeCd: '', //관계
    minorAgentRelTypeNm: '', //관계명
    minorAgentTelFnNo: '', //연락처1
    minorAgentTelMnNo: '', //연락처2
    minorAgentTelRnNo: '', //연락처3
    /* 가입자 연락처 */
    mobileNo1: '010', //휴대폰번호1
    mobileNo2: '', //휴대폰번호2
    mobileNo3: '', //휴대폰번호3
    telNo1: '', //전화번호 지역번호
    telNo2: '', //전화번호 가운데
    telNo3: '', //전화번호 끝
    emailAddr1: '', //이메일 아이디
    emailAddr2: '', //이메일 도메인
    zipNo: '', //우편번호
    address: '', //주소
    detailAddress: '', //상세주소
    /** 서비스 변경 선택_type01__디자인미확정 */
    allCheck: '', //전체 선택
    serviceSelect: [], // 서비스선택
    serviceList: [], //서비스리스트
    serviceCheckYn: 'N', //서비스 체크 여부
    serviceChecked: false, //서비스 체크 완료 여부
    serviceSelectCompleteYn: 'N', //서비스 선택 완료 여부
    serviceSelectCompleted: false, //서비스 선택 완료 여부
    serviceSelectionLocked: false, //서비스상품 다음 버튼 완료 후 서비스변경 선택 영역 잠금 여부
    serviceAreaLoadingTargets: [], // 다음 버튼 이후 초기 데이터 로딩 대기 서비스 목록
    additionList: [], // 부가서비스 선택 목록
    additionCancelList: [], // 부가서비스 해지 목록
    additionConfirmCompleted: false, // 부가서비스 작성 완료 여부
    additionInitialLoading: false,
    appConfirmCompleted: false, // 신청서 확인 완료 여부
    reportSignatureCompleted: false, // 판매자/가입자 리포트 서명 검증 완료 여부
    completeApplicationCompleted: false,
    signTgtSbst: '',
    blockService: null, // 무선데이터차단 선택
    managerCd: '',
    managerNm: '',
    agentCd: '',
    ktOrgId: '',
    agentNm: '',
    shopCd: '',
    shopNm: '',
    realShopNm: '',
    telephone: '',
    representativeTelephone: '',
    cpntId: '',
    cpntNm: '',
    cntpntShopCd: '',
    cntpntShopNm: '',
    /** 서비스 변경 선택_type02__디자인미확정 */
    addonService: '', //요금제/부가 서비스
    combinedService: '', //결합서비스
    loseLock: '', //일시/분실정지
    joinInfoChange: '', //가입정보 변경
    wirelessBlockConfirmCompleted: false, // 무선데이터차단 확인 완료 여부
    /* P11: 요금제변경 */
    actCode: '',
    planName1: '',
    planName2: '',
    changeDate: '',
    planFtrNewParam: '',
    planChangeConfirmCompleted: false,
    currentProdId: '', // 현재 요금제
    currentProdNm: '',
    currentProdStdt: '',
    currentProdAmt: '',
    reservedProdId: '', // 예약된 요금제
    reservedProdNm: '',
    jehuPartnerTypeCd: '', // 제휴 정보 추가
    jehuPartnerTypeNm: '',
    jehuProdTypeCd: '',
    jehuProdName: '',
    repAgree1: false,
    repAgree2: false,
    /* O11: 번호변경 */
    reqWantFnNo: '',
    reqWantMnNo: '',
    reqWantRnNo: '',
    wishNo: '',
    wishNoc: '',
    wishMarket: '',
    numberChgConfirmCompleted: false,
    /* O12: 분실복구/일시정지해제 */
    unLockPw: '',
    unpauseConfirmCompleted: false,
    /* R14: 단말보험 */
    clauseInsuranceYn: '',
    recCat1: '',
    recCat2: '',
    reqBuyType: '',
    rprsPrdtId: '',
    insuranceDeviceOs: '',
    insuranceAgree: false,
    insuranceConfirmCompleted: false,
    /* O13: SIM정보 */
    hasSim: true, //SIM보유
    simTypeCd: '', //SIM타입
    usimKindsCd: '',
    reqUsimSn: '',
    reqUsimNm: null,
    eid: null,
    imei1: null,
    imei2: null,
    simPurchaseMethod: null,
    reqUsimConfirmCompleted: false,
    /* R15: 데이터쉐어링 */
    shareUseState: '',
    sharePhoneNum: '',
    shareUsimNum: isLocal() ? '8982300321000303121' : '',
    dataSharingSubscribed: false,
    dataSharingTargetNo: '',
    dataSharingAuthCompleted: false,
    dataSharingUsimCheckCompleted: false,
    dataSharingAvailableChecked: false,
    dataSharingAgreementCompleted: false,
    dataSharingConfirmCompleted: false,
    dataSharingMessage: '',
    dataSharingPlanName: '',
    /* R16: 결합Solo */
    soloData: '',
    combineSoloConfirmCompleted: false,
    termsAgreed: false,
    uploadedDocs: [],
    msfRequestDocList: [],
    memo: '',
  })

  const authFlags = ref({
    deviceChgTel: false,
    repPhone: false,
    insurance: false,
    dataSharingPhone: false,
    dataSharingUsim: false,
    unpause: false,
    numberChg: false,
    combineSelfAuth: false,
    reqUsimSn: false,
    requiredDocs: false,
  })

  const initialFormData = JSON.parse(JSON.stringify(formData))
  const initialAuthFlags = JSON.parse(JSON.stringify(authFlags.value))

  const resetAll = async () => {
    Object.keys(formData).forEach((key) => {
      delete formData[key]
    })
    Object.assign(formData, JSON.parse(JSON.stringify(initialFormData)))

    authFlags.value = JSON.parse(JSON.stringify(initialAuthFlags))
    requestKey.value = ''
    documentId.value = []
    eformsignFileData.value = []
    completeErrorMessage.value = ''
    completeNoticeMessage.value = ''
    completeResultData.value = {}
    completeFinalFailed.value = false
    wishNoSearchCount.value = 0
    validateCustomerWithAlert.value = () => true
    validateCustomerAgeWithAlert.value = () => true
    cancelAuthResetKey.value++
  }

  const resetAuthForEdit = () => {
    formData.customerTypeLocked = true
    documentId.value = []
    eformsignFileData.value = []
    formData.isVerified = false
    formData.isScanVerified = false
    formData.isSaved = false
    formData.repAgree = false
    formData.insuranceDeviceOs = ''
    formData.insuranceAgree = false
    formData.insuranceConfirmCompleted = false
    formData.termsAgreed = false
    formData.serviceSelectionLocked = false
    formData.serviceSelectCompleted = false
    formData.completeApplicationCompleted = false
    formData.appConfirmCompleted = false
    formData.reportSignatureCompleted = false
    formData.planChangeConfirmCompleted = false
    formData.repAgree1 = false
    formData.repAgree2 = false
    formData.additionConfirmCompleted = false
    formData.wirelessBlockConfirmCompleted = false
    formData.numberChgConfirmCompleted = false
    formData.wishNo = ''
    formData.wishNoc = ''
    formData.wishMarket = ''
    formData.unpauseConfirmCompleted = false
    formData.dataSharingAuthCompleted = false
    formData.dataSharingUsimCheckCompleted = false
    formData.dataSharingAvailableChecked = false
    formData.dataSharingAgreementCompleted = false
    formData.dataSharingConfirmCompleted = false
    formData.dataSharingPlanName = ''
    formData.soloData = ''
    formData.combineSoloConfirmCompleted = false
    formData.reqUsimConfirmCompleted = false
    if (Array.isArray(formData.clauses)) {
      formData.clauses = formData.clauses.map((item) => ({ ...item, checked: false }))
    }
    if (authFlags.value) {
      Object.keys(authFlags.value).forEach((key) => {
        authFlags.value[key] = false
      })
    }
    cancelAuthResetKey.value++
  }

  // 서비스변경 후속 업무는 NCN(SVC_CNTR_NO)이 기준이다.
  // CONTRACT_NUM은 인증 응답 보관용으로만 두고, ncn 대체값으로 사용하지 않는다.
  const getServiceChangeNcn = () => formData.ncn || ''

  const apiGetMyinfoView = async () => {
    const ncn = getServiceChangeNcn()
    const ctn = `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`
    console.log('[변경][MyinfoView] 가입정보 조회 요청', { ncn, ctn })
    if (!ncn) {
      console.warn('[변경][MyinfoView] 가입정보조회 중단', {
        reason: 'missing svcCntrNo',
        contractNum: formData.contractNum,
        ctn,
      })
      return null
    }

    try {
      const data = await post(
        '/api/msf/formServiceChange/changinfo/view',
        {
          ncn,
          ctn,
          contractNum: formData.contractNum || '',
          custId: formData.custId || '',
        },
        { silent: true },
      )
      console.log('[변경][MyinfoView] 가입정보 조회 응답', data)
      const formResponse = data?.data
      const changInfo = formResponse?.resData
      if (formResponse?.resCode === '0000' && changInfo) {
        if (changInfo.prvRateGrpNm !== undefined)
          formData.prvRateGrpNm = changInfo.prvRateGrpNm || ''
        if (changInfo.initActivationDate && changInfo.initActivationDate !== '-') {
          formData.initActivationDate = changInfo.initActivationDate
          formData.lstComActvDate = changInfo.initActivationDate
        }
        if (changInfo.addr && changInfo.addr !== '-') formData.addr = changInfo.addr
        if (changInfo.remindBlckYn !== undefined)
          formData.remindBlckYn = changInfo.remindBlckYn || ''
        if (changInfo.subStatus !== undefined || changInfo.status !== undefined) {
          formData.subStatus = changInfo.subStatus || changInfo.status || ''
        }
        if (changInfo.payData !== undefined) formData.payData = changInfo.payData
        if (changInfo.billData !== undefined) formData.billData = changInfo.billData

        const bizNo =
          changInfo.cstmrJuridicalBizNo ||
          changInfo.bizNo ||
          changInfo.businessNo ||
          changInfo.brNo ||
          ''
        if (bizNo) {
          const rawBizNo = String(bizNo).replace(/\D/g, '')
          formData.cstmrJuridicalBizNo1 = rawBizNo.substring(0, 3)
          formData.cstmrJuridicalBizNo2 = rawBizNo.substring(3, 5)
          formData.cstmrJuridicalBizNo3 = rawBizNo.substring(5, 10)
        }
        if (changInfo.cstmrJuridicalBizNo1 !== undefined) {
          formData.cstmrJuridicalBizNo1 =
            changInfo.cstmrJuridicalBizNo1 || formData.cstmrJuridicalBizNo1
        }
        if (changInfo.cstmrJuridicalBizNo2 !== undefined) {
          formData.cstmrJuridicalBizNo2 =
            changInfo.cstmrJuridicalBizNo2 || formData.cstmrJuridicalBizNo2
        }
        if (changInfo.cstmrJuridicalBizNo3 !== undefined) {
          formData.cstmrJuridicalBizNo3 =
            changInfo.cstmrJuridicalBizNo3 || formData.cstmrJuridicalBizNo3
        }

        // 주소 ← selectCntrListNoLogin BAN 주소
        if (changInfo.zipNo && changInfo.zipNo !== '-') {
          formData.zipNo = changInfo.zipNo
        }
        if (changInfo.address && changInfo.address !== '-') {
          formData.address = changInfo.address
        } else if (changInfo.addr && changInfo.addr !== '-') {
          formData.address = changInfo.addr
        }
        if (changInfo.detailAddress && changInfo.detailAddress !== '-') {
          formData.detailAddress = changInfo.detailAddress
        }

        // 이메일 ← email (아이디@도메인 분리)
        if (changInfo.email && changInfo.email.includes('@')) {
          const [id, domain] = changInfo.email.split('@')
          formData.emailAddr1 = id || ''
          formData.emailAddr2 = domain || ''
        }

        formData.reqBuyType = changInfo.reqBuyType
        formData.rprsPrdtId = changInfo.rprsPrdtId
      } else {
        console.warn('[변경][MyinfoView] 조회 실패', {
          resCode: formResponse?.resCode,
          resMessage: formResponse?.resMessage,
        })
        return null
      }
      return changInfo || null
    } catch (e) {
      console.warn('[변경][MyinfoView] 가입정보 조회 실패 (무시하고 진행)', e?.message)
      return null
    }
  }

  const getPhoneNo = () =>
    `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`

  const getCommonAdditionPayload = () => ({
    ncn: getServiceChangeNcn(),
    ctn: getPhoneNo(),
    custId: formData.custId || '',
  })

  const getServiceCode = (svc = {}) =>
    String(svc.rateCd || svc.soc || svc.prodId || svc.addSvcCd || '')

  const getServiceName = (svc = {}) =>
    svc.rateNm ||
    svc.socDescription ||
    svc.prodNm ||
    svc.addSvcNm ||
    svc.serviceName ||
    getServiceCode(svc)

  const getProductSeqNo = (svc = {}) =>
    String(
      svc.prodHstSeq || svc.prdcSeqNo || svc.prodSeqNo || svc.productSeqNo || svc.svcSeqNo || '',
    )

  const getReqUsimNm = () => {
    if (formData.simTypeCd === 'ESIM' || formData.usimKindsCd === '09') return ''
    return formData.reqUsimNm || ''
  }

  const getFtrNewParam = (svc = {}) => {
    const serviceCode = getServiceCode(svc).toUpperCase()
    if (['RNGTOUPR3', 'SKCOREPAC', 'XRINGMON', 'XRINGWEEK'].includes(serviceCode)) {
      return ''
    }
    if (svc.ftrNewParam) return String(svc.ftrNewParam)

    const settingData = svc.addSvcSettingData || {}
    if (Object.prototype.hasOwnProperty.call(settingData, 'ftrNewParam')) {
      return String(settingData.ftrNewParam || '')
    }
    const entries = Object.entries(settingData).filter(
      ([, value]) => value !== undefined && value !== null,
    )
    if (entries.length === 0) return ''

    return entries.map(([key, value]) => `${key}=${value}`).join('|')
  }

  const getUniqueServices = (services = []) => {
    const seen = new Set()
    return services.filter((svc) => {
      const key = `${getServiceCode(svc)}|${getProductSeqNo(svc)}|${svc.action || ''}`
      if (!getServiceCode(svc) || seen.has(key)) return false
      seen.add(key)
      return true
    })
  }

  const getSelectedServiceTypes = () =>
    Array.isArray(formData.serviceSelect) ? formData.serviceSelect : []

  const getServiceTargetCd = (svcTypeCd) => {
    const target = (formData.serviceList || []).find((item) => item.value === svcTypeCd)
    return target?.svcTgtCd || svcTypeCd
  }

  const getWirelessBlockAddServices = () => {
    if (formData.blockService !== 'blockService2') return []
    return [
      {
        soc: 'WIRELESSC',
        serviceName: '무선데이터차단서비스 가입',
        ftrNewParam: '',
        flag: '',
        svcTgtCd: getServiceTargetCd('R12'),
      },
    ]
  }

  const getWirelessBlockCancelServices = () => {
    if (formData.blockService !== 'blockService1') return []
    return [
      {
        soc: 'WIRELESSC',
        serviceName: '무선데이터차단서비스 해지',
        prodHstSeq: '',
        svcTgtCd: getServiceTargetCd('R12'),
      },
    ]
  }

  // ServiceChangeProduct.vue의 CONFIRM_REQUIRED_MAP과 동일하게 유지
  const CONFIRM_REQUIRED_MAP = {
    P11: 'planChangeConfirmCompleted',
    O11: 'numberChgConfirmCompleted',
    O12: 'unpauseConfirmCompleted',
    R11: 'additionConfirmCompleted',
    R12: 'wirelessBlockConfirmCompleted',
    R14: 'insuranceConfirmCompleted',
    O13: 'reqUsimConfirmCompleted',
    R15: 'dataSharingConfirmCompleted',
    R16: 'combineSoloConfirmCompleted',
  }

  const isReadyToComplete = () => {
    const selectedTypes = getSelectedServiceTypes()
    for (const [type, field] of Object.entries(CONFIRM_REQUIRED_MAP)) {
      if (selectedTypes.includes(type) && formData[field] !== true) return false
    }
    // R12(무선데이터차단)는 blockService 선택 여부도 추가 검증
    if (selectedTypes.includes('R12') && !formData.blockService) return false
    return true
  }

  const isFormResponseSuccess = (res) => {
    const raw = res?.data?.code !== undefined ? res.data : res
    const formResponse = raw?.data || {}
    return raw?.code === '0000' && (!formResponse?.resCode || formResponse.resCode === '0000')
  }

  const getDataSharingBasePayload = (override = {}) => ({
    custId: formData.custId || '',
    ncn: formData.ncn || '',
    contractNum: formData.contractNum || '',
    subStatus: formData.subStatus || '',
    ctn: `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`,
    ...override,
  })

  const getFormResponse = (response) => response?.data?.data || response?.data || response
  const isWrappedSuccess = (response) => {
    const raw = response?.data || response
    const formResponse = getFormResponse(response)
    return raw?.code === '0000' && (!formResponse?.resCode || formResponse.resCode === '0000')
  }
  const getWrappedMessage = (response) => {
    const raw = response?.data || response
    const formResponse = getFormResponse(response)
    return formResponse?.resMessage || raw?.message || ''
  }

  const apiDataSharingList = async () => {
    const payload = getDataSharingBasePayload()
    console.log('[dataSharing] dataSharingStep2 req:', payload)
    const response = await postRaw('/api/msf/formServiceChange/dataSharingStep2', payload)
    const result = {
      success: isWrappedSuccess(response),
      message: getWrappedMessage(response),
      data: getFormResponse(response)?.resData || {},
      response,
    }
    console.log('[dataSharing] dataSharingStep2 res:', result)
    return result
  }

  const apiDataSharingCheck = async (opmdSvcNo) => {
    const payload = getDataSharingBasePayload({ opmdSvcNo })
    console.log('[dataSharing] dataSharingCheckAjax req:', payload)
    const response = await postRaw('/api/msf/formServiceChange/dataSharingCheckAjax', payload)
    const result = {
      success: isWrappedSuccess(response),
      message: getWrappedMessage(response),
      data: getFormResponse(response)?.resData || {},
      response,
    }
    console.log('[dataSharing] dataSharingCheckAjax res:', result)
    return result
  }

  const apiDataSharingUsimCheck = async (iccId) => {
    console.log('[dataSharing] usiminfo/verify req: iccId=', iccId)
    const response = await postRaw('/api/form/usiminfo/verify', {
      iccId,
      agentCd: formData.agentCd || '',
      hasSim: true,
    })
    const result = {
      success: isWrappedSuccess(response),
      message: getWrappedMessage(response),
      data: getFormResponse(response)?.resData || {},
      response,
    }
    console.log('[dataSharing] usiminfo/verify res:', result)
    return result
  }

  const getResponseMessage = (res) =>
    res?.data?.resMessage || res?.message || DEFAULT_COMPLETE_ERROR_MESSAGE

  const escapeHtml = (value) =>
    String(value ?? '')
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#39;')

  const formatCountSummary = (line = '') => {
    const match = String(line).match(
      /^총\s*(\d+)건\s*중\s*처리\s*(\d+)건\s*\(성공\s*(\d+)건\/실패\s*(\d+)건\),\s*미처리\s*(\d+)건$/,
    )
    if (!match) return line

    const [, total, processed, success, failed, unprocessed] = match.map(Number)
    const processedParts = []
    if (success > 0) processedParts.push(`성공 ${success}건`)
    if (failed > 0) processedParts.push(`실패 ${failed}건`)

    const parts = [`총 ${total}건 중`]
    if (processed > 0) {
      parts.push(
        `처리 ${processed}건${processedParts.length ? `(${processedParts.join('/')})` : ''}`,
      )
    }
    if (unprocessed > 0) parts.push(`미처리 ${unprocessed}건`)

    return parts.join(' ')
  }

  const formatCompleteErrorMessage = (message) => {
    const lines = String(message || DEFAULT_COMPLETE_ERROR_MESSAGE).split(/\r?\n/)
    return lines
      .map((line, index) => {
        const text = index === 1 ? formatCountSummary(line) : line
        const escaped = escapeHtml(text)
        return index === 1
          ? `<span style="display:block;margin-top:8px;font-size:13px;font-weight:400;line-height:1.45;color:#666;">${escaped}</span>`
          : escaped
      })
      .join('<br>')
  }

  const getCompleteResult = (res) =>
    res?.data?.resData || res?.data?.data?.resData || res?.resData || {}

  const getProcessResults = (result = {}) =>
    Array.isArray(result.processResults) ? result.processResults : []

  const logCompleteResult = (res, completeResult = {}) => {
    const processResults = getProcessResults(completeResult)
    const successResults = processResults.filter((item) => item && item.success === true)
    const failedResults = processResults.filter((item) => item && item.success === false)

    console.log('[변경][작성완료] complete 응답 원본', res)
    console.log('[변경][작성완료] complete 결과 요약', {
      resCode: completeResult?.resCode || res?.data?.resCode || res?.data?.data?.resCode || '',
      resMessage:
        completeResult?.resMessage || res?.data?.resMessage || res?.data?.data?.resMessage || '',
      totalCount: processResults.length,
      successCount: successResults.length,
      failCount: failedResults.length,
      successServiceSelect: [
        ...new Set(successResults.map(getProcessResultServiceCode).filter(Boolean)),
      ],
      successResults,
      failedResults,
    })
  }

  const getProcessCounts = (result = {}) => {
    const processResults = getProcessResults(result)
    if (processResults.length > 0) {
      const successCount = processResults.filter((item) => item && item.success === true).length
      const failCount = processResults.filter((item) => item && item.success === false).length
      const processedCount = successCount + failCount
      return {
        totalCount: processResults.length,
        successCount,
        failCount,
        processedCount,
        unprocessedCount: Math.max(0, processResults.length - processedCount),
      }
    }

    const totalCount = Number(result.addCount || 0) + Number(result.cancelCount || 0)
    const successCount =
      Number(result.addSuccessCount || 0) + Number(result.cancelSuccessCount || 0)
    const failCount = Number(result.addFailCount || 0) + Number(result.cancelFailCount || 0)
    const processedCount = successCount + failCount
    return {
      totalCount,
      successCount,
      failCount,
      processedCount,
      unprocessedCount: Math.max(0, totalCount - processedCount),
    }
  }

  const hasPartialSuccess = (result = {}) => {
    const { successCount, failCount } = getProcessCounts(result)
    return successCount > 0 && failCount > 0
  }

  const isFinalCompleteFailure = (result = {}) => {
    const processResults = getProcessResults(result)
    if (!result?.requestKey || processResults.length === 0) return false
    const successCount = processResults.filter((item) => item && item.success === true).length
    const failCount = processResults.filter((item) => item && item.success === false).length
    return successCount === 0 && failCount > 0
  }

  const getProcessResultServiceCode = (item = {}) => {
    if (item.svcTgtCd) return item.svcTgtCd
    if (item.action === 'PLANCHG' || item.action === 'PLANRESERVECHG') return 'P11'
    if (item.action === 'NUMBERCHGE') return 'O11'
    if (item.action === 'UNPAUSE') return 'O12'
    if (item.action === 'INSR') return 'R14'
    if (item.action === 'USIM') return 'O13'
    if (item.action === 'DATASHARING') return 'R15'
    if (item.action === 'COMBINE') return 'R16'
    return ''
  }

  const getSuccessfulServiceSelect = () => [
    ...new Set(
      getProcessResults(completeResultData.value)
        .filter((item) => item && item.success === true)
        .map(getProcessResultServiceCode)
        .filter(Boolean),
    ),
  ]

  const getSuccessfulProcessResults = () =>
    getProcessResults(completeResultData.value)
      .filter((item) => item && item.success === true)
      .map((item) => ({
        action: item.action || '',
        svcTgtCd: getProcessResultServiceCode(item),
        procTypeCd: item.procTypeCd || '',
        soc: item.soc || '',
        serviceName: item.serviceName || '',
        prodHstSeq: item.prodHstSeq || '',
        success: true,
        resCode: item.resCode || '',
        resMessage: item.resMessage || '',
      }))
      .filter((item) => item.svcTgtCd)

  const getProcessActionLabel = (action = '') => {
    if (action === 'ADD' || action === 'INSR') return '가입'
    if (action === 'CANCEL') return '해지'
    if (action === 'PLANRESERVECHG') return '예약'
    return '처리'
  }

  const formatProcessResultDetailName = (name, action) => {
    const cleanName = String(name || '서비스').trim()
    if (!action || cleanName.endsWith(action)) return cleanName
    return `${cleanName} ${action}`
  }

  const sortProcessResultsByStatus = (items = []) =>
    [...items].sort((a, b) => {
      if (a?.success === b?.success) return 0
      return a?.success === true ? -1 : 1
    })

  const formatProcessResultDetailText = (item = {}) => {
    const action = getProcessActionLabel(item.action)
    const name = item.serviceName || item.soc || '서비스'
    const displayName = formatProcessResultDetailName(name, action)
    const status = item.success === true ? '성공' : '실패'
    const message = item.success === false && item.resMessage ? `: ${item.resMessage}` : ''
    return `${displayName} ${status}${message}`
  }

  const formatProcessResultPlainMessage = (title, result = {}) => {
    const { totalCount, successCount, failCount, processedCount, unprocessedCount } =
      getProcessCounts(result)
    const detail = sortProcessResultsByStatus(
      getProcessResults(result).filter(
        (item) => item && (item.success === true || item.success === false),
      ),
    )
      .map(formatProcessResultDetailText)
      .filter(Boolean)
      .join('\n')
    const countMessage = `총 ${totalCount}건 중 처리 ${processedCount}건(성공 ${successCount}건/실패 ${failCount}건), 미처리 ${unprocessedCount}건`

    return detail ? `${title}\n${countMessage}\n${detail}` : `${title}\n${countMessage}`
  }

  const formatPartialSuccessMessage = (result = {}) => {
    const { totalCount, successCount, failCount, processedCount, unprocessedCount } =
      getProcessCounts(result)
    const countMessage = formatCountSummary(
      `총 ${totalCount}건 중 처리 ${processedCount}건(성공 ${successCount}건/실패 ${failCount}건), 미처리 ${unprocessedCount}건`,
    )
    const details = sortProcessResultsByStatus(
      getProcessResults(result).filter(
        (item) => item && (item.success === true || item.success === false),
      ),
    )
      .map((item) => {
        const statusClass = item.success === true ? 'is-success' : 'is-fail'
        return `<div class="partial-result-item ${statusClass}">${escapeHtml(formatProcessResultDetailText(item))}</div>`
      })
      .join('')

    return [
      `<div class="partial-result-title">${escapeHtml('일부 서비스만 처리되었습니다.')}</div>`,
      `<div class="partial-result-summary">${escapeHtml(countMessage)}</div>`,
      details ? `<div class="partial-result-list">${details}</div>` : '',
    ]
      .filter(Boolean)
      .join('<br>')
  }

  const getFilePathName = (file = {}) =>
    file.pathFileName ||
    file.filePathNm ||
    file.recFilePathNm ||
    file.filePath ||
    file.file?.filePath ||
    file.file?.pathFileName ||
    ''

  const getEformImageSystemFiles = () =>
    (Array.isArray(eformsignFileData.value) ? eformsignFileData.value : [])
      .map((file, index) => ({
        pathFileName: getFilePathName(file),
        fileTypeCd: '',
        filePageNo: index + 1,
      }))
      .filter((file) => file.pathFileName)

  const getRequiredDocImageSystemFiles = () =>
    (Array.isArray(formData.msfRequestDocList) ? formData.msfRequestDocList : [])
      .map((doc) => ({
        pathFileName: getFilePathName(doc),
        fileTypeCd: doc.fileTypeCd || '',
        filePageNo: doc.filePageNo !== undefined ? Number(doc.filePageNo) : 1,
      }))
      .filter((file) => file.pathFileName)

  const getReportDocumentTypes = (serviceTypes = getSelectedServiceTypes()) => {
    const types = ['servicechange']
    if (serviceTypes.includes('R15') && formData.shareUseState === 'shareUseState1') {
      types.push('datasharing')
    }
    if (serviceTypes.includes('R14') && formData.clauseInsuranceYn === 'Y') {
      types.push('insurance')
    }
    return types
  }

  const getReportFilesPayload = (serviceTypes = getSelectedServiceTypes()) => {
    const documentTypes = getReportDocumentTypes(serviceTypes)
    return getEformImageSystemFiles().map((file, index) => ({
      ...file,
      documentType: documentTypes[index] || '',
      documentId: Array.isArray(documentId.value) ? documentId.value[index] || '' : '',
    }))
  }

  const getRequiredDocFilesPayload = () =>
    getRequiredDocImageSystemFiles().map((file) => ({
      ...file,
      documentType: 'requiredDoc',
    }))

  const apiCompleteAdditionApplication = async () => {
    completeErrorMessage.value = ''
    completeNoticeMessage.value = ''
    completeResultData.value = {}
    completeFinalFailed.value = false
    formData.completeApplicationCompleted = false

    const commonPayload = getCommonAdditionPayload()
    const selectedTypes = getSelectedServiceTypes()
    const addServices = getUniqueServices(
      (formData.additionList || []).filter((svc) => (svc.action || 'ADD') === 'ADD'),
    )
    const cancelServices = getUniqueServices(formData.additionCancelList || [])
    const wirelessAddServices = getUniqueServices(getWirelessBlockAddServices())
    const wirelessCancelServices = getUniqueServices(getWirelessBlockCancelServices())
    const finalAddServices = getUniqueServices([...addServices, ...wirelessAddServices])
    const finalCancelServices = getUniqueServices([...cancelServices, ...wirelessCancelServices])

    console.log('[변경][작성완료] 부가서비스 처리 시작', {
      ...commonPayload,
      selectedTypes,
      addCount: finalAddServices.length,
      cancelCount: finalCancelServices.length,
      addServices: finalAddServices.map((svc) => ({
        soc: getServiceCode(svc),
        name: getServiceName(svc),
        selfCareUnavailable: svc.selfCareUnavailable === true,
      })),
      cancelServices: finalCancelServices.map((svc) => ({
        soc: getServiceCode(svc),
        name: getServiceName(svc),
        prodHstSeq: getProductSeqNo(svc),
        selfCareUnavailable: svc.selfCareUnavailable === true,
      })),
    })

    if (!isReadyToComplete()) {
      completeErrorMessage.value = '작성이 완료되지 않았습니다. 확인 상태를 다시 확인해 주세요.'
      console.warn('[변경][작성완료] 부가서비스 처리 중단', {
        reason: 'confirm incomplete',
        additionConfirmCompleted: formData.additionConfirmCompleted,
        wirelessBlockConfirmCompleted: formData.wirelessBlockConfirmCompleted,
        selectedTypes,
      })
      return false
    }

    if (!commonPayload.ncn || !commonPayload.ctn) {
      completeErrorMessage.value =
        '서비스계약번호 또는 휴대폰번호가 없어 처리를 진행할 수 없습니다.'
      console.warn('[변경][작성완료] 부가서비스 처리 중단', {
        reason: 'missing common payload',
        contractNum: formData.contractNum,
        ...commonPayload,
      })
      return false
    }

    try {
      const payload = {
        ...commonPayload,
        parentScanId: formData.parentScanId || '',
        requestKey: requestKey.value ? Number(requestKey.value) : null,
        documentId: Array.isArray(documentId.value) ? documentId.value : [],
        signTgtSbst: formData.signTgtSbst || '',
        reportFiles: [], // 이미징 업로드는 작성완료와 분리된 API에서 처리
        requiredDocFiles: [],
        serviceSelect: selectedTypes,
        cstmrTypeCd: formData.cstmrTypeCd || 'NA',
        additionConfirmCompleted: formData.additionConfirmCompleted === true,
        cstmrNm: formData.cstmrNm || '',
        userBirthDate: formData.userBirthDate || '',
        userGender: formData.userGender || '',
        cstmrPrivateBizNo: ['FN', 'FM'].includes(formData.cstmrTypeCd)
          ? `${formData.cstmrForeignerRrn1 || ''}${formData.cstmrForeignerRrn2 || ''}`
          : `${formData.cstmrJuridicalRrn1 || ''}${formData.cstmrJuridicalRrn2 || ''}` ||
            `${formData.cstmrJuridicalBizNo1 || ''}${formData.cstmrJuridicalBizNo2 || ''}${formData.cstmrJuridicalBizNo3 || ''}`,
        cstmrForeignerRrn: `${formData.cstmrForeignerRrn1 || ''}${formData.cstmrForeignerRrn2 || ''}`,
        cstmrJuridicalRrn1: formData.cstmrJuridicalRrn1 || '',
        cstmrJuridicalRrn2: formData.cstmrJuridicalRrn2 || '',
        cstmrJuridicalBizNo1: formData.cstmrJuridicalBizNo1 || '',
        cstmrJuridicalBizNo2: formData.cstmrJuridicalBizNo2 || '',
        cstmrJuridicalBizNo3: formData.cstmrJuridicalBizNo3 || '',
        cstmrPrivateBizNoIssuDate: formData.cstmrPrivateBizNoIssuDate || '',
        cstmrJuridicalRepNm: formData.cstmrJuridicalRepNm || '',
        cstmrVisitTypeCd: formData.cstmrVisitTypeCd || '',
        telNo1: formData.telNo1 || '',
        telNo2: formData.telNo2 || '',
        telNo3: formData.telNo3 || '',
        mobileNo1: formData.mobileNo1 || '',
        mobileNo2: formData.mobileNo2 || '',
        mobileNo3: formData.mobileNo3 || '',
        emailAddr1: formData.emailAddr1 || '',
        emailAddr2: formData.emailAddr2 || '',
        zipNo: formData.zipNo || '',
        address: formData.address || '',
        detailAddress: formData.detailAddress || '',
        repName: formData.repName || '',
        repBirthDate: formData.repBirthDate || '',
        repGender: formData.repGender || '',
        repRegistrationNo1: formData.repRegistrationNo1 || '',
        repRegistrationNo2: formData.repRegistrationNo2 || '',
        repForeignerNo1: formData.repForeignerNo1 || '',
        repForeignerNo2: formData.repForeignerNo2 || '',
        repAgree: formData.repAgree === true,
        minorAgentNm: formData.minorAgentNm || '',
        agentBirthDate: formData.agentBirthDate || '',
        agentGender: formData.agentGender || '',
        minorAgentRelTypeCd: formData.minorAgentRelTypeCd || '',
        minorAgentTelFnNo: formData.minorAgentTelFnNo || '',
        minorAgentTelMnNo: formData.minorAgentTelMnNo || '',
        minorAgentTelRnNo: formData.minorAgentTelRnNo || '',
        clauses: Array.isArray(formData.clauses) ? formData.clauses : [],
        managerCd: formData.managerCd || '',
        managerNm: formData.managerNm || '',
        agentCd: formData.agentCd || '',
        ktOrgId: formData.ktOrgId || '',
        agentNm: formData.agentNm || '',
        shopCd: formData.shopCd || '',
        shopNm: formData.shopNm || '',
        realShopNm: formData.realShopNm || '',
        cpntId: formData.cpntId || '',
        cpntNm: formData.cpntNm || '',
        cntpntShopCd: formData.cntpntShopCd || '',
        cntpntShopNm: formData.cntpntShopNm || '',
        memo: formData.memo || '',
        additionCancelList: finalCancelServices.map((svc) => ({
          soc: getServiceCode(svc),
          serviceName: getServiceName(svc),
          prodHstSeq: getProductSeqNo(svc),
          selfCareUnavailable: svc.selfCareUnavailable === true,
          svcTgtCd: svc.svcTgtCd || getServiceTargetCd('R11'),
        })),
        additionList: finalAddServices.map((svc) => ({
          soc: getServiceCode(svc),
          serviceName: getServiceName(svc),
          ftrNewParam: getFtrNewParam(svc),
          flag: svc.flag || '',
          selfCareUnavailable: svc.selfCareUnavailable === true,
          svcTgtCd: svc.svcTgtCd || getServiceTargetCd('R11'),
        })),
        // P11: 요금제변경
        ...(selectedTypes.includes('P11')
          ? {
              planChange: {
                svcTgtCd: getServiceTargetCd('P11'),
                planCategoryCd: formData.planName1 || '',
                planCd: formData.planName2 || '',
                changeTypeCd: formData.changeDate || '',
                actCode: formData.actCode,
                planSoc: formData.planName2,
                beforePlanSoc: formData.currentProdId,
                beforePlanAmt: formData.currentProdAmt,
                planFtrNewParam: formData.planFtrNewParam,
                openingDate: formData.lstComActvDate,
              },
            }
          : {}),
        // O11: 번호변경
        ...(selectedTypes.includes('O11')
          ? {
              numberChange: {
                svcTgtCd: getServiceTargetCd('O11'),
                reqWantFnNo: formData.reqWantFnNo || '',
                reqWantMnNo: formData.reqWantMnNo || '',
                reqWantRnNo: formData.reqWantRnNo || '',
                wishNo: formData.wishNo || '',
                wishNoc: formData.wishNoc || '',
                wishMarket: formData.wishMarket || '',
                numberChgConfirmCompleted: formData.numberChgConfirmCompleted === true,
              },
            }
          : {}),
        // O12: 분실복구/일시정지해제
        ...(selectedTypes.includes('O12')
          ? {
              unpause: {
                svcTgtCd: getServiceTargetCd('O12'),
                unLockPw: formData.unLockPw || '',
                unpauseConfirmCompleted: formData.unpauseConfirmCompleted === true,
              },
            }
          : {}),
        // R14: 단말보험
        ...(selectedTypes.includes('R14')
          ? {
              insurance: {
                svcTgtCd: getServiceTargetCd('R14'),
                clauseInsuranceYn: formData.clauseInsuranceYn || '',
                catCd: formData.recCat1 || '',
                insrProdCd: formData.recCat2 || '',
                deviceOs: formData.insuranceDeviceOs || '',
              },
            }
          : {}),
        // O13: SIM정보
        ...(selectedTypes.includes('O13')
          ? {
              simInfo: {
                svcTgtCd: getServiceTargetCd('O13'),
                hasSim: formData.hasSim === false ? false : formData.hasSim || '',
                usimKindsCd: formData.usimKindsCd || '',
                reqUsimNm: getReqUsimNm(),
                reqUsimSn: formData.reqUsimSn || '',
                usimNm: getReqUsimNm(),
                eid: formData.eid || '',
                imei1: formData.imei1 || '',
                imei2: formData.imei2 || '',
                simPurchaseMethod: formData.simPurchaseMethod || '',
                simTypeCd: formData.simTypeCd,
              },
            }
          : {}),
        // R15: 데이터쉐어링
        ...(selectedTypes.includes('R15')
          ? {
              dataSharing: {
                svcTgtCd: getServiceTargetCd('R15'),
                shareUseState: formData.shareUseState || '',
                sharePhoneNum: formData.sharePhoneNum || '',
                shareUsimNum: formData.shareUsimNum || '',
                dataSharingTargetNo: formData.dataSharingTargetNo || '',
                dataSharingAuthCompleted: formData.dataSharingAuthCompleted === true,
                dataSharingUsimCheckCompleted: formData.dataSharingUsimCheckCompleted === true,
                dataSharingAvailableChecked: formData.dataSharingAvailableChecked === true,
                dataSharingAgreementCompleted: formData.dataSharingAgreementCompleted === true,
                dataSharingConfirmCompleted: formData.dataSharingConfirmCompleted === true,
              },
            }
          : {}),
        // R16: 결합Solo
        ...(selectedTypes.includes('R16')
          ? {
              combineSolo: {
                svcTgtCd: getServiceTargetCd('R16'),
                soloData: formData.soloData || '',
              },
            }
          : {}),
      }
      console.log('[변경][작성완료] complete 요청', {
        ...payload,
      })
      const response = await postRaw('/api/msf/formServiceChange/complete', payload, {
        timeout: selectedTypes.includes('R15') || selectedTypes.includes('R11') ? 180000 : 60000, //R15(쉐어링) R11(부가서비스) 3분
      })
      const res = response.data
      const formResponse = res?.data
      const completedRequestKey = String(formResponse?.resData?.requestKey || '')
      if (completedRequestKey) {
        requestKey.value = completedRequestKey
        formData.completeApplicationCompleted = true
      }

      const completeResult = getCompleteResult(res)
      completeResultData.value = completeResult || {}
      logCompleteResult(res, completeResultData.value)
      if (!isFormResponseSuccess(res)) {
        completeErrorMessage.value = formatCompleteErrorMessage(getResponseMessage(res))
        completeFinalFailed.value = isFinalCompleteFailure(completeResult)
        return false
      }

      // 해지와 동일하게 정상 접수번호를 저장해 공통 완료 화면으로 전달한다.
      completeErrorMessage.value = ''
      completeFinalFailed.value = false
      completeNoticeMessage.value = hasPartialSuccess(completeResult)
        ? formatPartialSuccessMessage(completeResult)
        : ''
      console.log('[변경][작성완료] complete 처리 완료')
      return true
    } catch (e) {
      completeErrorMessage.value = formatCompleteErrorMessage(
        e?.response?.data?.data?.resMessage || e?.message || DEFAULT_COMPLETE_ERROR_MESSAGE,
      )
      completeFinalFailed.value = false
      console.error('[변경][작성완료] 부가서비스 처리 예외', e)
      return false
    }
  }

  /** 이폼서버 업로드 완료 후 BE SCAN_ID 후처리 업데이트 (서비스변경 전용) */
  const apiUpdateScanId = async (options = {}) => {
    try {
      const selectedTypes = Array.isArray(options.serviceSelect)
        ? options.serviceSelect
        : getSelectedServiceTypes()
      const successServiceSelect = Array.isArray(options.successServiceSelect)
        ? options.successServiceSelect
        : getSuccessfulServiceSelect()
      const successProcessResults = Array.isArray(options.successProcessResults)
        ? options.successProcessResults
        : getSuccessfulProcessResults()
      const payload = {
        requestKey: requestKey.value ? Number(requestKey.value) : null,
        documentId: Array.isArray(documentId.value) ? documentId.value : [],
        signTgtSbst: options.signTgtSbst || formData.signTgtSbst || '',
        serviceSelect: selectedTypes,
        successServiceSelect,
        successProcessResults,
        dataSharing: selectedTypes.includes('R15') && formData.shareUseState === 'shareUseState1',
        // 대표 신청서 파일명 DB 반영용
        reportFiles: getReportFilesPayload(selectedTypes),
      }
      console.log('[변경][SCAN_ID업데이트] 요청', {
        requestKey: payload.requestKey,
        documentIdCount: payload.documentId.length,
        reportFileCount: payload.reportFiles.length,
      })
      const response = await postRaw('/api/msf/formServiceChange/scanId/modify', payload)
      const success = isWrappedSuccess(response)
      console.log('[변경][SCAN_ID업데이트] 결과', {
        success,
        code: response?.data?.code,
        resCode: getFormResponse(response)?.resCode,
      })
      return success
    } catch (e) {
      console.error('[변경][SCAN_ID업데이트] 예외', e)
      return false
    }
  }

  /** 서비스변경 신청서 및 구비서류 이미징 시스템 업로드 */
  const apiUploadImageSystem = async (options = {}) => {
    try {
      const selectedTypes = Array.isArray(options.serviceSelect)
        ? options.serviceSelect
        : getSelectedServiceTypes()
      const payload = {
        requestKey: requestKey.value ? Number(requestKey.value) : null,
        reportFiles: getReportFilesPayload(selectedTypes),
        requiredDocFiles: getRequiredDocFilesPayload(),
        cstmrNm: formData.cstmrNm || '',
        memo: formData.memo || '',
        managerCd: formData.managerCd || '',
        shopCd: formData.shopCd || '',
        cpntId: formData.cpntId || '',
        agentCd: formData.agentCd || '',
        parentScanId: formData.parentScanId || '',
      }
      console.log('[변경][이미징업로드] 요청', {
        requestKey: payload.requestKey,
        reportFileCount: payload.reportFiles.length,
        requiredDocFileCount: payload.requiredDocFiles.length,
      })
      const response = await postRaw('/api/msf/formServiceChange/imageSystem/upload', payload)
      const success = isWrappedSuccess(response)
      console.log('[변경][이미징업로드] 결과', {
        success,
        code: response?.data?.code,
        resCode: getFormResponse(response)?.resCode,
      })
      return success
    } catch (e) {
      console.error('[변경][이미징업로드] 예외', e)
      return false
    }
  }

  // -- 분실신고/일시정지 해제신청
  const getUnpauseBasePayload = (override = {}) => ({
    custId: formData.custId || '',
    ncn: formData.ncn || '',
    ctn: `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`,
    ...override,
  })

  const apiUnpauseCheck = async (opmdSvcNo) => {
    const payload = getUnpauseBasePayload(opmdSvcNo)
    console.log('[unpauseCheck] unpauseCheckAjax req:', payload)
    const response = await postRaw('/api/msf/formServiceChange/unpause/check', payload)
    const result = {
      success: isWrappedSuccess(response),
      message: getWrappedMessage(response),
      data: getFormResponse(response)?.resData || {},
      response,
    }
    console.log('[unpauseCheck] unpauseCheckAjax res:', result)
    return result
  }

  const apiUnpauseActice = async (opmdSvcNo) => {
    const payload = getUnpauseBasePayload(opmdSvcNo)
    console.log('[unpauseCheck] apiUnpauseActiceAjax req:', payload)
    const response = await postRaw('/api/msf/formServiceChange/test/pause/process', payload)
    const result = {
      success: isWrappedSuccess(response),
      message: getWrappedMessage(response),
      data: getFormResponse(response)?.resData || {},
      response,
    }
    console.log('[unpauseCheck] unpauseCheckAjax res:', result)
    return result
  }

  // 서비스해지 잔여요금 조회 시 requestKey를 발급하는 패턴과 동일
  // 고객 정보 입력 완료(다음 버튼) 시점에 eform에 전달할 requestKey를 사전 채번한다.
  const apiGetRequestKey = async () => {
    try {
      const data = await post('/api/msf/formServiceChange/requestKey/get', {})
      console.log('[변경][신청서키] 채번 응답', data)
      const resData = data?.data?.resData || data?.resData || {}
      const key = resData?.requestKey || ''
      if (key) {
        requestKey.value = String(key)
        formData.completeApplicationCompleted = false
        console.log('[변경][신청서키] requestKey 설정됨:', requestKey.value)
      } else {
        console.warn('[변경][신청서키] requestKey 없음 — 응답 구조 확인 필요', data)
      }
      return key
    } catch (e) {
      console.error('[변경][신청서키] 채번 실패 (무시하고 진행)', e?.message)
      return ''
    }
  }

  const getCompleteErrorMessage = () => completeErrorMessage.value || DEFAULT_COMPLETE_ERROR_MESSAGE
  const getCompleteNoticeMessage = () => completeNoticeMessage.value

  const fieldRules = computed(() => {
    const serviceRules = [
      {
        serviceName: 'R14', // 단말보험
        rule: {
          // clauseInsuranceYn: { is: true, msg: '약관에 동의해주세요.', match: 'Y' },
          insuranceAgree: { is: true, msg: '약관에 동의해주세요.', match: true },
          recCat2: { is: true, msg: '단말 보험을 선택해주세요.' },
          reqBuyType: { is: false, msg: '휴대폰 인증을 해주세요.' },
        },
      },
      {
        serviceName: 'O13', // USIM변경
        rule: {
          hasSim: { is: false, msg: '유심 보유 여부' },
          simTypeCd: { is: false, msg: '유심 보유 여부' },
          usimKindsCd: { is: false, msg: '유심 보유 여부' },
          reqUsimSn: { is: false, msg: '유심 보유 여부' },
          simPurchaseMethod: { is: false, msg: '유심 보유 여부' },
        },
      },
      {
        serviceName: 'R16', // 아무나SOLO
        rule: {
          soloData: { is: false, msg: '유심 보유 여부' },
          termsAgreed: { is: false, msg: '유심 보유 여부' },
        },
      },
    ]

    const selectRules = serviceRules.filter((obj) =>
      formData.serviceSelect.includes(obj?.serviceName),
    )

    const checkrules = selectRules.reduce((acc, cur) => Object.assign(acc, cur.rule), {})

    return checkrules
  })

  const validateSubFields = (data, rules) => {
    for (const key in rules) {
      const rule = rules[key]
      if (rule?.is) {
        const value = data[key]

        if ('match' in rule) {
          if (value !== rule.match) {
            showAlert(rule.msg)
            return false
          }
        } else if (value == null || value === '') {
          showAlert(rule.msg)
          return false
        }
      }
    }
    return true
  }

  const validateFormFields = () => {
    return validateSubFields(formData, fieldRules.value)
  }

  return {
    requestKey,
    documentId,
    eformsignFileData,
    formData,
    authFlags,
    cancelAuthResetKey,
    completeErrorMessage,
    completeNoticeMessage,
    completeFinalFailed,
    incrementWishNoSearchCount,
    apiGetMyinfoView,
    apiGetRequestKey,
    apiDataSharingList,
    apiDataSharingCheck,
    apiDataSharingUsimCheck,
    apiUnpauseCheck,
    apiUnpauseActice,
    apiCompleteAdditionApplication,
    apiUpdateScanId,
    apiUploadImageSystem,
    isReadyToComplete,
    getSuccessfulServiceSelect,
    getSuccessfulProcessResults,
    getCompleteErrorMessage,
    getCompleteNoticeMessage,
    wishNoSearchCount,
    resetAll,
    resetAuthForEdit,
    validateFormFields,
    validateCustomerWithAlert,
    validateCustomerAgeWithAlert,
  }
})
