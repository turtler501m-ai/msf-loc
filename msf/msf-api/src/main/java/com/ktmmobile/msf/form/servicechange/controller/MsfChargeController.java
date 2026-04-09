package com.ktmmobile.msf.form.servicechange.controller;

import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.TIME_OVERLAP_EXCEPTION;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.service.MsfChargeService;
import com.ktmmobile.msf.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfRealTimePayService;
import com.ktmmobile.msf.common.dto.AuthSmsDto;
import com.ktmmobile.msf.common.dto.JsonReturnDto;
import com.ktmmobile.msf.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.common.dto.UserSessionDto;
import com.ktmmobile.msf.common.exception.McpCommonException;
import com.ktmmobile.msf.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.common.exception.SelfServiceException;
import com.ktmmobile.msf.common.mplatform.vo.MpFarMonBillingInfoDto;
import com.ktmmobile.msf.common.mplatform.vo.MpFarMonDetailInfoDto;
import com.ktmmobile.msf.common.mplatform.vo.MpFarPaymentInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpFarPaymentInfoVO.ItemPay;
import com.ktmmobile.msf.common.mplatform.vo.MpFarPriceInfoDetailItemVO;
import com.ktmmobile.msf.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpFarRealtimePayInfoVO.RealFareVO;
import com.ktmmobile.msf.common.mplatform.vo.MpMonthPayMentDto;
import com.ktmmobile.msf.common.service.IpStatisticService;
import com.ktmmobile.msf.common.util.DateTimeUtil;
import com.ktmmobile.msf.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.common.util.SessionUtils;
import com.ktmmobile.msf.common.util.StringMakerUtil;
import com.ktmmobile.msf.common.util.StringUtil;


@Controller
public class MsfChargeController {

    private static final Logger logger = LoggerFactory.getLogger(MsfChargeController.class);

    @Autowired
    private MsfMypageSvc msfMypageSvc ;

    @Autowired
    private MsfChargeService chargeService;

    @Autowired
    private MsfRealTimePayService realTimePayService;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    /**
     * 월별요금조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = {"/mypage/chargeView01.do"})
    public String doChargeView01(HttpServletRequest request, Model model,
              @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String returnUrl = "";
        String redirectUrl = "";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                 returnUrl = "/mobile/mypage/chargeView01";
                 redirectUrl = "/m/mypage/chargeView01.do";
        }else {
               returnUrl = "/portal/mypage/chargeView01";
               redirectUrl = "/mypage/chargeView01.do";
        }

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(redirectUrl);
        checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if ( userSession != null ) { // 취약성 306
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }
        MpFarMonDetailInfoDto detailInfo = null;

        //x15
        MpFarMonBillingInfoDto billInfo = null ;

        try {
            billInfo = chargeService.farMonBillingInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), DateTimeUtil.getFormatString("yyyyMM"));
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        if(billInfo==null ){
            logger.error("chargeView01 billInfo NULL USERID##="+userSession.getUserId());
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        List<MpMonthPayMentDto> monthList = billInfo.getMonthList();

        /*
         * KOS 변경 사항 수정
         * 인자값 BillSeqNo 이 BillMonth
         */

        String billMonth = searchVO.getBillMonth();
        MpMonthPayMentDto monthPay = null;

        if(monthList != null && monthList.size() > 0){
            if(StringUtil.isNotNull(billMonth)){
                for( MpMonthPayMentDto item : monthList ){
                    if(StringUtil.equals(item.getBillMonth(), billMonth)){
                        monthPay = item;
                        break;
                    }
                }
            }

            if(monthPay == null){
                monthPay = monthList.get(0);
            }
        }
        //X16 월별요금조회 상세
        if(monthPay != null){
             try {
                 detailInfo = chargeService.farMonDetailInfoDto(searchVO.getNcn()
                         , searchVO.getCtn()
                         , searchVO.getCustId()
                         , monthPay.getBillSeqNo()
                         , monthPay.getBillDueDateList()
                         , monthPay.getBillMonth()
                         , monthPay.getBillStartDate()
                         , monthPay.getBillEndDate());
             } catch (SelfServiceException e) {
                 logger.error("Exception e : {}", e.getMessage());
             }  catch (Exception e) {
                 logger.error("Exception e : {}", e.getMessage());
             }
        }

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            searchVO.setCtn(searchVO.getCtn());
            searchVO.setUserName(userSession.getName());

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

        }else {
            searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
            searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        }

        model.addAttribute("billInfo", billInfo);
        model.addAttribute("monthPay", monthPay);
        model.addAttribute("detailInfo", detailInfo);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);

        return returnUrl;

    }

    /**
     * 월별요금조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = {"/m/mypage/chargeView01.do" })
    public String doMoChargeView01(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String returnUrl = "";
        String redirectUrl = "";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                 returnUrl = "/mobile/mypage/chargeView01";
                 redirectUrl = "/m/mypage/chargeView01.do";
        }else {
               returnUrl = "/portal/mypage/chargeView01";
               redirectUrl = "/mypage/chargeView01.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(redirectUrl);
        checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 309
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }
        MpFarMonDetailInfoDto detailInfo = null;

        //x15
        MpFarMonBillingInfoDto billInfo = null ;

        try {
            billInfo = chargeService.farMonBillingInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), DateTimeUtil.getFormatString("yyyyMM"));
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        if(billInfo==null ){
            logger.error("chargeView01 billInfo NULL USERID##="+userSession.getUserId());
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        List<MpMonthPayMentDto> monthList = billInfo.getMonthList();

        /*
         * KOS 변경 사항 수정
         * 인자값 BillSeqNo 이 BillMonth
         */

        String billMonth = searchVO.getBillMonth();
        MpMonthPayMentDto monthPay = null;

        if(monthList != null && monthList.size() > 0){
            if(StringUtil.isNotNull(billMonth)){
                for( MpMonthPayMentDto item : monthList ){
                    if(StringUtil.equals(item.getBillMonth(), billMonth)){
                        monthPay = item;
                        break;
                    }
                }
            }

            if(monthPay == null){
                monthPay = monthList.get(0);
            }
        }
        //X16 월별요금조회 상세
        if(monthPay != null){
             try {
                 detailInfo = chargeService.farMonDetailInfoDto(searchVO.getNcn()
                         , searchVO.getCtn()
                         , searchVO.getCustId()
                         , monthPay.getBillSeqNo()
                         , monthPay.getBillDueDateList()
                         , monthPay.getBillMonth()
                         , monthPay.getBillStartDate()
                         , monthPay.getBillEndDate());
             } catch (SelfServiceException e) {
                 logger.error("Exception e : {}", e.getMessage());
             }  catch (Exception e) {
                 logger.error("Exception e : {}", e.getMessage());
             }
        }

        MpFarPaymentInfoVO vo = null;
        try {
            vo = realTimePayService.farPaymentInfoList(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(),"","");
            if(!vo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION);
            }
        } catch (SelfServiceException e) {
            String strMsg = e.getMessage();
            if ( strMsg.indexOf("ITL_SYS_E0001") > -1) {
                strMsg = "조회 가능한 금액을 초과하였습니다.<br> 확인을 원하실 경우 고객센터(1899-5000)으로 문의 부탁드립니다.";
            }
            throw new McpCommonException(strMsg);
        }  catch (Exception e) {

            String strMsg = e.getMessage();
            if ( strMsg.indexOf("ITL_SYS_E0001") > -1) {
                strMsg = "조회 가능한 금액을 초과하였습니다.<br> 확인을 원하실 경우 고객센터(1899-5000)으로 문의 부탁드립니다.";
            }
            throw new McpCommonException(strMsg);
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

        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        model.addAttribute("billInfo", billInfo);
        model.addAttribute("monthPay", monthPay);
        model.addAttribute("detailInfo", detailInfo);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("vo", vo);

        return returnUrl;

    }





    /**
     * 최근 납부 내역
     * @param request
     * @param contractNum
     * @return
     */
    @RequestMapping(value="/mypage/recentPaymentAjax.do")
    @ResponseBody
    public Map<String,Object> recentPaymentAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "ncn", required = true) String ncn1
            ,@RequestParam(value = "startDate", required = true) String startDate,@RequestParam(value = "endDate", required = true) String endDate)  {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String returnCode = "00";
        String message = "";
        String ncn = ncn1;
        String custId = "";
        String ctn = "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
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
        MpFarPaymentInfoVO mpFarPaymentInfoVO = null;
        List<ItemPay> itemPay = null;
        try {
            mpFarPaymentInfoVO = realTimePayService.farPaymentInfoList(ncn, ctn, custId,startDate,endDate);

            if(mpFarPaymentInfoVO !=null) {
                if(!mpFarPaymentInfoVO.isSuccess()) {
                    returnCode = "E";
                    message = "서비스 처리중 오류가 발생 하였습니다.";
                } else {
                    itemPay = mpFarPaymentInfoVO.getItemPay();
                }
            }

        } catch (SelfServiceException e) {
            returnCode = "E";
            message = e.getMessage();
            if(message !=null &&  !"".equals(message)) {
                if ( message.indexOf("ITL_SYS_E0001") > -1) {
                    message = "조회 가능한 금액을 초과하였습니다.<br> 확인을 원하실 경우 고객센터(1899-5000)으로 문의 부탁드립니다.";
                } else {
                    message = "서비스 처리중 오류가 발생 하였습니다.";
                }
            }
        }  catch (Exception e) {

            returnCode = "E";
            message = e.getMessage();
            if(message !=null &&  !"".equals(message)) {
                if ( message.indexOf("ITL_SYS_E0001") > -1) {
                    message = "조회 가능한 금액을 초과하였습니다.<br> 확인을 원하실 경우 고객센터(1899-5000)으로 문의 부탁드립니다.";
                } else {
                    message = "서비스 처리중 오류가 발생 하였습니다.";
                }
            }
        }
        rtnMap.put("returnCode", returnCode);
        rtnMap.put("message", message);
        rtnMap.put("itemPay", itemPay);
        return rtnMap;
    }


    /**
     * 납부내역 엑셀
     * @return
     */
    @RequestMapping("/mypage/chargeMonthListExcelDownload.do")
    public ModelAndView chargeListExcelDownload(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            @RequestParam(value = "startDate", required = true) String startDate,@RequestParam(value = "endDate", required = true) String endDate)  {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 297
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            return null;
        }


        MpFarPaymentInfoVO farPaymentInfo = null;
        try {
            farPaymentInfo = realTimePayService.farPaymentInfoList(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(),startDate,endDate);
            if(!farPaymentInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION);
            }
        } catch (SelfServiceException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        String strUserName = userSession.getName() != null ? userSession.getName() : "" ;
        model.addAttribute("list", farPaymentInfo.getItemPay());
        model.addAttribute("userName", StringMakerUtil.getName(strUserName));
        model.addAttribute("userCtn", StringMakerUtil.getPhoneNum(searchVO.getCtn()));

// PNB_확인
//        ModelAndView mav =  new ModelAndView(new ChargeMonthListExcelView());
//        mav.addAllObjects(model);
//        return mav;
      ModelAndView mav =  new ModelAndView();
      mav.addAllObjects(model);
      return mav;        
    }

    /**
     * 실시간 요금조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     * @throws ParseException
     * @throws SocketTimeoutException
     * @throws SelfServiceException
     */

    @RequestMapping(value = { "/mypage/chargeView03.do", "/m/mypage/chargeView03.do" })
    public String chargeView03(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO ) {

        String returnUrl = "/portal/mypage/chargeView03";
        String redirectUrl = "/mypage/chargeView03.do";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/chargeView03";
            redirectUrl = "/m/mypage/chargeView03.do";
        }

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(redirectUrl);
        checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 297
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String ctn = searchVO.getCtn();
        String contractNum = searchVO.getContractNum();
        //요금제조회
        String rateNm = "";
        McpUserCntrMngDto socMngDto = msfMypageSvc.selectSocDesc(contractNum); //요금제
        if(socMngDto !=null) {
            rateNm = StringUtil.NVL(socMngDto.getRateNm(),"-");
        }
        //x18 실시간 요금조회
        MpFarRealtimePayInfoVO mpFarRealtimePayInfoVO = null;
        String useDay = "0";
        int useTotalDay = 0;
        String searchDay = "";
        String searchTime = "";
        String sumAmt = "0";
        List<RealFareVO> realFareVOList = null;
        try {
            mpFarRealtimePayInfoVO = chargeService.farRealtimePayInfo(ncn, ctn, custId);
            if(mpFarRealtimePayInfoVO != null) {
                searchDay = mpFarRealtimePayInfoVO.getSearchDay();
                useDay = searchDay.substring(6,8);
                searchTime = mpFarRealtimePayInfoVO.getSearchTime();
                sumAmt = StringUtil.addComma(mpFarRealtimePayInfoVO.getSumAmt());
                realFareVOList = mpFarRealtimePayInfoVO.getList();
                if(realFareVOList !=null && !realFareVOList.isEmpty()) {
                    for(RealFareVO dto : realFareVOList) {
                        String payment = StringUtil.NVL(dto.getPayment(),"0");
                        payment = StringUtil.addComma(payment);
                        dto.setPayment(payment);
                    }
                }
            }
        } catch (SelfServiceException e) {
            logger.info("X18 ERROR");
        }  catch (Exception e) {
            logger.info("X18 ERROR");
        }

        try {
            if(!"".equals(searchDay) && searchDay.length() >= 8) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
                formatter.parse(searchDay);
                String yy = searchDay.substring(0, 4);
                String mm = searchDay.substring(4, 6);
                String day = searchDay.substring(6,8);

                cal.set(Integer.parseInt(yy),Integer.parseInt(mm)-1, Integer.parseInt(day));
                useTotalDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
        } catch(IllegalArgumentException e) {
            logger.info("SimpleDateFormat ERROR");
        } catch(Exception e) {
            logger.info("SimpleDateFormat ERROR");
        }

        String grapWidth = "0";
        double width = 0l;
        try {

            double dUseDay = Double.parseDouble(useDay);
            double dUseTotalDay =  Double.valueOf(useTotalDay);
            width =  (dUseDay/dUseTotalDay)*100;
            grapWidth = String.format("%.2f", width);

        } catch(NumberFormatException e) {
            logger.info("NumberFormatException ERROR");
        } catch(Exception e) {
            logger.info("width ERROR");
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

        model.addAttribute("grapWidth", grapWidth);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("realFareVOList", realFareVOList);
        model.addAttribute("useDay", useDay);
        model.addAttribute("useTotalDay", useTotalDay);
        model.addAttribute("searchDay", searchDay);
        model.addAttribute("searchTime", searchTime);
        model.addAttribute("rateNm", rateNm);
        model.addAttribute("sumAmt", sumAmt);

        return returnUrl;
    }

    /**
     * 요금 항목별 조회 ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param billMonth
     * @param messageLine
     * @return
     */

    @RequestMapping(value = { "/mypage/chargeDetailItemAjax.do", "/m/mypage/chargeDetailItemAjax.do" })
    @ResponseBody
    public JsonReturnDto chargeDetailItemAjax(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO, String billMonth, String messageLine)  {

        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 밸생했습니다. 다시 시도해 주세요.";
        Object json = null;
        MpFarPriceInfoDetailItemVO item = null;

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 303
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
        }

        if(billMonth != null && messageLine != null){
            item = chargeService.farPriceInfoDetailItem
                    (searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(),
                            "0", billMonth
                            , messageLine );
            json = item.getList();
             returnCode = "00";
             message = "";
        }

        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(json);

        return result;
    }

    /**
     * 월별요금조회 프린트
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping("/mypage/chargeViewPrint.do")
    public String chargeViewPrint(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 293
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }
        String returnUrl = "/portal/mypage/chargeViewPrint";


        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }
        MpFarMonBillingInfoDto billInfo = chargeService.farMonBillingInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), DateTimeUtil.getFormatString("yyyyMM"));
        if(!billInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
            throw new McpCommonException(COMMON_EXCEPTION,"/main.do");
        }

        model.addAttribute("billInfo", billInfo);
        List<MpMonthPayMentDto> monthList = billInfo.getMonthList();
        String billMonth = searchVO.getBillMonth();

        MpMonthPayMentDto monthPay = null;
        MpFarMonDetailInfoDto detailInfo = null;

        if(monthList != null && monthList.size() > 0){
            if(StringUtil.isNotNull(billMonth)){
                for( MpMonthPayMentDto item : monthList ){
                    if(StringUtil.equals(item.getBillMonth(), billMonth)){
                        monthPay = item;
                        break;
                    }
                }
            }else{
                monthPay = monthList.get(0);
            }
        }

        if(monthPay != null){
            detailInfo = chargeService.farMonDetailInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(),
                    monthPay.getBillSeqNo(), monthPay.getBillDueDateList(), monthPay.getBillMonth(),
                    monthPay.getBillStartDate(), monthPay.getBillEndDate() );

            if(!detailInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION);
            }
        }
        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        model.addAttribute("monthPay", monthPay);
        model.addAttribute("detailInfo", detailInfo);

        model.addAttribute("cntrList", cntrList);
        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        model.addAttribute("searchVO", searchVO);
        return returnUrl;
    }

    /**
     * 월별요금조회 프린트
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping("/mypage/chargeMonPrint.do")
    public String chargeMonPrint(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, Model model,@RequestParam(value = "ncn", required = true) String ncn1
            ,@RequestParam(value = "startDate", required = true) String startDate,@RequestParam(value = "endDate", required = true) String endDate)  {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String returnCode = "00";
        String message = "";
        String ncn = ncn1;
        String custId = "";
        String ctn = "";

        String returnUrl = "/portal/mypage/chargeMonPrint";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 301
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }
        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }
        custId = searchVO.getCustId();
        ctn = searchVO.getCtn();

        //SMS인증 여부 확인
        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setPhoneNum(ctn);
        authSmsDto.setMenu("chargePrint");
        authSmsDto.setCheck(true);

//PNB_확인        SessionUtils.checkAuthSmsSession(authSmsDto);

        if(!authSmsDto.isResult()) {
            throw new McpCommonJsonException("01",authSmsDto.getMessage());
        }

        //SMS 인증정보 초기화
        //실행할때 마다 SMS 인증 처리
//PNB_확인        SessionUtils.setAuthSmsSetNullSession(authSmsDto);


        MpFarPaymentInfoVO mpFarPaymentInfoVO = null;
        List<ItemPay> itemPay = null;
        try {
            mpFarPaymentInfoVO = realTimePayService.farPaymentInfoList(ncn, ctn, custId,startDate,endDate);
            if(mpFarPaymentInfoVO !=null && mpFarPaymentInfoVO.isSuccess()) {
                itemPay = mpFarPaymentInfoVO.getItemPay();
                if(itemPay !=null && !itemPay.isEmpty()) {
                    for(ItemPay dto : itemPay) {
                        // 날짜포멧
                        String payMentDate = StringUtil.NVL(dto.getPayMentDate(),"");
                        if(!"".equals(payMentDate) && payMentDate.length() > 7) {
                            payMentDate = payMentDate.substring(0,4)+"."+payMentDate.substring(4,6)+"."+payMentDate.substring(6,8);
                            dto.setPayMentDate(payMentDate);
                        }
                        // 금액포멧
                        String payMentMoney = StringUtil.NVL(dto.getPayMentMoney(),"0");
                        payMentMoney = StringUtil.addComma(payMentMoney);
                        dto.setPayMentMoney(payMentMoney);
                    }
                }
               }

        } catch (SelfServiceException e) {
            logger.info("error:"+e.getMessage());
        }  catch (Exception e) {
            logger.info("error:"+e.getMessage());
        }
        model.addAttribute("itemPay", itemPay);
        /*searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));*/

        String[] nums = StringUtil.getMobileNum(searchVO.getCtn());
        String telNo= nums[0]+"-"+nums[1]+"-"+nums[2];

        searchVO.setUserName(userSession.getName());
        searchVO.setCtn(telNo);
        model.addAttribute("searchVO", searchVO);
        return returnUrl;
    }

    /**
     * 월별요금조회 엑셀
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping("/mypage/chargeView01ExcelDownload.do")
    public ModelAndView chargeView01ExcelDownload(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        //List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 297
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }

        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            return null;
        }

        MpMonthPayMentDto monthPay;
        MpFarMonDetailInfoDto detailInfo;

        MpFarMonBillingInfoDto billInfo = chargeService.farMonBillingInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), DateTimeUtil.getFormatString("yyyyMM"));
        if(!billInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
            throw new McpCommonException(COMMON_EXCEPTION,"/main.do");
        }

        List<MpMonthPayMentDto> monthList = billInfo.getMonthList();
        String billMonth = searchVO.getBillMonth();//청구일련번호
        monthPay = null;
        detailInfo = null;
        if(monthList != null && monthList.size() > 0){
            if(StringUtil.isNotNull(billMonth)){
                for(MpMonthPayMentDto item : monthList ){
                    if(StringUtil.equals(item.getBillMonth(), billMonth)){
                        monthPay = item;
                        break;
                    }
                }
            }else{
                monthPay = monthList.get(0);
            }
        }

        if(monthPay != null){
            detailInfo = chargeService.farMonDetailInfoDto(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(),
                    monthPay.getBillSeqNo(), monthPay.getBillDueDateList(), monthPay.getBillMonth(),
                    monthPay.getBillStartDate(), monthPay.getBillEndDate() );

            if(!detailInfo.isSuccess()){//mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION,"/main.do");
            }
        }

        String strUserName = userSession.getName() != null ? userSession.getName() : "" ;
        model.addAttribute("list", detailInfo.getItemList());
        model.addAttribute("total", monthPay);
        model.addAttribute("userName", StringMakerUtil.getName(strUserName));
        model.addAttribute("userCtn", StringMakerUtil.getPhoneNum(searchVO.getCtn()));

//PNB_확인        
//        ModelAndView mav =  new ModelAndView(new ChargeExcleView());
//        mav.addAllObjects(model);
//        return mav;        

        ModelAndView mav =  new ModelAndView();
        mav.addAllObjects(model);
        return mav;
    }


    private ResponseSuccessDto getMessageBox() {
        ResponseSuccessDto mbox = new ResponseSuccessDto();
        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }

        mbox.setRedirectUrl(url);
        mbox.setSuccessMsg(NOT_FULL_MEMBER_EXCEPTION);
        return mbox;
    }

    @Deprecated
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
}
