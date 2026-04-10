package com.ktmmobile.mcp.common.controller;

import static com.ktmmobile.mcp.common.constants.Constants.CUST_AUTH;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ktmmobile.mcp.common.dto.*;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.*;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.app.dto.AppInfoDTO;
import com.ktmmobile.mcp.app.service.AppSvc;
//import com.ktmmobile.mcp.common.dto.NaverDto;
//import com.ktmmobile.mcp.common.dto.NiceLogDto;
import com.ktmmobile.mcp.common.dto.db.NmcpAutoLoginTxnDto;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
//import com.ktmmobile.mcp.common.service.NaverCertifySvc;
//import com.ktmmobile.mcp.common.service.NiceLogSvc;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.join.service.JoinSvc;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : NaverCertifyController.java
 * 날짜     : 2021. 6. 17. 오후 2:51:41
 * 작성자   : kim
 * 설명     : 네이버
 * </pre>
 */
@Controller
public class NaverCertifyController {

    private static final Logger logger = LoggerFactory.getLogger(NaverCertifyController.class);

    @Autowired
    private NaverCertifySvc naverCertifySvc;

    @Autowired
    private SnsSvc snsSvc;

    @Autowired
    private LoginSvc loginSvc;

    @Autowired
    JoinSvc joinSvc;

    @Autowired
    NiceLogSvc nicelog ;

    @Autowired
    NiceCertifySvc niceCertifySvc;


    /**
     * 설명 :
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param reqNaverDto
     * @param model
     * @return
     */
    @RequestMapping(value = {"/m/cert/nearoCallBack.do", "/cert/nearoCallBack.do"})
    public String nearoCallBack(HttpServletRequest request,NaverDto reqNaverDto,Model model) {
        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/naver/nearoCallBack";
        }else {
            return "/portal/naver/nearoCallBack";
        }
    }

    /**
     * 설명 : 네이버 본인인증 이후 처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param reqNaverDto
     * @param model
     * @return
     */
    @RequestMapping(value = {"/m/cert/nearoCallBackJson.do", "/cert/nearoCallBackJson.do"})
    @ResponseBody
    public HashMap<String, Object> nearoCallBackJson(HttpServletRequest request,NaverDto reqNaverDto,Model model) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // SMS 인증 완료 여부 확인 - 신규셀프개통인 경우만
        if(!niceCertifySvc.preChkSimpleAuth()){
            rtnMap.put("rtnMsg", "fail");
            rtnMap.put("errMsg", F_BIND_EXCEPTION);
            return rtnMap;
        }

        String accessToken = reqNaverDto.getAccessToken();
        String platform = reqNaverDto.getPlatform();
        String remoteAddress = StringUtil.NVL(request.getRemoteAddr(),"");
        String name = reqNaverDto.getName();

        NaverDto naverDto = new NaverDto();
        naverDto.setAccessToken(accessToken);
        naverDto.setPlatform(platform);
        naverDto.setRemoteAddress(remoteAddress);

        String result = naverCertifySvc.naverAuthSession(naverDto); // 본인인증 알람 요청

        String rtnMsg = "";
        String pollingPageUrl = "";
        String id = "";
        String txId = "";
        String errMsg = "";


        try {
            JSONObject jObject = new JSONObject(result);

            rtnMsg = jObject.getString("rtnMsg");  // 요청 결과
            if("success".equals(rtnMsg)){

                pollingPageUrl = jObject.getString("pollingPageUrl"); // 리다이렉트할 페이지 URL
                id = jObject.getString("id"); // 동일인 식별 정보, 네이버 아이디마다 고유하게 발급되는 유니크한 일련번호 값
                txId = jObject.getString("txId"); // 인증 식별값

                // insert 또는 업데이트 id,txid,accessToken : id 만 고객고유번호 나머지는 변경됨
                reqNaverDto.setTxId(txId);
                reqNaverDto.setId(id);
                reqNaverDto.setName(name);
                reqNaverDto.setPlatform(platform);

                naverCertifySvc.updateMcpAuthData(reqNaverDto);
            } else if("invalid_login_info".equals(rtnMsg)){
                errMsg = "Access Token이 유효하지 않습니다.";
            } else if("invalid_param".equals(rtnMsg)){
                errMsg = "파라미터가 유효하지 않습니다.";
            } else if("invalid_callback_url".equals(rtnMsg)){
                errMsg = "invalid_callback_url";
            } else if("not_realname_user".equals(rtnMsg)){
                errMsg = "실명회원이 아니거나, 본인인증된 회원이 아닙니다.";
            } else if("not_allowed_client".equals(rtnMsg)){
                errMsg = "네이버인증 scope가 허용되어있지 않습니다.";
            } else if("internal_server_error".equals(rtnMsg)){
                errMsg = "내부 서버 오류입니다.";
            } else if("too_many_requests".equals(rtnMsg)){
                errMsg = "요청이 많아 처리할 수 없습니다.";
            } else {
                errMsg = "실패입니다.";
            }

        } catch (JSONException e) {
            logger.error("nearoCallBackJson JSONException error:"+e.getMessage());
        } catch(Exception e1){
            logger.error("nearoCallBackJson JSONException error:"+e1.getMessage());
        }

        rtnMap.put("pollingPageUrl", pollingPageUrl);
        rtnMap.put("rtnMsg", rtnMsg);
        rtnMap.put("errMsg", errMsg);

        return rtnMap;
    }

    /**
     * 설명 : 네이버 본인인증 리턴(LOCAL)
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param reqNaverDto
     * @param model
     * @return
     */
    @RequestMapping(value = {"/m/cert/naverCallBackPage.do","/cert/naverCallBackPage.do"})
    public String naverCallBack(HttpServletRequest request,NaverDto reqNaverDto,Model model) {

        // 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록
        NiceTryLogDto niceTryDto= new NiceTryLogDto();
        niceTryDto.setAuthType("N");
        niceTryDto.setSuccYn("N");

        // 네이버 본인인증 데이터 조회
        NaverDto resDto = new NaverDto();
        String result = "";
        String authResult = "";
        String birthyear = "";
        String birthday = "";
        String gender = "";
        String name = "";
        String mobile = "";
        String errMsg = "";
        // String signature = "";
        String profileId = "";
        String profileCi = "";

        String txId = StringUtil.NVL(reqNaverDto.getTxId(),"");
        if(!"".equals(txId)){

            reqNaverDto.setTxId(txId);
            niceTryDto.setReqSeq(txId);

            resDto = naverCertifySvc.getMcpAuthData(reqNaverDto); // resDto에 ID, NAME, TXID, ACCESS_TOKEN, PLATFORM 세팅
            result = naverCertifySvc.naverGetResult(resDto);
            niceTryDto.setSysRdate(resDto.getSysRdate());

            try{

                JSONObject jObject = new JSONObject(result);
                JSONObject profileObject = (JSONObject) jObject.get("profile");
                //JSONObject assertionObject = (JSONObject) jObject.get("assertion");

                authResult = jObject.getString("authResult"); // 인증 결과
                if("success".equals(authResult)){ // 성공

                    // 암호화 값
                    birthday = profileObject.getString("birthday");
                    birthyear = profileObject.getString("birthyear");
                    gender = profileObject.getString("gender");
                    name = profileObject.getString("name");
                    mobile = profileObject.getString("mobile");
                    profileId = profileObject.getString("id");

                    // 복호화 값
                    birthday= EncryptUtil.ace256Dec(birthday); // mm-dd
                    birthyear= EncryptUtil.ace256Dec(birthyear); // yyyy
                    gender= EncryptUtil.ace256Dec(gender); // F, M
                    name= EncryptUtil.ace256Dec(name);
                    mobile= EncryptUtil.ace256Dec(mobile);
                    profileId= EncryptUtil.ace256Dec(profileId);

                    try {
                        profileCi = profileObject.getString("ci");
                        profileCi = EncryptUtil.ace256Dec(profileCi);
                    } catch (Exception e) {
                        profileCi = "NULL";
                    }

                    // 데이터 보정
                    String birthdate= birthyear+birthday.replaceAll("-", ""); // yyyymmdd
                    if (birthdate.length() > 7) {
                        birthdate = birthdate.substring(2, 8); // yymmdd
                    }

                    if("F".equals(gender)) {
                        gender= "0";  // 여자
                    }else {
                        gender= "1";  // 남자
                    }

                    // ========== 본인인증 리턴 로직 개선 START ==========

                    // 1. 본인인증 이력 저장
                    NiceLogDto niceLogDto = new NiceLogDto();
                    niceLogDto.setName(name);
                    niceLogDto.setBirthDate(birthdate);
                    niceLogDto.setReqSeq(reqNaverDto.getTxId());
                    niceLogDto.setResSeq(profileId);
                    niceLogDto.setConnInfo(profileCi);
                    niceLogDto.setDupInfo(profileId);
                    niceLogDto.setGender(gender);
                    //niceLogDto.setNationalInfo("0"); 넘어오는 국가정보 없으므로 주석 처리
                    niceLogDto.setSysRdate(resDto.getSysRdate()); //최초 요청 시간
                    niceLogDto.setAuthType("N");
                    niceLogDto.setParamR3(CUST_AUTH);
                    nicelog.saveMcpNiceHist(niceLogDto);

                    // 1. 화면 리턴값
                    NiceResDto niceResDtoSess = new NiceResDto();
                    niceResDtoSess.setReqSeq(reqNaverDto.getTxId());
                    niceResDtoSess.setResSeq(profileId);
                    niceResDtoSess.setAuthType("N");

                    model.addAttribute("NiceRes", niceResDtoSess) ;

                    // 3. 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록 (성공)
                    niceTryDto.setName(name);
                    niceTryDto.setBirthDate(EncryptUtil.ace256Enc(birthdate));
                    niceTryDto.setResSeq(profileId);
                    niceTryDto.setConnInfo(profileCi);
                    niceTryDto.setSuccYn("Y");

                    // ========== 본인인증 리턴 로직 개선 END ==========

                } else if("cancel".equals(authResult)){
                    errMsg = "인증 취소했습니다.";
                } else if("pending".equals(authResult)){
                    errMsg = "인증 진행 중입니다.";
                } else if("invalid_login_info".equals(authResult)){
                    errMsg = "Access Token이 유효하지 않습니다.";
                } else if("invalid_param".equals(authResult)){
                    errMsg = "파라미터가 유효하지 않습니다.";
                } else if("not_allowed_client".equals(authResult)){
                    errMsg = "네이버인증 scope가 허용되어있지 않습니다.";
                } else if("internal_server_error".equals(authResult)){
                    errMsg = "내부 서버 오류입니다.";
                } else if("too_many_requests".equals(authResult)){
                    errMsg = "요청이 많아 처리할 수 없습니다.";
                } else {
                    errMsg = "실패입니다.";
                }

            }catch(Exception e){
                logger.error("naverCallBack JSONException error:"+e.getMessage());
            }
        }

        nicelog.insertMcpNiceTryHist(niceTryDto);

        model.addAttribute("authResult", authResult);
        model.addAttribute("errMsg", errMsg);

        return "/portal/naver/naverCallBackPage";
    }

    /**
     * 설명 : 네이버 로그인 리턴페이지
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param reqNaverDto
     * @param model
     * @return
     */
    @RequestMapping(value = {"/m/cert/nearoCallBackLogin.do", "/cert/nearoCallBackLogin.do"})
    public String nearoCallBackLogin(HttpServletRequest request,NaverDto reqNaverDto,Model model) {
    	/*
    	if("Y".equals(NmcpServiceUtils.isMobile())){
    		return "/mobile/naver/nearoCallBackLogin";
    	} else {
    		return "/portal/naver/nearoCallBackLogin";
    	}
    	*/

        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/naver/naverLoginCallBack";
        } else {
            return "/portal/naver/naverLoginCallBack";
        }

    }

    /**
     * 설명 : 네이버 로그인 이후 로그인 처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping({"/m/cert/nearoCallBackLoginProcese.do","/cert/nearoCallBackLoginProcese.do"})
    public String nearoCallBackLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {

            HttpSession session = request.getSession();

            if (request.getParameter("id") != null && !request.getParameter("id").equals("")) {
                String naverUniqueNo = request.getParameter("id").toString();
                String snsGender = request.getParameter("gender").toString();
                String snsEmail = request.getParameter("email").toString();

                SnsLoginDto snsLoginDto = new SnsLoginDto();
                snsLoginDto = snsSvc.selectSnsIdCheck(naverUniqueNo);

                if(snsLoginDto != null) {
                    //로그인 처리 필요
                    UserSessionDto userSessionDto = snsSvc.selectSnsMcpUser(snsLoginDto.getUserId());

                    //휴면계정 조회
                    UserSessionDto param = new UserSessionDto();
                    param.setUserId(snsLoginDto.getUserId());
                    UserSessionDto dormancyUserDto = loginSvc.dormancyLoginProcess(param);

                    if(userSessionDto == null && dormancyUserDto == null) {
                        ResponseSuccessDto responseSuccessDto = noSnsInfo(snsLoginDto);

                        model.addAttribute("resultCd", "-2");
                        model.addAttribute("msg", responseSuccessDto.getSuccessMsg());
                        model.addAttribute("returnUrl", "/loginForm.do");
                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            model.addAttribute("returnUrl", "/m/loginForm.do");
                        }

                        return "/mobile/login/snsCalbk";

                    }else {

                        if(dormancyUserDto != null) {
                            SessionUtils.invalidateSession();
                            SessionUtils.saveDormancySession(dormancyUserDto);	//휴면계정 세션저장

                            model.addAttribute("resultCd", "-2");
                            model.addAttribute("msg", "kt M모바일을 오랫동안 이용하지 않아 아이디가</br>휴면 상태로 전환되었습니다.</br></br>본인인증을 통해 해제 후 서비스 이용 부탁드립니다.");
                            model.addAttribute("returnUrl", "/login/dormantUserView.do");
                            if("Y".equals(NmcpServiceUtils.isMobile())){
                                model.addAttribute("returnUrl", "/m/login/dormantUserView.do");
                            }
                            return "/mobile/login/snsCalbk";

                        }

		    			String resltCd = snsSvc.snsLoginProcess(request, userSessionDto, naverUniqueNo);
                        String redirectPage = "/main.do";
                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            redirectPage = "/m/main.do";
                        }

                        setSnsLoginCookie(response, "NAVER");

		    			if("-1".equals(resltCd)) {
                            model.addAttribute("resultCd", "-3");
                            model.addAttribute("msg", "");
                            model.addAttribute("returnUrl", "/pwChgInfoView.do");
                            if("Y".equals(NmcpServiceUtils.isMobile())){
                                model.addAttribute("returnUrl", "/m/pwChgInfoView.do");
                            }
                            return "/mobile/login/snsCalbk";

		    			}else if("-2".equals(resltCd)){
                            model.addAttribute("resultCd", "-4");
                            model.addAttribute("msg", "");
                            model.addAttribute("returnUrl", "/addBirthGenderView.do?loginType=SNS");
                            if("Y".equals(NmcpServiceUtils.isMobile())){
                                model.addAttribute("returnUrl", "/m/addBirthGenderView.do?loginType=SNS");
                            }
                            return "/mobile/login/snsCalbk";

                        }else {
                            model.addAttribute("resultCd", "0000");
                            model.addAttribute("msg", "");
                            model.addAttribute("returnUrl", redirectPage);

                            return "/mobile/login/snsCalbk";
                        }
                    }
                }else {
                    // 회원정보 수정 > 소셜로그인 관리  소셜로그인시 즉시 연동처리
                    if(session.getAttribute("snsLoginPreUrl") != null && session.getAttribute("snsLoginPreUrl").toString().indexOf("userSnsList") > -1) {
                        if (SessionUtils.hasLoginUserSessionBean()) {
                            UserSessionDto userSession =  null;
                            userSession = SessionUtils.getUserCookieBean();
                            JoinDto joinDto = new JoinDto();
                            joinDto.setUserId(userSession.getUserId());
                            joinDto.setSnsCd("NAVER");
                            joinDto.setSnsId(EncryptUtil.ace256Enc(naverUniqueNo));
                            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
                            try {
                                joinSvc.insertSnsInfo(joinDto);
                                responseSuccessDto.setSuccessMsg("정상적으로 연결되었습니다.");
                                model.addAttribute("msg", "정상적으로 연결되었습니다.");
                            } catch(DataAccessException e) {
                                responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
                                model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
                            } catch(Exception e){
                                responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
                                model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
                            }
                            responseSuccessDto.setRedirectUrl("/m/mypage/userSnsList.do");
                            model.addAttribute("responseSuccessDto", responseSuccessDto);
                        }
                        model.addAttribute("resultCd", "-5");
                        model.addAttribute("returnUrl", "/mypage/userSnsList.do");
                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            model.addAttribute("returnUrl", "/m/mypage/userSnsList.do");
                        }
                        return "/mobile/login/snsCalbk";


                    }else {
                        //회원가입용
                        request.getSession().setAttribute("snsCd", "NAVER");
                        request.getSession().setAttribute("snsId", EncryptUtil.ace256Enc(naverUniqueNo));
                        request.getSession().setAttribute("snsGender", snsGender);
                        request.getSession().setAttribute("snsEmail", snsEmail);
                        model.addAttribute("resultCd", "-6");
                        model.addAttribute("msg", "가입되어 있지 않은 소셜 계정입니다. 회원가입으로 이동합니다.");
                        model.addAttribute("returnUrl", "/join/fstPage.do");
                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            model.addAttribute("returnUrl", "/m/join/fstPage.do");
                        }
                        return "/mobile/login/snsCalbk";
                    }
                }

                // 네이버 정보조회 실패
            } else {
                model.addAttribute("resultCd", "-7");
                model.addAttribute("msg", "네이버 정보조회에 실패했습니다.");
                model.addAttribute("returnUrl", "/loginForm.do");
                if("Y".equals(NmcpServiceUtils.isMobile())){
                    model.addAttribute("returnUrl", "/m/loginForm.do");
                }
                return "/mobile/login/snsCalbk";

            }
        } catch(DataAccessException e) {
            model.addAttribute("resultCd", "-7");
            model.addAttribute("msg", "네이버 정보조회에 실패했습니다.");
            model.addAttribute("returnUrl", "/loginForm.do");
            if("Y".equals(NmcpServiceUtils.isMobile())){
                model.addAttribute("returnUrl", "/m/loginForm.do");
            }
            return "/mobile/login/snsCalbk";
        } catch(Exception e) {
            model.addAttribute("resultCd", "-7");
            model.addAttribute("msg", "네이버 정보조회에 실패했습니다.");
            model.addAttribute("returnUrl", "/loginForm.do");
            if("Y".equals(NmcpServiceUtils.isMobile())){
                model.addAttribute("returnUrl", "/m/loginForm.do");
            }
            return "/mobile/login/snsCalbk";
        }
    }

    /**
     * 설명 : SNS 정보 없을때 처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @param snsLoginDto
     * @return
     */
    public ResponseSuccessDto noSnsInfo(SnsLoginDto snsLoginDto) {

        //SNS 로그인 실패 정보 저장
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("loginResult", "-1");
        param.put("snsId", snsLoginDto.getSnsId());
        param.put("userId", snsLoginDto.getUserId());
        snsSvc.updateSnsLoginInfo(param);

        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setSuccessMsg("존재하지 않거나 유효하지 않은 아이디입니다.");
        if("Y".equals(NmcpServiceUtils.isMobile())){
            responseSuccessDto.setRedirectUrl("/m/loginForm.do");
        } else {
            responseSuccessDto.setRedirectUrl("/loginForm.do");
        }

        return responseSuccessDto;
    }

    /**
     * 설명 : 마지막 SNS 로그인 쿠키저장
     * @Author : jsh
     * @Date : 2021.12.30
     * @param response
     * @param snsCd
     */
    public void setSnsLoginCookie(HttpServletResponse response, String snsCd) {
        Cookie cookie = new Cookie("LastSnsLogin", snsCd);
        cookie.setSecure(true);
//        cookie.setMaxAge(60*60*24*30); //기존로직
        cookie.setMaxAge(60*60);  // 취약성 242
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 설명 : 네이버 본인인증 취소 처리
     * @Author : jsh
     * @Date : 2021.12.30
     * @param request
     * @param reqNaverDto
     * @param model
     * @return
     */
    @RequestMapping(value = {"/cert/naverFailPage.do","/m/cert/naverFailPage.do"})
    public String naverFailPage(HttpServletRequest request,NaverDto reqNaverDto,Model model) {

        // 본인인증 요청 알람(push) 인증취소 응답 건 로그 기록
        NiceTryLogDto niceTryDto= new NiceTryLogDto();
        niceTryDto.setAuthType("N");
        niceTryDto.setSuccYn("N");

        if(reqNaverDto != null){
            if(!StringUtil.isEmpty(reqNaverDto.getTxId())){
                niceTryDto.setReqSeq(reqNaverDto.getTxId());

                // 네이버 본인인증 요청 데이터 조회
                NaverDto resDto = naverCertifySvc.getMcpAuthData(reqNaverDto);
                if (resDto != null)  niceTryDto.setSysRdate(resDto.getSysRdate());
            }
        }

        nicelog.insertMcpNiceTryHist(niceTryDto);
        return "/portal/naver/naverFailPage";
    }


    @RequestMapping(value ={"/cert/naverLoginCallBackJson.do","/m/cert/naverLoginCallBackJson.do"} )
    public String naverLoginCallBack(HttpServletRequest request,HttpServletResponse response,NaverDto reqNaverDto,Model model) {

        if (StringUtils.isBlank(reqNaverDto.getAccessToken())) {
            throw new McpCommonException(INVALID_PARAMATER_EXCEPTION);
        }
        String result = naverCertifySvc.naverLoginCallBack(reqNaverDto);

        String authResult = "";
        String naverUniqueNo = "";
        String snsGender = "";
        String snsEmail = "";

        try {
            JSONObject jObject = new JSONObject(result);
            authResult = jObject.getString("resultcode"); // 인증 결과

            if("00".equals(authResult)){ // 성공
                // 암호화 값
                naverUniqueNo = jObject.getString("id");
                snsGender = jObject.getString("gender");
                snsEmail = jObject.getString("email");

                // 복호화 값
                naverUniqueNo= EncryptUtil.ace256Dec(naverUniqueNo); // mm-dd
                snsGender= EncryptUtil.ace256Dec(snsGender); // yyyy
                snsEmail= EncryptUtil.ace256Dec(snsEmail); // F, M

                SnsLoginDto snsLoginDto = new SnsLoginDto();
                snsLoginDto = snsSvc.selectSnsIdCheck(naverUniqueNo);

                if(snsLoginDto != null) {
                    //로그인 처리 필요
                    UserSessionDto userSessionDto = snsSvc.selectSnsMcpUser(snsLoginDto.getUserId());

                    //휴면계정 조회
                    UserSessionDto param = new UserSessionDto();
                    param.setUserId(snsLoginDto.getUserId());
                    UserSessionDto dormancyUserDto = loginSvc.dormancyLoginProcess(param);

                    if(userSessionDto == null && dormancyUserDto == null) {
                        ResponseSuccessDto responseSuccessDto = noSnsInfo(snsLoginDto);
                        model.addAttribute("resultCd", "-2");
                        model.addAttribute("msg", responseSuccessDto.getSuccessMsg());
                        model.addAttribute("returnUrl", "/loginForm.do");
                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            model.addAttribute("returnUrl", "/m/loginForm.do");
                        }
                        return "/mobile/login/snsCalbk";
                    } else {

                        if(dormancyUserDto != null) {
                            SessionUtils.invalidateSession();
                            SessionUtils.saveDormancySession(dormancyUserDto);	//휴면계정 세션저장

                            model.addAttribute("resultCd", "-2");
                            model.addAttribute("msg", "kt M모바일을 오랫동안 이용하지 않아 아이디가</br>휴면 상태로 전환되었습니다.</br></br>본인인증을 통해 해제 후 서비스 이용 부탁드립니다.");
                            model.addAttribute("returnUrl", "/login/dormantUserView.do");
                            if("Y".equals(NmcpServiceUtils.isMobile())){
                                model.addAttribute("returnUrl", "/m/login/dormantUserView.do");
                            }
                            return "/mobile/login/snsCalbk";

                        }

                        String resltCd = snsSvc.snsLoginProcess(request, userSessionDto, naverUniqueNo);
                        String redirectPage = "/main.do";
                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            redirectPage = "/m/main.do";
                        }

                        setSnsLoginCookie(response, "NAVER");

                        if("-1".equals(resltCd)) {
                            model.addAttribute("resultCd", "-3");
                            model.addAttribute("msg", "");
                            model.addAttribute("returnUrl", "/pwChgInfoView.do");
                            if("Y".equals(NmcpServiceUtils.isMobile())){
                                model.addAttribute("returnUrl", "/m/pwChgInfoView.do");
                            }
                            return "/mobile/login/snsCalbk";

                        } else if("-2".equals(resltCd)){
                            model.addAttribute("resultCd", "-4");
                            model.addAttribute("msg", "");
                            model.addAttribute("returnUrl", "/addBirthGenderView.do?loginType=SNS");
                            if("Y".equals(NmcpServiceUtils.isMobile())){
                                model.addAttribute("returnUrl", "/m/addBirthGenderView.do?loginType=SNS");
                            }
                            return "/mobile/login/snsCalbk";
                        } else {
                            model.addAttribute("resultCd", "0000");
                            model.addAttribute("msg", "");
                            model.addAttribute("returnUrl", redirectPage);

                            return "/mobile/login/snsCalbk";
                        }
                    }
                } else {
                    HttpSession session = request.getSession();
                    // 회원정보 수정 > 소셜로그인 관리  소셜로그인시 즉시 연동처리
                    if(session.getAttribute("snsLoginPreUrl") != null && session.getAttribute("snsLoginPreUrl").toString().indexOf("userSnsList") > -1) {
                        if (SessionUtils.hasLoginUserSessionBean()) {
                            UserSessionDto userSession =  null;
                            userSession = SessionUtils.getUserCookieBean();
                            JoinDto joinDto = new JoinDto();
                            joinDto.setUserId(userSession.getUserId());
                            joinDto.setSnsCd("NAVER");
                            joinDto.setSnsId(EncryptUtil.ace256Enc(naverUniqueNo));
                            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
                            try {
                                joinSvc.insertSnsInfo(joinDto);
                                responseSuccessDto.setSuccessMsg("정상적으로 연결되었습니다.");
                                model.addAttribute("msg", "정상적으로 연결되었습니다.");
                            } catch(DataAccessException e) {
                                responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
                                model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
                            } catch(Exception e){
                                responseSuccessDto.setSuccessMsg("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
                                model.addAttribute("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
                            }
                            responseSuccessDto.setRedirectUrl("/m/mypage/userSnsList.do");
                            model.addAttribute("responseSuccessDto", responseSuccessDto);
                        }
                        model.addAttribute("resultCd", "-5");
                        model.addAttribute("returnUrl", "/mypage/userSnsList.do");
                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            model.addAttribute("returnUrl", "/m/mypage/userSnsList.do");
                        }
                        return "/mobile/login/snsCalbk";
                    } else {
                        //회원가입용
                        request.getSession().setAttribute("snsCd", "NAVER");
                        request.getSession().setAttribute("snsId", EncryptUtil.ace256Enc(naverUniqueNo));
                        request.getSession().setAttribute("snsGender", snsGender);
                        request.getSession().setAttribute("snsEmail", snsEmail);
                        model.addAttribute("resultCd", "-6");
                        model.addAttribute("msg", "가입되어 있지 않은 소셜 계정입니다. 회원가입으로 이동합니다.");
                        model.addAttribute("returnUrl", "/join/fstPage.do");
                        if("Y".equals(NmcpServiceUtils.isMobile())){
                            model.addAttribute("returnUrl", "/m/join/fstPage.do");
                        }
                        return "/mobile/login/snsCalbk";
                    }
                }
            }  else {
                model.addAttribute("resultCd", "-7");
                model.addAttribute("msg", "네이버 정보조회에 실패했습니다.");
                model.addAttribute("returnUrl", "/loginForm.do");
                if("Y".equals(NmcpServiceUtils.isMobile())){
                    model.addAttribute("returnUrl", "/m/loginForm.do");
                }
                return "/mobile/login/snsCalbk";
            }
        } catch(Exception e){
            //e.printStackTrace();
            model.addAttribute("resultCd", "-7");
            model.addAttribute("msg", "네이버 정보조회에 실패했습니다.");
            model.addAttribute("returnUrl", "/loginForm.do");
            if("Y".equals(NmcpServiceUtils.isMobile())){
                model.addAttribute("returnUrl", "/m/loginForm.do");
            }
            return "/mobile/login/snsCalbk";
        }
    }

}
