package com.ktmmobile.msf.form.servicechange.controller;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.constants.Constants.CSTMR_TYPE_NM;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.STEP_CNT_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.STEP_INFO_NULL_EXCEPTION;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.form.ownerchange.dto.MyNameChgReqDto;
import com.ktmmobile.msf.form.ownerchange.service.MyNameChgService;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.service.MsfCustRequestScanService;
import com.ktmmobile.msf.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.system.cert.dto.CertDto;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.system.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.EncryptUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringMakerUtil;
import com.ktmmobile.msf.system.faceauth.service.FathService;

@Controller
public class MyNameChgController {

    private static final Logger logger = LoggerFactory.getLogger(MyNameChgController.class);


    @Autowired
    private MsfMypageSvc mypageService;

    @Autowired
    private MyNameChgService myNameChgService;

    @Autowired
    private MsfCustRequestScanService custRequestScanService;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private CertService certService;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    @Autowired
    private FCommonSvc fCommonSvc;
    
    @Autowired
    private FathService fathService;

    /**
     * 설명 : 명의변경 화면
     */
    @RequestMapping(value = {"/mypage/myNameChg.do", "/m/mypage/myNameChg.do"}  )
    public String myNameChg(ModelMap model , HttpServletRequest request
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO){
        //안면인증 세션 초기화
        SessionUtils.initializeFathSession();

        String jspPageName = "/portal/mypage/myNameChg";
        String thisPageName ="/mypage/myNameChg.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/mobile/mypage/myNameChg";
            thisPageName ="/m/mypage/myNameChg.do";
        }

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();  //중복요청 체크
        checkOverlapDto.setRedirectUrl(thisPageName);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        List<McpUserCntrMngDto> cntrList = myNameChgService.selectCntrListNmChg(userSession.getUserId(), null);
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
            maskingDto.setUnmaskingInfo("휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        }


        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        model.addAttribute("phoneNum", searchVO.getCtn());
        model.addAttribute("custId", cntrList.get(0).getCustId());
        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);

        NmcpCdDtlDto jehuProdInfo = fCommonSvc.getJehuProdInfo(searchVO.getSoc());
        model.addAttribute("jehuProdType", jehuProdInfo.getDtlCd());
        model.addAttribute("jehuProdName", jehuProdInfo.getDtlCdNm());

        return jspPageName;
    }

    /**
     * 설명     : 명의변경 신청
     */
    @RequestMapping(value = "/mypage/myNameChgRequest.do")
    @ResponseBody
    public Map<String, Object> myNameChgRequest(MyNameChgReqDto myNameChgReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonException(NO_FRONT_SESSION_EXCEPTION);
        }
        myNameChgReqDto.setUserid(userSession.getUserId());
        myNameChgReqDto.setCustomerId(userSession.getCustomerId());

        // 명의변경 최종 STEP 검사
        certAuthChgRequest(myNameChgReqDto);

        //2025-12-19 MC0 추가 연동 값
        myNameChgReqDto.setMcnStatRsnCd("RCMCMCN"); // RCMCMCN 명의변경-실사용자를 위한 명변으로 고정
        myNameChgReqDto.setUsimSuccYn("Y");         // USIM 승계로 고정
        myNameChgReqDto.setIccId(null);

        try {
            String result = myNameChgService.myNameChgRequest(myNameChgReqDto);

            if ("SUCCESS".equals(result)) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            } else {
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("ERROR_NE_MSG","시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
            }

            try {
                //5. SCAN 서버에 서식지 데이터 생성 및 전송
                custRequestScanService.prodSendScan(Long.parseLong(myNameChgReqDto.getCustReqSeq()), myNameChgReqDto.getUserid(), "NC");

                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CUST_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + myNameChgReqDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(myNameChgReqDto.getCustReqSeq());
                mcpIpStatisticDto.setTrtmRsltSmst("SUCCESS");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            } catch(McpCommonJsonException e) {
                rtnMap.put("RESULT_CODE", "FAIL");
                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CUST_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" +myNameChgReqDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(myNameChgReqDto.getCustReqSeq());
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            }catch (Exception e) {        //예외 전환 처리
                rtnMap.put("RESULT_CODE", "FAIL");
                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CUST_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" +myNameChgReqDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(myNameChgReqDto.getCustReqSeq());
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            }
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }
        return rtnMap;
    }


    /**
     * 설명     : 양도인 신청가능여부 체크
     */
    @RequestMapping(value = "/mypage/grantorReqChk.do")
    @ResponseBody
    public Map<String, Object> grantorReqChk(MyNameChgReqDto myNameChgReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonException(NO_FRONT_SESSION_EXCEPTION);
        }
        myNameChgReqDto.setUserid(userSession.getUserId());

        // ================ STEP START ================
        // 양도인 구분값, 계약번호
        String[] certKey= {"urlType", "ncType", "contractNum"};
        String[] certValue= {"chkGrantorForm", "0", myNameChgReqDto.getContractNum()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
        }
        // ================ STEP END ================

        try {
            String result = myNameChgService.grantorReqChk(myNameChgReqDto);
            if ("SUCCESS".equals(result)) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            } else if ("STOP".equals(result)) {
                rtnMap.put("RESULT_CODE", "0002");
            } else if ("NONPAY".equals(result)) {
                rtnMap.put("RESULT_CODE", "0003");
            } else if ("LESSNINETY".equals(result)) {
                rtnMap.put("RESULT_CODE", "0004");
            } else if ("ERROR".equals(result)) {
                rtnMap.put("RESULT_CODE", "0005");
            } else {
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("ERROR_NE_MSG","시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
            }
        } catch (RestClientException e) {
            logger.error(e.getMessage());
        }   catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }
        return rtnMap;
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

    /**
     * 설명  : 명의변경 신청 전, 사용자 입력값 비교 로직 추가
     * */
    private void certAuthChgRequest(MyNameChgReqDto myNameChgReqDto) {

        String[] certKey= null;
        String[] certValue= null;
        Map<String,String> vldReslt= null;
        int certStep= 8;

        // 1. 화면에서 넘어온 개통관련 정보 비교
        if(StringUtils.isBlank(myNameChgReqDto.getContractNum())){
            throw new McpCommonJsonException("AUTH01", STEP_INFO_NULL_EXCEPTION);
        }

        List<McpUserCntrMngDto> cntrList = myNameChgService.selectCntrListNmChg(myNameChgReqDto.getUserid(), myNameChgReqDto.getContractNum());
        if(cntrList == null|| cntrList.size() == 0) throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);

        McpUserCntrMngDto cntrMngDto= cntrList.get(0);
        myNameChgReqDto.setMobileNo(cntrMngDto.getUnSvcNo());
        if(!cntrMngDto.getUnSvcNo().equals(myNameChgReqDto.getMobileNo())
           || !cntrMngDto.getSoc().equals(myNameChgReqDto.getSoc())
           || !cntrMngDto.getCstmrType().equals(myNameChgReqDto.getGrCstmrType())
           || !"I".equals(cntrMngDto.getCustomerType())
           || (StringUtils.isEmpty(myNameChgReqDto.getGrCstmrType()) && "I".equals(cntrMngDto.getCustomerType()))){
            throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        }

        // 계좌인증 실패로인해 신용카드 인증으로 진행한 경우, 계좌인증 관련 스텝 초기화
        if("Y".equals(myNameChgReqDto.getReqInfoChgYn()) && !"D".equals(myNameChgReqDto.getReqPayType())){
            if(0 < certService.getModuTypeStepCnt("account", "1")){
                CertDto certDto = new CertDto();
                certDto.setModuType("account");
                certDto.setCompType("G");
                certDto.setNcType("1");
                certService.getCertInfo(certDto);
            }
        }

        // 2. STEP 체크
        // 2-1. 양도인 정보 체크
        // 양도인 구분값, 이름, 생년월일, 계약번호, 본인인증유형, reqSeq, resSeq
        String ncType = "0"; // 양도인
        String name = myNameChgReqDto.getGrCstmrName();
        String birthDate = myNameChgReqDto.getGrCstmrNativeRrn();
        String contractNum = myNameChgReqDto.getContractNum();
        String authType = myNameChgReqDto.getGrOnlineAuthType();
        String reqSeq = myNameChgReqDto.getGrReqSeq();
        String resSeq = myNameChgReqDto.getGrResSeq();
        if (CSTMR_TYPE_NM.equals(myNameChgReqDto.getGrCstmrType())) {
            ncType = "2"; // 양도인 법정대리인
            name = myNameChgReqDto.getGrMinorAgentName();
            birthDate = myNameChgReqDto.getGrMinorAgentRrn();
        }
        certKey= new String[]{"urlType", "ncType", "name", "birthDate", "contractNum", "authType", "reqSeq", "resSeq"};
        certValue= new String[]{"saveGrantorForm", ncType, name, birthDate, contractNum, authType, reqSeq, resSeq};

        vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
        }

        // 2-2. 양수인 정보 체크
        // step종료 여부, 양수인 구분값, 이름, 생년월일, 본인인증유형, reqSeq, resSeq, 계좌번호, 은행코드
        ncType = "1"; // 양수인
        name = myNameChgReqDto.getCstmrName();
        birthDate = myNameChgReqDto.getCstmrNativeRrn();
        authType = myNameChgReqDto.getOnlineAuthType();
        reqSeq = myNameChgReqDto.getReqSeq();
        resSeq = myNameChgReqDto.getResSeq();
        String reqAccountNumber = myNameChgReqDto.getReqAccountNumber();
        String reqBank = myNameChgReqDto.getReqBank();
        if (CSTMR_TYPE_NM.equals(myNameChgReqDto.getCstmrType())) {
            ncType = "3"; // 양수인 법정대리인
            name = myNameChgReqDto.getMinorAgentName();
            birthDate = myNameChgReqDto.getMinorAgentRrn();
        }
        certKey= new String[]{"urlType", "stepEndYn", "ncType", "name", "birthDate", "authType", "reqSeq", "resSeq", "reqAccountNumber", "reqBank"};
        certValue= new String[]{"saveAssigneeForm", "Y", ncType, name, birthDate, authType, reqSeq, resSeq, reqAccountNumber, reqBank};

        if("Y".equals(myNameChgReqDto.getReqInfoChgYn())){
            if("D".equals(myNameChgReqDto.getReqPayType())){
                certStep+= 2;  // 10개
            }else{
                // step종료 여부, 양수인 구분값, 이름, 생년월일, 본인인증유형, reqSeq, resSeq
                certKey= Arrays.copyOfRange(certKey, 0, 8);
            }
        }else{
            // step종료 여부, 양수인 구분값, 이름, 생년월일, 본인인증유형, reqSeq, resSeq
            certKey= Arrays.copyOfRange(certKey, 0, 8);
        }

        // 2-2. 양수인 정보 체크 전 최소 스텝 수 체크
        if(certService.getStepCnt() < certStep){
            throw new McpCommonJsonException("STEP02", STEP_CNT_EXCEPTION);
        }

        // 양수인 정보 검증
        vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP03", vldReslt.get("RESULT_DESC"));
        }

        // 3. 필수값 세팅
        myNameChgReqDto.setGrOnlineAuthInfo("ReqNo:" + myNameChgReqDto.getGrReqSeq() + ", ResNo:" + myNameChgReqDto.getGrResSeq());
        myNameChgReqDto.setOnlineAuthInfo("ReqNo:" + myNameChgReqDto.getReqSeq() + ", ResNo:" + myNameChgReqDto.getResSeq());
        myNameChgReqDto.setSocNm(cntrList.get(0).getSocNm());
    }

    @RequestMapping(value = "/mypage/nameChgChkTelNo.do")
    @ResponseBody
    public Map<String, Object> nameChgChkTelNo(MyNameChgReqDto myNameChgReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonException(NO_FRONT_SESSION_EXCEPTION);
        }
        myNameChgReqDto.setUserid(userSession.getUserId());
        String rsltCode = "0001";
        String rsltMsg = "명의변경 회선과 양도인 미납을 위한 연락처는 달라야 합니다.";

        try {
            String result = myNameChgService.nameChgChkTelNo(myNameChgReqDto);
            if ("SUCCESS".equals(result)) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            } else {
                if ("NOTEQUAL_MINOR".equals(result)) {
                    rsltCode = "0002";
                    rsltMsg = "명의변경 회선과 법정대리인 연락처는 달라야 합니다.";
                } else if ("NOTEQUAL_MINOR2".equals(result)) {
                    rsltCode = "0003";
                    rsltMsg = "연락가능 연락처와 법정대리인 연락처는 달라야 합니다.";
                }
                rtnMap.put("RESULT_CODE", rsltCode);
                rtnMap.put("RESULT_MSG", rsltMsg);
            }
        } catch (RestClientException e) {
            logger.error(e.getMessage());
        }   catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }
        return rtnMap;
    }

    /**
     * 설명  : 입력한 회원 정보(이름,주민번호)와 회선정보가 일치하는지 체크
     * */
    @RequestMapping(value = "/mypage/userInfoChk.do")
    @ResponseBody
    private Map<String, Object> userInfoChk(MyNameChgReqDto myNameChgReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonException(NO_FRONT_SESSION_EXCEPTION);
        }

        if(StringUtils.isBlank(myNameChgReqDto.getContractNum())){
            throw new McpCommonJsonException("9999", F_BIND_EXCEPTION);
        }

        List<McpUserCntrMngDto> cntrList = myNameChgService.selectCntrListNmChg(userSession.getUserId(), myNameChgReqDto.getContractNum());
        if(cntrList == null|| cntrList.size() == 0) throw new McpCommonJsonException("9998", F_BIND_EXCEPTION);
        String cstmrNativeRrn;
        try {
            cstmrNativeRrn = EncryptUtil.ace256Dec(myNameChgReqDto.getCstmrNativeRrn());
        } catch (CryptoException e) {
            cstmrNativeRrn = "";
        }
        McpUserCntrMngDto cntrMngDto = cntrList.get(0);
        if (!myNameChgReqDto.getCstmrName().equals(cntrMngDto.getUserName())
            || !cstmrNativeRrn.equals(cntrMngDto.getUnUserSSn())) {
            throw new McpCommonJsonException("9997", "입력하신 가입자정보가 <br/>회선정보와 일치하지 않습니다.");
        } else {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        }
        return rtnMap;
        
    }
}
