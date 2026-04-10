package com.ktmmobile.mcp.content.controller;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.McpIpStatisticDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mplatform.dto.MoscOtpSvcInfoRes;
import com.ktmmobile.mcp.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.mcp.common.mplatform.vo.MoscCombStatMgmtInfoOutVO;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.content.dto.ReShareDataReqDto;
import com.ktmmobile.mcp.content.dto.ReShareDataResDto;
import com.ktmmobile.mcp.content.service.ReShareDataSvc;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpFarPriceDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import org.apache.commons.lang3.StringUtils;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.*;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;


@Controller
public class ReShareDataController {

    private static final Logger logger = LoggerFactory.getLogger(ReShareDataController .class);

    @Autowired
    IpStatisticService ipstatisticService;

    @Autowired
    MypageService mypageService;

    @Autowired
    ReShareDataSvc reShareDataSvc;

    @Autowired
    CertService certService;

    @Autowired
    private MaskingSvc maskingSvc;

    /**
     * 함께쓰기 (인증전)
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = { "/myShareDataCntrInfo.do", "/m/myShareDataCntrInfo.do" })
    public String doMyShareDataCntrInfo(HttpServletRequest request, Model model
             ,@ModelAttribute("searchVO") MyPageSearchDto searchVO
             ) {
        String returnUrl = "/portal/content/myShareDataCntrInfo";
        String userRtnUrl = "/content/myShareDataView.do";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            userRtnUrl = "/m/content/myShareDataView.do";
            returnUrl = "/mobile/content/myShareDataCntrInfo";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if(userSession != null){
            return "redirect:"+userRtnUrl;
        }

        return returnUrl;
    }

    /**
     * 함께쓰기 (인증후)
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param session
     * @param menuType
     * @param phoneNum
     * @return
     */

    @RequestMapping(value = { "/content/myShareDataView.do", "/m/content/myShareDataView.do" })
    public String doMyShareDataCntrView(HttpServletRequest request, Model model
             ,@ModelAttribute("searchVO") MyPageSearchDto searchVO, HttpSession session
             ,@RequestParam(value = "menuType", required = false) String menuType
             ,@RequestParam(value = "phoneNum", required = false) String phoneNum
             ,@RequestParam(value = "search", required = false) String search
        ) {

        String returnUrl = "/portal/content/myShareDataView";
        String rtnUrl = "/myShareDataCntrInfo.do";

        /*
         * if ("Y".equals(NmcpServiceUtils.isMobile())) { returnUrl =
         * "/mobile/content/myShareDataView"; rtnUrl = "/m/myShareDataCntrInfo.do"; }
         */

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/content/myShareDataView";
            rtnUrl = "/m/myShareDataCntrInfo.do";
        }


       //함께쓰기 조회버튼을 클릭 or 모바일버전 휴대폰번호 변경시에만 타는 로직
       if("Y".equals(search)) {
            ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();  //중복요청 체크
            checkOverlapDto.setRedirectUrl(rtnUrl);
            checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);

            if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
                model.addAttribute("responseSuccessDto", checkOverlapDto);
                model.addAttribute("MyPageSearchDto", searchVO);
                return "/common/successRedirect";
            }
       }

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);

        if(userSessionDto == null && authSmsDto  == null) {
            return "redirect:"+rtnUrl;
        }

        if(authSmsDto != null) {

            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();

            if(phoneNum != null) {
                userCntrMngDto.setCntrMobileNo(phoneNum);
            } else {
                userCntrMngDto.setSvcCntrNo(searchVO.getNcn());
            }

            //비회원로그인
            McpUserCntrMngDto cntrList =  mypageService.selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            // ============ STEP START ============
            // 결합대상 구분값, 계약번호, 핸드폰번호
            String[] certKey = {"urlType", "ncType", "contractNum", "mobileNo"};
            String[] certValue = {"chkMemberAuth", "0", cntrList.getContractNum(), cntrList.getCntrMobileNo()};
            Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
            if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))){
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), rtnUrl);
            }
            // ============ STEP END ============

            searchVO.setCustId(cntrList.getCustId());
            searchVO.setContractNum(cntrList.getContractNum());
            searchVO.setNcn(cntrList.getSvcCntrNo());
            searchVO.setCtn(cntrList.getCntrMobileNo());
            searchVO.setSubStatus(cntrList.getSubStatus());
            searchVO.setUserDivisionYn("02");
            model.addAttribute("searchVO", searchVO);
            session.setAttribute("userDivisionYn", searchVO.getUserDivisionYn());

        } else {

            String userId = userSessionDto.getUserId();

            // 본인이 가지고 있는 회선정보
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userId);

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                ResponseSuccessDto responseSuccessDto = getMessageBox();
                model.addAttribute("responseSuccessDto", responseSuccessDto);
                return "/common/successRedirect";
            }

            model.addAttribute("cntrList", cntrList);

        }

        String resultCode= "";
        String subStatus = "";
           //정지회선 체크
        if("S".equals(searchVO.getSubStatus())) {
            subStatus = searchVO.getSubStatus();
        }

        McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());

        //자회선 인증 처리
        String isShareChild = "N";
        String isShareParent = "N";
        String retvGubunCd = ""; // G(주기회선조회) / R(받기회선조회)

        //1. 공통코드 사용가능한 요금제 모회선 자회선 구분
        String shareChildNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(SHARE_RATE_CHILD_LIST,mcpUserCntrMngDto.getSoc()),"");
        String shareParentNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(SHARE_RATE_PARENT_LIST,mcpUserCntrMngDto.getSoc()),"");

        if( !"".equals(shareChildNm) ){
            isShareChild = "Y";
        }
        if( !"".equals(shareParentNm) ){
            isShareParent = "Y";
        }

        if( ("N".equals(isShareChild) && "N".equals(isShareParent)) || ("Y".equals(isShareChild) && "Y".equals(isShareParent))){ // 선택한 회선이 모자 둘다 아니거나 둘다일때 에러
            resultCode = "01";
        } else {

            //인자값 확인 검증..
            if (StringUtils.isBlank(searchVO.getContractNum())) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            String contractNum = searchVO.getContractNum();
            String ctn = searchVO.getCtn();
            String custId = searchVO.getCustId();
            String svcCntrNo = searchVO.getNcn();

            try{

                // 주기회선인 경우
                if( "Y".equals(isShareParent) ){
                    retvGubunCd = "G";
                    // 받기 회선 인 경우
                } else {
                    retvGubunCd = "R";
                }
                resultCode = this.getMoscCombStatMgmtInfo(svcCntrNo,ctn,custId,retvGubunCd);
            } catch(DataAccessException e) {
                logger.debug("reShareData DataAccessException");
            }  catch(Exception e){
                logger.debug("reShareData ERROR");
            }
        }


        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");

            UserSessionDto userSession = SessionUtils.getUserCookieBean();
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


      //  searchVO.setUserName(StringMakerUtil.getName(userSessionDto.getName()));
        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));

        model.addAttribute("mcpUserCntrMngDto", mcpUserCntrMngDto);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("resultCode", resultCode);
        model.addAttribute("subStatus", subStatus);
        model.addAttribute("retvGubunCd", retvGubunCd);
        model.addAttribute("menuType", menuType);

        return returnUrl;
    }

    /**
     * 함께쓰기 결합내역 조회 팝업
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param cntrMng
     * @param session
     * @return
     * @throws ParseException
     */

    @RequestMapping(value = { "/content/myShareDataList.do", "/m/content/myShareDataList.do" })
    public String doMyShareDataList(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO
            ,@ModelAttribute McpUserCntrMngDto cntrMng
             ,HttpSession session) throws ParseException {

        String returnUrl= "";
        if("R".equals(cntrMng.getRetvGubunCd())) {
            returnUrl = "/portal/content/myShareDataHistList";

            if ("Y".equals(NmcpServiceUtils.isMobile())) {
                returnUrl = "/mobile/content/myShareDataHistList";
            }
        }else {
            returnUrl = "/portal/content/myShareDataList";

            if ("Y".equals(NmcpServiceUtils.isMobile())) {
                returnUrl = "/mobile/content/myShareDataList";
            }
        }

        String userDivisionYn =  (String) session.getAttribute("userDivisionYn");
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession("shareData");
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null && authSmsDto  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        //비회원로그인
        if(authSmsDto != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(searchVO.getNcn());
            McpUserCntrMngDto cntrList =  mypageService.selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            // ============ STEP START ============
            // 결합대상 구분값, 계약번호
            String[] certKey = {"urlType", "ncType", "contractNum"};
            String[] certValue = {"getShareDataList", "0", cntrList.getContractNum()};
            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                String errorUrl = "/portal/errmsg/errorPop";
                if("Y".equals(NmcpServiceUtils.isMobile())) errorUrl = "/mobile/errmsg/errorPop";

                model.addAttribute("ErrorTitle", "결합내역 조회");
                model.addAttribute("ErrorMsg", vldReslt.get("RESULT_DESC"));
                return errorUrl;
            }
            // ============ STEP END ============

            searchVO.setCustId(cntrList.getCustId());
            searchVO.setContractNum(cntrList.getContractNum());
            searchVO.setNcn(cntrList.getSvcCntrNo());
            searchVO.setCtn(cntrList.getCntrMobileNo());
            searchVO.setSubStatus(cntrList.getSubStatus());
            model.addAttribute("searchVO", searchVO);

        } else {

            String userId = userSessionDto.getUserId();

            // 본인이 가지고 있는 회선정보
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userId);

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                ResponseSuccessDto responseSuccessDto = getMessageBox();
                model.addAttribute("responseSuccessDto", responseSuccessDto);
                return "/common/successRedirect";
            }

            model.addAttribute("cntrList", cntrList);
        }

        ReShareDataReqDto reShareDataReqDto = new ReShareDataReqDto();
        String contractNum = searchVO.getContractNum();
        String ctn = searchVO.getCtn();
        String custId = searchVO.getCustId();
        String svcCntrNo = searchVO.getNcn();
        String retvGubunCd = StringUtil.NVL(cntrMng.getRetvGubunCd(),"R");

        reShareDataReqDto.setCustId(custId);
        reShareDataReqDto.setNcn(svcCntrNo);
        reShareDataReqDto.setCtn(ctn);
        reShareDataReqDto.setRetvGubunCd("R"); //무조건 R로만 한다. as-is랑 동일

        //x86
        MoscCombStatMgmtInfoOutVO moscCombStatMgmtInfoOutVO  = reShareDataSvc.selectMyShareDataList(reShareDataReqDto);

        model.addAttribute("moscCombStatMgmtInfoOutVO", moscCombStatMgmtInfoOutVO);
        model.addAttribute("outWireDtoInfo", moscCombStatMgmtInfoOutVO.getOutWireDtoVo());
        model.addAttribute("svcCntrNo", svcCntrNo);
        model.addAttribute("retvGubunCd",retvGubunCd);
        return returnUrl;
    }

    /**
     * 함께쓰기 신청 view
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param session
     * @param searchVO
     * @param ncn
     * @param cntrMobileNo
     * @param subLinkName
     * @param retvGubunCd
     * @return
     * @throws ParseException
     */

    @RequestMapping(value = { "/content/reqShareDataView.do", "/m/content/reqShareDataView.do" })
    public String doReqShareDataView(HttpServletRequest request, Model model,
            HttpSession session
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO
            ,@RequestParam(value = "ncn", required = false) String ncn
            ,@RequestParam(value = "cntrMobileNo", defaultValue="", required = false) String cntrMobileNo
            ,@RequestParam(value = "subLinkName", defaultValue="", required = false) String subLinkName
            ,@RequestParam(value = "retvGubunCd", required = false) String retvGubunCd) throws ParseException {

        String returnUrl = "/portal/content/reqShareDataView";
        String rtnUrl = "/content/myShareDataView.do";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/content/reqShareDataView";
            rtnUrl = "/m/content/myShareDataView.do";
        }

        if(!StringUtil.isNotNull(retvGubunCd)) {
            return "redirect:"+rtnUrl;
        }

        ReShareDataResDto reShareDataResDto = new ReShareDataResDto();
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession("shareData");
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

         if(userSessionDto == null && authSmsDto  == null) {
            return "redirect:"+rtnUrl;
         }

        // ============ STEP START ============
        if(StringUtil.isEmpty(ncn) || StringUtil.isEmpty(cntrMobileNo) || StringUtil.isEmpty(subLinkName)){
            throw new McpCommonException(BIDING_EXCEPTION, rtnUrl);
        }

        // 결합대상 구분값, 이름, 핸드폰번호
        String[] certKey = {"urlType", "ncType", "name",  "mobileNo"};
        String[] certValue = {"chkSubUserInfo", "1", subLinkName, cntrMobileNo};
        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonException(vldReslt.get("RESULT_DESC"), rtnUrl);
        }
        // ============ STEP END ============

        if(authSmsDto != null) {

            McpUserCntrMngDto cntrMng = new McpUserCntrMngDto();
            cntrMng.setSvcCntrNo(searchVO.getNcn());
            McpUserCntrMngDto cntrList =  mypageService.selectCntrListNoLogin(cntrMng);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            // ============ STEP START ============
            // 결합대상 구분값, 계약번호
            certKey = new String[]{"urlType", "ncType", "contractNum"};
            certValue = new String[]{"chkMainUserInfo", "0", cntrList.getContractNum()};
            vldReslt = certService.vdlCertInfo("D", certKey, certValue);
            if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), rtnUrl);
            }
            // ============ STEP END ============

            searchVO.setCustId(cntrList.getCustId());
            searchVO.setContractNum(cntrList.getContractNum());
            searchVO.setNcn(cntrList.getSvcCntrNo());
            searchVO.setCtn(cntrList.getCntrMobileNo());
            searchVO.setSubStatus(cntrList.getSubStatus());
            model.addAttribute("searchVO", searchVO);

        }else {

            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());

            if( ! this.checkUserType(searchVO, cntrList, userSessionDto) ){
                ResponseSuccessDto responseSuccessDto = getMessageBox();
                model.addAttribute("responseSuccessDto", responseSuccessDto);
                return "/common/successRedirect";
            }

            // ============ STEP START ============
            if(StringUtil.isEmpty(searchVO.getContractNum())){
                throw new McpCommonException(F_BIND_EXCEPTION, rtnUrl);
            }

            // 결합대상 구분값, 계약번호
            certKey = new String[]{"urlType", "ncType", "contractNum"};
            certValue = new String[]{"chkMainUserInfo", "0", searchVO.getContractNum()};
            certService.vdlCertInfo("C", certKey, certValue);
            // ============ STEP END ============
        }

        reShareDataResDto.setContractNum(searchVO.getContractNum());
        reShareDataResDto.setMySvcNo(StringMakerUtil.getPhoneNum(searchVO.getCtn()));

        McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());
        reShareDataResDto.setMySocNm(mcpUserCntrMngDto.getRateNm());
        reShareDataResDto.setReqSvcNo(cntrMobileNo);
        reShareDataResDto.setMskReqSvcNo(StringMakerUtil.getPhoneNum(cntrMobileNo));
        reShareDataResDto.setReqCustName(StringMakerUtil.getName(subLinkName));
        reShareDataResDto.setNcn(searchVO.getNcn());

        model.addAttribute("reShareDataResDto", reShareDataResDto);
        model.addAttribute("retvGubunCd", retvGubunCd);
        return returnUrl;

    }

    /**
     * 함께쓰기 검증
     * keyIN 정보 데이터 검증
     * 함께쓰기 결합가능여부조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param cntrMng
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = { "/content/preShareDataCheckAjax.do", "/m/content/preShareDataCheckAjax.do" })
    @ResponseBody
    public HashMap<String, Object> preShareDataCheckAjax(HttpServletRequest request, Model model,
            @ModelAttribute McpUserCntrMngDto cntrMng)throws ParseException {

         HashMap<String, Object> rtnMap = new HashMap<String, Object>();

         String resultCode = "00";

        //1. key in 정보 회선등록 자사 회선 검증
        McpUserCntrMngDto rtnCntrMng = mypageService.selectCntrListNoLogin(cntrMng);
        if (rtnCntrMng == null) {
            rtnMap.put("resultCode", "05");
            return rtnMap;
        }

       //1-1.정지회선 체크
        if("S".equals(rtnCntrMng.getSubStatus())) {
            rtnMap.put("resultCode", "06");
            return rtnMap;
        }

        //2.대상요금제 대상 여부 요금제
        // 받기회선 현재 요금제 조회 svcCntrNo
        McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(rtnCntrMng.getSvcCntrNo());

        // selectbox 선택된 회선이 주기회선인지 받기 회선인지 체크
        String retvGubunCd = StringUtil.NVL(cntrMng.getRetvGubunCd(),""); //임시 나중에 G 제거
        String shareChildNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(SHARE_RATE_CHILD_LIST,mcpFarPriceDto.getPrvRateCd()),"") ;
        String shareParentNm = NmcpServiceUtils.getCodeNm(SHARE_RATE_PARENT_LIST,mcpFarPriceDto.getPrvRateCd()) ;

        // selectBox 에 선택된 회선이 주기 회선
        if( "G".equals(retvGubunCd) ){
            // keyin은 받기회선 체크
            if("".equals(shareChildNm)){
                resultCode = "01";
                rtnMap.put("resultCode", resultCode);
                return rtnMap;
            }

            resultCode = getMoscCombStatMgmtInfo(rtnCntrMng.getSvcCntrNo(),rtnCntrMng.getCntrMobileNo(),rtnCntrMng.getCustId(),"R"); // 키인정보로 조회
        } else if( "R".equals(retvGubunCd) ){

            // keyin은 주기회선 체크
            if("".equals(shareParentNm)){
                resultCode = "01";
                rtnMap.put("resultCode", resultCode);
                return rtnMap;
            }

            resultCode = getMoscCombStatMgmtInfo(rtnCntrMng.getSvcCntrNo(),rtnCntrMng.getCntrMobileNo(),rtnCntrMng.getCustId(),"G"); // 키인정보로 조회
        } else {
            resultCode = "07";
        }

        // ============ STEP START ============
        if("00".equals(resultCode)){
            if(StringUtils.isEmpty(cntrMng.getSubLinkName())) {
                rtnMap.put("resultCode", "07");
                return rtnMap;
            }

            // 결합대상 구분값, 이름, 핸드폰번호
            String[] certKey = new String[]{"urlType", "ncType", "name", "mobileNo"};
            String[] certValue = new String[]{"chkPreShareData", "1", cntrMng.getSubLinkName(), cntrMng.getCntrMobileNo()};
            certService.vdlCertInfo("C", certKey, certValue);
        }
        // ============ STEP END ============

        rtnMap.put("resultCode",resultCode);
        return rtnMap;
    }

    /**
     * OTP인증서비스
     * 인증 발송 대상은 :  keyin svcno / 부가파람은 selectBox 기준으로 PL208J939 또는 PL208J938
     * @author bsj
     * @Date : 2021.12.30
     * @param searchVO
     * @param svcNo
     * @param retvGubunCd
     * @param session
     * @return
     */

   @RequestMapping(value = { "/content/callOtpSvcAjax.do", "/m/content/callOtpSvcAjax.do" })
   @ResponseBody
   public Map<String, Object> callOtpSvcAjax(@ModelAttribute("searchVO") MyPageSearchDto searchVO,
           @RequestParam(value = "svcNo", required=true) String svcNo,
           @RequestParam(value = "retvGubunCd", required=true) String retvGubunCd
          , HttpSession session )   {

       HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String userDivisionYn =  (String) session.getAttribute("userDivisionYn");

        AuthSmsDto authSmsDto = SessionUtils.getSmsSession("shareData");
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null && authSmsDto  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

           if(authSmsDto != null) {
               McpUserCntrMngDto cntrMng = new McpUserCntrMngDto();
                cntrMng.setSvcCntrNo(searchVO.getNcn());
                McpUserCntrMngDto cntrList =  mypageService.selectCntrListNoLogin(cntrMng);

                if(cntrList == null) {
                    throw new McpCommonException(F_BIND_EXCEPTION);
                }

                searchVO.setCustId(cntrList.getCustId());
                searchVO.setContractNum(cntrList.getContractNum());
                searchVO.setNcn(cntrList.getSvcCntrNo());
                searchVO.setCtn(cntrList.getCntrMobileNo());
                searchVO.setSubStatus(cntrList.getSubStatus());

           }else {

               List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());

               if(!this.checkUserType(searchVO, cntrList, userSessionDto) ){
                  throw new McpCommonJsonException("0002" , INVALID_REFERER_EXCEPTION);
               }
           }

       // ============ STEP START ============
       // 결합대상 구분값, 핸드폰번호
       String[] certKey = {"urlType", "ncType", "mobileNo"};
       String[] certValue = {"reqSubSmsOtpAuthNum", "1", svcNo};
       Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
       if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
           throw new McpCommonJsonException("STEP01" , vldReslt.get("RESULT_DESC"));
       }

       // 결합대상 구분값, 계약번호
       certKey = new String[]{"urlType", "ncType", "contractNum"};
       certValue = new String[]{"reqMainSmsOtpAuthNum", "0", searchVO.getContractNum()};
       vldReslt = certService.vdlCertInfo("D", certKey, certValue);
       if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
           throw new McpCommonJsonException("STEP01" , vldReslt.get("RESULT_DESC"));
       }
       // ============ STEP END ============

       MoscOtpSvcInfoRes otpSvc = null;
       otpSvc = reShareDataSvc.moscOtpSvcInfo(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(),svcNo, retvGubunCd) ;

       /*
       업무구분코드: 01
       (00 : 정상처리
        01 : 서비스 계약조건 처리 불가
       02 : 요금제조건 처리 불가
       03 : 기 결합 중이거나 결합 제약 기간 내 결합이력 존재하여 처리 불가)"
        */
       String resltCd="00";
       // String resltCd = otpSvc.getResltCd();
       String resltMsg =  otpSvc.getResltMsgSbst();

       if ("00".equals(resltCd)) {
           rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

           //인증번호 session저장 처리
           SessionUtils.saveOtpInfo(otpSvc.getOtpNo());
       } else {
           rtnMap.put("RESULT_CODE", resltCd);
       }

       rtnMap.put("RESULT_MSG", resltMsg);
       return rtnMap;
    }

   /**
    * 함께쓰기 신청
    * OTP 번호 확인 및 부가 서비스 가입 처리
    * 부가서비스 가입은 상단 selectBox 기준으로 가입
    * selectBox 주기 회선이면 PL208J938 / 받기 회선이면 PL208J939
    * selectBox 기준으로 svcno
    * @author bsj
    * @Date : 2021.12.30
    * @param request
    * @param model
    * @param session
    * @param searchVO
    * @param cntrMng
    * @param otpNo
    * @return
    */

    @RequestMapping(value = { "/content/insertShareDataRequestAjax.do", "/m/content/insertShareDataRequestAjax.do" })
    @ResponseBody
    public HashMap<String, Object> combinationCheckAjax(HttpServletRequest request, Model model,HttpSession session,
        @ModelAttribute("searchVO") MyPageSearchDto searchVO, @ModelAttribute McpUserCntrMngDto cntrMng,
        @RequestParam(value = "otpNo", required=true) String otpNo )   {


         HashMap<String, Object> rtnMap = new HashMap<String, Object>();
         //1.OTP번호 검증
         String sessionOtp = SessionUtils.getOtpInfo();
         if (sessionOtp == null  || "".equals(sessionOtp)) {
               rtnMap.put("RESULT_CODE", "07");
               rtnMap.put("RESULT_MSG", "인증번호 요청 하시기 바랍니다. ");
               return rtnMap;
          }

         if (!otpNo.equals(sessionOtp)) {
               rtnMap.put("RESULT_CODE", "01");
               rtnMap.put("RESULT_MSG", "인증번호가 상이 합니다. ");
               return rtnMap;
         }

         AuthSmsDto authSmsDto = SessionUtils.getSmsSession("shareData");
         String userDivisionYn =  (String) session.getAttribute("userDivisionYn");

         UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

         if(userSessionDto == null && authSmsDto  == null) {
             throw new McpCommonException(F_BIND_EXCEPTION);
         }

         if(userDivisionYn != null) {
            McpUserCntrMngDto tmpCntrMng= new McpUserCntrMngDto();
            tmpCntrMng.setSvcCntrNo(searchVO.getNcn());
            McpUserCntrMngDto cntrList =  mypageService.selectCntrListNoLogin(tmpCntrMng);
            if(cntrList == null ){
                rtnMap.put("RESULT_CODE", "02");
                  rtnMap.put("RESULT_MSG", "자사 회선이 아닙니다.");
                  return rtnMap;
               }else {
                   searchVO.setCustId(cntrList.getCustId());
                   searchVO.setContractNum(cntrList.getContractNum());
                   searchVO.setNcn(cntrList.getSvcCntrNo());
                   searchVO.setCtn(cntrList.getCntrMobileNo());
                   searchVO.setSubStatus(cntrList.getSubStatus());
               }

           }else {

               if (userSessionDto == null) {
                   rtnMap.put("RESULT_CODE", "03");
                   rtnMap.put("RESULT_MSG", "로그인 정보가 없습니다. ");
                   return rtnMap;
               }

               List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSessionDto.getUserId());

               if(!this.checkUserType(searchVO, cntrList, userSessionDto) ){
                   rtnMap.put("RESULT_CODE", "02");
                  rtnMap.put("RESULT_MSG", "자사 회선이 아닙니다.");
                  return rtnMap;
               }
           }

         //2.keyIn 회선정보 확인
         McpUserCntrMngDto rtnCntrMng = mypageService.selectCntrListNoLogin(cntrMng);
         if (rtnCntrMng == null) {
             rtnMap.put("RESULT_CODE", "02");
             rtnMap.put("RESULT_MSG", "자사 회선이 아닙니다.");
             return rtnMap;
         }

        // ============ STEP START ============
        // 결합대상 구분값, 이름, 핸드폰번호
        String[] certKey = {"urlType", "ncType", "name", "mobileNo"};
        String[] certValue = {"chkSubSmsOtpAuthNum", "1", rtnCntrMng.getUserName(), cntrMng.getCntrMobileNo()};
        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP01" , vldReslt.get("RESULT_DESC"));
        }

        if(userDivisionYn != null){
            // 결합대상 구분값, 계약번호, 핸드폰번호
            certKey = new String[]{"urlType", "ncType", "contractNum", "mobileNo"};
            certValue = new String[]{"chkMainSmsOtpAuthNum", "0", searchVO.getContractNum(), searchVO.getCtn()};

        }else{
            // 결합대상 구분값, 계약번호
            certKey = new String[]{"urlType", "ncType", "contractNum"};
            certValue = new String[]{"chkMainSmsOtpAuthNum", "0", searchVO.getContractNum()};
        }

        vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP02" , vldReslt.get("RESULT_DESC"));
        }
        // ============ STEP END ============

         RegSvcChgRes regSvcChgSelfVO= new RegSvcChgRes();
         regSvcChgSelfVO = reShareDataSvc.regSvcChgNe(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), cntrMng.getRetvGubunCd(), otpNo);
           //결과 로그 저장 처리
         McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
         mcpIpStatisticDto.setPrcsMdlInd("REQUEST_SHAER_DATA");
         mcpIpStatisticDto.setParameter("NCN[" +searchVO.getNcn() +"]CTN["+ searchVO.getCtn()+"]SOC[PL208J939]GLOBAL_NO["+ regSvcChgSelfVO.getGlobalNo()+"]RESLT_CD["+ regSvcChgSelfVO.getResultCode()+"]");
         mcpIpStatisticDto.setPrcsSbst(searchVO.getNcn()+"");

         if(regSvcChgSelfVO.isSuccess()) {
               mcpIpStatisticDto.setTrtmRsltSmst("REG_SVC");
               ipstatisticService.insertAdminAccessTrace (mcpIpStatisticDto);
             //AS-IS ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
           } else {
               mcpIpStatisticDto.setTrtmRsltSmst("REG_SVC_FAIL");
               //AS-IS ipstatisticService.insertAccessTrace(mcpIpStatisticDto);
               ipstatisticService.insertAdminAccessTrace (mcpIpStatisticDto);
               rtnMap.put("RESULT_CODE", "03");
               rtnMap.put("RESULT_MSG", regSvcChgSelfVO.getSvcMsg());
               return rtnMap;
           }

         rtnMap.put("RESULT_CODE", "00000");
         rtnMap.put("RESULT_MSG", "부가서비스 가입 완료");
         session.setAttribute("otpNo", otpNo);
         return rtnMap;
    }

    /**
     * 함께쓰기 완료 페이지 view
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param reqCustName
     * @param reqSvcNo
     * @param searchVO
     * @param cntrMng
     * @param session
     * @return
     */

    @RequestMapping(value = { "/content/reShareDataComplete.do", "/m/content/reShareDataComplete.do" })
    public String doReShareDataComplete(HttpServletRequest request, Model model,
            @RequestParam(value = "reqCustName", required = false) String reqCustName
            ,@RequestParam(value = "reqSvcNo", required = false) String reqSvcNo
           , @ModelAttribute("searchVO") MyPageSearchDto searchVO
           , @ModelAttribute McpUserCntrMngDto cntrMng
           , HttpSession session
          ){

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        String returnUrl = "/portal/content/reShareDataComplete";
        String thisPageName ="/content/reqShareDataView.do";
        String redirectUrl= "/main.do";

        String reqSvcNo_Ne =  StringUtil.isNotNull(reqSvcNo) ? reqSvcNo : ""; //취약성 458

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/content/reShareDataComplete";
            thisPageName ="/m/content/reqShareDataView.do";
            redirectUrl= "/m/main.do";
        }

        String otpNo = (String) session.getAttribute("otpNo");

        if(otpNo == null) {
            return "redirect:"+thisPageName;
        }


        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();  //중복요청 체크
        checkOverlapDto.setRedirectUrl(thisPageName);
        checkOverlapDto.setSuccessMsg(TIME_OVERLAP_EXCEPTION);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        ReShareDataResDto reShareDataResDto = new ReShareDataResDto();
        String reqContractNum = "";
        String userDivisionYn =  (String) session.getAttribute("userDivisionYn");
        AuthSmsDto authSmsDto = SessionUtils.getSmsSession("shareData");

        //비회원로그인
        if(userDivisionYn != null) {
            McpUserCntrMngDto cntrList = null;
            McpUserCntrMngDto resultDto = new McpUserCntrMngDto();
            resultDto.setSvcCntrNo(searchVO.getNcn());
             cntrList =  mypageService.selectCntrListNoLogin(resultDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION, redirectUrl);
            }

            searchVO.setCustId(cntrList.getCustId());
            searchVO.setContractNum(cntrList.getContractNum());
            searchVO.setNcn(cntrList.getSvcCntrNo());
            searchVO.setCtn(cntrList.getCntrMobileNo());
            searchVO.setSubStatus(cntrList.getSubStatus());
            model.addAttribute("searchVO", searchVO);

            // 당연히 같음
            if (searchVO.getNcn().equals(cntrList.getSvcCntrNo())) {
                reShareDataResDto.setMySvcNo(StringMakerUtil.getPhoneNum(cntrList.getCntrMobileNo()));
                //reqContractNum = cntrList.getContractNum();
            }

        } else {
            // 본인이 가지고 있는 회선정보
            List<McpUserCntrMngDto> cntrList = null;
            if (userSession != null) { // 취약성 275
                cntrList  = mypageService.selectCntrList(userSession.getUserId());
            }
            if (!this.checkUserType(searchVO, cntrList, userSession)) {
                ResponseSuccessDto responseSuccessDto = getMessageBox();
                model.addAttribute("responseSuccessDto", responseSuccessDto);
                return "/common/successRedirect";
            }

            if (cntrList != null && cntrList.size() > 0) {
                for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                    if (searchVO.getNcn().equals(mcpUserCntrMngDto.getSvcCntrNo())) {
                        reShareDataResDto.setMySvcNo(StringMakerUtil.getPhoneNum(mcpUserCntrMngDto.getCntrMobileNo()));
                        //reqContractNum = mcpUserCntrMngDto.getContractNum();
                        break;
                    }
                }
            }
        }

        // ============ STEP START ============
        // 결합대상 구분값, 핸드폰번호
        String[] certKey = {"urlType", "ncType", "mobileNo"};
        String[] certValue = {"completeSubDataShare", "1", reqSvcNo};
        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonException(vldReslt.get("RESULT_DESC"), redirectUrl);
        }

        // 최소 스텝 수 체크
        int certStep= 10;
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) certStep = 13;

        if(certService.getStepCnt() < certStep ){
            throw new McpCommonException(STEP_CNT_EXCEPTION, redirectUrl);
        }

        // 결합대상 구분값, 스텝종료여부, 계약번호
        certKey = new String[]{"urlType", "stepEndYn", "ncType", "contractNum"};
        certValue = new String[]{"completeMainDataShare", "Y", "0", searchVO.getContractNum()};
        vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonException(vldReslt.get("RESULT_DESC"), redirectUrl);
        }
        // ============ STEP END ============

        //현재 요금제 조회
        McpUserCntrMngDto mcpUserCntrMng = mypageService.selectSocDesc(searchVO.getContractNum());

        McpUserCntrMngDto resultDto = new McpUserCntrMngDto();
        resultDto.setCntrMobileNo(reqSvcNo_Ne);

        //1.자사 회선 검증(모회선)
        McpUserCntrMngDto rtnCntrMng = mypageService.selectCntrListNoLogin(resultDto);

        if (rtnCntrMng == null) {
            throw new McpCommonException(INVALID_REFERER_EXCEPTION, redirectUrl);
        }

        //McpUserCntrMngDto reqDto = mypageService.selectSocDesc(reqContractNum);
        McpUserCntrMngDto reqDto = mypageService.selectSocDesc(rtnCntrMng.getContractNum());

        reShareDataResDto.setMySocNm(mcpUserCntrMng.getRateNm());
        reShareDataResDto.setReqSvcNo(StringMakerUtil.getPhoneNum(reqSvcNo_Ne));
        reShareDataResDto.setReqCustName(StringMakerUtil.getName(reqCustName));
        reShareDataResDto.setReqSocNm(reqDto.getRateNm());

        model.addAttribute("reShareDataResDto", reShareDataResDto);
        session.removeAttribute("userDivisionYn");
        session.removeAttribute("otpNo");
        session.removeAttribute("retvGubunCd");
        SessionUtils.saveNiceRes(null);
        return returnUrl;
    }

    private boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList,
            UserSessionDto userSession) {

        if (!StringUtil.equals(userSession.getUserDivision(), "01")) {
            return false;
        }

        if(cntrList == null) {
            return false;
        }

        if (cntrList.size() <= 0) {
            return false;
        }

        if (StringUtil.isEmpty(searchVO.getNcn())) {
            searchVO.setNcn(cntrList.get(0).getSvcCntrNo());
            searchVO.setCtn(cntrList.get(0).getCntrMobileNo());
            searchVO.setCustId(cntrList.get(0).getCustId());
            searchVO.setModelName(cntrList.get(0).getModelName());
            searchVO.setContractNum(cntrList.get(0).getContractNum());
            searchVO.setSubStatus(cntrList.get(0).getSubStatus());

        }

        for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
            String ctn = mcpUserCntrMngDto.getCntrMobileNo();
            String ncn = mcpUserCntrMngDto.getSvcCntrNo();
            String custId = mcpUserCntrMngDto.getCustId();
            String modelName = mcpUserCntrMngDto.getModelName();
            String contractNum = mcpUserCntrMngDto.getContractNum();
            String subStatus = mcpUserCntrMngDto.getSubStatus();

            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            mcpUserCntrMngDto.setSvcCntrNo(ncn);
            mcpUserCntrMngDto.setCustId(custId);
            mcpUserCntrMngDto.setModelName(modelName);
            mcpUserCntrMngDto.setContractNum(contractNum);
            if (StringUtil.equals(searchVO.getNcn(), String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))) {
                searchVO.setNcn(ncn);
                searchVO.setCtn(ctn);
                searchVO.setCustId(custId);
                searchVO.setModelName(modelName);
                searchVO.setContractNum(contractNum);
                searchVO.setSubStatus(subStatus);
            }
        }
        return true;
    }


    private String getMoscCombStatMgmtInfo(String svcCntrNo, String ctn, String custId, String retvGubunCd){

           String resultCode = "00";

           try{

               ReShareDataReqDto reShareDataReqDto = new ReShareDataReqDto();
               reShareDataReqDto.setNcn(svcCntrNo);
               reShareDataReqDto.setCtn(ctn);
               reShareDataReqDto.setCustId(custId);
               reShareDataReqDto.setRetvGubunCd(retvGubunCd);

               MoscCombStatMgmtInfoOutVO resDto = reShareDataSvc.selectMyShareDataList(reShareDataReqDto); // ncn, ctn, custid ==> 1건결합

               MoscCombStatMgmtInfoOutVO.OutWireDto outWireDtoVo = new MoscCombStatMgmtInfoOutVO.OutWireDto(); // 공통항목
               List<MoscCombStatMgmtInfoOutVO.OutGiveListDto>  outGiveDtoList = new ArrayList<MoscCombStatMgmtInfoOutVO.OutGiveListDto>(); // 주기회선으로 조회시
               List<MoscCombStatMgmtInfoOutVO.OutRcvListDto>  outRcvDtoList = new ArrayList<MoscCombStatMgmtInfoOutVO.OutRcvListDto>(); // 받기회선으로 조회시

               if(resDto !=null){

                   if("G".equals(retvGubunCd)){ // 주기회선인경우

                       outWireDtoVo = resDto.getOutWireDtoVo(); //  공통항목
                       outGiveDtoList = resDto.getOutGiveDtoList();

                       String sbscBindRemdCnt = "";
                       int intSbscBindRemdCnt = 0;
                       if(outWireDtoVo !=null){
                           sbscBindRemdCnt = StringUtil.NVL(outWireDtoVo.getSbscBindRemdCnt(),"0");
                           intSbscBindRemdCnt = Integer.parseInt(sbscBindRemdCnt); // 가입가능한 회선수
                       }

                       if( intSbscBindRemdCnt <=0 ){ // 1. 가입가능한 회선수가 있는지 체크
                           resultCode = "02";

                       } else { // 2. 해지이력 3개월 이전 회선 있는지 확인

                           if(outGiveDtoList !=null && !outGiveDtoList.isEmpty()){
                               String bindStatus = "";
                               String efctFnsDt = "";
                               for(int i=0; i<outGiveDtoList.size(); i++){
                                   int day = 0;
                                   bindStatus = StringUtil.NVL(outGiveDtoList.get(i).getBindStatus(),"");
                                   efctFnsDt = StringUtil.NVL(outGiveDtoList.get(i).getEfctFnsDt(),"");
                                   if("미결합".equals(bindStatus)){
                                       efctFnsDt = efctFnsDt.replace("-","");
                                       day = DateTimeUtil.daysBetween(efctFnsDt, DateTimeUtil.getShortDateString());
                                       if(day < 90){
                                           resultCode = "03";
                                           break;
                                       }
                                   }
                               }
                           }
                       }

                   } else if("R".equals(retvGubunCd)){ // 받기회선인경우

                       outRcvDtoList = resDto.getOutRcvDtoList();
                       if(outRcvDtoList !=null && !outRcvDtoList.isEmpty()){

                           String bindStatus = "";
                           String efctFnsDt = "";
                           for(int i=0; i<outRcvDtoList.size(); i++){
                               int day = 0;
                               bindStatus = StringUtil.NVL(outRcvDtoList.get(i).getBindStatus(),"");
                               efctFnsDt = StringUtil.NVL(outRcvDtoList.get(i).getEfctFnsDt(),"");
                               if("미결합".equals(bindStatus)){
                                   efctFnsDt = efctFnsDt.replace("-","");
                                   day = DateTimeUtil.daysBetween(efctFnsDt, DateTimeUtil.getShortDateString());
                                   if(day < 90){
                                       resultCode = "03";
                                       break;
                                   }
                               }
                           }
                       }
                   }
               }

           } catch(DataAccessException e) {
               logger.debug("DataAccessException ERROR");
               resultCode = "01";
           } catch(Exception e){
               logger.debug("getMoscCombStatMgmtInfo ERROR");
               resultCode = "01";
           }


           return resultCode;
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