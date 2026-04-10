package com.ktmmobile.mcp.mcash.controller;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mcash.dto.McashShopDto;
import com.ktmmobile.mcp.mcash.dto.McashUserDto;
import com.ktmmobile.mcp.mcash.service.McashService;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.*;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class McashController {

    private static final Logger logger = LoggerFactory.getLogger(McashController.class);

    @Autowired
    private McashService mcashService;

    @Autowired
    private CertService certService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private FCommonSvc fCommonSvc;

    /**
     * 설명 : M쇼핑할인 소개 페이지
     *
     * @param model
     * @return String
     * @Date : 2024.05.21
     */
    @RequestMapping(value = {"/mcash/mcashView.do", "/m/mcash/mcashView.do"})
    public String mcashView(HttpServletRequest request, ModelMap model) {
        if ( !mcashService.isMcashAuth() ) {
            throw new McpCommonException("비정상적인 접근입니다.");
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        // 1. 리턴페이지 설정
        String jspPageName = "/portal/mcash/mcashView";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            jspPageName = "/mobile/mcash/mcashView";
        }

        // 2. M쇼핑할인 이용 가능 여부 체크
        Map<String, String> limitChkMap = mcashService.chkMcashUse();

        String errorCause = limitChkMap.get("resultMsg");
        String resultCd = limitChkMap.get("resultCd");

        // 2-3. resultCd와 errorCause에 따라 화면 표출
        // CASE 1) resultCd 값이 0000인 경우 : M쇼핑할인 이용가능
        // CASE 2) resultCd 값이 0001이고 errorCause가 LOGIN인 경우 : 로그인 페이지 이동
        // CASE 3) resultCd 값이 0001이고 errorCause가 GRADE인 경우 : 정회원 인증 페이지 이동
        // CASE 4) resultCd 값이 0001이고 errorCause가 JOIN인 경우  : M쇼핑할인 가입하기 페이지 이동
        // CASE 5) 이외의 경우 : 이용불가 문구 표출
        model.addAttribute("RESULT_CODE", resultCd);
        model.addAttribute("RESULT_MSG", errorCause);

        if ( "0000".equals(resultCd) && "Y".equals(NmcpServiceUtils.isMobile()) ) {
            Map<String, String> checkMap = fCommonSvc.checkServiceAvailable("MCASH");
            if (checkMap != null && "9999".equals(checkMap.get("code"))) {
                throw new McpCommonException(checkMap.get("message"), "/m/main.do");
            }
            if (userSession == null || "".equals(userSession.getUserId())) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("C004")); // bannCtg
            model.addAttribute("mcashMainUrl", mcashService.getMcashMainUrl(userSession.getUserId()));
            return "/mobile/mcash/mcashMain";
        }

        model.addAttribute("bannerTextInfo", NmcpServiceUtils.getBannerTextList("McashReviewTop")); // bannCtg
        model.addAttribute("mcashAccessCnt", StringUtil.addComma(fCommonSvc.selectPageViewsCount(request.getRequestURI()))); // bannCtg

        return jspPageName;
    }

    @RequestMapping(value = "/m/mcash/mcashMain.do")
    public String mcashMain(HttpServletRequest request, ModelMap model) {
        if (!mcashService.isMcashAuth()) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        if ("N".equals(NmcpServiceUtils.isMobile())) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        Map<String, String> checkMap = fCommonSvc.checkServiceAvailable("MCASH");
        if (checkMap != null && "9999".equals(checkMap.get("code"))) {
            throw new McpCommonException(checkMap.get("message"), "/m/main.do");
        }

        Map<String, String> limitChkMap = mcashService.chkMcashUse();

        String errorCause = limitChkMap.get("resultMsg");
        String resultCd = limitChkMap.get("resultCd");

        if (!"0000".equals(resultCd)) {
            throw new McpCommonException(errorCause);
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || "".equals(userSession.getUserId())) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        model.addAttribute("mcashMainUrl", mcashService.getMcashMainUrl(userSession.getUserId()));
        return "/mobile/mcash/mcashMain";
    }

    /**
     * 설명 : M쇼핑할인 가입 팝업
     *
     * @param request
     * @param model
     * @return
     * @Date : 2024.08.09
     */
    @RequestMapping(value = { "/m/mcash/mcashJoinPop.do"}, method = RequestMethod.GET)
    public String mcashJoinPop(HttpServletRequest request, ModelMap model) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        // 1. 가입조건 체크
        Map<String, String> map = mcashService.getMcashJoinCondition(userSession);
        String resultCode = map.get("RESULT_CODE");

        // 2. 회선, 요금제, M쇼핑할인 이용불가 요금제 조회
        List<McashUserDto> mcashCntrList = mcashService.getMcashAvailableCntrList(userSession.getUserId());

        if ("0000".equals(resultCode) && mcashCntrList.size() > 0) {
            model.addAttribute("mcashCntrList", mcashCntrList);
        }

        model.addAttribute("custNm", userSession.getName());
        model.addAttribute("RESULT_CODE", resultCode);

        return "/mobile/mcash/mcashJoinPop";
    }

    /**
     * 설명 : M쇼핑할인 가입 연동
     *
     * @Date : 2024.08.01
     * @param request
     * @return  Map<String,String>
     */
    @RequestMapping(value = "/mcash/joinMcashUserAjax.do")
    @ResponseBody
    public Map<String, String> joinMcashUserAjax(HttpServletRequest request, @RequestParam(value = "svcCntrNo") String svcCntrNo) {

        if (StringUtils.isEmpty(svcCntrNo)) {
            throw new McpCommonJsonException("1000", INVALID_REFERER_EXCEPTION);
        }

        // 1. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("1001", NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. 유저 정보 확인
        McashUserDto mcashUserDto = mcashService.getMcashUserByUserid(userSession.getUserId());
        if (mcashUserDto != null && MCASH_USER_STATUS_ACTIVE.equals(mcashUserDto.getStatus())) {
            throw new McpCommonJsonException("1002", "이미 가입 중인 서비스입니다.");
        }

        // ============ STEP START ============
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        // 3-1. session 확인
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("1003", NICE_CERT_EXCEPTION);
        }
        // 3-2. 최소 스텝 수 확인
        if(certService.getStepCnt() < 3 ){
            throw new McpCommonJsonException("1004", STEP_CNT_EXCEPTION);
        }

        // 3-3. 스텝종료여부, 이름, 생년월일, ci, 서비스계약번호
        String[] certKey= {"urlType", "stepEndYn", "name", "birthDate", "connInfo"};
        String[] certValue= {"joinMcashUser", "Y", sessNiceRes.getName(), sessNiceRes.getBirthDate(), sessNiceRes.getConnInfo()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException(vldReslt.get("RESULT_CODE"), vldReslt.get("RESULT_DESC"));
        }
        // ============ STEP END ============

        // 4. 가입 연동 처리(svcCntrNo : 가입할 서비스계약번호)
        Map<String, String> rtnMap = mcashService.joinMcashUser(svcCntrNo);

        return rtnMap;
    }

    /**
     * 설명 : M쇼핑할인 회선변경 연동
     *
     * @Date : 2024.08.08
     * @param request
     * @return  Map<String,String>
     */
    @RequestMapping(value = "/mcash/cntrChgMcashUserAjax.do")
    @ResponseBody
    public Map<String, String> cntrChgMcashUserAjax(HttpServletRequest request, @RequestParam(value = "svcCntrNo") String svcCntrNo) {

        if (StringUtils.isEmpty(svcCntrNo)) {
            throw new McpCommonJsonException("1000", INVALID_REFERER_EXCEPTION);
        }

        // 1. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("1001", NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. 유저 정보 확인
        McashUserDto mcashUserDto = mcashService.getMcashUserByUserid(userSession.getUserId());
        if (mcashUserDto != null && svcCntrNo.equals(mcashUserDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("1002", "변경가능한 회선이 없습니다.");
        }

        // 3. 회선변경 연동 처리(svcCntrNo : 가입할 서비스계약번호)
        Map<String, String> rtnMap = mcashService.changeMcashUser(svcCntrNo);

        return rtnMap;
    }

    /**
     * 설명 : M쇼핑할인 가입 회원 확인
     *
     * @param request
     * @return
     * @Date : 2024.08.09
     */
    @RequestMapping(value = "/mcash/checkMcashUserAjax.do")
    @ResponseBody
    public Map<String, Object> checkMcashUserAjax(HttpServletRequest request) {
        Map<String, Object> rtnMap = new HashMap<>();
        String mcashUserYn = "N";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        McashUserDto mcashUserInfo = mcashService.getMcashUserByUserid(userSession.getUserId());
        if ( mcashUserInfo != null && MCASH_USER_STATUS_ACTIVE.equals(mcashUserInfo.getStatus()) ) {
            mcashUserYn = "Y";
        }
        rtnMap.put("mcashUserYn", mcashUserYn);

        return rtnMap;
    }

    /**
     * 설명 : M쇼핑할인 이용 가능 회선 목록 조회
     *
     * @param request
     * @return
     * @Date : 2024.08.02
     */
    @RequestMapping(value = "/mcash/getMcashAvailableCntrListAjax.do")
    @ResponseBody
    public Map<String, Object> getMcashAvailableCntrListAjax(HttpServletRequest request) {
        Map<String, Object> rtnMap = new HashMap<>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McashUserDto> availableCntrList = mcashService.getMcashAvailableCntrList(userSession.getUserId());
        rtnMap.put("availableCntrList", availableCntrList);

        return rtnMap;
    }

    /**
     * 설명 : M쇼핑할인 회선 변경 팝업
     *
     * @param request
     * @param model
     * @return
     * @Date : 2024.07.09
     */
    @RequestMapping(value = {"/mcash/mcashCntrChgPop.do", "/m/mcash/mcashCntrChgPop.do"}, method = RequestMethod.GET)
    public String mcashCntrChgPop(HttpServletRequest request, ModelMap model) {

        // 1. 리턴페이지 설정
        String jspPageName = "/portal/mcash/mcashCntrChgPop";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            jspPageName = "/mobile/mcash/mcashCntrChgPop";
        }

        // M쇼핑할인 회선, 요금제 정보 조회
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McashUserDto> mcashCntrList = mcashService.getMcashAvailableCntrList(userSession.getUserId());

        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");

            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("서비스 계약번호,휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        }

        model.addAttribute("mcashCntrList", mcashCntrList);
        return jspPageName;
    }


    /**
     * 설명 : M쇼핑할인 해지 연동
     *
     * @Date : 2024.08.08
     * @param request
     * @return  Map<String,String>
     */
    @RequestMapping(value = "/mcash/svcCanMcashUserAjax.do")
    @ResponseBody
    public Map<String, String> svcCanMcashUserAjax(HttpServletRequest request) {

        // 1. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("1001", NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. 유저 정보 확인
        McashUserDto mcashUserDto = mcashService.getMcashUserByUserid(userSession.getUserId());
        if (mcashUserDto == null || MCASH_USER_STATUS_CANCEL.equals(mcashUserDto.getStatus())) {
            throw new McpCommonJsonException("1002", "해지할 회선이 없습니다.");
        }

        // 3. M쇼핑할인 해지 연동 처리(userid : 해지할 포털ID, evntTypeDtl: 해지유형)
        Map<String, String> rtnMap = mcashService.cancelMcashUser(userSession.getUserId(), MCASH_EVENT_CANCEL_MCASH);

        return rtnMap;
    }

    /**
     * 설명 : M쇼핑할인 서비스 해지 팝업
     *
     * @Date : 2024.07.18
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/mcash/mcashSvcCanPop.do", "/m/mcash/mcashSvcCanPop.do"}, method = RequestMethod.GET)
    public String mcashSvcCanPop(HttpServletRequest request, ModelMap model) {

        // 1. 리턴페이지 설정
        String jspPageName = "/portal/mcash/mcashSvcCanPop";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            jspPageName = "/mobile/mcash/mcashSvcCanPop";
        }

        // M쇼핑할인 서비스 해지할 회선 조회
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        McashUserDto mcashJoinInfo = mcashService.getMcashJoinInfo(userSession.getUserId());
        String mobileNo = "-";
        if (mcashJoinInfo != null) {
            // 마스킹해제
            if(SessionUtils.getMaskingSession() > 0 ) {
                model.addAttribute("maskingSession", "Y");
                mobileNo = StringUtil.getMobileFullNum(StringUtil.NVL(mcashJoinInfo.getMobileNo(),""));

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
                mobileNo = StringUtil.NVL(mcashJoinInfo.getMkMobileNo(), "-");
            }
        }
        model.addAttribute("mobileNo", mobileNo);

        return jspPageName;
    }

    @RequestMapping(value = "/mcash/getShopDiscountRateListAjax.do")
    @ResponseBody
    public List<McashShopDto> getShopDiscountRateList() {
        return mcashService.getShopDiscountRateList();
    }

    @RequestMapping(value = "/mcash/checkMcashUseAjax.do")
    @ResponseBody
    public Map<String, String> checkMcashUseAjax() {
        return mcashService.chkMcashUse();
    }
}