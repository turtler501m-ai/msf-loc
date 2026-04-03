package com.ktmmobile.msf.login.controller;

import com.ktmmobile.msf.login.dto.AppInfoDTO;
import com.ktmmobile.msf.login.service.AppSvc;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.LoginHistoryDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpAutoLoginTxnDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.vo.MpTelTotalUseTimeMobileDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpTelVO;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.service.LoginSvc;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringMakerUtil;
import com.ktmmobile.msf.system.common.util.StringUtil;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.service.MypageService;
import com.ktmmobile.msf.form.servicechange.service.MypageUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Controller
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    LoginSvc loginSvc;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private AppSvc appSvc;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private MypageUserService mypageUserService;

    @Value("${SERVER_NAME}")
    private String serverName;


    /**
     * 설명 : APP - VERSION 정보 조회 , 기존 : /app/getVersionJson.do
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param os
     * @param version
     * @param uuid
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/m/set/appVersionInfoAjax.do")
    @ResponseBody
    public Map<String, Object> getVersionJson(HttpServletRequest request, HttpServletResponse response
            , String os, String version, String uuid
            ) throws UnsupportedEncodingException {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        if(!"".equals(StringUtil.NVL(uuid, "")) && !"".equals(StringUtil.NVL(os, "")) && !"".equals(StringUtil.NVL(version, ""))) {

            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("os", os);
            paramMap.put("version", version);

            // 세션에 uuid 저장
            SessionUtils.saveAppUuidSession(uuid);

            //세션의 uuid 확인
            String appUuid = StringUtil.NVL(SessionUtils.getAppUuidSession(),"");

            AppInfoDTO appInfoDTO = new AppInfoDTO();
            appInfoDTO.setUuid(uuid);
            appInfoDTO.setOs(os);
            appInfoDTO.setVersion(version);
            appInfoDTO.setUpdateFlag("N");

            try {
                //기본정보 저장
                int count = appSvc.mergeUsrAppInfo(appInfoDTO);
            } catch(Exception e) {
                logger.error(e.toString());
            }


            Map<String, Object> valueMap = new HashMap<String, Object>();
            try {
                Map<String, String> appVersion = appSvc.selectAppVersion(os);

                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("VERSION", appVersion.get("VERSION"));
                resultMap.put("DN_URL", appVersion.get("DN_URL"));
                resultMap.put("MUST_UP", appVersion.get("MUST_UP"));

                //전달한 인자값 version 높은며... 인자값 전달
                if (resultMap != null) {
                    String versionDB = appVersion.get("VERSION").replace(".", "");
                    String versionPa = version.replace(".", "");
                    try {
                        if (Integer.parseInt(versionPa) > Integer.parseInt(versionDB)) {
                            resultMap.put("VERSION", version);
                        }
                    }catch(Exception e) {
                        logger.error(e.getMessage());
                    }
                }
                valueMap.put("RESULT_CD", "0000");
                valueMap.put("RESULT_DESC", "");
                valueMap.put("RESULT_DATA", resultMap);
            } catch(DataAccessException e) {
                valueMap.put("RESULT_CD", "4002");
                valueMap.put("RESULT_DESC", "VERSION 정보를 가지고 오는데 실패했습니다.");
            } catch(Exception e) {
                valueMap.put("RESULT_CD", "4002");
                valueMap.put("RESULT_DESC", "VERSION 정보를 가지고 오는데 실패했습니다.");
            }
            returnMap.put("CODE", "0000");
            returnMap.put("ERRORDESC", "");
            returnMap.put("VALUE", valueMap);
        } else {
            returnMap.put("CODE", "9001");
            returnMap.put("ERRORDESC", "파라미터를 확인해 주세요.");
        }

        return returnMap;
    }


    /**
     * 설명 : APP - PUSH 정보 수정, 기존 : /app/setPushSendJson.do
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param uuid
     * @param os
     * @param udid
     * @param sendYn
     * @param appOsVer
     * @param appNm
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/m/set/appPushInfoAjax.do")
    @ResponseBody
    public Map<String, Object> setPushSendJson(HttpServletRequest request, HttpServletResponse response
            , String uuid, String os, String udid, String sendYn, String appOsVer, String appNm
            ) throws UnsupportedEncodingException {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        //로그인한 경우 세션의 userId 추가
        //login 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        String userId = null;

        if (userSession != null) {
            userId = userSession.getUserId();
        }

        if(!"".equals(StringUtil.NVL(uuid, "")) && !"".equals(StringUtil.NVL(udid, "")) && !"".equals(StringUtil.NVL(sendYn, ""))) {

            // 세션에 uuid 저장
            SessionUtils.saveAppUuidSession(uuid);

            AppInfoDTO appInfoDTO = new AppInfoDTO();
            appInfoDTO.setUuid(uuid);
            appInfoDTO.setUdid(udid);
            appInfoDTO.setSendYn(sendYn);
            appInfoDTO.setOs(os);
            appInfoDTO.setUserId(userId);
            appInfoDTO.setAppOsVer(appOsVer); //앱OS버전
            appInfoDTO.setAppNm(appNm); //단말기명

            Map<String, Object> valueMap = new HashMap<String, Object>();
            try {
                int count = appSvc.mergeUsrAppInfo(appInfoDTO);

                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("COUNT", ""+count);

                valueMap.put("RESULT_CD", "0000");
                valueMap.put("RESULT_DESC", "");
                valueMap.put("RESULT_DATA", resultMap);
            } catch(Exception e) {
                valueMap.put("RESULT_CD", "4002");
                valueMap.put("RESULT_DESC", "PUSH 전송 구분을 저장하는데 실패했습니다.");
            }
            returnMap.put("CODE", "0000");
            returnMap.put("ERRORDESC", "");
            returnMap.put("VALUE", valueMap);
        } else {
            returnMap.put("CODE", "9001");
            returnMap.put("ERRORDESC", "파라미터를 확인해 주세요.");
        }

        return returnMap;
    }


    /**
     * 설명 : WEB - 로그인하기
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param userSessionDto
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/app/loginProcJson.do")
    @ResponseBody
    public Map<String, Object> loginProcJson(UserSessionDto userSessionDto, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        //세션의 uuid 확인
        String appUuid = StringUtil.NVL(SessionUtils.getAppUuidSession(),"");

        UserSessionDto userSession = null;
        boolean isError = false;

        //자동로그인, 지문, 간편비밀번호
        boolean appAutoLogin = false;
        if (!StringUtils.isBlank(userSessionDto.getToken()) && !StringUtils.isBlank(userSessionDto.getLoginDivCd())) {
            appAutoLogin = true;
        } else {
            returnMap.put("CODE", "1001");
            returnMap.put("ERRORDESC", "로그인 시 오류가 발생했습니다. 다시 시도해 주세요.");
            return returnMap;
        }

        try {
            if(appAutoLogin) {
                userSession = loginSvc.loginCheckAppAutoLogin(userSessionDto);
            } else {
                userSession = loginSvc.loginCheckFailCount(userSessionDto);
            }
        } catch(Exception e) {
            isError = true;
        }

        if(userSession != null && userSession.isLogin()) {
            returnMap.put("CODE", "0000");
            returnMap.put("ERRORDESC", "");

            userSession.setLoginDivCd(StringUtil.NVL(userSessionDto.getLoginDivCd(),""));

            //자동로그인 토큰 update
            if(appAutoLogin) {
                NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto = loginSvc.updateAutoLoginTxn(userSession);

                String phoneOs = NmcpServiceUtils.getPhoneOs();
                String newToken = nmcpAutoLoginTxnDto.getToken();
                returnMap.put("phoneOs", phoneOs);
                returnMap.put("newToken", newToken);
            }
        } else {

            //자동로그인 - token 이 다른 경우
            if("AUTO".equals(StringUtil.NVL(userSessionDto.getLoginDivCd(),""))) {
                returnMap.put("CODE", "1001");
                returnMap.put("ERRORDESC", "자동로그인 시 오류가 발생했습니다.");
            } else {
                if(isError) {
                    returnMap.put("CODE", "1001");
                    returnMap.put("ERRORDESC", "로그인 시 오류가 발생했습니다. 다시 시도해 주세요.");
                } else {

                    if (userSession == null || !userSession.isLoginFailCntOver()) {
                        UserSessionDto dormancyUserDto = loginSvc.loginCheckAppAutoLoginDormancy(userSessionDto);
                        if(dormancyUserDto != null) {
                            SessionUtils.saveDormancySession(dormancyUserDto);	//휴면계정 세션저장
                            returnMap.put("CODE", "0002");
                            returnMap.put("ERRORDESC", "kt M모바일을 오랫동안 이용하지 않아 아이디가\n휴면 상태로 전환되었습니다.\n\n본인인증을 통해 해제 후 서비스 이용 부탁드립니다.");
                        }else if(appAutoLogin) {
                            returnMap.put("CODE", "1001");
                            returnMap.put("ERRORDESC", "로그인 시 오류가 발생했습니다. 다시 시도해 주세요.");
                        } else {
                            returnMap.put("CODE", "0001");
                            returnMap.put("ERRORDESC", "아이디나 비밀번호가 정확하지 않습니다.\n비밀번호 5회 이상 오류 시 로그인이 제한되며, 비밀번호 찾기 후 로그인이 가능합니다");
                        }
                    } else if (userSession.isLoginFailAttack()) {
                        Map<String, Object> valueMap = new WeakHashMap<String, Object>();
                        returnMap.put("CODE", "1010");
                        returnMap.put("ERRORDESC", "비정상적인 로그인 시도가 탐지되어 로그인 제한처리 되었습니다.\nwww.ktmmobile.com에서 비밀번호 찾기를 통해 비밀번호를 변경하시면 정상적으로 서비스를 이용하실 수 있습니다.");
                        valueMap.put("RESULT_CD", "");
                        valueMap.put("RESULT_DESC", "");

                        if ("DEV".equals(serverName) || "LOCAL".equals(serverName)) {
                            valueMap.put("RESULT_DATA", "https://mcpdev.ktmmobile.com/m/findPassword.do?tabId=p");
                        } else {
                            valueMap.put("RESULT_DATA", "https://www.ktmmobile.com/m/findPassword.do?tabId=p");
                        }

                        returnMap.put("VALUE", valueMap);
                    } else {
                        if(appAutoLogin) {
                            logger.debug("appAutoLogin");
                        } else {
                            Map<String, Object> valueMap = new WeakHashMap<String, Object>();
                            returnMap.put("CODE", "1010");
                            returnMap.put("ERRORDESC", "비밀번호 입력 오류가 5회 이상 발생하여 로그인 제한처리 되었습니다.\nwww.ktmmobile.com에서 비밀번호 찾기를 통해 비밀번호를 변경하시면 정상적으로 서비스를 이용하실 수 있습니다.");
                            valueMap.put("RESULT_CD", "");
                            valueMap.put("RESULT_DESC", "");

                            if ("DEV".equals(serverName) || "LOCAL".equals(serverName)) {
                                valueMap.put("RESULT_DATA", "https://mcpdev.ktmmobile.com/m/findPassword.do?tabId=p");
                            } else {
                                valueMap.put("RESULT_DATA", "https://www.ktmmobile.com/m/findPassword.do?tabId=p");
                            }

                            returnMap.put("VALUE", valueMap);
                        }
                    }
                }
            }
        }
        //TODO  Memory Leak 의심으로 명시적으로 null 선언
        userSession = null;

        return returnMap;
    }


    /**
     * 설명 : WEB- 앱설정 - 조회, 기존 : /app/getConfigDataJson.do
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/m/set/appSettingView.do")
    public String appSettingView(HttpServletRequest request, Model model) {

        String returnUrl = "mobile/set/appSettingView";

        //login 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        String userId = "";
        String checkUserType = "N"; // 정회원 여부(Y/N)
        List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();
        MyPageSearchDto searchVO = new MyPageSearchDto();
        List<NmcpCdDtlDto> wdgtCallCyclListDefault = new ArrayList<NmcpCdDtlDto>();
        List<NmcpCdDtlDto> wdgtCallCyclList = new ArrayList<NmcpCdDtlDto>();
        AppInfoDTO appInfoDTO = null;
        String appVersion = "";
        String appDown = "";

        int count = 0;

        //세션의 uuid 확인
        String appUuid = StringUtil.NVL(SessionUtils.getAppUuidSession(),"");

        if("".equals(appUuid)) {
            // 세션에 uuid 저장
            appUuid = StringUtil.NVL(request.getParameter("appUuid"),"");

            SessionUtils.saveAppUuidSession(appUuid);
        }

        if(!"".equals(appUuid)) {
            AppInfoDTO appInfoDTOParam = new AppInfoDTO();
            appInfoDTOParam.setUuid(appUuid);

               appInfoDTO = appSvc.selectUsrAppDetail(appInfoDTOParam);

            if(appInfoDTO != null) {

                String appOs = StringUtil.NVL(appInfoDTO.getOs(), NmcpServiceUtils.getPhoneOs());

                Map<String, String> appVersionMap = null;
                appVersionMap = appSvc.selectAppVersion(appOs);

                if(appVersionMap != null) {
                    appVersion = (String)appVersionMap.get("VERSION");
                    appDown = (String)appVersionMap.get("DN_URL");
                }
            }
        }

        if (userSession != null) {

            userId = userSession.getUserId();

            if(!"".equals(appUuid)) {
                try {
                    AppInfoDTO appInfo = new AppInfoDTO();
                    appInfo.setUuid(appUuid);
                    appInfo.setUserId(userId);

                    count = appSvc.updateUsrAppInfo(appInfo);

                } catch(DataAccessException e) {
                    logger.error( e.toString());
                } catch(Exception e) {
                    logger.error(e.toString());
                }
            }

            if(count > 0) {
                //정회원 여부 확인
                cntrList = mypageService.selectCntrList(userId);
                if(this.checkUserType(searchVO, cntrList, userSession) ){
                    checkUserType = "Y";

                    //위젯호출 주기(기본값)
                    wdgtCallCyclListDefault = NmcpServiceUtils.getCodeList("SY0026");
                    String defaultUserSet = StringUtil.NVL(wdgtCallCyclListDefault.get(0).getDtlCd(),"");

                    //위젯호출 주기
                    wdgtCallCyclList = NmcpServiceUtils.getCodeList("SY0025");

                    //기본값이 변경된 경우 처리
                    boolean userSetMatch = false;
                    String userSet = "";

                    if (appInfoDTO != null ) {
                        userSet = appInfoDTO.getWdgtUseQntyCallCyclCd();
                    }

                    if (ObjectUtils.isNotEmpty(wdgtCallCyclList)) { // 취약성 233
                        for(NmcpCdDtlDto cddtl : wdgtCallCyclList) {
                            if(userSet.equals(cddtl.getDtlCd())) {
                                userSetMatch = true;
                            }
                        }
                    }
                    if(!userSetMatch) {
                        appInfoDTO.setWdgtUseQntyCallCyclCd(defaultUserSet);
                    }
                }
            }
        }
/*
        //버전값을 비교하여 업데이트 버튼 노출( 버전은 숫자로 변경했을때 숫자로 변경하여 비교하므로 자리수 확인 필요
        int versionDB = Integer.parseInt(appVersion.replace(".", ""));
        int version = Integer.parseInt(appInfoDTO.getVersion().replace(".", ""));

        if (versionDB <= version) {
            appDown = "";
        }
*/
        model.addAttribute("appUuid", appUuid);
        model.addAttribute("userId", userId);
        model.addAttribute("checkUserType", checkUserType);
        model.addAttribute("rtnCntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("wdgtCallCyclList", wdgtCallCyclList);
        model.addAttribute("appInfoDTO", appInfoDTO);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("appDown", appDown);

        return returnUrl;
    }


    /**
     * 설명 : WEB - 앱설정 - 대표번호 정보 수정, 기존 : /app/setRepJson.do
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param appInfoDTO
     * @return
     */
    @RequestMapping(value = "/m/set/updateAppRepLineAjax.do")
    @ResponseBody
    public Map<String, Object> updateAppRepLineAjax(HttpServletRequest request, AppInfoDTO appInfoDTO) {

        Map<String, Object> returnMap = new HashMap<String, Object>();

        //login 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if(userSession == null) {
            returnMap.put("CODE", "1010");
            returnMap.put("ERRORDESC", "비정상적인 접근입니다.");
        }else{
            if("".equals(StringUtil.NVL(appInfoDTO.getNcn(), ""))) {
                returnMap.put("CODE", "9001");
                returnMap.put("ERRORDESC", "파라미터를 확인해 주세요.");
            } else {
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("userId", userSession.getUserId());
                paramMap.put("ncn", appInfoDTO.getNcn());
                paramMap.put("customerId", userSession.getCustomerId());
                try {
                    int count = appSvc.userRepChange(paramMap);

                    if(count > 0) {
                        logger.debug("대표번호로 세션값 수정");
                    }
                } catch(DataAccessException e) {
                    logger.error(e.toString());
                } catch(Exception e) {
                    logger.error(e.toString());
                }

                returnMap.put("CODE", "0000");
                returnMap.put("ERRORDESC", "");
            }
        }

        return returnMap;
    }


    /**
     * 설명 : WEB - 앱설정 - 자동로그인 / 생체로그인 / 위젯호출주기 / 이벤트 정보수신 수정
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param appInfoDTO
     * @return
     */
    @RequestMapping(value = "/m/set/updateAppSetAjax.do")
    @ResponseBody
    public Map<String, Object> updateAppSetAjax(HttpServletRequest request, AppInfoDTO appInfoDTO) {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("CODE", "9001");
        returnMap.put("ERRORDESC", "파라미터를 확인해 주세요.");

        int updateChk = 0;
        String retYn = "";

        String appUuid = StringUtil.NVL(SessionUtils.getAppUuidSession(),"");

        // 최근 생성된 uuid 의 userId 조회
        AppInfoDTO updateAppInfo = new AppInfoDTO();
        updateAppInfo.setUuid(appUuid);

        if(!"".equals(StringUtil.NVL(appInfoDTO.getSimpleLoginYn(), ""))) {
            updateAppInfo.setSimpleLoginYn(appInfoDTO.getSimpleLoginYn());
            if("Y".equals(appInfoDTO.getSimpleLoginYn())) {
                updateAppInfo.setBioLoginYn("N");
            }
            retYn = appInfoDTO.getSimpleLoginYn();
            updateChk = 1;	//간편로그인
        }

        if(!"".equals(StringUtil.NVL(appInfoDTO.getBioLoginYn(), ""))) {
            updateAppInfo.setBioLoginYn(appInfoDTO.getBioLoginYn());
            if("Y".equals(appInfoDTO.getBioLoginYn())) {
                updateAppInfo.setSimpleLoginYn("N");
            }
            retYn = appInfoDTO.getBioLoginYn();
            updateChk = 2;	//생체로그인
        }

        if(!"".equals(StringUtil.NVL(appInfoDTO.getWdgtUseQntyCallCyclCd(), ""))) {
            updateAppInfo.setWdgtUseQntyCallCyclCd(appInfoDTO.getWdgtUseQntyCallCyclCd());
            retYn = appInfoDTO.getWdgtUseQntyCallCyclCd();
            updateChk = 3; 	//위젯호출주기
        }

        if(!"".equals(StringUtil.NVL(appInfoDTO.getSendYn(), ""))) {
            updateAppInfo.setSendYn(appInfoDTO.getSendYn());
            updateAppInfo.setSendYnReferer(appInfoDTO.getSendYnReferer());
            retYn = appInfoDTO.getSendYn();
            updateChk = 4; 	//이벤트 정보수신
        }

        if(updateChk > 0) {
            int count = appSvc.updateUsrAppInfo(updateAppInfo);

            if(count > 0) {
                returnMap.put("CODE", "0000");
                returnMap.put("ERRORDESC", "");
            }
        }
        returnMap.put("retYn", retYn);

        return returnMap;
    }


    /**
     * 설명 : WEB - 간편/생체인증 로그인 비밀번호 설정 화면
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param loginType
     * @return
     */
    @RequestMapping(value = "/m/set/appSimpleCheckView.do")
    public String  appSimpleCheckView(HttpServletRequest request, Model model, String  loginType ) {

        String returnUrl = "mobile/set/appSimpleCheckView";

        model.addAttribute("loginType", loginType);

        return returnUrl;
    }


    /**
     * 설명 : WEB - 간편/생체인증 로그인 비밀번호 확인
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param userSessionDto
     * @return
     */
    @RequestMapping(value = "/m/set/appSimpleCheckAjax.do")
    @ResponseBody
    public Map<String, Object> appSimpleCheckAjax(HttpServletRequest request, Model model, UserSessionDto userSessionDto ) {

        Map<String, Object> returnMap = new HashMap<String, Object>();

        //login 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        UserSessionDto chkUserSession = null;
        boolean isError = false;

        if(userSession != null && userSession.getUserId().equals(userSessionDto.getUserId())) {
            try {
                chkUserSession = loginSvc.loginProcess(userSessionDto);
            } catch(DataAccessException e) {
                isError = true;
            } catch(Exception e) {
                isError = true;
            }
        }

        if(chkUserSession == null){
            if(isError) {
                returnMap.put("CODE", "1001");
                returnMap.put("ERRORDESC", "인증 확인 중 오류가 발생했습니다. 다시 시도해 주세요.");
            } else {
                returnMap.put("CODE", "0001");
                returnMap.put("ERRORDESC", "kt M모바일 회원\n아이디 또는 비밀번호가 일치하지 않습니다.");
            }
        } else {
            returnMap.put("CODE", "0000");
            returnMap.put("ERRORDESC", "");
        }

        return returnMap;
    }


    /**
     * 설명 : 간편/바이오 로그인 성공시 새 token 생성 , 자동로그인 추가
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param userSessionDto
     * @return
     */
    @RequestMapping(value = "/m/set/appNewAjax.do")
    @ResponseBody
    public Map<String, Object> appNewAjax(HttpServletRequest request, UserSessionDto userSessionDto ){

        Map<String, Object> returnMap = new HashMap<String, Object>();

        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        if(userSessionDto != null && !"".equals(StringUtil.NVL(userSessionDto.getToken(),""))) {

            try {
                    NmcpAutoLoginTxnDto nmcpAutoLoginTxnDto = loginSvc.getLoginAutoLogin(userSessionDto);

                    if(nmcpAutoLoginTxnDto == null) {
                        loginSvc.insertAutoLoginTxn(userSessionDto);
                    }else {
                        loginSvc.updateAutoLoginTxn(userSessionDto);
                    }
                    returnCode = "0000";
                    message = "";
            } catch(DataAccessException e) {
                logger.error("appNewAjax Exception = {}", e.getMessage());
            } catch(Exception e) {
                logger.error("appNewAjax Exception = {}", e.getMessage());
            }
        }

        returnMap.put("CODE", returnCode);
        returnMap.put("ERRORDESC", message);

        return returnMap;
    }


    /**
     * 설명 : APP - 위젯 - 정보조회, 기존 : /app/getWidgetDataJson.do
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param response
     * @param userSessionDto
     * @param sessionStatus
     * @return
     */
    @RequestMapping(value = "/m/get/appWidgetDataAjax.do")
    @ResponseBody
    public Map<String, Object> getWidgetDataJson(HttpServletRequest request
            , HttpServletResponse response
            , @ModelAttribute("userSessionDto") UserSessionDto userSessionDto
            , SessionStatus sessionStatus) {
        Map<String, Object> returnMap = new WeakHashMap<String, Object>();

        UserSessionDto userSession = null;

        String paramToken = StringUtil.NVL(request.getParameter("token"),"");
        if (!"".equals(paramToken)) {
            try {
                UserSessionDto userSessionDto2 = new UserSessionDto();
                userSessionDto2.setToken(paramToken);
                userSession = loginSvc.loginCheckAppAutoLogin(userSessionDto2);
            } catch(Exception e) {
                logger.error(e.toString());
            }
        }

        if(userSession == null) {
            returnMap.put("CODE", "1001");
            returnMap.put("ERRORDESC", "로그인 시 오류가 발생했습니다. 다시 시도해 주세요.");
        } else {

            /*조회수 증가*/
            String userId = userSession.getUserId();
            loginSvc.updateHit(userId);

            MyPageSearchDto searchVO = new MyPageSearchDto();
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            if(!this.checkUserType(searchVO, cntrList, userSession) ){
                returnMap.put("CODE", "2001");
                returnMap.put("ERRORDESC", "kt M모바일 홈페이지를 통해\n정회원 인증 후 사용 가능합니다.\nkt고객님은 App store에서 ‘마이 케이티 앱‘ 을 설치 후 이용하시기 바랍니다.");
            } else {
                Map<String, Object> valueMap = new WeakHashMap<String, Object>();
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();

                //선불 요금제 여부 조회
                boolean prePaymentFlag = mypageUserService.selectPrePayment(searchVO.getNcn());

                if (prePaymentFlag) {
                    setDefaultValueMap(list);
                    valueMap.put("RESULT_CD", "0000");
                    valueMap.put("RESULT_DESC", "");
                    valueMap.put("RESULT_DATA", list);

                    returnMap.put("CODE", "0000");
                    returnMap.put("ERRORDESC", "");
                    returnMap.put("VALUE", valueMap);

                    sessionStatus.setComplete();    // 세센에 저장된 정보를 모두 제거한다.
                    SessionUtils.invalidateSession();

                    return returnMap;
                }

                try {

                    //사용량 조회 2시간동안 조회 횟수 카운트
                    HashMap<String, String> hm= new HashMap<String, String>();
                    hm.put("contractNum", searchVO.getNcn());
                    hm.put("eventCd", "X12");
                    int limitCnt = mypageService.selectCallSvcLimitCount(hm);
                    MpTelTotalUseTimeMobileDto vo = new MpTelTotalUseTimeMobileDto();

                    if (Constants.CALL_USETIME_SVC_LIMIT > limitCnt) {
                         vo = mPlatFormService.telTotalUseTimeMobileDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
                         //사용량 조회 횟수 카운트를 위한 로그 저장
                         mypageService.insertMcpSelfcareStatistic(hm);
                    } else {
                        valueMap.put("RESULT_CD", "0202");
                        valueMap.put("RESULT_DESC", "안정적인 서비스를 위해 사용량 조회 횟수를 120분 20회로 제한하고 있습니다.");
                        returnMap.put("CODE", "0000");
                        returnMap.put("ERRORDESC", "");
                        returnMap.put("VALUE", valueMap);

                        sessionStatus.setComplete();    // 세센에 저장된 정보를 모두 제거한다.
                        SessionUtils.invalidateSession();
                        return returnMap;
                    }

                    Map<String, String> rateMap = appSvc.selectUsrRateInfo(searchVO.getContractNum());

                    boolean dataOk = false;
                    boolean phoneOk = false;
                    boolean smsOk = false;
                    boolean checkData = false;
                    boolean checkPhone = false;
                    boolean checkSms = false;
                    for(MpTelVO item : vo.getItemTelList()) {
                        if("음성/영상".equals(item.getStrSvcName())) {
                            checkPhone = true;
                        } else if("SMS/MMS".equals(item.getStrSvcName())) {
                            checkSms = true;
                        } else if("데이터-합계".equals(item.getStrSvcName())) {
                            checkData = true;
                        }
                    }

                    for(MpTelVO item : vo.getItemTelList()) {
                        boolean check = false;
                        Map<String, String> map = null;

                        if(!phoneOk && checkPhone && "음성/영상".equals(item.getStrSvcName())) {
                            map = new WeakHashMap<String, String>();
                            check = true;
                            map.put("WIDGET_TITLE", "음성/영상");
                            map.put("WIDGET_SORT", "2");
                            map.put("WIDGET_LINK", "N");
                            map.put("WIDGET_KEY", "002");
                            phoneOk = true;
                        } else if(!phoneOk && !checkPhone && "음성".equals(item.getStrSvcName())){
                            map = new WeakHashMap<String, String>();
                            check = true;
                            map.put("WIDGET_TITLE", "음성");
                            map.put("WIDGET_SORT", "2");
                            map.put("WIDGET_LINK", "N");
                            map.put("WIDGET_KEY", "002");
                            phoneOk = true;
                        }

                        if(!smsOk && checkSms && "SMS/MMS".equals(item.getStrSvcName())) {
                            map = new WeakHashMap<String, String>();
                            check = true;
                            map.put("WIDGET_TITLE", "SMS/MMS");
                            map.put("WIDGET_SORT", "3");
                            map.put("WIDGET_LINK", "N");
                            map.put("WIDGET_KEY", "003");
                            smsOk = true;
                        } else if(!smsOk && !checkSms && "SMS".equals(item.getStrSvcName())){
                            map = new WeakHashMap<String, String>();
                            check = true;
                            map.put("WIDGET_TITLE", "SMS");
                            map.put("WIDGET_SORT", "3");
                            map.put("WIDGET_LINK", "N");
                            map.put("WIDGET_KEY", "003");
                            smsOk = true;
                        }

                        if(!dataOk && checkData && checkData && "데이터-합계".equals(item.getStrSvcName())) {
                            map = new WeakHashMap<String, String>();
                            check = true;
                            map.put("WIDGET_TITLE", "데이터");
                            map.put("WIDGET_SORT", "1");
                            map.put("WIDGET_KEY", "001");
                            if(rateMap != null) {
                                if(    StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM62") //M LTE 62 (안심차단)
                                    || StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM72") //M LTE 72 (안심차단)
                                    || StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM35") //M 망내 기본제공 LTE 35 (안심차단)
                                    || StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM45") //M 망내 기본제공 LTE 45 (안심차단)
                                    || StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM55") //M 망내 기본제공 LTE 55 (안심차단)
                                    || StringUtil.NVL(rateMap.get("DATA_TYPE"), "").equals("3G")) {
                                    if("0".equals(item.getStrFreeMinRemain())) {
                                        map.put("WIDGET_LINK", "Y");
                                    } else {
                                        map.put("WIDGET_LINK", "N");
                                    }
                                } else {
                                    map.put("WIDGET_LINK", "N");
                                }
                            }
                            dataOk = true;
                        } else if(!dataOk && !checkData && "데이터".equals(item.getStrSvcName())){
                            map = new WeakHashMap<String, String>();
                            check = true;
                            map.put("WIDGET_TITLE", "데이터");
                            map.put("WIDGET_SORT", "1");
                            map.put("WIDGET_KEY", "001");
                            if(rateMap != null) {
                                if(    StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM62") //M LTE 62 (안심차단)
                                    || StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM72") //M LTE 72 (안심차단)
                                    || StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM35") //M 망내 기본제공 LTE 35 (안심차단)
                                    || StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM45") //M 망내 기본제공 LTE 45 (안심차단)
                                    || StringUtil.NVL(rateMap.get("RATE_CD"), "").equals("KTILTEM55") //M 망내 기본제공 LTE 55 (안심차단)
                                    || StringUtil.NVL(rateMap.get("DATA_TYPE"), "").equals("3G")) {
                                    if("0".equals(item.getStrFreeMinRemain())) {
                                        map.put("WIDGET_LINK", "Y");
                                    } else {
                                        map.put("WIDGET_LINK", "N");
                                    }
                                } else {
                                    map.put("WIDGET_LINK", "N");
                                }
                            }
                            dataOk = true;
                        }

                        if(check) {
                            map.put("USE_PERCENT", ""+(int)((double)item.getStrFreeMinUseInt() / (double)item.getStrFreeMinTotalInt() * 100)); //퍼센트
                            String remain = "";
                            if("기본제공".equals(item.getStrFreeMinTotal())) {
                                map.put("REMAIN_TXT", "");
                                map.put("TOTAL_DATA", "기본제공");
                                remain = "기본제공";//잔여
                            } else {
                                map.put("REMAIN_TXT", "잔여");
                                String bungun = item.getBunGunNm();
                                if("MB".equals(item.getBunGunNm())) {
                                    if((Double)(item.getStrFreeMinTotalInt() * 0.5)/1024 > 1000) {
                                        bungun = "GB";
                                    } else {
                                        bungun = "MB";
                                    }
                                }
                                map.put("TOTAL_DATA", item.getStrFreeMinTotal()+bungun);
                                remain = item.getStrFreeMinRemain()+item.getBunGunNm();//잔여
                            }
                            map.put("USE_REMAIN", remain);
                            list.add(map);
                        }
                    }

                    //TODO  MpTelTotalUseTimeMobileDto 총 통화 시간 조회 모바일용  : Memory Leak 의심으로 명시적으로 null 선언
                    vo = null;
                    if(!phoneOk) {
                        Map<String, String> map = new WeakHashMap<String, String>();
                        map.put("WIDGET_TITLE", "음성/영상");
                        map.put("WIDGET_SORT", "2");
                        map.put("WIDGET_LINK", "N");
                        map.put("WIDGET_KEY", "002");
                        map.put("REMAIN_TXT", "잔여");
                        map.put("TOTAL_DATA", "0분");
                        map.put("USE_REMAIN", "0분");
                        map.put("USE_PERCENT", "100");
                        list.add(map);
                    }

                    if(!smsOk) {
                        Map<String, String> map = new WeakHashMap<String, String>();
                        map.put("WIDGET_TITLE", "SMS/MMS");
                        map.put("WIDGET_SORT", "3");
                        map.put("WIDGET_LINK", "N");
                        map.put("WIDGET_KEY", "003");
                        map.put("REMAIN_TXT", "잔여");
                        map.put("TOTAL_DATA", "0건");
                        map.put("USE_REMAIN", "0건");
                        map.put("USE_PERCENT", "100");
                        list.add(map);
                    }

                    if(!dataOk) {
                        Map<String, String> map = new WeakHashMap<String, String>();
                        map.put("WIDGET_TITLE", "데이터");
                        map.put("WIDGET_SORT", "1");
                        map.put("WIDGET_LINK", "N");
                        map.put("WIDGET_KEY", "001");
                        map.put("REMAIN_TXT", "잔여");
                        map.put("TOTAL_DATA", "0MB");
                        map.put("USE_REMAIN", "0MB");
                        map.put("USE_PERCENT", "100");
                        list.add(map);
                    }
                    valueMap.put("RESULT_CD", "0000");
                    valueMap.put("RESULT_DESC", "");
                    valueMap.put("RESULT_DATA", list);
                } catch (SelfServiceException e) {
                    cntrList = null;
                    searchVO = null;
                    userSession = null;
                    valueMap.put("RESULT_CD", "3001");
                    valueMap.put("RESULT_DESC", "위젯 데이터 불러오기를 실패했습니다. 다시 시도해 주세요.");
                } catch (SocketTimeoutException e){
                    cntrList = null;
                    searchVO = null;
                    userSession = null;
                    valueMap.put("RESULT_CD", "3002");
                    valueMap.put("RESULT_DESC", "위젯 데이터 불러오기를 실패했습니다(SocketTimeoutException). 다시 시도해 주세요.");
                } catch (Exception e) {
                    cntrList = null;
                    searchVO = null;
                    userSession = null;
                    valueMap.put("RESULT_CD", "3002");
                    valueMap.put("RESULT_DESC", "위젯 데이터 불러오기를 실패했습니다. 다시 시도해 주세요.");
                }

                //위젯호출 주기(기본값)
                List<NmcpCdDtlDto> wdgtCallCyclListDefault = new ArrayList<NmcpCdDtlDto>();
                wdgtCallCyclListDefault = NmcpServiceUtils.getCodeList("SY0026");
//                String defaultUserSet = StringUtil.NVL(wdgtCallCyclListDefault.get(0).getDtlCd(),""); // 기존로직
                String defaultUserSet = ObjectUtils.isNotEmpty(wdgtCallCyclListDefault) ?
                        StringUtil.NVL(wdgtCallCyclListDefault.get(0).getDtlCd(),"") : "";  //취약성 229

                returnMap.put("CODE", "0000");
                returnMap.put("ERRORDESC", "");
                returnMap.put("MINWDGTCALL", defaultUserSet);
                returnMap.put("VALUE", valueMap);
            }
            //TODO  Memory Leak 의심으로 명시적으로 null 선언
            cntrList = null;
            searchVO = null;
        }
        sessionStatus.setComplete();    // 세센에 저장된 정보를 모두 제거한다.
        SessionUtils.invalidateSession();

        //TODO  Memory Leak 의심으로 명시적으로 null 선언
        userSession = null;
        return returnMap;
    }


    /**
     * 설명 : 사용자 타입 확인
     */
    private boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession){
        if( ! StringUtil.equals(userSession.getUserDivision(), "01") ){
            return false;
        }

        if(cntrList == null) {
            return false;
        }

        if(cntrList.size() <= 0){
            return false;
        }

        if( StringUtil.isEmpty(searchVO.getNcn()) ){
            searchVO.setNcn(cntrList.get(0).getSvcCntrNo() );
            searchVO.setCtn(cntrList.get(0).getCntrMobileNo() );
            searchVO.setCustId(cntrList.get(0).getCustId());
            searchVO.setModelName(cntrList.get(0).getModelName());
            searchVO.setContractNum(cntrList.get(0).getContractNum());
        }

        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            String ctn = mcpUserCntrMngDto.getCntrMobileNo();
            String ncn = mcpUserCntrMngDto.getSvcCntrNo();
            String custId = mcpUserCntrMngDto.getCustId();
            String modelName = mcpUserCntrMngDto.getModelName();
            String contractNum = mcpUserCntrMngDto.getContractNum();

            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            mcpUserCntrMngDto.setSvcCntrNo(ncn);
            mcpUserCntrMngDto.setCustId(custId);
            mcpUserCntrMngDto.setModelName(modelName);
            mcpUserCntrMngDto.setContractNum(contractNum);
            if(StringUtil.equals(searchVO.getNcn(), String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))){
                searchVO.setNcn( ncn );
                searchVO.setCtn( ctn );
                searchVO.setCustId(custId);
                searchVO.setModelName(modelName);
                searchVO.setContractNum(contractNum);
            }
        }

        return true;
    }


    /**
     * 설명 : 데이터 셋팅
     */
    public void setDefaultValueMap(List<Map<String, String>> list){
        Map<String, String> map = new WeakHashMap<String, String>();
        map.put("WIDGET_TITLE", "음성/영상");
        map.put("WIDGET_SORT", "2");
        map.put("WIDGET_LINK", "N");
        map.put("WIDGET_KEY", "002");
        map.put("REMAIN_TXT", "잔여");
        map.put("TOTAL_DATA", "0분");
        map.put("USE_REMAIN", "0분");
        map.put("USE_PERCENT", "100");
        list.add(map);

        Map<String, String> map2 = new WeakHashMap<String, String>();
        map2.put("WIDGET_TITLE", "SMS/MMS");
        map2.put("WIDGET_SORT", "3");
        map2.put("WIDGET_LINK", "N");
        map2.put("WIDGET_KEY", "003");
        map2.put("REMAIN_TXT", "잔여");
        map2.put("TOTAL_DATA", "0건");
        map2.put("USE_REMAIN", "0건");
        map2.put("USE_PERCENT", "100");
        list.add(map2);

        Map<String, String> map3 = new WeakHashMap<String, String>();
        map3.put("WIDGET_TITLE", "데이터");
        map3.put("WIDGET_SORT", "1");
        map3.put("WIDGET_LINK", "N");
        map3.put("WIDGET_KEY", "001");
        map3.put("REMAIN_TXT", "잔여");
        map3.put("TOTAL_DATA", "0MB");
        map3.put("USE_REMAIN", "0MB");
        map3.put("USE_PERCENT", "100");
        list.add(map3);
    }


    /**
     * 설명 : WEB - app 설정 정보 조회
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param uuid
     * @param token
     * @param loginDivCd
     * @param loginAuto
     * @return
     */
    @RequestMapping(value = "/m/app/getUuidAppInfo.do")
    @ResponseBody
    public Map<String, Object> getUuidAppInfo(@RequestParam String uuid, String token, String loginDivCd, String loginAuto) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String retCode = "";

        String uuidParam = StringUtil.NVL(uuid, "");
        String tokenParam = StringUtil.NVL(token, "");
        String loginDivCdParam = StringUtil.NVL(loginDivCd, "");
        String loginAutoParam = StringUtil.NVL(loginAuto, "");

        //세션의 uuid 확인
        String appUuid = StringUtil.NVL(SessionUtils.getAppUuidSession(),"");

        if("".equals(appUuid)) {
            // 세션에 uuid 저장
            SessionUtils.saveAppUuidSession(uuidParam);
        }

        try {
            //필수 인자값 체크
            if(uuidParam.equals("") && tokenParam.equals("")) {
                returnMap.put("resltCd", "-2");
                return returnMap;
            }

            AppInfoDTO appInfoDTOParam = new AppInfoDTO();
            appInfoDTOParam.setUuid(uuidParam);
            appInfoDTOParam.setToken(tokenParam);

            AppInfoDTO uuidAppInfo = appSvc.selectUsrAppDetail(appInfoDTOParam);
            if(uuidAppInfo == null) {
                retCode = "-1";
            } else {
                retCode = "00000";
                returnMap.put("token", uuidAppInfo.getToken());
                returnMap.put("wdgtUseQntyCallCyclCd", uuidAppInfo.getWdgtUseQntyCallCyclCd());
                returnMap.put("simpleLoginYn", uuidAppInfo.getSimpleLoginYn());
                returnMap.put("bioLoginYn", uuidAppInfo.getBioLoginYn());

                //자동로그인 주기(기본값)
                List<NmcpCdDtlDto> autoLimitDefault = new ArrayList<NmcpCdDtlDto>();
                autoLimitDefault = NmcpServiceUtils.getCodeList("SY0027");
                String defaultAutoLimit = "14";
                if(autoLimitDefault != null) {
                    defaultAutoLimit = autoLimitDefault.get(0).getDtlCd();
                }

                if("Y".equals(loginAutoParam)) {
                    returnMap.put("isAutoLogin", loginAutoParam);
                }

                if("AUTO".equals(loginDivCdParam)) {

                    //Token값 확인
                    if(uuidAppInfo.getToken() == null || uuidAppInfo.getToken().equals("")) {
                        returnMap.put("resltCd", "-3");
                        return returnMap;
                    }

                    // 자동로그인 세션 생성
                    UserSessionDto userSessionDto1 = new UserSessionDto();
                    userSessionDto1.setToken(uuidAppInfo.getToken());
                    UserSessionDto userDto = loginSvc.loginCheckAppAutoLogin(userSessionDto1);


                    //휴면계정 조회
                    UserSessionDto dormancyUserDto = loginSvc.loginCheckAppAutoLoginDormancy(userSessionDto1);

                    if (userDto == null && dormancyUserDto == null) {
                        retCode = "01001";
                    }

                    if(userDto.getMonCnt() >= 3) {	//비밀번호 변경후 3개월 경과하면
                        if(userDto.getDayCnt() != -1 && userDto.getDayCnt() <= 14) {
                            logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 비밀번호 변경후 3개월 경과 and 2주 안지남");
                        }else {
                            retCode = "01002";
                        }
                    }

                    if("00000".equals(retCode)) {

                        returnMap.put("autoLimit", defaultAutoLimit);
                        returnMap.put("isAutoLogin", "Y");

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

                        SessionUtils.saveUserSession(userDto);	//사용자 세션저장
                    }
                }
            }
        } catch(DataAccessException e) {
            logger.error("getUuidAppInfo Exception = {}", e.getMessage());
        } catch(Exception e) {
            logger.error("getUuidAppInfo Exception = {}", e.getMessage());
        }

        returnMap.put("resltCd", retCode);
        return returnMap;
    }


    /**
     * 설명 : WEB - 자동로그인 메인
     * @Author : 박정웅
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/m/app/loginAutoMain.do")
    public String loginAutoMain(HttpServletRequest request, Model model){

        String returnUrl = "/mobile/main/loginAutoMain";

        return returnUrl;
    }


    /*public static void main(String[] args) {
        List<NmcpCdDtlDto> wdgtCallCyclList = new ArrayList<NmcpCdDtlDto>();

        for(NmcpCdDtlDto cddtl : wdgtCallCyclList) {
            System.out.println("====>11111");
        }
    }*/

    @RequestMapping(value = "/m/app/getSendYnInfoAjax.do")
    @ResponseBody
    public Map<String, Object> getSendYnInfoAjax(HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("returnCode", "01");

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        String uuid = StringUtil.NVL(SessionUtils.getAppUuidSession(),"");

        if(userSession == null || "".equals(uuid)) {
            return returnMap;
        }

        boolean isMktAgreePop = mypageUserService.isOpenMktAgreePop(userSession.getUserId());
        if (!isMktAgreePop) {
            return returnMap;
        }

        //세션의 uuid 확인
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        appInfoDTO.setUuid(uuid);
        appInfoDTO = appSvc.selectUsrAppDetail(appInfoDTO);

        if(appInfoDTO == null || appInfoDTO.getSendYn() == null){
            return returnMap;
        }
        returnMap.put("returnCode", "00");
        returnMap.put("sendYn", appInfoDTO.getSendYn());

        return returnMap;
    }
}
