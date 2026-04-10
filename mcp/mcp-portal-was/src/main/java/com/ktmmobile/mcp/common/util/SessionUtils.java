package com.ktmmobile.mcp.common.util;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.common.dto.*;
import com.ktmmobile.mcp.common.dto.db.McpRequestOsstDto;
import com.ktmmobile.mcp.common.mplatform.dto.MoscMvnoComInfo;
import com.ktmmobile.mcp.content.dto.MyShareDataReqDto;
import com.ktmmobile.mcp.document.receive.dto.DocRcvSessionDto;
import com.ktmmobile.mcp.fath.dto.FathSessionDto;
import com.ktmmobile.mcp.fqc.dto.FqcBasDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import nl.captcha.Captcha;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SessionUtils {

    private static final Logger logger = LoggerFactory.getLogger(SessionUtils.class);

    public static final String USER_SESSION = "USER_SESSION";
    public static final String ROLE_MENU_COOKIE = "roleMenu";
    public static final String NICE_AUT_COOKIE = "NICE_AUT_COOKIE";

    /* 카카오 본인인증 요청 값(이름,생년월일,전화번호) , PASS 본인인증 요청 값(이름,전화번호) 임시 저장 */
    public static final String NICE_AUT_REQEUST_TMP = "NICE_AUT_REQEUST_TMP";
    public static final String NICE_AUT_INSR_COOKIE = "NICE_AUT_INSR_COOKIE";  //분실파손 휴대폰 인증
    public static final String NICE_AUT_OPEN_COOKIE = "NICE_AUT_OPEN_COOKIE";  //약정 만료 알림 인증
    public static final String NICE_AUT_RWD_COOKIE = "NICE_AUT_RWD_COOKIE";    //자급제 보상서비스 휴대폰 인증

    public static final String NICE_AUT_BASE_COOKIE = "NICE_AUT_BASE_COOKIE";    //인증 한것 그대로 저장
    public static final String COMM_AUTH_SMS_INFO = "COMM_AUTH_SMS_INFO";
    public static final String CHANGE_AUT_COOKIE = "CHANGE_AUT_COOKIE";
    public static final String INI_PRICE_COOKIE = "INI_PRICE";
    public static final String ORDER_SESSION = "ORDER_SESSION";
    public static final String REQUEST_CHECK_COOKIE = "REQUEST_CHECK_COOKIE";
    public static final String REQUEST_CNTR_LIST_COOKIE = "REQUEST_CNTR_LIST";
    public static final String APP_FORM_SESSION = "APP_FORM_SESSION";
    public static final String OSST_DTO_SESSION = "OSST_DTO_SESSION";
    public static final String CNT_SESSION = "CNT_SESSION";
    public static final String NICE_PARAM_SESSION = "NICE_PARAM_SESSION";
    public static final String KTM_REFERER_SESSION = "ktmReferer ";   //최조 유입 경로 저장 처리
    public static final String OTP_INFO_SESSION = "otpCallNo ";   //otp인증번호
    public static final String ETC_DOMAIN_REFERER_SESSION = "ETC_DOMAIN_REFERER_SESSION";
    public static final String CURR_MENU_SESSION = "CURR_MENU_SESSION"; //현재 URL 메뉴 정보
    public static final String BEFORE_MENU_SESSION = "BEFORE_MENU_SESSION"; //현재 URL 메뉴 정보
    public static final String CURR_MENU_URL_SESSION = "CURR_MENU_URL_SESSION"; //현재 MENU URL 메뉴 정보
    public static final String DORMANCY_SESSION = "DORMANCY_SESSION";
    public static final String NICE_AGENT_AUT_COOKIE = "NICE_AGENT_AUT_COOKIE";
    public static final String APP_UUID_SESSION = "APP_UUID_SESSION";

    public static final String TEST_LOGIN = "TEST_LOGIN";
    public static final String TEST_LOGIN_ID = "TEST_LOGIN_ID";

    public static final String DUMMY_SESSION = "DUMMY_SESSION";
    public static final String CURR_PHONE_NCN = "CURR_PHONE_NCN";

    public static final String COALITION_INFLOW_CODE = "COALITION_INFLOW_CODE"; // 유입 URL 제휴코드

    public static final String RECAPTCHA_SESSION= "RECAPTCHA_SESSION"; // 구글캡챠 인증 여부

    public static final String RECAPTCHA_SMS_SESSION= "RECAPTCHA_SMS_SESSION"; // 구글캡챠 SMS 대체 인증 여부

    public static final String CERT_SEQ = "CERT_SEQ"; //본인인증시퀀스

    public static final String PAGE_TYPE = "PAGE_TYPE"; //현재메뉴를 나타냄

    public static final String RECOMMEND = "RECOMMEND_SESSION"; //친구초대 ID

    public static final String NICE_AUTH_ENC_KEY= "NICE_AUTH_ENC_KEY"; // NICE 인증 암호화키

    public static final String NONMEMBER_AUTH_SHARING= "NONMEMBER_AUTH_SHARING"; // 비로그인 데이터쉐어링 고객 인증정보

    public static final String USER_PROMOTION = "USER_PROMOTION"; // 회원가입 프로모션

    public static final String ONLINE_SESSION = "ONLINE_SESSION"; // 해피콜 선택 여부

    public static final String MARSKING_SESSION = "maskingSession"; //마스킹 세션

    public static final String MARSKING_SESSION_TIMEOUT = "maskingSessionTimeout";  // 마스킹 세션 타임아웃 설정

    public static final String AGENT_FORM_LINK = "AGENT_FORM_LINK";  // 대리점 신청서 URL
    public static final String ONE_TIME_POPUP = "ONE_TIME_POPUP";  // 일회성 팝업
    public static final String COMBINE_LIST = "COMBINE_LIST";  // 결합대상회선

    public static final String JEHU_PARTNER_TYPE = "JEHU_PARTNER_TYPE"; // 제휴사 구분(서식지용)
    public static final String PERSONAL_AUTH_SMS_INFO = "PERSONAL_AUTH_SMS_INFO";  // 개인화 URL SMS 인증 세션
    public static final String PERSONAL_URL = "PERSONAL_URL";  // 개인화 URL 세션

    public static final String DATA_SHARING_SESSION= "DATA_SHARING_SESSION"; //  데이터쉐어링 정보

    public static final String DOCUMENT_RECEIVE = "DOCUMENT_RECEIVE"; //  서류등록

    public static final String NICE_AUT_MOBILE_SESSION = "NICE_AUT_MOBILE_SESSION"; //셀프개통+신규가입 셀프개통 휴대폰인증 세션

    public static final String FATH_TRANSAC_ID = "FATH_TRANSAC_ID"; // 안면인증 트랜잭션 ID
    public static final String FATH_CMPLT_NTFY_DT = "FATH_CMPLT_NTFY_DT"; // 안면인증 수행일자
    public static final String FATH_TRY_COUNT = "FATH_TRY_COUNT"; // 안면인증 연동 시도횟수
    public static final String FATH_FT0_GLOBAL_ID = "FATH_FT0_GLOBAL_ID"; // FT0 글로벌ID
    public static final String FATH_FS8_GLOBAL_ID = "FATH_FS8_GLOBAL_ID"; // FS8 글로벌ID
    public static final String FATH_FS9_GLOBAL_ID = "FATH_FS9_GLOBAL_ID"; // FS9 글로벌ID
    public static final String FATH_SESSION = "FATH_SESSION"; // 안면인증 세션
    /**
     * <pre>
     * 설명     : 동일한 5초 이네에 동일한 요청 여부를 확인 한다.
     * @param
     * @return: void
     * </pre>
     */
    public static boolean overlapRequestCheck(ResponseSuccessDto responseSuccessDto) {
        return overlapRequestCheck(responseSuccessDto,5);
    }

    /**
     * <pre>
     * 설명     : 동일한 2초 이네에 동일한 파일 다운로드 요청
     * @param
     * @return: void
     * </pre>
     */
    public static boolean overlapRequestCheck(ResponseSuccessDto responseSuccessDto,int secondTime) {
        ResponseSuccessDto baseDto = getRequestTime();
        if (baseDto != null && baseDto.getRedirectUrl().equals(responseSuccessDto.getRedirectUrl())) {
            Date nowDate = new Date();
            Date requestTime = baseDto.getRequestTime();
            long diff = nowDate.getTime() - requestTime.getTime();

            if (diff < secondTime * 1000) {
                return true;
            }
        }

        responseSuccessDto.setRequestTime(new Date());
        saveRequestTime(responseSuccessDto) ;
        return false;
    }


    /**
     * <pre>
     * 설명     : 요청시간을 SESSION저장 한다.
     * @param
     * @return: void
     * </pre>
     */
    public static void saveRequestTime(ResponseSuccessDto responseSuccessDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(REQUEST_CHECK_COOKIE, responseSuccessDto);
    }


    /**
     * <pre>
     * 설명     : 저장한 요청시간을 요청
     * @return
     * </pre>
     */
    public static ResponseSuccessDto getRequestTime() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Object tempObj = session.getAttribute(REQUEST_CHECK_COOKIE);

        if (tempObj == null) {
            return null;
        }
        return (ResponseSuccessDto)tempObj;
    }



    /**
     * <pre>
     * 설명     : Inicis 결제 금액 저장
     * @param iniPrice
     * @return: void
     * </pre>
     */
    public static void saveIniPriceCooke(String iniPrice) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(INI_PRICE_COOKIE, EncryptUtil.ace256Enc(iniPrice));
    }

    /**
     * <pre>
     * 설명     : 메뉴권한 리스트 정보 session 저장
     * </pre>
     */
    public static void saveRoleMenuList(List<RoleMenuDto> listObj) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(ROLE_MENU_COOKIE, listObj);
    }


    /**
     * <pre>
     * 설명     : 메뉴권한 리스트 정보 session return
     * @return
     * </pre>
     */
    public static List<RoleMenuDto> getRoleMenuList(String paraDepthKey, String paraPrntsKey) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        @SuppressWarnings("unchecked")
        List<RoleMenuDto> listObj = (List<RoleMenuDto>)session.getAttribute(ROLE_MENU_COOKIE);

        if (listObj == null) {
            return null;
        }

        List<RoleMenuDto> rtnList = new ArrayList<RoleMenuDto>();

        if ("1".equals(paraDepthKey)) {
            for (RoleMenuDto roleMenuDto : listObj ) {
                if ("1".equals(roleMenuDto.getDepthKey())) {
                    rtnList.add(roleMenuDto);
                }
            }
        } else {
            for (RoleMenuDto roleMenuDto : listObj ) {
                if (paraDepthKey.equals(roleMenuDto.getDepthKey()) && paraPrntsKey.equals(roleMenuDto.getPrntsKey())) {
                    rtnList.add(roleMenuDto);
                }
            }
        }
        return rtnList;
    }





    /**
     * <pre>
     * 설명     : 사용자 정보 session return
     * @return
     * </pre>
     */
    public static UserSessionDto getUserCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object userSessionDto = session.getAttribute(USER_SESSION);
        if (userSessionDto == null) {
            return null;
        }

        return (UserSessionDto)userSessionDto;
    }



    /**
     * <pre>
     * 설명     : 사용자 정보 session 생성
     * @param nonMemReqBean
     * </pre>
     */
    public static void saveUserSession(UserSessionDto userSessionDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60);  //60분 세션 설정...

        session.setAttribute(USER_SESSION, userSessionDto);
        session.setAttribute("passWord", "");	//비밀번호는 세션에 담지않는다.

        try {
            sessionToCookie();
        } catch (Exception e) {
            logger.debug("###ERROR### : ");
        }
    }

    /**
     * <pre>
     * 설명     : 서직지 정보 session return
     * @return
     * </pre>
     */
    public static AppformReqDto getAppformSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Object appformReqDto = session.getAttribute(APP_FORM_SESSION);

        if (appformReqDto == null) {
            return null;
        }

        return (AppformReqDto)appformReqDto;
    }


    /**
     * <pre>
     * 설명     : 서직지 정보 session 생성
     * @param nonMemReqBean
     * </pre>
     */
    public static void saveAppformDto(AppformReqDto appformReqDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60);  //60분 세션 설정...

        session.setAttribute(APP_FORM_SESSION, appformReqDto);
        try {
            sessionToCookie();
        } catch (Exception e) {
            logger.debug("###ERROR### : ");
        }
    }

    /**
     * <pre>
     * 설명     : 개통정보
     * @return
     * </pre>
     */
    public static McpRequestOsstDto getOsstDtoSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Object mcpRequestOsstDto = session.getAttribute(OSST_DTO_SESSION);

        if (mcpRequestOsstDto == null) {
            return null;
        }

        return (McpRequestOsstDto)mcpRequestOsstDto;
    }


    /**
     * <pre>
     * 설명     : 개통정보 session 생성
     * @param nonMemReqBean
     * </pre>
     */
    public static void saveOsstDto(McpRequestOsstDto mcpRequestOsstDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60);  //60분 세션 설정...

        session.setAttribute(OSST_DTO_SESSION, mcpRequestOsstDto);
        try {
            sessionToCookie();
        } catch (Exception e) {
            logger.debug("###ERROR### : ");
        }
    }


    /**
     * <pre>
     * 설명     : Cnt Session
     * @return
     * </pre>
     */
    public static int getCntSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object sesObject = session.getAttribute(CNT_SESSION);

        if (sesObject == null) {
            return 0;
        }

        return (Integer)sesObject;
    }


    /**
     * <pre>
     * 설명     : 개통정보 Cnt Session
     * @param int
     * </pre>
     */
    public static void saveCntSession(int cntSession) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60);  //60분 세션 설정...

        session.setAttribute(CNT_SESSION, cntSession);
        try {
            sessionToCookie();
        } catch (Exception e) {
            logger.debug("###ERROR### : ");
        }
    }


    /**
     * <pre>
     * 설명     : 비회원로그인 정보 session 생성
     * @param nonMemReqBean
     * </pre>
     */
    public static void saveOrderSession(UserSessionDto userSessionDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(ORDER_SESSION, userSessionDto);

        try {
            sessionToCookie();
        } catch (Exception e) {
            logger.debug("###ERROR### : ");
        }
    }


    /**
     * <pre>
     * 설명     : 사용자 정보 session return
     * @return
     * </pre>
     */
    public static UserSessionDto getOrderSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Object userSessionDto = session.getAttribute(ORDER_SESSION);

        if (userSessionDto == null) {
            return null;
        }

        return (UserSessionDto)userSessionDto;
    }


    /**
     * 프론트 로그인 세션이 있는지 확인
     * @param request
     * @return 로그인 세션이 존재하면 true
     */
    public static boolean hasLoginUserSessionBean() {

        UserSessionDto userSessionDto = getUserCookieBean();

        if (userSessionDto == null) {
            return false;
        }
        return true;
    }

    /**
     * <pre>
     * 설명     : NICE 인증 정보 저장
     * @param niceResDto
     * @return: void
     * </pre>
     */
    public static void saveNiceRes(NiceResDto niceResDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(NICE_AUT_COOKIE, niceResDto);
    }



    /**
     * <pre>
     * 설명     :   NICE 인증  session return
     * @return
     * </pre>
     */
    public static NiceResDto getNiceResCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_AUT_COOKIE);

        if (niceResDto == null) {
            return null;
        }

        return (NiceResDto)niceResDto;
    }

    /**
     * <pre>
     * 설명    : 본인인증 요청 값 임시 저장 세션 (카카오인증: 이름,생년월일, 전화번호 / PASS인증: 이름, 전화번호)
     * @param niceResDto
     * @return: void
     * </pre>
     */
    public static void saveNiceReqTmp(NiceResDto niceResDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(NICE_AUT_REQEUST_TMP, niceResDto);
    }


    /**
     * <pre>
     * 설명     :   본인인증 요청 값 session return
     * @return
     * </pre>
     */
    public static NiceResDto getNiceReqTmpCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_AUT_REQEUST_TMP);

        if (niceResDto == null) {
            return null;
        }

        return (NiceResDto)niceResDto;
    }

    /**
     * <pre>
     * 설명     : NICE 대리인 인증 정보 저장
     * @param niceResDto
     * @return: void
     * </pre>
     */
    public static void saveNiceAgentRes(NiceResDto niceResDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(NICE_AGENT_AUT_COOKIE, niceResDto);
    }



    /**
     * <pre>
     * 설명     :   NICE 대리인 인증  session return
     * @return
     * </pre>
     */
    public static NiceResDto getNiceAgentResCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_AGENT_AUT_COOKIE);

        if (niceResDto == null) {
            return null;
        }

        return (NiceResDto)niceResDto;
    }

    /**
     * <pre>
     * 설명     : 분실파손 휴대폰 인증  정보 저장
     * @param niceResDto
     * @return: void
     * </pre>
     */
    public static void saveNiceInsrRes(NiceResDto niceResDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(NICE_AUT_INSR_COOKIE, niceResDto);
    }



    /**
     * <pre>
     * 설명     :   분실파손 휴대폰 인증 인증  session return
     * @return
     * </pre>
     */
    public static NiceResDto getNiceInsrResCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_AUT_INSR_COOKIE);

        if (niceResDto == null) {
            return null;
        }

        return (NiceResDto)niceResDto;
    }


    /**
     * <pre>
     * 설명     : 약정 만료 알림 인증
     * @param niceResDto
     * @return: void
     * </pre>
     */
    public static void saveNiceOpenRes(NiceResDto niceResDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(NICE_AUT_OPEN_COOKIE, niceResDto);
    }



    /**
     * <pre>
     * 설명     :   약정 만료 알림 인증   session return
     * @return
     * </pre>
     */
    public static NiceResDto getNiceOpenResCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_AUT_OPEN_COOKIE);

        if (niceResDto == null) {
            return null;
        }

        return (NiceResDto)niceResDto;
    }


    /**
     * 2023.02.28 hsy
     * 자급제 보상 서비스 휴대폰 인증 정보 저장
     * @param niceResDto
     * @return: void
     */
    public static void saveNiceRwdRes(NiceResDto niceResDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(NICE_AUT_RWD_COOKIE, niceResDto);
    }



    /**
     * 2023.02.28 hsy
     * 자급제 보상 서비스 휴대폰 인증 정보 session return
     * @return: NiceResDto
     */
    public static NiceResDto getNiceRwdResCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_AUT_RWD_COOKIE);

        if (niceResDto == null) {
            return null;
        }

        return (NiceResDto)niceResDto;
    }



    public static void saveNiceBasRes(NiceResDto niceResDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(NICE_AUT_BASE_COOKIE, niceResDto);
    }




    public static NiceResDto getNiceBasResCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_AUT_BASE_COOKIE);

        if (niceResDto == null) {
            return null;
        }

        return (NiceResDto)niceResDto;
    }

    /**
     * NICE 인증 여부
     * @param request
     * @return 로그인 세션이 존재하면 true
     */
    public static boolean hasNiceRes() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_AUT_COOKIE);

        if (niceResDto != null) {
            return true;
        } else {
            return false;
        }


    }
    /**
     * <pre>
     * 설명     : 기기변경 고객인증
     * @param niceResDto
     * @return: void
     * </pre>
     */
    public static void saveChangeAut(JuoSubInfoDto juoSubInfoDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60);  //60분 세션 설정...
        session.setAttribute(CHANGE_AUT_COOKIE, juoSubInfoDto);
    }


    /**
     * <pre>
     * 설명     :   기기변경 고객인증  session return
     * @return
     * </pre>
     */
    public static JuoSubInfoDto getChangeAutCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object juoSubInfoDto = session.getAttribute(CHANGE_AUT_COOKIE);
        if (juoSubInfoDto == null) {
            return null;
        }

        return (JuoSubInfoDto)juoSubInfoDto;
    }

    /**
     * <pre>
     * 설명     : 인증 SMS 정보 session 생성
     * @param authSmsDto
     * </pre>
     */
    public static void setAuthSmsSession(AuthSmsDto authSmsDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
        atr.append("_").append(authSmsDto.getMenu());
        session.setAttribute(atr.toString(), authSmsDto);
    }


    /**
     * <pre>
     * 설명     : 인증 SMS 정보 session null 초기화
     * @param authSmsDto
     * </pre>
     */
    public static void setAuthSmsSetNullSession(AuthSmsDto authSmsDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
        atr.append("_").append(authSmsDto.getMenu());
        session.setAttribute(atr.toString(), null);
    }






    /**
     * <pre>
     * 설명     : 인증 SMS 정보 session 리턴
     * @param authSmsDto
     * </pre>
     */
    public static void checkAuthSmsSession(AuthSmsDto authSmsDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
        atr.append("_").append(authSmsDto.getMenu());
        logger.debug("GET SMS SESSION : "+atr.toString());
        logger.debug("GET SMS PHONE : "+authSmsDto.getPhoneNum());

        AuthSmsDto sessionAuthSmsDto = (AuthSmsDto)session.getAttribute(atr.toString());
        logger.debug(atr.toString());
        if(sessionAuthSmsDto == null) {
            authSmsDto.setMessage("인증번호가 없습니다. 인증번호를 다시 받아 주세요.");
            authSmsDto.setResult(false);
            return;
        } else {
            if(!sessionAuthSmsDto.getPhoneNum().equals(authSmsDto.getPhoneNum())) {
                authSmsDto.setMessage("휴대폰 번호가 다릅니다. 인증번호를 다시 받아 주세요.");
                authSmsDto.setResult(false);
                return;
            } else {
                if(sessionAuthSmsDto.getMenu().equals(authSmsDto.getMenu())) {

                    if(authSmsDto.isCheck()) {
                        if(sessionAuthSmsDto.isResult()) {
                            String startDay = sessionAuthSmsDto.getStartDate();
                            String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
                            int btw = 0;
                            try {
                                btw = DateTimeUtil.minsBetween(startDay, today, "yyyyMMddHHmmss");
                            } catch (ParseException e) {
                                authSmsDto.setMessage("서비스가 지연되고 있습니다. 다시 시도해 주세요.");
                                authSmsDto.setResult(false);
                                return;
                            }
                            if(btw < 30) {
                                authSmsDto.setMessage("정상인증");
                                authSmsDto.setResult(true);
                                if(authSmsDto.isDelete()) {
                                    session.removeAttribute(atr.toString());
                                }
                                return;
                            } else {
                                authSmsDto.setMessage("인증 후 30분이 경과 되었습니다. 다시 인증 해 주세요.");
                                authSmsDto.setResult(false);
                                return;
                            }
                        } else {
                            authSmsDto.setMessage("인증정보가 없습니다. 다시 확인해 주세요.");
                            authSmsDto.setResult(false);
                            return;
                        }
                    } else {
                        String startDay = sessionAuthSmsDto.getStartDate();
                        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
                        int btw = 0;
                        try {
                            btw = DateTimeUtil.minsBetween(startDay, today, "yyyyMMddHHmmss");
                        } catch (ParseException e) {
                            authSmsDto.setMessage("서비스가 지연되고 있습니다. 다시 시도해 주세요.");
                            authSmsDto.setResult(false);
                            return;
                        }

                        if(btw < 3) {
                            if(sessionAuthSmsDto.getAuthNum().equals(authSmsDto.getAuthNum())) {
                                authSmsDto.setMessage("인증완료");
                                authSmsDto.setResult(true);
                                sessionAuthSmsDto.setResult(true);
                                //session.removeAttribute(COMM_AUTH_SMS_INFO);
                                return;
                            } else {
                                authSmsDto.setMessage("인증번호가 맞지않습니다.");
                                authSmsDto.setResult(false);
                                return;
                            }
                        } else {
                            authSmsDto.setMessage("인증번호의 유효기간이 지났습니다.");
                            authSmsDto.setResult(false);
                            return;
                        }
                    }
                } else {
                    authSmsDto.setMessage("인증번호가 없습니다. 인증번호를 다시 받아 주세요.");
                    authSmsDto.setResult(false);
                    return;
                }
            }
        }

    }



    /**
     * <pre>
     * 설명     : 휴대폰 회선관리 리스트를 session 저장
     * @param nonMemReqBean
     * </pre>
     */
    public static void saveCntrList(List<McpUserCntrMngDto> cntrList) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(REQUEST_CNTR_LIST_COOKIE, cntrList);
    }


    /**
     * <pre>
     * 설명     :   휴대폰 회선관리 리스트를 session n return
     * @return
     * </pre>
     */
    public static List<McpUserCntrMngDto> getCntrList() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        @SuppressWarnings("unchecked")
        List<McpUserCntrMngDto> listObj = (List<McpUserCntrMngDto>)session.getAttribute(REQUEST_CNTR_LIST_COOKIE);

        if (listObj == null) {
            return null;
        }

        return listObj;
    }


    /**
     * 모든 세션 정보 삭제
     * @param request
     */
    public static void invalidateSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getResponse();

        HttpSession session = request.getSession();
        logger.debug("======== session id1:{}", session.getId());
        session.invalidate();
        logger.info("======== LOGOUT session id2:{}", session.getId());

        // 전체 쿠키 삭제하기
        Cookie[] cookies = request.getCookies() ;
        if(cookies != null){
            for(int i=0; i < cookies.length; i++){
                if(cookies[i].getName().indexOf("ktm_") > -1) {

                    // 쿠키 이름에서 CRLF 문자 제거
                    String safeCookieName = cookies[i].getName().replace("\r\n", "");
                    // 만료된 쿠키 생성
                    Cookie cookie = new Cookie(safeCookieName, "");
                    // 쿠키의 유효시간을 0으로 설정하여 만료시킨다.
                    cookie.setMaxAge(0);
                    cookie.setSecure(true); // 응답 헤더에 추가
                    response.addCookie(cookie);

                    //cookies[i].setMaxAge(0) ;
                    //cookies[i].setSecure(true);

                    // 응답 헤더에 추가한다
                    //response.addCookie(cookies[i]) ;
                }
            }
        }
        logger.debug("======== session id3:{}", session.getId());
    }


    /**
     * <pre>
     * 설명     :   SMS session return
     * @return
     * </pre>
     */
    public static AuthSmsDto getAuthSmsBean(AuthSmsDto authSmsDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
        atr.append("_").append(authSmsDto.getMenu());
        Object returnDto = session.getAttribute(atr.toString());

        if (returnDto == null) {
            return null;
        }

        return (AuthSmsDto)returnDto;
    }

    public static boolean authSmsBeanCheck(AuthSmsDto authSmsDto) {
        AuthSmsDto sessionAuthSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);

        if (sessionAuthSmsDto == null) {
            return false;
        }

        if(null != sessionAuthSmsDto.getSendTime() && !"".equals(sessionAuthSmsDto.getSendTime())){
            Long resultTime = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date()))-Long.parseLong(sessionAuthSmsDto.getSendTime());
            if(resultTime>(sessionAuthSmsDto.getDuration()*60)){
                return false;
            }
        } else {
            return false;
        }

        if (sessionAuthSmsDto.getSmsNo().equals(authSmsDto.getSmsNo())
                && sessionAuthSmsDto.getPhoneNum().equals(authSmsDto.getPhoneNum())) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * <pre>
     * 설명     : 프리퀀시 정책 정보
     * @return: void
     * </pre>
     */
    public static void saveFqcBas(FqcBasDto fqcBasDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("FQC_BAS_COOKIE", fqcBasDto);
    }



    /**
     * <pre>
     * 설명     :   프리퀀시 정책   session return
     * @return
     * </pre>
     */
    public static FqcBasDto getFqcBasCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object itemObj = session.getAttribute("FQC_BAS_COOKIE");

        if (itemObj == null) {
            return null;
        }

        return (FqcBasDto)itemObj;
    }



    /**
     * <pre>
     * 설명     :   세션값을 암호화 해서 쿠키에 저장한다.
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * </pre>
     */
    public static void sessionToCookie() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getResponse();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();


        UserSessionDto userSession = getUserCookieBean();
        if(userSession != null && request.getHeader("User-Agent").indexOf("M-Mobile-App") != -1) {
            boolean chkId = false;
            boolean chkNm = false;
            boolean chkDiv = false;
            Field[] fields = userSession.getClass().getDeclaredFields();
            for(Field f : fields) {
                f.setAccessible(true);
                try {
                    if(f.get(userSession) != null) {
                        String tmpString = EncryptUtil.ace256Enc(f.get(userSession).toString());

                        if (tmpString != null && !"".equals(tmpString) ) {
                            Cookie cookie = new Cookie("ktm_"+f.getName(), tmpString);
                            cookie.setSecure(true);
                            response.addCookie(cookie);                // 쿠키저장
                            if("userId".equals(f.getName())) chkId = true;
                            if("name".equals(f.getName())) chkNm = true;
                            if("userDivision".equals(f.getName())) chkDiv = true;
                        }


                    }
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                }
            }

            // 회원 아이디, 이름, 정회원 여부 필드가 있으면 쿠키 저장한다.
            if(chkId && chkNm && chkDiv) {
                Cookie cookie = new Cookie("ktm_encKey", "00001");
                cookie.setSecure(true);
                //cookie.setMaxAge(60*60*24*365);            // 쿠키 유지 기간(이부분이 없으면 브라우저 종료시 사라짐)
                cookie.setPath("/");                               // 모든 경로에서 접근 가능하도록
                //cookie.setDomain(".ktmmobile.com");//이부분을 적용하면 서브 도메인간 공유 가능
                response.addCookie(cookie);                // 쿠키저장
            }
        }

//       if(!"LOCAL".equals(NmcpServiceUtils.getPropertiesVal("SERVER_NAME"))){
//    	   Cookie[] cookieArray = request.getCookies();
//    	   if (cookieArray != null) {
//    		   for (Cookie cookie : cookieArray) {
//    			   if ("JSESSIONID".equals(cookie.getName())) {
//    				   response.setHeader(org.springframework.http.HttpHeaders.SET_COOKIE, "JSESSIONID="+cookie.getValue()+";path=/;SameSite=None;Secure;HttpOnly;");
//    			   }
//    		   }
//    	   }
//       }

    }


    /**
     * <pre>
     * 설명     :   쿠키값을 복호화 해서 세션에 저장한다.
     * @param HttpServletRequest request
     * </pre>
     */
    public static void cookieToSession()  {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        if(!SessionUtils.hasLoginUserSessionBean()) {
            boolean checkCookie = false;

            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if("ktm_encKey".equals(cookie.getName())) {
                    if(cookie.getValue() != null && !"".equals(cookie.getValue())) {
                        checkCookie = true;
                        break;
                    }
                }
            }

            //ktm_encKey 값이 있으면 쿠키를 세션으로 만드는 작업을 시작한다.
            if(checkCookie) {
                UserSessionDto userSession = new UserSessionDto();
                Field[] fields = userSession.getClass().getDeclaredFields();

                try {
                    for (Cookie cookie : cookies) {
                        String tmpName = "";
                        if(cookie.getName().indexOf("ktm_") > -1) tmpName = cookie.getName().replaceAll("ktm_", "");
                        for(Field f : fields) {
                            if(tmpName.equals(f.getName())) {
                                if(cookie.getValue() != null && !"".equals(cookie.getValue()) ) {
                                    String decryptStr = EncryptUtil.ace256Dec(cookie.getValue());
                                    f.setAccessible(true);
                                    if(f.getType() == int.class) {
                                        if(decryptStr != null && !"".equals(decryptStr)) {
                                            f.set(userSession, Integer.parseInt(decryptStr));
                                        }
                                    } else {
                                        f.set(userSession, decryptStr);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } catch (CryptoException e) {
                    logger.error(e.getMessage());
                }  catch (IllegalArgumentException e) {
                    logger.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                }



                // 회원 아이디, 이름, 정회원 여부 필드가 있으면 세션 저장한다.
                if(userSession.getUserId() != null && userSession.getUserDivision() != null && userSession.getName() != null) {
                    saveUserSession(userSession);
                }
            }
        }
    }

    /**
     * <pre>
     * 설명     :   captcha  session return
     * @return
     * </pre>
     */
    public static Captcha getCaptcha() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object captcha = session.getAttribute(Captcha.NAME);
        if (captcha == null) {
            return null;
        }

        return (Captcha)captcha;
    }

    public static void setCaptcha(Captcha captcha) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(Captcha.NAME, captcha);
    }

    /**
     * <pre>
     * 설명     : 보안 문자 리턴
     * @return
     * </pre>
     */
    public static String getAnswer() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object tempObj = session.getAttribute("getAnswer");
        if (tempObj == null) {
            return null;
        }
        return (String)tempObj;
    }

    /**
     * <pre>
     * 설명     : NICE PARAM 정보 저장
     * @param niceResDto
     * @return: void
     * </pre>
     */
    public static void saveNiceParam(NiceResDto niceResDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(NICE_PARAM_SESSION, niceResDto);
    }



    /**
     * <pre>
     * 설명     :   NICE PARAM 인증  session return
     * @return
     * </pre>
     */
    public static NiceResDto getNiceParam() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_PARAM_SESSION);

        if (niceResDto == null) {
            return null;
        }

        return (NiceResDto)niceResDto;
    }

    /**
     * <pre>
     * 설명     : 최조 유입 경로 저장 처리
     * @param String
     * @return: void
     * </pre>
     */
    public static void saveKtmReferer(String ktmReferer) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(KTM_REFERER_SESSION, ktmReferer);
    }



    /**
     * <pre>
     * 설명     :   NICE PARAM 인증  session return
     * @return
     * </pre>
     */
    public static String getKtmReferer() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object ojbDto = session.getAttribute(KTM_REFERER_SESSION);

        if (ojbDto == null) {
            return null;
        }

        return (String)ojbDto;
    }

    /**
     * <pre>
     * 설명     : otp인증번호  session return
     * @return
     * </pre>
     */
    public static String getOtpInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object optInfoObj = session.getAttribute(OTP_INFO_SESSION);
        if (optInfoObj == null) {
            return null;
        }

        return (String)optInfoObj;
    }


    /**
     * <pre>
     * 설명     : otp인증번호  생성
     * @param nonMemReqBean
     * </pre>
     */
    public static void saveOtpInfo(String strOtp) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(OTP_INFO_SESSION, strOtp);

    }


    public static void saveEtcDomainReferer(String referer) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(ETC_DOMAIN_REFERER_SESSION, referer);
    }

    public static String getEtcDomainReferer() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return (String)session.getAttribute(ETC_DOMAIN_REFERER_SESSION);
    }

    public static String getServerInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String serverInfo = "https://"+  request.getServerName() ;

        String serverPort = String.valueOf(request.getServerPort());
        if(!"443".equals(serverPort) && !"80".equals(serverPort)) {
            serverInfo = serverInfo.concat(":").concat(serverPort);
        }

        return serverInfo;
    }

    public static void saveCurrentMenuDto(SiteMenuDto siteMenuDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        SiteMenuDto beforeDto = SessionUtils.getCurrentMenuDto();

        if(beforeDto == null) {
            session.setAttribute(BEFORE_MENU_SESSION, siteMenuDto);
        } else {
            session.setAttribute(BEFORE_MENU_SESSION, beforeDto);
        }

        if(siteMenuDto != null) {
            logger.debug("saveCurrentMenuDto siteMenuDto:{}/{}/{}/{}",siteMenuDto.getUrlAdr(), siteMenuDto.getMenuNm(), siteMenuDto.getMenuSeq(), siteMenuDto.getMenuCode());
        }

        session.setAttribute(CURR_MENU_SESSION, siteMenuDto);
    }

    public static SiteMenuDto getCurrentMenuDto() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        SiteMenuDto siteMenuDto = (SiteMenuDto)session.getAttribute(CURR_MENU_SESSION);
        if (siteMenuDto == null) {
            return null;
        }

        return siteMenuDto;
    }

    public static SiteMenuDto getBeforeMenuDto() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        SiteMenuDto siteMenuDto = (SiteMenuDto)session.getAttribute(BEFORE_MENU_SESSION);
        if (siteMenuDto == null) {
            return null;
        }

        return siteMenuDto;
    }

    public static String getCurrentMenuName() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        SiteMenuDto siteMenuDto = (SiteMenuDto)session.getAttribute(CURR_MENU_SESSION);
        String menuNm = " ";
        if (siteMenuDto == null) {
            return " ";
        } else {
            menuNm = StringUtil.NVL(siteMenuDto.getMenuNm()," ");
        }
        return menuNm;
    }

    public static void saveCurrentMenuUrlDto(WorkNotiDto workNotiDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(CURR_MENU_URL_SESSION, workNotiDto);
    }

    public static WorkNotiDto getCurrentMenuUrl() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        WorkNotiDto workNotiDto = (WorkNotiDto)session.getAttribute(CURR_MENU_URL_SESSION);

        if (workNotiDto == null) {
            return null;
        }

        return workNotiDto;
    }

    /**
     * <pre>
     * 설명     : 휴면 사용자 정보 session 생성
     * @param dormancyMember
     * </pre>
     */
    public static void saveDormancySession(UserSessionDto userSessionDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60);  //60분 세션 설정...

        session.setAttribute(DORMANCY_SESSION, userSessionDto);
        session.setAttribute("passWord", "");	//비밀번호는 세션에 담지않는다.
        try {
            sessionToDormancyCookie();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.debug("###ERROR### : ");
        }
    }

    /**
     * <pre>
     * 설명     :   세션값을 암호화 해서 쿠키에 저장한다.
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * </pre>
     */
    public static void sessionToDormancyCookie() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getResponse();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();


        UserSessionDto userSession = getUserDormancyCookieBean();
        if(userSession != null && request.getHeader("User-Agent").indexOf("M-Mobile-App") != -1) {
            boolean chkId = false;
            boolean chkNm = false;
            boolean chkDiv = false;
            Field[] fields = userSession.getClass().getDeclaredFields();
            for(Field f : fields) {
                f.setAccessible(true);
                try {
                    if(f.get(userSession) != null) {
                        String tmpString = EncryptUtil.ace256Enc(f.get(userSession).toString());

                        if (tmpString != null && !"".equals(tmpString) ) {
                            Cookie cookie = new Cookie("ktm_"+f.getName(), tmpString);
                            cookie.setSecure(true);
                            response.addCookie(cookie);                // 쿠키저장
                            if("userId".equals(f.getName())) chkId = true;
                            if("name".equals(f.getName())) chkNm = true;
                            if("userDivision".equals(f.getName())) chkDiv = true;
                        }


                    }
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                }
            }

            // 회원 아이디, 이름, 정회원 여부 필드가 있으면 쿠키 저장한다.
            if(chkId && chkNm && chkDiv) {
                Cookie cookie = new Cookie("ktm_encKey", "00001");
                cookie.setSecure(true);
                //cookie.setMaxAge(60*60*24*365);            // 쿠키 유지 기간(이부분이 없으면 브라우저 종료시 사라짐)
                cookie.setPath("/");                               // 모든 경로에서 접근 가능하도록
                //cookie.setDomain(".ktmmobile.com");//이부분을 적용하면 서브 도메인간 공유 가능
                response.addCookie(cookie);                // 쿠키저장
            }
        }
    }

    /**
     * <pre>
     * 설명     : 사용자 정보 session return
     * @return
     * </pre>
     */
    public static UserSessionDto getUserDormancyCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object userSessionDto = session.getAttribute(DORMANCY_SESSION);
        if (userSessionDto == null) {
            return null;
        }

        return (UserSessionDto)userSessionDto;
    }


    public static void saveAppUuidSession(String uuid) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(APP_UUID_SESSION, uuid);
    }

    public static String getAppUuidSession() {
        HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String appUuid = (String)session.getAttribute(APP_UUID_SESSION);
        if (appUuid == null) {
            return null;
        }

        return appUuid;
    }

    public static AuthSmsDto getSmsSession(String menuType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
        atr.append("_").append(menuType);
        AuthSmsDto sessionAuthSmsDto = (AuthSmsDto)session.getAttribute(atr.toString());

        if(sessionAuthSmsDto !=null) {
            if(sessionAuthSmsDto.isResult()) {
                return sessionAuthSmsDto;
            }
        }
        return null;
    }

    public static void saveTestLoginSession(String testLogin, String testLoginId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(TEST_LOGIN, testLogin);
        session.setAttribute(TEST_LOGIN_ID, testLoginId);
    }

    public static String getTestLoginSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String testLogin = (String)session.getAttribute(TEST_LOGIN);
        if (testLogin == null) {
            return "N";
        }

        return testLogin;
    }

    public static String getTestLoginIdSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String testLoginId = (String)session.getAttribute(TEST_LOGIN_ID);
        if (testLoginId == null) {
            return "";
        }

        return testLoginId;
    }


    public static UserSessionDto getUserDummyCookieBean() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object userSessionDto = session.getAttribute(DUMMY_SESSION);
        if (userSessionDto == null) {
            return null;
        }

        return (UserSessionDto)userSessionDto;
    }

    public static void saveDummySession(UserSessionDto userSessionDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*60);  //60분 세션 설정...

        session.setAttribute(DUMMY_SESSION, userSessionDto);
        session.setAttribute("passWord", "");	//비밀번호는 세션에 담지않는다.
        try {
            sessionToDummyCookie();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error("###ERROR### : ");
        }
    }

    /**
     * <pre>
     * 설명     :   세션값을 암호화 해서 쿠키에 저장한다.
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * </pre>
     */
    public static void sessionToDummyCookie() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getResponse();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();


        UserSessionDto userSession = getUserDummyCookieBean();
        if(userSession != null && request.getHeader("User-Agent").indexOf("M-Mobile-App") != -1) {
            boolean chkId = false;
            boolean chkNm = false;
            boolean chkDiv = false;
            Field[] fields = userSession.getClass().getDeclaredFields();
            for(Field f : fields) {
                f.setAccessible(true);
                try {
                    if(f.get(userSession) != null) {
                        String tmpString = EncryptUtil.ace256Enc(f.get(userSession).toString());

                        if (tmpString != null && !"".equals(tmpString) ) {
                            Cookie cookie = new Cookie("ktm_"+f.getName(), tmpString);
                            cookie.setSecure(true);
                            response.addCookie(cookie);                // 쿠키저장
                            if("userId".equals(f.getName())) chkId = true;
                            if("name".equals(f.getName())) chkNm = true;
                            if("userDivision".equals(f.getName())) chkDiv = true;
                        }


                    }
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                }
            }

            // 회원 아이디, 이름, 정회원 여부 필드가 있으면 쿠키 저장한다.
            if(chkId && chkNm && chkDiv) {
                Cookie cookie = new Cookie("ktm_encKey", "00001");
                cookie.setSecure(true);
                //cookie.setMaxAge(60*60*24*365);            // 쿠키 유지 기간(이부분이 없으면 브라우저 종료시 사라짐)
                cookie.setPath("/");                               // 모든 경로에서 접근 가능하도록
                //cookie.setDomain(".ktmmobile.com");//이부분을 적용하면 서브 도메인간 공유 가능
                response.addCookie(cookie);                // 쿠키저장
            }
        }
    }

    public static void setGnbMenuCode(String menuCode) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        SiteMenuDto siteMenuDto = (SiteMenuDto)session.getAttribute(CURR_MENU_SESSION);

        if (!"".equals(StringUtil.NVL(menuCode, ""))) {
            if (siteMenuDto != null) {
                siteMenuDto.setMenuCode(menuCode);
            } else {
                logger.debug("setGnbMenuCode - siteMenuDto is null");
            }
        }
        session.setAttribute(CURR_MENU_SESSION, siteMenuDto);
    }

    public static String getGnbMenuCode() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        SiteMenuDto siteMenuDto = (SiteMenuDto)session.getAttribute(CURR_MENU_SESSION);

        String menuCode = "";
        if (siteMenuDto != null) {
            menuCode = StringUtil.NVL(siteMenuDto.getMenuCode(),"").replaceAll("PCMENU", "").replaceAll("MOMENU", "");
            if(menuCode.length() < 4) {
                menuCode = "";
            }
        }
        return menuCode;
    }


    public static void setCurrPhoneNcn(String phoneNcn) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(CURR_PHONE_NCN, phoneNcn);
    }

    public static String getCurrPhoneNcn() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String phoneNcn = (String)session.getAttribute(CURR_PHONE_NCN);

        return StringUtil.NVL(phoneNcn, "");
    }

    /**
     * 유입 제휴 코드 세션 저장
     * @param inFlow
     */
    public static void saveCoalitionInflow(String inFlow) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(COALITION_INFLOW_CODE, inFlow);

    }

    /**
     * 유입 제휴 코드
     * @return
     */
    public static String getCoalitionInflow() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String inFlowCd = (String)session.getAttribute(COALITION_INFLOW_CODE);
        return StringUtils.isNotEmpty(inFlowCd) ? inFlowCd : null;
    }


    /**
     * <pre>
     * 설명     :  구글 리캡챠 인증 여부
     * @param recaptchaYn (Y 또는 N)
     * @return: void
     * </pre>
     */
    public static void saveRecaptchaSession(String recaptchaYn) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(RECAPTCHA_SESSION, recaptchaYn);
    }

    /**
     * <pre>
     * 설명    : 구글 리캡챠 인증 여부
     * @return: String
     * </pre>
     */
    public static String getRecaptchaSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Object recaptchaYn = session.getAttribute(RECAPTCHA_SESSION);

        if (recaptchaYn == null) {
            return null;
        }

        return (String) recaptchaYn;
    }

    /**
     * <pre>
     * 설명     :  구글 리캡챠 SMS 대체 인증 여부
     * @param recaptchaSmsYn (Y 또는 N)
     * @return: void
     * </pre>
     */
    public static void saveRecaptchaSmsSession(String recaptchaSmsYn) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(RECAPTCHA_SMS_SESSION, recaptchaSmsYn);
    }

    /**
     * <pre>
     * 설명    : 구글 리캡챠 SMS 대체 인증 여부
     * @return: String
     * </pre>
     */
    public static String getRecaptchaSmsSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Object recaptchaSmsYn = session.getAttribute(RECAPTCHA_SMS_SESSION);

        if (recaptchaSmsYn == null) {
            return null;
        }

        return (String) recaptchaSmsYn;
    }


    /** param으로 받은 본인인증 시퀀스 세션에 저장
     * @param long
     * @return
     * @author wooki
     * @Date : 2023.12 */
    public static void setCertSession(long crtSeq) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(CERT_SEQ, crtSeq);
    }

    /** 세션에 저장된 본인인증 시퀀스 리턴
     * @param
     * @return long
     * @author wooki
     * @Date : 2023.12 */
    public static long getCertSession() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Object certSeqObj = session.getAttribute(CERT_SEQ);
        if (certSeqObj == null) return 0;

        long crtSeqLong = 0;
        try {
            crtSeqLong = (Long) session.getAttribute(CERT_SEQ);
        }catch(NumberFormatException e) {
            crtSeqLong = 0;
        }

        return crtSeqLong;
    }

    /** 본인인증 시퀀스 삭제
     * @param
     * @return
     * @author wooki
     * @Date : 2023.12 */
    public static void removeCertSession() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.removeAttribute(CERT_SEQ);
    }

    /** param으로 받은 페이지 타입 세션 저장 - 본인인증 사용한 메뉴 구분
     * @param String
     * @return
     * @author wooki
     * @Date : 2023.12 */
    public static void setPageSession(String pageType) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        session.setAttribute(PAGE_TYPE, pageType);
    }

    /** 세션에 저장된 페이지 타입 리턴
     * @param String
     * @return
     * @author wooki
     * @Date : 2023.12 */
    public static String getPageSession() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Object pageTypeObj = session.getAttribute(PAGE_TYPE);

        if(pageTypeObj == null) {
            return null;
        }

        return (String) pageTypeObj;
    }


    /**
     * 친구초대 ID 세션 저장
     * @param inFlow
     */
    public static void saveFriendInvitation(String recommend) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(RECOMMEND, recommend);

    }

    /**
     * 친구초대 ID
     * @return
     */
    public static String getFriendInvitation() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String recommend = (String)session.getAttribute(RECOMMEND);
        return StringUtils.isNotEmpty(recommend) ? recommend : null;
    }



    /**
     * <pre>
     * 설명     :  nice 인증 암호화 key,iv 저장
     * @return: void
     * </pre>
     */
    public static void saveNiceKeySession(String key, String iv) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", key);
        map.put("iv", iv);
        session.setAttribute(NICE_AUTH_ENC_KEY, map);
    }

    /**
     *  nice 인증 암호화 key,iv 가져오기
     * @return
     */
    public static Map<String,String> getNiceKeySession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Map<String,String> niceKey = (Map<String,String>)session.getAttribute(NICE_AUTH_ENC_KEY);
        return niceKey;
    }

    /**
     * <pre>
     * 설명     :  비로그인 데이터쉐어링 고객 인증정보 저장
     * @param mcpUserCntrMngDto
     * @return: void
     * </pre>
     */
    public static void saveNonmemberSharingInfo(McpUserCntrMngDto mcpUserCntrMngDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(NONMEMBER_AUTH_SHARING, mcpUserCntrMngDto);
    }

    /**
     * <pre>
     * 설명     :  비로그인 데이터쉐어링 고객 인증정보 가져오기
     * @return
     * </pre>
     */
    public static McpUserCntrMngDto getNonmemberSharingInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object mcpUserCntrMngDto = session.getAttribute(NONMEMBER_AUTH_SHARING);

        if (mcpUserCntrMngDto == null) {
            return null;
        }

        return (McpUserCntrMngDto)mcpUserCntrMngDto;
    }




    /**
     * <pre>
     * 설명 : 데이터쉐어링 정보 저장
     * @return: void
     * </pre>
     */
    public static void saveDateSharingSession(MyShareDataReqDto myShareDataReqDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(DATA_SHARING_SESSION, myShareDataReqDto);
    }

    /**
     * <pre>
     * 설명     :  데이터쉐어링 정보 가져오기
     * @return
     * </pre>
     */
    public static MyShareDataReqDto getDateSharingSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object myShareDataReqDto = session.getAttribute(DATA_SHARING_SESSION);

        if (myShareDataReqDto == null) {
            return null;
        }

        return (MyShareDataReqDto)myShareDataReqDto;
    }



    /**
     * 회원가입 프로모션 세션 저장
     * @param inFlow
     */
    public static void saveUserPromotionRes(String userProReq) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(USER_PROMOTION, userProReq);

    }

    /**
     * 회원가입 프로모션
     * @return
     */
    public static String getUserPromotionRes() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userProReq = (String)session.getAttribute(USER_PROMOTION);
        return StringUtils.isNotEmpty(userProReq) ? userProReq : null;
    }

    /**
     * 해피콜 선택여부 세션 저장
     * @param onlineTypeYn
     */
    public static void saveOnlineSession(String onlineTypeYn) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(ONLINE_SESSION, onlineTypeYn);
    }

    /**
     * 해피콜 선택여부
     * @return
     */
    public static String getOnlineSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object onlineTypeYn = session.getAttribute(ONLINE_SESSION);

        if(onlineTypeYn == null) return "N";

        return (String)onlineTypeYn;
    }


    /**
     * <pre>
     * 설명     : ignoredScript   session 생성
     * @param authSmsDto
     * </pre>
     */
    public static void setIgnoredScriptSession(String ignoredScript) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("IGNORED_SCRIPT", ignoredScript);
    }

    public static boolean hasIgnoredScriptSession() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object objectSession = session.getAttribute("IGNORED_SCRIPT");

        if(objectSession == null) return false;

        String ignoredScript = (String)objectSession;

        if ("ktmmobileDevelopment".equals(ignoredScript)) {
            return true;
        }
        return false;
    }


    /**
     * 마스킹 해제 세션 저장
     * @param inFlow
     */
    public static void saveMaskingSession(long maskingSeq) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        // MARSKING_SESSION 속성과 함께 타임아웃 시간 설정
        session.setAttribute(MARSKING_SESSION, maskingSeq);
        session.setAttribute(MARSKING_SESSION_TIMEOUT, System.currentTimeMillis() + (10 * 60 * 1000));  // 10분 후 타임아웃 설정
    }

    /**
     * 마스킹 해제 세션
     * @return
     */
    public static long getMaskingSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Long sessionTimeout = (Long) session.getAttribute(MARSKING_SESSION_TIMEOUT);

        if (sessionTimeout == null || System.currentTimeMillis() > sessionTimeout) {
            session.removeAttribute(MARSKING_SESSION);  // 타임아웃이 지났으면 속성 삭제
            session.removeAttribute(MARSKING_SESSION_TIMEOUT);  // 타임아웃 정보도 삭제
            return 0;
        }

        Object maskingSeqObj = session.getAttribute(MARSKING_SESSION);
        if (maskingSeqObj == null) return 0;


        return (Long) maskingSeqObj;

    }

    /**
     * 대리점 신청서 URL 세션 저장
     * @param agentFormLink
     */
    public static void saveAgentFormLink(String agentFormLink) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(AGENT_FORM_LINK, agentFormLink);
    }

    /**
     * 대리점 신청서 URL 세션 조회
     * @return
     */
    public static String getAgentFormLink() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object agentFormLink = session.getAttribute(AGENT_FORM_LINK);

        if(agentFormLink == null) return "";

        return (String)agentFormLink;

    }
    /**
     * 1회성 팝업 세션 저장
     */
    public static void saveOneTimePopup(String oneTimePopupGrp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(ONE_TIME_POPUP, oneTimePopupGrp);
    }

    /**
     * 1회성 팝업 세션 세션 조회
     * @return
     */
    public static String getOneTimePopup() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object oneTimePopup = session.getAttribute(ONE_TIME_POPUP);

        if(oneTimePopup == null) return "";

        return (String)oneTimePopup;
    }

    /**
     * <pre>
     * 설명     : 결합대상회선 리스트 세션 저장
     * @return: void
     * </pre>
     */
    public static void saveCombineList(List<MoscMvnoComInfo> combList) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(COMBINE_LIST, combList);
    }

    /**
     * <pre>
     * 설명     :   결합대상회선 리스트 return
     * @return
     * </pre>
     */
    public static List<MoscMvnoComInfo> getCombineList() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object combList = session.getAttribute(COMBINE_LIST);

        if (combList == null) {
            return null;
        }

        return (List<MoscMvnoComInfo>) combList;
    }

    /**
     * 제휴사 구분 세션 저장
     * @param jehuPartnerType
     */
    public static void saveJehuPartnerType(String jehuPartnerType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(JEHU_PARTNER_TYPE, jehuPartnerType);
    }

    /**
     * 제휴사 구분 세션 조회
     * @return
     */
    public static String getJehuPartnerType() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object jehuPartnerType = session.getAttribute(JEHU_PARTNER_TYPE);

        if(jehuPartnerType == null) return "";

        return (String)jehuPartnerType;

    }

    /**
     * <pre>
     * 설명 : 개인화 URL 인증 SESSION 생성
     * @param authSmsDto
     * </pre>
     */
    public static void setPersonalAuthSmsSession(AuthSmsDto authSmsDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
          .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        StringBuffer atr = new StringBuffer(PERSONAL_AUTH_SMS_INFO);
        atr.append("_").append(authSmsDto.getMenu());
        session.setAttribute(atr.toString(), authSmsDto);
    }

    /**
     * <pre>
     * 설명 : 개인화 URL 인증 SESSION RETURN
     * @param authSmsDto
     * @return AuthSmsDto
     * </pre>
     */
    public static AuthSmsDto getPersonalAuthSmsBean(AuthSmsDto authSmsDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
          .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        StringBuffer atr = new StringBuffer(PERSONAL_AUTH_SMS_INFO);
        atr.append("_").append(authSmsDto.getMenu());
        Object returnDto = session.getAttribute(atr.toString());

        return (returnDto == null) ? null : (AuthSmsDto) returnDto;
    }

    /**
     * <pre>
     * 설명 : 개인화 URL 인증 확인
     * @param authSmsDto
     * @return AuthSmsDto
     * </pre>
     */
    public static void checkPersonalAuthSmsSession(AuthSmsDto authSmsDto) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
          .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        StringBuffer atr = new StringBuffer(PERSONAL_AUTH_SMS_INFO);
        atr.append("_").append(authSmsDto.getMenu());
        Object returnDto = session.getAttribute(atr.toString());

        if(returnDto == null){
          authSmsDto.setResult(false);
          authSmsDto.setMessage("인증번호가 없습니다. 인증번호를 다시 받아 주세요.");
          return;
        }

        // 인증 유효시간 확인
        AuthSmsDto sessionAuthSmsDto = (AuthSmsDto) returnDto;
        String startDay = sessionAuthSmsDto.getStartDate();
        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int btw = 0;

        try{
            btw = DateTimeUtil.minsBetween(startDay, today, "yyyyMMddHHmmss");
        }catch(ParseException e){
            authSmsDto.setResult(false);
            authSmsDto.setMessage("서비스가 지연되고 있습니다. 다시 시도해 주세요.");
            return;
        }

        // 인증 유효시간 초과
        if(btw >= 3) {
            authSmsDto.setResult(false);
            authSmsDto.setMessage("인증번호의 유효기간이 지났습니다.");
            return;
        }

        // 인증번호 확인
        if(!sessionAuthSmsDto.getAuthNum().equals(authSmsDto.getAuthNum())){
            authSmsDto.setResult(false);
            authSmsDto.setMessage("인증번호가 맞지않습니다.");
            return;
        }

        // 인증 성공
        authSmsDto.setResult(true);
        authSmsDto.setMessage("인증완료");
        sessionAuthSmsDto.setResult(true);
    }

    /**
     * <pre>
     * 설명 : 개인화 URL SESSION
     * @param pageType
     * @param personalUrl
     * </pre>
     */
    public static void setPersonalUrl(String pageType, String personalUrl) {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
      HttpSession session = request.getSession();
      StringBuffer atr = new StringBuffer(PERSONAL_URL);
      atr.append("_").append(pageType);
      session.setAttribute(atr.toString(), personalUrl);
    }

    /**
     * <pre>
     * 설명 : 개인화 URL SESSION RETURN
     * @param pageType
     * @return String
     * </pre>
     */
    public static String getPersonalUrl(String pageType) {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
      HttpSession session = request.getSession();

      StringBuffer atr = new StringBuffer(PERSONAL_URL);
      atr.append("_").append(pageType);
      Object personalUrl = session.getAttribute(atr.toString());

      return (personalUrl == null) ? "" : (String) personalUrl;
    }

    public static void setDocumentReceive(DocRcvSessionDto docRcvSessionDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Map<String, DocRcvSessionDto> sessionDtoMap = (Map<String, DocRcvSessionDto>) session.getAttribute(DOCUMENT_RECEIVE);
        if (sessionDtoMap == null) {
            sessionDtoMap = new HashMap<>();
        }
        sessionDtoMap.put(docRcvSessionDto.getDocRcvId(), docRcvSessionDto);
        session.setAttribute(DOCUMENT_RECEIVE, sessionDtoMap);
    }

    public static DocRcvSessionDto getDocumentReceive(String docRcvId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Map<String, DocRcvSessionDto> sessionDtoMap = (Map<String, DocRcvSessionDto>) session.getAttribute(DOCUMENT_RECEIVE);
        if (sessionDtoMap == null) {
            return null;
        }
        return sessionDtoMap.get(docRcvId);
    }

    public static void invalidateDocumentReceive(String docRcvId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Map<String, DocRcvSessionDto> sessionDtoMap = (Map<String, DocRcvSessionDto>) session.getAttribute(DOCUMENT_RECEIVE);
        if (sessionDtoMap != null) {
            sessionDtoMap.remove(docRcvId);
        }
    }


    /**
     * <pre>
     * 설명    : 셀프개통+신규가입인 경우 휴대폰인증 세션 저장
     * @param niceResDto
     * @return: void
     * </pre>
     */
    public static void saveNiceMobileSession(NiceResDto niceResDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(NICE_AUT_MOBILE_SESSION, niceResDto);
    }


    /**
     * <pre>
     * 설명     :   셀프개통+신규가입 개통완료시 휴대폰인증 세션 return
     * @return
     * </pre>
     */
    public static NiceResDto getNiceMobileSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object niceResDto = session.getAttribute(NICE_AUT_MOBILE_SESSION);

        if (niceResDto == null) {
            return null;
        }

        return (NiceResDto)niceResDto;
    }

    /**
     * 안면인증 트랜잭션 ID 세션 저장
     * @param String
     */
    public static void saveFathTransacId(String fathTransacId) {
        FathSessionDto fathSessionDto = SessionUtils.getFathSession();
        fathSessionDto.setTransacId(fathTransacId);
        SessionUtils.saveFathSession(fathSessionDto);
    }

    /**
     * 안면인증 수행일자 세션 저장
     * @param String
     */
    public static void saveFathCmpltNtfyDt(String fathCmpltNtfyDt) {
        FathSessionDto fathSessionDto = SessionUtils.getFathSession();
        fathSessionDto.setCmpltNtfyDt(fathCmpltNtfyDt);
        SessionUtils.saveFathSession(fathSessionDto);
    }

    public static void increaseFathTryCount() {
        FathSessionDto fathSessionDto = SessionUtils.getFathSession();
        int tryCount = fathSessionDto.getTryCount();
        fathSessionDto.setTryCount(++tryCount);
        SessionUtils.saveFathSession(fathSessionDto);
    }
    
    /**
     * <pre>
     * 설명     : 안면인증 CPNT ID 세션 저장
     * @param int
     * </pre>
     */
    public static void saveFathCpntId(String cpntId) {
        FathSessionDto fathSessionDto = SessionUtils.getFathSession();
        fathSessionDto.setCpntId(cpntId);
        SessionUtils.saveFathSession(fathSessionDto);
    }

    public static FathSessionDto getFathSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object fathSessionDto = session.getAttribute(FATH_SESSION);
        if (fathSessionDto == null) {
            fathSessionDto = SessionUtils.initializeFathSession();
        }

        return (FathSessionDto) fathSessionDto;
    }

    public static void saveFathSession(FathSessionDto fathSessionDto) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(FATH_SESSION, fathSessionDto);
    }

    public static FathSessionDto initializeFathSession() {
        FathSessionDto fathSessionDto = new FathSessionDto();
        SessionUtils.saveFathSession(fathSessionDto);
        return fathSessionDto;
    }

    /**
     * <pre>
     * 설명     : 안면인증 임시 예약번호 세션 저장
     * </pre>
     */
    public static void saveFathTempResNo(String tempResNo) {
        FathSessionDto fathSessionDto = SessionUtils.getFathSession();
        fathSessionDto.setTempResNo(tempResNo);
        SessionUtils.saveFathSession(fathSessionDto);
    }
    
    /**
     * <pre>
     * 설명     : 안면인증 안정화기간 여부 세션 저장
     * </pre>
     */
    public static void saveStbznPerdYn(String stbznPerdYn) {
        FathSessionDto fathSessionDto = SessionUtils.getFathSession();
        fathSessionDto.setStbznPerdYn(stbznPerdYn);
        SessionUtils.saveFathSession(fathSessionDto);
    }
    
    /**
     * <pre>
     * 설명     : 안면인증 결과확인 버튼 누른시점 세션 저장
     * </pre>
     */
    public static void saveFathResltFirstReqAt() {
        FathSessionDto fathSessionDto = SessionUtils.getFathSession();
        if(fathSessionDto.getFathResltFirstReqAt() == null) {
            fathSessionDto.setFathResltFirstReqAt(new Date());
            SessionUtils.saveFathSession(fathSessionDto);
        }
    }
    /**
     * <pre>
     * 설명     : (FS9) 안면인증 결과확인 완료
     * </pre>
     */
    public static void saveIsFs9() {
        FathSessionDto fathSessionDto = SessionUtils.getFathSession();
        if(fathSessionDto.getIsFs9() == null) {
            fathSessionDto.setIsFs9("Y");
            SessionUtils.saveFathSession(fathSessionDto);
        }
    }
    
}

