package com.ktmmobile.mcp.mypage.controller;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.vo.MpTelVO;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.vo.MpTelTotalUseTimeDto;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.FarPricePlanResDto;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpFarPriceDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.CallRegService;
import com.ktmmobile.mcp.mypage.service.FarPricePlanService;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;


@Controller
public class CallRegController {

    private static final Logger logger = LoggerFactory.getLogger(CallRegController.class);

    @Autowired
    private MypageService mypageService;

    @Autowired
    private FarPricePlanService farPricePlanService;

    @Autowired
    private CallRegService callRegService;

    @Autowired
    private MplatFormService mPlatFormService;


    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    /**
     * 이용량조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param useMonth
     * @return
     * @throws ParseException
     */

    @RequestMapping(value = { "/mypage/callView01.do", "/m/mypage/callView01.do" })
    public String doCallView01(HttpServletRequest request, Model model,
              @ModelAttribute("searchVO") MyPageSearchDto searchVO,
              @RequestParam(value = "useMonth", required = false) String useMonth) throws ParseException {
        String returnUrl = "/portal/mypage/callView01";
        String checkUrl = "/mypage/callView01.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/mypage/callView01";
            checkUrl = "/m/mypage/callView01.do";
        }

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(checkUrl);
        checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 291
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String ncn = searchVO.getNcn();
        String ctn = searchVO.getCtn();
        String custId = searchVO.getCustId();
        String contractNum = searchVO.getContractNum();


        try {
            String strSvcNameSms = "";
            MpTelTotalUseTimeDto vo = new MpTelTotalUseTimeDto();
            // 1.이용량 조회
            vo = callRegService.telTotalUseTimeDto(ncn, ctn, custId, useMonth);

            logger.info("vo==>"+ObjectUtils.convertObjectToString(vo));

            // 2. 요금제조회
            McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(contractNum);
            if(mcpFarPriceDto !=null) {
                strSvcNameSms = mcpFarPriceDto.getPrvRateGrpNm();
            } else {
                mcpFarPriceDto = new McpFarPriceDto();
            }
            vo.setStrSvcNameSms(strSvcNameSms);

            // 3. 요금제 상세정보
            FarPricePlanResDto farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);
            if(farPricePlanResDto != null) {
                mcpFarPriceDto.setRateAdsvcLteDesc(StringUtil.NVL(farPricePlanResDto.getRateAdsvcLteDesc(),"-"));
                mcpFarPriceDto.setRateAdsvcCallDesc(StringUtil.NVL(farPricePlanResDto.getRateAdsvcCallDesc(),"-"));
                mcpFarPriceDto.setRateAdsvcSmsDesc(StringUtil.NVL(farPricePlanResDto.getRateAdsvcSmsDesc(),"-"));
            }

            //////로그 END ->
            SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
            Date currentTime = new Date();
            String toDay = formatter.format ( currentTime );
            String lastDay ="";
            try {
                lastDay = DateTimeUtil.lastDayOfMonth(toDay);
            } catch (ParseException e) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            List<String> list = new ArrayList<String>();

            // 4-1-1. 당월 시작일 ~ 현재일
            String startDate = DateTimeUtil.getFormatString(lastDay.substring(0,4)+lastDay.substring(4,6)+"01", "yyyyMMdd", "yyyy.MM.dd");
            String endDate = DateTimeUtil.getFormatString(DateTimeUtil.getShortDateString(), "yyyyMMdd", "yyyy.MM.dd");

            // 4-1-2. 한달전 시작일 ~ 마지막일
            String prvDate1= DateTimeUtil.addMonths(lastDay, -1);
            String prvStartDate1 = DateTimeUtil.getFormatString(prvDate1.substring(0,4)+ prvDate1.substring(4,6)+"01", "yyyyMMdd", "yyyy.MM.dd");
            String prvEndDate1 = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(prvDate1), "yyyyMMdd", "yyyy.MM.dd");

            // 4-1-3. 두달전 시작일 ~ 마지막일
            String prvDate2= DateTimeUtil.addMonths(lastDay, -2);
            String prvStartDate2 = DateTimeUtil.getFormatString(prvDate2.substring(0,4)+prvDate2.substring(4,6)+"01", "yyyyMMdd", "yyyy.MM.dd");
            String prvEndDate2 = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(prvDate2), "yyyyMMdd", "yyyy.MM.dd");

            // 4-1-4. 세달전 시작일 ~ 마지막일
            String prvDate3= DateTimeUtil.addMonths(lastDay, -3);
            String prvStartDate3 = DateTimeUtil.getFormatString(prvDate3.substring(0,4)+prvDate3.substring(4,6)+"01", "yyyyMMdd", "yyyy.MM.dd");
            String prvEndDate3 = DateTimeUtil.getFormatString(DateTimeUtil.lastDayOfMonth(prvDate3), "yyyyMMdd", "yyyy.MM.dd");

            list.add(startDate+"~"+endDate);
            list.add(prvStartDate1+"~"+prvEndDate1);
            list.add(prvStartDate2+"~"+prvEndDate2);
            list.add(prvStartDate3+"~"+prvEndDate3);

            String[] todayList =list.toArray(new String[list.size()]);

            model.addAttribute("todayList", todayList);

            int useTime = Integer.parseInt(lastDay) - Integer.parseInt(toDay);

            model.addAttribute("toDay", toDay.substring(6));
            model.addAttribute("fisrtDay", toDay.substring(0,6));
            model.addAttribute("toDayOrign", toDay);
            model.addAttribute("lastDay", lastDay.substring(6));
            model.addAttribute("useTime", useTime);
            model.addAttribute("vo", vo);
            model.addAttribute("data", vo.getItemTelVOListData());
            model.addAttribute("voice", vo.getItemTelVOListVoice());
            model.addAttribute("sms", vo.getItemTelVOListSms());
            model.addAttribute("etc", vo.getItemTelVOListEtc());
            model.addAttribute("roaming", vo.getItemTelVOListRoaming());
            model.addAttribute("cntrList", cntrList);
            searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
            model.addAttribute("searchVO", searchVO);
            model.addAttribute("useTimeSvcLimit", vo.isUseTimeSvcLimit());
            model.addAttribute("mcpFarPriceDto", mcpFarPriceDto);
            model.addAttribute("useMonth", useMonth);
            model.addAttribute("useMonth", useMonth);

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
     * 이용량조회 ajax (당월조회)
     * @author hsy
     * @Date : 2023.03.07
     * @param request
     * @param searchVO
     * @return Map<String, Object>
     * @throws ParseException
     */

    @RequestMapping(value = "/mypage/selectRealTimeUseageAjax.do")
    @ResponseBody
    public Map<String, Object> selectRealTimeUseageAjax(HttpServletRequest request,
                                                        @ModelAttribute("searchVO") MyPageSearchDto searchVO) throws ParseException {

        Map<String, Object> rtnMap= new HashMap<>();

        // 1. 중복요청 체크
        String checkUrl = "/mypage/selectRealTimeUseageAjax.do";
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(checkUrl);
        checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            // 5초 이내의 중복요청으로 즉시 리턴
            throw new McpCommonJsonException("0001", TIME_OVERLAP_EXCEPTION);
        }

        // 2. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtil.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0002", NO_FRONT_SESSION_EXCEPTION);
        }

        // 3. 정회원 체크
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException("0003", NOT_FULL_MEMBER_EXCEPTION);
        }

        // 4. 이용량 조회 시작
        String ncn = searchVO.getNcn();
        String ctn = searchVO.getCtn();
        String custId = searchVO.getCustId();

        try {

            // 4-1. 이용량 조회 (당월)
            MpTelTotalUseTimeDto vo = new MpTelTotalUseTimeDto();
            vo = callRegService.telTotalUseTimeDto(ncn, ctn, custId, null);

            logger.info("vo==>"+ObjectUtils.convertObjectToString(vo));

            rtnMap.put("useageData"      , vo.getItemTelVOListData());    // 실시간이용량_데이터
            rtnMap.put("useageVoice"     , vo.getItemTelVOListVoice());   // 실시간이용량_통화
            rtnMap.put("useageSms"       , vo.getItemTelVOListSms());     // 실시간이용량_문자
            rtnMap.put("useageEtc"       , vo.getItemTelVOListEtc());     // 실시간이용량_기타
            rtnMap.put("useTimeSvcLimit" , vo.isUseTimeSvcLimit());       // 조회가능한 횟수 초과여부

            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            searchVO.setMessage("정상응답");

        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
            rtnMap.put("RESULT_CODE", "0005");
            searchVO.setMessage(getErrMsg(e.getMessage()));
        } catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
            rtnMap.put("RESULT_CODE", "0006");
            searchVO.setMessage(getErrMsg(e.getMessage()));
        }

        rtnMap.put("RESULT_MSG", searchVO.getMessage());
        return rtnMap;
    }



    /**
     * 기본 제공량 모두 소진하여 초과 요금 발생 고객 구분
     * @author papier
     * @Date : 2025.12.10
     * @return Map<String, Object>
     * @throws ParseException
     */
    @RequestMapping(value = "/mypage/isOverUsageChargeAjax.do")
    @ResponseBody
    public Map<String, Object> isOverUsageChargeAjax(@ModelAttribute("searchVO") MyPageSearchDto searchVO) throws ParseException {

        // 1. 중복요청 체크
        String checkUrl = "/mypage/isOverUsageChargeAjax.do";
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(checkUrl);
        checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            // 5초 이내의 중복요청으로 즉시 리턴
            throw new McpCommonJsonException("0001", TIME_OVERLAP_EXCEPTION);
        }

        // 2. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtil.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0002", NO_FRONT_SESSION_EXCEPTION);
        }

        // 3. 정회원 체크
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException("0003", NOT_FULL_MEMBER_EXCEPTION);
        }

        // 4. 기본 제공량 모두 소진하여 초과 요금 발생 고객 구분
        String ncn = searchVO.getNcn();
        String ctn = searchVO.getCtn();
        String custId = searchVO.getCustId();
        String contractNum = searchVO.getContractNum();

        return callRegService.isOverUsageChargeAjax(ncn, contractNum, ctn, custId);

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

    /**
     * 데이터 당겨쓰기
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = { "/mypage/pullData01.do", "/m/mypage/pullData01.do" })
    public String dopullData01(HttpServletRequest request, Model model,
              @ModelAttribute("searchVO") MyPageSearchDto searchVO) {
        String returnUrl = "/portal/mypage/pullData01";
        String redirectUrl = "/mypage/pullData01.do";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/mypage/pullData01";
            redirectUrl = "/m/mypage/pullData01.do";
        }

         //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(redirectUrl);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 289
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
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

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);

        return returnUrl;

    }

    /**
     * 당겨쓰기 조회 ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     * @throws SocketTimeoutException
     * @throws SelfServiceException
     */
    @RequestMapping(value = { "/mypage/totalUseTimeListAjax.do", "/m/mypage/totalUseTimeListAjax.do" })
    @ResponseBody
    public JsonReturnDto totalUseTimeListAjax(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO) throws SocketTimeoutException, SelfServiceException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        JsonReturnDto result = new JsonReturnDto();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 285
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException("0098" ,NOT_FULL_MEMBER_EXCEPTION);
        }

        //현재 요금제 조회
        McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());

        if(mcpUserCntrMngDto == null) {
            throw new McpCommonException(NO_EXSIST_RATE);
        }

        rtnMap = callRegService.totalUseTimeList(mcpUserCntrMngDto,searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
        rtnMap.put("rateNm", mcpUserCntrMngDto.getRateNm());
        result.setReturnCode("00");
        result.setResultMap(rtnMap);
        return result;
    }

    /**
     * 당겨쓰기 내역조회 Ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     * @throws SocketTimeoutException
     * @throws SelfServiceException
     */

    @RequestMapping(value = { "/mypage/pullDataHistListAjax.do", "/m/mypage/pullDataHistListAjax.do" })
    @ResponseBody
    public JsonReturnDto pullDataHistListAjax(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO) throws SocketTimeoutException, SelfServiceException {
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        List<McpUserCntrMngDto> cntrList = null;
        if ( userSession != null) { // 취약성 287
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        JsonReturnDto result = new JsonReturnDto();
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException("0098" ,NOT_FULL_MEMBER_EXCEPTION);
        }

        //현재 요금제 조회
        McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());

        if(mcpUserCntrMngDto == null) {
            throw new McpCommonException(NO_EXSIST_RATE);
        }

        rtnMap = callRegService.totalUseTimeHist(mcpUserCntrMngDto,searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());

        result.setReturnCode("00");
        result.setResultMap(rtnMap);
        return result;
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
}

