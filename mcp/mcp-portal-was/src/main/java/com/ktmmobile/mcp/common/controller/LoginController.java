package com.ktmmobile.mcp.common.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.app.dto.AppInfoDTO;
import com.ktmmobile.mcp.app.service.AppSvc;
import com.ktmmobile.mcp.common.dao.LoginDao;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.LoginHistoryDto;
import com.ktmmobile.mcp.common.dto.McpUserDarkwebDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.PersonalPolicyDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.SiteMenuDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpAutoLoginTxnDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.KakaoLoginSvc;
import com.ktmmobile.mcp.common.service.LoginSvc;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
//import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageService;

import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;
import nl.captcha.audio.Sample;
import nl.captcha.audio.producer.VoiceProducer;
import nl.captcha.servlet.CaptchaServletUtil;

/**
 * Login Controller
 * @author jsh
 * @date 2021.12.30
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    //비밀번호 패턴
    //* 영문, 숫자, 특수문자 중 3종류를 조합하여 8~16자리
    private static final String Passwrod_PATTERN_8 = "^(?=.*[a-zA-Z]+)(?=.*[0-9]+)(?=.*[!@#$%^*+=-]+).{8,16}$";
    //* 영문, 숫자, 특수문자 중 2종류를 조합하여 10~16자리
    private static final String Passwrod_PATTERN_TEXT_NUM10 = "^(?=.*[a-zA-Z]+)(?=.*[0-9]+).{10,16}$";
    private static final String Passwrod_PATTERN_NUM_SPECIAL10 = "^(?=.*[!@#$%^*+=-]+)(?=.*[0-9]+).{10,16}$";
    private static final String Passwrod_PATTERN_TEXT_SPECIAL10 = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]+).{10,16}$";

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${captcha.audio.wavPath}")
    private String wavPath;

    @Autowired
    LoginSvc loginSvc;

    @Autowired
    LoginDao loginDao;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private AppSvc appSvc;

    @Autowired
    private KakaoLoginSvc kakaoLoginSvc;

    @Autowired
    private CertService certService;

    /**
     * 설명 : 아이디 찾기
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param searchVO
     * @return
     */
    @RequestMapping("/checkNiceCertAjax.do")
    @ResponseBody
    public Map<String, Object> checkNiceCertAjax(HttpServletRequest request, @ModelAttribute ("searchVO") UserSessionDto searchVO) {

        Map<String, Object> result = new HashMap<String, Object>();

        // 1. 본인인증 세션 확인
        NiceResDto niceResDto = SessionUtils.getNiceResCookieBean();
        if(niceResDto == null || StringUtils.isEmpty(niceResDto.getDupInfo())){
            throw new McpCommonJsonException("AUTH01", NICE_CERT_EXCEPTION_INSR);
        }

        searchVO.setBirthday(niceResDto.getBirthDate());
        searchVO.setPin(niceResDto.getDupInfo());

        //중복 회원 처리
        int pinCnt = loginSvc.findPinToIdCnt(searchVO);
        if(pinCnt > 0) {
            UserSessionDto dormancyUser = loginSvc.checkNiceCertDormantAjax(searchVO);
            if(dormancyUser != null) {
                result.put("RESULT_CODE", "E0001");
                return result;
            }
        }

        //고객정보 조회
        UserSessionDto userSessionDto = loginSvc.checkNiceCertAjax(searchVO);
        if(userSessionDto == null) {
            userSessionDto = loginSvc.checkNiceCertDormantAjax(searchVO);
        }

        if (userSessionDto != null) {

            List<Map<String,String>> certMobileNoList = new ArrayList<Map<String,String>>();

            //정회원일경우 인증받은 회선 조회
            if ("01".equals(userSessionDto.getUserDivision())){
                List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());

                Map<String,String> certMobileHm = null;
                if (cntrList != null && 0 < cntrList.size()) {
                    for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                        certMobileHm = new HashMap<String, String>();
                        certMobileHm.put("certMobileNo", mcpUserCntrMngDto.getCntrMobileNoMasking());
                        certMobileHm.put("contractNum", mcpUserCntrMngDto.getContractNum());
                        certMobileNoList.add(certMobileHm);
                    }
                }

            }

            result.put("userId", MaskingUtil.getMaskedId(userSessionDto.getUserId()));
            //회원가입시휴대폰번호
            result.put("userMobileNo", MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(userSessionDto.getMobileNo())));
            //정회원 인증받은 회선번호
            result.put("certMobileNoList", certMobileNoList);
            result.put("RESULT_CODE", "S");

        } else {

            request.getSession().removeAttribute(SessionUtils.NICE_AUT_COOKIE);
            result.put("RESULT_MSG", "해당 아이디가 없습니다. 다시 확인해 주세요.");
        }

        return result;
    }

    /**
     * 설명 : 전체 아이디 발송
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param sendSmsNum
     * @return
     */
    @RequestMapping("/sendIdSmsAjax.do")
    @ResponseBody
    public Map<String, Object> sendIdSmsAjax(HttpServletRequest request, @RequestParam(value = "sendSmsNum", required = true) String sendSmsNum) {

        Map<String, Object> result = new HashMap<String, Object>();

        boolean check = false;
        String smsSendNum = ""; //id발송할 번호

        //인증 받은 정보와 아이디 발송 요청 정보가 맞는 체크
        NiceResDto niceResDto = SessionUtils.getNiceResCookieBean();
        if (niceResDto != null) {

            // ============ STEP START ============
            // 1. 최소 스텝 수 확인
            if(certService.getStepCnt() < 1 ){
                throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
            }

            // 2. 최종 데이터 확인: 스텝종료여부, 생년월일, DI
            String[] certKey= {"urlType", "stepEndYn", "birthDate", "dupInfo"};
            String[] certValue= {"findUserId", "Y", niceResDto.getBirthDate(), niceResDto.getDupInfo()};

            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
            }
            // ============ STEP END ============

            UserSessionDto searchVO = new UserSessionDto();
            //searchVO.setName(niceResDto.getName());
            searchVO.setBirthday(niceResDto.getBirthDate());
            searchVO.setAuthType(niceResDto.getAuthType());
            searchVO.setPin(niceResDto.getDupInfo());

            UserSessionDto userSessionDto = loginSvc.checkNiceCertAjax(searchVO);
            if(userSessionDto == null) {
                userSessionDto = loginSvc.checkNiceCertDormantAjax(searchVO);
            }

            //회원등록번호로 아이디 발송 요청한경우
            if ("USER".equals(sendSmsNum)) {
                smsSendNum = userSessionDto.getMobileNo();
            } else { //가입회선으로 아이디 발송 요청한경우

                List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());

                if (cntrList != null && cntrList.size() > 0) {
                    for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                        if (mcpUserCntrMngDto.getContractNum().equals(sendSmsNum)) {
                            smsSendNum = mcpUserCntrMngDto.getCntrMobileNo();
                            break;
                        }
                    }
                }
            }

            if (smsSendNum != null && !smsSendNum.equals("")) {

                //아이디 찾기 SMS 발송 문구
                StringBuffer messageSb = new StringBuffer();
                messageSb.append("[kt M모바일] 안녕하세요.\n");
                messageSb.append("고객님 kt M모바일입니다.\n");
                messageSb.append("고객님 아이디는 [");
                messageSb.append(userSessionDto.getUserId());
                messageSb.append("]입니다.\n");
                messageSb.append("감사합니다.");

                //아이디 발송
                AuthSmsDto authSmsDto = new AuthSmsDto();
                authSmsDto.setMessage(messageSb.toString());
                authSmsDto.setPhoneNum(smsSendNum);
                authSmsDto.setAuthNum(userSessionDto.getUserId());

                check = loginSvc.sendAuthSms(authSmsDto);
                if (check) {
                    result.put("RESULT_CODE", "S");
                } else {
                    //sms발송 실패
                    if ("DEV".equals(serverName) || "LOCAL".equals(serverName)) {
                        result.put("RESULT_CODE", "S");
                    }else {
                        throw new McpCommonJsonException(COMMON_EXCEPTION);
                    }
                }
            } else {
                //인증 정보와 다름. 비정상적인 호출
                throw new McpCommonJsonException(F_BIND_EXCEPTION);
            }
        } else {
            //인증 정보 없음. 비정상적인 접근
            throw new McpCommonJsonException(F_BIND_EXCEPTION);
        }

        return result;
    }

    /**
     * 설명 : 비밀번호 찾기
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param searchVO
     * @return
     */
    @RequestMapping("/checkNicePwAjax.do")
    @ResponseBody
    public Map<String, Object> checkNicePwAjax (HttpServletRequest request, @ModelAttribute ("searchVO") UserSessionDto searchVO) {

        Map<String, Object> result = new HashMap<String, Object>();

        //인증 받은 정보와 아이디 발송 요청 정보가 맞는 체크
        NiceResDto niceResDto = SessionUtils.getNiceResCookieBean();
        if (niceResDto != null && !StringUtils.isEmpty(niceResDto.getDupInfo())) { //인증정보 체크

            // ============ STEP START ============
            // 이름, DI
            String[] certKey= {"urlType", "name", "dupInfo"};
            String[] certValue= {"findPw", searchVO.getName(), niceResDto.getDupInfo()};

            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
            }

            searchVO.setBirthday(niceResDto.getBirthDate());
            searchVO.setPin(niceResDto.getDupInfo());
            // ============ STEP END ============

            //중복 회원 처리
            int pinCnt = loginSvc.findPinToIdCnt(searchVO);
            if(pinCnt > 0) {
                UserSessionDto dormancyUser = loginSvc.checkNiceCertDormantAjax(searchVO);
                if(dormancyUser != null) {
                    result.put("RESULT_CODE", "E0001");
                    return result;
                }
            }

            UserSessionDto userSessionDto = loginSvc.checkNiceCertAjax(searchVO);
            if(userSessionDto == null) {
                userSessionDto = loginSvc.checkNiceCertDormantAjax(searchVO);
            }

            if (userSessionDto == null) {	//인증은 통과 했으나 입력한정보의 회원정보가 없는경우
                result.put("RESULT_MSG", "입력하신 고객정보가 없습니다.");
            } else {
                result.put("RESULT_CODE", "S");
            }

        } else {
            result.put("RESULT_MSG", "입력하신 고객정보가 없습니다.");
            //인증 정보 없음. 비정상적인 접근
            //throw new McpCommonJsonException(F_BIND_EXCEPTION);
        }

        return result;
    }

    /**
     * 설명 : 인증정보 정보와 입력한 아이디, 이름 체크
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param userId
     * @param userName
     * @return
     */
    @RequestMapping("/checkIdAjax.do")
    @ResponseBody
    public Map<String, Object> checkIdAjax(HttpServletRequest request,
                                           @RequestParam(value = "userId", required = true) String userId,
                                           @RequestParam(value = "userName", required = true) String userName) {

        Map<String, Object> result = new HashMap<String, Object>();

        //인증 받은 정보와 입력받은 아이디, 이름 체크   (비밀번호 찾기 프로세스 순서 변경으로 인해 주석)
        UserSessionDto searchVO = new UserSessionDto();
        searchVO.setName(userName);
        searchVO.setUserId(userId);

        UserSessionDto userSessionDto = loginSvc.checkIdNameAjax(searchVO);
        if(userSessionDto == null) {
            userSessionDto = loginSvc.checkIdNameDormantAjax(searchVO);
        }

        if (userSessionDto != null) {
            //입력한 아이디, 이름 체크 (영문 이름일경우 DB와 동일하게 대문자로 비교)
            if (userId.equals(userSessionDto.getUserId()) && userName.equalsIgnoreCase(userSessionDto.getName()) ) {

                result.put("RESULT_CODE", "S");
                result.put("userId", MaskingUtil.getMaskedId(userId));
                //result.put("pin", userSessionDto.getPin());

                // ================= STEP START ==================
                // 이름, 아이디, DI
                String[] certKey= {"urlType", "name", "regstId", "dupInfo"};
                String[] certValue= {"chkMcpUserAuth", userName, userId, userSessionDto.getPin()};
                certService.vdlCertInfo("C", certKey, certValue);
                // ================= STEP END ==================

            } else {
                result.put("RESULT_MSG", "입력하신 아이디/이름을 찾을 수 없습니다.");
            }
        } else {
            //인증 정보와 다름. 비정상적인 호출
            result.put("RESULT_MSG", "입력하신 아이디/이름을 찾을 수 없습니다.");
        }

        return result;
    }

    /**
     * 설명 : 비밀번호 변경
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param userId
     * @param userName
     * @param newPassword
     * @return
     */
    @RequestMapping("/changePwAjax.do")
    @ResponseBody
    public Map<String, Object> changePwAjax(HttpServletRequest request,
                                            @RequestParam(value = "userId", required = true) String userId,
                                            @RequestParam(value = "userName", required = true) String userName,
                                            @RequestParam(value = "newPassword", required = true) String newPassword) {

        Map<String, Object> result = new HashMap<String, Object>();

        //인증 받은 정보와 입력받은 아이디, 이름 체크
        NiceResDto niceResDto = SessionUtils.getNiceResCookieBean();
        if (niceResDto != null) {

            UserSessionDto searchVO = new UserSessionDto();
            //searchVO.setName(niceResDto.getName());
            searchVO.setName(userName);
            searchVO.setBirthday(niceResDto.getBirthDate());
            searchVO.setAuthType(niceResDto.getAuthType());
            searchVO.setPin(niceResDto.getDupInfo());

            UserSessionDto userSessionDto = loginSvc.checkNiceCertAjax(searchVO);
            String dormantYn = "N";
            if(userSessionDto == null) {
                userSessionDto = loginSvc.checkNiceCertDormantAjax(searchVO);
                dormantYn = "Y";
            }

            if (userSessionDto != null) {

                //입력한 아이디, 이름 체크 (영문 이름일경우 DB와 동일하게 대문자로 비교)
                if (userId.equals(userSessionDto.getUserId()) && userName.equalsIgnoreCase(userSessionDto.getName()) ) {

                    //패스워드 패턴 체크 추가 -S-
                    boolean pwCheckFlag = StringUtil.passwordPatternCheck(newPassword);
                    //패스워드 패턴 체크 추가 -E-

                    if (pwCheckFlag) {

                        // ============ STEP START ============
                        // 1. 최소 스텝 수 체크
                        if(certService.getStepCnt() < 4 ){
                            throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
                        }

                        // 2. 최종 데이터 체크: 스텝종료여부, 이름, 아이디, DI
                        String[] certKey= {"urlType", "stepEndYn", "name", "regstId", "dupInfo"};
                        String[] certValue= {"updatePw", "Y", userName, userId, niceResDto.getDupInfo()};

                        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                            throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
                        }
                        // ============ STEP END ============

                        //비밀번호 update
                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("userid", userId);
                        map.put("password", EncryptUtil.sha512HexEnc(newPassword));

                        int updateCnt = -1;
                        if("Y".equals(dormantYn)) {
                            updateCnt = loginSvc.updateNewPasswordDormant(map);
                        }else {
                            updateCnt = loginSvc.updateNewPassword(map);
                        }

                        if (updateCnt > 0) {
                            // 다크웹 대상자인경우 update
                            try{
                                int darkWebCtn = loginSvc.updateMcpUserDarkweb(userId);
                            } catch(DataAccessException e) {
                                logger.error("updateMcpUserDarkweb ERROR");
                            } catch(Exception e){
                                logger.error("updateMcpUserDarkweb ERROR");
                            }
                            result.put("RESULT_CODE", "S");
                        } else {
                            throw new McpCommonJsonException(COMMON_EXCEPTION);
                        }

                    }else{
                        result.put("RESULT_MSG", "비밀번호 패턴을 확인해 주시기 바랍니다.");
                    }
                } else {
                    //인증 정보와 다름. 비정상적인 호출
                    throw new McpCommonJsonException(F_BIND_EXCEPTION);
                }
            } else {
                //인증 정보와 다름. 비정상적인 호출
                throw new McpCommonJsonException(F_BIND_EXCEPTION);
            }
        } else {
            //인증 정보 없음. 비정상적인 접근
            result.put("RESULT_MSG", "인증정보가 없거나 유효하지 않은 접근입니다.");
        }

        return result;
    }

    /**
     * 설명 : 생일, 성별 추가 입력 레이어 노출 여부 ( 이게 왜 로그인? )
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param userSessionDto
     * @return
     */
    @RequestMapping(value = "/isBirthGenderAjax.do")
    @ResponseBody
    public JsonReturnDto isBirthGenderAjax(HttpServletRequest request, @ModelAttribute("userSessionDto") UserSessionDto userSessionDto ){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";
        Object json = null;

        /*캡챠체크 시작*/
        String getAnswer = "";
        if(request.getSession().getAttribute(Captcha.NAME) != null){
            Captcha captcha = (Captcha) request.getSession().getAttribute(Captcha.NAME);	//simpleCaptcha
            getAnswer = captcha.getAnswer();
        }

        HttpSession session = request.getSession();
        session.setAttribute("getAnswer", getAnswer);
        /*캡챠체크 종료*/

        /* recaptcha 결과 확인 */
        // 0. 구글캡챠 세션 초기화
        SessionUtils.saveRecaptchaSession(null);

        // 1. reacaptcha 적용 플랫폼 확인
        NmcpCdDtlDto resCodeVo= NmcpServiceUtils.getCodeNmDto("RECAPTCHAPLATFORM", NmcpServiceUtils.getPlatFormCd());
        String platformLimit= (resCodeVo == null) ? "Y" : resCodeVo.getExpnsnStrVal1();

        if("Y".equalsIgnoreCase(platformLimit)){

            // 2. recaptcha 차단 여부 확인 (default 차단시간 5분)
            resCodeVo= NmcpServiceUtils.getCodeNmDto("RECAPTCHALIMIT", "BLOCKSECOND");
            int blockTime= (resCodeVo == null) ? 300 : Integer.parseInt(resCodeVo.getExpnsnStrVal1());

            userSessionDto.setLimitTime(blockTime);
            userSessionDto.setLimitType("RECAPTCHA");
            boolean isBlocked = (loginSvc.limitCnt(userSessionDto) > 0) ? true : false;
            if(isBlocked){
                // 기존 무작위 로그인 시도와 같이 화면에 표출 > 메인페이지로 이동
                result.setReturnCode("98");
                result.setMessage("단시간 반복된 서비스 호출로 로그인이 제한됩니다. 잠시 후 이용 바랍니다.");
                return result;
            }

            // 3. 아이디/비밀번호 일치 여부와 상관 없이 동일 ip에서 5초 이내에 6회 실패 시 5분 차단
            int recaptchaFailCnt= loginSvc.getRecaptchaTraceCnt("RECAPTCHA_FAIL");
            resCodeVo= NmcpServiceUtils.getCodeNmDto("RECAPTCHALIMIT", "LIMITCOUNT");
            int limitCnt= (resCodeVo == null) ? 6 : Integer.parseInt(resCodeVo.getExpnsnStrVal1());
            if(recaptchaFailCnt >= limitCnt){
                // 기존 접속 제한 IP 테이블에 추가
                loginSvc.insertIpLimit(userSessionDto);

                // 기존 무작위 로그인 시도와 같이 화면에 표출 > 메인페이지로 이동
                result.setReturnCode("98");
                result.setMessage("단시간 반복된 서비스 호출로 로그인이 제한됩니다. 잠시 후 이용 바랍니다.");
                return result;
            }

            // 4. 결과 확인
            Map<String,String> recaptchaMap= loginSvc.getRecaptchaResult(userSessionDto);
            if(!"0000".equals(recaptchaMap.get("RESULT_CODE"))) {

                // success 여부가 true인데 점수를 통과하지 못한 경우 분기처리
                if("0004".equals(recaptchaMap.get("RESULT_CODE"))){
                    // 5. recaptcha 대체 SMS 인증 여부 확인 > sms 인증완료된 경우 recaptcha 점수에 상관없이 통과처리
                    if(!"Y".equals(SessionUtils.getRecaptchaSmsSession())){
                        returnCode = "96";
                        result.setReturnCode(returnCode);
                        result.setMessage(recaptchaMap.get("RESULT_MSG"));
                        return result;
                    }
                }else{
                    returnCode = "97";
                    result.setReturnCode(returnCode);
                    result.setMessage(recaptchaMap.get("RESULT_MSG"));
                    return result;
                }
            }
        }
        /* recaptcha 결과 종료 */

        UserSessionDto userDto = loginSvc.loginCheckFailCount(userSessionDto);

        if(userDto != null && userDto.isLogin()) { //아이디, 비번은 일치

            Map<String, String> certMap = loginSvc.certDateCheck(userDto);
            if(!"00".equals(certMap.get("code"))) {
                result.setReturnCode(certMap.get("code"));
                result.setMessage(certMap.get("message"));
                return result;
            }

            if( userDto.getBirthday() == null || "".equals(userDto.getBirthday()) ){	// 생일,성별이 없으면 입력받음
                returnCode = "40";
                message = "";
            } else {	//아이디, 비번 맞고 생일, 성별도 입력된상태
                returnCode = "00";
                message = "";

                String loginDivCd = StringUtil.NVL(userSessionDto.getLoginDivCd(),"");

                try {
                    if(!"".equals(StringUtil.NVL(userSessionDto.getUuid(), ""))
                            && "A".equals(NmcpServiceUtils.getPlatFormCd())
                            && ("I".equals(NmcpServiceUtils.getPhoneOs()) || "A".equals(NmcpServiceUtils.getPhoneOs()))){

                        AppInfoDTO appInfoDTO = new AppInfoDTO();
                        appInfoDTO.setUuid(userSessionDto.getUuid());
                        appInfoDTO.setUserId(userSessionDto.getUserId());
                        appSvc.mergeUsrAppInfo(appInfoDTO);

                        NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto = loginSvc.getLoginAutoLogin(userSessionDto);

                        if(nmcpAutoLoginTxnDto == null) {
                            loginSvc.insertAutoLoginTxn(userSessionDto);
                        }else {
                            loginSvc.updateAutoLoginTxn(userSessionDto);

                        }
                    }
                } catch(DataAccessException e) {
                    logger.error("appSvc.mergeUsrAppInfo Exception = {}", e.getMessage());
                } catch(Exception e) {
                    logger.error("appSvc.mergeUsrAppInfo Exception = {}", e.getMessage());
                }
            }
        } else {	//아이디, 비번이 불일치
            if (userDto == null || !userDto.isLoginFailCntOver()) {

                // 휴면 계정 관련 로직 추가 진행 2023.09.08
                UserSessionDto dormancyUserDto = null;

                // 휴면 계정 존재 여부 확인
                String rrWord = userSessionDto.getPassWord();
                String ppWord = EncryptUtil.sha512HexEnc(userSessionDto.getPassWord());
                userSessionDto.setPassWord(ppWord);

                dormancyUserDto = loginSvc.dormancyLoginProcess(userSessionDto);
                if(dormancyUserDto != null) {
                    SessionUtils.saveDormancySession(dormancyUserDto);	//휴면계정 세션저장
                    returnCode = "20";  // 뭘로 할지 몰라 일단 임의로 정함
                    message = "휴면 계정으로확인 되어 휴면 해제 진행페이지로 이동 합니다. ";
                }else {
                    returnCode = "42";
                    message = "아이디나 비밀번호가 정확하지 않습니다.  비밀번호 5회 이상 오류 시 로그인이 제한되며, 비밀번호 찾기 후 로그인이 가능합니다";
                }
                // 암호화된 패스워드 원복
                userSessionDto.setPassWord(rrWord);

            } else if (userDto.isLoginFailAttack())  {
                returnCode = "99";
                message = "비정상적인 로그인 시도가 탐지되어 비밀번호 변경 후 서비스 이용이 가능합니다. ";
            } else if ("LIMIT".equals(userDto.getStatus()))  {
                // 계정 존재하지 않는 케이스 무작위 로그인 시도 방지. 2023-03-21 BY 장익준
                returnCode = "98";
                message = "단시간 반복된 서비스 호출로 로그인이 제한됩니다. 잠시 후 이용 바랍니다.";
            }  else {
                returnCode = "43";
                message = "비밀번호 입력 오류가 5회 발생하여 로그인 제한 처리 되었습니다. ";
            }
        }

        /// 추가로 다크웹 작업하기 - 탈퇴회원때문에 하단에서 체크
        List<McpUserDarkwebDto> mcpUserDarkwebDtoList = new ArrayList<McpUserDarkwebDto>();
        if(userDto !=null){
            String userId = userSessionDto.getUserId();
            mcpUserDarkwebDtoList = loginSvc.getMcpUserDarkwebList(userId);
            if(mcpUserDarkwebDtoList !=null && !mcpUserDarkwebDtoList.isEmpty()){
                returnCode = "50";
                message = "다크웹 대상자 입니다.";
            }
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(json);
        return result;
    }

    /**
     * 설명 : 성별, 생년월일 노출 화면
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param userSessionDto
     * @param model
     * @return
     */
    @RequestMapping(value = {"/addBirthGenderView.do", "/m/addBirthGenderView.do"})
    public String addBirthGenderView(HttpServletRequest request, @ModelAttribute("userSessionDto") UserSessionDto userSessionDto, Model model) {

        if(userSessionDto.getLoginType() != null && "SNS".equals(userSessionDto.getLoginType())) {
            if(request.getSession().getAttribute("addBirthGender") != null && "Y".equals(request.getSession().getAttribute("addBirthGender"))) {
                if("Y".equals(NmcpServiceUtils.isMobile())){
                    return "redirect:/m/main.do";
                }else {
                    return "redirect:/main.do";
                }
            }
        }else {
            if(request.getSession().getAttribute("firstAppLogin") == null || "".equals(request.getSession().getAttribute("firstAppLogin"))) {
                UserSessionDto userSession = SessionUtils.getUserCookieBean();
                if(userSession != null) {
                    if("Y".equals(NmcpServiceUtils.isMobile())){
                        return "redirect:/m/main.do";
                    }else {
                        return "redirect:/main.do";
                    }
                }
            }
        }
        request.getSession().removeAttribute("addBirthGender");
        model.addAttribute("extraInfo", userSessionDto);
        if(userSessionDto.getLoginType() != null && "SNS".equals(userSessionDto.getLoginType())) {
            model.addAttribute("extraInfo", SessionUtils.getUserCookieBean());
        }

        if("Y".equals(request.getSession().getAttribute("firstAppLogin"))) {
            model.addAttribute("extraInfo", SessionUtils.getUserCookieBean());
            request.getSession().removeAttribute("firstAppLogin");
        }

        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/login/addBirthGenderView";
        }else {
            return "/portal/login/addBirthGenderView";
        }
    }

    /**
     * 설명 : 생일, 성별 저장
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param userSessionDto
     * @return
     */
    @RequestMapping(value = {"/m/addBirthGenderAjax.do", "/addBirthGenderAjax.do"})
    @ResponseBody
    public JsonReturnDto addBirthGenderAjax(HttpServletRequest request, @ModelAttribute("userSessionDto") UserSessionDto userSessionDto ){

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";
        Object json = null;

        if("SNS".equals(userSessionDto.getLoginType())) {
            UserSessionDto userSession = SessionUtils.getUserCookieBean();
            if (userSession != null) {
                userSessionDto.setUserId(StringUtil.NVL(userSession.getUserId(),""));
            }

        }
        int rtn = loginSvc.addBirthGenderAjax(userSessionDto);

        if(rtn == 1){
            returnCode = "00";
            message = "";
            if("SNS".equals(userSessionDto.getLoginType()) || "A".equals(NmcpServiceUtils.getPlatFormCd())) {
                returnCode = "01";
            }
            request.getSession().setAttribute("addBirthGender", "Y");
        }else{
            returnCode = "41";
            message = "입력하신정보가 정확하지 않습니다.";
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(json);
        return result;
    }

    /**
     * 설명 : 로그인 처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @param userSessionDto
     * @param request
     * @param response
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = {"/loginProcess.do","/m/loginProcess.do"})
    @Transactional
    public String loginProcess(@ModelAttribute("userSessionDto") UserSessionDto userSessionDto, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String redirectPage = null;
        String preFix = "";

        int errCode = 0;
        try {
            if("Y".equals(NmcpServiceUtils.isMobile())){
                preFix = "/m";
            }

            /*로그인후 이전페이지로 보내는 처리*/
            if(request.getParameter("uri") == null || "".equals(request.getParameter("uri"))){
                redirectPage = preFix + "/main.do";
            }else{
                redirectPage = URLDecoder.decode(request.getParameter("uri"),"UTF-8");
                redirectPage = redirectPage.replaceAll("&amp;", "&");
            }
            redirectPage = org.springframework.web.util.HtmlUtils.htmlUnescape(redirectPage);

            //외부 URL 차단
            if (redirectPage.startsWith("http://") || redirectPage.startsWith("https://")) {
                redirectPage = preFix + "/main.do";
            } else {
                //메뉴 리스트와 비교 후 없으면  main
                List<SiteMenuDto> allMenuList = NmcpServiceUtils.getMenuList("");
                boolean isValidRedirect = false;
                for (SiteMenuDto menu : allMenuList) {
                    String urlAdr = menu.getUrlAdr();
                    if (urlAdr != null && redirectPage.startsWith(urlAdr)) {
                        isValidRedirect = true;
                        break;
                    }
                }

                if(!isValidRedirect || redirectPage == null || redirectPage.indexOf("/loginProcess.do") > -1 || redirectPage.indexOf("/join/thirdPage.do") > -1
                        || redirectPage.indexOf("/findPassword.do") > -1 || redirectPage.indexOf("/login/dormantUserView.do") > -1) {
                    redirectPage = preFix + "/main.do";
                }

            }

            if(request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo"))) {
                String pageNo = request.getParameter("pageNo");
                StringBuilder sbRedirectPage  = new StringBuilder(redirectPage);
                sbRedirectPage.append("?pageNo=");
                sbRedirectPage.append(pageNo.replaceAll("?", ""));
                redirectPage = sbRedirectPage.toString();
            }

            /* app 로그인 체크 */
            String reqToken = StringUtil.NVL(request.getParameter("token"), "");
            String getAnswer = "";

            UserSessionDto userDto = null;
            UserSessionDto dormancyUserDto = null;
            if(!"".equals(reqToken)) {
                userDto = loginSvc.loginCheckAppAutoLogin(userSessionDto);
            } else {
                //정상계정 조회
                //userDto = loginSvc.loginProcess(userSessionDto);

                /* recaptcha 이력 확인 */
                // 1. reacaptcha 적용 플랫폼 확인
                NmcpCdDtlDto resCodeVo= NmcpServiceUtils.getCodeNmDto("RECAPTCHAPLATFORM", NmcpServiceUtils.getPlatFormCd());
                String platformLimit= (resCodeVo == null) ? "Y" : resCodeVo.getExpnsnStrVal1();

                if("Y".equalsIgnoreCase(platformLimit)) {

                    // 2. recaptcha 차단 여부 확인 (default 차단시간 5분)
                    resCodeVo= NmcpServiceUtils.getCodeNmDto("RECAPTCHALIMIT", "BLOCKSECOND");
                    int blockTime= (resCodeVo == null) ? 300 : Integer.parseInt(resCodeVo.getExpnsnStrVal1());

                    userSessionDto.setLimitTime(blockTime);
                    userSessionDto.setLimitType("RECAPTCHA");
                    boolean isBlocked = (loginSvc.limitCnt(userSessionDto) > 0) ? true : false;
                    if(isBlocked){
                        errCode = -2 ;
                        throw new McpCommonException("단시간 반복된 서비스 호출로 로그인이 제한됩니다.<br>잠시 후 이용 바랍니다.", "/loginForm.do");
                    }

                    // 2. 구글캡챠 세션이 존재해야 함
                    if(!"Y".equals(SessionUtils.getRecaptchaSession())){
                        // 3. 구글리캡챠 세션이 존재하지 않더라고, 대체 sms 인증을 받은 경우 통과 처리
                        if(!"Y".equals(SessionUtils.getRecaptchaSmsSession())){
                            errCode = -3 ;
                            throw new McpCommonException("페이지 새로고침 후 재시도 부탁드립니다.", "/loginForm.do");
                        }
                    }
                }
                /* recaptcha 이력 확인 종료 */

                userDto = loginSvc.loginCheckFailCount(userSessionDto,false);
                if(userDto != null && !userDto.isLogin()) { //아이디, 비번은 일치
                    errCode = -1 ;
                    throw new McpCommonException("비정상적인 로그인 시도 입니다.", "/loginForm.do");
                }

                //이것이 뭐지?????
                if(SessionUtils.getUserCookieBean() != null && userDto == null) {
                    userDto = SessionUtils.getUserCookieBean();
                }
            }

            //휴면계정 조회
            if(!"".equals(reqToken)) {
                dormancyUserDto = loginSvc.loginCheckAppAutoLoginDormancy(userSessionDto);
            }else {
                //정상계정 휴면 정보 조회
                dormancyUserDto = loginSvc.dormancyLoginProcess(userSessionDto);

            }

            if (userDto == null && dormancyUserDto == null) {
                ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
                responseSuccessDto.setSuccessMsg("아이디 또는 비밀번호가 일치하지 않습니다.");
                if("Y".equals(NmcpServiceUtils.isMobile())){
                    responseSuccessDto.setRedirectUrl("/m/loginForm.do");
                } else {
                    responseSuccessDto.setRedirectUrl("/loginForm.do");
                }
                model.addAttribute("responseSuccessDto", responseSuccessDto);
                return "/common/successRedirect";
            } else {

                String appUuid = StringUtil.NVL(SessionUtils.getAppUuidSession(),"");

                //SessionUtils.invalidateSession();

                if(!"".equals(appUuid)) {
                    // 세션에 uuid 저장
                    SessionUtils.saveAppUuidSession(appUuid);
                }

                if(userDto != null) {
                    SessionUtils.saveUserSession(userDto);	//사용자 세션저장
                }else {

                    SessionUtils.saveDormancySession(dormancyUserDto);	//휴면계정 세션저장
                    ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
                    responseSuccessDto.setSuccessMsg("kt M모바일을 오랫동안 이용하지 않아 아이디가</br>휴면 상태로 전환되었습니다.</br></br>본인인증을 통해 해제 후 서비스 이용 부탁드립니다.");
                    if("Y".equals(NmcpServiceUtils.isMobile())){
                        responseSuccessDto.setRedirectUrl("/m/login/dormantUserView.do");
                    } else {
                        responseSuccessDto.setRedirectUrl("/login/dormantUserView.do");
                    }
                    model.addAttribute("responseSuccessDto", responseSuccessDto);
                    return "/common/successRedirect";
                }

                if("A".equals(NmcpServiceUtils.getPlatFormCd())) {
                    if( userDto.getBirthday() == null || "".equals(userDto.getBirthday())                             ){	// 생일,성별이 없으면 입력받음
                        request.getSession().setAttribute("firstAppLogin", "Y");
                        return "redirect:/m/addBirthGenderView.do";
                    }
                }

                /*조회수 증가*/
                String userId = userDto.getUserId();
                loginSvc.updateHit(userId);

                //로그인 히스토리에 저장 시작
                LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
                loginHistoryDto.setUserid(userDto.getUserId());

                String inTypeStr = StringUtil.NVL(NmcpServiceUtils.getPlatFormCd(),"P");
                if("A".equals(inTypeStr)){
                    inTypeStr = NmcpServiceUtils.getPhoneOs();
                }
                loginHistoryDto.setIntype(inTypeStr);

                loginHistoryDto.setName(userDto.getName());
                loginHistoryDto.setPhone(userDto.getMobileNo());

                fCommonSvc.insertLoginHistory(loginHistoryDto);
                //로그인 히스토리에 저장 끝

                //model.addAttribute("platFormCd", NmcpServiceUtils.getPlatFormCd());
                //model.addAttribute("phoneOs", NmcpServiceUtils.getPhoneOs());
                if(userDto.getMonCnt() >= 3) {	//비밀번호 변경후 3개월 경과하면
                    if(userDto.getDayCnt() != -1 && userDto.getDayCnt() <= 14) {
                        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 비밀번호 변경후 3개월 경과 and 2주 안지남");
                    } else {


                        if(request.getParameter("uri") != null && !"".equals(request.getParameter("uri"))  ) {
                            if ("first".equals(redirectPage)) {
                                if("A".equals(NmcpServiceUtils.getPlatFormCd()) ) {
                                    String userDivision = StringUtil.NVL(userDto.getUserDivision(),"00");
                                    if("01".equals(userDivision)) {
                                        request.getSession().setAttribute("pwRedirectUrl", "/m/mypage/myinfoView.do");
                                    }
                                }
                            } else {
                                request.getSession().setAttribute("pwRedirectUrl", redirectPage);
                            }
                        }

                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            return "redirect:/m/pwChgInfoView.do";
                        }else {
                            return "redirect:/pwChgInfoView.do";
                        }
                    }
                }


                if("A".equals(NmcpServiceUtils.getPlatFormCd()) && "first".equals(redirectPage)) {

                    // 로그인 최종 성공 시 리캡챠 sms 대체 인증 세션 빼기
                    SessionUtils.saveRecaptchaSmsSession(null);

                    //APP에서 최초 접속 로그인 시.. 가입 페이지로 이동 처리
                    String userDivision = StringUtil.NVL(userDto.getUserDivision(),"00");
                    if("01".equals(userDivision)) {
                        return "redirect:/m/mypage/myinfoView.do";
                    } else {
                        return "redirect:/m/main.do";
                    }
                } else {

                    // 로그인 최종 성공 시 리캡챠 sms 대체 인증 세션 빼기
                    SessionUtils.saveRecaptchaSmsSession(null);

                    return "redirect:"+redirectPage;
                }
            }

        } catch(Exception e) {

            if (errCode == -1) {
                if("Y".equals(NmcpServiceUtils.isMobile())){
                    throw new McpCommonException("비정상적인 로그인 시도 입니다.", "/m/loginForm.do");
                } else {
                    throw new McpCommonException("비정상적인 로그인 시도 입니다.", "/loginForm.do");
                }
            } else if(errCode == -2){ // 구글캡챠 문구 분기처리
                if("Y".equals(NmcpServiceUtils.isMobile())) throw new McpCommonException("단시간 반복된 서비스 호출로 로그인이 제한됩니다.<br>잠시 후 이용 바랍니다.", "/m/loginForm.do");
                else throw new McpCommonException("단시간 반복된 서비스 호출로 로그인이 제한됩니다.<br>잠시 후 이용 바랍니다.", "/loginForm.do");
            } else if(errCode == -3){ // 구글캡챠 문구 분기처리
                if("Y".equals(NmcpServiceUtils.isMobile())) throw new McpCommonException("페이지 새로고침 후 재시도 부탁드립니다.", "/m/loginForm.do");
                else throw new McpCommonException("페이지 새로고침 후 재시도 부탁드립니다.", "/loginForm.do");
            }else {
                if(redirectPage == null) {
                    if("Y".equals(NmcpServiceUtils.isMobile())){
                        throw new McpCommonException(ExceptionMsgConstant.COMMON_EXCEPTION, "/m/main.do");
                    } else {
                        throw new McpCommonException(ExceptionMsgConstant.COMMON_EXCEPTION, "/main.do");
                    }
                } else {
                    throw new McpCommonException(ExceptionMsgConstant.COMMON_EXCEPTION, redirectPage);
                }
            }

        }
    }

    /**
     * 설명 : 휴면 계정 화면
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param session
     * @param model
     * @param tabId
     * @return
     */
    @RequestMapping(value = {"/login/dormantUserView.do", "/m/login/dormantUserView.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String dormantUserView(HttpServletRequest request, HttpSession session, Model model, String tabId) {

        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.DORMANCY_SESSION);
        if(userSession == null) {
            if("Y".equals(NmcpServiceUtils.isMobile())){
                //return "redirect:/m/main.do";
                return "mobile/login/dormantUserView";
            }else {
                //return "redirect:/main.do";
                return "portal/login/dormantUserView";
            }
        }else {

            // ============ STEP START ============
            // 이름, DI
            String[] certKey= {"urlType", "name", "dupInfo"};
            String[] certValue= {"dormantUserInfo", userSession.getName(), userSession.getPin()};
            certService.vdlCertInfo("C", certKey, certValue);
            // ============ STEP END =============

            if("Y".equals(NmcpServiceUtils.isMobile())){
                return "mobile/login/dormantUserView";
            }else {
                return "portal/login/dormantUserView";
            }
        }

    }

    /**
     * 설명 : 휴면회원 해제 처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @return
     */
    @RequestMapping(value = {"/updateDormantUserChgAjax.do", "/m/updateDormantUserChgAjax.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @Transactional
    public Map<String, Object> updateDormantUserChgAjax(HttpServletRequest request) {
        Map<String, Object> reslt = new HashMap<String, Object>();

        NiceResDto niceResDto = (NiceResDto) request.getSession().getAttribute(SessionUtils.NICE_AUT_COOKIE);

        if (niceResDto == null || StringUtils.isEmpty(niceResDto.getResSeq())) {
            reslt.put("resltCd", "-1");
            reslt.put("msg", "본인인증 정보가 유효하지 않습니다.");
            return reslt;
        }

        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.DORMANCY_SESSION);


        if(userSession!=null && StringUtils.isNotEmpty(userSession.getUserId())) {
            if(niceResDto.getDupInfo().equals(userSession.getPin())) {

                // ============ STEP STAT ============
                // 1. 최소 스텝 수 확인
                if(certService.getStepCnt() < 3 ){
                    reslt.put("resltCd","STEP01");
                    reslt.put("msg", STEP_CNT_EXCEPTION);
                    return reslt;
                }

                // 2. 최종 데이터 확인: 스텝종료여부, 이름, DI
                String[] certKey= {"urlType", "stepEndYn", "name", "dupInfo"};
                String[] certValue= {"updateDormantUser", "Y", userSession.getName(), userSession.getPin()};

                Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    reslt.put("resltCd","STEP02");
                    reslt.put("msg", vldReslt.get("RESULT_DESC"));
                    return reslt;
                }
                // ============ STEP END ============

                reslt.put("resltCd", "-9");
                reslt.put("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
                //1. MCP_USER INSERT (신규)
                int updReslt = loginSvc.insertDormantToMcpUser(niceResDto);

                //2. NMCP_USER_HST UPDATE (정상회원)
                if(updReslt > 0) {
                    updReslt = loginSvc.updateDormantUserChg(niceResDto.getDupInfo());
                    if(updReslt > 0) {

                        //정회원 테이블 데이터 확인 진행
                        int cnt = 0;

                        cnt = loginDao.selCntrCnt(userSession.getUserId());

                        if(cnt == 0) {  //정회원인데 정회원 데이터가 없을 경우에 문제가 되므로 아래 로직을 새로이 구성
                            //준회원으로 변경 처리 로직 추가
                            loginDao.updCntr(userSession.getUserId());
                        }

                        reslt.put("resltCd", "0000");
                        reslt.put("msg", "휴면회원 해제가 완료되었습니다.");
                    }
                }

                request.getSession().setAttribute(SessionUtils.DORMANCY_SESSION, null);
                SessionUtils.invalidateSession();
            }else {
                reslt.put("resltCd", "-2");
                reslt.put("msg", "가입된 아이디가 없습니다.\r\n다시 확인해 주세요.");
            }

        }else {
            reslt.put("resltCd", "-3");
            reslt.put("msg", "로그인 정보가 존재하지 않습니다.");
        }
        return reslt;
    }

    /**
     * 설명 : 로그아웃 처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @param nextUrl
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/m/logOutProcess.do", "/logOutProcess.do"})
    public String logOutProcess(@RequestParam(defaultValue="/main.do") String nextUrl
            , HttpServletRequest request
            , HttpServletResponse response
            , Model model) {

        request.getSession().setAttribute("userSessionDto", null);
        SessionUtils.invalidateSession();
        String goUrl = nextUrl;
        if("Y".equals(NmcpServiceUtils.isMobile())){
            goUrl = "/m/main.do";
        }

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) && ("I".equals(NmcpServiceUtils.getPhoneOs()) || "A".equals(NmcpServiceUtils.getPhoneOs()))){
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            responseSuccessDto.setSuccessMsg("AppLogOut");
            responseSuccessDto.setRedirectUrl(goUrl);
            model.addAttribute("phoneOs", NmcpServiceUtils.getPhoneOs());
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }else {
            return "redirect:"+ goUrl;
        }
    }

    /**
     * 설명 : 로그인 화면
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param redirectUrl
     * @param model
     * @return
     */
    @RequestMapping(value = {"/loginForm.do","/m/loginForm.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String loginForm(HttpServletRequest request, @RequestParam(defaultValue="",required=false) String redirectUrl, Model model) {
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);

        if(userSession!=null && StringUtils.isNotEmpty(userSession.getUserId())) {
            if("Y".equals(NmcpServiceUtils.isMobile())){
                return "redirect:/m/main.do";
            } else {
                return "redirect:/main.do";
            }
        }

        // 구글캡챠 세션 초기화
        SessionUtils.saveRecaptchaSession(null);

        try{
            String mapping = request.getServletPath();
            String uri = request.getParameter("uri");	//인터셉터에서 온 이전페이지 url
            if((uri == null || "".equals(uri) ) && (request.getHeader("referer") != null)){	//상단 로그인 버튼눌렀을경우 이전페이지 url
                uri = request.getHeader("referer").substring(request.getHeader("referer").indexOf("/", 8), request.getHeader("referer").length());
            }

            /* ---------------redirectUrl 을 직접 파라메터로 던저줄경우 처리 시작 ----------------------*/
            String unescapeRedirectUrl = org.springframework.web.util.HtmlUtils.htmlUnescape(redirectUrl);
            if (StringUtils.isNotEmpty(unescapeRedirectUrl)) {
                uri = unescapeRedirectUrl;
            }
            /* ---------------redirectUrl  을 직접 파라메터로 던저줄경우 처리 끝----------------------*/

            PersonalPolicyDto personalPolicyDto = loginSvc.personalPolicySelect();
            if(personalPolicyDto != null && !"".equals(StringUtil.NVL(personalPolicyDto.getPersonalinfoEditor(),""))){
                personalPolicyDto.setPersonalinfoEditor(ParseHtmlTagUtil.convertHtmlchars(personalPolicyDto.getPersonalinfoEditor()));
            }
            model.addAttribute("policy", personalPolicyDto);
            model.addAttribute("uri", uri);
            model.addAttribute("mapping", mapping);

            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if("LastSnsLogin".equals(cookie.getName()) && (cookie.getValue() != null && !"".equals(cookie.getValue())) && cookie.getValue().matches("^[a-zA-Z0-9]+$") ) {
                    //logger.info("[WOO][WOO][WOO]cookie.getValue()===>" + cookie.getValue());
                    model.addAttribute("LastSnsLogin", cookie.getValue());
                    break;
                }
            }
        } catch (Exception e) {
            logger.debug("ERROR loginForm : ", e.getMessage());
        }

        String returnUrl = "portal/login/loginForm";
        String platFormCd = NmcpServiceUtils.getPlatFormCd();

        /* [start] recaptcha 플랫폼 별 적용 여부 */
        NmcpCdDtlDto resCodeVo= NmcpServiceUtils.getCodeNmDto("RECAPTCHAPLATFORM", platFormCd);
        String recaptchaPlatform=  (resCodeVo == null) ? "Y" : resCodeVo.getExpnsnStrVal1();
        /* [end] recaptcha 플랫폼 별 적용 여부 */

        if("A".equals(platFormCd)){

            model.addAttribute("platFormCd", NmcpServiceUtils.getPlatFormCd());
            model.addAttribute("phoneOs", NmcpServiceUtils.getPhoneOs());
            model.addAttribute("recaptchaPlatform", recaptchaPlatform);

            //자동로그인 체크
            //useragent
            String uagent = request.getHeader("User-Agent");

            String autoLoginYn = "N";
            if(uagent.contains("AUTOLOGIN")) {
                autoLoginYn = "Y";

            }
            model.addAttribute("autoLoginYn", autoLoginYn);
            returnUrl = "mobile/login/loginFormApp";
        } else if("M".equals(platFormCd)) {
            returnUrl = "mobile/login/loginForm";
            model.addAttribute("platFormCd", NmcpServiceUtils.getPlatFormCd());
            model.addAttribute("phoneOs", NmcpServiceUtils.getPhoneOs());
            model.addAttribute("recaptchaPlatform", recaptchaPlatform);
        } else {
            returnUrl = "portal/login/loginForm";
            model.addAttribute("recaptchaPlatform", recaptchaPlatform);
        }


        return returnUrl;
    }

    /*캡챠의 이미지생성*/
    @RequestMapping(value = "CaptChaImg.do", method = {RequestMethod.GET, RequestMethod.POST})
    protected void captChaImg(HttpServletRequest req, HttpServletResponse res, @RequestParam (defaultValue="",required=false) String rand, UserSessionDto userSessionDto, Model model) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(!rand.equals(session.getAttribute("rand")) ){	//이전로그인시 session에 담아뒀던 rand값이랑 지금 파라미터로 넘어온 rand 값이랑 같으면 캡챠이미지 재생성 안함
            CaptCha.getCaptCha(req, res);

            String getAnswer = "";
            Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);	//simpleCaptcha
            getAnswer = captcha.getAnswer();

            session.setAttribute("getAnswer", getAnswer);

            if (StringUtil.isNumeric(rand)) {
                session.setAttribute("rand", rand);
            }

        }
    }

    @RequestMapping(value = "/callCaptChaAudioByKor.do")
    @ResponseBody
    protected void testSound(HttpServletRequest req, HttpServletResponse res) {
        Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);
        if (captcha == null ) {
            throw new McpCommonException(NO_SESSION_EXCEPTION);
        }
        VoiceProducer voiceProducerKor = null;
        voiceProducerKor = new VoiceProducerForKor(wavPath,serverName);
        AudioCaptcha audiocaptcha = new AudioCaptcha.Builder()
                .addAnswer(new SetTextProducer(captcha.getAnswer()))
                .addVoice(voiceProducerKor) //한글음성생성기를 AudioCaptcha에 적용
                .addNoise()
                .build();

        CaptchaServletUtil.writeAudio(res, audiocaptcha.getChallenge());

        Sample sample = audiocaptcha.getChallenge();

        res.setHeader("Cache-Control", "private,no-cache,no-store");
        res.setContentType("audio/x-wav");
        OutputStream os = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            AudioSystem.write(sample.getAudioInputStream(),
                    AudioFileFormat.Type.WAVE, baos);

            res.setContentLength(baos.size());

            os = res.getOutputStream();
            os.write(baos.toByteArray());
            os.flush();
            os.close();
        } catch (IOException e) {
            logger.error("ERROR testSound");
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("ERROR testSound");
                }
            }
        }
    }


    @RequestMapping(value = "/callCaptChaAudioByEng.do")
    @ResponseBody
    protected void engSound(HttpServletRequest req, HttpServletResponse res) {
        Captcha captcha = (Captcha) req.getSession().getAttribute(Captcha.NAME);
        if (captcha == null ) {
            throw new McpCommonException(NO_SESSION_EXCEPTION);
        }
        VoiceProducer voiceProducerKor = new VoiceProducerForEng();
        AudioCaptcha audiocaptcha = new AudioCaptcha.Builder()
                .addAnswer(new SetTextProducer(captcha.getAnswer()))
                .addVoice(voiceProducerKor) //한글음성생성기를 AudioCaptcha에 적용
                .addNoise()
                .build();

        CaptchaServletUtil.writeAudio(res, audiocaptcha.getChallenge());

        Sample sample = audiocaptcha.getChallenge();

        res.setHeader("Cache-Control", "private,no-cache,no-store");
        res.setContentType("audio/x-wav");
        OutputStream os = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            AudioSystem.write(sample.getAudioInputStream(),
                    AudioFileFormat.Type.WAVE, baos);

            res.setContentLength(baos.size());

            os = res.getOutputStream();
            os.write(baos.toByteArray());
            os.flush();
            os.close();
        } catch (IOException e) {
            logger.error("ERROR engSound");
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("ERROR testSound");
                }
            }
        }
    }

    /*비회원 로그인 sms발송*/
    @RequestMapping(value = "/sendCertSmsAjax.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonReturnDto sendCertSmsAjax(HttpServletRequest request, @ModelAttribute ("searchVO") UserSessionDto searchVO ) {

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";
        Object json = null;

        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setPhoneNum(searchVO.getMobileNo());

        boolean check = fCommonSvc.sendAuthSms(authSmsDto);
        if(check) {
            returnCode = "00";

            request.getSession().setAttribute("authNum", authSmsDto.getAuthNum());	//sms 인증번호를 session에 담아 비회원 로그인시 검증값으로 사용
            message = "";

        } else {
            returnCode = "41";
            message = "인증번호를 발송하지 못했습니다. 다시 시도해 주세요.";
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(json);
        return result;
    }

    /**
     * 설명 : 아이디 / 비밀번호 찾기 화면
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param session
     * @param model
     * @param tabId
     * @return
     */
    @RequestMapping(value = {"/findPassword.do", "/m/findPassword.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String mfindPassword(HttpServletRequest request, HttpSession session, Model model, String tabId) {

        //로그인 후 메인 페이지로 이동
        if (SessionUtils.hasLoginUserSessionBean()) {
            if("Y".equals(NmcpServiceUtils.isMobile())){
                return "redirect:/m/main.do";
            }else {
                return "redirect:/main.do";
            }
        }

        // 본인인증 처음부터 진행
        SessionUtils.saveNiceRes(null);
        model.addAttribute("tabId", tabId);

        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/login/findPassword";

        }else {
            return "/portal/login/findPassword";

        }
    }

    /**
     * 설명 : 비밀번호 2주동안 안보이기 처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @return
     */
    @RequestMapping({"/updatePwChgInfoNoShowAjax.do", "/m/updatePwChgInfoNoShowAjax.do"})
    @ResponseBody
    public Map<String, Object> updatePwChgInfoNoShowAjax() {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("resultCd", "-2");
        result.put("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");

        try {
            UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
            if(userSessionDto != null) {
                int chk = loginSvc.updatePwChgInfoNoShow(userSessionDto.getUserId());
                if(chk == 1) {
                    result.put("resultCd", "0000");
                    result.put("msg", "2주동안 안보이도록 설정하였습니다.");
                }
            }else {
                result.put("resultCd", "-1");
                result.put("msg", "로그인 정보가 존재하지 않습니다.");
            }
        } catch(DataAccessException e) {
            logger.error("updatePwChgInfoNoShowAjax Exception = {}", e.getMessage());
        } catch(Exception e) {
            logger.error("updatePwChgInfoNoShowAjax Exception = {}", e.getMessage());
        }

        return result;
    }

    /**
     * 설명 : 비밀번호 변경 안내 화면
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping({"/pwChgInfoView.do", "/m/pwChgInfoView.do"})
    public String pwChgInfoView(HttpServletRequest request, Model model) {
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if(request.getSession().getAttribute("pwRedirectUrl") != null && !"".equals(request.getSession().getAttribute("pwRedirectUrl"))) {
            model.addAttribute("pwRedirectUrl", request.getSession().getAttribute("pwRedirectUrl"));
        }

        SessionUtils.invalidateSession();
        if(userSessionDto != null) {
            SessionUtils.saveDummySession(userSessionDto);
        }

        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/login/pwChgInfoView";
        }else {
            return "/portal/login/pwChgInfoView";
        }
    }

    /**
     * 설명 : 비밀번호 변경 안내시 저장한 세션 조회
     * @Author : jsh
     * @Date : 2021.12.30
     * @param resuest
     * @return
     */
    @RequestMapping({"/getDummySession.do", "/m/getDummySession.do"})
    @ResponseBody
    public Map<String, String> getDummySession(HttpServletRequest resuest) {
        Map<String, String> result = new HashMap<String, String>();
        UserSessionDto userSessionDto = SessionUtils.getUserDummyCookieBean();
        SessionUtils.invalidateSession();
        if(userSessionDto != null) {
            SessionUtils.saveUserSession(userSessionDto);
            result.put("resultCd", "0000");
        }else {
            result.put("resultCd", "-1");
        }
        return result;
    }

    /**
     * 설명 : 정상계정 + 휴면계정 중복회원 보완처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @param resuest
     * @param searchVO
     * @return
     */
    @RequestMapping({"/resetDormancy.do", "/m/resetDormancy.do"})
    @ResponseBody
    public Map<String, String> resetDormancy(HttpServletRequest resuest, @ModelAttribute ("searchVO") UserSessionDto searchVO) {

        Map<String, String> result = new HashMap<String, String>();
        result.put("RESULT_CODE", "E0001");
        result.put("RESULT_MSG", "처리중 오류가 발생하였습니다.");

        NiceResDto niceResDto = SessionUtils.getNiceResCookieBean();
        try {
            if (niceResDto != null && !StringUtils.isEmpty(niceResDto.getDupInfo())) { //인증정보 체크

                // ============ STEP START ============
                // 1. 최소 스텝 수 확인
                if(certService.getStepCnt() < 1 ){
                    throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
                }

                // 2. 최종 데이터 확인: 스텝종료여부, 생년월일, DI
                String[] certKey= {"urlType", "stepEndYn", "birthDate", "dupInfo"};
                String[] certValue= {"resetUserId", "Y", niceResDto.getBirthDate(), niceResDto.getDupInfo()};

                Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
                }

                searchVO.setBirthday(niceResDto.getBirthDate());
                searchVO.setPin(niceResDto.getDupInfo());
                // ============ STEP END ============

                //중복 회원 처리
                int pinCnt = loginSvc.resetDormancy(searchVO);
                if(pinCnt > 0) {
                    SessionUtils.invalidateSession();
                    result.put("RESULT_CODE", "S");
                }
            }
        } catch(DataAccessException e) {
            logger.error("resetDormancy Exception e : {}", e.getMessage());
        } catch(Exception e) {
            logger.error("resetDormancy Exception e : {}", e.getMessage());
        }
        return result;
    }



    /**
     * 설명 : 정상계정 + 휴면계정 중복회원 보완처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @param resuest
     * @param searchVO
     * @return
     */
    @RequestMapping("/setIgnoredScriptSession.do")
    @ResponseBody
    public Map<String, String> setIgnoredScriptSession(@ModelAttribute ("ignoredScript") String ignoredScript) {

        //관리자 정보 session 저장
        SessionUtils.setIgnoredScriptSession(ignoredScript);

        Map<String, String> result = new HashMap<String, String>();
        result.put("RESULT_CODE", "00000");

        return result;
    }
}
