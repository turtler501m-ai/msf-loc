package com.ktmmobile.msf.form.servicechange.controller;

import com.ktmmobile.msf.form.newchange.dto.FormDtlDTO;
import com.ktmmobile.msf.form.newchange.service.FormDtlSvc;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.dto.*;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.dto.MpVirtualAccountNoDto;
import com.ktmmobile.msf.system.common.mplatform.vo.*;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.service.NiceLogSvc;
import com.ktmmobile.msf.system.common.util.*;
import com.ktmmobile.msf.mcash.dto.McashUserDto;
import com.ktmmobile.msf.mcash.service.McashService;
import com.ktmmobile.msf.form.servicechange.dto.*;
import com.ktmmobile.msf.form.servicechange.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ktmmobile.msf.system.common.constants.Constants.*;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class MyinfoController {

    private static Logger logger = LoggerFactory.getLogger(MyinfoController.class);

    @Autowired
    private SfMypageSvc mypageService;

    @Autowired
    private MyinfoService myinfoService;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    private PaywayService paywayService;

    @Autowired
    private FarPricePlanService farPricePlanService;

    @Autowired
    private FormDtlSvc formDtlSvc;

    @Autowired
    private CertService certService;

    @Autowired
    private McashService mcashService;

    @Autowired
    private NiceLogSvc nicelog;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    IpStatisticService ipstatisticService;

    @Autowired
    private MplatFormService mPlatFormService;

    /** 가입정보 첫화면 */
    @RequestMapping(value= {"/m/mypage/myinfoView.do","/mypage/myinfoView.do"})
    public String suspendView01(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String pageJsp = "/portal/mypage/myinfo/myinfoView";
        String redirectUrl = "/mypage/myinfoView.do";
        String loginUrl = "/loginForm.do";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            pageJsp = "/mobile/mypage/myinfo/myinfoView";
            redirectUrl = "/m/mypage/myinfoView.do";
            loginUrl = "/m/loginForm.do";
        }

        // 1. 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(redirectUrl);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO); // 조회할 회선 정보 전달
            return "/common/successRedirect";
        }

        // 2. userSession 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            return "redirect:" + loginUrl;
        }

        // 3. 정회원 체크
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);

        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String userName= userSession.getName();         // 사용자명
        String ncn = searchVO.getNcn();                 // 서비스계약번호
        String custId = searchVO.getCustId();           // customerId
        String ctn = searchVO.getCtn();                 // 전화번호
        String contractNum = searchVO.getContractNum(); // 계약번호
        String modelName = StringUtil.NVL(searchVO.getModelName(), "-"); // 모델명(SUB_INFO)

        // 4.사용중인 요금제
        McpFarPriceDto mcpFarPriceDto = null;
        String prvRateGrpNm = "-";          // 요금제명 (FEATURE_INFO)
        String rateAdsvcLteDesc = "- MB";   // 데이터
        String rateAdsvcCallDesc = "- 분";  // 음성
        String rateAdsvcSmsDesc = "- 건";   // sms

        try {
            mcpFarPriceDto = mypageService.selectFarPricePlan(contractNum);
            if(mcpFarPriceDto != null) {
                prvRateGrpNm = mcpFarPriceDto.getPrvRateGrpNm();

                // 4-1. 요금제에 해당하는 상세정보 조회 (XML파일)
                FarPricePlanResDto farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);
                rateAdsvcLteDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcLteDesc(), "- MB");
                rateAdsvcCallDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcCallDesc(), "- 분");
                rateAdsvcSmsDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcSmsDesc(), "- 건");
            }
        } catch(SelfServiceException e) {
            logger.info("SelfServiceException error={}", e.getMessage());
        } catch(Exception e) {
            logger.info("Exception error={}", e.getMessage());
        }

        // 5. M쇼핑할인 가입정보 조회
        String isMcashAuth = mcashService.isMcashAuth() ? "Y" : "N";  // M쇼핑할인 표출여부
        String isMcashUser = "N";       // M쇼핑할인 가입여부 (ncn 기준)
        String isMcashJoinCnt = "N";    // M쇼핑할인 가입여부 (다른 회선)
        String mcashStrtDttm = "-";     // M쇼핑할인 가입일자

        if ("Y".equals(isMcashAuth)) {  //  M쇼핑할인 영역이 표출되는 경우

            McashUserDto mcashDto = new McashUserDto();
            mcashDto.setUserid(userSession.getUserId());
            mcashDto.setSvcCntrNo(ncn);

            // 5-1. M쇼핑할인 가입정보 조회 (ncn 기준)
            McashUserDto mcashUserDto = mcashService.getMcashUserBySvcCntrNo(mcashDto);
            if (mcashUserDto != null && MCASH_USER_STATUS_ACTIVE.equals(mcashUserDto.getStatus())) {
                isMcashUser = "Y";

                try{
                    mcashStrtDttm = DateTimeUtil.changeFormat(mcashUserDto.getStrtDttm(),"yyyyMMddHHmmss","yyyy.MM.dd");
                }catch(ParseException e){
                    mcashStrtDttm= "-";
                    logger.info("Mcash ParseException={}", e.getMessage());
                }
            }

            // 5-2. M쇼핑할인 가입정보 조회 (다른 회선)
            if (1 < cntrList.size()) {
                int mcashJoinCnt = mcashService.getMcashJoinCnt(userSession.getUserId());
                if (mcashUserDto == null && mcashJoinCnt > 0) {  // 현재 조회 회선이 아닌 다른 회선으로 M쇼핑 가입한 경우
                    isMcashJoinCnt = "Y";
                }
            }
        }

        // 6. 가입정보 조회 (X01)
        String addr = "-";
        String initActivationDate = "-";

        MpPerMyktfInfoVO perMyktfInfo= paywayService.perMyktfInfo(ncn, ctn, custId);
        if (perMyktfInfo != null) {
            addr= StringUtil.NVL(perMyktfInfo.getAddr(),"-");
            initActivationDate= StringUtil.NVL(perMyktfInfo.getInitActivationDate(),"-");
        }

        // 7. 마스킹 세션 확인
        String maskingSession = SessionUtils.getMaskingSession() > 0  ? "Y" : "";
        if ("Y".equals(maskingSession)) {

            // 마스킹 해제되어야 하는 데이터 세팅
            searchVO.setUserName(userSession.getName());

            // 이력 insert
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름,휴대폰번호,납부정보");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        }else{
            // 마스킹 처리되어야 하는 데이터 세팅
            searchVO.setUserName(StringMakerUtil.getName(userName));
            if(!"-".equals(addr)){
                addr += "********";  // (기존로직) 마스킹 해제가 아닌경우 뒤에 *만 붙여줌..
            }
        }

        // 8.제휴상품 리마인드 SMS 수신 상태 조회 및 변경 (Y42) 밀리의 서재 or CU 요금제만 해당
        McpUserCntrMngDto selectSocDesc = mypageService.selectSocDesc(contractNum);
        String remindBlckYn = "";
        if(selectSocDesc !=null) {
            if ("Y".equals(selectSocDesc.getRemindYn())) {
                if(!StringUtils.isEmpty(selectSocDesc.getRemindProdType())) {
                    //remindYn이 Y이면서 remindProdType 비어있지 않은경우(현재는 MI,CU만 가능)
                    remindBlckYn = "Y";
                }
            }
        }

        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("modelName", modelName);                    // 모델명
        model.addAttribute("prvRateGrpNm", prvRateGrpNm);              // 요금제명
        model.addAttribute("rateAdsvcLteDesc", rateAdsvcLteDesc);      // 데이터
        model.addAttribute("rateAdsvcCallDesc", rateAdsvcCallDesc);    // 음성
        model.addAttribute("rateAdsvcSmsDesc", rateAdsvcSmsDesc);      // sms
        model.addAttribute("isMcashAuth", isMcashAuth);                // M쇼핑할인 표출여부
        model.addAttribute("isMcashUser", isMcashUser);                // M쇼핑할인 가입여부 (ncn 기준)
        model.addAttribute("isMcashJoinCnt", isMcashJoinCnt);          // M쇼핑할인 가입여부 (다른 회선)
        model.addAttribute("mcashStrtDttm", mcashStrtDttm);            // M쇼핑할인 가입일자
        model.addAttribute("initActivationDate", initActivationDate);  // 가입일 (또는 명의변경일)
        model.addAttribute("addr", addr);                              // 고객 주소
        model.addAttribute("maskingBtn", "Y");             // 마스킹 해제 버튼 표출여부
        model.addAttribute("maskingSession", maskingSession);          // 마스킹 해제 여부
        model.addAttribute("remindBlckYn", remindBlckYn); // 리마인드 SMS ON/OFF
        return pageJsp;

    }

    /**
     * 당월청구 요금 조회
     * @param ncn
     * @return Map<String, Object>
     */
    @RequestMapping("/mypage/getFarPriceInfoAjax.do")
    @ResponseBody
    public Map<String, Object> getFarPriceInfoAjax(@RequestParam String ncn) {

        Map<String, Object> rtnMap= new HashMap<>();

        // 1. userSession 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0001", NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. MP 연동 파라미터 조회
        McpUserCntrMngDto mcpUserCntrMngDto = null;
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(cntrList != null && !cntrList.isEmpty()) {
            mcpUserCntrMngDto = cntrList.stream().filter(item -> ncn.equals(item.getSvcCntrNo())).findFirst().orElse(null);
        }

        if(mcpUserCntrMngDto == null){
            throw new McpCommonJsonException("0002", F_BIND_EXCEPTION);
        }

        // 3. 당월청구 요금 조회 (X15)
        String cnt = mcpUserCntrMngDto.getCntrMobileNo();
        String custId = mcpUserCntrMngDto.getCustId();
        Map<String, Object> monBillingMap = myinfoService.farMonBillingInfo(ncn, cnt, custId, DateTimeUtil.getFormatString("yyyyMM"));

        MpMonthPayMentDto monthPaymentDto = new MpMonthPayMentDto();
        String billStartDate = "";
        String billEndDate = "";
        String billMonth = "";
        String thisMonth = "";
        String totalDueAmt = "";

        // 3-1. 당월청구 요금 조회가 정상적으로 되지 않은 경우, 날짜정보만 세팅
        if(monBillingMap == null || monBillingMap.get("mMonthpaymentdto") == null){

            try{
                billStartDate = DateTimeUtil.getFormatString("yyyyMM");            // 현재월
                billStartDate = DateTimeUtil.addMonths(billStartDate+"01",-1);   // 지난월 1일
                billEndDate = DateTimeUtil.lastDayOfMonth(billStartDate);                 // 지난월 마지막일
                billMonth = DateTimeUtil.getFormatString("MM");                    // 현재월

                billStartDate = DateTimeUtil.changeFormat(billStartDate,"yyyyMMdd","yyyy.MM.dd");
                billEndDate = DateTimeUtil.changeFormat(billEndDate,"yyyyMMdd","yyyy.MM.dd");

                monthPaymentDto.setBillStartDate(billStartDate);
                monthPaymentDto.setBillEndDate(billEndDate);
                monthPaymentDto.setBillMonth(billMonth);

            }catch(ParseException e){
                logger.info("getFarPriceInfoAjax ParseException={}", e.getMessage());
            }

            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("monthPaymentDto", monthPaymentDto);

            return rtnMap;
        }

        // 3-2. 당월청구 요금 조회가 정상적으로 된 경우
        monthPaymentDto = (MpMonthPayMentDto) monBillingMap.get("mMonthpaymentdto");

        try{
            billStartDate = DateTimeUtil.changeFormat(monthPaymentDto.getBillStartDate(),"yyyyMMdd","yyyy.MM.dd");
            billEndDate = DateTimeUtil.changeFormat(monthPaymentDto.getBillEndDate(),"yyyyMMdd","yyyy.MM.dd");
            billMonth = monthPaymentDto.getBillMonth().substring(4,6);
            thisMonth = StringUtil.addComma(monthPaymentDto.getThisMonth());
            totalDueAmt = StringUtil.addComma(monthPaymentDto.getTotalDueAmt());

            monthPaymentDto.setBillStartDate(billStartDate);
            monthPaymentDto.setBillEndDate(billEndDate);
            monthPaymentDto.setBillMonth(billMonth);
            monthPaymentDto.setThisMonth(thisMonth);
            monthPaymentDto.setTotalDueAmt(totalDueAmt);

        }catch(ParseException e){
            logger.info("getFarPriceInfoAjax ParseException={}", e.getMessage());
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("monthPaymentDto", monthPaymentDto);

        return rtnMap;
    }


    /**
     * 실시간 요금 조회
     * @param ncn
     * @return Map<String, Object>
     */
    @RequestMapping("/mypage/getRealTimePriceAjax.do")
    @ResponseBody
    public Map<String, Object> getRealTimePriceAjax(@RequestParam String ncn){

        Map<String, Object> rtnMap= new HashMap<>();

        // 1. userSession 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0001", NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. MP 연동 파라미터 조회
        McpUserCntrMngDto mcpUserCntrMngDto = null;
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(cntrList != null && !cntrList.isEmpty()) {
            mcpUserCntrMngDto = cntrList.stream().filter(item -> ncn.equals(item.getSvcCntrNo())).findFirst().orElse(null);
        }

        if(mcpUserCntrMngDto == null){
            throw new McpCommonJsonException("0002", F_BIND_EXCEPTION);
        }

        // 3. 실시간 요금 조회 (X18)
        MpFarRealtimePayInfoVO mpFarRealtimePayInfoVO = myinfoService.farRealtimePayInfo(ncn, mcpUserCntrMngDto.getCntrMobileNo(), mcpUserCntrMngDto.getCustId());

        if(mpFarRealtimePayInfoVO == null){
            throw new McpCommonJsonException("0003", COMMON_EXCEPTION);
        }

        String sumAmt = mpFarRealtimePayInfoVO.getSumAmt(); // 실시간 요금
        sumAmt= (sumAmt == null) ? "-" : StringUtil.addComma(sumAmt);

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("sumAmt", sumAmt);

        return rtnMap;
    }

    /**
     * 부가서비스 카운트 조회
     * @param ncn
     * @return Map<String, Object>
     */
    @RequestMapping("/mypage/getAddServiceCntAjax.do")
    @ResponseBody
    public Map<String, Object> getAddServiceCntAjax(@RequestParam String ncn){

        Map<String, Object> rtnMap= new HashMap<>();

        // 1. userSession 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0001", NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. MP 연동 파라미터 조회
        McpUserCntrMngDto mcpUserCntrMngDto = null;
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(cntrList != null && !cntrList.isEmpty()) {
            mcpUserCntrMngDto = cntrList.stream().filter(item -> ncn.equals(item.getSvcCntrNo())).findFirst().orElse(null);
        }

        if(mcpUserCntrMngDto == null){
            throw new McpCommonJsonException("0002", F_BIND_EXCEPTION);
        }

        // 3. 이용중인 부가서비스 조회 (X20)
        MpAddSvcInfoDto mpAddSvcInfoDto = myinfoService.getAddSvcInfoDto(ncn, mcpUserCntrMngDto.getCntrMobileNo(), mcpUserCntrMngDto.getCustId());

        if (mpAddSvcInfoDto == null){
            throw new McpCommonJsonException("0003", COMMON_EXCEPTION);
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("freeCtn", mpAddSvcInfoDto.getFreeCnt());
        rtnMap.put("payCtn", mpAddSvcInfoDto.getNotfreeCnt());

        return rtnMap;
    }

    /**
     * 현재 납부방법 및 명세서 정보 조회
     * @param ncn
     * @return Map<String, Object>
     */
    @RequestMapping("/mypage/getFarChgWayAjax.do")
    @ResponseBody
    public Map<String, Object> getFarChgWayAjax(@RequestParam String ncn) {

        Map<String, Object> rtnMap= new HashMap<>();

        // 1. userSession 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0001", NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. MP 연동 파라미터 조회
        McpUserCntrMngDto mcpUserCntrMngDto = null;
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(cntrList != null && !cntrList.isEmpty()) {
            mcpUserCntrMngDto = cntrList.stream().filter(item -> ncn.equals(item.getSvcCntrNo())).findFirst().orElse(null);
        }

        if(mcpUserCntrMngDto == null){
            throw new McpCommonJsonException("0002", F_BIND_EXCEPTION);
        }

        // 3. 현재 납부 방법 조회 (X23)
        MpFarChangewayInfoVO farChgWayInfo = myinfoService.farChangewayInfo(ncn, mcpUserCntrMngDto.getCntrMobileNo(), mcpUserCntrMngDto.getCustId());

        if(farChgWayInfo == null){ // X23 조회 실패 시 X49 연동하지 않음
            throw new McpCommonJsonException("0003", COMMON_EXCEPTION);
        }

        // 4. 명세서 정보 조회 (x49)
        MpMoscBilEmailInfoInVO bilEmailInfo = myinfoService.kosMoscBillInfo(ncn, mcpUserCntrMngDto.getCntrMobileNo(), mcpUserCntrMngDto.getCustId());

        // 5. 납부 방법과 명세서 정보 조합
        Map<String, Object> combinePayData = myinfoService.combinePayData(farChgWayInfo, bilEmailInfo);

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("payData", combinePayData.get("payData"));
        rtnMap.put("billData", combinePayData.get("billData"));

        return rtnMap;
    }

    /**
     * M쇼핑할인 잔액 조회
     * @param ncn
     * @return Map<String, String>
     */
    @RequestMapping("/mypage/getRemainMcashAjax.do")
    @ResponseBody
    public Map<String, String> getRemainMcashAjax(@RequestParam String ncn){

        Map<String, String> rtnMap= new HashMap<>();
        String remainMcash= "-";  // M쇼핑할인 잔액

        // 1. userSession 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0001", NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. ncn 변조여부 확인
        McpUserCntrMngDto mcpUserCntrMngDto = null;
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(cntrList != null && !cntrList.isEmpty()) {
            mcpUserCntrMngDto = cntrList.stream().filter(item -> ncn.equals(item.getSvcCntrNo())).findFirst().orElse(null);
        }

        if(mcpUserCntrMngDto == null){
            throw new McpCommonJsonException("0002", F_BIND_EXCEPTION);
        }

        // 3. 해당 회선이 M쇼핑할인 가입대상인지 확인
        McashUserDto mcashDto = new McashUserDto();
        mcashDto.setUserid(userSession.getUserId());
        mcashDto.setSvcCntrNo(ncn);

        McashUserDto mcashUserDto = mcashService.getMcashUserBySvcCntrNo(mcashDto);
        if(mcashUserDto == null || !MCASH_USER_STATUS_ACTIVE.equals(mcashUserDto.getStatus())){
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }

        // 4. M쇼핑할인 잔액 조회
        try {
            Map<String, Object> remainCash = mcashService.getRemainCash(userSession.getUserId());
            if (remainCash != null && remainCash.containsKey("remainCash")) {
                remainMcash = StringUtil.addComma(String.valueOf(remainCash.get("remainCash")));
            }
        } catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
            throw new McpCommonJsonException("0004", COMMON_EXCEPTION);
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("remainMcash", remainMcash);

        return rtnMap;
    }

    @RequestMapping(value= {"/m/mypage/mktAgrPop.do","/mypage/mktAgrPop.do"})
    public String mktAgrPop(HttpServletRequest request ,@ModelAttribute("searchVO") MyPageSearchDto searchVO,Model model) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        //메시지 수신동의 여부 조회
        AgreeDto agreeInfo = mypageUserService.selectMspMrktAgrTime(searchVO.getContractNum());

        model.addAttribute("agreeInfo", agreeInfo);
        model.addAttribute("contractNum", searchVO.getContractNum());
        model.addAttribute("mrkAgrYn", formDtlSvc.FormDtlView(new FormDtlDTO(TERMS_FORM_SELECT_CD, CLAUSE_PRI_AD_CODE)));
        model.addAttribute("personalInfoCollectAgree", formDtlSvc.FormDtlView(new FormDtlDTO(TERMS_FORM_SELECT_CD, PERSONAL_INFO_COLLECT_AGREE)));
        model.addAttribute("othersTrnsAgree", formDtlSvc.FormDtlView(new FormDtlDTO(TERMS_FORM_SELECT_CD, OTHERS_TRNS_AGREE)));
        model.addAttribute("othersTrnsKtAgree", formDtlSvc.FormDtlView(new FormDtlDTO(TERMS_FORM_SELECT_CD, OTHERS_TRNS_KT_AGREE)));
        model.addAttribute("othersAdReceiveAgree", formDtlSvc.FormDtlView(new FormDtlDTO(TERMS_FORM_SELECT_CD, OTHERS_AD_RECEIVE_AGREE)));
        model.addAttribute("othersTrnsAllAgree", formDtlSvc.FormDtlView(new FormDtlDTO(TERMS_FORM_SELECT_CD, OTHERS_TRNS_ALL_AGREE)));
        model.addAttribute("indvLocaPrvAgree", formDtlSvc.FormDtlView(new FormDtlDTO(TERMS_FORM_SELECT_CD, INDV_LOCA_PRV_AGREE)));

        String pageUrl = "";
        //중복요청 체크
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/mypage/myinfo/mktAgrPop";
        } else {
            pageUrl = "/portal/mypage/myinfo/mktAgrPop";
        }

        return pageUrl;
    }

    /**
     * 회선별 마케팅 수신 동의 여부 저장
     * @param request
     * @param contractNum
     * @return
     */
    @RequestMapping("/mypage/setMktAgrYnAjax.do")
    @ResponseBody
    public Map<String, Object> setMktAgrYnAjax(HttpServletRequest request,
                                               @RequestParam(value = "contractNum", required = true) String contractNum,
                                               @RequestParam(value = "personalInfoCollectAgree", required = true) String personalInfoCollectAgree,
                                               @RequestParam(value = "othersTrnsAgree", required = true) String othersTrnsAgree,
                                               @RequestParam(value = "mktAgrYn", required = true) String mktAgrYn,
                                               @RequestParam(value = "othersTrnsKtAgree", required = true) String othersTrnsKtAgree,
                                               @RequestParam(value = "othersAdReceiveAgree", required = true) String othersAdReceiveAgree,
                                               @RequestParam(value = "indvLocaPrvAgree", required = true) String indvLocaPrvAgree) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("E", F_BIND_EXCEPTION);
        }

        if (!"Y".equals(personalInfoCollectAgree) && "Y".equals(mktAgrYn)) {
            throw new McpCommonJsonException("E", "[개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의]를 위해<br>[고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의]가 필요합니다.");
        }

        if (("Y".equals(othersTrnsAgree) || "Y".equals(othersTrnsKtAgree) || "Y".equals(othersAdReceiveAgree))
            && !("Y".equals(personalInfoCollectAgree) && "Y".equals(mktAgrYn))) {
            throw new McpCommonJsonException("E", "[혜택 제공을 위한 제3자 제공 및 광고 수신 동의]를 위해<br>[고객 혜택 제공을 위한 정보수집 이용 동의 및 혜택 광고의 수신 위탁 동의]가 필요합니다.");
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        //정회원
        if(cntrList.isEmpty()){
            throw new McpCommonJsonException("E", F_BIND_EXCEPTION);
        }

        McpUserCntrMngDto cntrInfo = null;
        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (contractNum.equals(mcpUserCntrMngDto.getContractNum())) {
                cntrInfo = mcpUserCntrMngDto;
                break;
            }
        }

        if (cntrInfo == null || "".equals(cntrInfo.getSvcCntrNo()) || "".equals(cntrInfo.getCntrMobileNo()) || "".equals(cntrInfo.getCustId())) {
            throw new McpCommonJsonException("E", F_BIND_EXCEPTION);
        }

        MpCustInfoAgreeVO mpCustInfoAgreeVO;
        try {
            MpCustInfoAgreeVO agreeParam = MpCustInfoAgreeVO.builder()
                    .othcmpInfoAdvrCnsgAgreYn(mktAgrYn)
                    .othcmpInfoAdvrRcvAgreYn(personalInfoCollectAgree)
                    .fnncDealAgreeYn(othersTrnsAgree)
                    .grpAgntBindSvcSbscAgreYn(othersTrnsKtAgree)
                    .cardInsrPrdcAgreYn(othersAdReceiveAgree)
                    .indvLoInfoPrvAgreeYn(indvLocaPrvAgree)
                    .build();
            mpCustInfoAgreeVO = mPlatFormService.moscContCustInfoAgreeChg(cntrInfo.getSvcCntrNo(), cntrInfo.getCntrMobileNo(), cntrInfo.getCustId(), agreeParam);
        } catch (Exception e) {
            throw new McpCommonJsonException("E", "정보/광고 수신동의 설정이 변경에 실패했습니다.");
        }

        if (!mpCustInfoAgreeVO.isSuccess()) {
            throw new McpCommonJsonException("E", "정보/광고 수신동의 설정이 변경에 실패했습니다.");
        }

        try {
            mypageUserService.callMspMrktAgr(cntrInfo.getCntrMobileNo(), personalInfoCollectAgree, othersTrnsAgree, mktAgrYn, othersTrnsKtAgree, othersAdReceiveAgree, indvLocaPrvAgree, userSession.getUserId());
        } catch(Exception e) {
            throw new McpCommonJsonException("E", "정보/광고 수신동의 설정이 변경에 실패했습니다.");
        }

        rtnMap.put("RESULT_CODE", "S");
        return rtnMap;
    }

    /**
     * 가입증명원 인쇄
     * @param request
     * @param contractNum
     * @return
     */
    @RequestMapping("/mypage/joinCert.do")
    public String joinCert(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        String redirectUri = "/mypage/myinfoView.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            redirectUri = "/m/mypage/myinfoView.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 334
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        // ============ STEP START ===========
        String errorUrl = "/portal/errmsg/errorPop";
        if("Y".equals(NmcpServiceUtils.isMobile())) {
            errorUrl = "/mobile/errmsg/errorPop";
        }

        // 1. 최소 스텝 수 검증
        if(certService.getStepCnt() < 3 ){
            model.addAttribute("ErrorTitle", "가입 증명원 인쇄");
            model.addAttribute("ErrorMsg", STEP_CNT_EXCEPTION);
            return errorUrl;
        }

        // 2. 최종 데이터 검증: step종료 여부, 계약번호, 전화번호
        String[] certKey = {"urlType", "stepEndYn", "contractNum", "mobileNo"};
        String[] certValue = {"getJoinCert", "Y", searchVO.getContractNum(),searchVO.getCtn()};
        Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))){
            model.addAttribute("ErrorTitle", "가입 증명원 인쇄");
            model.addAttribute("ErrorMsg", vldReslt.get("RESULT_DESC"));
            return errorUrl;
        }
        // ============ STEP END ============

        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String ctn = searchVO.getCtn();
        // 마스킹 원복 2022.10.31
        searchVO.setUserName(userSession.getName());
        String status = "";
        if(cntrList !=null && !cntrList.isEmpty()) {
            for(McpUserCntrMngDto dto : cntrList) {
                String svcCntrNo = dto.getSvcCntrNo();
                if(ncn.contentEquals(svcCntrNo)) {
                    status = dto.getSubStatus();
                    if(status.equals("A")) {
                        status ="사용중";
                    } else if(status.equals("S")) {
                        status ="이용중지";
                    } else {
                        status ="-";
                    }
                    break;
                }
            }
        }


        // 본인인증
        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setMenu("joinCert");
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


        McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());
        String dobyyyymmdd = "";
        String userSSn = "";
        if(mcpUserCntrMngDto == null) {
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            responseSuccessDto.setSuccessMsg("해당 사용자의 요금제 데이터가 없습니다.");
            responseSuccessDto.setRedirectUrl(redirectUri);
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        } else {
            dobyyyymmdd = StringUtil.NVL(mcpUserCntrMngDto.getDobyyyymmdd(),"");
            userSSn = StringUtil.NVL(mcpUserCntrMngDto.getUserSSn(),"");
        }

        if(!"".equals(dobyyyymmdd) && dobyyyymmdd.length() < 9) { // MSP 에서도 모르는 컬럼임.
            mcpUserCntrMngDto.setDobyyyymmdd(DateTimeUtil.getFormatString(dobyyyymmdd, "yyyyMMdd", "yyyy.MM.dd"));
        }
        if(!"".equals(userSSn) && userSSn.length() < 9 ) {
            mcpUserCntrMngDto.setUserSSn(DateTimeUtil.getFormatString(userSSn, "yyyyMMdd", "yyyy.MM.dd"));
        }

        String today = DateTimeUtil.getFormatString("yyyy년 M월 d일");
        //가입정보 조회
        MpPerMyktfInfoVO perMyktfInfo = myinfoService.perMyktfInfo(ncn, ctn, custId);
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("ctn", StringUtil.getMobileFullNum(searchVO.getCtn()));
        paramMap.put("actDate", perMyktfInfo.getInitActivationDate());
        paramMap.put("name", searchVO.getUserName());
        paramMap.put("birthday", mcpUserCntrMngDto.getDobyyyymmdd());
        paramMap.put("modelNm", mcpUserCntrMngDto.getModelName());
        // 마스킹 원복 2022.10.31
        paramMap.put("modelSr", mcpUserCntrMngDto.getUnIntmSrlNo());
        paramMap.put("userId", userSession != null ? userSession.getUserId() : ""); // 취약성 335
        mypageService.insertCertHist(paramMap);

        HashMap<String, Object> returnMap = (HashMap<String, Object>)mypageService.selectCertHist(userSession.getUserId());
        String seqSr = "";
        if(returnMap !=null) {
            seqSr = (String)returnMap.get("SEQSR");
        }

        model.addAttribute("today", today);
        model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto);
        model.addAttribute("perMyktfInfo", perMyktfInfo);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("seqSr", seqSr);
        // 마스킹 원복 2022.10.31
        model.addAttribute("ctn", StringUtil.getMobileFullNum(searchVO.getCtn()));
        model.addAttribute("status", status);

        return "/portal/mypage/myinfo/joinCertPop";
    }

    /**
     * 가입증명원 인쇄
     * @param request
     * @param contractNum
     * @return
     */

    @RequestMapping("/mypage/joinCertAjax.do")
    @ResponseBody
    public Map<String,Object> joinCertAjax(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        Map<String,Object> rtnMap = new HashMap<String, Object>();

        String resultCode = "00";
        String message = "";
        String seqSr = "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if ( userSession != null) { // 취약성 340
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            resultCode = "E";
            message = "비정상적인 접근입니다.";
            rtnMap.put("resultCode",resultCode);
            rtnMap.put("message",message);
            rtnMap.put("seqSr",seqSr);
            return rtnMap;
        }

        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String ctn = searchVO.getCtn();
        searchVO.setUserName(userSession.getName());


        McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());
        String dobyyyymmdd = "";
        String userSSn = "";
        if(mcpUserCntrMngDto == null) {
            resultCode = "50";
            message = "해당 사용자의 요금제 데이터가 없습니다.";
            rtnMap.put("resultCode",resultCode);
            rtnMap.put("message",message);
            rtnMap.put("seqSr",seqSr);
            return rtnMap;
        } else {
            dobyyyymmdd = StringUtil.NVL(mcpUserCntrMngDto.getDobyyyymmdd(),"");
            userSSn = StringUtil.NVL(mcpUserCntrMngDto.getUserSSn(),"");
        }

        if(!"".equals(dobyyyymmdd) && dobyyyymmdd.length() < 9) { // MSP 에서도 모르는 컬럼임.
            mcpUserCntrMngDto.setDobyyyymmdd(DateTimeUtil.getFormatString(dobyyyymmdd, "yyyyMMdd", "yyyy.M.d"));
        }
        if(!"".equals(userSSn) && userSSn.length() < 9 ) {
            mcpUserCntrMngDto.setUserSSn(DateTimeUtil.getFormatString(userSSn, "yyyyMMdd", "yyyy.MM.dd"));
        }

        String today = DateTimeUtil.getFormatString("yyyy년 M월 d일");
        //가입정보 조회
        MpPerMyktfInfoVO perMyktfInfo = myinfoService.perMyktfInfo(ncn, ctn, custId);

        try {

            HashMap<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("ctn", StringUtil.getMobileFullNum(searchVO.getCtn()));
            paramMap.put("actDate", perMyktfInfo.getInitActivationDate());
            paramMap.put("name", searchVO.getUserName());
            paramMap.put("birthday", mcpUserCntrMngDto.getDobyyyymmdd());
            paramMap.put("modelNm", mcpUserCntrMngDto.getModelName());
            paramMap.put("modelSr", mcpUserCntrMngDto.getIntmSrlNo());
            paramMap.put("userId", userSession.getUserId());
            mypageService.insertCertHist(paramMap);

            HashMap<String, Object> returnMap = (HashMap<String, Object>)mypageService.selectCertHist(userSession.getUserId());

            if(returnMap !=null) {
                seqSr = (String)returnMap.get("SEQSR");
            }
        } catch(DataAccessException e) {
            resultCode = "01";
            message = "데이터 조회에 실패했습니다.";
            rtnMap.put("resultCode",resultCode);
            rtnMap.put("message",message);
            rtnMap.put("seqSr",seqSr);
            return rtnMap;
        } catch(Exception e) {
            resultCode = "01";
            message = "데이터 조회에 실패했습니다.";
            rtnMap.put("resultCode",resultCode);
            rtnMap.put("message",message);
            rtnMap.put("seqSr",seqSr);
            return rtnMap;
        }
        rtnMap.put("resultCode",resultCode);
        rtnMap.put("message",message);
        rtnMap.put("seqSr", seqSr);
        return rtnMap;
    }

    /**
     * 가입정보 인쇄
     * @param request
     * @param contractNum
     * @return
     */
    @RequestMapping("/mypage/myInfoPrintPop.do")
    public String requestViewPrint(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
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

        //현재 요금제 조회
        McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());
        if(mcpUserCntrMngDto == null) {
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            responseSuccessDto.setSuccessMsg("해당 사용자의 요금제 데이터가 없습니다.");
            responseSuccessDto.setRedirectUrl("/main.do");
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        //가입정보 조회
        MpPerMyktfInfoVO perMyktfInfo = myinfoService.perMyktfInfo(ncn, ctn, custId);

        //이용중인 부가서비스 조회
        MpAddSvcInfoDto mAddsvcinfodto = myinfoService.getAddSvcInfoDto(ncn, ctn, custId);

        //현재 납부방법 조회
        MpFarChangewayInfoVO changeInfo = myinfoService.farChangewayInfo(ncn, ctn, custId);

        //명세서 유형 조회
        MpMoscBilEmailInfoInVO moscBilEmailInfo = myinfoService.kosMoscBillInfo(ncn, ctn, custId);

        String today = DateTimeUtil.getFormatString("yyyy년 M월 d일");

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            searchVO.setUserName(userSession.getName());

            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름,휴대폰번호,납부방법,요금명세서");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);


        }else {
            // 이름 마스킹 적용 2022.10.05
            searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        }

        model.addAttribute("today", today);
        model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto);
        model.addAttribute("perMyktfInfo", perMyktfInfo);
        model.addAttribute("mAddsvcinfodto", mAddsvcinfodto);
        model.addAttribute("changeInfo", changeInfo);
        model.addAttribute("moscBilEmailInfo", moscBilEmailInfo);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);

        return "/portal/mypage/myinfo/myInfoPrintPop";
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
     * 설명     : 가려진 정보 보기  popup
     */
    @RequestMapping(value = {"/maskingPop.do", "/m/maskingPop.do"})
    public String mskingPop(Model model) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        // 계약번호
        SessionUtils.removeCertSession();
        String[] certKey= {"urlType", "name", "birthDate"};
        String[] certValue= {"maskingAuth", userSession.getName(), userSession.getBirthday()};
        certService.vdlCertInfo("C", certKey, certValue);
        // ============ STEP END ============


        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())){
            return "/mobile/popup/maskingPop";
        }else {
            return "/portal/popup/maskingPop";
        }
    }


    /**
     * 설명 : 마스킹 해제 세션 저장 Ajax
     */
    @RequestMapping(value = {"/unMaskingAjax.do", "/m/unMaskingAjax.do"})
    @ResponseBody
    public Map<String, Object> unMaskingAjax(HttpServletRequest request,
                                             @RequestParam(value = "reqSeq", required = true) String reqSeq,
                                             @RequestParam(value = "resSeq", required = true) String resSeq){
        Map<String, Object> resultMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if (reqSeq == null || reqSeq.isEmpty() || resSeq == null || resSeq.isEmpty()) {
            resultMap.put("RESULT_CODE", "0001");
            resultMap.put("RESULT_MSG", "인증값에 문제가 발생하였습니다.<br>마스킹 해제를 원하시면<br>본인인증을 다시한번 진행해주세요.");
            return resultMap;
        }

        NiceLogDto niceLogDto= new NiceLogDto();
        niceLogDto.setReqSeq(reqSeq);
        niceLogDto.setResSeq(resSeq);

        NiceLogDto niceLogRtn = nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
        if(niceLogRtn == null) {
            resultMap.put("RESULT_CODE", "0001");
            resultMap.put("RESULT_MSG", "인증값에 문제가 발생하였습니다.<br>마스킹 해제를 원하시면<br>본인인증을 다시한번 진행해주세요.");
            return resultMap;
        }


        // 2. 최종 데이터 확인: 스텝종료여부, 생년월일, DI

        String[] certKey= {"urlType", "name", "birthDate"};
        String[] certValue= {"maskingAuth", niceLogRtn.getName(), niceLogRtn.getBirthDate()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);

        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            resultMap.put("RESULT_CODE", "0002");
            resultMap.put("RESULT_MSG", "비정상적인 시도가 탐지되어<br>본인인증이 취소 되었습니다.<br> 다시한번 진행해주세요.");
            return resultMap;
        }


        MaskingDto maskingDto = new MaskingDto();
        maskingDto.setUserId(userSession.getUserId());
        maskingDto.setAuthType(niceLogRtn.getAuthType());
        maskingDto.setCi(niceLogRtn.getConnInfo());
        maskingDto.setAccessIp(ipstatisticService.getClientIp());
        maskingDto.setCretId(userSession.getUserId());
        maskingDto.setAmdId(userSession.getUserId());

        maskingSvc.insertMaskingRelease(maskingDto);
        SessionUtils.saveMaskingSession(maskingDto.getMaskingReleaseSeq());

        return resultMap;
    }


    /**
     * 설명 : 마스킹 해제 시간 카운팅 Ajax
     */
    @RequestMapping(value = {"/mskingCountAjax.do", "/m/mskingCountAjax.do"})
    @ResponseBody
    public Map<String, Object> mskingCountAjax(HttpSession session){
        Map<String, Object> sessionInfo = new HashMap<>();

        Long sessionTimeout = (Long) session.getAttribute("maskingSessionTimeout");
        // 현재 시간과 세션의 만료 시간 비교
        long currentTime = System.currentTimeMillis();
        boolean sessionExpired = (sessionTimeout == null || currentTime > sessionTimeout);

        // 남은 시간 계산 (밀리초 기준)
        long timeLeft = (sessionTimeout != null) ? sessionTimeout - currentTime : 0;

        sessionInfo.put("sessionExpired", sessionExpired);
        sessionInfo.put("timeLeft", timeLeft);

        return sessionInfo;
    }


    /**
     * 설명 : 리마인드 문자 수신 조회 Ajax
     */
    @RequestMapping(value = {"/remindSmsAskAjax.do", "/m/remindSmsAskAjax.do"})
    @ResponseBody
    public Map<String, Object> remindSmsAskAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO){
        Map<String, Object> resultMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);

        if(!chk){
            resultMap.put("resultCode","E");
            resultMap.put("message", "비정상적인 접근입니다.");
            return resultMap;
        }

        String ncn = searchVO.getNcn();                 // 서비스계약번호
        String custId = searchVO.getCustId();           // customerId
        String ctn = searchVO.getCtn();                 // 전화번호
        String contractNum = searchVO.getContractNum(); // 계약번호


        String prcsMdlInd = DateTimeUtil.getFormatString("yyyyMMddHHmmss");

        //결과 로그 저장 처리
        McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
        serviceAlterTraceSub.setNcn(ncn);
        serviceAlterTraceSub.setContractNum(contractNum);
        serviceAlterTraceSub.setSubscriberNo(ctn);
        serviceAlterTraceSub.setPrcsMdlInd("MP"+prcsMdlInd);
        serviceAlterTraceSub.setEventCode("Y42");
        serviceAlterTraceSub.setTrtmRsltSmst("리마인드 SMS 수신상태 조회");

        // 8.제휴상품 리마인드 SMS 수신 상태 조회 및 변경 (Y42) 밀리의 서재 or CU 요금제만 해당
        McpUserCntrMngDto selectSocDesc = mypageService.selectSocDesc(contractNum);
        if(selectSocDesc !=null) {
            if ("Y".equals(selectSocDesc.getRemindYn())) {

                MoscRemindSmsDto moscRemindSmsDto = new MoscRemindSmsDto();
                moscRemindSmsDto.setCustId(custId);
                moscRemindSmsDto.setCtn(ctn);
                moscRemindSmsDto.setNcn(ncn);

                //prodGubun은 반드시 MI, CU 둘 중에 하나로 기입하여 호출
                if(!StringUtils.isEmpty(selectSocDesc.getRemindProdType())) {
                    moscRemindSmsDto.setProdGubun(selectSocDesc.getRemindProdType());
                }

                // R은 조회, U는 변경
                moscRemindSmsDto.setWrkjobCd("R");

                try {

                    MpRemindSmsVO mpRemindSmsVO = mPlatFormService.getRemindSms(moscRemindSmsDto);

                    serviceAlterTraceSub.setGlobalNo(mpRemindSmsVO.getGlobalNo());
                    serviceAlterTraceSub.setRsltCd(mpRemindSmsVO.getResultCode());
                    serviceAlterTraceSub.setPrcsSbst(mpRemindSmsVO.getSvcMsg());

                    if(mpRemindSmsVO != null) {
                        if("00".equals(mpRemindSmsVO.getResultCd())) { //응답코드 00:성공, 99:실패
                            if("Y".equals(mpRemindSmsVO.getSmsRcvBlckYn())) {
                                resultMap.put("resultCode","1001");
                            }else {
                                resultMap.put("resultCode","1000");
                            }
                            resultMap.put("prcsMdlInd",prcsMdlInd);
                            serviceAlterTraceSub.setTrtmRsltSmst("리마인드 SMS 수신상태 조회");
                        }else {
                            resultMap.put("resultCode","1002");
                            resultMap.put("message", mpRemindSmsVO.getResultMsg());
                            serviceAlterTraceSub.setTrtmRsltSmst("리마인드 SMS 수신상태 조회 실패 : 응답코드 99");
                        }
                        mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
                    }

                } catch(SelfServiceException e) {
                    logger.error("SelfServiceException e : {}", e.getMessage());
                    resultMap.put("resultCode", "1003");
                    resultMap.put("message", e.getMessage());
                    return resultMap;
                } catch(Exception e) {
                    logger.error("Exception e : {}", e.getMessage());
                    resultMap.put("resultCode", "1003");
                    resultMap.put("message", e.getMessage());
                    return resultMap;
                }
            }
        }

        return resultMap;
    }

    /**
     * 설명 : 리마인드 문자 수신 ON/OFF Ajax
     */
    @RequestMapping(value = {"/remindSmsStatAjax.do", "/m/remindSmsStatAjax.do"})
    @ResponseBody
    public Map<String, Object> remindSmsStatAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                 @RequestParam(value = "remindBtn", required = true) String remindBtn,
                                                 @RequestParam(value = "prcsMdlInd", required = true) String prcsMdlInd){
        Map<String, Object> resultMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);

        if(!chk){
            resultMap.put("resultCode","E");
            resultMap.put("message", "비정상적인 접근입니다.");
            return resultMap;
        }

        String ncn = searchVO.getNcn();                 // 서비스계약번호
        String custId = searchVO.getCustId();           // customerId
        String ctn = searchVO.getCtn();                 // 전화번호
        String contractNum = searchVO.getContractNum(); // 계약번호


        //결과 로그 저장 처리
        McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
        serviceAlterTraceSub.setNcn(ncn);
        serviceAlterTraceSub.setContractNum(contractNum);
        serviceAlterTraceSub.setSubscriberNo(ctn);
        serviceAlterTraceSub.setPrcsMdlInd("MP"+prcsMdlInd);
        serviceAlterTraceSub.setEventCode("Y42");
        serviceAlterTraceSub.setTrtmRsltSmst("리마인드 SMS 수신상태 변경");


        // 8.제휴상품 리마인드 SMS 수신 상태 조회 및 변경 (Y42) 밀리의 서재 or CU 요금제만 해당
        McpUserCntrMngDto selectSocDesc = mypageService.selectSocDesc(contractNum);

        if(selectSocDesc !=null) {
            if ("Y".equals(selectSocDesc.getRemindYn())) {

                MoscRemindSmsDto moscRemindSmsDto = new MoscRemindSmsDto() ;
                moscRemindSmsDto.setNcn(ncn);
                moscRemindSmsDto.setCustId(custId);
                moscRemindSmsDto.setCtn(ctn);

                //prodGubun은 반드시 MI, CU 둘 중에 하나로 기입하여 호출
                if(!StringUtils.isEmpty(selectSocDesc.getRemindProdType())) {
                    moscRemindSmsDto.setProdGubun(selectSocDesc.getRemindProdType());
                }
                // R은 조회, U는 변경
                moscRemindSmsDto.setWrkjobCd("U");
                if(remindBtn != null) {
                    moscRemindSmsDto.setSmsRcvBlckYn(remindBtn);
                }

                Date now= new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
                String procDd = dateFormat.format(now); // 기준일자 (YYYYMMDD)

                try {

                    MpRemindSmsVO mpRemindSmsVO = mPlatFormService.getRemindSms(moscRemindSmsDto);

                    serviceAlterTraceSub.setGlobalNo(mpRemindSmsVO.getGlobalNo());
                    serviceAlterTraceSub.setRsltCd(mpRemindSmsVO.getResultCode());
                    serviceAlterTraceSub.setPrcsSbst(mpRemindSmsVO.getSvcMsg());

                    if(mpRemindSmsVO != null) {
                        if("00".equals(mpRemindSmsVO.getResultCd())) { //응답코드 00:성공, 99:실패
                            if("N".equals(mpRemindSmsVO.getSmsRcvBlckYn())) {
                                resultMap.put("resultCode","1000");
                                resultMap.put("message", "문자 수신에 동의하셨습니다.<br>(변경일자 "+procDd+")");
                                mypageUserService.updateRemindYn(ncn,"N");
                            }else {
                                resultMap.put("resultCode","1001");
                                resultMap.put("message", "문자 수신 동의가 해제되었습니다.<br>(변경일자 "+procDd+")");
                                mypageUserService.updateRemindYn(ncn,"Y");
                            }
                            serviceAlterTraceSub.setTrtmRsltSmst("리마인드 SMS 수신상태 변경");
                        }else {
                            resultMap.put("resultCode","1002");
                            resultMap.put("message", mpRemindSmsVO.getResultMsg());
                            serviceAlterTraceSub.setTrtmRsltSmst("리마인드 SMS 수신상태 변경 실패 응답코드 : 99");
                        }
                        mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
                    }

                } catch(SelfServiceException e) {
                    logger.error("SelfServiceException e : {}", e.getMessage());
                    resultMap.put("resultCode", "1003");
                    resultMap.put("message", e.getMessage());
                    return resultMap;
                } catch(Exception e) {
                    logger.error("Exception e : {}", e.getMessage());
                    resultMap.put("resultCode", "1003");
                    resultMap.put("message", e.getMessage());
                    return resultMap;
                }
            }
        }

        return resultMap;
    }

    @RequestMapping(value = {"/mypage/virtualAccountListPop.do", "/m/mypage/virtualAccountListPop.do"})
    public String virtualAccountListPop(HttpServletRequest request
            , @RequestParam(value = "ncn") String ncn
            , Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO
    ) {
        String url = "/portal/mypage/myinfo/virtualAccountListPop";
        String errorUrl = "/portal/errmsg/errorPop";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())){
            url = "/mobile/mypage/myinfo/virtualAccountListPop";
            errorUrl = "/mobile/errmsg/errorPop";
        }

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto == null || StringUtils.isEmpty(userSessionDto.getUserId())) {
            model.addAttribute("ErrorTitle", "가상계좌 조회");
            model.addAttribute("ErrorMsg", NO_FRONT_SESSION_EXCEPTION);
            return errorUrl;
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSessionDto);
        if (!chk) {
            model.addAttribute("ErrorTitle", "가상계좌 조회");
            model.addAttribute("ErrorMsg", NOT_FULL_MEMBER_EXCEPTION);
            return errorUrl;
        }

        List<MpVirtualAccountNoDto> virtualAccountList = new ArrayList<>();
        boolean isSuccess = true;
        try {
            virtualAccountList = paywayService.getVirtualAccountNoList(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
        } catch (SocketTimeoutException | SelfServiceException e) {
            isSuccess = false;
        }

        if (SessionUtils.getMaskingSession() > 0) {

            // 마스킹 해제되어야 하는 데이터 세팅
            searchVO.setUserName(userSessionDto.getName());

            // 이력 insert
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSessionDto.getUserId());
            maskingDto.setCretId(userSessionDto.getUserId());
            maskingDto.setAmdId(userSessionDto.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        } else {
            // 마스킹 처리되어야 하는 데이터 세팅
            searchVO.setUserName(StringMakerUtil.getName(userSessionDto.getName()));
        }

        model.addAttribute("isSuccess", isSuccess);
        model.addAttribute("userName", searchVO.getUserName());
        model.addAttribute("virtualAccountList", virtualAccountList);

        return url;
    }
}

