package com.ktmmobile.mcp.mypage.controller;

import com.ktmmobile.mcp.cert.dto.CertDto;
import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.vo.MpBilPrintInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.PaywayService;
import com.ktmmobile.mcp.payinfo.dto.PaywayDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;


@Controller
public class PaywayController {

    private static Logger logger = LoggerFactory.getLogger(PaywayController.class);

    @Autowired
    private MypageService mypageService;

    @Autowired
    private PaywayService paywayService;

    @Autowired
    private CertService certService;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    /**
     * 명세서 관리
     */
    @RequestMapping(value= {"/m/mypage/chargeView05.do","/mypage/chargeView05.do"})
    public String chargeView05(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        String pageUrl = "/portal/mypage/pay/paywayInfo";
        String redirectUrl = "/mypage/chargeView05.do";
        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            pageUrl = "/mobile/mypage/pay/paywayInfo";
            redirectUrl = "/m/mypage/chargeView05.do";
        }
        checkOverlapDto.setRedirectUrl(redirectUrl);
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/m/loginForm.do";
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String ctn = searchVO.getCtn();

        String reqType = "";
        String reqTypeNm = "";
        String blaAddr = "";
        String payMethod = "";
        String blBankAcctNo = "";
        String billTypeCd = "";
        String billCycleDueDay = "";
        String prevCardNo = "";
        String prevExpirDt = "";
        String payTmsCd = "";

        Map<String,Object> map = new HashMap<String,Object>();
        try {

            map = paywayService.getPayData(ncn, ctn, custId);
            reqType = (String) map.get("reqType");
            reqTypeNm = (String) map.get("reqTypeNm");
            blaAddr = (String) map.get("blaAddr");
            payMethod = (String) map.get("payMethod");
            blBankAcctNo = (String) map.get("blBankAcctNo");
            billTypeCd = (String) map.get("billTypeCd");
            billCycleDueDay = (String) map.get("billCycleDueDay");
            prevCardNo = (String) map.get("prevCardNo");
            prevExpirDt = (String) map.get("prevExpirDt");
            payTmsCd = (String) map.get("payTmsCd");

        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("error");
        }

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("휴대폰번호,납부정보");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        }

        model.addAttribute("reqType", reqType);
        model.addAttribute("reqTypeNm", reqTypeNm);
        model.addAttribute("blaAddr", blaAddr);
        model.addAttribute("payMethod", payMethod);
        model.addAttribute("blBankAcctNo", blBankAcctNo);
        model.addAttribute("billTypeCd", billTypeCd);
        model.addAttribute("billCycleDueDay", billCycleDueDay);
        model.addAttribute("prevCardNo", prevCardNo);
        model.addAttribute("prevExpirDt", prevExpirDt);
        searchVO.setUserName(userSession.getName());
        model.addAttribute("cntrList", cntrList);
        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("payTmsCd", payTmsCd);

        return pageUrl;
    }

    /**
     * 명세서 관리
     */
    @RequestMapping(value= {"/m/mypage/paywayWiew.do","/mypage/paywayWiew.do","/m/mypage/paywayView.do","/mypage/paywayView.do"})
    public String payway(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        String pageUrl = "/portal/mypage/pay/paywayWiew";
        String redirectUri = "/mypage/chargeView05.do";
        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/mypage/pay/paywayWiew";
            redirectUri = "/m/mypage/chargeView05.do";
        }

        checkOverlapDto.setRedirectUrl("/mypage/paywayView.do");


        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }



        String paramNcn = StringUtil.NVL(searchVO.getNcn(),"");



        if("".equals(paramNcn)) {
            return redirectUri;
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String ctn = searchVO.getCtn();
        String ncn = searchVO.getNcn();
        String contractNum= searchVO.getContractNum();
        String custId = "";

        // ============ STEP START ============
        // 계약번호
        /*String[] certKey= {"urlType", "contractNum"};
        String[] certValue= {"chkMemberAuth", contractNum};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonException(vldReslt.get("RESULT_DESC"), redirectUri);
        }*/
        // 계약번호
        SessionUtils.removeCertSession();
        String[] certKey= {"urlType", "contractNum"};
        String[] certValue= {"chkMemberAuth", searchVO.getContractNum()};
        certService.vdlCertInfo("C", certKey, certValue);
        // ============ STEP END ============

        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                custId = mcpUserCntrMngDto.getCustId();
                break;
            }
        }

        // 본인인증
        /*
        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setMenu("charge");
        authSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);
        boolean cert = true;

        if(authSmsDto==null ) {
            cert = false;
        } else {
            String phoneNum = StringUtil.NVL(authSmsDto.getPhoneNum(),"");
            if("".equals(phoneNum) || !phoneNum.equals(ctn) || !authSmsDto.isResult()) {
                cert = false;
            }
        }
        if(!cert) {
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            responseSuccessDto.setRedirectUrl(redirectUri);
            responseSuccessDto.setSuccessMsg("인증정보가 없습니다.");
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }
        */



        // 현재 납부 방법 X23
        MpFarChangewayInfoVO mFarchangewayinfovo =  paywayService.farChangewayInfo(ncn, ctn, custId);
        String payMethod = "";
        String payBizrCd = "";
        if(mFarchangewayinfovo !=null) {
            payMethod = StringUtil.NVL(mFarchangewayinfovo.getPayMethod(),"");
            payBizrCd = StringUtil.NVL(mFarchangewayinfovo.getPayBizrCd(),"");
        }



        // 가입정보조회 연동 규격 X01
        MpPerMyktfInfoVO mPermyktfinfovo = paywayService.perMyktfInfo(ncn, ctn, custId);
        String addr = "";
        if( mPermyktfinfovo !=null ) {
            addr = mPermyktfinfovo.getAddr();
        }


        // 종이청구서조회 X10
        MpBilPrintInfoVO mBilprintinfovo = paywayService.bilPrintInfo(ncn, ctn, custId);
        String zipCode = "";
        String pAddr = "";
        String sAddr = "";
        if(mBilprintinfovo !=null) {
            zipCode = mBilprintinfovo.getZipCode();
            pAddr = mBilprintinfovo.getpAddr();
            sAddr = mBilprintinfovo.getsAddr();
        }
        Calendar today = Calendar.getInstance();
        Integer nowY = today.get(Calendar.YEAR);

        model.addAttribute("nowY", nowY);
        model.addAttribute("payMethod", payMethod);
        model.addAttribute("payBizrCd", payBizrCd);
        model.addAttribute("ncn", ncn);
        model.addAttribute("contractNum", contractNum);
        model.addAttribute("addr", addr);
        model.addAttribute("zipCode", zipCode);
        model.addAttribute("pAddr", pAddr);
        model.addAttribute("sAddr", sAddr);
        return pageUrl;
    }

    @RequestMapping(value =  {"/mypage/certChkAjax.do"})
    @ResponseBody
    public Map<String, Object> certChkAjax(HttpServletRequest request, Model model, @ModelAttribute PaywayDto paywayDto, HttpSession session){

        Map<String, Object> map = new HashMap<String, Object>();
        String RESULT_CODE = "00";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("01",F_BIND_EXCEPTION);
        }

        // session 고객정보
        String name = StringUtil.NVL(userSession.getName(),"");
        String birthday = StringUtil.NVL(userSession.getBirthday(),"");
        if(birthday.length() == 8) {
            birthday = birthday.substring(2,8);
        }
        // 파람
        String cstmrName = paywayDto.getCstmrName();
        String cstmrNativeRrn01 = paywayDto.getCstmrNativeRrn01();
        if(name.equals(cstmrName) && birthday.equals(cstmrNativeRrn01)) {
            RESULT_CODE = "00000";

            // ============ STEP START ============
            // 이름, 생년월일
            String[] certKey= {"urlType", "name", "birthDate"};
            String[] certValue= {"chkMcpUserAuth", cstmrName, cstmrNativeRrn01};
            certService.vdlCertInfo("C", certKey, certValue);
            // ============ STEP END ============
        }
        map.put("RESULT_CODE",RESULT_CODE);
        return map;
    }

       /**
     * 납부방법 변경 ajax
     */
    @RequestMapping(value="/mypage/farChgWayChgAjax.do")
    @ResponseBody
    public Map<String,Object> farChgWayChgAjax(HttpServletRequest request, Model model, @ModelAttribute PaywayDto paywayDto, HttpSession session)  {

        Map<String,Object> map = new HashMap<String,Object>();

        String message = "오류가 밸생했습니다.다시 시도해 주세요.";
        String returnCode= "00";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "01";
            message = "비정상적인 접근입니다.";
            map.put("returnCode",returnCode);
            map.put("message",message);
            return map;

        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        //정회원
        if(cntrList.size() <= 0){
            returnCode = "02";
            message = "비정상적인 접근입니다.";
            map.put("returnCode",returnCode);
            map.put("message",message);
            return map;
        }

        String ncn = StringUtil.NVL(paywayDto.getNcn(),"");
        if ( "".equals(ncn) ) {
            returnCode = "03";
            message = "비정상적인 접근입니다.";
            map.put("returnCode",returnCode);
            map.put("message",message);
            return map;
        }

        String ctn = "";
        String custId = "";
        String contractNum= "";
        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                custId = mcpUserCntrMngDto.getCustId();
                contractNum= mcpUserCntrMngDto.getContractNum();
                break;
            }
        }

        String reqPayType = StringUtil.NVL(paywayDto.getReqPayType(),"");
        paywayDto.setCtn(ctn);
        paywayDto.setCustId(custId);

        // ================ STEP START ================
        // 스텝종료여부, 계약번호, 이름, 생년월일, DI, 은행코드, 계좌번호
        String[] certKey= {"urlType", "stepEndYn", "contractNum",  "dupInfo"
                         , "reqBank", "reqAccountNumber"};
        String[] certValue= null;
        int certStep= 4;
        // ================ STEP END ================

        if("D".equals(reqPayType)) {

            certStep= 5;

            NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);
            //비정상 접근 확인
            if(niceResDto == null) {
                returnCode = "04";
                message = "본인인증 정보가 없습니다.";
                map.put("returnCode",returnCode);
                map.put("message",message);
                return map;
            }

            String cstmrNativeRrn01 = niceResDto.getBirthDate();
            if (cstmrNativeRrn01 != null && cstmrNativeRrn01.length() > 7) {
                cstmrNativeRrn01 = cstmrNativeRrn01.substring(2, 8);
            }
            paywayDto.setCstmrName(niceResDto.getName()) ;
            paywayDto.setCstmrNativeRrn01(cstmrNativeRrn01)   ;
            paywayDto.setReqSeq(niceResDto.getReqSeq());
            paywayDto.setResSeq(niceResDto.getResSeq());
            paywayDto.setUserId(userSession.getUserId());


            if ("C".equals(niceResDto.getAuthType())) {
                paywayDto.setMyslAthnTypeCd("03"); //03:신용카드
            } else if ("M".equals(niceResDto.getAuthType())) {
                paywayDto.setMyslAthnTypeCd("01"); //01:SMS
            } else {
                returnCode = "05";
                message = "인증 방식 정보가 없습니다.";
                map.put("returnCode",returnCode);
                map.put("message",message);
                return map;
            }

            certValue= new String[]{"saveFarChgWayChgForm", "Y", contractNum
                                  , niceResDto.getDupInfo(), paywayDto.getBlBankCode(), EncryptUtil.ace256Enc(paywayDto.getBankAcctNo())};

        } else if ("C".equals(reqPayType) || "R".equals(reqPayType)) {
            certStep= 5 ;

            NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);

            //비정상 접근 확인
            if(niceResDto == null) {
                returnCode = "04";
                message = "본인인증 정보가 없습니다..";
                map.put("returnCode",returnCode);
                map.put("message",message);
                return map;
            }

            String cstmrNativeRrn01 = niceResDto.getBirthDate();
            if (cstmrNativeRrn01 != null && cstmrNativeRrn01.length() > 7) {
                cstmrNativeRrn01 = cstmrNativeRrn01.substring(2, 8);
            }
            paywayDto.setCstmrName(niceResDto.getName()) ;
            paywayDto.setCstmrNativeRrn01(cstmrNativeRrn01)   ;
            paywayDto.setReqSeq(niceResDto.getReqSeq());
            paywayDto.setResSeq(niceResDto.getResSeq());
            paywayDto.setUserId(userSession.getUserId());
            certKey= Arrays.copyOfRange(certKey, 0, 4);
            certValue= new String[]{"saveFarChgWayChgForm", "Y", contractNum, niceResDto.getDupInfo()};
        } else {
            // 스텝종료여부, 계약번호
            certKey= Arrays.copyOfRange(certKey, 0, 3);
            certValue= new String[]{"saveFarChgWayChgForm", "Y", contractNum};
        }

        if(certService.getStepCnt() < certStep){
            map.put("returnCode","STEP01");
            map.put("message", STEP_CNT_EXCEPTION);
            return map;
        }

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            map.put("returnCode","STEP02");
            map.put("message", vldReslt.get("RESULT_DESC"));
            return map;
        }

        // 요금 납부 변경
        map = paywayService.farChgWayChg(paywayDto);

        return map;
    }

    @RequestMapping(value =  {"/mypage/sendKakaSmsAjax.do"})
    @ResponseBody
    public Map<String, Object> sendKakaSms(HttpServletRequest request, Model model, @ModelAttribute PaywayDto paywayDto, HttpSession session){

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("01",F_BIND_EXCEPTION);
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        //정회원
        if(cntrList.size() <= 0){
            throw new McpCommonJsonException("02",F_BIND_EXCEPTION);
        }

        String ncn = StringUtil.NVL(paywayDto.getNcn(),"");
        if ( "".equals(ncn) ) {
            throw new McpCommonJsonException("03",F_BIND_EXCEPTION);
        }

        String ctn = "";
        String custId = "";
        String contractNum= "";
        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                custId = mcpUserCntrMngDto.getCustId();
                contractNum= mcpUserCntrMngDto.getContractNum();
                break;
            }
        }

        // ============ STEP START ============
        // 1. 최소 스텝 수 검증
        if(certService.getStepCnt() < 1){
            throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
        }

        // 2. 최종 데이터 검증: 스텝종료여부, 계약번호
        String[] certKey= {"urlType", "stepEndYn", "contractNum"};
        String[] certValue= {"sendPaywayInfo", "Y", contractNum};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
        }
        // ============ STEP END ============

        //SMS인증 여부 확인
        /*AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setPhoneNum(ctn);
        authSmsDto.setMenu("charge");
        authSmsDto.setCheck(true);

        SessionUtils.checkAuthSmsSession(authSmsDto);

        if(!authSmsDto.isResult()) {
            throw new McpCommonJsonException("04",NO_FAIL_SESSION_EXCEPTION);
        }*/

        Map<String,Object> map = new HashMap<String, Object>();
        paywayDto.setCtn(ctn);
        paywayDto.setCustId(custId);
        map = paywayService.smsFarChgWayChg(paywayDto);

        return map;
    }

    private ResponseSuccessDto getMessageBox(){
        ResponseSuccessDto mbox = new ResponseSuccessDto();
        mbox.setRedirectUrl("/mypage/updateForm.do");
        if("Y".equals(NmcpServiceUtils.isMobile())){
            mbox.setRedirectUrl("/m/mypage/updateForm.do");
        }
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
        return mbox;
    }

}
