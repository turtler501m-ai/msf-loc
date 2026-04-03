package com.ktmmobile.msf.form.servicechange.controller;

import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.dto.NiceResDto;
import com.ktmmobile.msf.system.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.vo.*;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.*;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.service.MaskingSvc;
import com.ktmmobile.msf.form.servicechange.service.SfMypageSvc;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class PauseController {

    private static Logger logger = LoggerFactory.getLogger(PauseController.class);

    @Autowired
    private SfMypageSvc mypageService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private CertService certService;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    /**
     * 일시정지 첫화면
     */
    @RequestMapping(value= {"/m/mypage/suspendView01.do","/mypage/suspendView01.do"})
    public String suspendView01(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String pageUrl = "";
        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            pageUrl = "/mobile/mypage/pause/pauseInfo";
            checkOverlapDto.setRedirectUrl("/m/mypage/suspendView01.do");
        } else {
            pageUrl = "/portal/mypage/pause/pauseInfo";
            checkOverlapDto.setRedirectUrl("/mypage/suspendView01.do");
        }

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
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

        String subStatus = "-";
        String susCnt = "-";
        String susDays = "-";
        int solSusDays = 0;
        String expectSpDate = "-";
        String remainSusCnt = "";

        try {
            /** 26. 일시정지이력조회(X26)
            * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
            * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
            * @param String termGubun : 조회기간 1:최근 1년, 'A' : 전체사용 기간
            * */
            MpSuspenPosHisVO suspenPosHisVO = mPlatFormService.suspenPosHis( searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), "1");

            remainSusCnt = suspenPosHisVO.getRemainSusCnt();
            subStatus = suspenPosHisVO.getSubStatus();
            susCnt = suspenPosHisVO.getSusCnt();
            susDays = suspenPosHisVO.getSusDays();
            solSusDays = Integer.parseInt(StringUtil.NVL(suspenPosHisVO.getSolSusDays(),"0"));
            expectSpDate = StringUtil.NVL(suspenPosHisVO.getExpectSpDate(),"-");
            expectSpDate = StringUtil.yyyymmddDot(expectSpDate, ".");

        } catch (SelfServiceException e) {
            model.addAttribute("RESULT_CODE", "001");
        } catch (SocketTimeoutException e){
            model.addAttribute("RESULT_CODE", "002");
        }

        // 이력
        model.addAttribute("subStatus", subStatus);
        model.addAttribute("susCnt", susCnt);
        model.addAttribute("susDays", susDays);
        model.addAttribute("solSusDays", solSusDays);
        model.addAttribute("expectSpDate", expectSpDate);
        model.addAttribute("remainSusCnt", remainSusCnt);
        // 이력끝

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            searchVO.setUserName(userSession.getName());
            String[] nums = StringUtil.getMobileNum(searchVO.getCtn());
            String telNo = nums[0]+"-"+nums[1]+"-"+nums[2];
            searchVO.setCtn(telNo);

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
            searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
            searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        }


        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("USER_DI", userSession.getPin());
        model.addAttribute("ncn", searchVO.getNcn());


        return pageUrl;
    }

    /**
     * 일시정지가능여부 조회 ajax
     */
    @RequestMapping("/mypage/getSuspenPosInfoAjax.do")
    @ResponseBody
    public Map<String, Object> getSuspenPosInfoAjax(@RequestParam(value = "strNcn", required=false, defaultValue="") String strNcn)  {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("0001" ,F_BIND_EXCEPTION);
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        //정회원
        if(cntrList.size() <= 0){
            throw new McpCommonJsonException("0098" ,NOT_FULL_MEMBER_EXCEPTION);
        }

        String custId = "";
        String ctn = "";
        String contractNum = "";
        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (strNcn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                custId = mcpUserCntrMngDto.getCustId();
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                contractNum = mcpUserCntrMngDto.getContractNum();
                break;
            }
        }

        if ("".equals(custId)) {
            throw new McpCommonJsonException("0098" ,NOT_FULL_MEMBER_EXCEPTION);
        }

        // ================ STEP START ================
        // 계약번호
        String[] certKey= {"urlType", "contractNum"};
        String[] certValue= {"getSuspenPosInfo", contractNum};
        certService.vdlCertInfo("C", certKey, certValue);
        // ================ STEP END ================

        try {
            /** 27. 일시정지가능여부 조회
            * */
            MpSuspenPosInfoVO suspenPosInfoVO  = mPlatFormService.suspenPosInfo( strNcn, ctn, custId, "");
            Map<String, String> suspenPosInfoMap = suspenPosInfoVO.getMap();
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("ctnStatus", suspenPosInfoMap.get("ctnStatus"));
            rtnMap.put("rsltInd", suspenPosInfoMap.get("rsltInd"));
            rtnMap.put("rsltMsg", suspenPosInfoMap.get("rsltMsg"));
            rtnMap.put("insurMsg", suspenPosInfoMap.get("insurMsg"));

        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "001");
        } catch (SocketTimeoutException e){
            rtnMap.put("RESULT_CODE", "002");
        }

        MpPcsLostInfoVO mPcslostinfovo = null;

        try {
            String rsltCd = ""; // Y 만정상
            String runMode = ""; // I 가능/ U이미신청
            String subStatusLastAct = ""; // SUS : 일시정지 상태
            String coldeLinqStatus = ""; // 미납여부 N:정상 / D:미납
            String rsltMsg = "";
            mPcslostinfovo = mPlatFormService.pcsLostInfo(strNcn, ctn, custId);
            Map<String, String> mPcslostinfovoMap = mPcslostinfovo.getMap();

            if(mPcslostinfovoMap !=null) {

                rsltCd = StringUtil.NVL(mPcslostinfovoMap.get("rsltCd"),"");
                runMode = StringUtil.NVL(mPcslostinfovoMap.get("runMode"),"");
                subStatusLastAct = StringUtil.NVL(mPcslostinfovoMap.get("subStatusLastAct"),"");
                coldeLinqStatus = StringUtil.NVL(mPcslostinfovoMap.get("coldeLinqStatus"),"");
                rsltMsg = StringUtil.NVL(mPcslostinfovoMap.get("rsltMsg"),"");
            }
            rtnMap.put("runMode", runMode);
        } catch (SelfServiceException e) {
            logger.error("Exception e : {}", e.getMessage());
        }  catch(Exception e) {
            logger.debug("pcsLostInfo==>Error");
        }

        return rtnMap;
    }

    /**
     * 일시정지 두번째 화면
     */
    @RequestMapping(value= {"/m/mypage/suspendView02.do","/mypage/suspendView02.do"})
    public String suspendView02(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO, HttpSession session) {

        String pageUri = "/portal/mypage/pause/pauseApply";
        String redirectUri ="/mypage/suspendView01.do";
        // 화면분기
        if("Y".equals(NmcpServiceUtils.isMobile())){
            pageUri = "/mobile/mypage/pause/pauseApply";
            redirectUri ="/m/mypage/suspendView01.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        //정회원
        if(cntrList.size() <= 0){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        if (searchVO.getNcn() == null) {
            return "redirect:"+redirectUri;
        }

        String ctn = "";
        String contractNum = "";
        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (searchVO.getNcn().equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                contractNum = mcpUserCntrMngDto.getContractNum();
                break;
            }
        }

        NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);
        //비정상 접근 확인

        if (("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName))){
            logger.debug("인증 SKIP 처리");
        } else {
            if (niceResDto == null || !niceResDto.getDupInfo().equals(userSession.getPin())) {
                throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION,redirectUri);
            }
        }

        // ================ STEP START ================
        // 계약번호
        String[] certKey= {"urlType", "contractNum"};
        String[] certValue= {"chkMemberAuth", contractNum};
        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            // 일시정지/해제 초기화면으로 이동
            throw new McpCommonException(vldReslt.get("RESULT_CODE"), redirectUri);
        }
        // ================ STEP END ================

        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        searchVO.setCtn(StringMakerUtil.getPhoneNum(ctn));

        //디폴트 종료일 세팅 90일
        String endDay = "";
        String toDay = "";
        try {
            toDay = DateTimeUtil.getFormatString("yyyy-MM-dd");
            endDay = DateTimeUtil.addDays(90,"yyyy-MM-dd");
        } catch (ParseException e) {
            throw new McpCommonException(ExceptionMsgConstant.COMMON_EXCEPTION);
        }
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("toDay", toDay);
        model.addAttribute("endDay", endDay);

        return pageUri;
    }

    // 일시정지 신청
    @RequestMapping(value="/mypage/suspenChgAjax.do")
    @ResponseBody
    public Map<String,Object> suspenChgAjax(HttpServletRequest request, ModelMap model,HttpSession session,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            String reasonCode, String cpEndDt, String cpStartDt, String cpPwdInsert,String userMemo)  {

        Map<String, Object> rtnMap = new HashMap<String, Object>();

        String message = "오류가 밸생했습니다.다시 시도해 주세요.";
        String returnCode= "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "01";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;

        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
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
        String contractNum= "";

        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                custId = mcpUserCntrMngDto.getCustId();
                contractNum= mcpUserCntrMngDto.getContractNum();
                break;
            }
        }

        NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);
        //비정상 접근 확인
        if(niceResDto == null) {
            returnCode = "04";
            message = "본인 인증정보가 없습니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        if (("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName))){
            logger.debug("인증 SKIP 처리");
        } else {

            if(!userSession.getName().equalsIgnoreCase(niceResDto.getName()) || !userSession.getBirthday().equalsIgnoreCase(niceResDto.getBirthDate())) {
                returnCode = "05";
                message = "본인 인증한 정보가 틀립니다.";
                rtnMap.put("returnCode",returnCode);
                rtnMap.put("message",message);
                return rtnMap;
            }
        }

        try {
            String cpDateYn = "Y";

            if(!StringUtils.isEmpty(ncn) && !StringUtils.isEmpty(ctn) && !StringUtils.isEmpty(custId) && !StringUtils.isEmpty(reasonCode)
                    && !StringUtils.isEmpty(cpStartDt) && !StringUtils.isEmpty(cpEndDt) ) {

                // ================ STEP START ================
                // 1. 최소 스텝 수 체크
                if(certService.getStepCnt() < 6 ){
                    returnCode = "STEP01";
                    rtnMap.put("returnCode", returnCode);
                    rtnMap.put("message", STEP_CNT_EXCEPTION);
                    return rtnMap;
                }

                // 2. 최종 데이터 체크: step종료 여부, 계약번호, DI
                String[] certKey= {"urlType", "stepEndYn", "contractNum", "dupInfo"};
                String[] certValue= {"saveSuspenChgForm", "Y", contractNum, niceResDto.getDupInfo()};
                Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    returnCode = "STEP02";
                    rtnMap.put("returnCode", returnCode);
                    rtnMap.put("message", vldReslt.get("RESULT_DESC"));
                    return rtnMap;
                }
                // ================ STEP END ================

                mPlatFormService.suspenChg(ncn, ctn, custId, reasonCode, userMemo, cpDateYn, cpEndDt, cpStartDt, cpPwdInsert );
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
        }

        rtnMap.put("returnCode",returnCode);
        rtnMap.put("message",message);

        return rtnMap;
    }

    /**
     * 일시정지 세번째 화면
     */
    @RequestMapping(value= {"/m/mypage/suspendView03.do","/mypage/suspendView03.do"})
    public String suspendView03(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            String cpStartDt, String cpEndDt, String reasonCode, String gubun, String subStatusDate)  {

        String pageUri = "/portal/mypage/pause/pauseComplete";
        String redirectUri ="/mypage/suspendView01.do";
        // 화면분기
        if("Y".equals(NmcpServiceUtils.isMobile())){
            pageUri = "/mobile/mypage/pause/pauseComplete";
            redirectUri ="/m/mypage/suspendView01.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/m/loginForm.do";
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String ncn = StringUtil.NVL(searchVO.getNcn(),"");
        if ( "".equals(ncn) ) {
            ResponseSuccessDto mbox = new ResponseSuccessDto();
            mbox.setRedirectUrl(redirectUri);
            mbox.setSuccessMsg("잘못된 접근입니다.");
            model.addAttribute("responseSuccessDto", mbox);
            return "/common/successRedirect";
        }

        String cpStartDt1 = StringUtil.NVL(cpStartDt,"");
        String cpEndDt1 = StringUtil.NVL(cpEndDt,"");
        String reasonCode1 = StringUtil.NVL(reasonCode,"");
        String gubun1 = StringUtil.NVL(gubun,"");
        String subStatusDate1 = StringUtil.NVL(subStatusDate,"");

        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        String startDate = "-";
        String endDate = "-";
        String toDay = "-";
        if(!"".equals(cpStartDt1)) {
            startDate = DateTimeUtil.getFormatString(cpStartDt1, "yyyyMMdd","yyyy.MM.dd");
        }
        if(!"".equals(cpEndDt1)) {
            endDate = DateTimeUtil.getFormatString(cpEndDt1, "yyyyMMdd","yyyy.MM.dd");
        }
        if(!"".equals(subStatusDate1)) {
            subStatusDate1 = DateTimeUtil.getFormatString(subStatusDate1, "yyyyMMdd","yyyy.MM.dd");
        }
        toDay = DateTimeUtil.getFormatString("yyyy.MM.dd");

        String reasonDoc = "";
        if("99".equals(reasonCode1)) {
            reasonDoc = "정지없음";
        } else if("01".equals(reasonCode1)) {
            reasonDoc = "발신정지";
        } else if("02".equals(reasonCode1)) {
            reasonDoc = "착신정지";
        } else if("03".equals(reasonCode1)) {
            reasonDoc = "착발신정지";
        }
        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));

        model.addAttribute("toDay", toDay);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("reasonDoc", reasonDoc);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("gubun", gubun1);
        model.addAttribute("subStatusDate", subStatusDate);
        model.addAttribute("ncn", ncn);
        return pageUri;
    }

    // 일시정지 해제
    @RequestMapping(value = {"/mypage/suspendCnl.do", "/m/mypage/suspendCnl.do"}  )
    public String suspendCnl(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO, HttpSession session,RedirectAttributes redirect){

        String pageUri = "/portal/mypage/pause/pauseCnl";
        String redirectUri ="/mypage/suspendView01.do";
        // 화면분기
        if("Y".equals(NmcpServiceUtils.isMobile())){
            pageUri = "/mobile/mypage/pause/pauseCnl";
            redirectUri ="/m/mypage/suspendView01.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 354
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        if (searchVO.getNcn() == null) {
            return "redirect:"+redirectUri;
        }

        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String ctn = searchVO.getCtn();
        String contractNum= searchVO.getContractNum();

        NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);
        //비정상 접근 확인

        if (("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName))){
            logger.debug("인증 SKIP 처리");
        } else {
            if (niceResDto == null || !niceResDto.getDupInfo().equals(userSession.getPin())) {
                throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION,redirectUri);
            }
        }

        // ================ STEP START ================
        // 계약번호
        String[] certKey= {"urlType", "contractNum"};
        String[] certValue= {"chkMemberAuth", contractNum};
        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            // 일시정지/해제 초기화면으로 이동
            throw new McpCommonException(vldReslt.get("RESULT_CODE"), redirectUri);
        }
        // ================ STEP END ================

        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        searchVO.setCtn(StringUtil.getMobileFullNum(ctn));

        Map<String,String> sndarvStatCdMap  = new HashMap<String, String>();
        sndarvStatCdMap.put("01","발신정지");
        sndarvStatCdMap.put("02","착신정지");
        sndarvStatCdMap.put("03","발착신정지");

        String rsltInd = "";
        String sndarvStatCd = "";
        String subStatusDate = "";

        try {
            /** 28. 일시정지해제가능여부조회(X28)
            * */
            MpSuspenCnlPosInfoInVO suspenCnlPosInfo  = mPlatFormService.suspenCnlPosInfo( ncn, ctn, custId);
            Map<String, String> suspenCnlPosInfoMap = suspenCnlPosInfo.getMap();
            rsltInd = suspenCnlPosInfoMap.get("rsltInd"); //일시정지 가능여부
            sndarvStatCd = suspenCnlPosInfoMap.get("sndarvStatCd");    //발/착신구분코드
            subStatusDate = StringUtil.NVL(suspenCnlPosInfoMap.get("subStatusDate"),"");    //일시정지일자

            try {
                if("".equals(subStatusDate) ){
                    subStatusDate = "-";
                } else {
                    subStatusDate = DateTimeUtil.changeFormat(subStatusDate, "yyyyMMddHHmmss","yyyy.MM.dd");
                }
            } catch (ParseException e) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            if (!"Y".equals(rsltInd)) {
                redirect.addAttribute("ncn", ncn);
                throw new McpCommonException("고객님께서는 온라인으로 일시 정지 해제가 불가합니다. \\n고객센터(1899-5000)로 문의를 부탁 드립니다." ,redirectUri);
            }

        } catch (SelfServiceException e) {
            throw new McpCommonException(COMMON_EXCEPTION,redirectUri);
        } catch (SocketTimeoutException e){
            throw new McpCommonException(COMMON_EXCEPTION,redirectUri);
        }

        model.addAttribute("rsltInd", rsltInd);
        model.addAttribute("sndarvStatCdNm", sndarvStatCdMap.get(sndarvStatCd));
        model.addAttribute("sndarvStatCd", sndarvStatCd);
        model.addAttribute("subStatusDate", subStatusDate);
        model.addAttribute("ncn", ncn);
        model.addAttribute("userName", searchVO.getUserName());
        model.addAttribute("ctn", StringMakerUtil.getPhoneNum(searchVO.getCtn()));

        return pageUri;
    }


    /**
     * 일신정지 해지 신청 ajax
     */
    @RequestMapping(value="/mypage/suspenCnlChgInAjax.do")
    @ResponseBody
    public Map<String, Object> suspenCnlChgInAjax( HttpServletRequest request, ModelMap model,HttpSession session,
            @RequestParam(value = "strNcn", required=false, defaultValue="") String strNcn
            , @RequestParam(value = "cpPwdInsert", required=false, defaultValue="") String cpPwdInsert ) {

        String message = "오류가 밸생했습니다.다시 시도해 주세요.";
        String returnCode= "";
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "01";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        //정회원
        if(cntrList.size() <= 0){
            returnCode = "02";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        String ncn = strNcn;
        if ( "".equals(ncn) ) {
            returnCode = "03";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        String ctn = "";
        String custId = "";
        String contractNum= "";

        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                custId = mcpUserCntrMngDto.getCustId();
                contractNum= mcpUserCntrMngDto.getContractNum();
                break;
            }
        }

        NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);
        //비정상 접근 확인
        if(niceResDto == null) {
            returnCode = "04";
            message = "본인 인증한 정보가 틀립니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        if (("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName))){
            logger.debug("인증 SKIP 처리");
        } else {

            if(!userSession.getName().equalsIgnoreCase(niceResDto.getName()) || !userSession.getBirthday().equalsIgnoreCase(niceResDto.getBirthDate())) {
                returnCode = "05";
                message = "본인 인증한 정보가 틀립니다.";
                rtnMap.put("returnCode",returnCode);
                rtnMap.put("message",message);
                return rtnMap;
            }
        }

        // ================ STEP START ================
        // 1. 최소 스텝 수 체크
        if(certService.getStepCnt() < 6 ){
            returnCode = "STEP01";
            rtnMap.put("returnCode", returnCode);
            rtnMap.put("message", STEP_CNT_EXCEPTION);
            return rtnMap;
        }

        // 2. 최종 데이터 체크: step종료 여부, 계약번호, DI
        String[] certKey= {"urlType", "stepEndYn", "contractNum", "dupInfo"};
        String[] certValue= {"saveSuspenChgForm", "Y", contractNum, niceResDto.getDupInfo()};
        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            returnCode = "STEP02";
            rtnMap.put("returnCode", returnCode);
            rtnMap.put("message", vldReslt.get("RESULT_DESC"));
            return rtnMap;
        }
        // ================ STEP END ================

        String cpPwdInsert1 = cpPwdInsert;
        try {
            //pwdType   비밀번호 타입 2   M   PP: 일시정지, CP: 개인정보 암호
            String pwdType = "PP";
            if ("".equals(cpPwdInsert1)) {
                cpPwdInsert1 = "12345678"; // 비밀번호 입력 하지 않으며  임의 번호 설정
            }
            /** 30. 일시정지해제신청(X30)
            * */

            MpSuspenCnlChgInVO suspenCnlChgIn= mPlatFormService.suspenCnlChgIn(ncn, ctn, custId, pwdType, cpPwdInsert1) ;
            returnCode = AJAX_SUCCESS;
            message = "성공적으로 처리 하였습니다.";

        } catch (SelfServiceException e) {
            returnCode = "001";
            message = getErrMsg(e.getMessage());
        } catch (SocketTimeoutException e){
            returnCode = "002";
            message = getErrMsg(e.getMessage());
        }
        rtnMap.put("returnCode",returnCode);
        rtnMap.put("message",message);
        return rtnMap;
    }

    /**
     * 일시정지이력조회 ajax
     */
    @RequestMapping(value = {"/mypage/suspenPosHisPop.do", "/m/mypage/suspenPosHisPop.do"})
    public String suspenPosHisPop(HttpServletRequest request, Model model,@RequestParam(value = "strNcn", required=false, defaultValue="") String strNcn)  {


        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/m/loginForm.do";

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        //정회원
        if(cntrList.size() <= 0){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String custId = "";
        String ctn = "";
        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            if (strNcn.equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                custId = mcpUserCntrMngDto.getCustId();
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                break;
            }
        }

        List<MpBkInfoDto> suspenPosHisList =  new ArrayList<MpBkInfoDto>();
        try {
            /** 26. 일시정지이력조회(X26)
            * @param String ncn : 사용자 서비스 계약번호 9자리 [-]제외
            * @param String ctn : 사용자 전화번호 11자리 (10자리의 경우 앞에 0 추가)
            * @param String termGubun : 조회기간 1:최근 1년, 'A' : 전체사용 기간
            * */
            MpSuspenPosHisVO suspenPosHisVO = mPlatFormService.suspenPosHis( strNcn, ctn, custId, "1");
            suspenPosHisList = suspenPosHisVO.getItemList();
            if( suspenPosHisList != null && !suspenPosHisList.isEmpty()) {
                for(MpBkInfoDto dto : suspenPosHisList) {
                    String csaActivityRsnDesc = StringUtil.NVL(dto.getCsaActivityRsnDesc(),"");
                    String remainSusCnt = StringUtil.yyyymmddDot(dto.getRemainSusCnt(),".");
                    String colSusDays = StringUtil.yyyymmddDot(dto.getcolSusDays(),".");
                    csaActivityRsnDesc = csaActivityRsnDesc.replace("고객요청", "").replace("-", "");

                    dto.setCsaActivityRsnDesc(csaActivityRsnDesc);
                    dto.setRemainSusCnt(remainSusCnt);
                    dto.setColSusDays(colSusDays);
                }
            }

//            model.addAttribute("subStatus", suspenPosHisVO.getSubStatus());
//            model.addAttribute("reckonFromDate", suspenPosHisVO.getReckonFromDate());
//            model.addAttribute("susCnt", suspenPosHisVO.getSusCnt());
//            model.addAttribute("susDays", suspenPosHisVO.getSusDays());
//            model.addAttribute("remainSusCnt", suspenPosHisVO.getRemainSusCnt());
//            model.addAttribute("solSusDays", suspenPosHisVO.getSolSusDays());
//            model.addAttribute("remainOgDays", suspenPosHisVO.getRemainOgDays());
//            model.addAttribute("m2mRemainSusCnt", suspenPosHisVO.getM2mRemainSusCnt());
//            model.addAttribute("expectSpDate", suspenPosHisVO.getExpectSpDate());

        } catch (SelfServiceException e) {
            logger.debug("SelfServiceException");
        } catch (SocketTimeoutException e){
            logger.debug("SocketTimeoutException");
        } catch(Exception e) {
            logger.debug("Exception");
        }

        model.addAttribute("suspenPosHisList", suspenPosHisList);
        String pageUri = "/portal/mypage/pause/pauseHistPop";
        // 화면분기
        if("Y".equals(NmcpServiceUtils.isMobile())){
            pageUri = "/mobile/mypage/pause/pauseHistPop";
        }
        return pageUri;
    }


    /**
     * 분실신고 화면
     */
    @RequestMapping(value= {"/m/mypage/lostView.do","/mypage/lostView.do"})
    public String lostView(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        String pageUri = "/portal/mypage/pause/lostView";
        String redirectUri ="/mypage/lostView.do";
        // 화면분기
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            pageUri = "/mobile/mypage/pause/lostView";
            redirectUri ="/m/mypage/lostView.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 351
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
            searchVO.setUserName(userSession.getName());
            String[] nums = StringUtil.getMobileNum(searchVO.getCtn());
            String telNo = nums[0]+"-"+nums[1]+"-"+nums[2];
            searchVO.setCtn(telNo);

            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름,휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        }else {
            searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
            searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        }

        String toDay = DateTimeUtil.getFormatString("yyyy-MM-dd");
        model.addAttribute("toDay", toDay);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);

        return pageUri;
    }

    /**
     * 분실신고가능여부 조회 ajax
     */
    @RequestMapping(value="/mypage/pcsLostInfoAjax.do")
    @ResponseBody
    public Map<String,Object> pcsLostInfoAjax(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        String message = "";
        String returnCode= "";
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "01";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
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
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                custId = mcpUserCntrMngDto.getCustId();
                break;
            }
        }


        MpPcsLostInfoVO mPcslostinfovo = null;

        try {
            String rsltCd = ""; // Y 만정상
            String runMode = ""; // I 가능/ U이미신청
            String subStatusLastAct = ""; // SUS : 일시정지 상태
            String coldeLinqStatus = ""; // 미납여부 N:정상 / D:미납
            String rsltMsg = "";
            mPcslostinfovo = mPlatFormService.pcsLostInfo(searchVO.getNcn(), ctn, custId);
            Map<String, String> mPcslostinfovoMap = mPcslostinfovo.getMap();

            returnCode = "00";
            message = "";
            if(mPcslostinfovoMap !=null) {

                rsltCd = StringUtil.NVL(mPcslostinfovoMap.get("rsltCd"),"");
                runMode = StringUtil.NVL(mPcslostinfovoMap.get("runMode"),"");
                subStatusLastAct = StringUtil.NVL(mPcslostinfovoMap.get("subStatusLastAct"),"");
                coldeLinqStatus = StringUtil.NVL(mPcslostinfovoMap.get("coldeLinqStatus"),"");
                rsltMsg = StringUtil.NVL(mPcslostinfovoMap.get("rsltMsg"),"");

                if(runMode == null || "".equals(runMode)) {
                    returnCode = "00005";
                    message = "유심이 휴대폰과 분리되어있는 상태에서는 온라인으로 분실신고가 불가합니다."
                            + "</br>분실신고를 원하실 경우 고객센터(1899-5000)로 문의를 부탁드립니다.";
                    rtnMap.put("returnCode", returnCode);
                    rtnMap.put("message", message);
                    return rtnMap;
                }
                if(!"Y".equals(rsltCd)) {
                    returnCode = "00001";
                    message = rsltMsg;
                    rtnMap.put("returnCode",returnCode);
                    rtnMap.put("message",message);
                    return rtnMap;
                }
                if(!"I".equals(runMode)) {
                    returnCode = "00002";
                    message = rsltMsg;
                    rtnMap.put("returnCode",returnCode);
                    rtnMap.put("message",message);
                    return rtnMap;
                }
                if("SUS".equals(subStatusLastAct)) {
                    returnCode = "00003";
                    message = rsltMsg;
                    rtnMap.put("returnCode",returnCode);
                    rtnMap.put("message",message);
                    return rtnMap;
                }
                if("D".equals(coldeLinqStatus)) {
                    returnCode = "00004";
                    message = rsltMsg;
                    rtnMap.put("returnCode",returnCode);
                    rtnMap.put("message",message);
                    return rtnMap;
                }
            }

        } catch (SelfServiceException e) {
            returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(e.getMessage());
        } catch (SocketTimeoutException e){
            returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
        }

        rtnMap.put("returnCode",returnCode);
        rtnMap.put("message",message);
        return rtnMap;
    }


    /**
     * 분실신고 신청 ajax
     */
    @RequestMapping(value="/mypage/pcsLostChgAjax.do")
    @ResponseBody
    public Map<String,Object> pcsLostChgAjax(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            String loseType, String guideYn, String cntcTlphNo, String loseCont, String loseLocation, String strPwdInsert )  {

        String message = "";
        String returnCode= "";
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "01";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
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
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                custId = mcpUserCntrMngDto.getCustId();
                break;
            }
        }

        try {

            if( !StringUtils.isEmpty(loseType) && !StringUtils.isEmpty(guideYn) && !StringUtils.isEmpty(cntcTlphNo)
                    && !StringUtils.isEmpty(loseCont) && !StringUtils.isEmpty(loseLocation) && !StringUtils.isEmpty(strPwdInsert)){

                mPlatFormService.pcsLostChg(ncn, ctn, custId, loseType, guideYn, cntcTlphNo, loseCont, loseLocation, strPwdInsert);
                returnCode = "00";
                message = "";
            } else {
                returnCode = "30";
                message = "파라미터가 비정상입니다.";
            }

        } catch (SelfServiceException e) {
            returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(e.getMessage());
        } catch (SocketTimeoutException e){
            returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
        }

        rtnMap.put("returnCode",returnCode);
        rtnMap.put("message",message);
        return rtnMap;
    }


    /**
     * 분실신고 해제 화면
     */
    @RequestMapping(value= {"/m/mypage/lostCnlView.do","/mypage/lostCnlView.do"})
    public String lostCnlView(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        String pageUri = "/portal/mypage/pause/lostCnlView";
        // 화면분기
        if("Y".equals(NmcpServiceUtils.isMobile())){
            pageUri = "/mobile/mypage/pause/lostCnlView";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 348
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        MpPcsLostInfoVO mPcslostinfovo = null;
        String rsltCd = ""; // Y 만정상
        String runMode = ""; // I 가능/ U이미신청
        String subStatusLastAct = ""; // SUS : 일시정지 상태
        String coldeLinqStatus = ""; // 미납여부 N:정상 / D:미납
        try {
            // X33 : 분실신고 가능여부
            mPcslostinfovo = mPlatFormService.pcsLostInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());
            Map<String, String> mPcslostinfovoMap = mPcslostinfovo.getMap();

            if(mPcslostinfovoMap !=null) {

                rsltCd = StringUtil.NVL(mPcslostinfovoMap.get("rsltCd"),"");
                runMode = StringUtil.NVL(mPcslostinfovoMap.get("runMode"),"");
                subStatusLastAct = StringUtil.NVL(mPcslostinfovoMap.get("subStatusLastAct"),"");
                coldeLinqStatus = StringUtil.NVL(mPcslostinfovoMap.get("coldeLinqStatus"),"");
            }

        } catch (SelfServiceException e) {
            logger.error(e.getMessage());
        } catch (SocketTimeoutException e){
            logger.error(e.getMessage());
        }

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            searchVO.setUserName(userSession.getName());
            String[] nums = StringUtil.getMobileNum(searchVO.getCtn());
            String telNo = nums[0]+"-"+nums[1]+"-"+nums[2];
            searchVO.setCtn(telNo);

            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름,휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        }else {
            searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
            searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        }

        model.addAttribute("runMode", runMode);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("ncn", searchVO.getNcn());

        return pageUri;
    }

    /**
     * 분실신고 신청 ajax
     */
    @RequestMapping(value="/mypage/pcsLostCnlChgAjax.do")
    @ResponseBody
    public Map<String,Object> pcsLostCnlChgAjax(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            String strPwdNumInsert)  {

        String message = "";
        String returnCode= "";
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "01";
            message = "비정상적인 접근입니다.";
            rtnMap.put("returnCode",returnCode);
            rtnMap.put("message",message);
            return rtnMap;
        }

        int callCount = this.mPlatFormService.checkMpCallEventCount(userSession.getUserId(), "X35");
        if (callCount >= 10) {
            rtnMap.put("returnCode", "99");
            rtnMap.put("message","일일 허용 호출 건수를 초과 하였습니다.");
            return rtnMap;
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
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
                ctn = mcpUserCntrMngDto.getCntrMobileNo() ;
                custId = mcpUserCntrMngDto.getCustId();
                break;
            }
        }

        try {
            String pwdType = "PP";
            if( !StringUtils.isEmpty(strPwdNumInsert)){

                mPlatFormService.pcsLostCnlChg(ncn, ctn, custId, strPwdNumInsert, pwdType);
                returnCode = "00";
                message = "";
            } else {
                returnCode = "30";
                message = "파라미터가 비정상입니다.";
            }

        } catch (SelfServiceException e) {
            returnCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(e.getMessage());
        } catch (SocketTimeoutException e){
            returnCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
        }

        rtnMap.put("returnCode",returnCode);
        rtnMap.put("message",message);
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

    private String getErrCd(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        result = arg[0]; //N:성공, S:시스템에서, E:Biz 에러
        return result;
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
