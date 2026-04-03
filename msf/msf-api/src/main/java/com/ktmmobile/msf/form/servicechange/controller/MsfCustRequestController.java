package com.ktmmobile.msf.form.servicechange.controller;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NICE_CERT_EXCEPTION_INSR;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.STEP_CNT_EXCEPTION;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import com.ktmmobile.msf.form.servicechange.dto.CustRequestDto;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.service.MsfCustRequestScanService;
import com.ktmmobile.msf.form.servicechange.service.MsfCustRequestService;
import com.ktmmobile.msf.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfRegSvcService;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.system.common.dto.NiceResDto;
import com.ktmmobile.msf.system.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpUsimPukVO;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.service.SmsSvc;
import com.ktmmobile.msf.system.common.util.EncryptUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringMakerUtil;
import com.ktmmobile.msf.system.common.util.StringUtil;

@Controller
public class MsfCustRequestController {

    private static final Logger logger = LoggerFactory.getLogger(MsfCustRequestController.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MsfCustRequestScanService custRequestScanService;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private MsfCustRequestService custRequestService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private SmsSvc smsSvc;

    @Autowired
    private CertService certService;

    @Autowired
    private MsfRegSvcService regSvcService;

    @Autowired
    private MsfMaskingSvc maskingSvc;
    /**
     * 통화내역열람신청
     * @param HttpServletRequest
     * @param MyPageSearchDto
     * @param Model
     * @return string(pageUrl)
     */
    @RequestMapping(value = {"/mypage/reqCallList.do", "/m/mypage/reqCallList.do"}  )
    public String reSpnsrPlcyDc(ModelMap model , HttpServletRequest request
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO){

        String jspPageName = "/portal/mypage/reqCallList";
        String thisPageName ="/mypage/reqCallList.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/mobile/mypage/reqCallList";
            thisPageName ="/m/mypage/reqCallList.do";
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
        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
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
        return jspPageName;
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
     * wooki
     * 가입신청서 발급요청
     * @param HttpServletRequest
     * @param MyPageSearchDto
     * @param Model
     * @return string(pageUrl)
     */
    @RequestMapping(value= {"/m/mypage/reqJoinForm.do","/mypage/reqJoinForm.do"})
    public String reqJoinForm(HttpServletRequest request ,@ModelAttribute("searchVO") MyPageSearchDto searchVO, Model model) {

        String jspPageName = "/portal/mypage/reqJoinForm";
        String thisPageName ="/mypage/reqJoinForm.do";
        if("Y".equals(NmcpServiceUtils.isMobile())){
            jspPageName = "/mobile/mypage/reqJoinForm";
            thisPageName ="/m/mypage/reqJoinForm.do";
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
        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
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

        searchVO.setUserName(userSession.getName());
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);

        return jspPageName;
    }

    /**
     * wooki
     * 유심PUK번호 열람신청
     * @param HttpServletRequest
     * @param MyPageSearchDto
     * @param Model
     * @return string(pageUrl)
     */
    @RequestMapping(value= {"/m/mypage/reqUsimPuk.do","/mypage/reqUsimPuk.do"})
    public String reqUsimPuk(HttpServletRequest request ,@ModelAttribute("searchVO") MyPageSearchDto searchVO, Model model) {

        String jspPageName = "/portal/mypage/reqUsimPuk";
        String thisPageName ="/mypage/reqUsimPuk.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/mobile/mypage/reqUsimPuk";
            thisPageName ="/m/mypage/reqUsimPuk.do";
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
        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
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

        //searchVO.setUserName(userSession.getName());
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);

        return jspPageName;
    }

    /**
     * 2022.09.27 wooki
     * 단말보험(분실/파손) 가입신청
     * @param HttpServletRequest
     * @param MyPageSearchDto
     * @param Model
     * @return string(pageUrl)
     */
    @RequestMapping(value= {"/m/mypage/reqInsr.do","/mypage/reqInsr.do"})
    public String reqInsr(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, Model model) {

        String jspPageName = "/portal/mypage/reqInsr";
        String thisPageName ="/mypage/reqInsr.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/mobile/mypage/reqInsr";
            thisPageName ="/m/mypage/reqInsr.do";
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
        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        /**************** 계약번호로 안심보험 가입정보 조회하여 보험타입 구하기 start  ***************************/
        //String strNcn = searchVO.getNcn(); //계약번호
        String strContractNum = searchVO.getContractNum();
        String strActiveDate = ""; //개통일자
        String insrStatCd = ""; //보험가입상태(0:처리중,1:가입,2:해지..)
        String orderExist = ""; //보험가입진행중
        String requestExist = ""; //이미보험가입신청
        Map<String, String> insrInfo = null; //보험정보
        String reqBuyType = ""; //구매유형
        String custType = ""; //고객타입(I:개인,G:공공기관,B:법인)
        String unUserSsn = ""; //고객주민번호
        String customerType = ""; //고객종류(NA,NM,FN)
        long diffDays = 0; //개통일과 오늘의 일 수 차이
        String insrViewType = ""; //화면에 보여지는 보험 type(A:보험가입중/B:고객센터안내(미성년자,법인,신청상이등)/C:신규단말30일이내/D:신규단말30일이후/E:유심30일이내/F:유심30일이후/ING:보험가입신청중)

        //계약번호로 보험가입정보조회
        if(StringUtil.isNotNull(strContractNum)) {
            //API 호출
            insrInfo = custRequestService.getInsrInfo(strContractNum);
        }

        //보헙가입정보
        if(insrInfo != null) {
            reqBuyType  = (String)insrInfo.get("REQ_BUY_TYPE"); //구매유형
            insrStatCd = (String)insrInfo.get("INSR_STAT_CD");  //보험가입상태
            orderExist = (String)insrInfo.get("ORDER_EXIST");  //보험가입진행중
            requestExist = (String)insrInfo.get("REQUEST_EXIST"); //이미보험가입신청
        }

        //instStatCd가 0이나 1이면 보험가입 중 이므로 화면은 A
        if("0".equals(insrStatCd) || "1".equals(insrStatCd)) {
            insrViewType = "A";
        }
        else {
            //orderExist나 requestExist가 null이 아니면 보험가입진행중
            if(StringUtil.isNotNull(orderExist) || StringUtil.isNotNull(requestExist)) {
                insrViewType = "ING";
            }else {
                //개통일자, 고객타입, 주민번호 구하기
                for(int i=0; i<cntrList.size(); i++) {
                    if(strContractNum.equals(cntrList.get(i).getContractNum())) {
                        strActiveDate = cntrList.get(i).getLstComActvDate();
                        custType = cntrList.get(i).getCstmrType();
                        unUserSsn = cntrList.get(i).getUnUserSSn();
                        break;
                    }
                }

                //구매유형이 없거나(신청상이때문), 개인고객이 아니면 화면 B
                if(StringUtil.isBlank(reqBuyType) || !"I".equals(custType)) {
                    insrViewType = "B";
                }
                else {
                    //고객종류 구하기
                    if(StringUtil.isNotBlank(unUserSsn)) {
                        customerType = this.getCustomerType(unUserSsn);
                    }

                    //고객종류(NA,NM,FN) set(삭제예정)
                    for(int i=0; i<cntrList.size(); i++) {
                        cntrList.get(i).setCustomerType(customerType);
                    }

                    //미성년자면 화면B
                    if("NM".equals(customerType) || StringUtil.isBlank(customerType)) {
                        insrViewType = "B";
                    //미성년자가 아니면 개통일자로 날짜 계산
                    }else {
                        //개통일자로부터 오늘까지 날짜 계산
                        if(StringUtil.isNotBlank(strActiveDate)) {

                            try {
                                Date format = new SimpleDateFormat("yyyyMMdd").parse(strActiveDate);
                                Date today = new Date();
                                diffDays = ((today.getTime() - format.getTime()) / 1000) / (24*60*60);
                            } catch (ParseException e) {
                                logger.error("Exception e : {}", e.getMessage());
                            }

                        }

                        //신규단말구매
                         if("MM".equals(reqBuyType)) {
                             //개통일과 오늘의 일 수 차이가 45일 이내이면
                             if(diffDays <= 45) {
                                 insrViewType = "C";
                             }else {
                                 insrViewType = "D";
                             }
                         }

                        //유심구매
                         if("UU".equals(reqBuyType)) {
                             if(diffDays <= 45) {
                                 insrViewType = "E";
                             }else {
                                 insrViewType = "F";
                             }
                         }
                    }
                }
            }
        }
        /**************** 계약번호로 안심보험 가입정보 조회하여 보험타입 구하기 end  ***************************/

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

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("insrType", insrViewType);

        return jspPageName;
    }



    /**
     * 고객 요청 종류별로 처리(통화내역열람, 명의변경, 가입신청서출력, PUK조회, 휴대폰안심보험신청)
     * @param custRequestDto
     * @return Map<String, Object>
     */
    @RequestMapping(value = "/mypage/custRequestAjax.do")
    @ResponseBody
    public Map<String, Object> custRequestAjax(CustRequestDto custRequestDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // ================ STEP START ================
        // 1. 본인인증 세션 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("AUTH01", NICE_CERT_EXCEPTION_INSR);
        }

        // 2. 최소 스텝 수 확인
        int certStep= 4;
        if("CL".equals(custRequestDto.getReqType())) certStep= 5; // 5개

        if(certService.getStepCnt() < certStep){
            throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
        }

        // 3. 최종 정보 검증
        String urlType= "";
        if("IS".equals(custRequestDto.getReqType())) urlType= "saveReqInsrForm";
        else if("UP".equals(custRequestDto.getReqType())) urlType= "saveReqUsimPuk";
        else if("JF".equals(custRequestDto.getReqType())) urlType= "saveReqJoinForm";
        else if("CL".equals(custRequestDto.getReqType())) urlType= "saveReqCallList";

        // step종료 여부, 계약번호, 본인인증유형, reqSeq, resSeq, CI, 이름, 생년월일
        String[] certKey= {"urlType", "stepEndYn", "contractNum", "authType", "reqSeq", "resSeq"
                         , "connInfo", "name", "birthDate"};
        String[] certValue= {urlType, "Y", custRequestDto.getContractNum(), custRequestDto.getOnlineAuthType(), custRequestDto.getReqSeq(), custRequestDto.getResSeq()
                           , sessNiceRes.getConnInfo(), custRequestDto.getCstmrName(), EncryptUtil.ace256Enc(custRequestDto.getCstmrNativeRrn())};

        if(!"CL".equals(custRequestDto.getReqType())){
            // 통화내역열람신청을 제외한 요청은, 이름과 생년월일 필수값 아님
            // step종료 여부, 계약번호, 본인인증유형, reqSeq, resSeq, CI
            certKey = Arrays.copyOfRange(certKey, 0, 7);
        }

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
        }

        // 추가 값 세팅
        custRequestDto.setOnlineAuthInfo("ReqNo:" + custRequestDto.getReqSeq() + ", ResNo:" + custRequestDto.getResSeq());
        // ================ STEP END ================

        //안심보험신청, 유심PUK열람신청, 통화내역열람신청, 가입신청서출력요청
        if("IS".equals(custRequestDto.getReqType()) || "UP".equals(custRequestDto.getReqType())	|| "CL".equals(custRequestDto.getReqType()) || "JF".equals(custRequestDto.getReqType())) {
            //고객정보set
            CustRequestDto setDto = this.setCstmrInfo(custRequestDto);

            if(null != setDto) {
                custRequestDto.setUserId(setDto.getUserId());
                custRequestDto.setCstmrName(setDto.getCstmrName()); //고객명
                custRequestDto.setMobileNo(setDto.getMobileNo()); //휴대폰번호
                custRequestDto.setCstmrNativeRrn(setDto.getCstmrNativeRrn()); //주민번호
                custRequestDto.setCustId(setDto.getCustId()); //고객번호
                custRequestDto.setCstmrType(setDto.getCstmrType()); //고객종류
                custRequestDto.setCretId(setDto.getCretId()); //등록자아이디
                custRequestDto.setNcn(setDto.getNcn());
            }
        }



        long custReqSeq = 0;
        try {
            //1. CUST_REQUEST_SEQ 추출
            custReqSeq = custRequestService.getCustRequestSeq();
        } catch(DataAccessException e) {
            logger.error("Exception e : {}", e.getMessage());
        } catch(Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        if(custReqSeq <= 0) {
            rtnMap.put("RESULT_MSG", "신청이 완료되지 않았습니다. 고객센터에 문의해 주세요.");
            rtnMap.put("RESULT_CODE", "FAIL1");
            return rtnMap;
        }

        //custReqSeq를 DTO에 넣기
        custRequestDto.setCustReqSeq(custReqSeq);

        //주민번호 암호화
        custRequestDto.setCstmrNativeRrn(EncryptUtil.ace256Enc(custRequestDto.getCstmrNativeRrn()));

        boolean isInsertReqMst = false;
        try {
            //2. NMCP_CUST_REQUEST_MST에 마스터 정보 넣기
            isInsertReqMst = custRequestService.insertCustRequestMst(custRequestDto);
        } catch(DataAccessException e) {
            logger.error("Exception e : {}", e.getMessage());
        } catch(Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        if(!isInsertReqMst) {
            rtnMap.put("RESULT_MSG", "신청이 완료되지 않았습니다. 고객센터에 문의해 주세요.");
            rtnMap.put("RESULT_CODE", "FAIL2");
            return rtnMap;
        }

        //3. NMCP_CUST_REQUEST_요청종류 테이블에 정보 넣기
        boolean isInsertReqTable = false;

        //CL : 통화내역열람신청
        if("CL".equals(custRequestDto.getReqType())) {
            //starDate, endDate . 제거
            if(StringUtil.isNotNull(custRequestDto.getStartDate()) && StringUtil.isNotNull(custRequestDto.getEndDate())) {
                String reqStartDate = custRequestDto.getStartDate();
                String reqEndDate = custRequestDto.getEndDate();
                reqStartDate = reqStartDate.replaceAll("\\.", "").trim();
                reqEndDate = reqEndDate.replaceAll("\\.", "").trim();

                custRequestDto.setStartDate(reqStartDate);
                custRequestDto.setEndDate(reqEndDate);
            }

            try {
                //NMCP_CUST_REQUEST_CALL_LIST insert
                isInsertReqTable = custRequestService.insertCustRequestCallList(custRequestDto);
            }catch(DataAccessException e) {
                logger.error("Exception e : {}", e.getMessage());
            } catch(Exception e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }
        //NC : 명의변경 신청(이것은 컨트롤러가 따로 있음)
        else if("NC".equals(custRequestDto.getReqType())) {
            isInsertReqTable = true;
        }
        //JF : 가입신청서 출력요청
        else if("JF".equals(custRequestDto.getReqType())) {
            try {
                //NMCP_CUST_REQUEST_JOIN_FORM insert
                isInsertReqTable = custRequestService.insertCustRequestJoinForm(custRequestDto);
            } catch(DataAccessException e) {
                logger.error("Exception e : {}", e.getMessage());
            } catch(Exception e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }
        //UP : 유심PUK번호 열람신청
        else if("UP".equals(custRequestDto.getReqType())) {
            String ncn = custRequestDto.getNcn(); // 서비스 계약번호
            String ctn = custRequestDto.getMobileNo(); //전화번호
            String custId = custRequestDto.getCustId(); //고객번호

            try {
                //puk번호 열람은 prx 서버를 통해야 한다.
                MpUsimPukVO vo = custRequestService.getUsimPukByMP(ncn, ctn, custId);

                if(vo != null) {
                    rtnMap.put("USIMPUK", vo.getPukNo1());
                    rtnMap.put("RESULT_CODE", "SUCCESS");
                    return rtnMap;
                }else {
                    rtnMap.put("RESULT_MSG", "PUK 조회중 문제가 발생했습니다. 고객센터에 문의해 주세요.");
                    rtnMap.put("RESULT_CODE", "FAIL3");
                    return rtnMap;
                }
            } catch (RestClientException e) {
                logger.error("Y07 error USERID##=" + custRequestDto.getUserId() + " : " + e.getMessage());
            }  catch (Exception e) {
                logger.error("Y07 error USERID##=" + custRequestDto.getUserId() + " : " + e.getMessage());
            }
            isInsertReqTable = true;
        }
        //IS : 휴대폰안심보험 가입 신청
        else if("IS".equals(custRequestDto.getReqType())) {
            try {
                //NMCP_CUST_REQUEST_INSR insert
                isInsertReqTable = custRequestService.insertCustRequestInsr(custRequestDto);
            } catch(DataAccessException e) {
                logger.error("Exception e : {}", e.getMessage());
            } catch(Exception e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        if(!isInsertReqTable) {
            rtnMap.put("RESULT_MSG", "신청이 완료되지 않았습니다. 고객센터에 문의해 주세요.");
            rtnMap.put("RESULT_CODE", "FAIL3");
            return rtnMap;
        }

        //4. 통화내역열람신청(CL), 안심보험가입(IS)은 서식지 생성을 해준다.
        //통화내역열람신청
        if("CL".equals(custRequestDto.getReqType())) {
            try {
                //SCAN 서버에 서식지 데이터 생성 및 전송
                custRequestScanService.prodSendScan(custRequestDto.getCustReqSeq(), custRequestDto.getCretId(), "CL");

                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CUST_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET["+ custRequestDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(custRequestDto.getCustReqSeq()+"");
                mcpIpStatisticDto.setTrtmRsltSmst("SUCCESS");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            //예외 전환 처리
            } catch(McpCommonJsonException e) {
                rtnMap.put("RESULT_CODE", "FAIL");
                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CUST_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET["+ custRequestDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(custRequestDto.getCustReqSeq()+"");
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                logger.error(e.getMessage());
            } catch (Exception e) {
                rtnMap.put("RESULT_CODE", "FAIL");
                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CUST_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET["+ custRequestDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(custRequestDto.getCustReqSeq()+"");
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                logger.error(e.getMessage());
            }
        //안심보험가입신청
        }else if("IS".equals(custRequestDto.getReqType())) {

            try {

                //안심보험 부가서비스 가입 처리
                MpRegSvcChgVO res = regSvcService.regSvcChg(custRequestDto.getNcn(), custRequestDto.getMobileNo(), custRequestDto.getCustId(), custRequestDto.getInsrProdCd(), "");

                if(res == null || !res.isSuccess()) {
                    //logger.info("[안심보험가입신청 부가서비스가입오류]globalNo:" + res.getGlobalNo() + ",custReqSeq:" + custRequestDto.getCustReqSeq());
                    rtnMap.put("RESULT_MSG", "신청이 완료되지 않았습니다. 고객센터에 문의해 주세요.");
                    rtnMap.put("RESULT_CODE", "FAIL4");
                    return rtnMap;
                }

                //SCAN 서버에 서식지 데이터 생성 및 전송
                custRequestScanService.prodSendScan(custRequestDto.getCustReqSeq(), custRequestDto.getCretId(), "IS");

                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CUST_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET["+ custRequestDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(custRequestDto.getCustReqSeq()+"");
                mcpIpStatisticDto.setTrtmRsltSmst("SUCCESS");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto); //MCP_ADMIN_ACCESS_TRACE insert
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

                //5. 안심보험가입(IS) 중 D(자급제30일이후), E(유심30일이내), F(유심30일이후) 고객에게는 문자를 전송한다.
                if("D".equals(custRequestDto.getInsrType()) || "E".equals(custRequestDto.getInsrType()) || "F".equals(custRequestDto.getInsrType())) {
                    try {
                        //문자 템플릿 아이디 설정
                        int tempId = 0;
                        //자급제전용 보험
                        if("|PL214L310|PL214L312|PL214L316|PL214L317|PL214L319|PL245L230|PL245L229|PL245L231|PL245L228|PL245L232|PL245L235|PL245L237|PL245L236".indexOf(custRequestDto.getInsrProdCd()) > 0) {
                            /*
                                PL214L310|PL214L312|PL214L316|PL214L317|PL214L319|  24년 7월  이전
                                 PL245L230|PL245L229|PL245L231|PL245L228|PL245L232|PL245L235|PL245L237|PL245L236  신규 .. 24년 7월 추가
                             */
                            tempId = 239;
                        //USIM전용보험
                        }else if("|PL213M175|PL212O953|PL245L234|PL245L233".indexOf(custRequestDto.getInsrProdCd()) > 0) {
                            /*
                                PL213M175|PL212O953  24년 7월 추가
                                PL245L234, PL245L233  24년 7월 추가
                             */
                            tempId = 240;
                        }

                        if(tempId > 0) {
                            try{
                                //API 호출(SMS template가져오기)
                                MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(tempId);

                                if(mspSmsTemplateMstDto != null) {
                                    //API 호출(안심보험정보(보험이름,가격) 가져오기)
                                    Map<String, String> insrInfoMap = custRequestService.getInsrInfoByCd(custRequestDto.getInsrProdCd());

                                    if(insrInfoMap != null) {
                                        String mmsMsg = mspSmsTemplateMstDto.getText()
                                                .replaceAll(Pattern.quote("#{insrName}"), insrInfoMap.get("INSR_NAME"))
                                                .replaceAll(Pattern.quote("#{insrPrice}"), insrInfoMap.get("INSR_PRICE"));

                                        //API 호출 - 문자발송
                                        //smsSvc.sendLms(mspSmsTemplateMstDto.getSubject(), custRequestDto.getEtcMobile(), mmsMsg , mspSmsTemplateMstDto.getCallback());
                                        smsSvc.sendLms(mspSmsTemplateMstDto.getSubject(), custRequestDto.getEtcMobile(), mmsMsg ,
                                                mspSmsTemplateMstDto.getCallback(),String.valueOf(tempId),"SYSTEM");
                                    }
                                }
                            } catch (RestClientException e) {
                                logger.error("Exception e : {}", e.getMessage());
                            } catch(Exception e){
                                logger.error("Exception e : {}", e.getMessage());
                            }
                        }
                    } catch(RuntimeException e) {
                        logger.error("Exception e : {}", e.getMessage());
                    } catch(Exception e) {
                        logger.error("Exception e : {}", e.getMessage());
                    }
                }
                //예외 전환 처리
            } catch(McpCommonJsonException e) {
                rtnMap.put("RESULT_CODE", "FAIL");
                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CUST_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET["+ custRequestDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(custRequestDto.getCustReqSeq()+"");
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                logger.error(e.getMessage());
            } catch(Exception e) {
                rtnMap.put("RESULT_CODE", "FAIL");
                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CUST_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET["+ custRequestDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(custRequestDto.getCustReqSeq()+"");
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                logger.error(e.getMessage());
            }
        }

        rtnMap.put("RESULT_MSG", "신청이 완료되었습니다.");
        rtnMap.put("RESULT_CODE", "SUCCESS");
        return rtnMap;
    }

    /**
     * 2022.10.20 wooki
     * 화면에서 본인인증 시 로그인한 고객의 이름과 생년월일로 비교하기 위해 세선값을 가져감
     * @return Map
    */
    @RequestMapping(value = "/mypage/getUserInfoAjax.do")
    @ResponseBody
    public Map<String, Object> getUserInfoAjax() {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        rtnMap.put("USER_NAME", userSession != null ? userSession.getName() : "");
        rtnMap.put("USER_BIRTH", userSession != null ? userSession.getBirthday().substring(2) : "");

        return rtnMap;
    }

    /**
     * 2022.10.20 wooki
     * 주민번호로 만나이 계산하여 고객종류(NA,NA,FN) 리턴
     * @param String 주민번호
     * @return String 고객타입
     */
    private String getCustomerType(String unUserSsn) {
        //unUserSsn 없으면 리턴
        if(null == unUserSsn || "".equals(unUserSsn)) {
            return "";
        }

        String idNum = unUserSsn.substring(6, 7);
        String customerType = "";
        //외국인
        if("5".equals(idNum) || "6".equals(idNum)) {
            customerType = "FN";
        //내국인
        }else {
            int birthYear = 0;

            //뒷자리 7자리 중 첫번째가 1이나 2면 1900년대생
            if ("1".equals(idNum) || "2".equals(idNum)) {
                birthYear = Integer.parseInt("19" + unUserSsn.substring(0,2));
            //아니면 2000년대생
            }else {
                birthYear = Integer.parseInt("20" + unUserSsn.substring(0,2));
            }

            int birthMonth = Integer.parseInt(unUserSsn.substring(2, 4));
            int birthDay = Integer.parseInt(unUserSsn.substring(4, 6));

            Calendar current = Calendar.getInstance();

            int currentYear = current.get(Calendar.YEAR);
            int currentMonth = current.get(Calendar.MONTH);
            int currentDay = current.get(Calendar.DAY_OF_MONTH);
            int age = currentYear - birthYear; //만나이

            if(birthMonth * 100 + birthDay > currentMonth * 100 + currentDay) {
                age--;
            }

            if(age < 19) {
                customerType = "NM";
            }else {
                customerType = "NA";
            }
        }

        return customerType;
    }

    /**
     * 2022.11.08. wooki
     * 고객정보 셋팅
     * @param CustRequestDto
     * @return CustRequestDto
     */
    private CustRequestDto setCstmrInfo(CustRequestDto crDto) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) {
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }
        CustRequestDto setDto = new CustRequestDto();
        for(int i=0; i<cntrList.size(); i++) {
            if(crDto.getContractNum().equals(cntrList.get(i).getContractNum())) {
                setDto.setUserId(cntrList.get(i).getUserid()); //아이디
                setDto.setCstmrName(cntrList.get(i).getUserName()); //고객명
                setDto.setMobileNo(cntrList.get(i).getUnSvcNo()); //휴대폰번호
                setDto.setCstmrNativeRrn(cntrList.get(i).getUnUserSSn()); //주민번호
                setDto.setCustId(cntrList.get(i).getCustId()); //고객번호
                setDto.setCretId(cntrList.get(i).getUserid()); //등록자아이디
                setDto.setNcn(cntrList.get(i).getSvcCntrNo()); //서비스 계약번호
                break;
            }
        }

        //고객종류 구하기
        if(null != setDto.getCstmrNativeRrn() && !"".equals(setDto.getCstmrNativeRrn())) {
            setDto.setCstmrType(this.getCustomerType(setDto.getCstmrNativeRrn())); //고객종류
        }

        return setDto;
    }
}

