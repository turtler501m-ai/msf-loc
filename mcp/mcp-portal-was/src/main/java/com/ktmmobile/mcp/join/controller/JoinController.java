package com.ktmmobile.mcp.join.controller;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.NiceLogDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.SnsLoginDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.service.NiceLogSvc;
import com.ktmmobile.mcp.common.service.SnsSvc;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.event.dto.UserPromotionDto;
import com.ktmmobile.mcp.event.service.UserPromotionSvc;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.join.dto.UserAgentDto;
import com.ktmmobile.mcp.join.service.JoinSvc;
import com.ktmmobile.mcp.mypage.service.MypageUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

/**
 * 설명 : 회원가입 Controller
 * @Author : jsh
 * @Date : 2021.12.30
 */
@Controller
public class JoinController {

    private static Logger logger = LoggerFactory.getLogger(JoinController.class);

    @Autowired
    JoinSvc joinSvc;

    @Autowired
    SnsSvc snsSvc;

    @Autowired
    NiceLogSvc nicelog ;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    CertService certService;

    @Autowired
    private UserPromotionSvc userPromotionSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Value("${SERVER_NAME}")
    private String serverName;

    /**
     * 설명 : 회원가입 약관동의 / 본인인증
     * @Author : jsh
     * @Date : 2021.12.30
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/join/fstPage.do", "/m/join/fstPage.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String joinFstPageView(Model model, HttpServletRequest request, HttpSession session) {

        String userProReq = SessionUtils.getUserPromotionRes();
        //회원가입 프로모션 세션이 있다면 세션 삭제
        if(userProReq != null) {
            SessionUtils.saveUserPromotionRes(null);
        }

        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/join/joinOne";
        }else {
            return "/portal/join/joinOne";
        }
    }

    /**
     * 설명 : 회원가입 정보 입력
     * @Author : jsh
     * @Date : 2021.12.30
     * @param joinDto
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = {"/join/sndPage.do", "/m/join/sndPage.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String joinSndPageView(@ModelAttribute JoinDto joinDto, HttpServletRequest request, HttpSession session,Model model) {

        NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);

        if (niceResDto == null || StringUtils.isEmpty(niceResDto.getResSeq())) {
            if("Y".equals(NmcpServiceUtils.isMobile())){
                return "redirect:/m/join/fstPage.do";
            }else {
                return "redirect:/join/fstPage.do";
            }
        }

        //회원가입 중복체크
        int duplicateCount = joinSvc.dupleChk(niceResDto.getDupInfo());
        int dormancyCount = joinSvc.dormancyDupleChk(niceResDto.getDupInfo());

        model.addAttribute("snsAddYn", "N");

        if (duplicateCount > 0 || dormancyCount > 0) {
            if(!"".equals(StringUtil.NVL(request.getParameter("snsCd"), "")) && !"".equals(StringUtil.NVL(request.getParameter("snsId"), ""))) {
                model.addAttribute("diVal", EncryptUtil.ace256Enc(niceResDto.getDupInfo()));
                model.addAttribute("snsAddYn", "Y");
                session.setAttribute("joinVal", "Y");
            }else {
                ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
                responseSuccessDto.setSuccessMsg(DUPLICATE_SQL_EXCEPTION);
                if("Y".equals(NmcpServiceUtils.isMobile())){
                    responseSuccessDto.setRedirectUrl("/m/loginForm.do");
                } else {
                    responseSuccessDto.setRedirectUrl("/loginForm.do");
                }
                model.addAttribute("responseSuccessDto", responseSuccessDto);
                return "/common/successRedirect";

            }
        }


        model.addAttribute("checkKid", "N");
        if(niceResDto.getBirthDate() != null && !"".equals(niceResDto.getBirthDate())){
            try {
                //만 나이 확인
                String birthDate = niceResDto.getBirthDate().length() > 8 ? EncryptUtil.ace256Dec(niceResDto.getBirthDate()) : niceResDto.getBirthDate();
                niceResDto.setBirthDate(birthDate);
                int age = NmcpServiceUtils.getBirthDateToAge(birthDate, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
                //회원가입 시, 만 14세 미만 이용자 법적대리인 동의 절차 적용 
                if (Constants.AGREE_AUT_AGE > age) {
                    model.addAttribute("checkKid", "Y");
                }
            } catch (CryptoException e) {
                logger.error("Exception e : {}", e.getMessage());
            }

        }
        model.addAttribute("snsCd", request.getParameter("snsCd"));
        model.addAttribute("snsId", request.getParameter("snsId"));
        model.addAttribute("snsMobileNo", request.getParameter("snsMobileNo"));
        model.addAttribute("snsEmail", request.getParameter("snsEmail"));
        model.addAttribute("snsBirthday", request.getParameter("snsBirthday"));
        model.addAttribute("clausePriAdFlag", request.getParameter("clausePriAdFlag"));
        model.addAttribute("personalInfoCollectAgree", request.getParameter("personalInfoCollectAgree"));
        model.addAttribute("othersTrnsAgree", request.getParameter("othersTrnsAgree"));
        model.addAttribute("getInfo", niceResDto);
        model.addAttribute("joinObj", joinDto);

        if("Y".equals(NmcpServiceUtils.isMobile())){
            return "/mobile/join/joinTwo";
        }else {
            return "/portal/join/joinTwo";
        }
    }

    /**
     * 설명 : 아이디 중복 체크
     * @Author : jsh
     * @Date : 2021.12.30
     * @param checkId
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = {"/join/idDuplicatedCheckAjax.do", "/m/join/idDuplicatedCheckAjax.do"}, method=RequestMethod.POST)
    @ResponseBody
    public int dublicateAjax(@RequestParam(required=false, defaultValue="") String checkId
            , Model model, HttpServletResponse response) {

        int resultVal = joinSvc.idCheck(checkId);
        model.addAttribute("resultVal", resultVal);

        return resultVal;
    }

    /**
     * 설명 : 회원가입 정보 입력
     * @Author : jsh
     * @Date : 2021.12.30
     * @param joinDto
     * @param model
     * @param bind
     * @param session
     * @return
     */
    @RequestMapping({"/join/joinAction.do", "/m/join/joinAction.do"})
    public String joinAction(@ModelAttribute("joinDto") JoinDto joinDto, Model model, BindingResult bind, HttpSession session) {

        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);

        if (niceResDto ==null) {
            throw new McpCommonException(NICE_CERT_EXCEPTION_INSR);
        } else {
            joinDto.setName(niceResDto.getName());
        }

        String redirectUrl= "/join/fstPage.do";
        if("Y".equals(NmcpServiceUtils.isMobile())) redirectUrl= "/m/join/fstPage.do";

        // 아이디 검사
        if(joinDto.getUserId() == null) {
            throw new McpCommonException("아이디 미입력 되었습니다. 확인해 주세요.", redirectUrl);
        }
        // 4~12자의 영문 소문자, 숫자만
        String regex = "^[a-z0-9]{4,12}$";
        boolean checkId = Pattern.matches(regex, joinDto.getUserId());
        if(!checkId){
            throw new McpCommonException("4~12자의 영문 소문자, 숫자만 사용 가능합니다.", redirectUrl);
        }



        //만 나이 확인
        //int age = NmcpServiceUtils.getBirthDateToAge(niceResDto.getBirthDate(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
        int age = 999;
        String birthDate = "";
        //일단.. TO-BE 형태는 세션정보 없을시 디비에서 가져오는 로직을 제거했으므로 임시 아래와 같이 처리
        if (niceResDto.getBirthDate() != null && !"".equals(niceResDto.getBirthDate())) {
            try {
                // 만 나이 확인
                birthDate = niceResDto.getBirthDate().length() > 8 ? EncryptUtil.ace256Dec(niceResDto.getBirthDate()) : niceResDto.getBirthDate();
                joinDto.setBirthday(birthDate);
                age = NmcpServiceUtils.getBirthDateToAge(birthDate, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            } catch (CryptoException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        NiceResDto niceResAgent = null ;
        UserAgentDto userAgent = null;

        //회원가입 시, 만 14세 미만 이용자 법적대리인 동의 절차 적용 
        if (Constants.AGREE_AUT_AGE > age) {

            //법정대리인 검증
            niceResAgent =  SessionUtils.getNiceAgentResCookieBean() ;

            if (niceResAgent == null) {
                if (joinDto.getNiceAgentHistSeq() > 0) {
                    /*
                     * 외부사이트 팝업후 session 못읽어 오는 경우 DB 정보 패치
                     * MCP_NICE_HIST 정보조회
                     */
                    NiceLogDto niceLogTmp = new NiceLogDto();
                    niceLogTmp.setEndYn("N");
                    niceLogTmp.setParamR3(Constants.CUST_AGENT_AUTH);
                    niceLogTmp.setNiceHistSeq(joinDto.getNiceAgentHistSeq());
                    NiceLogDto niceLogRtn = nicelog.showMcpNiceHist(niceLogTmp);
                    if (niceLogRtn != null) {
                        niceResAgent = new NiceResDto();
                        niceResAgent.setName(niceLogRtn.getName());
                        //niceResAgent.setBirthDate(niceLogRtn.getBirthDateDec());
                        niceResAgent.setBirthDate(birthDate);
                        niceResAgent.setDupInfo(niceLogRtn.getDupInfo());
                        niceResAgent.setConnInfo(niceLogRtn.getConnInfo());
                        niceResAgent.setReqSeq(niceLogRtn.getReqSeq());
                        niceResAgent.setResSeq(niceLogRtn.getResSeq());
                    }
                }
            }

            if (niceResAgent == null) {
                throw new McpCommonException(NICE_CERT_AGENT_EXCEPTION_INSR);
            } else {
                //미성년자 법정대리인 개인정보 수집이용 동의여부
                if(StringUtil.isEmpty(joinDto.getMinorAgentAgree()) || !"Y".equals(joinDto.getMinorAgentAgree()) ){
                    throw new McpCommonException("미성년자 법정대리인 개인정보 수집이용에 동의하시기 바랍니다.");
                }

                userAgent = new UserAgentDto();
                userAgent.setUserid(joinDto.getUserId());
                userAgent.setMinorAgentName(niceResAgent.getName());
                userAgent.setMinorAgentRrn(niceResAgent.getBirthDate());
                userAgent.setMinorAgentRelation(joinDto.getMinorAgentRelation()) ;
                userAgent.setMinorAgentCi(niceResAgent.getConnInfo());
                userAgent.setAgentAuthInfo(niceResAgent.getReqSeq() + " " + niceResAgent.getResSeq() ) ;
                userAgent.setMinorAgentAgree(joinDto.getMinorAgentAgree());
            }

            // ============ STEP START ===========
            // 대리인 최종정보 검증
            // 고객유형, 이름, 생년월일, DI
            String[] certKey= {"urlType", "ncType", "name", "birthDate", "dupInfo"};
            String[] certValue= {"chkAgentJoinForm", "1", joinDto.getMinorAgentName(), niceResAgent.getBirthDate(), niceResAgent.getDupInfo()};

            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), redirectUrl);
            }
            // ============ STEP END ============

        }

        joinDto.setName(niceResDto.getName());


        if (joinDto.isChkAndPut(niceResDto)) {
            throw new McpCommonException(INVALID_REFERER_EXCEPTION);
        }
        //중복가입 체크

        int resultVal = joinSvc.idCheck(joinDto.getUserId());
        if(resultVal > 0) {
            throw new McpCommonException(DUPLICATE_SQL_EXCEPTION);
        }

        if(niceResDto != null && joinSvc.dupleChk(niceResDto.getDupInfo()) > 0) {
            throw new McpCommonException(DUPLICATE_SQL_EXCEPTION);
        }
        //핀 넣기
        String pin = niceResDto.getDupInfo();
        joinDto.setPin(pin);

        if("Y".equals(joinDto.getClausePriAdFlag())){
            joinDto.setAgreeEmail("Y");
            joinDto.setAgreeSMS("Y");
        }else {
            joinDto.setAgreeEmail("N");
            joinDto.setAgreeSMS("N");
        }

        // ============ STEP START ===========
        int certStep= 2;
        if(Constants.AGREE_AUT_AGE > age) certStep= 4; // 4개

        // 1. 최소 스텝 수 확인
        if(certService.getStepCnt() < certStep ){
            throw new McpCommonException(STEP_CNT_EXCEPTION, redirectUrl);
        }

        // 2. 최종 데이터 검증 : 스텝종료여부, 고객유형, 이름, 생년월일, DI, 휴대폰번호
        String[] certKey= {"urlType", "stepEndYn", "ncType", "name", "birthDate", "dupInfo", "mobileNo"};
        String[] certValue= {"chkJoinForm", "Y", "0", joinDto.getName(), joinDto.getBirthday(), joinDto.getPin(), joinDto.getMobileNo()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonException(vldReslt.get("RESULT_DESC"), redirectUrl);
        }
        // ============ STEP END ============

        //비밀번호 role check start 20160711
        if(joinDto.getPassword().equals(joinDto.getPasswordChk())){//비번 , 비번 확인 비교
            //패스워드 패턴 체크 추가 -S-
            boolean pwCheckFlag = StringUtil.passwordPatternCheck(joinDto.getPassword());
            if (pwCheckFlag) {


                if("1".equals(joinDto.getGender())){
                    joinDto.setGender("0");
                }else{
                    joinDto.setGender("1");
                }

                //인증통신사 추가
                joinDto.setAuthTelCode(niceResDto.getsMobileCo());
                //마케팅 수신동의 경로 추가
                joinDto.setMtkAgrReferer("join");

                joinSvc.insertMemberInfo(joinDto);
                joinSvc.insertUpdateMrktHist(joinDto); // 수정

                String userProReq = SessionUtils.getUserPromotionRes();

                //회원가입 프로모션으로 인증 받아 가입 한 경우
                if(userProReq != null) {

                    UserPromotionDto userPromotionDto = new UserPromotionDto();
                    // 이벤트 코드
                    NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
                    nmcpMainCdDtlDto.setCdGroupId(Constants.USER_PROMOTION);
                    List<NmcpCdDtlDto> nmcpMainCdDtlDtoList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

                    if(nmcpMainCdDtlDtoList !=null && !nmcpMainCdDtlDtoList.isEmpty()){
                        for(NmcpCdDtlDto dto : nmcpMainCdDtlDtoList){
                            userPromotionDto.setPromotionCode(dto.getDtlCd());
                        }
                    }

                    userPromotionDto.setUserId(joinDto.getUserId());
                    userPromotionDto.setInterestCode(joinDto.getChkInterest());
                    userPromotionDto.setUseTelCode(niceResDto.getsMobileCo());
                    userPromotionDto.setCretId(joinDto.getUserId());
                    userPromotionDto.setAmdId(joinDto.getUserId());
                    userPromotionDto.setRip(ipstatisticService.getClientIp());

                    userPromotionSvc.insertUserPromotion(userPromotionDto);
                }


                //법정 대리인 정보 입력
                if (userAgent != null) {
                    joinSvc.insetUserAgent(userAgent);
                }
            } else {
                throw new McpCommonException(NO_MATCHE_PASSWORD_PATTERN);
            }
            //패스워드 패턴 체크 추가 -E-

        }else{
            throw new McpCommonException(NO_MATCHE_PASSWORD);
        }
        session.setAttribute("joinVal", "Y");
        session.setAttribute("maskNm", MaskingUtil.getMaskedName(niceResDto.getName()));

        if(!"".equals(StringUtil.NVL(joinDto.getSnsCd(), "")) && !"".equals(StringUtil.NVL(joinDto.getSnsId(), ""))){
            joinSvc.insertSnsInfo(joinDto);
            session.setAttribute("mbrSnsAddYn", "Y");
        }




        try {
            JuoSubInfoDto juoSubInfoRtn = mypageUserService.selectJuoSubInfo(niceResDto.getName(),joinDto.getMobileNo());

            if(juoSubInfoRtn != null && ("I".equals(juoSubInfoRtn.getCustomerType()) || "O".equals(juoSubInfoRtn.getCustomerType()))){
                String customerSsn = EncryptUtil.ace256Dec(juoSubInfoRtn.getCustomerSsn()).substring(0, 6);
                String niceBirth = niceResDto.getBirthDate();

                if(niceResDto.getBirthDate().length() >= 8) {
                    niceBirth = niceResDto.getBirthDate().substring(2, 8);
                }else if(niceResDto.getBirthDate().length() == 6) {
                    niceBirth = niceResDto.getBirthDate().substring(0, 6);
                }

                boolean regularYn = false;
                if("LOCAL".equals(serverName)) {
                    if(juoSubInfoRtn.getCustomerLinkName().equals(niceResDto.getName()) && juoSubInfoRtn.getSubscriberNo().equals(joinDto.getMobileNo())) {
                        regularYn = true;
                    }
                }else {
                    if(customerSsn.equals(niceBirth) && juoSubInfoRtn.getCustomerLinkName().equals(niceResDto.getName())
                            && juoSubInfoRtn.getSubscriberNo().equals(joinDto.getMobileNo())) {

                        //1,2 1900년대생의 남,녀
                        //3,4 2000년대생 남녀
                        //5,6 외국 1900년대생 남녀
                        //7,8 외국 2000년대생 남녀
                        if("1".equals(niceResDto.getGender())) {
                            if("1".equals(juoSubInfoRtn.getGender()) || "3".equals(juoSubInfoRtn.getGender()) || "5".equals(juoSubInfoRtn.getGender()) || "7".equals(juoSubInfoRtn.getGender())){
                                regularYn = true;
                            }
                        }else {
                            if("2".equals(juoSubInfoRtn.getGender()) || "4".equals(juoSubInfoRtn.getGender()) || "6".equals(juoSubInfoRtn.getGender()) || "8".equals(juoSubInfoRtn.getGender())){
                                regularYn = true;
                            }
                        }
                    }
                }

                //정회원 처리
                if(regularYn) {
                    try {
                        UserVO userVo = new UserVO();
                        userVo.setContractNo(juoSubInfoRtn.getContractNum());
                        userVo.setPhone1(joinDto.getMobileNo().substring(0, 3));
                        userVo.setPhone2(joinDto.getMobileNo().substring(3, 7));
                        userVo.setPhone3(joinDto.getMobileNo().substring(7));
                        userVo.setUserid(joinDto.getUserId());
                        userVo.setCustomerId(juoSubInfoRtn.getCustomerId());
                        userVo.setRepNo("R");
                        userVo.setJoinYn("Y");
                        mypageUserService.userRegularUpdate(userVo);
                        mypageUserService.insertRegularUpdate(userVo);
                        session.setAttribute("regularYn", "Y");
                    } catch(DataAccessException e) {
                        logger.error("회원가입 중 정회원 처리 Exception : {}", e.getMessage());
                    } catch(Exception e) {
                        logger.error("회원가입 중 정회원 처리 Exception : {}", e.getMessage());
                    }
                }
            }
        } catch (CryptoException e) {
            logger.error("CryptoException e = {}", ACE_256_DECRYPT_EXCEPTION);
        } catch (Exception e){
            logger.error("Exception e = {}", e.getMessage());
        }

        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        if("Y".equals(NmcpServiceUtils.isMobile())){
            responseSuccessDto.setRedirectUrl("/m/join/thirdPage.do");
        }else {
            responseSuccessDto.setRedirectUrl("/join/thirdPage.do");
        }
        model.addAttribute("responseSuccessDto", responseSuccessDto);
        return "/common/successRedirect";
    }


    /**
     * 설명 : 회원가입 완료 페이지
     * @Author : jsh
     * @Date : 2021.12.30
     * @param joinDto
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = {"/join/thirdPage.do", "/m/join/thirdPage.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String joinTrdPageView(@ModelAttribute("joinDto") JoinDto joinDto, Model model, HttpSession session) {

        String returnUrl = "";
        String sessionChk = (String) session.getAttribute("joinVal");
        String mbrSnsAddYn = (String) session.getAttribute("mbrSnsAddYn");
        String name = (String) session.getAttribute("snsAddName");
        String maskNm = (String) session.getAttribute("maskNm");
        String regularYn = (String) session.getAttribute("regularYn");
        session.removeAttribute("regularYn");

        NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);

        if(niceResDto != null) {
            joinDto.setName(MaskingUtil.getMaskedName(niceResDto.getName()));
        }else {
            joinDto.setName(MaskingUtil.getMaskedName(name));
        }

        if(joinDto.getName() == null || "".equals(joinDto.getName())) {
            joinDto.setName(maskNm);
        }

        String userProReq = SessionUtils.getUserPromotionRes();

        //회원가입 프로모션으로 인증 받아 가입 한 경우 세션 삭제
        if(userProReq != null) {
            SessionUtils.saveUserPromotionRes(null);
            if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                return "redirect:/m/event/userPromotion.do?promoFinish=Y";
            }else {
                return "redirect:/event/userPromotion.do?promoFinish=Y";
            }
        }else {
            if("Y".equals(sessionChk)) {
                if("Y".equals(NmcpServiceUtils.isMobile())){
                    returnUrl = "/mobile/join/joinThird";
                }else {
                    returnUrl = "/portal/join/joinThird";
                }
            } else {
                if("Y".equals(NmcpServiceUtils.isMobile())){
                    return "redirect:/m/join/fstPage.do";
                }else {
                    return "redirect:/join/fstPage.do";
                }
            }
        }

        session.removeAttribute(SessionUtils.NICE_AUT_COOKIE);

        model.addAttribute("mbrSnsAddYn", mbrSnsAddYn);
        model.addAttribute("getIpinInfo", joinDto);
        model.addAttribute("regularYn", regularYn);

        // 기존 회원 SNS 연동 추가하는 CASE
        if(joinDto.getSnsAddYn() != null && "Y".equals(joinDto.getSnsAddYn()) && !"".equals(StringUtil.NVL(joinDto.getDiVal(), ""))) {
            JoinDto reslt = joinSvc.getPinToUserInfo(joinDto.getDiVal());
            if(reslt != null) {
                reslt.setEncUserId(EncryptUtil.ace256Enc(reslt.getUserId()));
                reslt.setUserId(MaskingUtil.getMaskedId(reslt.getUserId()));
                reslt.setName(MaskingUtil.getMaskedName(reslt.getName()));
                reslt.setSnsCd(joinDto.getSnsCd());
                reslt.setSnsId(joinDto.getSnsId());
                reslt.setClausePriAdFlag(joinDto.getClausePriAdFlag());
                model.addAttribute("aleadyMbrYn", "Y");
                model.addAttribute("getIpinInfo", reslt);
            }else {
                throw new McpCommonException(INVALID_REFERER_EXCEPTION);
            }
        }

        return returnUrl;
    }

    /**
     * 설명 : 중복가입 확인
     * @Author : jsh
     * @Date : 2021.12.30
     * @param pin
     * @param model
     * @return
     */
    @RequestMapping(value = "/m/join/dupleJoinCheckAjax.do", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> dupleChk(@RequestParam(required=false, defaultValue="") String pin, Model model) {

        Map<String,Object> rstMap = new HashMap<String, Object>();
        int rst = joinSvc.dupleChk(pin);

        rstMap.put("result", rst);


        return rstMap;
    }

    /**
     * 설명 : SNS 연결하기
     * @Author : jsh
     * @Date : 2021.12.30
     * @param pin
     * @param model
     * @return
     */
    @RequestMapping(value = {"/join/snsAdd.do", "/m/join/snsAdd.do"})
    @ResponseBody
    public Map<String, Object> snsAdd(@ModelAttribute("joinDto") JoinDto joinDto, HttpSession session) {

        Map<String, Object> rstMap = new HashMap<String, Object>();

        if(!"".equals(StringUtil.NVL(joinDto.getEncUserId(), "")) && !"".equals(StringUtil.NVL(joinDto.getSnsCd(), "")) && !"".equals(StringUtil.NVL(joinDto.getSnsId(), ""))){
            try {

                SnsLoginDto snsCheck = snsSvc.selectSnsIdCheck(joinDto.getSnsId());

                if(snsCheck == null) {
                    joinDto.setUserId(EncryptUtil.ace256Dec(joinDto.getEncUserId()));
                    joinSvc.insertSnsInfo(joinDto);

                    if("Y".equals(joinDto.getClausePriAdFlag())) {
                        joinDto.setAgreeEmail("Y");
                        joinDto.setAgreeSMS("Y");
                        joinSvc.updateClausePriAdFlag(joinDto);
                    }

                    session.setAttribute("mbrSnsAddYn", "Y");
                    session.setAttribute("snsAddName", joinDto.getName());

                }
                rstMap.put("resltCd", "0000");
            } catch (CryptoException e) {
                rstMap.put("resltCd", "-1");
            }
        }else {
            rstMap.put("resltCd", "-2");
        }

        return rstMap;
    }
}
