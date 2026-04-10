package com.ktmmobile.mcp.mypage.controller;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.*;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.mplatform.vo.PaymentInfoVO;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.RealTimePayService;
import org.apache.commons.lang.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

/**
 * 마이페이지 > 요금조회 및 납부 > 즉시납부 > 요금납부
 */

@Controller
public class RealTimePayController {

    private static final Logger logger = LoggerFactory.getLogger(RealTimePayController.class);

    @Autowired
    MypageService mypageService;

    @Autowired
    RealTimePayService realTimePayService;

    @Autowired
    private FCommonSvc fCommonSvc;


    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    SmsSvc smsSvc;

    @Autowired
    CertService certService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    /**
     * 즉시납부 > 요금납부 > 미납요금조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = { "/mypage/unpaidChargeList.do", "/m/mypage/unpaidChargeList.do" })
    public String doUnpaidChargeList(HttpServletRequest request, Model model
            ,@ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String returnUrl = "/portal/mypage/unpaidChargeList";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/unpaidChargeList";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) {
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String customerType = mypageService.selectCustomerType(searchVO.getCustId());

        if("G".equals(customerType) || "B".equals(customerType)) {
            customerType = "Y";
        }

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

        //인증받고서 처음페이지 왔으니깐 다시 인증
        SessionUtils.saveNiceRes(null);
        model.addAttribute("customerType", customerType); // 회선정보
        model.addAttribute("cntrList", cntrList); // 회선정보
        if(Optional.ofNullable(userSession.getBirthday()).isPresent()) {
            model.addAttribute("birthday", userSession.getBirthday()); // 로그인한 사용자
        }
        model.addAttribute("searchVO", searchVO); // 회선정보
        return returnUrl;

    }

    /**
     * 청구년월 미납,요금조회
     * x67 -> x92 교체
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = { "/mypage/unpaidChargeListAjax.do", "/m/mypage/unpaidChargeListAjax.do" })
    @ResponseBody
    public JsonReturnDto  unpaidChargeListAjax(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) {
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        String rtnUrl = "/main.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnUrl = "/m/main.do";
        }

        // ============ STEP START ============
        if(StringUtils.isBlank(searchVO.getContractNum())){
            throw new McpCommonException(F_BIND_EXCEPTION, rtnUrl);
        }

        // 계약번호
        String[] certKey= {"urlType", "contractNum"};
        String[] certValue= {"getUnpaidInfo", searchVO.getContractNum()};
        certService.vdlCertInfo("C", certKey, certValue);
        // ============ STEP END ============

        PaymentInfoVO farPaymentInfoVO = new PaymentInfoVO();
        JsonReturnDto jsonReturnDto = new JsonReturnDto();
        Object json = null;
        String customerType = mypageService.selectCustomerType(searchVO.getCustId());

        if("G".equals(customerType) || "B".equals(customerType)) {
            customerType = "Y";
            rtnMap.put("customerType",customerType);
            jsonReturnDto.setReturnCode("01");
            jsonReturnDto.setMessage("개인고객만 납부 가능합니다.");
        }

        if(!"Y".equals(customerType)) {
            //x67->x92로 교체 미납조회
            farPaymentInfoVO =  realTimePayService.moscCurrMthNpayInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());

            if(!farPaymentInfoVO.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION,rtnUrl);
            }

            if(!farPaymentInfoVO.isSuccess()) {
                jsonReturnDto.setReturnCode("E");
                jsonReturnDto.setMessage(farPaymentInfoVO.getSvcMsg());
            } else {
                rtnMap.put("totNpayAmt", farPaymentInfoVO.getTotNpayAmt());
                rtnMap.put("currMthNpayAmt", farPaymentInfoVO.getCurrMthNpayAmt());
                rtnMap.put("payTgtAmt", farPaymentInfoVO.getPayTgtAmt());
                jsonReturnDto.setMessage(farPaymentInfoVO.getSvcMsg());
            }

            jsonReturnDto.setReturnCode("S");
            jsonReturnDto.setMessage("");
        }

        jsonReturnDto.setResultMap(rtnMap);
        return jsonReturnDto;

    }

    /**
     * 즉시납부 신청 VIEW
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param moscPymnReqDto
     * @param searchVO
     * @param contractNum
     * @return
     */

    @RequestMapping(value = { "/mypage/realTimePaymentView.do", "/m/mypage/realTimePaymentView.do" })
    public String doRealTimePaymentView(HttpServletRequest request, Model model
            ,@ModelAttribute("moscPymnReqDto") MoscPymnReqDto moscPymnReqDto
            ,@ModelAttribute("searchVO") MyPageSearchDto searchVO
            ,@RequestParam(value = "contractNum", required = false) String contractNum){

        String returnUrl = "/portal/mypage/realTimePaymentView";
        String rtnUrl = "/mypage/unpaidChargeList.do";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/realTimePaymentView";
            rtnUrl = "/m/mypage/unpaidChargeList.do";
        }

        if(contractNum == null) {
            return "redirect:"+rtnUrl;
        }

        // ============ STEP START ============
        // 계약번호
        String[] certKey= {"urlType", "contractNum"};
        String[] certValue= {"chkMemberAuth", contractNum};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonException(vldReslt.get("RESULT_DESC"), rtnUrl);
        }
        // ============ STEP END ============

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if ( userSession != null) { // 취약성 364
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        Calendar today = Calendar.getInstance();
        Integer nowY = today.get(Calendar.YEAR);

        String cstmrNativeRrn = userSession.getBirthday();



        if(!"LOCAL".equals(serverName)) {
             // 본인인증 확인
             NiceResDto sessNiceRes =  SessionUtils.getNiceResCookieBean() ;
               if (sessNiceRes == null) {
                 throw new McpCommonException(NICE_CERT_EXCEPTION);
             }

             if (!sessNiceRes.getBirthDate().equals(cstmrNativeRrn)) {
                   throw new McpCommonException(NICE_CERT_EXCEPTION);
             }
        }

        //  계좌이체 오픈 여부 payTransfer

        String isPayTransfer= NmcpServiceUtils.getCodeNm("Constant","isPayTransfer");
        if (isPayTransfer == null || isPayTransfer.equals("") ) {
            isPayTransfer = "N" ;
        }

        //테스트 아이디
        if (!"Y".equals(isPayTransfer)) {
            String isExceptionId =NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSession.getUserId());
            if (isExceptionId != null && "Y".equals(isExceptionId) ) {
                isPayTransfer = "Y" ;
            }
        }



        model.addAttribute("payMentMoney", moscPymnReqDto.getPayMentMoney());
        model.addAttribute("contractNum", contractNum);
        model.addAttribute("nowY", nowY);
        model.addAttribute("isPayTransfer", isPayTransfer);
        return returnUrl;
    }

    /**
     * 납부신청 AJAX
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param moscPymnReqDto
     * @param session
     * @param contractNum
     * @return
     */

    @RequestMapping(value = { "/mypage/insertRealTimePaymentAjax.do", "/m/mypage/insertRealTimePaymentAjax.do" })
    @ResponseBody
    public HashMap<String, Object> doInsertRealTimePaymentAjax(HttpServletRequest request, Model model
            ,@ModelAttribute("moscPymnReqDto") MoscPymnReqDto moscPymnReqDto
            , HttpSession session
            ,@RequestParam(value = "contractNum", required = true) String contractNum){

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String linkUrl = ""; //간편결제일때 url
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        // ======= STEP START =======
        //본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null || StringUtils.isEmpty(sessNiceRes.getDupInfo())) {
            throw new McpCommonJsonException("AUTH01", NICE_CERT_EXCEPTION_INSR);
        }

        // 최소 스텝 수 확인
        if(certService.getStepCnt() < 6 ){
            throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
        }

        // 최종 데이터 확인: step종료 여부, 계약번호, DI
        String[] certKey= {"urlType", "stepEndYn", "contractNum", "dupInfo"};
        String[] certValue= {"saveRealTimePayment", "Y", contractNum, sessNiceRes.getDupInfo()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
        }
        // ======= STEP END =======

        try {

            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            if (cntrList != null && cntrList.size() > 0) {
                for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {

                    if (contractNum.equals(mcpUserCntrMngDto.getContractNum())) {
                        moscPymnReqDto.setCustId(mcpUserCntrMngDto.getCustId());
                        moscPymnReqDto.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
                        moscPymnReqDto.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
                        break;
                    }
                }
            }

            moscPymnReqDto.setPayMentMoney(moscPymnReqDto.getPayMentMoney().replaceAll(",", ""));
            linkUrl = realTimePayService.moscCurrMthNpayTreat(moscPymnReqDto);
            //x68 url = realTimePayService.moscPymnTreat(moscPymnReqDto);
            session.setAttribute("blMethod", moscPymnReqDto.getBlMethod());


            String blMethod = moscPymnReqDto.getBlMethod();

            if ("P".equals(blMethod)) {
                //P: 간편결제

                //간편결제 오픈 여부
                String isSamplePayOpen = NmcpServiceUtils.getCodeNm("Constant","isSamplePayOpen");
                if (isSamplePayOpen == null ) {
                    isSamplePayOpen = "N" ;
                }

                //테스트 아이디
                if (!"Y".equals(isSamplePayOpen)) {
                    String isExceptionId = NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSession.getUserId());
                    if (isExceptionId != null && "Y".equals(isExceptionId) ) {
                        isSamplePayOpen = "Y" ;
                    }
                }

                if ("Y".equals(isSamplePayOpen)) {
                    //SMS발송 처리
                    MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SAMPLE_PAY_TEMPLATE_ID);

                    if (mspSmsTemplateMstDto != null) {
                        String rmnyChIdNm = NmcpServiceUtils.getCodeNm(Constants.RMNY_CHID_OBJ_LIST , moscPymnReqDto.getRmnyChId());
                        if (rmnyChIdNm != null && !"".equals(rmnyChIdNm) ) {
                            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("{rmnyChIdNm}", rmnyChIdNm));
                            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("{rmnyChIdNm}", rmnyChIdNm));
                            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("{rmnyChIdNm}", rmnyChIdNm));
                            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("{linkUrl}", linkUrl ));
                            //smsSvc.sendMsgQueue( mspSmsTemplateMstDto.getSubject(), moscPymnReqDto.getCtn(), mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
                            smsSvc.sendMsgQueue( mspSmsTemplateMstDto.getSubject(), moscPymnReqDto.getCtn(), mspSmsTemplateMstDto.getText(),
                                    mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY
                                    ,String.valueOf(Constants.SAMPLE_PAY_TEMPLATE_ID),"SYSTEM");
                        }
                    }
                }

            }

        } catch(SelfServiceException e) {
            String resultCode = e.getResultCode();
            String strMsg = "";
            //error 메세지 치환 처리
            if (resultCode != null  && !"".equals(resultCode)) {
                NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("errorCodeMsg", resultCode);
                if (nmcpCdDtlDto != null && !"".equals(nmcpCdDtlDto.getDtlCdDesc())) {
                    strMsg = nmcpCdDtlDto.getDtlCdDesc();
                } else {
                    strMsg = e.getMessageNe();
                }
            } else {
                strMsg = e.getMessageNe();
            }
            throw new McpCommonJsonException("0001", strMsg);
        } catch(DataAccessException e) {
            throw new McpCommonJsonException("9998", COMMON_EXCEPTION);
        }  catch (Exception e) {
            throw new McpCommonJsonException("9999", COMMON_EXCEPTION);
        }

        rtnMap.put("RESULT_CODE", "S");
        rtnMap.put("url", linkUrl);
        return rtnMap;
    }


    /**
     * 납부완료 VIEW
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param session
     * @param contractNum
     * @param rmnyChId
     * @param payMentMoney
     * @return
     */

    @RequestMapping(value = { "/mypage/realTimePaymentCompleteView.do", "/m/mypage/realTimePaymentCompleteView.do" })
    public String doRealTimePaymentCompleteView(HttpServletRequest request, Model model
            ,@ModelAttribute("searchVO") MyPageSearchDto searchVO
            , HttpSession session
            ,@RequestParam(value = "contractNum", required = false) String contractNum
            ,@RequestParam(value = "rmnyChId", required = false) String rmnyChId
            ,@RequestParam(value = "payMentMoney", required = false) String payMentMoney){

        String returnUrl = "/portal/mypage/realTimePaymentCompleteView";
        String rtnUrl ="/mypage/unpaidChargeList.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/realTimePaymentCompleteView";
            rtnUrl ="/m/mypage/unpaidChargeList.do";
        }

        String blMethod = (String) session.getAttribute("blMethod");

        if(blMethod == null) {
            return "redirect:"+rtnUrl;
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 362
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        session.removeAttribute("blMethod");
        model.addAttribute("contractNum", contractNum);
        model.addAttribute("rmnyChId", rmnyChId);
        model.addAttribute("payMentMoney", payMentMoney);

        return returnUrl;

    }

     private ResponseSuccessDto getMessageBox() {
        ResponseSuccessDto mbox = new ResponseSuccessDto();
        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }
        mbox.setRedirectUrl(url);
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
        return mbox;
    }

}
