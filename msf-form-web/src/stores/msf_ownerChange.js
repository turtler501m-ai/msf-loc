import { defineStore } from 'pinia'
import { computed, ref, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

export const useMsfFormOwnChgStore = defineStore('msf_form_own_chg', () => {
  // Step 1: Customer Info
  const formData = ref({
    /********** 양도고객 (tr) **********/
    /** 고객(양도고객)유형 */
    tr_customer: {
      cstmrTypeCd: 'NA', //고객유형
      /** 고객(양도고객)신분증 확인 */
      identityCertTypeCd: '',
      tr_idCard: '', //신분증
      identityTypeCd: '', //신분증 스캔
      identityIssuDate: '', //발급일자
      identityIssuRegion: '', //발급지역
      driveLicnsNo: '', //면허번호
      identityIssuRegion: '', //면허 지역
      cstmrJuridicalRrn1: '', //법인등록번호1
      cstmrJuridicalRrn2: '', //법인등록번호2
      cstmrJuridicalBizNo1: '', //사업자등록번호1
      cstmrJuridicalBizNo2: '', //사업자등록번호2
      cstmrJuridicalBizNo3: '', //사업자등록번호3
      cstmrJuridicalRepNm: '', //대표자명
      upjnCd: '', //업종
      bcuSbst: '', //업태
      deviceChgTel1: '', //휴대폰 처음3자리
      deviceChgTel2: '', //휴대폰 가운데4자리
      deviceChgTel3: '', //휴대폰 마지막4자리
      /* 법정대리인 정보 */
      minorAgentNm: '', //위임받은고객이름
      minorAgentRelTypeCd: '', //신청인과의 관계
      minorAgentTelFnNo: '', //연락처1
      minorAgentTelMnNo: '', //연락처2
      minorAgentTelRnNo: '', //연락처3
      repAgree: '', // 동의
      /* 법정대리인 정보 */
      /** 고객(양도고객)정보 */
      cstmrNm: '', //이름
      cstmrNativeRrn1: '', //내국인 주민번호1
      cstmrNativeRrn2: '', //내국인 주민번호2
      cstmrForeignerRrn1: '', //외국인 주민번호1
      cstmrForeignerRrn2: '', //외국인 주민번호2
      userBirthDate: '', //생년월일
      userGender: 'M', //성별
      isTrCustomer: true,
      formType: 'OWN',
      serviceType: 'TR_CUSTOMER',
      userId: '',
      isVerified: false,
    },
    /********** 양수고객 (te) **********/
    /** 고객(양수고객)유형 */
    te_customer: {
      cstmrTypeCd: 'NA', //고객유형
      /** 고객(양수고객)신분증 확인 */
      identityCertTypeCd: '',
      tr_idCard: '', //신분증
      identityTypeCd: '', //신분증 스캔
      identityIssuDate: '', //발급일자
      identityIssuRegion: '', //발급지역
      driveLicnsNo: '', //면허번호
      identityIssuRegion: '', //면허 지역
      cstmrJuridicalRrn1: '', //법인등록번호1
      cstmrJuridicalRrn2: '', //법인등록번호2
      cstmrJuridicalBizNo1: '', //사업자등록번호1
      cstmrJuridicalBizNo2: '', //사업자등록번호2
      cstmrJuridicalBizNo3: '', //사업자등록번호3
      cstmrJuridicalRepNm: '', //대표자명
      upjnCd: '', //업종
      bcuSbst: '', //업태
      deviceChgTel1: '', //휴대폰 처음3자리
      deviceChgTel2: '', //휴대폰 가운데4자리
      deviceChgTel3: '', //휴대폰 마지막4자리
      /* 법정대리인 정보 */
      minorAgentNm: '', //위임받은고객이름
      minorAgentRelTypeCd: '', //신청인과의 관계
      minorAgentTelFnNo: '', //연락처1
      minorAgentTelMnNo: '', //연락처2
      minorAgentTelRnNo: '', //연락처3
      repAgree: '', // 동의
      /* 법정대리인 정보 */
      /** 고객(양수고객) 연락처 */
      mobileNo1: '010', //휴대폰번호1
      mobileNo2: '', //휴대폰번호2
      mobileNo3: '', //휴대폰번호3
      telNo1: '', //전화번호1
      telNo2: '', //전화번호2
      telNo3: '', //전화번호3
      emailAddr1: '', //이메일주소1
      emailAddr2: '', //이메일주소2
      zip: '', //주소1
      address: '', //주소2
      detailAddress: '', //주소3
      country: '', //국가
      te_stayPeriod: '', //체류기간
      visaType: '', //비자
      isTeCustomer: true,
      formType: 'OWN',
      serviceType: 'TE_CUSTOMER',
      userId: '',
      isVerified: false,
    },
    // 실사용자 & 대리인
    realUserName: '', //실사용자 이름
    userBirthDate: '', //생년월일
    userGender: '', //성별
    minorAgentNm: '', //위임받은 고객이름
    agentGender: '', //성별
    agentBirthDate: '', //대리인 생년월일
    minorAgentRelTypeCd: '', //신청인과의 관계
    minorAgentTelFnNo: '', //연락처 앞자리
    minorAgentTelMnNo: '', //연락처 중간자리
    minorAgentTelRnNo: '', //연락처 뒷자리
    // 실사용자 & 대리인
    /** 고객(실사용자) 정보 */
    cstmrNm: '', //실사용자이름
    /** 대리인 위임 정보 */
    /** 요금제 정보 */
    planInfo: {
      planName1: '', // 요금제1
      planName2: '', // 요금제2
      planName3: '', // 요금제3
      agency: '', //대리점
      planSelectType: 'CURRENT',
      userId: '',
      ncn: '',
      ctn: '',
      custId: '',
      orgProdId: '',
    },
    /** 요금제 정보 */
    /** USIM 정보 */
    usimInfo: {
      hasSim: 'hasSim3', //SIM보유
      usimKindsCd: '', //USIM 선택
      reqUsimSn: '', //USIM 번호
      simPurchaseMethod: '', //USIM 구매 방식
      prodNm: '', //휴대폰 모델병
      eid: '', //EID
      imei1: '', //IMEI1
      imei2: '', //IMEI2
      modelNm: '',
    },
    /** 납부정보 */
    productPayment: {
      /* 납부 정보 */
      cstmrBillSendTypeCd: '', //수신유형
      reqPayTypeCd: '', //납부방법
      autoPayerType: '', //자동이체-납부자유형
      reqBankCd: '', //자동이체-은행선택
      reqAccountNo: '', //자동이체-계좌번호입력
      reqAccountNm: '', //자동이체-납부고객명
      reqAccountRrn: '', //자동이체-생년월일(8자리) 임력
      reqAccountRelTypeCd: '', //자동이체-관계
      isAutoAgree: false, //자동이체-동의
      cardPayerType: '', //신용카드-납부자유형
      reqCardCompanyCd: '', //신용카드-카드사선택
      reqCardNo: '', //신용카드-카드번호입력
      reqCardMm: '', //신용카드-유효기간(MM)
      reqCardYy: '', //신용카드-유효기간(YY)
      reqCardNm: '', //신용카드-납부고객명
      reqCardRrn: '', //신용카드-생년월일
      cardRelation: '', //신용카드-관계
      othersPaymentAgrYn: 'N', //타인납부-동의
      combId: '', //통합청구-청구계정ID
      combAgree: false, //통합청구-동의
    },
    /** 납부정보 */
    /** USIM 정보 */
    customer_term: [
      'CLAUSE_REQUIRED_02',
      'CLAUSE_REQUIRED_01',
      'CLAUSE_MOVE_01',
      'CLAUSE_REQUIRED_03',
      'CLAUSE_FATH_01',
      'CLAUSE_FATH_02',
      'CLAUSE_PARTNER_01',
      'CLAUSE_INFO_01',
      'CLAUSE_SELECT_10',
      'CLAUSE_SELECT_TIT_01',
      'CLAUSE_SELECT_TIT_02',
    ],
  })

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

  // ✨ cstmrTypeCd의 변화에 반응하는 computed 타이틀
  const trCustomerTitle = computed(() => {
    const type = formData.value.tr_customer.cstmrTypeCd
    const minor = ['NM', 'FM']
    const dynamicTitle = {
      identityVerifyTitle: !minor.includes(type)
        ? '고객(양도고객) 신분증 확인'
        : '고객(양도고객) 법정대리인 신분증 확인',
      subscriberInfo: !minor.includes(type)
        ? '고객(양도고객) 정보'
        : '고객(양도고객) 미성년자 정보',
    }
    return dynamicTitle
  })

  const teCustomerTitle = computed(() => {
    const type = formData.value.te_customer.cstmrTypeCd
    const minor = ['NM', 'FM']
    const dynamicTitle = {
      identityVerifyTitle: !minor.includes(type)
        ? '고객(양수고객) 신분증 확인'
        : '고객(양수고객) 법정대리인 신분증 확인',
      subscriberInfo: !minor.includes(type)
        ? '고객(양수고객) 정보'
        : '고객(양수고객) 미성년자 정보',
    }
    return dynamicTitle
  })

  const updateTrCustomer = (payload) => {
    if (!formData.value.tr_customer) return

    Object.assign(formData.value.tr_customer, payload)
  }

  const updatePlanInfo = (payload) => {
    if (!formData.value.planInfo) return

    Object.assign(formData.value.planInfo, payload)
  }

  return {
    formData,
    updateTrCustomer,
    updatePlanInfo,
    trCustomerTitle,
    teCustomerTitle,
    authFlags,
  }
})
