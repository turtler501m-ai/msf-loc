package com.ktmmobile.msf.domains.form.common.exception.msg;


/**
 * @Class Name : ExceptionMsgConstant
 * @Description : 예외처리 메세지 공통 상수
 *
 * @author : ant
 * @Create Date : 2015. 12. 30.
 */
public final class ExceptionMsgConstant {

    public static final String F_BIND_EXCEPTION = "비정상적인 접근입니다";

    public static final String COMMON_EXCEPTION = "서비스 처리중 오류가 발생 하였습니다.";

    public static final String DB_EXCEPTION = "DB 처리중 오류가 발생 하였습니다.";

    public static final String SAMPLE_EXCEPTION = "시퀀스값은 0을 입력할수 없습니다.";

    public static final String BIDING_EXCEPTION = "바인딩 처리중 오류가 발생하였습니다.";

    public static final String INVALID_PARAMATER_EXCEPTION = "인자값 오류가 발생 하였습니다. ";

    public static final String INVALID_REFERER_EXCEPTION = "잘못된 접근입니다. ";

    public static final String FILE_UPLOAD_EXCEPTION = "파일업로드중 오류가 발생하였습니다.";

    public static final String EXCEED_FILE_SIZE_EXCEPTION = "업로드 파일용량 사이즈를 초과 하였습니다.";

    public static final String DENINED_FILE_TYPE_EXCEPTION = "업로드 할수없는 파일 유형입니다.";

    public static final String NO_SESSION_EXCEPTION = "SESSION 정보가 없습니다 ";

    public static final String NO_FAIL_SESSION_EXCEPTION = "SESSION 정보가 없거나 인증번호가 일치하지 않습니다.";

    public static final String NO_ADMIN_SESSION_EXCEPTION = "관리자 로그인 정보가 없습니다 ";

    public static final String AUTH_NUM_DIFFERENT_EXCEPTION = "인증번호가 일치하지 않습니다.\n인증번호를 다시 입력해 주세요.";

    public static final String AUTH_NUM_TIMER_EXCEPTION = "인증번호가 입력 3분 초과 하였습니다.\nSMS 재발송해 주세요.";

    public static final String ADMIN_FIRST_PASS_SET_EXCEPTION = "최초 로그인시 패스워드를 다시 설정해 주세요.";

    public static final String ADMIN_PASS_CHG_90DAY_OVER_EXCEPTION = "패스워드 변경 90일 경과 패스워드를 다시 설정해 주세요.";

    public static final String ADMIN_ACCOUNT_STATUS_STOP_EXCEPTION = "5일 미 접속으로 계정 정지 상태입니다. \n관리자에게 문의해 주세요.";

    public static final String ADMIN_NONE_CHG_PASS_EXCEPTION = "현재 패스워드와 같은 패스워드로 변경할수 없습니다.";

    public static final String ADMIN_NO_MATCHE_PASSWORD_PATTERN = "패스워드 패턴을 확인해 주세요.";

    public static final String DUPLICATE_SQL_EXCEPTION = "이미 등록되있거나 중복된 값이 존재합니다.";

    public static final String NO_EXSIST_SALE_PLCY_CD_EXCEPTION = "정책정보가 존재하지 않습니다.";

    public static final String NO_EXSIST_SALE_PLCY_CD_RATE_EXCEPTION = "정책정보의 요금제 정보가 존재하지 않습니다.";

    public static final String NO_EXSIST_SALE_PRDT = "해당상품의 MSP 판매정보가 존재하지 않습니다.";

    public static final String NO_EXSIST_PROD_BAS_EXCEPTION = "상품정보가 존재하지 않습니다.";

    public static final String SCAN_SERVER_SEND_EXCEPTION = "스캔서버 전송이 실패 하였습니다.";

    public static final String ACE_256_DECRYPT_EXCEPTION = "복호화  실패 하였습니다.";

    public static final String SCAN_XML_SAVE_EXCEPTION = "스캔 XML 생성이 실패 하였습니다.";

    public static final String NOTFOUND_FILE_EXCEPTION = "파일이 존재하지 않습니다.";

    public static final String ACE_256_ENC_EXCEPTION = "암호화(ace256Enc) 오류 발생 ";

    public static final String ACE_APP_ENC_EXCEPTION = "APP 암호화 오류 발생";

    public static final String ACE_APP_DECRYPT_EXCEPTION = "APP 복호화  실패 하였습니다.";

    public static final String NICE_ENC_EXCEPTION = "NICE 암호화 오류 발생";

    public static final String NICE_DECRYPT_EXCEPTION = "NICE 복호화 오류 발생";

    public static final String NICE_CERT_EXCEPTION = "본인 인증한 정보가 틀립니다.";

    public static final String NICE_CERT_EXPIR_EXCEPTION = "SMS 인증 만료시간이 초과되었습니다.";

    public static final String NICE_CERT_EXCEPTION_INSR = "본인 인증한 정보가 없습니다.";

    public static final String NICE_CERT_EXCEPTION_SMS = "고객정보와 휴대폰 인증 정보가 일치하지 않습니다.";

    public static final String NICE_CERT_EXCEPTION_INSR2 = "본인인증 시 고객정보와 휴대폰 인증 고객정보가 일치하지 않습니다.";

    public static final String NICE_CERT_EXCEPTION_RWD = "본인인증 시 고객정보와 휴대폰 인증 고객정보가 일치하지 않습니다.";

    public static final String NICE_CERT_REQ_NULL_EXCEPTION = "본인 인증 요청 정보가 없습니다.";

    public static final String NICE_CERT_AGENT_EXCEPTION_INSR = "법정대리인 인증한 정보가 없습니다.";

    public static final String REGNO_TEEN_EXCEPTION = "청소년 주민등록 번호가 아닙니다.";

    public static final String REGNO_ADULT_EXCEPTION = "성인 주민등록 번호가 아닙니다.";

    public static final String CHANGE_AUT_EXCEPTION = "기기변경 휴대폰 번호가 인증한 정보와 일치하지 않습니다.";

    public static final String NO_EXSIST_RATE = "요금제 정보가 존재하지 않습니다.";

    public static final String SMS_TIME_OUT = "인증 시간이 만료되었습니다.";

    public static final String IO_EXCEPTION = "파일 생성이 실패하였습니다.";

    public static final String OWNER_CHECK_EXCEPTION = "사용자 검증에 실패 하였습니다.";

    public static final String INVALID_PRICE_EXCEPTION = "가격정보가 일치하지 않습니다.";

    public static final String USIM_PRICE_EXCEPTION = "가입비 유심비 정보가 없습니다.";

    public static final String NO_EXSIST_INST_NOM = "할부/약정기간 정보가 존재하지 않습니다.";

    public static final String CLOSE_EVENT_EXCEPTION = "종료된 이벤트 입니다.";

    public static final String ERROR_READ_SIMPLECAPCHA_KORWAVE = "심플캡챠 음성파일을 읽는중 오류가 발생 하였습니다.";

    public static final String INIPAY_RESULT_EXCEPTION = "결제 시스템 처리중 오류가 발생 하였습니다.";

    public static final String NOTFOUND_REQUEST_DATA_EXCEPTION = "서식지 정보가 없습니다.";

    public static final String UPDATE_REQUEST_DATA_EXCEPTION = "서식지 정보 UPDATE를 실패 하였습니다.";

    public static final String NO_FRONT_SESSION_EXCEPTION = "로그인이 필요 합니다.";

    public static final String NO_FRONT_SESSION_ORDER_EXCEPTION = "주문하신 내역은 홈페이지 회원가입 후 확인이 가능합니다.";

    public static final String NO_EXSIST_MCP_MODEL_INFO = "모델 정보가 없습니다.(MCP)";

    public static final String NO_MATCHE_PASSWORD = "첫번째 비밀번호와 두번째 비밀번호 확인이 맞지 않습니다.";

    public static final String NO_MATCHE_PASSWORD_PATTERN = "비밀번호 패턴을 확인해 주세요.";

    //SocketTimeoutException
    public static final String SOCKET_TIMEOUT_EXCEPTION = "처리중인 업무가 있습니다. 잠시 후 다시 시도해 주시기 바랍니다."; //"서비스 점검중입니다. 잠시후 다시 이용해 주세요."; //"서버(M-Platform) 점검중 입니다. 잠시후 다시 시도 하기기 바랍니다.(SocketTimeout)";
    //payinfo remakeImg fail;
    public static final String PAY_INFO_FAIL = "변경신청서 재생성에 실패하였습니다";
    //exist image
    public static final String PAY_INFO_EXIST_IMG = "생성된 이미지가 존재합니다.";


   //SocketTimeoutException  responseXml.isEmpty()) M-PlatForm
    public static final String MPLATFORM_RESPONEXML_EMPTY_EXCEPTION = "처리중인 업무가 있습니다. 잠시 후 다시 시도해 주시기 바랍니다."; //"서비스 점검중입니다. 잠시후 다시 이용해 주세요."; // "서버(M-Platform) 점검중 입니다. 잠시후 다시 시도 하기기 바랍니다.(XML EMPTY)";

    //numberView01
    public static final String NUMBER_CHANGE_TIME_EXCEPTION = "번호변경 가능 시간은 평일 오전10시~오후5시까지 가능합니다. (주말 공휴일은 변경불가)";


    public static final String TIME_OVERLAP_EXCEPTION = "처리중인 업무가 있습니다. 잠시 후 다시 시도해 주세요.";

    //번호 변경 선불 요금제 가입자 제한
    public static final String NUMBER_CHANGE_PREPAYMENT_EXCEPTION = "선불 요금제 가입자는 번호변경이 불가능 합니다.";

    public static final String USIM_REGISTRATION_EXCEPTION = "유효하지 않은 유심입니다.";

    //온라인에서 부가서비스 해지 불가 안내
    public static final String NO_ONLINE_CAN_CHANGE_ADD = "해지할 수 없는 부가서비스는 고객센터를 통해 해지 가능합니다.";

    public static final String SIMPLE_OPEN_TIME_EXCEPTION = "셀프 개통 가능한 시간은 신규(08:00~21:50), 번호이동(10:00~19:50) 입니다.";

    public static final String SIMPLE_CNTPNT_SHOPID_EXCEPTION = "셀프 개통 가능한 접점코드가 아닙니다.";

    public static final String SIMPLE_OPEN_DATE_EXCEPTION = "셀프 개통 가능한 날짜가 아닙니다.";

    public static final String OVER_LIMIT_EXCEPTION = "개통희망번호 조회 20회 초과하셨습니다.\n신청서를 처음부터 다시 작성해 주십시요.";

    public static final String NOT_FULL_MEMBER_EXCEPTION = "정회원 인증 후 이용하실 수 있습니다.";

    public static final String NOT_FOUND_DATA_EXCEPTION = "DATA 정보가 없습니다.";

    public static final String PASS_AUTH_EXCEPTION = "계좌인증한 정보가 없습니다.";

    public static final String NOT_ENGG_INFO_EXCEPTION = "약정 정보가 없습니다.";

    public static final String NOT_REQUEST_INFO_EXCEPTION = "약정 정보가 없습니다.";

    public static final String OVER_LIMIT_OPEN_FORM_EXCEPTION = "동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회 존재 합니다. \n고객센터에 문의 하시기 바랍니다.";

    public static final String USIM_REGI_SHOPID_EXCEPTION = "유심신청 가능한 접점코드가 아닙니다.";

    public static final String SOCCODE_BIDING_EXCEPTION = "요금제 정보가 없습니다."; //SocCode

    public static final String ON_OFF_TYPE_BIDING_EXCEPTION = "지원하는 ON_OFF_TYPE 이 없습니다."; //OnOffType

    public static final String GIFT_LIMIT_EXCEPTION = "사은품 선택 개수를 초과하였습니다.";

    public static final String GIFT_EXSIST_EXCEPTION = "사은품 정보가 없습니다.";

    public static final String POINT_OWN_EXCEPTION = "포인트 사용 요청 정보와 로그인한 사용자 정보가 일치하지 않습니다.";
    public static final String POINT_NULL_EXCEPTION = "포인트 정보가 없습니다.";
    public static final String POINT_OVER_EXCEPTION = "포인트가 부족합니다."; //"사용하는 포인트가 남은 포인트보다 높습니다.";

    public static final String POINT_PROD_OVER_EXCEPTION = "사용하는 포인트가 결제 금액보다 높습니다.";
    public static final String POINT_VALUE_EXCEPTION = "결제 금액이 일치하지 않습니다.";
    public static final String POINT_PHONE_PRICE_EXCEPTION = "휴대폰 금액이 없습니다.";

    public static final String OWN_EXCEPTION = "로그인한 사용자 정보가 일치하지 않습니다.";

    public static final String SELF_PHONE_CODE_NULL_EXCEPTION = "자급제 상품코드가 존재 하지 않습니다.";

    public static final String MAX_DC_OVER_EXCEPTION = "최대할인금액을 초과 하였습니다.";

    public static final String TEMP_FORM_INFO_NULL_EXCEPTION = "임시저장한 정보를 확인할 수 없습니다.";

    public static final String SHARE_NUM_NULL_EXCEPTION = "쉐어링 신청가능한 전화번호가 없습니다.";

    public static final String PHONE_EID_NULL_EXCEPTION = "휴대폰 EID 정보가 없습니다.";

    public static final String STEP_AUTH_MOVE_EXCEPTION = "이미 케이티엠모바일에 가입되어 있어 번호이동이 불가합니다.";

    public static final String STEP_INFO_EXCEPTION = "[STEP] 본인 인증 정보가 일치하지 않습니다. 이 메시지가 계속되면 처음부터 다시 시도해 주세요.";
    public static final String STEP_INFO_NULL_EXCEPTION = "[STEP] 필수 정보가 존재하지 않습니다.";
    public static final String STEP_CNT_EXCEPTION = "[STEP] 필수 단계가 누락됐습니다. 새로고침 후 다시 시도해 주세요.";

    public static final String SIMPLE_OPEN_PLATFORM_EXCEPTION = "APP 점검으로 일부 메뉴의 서비스 제공이 중단되었습니다.<br>" +
                                                                "https://www.ktmmobile.com 에서 서비스 이용이 가능합니다.<br>" +
                                                                "불편을 끼쳐 드려 죄송합니다.";

    public static final String AUTH_TYPE_EXCEPTION = "이용 불가한 간편본인인증 수단입니다.";

    public static final String ALREADY_JOIN_EXCEPTION = "이미 가입 중인 서비스입니다.";

    public static final String STOLEN_IP_EXCEPTION = "접속하신 IP는 부정사용 개통 신고되어 있는<br>" +
                                                     "IP로 셀프개통이 제한되어 있습니다.<br>" +
                                                     "가입을 원하시면 『상담사 개통 신청』 을 통해<br>" +
                                                     "가입신청 가능하며 IP 접속제한 해제를<br>" +
                                                     "희망하실 경우 고객센터(114/무료)로<br>" +
                                                     "연락주시면 본인인증 후 제한 해제 가능합니다.";

    public static final String NAC_SELF_IP_EXCEPTION = "상담사 개통으로만 신청 가능합니다.<br/>『상담사 개통 신청』 을 이용 바랍니다.";
    public static final String NAC_AGENT_SELF_IP_EXCEPTION = "셀프개통 이용이 불가합니다.";

    public static final String ESIM_SELF_ABUSE_IMEI_EXCEPTION = "입력하신 단말기는 셀프개통 이용이 불가합니다.<br/>" +
                                                                "자세한 내용은 고객센터(1899-5000)로 문의 부탁 드립니다.";

    public static final String SELF_LIMIT_EXCEPTION = "※ 신규가입은 명의당 30일이내 1회선만 가입 가능합니다.<br>" +
                                                      "추가 가입은 최근 가입하신 KT M모바일 회선 가입일을 기준으로 30일 경과된 시점에 신청 부탁드립니다.";

    public static final String CONTAINS_GOLD_NUMBER_EXCEPTION = "입력하신 가입희망번호 중<br>" +
                                                                "골드번호가 포함되어 있습니다.<br>" +
                                                                "희망번호 수정 후 다시 시도 부탁드립니다.";
    public static final String FATH_CERT_EXPIR_EXCEPTION = "안면인증 시간이 만료되었습니다.<br>" +
                                                           "새로고침 후 다시 시도 부탁드립니다.";
    
    public static final String FATH_CERT_RESLT1_EXCEPTION = "안면인증 결과가 아직 확인되지 않습니다.<br>" +
                                                            "잠시 후 다시 시도해주세요. <br>";
    public static final String FATH_CERT_RESLT2_EXCEPTION = "안면인증 유효기간(영업일 7일)이 초과되었습니다.<br>" +
                                                            "다시 안면인증을 진행해주세요";
    public static final String FATH_LIMIT_EXCEPTION = "안면인증 URL받기 5회 초과하셨습니다.<br>" +
                                                        "새로고침 후 다시 진행해주세요.";
}
