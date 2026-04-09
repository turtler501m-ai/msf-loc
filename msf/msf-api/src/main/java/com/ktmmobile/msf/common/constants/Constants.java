package com.ktmmobile.msf.common.constants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Constants {

    /** 사이트 구분 : 직영 */
    public static final String SITE_CODE_DEFAULT = "SM";

    /** 폴더 경로 구분 **/
    public static final String SEPARATOR = File.separator;

    public static final String FILE_CACHE_NAME = "cache_reload_data.txt";

    public static final String FILE_CACHE_DIR = SEPARATOR + "cache";

    /** AJAX 연동 결과 성공 */
    public static final String AJAX_SUCCESS = "00000";



    /** 쿠팡 접점  */
    public static final String CONTPNT_SHOP_ID_COUPANG =  "1100028432" ;

    /**  M모바일(M쇼핑)(V000016007)으로 shop */
    public static final String CONTPNT_SHOP_ID_MSHOP =  "1100011741" ;

    /**  M모바일(자급제)/V000019481  */
    public static final String CONTPNT_SELF_PHONE =  "V000019481" ;


    //개통 간소화 관련 OSST
    /** OSST 연동 결과 성공 */
    public static final String OSST_SUCCESS = "0000";
    /** 가입내역안내서 발송에러(KOS 전산 개통완료) */
    public static final String OSST_ERROR_SEND_GUIDE = "3060";
    /** OTA 발송 에러(KOS 전산 개통완료) */
    public static final String OSST_ERROR_SEND_OTA = "3070";


    /** EVENT_CODE 사전체크 및 고객생성(PC0) PreCheck */
    public static final String EVENT_CODE_PRE_CHECK = "PC0";

    /** EVENT_CODE 번호조회(NU1) */
    public static final String EVENT_CODE_SEARCH_NUMBER = "NU1";


    /** EVENT_CODE 번호예약/취소(NU2) */
    public static final String EVENT_CODE_NUMBER_REG = "NU2";

    /** EVENT_CODE 개통및수납(OP0) */
    public static final String EVENT_CODE_REQ_OPEN = "OP0";

    /** EVENT_CODE 개통및수납 결과(OP2) */
    public static final String EVENT_CODE_REQ_OPEN_RESULT = "OP2";

    /** EVENT_CODE 번호이동 사전동의 요청(NP1) */
    public static final String EVENT_CODE_NP_PRE_CHECK = "NP1";

    /** EVENT_CODE 납부주장 요청(NP2) */
    public static final String EVENT_CODE_NP_REQ_PAY = "NP2";


    /** 사전동의 결과조회(NP3)  Agree*/
    public static final String EVENT_CODE_NP_ARREE = "NP3";

    /** EVENT_CODE 사전체크 및 고객생성 결과 확인(PC2) */
    public static final String EVENT_CODE_PC_RESULT = "PC2";



    /** EVENT_CODE 유심셀프변경(UC0) */
    public static final String EVENT_CODE_USIM_SELF_CHG = "UC0";
    /** EVENT_CODE 유심변경결과(UC2) */
    public static final String EVENT_CODE_USIM_CHG_RST = "UC2";

    /** EVENT_CODE 상태조회(ST1) */
    public static final String EVENT_CODE_PRE_SCH = "ST1";


    /** 업무구분코드 RSV : 예약 */
    public static final String WORK_CODE_RES = "RSV";

    /** 업무구분코드 RRS : 예약취소 */
    public static final String WORK_CODE_RES_CANCEL = "RRS";

    /**14세 미만의 아동 부모동의 */
    public static final int AGREE_AUT_AGE = 14;
    //public static final int AGREE_AUT_AGE = 50; // 테스트용

    //############################# 서식지  관련 ##############################################
    /** 업무구분 operType
     * HCN3 : 기기변경
     * HDN3 : 보상기변3G
     * MNP3 : 번호이동
     * NAC3 : 신규개통
     *  */
    /** NAC3 : 신규개통 */
    public static final String OPER_TYPE_NEW = "NAC3";
    /** MNP3 : 번호이동 */
    public static final String OPER_TYPE_MOVE_NUM = "MNP3";
    /** HCN3 : 기기변경 */
    public static final String OPER_TYPE_CHANGE = "HCN3";
    /** HDN3 : 우수기변 */
    public static final String OPER_TYPE_EXCHANGE = "HDN3";

    public static Map<String,String> OPER_TYPE_NAME_MAP ;


    /** 고객구분
     *  JP:법인
     *  PP:개인사업자
     * */
    /** NA :내국인 */
    public static final String CSTMR_TYPE_NA = "NA";
    /** NM : 내국인(미성년자) */
    public static final String CSTMR_TYPE_NM = "NM";
    /** FN:외국인 */
    public static final String CSTMR_TYPE_FN = "FN";

    public static Map<String,String> CSTMR_TYPE_NAME_MAP ;

    /** 구매유형
     * 단말 구매:MM
     * USIM(유심)단독 구매:UU */
    /** MM :단말 구매 */
    public static final String REQ_BUY_TYPE_PHONE = "MM";
    /** UU :USIM(유심)단독 구매 */
    public static final String REQ_BUY_TYPE_USIM = "UU";



    /**
     * 메인 상품 추천 정보 ReqProdCommend
     * 상품 분류 (휴대폰 :01,유심 :02)
     */
    /** 상품 분류 (휴대폰 :01,유심 :02)*/
    public static final String PROD_TYPE_PHONE = "01";
    /** 상품 분류 (휴대폰 :01,유심 :02)*/
    public static final String PROD_TYPE_USIM = "02";


    /** 서비스구분
     * PP:선불
     * PO:후불
     * */
    /** 서비스구분 : 선불 */
    public static final String SERVICE_TYPE_PREPAID = "PP";
    /** 서비스구분 : 후불 */
    public static final String SERVICE_TYPE_LATER_PAY = "PO";

    /** 통신사 리스트 그룹 코드  */
    public static final String WIRE_SERVICE_CODE = "NSC";


    /** 추천인 구분 그룹 코드 F001 01:추천 직원번호 02:제휴사 추천코드  */
    public static final String RECOMMEND_FLAG_GRUP_CODE = "F001";

    /** MSP 판매정책조회 그룹 코드값  sprtTp(KD) => 단말할인:KD,요금할인:PM*/
    public static final String PHONE_DISCOUNT_GRUP_CODE = "F002";

    /** MCP_REQUEST 미승인 상태 코드 */
    public static final String PRE_APPROVAL_PSTATE_CODE = "99";

    /** NMCP_BOARD_BAS 구분
     * 고객서비스 안내:59
     *  */
    public static final int BOARD_CTG_GUIDE = 59;

    /** 제휴이벤트  분류코드  */
    public static final String GROUP_CODE_SYS_EVENT_CTG = "SysEventCtg";

    /** 고객 서비스 안내 분류 코드  */
    public static final String GROUP_CODE_SERVICE_GUIDE_CTG = "ServiceGuideCtgCD";

    /** 유심등록사이트리스트 분류 코드  */
    public static final String GROUP_CODE_USIM_REGI_SITE_LIST = "UsimRegiSiteList";

    /** 직접 개통 가능 접점 코드 리스트  */
    public static final String GROUP_CODE_SIMPLE_REGI_SITE_LIST = "SimpleRegiSiteList";

    /** 위탁온라인 가입비/유심비 면제 여부  */
    public static final String GROUP_CODE_MARKET_JOIN_USIM_INFO = "MarketJoinUsimPriceInfo";

    /** 지역번호 리스트  */
    public static final String GROUP_CODE_ARE_CODE = "AreaCodeList";

    /** 위탁 서식지 랜딩 페이지 URL 리스트  */
    public static final String GROUP_CODE_FORM_LAND_URL = "FormLandingUrl";


    /** 고객포탈 판매 요금제 별 가입비/유심비 면제 여부  */
    public static final String GROUP_CODE_USIM_PRICE_INFO = "AppFormJoinUsimPriceInfo";

    /** 직영에서만 적용하는 유심비  */
    public static final String GROUP_CODE_DIRECT_USIM_PRICE = "DirectUsimPrice";

    /** 재약정 사은품 정보   */
    public static final String GROUP_CODE_PRESENT_CODE = "presentCode";

    /** 요금제 구분  */
    public static final String GROUP_RATE_KIND = "RATEKIND";

    /** 메인 추천요금제 코드  */
    public static final String GROUP_CODE_MAIN_RATE_PLAN = "mainRatePlan";

    /** 메인 추천 휴대폰 관리 코드 구분  */
    public static final String GROUP_CODE_MAIN_PHONE_RATE_PLAN = "mainPhoneRatePlan";


    /** 요금제 속성 리스트 그룹코드  */
    public static final String GROUP_CODE_RATE_PROPERTY_LIST = "RatePropertyDtlList";
    public static final String GROUP_CODE_RATE_PROPERTY_SOLO = "01";
    public static final String GROUP_CODE_RATE_PROPERTY_SHARE = "06";
    public static final String GROUP_CODE_RATE_PROPERTY_TOGET = "07";
    public static final String GROUP_CODE_RATE_PROPERTY_TRIPLE = "05";
    public static final String GROUP_CODE_RATE_PROPERTY_WIFI = "09";
    public static final String GROUP_CODE_RATE_PROPERTY_COUPON = "02";



    public static final String  DTL_CD_OBJ_BASE = "BASE";
    public static final String  DTL_CD_OBJ_5G = "5G";
    public static final String  DTL_CD_OBJ_3G = "3G";
    public static final String  DTL_CD_USIM_NFC = "NFC";

    /** 팝업타이틀 공통 코드화  */
    public static final String	POP_TITLE_CODE = "PopupTitle";



    /** 제휴이벤트  분류코드  :제휴 : J */
    public static final String EVENT_SBST_CTG_JEHU = "J";

    /** 제휴이벤트  분류코드  :제휴카드 : C */
    public static final String EVENT_SBST_CTG_JEHUCARD = "C";

    /** 제휴이벤트  분류코드  :기획전 : G */
    public static final String EVENT_SBST_CTG_PLAN = "G";

    /** 제휴이벤트  분류코드  :이벤트 : E */
    public static final String EVENT_SBST_CTG_EVENT = "E";


    /** SMS발송 접점코드 그룹 코드   */
    public static final String SEND_LMS_CNTPNT_GROP_CODE = "SysFormSmsCntpntShopId";

    /** SMS발송 관리자 그룹 코드   */
    public static final String SEND_SMS_MNG_GROP_CODE = "SendSmsMngGropCode";

    /**셀프 개통 그룹 코드   */
    public static final String SIMPLE_OPEN_EXCEPTION_GROP_CODE = "SIMPLEOPENEXCEPTION" ;

    /**셀프 개통 가능 플랫폼 코드 */
    public static final String SIMPLE_OPEN_PLATFORM_CODE = "simpleOpenPlatform" ;

    /**셀프개통 플랫폼 제한 예외 IP */
    public static final String SIMPLE_OPEN_LIMIT_EXCEPTION_IP = "simpleOpenLimitExceptionIp" ;

    /**유심코드 공통코드  */
    public static final String USIM_PROD_ID_GROP_CODE = "usimProdInfo" ;

    /**유심코드상세 공통코드  */
    public static final String USIM_PROD_DTL_ID_GROP_CODE = "usimProdDetailInfo" ;

    /**셀프 배송요청 진행상태 공통코드[dStateCode] 01 접수대기, 02 배송중 , 03 배송완료 , 04 개통완료  */
    public static final String USIM_PROD_STATE_CODE = "dStateCode" ;

    /**택배사 목록  */
    public static final String DLVRY_TB_CODE = "PERCEL" ;

    /*위탁서식지 담당자 문자 발송 접점코드*/
    public static final String SEND_SMS_CNTPNT_AGENT_CODE = "SendSmsCntpntAgentCode";

    /**Data 함께쓰기 자회선 요금제 대상  공통코드[ShareRateChildList] */
    public static final String SHARE_RATE_CHILD_LIST = "ShareRateChildList" ;

    /**Data 함께쓰기 모회선 요금제 대상  공통코드[ShareRateParentList] */
    public static final String SHARE_RATE_PARENT_LIST = "ShareRateParentList" ;

    public static final String DLVRLY_ENEXCEPTION_DATE = "DLVRLYENEXCEPTIONDATE" ;

    public static final String DLVRLY_VIEW_YN = "DLVRLYVIEWYN" ;

    /** 01 : 정회원 코드 */
    public static final String DIVISION_CODE_LEGALLY_MEMBER = "01";

    /** 테스트를 위한 예외 아이디 **/
    public static final String ID_GROUP_EXCEPTION = "idGroupException";

    /** 간편결제 수납채널 LIST
     * - 카카오페이 : KA
     * - 페이코 : PY
     * - 네이버페이 : NP
     * - 토스페이: TS
    **/
    public static final String RMNY_CHID_OBJ_LIST = "rmnyChIdObjList";



    /*
    * 01 개인정보 수집.이용 동의   clausePriCollectFlag
    * 02 개인정보의 제공 동의   clausePriOfferFlag
    * 03 고유식별정보의 수집·이용·제공 동의  clauseEssCollectFlag
    * 04 개인정보의 처리 업무 위탁 동의  clausePriTrustFlag
    * 05 정보/광고 전송을 위한 개인정보 이용, 취급위탁 및 정보/광고 수신동의서    clausePriAdFlag
    * 06 제휴 서비스를 위한 동의
    * 07 신용정보 조회·이용·제공에 대한 동의서  clauseConfidenceFlag
    * 08 청소년 유해정보 네트워크차단 동의
    * 09 청소년 유해정보차단 APP 설치 동의
    * 10 서비스 이용약관
    * 11 선불서비스 안내사항
    * 12 후불서비스 안내사항
    * 13 핸드폰 할부매매 약정안내
    * 14 미성년고객 이용안내
    * 15 청소년 보호를 위한 이용약관 [12장 청소년 보호 등]
    * 16    (고지)서비스 이용약관  xxxxx  10
    * 17    (고지)선불서비스 안내사항  xxxx 11
    * 18    (고지)후불서비스 안내사항   XXXXX 12
    * 19    (고지)핸드폰 할부매매 약정안내  XXXX 13
    * 20    (고지)미성년고객 이용안내  XXX 14
    * 21    (고지)청소년 보호를 위한 이용약관 [12장 청소년 보호 등]   xxxx 15
    * 22    (고지) 청소년 모바일 유해 차단 의무화 시행
    * 23    청소년 유해정보 네트워크차단 동의
    * 24    청소년 유해정보차단 APP 설치 동의
    */
    /** 개인정보 수집.이용 동의   clausePriCollectFlag  */
    public static final String CLAUSE_PRI_COLLECT_CODE = "01";

    /** 개인정보의 제공 동의   clausePriOfferFlag */
    public static final String CLAUSE_PRI_OFFER_CODE = "02";

    /** 고유식별정보의 수집·이용·제공 동의  clauseEssCollectFlag */
    public static final String CLAUSE_ESS_COLLECT_CODE = "03";

    /** 개인정보의 처리 업무 위탁 동의  clausePriTrustFlag */
    public static final String CLAUSE_PRI_TRUST_CODE = "04";

    /** 정보/광고 전송을 위한 개인정보 이용, 취급위탁 및 정보/광고 수신동의서    clausePriAdFlag */
    public static final String CLAUSE_PRI_AD_CODE = "01";

    /** 고객 혜택 제공을 위한 개인정보 수집 및 이동 동의 personalInfoCollectAgree **/
    public static final String PERSONAL_INFO_COLLECT_AGREE = "03";

    /** 제 3자 제공 동의 othersTrnsAgree **/
    public static final String OTHERS_TRNS_AGREE = "04";

    /** 인터넷 가입 상담을 위한 개인정보 제3자 제공 동의(선택) ktCounselFlag **/
    public static final String KT_COUNSEL_AGREE = "ktCounselFlag";

    /** 제휴 서비스를 위한 동의*/
    public static final String CLAUSE_CODE_06 = "02";

    /** 신용정보 조회·이용·제공에 대한 동의서  clauseConfidenceFlag*/
    public static final String CLAUSE_CONFIDENCE_CODE = "05";

    /** 혜택 제공을 위한 제3자 제공 동의(KT) othersTrnsKtAgree */
    public static final String OTHERS_TRNS_KT_AGREE = "06";

    /** 제3자 제공관련 광고 수신 동의 othersAdReceiveAgree */
    public static final String OTHERS_AD_RECEIVE_AGREE = "07";

    /** 혜택 제공을 위한 제3자 제공 동의(엠모바일 + KT) */
    public static final String OTHERS_TRNS_ALL_AGREE = "08";

    /** 개인위치정보 제 3자 제공 동의 */
    public static final String INDV_LOCA_PRV_AGREE = "10";

    /** 고객정보 삭제에 대한 동의서(명의변경)  clauseCntrDelFlag*/
    public static final String CLAUSE_CNTR_DEL_CODE = "cntrDelFlag";


    /** 중고렌탈 프로그램 서비스 이용에 대한 동의서 */
    public static final String CLAUSE_RENTAL_INFO = "ClauseRentalInfo";

    /** 단말배상금 안내사항 */
    public static final String CLAUSE_RENTAL_CP = "ClauseRentalCP";

    /** 단말배상금(부분파손) 안내사항 */
    public static final String CLAUSE_RENTAL_CPPR = "ClauseRentalCpPr";

    /** 선불 M PPS 요금제_동의 */
    public static final String CLAUSE_MPPS35_FLAG = "clauseMpps35Flag";

    /** 개인(신용)정보 처리 및 보험가입 동의서 */
    public static final String CLAUSE_FINANCE_FLAG = "financeFlag";

    /** DB선택보험 개인(신용) 정보처리 및 보험가입 동의 */
    public static final String CLAUSE_INSURANCE_FLAG = "insuranceFlag";

    /** 셀프개통 안내사항 */
    public static final String CLAUSE_SIMPLE_FLAG = "clauseSimpleOpen";



    /** DB 휴대폰 안심 서비스동의 */
    public static final String CLAUSE_INSR_PROD_FLAG = "clauseInsrProdFlag";


    /** 번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법   clauseMoveCode  */
    public static final String CLAUSE_MOVE_CODE = "clauseMoveCode";

    /** 번호이동 시 이전 통신사의 잔여요금 및 환급금 납부방법   clause5gCoverage  */
    public static final String CLAUSE_5G_COVERAGE = "clause5gCoverage";

    /** 셀프개통 배송 요청  개인정보 수집, 이용 동의(필수) */
    public static final String CLAUSE_SELF_PERSONAL = "clauseSelfPersonal";

    /** 셀프개통 배송 요청  개인정보의 처리 업무 위탁동의(필수) */
    public static final String CLAUSE_SELF_COMMISSION = "clauseSelfCommission";


    /** 자급제 보상 서비스  > 상품설명(이용료) 및 서비스 가입 동의(필수) */
    public static final String CLAUSE_RWD_REG_FLAG = "clauseRwdRegFlag";

    /** 자급제 보상 서비스  > 만기 시 보장율/보장금액 지급기준 이행 동의(필수) */
    public static final String CLAUSE_RWD_PAYMENT_FLAG = "clauseRwdPaymentFlag";

    /** 자급제 보상 서비스  > 서비스 단말 반납 조건, 등급산정 및 평가 기준 동의(필수) */
    public static final String CLAUSE_RWD_RATING_FLAG = "clauseRwdRatingFlag";

    /** 자급제 보상 서비스  > 개인정보 수집ㆍ이용에 대한 동의(필수) */
    public static final String CLAUSE_RWD_PRIVACY_INFO_FLAG = "clauseRwdPrivacyInfoFlag";

    /** 자급제 보상 서비스  > 서비스 위탁사 개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서(필수) */
    public static final String CLAUSE_RWD_TRUST_FLAG = "clauseRwdTrustFlag";


    /** 법정대리인 안내사항 (필수) */
    public static final String CLAUSE_MINOR_AGENT = "clauseMinorAgent";



    /** 16  (고지)서비스 이용약관 */
    public static final String NOTICE_CODE_16 = "01";
    /** 17  (고지)선불서비스 안내사항 */
    public static final String NOTICE_CODE_17 = "02";
    /** 18  (고지)후불서비스 안내사항 */
    public static final String NOTICE_CODE_18 = "03";
    /** 19  (고지)핸드폰 할부매매 약정안내 */
    public static final String NOTICE_CODE_19 = "04";
    /** 20  (고지)미성년고객 이용안내 */
    public static final String NOTICE_CODE_20 = "05";
    /** 21  (고지)청소년 보호를 위한 이용약관 [12장 청소년 보호 등] */
    public static final String NOTICE_CODE_21 = "06";
    /** 22  (고지) 청소년 모바일 유해 차단 의무화 시행 */
    public static final String NOTICE_CODE_22 = "07";
    /** 25  (고지) 기타 안내 */
    public static final String NOTICE_CODE_25 = "08";
    /** 26  (고지) 혜택 소멸 */
    public static final String NOTICE_CODE_26 = "09";
    /** 27  (고지) 명의변경 주요안내 */
    public static final String NOTICE_CODE_27 = "10";

    /** 23  청소년 유해정보 네트워크차단 동의 */
    public static final String NOTICE_CODE_23 = "06";
    /** 24 청소년 유해정보차단 APP 설치 동의 */
    public static final String NOTICE_CODE_24 = "07";

    /** 비회원 기본 아이디 값... */
    public static final String NON_MEMBER_ID = "" ;



    /** 가입비  기본값 ... */
    public static final String USIM_PRICE_TYPE_BASE = "B" ;

    /** 유심비 기본값... */
    public static final String JOIN_PRICE_TYPE_BASE = "I" ;

    /** AGENT CODE 기본값... AA00364 */
    public static final String AGENT_DEFALUT_CODE = "AA00364" ;

    /** AGENT CODE O원 렌탈... */
    public static final String AGENT_RENTAL_CODE = "AA00364" ;

    /**  O원 렌탈 접점코드 R123456789... */
    public static final String CNTPNT_SHOP_ID_RENTAL = "1100026442" ;

    /** 직영 온라인 AGENT_CODE*/
    public static final String NEW_AGENT_DEFALUT_CODE = "VKI0011" ;


    //############################# NICI 관련 ##############################################
    public static Map<String,String> NICE_ERROR_CODE_MAP ;

    /**
     * NICE로부터 부여받은 사이트 코드1
     */
    public static final String NICE_SITE_CODE ="G6280";



    /**
     * Nice 본이확인 서비스 테스트 모듈
     */
    public static final String NICE_TEST_SITE_CODE ="BQ743";

    /**
     * 성공시 이동될 URL
     */
    public static final String NICE_SUCC_RETURN_URL ="/nice/popNiceSucc.do";

    /**
     * 실패시 이동될 URL
     */
    public static final String NICE_FAILL_RETURN_URL ="/nice/popNiceFail.do";


    /**
     * NICE로부터 부여받은 사이트 코드
     */
    public static final String NICE_IPIN_SITE_CODE ="K406";

        /**
     * IPIN 성공시 이동될 URL
     */
    public static final String NICE_SUCC_IPIN_RETURN_URL ="/nice/popNiceIpinSucc.do";


    /**
     * 한국신용정보에서 고객사에 부여한 구분 id
     */
    public static String NICE_UID = "Nktisc";



    /** 전화상담 구매 예정지역 1차코드 */
    public static final String TEL_REGION_GRP_CD = "TELREGION";

    /** 전화상담 구매 연령코드 1차코드 */
    public static final String TEL_AGE_GRP_CD = "TELAGE";

    /** 전화상담 구매 상담상태 1차코드 */
    public static final String TEL_REPLY_GRP_CD = "TELREPLAY";

    /** 전화상담 구매 가입구분 */
    public static final String TEL_AGE_GUBUN_CD = "TELGUBUN";






  //############################# 외부 서식지 관련 ##############################################
    /** 외부서식지 구분
     * market : 오픈마켓
     * pps : 선불온라인
     * homeshop : 홈쇼핑
     * */
    /** 외부서식지 구분 : 오픈마켓 */
    public static final String SITE_REFERER_MARKET = "market";
    /** 외부서식지 구분 : 선불온라인 */
    public static final String SITE_REFERER_PPS = "pps";
    /** 외부서식지 구분 : 홈쇼핑 */
    public static final String SITE_REFERER_HOMESHOP = "homeshop";
    /** 외부서식지 구분 : 프리파아 */
    public static final String SITE_REFERER_PREPIA = "prepia";




    //##################공통코드########################################
    /** 신청진행상태 */
    public static final String CODE_APPFORM_STATE = "PSTATE00";


    //##################SMS TEMPLATE_ID########################################
    /** 신규/번호이동 신청서 완료 SMS문자 정보 키 값 17 */
    public static final int SMS_TEMPLATE_ID_NEW = 17;
    /** 기변 신청서 완료 SMS문자 정보 키 값 17 */
    public static final int SMS_TEMPLATE_ID_CHAGNE = 18;

    public static final int SMS_RETENTION_EXPIRATION = 45;

    public static final int SMS_CNTPNT_TEMPLATE_ID = 114;

    public static final int SMS_FRND_RECOMMEND_ID = 130;

    public static final int SPNSR_RECONTRACT = 134;

    public static final int SMS_NOW_DELIVERY_TEMPLATE_ID = 186;

    public static final int SMS_NOW_DELIVERY_FAIL_TEMPLATE_ID = 189;

    public static final int SMS_GIFT_PROMOTION_TEMPLATE_ID = 207;

    /** 온라인신청서를 통해 바로배송유심을 신청시 , 바로배송 결제 후 연동결과 실패시, 결제 취소 안내 209 */
    public static final int SMS_APP_NOW_DELIVERY_FAIL_TEMPLATE_ID = 209 ;

    /** 온라인신청서를 통해 바로배송유심을 신청시, 유심번호등록 안내 문자 210 */
    public static final int SMS_APP_NOW_DELIVERY_TEMPLATE_ID = 210 ;

    /** 요금제 변경 실패고객 문자안내 211 */
    public static final int SMS_PRICE_CHANGE_FAIL_TEMPLATE_ID = 211 ;

    /** 즉시 간편결제 후 문자 안내  SamplePay C: 신용카드, D: 실시간 계좌이체, P: 간편결제 */
    public static final int SAMPLE_PAY_TEMPLATE_ID = 227 ;

    /** 결합서비스 인증 안내*/
    public static final int COMBINE_AUTS_TEMPLATE_ID = 251 ;

    /** 마케팅 동의 고지*/
    public static final int MKRT_AGR_TEMPLATE_ID = 16 ;

    /** 서류등록 온라인 URL 인증번호 안내 */
    public static final int SMS_TEMPLATE_SEND_URL_OTP = 394 ;

    /** 자급제 보상 서비스 신청 접수 완료 문자 */
    // public static final int SMS_RWD_APPLY_SUCCESS_TEMPLATE_ID = 254 ;

    /** 카카오 알림톡 sender key */
    public static final String KAKAO_SENDER_KEY = "4d20cd61006833cfc264543493d47797f5e310b8";


    /** 직구관련 접점코드 */
    public static final String CNTPNT_SHOPID_PHONE_DIRECT = "1100034010";

    /** M SHOP 셀프개통에서 해피콜 개통 호출 REFERER */
    public static final String OPEN_MARKET_REFERER_M_SIMPLE = "mSimple"   ;//.equals(appformReqDto.getOpenMarketReferer())) {  //M SHOP 셀프개통에서 해피콜 개통 호출 ";


    /**  가입비 , USIM 밑줄 처리*/
    public static ArrayList<String> MNG_CNTPNT_STRIKE ;
    static {
        MNG_CNTPNT_STRIKE = new ArrayList<String>();
        MNG_CNTPNT_STRIKE.add("1100011741"); //모바일(직영온라인)- 1100011741
        MNG_CNTPNT_STRIKE.add("1100011744"); //모바일(사내특판)- 1100011744
        MNG_CNTPNT_STRIKE.add("1100017898"); //M모바일(허브)- 1100017898
    }

    static {
        OPER_TYPE_NAME_MAP  = new HashMap<String, String>();
        OPER_TYPE_NAME_MAP.put(OPER_TYPE_NEW,"신규개통");
        OPER_TYPE_NAME_MAP.put(OPER_TYPE_MOVE_NUM,"번호이동");
        OPER_TYPE_NAME_MAP.put(OPER_TYPE_CHANGE,"기기변경");
        OPER_TYPE_NAME_MAP.put(OPER_TYPE_EXCHANGE,"기기변경");
    }

    static {
        CSTMR_TYPE_NAME_MAP  = new HashMap<String, String>();
        CSTMR_TYPE_NAME_MAP.put(CSTMR_TYPE_NA,"내국인");
        CSTMR_TYPE_NAME_MAP.put(CSTMR_TYPE_NM,"내국인(미성년자)");
        CSTMR_TYPE_NAME_MAP.put(CSTMR_TYPE_FN,"외국인");
    }

    static {
        NICE_ERROR_CODE_MAP = new HashMap<String, String>();
        NICE_ERROR_CODE_MAP.put("0001","주민/사업자번호 틀림");
        NICE_ERROR_CODE_MAP.put("0002","카드사 전화요망");
        NICE_ERROR_CODE_MAP.put("0019","승인");
        NICE_ERROR_CODE_MAP.put("0120","인터넷 인증오류");
        NICE_ERROR_CODE_MAP.put("0157","사용개시 등록요망(1588-4500)");
        NICE_ERROR_CODE_MAP.put("0189","은행시스템 작업중");
        NICE_ERROR_CODE_MAP.put("0194","거래은행 전화요망");
        NICE_ERROR_CODE_MAP.put("01C3","탈퇴카드 사용불가");
        NICE_ERROR_CODE_MAP.put("01C5","비밀번호 미등록");
        NICE_ERROR_CODE_MAP.put("01C6","일시불 거래안됨");
        NICE_ERROR_CODE_MAP.put("01C7","거래제한 사용안됨");
        NICE_ERROR_CODE_MAP.put("01I9","카드사 전화요망");
        NICE_ERROR_CODE_MAP.put("01K2","카드발급 상태이상");
        NICE_ERROR_CODE_MAP.put("01K3","주민/사업자번호 틀림");
        NICE_ERROR_CODE_MAP.put("01K8","카드사 전화요망");
        NICE_ERROR_CODE_MAP.put("0249","인증오류 횟수초과");
        NICE_ERROR_CODE_MAP.put("0264","일시불거래 제한 회원요청");
        NICE_ERROR_CODE_MAP.put("0271","신규발급카드 사용요망");
        NICE_ERROR_CODE_MAP.put("02VA","이용불가 제휴카드");
        NICE_ERROR_CODE_MAP.put("0414","카드번호 오류");
        NICE_ERROR_CODE_MAP.put("0441","분실/도난 카드");
        NICE_ERROR_CODE_MAP.put("0455","비밀번호 틀림");
        NICE_ERROR_CODE_MAP.put("0457","해당카드 거래불가");
        NICE_ERROR_CODE_MAP.put("0475","비밀번호오류 초과");
        NICE_ERROR_CODE_MAP.put("04A4","주민번호 오류");
        NICE_ERROR_CODE_MAP.put("04A5","주민비밀번호 오류");
        NICE_ERROR_CODE_MAP.put("04A6","인증불가 가맹점");
        NICE_ERROR_CODE_MAP.put("0618","회원정보 오류");
        NICE_ERROR_CODE_MAP.put("0810","가맹점번호 부적당");
        NICE_ERROR_CODE_MAP.put("0916","기프트카드 서비스불가");
        NICE_ERROR_CODE_MAP.put("0973","비밀번호 횟수초과");
        NICE_ERROR_CODE_MAP.put("1280","허가된거래 아님");
        NICE_ERROR_CODE_MAP.put("12C4","미교부 카드");
        NICE_ERROR_CODE_MAP.put("3001","실명/주민번호 틀림");
        NICE_ERROR_CODE_MAP.put("3002","실명/주민번호 틀림");
        NICE_ERROR_CODE_MAP.put("3003","실명/주민번호 틀림");
        NICE_ERROR_CODE_MAP.put("3064","비밀번호 등록요망");
        NICE_ERROR_CODE_MAP.put("3064","비밀번호 오류초과");
        NICE_ERROR_CODE_MAP.put("3064","유효기간 만료");
        NICE_ERROR_CODE_MAP.put("4111","시스템 오류");
        NICE_ERROR_CODE_MAP.put("4873","서비스시간 종료");
        NICE_ERROR_CODE_MAP.put("4949","1분후 재조회 요망");
        NICE_ERROR_CODE_MAP.put("7002","카드사 연락요망");
        NICE_ERROR_CODE_MAP.put("7004","도난/분실");
        NICE_ERROR_CODE_MAP.put("7005","거래정지");
        NICE_ERROR_CODE_MAP.put("7083","카드번호오류");
        NICE_ERROR_CODE_MAP.put("7091","유효기간 입력오류");
        NICE_ERROR_CODE_MAP.put("7452","거래정지 카드");
        NICE_ERROR_CODE_MAP.put("7575","미등록 가맹점");
        NICE_ERROR_CODE_MAP.put("7576","CVC/CVV 검증오류");
        NICE_ERROR_CODE_MAP.put("7577","MS훼손카드");
        NICE_ERROR_CODE_MAP.put("8037","카드번호오류");
        NICE_ERROR_CODE_MAP.put("8310","비밀번호오류");
        NICE_ERROR_CODE_MAP.put("8311","주민등록번호상이");
        NICE_ERROR_CODE_MAP.put("8312","사업자번호상이");
        NICE_ERROR_CODE_MAP.put("8314","유효기간만료");
        NICE_ERROR_CODE_MAP.put("8323","거래정지카드");
        NICE_ERROR_CODE_MAP.put("8324","거래정지카드");
        NICE_ERROR_CODE_MAP.put("8326","사용한도초과");
        NICE_ERROR_CODE_MAP.put("8330","주민비밀번호틀림");
        NICE_ERROR_CODE_MAP.put("8350","도난/분실");
        NICE_ERROR_CODE_MAP.put("8373","카드사로문의요망");
        NICE_ERROR_CODE_MAP.put("8381","외환카드전산오류");
        NICE_ERROR_CODE_MAP.put("8384","CALL1566-6900");
        NICE_ERROR_CODE_MAP.put("8392","CALL1588-8300");
        NICE_ERROR_CODE_MAP.put("8397","비밀번호등록요망");
        NICE_ERROR_CODE_MAP.put("8398","사용제한가맹점");
        NICE_ERROR_CODE_MAP.put("8399","카드수령등록요망");
        NICE_ERROR_CODE_MAP.put("8410","서비스미적용회원");
        NICE_ERROR_CODE_MAP.put("8418","선불카드는 인증이 제한됩니다.");
        NICE_ERROR_CODE_MAP.put("8418","조회불가카드");
        NICE_ERROR_CODE_MAP.put("8418","현대카드는 인증이 제한됩니다.");
        NICE_ERROR_CODE_MAP.put("8423","인증대상카드아님");
        NICE_ERROR_CODE_MAP.put("8521","거래불가카드");
        NICE_ERROR_CODE_MAP.put("8532","시스템장애");
        NICE_ERROR_CODE_MAP.put("8833","신카드사용요망");
        NICE_ERROR_CODE_MAP.put("9000","체크카드는 인증이 제한됩니다.");
        NICE_ERROR_CODE_MAP.put("9001","신한카드는 인증이 제한됩니다.");
        NICE_ERROR_CODE_MAP.put("1707","액세스가 허용되지 않습니다.");
    }


    /** 금융 제휴 요금제 변경 상태 코드 - 요청*/
    public static final String  MCPRATE_CHG_HIST_CREATEYN_REQ = "";

    /** 금융 제휴 요금제 변경 상태 코드 - 타임아웃*/
    public static final String  MCPRATE_CHG_HIST_CREATEYN_TIMEOUT = "T";

    /** 금융 제휴 요금제 변경 상태 코드 - 응답 성공*/
    public static final String  MCPRATE_CHG_HIST_CREATEYN_SUCESS = "S";

    /** 금융 제휴 요금제 변경 상태 코드 - 응답 에러*/
    public static final String  MCPRATE_CHG_HIST_CREATEYN_FAIL = "F";

    /** 금융 제휴 요금제 변경 상태 코드 - 합본 성공(요금제 정상 변경 완료)*/
    public static final String  MCPRATE_CHG_HIST_CREATEYN_FINAL = "Y";

    /** 사용량 조회 2시간 제한 횟수 */
    public static final int CALL_USETIME_SVC_LIMIT = 20;

    /** 이벤트 구분 */
    public static final String CPA_EVENT_CD = "CPA EVENT";

    /** CPA이벤트 신청 - 이벤트 페이지 접속 */
    public static final String CPA_APPLY_1_EVENT_STATUS = "1";

    /** CPA이벤트 신청 - MARKETREQUEST페이지 접속 */
    public static final String CPA_APPLY_2_EVENT_STATUS = "2";

    /** CPA이벤트 신청 - 서식지 작성 페이지 접속 */
    public static final String CPA_APPLY_3_EVENT_STATUS = "3";

    /** CPA이벤트 신청 완료*/
    public static final String CPA_COMPLETE_EVENT_STATUS = "Y";


    /** PAYINFO - 요청*/
    public static final String  PAY_INFO_CREATEYN_REQ = "";

    /** PAYINFO - 타임아웃*/
    public static final String  PAY_INFO_CREATEYN_TIMEOUT = "T";

    /** PAYINFO - 응답 성공*/
    public static final String  PAY_INFO_CREATEYN_SUCESS = "S";

    /** PAYINFO - 이미지 생성 완료*/
    public static final String  PAY_INFO_CREATEYN_IMG = "Y";

    /** PAYINFO - 응답 에러*/
    public static final String  PAY_INFO_CREATEYN_FAIL = "F";


    /** 제휴이벤트  분류코드  */
    public static final String USIM_VIDEO_URL = "usimvideourl";

    /*public static void main(String[] args) {
        MNG_CNTPNT_STRIKE.contains("1100011741")
         MNG_CNTPNT_STRIKE.contains("11000117")
    }*/

    /** 관리자 패스워드 변경 주기  */
    public static final int ADMIN_PASS_CHG_DAY_OVER = 90;


    /** 오프라인 유심 FAQ 분류 코드 - 씨스페이스  */
    public static final String GROUP_CODE_DIRECTFAQB_CTG = "DIRECTFAQB";

    /** 오프라인 유심 FAQ 분류 코드 - 미니스톱 */
    public static final String GROUP_CODE_MINISTOP_CTG = "MINISTOPFAQ";

    /** 오프라인 유심 FAQ 분류 코드 - GS25 */
    public static final String GROUP_CODE_GS25_CTG = "GS25FAQ";

    /** 오프라인 유심 FAQ 분류 코드 - 7-11 */
    public static final String GROUP_CODE_711_CTG = "7-11FAQ";

    /** 오프라인 유심 FAQ 분류 코드 - 7-11 */
    public static final String GROUP_CODE_STORY_WAY_CTG = "storywayFAQ";

    /** 오프라인 유심 FAQ 분류 코드 - cu */
    public static final String GROUP_CODE_CU_CTG = "cuFAQ";

    /** 오프라인 유심 FAQ 분류 코드 - cu */
    public static final String GROUP_CODE_EMART24_CTG = "emart24FAQ";

    /** 유선 FAQ 분류 코드 */
    public static final String GROUP_CODE_WIRE_CTG = "WIREFAQ";

    /** NMCP_BOARD_BAS 구분
     * 씨스페이스 FAQ:66
     *  */
    public static final int BOARD_CTG_DIRECT_INFO = 66;

    /** NMCP_BOARD_BAS 구분
     * 미니스톱 FAQ:67
     *  */
    public static final int BOARD_CTG_MINISTOP_INFO = 67;

    /** NMCP_BOARD_BAS 구분
     * 유선 FAQ:68
     *  */
    public static final int BOARD_CTG_WIRE_INFO = 68;

    /** NMCP_BOARD_BAS 구분
     * GS25 FAQ:69
     *  */
    public static final int BOARD_CTG_GS25_INFO = 69;

    /** NMCP_BOARD_BAS 구분
     * 7-11 FAQ:70
     *  */
    public static final int BOARD_CTG_711_INFO = 70;

    /** 오프라인 유심 - 씨스페이스 */
    public static final String  DIRECT_USIM_CSPACE = "cspace";

    /** 오프라인 유심 - 미니스톱 */
    public static final String DIRECT_USIM_MINSTOP = "ministop";

    /** 오프라인 유심 - GS25 */
    public static final String DIRECT_USIM_GS25 = "gs25";

    /** 오프라인 유심 - 7-11 */
    public static final String DIRECT_USIM_711 = "7-11";

    /** 오프라인 유심 - storyway */
    public static final String DIRECT_USIM_STORY_WAY = "storyway";

    /** 오프라인 유심 - cu */
    public static final String DIRECT_USIM_CU = "cu";

    /** 오프라인 유심 - cu */
    public static final String DIRECT_USIM_EMART24 = "emart24";

    /**  오프라인 유심 URL 구분 리스트 */
    public static ArrayList<String> DIRECT_USIM_URL_GUBUN_LIST ;
    static {
        DIRECT_USIM_URL_GUBUN_LIST = new ArrayList<String>();
        DIRECT_USIM_URL_GUBUN_LIST.add(DIRECT_USIM_CSPACE);
        DIRECT_USIM_URL_GUBUN_LIST.add(DIRECT_USIM_MINSTOP);
        DIRECT_USIM_URL_GUBUN_LIST.add(DIRECT_USIM_GS25);
        DIRECT_USIM_URL_GUBUN_LIST.add(DIRECT_USIM_711);
        DIRECT_USIM_URL_GUBUN_LIST.add(DIRECT_USIM_STORY_WAY);
        DIRECT_USIM_URL_GUBUN_LIST.add(DIRECT_USIM_CU);
        DIRECT_USIM_URL_GUBUN_LIST.add(DIRECT_USIM_EMART24);
    }

    /**  오프라인 유심 판매점별 매인 롤링 배너 코드*/
    public static Map<String,String> DIRECT_USIM_MAIN_BANNER_CD ;
    static {
        DIRECT_USIM_MAIN_BANNER_CD  = new HashMap<String, String>();
        DIRECT_USIM_MAIN_BANNER_CD.put(DIRECT_USIM_CSPACE, "38");
        DIRECT_USIM_MAIN_BANNER_CD.put(DIRECT_USIM_MINSTOP, "39");
        DIRECT_USIM_MAIN_BANNER_CD.put(DIRECT_USIM_GS25, "56");
        DIRECT_USIM_MAIN_BANNER_CD.put(DIRECT_USIM_711, "58");
        DIRECT_USIM_MAIN_BANNER_CD.put(DIRECT_USIM_STORY_WAY, "69");
        DIRECT_USIM_MAIN_BANNER_CD.put(DIRECT_USIM_CU, "72");
        DIRECT_USIM_MAIN_BANNER_CD.put(DIRECT_USIM_EMART24, "74");
    }

    /**  오프라인 유심 판매점별 FAQ코드*/
    public static Map<String,String> DIRECT_USIM_FAQ_CTG_CD ;
    static {
        DIRECT_USIM_FAQ_CTG_CD  = new HashMap<String, String>();
        DIRECT_USIM_FAQ_CTG_CD.put(DIRECT_USIM_CSPACE, GROUP_CODE_DIRECTFAQB_CTG);
        DIRECT_USIM_FAQ_CTG_CD.put(DIRECT_USIM_MINSTOP, GROUP_CODE_MINISTOP_CTG);
        DIRECT_USIM_FAQ_CTG_CD.put(DIRECT_USIM_GS25, GROUP_CODE_GS25_CTG);
        DIRECT_USIM_FAQ_CTG_CD.put(DIRECT_USIM_711, GROUP_CODE_711_CTG);
        DIRECT_USIM_FAQ_CTG_CD.put(DIRECT_USIM_STORY_WAY, GROUP_CODE_STORY_WAY_CTG);
        DIRECT_USIM_FAQ_CTG_CD.put(DIRECT_USIM_CU, GROUP_CODE_CU_CTG);
        DIRECT_USIM_FAQ_CTG_CD.put(DIRECT_USIM_EMART24, GROUP_CODE_EMART24_CTG);
    }

    /**  모바일 오프라인 유심 판매점별 매인 롤링 배너 코드*/
    public static Map<String,String> DIRECT_USIM_MOBILE_MAIN_BANNER_CD ;
    static {
        DIRECT_USIM_MOBILE_MAIN_BANNER_CD  = new HashMap<String, String>();
        DIRECT_USIM_MOBILE_MAIN_BANNER_CD.put(DIRECT_USIM_CSPACE, "40");
        DIRECT_USIM_MOBILE_MAIN_BANNER_CD.put(DIRECT_USIM_MINSTOP, "41");
        DIRECT_USIM_MOBILE_MAIN_BANNER_CD.put(DIRECT_USIM_GS25, "57");
        DIRECT_USIM_MOBILE_MAIN_BANNER_CD.put(DIRECT_USIM_711, "59");
        DIRECT_USIM_MOBILE_MAIN_BANNER_CD.put(DIRECT_USIM_STORY_WAY, "70");
        DIRECT_USIM_MOBILE_MAIN_BANNER_CD.put(DIRECT_USIM_CU, "73");
        DIRECT_USIM_MOBILE_MAIN_BANNER_CD.put(DIRECT_USIM_EMART24, "75");
    }

    /** MSTORE 가입 가능 여부 코드  */
    public static Map<String,String> MSTORE_JOIN_LIMIT_CD ;
    static {
        MSTORE_JOIN_LIMIT_CD  = new HashMap<String, String>();
        MSTORE_JOIN_LIMIT_CD.put("REGULAR", "REGULAR");
        MSTORE_JOIN_LIMIT_CD.put("ASSOCIATE", "ASSOCIATE");
        MSTORE_JOIN_LIMIT_CD.put("NON", "NON");
    }

    /** Mstore 고객사 코드 */
    public static final String MSTORE_CMPYNO_CODE = "QAY";

    /** 윤리위반신고 관리자 답변 SMS 템플릿 ID*/
    public static final int SMS_TEMPLATE_ID_VIOLATION_REPORT_ANSWER = 42;

    /** 윤리위반신고 등록 완료 SMS 템플릿 ID*/
    public static final int SMS_TEMPLATE_ID_VIOLATION_REPORT_SAVE_CPL = 43;

    /** 윤리위반신고 등록 관리자 알림 SMS 템플릿 ID*/
    public static final int SMS_TEMPLATE_ID_VIOLATION_REPORT_SAVE_ADMIN_INFO = 44;

    /** 유심등록사이트 온라인 구매 접점 */
    public static final String GROUP_CODE_USIM_OFF_ONLINE_LIST = "usimoffonline";

    /** 유선 이벤트 카테고리 코드 */
    public static final String WIRE_EVENT_SBSTCTG_CD = "WE";

    /** 유선 상품 중분류 코드 */
    public static final String WIRE_PROD_2DEPTH_CD = "WIREPROD2DEPTH";

    /** 유선 상품 사업자 구분 코드 */
    public static final String WIRE_PROD_CORP = "WIREPRODCORP";

    /** 유선 상담 신청상태 */
    public static final String WIRE_CONSEL_STATUS = "WireConselStatus";

    /** 유선 상담 진행상태 */
    public static final String WIRE_PRO_STATUS = "WireProStatus";


    /** 휴대폰 안심 서비스 insrProdCD USIM중고형 */
    public static final String INSR_PROD_OLD_CD = "USIMOLDFO";

    ///////////////// CEO패키지 //////////////////////////////////////////
    /** CEO패키지 관심상품 */
    public static final String CEO_PRODUCT_LIST = "CeoProductList";

    /** CEO패키지 신청상태 */
    public static final String CEO_COUNSEL_STATUS = "CeoCounselStatus";

    /** CEO패키지 진행상태 */
    public static final String CEO_PRODUCT_STATUS = "CeoProStatus";

    /** 번호이동정보_휴대폰_할부금상태  분납지속(LMS청구) */
    public static final String MOVE_ALLOTMENT_STAT_CODE1 ="AD"  ;//
    /** 번호이동정보_휴대폰_할부금상태  분납지속(LMS미청구)  */
    public static final String MOVE_ALLOTMENT_STAT_CODE2 ="AD2"  ;//

    /** 당첨후기 */
    public static final String REVIEW_EVENT_CD = "ReviewEventInfo";
    public static final String REVIEW_PERCENT = "ReviewPercent";
    public static final String REVIEW_AGENT_CD = "RviewAgentCode";

    public static final String COUP_STAT_CD = "CoupStatCd";

    /** 부가서비스 과세/비과세 구분 */
    public static final String GROUP_CODE_SOC_FREE_TAX_LIST = "SocFreeTaxList";

    public static final String STORE_NFC_VIEW_CKECK = "StoreNFCViewCkeck";

    /** 셀프개통신규(010) 다회선 제한 요금제는 제외 요금제 코드 */
    public static final String GROUP_CODE_EXCEPTION_LIST_PRICE_CD = "ExceptionListPriceCd";

    /** 본인인증 구분 코드 */
    public static final String INSR_PROD = "InsrProd";
    public static final String SMS_AUTH = "smsAuth";
    public static final String CUST_AUTH = "custAuth";
    public static final String PASS_AUTH = "passCheck";
    public static final String CUST_AGENT_AUTH = "agentAuth";
    public static final String OPEN_AUTH = "openAuth";

    public static final String RWD_PROD = "rwdProd";


    //인증한것 그대로 사용 처리
    public static final String BASE_AUTH = "baseAuth";

    /** 메인개편 */
    /** 메인노출 인기 요금제 카테고리 */
    public static final String MAIN_RATE_PLAN = "mainRatePlan";
    /** 메인 노출 추천 휴대폰 요금제 카테고리 */
    public static final String MAIN_PHONE_RATE_PLAN = "mainPhoneRatePlan";

    /** 포인트처리코드 */
    public static final String POINT_TRT_SAVE = "S"; // 포인트 적립
    public static final String POINT_TRT_USE = "U"; // 포인트 사용
    public static final String POINT_TRT_EXTINCTION = "E";  // 포인트 차감(소멸)

    /** 포인트사용사유코드 */
    public static final String POINT_RSN_CD_U11 = "U11";
    public static final String POINT_RSN_CD_U12 = "U12";
    public static final String POINT_RSN_CD_U13 = "U13";
    public static final String POINT_RSN_CD_U21 = "U21";
    public static final String POINT_RSN_CD_U31 = "U31";
    public static final String POINT_RSN_CD_U32 = "U32";
    public static final String POINT_RSN_CD_U41 = "U41";
    public static final String POINT_RSN_CD_U51 = "U51";
    public static final String POINT_RSN_CD_U61 = "U61";
    public static final String POINT_RSN_CD_U62 = "U62";
    public static final String POINT_RSN_CD_U99 = "U99";



    /** 게시판 카테고리 코드 */
    public static final int BOARD_NOTICE_CTG = 51; // 공지사항케시판
    public static final int BOARD_REGFORM_CTG = 53; // 서식지다운로드게시판
    public static final int BOARD_PRIVACY_CTG = 92; // 서식지다운로드게시판

    /** 1:1 문의 코드*/
    public static final String INQUIRY_MEMBER = "UI0010";
    public static final String INQUIRY_NON_MEMBER = "UI0020";

    /** 약관 코드 */
    public static final String TERMS_MEM_CD = "TERMSMEM"; //회원가입 약관
    public static final String TERMS_FORM_CD = "FORMREQUIRED"; //서식지 약관
    public static final String TERMS_FORM_SELECT_CD = "FORMSELECT"; //서식지(선택) 약관

    /** 피혜예방 가이드  메인카테고리*/
    public static final String PRAVACY_CTG = "UI0031";
    public static final String PRAVACY_INFO_CTG = "UI0032";

    /**피혜예방 가이드 서브카테고리*/
    public static final String PRAVACY_NOTICE = "UI00310100"; // 공지사항
    public static final String PRIVACY_NEW_INFO = "UI00310200"; // 최신폐해예방 정보
    public static final String PRIVACY_PREVENTION = "UI00310300"; // 피혜사례별 예방법
    public static final String PRIVACY_MOVIE = "UI00320100"; // 동영상으로 보기
    public static final String PRIVACY_WEBTOON = "UI00320200"; // 웹툰으로 보기
    public static final String PRIVACY_PREVENTION_EDU = "UI00320300"; // 피해예방교육

    /** 은행 코드*/
    public static final String BANK_CD = "BNK"; //은행 목록

    /**결합 약관 코드 */
    public static final String COMBI_CD = "TERMSCOM";

    /**안내 소개 */
    public static final String INFO_CD = "INFO";

    /**결합 컨텐츠 소개 */
    public static final String COMBI_INFO_CD = "COMB00001";

    /** 데이터쉐어링 소개 */
    public static final String SHAR_INFO_CD = "COMB00002";

    /**함께쓰기 소개 */
    public static final String DATA_INFO_CD = "COMB00003";


    /** 데이터쉐어링 약관 코드 */
    public static final String SHAR_CD = "TERMSSHA";

    /** 데이터 함께쓰기 약관코드 */
    public static final String SDAT_CD = "TERMSDAT";

    /** 친구 초대 약관 코드 */
    public static final String TERMS_FRNDINV_CD = "INFO"; // 친구초대 약관

    /** 요금제변경 약관코드 */
    public static final String FAR_CD = "FORMSELECT";

    /**불법TM 수신차단 부가서비스 code */
    public static final String REG_SVC_CD_1 = "NOSPAM4";

    /** 번호도용 차단서비스 부가서비스 code  */
    public static final String REG_SVC_CD_2 = "STLPVTPHN";

    /** 로밍 하루종일 ON 부가서비스 code */
    public static final String REG_SVC_CD_3 = "PL2078760";

    /** 요금할인 서비스 부가서비스 code */
    public static final String REG_SVC_CD_4 = "PL2079770";

    /** 로밍 하루종일 ON 투게더(대표) 부가서비스 code */
    public static final String REG_SVC_CD_5 = "PL2079777";

    /** 요금제 변경 불가능한 SOC_CODE LIST */
    public static final String  GROUP_CODE_EXCEPTION_LIST_SOC_CODE = "ExceptionListSocCode";

    /** 색상 코드 */
    public static final String COLOR_CODE = "GD0008";

    /** 제휴카드 요금제코드 */
    public static final String RATE_FAR_CD = "ClauseJehuRatecd";

    /** 쉐어링코드 */
    public static final String SHARINGRATE = "SHARINGRATE";

    /** 소액결제 상태 코드 */
    public static final String MPAY_STATUS_CD = "MPAY";

    /** 아무나 결합 */
    public static final String COMB_LINK_CD = "COMB00008";

    /** 아무나 가족 결합 */
    public static final String COMB_FAM_LINK_CD = "COMB00009";

    /** 친구 초대 M스토어 약관 동의 코드 - 포탈 */
    public static final String FRND_MSTORE_TERMS_CD_1 = "FrndMstoreAgree1";

    /** 친구 초대 M스토어 약관 동의 코드 - 모바일 */
    public static final String FRND_MSTORE_TERMS_CD_2 = "FrndMstoreAgree2";

    /** CSS_VERSION  */
    public static final String CSS_VERSION = "260310";

    /** 카카오 본인인증 Product_Code  */
    public static final String KAKAO_IDENTITY_VERIFICATION = "K1110";

    public static final String KAKAO_AUTH = "K";
    public static final String KAKAO_AUTH_TYPE = "kakaoAuth";

    /** 이벤트 참여 프로모션 코드  */
    public static final String EVENT_JOIN_CD = "EventJoinInfo";

    public static final String Event_Category = "EventCategory";

    /** 회원가입 프로모션 코드  */

    public static final String USER_PROMOTION = "UserPromotion";

    /** 관심사 코드  */
    public static final String INTEREST = "Interest"; //회원가입 약관

    /** M캐시 응답 코드 */
    public static final String MCASH_RESPONSE_SUCCESS = "200001";   // 성공

    /** M캐시 회원상태 코드 */
    public static final String MCASH_USER_STATUS_ACTIVE = "A";  // 정상
    public static final String MCASH_USER_STATUS_CANCEL = "C";  // 해지
    public static final String MCASH_USER_STATUS_WAIT = "S";    // 해지 대기

    /** M캐시 연동 유형 코드 */
    public static final String MCASH_EVENT_JOIN = "JOIN";       // 가입
    public static final String MCASH_EVENT_REPAIR = "REPAIR";   // 복구
    public static final String MCASH_EVENT_CHANGE = "CHANGE";   // 변경
    public static final String MCASH_EVENT_CANCEL = "CANCEL";   // 해지
    public static final String MCASH_EVENT_CHECK = "CHECK";     // 조회

    /** M캐시 연동 유형 상세 코드 */
    public static final String MCASH_EVENT_JOIN_NEW = "NEW";            // 가입 - 신규가입
    public static final String MCASH_EVENT_JOIN_RE = "RE";              // 가입 - 재가입
    public static final String MCASH_EVENT_REPAIR_RPR = "RPR";          // 복구 - 복구
    public static final String MCASH_EVENT_CHANGE_CNTR = "CNTR";        // 변경 - 회선변경
    public static final String MCASH_EVENT_CANCEL_PORTAL = "PORTAL";    // 해지 - 포탈탈퇴
    public static final String MCASH_EVENT_CANCEL_MCASH = "MCASH";      // 해지 - M캐시탈회
    public static final String MCASH_EVENT_CANCEL_CAN = "CAN";          // 해지 - 회선해지
    public static final String MCASH_EVENT_CANCEL_MCN = "MCN";          // 해지 - 명의변경
    public static final String MCASH_EVENT_CHECK_CASH = "CASH";         // 조회 - 잔여캐시조회
    public static final String MCASH_EVENT_CHECK_USER = "USER";         // 조회 - 회원정보조회

    /** 제휴제안 카테고리 상세 코드 */
    public static final String JEHU_SUGGESTION_CATEGORY = "JehuSuggestionCategory";

    /** Acen 연동 사용 대리점 */
    public static final String ACEN_TARGET_AGENT = "AcenTargetAgent";

    /*쿠폰 카테고리*/
    public static final String COUPON_CATEGORY = "CouponCategory";

    /* 서비스 중단 시간 그룹 코드 */
    public static final String SERVICE_DOWNTIME = "ServiceDowntime";

    /* 리뷰 유형 그룹 코드 */
    public static final String REVIEW_TYPE_CD = "ReviewType";

    /* M쇼핑할인 사용후기 그룹 코드 */
    public static final String MCASH_REVIEW_EVENT = "McashReviewEvent";

    /** 기간제한 그룹코드 */
    public static final String CMM_PERIOD_LIMIT = "CmmPeriodLimit";

    /** 셀프개통 010신규 기간제한 예외 IP */
    public static final String NAC_SELF_LIMIT_EXCEPTION_IP = "NacSelfLimitExceptionIp";

    /** 대리점 셀프개통 가입신청서 부가설정 */
    public static final String AGENT_FORM_SETTING = "AgentFormSetting";


    public static final String REGULAR_MEMBER = "01"; // 정회원

    public static final String FQC_PLCY_MSN_CODE = "FqcPlcyMsnList"; // 프리퀀시 정책 미션 공통코드

    public static final String NP_NSC_EXCEPTION = "NpNscException";  // 사전동의 예외 통신사

    /** 홈페이지 1:1 문의 및 예약상담 연동대상 서버 */
    public static final String ACEN_ARS_SETTING = "AcenArsSetting";

    /** 제휴용 위탁온라인 신청서대상 목록 / 기존 위탁온라인 서식지,대리점셀프개통  STEP4 친구추천 영역 삭제 */
    public static final String JEHU_AGENT_LIST = "JehuAgentList";

    /** 제휴용 위탁온라인 상담사개통 신청서 유심 미보유 선택시 유심등록 URL 전송문자 템플릿 */
    public static final int SMS_JEHU_USIM_REG_TEMPLATE_ID = 376;

    public static final String ADSVC_CODE = "G";          // 일반 부가서비스
    public static final String ROAMING_ADSVC_CODE = "R";  // 로밍 부가서비스

    public static final String POPUP_CONTENT_TYPE_EDITOR = "EDITOR";
    public static final String POPUP_CONTENT_TYPE_IMAGE = "IMAGE";

    public static final int USIM_BUY_CHILD_AGE = 14;        // 유심구매 미성년자 나이 (14세 미만)
    public static final int USIM_BUY_MINOR_AGENT_AGE = 19;  // 유심구매 법정대리인 나이 (19세 이상)

    /*요금제 체감가 제휴혜택 공통코드*/
    public static final String JEHU_PRICE_REFLECT = "JehuPriceReflect";

    /*요금제 체감가 프로모션혜택 공통코드*/
    public static final String PROMO_PRICE_REFLECT = "PromoPriceReflect";

    /** EVENT_CODE 유심무상교체 접수 가능 여부조회 (T01) */
    public static final String EVENT_CODE_REPLACE_USIM_PRE_CHK = "T01";

    /** 신규 셀프개통 완료 후 개통한 회선 안내문자 */
    public static final int SMS_COMPLETE_TEMPLATE_ID = 433;

    /** EVENT_CODE 고객 안면인증 대상 여부 조회 (FT0)  */
    public static final String EVENT_CODE_FATH_TGT_YN = "FT0";
    /** EVENT_CODE 고객 안면인증 URL 요청 (FS8)  */
    public static final String EVENT_CODE_FATH_URL_RQT = "FS8";
    /** EVENT_CODE 고객 안면인증 내역 조회 (FS9)  */
    public static final String EVENT_CODE_FATH_URL_RETV = "FS9";
    public static final String EVENT_CODE_FATH_SKIP = "FT1";

    public static Map<String,String> FATH_RETV_CD_VAL;
    static {
        FATH_RETV_CD_VAL = new HashMap<>();
        FATH_RETV_CD_VAL.put("01", "REGID");
        FATH_RETV_CD_VAL.put("02", "DRIVE");
        FATH_RETV_CD_VAL.put("03", "HANDI");
        FATH_RETV_CD_VAL.put("04", "MERIT");
        FATH_RETV_CD_VAL.put("05", "NAMEC");
        FATH_RETV_CD_VAL.put("06", "FORGN");
    }

    public static Map<String,String> FATH_SBSC_DIV_CD;
    static {
        FATH_SBSC_DIV_CD = new HashMap<>();
        FATH_SBSC_DIV_CD.put("NAC3", "1");
        FATH_SBSC_DIV_CD.put("MNP3", "2");
        FATH_SBSC_DIV_CD.put("MCN", "3");
        FATH_SBSC_DIV_CD.put("HCN3", "4");
        FATH_SBSC_DIV_CD.put("HDN3", "4");
    }
}
