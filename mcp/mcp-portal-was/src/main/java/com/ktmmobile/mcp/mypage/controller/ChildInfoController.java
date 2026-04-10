package com.ktmmobile.mcp.mypage.controller;

import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.vo.*;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.mypage.dto.*;
import com.ktmmobile.mcp.mypage.service.*;
import com.ktmmobile.mcp.mypage.view.ChargeExcleView;
import com.ktmmobile.mcp.mypage.view.ChargeMonthListExcelView;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarRealtimePayInfoVO.RealFareVO;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarPaymentInfoVO.ItemPay;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class ChildInfoController {

    private static Logger logger = LoggerFactory.getLogger(ChildInfoController.class);

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MyinfoService myinfoService;

    @Autowired
    private ChildInfoService childInfoService;

    @Autowired
    private FarPricePlanService farPricePlanService;

    @Autowired
    private PaywayService paywayService;

    @Autowired
    private CallRegService callRegService;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private RealTimePayService realTimePayService;

    @Autowired
    private RegSvcService regSvcService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    /**
     * 설명 : 자녀 가입정보 첫화면
     *
     * @param request
     * @param model
     * @param searchVO
     * @return String
     * @Date : 2024.09.12
     */
    @RequestMapping(value = {"/mypage/childInfoView.do", "/m/mypage/childInfoView.do"})
    public String childInfoView(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "childNcn", required = false) String childNcn) {

        String pageUrl = "";

        // 중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/mypage/childinfo/childInfoView";
            checkOverlapDto.setRedirectUrl("/m/mypage/childInfoView.do");
        } else {
            pageUrl = "/portal/mypage/childinfo/childInfoView";
            checkOverlapDto.setRedirectUrl("/mypage/childInfoView.do");
        }

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        // 로그인 체크
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String ncn = searchVO.getNcn();
        String childContractNum = "";

        // 법정대리인 정보 조회
        ChildInfoDto minorAgent = childInfoService.getMinorAgentInfo(ncn);
        if ( minorAgent == null || !"Y".equals(minorAgent.getUseYn()) ) {
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            responseSuccessDto.setSuccessMsg("고객센터를 통해 가족 관계 등록을 하신 법정대리인만 이용 가능합니다.");
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        // 자녀 회선 목록 조회
        ChildInfoDto childInfo = new ChildInfoDto();
        List<ChildInfoDto> childCntrList = childInfoService.selectChildCntrList(minorAgent.getFamSeq());

        if (childCntrList != null && childCntrList.size() > 0) {
            if (childNcn != null && !"".equals(childNcn)) {
                for (ChildInfoDto dto : childCntrList) {
                    if (childNcn.equals(dto.getSvcCntrNo())) {
                        childInfo = dto;
                        break;
                    }
                }
            } else {
                childInfo = childCntrList.get(0);
            }
            childContractNum = childInfo.getContractNum();
            childInfo.setMoCtn(childCntrList.size());
            if(SessionUtils.getMaskingSession() > 0 ) {
                model.addAttribute("maskingSession", "Y");
                childInfo.setName(childInfo.getName());

                MaskingDto maskingDto = new MaskingDto();

                long maskingRelSeq = SessionUtils.getMaskingSession();
                maskingDto.setMaskingReleaseSeq(maskingRelSeq);
                maskingDto.setUnmaskingInfo("자녀이름,자녀휴대폰번호,납부정보");
                maskingDto.setAccessIp(ipstatisticService.getClientIp());
                maskingDto.setAccessUrl(request.getRequestURI());
                maskingDto.setUserId(userSession.getUserId());
                maskingDto.setCretId(userSession.getUserId());
                maskingDto.setAmdId(userSession.getUserId());
                maskingSvc.insertMaskingReleaseHist(maskingDto);

            }else {
                childInfo.setName(StringMakerUtil.getName(childInfo.getName()));
            }
        } else {
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            responseSuccessDto.setSuccessMsg("고객센터를 통해 가족 관계 등록을 하신 법정대리인만 이용 가능합니다.");
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        // 1. 전산조회 모델ID 조회
        McpUserCntrMngDto selectSocDesc = mypageService.selectSocDesc(childContractNum);
        String modelName = "-";
        if (selectSocDesc != null) {
            modelName = StringUtil.NVL(selectSocDesc.getModelName(), "-");
        }
        model.addAttribute("modelName", modelName);

        // 3. 사용중인 요금제
        McpFarPriceDto mcpFarPriceDto = null;
        FarPricePlanResDto farPricePlanResDto = new FarPricePlanResDto();
        String prvRateGrpNm = "-";          // 요금제명
        String rateAdsvcLteDesc = "- MB";   // 데이터
        String rateAdsvcCallDesc = "- 분";   // 음성
        String rateAdsvcSmsDesc = "- 건";    // sms

        try {
            mcpFarPriceDto = mypageService.selectFarPricePlan(childContractNum);
            if (mcpFarPriceDto != null) {
                prvRateGrpNm = mcpFarPriceDto.getPrvRateGrpNm();
                farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);
            }
            rateAdsvcLteDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcLteDesc(), "- MB");
            rateAdsvcCallDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcCallDesc(), "- 분");
            rateAdsvcSmsDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcSmsDesc(), "- 건");

        } catch (SelfServiceException e) {
            logger.info("error");
        } catch (Exception e) {
            logger.info("error");
        }

        model.addAttribute("prvRateGrpNm", prvRateGrpNm);
        model.addAttribute("rateAdsvcLteDesc", rateAdsvcLteDesc);
        model.addAttribute("rateAdsvcCallDesc", rateAdsvcCallDesc);
        model.addAttribute("rateAdsvcSmsDesc", rateAdsvcSmsDesc);

        // 고객정보
        model.addAttribute("childCntrList", childCntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("childInfo", childInfo);
        model.addAttribute("caller", "Main");

        return pageUrl;
    }

    /**
     * 설명 : 이용량 조회
     *
     * @param request
     * @param model
     * @param searchVO
     * @param useMonth
     * @return String
     * @Date : 2024.09.12
     */
    @RequestMapping(value = {"/mypage/childCallView.do", "/m/mypage/childCallView.do"})
    public String doChildCallView(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "useMonth", required = false) String useMonth, @RequestParam(value = "childNcn", required = false) String childNcn) {

        String returnUrl = "/portal/mypage/childinfo/childCallView";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/childinfo/childCallView";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) {
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            return returnUrl;
        }

        String ncn = searchVO.getNcn();
        String childContractNum = "";

        ChildInfoDto minorAgent = childInfoService.getMinorAgentInfo(ncn);

        ChildInfoDto childInfo = new ChildInfoDto();
        List<ChildInfoDto> childCntrList = childInfoService.selectChildCntrList(minorAgent.getFamSeq());

        if (childCntrList != null && childCntrList.size() > 0) {
            if (childNcn != null && !"".equals(childNcn)) {
                for (ChildInfoDto dto : childCntrList) {
                    if (childNcn.equals(dto.getSvcCntrNo())) {
                        childInfo = dto;
                    }
                }
            } else {
                childInfo = childCntrList.get(0);
            }

            childContractNum = childInfo.getContractNum();
            childInfo.setName(StringMakerUtil.getName(childInfo.getName()));
            childInfo.setMoCtn(childCntrList.size());
        }


        try {
            String strSvcNameSms = "";

            // 2. 요금제 조회
            McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(childContractNum);
            if (mcpFarPriceDto != null) {
                strSvcNameSms = mcpFarPriceDto.getPrvRateGrpNm();
            } else {
                mcpFarPriceDto = new McpFarPriceDto();
            }

            // 2. 요금제 상세정보
            FarPricePlanResDto farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);
            if (farPricePlanResDto != null) {
                mcpFarPriceDto.setRateAdsvcLteDesc(farPricePlanResDto.getRateAdsvcLteDesc());
                mcpFarPriceDto.setRateAdsvcCallDesc(farPricePlanResDto.getRateAdsvcCallDesc());
                mcpFarPriceDto.setRateAdsvcSmsDesc(farPricePlanResDto.getRateAdsvcSmsDesc());
            }

            //////로그 END ->
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
            Date currentTime = new Date();
            String today = formatter.format(currentTime);
            String lastDay = "";
            try {
                lastDay = DateTimeUtil.lastDayOfMonth(today);
            } catch (ParseException e) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            List<String> list = new ArrayList<>();

            // 3-1. 당월 시작일 ~ 현재일
            String startDate = DateTimeUtil.getFormatString(lastDay.substring(0, 4) + lastDay.substring(4, 6) + "01", "yyyyMMdd", "yyyy.MM.dd");
            String endDate = DateTimeUtil.getFormatString(DateTimeUtil.getShortDateString(), "yyyyMMdd", "yyyy.MM.dd");

            // 3-2. 한 달 전 시작일 ~ 마지막일
            String prvDateOne = DateTimeUtil.addMonths(lastDay, -1);
            String prvStartDateOne = DateTimeUtil.getFormatString(prvDateOne.substring(0, 4) + prvDateOne.substring(4, 6) + "01", "yyyyMMdd", "yyyy.MM.dd");
            String prvEndDateOne = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(prvDateOne), "yyyyMMdd", "yyyy.MM.dd");

            // 3-3. 두 달 전 시작일 ~ 마지막일
            String prvDateTwo = DateTimeUtil.addMonths(lastDay, -2);
            String prvStartDateTwo = DateTimeUtil.getFormatString(prvDateTwo.substring(0, 4) + prvDateTwo.substring(4, 6) + "01", "yyyyMMdd", "yyyy.MM.dd");
            String prvEndDateTwo = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(prvDateTwo), "yyyyMMdd", "yyyy.MM.dd");

            // 3-4. 세 달 전 시작일 ~ 마지막일
            String prvDateThree = DateTimeUtil.addMonths(lastDay, -3);
            String prvStartDateThree = DateTimeUtil.getFormatString(prvDateThree.substring(0, 4) + prvDateThree.substring(4, 6) + "01", "yyyyMMdd", "yyyy.MM.dd");
            String prvEndDateThree = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(prvDateThree), "yyyyMMdd", "yyyy.MM.dd");

            list.add(startDate + "~" + endDate);
            list.add(prvStartDateOne + "~" + prvEndDateOne);
            list.add(prvStartDateTwo + "~" + prvEndDateTwo);
            list.add(prvStartDateThree + "~" + prvEndDateThree);

            String[] todayList = list.toArray(new String[list.size()]);

            model.addAttribute("todayList", todayList);

            int useTime = Integer.parseInt(lastDay) - Integer.parseInt(today);

            model.addAttribute("today", today.substring(6));
            model.addAttribute("firstDay", today.substring(0, 6));
            model.addAttribute("todayOrign", today);
            model.addAttribute("lastDay", lastDay.substring(6));
            model.addAttribute("useTime", useTime);
            model.addAttribute("childCntrList", childCntrList);
            model.addAttribute("childInfo", childInfo);
            model.addAttribute("searchVO", searchVO);
            model.addAttribute("mcpFarPriceDto", mcpFarPriceDto);
            model.addAttribute("strSvcNameSms", strSvcNameSms);
            model.addAttribute("useMonth", useMonth);
            model.addAttribute("caller", "Detail");

        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
            searchVO.setMessage(getErrMsg(e.getMessage()));
        } catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        model.addAttribute("message", searchVO.getMessage());
        return returnUrl;
    }

    /**
     * 설명 : 월별요금 조회
     *
     * @param request
     * @param model
     * @param searchVO
     * @return String
     * @Date : 2024.09.13
     */
    @RequestMapping(value = {"/mypage/childChargeView.do", "/m/mypage/childChargeView.do"})
    public String doChildChargeView(HttpServletRequest request, Model model,
                                        @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                        @RequestParam(value = "childNcn", required = false) String childNcn,
                                        @RequestParam(value = "billMonth", required = false) String billMonth) {

        String returnUrl = "/portal/mypage/childinfo/childChargeView";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/childinfo/childChargeView";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) {
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            return returnUrl;
        }

        String ncn = searchVO.getNcn();
        ChildInfoDto minorAgent = childInfoService.getMinorAgentInfo(ncn);

        ChildInfoDto childInfo = new ChildInfoDto();
        List<ChildInfoDto> childCntrList = childInfoService.selectChildCntrList(minorAgent.getFamSeq());
        if (childCntrList != null && childCntrList.size() > 0) {
            if (childNcn != null && !"".equals(childNcn)) {
                for (ChildInfoDto dto : childCntrList) {
                    if (childNcn.equals(dto.getSvcCntrNo())) {
                        childInfo = dto;
                        break;
                    }
                }
            } else {
                childInfo = childCntrList.get(0);
            }
            childInfo.setName(StringMakerUtil.getName(childInfo.getName()));
            childInfo.setMoCtn(childCntrList.size());
        }

        /*if ( billMonth == null || "".equals(billMonth) ) {
            billMonth = DateTimeUtil.getFormatString("yyyyMM");
        }*/
        String billMonth2 = DateTimeUtil.getFormatString("yyyyMM");

        model.addAttribute("childCntrList", childCntrList);
        model.addAttribute("childInfo", childInfo);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("billMonth", (billMonth == null || "".equals(billMonth)) ? billMonth2 : billMonth);
        model.addAttribute("caller", "Detail");

        return returnUrl;
    }

    /**
     * 설명 : 월별 요금 조회
     *
     * @param request
     * @param searchVO
     * @param billMonth
     * @return Map
     * @Date : 2024.10.04
     */
    @RequestMapping(value = "/mypage/getMonthBillListAjax.do")
    @ResponseBody
    public Map<String, Object> getMonthBillListAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                      @RequestParam(value = "childNcn", required = true) String childNcn,
                                                      @RequestParam(value = "billMonth", required = false) String billMonth) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonJsonException("10001", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("10002", COMMON_EXCEPTION);
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonJsonException("10003", "회선 정보를 확인할 수 없습니다.");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        if ("".equals(childCustId) || "".equals(childCtn)) {
            throw new McpCommonJsonException("10004", F_BIND_EXCEPTION);
        }

        MpFarMonDetailInfoDto detailInfo = new MpFarMonDetailInfoDto();
        // X15 요금조회
        MpFarMonBillingInfoDto billInfo = null;

        try {
            billInfo = chargeService.farMonBillingInfoDto(childNcn, childCtn, childCustId, DateTimeUtil.getFormatString("yyyyMM"));
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        if (billInfo == null) {
            logger.error("getMonthBillListAjax billInfo NULL USERID##=" + userSession.getUserId());
            throw new McpCommonJsonException("10005", COMMON_EXCEPTION);
        }

        List<MpMonthPayMentDto> monthList = billInfo.getMonthList();

        MpMonthPayMentDto monthPay = null;

        if (monthList != null && monthList.size() > 0) {
            if (StringUtil.isNotNull(billMonth)) {
                for (MpMonthPayMentDto item : monthList) {
                    if (StringUtil.equals(item.getBillMonth(), billMonth)) {
                        monthPay = item;
                        break;
                    }
                }
            }

            if (monthPay == null) {
                monthPay = monthList.get(0);
            }
        }

        // X16 월별요금조회 상세
        if (monthPay != null) {
            try {
                detailInfo = chargeService.farMonDetailInfoDto(childNcn, childCtn, childCustId
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

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "성공");
        rtnMap.put("monthList", monthList);
        rtnMap.put("monthPay", monthPay);
        rtnMap.put("itemList", detailInfo.getItemList());
        rtnMap.put("installmentAmt", detailInfo.getInstallmentAmt());
        rtnMap.put("installmentYN", detailInfo.getInstallmentYN());
        rtnMap.put("totalNoOfInstall", detailInfo.getTotalNoOfInstall());
        rtnMap.put("lastInstallMonth", detailInfo.getLastInstallMonth());
        return rtnMap;
    }

    /**
     * 설명 : 납부내역 목록 조회
     *
     * @param request
     * @param searchVO
     * @param startDate
     * @param endDate
     * @return Map
     * @Date : 2024.09.23
     */
    @RequestMapping(value = "/mypage/childRecentPaymentAjax.do")
    @ResponseBody
    public Map<String, Object> childRecentPaymentAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                      @RequestParam(value = "childNcn", required = true) String childNcn,
                                                      @RequestParam(value = "startDate", required = true) String startDate,
                                                      @RequestParam(value = "endDate", required = true) String endDate) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonJsonException("10001", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("10002", COMMON_EXCEPTION);
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonJsonException("10003", "회선 정보를 확인할 수 없습니다.");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        if ("".equals(childCustId) || "".equals(childCtn)) {
            throw new McpCommonJsonException("10004", F_BIND_EXCEPTION);
        }

        // 납부/미납요금 조회 X22
        MpFarPaymentInfoVO mpFarPaymentInfoVO = null;
        try {
            mpFarPaymentInfoVO = realTimePayService.farPaymentInfoList(childNcn, childCtn, childCustId, startDate, endDate);
        } catch (Exception e) {
            String message = e.getMessage();
            if(message !=null &&  !"".equals(message)) {
                if ( message.indexOf("ITL_SYS_E0001") > -1) {
                    throw new McpCommonJsonException("10005", "조회 가능한 금액을 초과하였습니다.<br> 확인을 원하실 경우 고객센터(1899-5000)으로 문의 부탁드립니다.");
                }
            }

            throw new McpCommonJsonException("10006", COMMON_EXCEPTION);
        }

        List<ItemPay> itemPay = null;
        if (mpFarPaymentInfoVO != null) {
            if ( !mpFarPaymentInfoVO.isSuccess() ) {
                throw new McpCommonJsonException("10007", COMMON_EXCEPTION);
            }

            itemPay = mpFarPaymentInfoVO.getItemPay();
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "성공");
        rtnMap.put("itemPay", itemPay);
        return rtnMap;
    }

    /**
     * 설명 : 청구요금 상세 엑셀
     *
     * @param request
     * @param model
     * @param searchVO
     * @return ModelAndView
     * @Date : 2024.09.23
     */
    @RequestMapping("/mypage/childChargeViewExcelDownload.do")
    public ModelAndView childChargeViewExcelDownload(HttpServletRequest request, ModelMap model,
                                                     @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                     @RequestParam(value = "childNcn", required = true) String childNcn) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonException(NO_SESSION_EXCEPTION, "/main.do");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        MpMonthPayMentDto monthPay;
        MpFarMonDetailInfoDto detailInfo;

        MpFarMonBillingInfoDto billInfo = chargeService.farMonBillingInfoDto(childNcn, childCtn, childCustId, DateTimeUtil.getFormatString("yyyyMM"));
        if (!billInfo.isSuccess()) { // mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }

        List<MpMonthPayMentDto> monthList = billInfo.getMonthList();
        String billMonth = searchVO.getBillMonth();
        monthPay = null;
        detailInfo = null;
        if (monthList != null && monthList.size() > 0) {
            if (StringUtil.isNotNull(billMonth)) {
                for (MpMonthPayMentDto item : monthList) {
                    if (StringUtil.equals(item.getBillMonth(), billMonth)) {
                        monthPay = item;
                        break;
                    }
                }
            } else {
                monthPay = monthList.get(0);
            }
        }

        if (monthPay != null) {
            detailInfo = chargeService.farMonDetailInfoDto(childNcn, childCtn, childCustId,
                    monthPay.getBillSeqNo(), monthPay.getBillDueDateList(), monthPay.getBillMonth(),
                    monthPay.getBillStartDate(), monthPay.getBillEndDate());

            if (!detailInfo.isSuccess()) { // mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
            }
        }

        String strUserName = childCntr.getName() != null ? childCntr.getName() : "";
        model.addAttribute("list", detailInfo.getItemList());
        model.addAttribute("total", monthPay);
        model.addAttribute("userName", StringMakerUtil.getName(strUserName));
        model.addAttribute("userCtn", StringMakerUtil.getPhoneNum(childCntr.getMobileNo()));

        ModelAndView mav = new ModelAndView(new ChargeExcleView());
        mav.addAllObjects(model);
        return mav;
    }

    /**
     * 설명 : 납부내역 목록 엑셀
     *
     * @param request
     * @param model
     * @param searchVO
     * @param startDate
     * @param endDate
     * @return ModelAndView
     * @Date : 2024.09.23
     */
    @RequestMapping("/mypage/childChargeMonthListExcelDownload.do")
    public ModelAndView childChargeListExcelDownload(HttpServletRequest request, ModelMap model,
                                                     @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                     @RequestParam(value = "childNcn", required = true) String childNcn,
                                                     @RequestParam(value = "startDate", required = true) String startDate,
                                                     @RequestParam(value = "endDate", required = true) String endDate) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonException(NO_SESSION_EXCEPTION, "/main.do");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        MpFarPaymentInfoVO farPaymentInfo = null;
        try {
            farPaymentInfo = realTimePayService.farPaymentInfoList(childNcn, childCtn, childCustId, startDate, endDate);
            if (!farPaymentInfo.isSuccess()) { // mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION);
            }
        } catch (SelfServiceException e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        String strUserName = childCntr.getName() != null ? childCntr.getName() : "";
        model.addAttribute("list", farPaymentInfo.getItemPay());
        model.addAttribute("userName", StringMakerUtil.getName(strUserName));
        model.addAttribute("userCtn", StringMakerUtil.getPhoneNum(childCntr.getMobileNo()));

        ModelAndView mav = new ModelAndView(new ChargeMonthListExcelView());
        mav.addAllObjects(model);
        return mav;
    }

    /**
     * 설명 : 청구요금 상세 프린트
     *
     * @param request
     * @param model
     * @param searchVO
     * @return String
     * @Date : 2024.09.23
     */
    @RequestMapping("/mypage/childChargeViewPrint.do")
    public String childChargeViewPrint(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                     @RequestParam(value = "childNcn", required = true) String childNcn) {

        String returnUrl = "/portal/mypage/childinfo/childChargeViewPrint";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonException(NO_SESSION_EXCEPTION, "/main.do");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        // 요금조회 X15
        MpFarMonBillingInfoDto billInfo = chargeService.farMonBillingInfoDto(childNcn, childCtn, childCustId, DateTimeUtil.getFormatString("yyyyMM"));
        if (!billInfo.isSuccess()) { // mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }

        model.addAttribute("billInfo", billInfo);
        List<MpMonthPayMentDto> monthList = billInfo.getMonthList();
        String billMonth = searchVO.getBillMonth();

        MpMonthPayMentDto monthPay = null;
        MpFarMonDetailInfoDto detailInfo = null;

        if (monthList != null && monthList.size() > 0) {
            if (StringUtil.isNotNull(billMonth)) {
                for (MpMonthPayMentDto item : monthList) {
                    if (StringUtil.equals(item.getBillMonth(), billMonth)) {
                        monthPay = item;
                        break;
                    }
                }
            } else {
                monthPay = monthList.get(0);
            }
        }

        if (monthPay != null) {
            detailInfo = chargeService.farMonDetailInfoDto(childNcn, childCtn, childCustId,
                    monthPay.getBillSeqNo(), monthPay.getBillDueDateList(), monthPay.getBillMonth(),
                    monthPay.getBillStartDate(), monthPay.getBillEndDate());

            if (!detailInfo.isSuccess()) { // mPlatFormService에서 response massage is null.로들어오는 경우 exception 처리
                throw new McpCommonException(COMMON_EXCEPTION);
            }
        }

        childCntr.setName(StringMakerUtil.getName(childCntr.getName()));
        model.addAttribute("monthPay", monthPay);
        model.addAttribute("detailInfo", detailInfo);
        model.addAttribute("childInfo", childCntr);

        return returnUrl;
    }

    /**
     * 설명 : 납부내역 목록 프린트
     *
     * @param request
     * @param model
     * @param searchVO
     * @return String
     * @Date : 2024.09.23
     */
    @RequestMapping("/mypage/childChargeMonPrint.do")
    public String chargeMonPrint(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, Model model, @RequestParam(value = "childNcn", required = true) String childNcn
            ,@RequestParam(value = "startDate", required = true) String startDate, @RequestParam(value = "endDate", required = true) String endDate)  {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String returnUrl = "/portal/mypage/childinfo/childChargeMonPrint";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonException(NO_SESSION_EXCEPTION, "/main.do");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonException(COMMON_EXCEPTION, "/main.do");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        //SMS인증 여부 확인
        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setPhoneNum(searchVO.getCtn());
        authSmsDto.setMenu("chargePrint");
        authSmsDto.setCheck(true);

        SessionUtils.checkAuthSmsSession(authSmsDto);

        if(!authSmsDto.isResult()) {
            throw new McpCommonJsonException("01", authSmsDto.getMessage());
        }

        //SMS 인증정보 초기화
        //실행할때 마다 SMS 인증 처리
        SessionUtils.setAuthSmsSetNullSession(authSmsDto);

        MpFarPaymentInfoVO mpFarPaymentInfoVO = null;
        List<ItemPay> itemPay = null;
        try {
            mpFarPaymentInfoVO = realTimePayService.farPaymentInfoList(childNcn, childCtn, childCustId, startDate, endDate);
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

        String[] nums = StringUtil.getMobileNum(childCntr.getMobileNo());
        String telNo= nums[0]+"-"+nums[1]+"-"+nums[2];

        childCntr.getName();
        childCntr.setMobileNo(telNo);
        model.addAttribute("childInfo", childCntr);
        return returnUrl;
    }

    /**
     * 설명 : 실시간 요금조회
     *
     * @param request
     * @param model
     * @param searchVO
     * @return String
     * @Date : 2024.09.23
     */
    @RequestMapping(value = {"/mypage/childRealTimeRateView.do", "/m/mypage/childRealTimeRateView.do"})
    public String childRealTimeRateView(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "childNcn", required = false) String childNcn) {

        String returnUrl = "/portal/mypage/childinfo/childRealTimeRateView";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/childinfo/childRealTimeRateView";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) {
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            return returnUrl;
        }

        String ncn = searchVO.getNcn();
        String childContractNum = "";
        ChildInfoDto childInfo = new ChildInfoDto();

        // 법정대리인 정보 조회
        ChildInfoDto minorAgent = childInfoService.getMinorAgentInfo(ncn);

        // 자녀 회선 목록 조회
        List<ChildInfoDto> childCntrList = childInfoService.selectChildCntrList(minorAgent.getFamSeq());

        if (childCntrList != null && childCntrList.size() > 0) {
            if (childNcn != null && !"".equals(childNcn)) {
                for (ChildInfoDto dto : childCntrList) {
                    if (childNcn.equals(dto.getSvcCntrNo())) {
                        childInfo = dto;
                        break;
                    }
                }
            } else {
                childInfo = childCntrList.get(0);
            }
            childContractNum = childInfo.getContractNum();
            childInfo.setName(StringMakerUtil.getName(childInfo.getName()));
            childInfo.setMoCtn(childCntrList.size());
        }

        // 요금제 조회
        String rateNm = "";
        McpUserCntrMngDto socMngDto = mypageService.selectSocDesc(childContractNum);
        if (socMngDto != null) {
            rateNm = StringUtil.NVL(socMngDto.getRateNm(), "-");
        }

        model.addAttribute("childCntrList", childCntrList);
        model.addAttribute("childInfo", childInfo);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("rateNm", rateNm);
        model.addAttribute("caller", "Detail");

        return returnUrl;
    }

    /**
     * 설명 : 요금 항목별 조회
     *
     * @param request
     * @param billMonth
     * @param messageLine
     * @param searchVO
     * @param childNcn
     * @return String
     * @Date : 2024.09.23
     */
    @RequestMapping(value = {"/mypage/getPriceInfoDetailItemAjax.do"})
    @ResponseBody
    public Map<String, Object> getPriceInfoDetailItemAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "childNcn") String childNcn, String billMonth, String messageLine) {

        Map<String, Object> rtnMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonJsonException("10001", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("10002", COMMON_EXCEPTION);
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonJsonException("10003", "회선 정보를 확인할 수 없습니다.");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        Object json = null;
        // 요금 항목별 조회 X17
        MpFarPriceInfoDetailItemVO item = null;

        if (billMonth != null && messageLine != null) {
            item = chargeService.farPriceInfoDetailItem(childNcn, childCtn, childCustId, "0", billMonth, messageLine);
            json = item.getList();
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "성공");
        rtnMap.put("result", json);

        return rtnMap;
    }


    /**
     * 당월 청구요금 조회
     * @param request
     * @param searchVO
     * @param childNcn
     * @return
     */

    @RequestMapping("/mypage/getThisMonthPayInfoAjax.do")
    @ResponseBody
    public Map<String, Object> getThisMonthPayInfoAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "childNcn", required = true) String childNcn)  {
        Map<String, Object> rtnMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonJsonException("10001", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("10002", COMMON_EXCEPTION);
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonJsonException("10003", "회선 정보를 확인할 수 없습니다.");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        // 당월청구요금 당월요금 (X15)
        MpMonthPayMentDto mMonthpaymentdto = null;

        String billStartDate = "";
        String billEndDate = "";
        String billMonth = "";
        String thisMonth = "0";
        String totalDueAmt = "0";
        try {
            // 지난달 정보
            billStartDate = DateTimeUtil.getFormatString("yyyyMM"); // 현재월
            billStartDate = DateTimeUtil.addMonths(billStartDate+"01",-1); // 지난월1일
            billEndDate = DateTimeUtil.lastDayOfMonth(billStartDate); // 지난월 마지막일

            // 현재월
            int intBillMonth = DateTimeUtil.getMonth();
            if(intBillMonth < 10) {
                billMonth = "0"+String.valueOf(intBillMonth);
            }
            Map<String,Object> map = myinfoService.farMonBillingInfo(childNcn, childCtn, childCustId, DateTimeUtil.getFormatString("yyyyMM"));
            mMonthpaymentdto = (MpMonthPayMentDto) map.get("mMonthpaymentdto"); // 사용요금

            if(mMonthpaymentdto !=null) {

                billStartDate = mMonthpaymentdto.getBillStartDate();
                billEndDate = mMonthpaymentdto.getBillEndDate();
                billMonth = mMonthpaymentdto.getBillMonth().substring(4,6);
                thisMonth = StringUtil.addComma(mMonthpaymentdto.getThisMonth());
                totalDueAmt = StringUtil.addComma(mMonthpaymentdto.getTotalDueAmt());
            } else {
                mMonthpaymentdto = new MpMonthPayMentDto();
            }
            billStartDate = DateTimeUtil.changeFormat(billStartDate,"yyyyMMdd","yyyy.MM.dd");
            billEndDate = DateTimeUtil.changeFormat(billEndDate,"yyyyMMdd","yyyy.MM.dd");
            mMonthpaymentdto.setBillStartDate(billStartDate);
            mMonthpaymentdto.setBillEndDate(billEndDate);
            mMonthpaymentdto.setBillMonth(billMonth);
            mMonthpaymentdto.setThisMonth(thisMonth);
            mMonthpaymentdto.setTotalDueAmt(totalDueAmt);

        } catch(SelfServiceException e){
            logger.error("getThisMonthPayInfoAjax error USERID##="+userSession.getUserId());
        } catch (Exception e) {
            logger.error("getThisMonthPayInfoAjax error USERID##="+userSession.getUserId());
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "성공");
        rtnMap.put("mMonthpaymentdto", mMonthpaymentdto);

        return rtnMap;
    }

    /**
     * 실시간 요금 조회
     * @param request
     * @param searchVO
     * @param childNcn
     * @return
     */

    @RequestMapping("/mypage/getRealTimePayInfoAjax.do")
    @ResponseBody
    public Map<String, Object> getRealTimePayInfoAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "childNcn", required = true) String childNcn)  {
        Map<String, Object> rtnMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonJsonException("10001", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("10002", COMMON_EXCEPTION);
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonJsonException("10003", "회선 정보를 확인할 수 없습니다.");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        // 1-2 당월청구요금 실시간 요금 X18
        MpFarRealtimePayInfoVO mpFarRealtimePayInfoVO = null;

        String useDay = "0";
        int useTotalDay = 0;
        String searchDay = "";
        String searchTime = "";
        String sumAmt = "";

        List<RealFareVO> realFareVOList = null;
        try {
            mpFarRealtimePayInfoVO = chargeService.farRealtimePayInfo(childNcn, childCtn, childCustId);
            if (mpFarRealtimePayInfoVO != null) {
                searchDay = mpFarRealtimePayInfoVO.getSearchDay();
                useDay = searchDay.substring(6, 8);
                searchTime = mpFarRealtimePayInfoVO.getSearchTime();
                sumAmt = StringUtil.addComma(mpFarRealtimePayInfoVO.getSumAmt());
                realFareVOList = mpFarRealtimePayInfoVO.getList();
                if (realFareVOList != null && !realFareVOList.isEmpty()) {
                    for (RealFareVO dto : realFareVOList) {
                        String payment = StringUtil.NVL(dto.getPayment(), "0");
                        payment = StringUtil.addComma(payment);
                        dto.setPayment(payment);
                    }
                }
            }
        } catch(SelfServiceException e){
            logger.error("getRealTimePayInfoAjax error USERID##="+userSession.getUserId());
        } catch (Exception e) {
            logger.error("getRealTimePayInfoAjax error USERID##="+userSession.getUserId());
        }

        try {
            if (!"".equals(searchDay) && searchDay.length() >= 8) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
                formatter.parse(searchDay);
                String yy = searchDay.substring(0, 4);
                String mm = searchDay.substring(4, 6);
                String day = searchDay.substring(6, 8);

                cal.set(Integer.parseInt(yy), Integer.parseInt(mm)-1, Integer.parseInt(day));
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
            double dUseTotalDay = Double.valueOf(useTotalDay);
            width = (dUseDay/dUseTotalDay) * 100;
            grapWidth = String.format("%.2f", width);
        } catch (NumberFormatException e) {
            logger.info("NumberFormatException ERROR");
        } catch(Exception e) {
            logger.info("width ERROR");
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "성공");
        rtnMap.put("grapWidth", grapWidth);
        rtnMap.put("realFareVOList", realFareVOList);
        rtnMap.put("useDay", useDay);
        rtnMap.put("useTotalDay", useTotalDay);
        rtnMap.put("searchDay", searchDay);
        rtnMap.put("searchTime", searchTime);
        rtnMap.put("sumAmt", sumAmt);

        return rtnMap;
    }

    /**
     * 납부 정보 조회
     * @param request
     * @param searchVO
     * @param childNcn
     * @return
     */

    @RequestMapping("/mypage/getPayDataAjax.do")
    @ResponseBody
    public Map<String, Object> getPayDataAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "childNcn", required = true) String childNcn)  {
        Map<String, Object> rtnMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonJsonException("10001", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("10002", COMMON_EXCEPTION);
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonJsonException("10003", "회선 정보를 확인할 수 없습니다.");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        // 가입정보조회 연동 규격 X01 ,현재 납부 방법 연동 X23, 청구서조회 X49
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            map = paywayService.getPayData(childNcn, childCtn, childCustId);
        } catch (DataAccessException e) {
            logger.error("getPayDataAjax error USERID##="+userSession.getUserId());
        }  catch(Exception e) {
            logger.error("getPayDataAjax error USERID##="+userSession.getUserId());
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "성공");
        rtnMap.put("payData", map);

        return rtnMap;
    }

    /**
     * (유료, 무료) 이용 중인 부가서비스 조회
     * @param request
     * @param searchVO
     * @param childNcn
     * @return
     */

    @RequestMapping("/mypage/getAddSvcInfoAjax.do")
    @ResponseBody
    public Map<String, Object> getAddSvcInfoAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO, @RequestParam(value = "childNcn", required = true) String childNcn)  {
        Map<String, Object> rtnMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonJsonException("10001", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("10002", COMMON_EXCEPTION);
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonJsonException("10003", "회선 정보를 확인할 수 없습니다.");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        // 이용중인 부가서비스 x20
        MpAddSvcInfoDto mpAddSvcInfoDto = myinfoService.getAddSvcInfoDto(childNcn, childCtn, childCustId);

        int freeCtn = 0;
        int payCtn = 0;
        if( mpAddSvcInfoDto != null ) {
            freeCtn = mpAddSvcInfoDto.getFreeCnt();
            payCtn = mpAddSvcInfoDto.getNotfreeCnt();
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "성공");
        rtnMap.put("freeCtn", freeCtn);
        rtnMap.put("payCtn", payCtn);

        return rtnMap;
    }

    /**
     * 이용 중인 부가서비스 목록 조회 X97
     * @param request
     * @param searchVO
     * @param childNcn
     * @param addDivCd
     * @return
     */

    @RequestMapping("/mypage/useAddSvcListAjax.do")
    @ResponseBody
    public Map<String, Object> useAddSvcListAjax(HttpServletRequest request,
                                                 @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                 @RequestParam(value = "childNcn", required = true) String childNcn,
                                                 @RequestParam(value = "addDivCd", required = false) String addDivCd)  {
        Map<String, Object> rtnMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ( userSession == null ) {
            throw new McpCommonJsonException("10001", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("10002", COMMON_EXCEPTION);
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonJsonException("10003", "회선 정보를 확인할 수 없습니다.");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        // 이용중인 부가서비스 X97
        MpAddSvcInfoParamDto res = regSvcService.selectmyAddSvcList(childNcn, childCtn, childCustId);

        List<MpSocVO> outList = res.getList();

        //"G" 일반 부가서비스만 조회
        //"R" 로밍 부가서비스만 조회
        regSvcService.getMpSocListByDiv(outList, addDivCd);

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "성공");
        rtnMap.put("outList", outList);

        return rtnMap;
    }

    /**
     * 총 통화 시간 조회
     * @param request
     * @param searchVO
     * @param childNcn
     * @return
     */
    @RequestMapping("/mypage/getTelTotalUseTimeAjax.do")
    @ResponseBody
    public Map<String, Object> getTelTotalUseTimeAjax(HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
                                                      @RequestParam(value = "useMonth", required = false) String useMonth,
                                                      @RequestParam(value = "childNcn", required = true) String childNcn) {

        Map<String, Object> rtnMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null) {
            throw new McpCommonJsonException("10001", "로그인 정보가 없습니다.<br>로그인 후 이용해 주세요.");
        }

        List<McpUserCntrMngDto> cntrList = null;
        cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if (!chk) {
            throw new McpCommonJsonException("10002", COMMON_EXCEPTION);
        }

        String childCtn;
        String childCustId;
        ChildInfoDto ChildInfoDto = new ChildInfoDto();
        ChildInfoDto.setMinorAgentSvcCntrNo(searchVO.getNcn());
        ChildInfoDto.setSvcCntrNo(childNcn);
        ChildInfoDto childCntr = childInfoService.selectChildCntr(ChildInfoDto);
        if (childCntr == null) {
            throw new McpCommonJsonException("10003", "회선 정보를 확인할 수 없습니다.");
        }
        childCtn = childCntr.getMobileNo();
        childCustId = childCntr.getCustomerId();

        // 총 통화 시간 조회 x12
        MpTelTotalUseTimeDto vo = callRegService.telTotalUseTimeDto(childNcn, childCtn, childCustId, useMonth);

        MpTelTotalUseTimeDto rtnVo = new MpTelTotalUseTimeDto();
        rtnVo.setItemTelVOListData(vo.getItemTelVOListData());
        rtnVo.setItemTelVOListVoice(vo.getItemTelVOListVoice());
        rtnVo.setItemTelVOListSms(vo.getItemTelVOListSms());
        rtnVo.setUseTimeSvcLimit(vo.isUseTimeSvcLimit());

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "성공");
        rtnMap.put("vo", rtnVo);
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