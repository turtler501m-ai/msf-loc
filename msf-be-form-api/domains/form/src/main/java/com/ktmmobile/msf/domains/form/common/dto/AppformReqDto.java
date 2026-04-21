package com.ktmmobile.msf.domains.form.common.dto;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.CSTMR_TYPE_NAME_MAP;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.OPER_TYPE_NAME_MAP;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.SERVICE_TYPE_LATER_PAY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ktds.crypto.exception.CryptoException;

import com.ktmmobile.msf.domains.form.common.constants.Constants;
//import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

public class AppformReqDto implements Serializable {

    @Deprecated
    private static final Logger logger = LoggerFactory.getLogger(AppformReqDto.class);

    private static final long serialVersionUID = 1L;

    /** 외부서식지 구분
     * market : 오픈마켓
     * pps : 선불온라인
     * homeshop : 홈쇼핑
     * */
    private String siteReferer;

    //MSP 조회 관련  reqPayType
    /** MSP 판매정책 조회코드값 plcyTypeCd => 온라인(직영):D ,오프라인(도매):W , 특수:S ,제휴 :A */
    private String plcyTypeCd;


    /** ???? 뭘까??? (외부서식지)
     * 01 :  단말 구매   할부 개월 리스트 표현
     * 02 :   USIM(유심)단독 구매:UU  24개월 고정
     *     */
    private String plcySctnCd;


    /** 할부 이자율 */
    private BigDecimal instRate;

    /** 상품명    */
    private String prdtNm = "";

    /** 제품구분코드(02:3G , 03:LTE , 5G )   */
    private String prdtSctnCd = "LTE";

    /** 중고폰 , 유심 상품코드 */
    private String prdtId;

    /** 가입신청_키 */
    private long requestKey;

    /** MCP_UPLOAD_PHONE_INFO _FK
     * 휴대폰 이미지 등록 EID 등록한 테이블 FK
     *  */
    private long uploadPhoneSrlNo = 0;


    /** 본인인증한 생년월일  */
    private String birthDate;

    /// MCP_REQUEST 관련
    /** 업무구분
     * HCN3 : 기기변경
     * HDN3 : 보상기변3G
     * MNP3 : 번호이동
     * NAC3 : 신규개통
     *  */
    private String operType = Constants.OPER_TYPE_MOVE_NUM;

    /** 고객구분
     *  NA :내국인
     *  NM : 내국인(미성년자)
     *  FN:외국인
     *  JP:법인
     *  PP:개인사업자
     * */
    private String cstmrType;
    /** 예약등록_코드 */
    private String resCode;
    /** 예약등록_메세지 */
    private String resMsg;
    /** 예약등록_예약번호 */
    private String resNo;
    /** 약관_개인정보_수집_동의 */
    private String clausePriCollectFlag;
    /** 약관_개인정보_제공_동의 */
    private String clausePriOfferFlag;
    /** 약관_고유식별정보_수집이용제공_동의 */
    private String clauseEssCollectFlag;
    /** 약관_개인정보_위탁_동의 */
    private String clausePriTrustFlag;
    /** 약관_신용정보_이용_동의 */
    private String clauseConfidenceFlag;
    /** 제휴_서비스동의 */
    private String clauseJehuFlag;
    /** M PPS 35 제약사항 동의 */
    private String clauseMpps35Flag;
    /** 개인(신용)정보 처리 및 보험가입 동의 */
    private String clauseFinanceFlag;
    /** 개인정보 수집 및 이용 동의 */
    private String personalInfoCollectAgree;
    /** 제 3자 제공 동의 */
    private String othersTrnsAgree;
    /** 약관_개인정보_광고전송_동의 */
    private String clausePriAdFlag;
    /** 혜택 제공을 위한 제3자 제공 동의 : KT */
    private String othersTrnsKtAgree;
    /** 제3자 제공관련 광고 수신 동의 */
    private String othersAdReceiveAgree;


    /** 온라인_인증방식
     * C : 카드 본인 인증
     * X : 범용
     * M : 모바일
     * P : 서식지 인증
     * */
    private String onlineAuthType;
    /** 온라인_인증정보 */
    private String onlineAuthInfo;
    /** 신청서_상태 */
    private String pstate;
    /** 가입진행_코드 */
    private String requestStateCode;
    /** 개통번호 */
    private String openNo;
    /** 파일01 */
    private String file01;
    /** 파일01_마스크 */
    private String file01Mask;
    /** 팩스사용여부 */
    private String faxyn;
    /** 팩스번호 */
    private String faxnum;
    /** 스캐너아이디 */
    private String scanId;
    /** 온라인오프라인구분 */
    private String onOffType;
    /** 등록아이피 */
    private String rip;
    /** 희망번호1 */
    private String reqWantNumber;
    /** 희망번호2 */
    private String reqWantNumber2;
    /** 희망번호3 */
    private String reqWantNumber3;

    /** 구매유형 단말
     * 구매:MM
     * USIM(유심)단독 구매:UU
     * */
    private String reqBuyType = Constants.REQ_BUY_TYPE_USIM;
    private String reqModelName;

    /** 색상 코드 */
    private String reqModelColor;
    private String reqModelColorNm;
    private String reqPhoneSn;
    private String reqUsimSn;

    /** 요금납부방법
     *  자동이체 : D
     *  신용카드 : C
     *  가상계좌 : VA
     *  자동충전(계좌이체) : AA
     * */
    private String reqPayType;

    /** 신청정보_부가서비스_목록 */
    private String reqAddition;
    /** 판매점 코드 */
    private String shopCd;
    private String appFormYn;
    private String contractNum;
    /** 기타/특약사항 */
    private String etcSpecial;
    /** USIM 모델명  */
    private String reqUsimName;
    /** 휴대폰결제 이용여부 */
    private String phonePayment;
    /** 부가서비스금액 */
    private long reqAdditionPrice;
    private String appFormXmlYn;
    private String spcCode;
    /** 채널점아이디_판매점코드 */
    private String cntpntShopId = Constants.CONTPNT_SHOP_ID_MSHOP;
    private String shopUsmId;
    private String memo;
    /** 녹취여부 */
    private String recYn;
    private String openMarketReferer;
    /** 사업자코드(I:KTIS, M:M모바일) */
    private String soCd;
    /** 전화상담 여부 */
    private String telAdvice = "N";


   /* TODO (필수)청소년 유해정보 네트워크차단 동의
    (필수)청소년 유해정보차단 APP 설치 동의*/


    /** 네트워크차단동의여부   청소년 유해정보 네트워크차단 동의  */
    private String nwBlckAgrmYn;
    /** 어플리케이션차단동의여부 청소년 유해정보차단 APP 설치 동의 */
    private String appBlckAgrmYn;
    /** APP구분코드
     * 1 : 스마트보안관(무료)
     * */
    private String appCd;
    /** 매니저_코드 */
    private String managerCode;
    /** 대리점_코드 */
    private String agentCode;
    /** 서비스구분
     * PP:선불
     * PO:후불
     * */
    private String serviceType = SERVICE_TYPE_LATER_PAY;
    /** 상품아이디 */
    private String prodId;

    /** 생성자아이디(로그인아이디) */
    private String cretId;
    /** 배너코드 */
    private String bannerCd;

    /** 단말기모델아이디_색상검색용*/
    private String sntyColorCd;
    /** 단품용량코드*/
    private String sntyCapacCd;


    //MCP_REQUEST_CSTMR     가입신청_고객정보
    /** 고객정보_외국인_국적 */
    private String cstmrForeignerNation;
    /** 고객정보_외국인_여권번호 */
    private String cstmrForeignerPn;
    /** 고객정보_외국인_외국인등록번호 */
    private String cstmrForeignerRrn;
    /** 고객정보_외국인_체류_시작날자 */
    private String cstmrForeignerSdate;
    /** 고객정보_외국인_체류_종료날자 */
    private String cstmrForeignerEdate;

    /** 고객정보_개인사업자_상호명 */
    private String cstmrPrivateCname;
    /** 고객정보_개인사업자_사업자등록번호 */
    private String cstmrPrivateNumber;
    /** 고객정보_법인사업자_법인명 */
    private String cstmrJuridicalCname;
    /** 고객정보_법인사업자_법인번호 */
    private String cstmrJuridicalRrn;
    /** 고객정보_법인사업자_사업자등록번호 */
    private String cstmrJuridicalNumber;
    /** 고객정보_전화번호_앞자리 */
    private String cstmrTelFn;
    /** 고객정보_전화번호_가운데자리 */
    private String cstmrTelMn;
    /** 고객정보_전화번호_끝자리 */
    private String cstmrTelRn;
    /** 고객정보_휴대폰번호_앞자리 */
    private String cstmrMobileFn;
    /** 고객정보_휴대폰번호_중간자리 */
    private String cstmrMobileMn;
    /** 고객정보_휴대폰번호_끝자리 */
    private String cstmrMobileRn;
    /** 고객정보_우편번호 */
    private String cstmrPost;
    /** 고객정보_주소 */
    private String cstmrAddr;
    /** 고객정보_상세주소 */
    private String cstmrAddrDtl;
    /** 고객정보_법정동주소 */
    private String cstmrAddrBjd;  //<<==============
    /** 고객정보_명세서종류 */
    private String cstmrBillSendCode;
    /** 고객정보_메일 */
    private String cstmrMail;
    /** 고객정보_메일수신_여부 */
    private String cstmrMailReceiveFlag;
    /** 고객정보_방문고객정보 */
    private String cstmrVisitType; //<<==============
    /** 고객정보_연락받을번호_앞자 */
    private String cstmrReceiveTelFn; //<<==============
    /** 고객정보_연락받을번호_중간자리 */
    private String cstmrReceiveTelNm; //<<==============
    /** 고객정보_연락받을번호_끝자리 */
    private String cstmrReceiveTelRn; //<<==============
    /** 고객정보_타인납부동의 */
    private String othersPaymentAg;//<<==============
    /** 고객정보_타인납부_고객명 */
    private String othersPaymentNm;//<<==============
    /** 고객정보_타인납부_주민번호 */
    private String othersPaymentRrn;//<<==============
    /** 고객정보_타인납부_명의자와의관계 */
    private String othersPaymentRelation;//<<==============
    /** 고객정보_타인납부_신청인 */
    private String othersPaymentRnm;//<<==============
    /** 제주항공ID */
    private String cstmrJejuId;

    /** 고객정보_고객명 */
    private String cstmrName = "";
    /** 고객정보_내국인_주민등록번호 */
    private String cstmrNativeRrn;
    /** 고객정보_내국인_주민등록번호 */
    private String desCstmrNativeRrn;

    private String cstmrForeignerDod;
    private String cstmrForeignerBirth;
    /** 본인인증조회동의 */
    private String selfInqryAgrmYn;
    /** 본인인증유형 */
    private String selfCertType;
    /** 발급/만료일자 */
    private String selfIssuExprDt;
    /** 발급번호 */
    private String selfIssuNum;
    /** 본인인증한  CI 정보 */
    private String selfCstmrCi;
    /** 본인인증한  DI 정보 */
    private String selfCstmrDi;
    /** 추천인 구분 코드 F001 01:추천 직원번호 02:제휴사 추천코드 */
    private String recommendFlagCd;
    /** 추천인정보 */
    private String recommendInfo;
    /** 상품 분류 (일반 :01, 0원 상품 :02) */
    private String prodType = "01";
    /** 중고렌탈 프로그램 서비스 이용에 대한 동의서 */
    private String clauseRentalService;
    /** 단말배상금 안내사항 */
    private String clauseRentalModelCp;
    /** 단말배상금(부분파손) 안내사항 */
    private String clauseRentalModelCpPr;


    //MCP_REQUEST_CSTMR     가입신청_고객정보


    //가입신청_대리인 테이블(MCP_REQUEST_AGENT)
    /** 미성년자_법정대리인_성명 */
    private String minorAgentName;
    /** 미성년자_법정대리인_주민등록번호 */
    private String minorAgentRrn;

    /** 미성년자_법정대리인_연락처_앞자리 */
    private String minorAgentTelFn;
    /** 미성년자_법정대리인_연락처_중간자리 */
    private String minorAgentTelMn;
    /** 미성년자_법정대리인_끝자리 */
    private String minorAgentTelRn;
    /** 미성년자_법정대리인_관계 */
    private String minorAgentRelation;


    /** 법인_대리인_성명 */
    private String jrdclAgentName;
    /** 법인_대리인_주민등록번호 */
    private String jrdclAgentRrn;
    /** 법인_대리인_연락처_앞자리 */
    private String jrdclAgentTelFn;
    /** 법인_대리인_연락처_중간자리 */
    private String jrdclAgentTelMn;
    /** 법인_대리인_연락처_끝자리 */
    private String jrdclAgentTelRn;
    /** 법정대리인_위임하는분 */
    private String entrustReqNm;
    /** 법정대리인_위임받는분 */
    private String entrustResNm;
    /** 법정대리인_위임관계 */
    private String entrustReqRelation;
    /** 법정대리인_위임_주민등록번호 */
    private String entrustResRrn;
    /** 법정대리인_위임_전화번호_앞자리 */
    private String entrustResTelFn;
    /** 법정대리인_위임_전화번호_중간자리 */
    private String entrustResTelMn;
    /** 법정대리인_위임_전화번호_뒷자리 */
    private String entrustResTelRn;
    /** 본인인증조회동의 */
    private String minorSelfInqryAgrmYn;
    /** 본인인증유형 */
    private String minorSelfCertType;
    /** 발급/만료일자 */
    private String minorSelfIssuExprDt;
    /** 발급번호 */
    private String minorSelfIssuNum;


    //번호이동정보 테이블(MCP_REQUEST_MOVE)
    /** 번호이동정보_변경전통신사 */
    private String moveCompany;
    /** 번호이동정보_이동할번호_앞자리 */
    private String moveMobileFn;
    /** 번호이동정보_이동할번호_중간자리 */
    private String moveMobileMn;
    /** 번호이동정보_이동할번호_끝자리 */
    private String moveMobileRn;
    /** 번호이동정보_인증유형 */
    private String moveAuthType;
    /** 번호이동정보_인증번호4자리 */
    private String moveAuthNumber;
    /** 번호이동정보_이달사용요금납부방법 */
    private String moveThismonthPayType;
    /** 번호이동정보_휴대폰_할부금상태 */
    private String moveAllotmentStat;
    /** 번호이동정보_미환급액요금상계동의_여부 */
    private String moveRefundAgreeFlag;
    /** 번호연결서비스 */
    private String reqGuideFlag;
    /** 번호연결서비스 전화번호 앞자리 */
    private String reqGuideFn;
    /** 번호연결서비스 전화번호 중간자리 */
    private String reqGuideRn;
    /** 번호연결서비스 전화번호 끝자리 */
    private String reqGuideMn;
    /** 법정 대리인 안내사항 및 동의 */
    private String minorAgentAgrmYn;


    //가입신청_선불충전 테이블(MCP_REQUEST_PAYMENT)
    private long reqAc02Amount;
    private String reqAcType;
    private long reqAc01Balance;
    private String reqAc02Day;
    private long reqAc01Amount;

    //가입신청_판매정보 테이블(MCP_REQUEST_SALEINFO)
    /** 지원금유형 (단말할인:KD , 요금할인:PM)
     * 단말할인:KD
     * 요금할인:PM
     * 심플할인:SM   (유심, 약정이 있을경우)
     * */
    private String sprtTp;
    /** 추가지원금(MAX) */
    private long maxDiscount3;
    /** 할인금액 */
    private long dcAmt;
    /** 추가할인금액 */
    private long addDcAmt;
    /**
     * 모델ID
     * prdtId 동일  휴대폰 : 대표 모델ID  유심 :
     *  */
    private String modelId;
    /** 단말할부개월수 */
    private String modelMonthly;
    /** 약정개월수 */
    private long enggMnthCnt;

    /** 단말할부원금 */
    private long modelInstallment;
    /** 판매정책 코드 */
    private String modelSalePolicyCode;
    /** 단말출고가_부가세 */
    private long modelPriceVat;
    /** 중고여부 */
    private String recycleYn;
    /** 제조사장려금 */
    private long modelDiscount1;
    /** 공시지원금 */
    private long modelDiscount2;

    /** 요금제코드 */
    private String socCode;
    /** 유심납부유형(B: 후청구, R:선납 , N:비구매) */
    private String usimPriceType;
    /** 가입비납부유형(R:완납, I:분납 , P:면제) */
    private String joinPriceType;
    private long joinPrice;
    /** 유심판매가 */
    private long usimPrice;
    /** 단말출고가 */
    private long modelPrice;
    /** 부가서비스 */
    private String addtionService;
    /** 부가서비스합계 */
    private String addtionServiceSum;
    /** 대리점보조금 */
    private long modelDiscount3;
    /** USIM비 납부방법
     * 1 면제
     * 2 일시납
     * 3 후납
     * 1:면제 2:일시납 3:후납
     */
    private String usimPayMthdCd = "3";
    /** 가입비 납부방법
     * 1 면제
     * 2 일시납
     * 3 3개월분납
     */
    private String joinPayMthdCd = "3";
    /** 실제단말할부원금(VAT포함) */
    private long realMdlInstamt;

    /** 결제수단코드  01 신용카드 , 02 실시간계좌이체 */
    private String settlWayCd;
    /** 결제금액 */
    private int settlAmt = 0;
    /** 결제 승인번호*/
    private String settlApvNo;
    /** 개인 통관 고유 부호-외산 휴대폰 */
    private String ownPersonalCode;
    /** 렌탈 기본료 금액 */
    private int rentalBaseAmt;
    /** 렌탈 기본료 할인 금액 */
    private int rentalBaseDcAmt;
    /** 단말기 배상 금액 */
    private int rentalModelCpAmt;


    //가입신청_배송정보 테이블(MCP_REQUSET_DLVRY)
    /** 배송정보_이름 */
    private String dlvryName;
    /** 배송정보_전화번호_앞자리 */
    private String dlvryTelFn;
    /** 배송정보_전화번호_중간자리 */
    private String dlvryTelMn;
    /** 배송정보_전화번호_뒷자리 */
    private String dlvryTelRn;
    /** 배송정보_휴대폰번호_앞자리 */
    private String dlvryMobileFn;
    /** 배송정보_휴대폰번호_중간자리 */
    private String dlvryMobileMn;
    /** 배송정보_휴대폰번호_뒷자리 */
    private String dlvryMobileRn;
    /** 배송정보_우편번호 */
    private String dlvryPost;
    /** 배송정보_주소 */
    private String dlvryAddr;
    /** 배송정보_상세주소 */
    private String dlvryAddrDtl;
    /** 배송정보_법정동주소 */
    private String dlvryAddrBjd;   // <===============
    /** 배송정보_요청사항 */
    private String dlvryMemo;


    //가입신청_청구정보 테이블(MCP_REQUEST_REQ)
    /** 신청정보_계좌이체_은행 */
    private String reqBank;
    /** 신청정보_계좌이체_예금주 */
    private String reqAccountName;  // <===============
    /** 신청정보_계좌이체_예금주_주민번호 */
    private String reqAccountRrn;  // <===============
    /** 신청정보_계좌이체_예금주와관계 */
    private String reqAccountRelation;  // <===============
    /** 신청정보_계좌이체_계좌번호 */
    private String reqAccountNumber;

    /** 신청정보_신용카드_명의자 */
    private String reqCardName;  // <===============
    /** 신청정보_신용카드_명의자_주민번호 */
    private String reqCardRrn; // <===============
    /** 신청정보_신용카드_카드사 */
    private String reqCardCompany;
    /** 신청정보_신용카드_번호 */
    private String reqCardNo;
    /** 신청정보_신용카드_유효년 */
    private String reqCardYy;
    /** 신청정보_신용카드_유효월 */
    private String reqCardMm;

    /** 신청정보_무선데이터_이용_타입
     * 이용 : AY
     * 차단 : AN
     * 데이터 로링 차단 : DN
     * ex) AY,DN */
    private String reqWireType;
    /** 신청정보_타인납부_여부 */
    private String reqPayOtherFlag;
    /** 신청정보_타인납부_전화번호_앞자리 */
    private String reqPayOtherTelFn;
    /** 신청정보_타인납부_전화번호_중간자리 */
    private String reqPayOtherTelMn;
    /** 신청정보_타인납부_전화번호_끝자리 */
    private String reqPayOtherTelRn;
    /** 배송유형 [01 택배,  02 바로배송] */
    private String dlvryType = "";
    /** 신청정보_바로 배송 요청 table [일련번호 ] */
    private long selfDlvryIdx = 0;


    //진행상태 테이블(MCP_REQUEST_STATE)
    /** 송장번호 */
    private String dlvryNo;
    /** 등록자아이디 */
    private String rid;
    /** 화면표시_여부 */
    private String viewFlag;


    private String tbCd;

    private String[] additionKeyList;

    private String[] additionSocCodeList;

    /** 약관동의 코드  */
    private String[] clauseCodeList;

    /** 약관동의 코드 버젼 */
    private String[] clauseDocverList;

    /** 대표모델아이디 */
    private String rprsPrdtId;

    /** CAP 이벤트 */
    private String ohvalue;

    /** 번호이동 납부방법코드 */
    private String osstPayType;

    /** 번호이동 납부주장일자 */
    private String osstPayDay;

    /** 오프라인 유심 접점 코드(유심 온라인 구매시 구분하기위한 필드) */
    private String orignalCntpntShopId;

    /** DB선택보험*/
    private String insrCd;
    private String clauseInsuranceFlag;
    private String text;
    private String value;
    /** 상품명(NMCP_PROD_BAS.PROD_NM) */
    private String prodNm;
    /** 단말보험 CD */
    private String insrProdCd;
    /** 단말보험가입동의 */
    private String clauseInsrProdFlag;
    /** 단말보험인증정보 */
    private String insrAuthInfo;

    /** 5g 커버리지 확인 및 가입 동의 */
    private String clause5gCoverageFlag;

    /** 위탁서식지 유심번호 추가 유심배송비는 유료로
     * 위탁온라인신청서 내 유심일련번호 입력 추가
     *  */
    private String market;

    /** 유심종류(RCP2035) 06 */
    private String usimKindsCd;

    /** 추천이 아이디 */
    private String commendId;

    /** 이전URL Type*/
    private String preUrlType;

    /** 요금제 조회 구분 0:추천, 1:전체 */
    private String indcOdrg;

    /** 결제거래번호 */
    private String settlTraNo;

    /** 수정자ID */
    private String rvisnId;

    /** 수정일시 */
    private String rvisnDttm;

    /** OPEN_REG_DATE */
    private String openReqDate;

    /** 등록일시 */
    private String sysRdate;

    /** 가입신청일 */
    private String reqInDay;

    /** 사업이관동의여부 */
    private String soTrnsAgrmYn;

    /** 판매점 */
    private String shopNm;

    /** 프로모션코드*/
    private String promotionCd;

    /** 실 판매점*/
    private String realShopNm;

    /** 사용자ID*/
    private String userId;

    /** 이름 */
    private String nm;

    /** PIN */
    private String pin;

    /** 모바일번호*/
    private String mobileNo;

    /** 임시저장 단계 */
    private String tmpStep;

    // 사은품 프로모션 코드
    private String[] prmtIdList;

    // 사은품 제품ID
    private String[] prdtIdList;

    // 사은품 제품Type
    private String[] prmtTypeList;

    /** 자급제폰 여부 */
    private String sesplsYn;

    /**'01' : 공시지원단말 , '02' : '중고폰' , '03' : '자급제'  */
    private String prodCtgType;

    /** */
    private String ktmReferer;

    /** 자급제 단말기 판매가 */
    private String hndsetSalePrice;
    /** 사용포인트 */
    private String usePoint;
    /** 사용포인트서비스계약번호 */
    private String usePointSvcCntrNo;
    /** 카드할인코드 */
    private String cardDcCd;
    /** 카드할인금액 */
    private String cardDcAmt;
    /** 카드할인유형 카드 할인 유형 PCT  WON */
    private String cardDcDivCd;

    /** 자급제 prodId*/
    private String sesplsProdId;

    /** 유심 구매 구분 */
    private String usimBuyDivCd;

    private String eid;
    private String imei1;
    private String imei2;
    private String esimPhoneId;//--ESIM_PHONE_ID;

    /** 모회선 계약번호*/
    private String prntsContractNo;


    /** 모회선 전화번호*/
    private String prntsCtn;

    /** 모회선 전화번호*/
    private String prntsBillNo;

    /** 자급제 보상 서비스 코드 */
    private String rwdProdCd;
    /** 자급제 보상 서비스 가입 동의 */
    private String clauseRwdFlag;
    /** 자급제 보상 서비스 인증 정보 */
    private String rwdAuthInfo;

    /** kt 인터넷 ID */
    private String ktInterSvcNo;

    /** STEP별 본인인증 메뉴 타입 */
    private String certMenuType;

    /** 요금제 구분 LTE, 5G */
    private String dataType;

    /** 평생할인 프로모션 ID */
    private String prmtId;

    /** 업무구분 */
    private String evntCd;

    private String reqSeq;
    private String resSeq;

    //    private NiceLogDto niceLogDto;  // 010셀프개통대상 해피콜 처리

    private String clausePartnerOfferFlag;  // 제휴사 제공 동의
    private String jehuProdType;            // 요금제 제휴처

    /** 대리점 셀프개통 정책 여부 */
    private String selfOpenYn;

    /** 오픈마켓유입아이디 */
    private String openMarketId;

    private String mpCode;

    /**제휴위탁온라인 유입여부*/
    private String jehuRefererYn;

    /** 사은품 - 이벤트 코드 */
    private String evntCdPrmt;
    private String giftUseYn;
    private String recoUseYn;

    private String fathTrgYn; //안면인증 대상여부
    private String clauseFathFlag; //안면인증 동의여부
    private String fathTransacId; //안면인증 트랜잭션 ID
    private String fathCmpltNtfyDt; //안면인증 완료일


    /** 안면인증정보_휴대폰번호_앞자리 */
    private String fathMobileFn;
    /** 안면인증정보_휴대폰번호_중간자리 */
    private String fathMobileMn;
    /** 안면인증정보_휴대폰번호_끝자리 */
    private String fathMobileRn;
    /** 명의 변경 안면인증 연락처*/
    private String fathTelNo;

    /** 연동코드*/
    private String appEventCd;

    private String cpntId;

    /** 아무나 SOLO 결합 신청 여부*/
    private String combineSoloType;

    /** 아무나 SOLO 결합 신청 동의 여부*/
    private String combineSoloFlag;

    private long requestKeyTemp; //신청키temp

    private String indvLocaPrvAgree; // 개인위치정보 제 3자 제공 동의

    private String ktCounselAgree;  //인터넷가입 상담을 위한 개인정보 제3자 제공동의
    private String newCcrSeq;       //인터넷가입상담 뉴키

    public AppformReqDto() {
    }


    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInsrCd() {
        return insrCd;
    }

    public void setInsrCd(String insrCd) {
        this.insrCd = insrCd;
    }

    public String getClauseInsuranceFlag() {
        return clauseInsuranceFlag;
    }

    public void setClauseInsuranceFlag(String clauseInsuranceFlag) {
        this.clauseInsuranceFlag = clauseInsuranceFlag;
    }

    public String getOrignalCntpntShopId() {
        return orignalCntpntShopId;
    }

    public void setOrignalCntpntShopId(String orignalCntpntShopId) {
        this.orignalCntpntShopId = orignalCntpntShopId;
    }

    public String getOhvalue() {
        return ohvalue;
    }

    public void setOhvalue(String ohvalue) {
        this.ohvalue = ohvalue;
    }

    public long getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperTypeSmall() {
        if (operType == null) {
            return "";
        } else {
            return operType;
        }
    }


    public String getOperTypeNm() {
        return OPER_TYPE_NAME_MAP.get(operType);
    }

    public String getCstmrType() {
        return cstmrType;
    }

    public String getCstmrTypeNm() {
        return CSTMR_TYPE_NAME_MAP.get(cstmrType);
    }

    public void setCstmrType(String cstmrType) {
        this.cstmrType = cstmrType;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getClausePriCollectFlag() {
        return clausePriCollectFlag;
    }

    public void setClausePriCollectFlag(String clausePriCollectFlag) {
        this.clausePriCollectFlag = clausePriCollectFlag;
    }

    public String getClausePriOfferFlag() {
        return clausePriOfferFlag;
    }

    public void setClausePriOfferFlag(String clausePriOfferFlag) {
        this.clausePriOfferFlag = clausePriOfferFlag;
    }

    public String getClauseEssCollectFlag() {
        return clauseEssCollectFlag;
    }

    public void setClauseEssCollectFlag(String clauseEssCollectFlag) {
        this.clauseEssCollectFlag = clauseEssCollectFlag;
    }

    public String getClausePriTrustFlag() {
        return clausePriTrustFlag;
    }

    public void setClausePriTrustFlag(String clausePriTrustFlag) {
        this.clausePriTrustFlag = clausePriTrustFlag;
    }

    public String getClausePriAdFlag() {
        return clausePriAdFlag;
    }

    public void setClausePriAdFlag(String clausePriAdFlag) {
        this.clausePriAdFlag = clausePriAdFlag;
    }

    public String getOthersTrnsKtAgree() {
        return othersTrnsKtAgree;
    }

    public void setOthersTrnsKtAgree(String othersTrnsKtAgree) {
        this.othersTrnsKtAgree = othersTrnsKtAgree;
    }

    public String getOthersAdReceiveAgree() {
        return othersAdReceiveAgree;
    }

    public void setOthersAdReceiveAgree(String othersAdReceiveAgree) {
        this.othersAdReceiveAgree = othersAdReceiveAgree;
    }

    public String getClauseConfidenceFlag() {
        return clauseConfidenceFlag;
    }

    public void setClauseConfidenceFlag(String clauseConfidenceFlag) {
        this.clauseConfidenceFlag = clauseConfidenceFlag;
    }

    public String getOnlineAuthType() {
        return onlineAuthType;
    }

    public void setOnlineAuthType(String onlineAuthType) {
        this.onlineAuthType = onlineAuthType;
    }

    public String getOnlineAuthInfo() {
        return onlineAuthInfo;
    }

    public void setOnlineAuthInfo(String onlineAuthInfo) {
        this.onlineAuthInfo = onlineAuthInfo;
    }

    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public String getRequestStateCode() {
        return requestStateCode;
    }

    public void setRequestStateCode(String requestStateCode) {
        this.requestStateCode = requestStateCode;
    }

    public String getOpenNo() {
        return openNo;
    }

    public void setOpenNo(String openNo) {
        this.openNo = openNo;
    }

    public String getFile01() {
        return file01;
    }

    public void setFile01(String file01) {
        this.file01 = file01;
    }

    public String getFile01Mask() {
        return file01Mask;
    }

    public void setFile01Mask(String file01Mask) {
        this.file01Mask = file01Mask;
    }

    public String getFaxyn() {
        return faxyn;
    }

    public void setFaxyn(String faxyn) {
        this.faxyn = faxyn;
    }

    public String getFaxnum() {
        return faxnum;
    }

    public void setFaxnum(String faxnum) {
        this.faxnum = faxnum;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getOnOffType() {
        return onOffType;
    }

    public void setOnOffType(String onOffType) {
        this.onOffType = onOffType;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }

    public String getReqWantNumber() {
        return reqWantNumber;
    }

    public void setReqWantNumber(String reqWantNumber) {
        this.reqWantNumber = reqWantNumber;
    }

    public String getReqWantNumber2() {
        return reqWantNumber2;
    }

    public void setReqWantNumber2(String reqWantNumber2) {
        this.reqWantNumber2 = reqWantNumber2;
    }

    public String getReqWantNumber3() {
        return reqWantNumber3;
    }

    public void setReqWantNumber3(String reqWantNumber3) {
        this.reqWantNumber3 = reqWantNumber3;
    }

    public String getReqBuyType() {
        return reqBuyType;
    }

    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }

    public String getReqBuyTypeNm() {
        if (reqBuyType == null || "".equals(reqBuyType)) {
            return "";
        } else if ("MM".equals(reqBuyType)) {
            return "단말구매";
        } else if ("UU".equals(reqBuyType)) {
            return "USIM(유심) 단독 구매";
        } else {
            return reqBuyType;
        }
    }

    public String getReqModelName() {
        return reqModelName;
    }

    public void setReqModelName(String reqModelName) {
        this.reqModelName = reqModelName;
    }

    public String getReqModelColor() {
        return reqModelColor;
    }

    public void setReqModelColor(String reqModelColor) {
        this.reqModelColor = reqModelColor;
    }


    public String getReqModelColorNm() {
        return reqModelColorNm;
    }

    public void setReqModelColorNm(String reqModelColorNm) {
        this.reqModelColorNm = reqModelColorNm;
    }

    public String getReqPhoneSn() {
        return reqPhoneSn;
    }

    public void setReqPhoneSn(String reqPhoneSn) {
        this.reqPhoneSn = reqPhoneSn;
    }

    public String getReqUsimSn() {
        return reqUsimSn;
    }

    public void setReqUsimSn(String reqUsimSn) {
        this.reqUsimSn = reqUsimSn;
    }

    public String getReqPayType() {
        return reqPayType;
    }

    public void setReqPayType(String reqPayType) {
        this.reqPayType = reqPayType;
    }

    public String getReqAddition() {
        return reqAddition;
    }

    public void setReqAddition(String reqAddition) {
        this.reqAddition = reqAddition;
    }

    public String getShopCd() {
        return shopCd;
    }

    public void setShopCd(String shopCd) {
        this.shopCd = shopCd;
    }

    public String getAppFormYn() {
        return appFormYn;
    }

    public void setAppFormYn(String appFormYn) {
        this.appFormYn = appFormYn;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getEtcSpecial() {
        return etcSpecial;
    }

    public void setEtcSpecial(String etcSpecial) {
        this.etcSpecial = etcSpecial;
    }

    public String getReqUsimName() {
        return reqUsimName;
    }

    public void setReqUsimName(String reqUsimName) {
        this.reqUsimName = reqUsimName;
    }

    public String getPhonePayment() {
        return phonePayment;
    }

    public void setPhonePayment(String phonePayment) {
        this.phonePayment = phonePayment;
    }

    public long getReqAdditionPrice() {
        return reqAdditionPrice;
    }

    public void setReqAdditionPrice(long reqAdditionPrice) {
        this.reqAdditionPrice = reqAdditionPrice;
    }

    public String getAppFormXmlYn() {
        return appFormXmlYn;
    }

    public void setAppFormXmlYn(String appFormXmlYn) {
        this.appFormXmlYn = appFormXmlYn;
    }

    public String getSpcCode() {
        return spcCode;
    }

    public void setSpcCode(String spcCode) {
        this.spcCode = spcCode;
    }

    public String getCntpntShopId() {
        return cntpntShopId;
    }

    public void setCntpntShopId(String cntpntShopId) {
        this.cntpntShopId = cntpntShopId;
    }

    public String getShopUsmId() {
        return shopUsmId;
    }

    public void setShopUsmId(String shopUsmId) {
        this.shopUsmId = shopUsmId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRecYn() {
        return recYn;
    }

    public void setRecYn(String recYn) {
        this.recYn = recYn;
    }

    public String getOpenMarketReferer() {
        return openMarketReferer;
    }

    public void setOpenMarketReferer(String openMarketReferer) {
        this.openMarketReferer = openMarketReferer;
    }

    public String getSoCd() {
        return soCd;
    }

    public void setSoCd(String soCd) {
        this.soCd = soCd;
    }

    public String getNwBlckAgrmYn() {
        return nwBlckAgrmYn;
    }

    public void setNwBlckAgrmYn(String nwBlckAgrmYn) {
        this.nwBlckAgrmYn = nwBlckAgrmYn;
    }

    public String getAppBlckAgrmYn() {
        return appBlckAgrmYn;
    }

    public void setAppBlckAgrmYn(String appBlckAgrmYn) {
        this.appBlckAgrmYn = appBlckAgrmYn;
    }

    public String getAppCd() {
        return appCd;
    }

    public void setAppCd(String appCd) {
        this.appCd = appCd;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceTypeNm() {
        if (serviceType == null || "".equals(serviceType)) {
            return "";
        } else if ("PP".equals(serviceType)) {
            return "선불";
        } else if ("PO".equals(serviceType)) {
            return "후불";
        } else {
            return serviceType;
        }
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCstmrForeignerNation() {
        return cstmrForeignerNation;
    }

    public void setCstmrForeignerNation(String cstmrForeignerNation) {
        this.cstmrForeignerNation = cstmrForeignerNation;
    }

    public String getCstmrForeignerPn() {
        return cstmrForeignerPn;
    }

    public String getCstmrForeignerPnDesc() {
        try {
            if (!StringUtils.hasText(cstmrForeignerPn)) {
                return "";
            } else {
                return EncryptUtil.ace256Dec(cstmrForeignerPn);
            }
        } catch (CryptoException e) {
            return cstmrForeignerPn;
        }
    }


    public void setCstmrForeignerPn(String cstmrForeignerPn) {
        if (!StringUtils.hasText(cstmrForeignerPn)) {
            this.cstmrForeignerPn = "";
        } else {

            //영문 숫자 일때.. 암호화
            if (Pattern.matches("^[0-9a-zA-Z]*$", cstmrForeignerPn)) {
                this.cstmrForeignerPn = EncryptUtil.ace256Enc(cstmrForeignerPn);
            } else {
                this.cstmrForeignerPn = cstmrForeignerPn;
            }

        }
    }


    public String getCstmrForeignerRrn() {
        return cstmrForeignerRrn;
    }

    public String getCstmrForeignerRrnDesc() {
        try {
            if (!StringUtils.hasText(cstmrForeignerRrn)) {
                return "";
            } else {
                return EncryptUtil.ace256Dec(cstmrForeignerRrn);
            }
        } catch (CryptoException e) {
            return cstmrForeignerRrn;
        }
    }

    public void setCstmrForeignerRrn(String cstmrForeignerRrn) {
        if (!StringUtils.hasText(cstmrForeignerRrn)) {
            this.cstmrForeignerRrn = "";
        } else {
            if ((cstmrForeignerRrn != null && cstmrForeignerRrn.matches("\\d+"))) {
                this.cstmrForeignerRrn = EncryptUtil.ace256Enc(cstmrForeignerRrn);
            } else {
                this.cstmrForeignerRrn = cstmrForeignerRrn;
            }
        }
    }

    public String getCstmrForeignerSdate() {
        return cstmrForeignerSdate;
    }

    public Date getDateCstmrForeignerSdate() {
        if (cstmrForeignerSdate != null) {
            try {
                return DateTimeUtil.check(cstmrForeignerSdate, "yyyyMMdd");
            } catch (ParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void setCstmrForeignerSdate(String cstmrForeignerSdate) {
        this.cstmrForeignerSdate = cstmrForeignerSdate;
    }

    public String getCstmrForeignerEdate() {
        return cstmrForeignerEdate;
    }

    public Date getDateCstmrForeignerEdate() {
        if (cstmrForeignerEdate != null) {
            try {
                return DateTimeUtil.check(cstmrForeignerEdate, "yyyyMMdd");
            } catch (ParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }


    public void setCstmrForeignerEdate(String cstmrForeignerEdate) {
        this.cstmrForeignerEdate = cstmrForeignerEdate;
    }

    public String getCstmrPrivateCname() {
        return cstmrPrivateCname;
    }

    public void setCstmrPrivateCname(String cstmrPrivateCname) {
        this.cstmrPrivateCname = cstmrPrivateCname;
    }

    public String getCstmrPrivateNumber() {
        return cstmrPrivateNumber;
    }

    public void setCstmrPrivateNumber(String cstmrPrivateNumber) {
        this.cstmrPrivateNumber = cstmrPrivateNumber;
    }

    public String getCstmrJuridicalCname() {
        return cstmrJuridicalCname;
    }

    public void setCstmrJuridicalCname(String cstmrJuridicalCname) {
        this.cstmrJuridicalCname = cstmrJuridicalCname;
    }

    public String getCstmrJuridicalRrn() {
        return cstmrJuridicalRrn;
    }

    public void setCstmrJuridicalRrn(String cstmrJuridicalRrn) {
        this.cstmrJuridicalRrn = cstmrJuridicalRrn;
    }

    public String getCstmrJuridicalNumber() {
        return cstmrJuridicalNumber;
    }

    public void setCstmrJuridicalNumber(String cstmrJuridicalNumber) {
        this.cstmrJuridicalNumber = cstmrJuridicalNumber;
    }

    public String getCstmrTelFn() {
        return cstmrTelFn;
    }

    public void setCstmrTelFn(String cstmrTelFn) {
        this.cstmrTelFn = cstmrTelFn;
    }

    public String getCstmrTelMn() {
        return cstmrTelMn;
    }

    public void setCstmrTelMn(String cstmrTelMn) {
        this.cstmrTelMn = cstmrTelMn;
    }

    public String getCstmrTelRn() {
        return cstmrTelRn;
    }

    public void setCstmrTelRn(String cstmrTelRn) {
        this.cstmrTelRn = cstmrTelRn;
    }

    public String getCstmrMobileFn() {
        return cstmrMobileFn;
    }

    public void setCstmrMobileFn(String cstmrMobileFn) {
        this.cstmrMobileFn = cstmrMobileFn;
    }

    public String getCstmrMobileMn() {
        return cstmrMobileMn;
    }

    public void setCstmrMobileMn(String cstmrMobileMn) {
        this.cstmrMobileMn = cstmrMobileMn;
    }

    public String getCstmrMobileRn() {
        return cstmrMobileRn;
    }

    public void setCstmrMobileRn(String cstmrMobileRn) {
        this.cstmrMobileRn = cstmrMobileRn;
    }

    public String getCstmrPost() {
        return cstmrPost;
    }

    public void setCstmrPost(String cstmrPost) {
        this.cstmrPost = cstmrPost;
    }

    public String getCstmrAddr() {
        return cstmrAddr;
    }

    public void setCstmrAddr(String cstmrAddr) {
        this.cstmrAddr = org.springframework.web.util.HtmlUtils.htmlUnescape(cstmrAddr);
    }

    public String getCstmrAddrDtl() {
        return cstmrAddrDtl;
    }

    public void setCstmrAddrDtl(String cstmrAddrDtl) {
        if (cstmrAddrDtl == null) {
            this.cstmrAddrDtl = "";
            return;
        }

        String strTemp = org.springframework.web.util.HtmlUtils.htmlUnescape(cstmrAddrDtl);

        //한글, 숫자, 영문, 공백, 기본 특수문자( . - , ( ) / ) 총 6종
        StringBuilder sb = new StringBuilder();

        strTemp.codePoints().forEach(cp -> {
            if (StringUtil.isAllowedAddr(cp)) {
                sb.appendCodePoint(cp);
            }
        });
        strTemp = sb.toString();
        this.cstmrAddrDtl = strTemp;
    }

    public String getCstmrAddrBjd() {
        return cstmrAddrBjd;
    }

    public void setCstmrAddrBjd(String cstmrAddrBjd) {
        this.cstmrAddrBjd = cstmrAddrBjd;
    }

    public String getCstmrBillSendCode() {
        return cstmrBillSendCode;
    }

    public void setCstmrBillSendCode(String cstmrBillSendCode) {
        this.cstmrBillSendCode = cstmrBillSendCode;
    }

    public String getCstmrMail() {
        return cstmrMail;
    }

    public void setCstmrMail(String cstmrMail) {
        this.cstmrMail = cstmrMail;
    }

    public String getCstmrMailReceiveFlag() {
        return cstmrMailReceiveFlag;
    }

    public void setCstmrMailReceiveFlag(String cstmrMailReceiveFlag) {
        this.cstmrMailReceiveFlag = cstmrMailReceiveFlag;
    }

    public String getCstmrVisitType() {
        return cstmrVisitType;
    }

    public void setCstmrVisitType(String cstmrVisitType) {
        this.cstmrVisitType = cstmrVisitType;
    }

    public String getCstmrReceiveTelFn() {
        return cstmrReceiveTelFn;
    }

    public void setCstmrReceiveTelFn(String cstmrReceiveTelFn) {
        this.cstmrReceiveTelFn = cstmrReceiveTelFn;
    }

    public String getCstmrReceiveTelNm() {
        return cstmrReceiveTelNm;
    }

    public void setCstmrReceiveTelNm(String cstmrReceiveTelNm) {
        this.cstmrReceiveTelNm = cstmrReceiveTelNm;
    }

    public String getCstmrReceiveTelRn() {
        return cstmrReceiveTelRn;
    }

    public void setCstmrReceiveTelRn(String cstmrReceiveTelRn) {
        this.cstmrReceiveTelRn = cstmrReceiveTelRn;
    }

    public String getOthersPaymentAg() {
        return othersPaymentAg;
    }

    public void setOthersPaymentAg(String othersPaymentAg) {
        this.othersPaymentAg = othersPaymentAg;
    }

    public String getOthersPaymentNm() {
        return othersPaymentNm;
    }

    public void setOthersPaymentNm(String othersPaymentNm) {
        this.othersPaymentNm = othersPaymentNm;
    }

    public String getOthersPaymentRrn() {
        return othersPaymentRrn;
    }

    public void setOthersPaymentRrn(String othersPaymentRrn) {
        if (!StringUtils.hasText(othersPaymentRrn)) {
            this.othersPaymentRrn = "";
        } else {
            this.othersPaymentRrn = EncryptUtil.ace256Enc(othersPaymentRrn);
        }
    }

    public String getOthersPaymentRelation() {
        return othersPaymentRelation;
    }

    public void setOthersPaymentRelation(String othersPaymentRelation) {
        this.othersPaymentRelation = othersPaymentRelation;
    }

    public String getOthersPaymentRnm() {
        return othersPaymentRnm;
    }

    public void setOthersPaymentRnm(String othersPaymentRnm) {
        this.othersPaymentRnm = othersPaymentRnm;
    }

    public String getCstmrName() {
        return cstmrName;
    }

    public void setCstmrName(String cstmrName) {
        this.cstmrName = StringUtil.substringByBytes(cstmrName, 0, 60);
    }

    public String getCstmrNativeRrn() {
        return cstmrNativeRrn;
    }

    public String getCstmrNativeRrnDesc() {
        try {
            if (!StringUtils.hasText(cstmrNativeRrn)) {
                return "";
            } else {
                return EncryptUtil.ace256Dec(cstmrNativeRrn);
            }
        } catch (CryptoException e) {
            return cstmrNativeRrn;
        }
    }


    public void setCstmrNativeRrn(String cstmrNativeRrn) {
        if (!StringUtils.hasText(cstmrNativeRrn)) {
            this.cstmrNativeRrn = "";
        } else {
            //암호화 문자를 다시 암호화하지 않는다.
            if ((cstmrNativeRrn != null && cstmrNativeRrn.matches("\\d+"))) {
                this.cstmrNativeRrn = EncryptUtil.ace256Enc(cstmrNativeRrn);
            } else {
                this.cstmrNativeRrn = cstmrNativeRrn;
            }

        }
    }

    public String getDesCstmrNativeRrn() {
        return desCstmrNativeRrn;
    }

    public void setDesCstmrNativeRrn(String desCstmrNativeRrn) {
        this.desCstmrNativeRrn = desCstmrNativeRrn;
    }

    public String getCstmrForeignerDod() {
        return cstmrForeignerDod;
    }

    public void setCstmrForeignerDod(String cstmrForeignerDod) {
        this.cstmrForeignerDod = cstmrForeignerDod;
    }

    public String getCstmrForeignerBirth() {
        return cstmrForeignerBirth;
    }

    public void setCstmrForeignerBirth(String cstmrForeignerBirth) {
        this.cstmrForeignerBirth = cstmrForeignerBirth;
    }

    public String getSelfInqryAgrmYn() {
        return selfInqryAgrmYn;
    }

    public void setSelfInqryAgrmYn(String selfInqryAgrmYn) {
        this.selfInqryAgrmYn = selfInqryAgrmYn;
    }

    public String getSelfCertType() {
        return selfCertType;
    }

    public void setSelfCertType(String selfCertType) {
        this.selfCertType = selfCertType;
    }

    public String getSelfIssuExprDt() {
        return selfIssuExprDt;
    }

    public void setSelfIssuExprDt(String selfIssuExprDt) {
        this.selfIssuExprDt = selfIssuExprDt;
    }

    public Date getSelfIssuExprDate() {
        if (selfIssuExprDt != null) {
            try {
                return DateTimeUtil.check(selfIssuExprDt, "yyyyMMdd");
            } catch (ParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getSelfIssuNum() {
        return selfIssuNum;
    }

    public void setSelfIssuNum(String selfIssuNum) {
        if (!StringUtils.hasText(selfIssuNum)) {
            this.selfIssuNum = "";
        } else {
            if ((selfIssuNum != null && selfIssuNum.matches("\\d+"))) {
                this.selfIssuNum = EncryptUtil.ace256Enc(selfIssuNum);
            } else {
                this.selfIssuNum = selfIssuNum;
            }
        }
    }

    public void setSelfIssuNumEnc(String selfIssuNum) {
        if (!StringUtils.hasText(selfIssuNum)) {
            this.selfIssuNum = "";
        } else {
            this.selfIssuNum = EncryptUtil.ace256Enc(selfIssuNum);
        }
    }

    public String getSelfIssuNumDesc() {
        try {
            if (!StringUtils.hasText(selfIssuNum)) {
                return "";
            } else {
                return EncryptUtil.ace256Dec(selfIssuNum);
            }
        } catch (CryptoException e) {
            return selfIssuNum;
        }
    }

    public String getMinorAgentName() {
        return minorAgentName;
    }

    public void setMinorAgentName(String minorAgentName) {
        this.minorAgentName = StringUtil.substringByBytes(minorAgentName, 0, 60);
    }

    public String getMinorAgentRrn() {
        return minorAgentRrn;
    }

    public String getMinorAgentRrnDesc() {
        try {
            if (!StringUtils.hasText(minorAgentRrn)) {
                return "";
            } else {
                return EncryptUtil.ace256Dec(minorAgentRrn);
            }
        } catch (CryptoException e) {
            return minorAgentRrn;
        }
    }

    public void setMinorAgentRrn(String minorAgentRrn) {
        if (!StringUtils.hasText(minorAgentRrn)) {
            this.minorAgentRrn = "";
        } else {
            if ((minorAgentRrn != null && minorAgentRrn.matches("\\d+"))) {
                this.minorAgentRrn = EncryptUtil.ace256Enc(minorAgentRrn);
            } else {
                this.minorAgentRrn = minorAgentRrn;
            }
        }
    }

    public String getMinorAgentTelFn() {
        return minorAgentTelFn;
    }

    public void setMinorAgentTelFn(String minorAgentTelFn) {
        this.minorAgentTelFn = minorAgentTelFn;
    }

    public String getMinorAgentTelMn() {
        return minorAgentTelMn;
    }

    public void setMinorAgentTelMn(String minorAgentTelMn) {
        this.minorAgentTelMn = minorAgentTelMn;
    }

    public String getMinorAgentTelRn() {
        return minorAgentTelRn;
    }

    public void setMinorAgentTelRn(String minorAgentTelRn) {
        this.minorAgentTelRn = minorAgentTelRn;
    }

    public String getMinorAgentRelation() {
        return minorAgentRelation;
    }

    public void setMinorAgentRelation(String minorAgentRelation) {
        this.minorAgentRelation = minorAgentRelation;
    }

    public String getJrdclAgentName() {
        return jrdclAgentName;
    }

    public void setJrdclAgentName(String jrdclAgentName) {
        this.jrdclAgentName = jrdclAgentName;
    }

    public String getJrdclAgentRrn() {
        return jrdclAgentRrn;
    }

    public void setJrdclAgentRrn(String jrdclAgentRrn) {
        if (!StringUtils.hasText(jrdclAgentRrn)) {
            this.jrdclAgentRrn = "";
        } else {
            this.jrdclAgentRrn = EncryptUtil.ace256Enc(jrdclAgentRrn);
        }
    }

    public String getJrdclAgentTelFn() {
        return jrdclAgentTelFn;
    }

    public void setJrdclAgentTelFn(String jrdclAgentTelFn) {
        this.jrdclAgentTelFn = jrdclAgentTelFn;
    }

    public String getJrdclAgentTelMn() {
        return jrdclAgentTelMn;
    }

    public void setJrdclAgentTelMn(String jrdclAgentTelMn) {
        this.jrdclAgentTelMn = jrdclAgentTelMn;
    }

    public String getJrdclAgentTelRn() {
        return jrdclAgentTelRn;
    }

    public void setJrdclAgentTelRn(String jrdclAgentTelRn) {
        this.jrdclAgentTelRn = jrdclAgentTelRn;
    }

    public String getEntrustReqNm() {
        return entrustReqNm;
    }

    public void setEntrustReqNm(String entrustReqNm) {
        this.entrustReqNm = entrustReqNm;
    }

    public String getEntrustResNm() {
        return entrustResNm;
    }

    public void setEntrustResNm(String entrustResNm) {
        this.entrustResNm = entrustResNm;
    }

    public String getEntrustReqRelation() {
        return entrustReqRelation;
    }

    public void setEntrustReqRelation(String entrustReqRelation) {
        this.entrustReqRelation = entrustReqRelation;
    }

    public String getEntrustResRrn() {
        return entrustResRrn;
    }

    public void setEntrustResRrn(String entrustResRrn) {
        if (!StringUtils.hasText(entrustResRrn)) {
            this.entrustResRrn = "";
        } else {
            this.entrustResRrn = EncryptUtil.ace256Enc(entrustResRrn);
        }
    }

    public String getEntrustResTelFn() {
        return entrustResTelFn;
    }

    public void setEntrustResTelFn(String entrustResTelFn) {
        this.entrustResTelFn = entrustResTelFn;
    }

    public String getEntrustResTelMn() {
        return entrustResTelMn;
    }

    public void setEntrustResTelMn(String entrustResTelMn) {
        this.entrustResTelMn = entrustResTelMn;
    }

    public String getEntrustResTelRn() {
        return entrustResTelRn;
    }

    public void setEntrustResTelRn(String entrustResTelRn) {
        this.entrustResTelRn = entrustResTelRn;
    }

    public String getMinorSelfInqryAgrmYn() {
        return minorSelfInqryAgrmYn;
    }

    public void setMinorSelfInqryAgrmYn(String minorSelfInqryAgrmYn) {
        this.minorSelfInqryAgrmYn = minorSelfInqryAgrmYn;
    }

    public String getMinorSelfCertType() {
        return minorSelfCertType;
    }

    public void setMinorSelfCertType(String minorSelfCertType) {
        this.minorSelfCertType = minorSelfCertType;
    }

    public String getMinorSelfIssuExprDt() {
        return minorSelfIssuExprDt;
    }

    public void setMinorSelfIssuExprDt(String minorSelfIssuExprDt) {
        this.minorSelfIssuExprDt = minorSelfIssuExprDt;
    }

    public String getMinorSelfIssuNum() {
        return minorSelfIssuNum;
    }

    public void setMinorSelfIssuNum(String minorSelfIssuNum) {
        if (!StringUtils.hasText(minorSelfIssuNum)) {
            this.minorSelfIssuNum = "";
        } else {
            if ((minorSelfIssuNum != null && minorSelfIssuNum.matches("\\d+"))) {
                this.minorSelfIssuNum = EncryptUtil.ace256Enc(minorSelfIssuNum);
            } else {
                this.minorSelfIssuNum = minorSelfIssuNum;
            }

        }
    }

    public String getMoveCompany() {
        return moveCompany;
    }

    public void setMoveCompany(String moveCompany) {
        this.moveCompany = moveCompany;
    }

    public String getMoveMobileFn() {
        return moveMobileFn;
    }

    public void setMoveMobileFn(String moveMobileFn) {
        this.moveMobileFn = moveMobileFn;
    }

    public String getMoveMobileMn() {
        return moveMobileMn;
    }

    public void setMoveMobileMn(String moveMobileMn) {
        this.moveMobileMn = moveMobileMn;
    }

    public String getMoveMobileRn() {
        return moveMobileRn;
    }

    public void setMoveMobileRn(String moveMobileRn) {
        this.moveMobileRn = moveMobileRn;
    }

    public String getMoveAuthType() {
        return moveAuthType;
    }

    public void setMoveAuthType(String moveAuthType) {
        this.moveAuthType = moveAuthType;
    }

    public String getMoveAuthNumber() {
        return moveAuthNumber;
    }

    public void setMoveAuthNumber(String moveAuthNumber) {
        this.moveAuthNumber = moveAuthNumber;
    }

    public String getMoveThismonthPayType() {
        return moveThismonthPayType;
    }

    public void setMoveThismonthPayType(String moveThismonthPayType) {
        this.moveThismonthPayType = moveThismonthPayType;
    }

    public String getMoveAllotmentStat() {
        return moveAllotmentStat;
    }

    public void setMoveAllotmentStat(String moveAllotmentStat) {
        this.moveAllotmentStat = moveAllotmentStat;
    }

    public String getMoveRefundAgreeFlag() {
        return moveRefundAgreeFlag;
    }

    public void setMoveRefundAgreeFlag(String moveRefundAgreeFlag) {
        this.moveRefundAgreeFlag = moveRefundAgreeFlag;
    }

    public String getReqGuideFlag() {
        return reqGuideFlag;
    }

    public void setReqGuideFlag(String reqGuideFlag) {
        this.reqGuideFlag = reqGuideFlag;
    }

    public String getReqGuideFn() {
        return reqGuideFn;
    }

    public void setReqGuideFn(String reqGuideFn) {
        this.reqGuideFn = reqGuideFn;
    }

    public String getReqGuideRn() {
        return reqGuideRn;
    }

    public void setReqGuideRn(String reqGuideRn) {
        this.reqGuideRn = reqGuideRn;
    }

    public String getReqGuideMn() {
        return reqGuideMn;
    }

    public void setReqGuideMn(String reqGuideMn) {
        this.reqGuideMn = reqGuideMn;
    }

    public long getReqAc02Amount() {
        return reqAc02Amount;
    }

    public void setReqAc02Amount(long reqAc02Amount) {
        this.reqAc02Amount = reqAc02Amount;
    }

    public String getReqAcType() {
        return reqAcType;
    }

    public void setReqAcType(String reqAcType) {
        this.reqAcType = reqAcType;
    }

    public long getReqAc01Balance() {
        return reqAc01Balance;
    }

    public void setReqAc01Balance(long reqAc01Balance) {
        this.reqAc01Balance = reqAc01Balance;
    }

    public String getReqAc02Day() {
        return reqAc02Day;
    }

    public void setReqAc02Day(String reqAc02Day) {
        this.reqAc02Day = reqAc02Day;
    }

    public long getReqAc01Amount() {
        return reqAc01Amount;
    }

    public void setReqAc01Amount(long reqAc01Amount) {
        this.reqAc01Amount = reqAc01Amount;
    }

    public String getModelId() {
        if (modelId == null) {
            return "";
        }
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelMonthly() {
        return modelMonthly;
    }

    public void setModelMonthly(String modelMonthly) {
        this.modelMonthly = modelMonthly;
    }

    public long getModelInstallment() {
        return modelInstallment;
    }

    public void setModelInstallment(long modelInstallment) {
        this.modelInstallment = modelInstallment;
    }

    public String getModelSalePolicyCode() {
        return modelSalePolicyCode;
    }

    public void setModelSalePolicyCode(String modelSalePolicyCode) {
        this.modelSalePolicyCode = modelSalePolicyCode;
    }

    public long getModelPriceVat() {
        return modelPriceVat;
    }

    public void setModelPriceVat(long modelPriceVat) {
        this.modelPriceVat = modelPriceVat;
    }

    public String getRecycleYn() {
        return recycleYn;
    }

    public void setRecycleYn(String recycleYn) {
        this.recycleYn = recycleYn;
    }

    public long getModelDiscount1() {
        return modelDiscount1;
    }

    public void setModelDiscount1(long modelDiscount1) {
        this.modelDiscount1 = modelDiscount1;
    }

    public long getModelDiscount2() {
        return modelDiscount2;
    }

    public void setModelDiscount2(long modelDiscount2) {
        this.modelDiscount2 = modelDiscount2;
    }

    public long getEnggMnthCnt() {
        return enggMnthCnt;
    }

    public void setEnggMnthCnt(long enggMnthCnt) {
        this.enggMnthCnt = enggMnthCnt;
    }

    public String getSocCode() {
        return socCode;
    }

    public void setSocCode(String socCode) {
        this.socCode = socCode;
    }

    public String getUsimPriceType() {
        return usimPriceType;
    }

    public void setUsimPriceType(String usimPriceType) {
        this.usimPriceType = usimPriceType;
    }

    public String getJoinPriceType() {
        return joinPriceType;
    }

    public void setJoinPriceType(String joinPriceType) {
        this.joinPriceType = joinPriceType;
    }

    public long getJoinPrice() {
        return joinPrice;
    }

    public void setJoinPrice(long joinPrice) {
        this.joinPrice = joinPrice;
    }

    public long getUsimPrice() {
        return usimPrice;
    }

    public void setUsimPrice(long usimPrice) {
        this.usimPrice = usimPrice;
    }

    public long getModelPrice() {
        return modelPrice;
    }

    public void setModelPrice(long modelPrice) {
        this.modelPrice = modelPrice;
    }

    public String getAddtionService() {
        return addtionService;
    }

    public void setAddtionService(String addtionService) {
        this.addtionService = addtionService;
    }

    public String getAddtionServiceSum() {
        return addtionServiceSum;
    }

    public void setAddtionServiceSum(String addtionServiceSum) {
        this.addtionServiceSum = addtionServiceSum;
    }

    public long getModelDiscount3() {
        return modelDiscount3;
    }

    public void setModelDiscount3(long modelDiscount3) {
        this.modelDiscount3 = modelDiscount3;
    }

    public String getUsimPayMthdCd() {
        return usimPayMthdCd;
    }

    public void setUsimPayMthdCd(String usimPayMthdCd) {
        this.usimPayMthdCd = usimPayMthdCd;
    }

    public String getJoinPayMthdCd() {
        return joinPayMthdCd;
    }

    public void setJoinPayMthdCd(String joinPayMthdCd) {
        this.joinPayMthdCd = joinPayMthdCd;
    }

    public long getRealMdlInstamt() {
        return realMdlInstamt;
    }

    public void setRealMdlInstamt(long realMdlInstamt) {
        this.realMdlInstamt = realMdlInstamt;
    }

    public String getDlvryName() {
        return dlvryName;
    }

    public void setDlvryName(String dlvryName) {
        this.dlvryName = StringUtil.substringByBytes(dlvryName, 0, 60);
    }

    public String getDlvryTelFn() {
        return dlvryTelFn;
    }

    public void setDlvryTelFn(String dlvryTelFn) {
        this.dlvryTelFn = dlvryTelFn;
    }

    public String getDlvryTelMn() {
        return dlvryTelMn;
    }

    public void setDlvryTelMn(String dlvryTelMn) {
        this.dlvryTelMn = dlvryTelMn;
    }

    public String getDlvryTelRn() {
        return dlvryTelRn;
    }

    public void setDlvryTelRn(String dlvryTelRn) {
        this.dlvryTelRn = dlvryTelRn;
    }

    public String getDlvryMobileFn() {
        return dlvryMobileFn;
    }

    public void setDlvryMobileFn(String dlvryMobileFn) {
        this.dlvryMobileFn = dlvryMobileFn;
    }

    public String getDlvryMobileMn() {
        return dlvryMobileMn;
    }

    public void setDlvryMobileMn(String dlvryMobileMn) {
        this.dlvryMobileMn = dlvryMobileMn;
    }

    public String getDlvryMobileRn() {
        return dlvryMobileRn;
    }

    public void setDlvryMobileRn(String dlvryMobileRn) {
        this.dlvryMobileRn = dlvryMobileRn;
    }

    public String getDlvryPost() {
        return dlvryPost;
    }

    public void setDlvryPost(String dlvryPost) {
        this.dlvryPost = dlvryPost;
    }

    public String getDlvryAddr() {
        return dlvryAddr;
    }

    public void setDlvryAddr(String dlvryAddr) {
        this.dlvryAddr = org.springframework.web.util.HtmlUtils.htmlUnescape(dlvryAddr);
    }

    public String getDlvryAddrDtl() {
        return dlvryAddrDtl;
    }

    public void setDlvryAddrDtl(String dlvryAddrDtl) {
        if (dlvryAddrDtl == null) {
            this.dlvryAddrDtl = "";
            return;
        }

        String strTemp = org.springframework.web.util.HtmlUtils.htmlUnescape(dlvryAddrDtl);
        //한글, 숫자, 영문, 공백, 기본 특수문자( . - , ( ) / ) 총 6종
        StringBuilder sb = new StringBuilder();
        strTemp.codePoints().forEach(cp -> {
            if (StringUtil.isAllowedAddr(cp)) {
                sb.appendCodePoint(cp);
            }
        });
        strTemp = sb.toString();
        this.dlvryAddrDtl = strTemp;
    }

    public String getDlvryAddrBjd() {
        return dlvryAddrBjd;
    }

    public void setDlvryAddrBjd(String dlvryAddrBjd) {
        this.dlvryAddrBjd = dlvryAddrBjd;
    }

    public String getDlvryMemo() {
        return dlvryMemo;
    }

    public void setDlvryMemo(String dlvryMemo) {
        this.dlvryMemo = dlvryMemo;
    }

    public String getReqBank() {
        return reqBank;
    }

    public void setReqBank(String reqBank) {
        this.reqBank = reqBank;
    }

    public String getReqAccountName() {
        return reqAccountName;
    }

    public void setReqAccountName(String reqAccountName) {
        this.reqAccountName = StringUtil.substringByBytes(reqAccountName, 0, 60);
    }

    public String getReqAccountRrn() {
        return reqAccountRrn;
    }

    public void setReqAccountRrn(String reqAccountRrn) {
        if (!StringUtils.hasText(reqAccountRrn)) {
            this.reqAccountRrn = "";
        } else {
            if ((reqAccountRrn != null && reqAccountRrn.matches("\\d+"))) {
                this.reqAccountRrn = EncryptUtil.ace256Enc(reqAccountRrn);
            } else {
                try {
                    this.reqAccountRrn = EncryptUtil.ace256Dec(reqAccountRrn);
                } catch (CryptoException e) {
                    this.reqAccountRrn = reqAccountRrn;
                }
            }

        }
    }

    public String getReqAccountRelation() {
        return reqAccountRelation;
    }

    public void setReqAccountRelation(String reqAccountRelation) {
        this.reqAccountRelation = reqAccountRelation;
    }

    public String getReqAccountNumber() {
        return reqAccountNumber;
    }

    public void setReqAccountNumber(String reqAccountNumber) {
        if (!StringUtils.hasText(reqAccountNumber)) {
            this.reqAccountNumber = "";
        } else {
            if ((reqAccountNumber != null && reqAccountNumber.matches("\\d+"))) {
                this.reqAccountNumber = EncryptUtil.ace256Enc(reqAccountNumber);
            } else {
                try {
                    this.reqAccountNumber = EncryptUtil.ace256Dec(reqAccountNumber);
                } catch (CryptoException e) {
                    this.reqAccountNumber = reqAccountNumber;
                }
            }
        }
    }

    public String getReqCardName() {
        return reqCardName;
    }

    public void setReqCardName(String reqCardName) {
        this.reqCardName = StringUtil.substringByBytes(reqCardName, 0, 60);
    }

    public String getReqCardRrn() {
        return reqCardRrn;
    }

    public void setReqCardRrn(String reqCardRrn) {
        if (!StringUtils.hasText(reqCardRrn)) {
            this.reqCardRrn = "";
        } else {
            if ((reqCardRrn != null && reqCardRrn.matches("\\d+"))) {
                this.reqCardRrn = EncryptUtil.ace256Enc(reqCardRrn);
            } else {
                this.reqCardRrn = reqCardRrn;

            }

        }
    }

    public String getReqCardCompany() {
        return reqCardCompany;
    }

    public void setReqCardCompany(String reqCardCompany) {
        this.reqCardCompany = reqCardCompany;
    }

    public String getReqCardNo() {
        return reqCardNo;
    }

    public void setReqCardNo(String reqCardNo) {
        if (!StringUtils.hasText(reqCardNo)) {
            this.reqCardNo = "";
        } else {
            if ((reqCardNo != null && reqCardNo.matches("\\d+"))) {
                this.reqCardNo = EncryptUtil.ace256Enc(reqCardNo);
            } else {
                try {
                    this.reqCardNo = EncryptUtil.ace256Dec(reqCardNo);
                } catch (CryptoException e) {
                    this.reqCardNo = reqCardNo;
                }
            }
        }
    }

    public String getReqCardYy() {
        return reqCardYy;
    }

    public void setReqCardYy(String reqCardYy) {
        this.reqCardYy = reqCardYy;
    }

    public String getReqCardMm() {
        return reqCardMm;
    }

    public void setReqCardMm(String reqCardMm) {
        this.reqCardMm = reqCardMm;
    }

    public String getReqWireType() {
        return reqWireType;
    }

    public void setReqWireType(String reqWireType) {
        this.reqWireType = reqWireType;
    }

    public String getReqPayOtherFlag() {
        return reqPayOtherFlag;
    }

    public void setReqPayOtherFlag(String reqPayOtherFlag) {
        this.reqPayOtherFlag = reqPayOtherFlag;
    }

    public String getReqPayOtherTelFn() {
        return reqPayOtherTelFn;
    }

    public void setReqPayOtherTelFn(String reqPayOtherTelFn) {
        this.reqPayOtherTelFn = reqPayOtherTelFn;
    }

    public String getReqPayOtherTelMn() {
        return reqPayOtherTelMn;
    }

    public void setReqPayOtherTelMn(String reqPayOtherTelMn) {
        this.reqPayOtherTelMn = reqPayOtherTelMn;
    }

    public String getReqPayOtherTelRn() {
        return reqPayOtherTelRn;
    }

    public void setReqPayOtherTelRn(String reqPayOtherTelRn) {
        this.reqPayOtherTelRn = reqPayOtherTelRn;
    }

    public String getDlvryNo() {
        return dlvryNo;
    }

    public void setDlvryNo(String dlvryNo) {
        this.dlvryNo = dlvryNo;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getViewFlag() {
        return viewFlag;
    }

    public void setViewFlag(String viewFlag) {
        this.viewFlag = viewFlag;
    }

    public String getTbCd() {
        return tbCd;
    }

    public void setTbCd(String tbCd) {
        this.tbCd = tbCd;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String[] getAdditionKeyList() {
        return additionKeyList;
    }

    public void setAdditionKeyList(String[] additionKeyList) {
        this.additionKeyList = additionKeyList;
    }

    public String[] getClauseCodeList() {
        return clauseCodeList;
    }

    public void setClauseCodeList(String[] clauseCodeList) {
        this.clauseCodeList = clauseCodeList;
    }

    public String[] getClauseDocverList() {
        return clauseDocverList;
    }

    public void setClauseDocverList(String[] clauseDocverList) {
        this.clauseDocverList = clauseDocverList;
    }

    public String getSprtTp() {
        return sprtTp;
    }

    public void setSprtTp(String sprtTp) {
        this.sprtTp = sprtTp;
    }

    public String getSprtTpNm() {
        if (sprtTp == null || "".equals(sprtTp)) {
            return "";
        } else if ("KD".equals(sprtTp)) {
            return "단말할인";
        } else if ("PM".equals(sprtTp)) {
            return "요금할인";
        } else {
            return sprtTp;
        }
    }

    public long getMaxDiscount3() {
        return maxDiscount3;
    }

    public void setMaxDiscount3(long maxDiscount3) {
        this.maxDiscount3 = maxDiscount3;
    }

    public long getDcAmt() {
        return dcAmt;
    }

    public void setDcAmt(long dcAmt) {
        this.dcAmt = dcAmt;
    }

    public long getAddDcAmt() {
        return addDcAmt;
    }

    public void setAddDcAmt(long addDcAmt) {
        this.addDcAmt = addDcAmt;
    }

    public String getPlcyTypeCd() {
        return plcyTypeCd;
    }

    public void setPlcyTypeCd(String plcyTypeCd) {
        this.plcyTypeCd = plcyTypeCd;
    }

    public String getPrdtSctnCd() {
        return prdtSctnCd;
    }

    public void setPrdtSctnCd(String prdtSctnCd) {
        this.prdtSctnCd = prdtSctnCd;
    }

    public String getCstmrJejuId() {
        return cstmrJejuId;
    }

    public void setCstmrJejuId(String cstmrJejuId) {
        this.cstmrJejuId = cstmrJejuId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getSelfCstmrCi() {
        return selfCstmrCi;
    }

    public void setSelfCstmrCi(String selfCstmrCi) {
        this.selfCstmrCi = selfCstmrCi;
    }

    public String getRecommendFlagCd() {
        return recommendFlagCd;
    }

    public void setRecommendFlagCd(String recommendFlagCd) {
        this.recommendFlagCd = recommendFlagCd;
    }

    public String getRecommendInfo() {
        return recommendInfo;
    }

    public void setRecommendInfo(String recommendInfo) {
        this.recommendInfo = StringUtil.onlyStringReplace(recommendInfo);
    }

    public String getCretId() {
        if (cretId == null) {
            return "";
        }
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getBannerCd() {
        return bannerCd;
    }

    public void setBannerCd(String bannerCd) {
        this.bannerCd = bannerCd;
    }

    public String getPrdtId() {
        return prdtId;
    }

    public void setPrdtId(String prdtId) {
        this.prdtId = prdtId;
    }

    public String getSettlWayCd() {
        return settlWayCd;
    }

    public void setSettlWayCd(String settlWayCd) {
        this.settlWayCd = settlWayCd;
    }

    public int getSettlAmt() {
        return settlAmt;
    }

    public void setSettlAmt(int settlAmt) {
        this.settlAmt = settlAmt;
    }

    public String getSettlApvNo() {
        return settlApvNo;
    }

    public void setSettlApvNo(String settlApvNo) {
        this.settlApvNo = settlApvNo;
    }

    public String getClauseJehuFlag() {
        return clauseJehuFlag;
    }

    public void setClauseJehuFlag(String clauseJehuFlag) {
        this.clauseJehuFlag = clauseJehuFlag;
    }

    public String getClauseMpps35Flag() {
        return clauseMpps35Flag;
    }

    public void setClauseMpps35Flag(String clauseMpps35Flag) {
        this.clauseMpps35Flag = clauseMpps35Flag;
    }

    public String getOwnPersonalCode() {
        return ownPersonalCode;
    }

    public void setOwnPersonalCode(String ownPersonalCode) {
        this.ownPersonalCode = ownPersonalCode;
    }

    public String getRprsPrdtId() {
        return rprsPrdtId;
    }

    public void setRprsPrdtId(String rprsPrdtId) {
        this.rprsPrdtId = rprsPrdtId;
    }

    public String getSntyColorCd() {
        return sntyColorCd;
    }

    public void setSntyColorCd(String sntyColorCd) {
        this.sntyColorCd = sntyColorCd;
    }

    public String getSntyCapacCd() {
        return sntyCapacCd;
    }

    public void setSntyCapacCd(String sntyCapacCd) {
        this.sntyCapacCd = sntyCapacCd;
    }

    public String getPrdtNm() {
        return prdtNm;
    }

    public void setPrdtNm(String prdtNm) {
        this.prdtNm = prdtNm;
    }

    public String getPlcySctnCd() {
        return plcySctnCd;
    }

    public void setPlcySctnCd(String plcySctnCd) {
        this.plcySctnCd = plcySctnCd;
    }

    public BigDecimal getInstRate() {
        return instRate;
    }

    public void setInstRate(BigDecimal instRate) {
        this.instRate = instRate;
    }

    public String getSiteReferer() {
        return siteReferer;
    }

    public void setSiteReferer(String siteReferer) {
        this.siteReferer = siteReferer;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getClauseRentalService() {
        return clauseRentalService;
    }

    public void setClauseRentalService(String clauseRentalService) {
        this.clauseRentalService = clauseRentalService;
    }

    public String getClauseRentalModelCp() {
        return clauseRentalModelCp;
    }

    public void setClauseRentalModelCp(String clauseRentalModelCp) {
        this.clauseRentalModelCp = clauseRentalModelCp;
    }

    public String getClauseRentalModelCpPr() {
        return clauseRentalModelCpPr;
    }

    public void setClauseRentalModelCpPr(String clauseRentalModelCpPr) {
        this.clauseRentalModelCpPr = clauseRentalModelCpPr;
    }

    public int getRentalBaseAmt() {
        return rentalBaseAmt;
    }

    public void setRentalBaseAmt(int rentalBaseAmt) {
        this.rentalBaseAmt = rentalBaseAmt;
    }

    public int getRentalBaseDcAmt() {
        return rentalBaseDcAmt;
    }

    public void setRentalBaseDcAmt(int rentalBaseDcAmt) {
        this.rentalBaseDcAmt = rentalBaseDcAmt;
    }

    public int getRentalModelCpAmt() {
        return rentalModelCpAmt;
    }

    public void setRentalModelCpAmt(int rentalModelCpAmt) {
        this.rentalModelCpAmt = rentalModelCpAmt;
    }

    public String getClauseFinanceFlag() {
        return clauseFinanceFlag;
    }

    public void setClauseFinanceFlag(String clauseFinanceFlag) {
        this.clauseFinanceFlag = clauseFinanceFlag;
    }

    public String[] getAdditionSocCodeList() {
        return additionSocCodeList;
    }

    public void setAdditionSocCodeList(String[] additionSocCodeList) {
        this.additionSocCodeList = additionSocCodeList;
    }

    public String getOsstPayType() {
        return osstPayType;
    }

    public void setOsstPayType(String osstPayType) {
        this.osstPayType = osstPayType;
    }

    public String getOsstPayDay() {
        return osstPayDay;
    }

    public void setOsstPayDay(String osstPayDay) {
        this.osstPayDay = osstPayDay;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getInsrProdCd() {
        return insrProdCd;
    }

    public void setInsrProdCd(String insrProdCd) {
        this.insrProdCd = insrProdCd;
    }

    public String getClauseInsrProdFlag() {
        return clauseInsrProdFlag;
    }

    public void setClauseInsrProdFlag(String clauseInsrProdFlag) {
        this.clauseInsrProdFlag = clauseInsrProdFlag;
    }

    public String getInsrAuthInfo() {
        return insrAuthInfo;
    }

    public void setInsrAuthInfo(String insrAuthInfo) {
        this.insrAuthInfo = insrAuthInfo;
    }

    public String getClause5gCoverageFlag() {
        return clause5gCoverageFlag;
    }

    public void setClause5gCoverageFlag(String clause5gCoverageFlag) {
        this.clause5gCoverageFlag = clause5gCoverageFlag;
    }

    public String getCommendId() {
        return commendId;
    }

    public void setCommendId(String commendId) {
        this.commendId = commendId;
    }

    public String getUsimKindsCd() {
        return usimKindsCd;
    }

    public void setUsimKindsCd(String usimKindsCd) {
        this.usimKindsCd = usimKindsCd;
    }

    public String getDlvryType() {
        if (dlvryType == null) {
            return "";
        }
        return dlvryType;
    }

    public void setDlvryType(String dlvryType) {
        this.dlvryType = dlvryType;
    }

    public long getSelfDlvryIdx() {
        return selfDlvryIdx;
    }

    public void setSelfDlvryIdx(long selfDlvryIdx) {
        this.selfDlvryIdx = selfDlvryIdx;
    }

    public String getPreUrlType() {
        return preUrlType;
    }

    public void setPreUrlType(String preUrlType) {
        this.preUrlType = preUrlType;
    }

    public String getIndcOdrg() {
        return indcOdrg;
    }

    public void setIndcOdrg(String indcOdrg) {
        this.indcOdrg = indcOdrg;
    }

    public String getSettlTraNo() {
        return settlTraNo;
    }

    public void setSettlTraNo(String settlTraNo) {
        this.settlTraNo = settlTraNo;
    }

    public String getRvisnId() {
        return rvisnId;
    }

    public void setRvisnId(String rvisnId) {
        this.rvisnId = rvisnId;
    }

    public String getRvisnDttm() {
        return rvisnDttm;
    }

    public void setRvisnDttm(String rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }

    public String getOpenReqDate() {
        return openReqDate;
    }

    public void setOpenReqDate(String openReqDate) {
        this.openReqDate = openReqDate;
    }

    public String getSysRdate() {
        return sysRdate;
    }

    public void setSysRdate(String sysRdate) {
        this.sysRdate = sysRdate;
    }

    public String getReqInDay() {
        return reqInDay;
    }

    public void setReqInDay(String reqInDay) {
        this.reqInDay = reqInDay;
    }

    public String getSoTrnsAgrmYn() {
        return soTrnsAgrmYn;
    }

    public void setSoTrnsAgrmYn(String soTrnsAgrmYn) {
        this.soTrnsAgrmYn = soTrnsAgrmYn;
    }

    public String getShopNm() {
        return shopNm;
    }

    public void setShopNm(String shopNm) {
        this.shopNm = shopNm;
    }

    public String getPromotionCd() {
        return promotionCd;
    }

    public void setPromotionCd(String promotionCd) {
        this.promotionCd = promotionCd;
    }

    public String getRealShopNm() {
        return realShopNm;
    }

    public void setRealShopNm(String realShopNm) {
        this.realShopNm = realShopNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getTmpStep() {
        return tmpStep;
    }

    public void setTmpStep(String tmpStep) {
        this.tmpStep = tmpStep;
    }

    public String getTelAdvice() {
        return telAdvice;
    }

    public void setTelAdvice(String telAdvice) {
        this.telAdvice = telAdvice;
    }

    public String[] getPrmtIdList() {
        return prmtIdList;
    }

    public void setPrmtIdList(String[] prmtIdList) {
        this.prmtIdList = prmtIdList;
    }

    public String[] getPrdtIdList() {
        return prdtIdList;
    }

    public void setPrdtIdList(String[] prdtIdList) {
        this.prdtIdList = prdtIdList;
    }

    public String getSesplsYn() {
        return sesplsYn;
    }

    public void setSesplsYn(String sesplsYn) {
        this.sesplsYn = sesplsYn;
    }

    public String getProdCtgType() {
        return prodCtgType;
    }

    public void setProdCtgType(String prodCtgType) {
        this.prodCtgType = prodCtgType;
    }

    public String getKtmReferer() {
        return ktmReferer;
    }

    public void setKtmReferer(String ktmReferer) {
        this.ktmReferer = ktmReferer;
    }

    public String getHndsetSalePrice() {
        return hndsetSalePrice;
    }

    public void setHndsetSalePrice(String hndsetSalePrice) {
        this.hndsetSalePrice = hndsetSalePrice;
    }

    /**
     * @Description :  최대할인 금액
     * 공통코드 BE0020 할인 카드 리스트
     * ExpnsnStrVal3 최대할인금액
     *  */
    public int getMaxDcAmt() {
        int maxDcAmt = 0;
        NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("BE0020", getCardDcCd());

        if (nmcpCdDtlDto == null) {
            return 0;
        }

        try {
            maxDcAmt = Integer.parseInt(nmcpCdDtlDto.getExpnsnStrVal3());
        } catch (NumberFormatException e) {
            return 0;
        } catch (Exception e) {
            return 0;
        }
        return maxDcAmt;
    }

    /**
     * @Description :  실제 결제 금액
    자급제 단말기 판매가 - 사용포인트   - 카드할인금액= 실제 결제 금액
    PCT
    결제 가격= 단말기가격 -  (단말기가격 * 할인퍼센트) - 포인튼
     * @return
     */
    public int getPaymentHndsetPrice() {
        int hndsetSalePriceInt = -1;
        try {
            hndsetSalePriceInt = Integer.parseInt(hndsetSalePrice);
        } catch (NumberFormatException e) {
            return -1;
        }

        //PCT
        if ("WON".equals(cardDcDivCd)) {
            return hndsetSalePriceInt - getUsePointInt() - getCardDcAmtInt();
        } else if ("PCT".equals(cardDcDivCd)) {
            //최대 할인 금액 확인
            int maxDcAmt = getMaxDcAmt();

            BigDecimal bgHndsetSalePrice = new BigDecimal(hndsetSalePrice); //단말기 가격
            BigDecimal bgPct = new BigDecimal("0.01");
            BigDecimal bgCardDcAmt = new BigDecimal(getCardDcAmt());   //할인퍼센트
            bgCardDcAmt = bgCardDcAmt.multiply(bgPct);

            //소수점 버림 처리
            int cardDcAmtInt = bgHndsetSalePrice.multiply(bgCardDcAmt).setScale(0, RoundingMode.DOWN).intValue();

            if (cardDcAmtInt > maxDcAmt) {
                cardDcAmtInt = maxDcAmt;
            }

            return hndsetSalePriceInt - getUsePointInt() - cardDcAmtInt;
        } else {
            return hndsetSalePriceInt - getUsePointInt();
        }
    }


    //CARD_TOT_DC_AMT
    //cardTotDcAmt
    public String getCardTotDcAmt() {
        //PCT
        if ("WON".equals(cardDcDivCd)) {
            return getCardDcAmtInt() + "";
        } else if ("PCT".equals(cardDcDivCd)) {
            //최대 할인 금액 확인
            int maxDcAmt = getMaxDcAmt();

            BigDecimal bgHndsetSalePrice = new BigDecimal(hndsetSalePrice); //단말기 가격
            BigDecimal bgPct = new BigDecimal("0.01");
            BigDecimal bgCardDcAmt = new BigDecimal(getCardDcAmt());   //할인퍼센트
            bgCardDcAmt = bgCardDcAmt.multiply(bgPct);

            //소수점 버림 처리
            int cardDcAmtInt = bgHndsetSalePrice.multiply(bgCardDcAmt).setScale(0, RoundingMode.DOWN).intValue();
            if (cardDcAmtInt > maxDcAmt) {
                cardDcAmtInt = maxDcAmt;
            }
            return cardDcAmtInt + "";
        } else {
            return "";
        }
    }

    public String getUsePoint() {
        return usePoint;
    }

    public int getUsePointInt() {
        int usePointInt = 0;
        try {
            usePointInt = Integer.parseInt(usePoint);
        } catch (NumberFormatException e) {
            usePointInt = 0;
        }
        return usePointInt;
    }


    public void setUsePoint(String usePoint) {
        this.usePoint = usePoint;
    }

    public String getUsePointSvcCntrNo() {
        return usePointSvcCntrNo;
    }

    public void setUsePointSvcCntrNo(String usePointSvcCntrNo) {
        this.usePointSvcCntrNo = usePointSvcCntrNo;
    }

    public String getCardDcCd() {
        return cardDcCd;
    }

    public void setCardDcCd(String cardDcCd) {
        this.cardDcCd = cardDcCd;
    }

    public String getCardDcAmt() {
        return cardDcAmt;
    }

    public int getCardDcAmtInt() {
        int cardDcAmtInt = 0;
        try {
            cardDcAmtInt = Integer.parseInt(cardDcAmt);
        } catch (NumberFormatException e) {
            cardDcAmtInt = 0;
        }
        return cardDcAmtInt;
    }

    public void setCardDcAmt(String cardDcAmt) {
        this.cardDcAmt = cardDcAmt;
    }

    public String getCardDcDivCd() {
        return cardDcDivCd;
    }

    public void setCardDcDivCd(String cardDcDivCd) {
        this.cardDcDivCd = cardDcDivCd;
    }

    public String getSesplsProdId() {
        return sesplsProdId;
    }

    public void setSesplsProdId(String sesplsProdId) {
        this.sesplsProdId = sesplsProdId;
    }

    public String getUsimBuyDivCd() {
        return usimBuyDivCd;
    }

    public void setUsimBuyDivCd(String usimBuyDivCd) {
        this.usimBuyDivCd = usimBuyDivCd;
    }

    public String[] getPrmtTypeList() {
        return prmtTypeList;
    }

    public void setPrmtTypeList(String[] prmtTypeList) {
        this.prmtTypeList = prmtTypeList;
    }

    public String getSelfCstmrDi() {
        return selfCstmrDi;
    }

    public void setSelfCstmrDi(String selfCstmrDi) {
        this.selfCstmrDi = selfCstmrDi;
    }

    public long getUploadPhoneSrlNo() {
        return uploadPhoneSrlNo;
    }

    public void setUploadPhoneSrlNo(long uploadPhoneSrlNo) {
        this.uploadPhoneSrlNo = uploadPhoneSrlNo;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getEsimPhoneId() {
        return esimPhoneId;
    }

    public void setEsimPhoneId(String esimPhoneId) {
        this.esimPhoneId = esimPhoneId;
    }


    public String getPrntsContractNo() {
        return prntsContractNo;
    }

    public void setPrntsContractNo(String prntsContractNo) {
        this.prntsContractNo = prntsContractNo;
    }

    public String getPrntsCtn() {
        return prntsCtn;
    }

    public void setPrntsCtn(String prntsCtn) {
        this.prntsCtn = prntsCtn;
    }

    public String getPrntsBillNo() {
        return prntsBillNo;
    }

    public void setPrntsBillNo(String prntsBillNo) {
        this.prntsBillNo = prntsBillNo;
    }

    public String getRwdProdCd() {
        return rwdProdCd;
    }

    public void setRwdProdCd(String rwdProdCd) {
        this.rwdProdCd = rwdProdCd;
    }

    public String getClauseRwdFlag() {
        return clauseRwdFlag;
    }

    public void setClauseRwdFlag(String clauseRwdFlag) {
        this.clauseRwdFlag = clauseRwdFlag;
    }

    public String getRwdAuthInfo() {
        return rwdAuthInfo;
    }

    public void setRwdAuthInfo(String rwdAuthInfo) {
        this.rwdAuthInfo = rwdAuthInfo;
    }

    public void setPersonalInfoCollectAgree(String personalInfoCollectAgree) {
        this.personalInfoCollectAgree = personalInfoCollectAgree;
    }

    public String getPersonalInfoCollectAgree() {
        return personalInfoCollectAgree;
    }

    public void setOthersTrnsAgree(String othersTrnsAgree) {
        this.othersTrnsAgree = othersTrnsAgree;
    }

    public String getOthersTrnsAgree() {
        return othersTrnsAgree;
    }

    public String getKtInterSvcNo() {
        return ktInterSvcNo;
    }

    public void setKtInterSvcNo(String ktInterSvcNo) {
        this.ktInterSvcNo = ktInterSvcNo;
    }

    public String getCertMenuType() {
        return certMenuType;
    }

    public void setCertMenuType(String certMenuType) {
        this.certMenuType = certMenuType;
    }

    public String getPrmtId() {
        return prmtId;
    }

    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }

    public String getEvntCd() {
        return evntCd;
    }

    public void setEvntCd(String evntCd) {
        this.evntCd = evntCd;
    }

    public String getReqSeq() {
        return reqSeq;
    }

    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }

    public String getResSeq() {
        return resSeq;
    }

    public void setResSeq(String resSeq) {
        this.resSeq = resSeq;
    }

    //    public NiceLogDto getNiceLogDto() {
    //        return niceLogDto;
    //    }
    //
    //    public void setNiceLogDto(NiceLogDto niceLogDto) {
    //        this.niceLogDto = niceLogDto;
    //    }

    public String getMinorAgentAgrmYn() {
        return minorAgentAgrmYn;
    }

    public void setMinorAgentAgrmYn(String minorAgentAgrmYn) {
        this.minorAgentAgrmYn = minorAgentAgrmYn;
    }

    public String getClausePartnerOfferFlag() {
        return clausePartnerOfferFlag;
    }

    public void setClausePartnerOfferFlag(String clausePartnerOfferFlag) {
        this.clausePartnerOfferFlag = clausePartnerOfferFlag;
    }

    public String getJehuProdType() {
        return jehuProdType;
    }

    public void setJehuProdType(String jehuProdType) {
        this.jehuProdType = jehuProdType;
    }

    public String getSelfOpenYn() {
        return selfOpenYn;
    }

    public void setSelfOpenYn(String selfOpenYn) {
        this.selfOpenYn = selfOpenYn;
    }

    public String getOpenMarketId() {
        return openMarketId;
    }

    public void setOpenMarketId(String openMarketId) {
        this.openMarketId = openMarketId;
    }

    public String getMpCode() {
        return mpCode;
    }

    public void setMpCode(String mpCode) {
        this.mpCode = mpCode;
    }

    public String getJehuRefererYn() {
        return jehuRefererYn;
    }

    public void setJehuRefererYn(String jehuRefererYn) {
        this.jehuRefererYn = jehuRefererYn;
    }

    public String getEvntCdPrmt() {
        return evntCdPrmt;
    }

    public void setEvntCdPrmt(String evntCdPrmt) {
        this.evntCdPrmt = evntCdPrmt;
    }

    public String getGiftUseYn() {
        return giftUseYn;
    }

    public void setGiftUseYn(String giftUseYn) {
        this.giftUseYn = giftUseYn;
    }

    public String getRecoUseYn() {
        return recoUseYn;
    }

    public void setRecoUseYn(String recoUseYn) {
        this.recoUseYn = recoUseYn;
    }

    public String getFathTrgYn() {
        return fathTrgYn;
    }

    public void setFathTrgYn(String fathTrgYn) {
        this.fathTrgYn = fathTrgYn;
    }

    public String getClauseFathFlag() {
        return clauseFathFlag;
    }

    public void setClauseFathFlag(String clauseFathFlag) {
        this.clauseFathFlag = clauseFathFlag;
    }

    public String getFathTransacId() {
        return fathTransacId;
    }

    public void setFathTransacId(String fathTransacId) {
        this.fathTransacId = fathTransacId;
    }

    public String getFathCmpltNtfyDt() {
        return fathCmpltNtfyDt;
    }

    public void setFathCmpltNtfyDt(String fathCmpltNtfyDt) {
        this.fathCmpltNtfyDt = fathCmpltNtfyDt;
    }

    public String getFathMobileFn() {
        return fathMobileFn;
    }

    public void setFathMobileFn(String fathMobileFn) {
        this.fathMobileFn = fathMobileFn;
    }

    public String getFathMobileMn() {
        return fathMobileMn;
    }

    public void setFathMobileMn(String fathMobileMn) {
        this.fathMobileMn = fathMobileMn;
    }

    public String getFathMobileRn() {
        return fathMobileRn;
    }

    public void setFathMobileRn(String fathMobileRn) {
        this.fathMobileRn = fathMobileRn;
    }

    public String getFathTelNo() {
        return fathTelNo;
    }

    public void setFathTelNo(String fathTelNo) {
        this.fathTelNo = fathTelNo;
    }

    public String getAppEventCd() {
        return appEventCd;
    }

    public void setAppEventCd(String appEventCd) {
        this.appEventCd = appEventCd;
    }

    public String getCpntId() {
        return cpntId;
    }

    public void setCpntId(String cpntId) {
        this.cpntId = cpntId;
    }

    public String getCombineSoloType() {
        return combineSoloType;
    }

    public void setCombineSoloType(String combineSoloType) {
        this.combineSoloType = combineSoloType;
    }

    public String getCombineSoloFlag() {
        return combineSoloFlag;
    }

    public void setCombineSoloFlag(String combineSoloFlag) {
        this.combineSoloFlag = combineSoloFlag;
    }

    public long getRequestKeyTemp() {
        return requestKeyTemp;
    }

    public void setRequestKeyTemp(long requestKeyTemp) {
        this.requestKeyTemp = requestKeyTemp;
    }

    public String getIndvLocaPrvAgree() {
        return indvLocaPrvAgree;
    }

    public void setIndvLocaPrvAgree(String indvLocaPrvAgree) {
        this.indvLocaPrvAgree = indvLocaPrvAgree;
    }

    public String getKtCounselAgree() {
        return ktCounselAgree;
    }

    public void setKtCounselAgree(String ktCounselAgree) {
        this.ktCounselAgree = ktCounselAgree;
    }

    public String getNewCcrSeq() {
        return newCcrSeq;
    }

    public void setNewCcrSeq(String newCcrSeq) {
        this.newCcrSeq = newCcrSeq;
    }

    /** 제품구분코드(CMN0045) */
    private String prdtIndCd;

    public String getPrdtIndCd() {
        return prdtIndCd;
    }

    public void setPrdtIndCd(String prdtIndCd) {
        this.prdtIndCd = prdtIndCd;
    }

    /** 위탁서식지 유심번호 추가 */
    private String apdSeq;

    public String getApdSeq() {
        return apdSeq;
    }

    public void setApdSeq(String apdSeq) {
        this.apdSeq = apdSeq;
    }

    private String slsTp;

    public String getSlsTp() {
        return slsTp;
    }

    public void setSlsTp(String slsTp) {
        this.slsTp = slsTp;
    }

}
