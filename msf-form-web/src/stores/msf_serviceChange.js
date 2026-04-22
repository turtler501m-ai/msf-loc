import { defineStore } from 'pinia'
import { reactive } from 'vue'
import { post } from '@/libs/api/msf.api'

export const useMsfFormSvcChgStore = defineStore('msf_form_svc_chg', () => {
  // Step 1: Customer Info
  const formData = reactive({
    /** 고객유형 */
    cstmrType: '', //고객유형
    /* 가입자 정보 */
    cstmrNm: '', //이름
    userBirthDate: '', //생년월일
    userGender: '', //성별
    cstmrJuridicalRrn1: '', //법인등록번호1
    cstmrJuridicalRrn2: '', //법인등록번호2
    cstmrJuridicalBizNo1: '', //사업자등록번호1
    cstmrJuridicalBizNo2: '', //사업자등록번호2
    cstmrJuridicalBizNo3: '', //사업자등록번호3
    cstmrJuridicalRepNm: '', //대표자명
    deviceChgTel1: '', //변경휴대폰번호1
    deviceChgTel2: '', //변경휴대폰번호2
    deviceChgTel3: '', //변경휴대폰번호3
    cstmrVisitTypeCd: '',
    formType: 'SVC',
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
    telNo1: '', //전화번호1
    telNo2: '', //전화번호2
    telNo3: '', //전화번호3
    emailAddr1: '', //이메일주소1
    emailAddr2: '', //이메일주소2
    address1: '', //주소1
    address2: '', //주소2
    address3: '', //주소3
    /** 서비스 변경 선택_type01__디자인미확정 */
    allCheck: '', //전체 선택
    serviceSelect: [], // 서비스선택
    serviceList: [
      { value: 'serviceSelect1', label: '무선데이터차단 서비스' },
      { value: 'serviceSelect2', label: '정보료 상한금액 설정/변경', checked: true },
      { value: 'serviceSelect3', label: '부가서비스 신청/변경' },
      { value: 'serviceSelect4', label: '요금제 변경', notConcurrentChange: true },
      { value: 'serviceSelect5', label: '번호변경', notConcurrentChange: true },
      {
        value: 'serviceSelect6',
        label: '분실복구/일시정지해제 신청',
        notConcurrentChange: true,
      },
      { value: 'serviceSelect7', label: '단말보험 가입' },
      { value: 'serviceSelect8', label: 'USIM 변경' },
      { value: 'serviceSelect9', label: '데이터쉐어링 가입/해지' },
      { value: 'serviceSelect10', label: '아무나 SOLO 결합' },
    ], //서비스리스트
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

  return {
    formData,
  }
})
