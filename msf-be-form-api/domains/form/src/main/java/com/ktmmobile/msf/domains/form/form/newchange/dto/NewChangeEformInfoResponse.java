package com.ktmmobile.msf.domains.form.form.newchange.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAdditionVo;

/**
 * 신규/변경 eform Response
 */
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class NewChangeEformInfoResponse {
    //암호화 및 가상화? 는 필요하지 않음?

    List<MsfRequestAdditionVo> additionList;

    //2026.05.23 추가
    Long requestKey;
    String cstmrEmailAdr;

    String cntpntShopCd;

    String cpntId; //판매점코드
    String cpntNm; //판매점명

    //무선서비스 계약 표준안내서
    String agentCd3; //대리점명/코드
    String telnum3; //연락처
    String modelPrice3; //출고가
    String modelSprt3; //단말기지원금
    String custPayAmt3; //선수납금
    String etcDcAmt3; //기타할인
    String modelInstallment3; //총 분할상환 원금
    String modelMonthly3; //총분할상환 월 할부금 개월수
    String modelInstFeeRate3; //총분할상환 할부수수료율
    String modelInstFee3; //총분할상환수수료
    String phoneMonthPayAmt3; //핸드폰 월 납부액
    String socCode3; //선택 요금제명
    String monthFeeVat3; //월 정액요금
    String enggMnthCnt3;//월 요금할인 개월약정
    String planDiscountMnthAmt3;//월 요금할인
    String planDiscountMnthRate3;//요금할인(지원금)할인율
    String planDiscountAmt3;//요금할인
    String telecomMonthPay3;//통신요금 월 납부액
    String baseMonthPay3;//월 기본납부액
    String penaltyCanRate3;//해지위약금%
    String penaltyChgRate3;//기기변경위약금%
    String penaltyCanMnth3;//개월수내 해지시
    String penalty12AmtMnth3;//위약금(12개월째)
    String penalty18AmtMnth3;//위약금(18개월째)
    String penaltyAmt3;//위약금
    String saleManagerNm3;//판매자명
    String mobileNo3;//이동전화번호
    String cstmrName3;//가입자
    String enggSignYn; //무선표준계약서 가입자서명여부


    //이용신청서_상단
    String operTypeCd; //업무구분 (NAC3:신규, MNP3:번이, HDN3: 기변)
    String serviceTypeCd; //선후불구분 (PO:후불, PP:선불)
    String cstmrTypeCd; //고객구분 (NA:내국인, NM:내국인미성년자, FA:외국인, FM:외국인미성년자, JP:법인사업자, 공공기관) // (I:개인, B:법인, E:기타)
    String agentCd; //대리점명/코드
    String telnum; //연락처

    //이용신청서_핸드폰대금
    //String modelMonthly; //구분 - 월할부금 개월수로 분기하여 처리?
    String modelMonthlyPricdCd; // 핸드폰 대금 0:일시불 1:할부
    String modelPrice; //출고가
    String modelSprt; //공통지원금
    String addDcAmt; //추가지원금
    String etcDcAmt; //기타할인
    String custPayAmt; //고객수납금(현금/카드)
    String modelInstallment; //할부원금
    String modelMonthly; //월 할부금 개월수
    String realMdlInstamt; //월 할부금 ( REAL_MDL_INSTAMT / MODEL_MONTHLY )
    String avgInstFee; //월 평균할부수수료 >> 가격정보조회 (예상납부금액 조회) 에서 쿼리에서 전달한 값이 맞을듯... 저장여부 검토필요.
    String reqModelName; //핸드폰모델명
    String reqPhoneSn; //일련번호
    String phoneMonthPayAmt; //핸드폰 월 납부액
    String phoneTotSubsidyAmt; //총 지원금
    String deviceDiscountAmt; //단말할인
    String planDiscountAmt; //요금할인(지원금)
    String penaltySupportAmt; //위약금 대납
    String joinFeeSupportAmt; //가입비 대납
    String etcSupportAmt; //기타

    //이용신청서_이동통신 요금
    String socCode; //선택한 요금상품
    String socCodeNm;  //선택한 요금상품
    String monthFeeVat; //월정액 요금(VAT 포함)
    String monthFeeDiscountAmt; //월 요금할인액
    String telecomMonthPay; //통신요금 월 납부액

    //이용신청서_요금
    String baseMonthPay; //월 기본 납부액

    //이용신청서_고객정보
    String cstmrName; //고객명(법인명)
    String cstmrNativeRrn; //개인(생년월일)/법인(등록번호)
    String gender; //성별
    String cstmrForeignerRrn; //외국인등록번호(외국인의 경우)
    String cstmrForeignerNation; //국적(외국인의 경우)
    String cstmrForeignerPn; //여권번호(외국인의 경우)
    String cstmrReceiveTelNo; //연락받을 전화번호
    String cstmrAddr; //주소
    String cstmrBillSendCode; //명세서 종류
    String cstmrMail; //이메일 주소
    String selfCertType; //본인인증 >> ? 스마트는 해당사항 없어보임.
    String cstmrForeignerSdate; //체류기간(외국인의 경우) 시작일자
    String cstmrForeignerEdate; //체류기간(외국인의 경우) 종료일자

    //이용신청서_요금자동납부
    String reqPayTypeCd; //요금자동납부_구분
    String reqPayType; //자동납부_구분 (자동이체, 신용카드, 자동충전(선불), 지로, 통합청구아이디)
    String autoPayOrgNm; //자동납부_은행/카드사 명 ( REQ_BANK / REQ_CARD_COMPNAY )
    String autoPayAcctCardNo; //자동납부_계좌/카드 번호 ( REQ_ACCOUNT_NUMBER / REQ_CARD_NO )
    String autoPayCardExp; //자동납부_카드유효기간 (유효년+유효월)
    String combineId; //통합청구_통합청구계정ID
    String othersPaymentNm; //타인납부동의_납부고객명
    String othersPaymentRelation; //타인납부동의_관계
    String othersPaymentRrn; //타인납부동의_생년월일
    String othersPaymentAgrYn; //타인납부동의여부

    //이용신청서_이용신청내용
    String additionNm; //데이터/부가상품
    String rantal; //합계
    String billSvc; //통신과금서비스 >> 화면에 없음. 확인필요
    String smPayPwdUseCd; //통신과금서비스_휴대폰소액결제 비밀번호 이용_구분 >> 화면에 없음. 확인필요
    String wishNoLinkSvc; //가입희망번호/번호연결서비스
    String usimKindsCd; //SIM모델명_USIM/eSIM_구분
    String reqUsimName; //SIM모델명
    String imei; //IMEI(일련번호)
    String imei1; //IMEI1(일련번호)
    String imei2; //IMEI2(일련번호)
    String eid; // EID
    String reqUsimSn; //SIM일련번호
    String usimPriceType; //SIM비용_구분
    String usimPrice; //SIM비용
    String usimPayMthdCd; //가입비
    String reqWireTypeCd; // 무선데이터 N:이용,Y:차단,R:데이터로밍 차단

    //이용신청서_번호이동
    String moveMobileNo; //번호이동할 전화번호
    String npBcntrTypeCd; //변경 전 통신사_선후불구분
    String moveCompany; //변경 전 통신사
    String moveCompanyCd; //변경 전 통신사_MVNO
    String moveThismonthPayType; //이번달 사용요금
    String moveAllotmentStat; //핸드폰 할부금
    String moveRefundAgreeFlag; //미환급액 요금상계(후불)


    //명의변경
    //trnsNm	고객명(법인명)
    //trnsMobileNo	변경대상 전화번호
    //cstmrNativeBirth	생년월일(법인/사업자등록번호)
    //gender	성별
    //remainPayDivCd	핸드폰 할부금

    //이용신청서_하단
    String appFormReqDt; //신청일자
    String authInfo; //인증정보

    //약관_개인정보 제3자 제공 동의
    String clausePriOfferYnJini; //㈜지니뮤직(지니뮤직)
    String clausePriOfferYnStory; //㈜스토리위즈(블라이스)
    String clausePriOfferYnWho; //㈜브이피(후후), ㈜메리츠화재
    String clausePriOfferYnMl; //㈜카카오엔터테인먼트(멜론)
    String clausePriOfferYnWc; //㈜왓차(왓차)
    String clausePriOfferYnMi; //㈜밀리의서재(밀리의서재)
    String clausePriOfferYnWv; //콘텐츠웨이브주식회사(Wavve)
    String clausePriOfferYnCu; //㈜비즈에프리테일(CU)
    String clausePriOfferYnMz; //㈜플렌티넷, ㈜플래티엠
    String clausePriOfferYnAlpha; //㈜케이티알파
    String clausePriOfferYnLotte; //㈜롯데멤버스
    String clausePriOfferYnMiWho; //㈜밀리의서재(밀리의서재), ㈜브이피(후후), ㈜메리츠화재

    //약관_개인정보 제3자 제공 동의
    String clausePriOfferYnKbank; //케이뱅크
    String clausePriOfferYnStory2; //스토리위즈

    //약관_혜택 제공을 위한 제3자 제공 동의
    String personalInfoCollectAgreeYn; //kt M mobile
    String othersTrnsAgreeYn; //㈜KT
    String collectAllAgreeYn; // 전체 선택 동의

    // 5G 커버리지 동의
    String clause5GCoverageYn;

    // 아무나솔로결합신청여부
    String combineSoloTypeYn;
    String combineSoloYn;

    String clauseEssCollectYn; // 고유식별정보 처리동의
    String clauseConfidenceYn; // 개인정보/신용정보수집.이용동의
    String clausePriOfferYn; // 개인정보 제3자 제공 동의
    String clauseSensiCollectYn; // 민감정보(생체인식정보) 수집 및 이용 동의
    String clauseSensiOfferYn; // 민감정보(생체인식정보) 조회 및 이용 / 3자 제공에 대한 동의
    String clauseJehuYn; // 개인정보 제 3자 제공 동의[제휴요금제]
    String clausePriTrustYn; // 개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의
    String othersTrnsAllAgreeYn; // 혜택 제공을 위한 제3자 제공 동의
    String othersTrnsKtAgreeYn; // 혜택 제공을 위한 제3자 제공 동의(kt)
    String othersAdReceiveAgreeYn; // 제3자 제공관련 광고 수신 동의
    String indvLocaPrvAgreeYn; // 개인정보 제 3자 제공 동의(선택동의)
    String ktCounselAgreeYn; // 인터넷 가입 상담을 위한 개인정보 제 3자 제공 동의
    String minorAgentAgrmYn; // 법정대리인동의서 서명
    String jrdclAgentNm; // 위임받는자
    String clauseConfidenceYn2; // 개인정보 수집 및 이용 동의
    String clausePriOfferYn2; // 개인정보 제3자 제공 동의(필수 동의)
    String disPrmtId;
    
    Long volumeMobileNoQnty;
    String volumeRepMobileNoYn;
    String volumeRepMobileNo;

    //법정대리인동의서/위임장_동의서
    String nwBlckAgrmYn; //유해정보 차단_네트워크 유해차단(무료)
    String blckAppDivCd; //유해정보 차단_네트워크 유해차단(무료)
    String appBlckAgrmYn; //유해정보 차단_청소년 유해정보 차단(엑스키퍼가드, 무료)
    String minorAgentNm; //법정대리인 성명
    String minorAgentRelTypeCd; //신청고객과의 관계
    String minorAgentRrn; //생년월일
    String minorAgentGender; //성별
    String minorAgentTelNo; //연락받을 전화번호

    //법정대리인동의서/위임장_위임장
    String minorDelegator; //위임하시는 분
    String minorAgent; //위임받는 분
    String minorAgentRelTypeCd2; //위임하는 분과의 관계
    String minorAgentRrn2; // 위임자 생년월일
    String minorAgentGender2;// 위임자 성별
    String minorAgentTelNo2;// 위임자 연락받을 전화번호

    String gdnFormReqDt; //신청일자

    //약정할인가 입신청서_상단
    String agentCd2; //대리점명/코드
    String telnum2; //연락처

    //약정할인 가입신청서_핸드폰 할인(베이직 코드)_핸드폰할인
    String enggSocCodeNm; //요금제 종류
    String modelEnggMnthCnt; //요금제로
    String modelSprt2; //단말지원금(공통지원금)

    //약정할인 가입신청서_핸드폰 할인(베이직 코드)_고객확인내용
    String modelEnggMnthCntDesc; //약정기간

    //약정할인 가입신청서_요금할인_요금할인
    String discountProg; //할인프로그램
    String enggMnthCnt; //요금제로
    //String modelMonthly; //약정기간
    String monthFeeDcVatAmt; //월 요금할인(VAT 포함)

    //약정할인 가입신청서_요금할인_고객확인내용
    String enggMnthCntDesc; //약정기간

    //약정할인 가입신청서_하단
    String enggReqDt; //신청일자

    //휴대폰 안심 보험(안드로이드)_상단
    String androidInsrProdCd; //보험코드

    //휴대폰 안심 보험(안드로이드)_하단
    String androidReqDt; //신청일자
    String androidCstmrNativeBirth; //신청인 생년월일

    //휴대폰 안심 보험(아이폰)_상단
    String iosInsrProdCd; //보험코드

    //휴대폰 안심 보험(아이폰)_하단
    String iosRegDt; //신청일자
    String iosReqDt; //신청일자
    String iosCstmrNativeBirth; //신청인 생년월일

    String saleManagerNm; // 로그인 사용자명
    String dcAmt; // 할인요금

    //서비스변경신청서_상단
    //agentCd	대리점명/코드
    //telnum	연락처
    //svcTgtCd	업무구분
    //etcSvcTgtCd	업무구분_기타

    //서비스변경신청서_핸드폰 대금
    //mobilePhoneAmt	핸드폰 대금
    //modelPrice	출고가
    //modelSprt	공시지원금
    //etcDcAmt	기타할인
    //modelInstallment	할부원금
    //modelMonthly	월 할부금(실구매가)
    //modelInstamt/ModelMonthly	월 할부금 개월
    //monInstFee	월 할부수수료
    //reqModelNm	핸드폰모델명
    //reqPhoneSn	일련번호
    //phoneMonthPayAmt	핸드폰 월 납부액(월 할부금 + 월 할부수수료)
    //subsidy	보조금
    //deviceDcAmt	단말 할인
    //penaltySupportAmt	위약금 대납
    //payBack	페이백
    //joinFeeSupportAmt	가입비 대납
    //etcSupportAmt	기타

    //서비스변경신청서_이동통신 요금
    //socCode	선택 요금상품
    //monthFee	월정액 요금
    //monthFee	월정액 요금
    //commMonthPayAmt	통신요금 월 납부액
    //baseMonthPayAmt	월 기본 납부액

    //서비스변경신청서_고객정보
    //cstmrNm	고객명(법인명)
    //cstmrNativeBirth	개인(생년월일)
    //gender	성별
    //cstmrPrivateBizNo	등록번호(법인/사업자/외국인)
    //cstmrMobileNo	연락처
    //cstmrTelNo	신청대상 전화번호
    //cstmrEmailAdr	e-mail 주소

    //서비스변경신청서_요금자동납부
    //othersPaymentYn	요금자동납부_구분
    //reqPayTypeCd	자동납부_구분
    //autoPayOrgNm	자동납부_은행/카드사명
    //autoPayAcctCardNo	자동납부_계좌/카드 번호
    //autoPayCardExpDt	자동납부_카드유효기간
    //autoChrgTypeCd	자동충전_구분
    //balReachAmt	충전잔여액_도달금액
    //balChrgAmt	충전잔여액 충전금액
    //remainDayChrgDt	충전잔여일_충전일
    //remainDayChrgAmt	충전잔여일 충전금액
    //othersPaymentNm	타인납부동의_납부고객명
    //othersPaymentRelTypeCd	타인납부동의_관계
    //othersPaymentRrn	타인납부동의_생년월일

    //서비스변경신청서_기타
    //telNoChg	번호 변경
    //noLinkSvc	번호연결서비스
    //reqUsimNm	USIM모델명
    //reqUsimSn	USIM일련번호
    //usimPriceTypeCd	USIM비용_구분
    //usimPrice	USIM비용_USIM비용
    //cstmrEmailAdr	e-mail 주소
    //cstmrAdr	요금청구 주소
    //socCode	요금 상품
    //addtionInfo	데이터/부가상품
    //reqWireTypeCd	무선데이터
    //pauseRel	일시정지/해제
    //longStopRsn	일시정지/해제_장기이용정지 사유
    //autoRelRsvYn	일시정지/해제_자동해제예약
    //reqModelNm	핸드폰 분실신고 권한_핸드폰모델명
    //reqPhoneSn	핸드폰 분실신고 권한_일련번호
    //lossAuthSuccYn	핸드폰 분실신고 권한_권한승계
    //lossAuthSuccNm	핸드폰 분실신고 권한_권한승계자 성명
    //lossAuthBirthDt	핸드폰 분실신고 권한_생년월일
    //lossAuthEtc	기타
    //cstmrJuridicalUserNm	법인폰 실사용자 등록_성명
    //cstmrJuridicalBirth	법인폰 실사용자 등록_주민번호

    //서비스변경신청서_하단
    //svcChgReqDt	신청일자
    //shopNm	신청서 접수점

    //해지신청서_상단
    //agentCd	대리점명/코드
    //telnum	연락처

    //해지신청서_고객정보
    //cstmrNm	고객명(법인명)
    //cstmrNativeBirth	개인(생년월일)
    //gender	성별
    //cstmrPrivateCname	(법인/사업자)등록번호
    //cstmrEmailAdr	연락받을 이메일
    //cancelMobileNo	해지신청번호
    //cstmrReceiveTelNo	연락받을 번호

    //해지신청서_해지신청내역
    //cancelUseCompanyCd	해지신청내용
    //payAmt	해지정산내용_사용요금
    //pnltAmt	해지정산내용_위약금
    //lastSumAmt	해지정산내용_최종정산요금
    //instamtMnthCnt	해지정산내용_핸드폰할부금/단말기_잔여 할부기간
    //instamtMnthAmt	해지정산내용_핸드폰할부금/단말기_잔여 할부금
    //instamtPayMthdCd	해지정산내용_핸드폰할부금/단말기_잔여 할부금 납부방법
    //receiveWayCd	해지정산내용_핸드폰할부금/단말기_잔여 할부내역 통보수단
    //cstmrAdr	해지정산내용_핸드폰할부금/단말기_잔여 할부내역 통보수단_우편 주소
    //cstmrEmailAdr	해지정산내용_핸드폰할부금/단말기_잔여 할부내역 통보수단_이메일 주소
    //reqTermSettleDeviceAgreeYn	해지정산내용_핸드폰할부금/단말기_동의
    //reqBankCd	해지정산내용_정산잔여금액 환불계좌_은행명
    //reqAccountNo	해지정산내용_정산잔여금액 환불계좌_계좌번호
    //benefitAgreeYn	혜택소멸

    //해지신청서_대리인위임장
    //delegatorCustNm	위임하는 고객_성명
    //msfRequestAgent	위임받는 고객_성명
    //minorAgentRelTypeCd	위임받는 고객_관계
    //minorAgentRrn	위임받는 고객_생년월일
    //minorAgentTelFn	위임받는 고객_연락처

    //서비스변경신청서_하단
    //svcChgReqDt	신청일자
    //agentCd	신청서 접수점

    //휴대폰 안심 보험(안드로이드)_하단
    //androidReqDt	신청일자
    //cstmrNativeBirth	신청인 생년월일

    //휴대폰 안심 보험(아이폰)_하단
    //iosReqDt	신청일자
    //cstmrNativeBirth	신청인 생년월일


    //스마트(MSF) 조회 컬럼과 eForm DATA SET 항목이 다른 경우에 대한 처리
    public void setCstmrNm(String cstmrNm) { //고객명
        this.cstmrName = cstmrNm;
    }

    public void setReqAdditionListNm(String reqAdditionListNm) { //부가서비스 명
        this.additionNm = reqAdditionListNm;
    }

    //public void setReqAdditionPrice(String reqAdditionPrice) { //부가서비스 합계금액
    //    this.addDcAmt = reqAdditionPrice;
    //}

    public void setCommonCode(MsfRequestEformRecord msfRequestEformRecord, CommonCodeGroups codeGroups) {
        if (msfRequestEformRecord != null) {
            if (msfRequestEformRecord.msfRequestBillReqVo() != null) {
                String reqPayTypeCd = StringUtil.NVL(msfRequestEformRecord.msfRequestBillReqVo().getReqPayTypeCd(), "");
                String reqBankCd = StringUtil.NVL(msfRequestEformRecord.msfRequestBillReqVo().getReqBankCd(), "");
                String reqCardCompanyCd = StringUtil.NVL(msfRequestEformRecord.msfRequestBillReqVo().getReqCardCompanyCd(), "");
                if ("D".equals(reqPayTypeCd) && !"".equals(reqBankCd)) {
                    // 은행명
                    this.setAutoPayOrgNm(codeGroups.getSimple("BNK", reqBankCd).title());
                } else if ("C".equals(reqPayTypeCd) && !"".equals(reqCardCompanyCd)) {
                    // 카드명
                    String cardNm = codeGroups.get("CRD")
                        .stream()
                        .filter(card -> card.detail() != null && reqCardCompanyCd.equals(card.detail().etcValue1()))
                        .findFirst()
                        .map(CommonCodeData::title)
                        .orElse(null);

                    this.setAutoPayOrgNm(cardNm);
                }
            }

            if (msfRequestEformRecord.msfRequestVo() != null
                && Constants.OPER_TYPE_MOVE_NUM.equals(StringUtil.NVL(msfRequestEformRecord.msfRequestVo().getOperTypeCd(), ""))
                && msfRequestEformRecord.msfRequestMoveVo() != null
            ) {
                String moveCompanyCd = StringUtil.NVL(msfRequestEformRecord.msfRequestMoveVo().getMoveCompanyCd(), "");
                if (!"SKT".equals(moveCompanyCd) && !"KT".equals(moveCompanyCd) && !"KTF".equals(moveCompanyCd) && !"LG U+".equals(moveCompanyCd) && !"LGT".equals(
                    moveCompanyCd)) {
                    // 변경 전 통신사 MVNO
                    this.setMoveCompany("ETC");
                    this.setMoveCompanyCd(codeGroups.getSimple("NSC", moveCompanyCd).title());
                } else {
                    if ("SKT".equals(moveCompanyCd)) {
                        this.setMoveCompany("SKT");
                    } else if ("KT".equals(moveCompanyCd) || "KTF".equals(moveCompanyCd)) {
                        this.setMoveCompany("KTF");
                    } else {
                        this.setMoveCompany("LGT");
                    }
                    this.setMoveCompanyCd(null);
                }
            }

            if (msfRequestEformRecord.msfRequestAgentVo() != null) {
                // 법정대리인동의서/위임장_동의서 신청고객과의 관계
                String minorAgentRelTypeCd = StringUtil.NVL(msfRequestEformRecord.msfRequestAgentVo().getMinorAgentRelTypeCd(), "");
                this.setMinorAgentRelTypeCd(codeGroups.getSimple("AGR", minorAgentRelTypeCd).title());
            }
            if (msfRequestEformRecord.msfRequestAgentVo() != null) {
                // 법정대리인동의서/위임장_위임장 신청고객과의 관계
                String jrdlclAgentRelTypeCd = StringUtil.NVL(msfRequestEformRecord.msfRequestAgentVo().getJrdclAgentRelTypeCd(), "");
                this.setMinorAgentRelTypeCd2(codeGroups.getSimple("RCP0021", jrdlclAgentRelTypeCd).title());
            }
            if (msfRequestEformRecord.msfRequestBillReqVo() != null) {
                // 타인납부동의 신청고객과의 관계
                String othersPaymentRelTypeCd = StringUtil.NVL(msfRequestEformRecord.msfRequestBillReqVo().getOthersPaymentRelTypeCd(), "");
                this.setOthersPaymentRelation(codeGroups.getSimple("AGR", othersPaymentRelTypeCd).title());
            }

            // 외국인 국적
            if (msfRequestEformRecord.msfRequestVo() != null
                && msfRequestEformRecord.msfRequestCstmrVo() != null
                && ("FN".equals(StringUtil.NVL(msfRequestEformRecord.msfRequestVo().getCstmrTypeCd(), ""))
                || "FM".equals(StringUtil.NVL(msfRequestEformRecord.msfRequestVo().getCstmrTypeCd(), "")))
            ) {
                String cstmrForeignerNation = StringUtil.NVL(msfRequestEformRecord.msfRequestCstmrVo().getCstmrForeignerNation(), "");
                this.setCstmrForeignerNation(codeGroups.getSimple("NATIONLIST", cstmrForeignerNation).title());
            }

        }
    }

    // 신청서 우측 상단 연락처
    public void setAgentInfo(AgencyCache agencyCache) {
        // 1. null 또는 빈 문자열 체크를 먼저 진행하고 정제하는 헬퍼 메서드 활용
        String telephone = cleanTelephone(agencyCache.telephone());
        String representativeTelephone = cleanTelephone(agencyCache.representativeTelephone());

        // 2. 우선순위에 따라 값이 있는 것을 선택 (둘 다 없으면 null 혹은 빈값)
        String finalTel = (telephone != null) ? telephone : representativeTelephone;

        this.setTelnum(finalTel);
        this.setTelnum2(finalTel);
        this.setTelnum3(finalTel);
    }

    // 전화번호 정제
    private String cleanTelephone(String phone) {
        if (StringUtil.isBlank(phone)) {
            return null;
        }

        String cleaned = phone.trim().replaceAll("[^0-9]", "");

        if (cleaned.isEmpty()) {
            return null;
        }

        boolean isRepresentativePrefix = cleaned.startsWith("15") || cleaned.startsWith("16") || cleaned.startsWith("18");
        boolean isValidLength = cleaned.length() >= 7 && cleaned.length() <= 9;

        if (isRepresentativePrefix && isValidLength) {
            return cleaned;
        }

        String zeroRemoved = cleaned.replaceAll("^0+", "");

        if (zeroRemoved.isEmpty()) {
            return null;
        }

        return "0" + zeroRemoved;
    }
}
