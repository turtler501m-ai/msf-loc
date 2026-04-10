package com.ktmmobile.mcp.mypage.controller;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.TIME_OVERLAP_EXCEPTION;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.vo.MoscBillReSendChgOutVO;
import com.ktmmobile.mcp.common.mplatform.vo.MoscBillSendInfoOutVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpBilPrintInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.BillWayChgDto;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MyinfoService;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.PaywayService;


@Controller
public class BillWayChgController {

    private static Logger logger = LoggerFactory.getLogger(BillWayChgController.class);
    @Autowired
    private MypageService mypageService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private MyinfoService myinfoService;

    @Autowired
    private PaywayService paywayService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;


    /**
     * 설명 : 명세서 정보변경 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */
    @RequestMapping(value= {"/m/mypage/billWayChgView.do", "/mypage/billWayChgView.do"})
    public String billWayChgView(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        String returnUrl = "portal/mypage/billWayChgView";
        checkOverlapDto.setRedirectUrl("/mypage/billWayChgView.do");
        checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "mobile/mypage/billWayChgView";
            checkOverlapDto.setRedirectUrl("/m/mypage/billWayChgView.do");
        }

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            //checkOverlapDto = getMessageBox();
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
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

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("휴대폰번호,명세서정보");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        }

        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        return returnUrl;
    }

    /**
     *
     * 설명 : 명세서 정보 조회 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param searchVO
     * @return
     */
    @RequestMapping(value={"/m/mypage/myBillInfoViewAjax.do", "/mypage/myBillInfoViewAjax.do"})
    @ResponseBody
    public HashMap<String, Object> chargeView05(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "ncn", required = true) String ncn1) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String returnCode = "00";
        String message = "";
        String ncn = ncn1;
        String custId = "";
        String ctn = "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(cntrList !=null && !cntrList.isEmpty()) {
            for(McpUserCntrMngDto dto : cntrList) {
                String svcCntrNo = StringUtil.NVL(dto.getSvcCntrNo(),"");
                if(svcCntrNo.equals(ncn)) {
                    custId = dto.getCustId();
                    ctn = dto.getCntrMobileNo();
                    break;
                }
            }
        } else {
            rtnMap.put("returnCode", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        if("".equals(custId) || "".equals(ctn)) {
            rtnMap.put("returnCode", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        try {

            MpPerMyktfInfoVO perMyktfInfo = myinfoService.perMyktfInfo(ncn, ctn, custId);
            String addr = "";
            if(perMyktfInfo !=null) {
                addr = perMyktfInfo.getAddr();
            }
            rtnMap.put("addr", addr);

            MpFarChangewayInfoVO changeInfo = myinfoService.farChangewayInfo(ncn, ctn, custId);
            String payMethod = "";
            String blAddr = "";
            String billCycleDueDay = "";
            String blBankAcctNo = "";
            String prevCardNo = "";
            String prevExpirDt = "";
            if(changeInfo !=null) {
                payMethod = changeInfo.getPayMethod();
                blAddr = changeInfo.getBlAddr();
                billCycleDueDay = changeInfo.getBillCycleDueDay();
                blBankAcctNo = changeInfo.getBlBankAcctNo();
                prevCardNo = changeInfo.getPrevCardNo();
                prevExpirDt = changeInfo.getPrevExpirDt();
            }
            rtnMap.put("payMethod", payMethod);
            rtnMap.put("blAddr", blAddr);
            rtnMap.put("billCycleDueDay", billCycleDueDay);
            rtnMap.put("blBankAcctNo", blBankAcctNo);
            rtnMap.put("prevCardNo", prevCardNo);
            rtnMap.put("prevExpirDt", prevExpirDt);

            MpMoscBilEmailInfoInVO moscBilEmailInfo = myinfoService.kosMoscBillInfo(ncn, ctn, custId);
            String billTypeCd = "";
            String email = "";
            String maskedEmail= "";
            String ctn1 = "";
            if(moscBilEmailInfo !=null) {
                billTypeCd = moscBilEmailInfo.getBillTypeCd();
                email = moscBilEmailInfo.getEmail();
                maskedEmail = moscBilEmailInfo.getMaskedEmail();
                ctn1 = moscBilEmailInfo.getCtn();
            }
            rtnMap.put("billTypeCd", billTypeCd);
            rtnMap.put("email", email);
            rtnMap.put("maskedEmail", maskedEmail);
            rtnMap.put("ctn", ctn1);

            MpBilPrintInfoVO bilPrintInfo = paywayService.bilPrintInfo(ncn, ctn, custId);
            String zipCode = "";
            String pAddr = "";
            String sAddr = "";
            if(bilPrintInfo !=null) {
                zipCode = bilPrintInfo.getZipCode();
                pAddr = bilPrintInfo.getpAddr();
                sAddr = bilPrintInfo.getsAddr();
            }
            rtnMap.put("zipCode", zipCode);
            rtnMap.put("pAddr", pAddr);
            rtnMap.put("sAddr", "");  //웹취약점 조치

        } catch (SelfServiceException e) {
            returnCode = "E";
            message = getErrMsg(e.getMessage());
        }  catch (Exception e) {
            returnCode = "E";
            message = getErrMsg(e.getMessage());
        }

        rtnMap.put("returnCode", returnCode);
        rtnMap.put("message", message);
        return rtnMap;
    }


    /**
     *
     * 설명 : 명세서 정보 변경 Ajax(X50)
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param email
     * @param securMailYn
     * @param ecRcvAgreYn
     * @param billTypeCd
     * @param billAdInfo
     * @param zip
     * @param addr1
     * @param addr2
     * @return
     */
    @RequestMapping(value = {"/mypage/billChgAjax.do", "/m/mypage/billChgAjax.do"},method = {RequestMethod.POST})
    @ResponseBody
    public HashMap<String, Object> billChgAjax(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO, String email,
            String securMailYn, String ecRcvAgreYn, String billTypeCd,
            String billAdInfo, String zip, String addr1, String addr2,@RequestParam(value = "ncn", required = true) String ncn1) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        String returnCode = "00";
        String message = "";
        String ncn = ncn1;
        String custId = "";
        String ctn = "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            rtnMap.put("returnCode", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        if(cntrList !=null && !cntrList.isEmpty()) {
            for(McpUserCntrMngDto dto : cntrList) {
                String svcCntrNo = StringUtil.NVL(dto.getSvcCntrNo(),"");
                if(svcCntrNo.equals(ncn)) {
                    custId = dto.getCustId();
                    ctn = searchVO.getCtn();
                    break;
                }
            }
        } else {
            rtnMap.put("returnCode", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        if("".equals(custId) || "".equals(ctn)) {
            rtnMap.put("returnCode", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        try {
            String billAdInfoTemp = billAdInfo;
            String status = "";				//1=신청, 9=변경, 0=해지
            String sendGubun = "";			//1=발송, 2=발송제외, 3=해지
            String securMailYnTmp = "";	//보안메일
            String ecRcvAgreYnTmp = "";//이벤트 수신 동의 여부
            String billTypeCdTmp = ""; 	//1 이메일, 2 MMS
            boolean serviceFlag = false;

            if ("MB".equals(billTypeCd)) { //MMS명세서
                status = "9";
                sendGubun = "1";
                securMailYnTmp = "Y";
                ecRcvAgreYnTmp = "N";
                billTypeCdTmp = "2";
                serviceFlag = true;
                billAdInfoTemp = ctn;

            } else if ("CB".equals(billTypeCd))  { //이메일 명세서
                status = "9";
                sendGubun = "1";
                securMailYnTmp = "Y";
                ecRcvAgreYnTmp = "N";
                billTypeCdTmp = "1";
                serviceFlag = true;

            } else if("LX".equals(billTypeCd)) {
                //현재 명세서 유형 조회
                MpMoscBilEmailInfoInVO moscBilEmailInfo = mPlatFormService.kosMoscBillInfo(ncn, ctn, custId);
                if (!moscBilEmailInfo.getBillTypeCd().equals(billTypeCd)) { //현재 명세서 유형이 우편이 아닐경우 X50해지
                    status = "0";
                    sendGubun = "3";
                    securMailYnTmp = "Y";
                    ecRcvAgreYnTmp = "Y";
                    if ("CB".equals(moscBilEmailInfo.getBillTypeCd())) {
                        billTypeCdTmp = "1";
                    } else if ("MB".equals(moscBilEmailInfo.getBillTypeCd())) {
                        billTypeCdTmp = "2";
                    }
                    serviceFlag = true;
                }
            }

            if (serviceFlag) {
                mPlatFormService.kosMoscBillChg(ncn, ctn, custId, billTypeCdTmp, status, billAdInfoTemp, securMailYnTmp, ecRcvAgreYnTmp, sendGubun);
            }
            //우편 명세서 신청, 주소변경
            if("LX".equals(billTypeCd)) {
                String bilCtnDisplay = "Y";
                mPlatFormService.perAddrChg(ncn, ctn, custId, zip, addr1, addr2, bilCtnDisplay);
            }

        } catch (SelfServiceException e) {
            returnCode = "E";
            message = getErrMsg(e.getMessage());
        } catch (SocketTimeoutException e){
            returnCode = "E";
            message = getErrMsg(e.getMessage());
        }
        rtnMap.put("returnCode", returnCode);
        rtnMap.put("message", message);
        return rtnMap;
    }

    /**
     * 설명 : 명세서 재발송
     * @Author KIM
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */
    @RequestMapping(value= {"/m/mypage/billWayReSend.do", "/mypage/billWayReSend.do"})
    public String billWayReSend(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        String returnUrl = "/portal/mypage/billWayReSend";
        checkOverlapDto.setRedirectUrl("/mypage/billWayReSend.do");
        checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/billWayReSend";
            checkOverlapDto.setRedirectUrl("/m/mypage/billWayReSend.do");
        }

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            //checkOverlapDto = getMessageBox();
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
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

        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String ctn = searchVO.getCtn();
        MoscBillSendInfoOutVO moscBillSendInfoOutVO = null;
        List<MoscBillSendInfoOutVO.MoscBillSendInfoOutDTO> outSendInfoListlDto = new ArrayList<MoscBillSendInfoOutVO.MoscBillSendInfoOutDTO>();
        try {
            moscBillSendInfoOutVO = mPlatFormService.kosMoscBillSendInfo(ncn, ctn, custId);
            if(moscBillSendInfoOutVO !=null) {
                outSendInfoListlDto = moscBillSendInfoOutVO.getMoscBillSendInfoOutDTO();
                if(outSendInfoListlDto !=null && outSendInfoListlDto.size() > 6) {
                    outSendInfoListlDto = outSendInfoListlDto.subList(0, 6);
                }
            }
        } catch (SelfServiceException e) {
            logger.info("SelfServiceException error");
        }  catch(Exception e) {
            logger.info("error");
        }

        logger.info("outSendInfoListlDto==>"+ObjectUtils.convertObjectToString(outSendInfoListlDto));

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        }

        model.addAttribute("outSendInfoListlDto", outSendInfoListlDto);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        return returnUrl;
    }

    /**
     * 설명 : 명세서 재발송
     * @Author KIM
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */
    @RequestMapping(value= {"/m/mypage/billWayReSendPop.do", "/mypage/billWayReSendPop.do"})
    public String billWayReSendPop(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            @RequestParam(value = "thisMonth", required = true) String thisMonth1) {

        String returnUrl = "/portal/mypage/billWayReSendPop";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/billWayReSendPop";
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

        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String mobileNo = StringMakerUtil.getPhoneNum(searchVO.getCtn());
        String thisMonth = thisMonth1;

        model.addAttribute("ncn", ncn);
        model.addAttribute("mobileNo", mobileNo);
        model.addAttribute("thisMonth", thisMonth);
        return returnUrl;
    }


    @RequestMapping(value="/mypage/billWayReSendAjax.do",method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> billWayReSendAjax(HttpServletRequest request, Model model,HttpSession session, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            @ModelAttribute BillWayChgDto billWayChgDto )  {

        Map<String, Object> rtnMap = new HashMap<String, Object>();

        String message = "";
        String returnCode = "";
        String billAdInfo = "";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "01";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            returnCode = "04";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        //정회원
        if(cntrList.size() <= 0){
            returnCode = "02";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        String ncn = StringUtil.NVL(searchVO.getNcn(),"");
        if ( "".equals(ncn) ) {
            returnCode = "03";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        String ctn = "";
        String custId = "";
        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                ctn = searchVO.getCtn();
                custId = mcpUserCntrMngDto.getCustId();
                break;
            }
        }

        MoscBillReSendChgOutVO moscBillReSendChgOutVO = new MoscBillReSendChgOutVO();
        try {

            String thisMonth = StringUtil.NVL(billWayChgDto.getThisMonth(),"");
            String billTypeCd = StringUtil.NVL(billWayChgDto.getBillTypeCd(),"");

            if(!StringUtils.isEmpty(ncn) && !StringUtils.isEmpty(ctn) && !StringUtils.isEmpty(custId) && !StringUtils.isEmpty(thisMonth) && !StringUtils.isEmpty(billTypeCd)) {
                String year = "";
                String month = "";
                if(thisMonth.length() > 5) {
                    year = thisMonth.substring(0, 4);
                    month = thisMonth.substring(4, 6);
                }
                if("1".equals(billTypeCd)) {
                    billAdInfo = billWayChgDto.getEmail();
                } else {
                    billAdInfo = ctn;
                }
                moscBillReSendChgOutVO = mPlatFormService.kosMoscBillReSendChg(ncn, ctn, custId, year, month, billTypeCd, billAdInfo);

                returnCode = "00";
                message = "";
            } else {
                returnCode = "06";
                message = "파라미터가 비정상입니다.";
            }

        } catch (SelfServiceException e) {
            returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(e.getMessage());
        } catch (SocketTimeoutException e){
            returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
        } finally {
            // 이력저장 MCP_BILLWAY_RESEND
            try {
                String globalNo = moscBillReSendChgOutVO.getGlobalNo();
                String userId = userSession.getUserId();
                String userNm = userSession.getName();
                String billTypeCd = StringUtil.NVL(billWayChgDto.getBillTypeCd(),"");
                String successYn = "Y";
                if(!"00".equals(returnCode)) {
                    successYn = "N";
                }
                String errMsg = message;
                BillWayChgDto resDto = mypageService.getMspData(ncn);
                if(resDto !=null) {
                    resDto.setGlobalNo(globalNo);
                    resDto.setUserId(userId);
                    resDto.setUserNm(userNm);
                    resDto.setBillTypeCd(billTypeCd);
                    resDto.setBillAdInfo(billAdInfo);
                    resDto.setSuccessYn(successYn);
                    resDto.setErrMsg(errMsg);
                    mypageService.insertMcpBillwayResend(resDto);
                }
            } catch(DataAccessException e) {
                logger.info("insertMcpBillwayResend DataAccessException error");
            } catch(Exception e) {
                logger.info("insertMcpBillwayResend error");
            }
        }

        rtnMap.put("returnCode",returnCode);
        rtnMap.put("message",message);

        return rtnMap;
    }

    private ResponseSuccessDto getMessageBox(){
        ResponseSuccessDto mbox = new ResponseSuccessDto();
        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }
        mbox.setRedirectUrl(url);
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");

        return mbox;
    }

    private String getErrMsg(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        if(arg.length > 1) {
            result = arg[1];
        } else {
            result = arg[0];
        }
        return result;
    }


    private String getErrCd(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        result = arg[0]; //N:성공, S:시스템에서, E:Biz 에러
        return result;
    }

}
