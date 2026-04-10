package com.ktmmobile.mcp.mypage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.common.exception.SelfServiceException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.mcp.common.mplatform.dto.InqrCoupInfoRes;
import com.ktmmobile.mcp.common.mplatform.dto.InqrUsedCoupListRes;
import com.ktmmobile.mcp.common.mplatform.dto.TrtmCoupUseRes;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.coupon.dto.CouponCategoryDto;
import com.ktmmobile.mcp.coupon.service.CouponService;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;

@Controller
public class MyOllehCouponController {

    private static final Logger logger = LoggerFactory.getLogger(MyOllehCouponController.class);

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    /**
     * <pre>
     * 설명     : 마이페이지 > 나의 혜택보기 > My 쿠폰 메인화면(M쿠폰)
     * @return String
     * </pre>
     */
    @RequestMapping(value = {"/mypage/couponList.do","/m/mypage/couponList.do"})
    public String couponList(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String redirectUrl = "/mypage/couponList.do";
        String returnUrl = "/portal/mypage/coupon/couponList";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            redirectUrl = "/m/mypage/couponList.do";
            returnUrl = "/mobile/mypage/coupon/couponList";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            return redirectUrl;
        }

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(redirectUrl);
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        // MCP 휴대폰 회선관리 목록 조회
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);

        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        CoupInfoDto coupInfoDto = new CoupInfoDto();
         List<CoupInfoDto> useCoupInfoList = null;
         boolean isData = false;

         try {
             /*
             *  M쿠폰 조회
             * * - M쿠폰 조회화면의 기준을 "서비스계약번호"에서 "계약번호"로 변경
             */
             //coupInfoDto.setSvcCntrNo(searchVO.getNcn());
             coupInfoDto.setSvcCntrNo(searchVO.getContractNum());
              coupInfoDto.setCoupnDivCd("MB");
              useCoupInfoList = couponService.getUseCoupInfoList(coupInfoDto);

             if(null != useCoupInfoList && !useCoupInfoList.isEmpty()) {
                 isData = true;
             }
         } catch(DataAccessException e) {
            logger.debug("DB 오류");
        } catch(Exception e) {
             logger.debug("DB 오류");
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

         model.addAttribute("isData", isData);
        model.addAttribute("useCoupInfoList", useCoupInfoList);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);

        return returnUrl;
    }

    /**
     * <pre>
     * 설명     : My 쿠폰 > 기타 혜택 쿠폰 > 사용가능
     * @return String
     * </pre>
     */
    @RequestMapping(value = {"/mypage/couponListOut.do","/m/mypage/couponListOut.do"})
    public String couponListOut(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String redirectUrl = "/mypage/couponListOut.do";
        String returnUrl = "/portal/mypage/coupon/couponListOut";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            redirectUrl = "/m/mypage/couponListOut.do";
            returnUrl = "/mobile/mypage/coupon/couponListOut";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            return redirectUrl;
        }

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(redirectUrl);
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        // MCP 휴대폰 회선관리 목록 조회
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

        // X74데이터값
        CoupInfoDto coupInfoDto = new CoupInfoDto();
        coupInfoDto.setNcn(ncn);
         coupInfoDto.setCtn(ctn);
         coupInfoDto.setCustId(custId);
         coupInfoDto.setCoupSerialNo("");
         coupInfoDto.setCoupTypeCd("");
         coupInfoDto.setCoupStatCd("BPCO");
         coupInfoDto.setSearchTypeCd("02");
         coupInfoDto.setSvcTypeCd("");
         coupInfoDto.setPageNo(1);

         List<CoupInfoDto> coupInfoList = null;
         List<CoupInfoDto> useCoupInfoList = null;
         // 1.쿠폰 정보조회(X74)
         // 2.사용가능한 외부쿠폰
         boolean isData = false;
         try {
             // 1.내부쿠폰
             InqrCoupInfoRes inqrCoupInfoRes = mPlatFormService.inqrCoupInfo(coupInfoDto);
             coupInfoList = new ArrayList<CoupInfoDto>();
             if(inqrCoupInfoRes != null){
                 coupInfoList = inqrCoupInfoRes.getCoupInfoList();
                 if(coupInfoList != null && !coupInfoList.isEmpty()) {
                     isData = true;
                 }
             }
         } catch (SelfServiceException e) {
            logger.error("X74오류 e : {}", e.getMessage());
        }  catch (Exception e) {
             logger.debug("X74오류");
         }

         try {
             //2.사용가능한 외부쿠폰
             coupInfoDto = new CoupInfoDto();
             coupInfoDto.setSvcCntrNo(ncn);
             useCoupInfoList = couponService.getUseCoupInfoList(coupInfoDto);
             if(useCoupInfoList !=null && !useCoupInfoList.isEmpty()) {
                 isData = true;
             }
         } catch(DataAccessException e) {
            logger.debug("DB 오류");
        } catch(Exception e) {
             logger.debug("DB 오류");
         }

         String toDay = "";
        try {
            toDay = DateTimeUtil.getFormatString("yyyy-MM-dd");
        } catch(IllegalArgumentException e) {
            logger.debug("날짜 오류");
        } catch (Exception e) {
            logger.debug("날짜 오류");
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

        model.addAttribute("isData", isData);
        model.addAttribute("coupInfoList", coupInfoList);
        model.addAttribute("useCoupInfoList", useCoupInfoList);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("toDay", toDay);
        return returnUrl;
    }

    /**
     * <pre>
     * 설명     : My 쿠폰 > 기타 혜택 쿠폰 > 사용완료
     * @return String
     * </pre>
     */
    @RequestMapping(value={"/mypage/couponUsedDataList.do","/m/mypage/couponUsedDataList.do"})
    public String couponUsedDataHtml(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String rtnCode = "";
        String rtnMsg = "";

        String redirectUrl = "/mypage/couponUsedDataList.do";
        String returnUrl = "/portal/mypage/coupon/couponUsedDataList";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl="/mobile/mypage/coupon/couponUsedDataList";
            redirectUrl = "/m/mypage/couponUsedDataList.do";
        }

        // 1.cnc 으로 회선정보 구하기
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            return redirectUrl;
        }

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

        // X76데이터값
        CoupInfoDto coupInfoDto = new CoupInfoDto();
        coupInfoDto.setNcn(ncn);
        coupInfoDto.setCtn(ctn);
        coupInfoDto.setCustId(custId);
        coupInfoDto.setCoupSerialNo("");
        coupInfoDto.setSvcTypeCd("");
        coupInfoDto.setPageNo(1);

        List<CoupInfoDto> coupInfoList = null;
        List<CoupInfoDto> usedCoupInfoList = null;
        boolean isData = false;

        // 1.사용완료 쿠폰 정보조회(X76)
        // 2.발송된 외부쿠폰
        try {
            // 1.사용완료 쿠폰 정보조회(X76)
            InqrUsedCoupListRes inqrUsedCoupListRes = mPlatFormService.inqrUsedCoupList(coupInfoDto);
            coupInfoList = new ArrayList<CoupInfoDto>();
            if(inqrUsedCoupListRes != null){
                coupInfoList = inqrUsedCoupListRes.getCoupInfoList();
                if(coupInfoList!=null && !coupInfoList.isEmpty()){
                    for(CoupInfoDto dto : coupInfoList){
                        String coupStatCdNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(Constants.COUP_STAT_CD,dto.getCoupStatCd()),"-");
                        dto.setCoupStatCdNm(coupStatCdNm);
                    }
                    isData = true;
                }
                rtnCode = inqrUsedCoupListRes.getRtnCode();
                rtnMsg = inqrUsedCoupListRes.getRtnMsg();
            }

        } catch (Exception e) {
            rtnCode = "9999";
            rtnMsg = "서비스 처리중 오류가 발생 하였습니다.";
        }


        try {
            // 2.발송된 외부쿠폰
             coupInfoDto = new CoupInfoDto();
             coupInfoDto.setSvcCntrNo(ncn);
             usedCoupInfoList = couponService.getUsedCoupInfoList(coupInfoDto);
             if(usedCoupInfoList !=null && !usedCoupInfoList.isEmpty()) {
                 isData = true;
             }
        } catch(DataAccessException e) {
            logger.debug("DB오류");
        } catch(Exception e) {
            logger.debug("DB오류");
        }

        String toDay = "";
        try {
            toDay = DateTimeUtil.getFormatString("yyyy-MM-dd");
        } catch(IllegalArgumentException e) {
            logger.debug("날짜 오류");
        } catch (Exception e) {
            logger.debug("날짜 오류");
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

        model.addAttribute("coupInfoList", coupInfoList);
        model.addAttribute("rtnCode", rtnCode);
        model.addAttribute("rtnMsg", rtnMsg);
        model.addAttribute("isData", isData);
        model.addAttribute("usedCoupInfoList", usedCoupInfoList);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("toDay", toDay);

        return returnUrl;
    }

    // 사용자 직접 keyin 신청 팝업
    @RequestMapping(value={"/mypage/couponRegPop.do","/m/mypage/couponRegPop.do"})
    public String couponRegPop(HttpServletRequest request, ModelMap model, @ModelAttribute CoupInfoDto coupInfoDto,@ModelAttribute("searchVO") MyPageSearchDto searchVO) {

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

        String pageUrl = "";
        //중복요청 체크
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/mypage/coupon/couponRegPop";
        } else {
            pageUrl = "/portal/mypage/coupon/couponRegPop";
        }

        model.addAttribute("ncn", ncn);
        model.addAttribute("mobileNo", StringMakerUtil.getPhoneNum(ctn));
        return pageUrl;
    }

    // 쿠폰조회 신청 팝업
    @RequestMapping(value={"/mypage/couponDtlRegPop.do","/m/mypage/couponDtlRegPop.do"})
    public String couponDtlRegPop(HttpServletRequest request, ModelMap model, @ModelAttribute CoupInfoDto coupInfoDto,@ModelAttribute("searchVO") MyPageSearchDto searchVO) {

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

        String pageUrl = "";
        //중복요청 체크
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/mypage/coupon/couponDtlRegPop";
        } else {
            pageUrl = "/portal/mypage/coupon/couponDtlRegPop";
        }

        CoupInfoDto reqDto = new CoupInfoDto();

        // X74데이터값
        reqDto.setNcn(ncn);
        coupInfoDto.setCtn("");
        coupInfoDto.setCustId(custId);
        coupInfoDto.setSearchTypeCd("01");
        coupInfoDto.setCoupTypeCd("");
        coupInfoDto.setCoupStatCd("BPCO");
        coupInfoDto.setSvcTypeCd("");
        coupInfoDto.setPageNo(1);

        List<CoupInfoDto> coupInfoList = null;
        String coupSerialNo = "";
        // 쿠폰 정보조회(X74)
        try {

            InqrCoupInfoRes inqrCoupInfoRes = mPlatFormService.inqrCoupInfo(coupInfoDto);
            coupInfoList = new ArrayList<CoupInfoDto>();
            if( inqrCoupInfoRes !=null ){
                coupInfoList = inqrCoupInfoRes.getCoupInfoList();
                if(coupInfoList !=null && !coupInfoList.isEmpty()) {
                    coupSerialNo = StringUtil.NVL(coupInfoList.get(0).getCoupSerialNo(),"");
                    if(!"".equals(coupSerialNo) && coupSerialNo.length() ==15){
                        String a = coupSerialNo.substring(0,4);
                        String b = coupSerialNo.substring(4,8);
                        String c = coupSerialNo.substring(8,12);
                        String d = coupSerialNo.substring(12,15);
                        coupSerialNo = a+"-"+b+"-"+c+"-"+d;
                    }
                }

            }
        } catch(DataAccessException e) {
            logger.error("coupon 조회 에러::"+e.getMessage());
        } catch (Exception e) {
            logger.error("coupon 조회 에러::"+e.getMessage());
        }

        model.addAttribute("coupInfoList", coupInfoList);
        model.addAttribute("ncn", ncn);
        model.addAttribute("mobileNo", StringMakerUtil.getPhoneNum(ctn));
        model.addAttribute("coupSerialNo", coupSerialNo);
        return pageUrl;
    }



    /**
     * 쿠폰 상세 , 쿠폰번호 등록 > 조회
     */
    @RequestMapping(value={"/mypage/couponDataAjax.do"})
    @ResponseBody
    public Map<String,Object> couponDataAjax(HttpServletRequest request, ModelMap model, @ModelAttribute CoupInfoDto coupInfoDto) {

        Map<String,Object> map = new HashMap<String,Object>();
        String rtnCode = "";
        String rtnMsg = "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            map.put("rtnCode", "999999");
            return map;
        }

        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/mypage/couponDataAjax.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            rtnMsg = "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.";
            rtnCode = "8888";
            map.put("rtnCode", rtnCode);
            map.put("rtnMsg", rtnMsg);
            return map;
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        String ctn = "";
        String custId = "";

        if(cntrList !=null && !cntrList.isEmpty()){
            String paramNcn = StringUtil.NVL(coupInfoDto.getNcn(),"");
            for(McpUserCntrMngDto dto : cntrList){
                String ncn = dto.getSvcCntrNo();
                if(paramNcn.equals(ncn)){
                    ctn = dto.getCntrMobileNo();
                    custId = dto.getCustId();
                }
            }
        }

        // X74데이터값
        coupInfoDto.setCtn("");
        coupInfoDto.setCustId(custId);
        coupInfoDto.setSearchTypeCd("01");
        coupInfoDto.setCoupTypeCd("");
        coupInfoDto.setCoupStatCd("BPCO");
        coupInfoDto.setSvcTypeCd("");
        coupInfoDto.setPageNo(1);

        List<CoupInfoDto> coupInfoList = null;

        // 쿠폰 정보조회(X74)
        try {

            InqrCoupInfoRes inqrCoupInfoRes = mPlatFormService.inqrCoupInfo(coupInfoDto);
            coupInfoList = new ArrayList<CoupInfoDto>();
            if( inqrCoupInfoRes !=null ){
                coupInfoList = inqrCoupInfoRes.getCoupInfoList();
                rtnCode = inqrCoupInfoRes.getRtnCode(); // 0000 : 성공
                rtnMsg = inqrCoupInfoRes.getRtnMsg();
            }
        } catch (Exception e) {
            rtnCode = "9999";
            rtnMsg = "서비스 처리중 오류가 발생 하였습니다.";
        }

        map.put("coupInfoList", coupInfoList);
        map.put("rtnCode", rtnCode);
        map.put("rtnMsg", rtnMsg);

        return map;
    }

    /**
     * 쿠폰 등록
     */
    @RequestMapping(value={"/mypage/couponRegAjax.do"})
    @ResponseBody
    public Map<String,Object> couponRegAjax(HttpServletRequest request, ModelMap model, @ModelAttribute CoupInfoDto coupInfoDto) {

        Map<String,Object> map = new HashMap<String,Object>();
        String rtnCode = "";
        String rtnMsg = "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())){
            map.put("rtnCode", "999999");
            return map;
        }

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/mypage/couponRegAjax.do");

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            rtnMsg = "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.";
            rtnCode = "8888";
            map.put("rtnCode", rtnCode);
            map.put("rtnMsg", rtnMsg);
            return map;
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        String ctn = "";
        String custId = "";
        String paramNcn = StringUtil.NVL(coupInfoDto.getNcn(),"");
        if(cntrList !=null && !cntrList.isEmpty()){

            for(McpUserCntrMngDto dto : cntrList){
                String ncn = dto.getSvcCntrNo();
                if(paramNcn.equals(ncn)){
                    ctn = dto.getCntrMobileNo();
                    custId = dto.getCustId();
                }
            }
        }

        // X75데이터값
        coupInfoDto.setNcn(paramNcn);
        coupInfoDto.setCtn(ctn);
        coupInfoDto.setCustId(custId);
        coupInfoDto.setClntUsrId("");

        CoupInfoDto resDto = null;

        // 쿠폰 정보조회(X75)
        try {

            TrtmCoupUseRes trtmCoupUseRes = mPlatFormService.trtmCoupUse(coupInfoDto);
            resDto = new CoupInfoDto();
            if( trtmCoupUseRes !=null ){
                resDto = trtmCoupUseRes.getCoupInfo();
                rtnCode = trtmCoupUseRes.getRtnCode(); // 0000 : 성공
                rtnMsg = trtmCoupUseRes.getRtnMsg();

            }
        } catch (Exception e) {
            rtnCode = "9999";
            rtnMsg = "서비스 처리중 오류가 발생 하였습니다.";
        }

        map.put("coupInfoDto", resDto);
        map.put("rtnCode", rtnCode);
        map.put("rtnMsg", rtnMsg);

        return map;
    }

    @Deprecated
    private String getErrCd(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        result = arg[0]; //N:성공, S:시스템에서, E:Biz 에러
        return result;
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
     * <pre>
     * 설명     : My 쿠폰 메인화면(M쿠폰)
     * @return String
     * </pre>
     */
    @RequestMapping(value = {"/mypage/couponDtlPop.do","/m/mypage/couponDtlPop.do"})
    public String couponDtlPop(HttpServletRequest request, ModelMap model, @ModelAttribute CouponCategoryDto ctgDto, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        // MCP 휴대폰 회선관리 목록 조회
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String pageUrl = "";
        //중복요청 체크
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/mypage/coupon/couponDtlPop";
        } else {
            pageUrl = "/portal/mypage/coupon/couponDtlPop";
        }

         CouponCategoryDto mbDtlDto = null;

         try {
             //M쿠폰 조회
             mbDtlDto = couponService.getMbershipDtl(ctgDto);
             if(null != mbDtlDto) {
                 mbDtlDto.setSmsRcvNo(StringMakerUtil.getPhoneNum(mbDtlDto.getSmsRcvNo()));
             }
         } catch(DataAccessException e) {
            logger.debug("DB 오류");
        } catch(Exception e) {
             logger.debug("DB 오류");
         }

        model.addAttribute("mbDtlDto", mbDtlDto);
        //model.addAttribute("cntrList", cntrList);
        //model.addAttribute("searchVO", searchVO);

        return pageUrl;
    }
}
