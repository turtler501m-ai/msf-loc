import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'
import { post } from '@/libs/api/msf.api'

export const useMsfFormSvcChgStore = defineStore('msf_form_svc_chg', () => {
  const DEFAULT_COMPLETE_ERROR_MESSAGE = '서비스 처리 중 오류가 발생했습니다.'
  const applicationKey = ref('TEMP_' + Math.random().toString(36).substring(7))
  const completeErrorMessage = ref('')

  // Step 1: Customer Info
  const formData = reactive({
    /** 고객유형 */
    cstmrTypeCd: 'NA', // 고객유형
    /* 가입자 정보 */
    cstmrNm: '전용식', //이름
    userBirthDate: '19800101', //생년월일
    userGender: 'M', //성별
    cstmrJuridicalRrn1: '', //법인등록번호1
    cstmrJuridicalRrn2: '', //법인등록번호2
    cstmrJuridicalBizNo1: '', //사업자등록번호1
    cstmrJuridicalBizNo2: '', //사업자등록번호2
    cstmrJuridicalBizNo3: '', //사업자등록번호3
    cstmrJuridicalRepNm: '', //대표자명
    deviceChgTel1: '010', //변경휴대폰번호1
    deviceChgTel2: '5358', //변경휴대폰번호2
    deviceChgTel3: '6069', //변경휴대폰번호3
    contractNum: '', // 인증 응답 계약번호
    ncn: '',
    custId: '',
    cstmrVisitTypeCd: '',
    formType: 'SERVICECHANGE',

    /* 가입정보 조회(getMyinfoView) 결과 */
    prvRateGrpNm: '',       // 현재 요금제명
    initActivationDate: '', // 개통일자
    lstComActvDate: '',     // 개통일자(동기화용)
    addr: '',               // 주소
    remindBlckYn: '',       // 해지 제한 여부
    subStatus: '',          // 회선 상태(A: 사용중, S: 정지)
    payData: null,          // 납부방법
    billData: null,         // 명세서
    /* 법정대리인 정보 */
    repName: '', //이름
    repBirthDate: '', //생년월일
    repGender: '', //성별
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
    additionList: [], // 부가서비스 선택 목록
    additionCancelList: [], // 부가서비스 해지 목록
    additionConfirmCompleted: false, // 부가서비스 작성 완료 여부
    appConfirmCompleted: false, // 신청서 확인 완료 여부
    blockService: null, // 무선데이터차단 선택
    agency: '', //대리점
    managerCd: '',
    managerNm: '',
    agentCd: '',
    agentNm: '',
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
    planName1: '',
    planName2: '',
    changeDate: '',
    /* O11: 번호변경 */
    reqWantFnNo: '',
    reqWantMnNo: '',
    reqWantRnNo: '',
    wishNo: '',
    /* O12: 분실복구/일시정지해제 */
    unLockPw: '',
    /* R14: 단말보험 */
    clauseInsuranceYn: '',
    recCat1: '',
    recCat2: '',
    /* O13: SIM정보 */
    hasSim: '',
    usimKindsCd: '',
    reqUsimSn: '',
    eid: '',
    imei1: '',
    imei2: '',
    /* R15: 데이터쉐어링 */
    shareUseState: '',
    sharePhoneNum: '',
    shareUsimNum: '',
    /* R16: 결합Solo */
    soloData: '',
  })

  const authFlags = ref({
    deviceChgTel: false,
  })

  const apiGetMyinfoView = async () => {
    const ncn = formData.ncn || formData.contractNum
    const ctn = `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`
    console.log('[변경][MyinfoView] 가입정보 조회 요청', { ncn, ctn })
    try {
      const data = await post('/api/msf/formServiceChange/changinfo/view', {
        ncn,
        ctn,
        contractNum: ncn,
        custId: formData.custId || '',
      }, { silent: true })
      console.log('[변경][MyinfoView] 가입정보 조회 응답', data)
      const formResponse = data?.data
      const changInfo = formResponse?.resData
      if (formResponse?.resCode === '0000' && changInfo) {
        if (changInfo.prvRateGrpNm !== undefined) formData.prvRateGrpNm = changInfo.prvRateGrpNm || ''
        if (changInfo.initActivationDate && changInfo.initActivationDate !== '-') {
          formData.initActivationDate = changInfo.initActivationDate
          formData.lstComActvDate = changInfo.initActivationDate
        }
        if (changInfo.addr && changInfo.addr !== '-') formData.addr = changInfo.addr
        if (changInfo.remindBlckYn !== undefined) formData.remindBlckYn = changInfo.remindBlckYn || ''
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

        // 가입자 연락처 자동 셋팅
        // 휴대폰번호 ← 인증된 변경 휴대폰번호
        formData.mobileNo1 = formData.deviceChgTel1 || ''
        formData.mobileNo2 = formData.deviceChgTel2 || ''
        formData.mobileNo3 = formData.deviceChgTel3 || ''

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
      } else {
        console.warn('[변경][MyinfoView] 조회 실패', {
          resCode: formResponse?.resCode,
          resMessage: formResponse?.resMessage,
        })
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
    ncn: formData.ncn || formData.contractNum || '',
    ctn: getPhoneNo(),
    custId: formData.custId || '',
  })

  const getServiceCode = (svc = {}) =>
    String(svc.rateCd || svc.soc || svc.prodId || svc.addSvcCd || '')

  const getServiceName = (svc = {}) =>
    svc.rateNm || svc.socDescription || svc.prodNm || svc.addSvcNm || svc.serviceName || getServiceCode(svc)

  const getProductSeqNo = (svc = {}) =>
    String(svc.prodHstSeq || svc.prdcSeqNo || svc.prodSeqNo || svc.productSeqNo || svc.svcSeqNo || '')

  const getFtrNewParam = (svc = {}) => {
    if (svc.ftrNewParam) return String(svc.ftrNewParam)

    const settingData = svc.addSvcSettingData || {}
    const entries = Object.entries(settingData).filter(([, value]) => value !== undefined && value !== null)
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

  const getSelectedServiceTypes = () => (Array.isArray(formData.serviceSelect) ? formData.serviceSelect : [])

  const getServiceTargetCd = (svcTypeCd) => {
    const target = (formData.serviceList || []).find((item) => item.value === svcTypeCd)
    return target?.svcTgtCd || svcTypeCd
  }

  const getWirelessBlockAddServices = () => {
    if (formData.blockService !== 'blockService2') return []
    return [{ soc: 'WIRELESSC', ftrNewParam: '', flag: '', svcTgtCd: getServiceTargetCd('R12') }]
  }

  const getWirelessBlockCancelServices = () => {
    if (formData.blockService !== 'blockService1') return []
    return [{ soc: 'WIRELESSC', prodHstSeq: '', svcTgtCd: getServiceTargetCd('R12') }]
  }

  // ServiceChangeProduct.vue의 CONFIRM_REQUIRED_MAP과 동일하게 유지
  const CONFIRM_REQUIRED_MAP = {
    R11: 'additionConfirmCompleted',
    R12: 'wirelessBlockConfirmCompleted',
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
    const formResponse = res?.data
    return res?.code === '0000' && (!formResponse?.resCode || formResponse.resCode === '0000')
  }

  const getResponseMessage = (res) =>
    res?.data?.resMessage || res?.message || DEFAULT_COMPLETE_ERROR_MESSAGE

  const apiCompleteAdditionApplication = async () => {
    completeErrorMessage.value = ''

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
      addServices: finalAddServices.map((svc) => ({ soc: getServiceCode(svc), name: getServiceName(svc) })),
      cancelServices: finalCancelServices.map((svc) => ({
        soc: getServiceCode(svc),
        name: getServiceName(svc),
        prodHstSeq: getProductSeqNo(svc),
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
      completeErrorMessage.value = '계약번호 또는 휴대폰번호가 없어 처리를 진행할 수 없습니다.'
      console.warn('[변경][작성완료] 부가서비스 처리 중단', {
        reason: 'missing common payload',
        ...commonPayload,
      })
      return false
    }

    try {
      const payload = {
        ...commonPayload,
        serviceSelect: selectedTypes,
        cstmrTypeCd: formData.cstmrTypeCd || 'NA',
        additionConfirmCompleted: formData.additionConfirmCompleted === true,
        cstmrNm: formData.cstmrNm || '',
        userBirthDate: formData.userBirthDate || '',
        userGender: formData.userGender || '',
        cstmrJuridicalRrn1: formData.cstmrJuridicalRrn1 || '',
        cstmrJuridicalRrn2: formData.cstmrJuridicalRrn2 || '',
        cstmrJuridicalBizNo1: formData.cstmrJuridicalBizNo1 || '',
        cstmrJuridicalBizNo2: formData.cstmrJuridicalBizNo2 || '',
        cstmrJuridicalBizNo3: formData.cstmrJuridicalBizNo3 || '',
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
        agentNm: formData.agentNm || '',
        cpntId: formData.cpntId || '',
        cpntNm: formData.cpntNm || '',
        cntpntShopCd: formData.cntpntShopCd || '',
        cntpntShopNm: formData.cntpntShopNm || '',
        additionCancelList: finalCancelServices.map((svc) => ({
          soc: getServiceCode(svc),
          prodHstSeq: getProductSeqNo(svc),
          svcTgtCd: svc.svcTgtCd || getServiceTargetCd('R11'),
        })),
        additionList: finalAddServices.map((svc) => ({
          soc: getServiceCode(svc),
          ftrNewParam: getFtrNewParam(svc),
          flag: svc.flag || '',
          svcTgtCd: svc.svcTgtCd || getServiceTargetCd('R11'),
        })),
        // P11: 요금제변경
        ...(selectedTypes.includes('P11') ? {
          planChange: {
            svcTgtCd: getServiceTargetCd('P11'),
            planCategoryCd: formData.planName1 || '',
            planCd: formData.planName2 || '',
            changeTypeCd: formData.changeDate || '',
          },
        } : {}),
        // O11: 번호변경
        ...(selectedTypes.includes('O11') ? {
          numberChange: {
            svcTgtCd: getServiceTargetCd('O11'),
            reqWantFnNo: formData.reqWantFnNo || '',
            reqWantMnNo: formData.reqWantMnNo || '',
            reqWantRnNo: formData.reqWantRnNo || '',
            wishNo: formData.wishNo || '',
          },
        } : {}),
        // O12: 분실복구/일시정지해제
        ...(selectedTypes.includes('O12') ? {
          unpause: {
            svcTgtCd: getServiceTargetCd('O12'),
            unLockPw: formData.unLockPw || '',
          },
        } : {}),
        // R14: 단말보험
        ...(selectedTypes.includes('R14') ? {
          insurance: {
            svcTgtCd: getServiceTargetCd('R14'),
            clauseInsuranceYn: formData.clauseInsuranceYn || '',
            catCd: formData.recCat1 || '',
            insrProdCd: formData.recCat2 || '',
          },
        } : {}),
        // O13: SIM정보
        ...(selectedTypes.includes('O13') ? {
          simInfo: {
            svcTgtCd: getServiceTargetCd('O13'),
            hasSim: formData.hasSim || '',
            usimKindsCd: formData.usimKindsCd || '',
            reqUsimSn: formData.reqUsimSn || '',
            eid: formData.eid || '',
            imei1: formData.imei1 || '',
            imei2: formData.imei2 || '',
          },
        } : {}),
        // R15: 데이터쉐어링
        ...(selectedTypes.includes('R15') ? {
          dataSharing: {
            svcTgtCd: getServiceTargetCd('R15'),
            shareUseState: formData.shareUseState || '',
            sharePhoneNum: formData.sharePhoneNum || '',
            shareUsimNum: formData.shareUsimNum || '',
          },
        } : {}),
        // R16: 결합Solo
        ...(selectedTypes.includes('R16') ? {
          combineSolo: {
            svcTgtCd: getServiceTargetCd('R16'),
            soloData: formData.soloData || '',
          },
        } : {}),
      }
      console.log('[변경][작성완료] complete 요청', {
        applicationKey: applicationKey.value,
        ...payload,
      })
      const res = await post(`/api/msf/formServiceChange/${applicationKey.value}/complete`, payload)
      console.log('[변경][작성완료] complete 응답', res)

      if (!isFormResponseSuccess(res)) {
        completeErrorMessage.value = getResponseMessage(res)
        return false
      }

      console.log('[변경][작성완료] complete 처리 완료')
      return true
    } catch (e) {
      completeErrorMessage.value = e?.response?.data?.data?.resMessage || e?.message || DEFAULT_COMPLETE_ERROR_MESSAGE
      console.error('[변경][작성완료] 부가서비스 처리 예외', e)
      return false
    }
  }

  const getCompleteErrorMessage = () => completeErrorMessage.value || DEFAULT_COMPLETE_ERROR_MESSAGE

  return {
    applicationKey,
    formData,
    authFlags,
    completeErrorMessage,
    apiGetMyinfoView,
    apiCompleteAdditionApplication,
    getCompleteErrorMessage,
  }
})
