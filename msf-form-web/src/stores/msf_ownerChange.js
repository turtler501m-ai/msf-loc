import { defineStore } from 'pinia'
import { computed, ref, watch } from 'vue'
import { post } from '@/libs/api/msf.api'

const DEFAULT_ERROR_MESSAGE = '신청서 등록이 실패하였습니다. 다시 시도해 주세요.'

export const useMsfFormOwnChgStore = defineStore('msf_form_own_chg', () => {
  const applicationKey = ref('TEMP_' + Math.random().toString(36).substring(7))
  const completeErrorMessage = ref('')

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
      identityTypeNm: '',
      isScanVerified: false,
      isSaved: false,
      identityIssuDate: '', //발급일자
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
      repName: '',
      minorAgentNm: '', //위임받은고객이름
      minorAgentRelTypeCd: '', //신청인과의 관계
      minorAgentTelFnNo: '', //연락처1
      minorAgentTelMnNo: '', //연락처2
      minorAgentTelRnNo: '', //연락처3
      repRegistrationNo1: '',
      repRegistrationNo2: '',
      repForeignerNo1: '',
      repForeignerNo2: '',
      minorUserBirthDate: '',
      minorUserGender: 'M',
      repAgree: false, // 동의
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
      fstEsimYn: null,
    },
    /********** 양수고객 (te) **********/
    /** 고객(양수고객)유형 */
    te_customer: {
      /* 고객(양수)정보 */
      cstmrTypeCd: 'NA', //고객유형
      cstmrNm: '', //이름
      cstmrNativeRrn1: '', //내국인 주민번호1
      cstmrNativeRrn2: '', //내국인 주민번호2
      cstmrForeignerRrn1: '', //외국인 주민번호1
      cstmrForeignerRrn2: '', //외국인 주민번호2
      upjnCd: '', //업종
      bcuSbst: '', //업태
      deviceChgTel1: '', //휴대폰 처음3자리
      deviceChgTel2: '', //휴대폰 가운데4자리
      deviceChgTel3: '', //휴대폰 마지막4자리
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
      cstmrVisitTypeCd: '', //방문유형코드
      /* 법정대리인 정보 */
      repName: '',
      minorAgentNm: '', //위임받은고객이름
      minorAgentRelTypeCd: '', //신청인과의 관계
      minorAgentTelFnNo: '', //연락처1
      minorAgentTelMnNo: '', //연락처2
      minorAgentTelRnNo: '', //연락처3
      repRegistrationNo1: '',
      repRegistrationNo2: '',
      repForeignerNo1: '',
      repForeignerNo2: '',
      minorUserBirthDate: '',
      minorUserGender: 'M',
      repAgree: false, // 동의
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
    realUserInfo: {
      // 실사용자 & 대리인
      realUserName: '', //실사용자 이름
      userBirthDate: '', //생년월일
      userGender: 'M', //성별
      minorAgentNm: '', //위임받은 고객이름
      agentGender: '', //성별
      agentBirthDate: '', //대리인 생년월일
      minorAgentRelTypeCd: '', //신청인과의 관계
      minorAgentTelFnNo: '', //연락처 앞자리
      minorAgentTelMnNo: '', //연락처 중간자리
      minorAgentTelRnNo: '', //연락처 뒷자리
    },
    memo: '',
    // 실사용자 & 대리인
    /** 고객(실사용자) 정보 */
    cstmrNm: '', //실사용자이름
    /** 대리인 위임 정보 */
    /** 요금제 정보 */
    planInfo: {
      planName1: '', // 요금제1
      planName2: '', // 요금제2
      planName3: '', // 요금제3
      planAmt: '', //요금
      planNm: '',
      agency: '', //대리점
      planSelectType: 'CURRENT',
      userId: '',
      ncn: '',
      ctn: '',
      custId: '',
      orgProdId: '',
      orgPordNm: '',
      ktOrgId: '',
    },
    /** 요금제 정보 */
    /** USIM 정보 */
    usimInfo: {
      hasSim: '', //SIM보유
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
      othersPaymentYn: 'N', //타인납부-동의
      combId: '', //통합청구-청구계정ID
      combAgree: false, //통합청구-동의
    },
    /** 납부정보 */
    /** USIM 정보 */
    agreeCheck1: false,
    agreeCheck2: false,
    agreeCheck3: false,
    agreeCheck4: false,
    agreeCheck5: false,
    agreeCheck6: false,
    termsAgreed: false,
    othersTrnsAgreeYn: 'N', // 혜택제공을위한제3자제공동의(M모바일)여부
    othersTrnsKtAgreeYn: 'N', // 혜택제공을위한제3자제공동의(KT)여부
    othersAdReceiveAgreeYn: 'N', // 제3자제공관련광고수신동의여부
  })

  const buildCompletePayload = () => ({
    /* MSF_REQEST_NAME_TRNS */
    trnsCstmrTypeCd: formData.value.tr_customer.cstmrTypeCd, // 양도인고객구분유형코드

    trnsTrnsfeMobileNo:
      formData.value.te_customer.mobileNo1 +
      formData.value.te_customer.mobileNo2 +
      formData.value.te_customer.mobileNo3, // 양수인모바일번호

    trIdentityCertTypeCd: formData.value.tr_customer.identityCertTypeCd, // 신분증인증유형코드

    trKnoteIdentityScanCstmrNm: null, // KNOTE신분증고객명
    trKnoteIdentityEssNo: null, // KNOTE신분증식별번호
    trKnoteIdentityTypeCd: null, // KNOTE신분증유형코드
    trKnoteIdentityScanDt: null, // KNOTE신분증스캔일시
    trKnoteScanId: null, // KNOTE신분증스캔번호

    trFathTrgYn: 'N', // 안면인증대상여부
    trFathTrgIdentityCertTypeCd: null, // 안면인증대상신분증유형코드
    trFathTransacId: null, // 안면인증트랜잭션ID
    trFathCmpltNtfyDate: null, // 안면인증완료일자
    trFathTelNo: null, // 안면인증URL전송전화번호
    trFathMobileFnNo: null, // 안면인증정보휴대폰번호앞자리번호
    trFathMobileMnNo: null, // 안면인증정보휴대폰번호중간자리번호
    trFathMobileRnNo: null, // 안면인증정보휴대폰번호뒷자리번호

    trAuthInfo: null, // 인증정보

    trIdentityTypeCd: formData.value.tr_customer.identityTypeCd, // 신분증유형코드
    trIdentityIssuDate: formData.value.tr_customer.identityIssuDate?.replaceAll(/[^0-9]/g, ''), // 신분증발급일자
    trIdentityIssuRegion: formData.value.tr_customer.identityIssuRegion, // 신분증발급지역

    trSelfIssuNo: null, // 발급번호
    trDriveLicnsNo: formData.value.tr_customer.driveLicnsNo, // 운전면허번호

    trnsNm: formData.value.tr_customer.cstmrNm, // 양도인명
    trnsMobileNo:
      formData.value.tr_customer.deviceChgTel1 +
      formData.value.tr_customer.deviceChgTel2 +
      formData.value.tr_customer.deviceChgTel3, // 명의변경대상모바일번호
    trnsPhoneNo: null, // 명의자연락처번호
    trnsPwd: null, // 명의변경용비밀번호
    trnsMyslfConfMethCd: null, // 양도인본인확인방법코드
    trnsTrnsfeNm: formData.value.te_customer.cstmrNm, // 양도인이입력한양수인명
    trCstmrVisitTypeCd: formData.value.te_customer.cstmrVisitTypeCd, // 방문고객유형코드

    trMinorAgentNm: formData.value.tr_customer.minorAgentNm, // 미성년자법정대리인성명
    trMinorAgentRrn: formData.value.tr_customer.minorAgentRrn, // 미성년자법정대리인등록번호
    trMinorAgentBirth: formData.value.tr_customer.minorAgentBirth, // 미성년자법정대리인생년월일
    trMinorAgentGenderCd: formData.value.tr_customer.minorAgentGenderCd, // 미성년자법정대리인성별
    trMinorAgentRelTypeCd: formData.value.tr_customer.minorAgentRelTypeCd, // 미성년자법정대리인관계유형코드
    trMinorAgentTelFnNo: formData.value.tr_customer.minorAgentTelFnNo, // 미성년자법정대리인연락처앞자리번호
    trMinorAgentTelMnNo: formData.value.tr_customer.minorAgentTelMnNo, // 미성년자법정대리인연락처중간자리번호
    trMinorAgentTelRnNo: formData.value.tr_customer.minorAgentTelRnNo, // 미성년자법정대리인끝자리번호

    trMinorAgentAgrmYn: 'N', // 미성년자법정대리인안내사항및동의여부
    trMinorAgentSelfInqryAgrmYn: 'N', // 미성년자법정대리인본인인증조회동의여부
    trMinorAgentSelfCertTypeCd: null, // 미성년자법정대리인본인인증유형코드
    trMinorAgentSelfIssuExprDate: null, // 미성년자법정대리인발급/만료일자
    trMinorAgentSelfIssuNo: null, // 미성년자법정대리인발급번호

    trnsSttusCd: 'N', // 처리상태코드
    authDelYn: 'N', // 개인정보삭제여부
    confirmMemo: null, // 처리메모
    /* MSF_REQEST_NAME_TRNS */

    /* MSF_REQEST_NAME_CHG */
    managerCd: formData.value.planInfo.agentCd, // 매니저코드
    managerNm: formData.value.planInfo.agency, // 매니저명
    agentCd: formData.value.planInfo.agentCd, // 대리점코드
    agentNm: formData.value.planInfo.agency, // 대리점명
    shopCd: formData.value.planInfo.agentCd, // 판매점코드
    shopNm: formData.value.planInfo.agency, // 판매점명
    realShopNm: formData.value.planInfo.agency, // 실판매점명
    cpntId: formData.value.planInfo.agentCd, // 접점ID
    cpntNm: formData.value.planInfo.agency, // 접점명
    cntpntShopCd: formData.value.planInfo.agentCd, // 채널판매점코드
    cntpntShopNm: formData.value.planInfo.agency, // 채널판매점명

    operTypeCd: 'OPER', // 업무구분유형코드

    cstmrTypeCd: formData.value.te_customer.cstmrTypeCd, // 고객구분유형코드

    mcnResNo: null, // 명의변경예약번호

    teIdentityCertTypeCd: formData.value.te_customer.identityCertTypeCd, // 신분증인증유형코드

    teKnoteIdentityScanCstmrNm: null, // KNOTE신분증고객명
    teKnoteIdentityEssNo: null, // KNOTE신분증식별번호
    teKnoteIdentityTypeCd: null, // KNOTE신분증유형코드
    teKnoteIdentityScanDt: null, // KNOTE신분증스캔일시
    teKnoteScanId: null, // KNOTE신분증스캔번호

    teFathTrgYn: 'N', // 안면인증대상여부
    teFathTrgIdentityCertTypeCd: null, // 안면인증대상신분증유형코드
    teFathTransacId: null, // 안면인증트랜잭션ID
    teFathCmpltNtfyDate: null, // 안면인증완료일자
    teFathTelNo: null, // 안면인증URL전송전화번호
    teFathMobileFnNo: null, // 안면인증정보휴대폰번호앞자리번호
    teFathMobileMnNo: null, // 안면인증정보휴대폰번호중간자리번호
    teFathMobileRnNo: null, // 안면인증정보휴대폰번호뒷자리번호

    teAuthInfo: null, // 인증정보

    teIdentityTypeCd: formData.value.te_customer.identityTypeCd, // 신분증유형코드

    teIdentityIssuDate: formData.value.te_customer.identityIssuDate?.replaceAll(/[^0-9]/g, ''), // 신분증발급일자

    teIdentityIssuRegion: formData.value.te_customer.identityIssuRegion, // 신분증발급지역

    teSelfIssuNo: null, // 발급번호

    teDriveLicnsNo: formData.value.te_customer.driveLicnsNo, // 운전면허번호

    reqInfoChgYn: 'N', // 가입정보변경여부

    soc: formData.value.planInfo.planName2, // 요금제
    socNm: formData.value.planInfo.planNm, // 요금제명
    socBaseChrgAmt: formData.value.planInfo.planAmt, // 요금제기본료

    jehuProdTypeCd: null, // 요금제제휴처코드

    usimSuccYn: formData.value.usimInfo.hasSim === 'hasSim1' ? 'Y' : 'N', // USIM승계여부

    usimSn: formData.value.usimInfo.reqUsimSn, // USIM번호
    iccId: formData.value.usimInfo.iccId, // ICCID
    eid: formData.value.usimInfo.eid, // EID
    imei1: formData.value.usimInfo.imei1, // IMEI1
    imei2: formData.value.usimInfo.imei2, // IMEI2
    esimPhoneId: formData.value.usimInfo.prodNm, // eSIM휴대폰모델ID

    uploadPhoneSrlNo: null, // 업로드휴대폰일련번호
    remainPayDivCd: null, // 완납/승계구분코드

    clauseCntrDelYn: 'Y', // 양도인고객정보삭제동의여부
    clausePriCollectYn: 'Y', // 약관개인정보수집동의여부
    clausePriOfferYn: 'Y', // 약관개인정보제공동의여부
    clauseEssCollectYn: 'Y', // 약관고유식별정보수집이용제공동의여부
    clauseConfidenceYn: 'Y', // 약관신용정보이용동의여부
    clausePriAdYn: 'Y', // 약관개인정보광고전송동의여부
    clauseJehuYn: 'Y', // 제휴서비스동의여부
    clauseFinanceYn: 'Y', // 금융제휴약관동의여부

    personalInfoCollectAgreeYn: 'Y', // 고객혜택제공을위한개인정보수집및이용관련동의여부

    othersTrnsAgreeYn: formData.value.othersTrnsAgreeYn, // 혜택제공을위한제3자제공동의(M모바일)여부
    othersTrnsKtAgreeYn: formData.value.othersTrnsKtAgreeYn, // 혜택제공을위한제3자제공동의(KT)여부
    othersAdReceiveAgreeYn: formData.value.othersAdReceiveAgreeYn, // 제3자제공관련광고수신동의여부

    clauseFathYn: 'N', // 안면인증동의여부

    mcnStatRsnCd: 'RCMCMCN', // 명변사유코드

    memo: formData.value.memo, // 메모

    mcnStateCd: null, // 진행상태코드
    rcvCustNo: null, // 양수인고객번호
    rcvBillAcntNo: null, // 양수인청구번호

    recYn: formData.value.recYn, // 녹취여부

    resCd: null, // 예약등록코드
    resMsg: null, // 예약등록메세지

    scanId: null, // 스캐너ID

    appFormYn: 'N', // 스캔이미지여부
    appFormXmlYn: 'N', // 서식지XML여부

    fileNm: null, // 파일명
    fileMaskNm: null, // 마스크파일명
    /* MSF_REQEST_NAME_CHG */

    /* MSF_REQUEST_CSTMR */
    cstmrNm: formData.value.te_customer.cstmrNm, // 고객명
    cstmrNativeRrn: null, // 고객정보내국인주민등록번호
    cstmrNativeBirth: null, // 고객정보내국인생년월일
    cstmrNativeGenderCd: null, // 고객정보내국인성별
    cstmrPrivateCname: null, // 고객정보개인사업자상호명
    cstmrPrivateBizNo:
      formData.value.te_customer.cstmrJuridicalBizNo1 +
      formData.value.te_customer.cstmrJuridicalBizNo2 +
      formData.value.te_customer.cstmrJuridicalBizNo3, // 개인사업자사업자등록번호
    cstmrForeignerRrn:
      formData.value.te_customer?.cstmrForeignerRrn1 +
      formData.value.te_customer?.cstmrForeignerRrn2, // 고객정보외국인외국인등록번호
    cstmrForeignerBirth: null, // 고객정보외국인생년월일
    cstmrForeignerGenderCd: null, // 고객정보외국인성별
    cstmrForeignerPn: null, // 고객정보외국인여권번호
    cstmrForeignerCountryCd: null, // 고객정보외국인국가코드
    cstmrForeignerNation: formData.value.te_customer?.country, // 고객정보외국인국적
    cstmrForeignerVisaNo: null, // 고객정보외국인비자번호
    cstmrForeignerVdateStartDate: null, // 고객정보외국인체류기간시작일자
    cstmrForeignerVdateEndDate: null, // 고객정보외국인체류기간종료일자
    cstmrJuridicalCname: null, // 고객정보법인사업자법인명
    cstmrJuridicalRrn: null, // 고객정보법인사업자법인번호
    cstmrJuridicalBizNo: null, // 고객정보법인사업자사업자등록번호
    cstmrJuridicalRepNm: null, // 고객정보법인대표자명
    upjnCd: null, // 업종코드
    bcuSbst: null, // 업태내용
    cstmrJuridicalUserNm: formData.value.realUserInfo.realUserName, // 법인실사용자명
    cstmrJuridicalBirth: formData.value.realUserInfo.userBirthDate, // 법인실사용자생년월일
    cstmrVisitTypeCd: formData.value.te_customer?.cstmrVisitTypeCd, // 방문고객유형코드
    cstmrTelFnNo: formData.value.te_customer?.telNo1, // 고객정보전화번호앞자리번호
    cstmrTelMnNo: formData.value.te_customer?.telNo2, // 고객정보전화번호가운데자리번호
    cstmrTelRnNo: formData.value.te_customer?.telNo3, // 고객정보전화번호끝자리번호
    cstmrMobileFnNo: null, // 고객정보휴대폰번호앞자리번호
    cstmrMobileMnNo: null, // 고객정보휴대폰번호중간자리번호
    cstmrMobileRnNo: null, // 고객정보휴대폰번호끝자리번호
    cstmrZipcd: formData.value.te_customer?.zip, // 고객정보우편번호
    cstmrAdr: formData.value.te_customer?.address, // 고객정보주소
    cstmrAdrDtl: formData.value.te_customer?.detailAddress, // 고객정보상세주소
    cstmrAdrBjd: null, // 고객정보법정동주소
    cstmrEmailAdr:
      formData.value.te_customer?.emailAddr1 + '@' + formData.value.te_customer?.emailAddr2, // 고객정보이메일
    cstmrEmailReceiveYn: 'N', // 고객정보이메일수신여부
    cstmrReceiveTelFnNo: formData.value.te_customer?.mobileNo1, // 고객정보연락받을번호앞자리번호
    cstmrReceiveTelNmNo: formData.value.te_customer?.mobileNo2, // 고객정보연락번호중간자리번호
    cstmrReceiveTelRnNo: formData.value.te_customer?.mobileNo3, // 고객정보연락받을번호끝자리번호
    /* MSF_REQUEST_CSTMR */

    /* MSF_REQUEST_BILL_REQ */
    reqPayTypeCd: formData.value.productPayment.reqPayTypeCd, // 요금납부방법유형코드
    reqBankCd: formData.value.productPayment.reqBankCd, // 신청정보계좌이체은행코드
    reqAccountNm: formData.value.productPayment.reqAccountNm, // 계좌예금주명
    reqAccountRrn: null, // 신청정보계좌이체예금주주민번호
    reqAccountRelTypeCd: null, // 신청정보계좌이체예금주와관계유형코드
    reqAccountNo: formData.value.productPayment.reqAccountNo, // 계좌번호
    reqCardNm: formData.value.productPayment.reqCardNm, // 신용카드명의자명
    reqCardRrn: null, // 신청정보신용카드명의자주민번호
    reqCardCompanyCd: formData.value.productPayment.reqCardCompanyCd, // 신청정보신용카드카드사코드
    reqCardNo: formData.value.productPayment.reqCardNo, // 신용카드번호
    reqCardYy: formData.value.productPayment.reqCardYy, // 신청정보신용카드유효년
    reqCardMm: formData.value.productPayment.reqCardMm, // 신청정보신용카드유효월
    reqWireTypeCd: null, // 무선데이터이용타입유형코드
    othersPaymentYn: formData.value.productPayment.othersPaymentYn, // 타인납부여부
    othersPaymentTelFnNo: null, // 타인납부전화번호앞자리번호
    othersPaymentTelMnNo: null, // 타인납부전화번호중간자리번호
    othersPaymentTelRnNo: null, // 타인납부전화번호끝자리번호
    othersPaymentNm: formData.value.productPayment.reqCardNm, // 타인납부고객명
    othersPaymentRrn: null, // 타인납부주민번호
    othersPaymentRelTypeCd: formData.value.productPayment.reqCardNm, // 타인납부명의자와의관계유형코드
    othersPaymentReqNm: formData.value.productPayment.reqCardNm, // 타인납부신청인명
    prntsBillNo: null, // 모회선청구번호
    cstmrBillSendTypeCd: formData.value.productPayment.cstmrBillSendTypeCd, // 명세서종류유형코드
    /* MSF_REQUEST_BILL_REQ */

    /* MSF_REQUEST_AGENT */
    minorAgentNm: formData.value.te_customer?.minorAgentNm, // 미성년자법정대리인성명
    minorAgentRrn:
      formData.value.te_customer?.combinedNo1 + formData.value.te_customer?.combinedNo2, // 미성년자법정대리인등록번호
    minorAgentBirth: null, // 미성년자법정대리인생년월일
    minorAgentGenderCd: null, // 미성년자법정대리인성별
    minorAgentRelTypeCd: formData.value.te_customer?.minorAgentRelTypeCd, // 미성년자법정대리인관계유형코드
    minorAgentTelFnNo: null, // 미성년자법정대리인연락처앞자리번호
    minorAgentTelMnNo: null, // 미성년자법정대리인연락처중간자리번호
    minorAgentTelRnNo: null, // 미성년자법정대리인끝자리번호
    minorAgentAgrmYn: 'Y', // 미성년자법정대리인안내사항및동의여부
    minorAgentSelfInqryAgrmYn: 'Y', // 미성년자법정대리인본인인증조회동의여부
    minorAgentSelfCertTypeCd: null, // 미성년자법정대리인본인인증유형코드
    minorAgentCiInfo: null, // 미성년자법정대리인CI정보
    jrdclAgentNm: formData.value.te_customer?.repName, // 법인대리인명
    jrdclAgentRrn:
      formData.value.te_customer?.cstmrJuridicalRrn1 +
      formData.value.te_customer?.cstmrJuridicalRrn2, // 법인대리인등록번호
    jrdclAgentRelTypeCd: null, // 법정대리인관계유형코드
    jrdclAgentTelFnNo: null, // 법인대리인연락처앞자리번호
    jrdclAgentTelMnNo: null, // 법인대리인연락처중간자리번호
    jrdclAgentTelRnNo: null, // 법인대리인연락처끝자리번호
    /* MSF_REQUEST_AGENT */
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

  // 작성완료 API
  const apiCompleteApplication = async () => {
    try {
      const payload = buildCompletePayload()
      console.log('[payload] =========================================')
      console.log(payload)
      console.debug('[apiCompleteApplication] request', {
        applicationKey: applicationKey.value,
        ncn: payload?.customer?.ncn,
        customerType: payload?.customer?.customerType,
        isActive: payload?.product?.isActive,
      })
      const data = await post(`/api/form/owner-change/form/save`, payload)
      console.debug('[apiCompleteApplication] response', data)
      if (data?.data?.success) {
        completeErrorMessage.value = ''
        console.info('[apiCompleteApplication] success', { applicationNo: data?.applicationNo })
        return true
      }
      completeErrorMessage.value = data?.message || DEFAULT_ERROR_MESSAGE
      console.warn('[apiCompleteApplication] failed response', data)
      return false
    } catch (e) {
      completeErrorMessage.value = e?.response?.data?.message || DEFAULT_ERROR_MESSAGE
      console.error('[apiCompleteApplication] exception', {
        message: e?.message,
        status: e?.response?.status,
        response: e?.response?.data,
      })
      return false
    }
  }

  const validateCustomer = ref(() => true)
  const validateProduct = ref(() => true)
  const validateAgreement = ref(() => true)

  const updateTrCustomer = (payload) => {
    if (!formData.value.tr_customer) return

    Object.assign(formData.value.tr_customer, payload)
  }

  const updatePlanInfo = (payload) => {
    if (!formData.value.planInfo) return

    Object.assign(formData.value.planInfo, payload)
  }

  const updateUsimInfo = (payload) => {
    if (!formData.value.planInfo) return

    Object.assign(formData.value.usimInfo, payload)
  }

  return {
    formData,
    updateTrCustomer,
    updatePlanInfo,
    updateUsimInfo,
    apiCompleteApplication,
    buildCompletePayload,
    trCustomerTitle,
    teCustomerTitle,
    authFlags,
    validateCustomer,
    validateProduct,
    validateAgreement,
  }
})
