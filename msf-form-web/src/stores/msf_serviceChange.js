import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'
import { post } from '@/libs/api/msf.api'

export const useMsfFormSvcChgStore = defineStore('msf_form_svc_chg', () => {
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
    agency: '', //대리점
    /** 서비스 변경 선택_type02__디자인미확정 */
    addonService: '', //요금제/부가 서비스
    combinedService: '', //결합서비스
    loseLock: '', //일시/분실정지
    joinInfoChange: '', //가입정보 변경
    /* 요금제 변경 관련 */
    planName1: '',
    planName2: '',
    changeDate: '',
  })

  const authFlags = ref({
    deviceChgTel: false,
  })

  const apiGetMyinfoView = async () => {
    const ncn = formData.ncn || formData.contractNum
    const ctn = `${formData.deviceChgTel1 || ''}${formData.deviceChgTel2 || ''}${formData.deviceChgTel3 || ''}`
    console.log('[서비스변경][MyinfoView] 가입정보 조회 요청', { ncn, ctn })
    try {
      const data = await post('/api/msf/formServiceChange/changinfo/view', {
        ncn,
        ctn,
        contractNum: ncn,
        custId: formData.custId || '',
      }, { silent: true })
      console.log('[서비스변경][MyinfoView] 가입정보 조회 응답', data)
      if (data) {
        if (data.prvRateGrpNm !== undefined) formData.prvRateGrpNm = data.prvRateGrpNm || ''
        if (data.initActivationDate && data.initActivationDate !== '-') {
          formData.initActivationDate = data.initActivationDate
          formData.lstComActvDate = data.initActivationDate
        }
        if (data.addr && data.addr !== '-') formData.addr = data.addr
        if (data.remindBlckYn !== undefined) formData.remindBlckYn = data.remindBlckYn || ''
        if (data.payData !== undefined) formData.payData = data.payData
        if (data.billData !== undefined) formData.billData = data.billData

        // 가입자 연락처 자동 셋팅
        // 휴대폰번호 ← 인증된 변경 휴대폰번호
        formData.mobileNo1 = formData.deviceChgTel1 || ''
        formData.mobileNo2 = formData.deviceChgTel2 || ''
        formData.mobileNo3 = formData.deviceChgTel3 || ''

        // 전화번호 ← homeTel (02-XXXX-XXXX 또는 0XX-XXXX-XXXX 형식 파싱)
        // 20260506 일단주석
        //const rawTel = (data.homeTel || '').replace(/\D/g, '')
        //if (rawTel.length >= 9) {
        //  if (rawTel.startsWith('02')) {
        //    formData.telNo1 = '02'
        //    formData.telNo2 = rawTel.slice(2, rawTel.length - 4)
        //    formData.telNo3 = rawTel.slice(-4)
        //  } else if (rawTel.length >= 10) {
        //    formData.telNo1 = rawTel.slice(0, 3)
        //    formData.telNo2 = rawTel.slice(3, rawTel.length - 4)
        //    formData.telNo3 = rawTel.slice(-4)
        //  }
        //}

        // 주소 ← selectCntrListNoLogin BAN 주소
        if (data.zipNo && data.zipNo !== '-') {
          formData.zipNo = data.zipNo
        }
        if (data.address && data.address !== '-') {
          formData.address = data.address
        } else if (data.addr && data.addr !== '-') {
          formData.address = data.addr
        }
        if (data.detailAddress && data.detailAddress !== '-') {
          formData.detailAddress = data.detailAddress
        }

        // 이메일 ← email (아이디@도메인 분리)
        if (data.email && data.email.includes('@')) {
          const [id, domain] = data.email.split('@')
          formData.emailAddr1 = id || ''
          formData.emailAddr2 = domain || ''
        }
      }
      return data
    } catch (e) {
      console.warn('[서비스변경][MyinfoView] 가입정보 조회 실패 (무시하고 진행)', e?.message)
      return null
    }
  }

  return {
    formData,
    authFlags,
    apiGetMyinfoView,
  }
})
