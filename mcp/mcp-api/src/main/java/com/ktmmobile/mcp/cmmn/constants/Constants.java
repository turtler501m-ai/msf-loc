package com.ktmmobile.mcp.cmmn.constants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Constants {

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


    //개통 간소화 관련 OSST
    /** OSST 연동 결과 성공 */
    public static final String OSST_SUCCESS = "0000";


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

    /** EVENT_CODE 사전체크 및 고객생성 결과 확인(PC2) */
    public static final String EVENT_CODE_PC_RESULT = "PC2";

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




    public static final String  DTL_CD_OBJ_BASE = "BASE";
    public static final String  DTL_CD_OBJ_5G = "5G";

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

    /** 제휴 서비스를 위한 동의*/
    public static final String CLAUSE_CODE_06 = "02";

    /** 신용정보 조회·이용·제공에 대한 동의서  clauseConfidenceFlag*/
    public static final String CLAUSE_CONFIDENCE_CODE = "05";


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

    /** 메인개편 */
    /** 메인노출 인기 요금제 카테고리 */
    public static final String MAIN_RATE_PLAN = "mainRatePlan";
    /** 메인 노출 추천 휴대폰 요금제 카테고리 */
    public static final String MAIN_PHONE_RATE_PLAN = "mainPhoneRatePlan";

    /** 요금제 변경 불가능한 SOC_CODE LIST */
    public static final String  GROUP_CODE_EXCEPTION_LIST_SOC_CODE = "ExceptionListSocCode";


}
