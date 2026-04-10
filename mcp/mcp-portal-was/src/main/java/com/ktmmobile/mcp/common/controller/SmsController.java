package com.ktmmobile.mcp.common.controller;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.cert.dto.CertDto;
import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;


@Controller
public class SmsController {

    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private CertService certService;

    /**
     * sms인증팝업 - 로그인상태
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param menuType
     * @param phoneNum
     * @param contractNum
     * @return
     */

    @RequestMapping(value = {"/sms/smsAuthInfoPop.do", "/m/sms/smsAuthInfoPop.do"})
    public String doMyCombinationSms(HttpServletRequest request, Model model,
                                     @RequestParam(value = "menuType", required = false) String menuType
                                    ,@RequestParam(value = "phoneNum", required = false) String phoneNum
                                    ,@RequestParam(value = "contractNum", required = false, defaultValue = "") String contractNum) {


        // ============ STEP START ============
        Map<String, String> smsRsltMap = certService.smsAuthCertMenuType(menuType, "smsPop");
        if("Y".equals(smsRsltMap.get("menuTypeYn"))) {

            String errorUrl = "/portal/errmsg/errorPop";
            if (!"P".equals(NmcpServiceUtils.getPlatFormCd())) {
                errorUrl = "/mobile/errmsg/errorPop";
            }

            // 1. 로그인 세션 확인
            UserSessionDto userSession = SessionUtils.getUserCookieBean();
            if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
                model.addAttribute("ErrorTitle", "SMS 인증");
                model.addAttribute("ErrorMsg", NO_FRONT_SESSION_EXCEPTION);
                return errorUrl;
            }

            // 2. 개통정보 확인
            String certContractNum = null;
            String certPhoneNum = null;
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            boolean certCheck = false;

            for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {

                // 당겨쓰기, 가입증명원인쇄, 번호변경, 납부방법변경 인 경우
                if ("pull".equals(menuType) || "joinCert".equals(menuType) || "phoneNumChange".equals(menuType) || "charge".equals(menuType)) {
                    if (contractNum.equals(mcpUserCntrMngDto.getSvcCntrNo())) { // 계약번호 비교
                        certContractNum = mcpUserCntrMngDto.getContractNum();
                        certPhoneNum = mcpUserCntrMngDto.getCntrMobileNo();
                        certCheck = true;
                        break;
                    }
                } else { //그 외의 경우
                    if (phoneNum.equals(mcpUserCntrMngDto.getCntrMobileNo())) { //휴대폰 번호 비교
                        certContractNum = mcpUserCntrMngDto.getContractNum();
                        certPhoneNum = mcpUserCntrMngDto.getCntrMobileNo();
                        certCheck = true;
                        break;
                    }
                }
            }

            //에러 발생
            if (!certCheck) {
                model.addAttribute("ErrorTitle", "SMS 인증");
                model.addAttribute("ErrorMsg", F_BIND_EXCEPTION);
                return errorUrl;
            }

            //성공인 경우
            // 1. sms팝업 호출이력 확인
            if (0 < certService.getModuTypeStepCnt("smsPop", "")) {
                // sms팝업 호출 관련 스텝 초기화
                CertDto certDto = new CertDto();
                certDto.setModuType("smsPop");
                certDto.setCompType("G");
                certService.getCertInfo(certDto);
            }

            // 2. 인증종류, 계약번호, 핸드폰번호
            String[] certKey = {"urlType", "moduType", "contractNum", "mobileNo"};
            String[] certValue = {"openSmsAuthPop", "smsPop", certContractNum, certPhoneNum};
            certService.vdlCertInfo("C", certKey, certValue);

        }
        // ============ STEP END ============

        String returnUrl = "/portal/popup/smsAuthInfoPop";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/popup/smsAuthInfoPop";
        }

        model.addAttribute("phoneNum", phoneNum);

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            model.addAttribute("maskedPhoneNum", StringUtil.getMobileFullNum(StringUtil.NVL(phoneNum,"")));
        }else {
             // 연락처 마스킹처리 추가 2022.10.05
            model.addAttribute("maskedPhoneNum", MaskingUtil.getMaskedTelNo(phoneNum));
        }

        model.addAttribute("menuType", menuType);
        model.addAttribute("contractNum", contractNum);

        return returnUrl;
    }

    @RequestMapping(value = "/sms/smsAuthToNcnInfoPop.do")
    public String doMyCombinationSms(HttpServletRequest request, Model model
            , @RequestParam(value = "menuType", required = false) String menuType
            , MyPageSearchDto searchVO) {
        String returnUrl = "/portal/popup/smsAuthInfoPop";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/popup/smsAuthInfoPop";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION);
        }
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.OWN_EXCEPTION);
        }

        model.addAttribute("phoneNum", searchVO.getCtn());
        model.addAttribute("contractNum",searchVO.getNcn() );
        model.addAttribute("maskedPhoneNum", MaskingUtil.getMaskedTelNo(searchVO.getCtn()));
        model.addAttribute("menuType", menuType);

        return returnUrl;
    }

    /**
     * otp sms 인증 팝업
     * 함께쓰기에서 사용함
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param menuType
     * @param contractNum
     * @param retvGubunCd
     * @param searchCtn
     * @param reqSvcNo
     * @return
     */

    @RequestMapping(value = {"/sms/smsOtpAuthInfoPop.do", "/m/sms/smsOtpAuthInfoPop.do"})
    public String smsOtpAuthInfoPop(HttpServletRequest request, Model model,
                                    @RequestParam(value = "menuType", required = true) String menuType,
                                    @RequestParam(value = "contractNum", required = true) String contractNum,
                                    @RequestParam(value = "retvGubunCd", required = true) String retvGubunCd,
                                    @RequestParam(value = "searchCtn", required = true) String searchCtn,
                                    @RequestParam(value = "reqSvcNo", required = true) String reqSvcNo,
                                    @RequestParam(value = "ncn", required = true) String ncn){

        String returnUrl = "/portal/popup/smsOtpAuthInfoPop";
        String errorUrl = "/portal/errmsg/errorPop";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/popup/smsOtpAuthInfoPop";
            errorUrl = "/mobile/errmsg/errorPop";
        }

        // ============ STEP START ============

        // 결합대상 구분값, 핸드폰번호
        String[] certKey = {"urlType", "ncType", "mobileNo"};
        String[] certValue = {"openSubSmsOtpPop", "1", reqSvcNo};
        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            model.addAttribute("ErrorTitle", "SMS 인증");
            model.addAttribute("ErrorMsg", vldReslt.get("RESULT_DESC"));
            return errorUrl;
        }

        // 결합대상 구분값, 계약번호
        certKey = new String[]{"urlType", "ncType", "contractNum"};
        certValue = new String[]{"openMainSmsOtpPop", "0", contractNum};
        vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            model.addAttribute("ErrorTitle", "SMS 인증");
            model.addAttribute("ErrorMsg", vldReslt.get("RESULT_DESC"));
            return errorUrl;
        }
        // ============ STEP END ============

        model.addAttribute("retvGubunCd", retvGubunCd);
        model.addAttribute("menuType", menuType);
        model.addAttribute("contractNum", contractNum);
        model.addAttribute("reqSvcNo", reqSvcNo);
        model.addAttribute("searchCtn", searchCtn);
        model.addAttribute("ncn", ncn);
        return returnUrl;
    }

    /**
     * 로그인 상태 sms인증번호 발송 ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param reqPhone
     * @param reqContractNum
     * @param reqMenuType
     * @param reqName
     * @param searchVO
     * @param menuLoc
     * @param timeYn
     * @return
     */

    @RequestMapping(value = {"/sms/smsAuthAjax.do", "/m/sms/smsAuthAjax.do"})
    @ResponseBody
    public Map<String, Object> authSmsAjax(HttpServletRequest request
            , @RequestParam(value = "phone", required = true) String reqPhone
            , @RequestParam(value = "contractNum", required = false) String reqContractNum
            , @RequestParam(value = "menuType", required = true) String reqMenuType
            , @RequestParam(value = "name", required = false) String reqName
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO
            , @RequestParam(value = "menuLoc", required = false) String menuLoc
            , @RequestParam(value = "timeYn", required = false) String timeYn) throws ParseException {


        String phone = StringUtil.NVL(reqPhone, "");
        String contractNum = StringUtil.NVL(reqContractNum, "");
        String menuType = StringUtil.NVL(reqMenuType, "");
        String name = StringUtil.NVL(reqName, "");
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if ( StringUtils.isBlank(phone) && StringUtils.isBlank(menuType)){
            rtnMap.put("RESULT_CODE", "00001");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        String[] nologinMenu = {"login"};  // 메뉴타입 추가
        List<String> nologinMenuList = Arrays.asList(nologinMenu);

        if (!nologinMenuList.contains(reqMenuType)) {
            UserSessionDto userSession = SessionUtils.getUserCookieBean();

            if (userSession == null) {
                rtnMap.put("RESULT_CODE", "00002");
                rtnMap.put("message", "로그인 정보가 없습니다. ");
                return rtnMap;
            }

            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();

            String userId = StringUtil.NVL(userSession.getUserId(), "");
            String userName = StringUtil.NVL(name, "");

            cntrList = mypageService.selectCntrList(userId);

            // 계약번호가 넘어오면 계약번호로 전화번호 찾아온다.
            if (!"".equals(contractNum)) {
                McpUserCntrMngDto cntrMngDto = mypageService.getCntrInfoByContNum(contractNum);
                if (cntrMngDto != null) {
                    phone = cntrMngDto.getCntrMobileNo();
                } else {
                    rtnMap.put("RESULT_CODE", "00003");
                    rtnMap.put("message", "가입정보를 다시 확인 해 주세요.");
                    return rtnMap;
                }
            }

            if (!this.checkUserType(searchVO, cntrList, userSession)) {
                rtnMap.put("RESULT_CODE", "00001");
                rtnMap.put("message", "정회원 인증 후 이용하실 수 있습니다.");
                return rtnMap;
            } else {

                boolean isCustYn = false;

                if ("multiPhone".equals(menuLoc)) { //다회선 추가일때만
                    McpUserCntrMngDto mcpUserCntrMngDto = new McpUserCntrMngDto();
                    mcpUserCntrMngDto.setSubLinkName(userSession.getName());
                    mcpUserCntrMngDto.setCntrMobileNo(phone);

                    McpUserCntrMngDto noCntrList = mypageService.selectCntrListNoLogin(mcpUserCntrMngDto);
                    if (cntrList != null) {
                        if ("".equals(userName)) {
                            userName = userSession.getName();
                        }

                        if (userName.equals(userSession.getName()) && phone.equals(noCntrList.getCntrMobileNo())) {
                            contractNum = noCntrList.getContractNum();
                            isCustYn = true;
                        }
                    }
                } else {
                    List<McpUserCntrMngDto> cntrOutList = mypageService.selectCntrList(userId);

                    if (cntrOutList != null && cntrOutList.size() > 0) {
                        for (McpUserCntrMngDto dto : cntrOutList) {
                            if ("".equals(userName)) {
                                userName = userSession.getName();
                            }

                            if (contractNum != null && contractNum.equals(dto.getContractNum())) {
                                phone = dto.getUnSvcNo();
                            }
                            if (userName.equals(userSession.getName()) && phone.equals(dto.getCntrMobileNo())) {
                                contractNum = dto.getContractNum();
                                isCustYn = true;
                                break;
                            }
                        }
                    }
                }

                if (!isCustYn) {
                    rtnMap.put("RESULT_CODE", "00001");
                    rtnMap.put("message", "사용자 정보가 일치하지 않습니다.");
                    return rtnMap;
                }
            }

            if (contractNum == null) {
                rtnMap.put("RESULT_CODE", "00001");
                rtnMap.put("message", "회원 정보가 없습니다.");
                return rtnMap;
            }

            // ============ STEP START ============
            Map<String, String> smsRsltMap = certService.smsAuthCertMenuType(reqMenuType, "smsPop");
            if("Y".equals(smsRsltMap.get("menuTypeYn"))) {

                // 인증종류, 계약번호, 핸드폰번호, 이름
                String[] certKey = {"urlType", "moduType", "contractNum", "mobileNo", "name"};
                String[] certValue = {"reqSmsAuthNum", "smsPop", contractNum, phone, name};

                // 인증번호 받기가 시작점인 메뉴타입 (이름 필수)
                String[] startMenuArr= {"reviewReg", "frndReg", "frndRegNe", "mcashReviewReg"};
                List<String> startMenuList = Arrays.asList(startMenuArr);

                if(startMenuList.contains(reqMenuType)){
                    if(StringUtil.isEmpty(name)){
//                        rtnMap.put("RESULT_CODE", "AUTH01");
//                        rtnMap.put("message", F_BIND_EXCEPTION);
//                        return rtnMap;
                        certValue[4] = userSession.getName();
                    }

                    // sms팝업 호출이력 확인
                    if (0 < certService.getModuTypeStepCnt("smsPop", "")) {
                        // sms팝업 호출 관련 스텝 초기화
                        CertDto certDto = new CertDto();
                        certDto.setModuType("smsPop");
                        certDto.setCompType("G");
                        certService.getCertInfo(certDto);
                    }

                    certService.vdlCertInfo("C", certKey, certValue);
                }else{
                    // 인증종류, 계약번호, 핸드폰번호
                    certKey= Arrays.copyOfRange(certKey, 0, 4);

                    Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
                    if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                        rtnMap.put("RESULT_CODE", "STEP01");
                        rtnMap.put("message", vldReslt.get("RESULT_DESC"));
                        return rtnMap;
                    }
                }
            }
            // ============ STEP END ============
        }

        if ("Y".equals(timeYn)) {
            AuthSmsDto authSmsDto = new AuthSmsDto();
            authSmsDto.setMenu(menuType);
            AuthSmsDto resultAuthSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);
            String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
            authSmsDto.setStartDate(today);
            authSmsDto.setPhoneNum(phone);
            authSmsDto.setMenu(menuType);
            if (resultAuthSmsDto != null) {
                authSmsDto.setAuthNum(resultAuthSmsDto.getAuthNum());
            } else {
                authSmsDto.setAuthNum("");
            }


            //관리자 정보 session 저장
            SessionUtils.setAuthSmsSession(authSmsDto);
            rtnMap.put("RESULT_CODE", "00010");
        } else {
            AuthSmsDto authSmsDto = new AuthSmsDto();
            authSmsDto.setPhoneNum(phone);
            authSmsDto.setMenu(menuType);
            authSmsDto.setReserved02(menuType);

            String reserved03 = "nonMember";

            if (!nologinMenuList.contains(reqMenuType)) {
                UserSessionDto userSession = SessionUtils.getUserCookieBean();
                reserved03 = (userSession != null && StringUtil.isNotEmpty(userSession.getUserId()))? userSession.getUserId(): "nonMember";
            }

            authSmsDto.setReserved03(reserved03);

//            boolean check = fCommonSvc.sendAuthSms(authSmsDto);
            Map<String, Object> reMap = this.fCommonSvc.sendAuthSms2(authSmsDto);
            boolean check = (boolean) reMap.get("result");

            if (check) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

            } else if (StringUtils.isNotEmpty(reMap.get("message").toString())) {
                rtnMap.put("RESULT_CODE", "00004");
                rtnMap.put("message", reMap.get("message"));

            } else {
                rtnMap.put("RESULT_CODE", "0003");
            }

            //임시주석처리
            if ("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName)) {
                rtnMap.put("AUTH_NUM", authSmsDto.getAuthNum());
            }
        }

        rtnMap.put("svcCntrNo", EncryptUtil.ace256Enc(phone));
        return rtnMap;
    }

    /**
     * 로그인 상태 SMS 인증번호 확인 ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param certifySms
     * @param phone
     * @param contractNum
     * @param menuType
     * @return
     */

    @RequestMapping(value = {"/sms/smsAuthCheckAjax.do", "/m/sms/smsAuthCheckAjax.do"})
    @ResponseBody
    public Map<String, Object> userSmsCheck(HttpServletRequest request
            , @RequestParam(value = "certifySms", required = false) String certifySms
            , @RequestParam(value = "phone", required = false) String phone
            , @RequestParam(value = "contractNum", required = false) String contractNum
            , @RequestParam(value = "menuType", required = false) String menuType
    ) {

        String paramPhone = phone;
        if (StringUtils.isBlank(certifySms) || StringUtils.isBlank(phone)) {
            throw new McpCommonJsonException("0001", INVALID_PARAMATER_EXCEPTION);
        }

        String[] nologinMenu = {"login"};  // 비로그인 메뉴타입 추가
        List<String> nologinMenuList = Arrays.asList(nologinMenu);

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 정회원 필수인 화면인 경우만 실행
        if (!nologinMenuList.contains(menuType)) {
            UserSessionDto userSession = (UserSessionDto) request.getSession().getAttribute(SessionUtils.USER_SESSION);
            if (userSession.getStatus().equals("F")) {
                rtnMap.put("RESULT_CODE", "xxxx");
                rtnMap.put("message", "비정상적인 접근입니다.");
                return rtnMap;
            }


            String certPhoneNum = null;
            String certContractNum = null;
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            if (contractNum != null && cntrList != null && cntrList.size() > 0) {
                for (McpUserCntrMngDto dto : cntrList) {
                    if (contractNum.equals(dto.getSvcCntrNo())) {
                        paramPhone = dto.getUnSvcNo().trim();
                        certContractNum = dto.getContractNum();
                        certPhoneNum = dto.getCntrMobileNo();
                        break;
                    }
                }
            }

            // ============ STEP START ============
            Map<String, String> smsRsltMap = certService.smsAuthCertMenuType(menuType, "smsPop");
            if("Y".equals(smsRsltMap.get("menuTypeYn"))) {

                if(!"pull".equals(menuType) && cntrList != null && cntrList.size() > 0){
                    for(McpUserCntrMngDto dto : cntrList) {
                        if(paramPhone.equals(dto.getCntrMobileNo())) {
                            certContractNum = dto.getContractNum();
                            certPhoneNum = dto.getCntrMobileNo();
                            break;
                        }
                    }
                }

                String[] certKey = null;
                String[] certValue = null;

                if("multiPhone".equals(menuType) && StringUtils.isBlank(certContractNum)){
                    // 정회원 인증
                    // 인증종류, 핸드폰번호
                    certKey = new String[]{"urlType", "moduType", "mobileNo"};
                    certValue = new String[]{"chkSmsAuthNum", "smsPop", paramPhone};
                }else{
                    // 인증종류, 핸드폰번호, 계약번호
                    certKey = new String[]{"urlType", "moduType", "mobileNo", "contractNum"};
                    certValue = new String[]{"chkSmsAuthNum", "smsPop", certPhoneNum, certContractNum};
                }

                Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
                if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    rtnMap.put("RESULT_CODE", "STEP01");
                    rtnMap.put("MSG", vldReslt.get("RESULT_DESC"));
                    return rtnMap;
                }

            }
            // ============ STEP END ============
        }

        AuthSmsDto tmp = new AuthSmsDto();
        tmp.setPhoneNum(paramPhone);
        tmp.setAuthNum(certifySms);
        tmp.setMenu(menuType);

        SessionUtils.checkAuthSmsSession(tmp);

        if (tmp.isResult()) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        } else {
            rtnMap.put("RESULT_CODE", "00001");
            rtnMap.put("MSG", tmp.getMessage());
        }

        return rtnMap;
    }

    /**
     * 비로그인 상태 sms인증
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param name
     * @param phone
     * @param menuType
     * @param timeYn
     * @return
     */

    @RequestMapping(value = {"/sms/sendCertSmsAjax.do", "/m/sms/sendCertSmsAjax.do"})
    @ResponseBody
    public Map<String, Object> sendCertSmsAjax(HttpServletRequest request
            , @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "phone", required = false) String phone
            , @RequestParam(value = "menuType", required = false) String menuType
            , @RequestParam(value = "timeYn", required = false) String timeYn
            , @RequestParam(value = "userSsn", required = false) String userSsn
            , @RequestParam(value = "ncType", required = false, defaultValue = "") String ncType) throws ParseException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (StringUtils.isBlank(name) || StringUtils.isBlank(phone)) {
            rtnMap.put("RESULT_CODE", "00001");
            rtnMap.put("message", "이름과 핸드폰 번호를 입력해 주세요.");
            return rtnMap;
        }

        //비회원 배송은 체크안하도록 수정
        String certContractNum= null;
        if (!"order".equals(menuType) && !"rate".equals(menuType) && !"QNA".equals(menuType) && !"gift".equals(menuType) && !"fath".equals(menuType)) {

            //String contractNum = mypageUserService.selectContractNum(name,phone);

            Map<String, String> resOjb = null;

            if("consentGift".equals(menuType)) {
                resOjb = mypageUserService.selectConSsnObj(name, phone, userSsn);
            }else {
                resOjb = mypageUserService.selectContractObj(name, phone, "");
            }
            String contractNum = null;
            if (resOjb != null) {
                contractNum = resOjb.get("CONTRACT_NUM");
                certContractNum= resOjb.get("CONTRACT_NUM");
            }
            if (contractNum == null) {
                if("consentGift".equals(menuType)) { //제세공과금개인정보
                    rtnMap.put("RESULT_CODE", "00003");
                    rtnMap.put("message", "가입정보를 다시 확인 해 주세요.");
                }else {
                    rtnMap.put("RESULT_CODE", "00003");
                    rtnMap.put("message", "사용자 정보가 일치하지 않습니다.");

                    if (menuType.equals("frndReg") || menuType.equals("frndRegNe")) {
                        rtnMap.put("message", "친구초대 이벤트는 kt M모바일 고객인 경우에만 이용가능합니다.");
                    }
                }
                return rtnMap;
            } else {

                //1. 선불 여부
                boolean prePaymentFlag = mypageUserService.selectPrePayment(contractNum);

                if (prePaymentFlag) {
                    rtnMap.put("RESULT_CODE", "00004");
                    return rtnMap;
                }

                //2. 현재상태 A 사용중 , S 정지 , C 해지
                String subStatus = resOjb.get("SUB_STATUS");
                if (!"A".equals(subStatus)) {
                    if("consentGift".equals(menuType)) { //제세공과금개인정보
                        rtnMap.put("RESULT_CODE", "00005");
                        rtnMap.put("message", "현재 사용중인 고객만 신청이 가능합니다.");
                        return rtnMap;
                    }else {
                        rtnMap.put("RESULT_CODE", "00005");
                        rtnMap.put("message", "현재 회선을 사용 중인 고객만 가능합니다.");
                        return rtnMap;
                    }
                }

                //결합신청 에서 만.. 체크
                if ("combine".equals(menuType)) {

                    //3. 청소년 외국인 확인
                    String customerSsn = resOjb.get("USER_SSN");
                    try {
                        customerSsn = EncryptUtil.ace256Dec(customerSsn);
                    } catch (CryptoException e) {
                        throw new McpCommonJsonException("9998", ACE_256_DECRYPT_EXCEPTION);
                    }


                    //외국인 청소년 구분
                    if (customerSsn.length() > 6) {
                        //나이 확인
                        int age = NmcpServiceUtils.getAge(customerSsn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
                        String diviVal = customerSsn.substring(6, 7);

                        if ("|5|6|7|8".indexOf(diviVal) > -1) {
                            rtnMap.put("RESULT_CODE", "00006");
                            rtnMap.put("message", "개인고객만 가입이 가능합니다. ");
                            return rtnMap;
                        }
                          /*else {
                              if (19 > age ) {
                                  rtnMap.put("RESULT_CODE", "00007");
                                  rtnMap.put("message", "미성년자는 결합이 불가하오니 고객센터로 문의바랍니다. ");
                                  return rtnMap;
                              }
                          }*/
                    } else {
                        rtnMap.put("RESULT_CODE", "00008");
                        rtnMap.put("message", "주민번호 형식이 일치하지 않습니다. 합니다. ");
                        return rtnMap;
                    }


                    //4. 개인 고객만...
                    //CUSTOMER_TYPE  I B , G
                      /* CUSTOMER_TYPE
                        B	법인고객
                        G	공공기관
                        I	개인고객
                       */
                    String customerType = resOjb.get("CUSTOMER_TYPE");
                    if (!"I".equals(customerType)) {
                        rtnMap.put("RESULT_CODE", "00009");
                        rtnMap.put("message", "개인고객만 가입이 가능합니다. ");
                        return rtnMap;
                    }
                } else if ("combineSelf".equals(menuType)) {
                    //마스터 결합 ...
                    //4. 개인 고객만...
                    //CUSTOMER_TYPE  I B , G
                      /* CUSTOMER_TYPE
                        B	법인고객
                        G	공공기관
                        I	개인고객
                       */
                    String customerType = resOjb.get("CUSTOMER_TYPE");
                    if (!"I".equals(customerType)) {
                        rtnMap.put("RESULT_CODE", "00009");
                        rtnMap.put("message", "개인고객만 가입이 가능합니다. ");
                        return rtnMap;
                    }
                }

            }
        }

        // ============ STEP START ============
        Map<String, String> smsRsltMap = certService.smsAuthCertMenuType(menuType, "smsSend");
        if("Y".equals(smsRsltMap.get("menuTypeYn"))){

            // 1. sms인증 호출이력 확인
            if (0 < certService.getModuTypeStepCnt("smsSend", "")) {
                // sms인증 호출 관련 스텝 초기화
                CertDto certDto = new CertDto();
                certDto.setModuType("smsSend");
                certDto.setCompType("G");
                certService.getCertInfo(certDto);
            }

            // 결합대상 구분값, 인증종류, 이름, 핸드폰번호, 계약번호
            String[] certKey= {"urlType", "ncType", "moduType", "name", "mobileNo", "contractNum"};
            String[] certValue= {"reqSmsAuthNum", ncType, "smsSend", name, phone, certContractNum};

            // 계약번호가 없는 메뉴
            if("order".equals(menuType) || "rate".equals(menuType) || "QNA".equals(menuType) || "gift".equals(menuType) || "fath".equals(menuType)){
                // 결합대상 구분값, 인증종류, 이름, 핸드폰번호
                certKey = Arrays.copyOfRange(certKey, 0, 5);
            }

            certService.vdlCertInfo("C", certKey, certValue);
        }
        // ============ STEP END ============

        if ("Y".equals(timeYn)) {//시간연장
            AuthSmsDto authSmsDto = new AuthSmsDto();
            authSmsDto.setMenu(menuType);
            AuthSmsDto resultAuthSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);
            String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
            authSmsDto.setStartDate(today);
            authSmsDto.setPhoneNum(phone);
            authSmsDto.setMenu(menuType);

            if (resultAuthSmsDto != null) {
                authSmsDto.setAuthNum(resultAuthSmsDto.getAuthNum());
            } else {
                authSmsDto.setAuthNum("");
            }

            //관리자 정보 session 저장
            SessionUtils.setAuthSmsSession(authSmsDto);
            rtnMap.put("RESULT_CODE", "00010");

        } else {
            AuthSmsDto authSmsDto = new AuthSmsDto();
            authSmsDto.setPhoneNum(phone);
            authSmsDto.setMenu(menuType);
            authSmsDto.setReserved02(menuType);
            authSmsDto.setReserved03("nonMember");

//            boolean check = fCommonSvc.sendAuthSms(authSmsDto);
            Map<String, Object> reMap = this.fCommonSvc.sendAuthSms2(authSmsDto);
            boolean check = (boolean) reMap.get("result");

            if (check) {
                request.getSession().setAttribute("authNum", authSmsDto.getAuthNum());    //sms 인증번호를 session에 담아 비회원 로그인시 검증값으로 사용
                request.getSession().setAttribute("phoneNum", authSmsDto.getPhoneNum());    //sms 인증번호를 session에 담아 비회원 로그인시 검증값으로 사용
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            } else if (StringUtils.isNotEmpty(reMap.get("message").toString())) {
                rtnMap.put("message", reMap.get("message"));
            } else {
                rtnMap.put("RESULT_CODE", "00002");
            }

            if ("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName)) {
                rtnMap.put("AUTH_NUM", authSmsDto.getAuthNum());
            }
        }

        return rtnMap;
    }

    /**
     * 비로그인 sms 번호 확인 ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param searchVO
     * @param certifySms
     * @param phone
     * @param name
     * @param menuType
     * @param birthday
     * @return
     */

    @RequestMapping(value = {"/sms/checkCertSmsAjax.do", "/m/sms/checkCertSmsAjax.do"})
    @ResponseBody
    public JsonReturnDto checkCertSmsAjax(HttpServletRequest request
            , @ModelAttribute("searchVO") UserSessionDto searchVO
            , @RequestParam(value = "certifySms", required = false) String certifySms
            , @RequestParam(value = "phone", required = false) String phone
            , @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "menuType", required = false) String menuType
            , @RequestParam(value = "birthday", required = false) String birthday
            , @RequestParam(value = "ncType", required = false, defaultValue = "") String ncType
    ) {

        JsonReturnDto result = new JsonReturnDto();

        if (StringUtils.isBlank(certifySms) || StringUtils.isBlank(phone)) {
            throw new McpCommonJsonException("0001", INVALID_PARAMATER_EXCEPTION);
        }
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";

        String certifyPhoneNum = (String)request.getSession().getAttribute("phoneNum");

        if (!phone.equals(certifyPhoneNum)) {
            result.setReturnCode("99");
            result.setMessage(NICE_CERT_EXCEPTION_RWD);
            return result;
        }

        // mobileNo가 존재하는 경우 추가 검증(비회원 배송조회)
        String mobileNo = searchVO.getMobileNo();
        //if (mobileNo != null && !mobileNo.trim().isEmpty()) {
         if (StringUtils.isNotBlank(mobileNo)) {

            if (!mobileNo.equals(certifyPhoneNum)) {
                result.setReturnCode("99");
                result.setMessage(NICE_CERT_EXCEPTION_RWD);
                return result;
            }
        }

        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setPhoneNum(phone);
        authSmsDto.setAuthNum(name);
        String timer0 = searchVO.getTimer0();

        if (!"0".equals(timer0)) {

            if (certifySms.equals(request.getSession().getAttribute("authNum"))) {

                long tmpCrtSeq = SessionUtils.getCertSession();
                String certPageType= SessionUtils.getPageSession();

                String certContractNum= null;

                SessionUtils.invalidateSession();
                searchVO.setUserDivision("02");    //비회원
                SessionUtils.saveOrderSession(searchVO);    //주문배송조회 세션저장
                authSmsDto.setResult(true);
                authSmsDto.setMenu(menuType);

                McpUserCntrMngDto mcpUserCntrMngDto = new McpUserCntrMngDto();
                mcpUserCntrMngDto.setSubLinkName(name);
                mcpUserCntrMngDto.setCntrMobileNo(phone);
                McpUserCntrMngDto cntrList = mypageService.selectCntrListNoLogin(mcpUserCntrMngDto);
                if (cntrList != null) {
                    authSmsDto.setBirthday(cntrList.getDobyyyymmdd());
                    authSmsDto.setCtn(cntrList.getCntrMobileNo());
                    authSmsDto.setSubLinkName(name);

                    certContractNum= cntrList.getContractNum();

                    //결합신청 에서 ContractNum , svnCntrNo 구분 처리
                    if ("combine".equals(menuType) || "combineSelf".equals(menuType)) {
                        authSmsDto.setSvcCntrNo(cntrList.getSvcCntrNo());
                        authSmsDto.setContractNum(cntrList.getContractNum());
                    } else {
                        authSmsDto.setSvcCntrNo(cntrList.getContractNum());
                    }

                    authSmsDto.setCustId(cntrList.getCustId());
                }

                if (StringUtil.isNotNull(birthday) && !authSmsDto.getBirthday().equals(birthday)) {
                    authSmsDto.setMessage("생년월일이 다릅니다. 다시 확인하세요.");
                    authSmsDto.setResult(false);
                }

                if (birthday.contentEquals("") && menuType.contentEquals("simple")) {
                    authSmsDto.setMessage("생년월일을 입력해주세요.");
                    authSmsDto.setResult(false);
                }


                // ============ STEP START ============
                if (authSmsDto.isResult()) {
                    Map<String, String> smsRsltMap = certService.smsAuthCertMenuType(menuType, "smsSend");
                    if("Y".equals(smsRsltMap.get("menuTypeYn"))){

                        SessionUtils.setCertSession(tmpCrtSeq);
                        SessionUtils.setPageSession(certPageType);

                        // 결합대상 구분값, 인증종류, 이름, 핸드폰번호, 계약번호
                        String[] certKey = {"urlType", "ncType", "moduType", "name", "mobileNo", "contractNum"};
                        String[] certValue = {"chkSmsAuthNum", ncType, "smsSend", name, phone, certContractNum};

                        // 인증번호 확인이 마지막 스텝인 메뉴
                        if("QNA".equals(menuType) || "order".equals(menuType)){

                            if(certService.getStepCnt() < 1 ){
                                result.setReturnCode("STEP01");
                                result.setMessage(STEP_CNT_EXCEPTION);
                                return result;
                            }

                            certKey= new String[]{"urlType", "moduType", "stepEndYn", "name", "mobileNo"};
                            certValue = new String[]{"chkSmsAuthNum", "smsSend", "Y", name, phone};

                        } else if("rate".equals(menuType) || "gift".equals(menuType)){
                            // 계약번호가 없는 메뉴
                            // 결합대상 구분값, 인증종류, 이름, 핸드폰번호
                            certKey = Arrays.copyOfRange(certKey, 0, 5);
                        } else if("fath".equals(menuType)) {
                            certKey= new String[]{"urlType", "moduType", "name", "mobileNo"};
                            certValue = new String[]{"chkSmsAuthNum", "smsSend", name, phone};
                        }

                        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
                        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                            result.setReturnCode("STEP02");
                            result.setMessage(vldReslt.get("RESULT_DESC"));
                            return result;
                        }
                    }
                }
                // ============ STEP END ============

                SessionUtils.setAuthSmsSession(authSmsDto);

            } else {
                authSmsDto.setMessage("인증번호가 틀렸습니다. 다시 확인해 주세요.");
                authSmsDto.setResult(false);
            }
        } else {
            authSmsDto.setMessage("인증 유효시간이 지났습니다. 인증번호를 다시 받아 주세요.");
            authSmsDto.setResult(false);
        }

        if (authSmsDto.isResult()) {
            returnCode = "00";
            message = "";
        } else {
            returnCode = "41";
            message = authSmsDto.getMessage();
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        return result;
    }

    private boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession) {
        if (!StringUtil.equals(userSession.getUserDivision(), "01")) {
            return false;
        }

        if (cntrList == null) {
            return false;
        }

        if (cntrList.size() <= 0) {
            return false;
        }

        if (StringUtil.isEmpty(searchVO.getNcn())) {
            searchVO.setNcn(cntrList.get(0).getSvcCntrNo());
            searchVO.setCtn(cntrList.get(0).getCntrMobileNo());
            searchVO.setCustId(cntrList.get(0).getCustId());
            searchVO.setModelName(cntrList.get(0).getModelName());
            searchVO.setContractNum(cntrList.get(0).getContractNum());
            searchVO.setSubStatus(cntrList.get(0).getSubStatus());
        }

        for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
            String ctn = mcpUserCntrMngDto.getCntrMobileNo();
            String ncn = mcpUserCntrMngDto.getSvcCntrNo();
            String custId = mcpUserCntrMngDto.getCustId();
            String modelName = mcpUserCntrMngDto.getModelName();
            String contractNum = mcpUserCntrMngDto.getContractNum();
            String subStatus = mcpUserCntrMngDto.getSubStatus();

            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            mcpUserCntrMngDto.setSvcCntrNo(ncn);
            mcpUserCntrMngDto.setCustId(custId);
            mcpUserCntrMngDto.setModelName(modelName);
            mcpUserCntrMngDto.setContractNum(contractNum);
            if (StringUtil.equals(searchVO.getNcn(), String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))) {
                searchVO.setNcn(ncn);
                searchVO.setCtn(ctn);
                searchVO.setCustId(custId);
                searchVO.setModelName(modelName);
                searchVO.setContractNum(contractNum);
                searchVO.setSubStatus(subStatus);
            }
        }
        return true;
    }

    /**
     * 로그인 페이지 sms 인증 확인 - sms 인증 성공 시 recaptcha 점수가 낮아도 로그인 처리
     */
    @RequestMapping(value = "/sms/loginSmsCheckAjax.do")
    @ResponseBody
    public Map<String, String> loginSmsCheckAjax(HttpServletRequest request
            , @RequestParam(value = "menuType", required = true) String menuType
            , @RequestParam(value = "phone", required = true) String phone) {

        Map<String, String> rtnMap = new HashMap<>();

        // 1. sms 세션 결과 확인
        AuthSmsDto tmp = new AuthSmsDto();
        tmp.setPhoneNum(phone);
        tmp.setMenu(menuType);

        AuthSmsDto sessAuthSmsDto = SessionUtils.getAuthSmsBean(tmp);

        // 2. sms 본인인증이 올바르지 않은 경우
        if (sessAuthSmsDto == null || !sessAuthSmsDto.isResult()) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "SMS 인증 정보가 없습니다. 다시 시도 바랍니다.");
        } else {
            // 3. sms 본인인증에 성공한 경우
            if (!phone.equals(sessAuthSmsDto.getPhoneNum())) {
                rtnMap.put("RESULT_CODE", "0002");
                rtnMap.put("RESULT_MSG", "휴대폰 번호가 다릅니다. 다시 시도 바랍니다.");
            } else {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                // 리캡챠 점수가 낮아도 통과 처리
                SessionUtils.saveRecaptchaSmsSession("Y");
            }
        }

        return rtnMap;
    }


}
