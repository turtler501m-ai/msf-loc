import { defineStore } from "pinia";
import { reactive, ref } from "vue";
import { post } from "@/libs/api/msf.api";
import { isLocal } from "@/libs/utils/env.utils";
import { useMsfStepStore } from "./msf_step";

const cloneDeep = (obj) => JSON.parse(JSON.stringify(obj))

const DEFAULT_ERROR_MESSAGE = '신청서 등록이 실패하였습니다. 다시 시도해 주세요.'
export const useMsfFormTerminationStore = defineStore('msf_form_termination', () => {
  const stepStore = useMsfStepStore()
  const requestKey = ref('')
  const documentId = ref('')
  const fileNm = ref('')
  const fileMaskNm = ref('')
  const completeErrorMessage = ref('')
  const applicationConfirmed = ref(false)
  const validateCustomerWithAlert = ref(() => true)
  const validateCustomerAgeWithAlert = ref(() => true)
  const validateProductWithAlert = ref(() => true)
  const customerAgreementResetKey = ref(0)
  const cancelAuthResetKey = ref(0)

  // formData 단일 소스
  // - 화면 공통 컴포넌트 바인딩
  // - API 전송 전 customer/product/agreement 구조로 매핑
  const formData = reactive({
    /* 고객 유형 */
    cstmrTypeCd: 'NA', // 고객유형
    visitCustomer: '',
    cstmrVisitTypeCd: '', // 방문고객 유형

    /* 고객 유형 잠금 (신청서 확인 후 수정 모드에서만 true — 고객 유형 재선택 방지) */
    customerTypeLocked: false,

    /* 신분증 확인 */
    identityCertTypeCd: 'S', // 신분증 인증유형(인증예외)
    identityIssuDate: '', // 발급일자
    driveLicnsNo: '', // 운전면허번호
    selfIssuNo: '', // 자필서명/자체발급번호
    identityTypeCd: '01', // 신분증 타입
    identityTypeNm: '', // 스캔된 신분증 명칭
    identityIssuRegion: '', // 발급지역
    isVerified: false, // 본인확인 완료 여부
    isScanVerified: false, // 신분증 스캔 완료 여부
    isSaved: false, // 임시저장 여부
    knoteIdentityScanCstmrNm: '', // K-NOTE 신분증 고객명
    knoteIdentityEssNo: '', // K-NOTE 신분증 식별번호
    knoteIdentityTypeCd: '', // K-NOTE 신분증 유형코드
    knoteIdentityScanDt: '', // K-NOTE 신분증 스캔일시
    knoteScanId: '', // K-NOTE 신분증 스캔번호

    /* 가입자 정보 */
    /* TODO_삭제  법인 (주)케이티엠모바일 19110111 010-6768-4455  133814-3410 */
    /* 스마트서식지 테스트 데이타
김성인 19810217 010-2594-7538
김아람 19901122 010-4883-3628
곽영환 19831202 010-2714-8794 */

    cstmrNm: isLocal() ? '김성인' : '', // 이름
    userBirthDate: isLocal() ? '19810217' : '', // 생년월일(YYYYMMDD)
    userGender: 'M', // 성별
    cstmrNativeGenderCd: '', // 내국인 성별코드
    cstmrForeignerGenderCd: '', // 외국인 성별코드
    cstmrNativeRrn1: '', // 내국인 주민번호 앞
    cstmrNativeRrn2: '', // 내국인 주민번호 뒤
    cstmrForeignerRrn1: '', // 외국인등록번호 앞
    cstmrForeignerRrn2: '', // 외국인등록번호 뒤
    cstmrJuridicalRrn1: '', // 법인등록번호 앞
    cstmrJuridicalRrn2: '', // 법인등록번호 뒤
    cstmrJuridicalBizNo1: '', // 사업자번호 1
    cstmrJuridicalBizNo2: '', // 사업자번호 2
    cstmrJuridicalBizNo3: '', // 사업자번호 3
    cstmrJuridicalBizNoIssuDate: '', // 사업자 교부일자
    cstmrJuridicalRepNm: '', // 대표자명
    upjnCd: '', // 업종코드
    bcuSbst: '', // 업태

    /* 해지 휴대폰 인증 */
    deviceChgTel1: isLocal() ? '010' : '',
    deviceChgTel2: isLocal() ? '2594' : '',
    deviceChgTel3: isLocal() ? '7538' : '',
    cancelPhoneAuth: '', // 인증번호
    contractNum: '', // 인증 응답 계약번호
    lstComActvDate: '', // 개통일자
    formType: 'TERMINATION',

    /* 법정대리인 정보 */
    repName: '',
    repBirthDate: '',
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

    /* 대리인 위임정보 */
    minorAgentNm: '',
    agentBirthDate: '',
    agentGender: '',
    minorAgentRelTypeCd: '',
    minorAgentRelTypeNm: '',
    minorAgentTelFnNo: '',
    minorAgentTelMnNo: '',
    minorAgentTelRnNo: '',

    /* 해지 후 연락처 */
    afterTel1: '',
    afterTel2: '',
    afterTel3: '',
    postMethod: 'E', // 해지 후 연락 수단

    /* 가입유형/매장 정보 */
    managerNm: '',
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

    /* 해지 정산 */
    cancelUseCompanyCd: '', // 해지 후 사용 통신사 코드
    usageFee: '', // 사용요금
    penaltyFee: '', // 위약금
    finalAmount: '', // 최종 정산요금
    remainPeriod: '', // 잔여분할상환 기간
    remainAmount: '', // 잔여분할상환 금액
    memo: '',
    uploadedDocs: [],
    msfRequestDocList: [],

    /* 연동키/기본코드 */
    agentCd: '',
    ktOrgId: '',
    managerCd: '',
    ncn: '',
    custId: '',

    /* X18 조회 원본 */
    remainChargeLoaded: false,
    remainChargeItems: [],

    /* 가입정보 조회(getMyinfoView) 결과 */
    prvRateGrpNm: '', // 현재 요금제명
    initActivationDate: '', // 개통일자
    addr: '', // 주소
    remindBlckYn: '', // 해지 제한 여부
    payData: null, // 납부방법
    billData: null, // 명세서

    /* 동의 */
    agreeCheck1: false,
    agreeCheck2: false,
    agreeCheck3: false,
    clauseAgreements: [],
  })

  // 인증 버튼 완료 여부 플래그
  const authFlags = ref({
    cancelPhone: false, // 해지 휴대폰 인증
    repPhone: false, // 법정대리인 휴대폰 인증
    requiredDocs: false, // 구비서류 등록
  })

  // 해지 업무는 후속 조회에 NCN(SVC_CNTR_NO)을 사용한다.
  // 기존 호출부 호환을 위해 두 번째 인자가 source 문자열이면 contractNum을 ncn으로도 저장한다.
  const setTerminationContract = (contractNum, svcCntrNo = '', source = 'unknown') => {
    if (source === 'unknown' && svcCntrNo && !/^\d+$/.test(String(svcCntrNo))) {
      source = svcCntrNo
      svcCntrNo = contractNum
    }
    if (!contractNum && !svcCntrNo) return false
    formData.contractNum = contractNum
    formData.ncn = svcCntrNo || contractNum
    sessionStorage.setItem('terminationContractNum', contractNum || '')
    sessionStorage.setItem('terminationNcn', formData.ncn || '')
    console.log('[TerminationStore] contract mapped', { source, contractNum, svcCntrNo: formData.ncn })
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
    const cachedNcn = sessionStorage.getItem('terminationNcn')
    if (cachedContractNum || cachedNcn) {
      setTerminationContract(cachedContractNum, cachedNcn, 'sessionStorage')
      return formData.ncn
    }

    const subscriberNo = `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`
    const customerLinkName = (formData.cstmrNm || '').trim()
    if (!subscriberNo || !customerLinkName) return ''

    try {
      const authResult = await post('/api/form/ktmmember/servicechange-auth', {
        subscriberNo,
        customerLinkName,
        customerSsn: formData.userBirthDate || '',
      })
      const authData = authResult?.data?.resData || authResult?.data || {}
      const contractNum = authData.contractNum || authData.contract_num || ''
      const svcCntrNo = authData.svcCntrNo || authData.svc_cntr_no || authData.ncn || ''
      const lstComActvDate =
        authData.lstComActvDate ||
        authData.lst_com_actv_date ||
        authData.initActivationDate ||
        ''
      if (authResult?.code === '0000' && svcCntrNo) {
        setTerminationContract(contractNum, svcCntrNo, 'servicechange-auth-api')
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
        roadAddrChk: false,
        skipPerMyktfInfo: true,
      }, { silent: true })
      console.log('[MyinfoView] 가입정보 조회 응답', data)
      const formResponse = data?.data
      const changInfo = formResponse?.resData
      if (formResponse?.resCode === '0000' && changInfo) {
        if (changInfo.custId !== undefined)
          formData.custId = changInfo.custId || formData.custId || ''
        if (changInfo.ctn !== undefined) {
          const rawCtn = (changInfo.ctn || '').replace(/\D/g, '')
          if (rawCtn.length >= 10) {
            formData.deviceChgTel1 = rawCtn.substring(0, 3)
            formData.deviceChgTel2 = rawCtn.substring(3, rawCtn.length - 4)
            formData.deviceChgTel3 = rawCtn.substring(rawCtn.length - 4)
          }
        }
        if (changInfo.ncn !== undefined) formData.ncn = changInfo.ncn || formData.ncn
        if (changInfo.contractNum !== undefined)
          formData.contractNum = changInfo.contractNum || formData.contractNum
        if (changInfo.cstmrNm !== undefined || changInfo.customerName !== undefined) {
          formData.cstmrNm = changInfo.cstmrNm || changInfo.customerName || formData.cstmrNm
        }
        const juridicalRrn =
          changInfo.cstmrJuridicalRrn ||
          changInfo.juridicalRrn ||
          changInfo.corpRegNo ||
          changInfo.corporateRegNo ||
          ''
        if (juridicalRrn) {
          const rawJuridicalRrn = String(juridicalRrn).replace(/\D/g, '')
          formData.cstmrJuridicalRrn1 = rawJuridicalRrn.substring(0, 6)
          formData.cstmrJuridicalRrn2 = rawJuridicalRrn.substring(6, 13)
        }
        if (changInfo.cstmrJuridicalRrn1 !== undefined) {
          formData.cstmrJuridicalRrn1 = changInfo.cstmrJuridicalRrn1 || formData.cstmrJuridicalRrn1
        }
        if (changInfo.cstmrJuridicalRrn2 !== undefined) {
          formData.cstmrJuridicalRrn2 = changInfo.cstmrJuridicalRrn2 || formData.cstmrJuridicalRrn2
        }
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
        if (
          !formData.cstmrJuridicalRepNm &&
          (changInfo.cstmrJuridicalRepNm !== undefined ||
            changInfo.representativeName !== undefined ||
            changInfo.repName !== undefined)
        ) {
          formData.cstmrJuridicalRepNm =
            changInfo.cstmrJuridicalRepNm ||
            changInfo.representativeName ||
            changInfo.repName ||
            formData.cstmrJuridicalRepNm
        }
        if (changInfo.prvRateGrpNm !== undefined)
          formData.prvRateGrpNm = changInfo.prvRateGrpNm || ''
        if (changInfo.initActivationDate && changInfo.initActivationDate !== '-') {
          formData.initActivationDate = changInfo.initActivationDate
          formData.lstComActvDate = changInfo.initActivationDate
        }
        if (changInfo.addr && changInfo.addr !== '-') formData.addr = changInfo.addr
        // homeTel 있으면 해지 후 연락처, 없으면 해지 휴대폰번호를 해지 후 연락처로 셋팅
        //20260528 해지후 연락처 셋팅 삭제 (해지번호와 같으면 오류발생)
        //const rawTel = (changInfo.homeTel || '').replace(/\D/g, '')
        //if (rawTel) {
        //  formData.afterTel1 = rawTel.substring(0, 3)
        //  formData.afterTel2 = rawTel.substring(3, rawTel.length - 4)
        //  formData.afterTel3 = rawTel.substring(rawTel.length - 4)
        //} else {
          //20260528 해지후 연락처 셋팅 삭제 (해지번호와 같으면 오류발생)
          //formData.afterTel1 = formData.deviceChgTel1 || ''
          //formData.afterTel2 = formData.deviceChgTel2 || ''
          //formData.afterTel3 = formData.deviceChgTel3 || ''
        //}
        if (changInfo.remindBlckYn !== undefined)
          formData.remindBlckYn = changInfo.remindBlckYn || ''
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

  // Pre-issue the requestKey before eform opens.
  const apiGetRequestKey = async () => {
    try {
      const data = await post('/api/msf/formTermination/requestKey/get', {})
      console.log('[termination][requestKey] response', data)
      const resData = data?.data?.resData || data?.resData || {}
      const key = resData?.requestKey || ''
      if (key) {
        requestKey.value = String(key)
        console.log('[termination][requestKey] set:', requestKey.value)
      } else {
        console.warn('[termination][requestKey] missing requestKey', data)
      }
      return key
    } catch (e) {
      console.error('[termination][requestKey] failed', e?.message)
      return ''
    }
  }

  // X18 잔여요금·위약금 실시간 조회
  const apiGetRemainCharge = async () => {
    const ncn = formData.ncn || formData.contractNum
    console.log('[X18] 잔여요금 조회 요청', { ncn, requestKey: requestKey.value || '' })
    try {
      // ctn·custId는 백엔드에서 세션 계약 목록으로 조회, ncn만 전송
      const payload = { ncn }
      if (requestKey.value) {
        payload.requestKey = requestKey.value
      }
      const data = await post('/api/msf/formTermination/remainCharge/list', payload, {
        silent: true,
        timeout: 30000,
      })
      console.log('[X18] 잔여요금 조회 응답. 현재 requestKey:', requestKey.value, '응답 데이터:', data)
      const responseBody = data?.data || {}
      const formResponse = responseBody?.data?.resCode ? responseBody.data : responseBody
      const resCode = formResponse?.resCode || responseBody?.resCode || responseBody?.code || ''
      const resMessage = formResponse?.resMessage || responseBody?.resMessage || responseBody?.message || ''
      const remainCharge = formResponse?.resData || {}
      console.log('[X18] 파싱된 remainCharge 데이터:', remainCharge)
      if (resCode === '0000') {
        // Termination e-form uses the request key issued by remain-charge lookup.
        if (!requestKey.value && remainCharge.requestKey) {
          requestKey.value = String(remainCharge.requestKey)
          console.log('[X18] 잔여요금 조회 성공 - 신규 requestKey 설정됨:', requestKey.value)
        } else {
          console.log('[X18] 잔여요금 조회 성공 - 현재 requestKey:', requestKey.value)
        }
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
          resCode,
          resMessage,
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
  const STEP_RESET_FIELDS = {
    0: [
      'cstmrTypeCd', 'customerTypeLocked', 'visitCustomer', 'cstmrVisitTypeCd', 'identityCertTypeCd', 'identityIssuDate',
      'driveLicnsNo', 'selfIssuNo', 'identityTypeCd', 'identityTypeNm', 'identityIssuRegion',
      'isVerified', 'isScanVerified', 'isSaved', 'knoteIdentityScanCstmrNm', 'knoteIdentityEssNo',
      'knoteIdentityTypeCd', 'knoteIdentityScanDt', 'knoteScanId',
      'cstmrNm', 'userBirthDate', 'userGender', 'cstmrNativeGenderCd', 'cstmrForeignerGenderCd',
      'cstmrNativeRrn1', 'cstmrNativeRrn2',
      'cstmrForeignerRrn1', 'cstmrForeignerRrn2', 'cstmrJuridicalRrn1', 'cstmrJuridicalRrn2',
      'cstmrJuridicalBizNo1', 'cstmrJuridicalBizNo2', 'cstmrJuridicalBizNo3',
      'cstmrJuridicalBizNoIssuDate', 'cstmrJuridicalRepNm',
      'upjnCd', 'bcuSbst', 'deviceChgTel1', 'deviceChgTel2', 'deviceChgTel3', 'cancelPhoneAuth',
      'contractNum', 'lstComActvDate', 'repName', 'repBirthDate', 'repRegistrationNo1',
      'repRegistrationNo2', 'repForeignerNo1', 'repForeignerNo2',
      'repRelation', 'repPhone1', 'repPhone2', 'repPhone3', 'repPhoneAuth', 'repAgree',
      'minorAgentNm', 'agentBirthDate', 'agentGender', 'minorAgentRelTypeCd', 'minorAgentRelTypeNm', 'minorAgentTelFnNo',
      'minorAgentTelMnNo', 'minorAgentTelRnNo', 'afterTel1', 'afterTel2', 'afterTel3', 'postMethod',
      'managerNm', 'agentNm', 'telephone', 'representativeTelephone', 'cpntId', 'cpntNm', 'cntpntShopCd', 'cntpntShopNm',
      'agentCd', 'ktOrgId', 'managerCd', 'ncn', 'custId', 'prvRateGrpNm', 'initActivationDate', 'addr',
      'remindBlckYn', 'payData', 'billData', 'uploadedDocs', 'msfRequestDocList',
    ],
    1: [
      'cancelUseCompanyCd', 'usageFee', 'penaltyFee', 'finalAmount',
      'remainPeriod', 'remainAmount', 'remainChargeLoaded', 'remainChargeItems', 'memo',
    ],
    2: ['agreeCheck1', 'agreeCheck2', 'agreeCheck3', 'clauseAgreements'],
  }

  const EMPTY_BY_FIELD = {
    cstmrTypeCd: 'NA',
    userGender:"M",
    customerTypeLocked: false,
    identityCertTypeCd: 'S',
    identityTypeCd: '01',
    deviceChgTel1: '010',
    postMethod: 'E',
    remainChargeLoaded: false,
    remainChargeItems: [],
    uploadedDocs: [],
    msfRequestDocList: [],
    payData: null,
    billData: null,
    isVerified: false,
    isScanVerified: false,
    isSaved: false,
    repAgree: false,
    agreeCheck1: false,
    agreeCheck2: false,
    agreeCheck3: false,
    clauseAgreements: [],
  }

  const buildStepEmptyValues = (fields) =>
    fields.reduce((acc, field) => {
      if (Object.prototype.hasOwnProperty.call(EMPTY_BY_FIELD, field)) {
        acc[field] = cloneDeep(EMPTY_BY_FIELD[field])
      } else {
        acc[field] = ''
      }
      return acc
    }, {})

  const STEP_EMPTY_VALUES = {
    0: buildStepEmptyValues(STEP_RESET_FIELDS[0]),
    1: buildStepEmptyValues(STEP_RESET_FIELDS[1]),
    2: buildStepEmptyValues(STEP_RESET_FIELDS[2]),
  }

  const resetStep = (step) => {
    const emptyValues = STEP_EMPTY_VALUES[step]
    if (!emptyValues) return
    Object.assign(formData, cloneDeep(emptyValues))

    if (step === 0) {
      authFlags.value.cancelPhone = false
      authFlags.value.repPhone = false
      authFlags.value.requiredDocs = false
      completeErrorMessage.value = ''
      documentId.value = ''
      fileNm.value = ''
      fileMaskNm.value = ''
      applicationConfirmed.value = false
      sessionStorage.removeItem('terminationContractNum')
      sessionStorage.removeItem('terminationNcn')
    }
  }

  const resetAll = () => {
    resetStep(0)
    resetStep(1)
    resetStep(2)
    completeErrorMessage.value = ''
    requestKey.value = ''
    documentId.value = ''
    fileNm.value = ''
    fileMaskNm.value = ''
    applicationConfirmed.value = false
    sessionStorage.removeItem('terminationContractNum')
    sessionStorage.removeItem('terminationNcn')
  }

  const resetCustomerAgreement = () => {
    formData.clauseAgreements = []
    customerAgreementResetKey.value += 1
  }

  const setApplicationConfirmed = (value) => {
    applicationConfirmed.value = !!value
  }

  // 신청서 확인 팝업 수정 버튼 클릭 시 — 인증 상태 전체 초기화
  const resetAuthForEdit = () => {
    applicationConfirmed.value = false
    formData.customerTypeLocked = true  // 고객 유형 재선택 잠금
    formData.isVerified = false
    formData.isScanVerified = false
    formData.agreeCheck1 = false        // 동의스텝 고객 안내사항 동의 미체크
    formData.repAgree = false           // 법정대리인 동의 미체크
    if (authFlags.value) {
      authFlags.value.cancelPhone = false
      authFlags.value.repPhone = false
    }
    resetCustomerAgreement()
    cancelAuthResetKey.value++
  }

  // 백엔드 요청 스키마(customer/product/agreement) 매핑
  const buildCompletePayload = () => ({
    parentScanId: stepStore.parentScanId || '',
    requestKey: requestKey.value || null,
    documentId: documentId.value,
    fileNm: fileNm.value,
    fileMaskNm: fileMaskNm.value,
    cstmrTypeCd: formData.cstmrTypeCd,
    receiveWayCd: formData.postMethod,
    cancelMobileNo: `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`,
    receiveMobileNo: `${formData.afterTel1 || ''}${formData.afterTel2 || ''}${formData.afterTel3 || ''}`,
    msfRequestDocList: formData.msfRequestDocList || [],
    customer: {
      managerCd: formData.managerCd,
      managerNm: formData.managerNm,
      agentCd: formData.agentCd,
      ktOrgId: formData.ktOrgId,
      agentNm: formData.agentNm,
      shopCd: formData.shopCd,
      shopNm: formData.shopNm,
      realShopNm: formData.realShopNm,
      customerType: formData.cstmrTypeCd,
      identityCertTypeCd: 'S',
      identityTypeCd: formData.identityTypeCd,
      identityTypeNm: formData.identityTypeNm,
      identityIssuDate: formData.identityIssuDate,
      identityIssuRegion: formData.identityIssuRegion,
      driveLicnsNo: formData.driveLicnsNo,
      selfIssuNo: formData.selfIssuNo,
      knoteIdentityScanCstmrNm: formData.knoteIdentityScanCstmrNm,
      knoteIdentityEssNo: formData.knoteIdentityEssNo,
      knoteIdentityTypeCd: formData.knoteIdentityTypeCd,
      knoteIdentityScanDt: formData.knoteIdentityScanDt,
      knoteScanId: formData.knoteScanId,
      userName: formData.cstmrNm,
      userBirthDate: formData.userBirthDate,
      userGender: formData.userGender,
      cstmrNativeRrn1: formData.cstmrNativeRrn1,
      cstmrNativeRrn2: formData.cstmrNativeRrn2,
      cstmrForeignerRrn1: formData.cstmrForeignerRrn1,
      cstmrForeignerRrn2: formData.cstmrForeignerRrn2,
      cstmrJuridicalRrn1: formData.cstmrJuridicalRrn1,
      cstmrJuridicalRrn2: formData.cstmrJuridicalRrn2,
      cstmrJuridicalBizNo1: formData.cstmrJuridicalBizNo1,
      cstmrJuridicalBizNo2: formData.cstmrJuridicalBizNo2,
      cstmrJuridicalBizNo3: formData.cstmrJuridicalBizNo3,
      cstmrJuridicalBizNoIssuDate: formData.cstmrJuridicalBizNoIssuDate,
      cstmrJuridicalRepNm: formData.cstmrJuridicalRepNm,
      upjnCd: formData.upjnCd,
      bcuSbst: formData.bcuSbst,
      repName: formData.repName,
      repBirthDate: formData.repBirthDate,
      repRegistrationNo1: formData.repRegistrationNo1,
      repRegistrationNo2: formData.repRegistrationNo2,
      repForeignerNo1: formData.repForeignerNo1,
      repForeignerNo2: formData.repForeignerNo2,
      repAgree: !!formData.repAgree,
      minorAgentNm: formData.minorAgentNm,
      agentBirthDate: formData.agentBirthDate,
      agentGender: formData.agentGender,
      minorAgentRelTypeCd: formData.minorAgentRelTypeCd,
      minorAgentTelFnNo: formData.minorAgentTelFnNo,
      minorAgentTelMnNo: formData.minorAgentTelMnNo,
      minorAgentTelRnNo: formData.minorAgentTelRnNo,
      cstmrVisitTypeCd: formData.cstmrVisitTypeCd,
      cancelPhone1: formData.deviceChgTel1,
      cancelPhone2: formData.deviceChgTel2,
      cancelPhone3: formData.deviceChgTel3,
      afterTel1: formData.afterTel1,
      afterTel2: formData.afterTel2,
      afterTel3: formData.afterTel3,
      postMethod: formData.postMethod,
      agencyName: formData.agentNm,
      cpntId: formData.cpntId,
      cpntNm: formData.cpntNm,
      cntpntShopCd: formData.cntpntShopCd,
      cntpntShopNm: formData.cntpntShopNm,
      ncn: formData.ncn || formData.contractNum,
      custId: formData.custId,
    },
    product: {
      cancelUseCompanyCd: formData.cancelUseCompanyCd,
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
      clauses: Array.isArray(formData.clauseAgreements) ? formData.clauseAgreements : [],
    },
  })

  // 작성완료 API
  const apiCompleteApplication = async () => {
    try {
      const payload = buildCompletePayload()
      console.log('[해지][작성완료] 요청 상세', {
        ncn: payload?.customer?.ncn,
        requestKey: payload?.requestKey,
        documentId: payload?.documentId,
        customerType: payload?.customer?.customerType,
        cancelUseCompanyCd: payload?.product?.cancelUseCompanyCd,
      })
      const data = await post(
        '/api/msf/formTermination/complete',
        payload,
        { silentSuccess: true },
      )
      console.debug('[apiCompleteApplication] response', data)
      const formResponse = data?.data
      console.log('[해지][작성완료] 응답 상세', {
        code: data?.code,
        message: data?.message,
        resCode: formResponse?.resCode,
        resMessage: formResponse?.resMessage,
        resData: formResponse?.resData,
        rawData: data,
      })
      if (formResponse?.resCode === '0000') {
        completeErrorMessage.value = ''
        const completedRequestKey = formResponse?.resData?.requestKey
        if (completedRequestKey) {
          requestKey.value = String(completedRequestKey)
        }
        console.info('[apiCompleteApplication] success', { requestKey: requestKey.value })
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
    requestKey,
    documentId,
    fileNm,
    fileMaskNm,
    formData,
    authFlags,
    completeErrorMessage,
    applicationConfirmed,
    validateCustomerWithAlert,
    validateCustomerAgeWithAlert,
    validateProductWithAlert,
    customerAgreementResetKey,
    cancelAuthResetKey,
    getCompleteErrorMessage,
    setApplicationConfirmed,
    resetCustomerAgreement,
    resetAuthForEdit,
    setTerminationContract,
    ensureTerminationNcn,
    resetStep,
    resetAll,
    apiGetMyinfoView,
    apiGetRequestKey,
    apiGetRemainCharge,
    apiCompleteApplication,
  }
})
