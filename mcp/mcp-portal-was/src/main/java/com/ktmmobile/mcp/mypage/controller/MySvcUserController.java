package com.ktmmobile.mcp.mypage.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mplatform.vo.UserSearchVO;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;

/**
 * @project : default
 * @file : ArticleController.java
 * @author : HanNamSik
 * @date : 2013. 4. 13. 오후 6:35:30
 * @history :
 *
 * @comment : 게시물
 *
 *
 *
 */
@Controller
public class MySvcUserController {

	private static Logger logger = LoggerFactory.getLogger(MySvcUserController.class);

    //비밀번호 패턴
    //* 영문, 숫자, 특수문자 중 3종류를 조합하여 8~16자리
    private static final String Passwrod_PATTERN_8 = "^(?=.*[a-zA-Z]+)(?=.*[0-9]+)(?=.*[!@#$%^*+=-]+).{8,16}$";
    //* 영문, 숫자, 특수문자 중 2종류를 조합하여 10~16자리
    private static final String Passwrod_PATTERN_TEXT_NUM10 = "^(?=.*[a-zA-Z]+)(?=.*[0-9]+).{10,16}$";
    private static final String Passwrod_PATTERN_NUM_SPECIAL10 = "^(?=.*[!@#$%^*+=-]+)(?=.*[0-9]+).{10,16}$";
    private static final String Passwrod_PATTERN_TEXT_SPECIAL10 = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]+).{10,16}$";

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private FCommonSvc fCommonSvc;

    private final static String PW_CHECK_YN = "PW_CHECK_YN";


    @RequestMapping({"/m/mypage/userPwChagePop.do"})
    public String userPwChagePop(HttpServletRequest request,ModelMap model){
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        model.addAttribute("id", userSession.getUserId());
        return "mobile/pop/M_UserPwChagePop";
    }

    @RequestMapping({"/m/mypage/userRegCertPop.do"})
    public String userRegCertPop(HttpServletRequest request, ModelMap model,HttpServletResponse response
            ,@ModelAttribute("userVO") UserVO userVO, String phone1, String phone2, String phone3, String smsRcvYn,String emailRcvYn){
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        userVO.setUserid(userSession.getUserId());
        userVO.setName(userSession.getName());
        model.addAttribute("userVO", userVO);
        model.addAttribute("phone1", phone1);
        model.addAttribute("phone2", phone2);
        model.addAttribute("phone3", phone3);
        model.addAttribute("smsRcvYn", smsRcvYn);
        model.addAttribute("emailRcvYn", emailRcvYn);
        return "mobile/pop/M_UserRegCertPop";
    }


    /**
     * <pre>
     * 설명     : SMS 발송 처리
     *           ID , 패스원스 사용자 인증 후 관리자 전화번호로 SMS 인증 문자 전송 처리
     * @return
     * </pre>
     */
    @RequestMapping(value = "/m/mypage/userSmsAjax.do")
    @ResponseBody
    public Map<String, Object> prodUserSms(HttpServletRequest request
            ,@RequestParam(value = "name", required = false) String name
            ,@RequestParam(value = "phone1", required = false) String phone1
            ,@RequestParam(value = "phone2", required = false) String phone2
            ,@RequestParam(value = "phone3", required = false) String phone3){
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);

        if (StringUtils.isBlank(name) || StringUtils.isBlank(phone1)|| StringUtils.isBlank(phone2)|| StringUtils.isBlank(phone3)) {
            throw new McpCommonJsonException("0001" ,INVALID_PARAMATER_EXCEPTION);
        }

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if(userSession.getStatus().equals("F")){
            rtnMap.put("RESULT_CODE", "xxxx");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        String contractNum = mypageUserService.selectContractNum(name,phone1,phone2,phone3);

        if(contractNum==null ){
            rtnMap.put("RESULT_CODE", "0001");
            return rtnMap;
        }

        StringBuffer phone = new StringBuffer();
        phone.append(phone1).append(phone2).append(phone3);

        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setPhoneNum(phone.toString());
        authSmsDto.setMenu("mUserReg");
        boolean check = fCommonSvc.sendAuthSms(authSmsDto);

        if (check) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            if("REAL".equalsIgnoreCase(serverName)) {
                rtnMap.put("message", "");
            } else {
//            	rtnMap.put("message", authSmsDto.getAuthNum());
                rtnMap.put("message", "");
            }

        } else {
            rtnMap.put("RESULT_CODE", "0001");
        }
        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : SMS 발송 처리
     *           ID , 패스원스 사용자 인증 후 관리자 전화번호로 SMS 인증 문자 전송 처리
     * @return
     * </pre>
     */
    @RequestMapping(value = "/m/mypage/userSmsCheckAjax.do")
    @ResponseBody
    public Map<String, Object> userSmsCheck(HttpServletRequest request
            ,@RequestParam(value = "certifySms", required = false) String certifySms
            ,@RequestParam(value = "phone1", required = false) String phone1
            ,@RequestParam(value = "phone2", required = false) String phone2
            ,@RequestParam(value = "phone3", required = false) String phone3){
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);

        if (StringUtils.isBlank(certifySms) || StringUtils.isBlank(phone1)|| StringUtils.isBlank(phone2)|| StringUtils.isBlank(phone3)) {
            throw new McpCommonJsonException("0001" ,INVALID_PARAMATER_EXCEPTION);
        }

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        if(userSession.getStatus().equals("F")){
            rtnMap.put("RESULT_CODE", "xxxx");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }
        StringBuffer phone = new StringBuffer();
        phone.append(phone1).append(phone2).append(phone3);
        AuthSmsDto tmp = new AuthSmsDto();
        tmp.setPhoneNum(phone.toString());
        tmp.setAuthNum(certifySms);
        tmp.setMenu("mUserReg");
        SessionUtils.checkAuthSmsSession(tmp);

        if (tmp.isResult()) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        } else {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("MSG",tmp.getMessage());
        }
        return rtnMap;
    }


    @RequestMapping({"/m/mypage/userRegularUpdateAjax.do"})
    @ResponseBody
    public Map<String, Object> userRegularUpdate(HttpServletRequest request, ModelMap model,HttpServletResponse response
            ,@ModelAttribute("userVO") UserVO userVO){
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession.getStatus().equals("F")){
            throw new McpCommonJsonException("xxxx","비정상적인 접근입니다.");
        }

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String division = mypageUserService.userRegularCheck(userVO);
        if(division != null && division.equals("01")){
            rtnMap.put("RESULT_CODE", "0002");
            return rtnMap;
        }

        String contractNum = mypageUserService.selectContractNum(userVO.getName(),userVO.getPhone1(),userVO.getPhone2(),userVO.getPhone3());
        userVO.setContractNo(contractNum);
        userVO.setCustomerId(userSession.getCustomerId());

        int updateS = mypageUserService.userRegularUpdate(userVO);
        userVO.setRepNo("R");
        this.mypageUserService.insertRegularUpdate(userVO);
        if(updateS==1){
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        }else{
            rtnMap.put("RESULT_CODE", "0001");
        }
        return rtnMap;
    }

    @RequestMapping({"/m/mypage/userRegularCheckAjax.do"})
    @ResponseBody
    public Map<String, Object> userRegularCheck(HttpServletRequest request, ModelMap model,HttpServletResponse response
            ,@ModelAttribute("userVO") UserVO userVO){

        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession.getStatus().equals("F")){
            throw new McpCommonJsonException("xxxx","비정상적인 접근입니다.");
        }
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();


        String division = mypageUserService.userRegularCheck(userVO);

        if(division==null||!division.equals("")){
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("division", division);
        }else{
            rtnMap.put("RESULT_CODE", "0001");
        }

        return rtnMap;
    }




    @RequestMapping(value = "/m/mypage/userMultiSmsCheckAjax.do")
    @ResponseBody
    public Map<String, Object> userMultiSmsCheck(HttpServletRequest request
            ,@RequestParam(value = "certifySms", required = false) String certifySms
            ,@RequestParam(value = "phone1", required = false) String phone1
            ,@RequestParam(value = "phone2", required = false) String phone2
            ,@RequestParam(value = "phone3", required = false) String phone3){
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        StringBuffer phone = new StringBuffer();
        phone.append(phone1).append(phone2).append(phone3);
        AuthSmsDto tmp = new AuthSmsDto();
        tmp.setPhoneNum(phone.toString());
        tmp.setAuthNum(certifySms);
        tmp.setMenu("mMultSms");
        SessionUtils.checkAuthSmsSession(tmp);

        if (StringUtils.isBlank(userSession.getUserId()) ||StringUtils.isBlank(certifySms) || StringUtils.isBlank(phone1)|| StringUtils.isBlank(phone2)|| StringUtils.isBlank(phone3)) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("MSG",INVALID_PARAMATER_EXCEPTION);
            return rtnMap;
        }

        if (tmp.isResult()) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        } else {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("MSG",tmp.getMessage());
        }
        return rtnMap;
    }




}
