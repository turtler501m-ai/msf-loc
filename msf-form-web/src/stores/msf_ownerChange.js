import { defineStore } from 'pinia'
import { reactive } from 'vue'
import { post } from '@/libs/api/msf.api'

export const useMsfFormOwnChgStore = defineStore('msf_form_own_chg', () => {
  // Step 1: Customer Info
  const formData = reactive({
    /********** 양도고객 (tr) **********/
    /** 고객(양도고객)유형 */
    tr_customer: {
      cstmrType: '', //고객유형
      /** 고객(양도고객)신분증 확인 */
      tr_idCard: '', //신분증
      tr_idCardScan: '', //신분증 스캔
      tr_licenseRegion: '', //면허 지역
      /** 고객(양도고객)정보 */
      tr_userName: '', //이름
      tr_userBirthDate: '', //생년월일
      tr_userGender: '', //성별
      tr_corpRegNo1: '', //법인등록번호1
      tr_corpRegNo2: '', //법인등록번호2
      tr_bizNo1: '', //사업자등록번호1
      tr_bizNo2: '', //사업자등록번호2
      tr_bizNo3: '', //사업자등록번호3
      tr_repreName: '', //대표자명
      tr_transferorPhone1: '010', //명의변경 휴대폰번호1
      tr_transferorPhone2: '', //명의변경 휴대폰번호2
      tr_transferorPhone3: '', //명의변경 휴대폰번호3
    },
    /** 고객(실사용자) 정보 */
    cstmrNm: '', //실사용자이름
    userBirthDate: '', //생년월일
    userGender: '', //성별
    /** 대리인 위임 정보 */
    minorAgentNm: '', //위임받은고객이름
    agentBirthDate: '', //생년월일
    agentGender: '', //성별
    minorAgentRelTypeCd: '', //관계
    minorAgentTelFnNo: '', //연락처1
    minorAgentTelMnNo: '', //연락처2
    minorAgentTelRnNo: '', //연락처3

    /********** 양수고객 (te) **********/
    /** 고객(양수고객)유형 */
    te_customer: {
      cstmrType: '', //고객유형
      te_visitCustomer: '', //방문고객
      /** 고객(양수고객)신분증 확인 */
      te_idCard: '', //신분증
      te_idCardScan: '', //신분증 스캔
      te_licenseRegion: '', //면허 지역
      /** 고객(양수고객) 정보 */
      te_customerName: '', //고객명
      te_userName: '', //이름
      te_residentNo1: '', //주민등록번호1
      te_residentNo2: '', //주민등록번호2
      te_foreignerNo1: '', //외국인등록번호1
      te_foreignerNo2: '', //외국인등록번호2
      te_corpRegNo1: '', //법인등록번호1
      te_corpRegNo2: '', //법인등록번호2
      te_bizNo1: '', //사업자등록번호1
      te_bizNo2: '', //사업자등록번호2
      te_bizNo3: '', //사업자등록번호3
      te_repreName: '', //대표자명
      te_bizType: '', //업종
      te_bizItem: '', //업태
      /* 법정대리인 정보 */
      te_repName: '', //이름
      te_repRegistrationNo1: '', //주민등록번호1
      te_repRegistrationNo2: '', //주민등록번호2
      te_repforeignerNo1: '', //외국인등록번호1
      te_repforeignerNo2: '', //외국인등록번호2
      te_repRelation: '', //관계
      te_repPhone1: '', // 연락처1
      te_repPhone2: '', // 연락처2
      te_repPhone3: '', // 연락처3
      te_repPhoneAuth: '', //인증번호입력
      te_repAgree: false, //동의
      /** 고객(양수고객) 연락처 */
      te_mobileNo1: '010', //휴대폰번호1
      te_mobileNo2: '', //휴대폰번호2
      te_mobileNo3: '', //휴대폰번호3
      te_telNo1: '', //전화번호1
      te_telNo2: '', //전화번호2
      te_telNo3: '', //전화번호3
      te_emailAddr1: '', //이메일주소1
      te_emailAddr2: '', //이메일주소2
      te_address1: '', //주소1
      te_address2: '', //주소2
      te_address3: '', //주소3
      te_country: '', //국가
      te_stayPeriod: '', //체류기간
      te_visaType: '', //비자
    },
    /** 요금제 정보 */
    planName1: '', // 요금제1
    planName2: '', // 요금제2
    planName3: '', // 요금제3
    agency: '', //대리점
  })

  return {
    formData,
  }
})
