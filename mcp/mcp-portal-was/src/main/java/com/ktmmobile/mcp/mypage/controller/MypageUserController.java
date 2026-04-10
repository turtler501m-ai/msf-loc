package com.ktmmobile.mcp.mypage.controller;

import com.ktmmobile.mcp.app.service.AppSvc;
import com.ktmmobile.mcp.appform.dto.JuoSubInfoDto;
import com.ktmmobile.mcp.cert.dto.CertDto;
import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.*;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.vo.UserSearchVO;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.event.dto.FrndRecommendDto;
import com.ktmmobile.mcp.event.dto.UserPromotionDto;
import com.ktmmobile.mcp.event.service.CoEventSvc;
import com.ktmmobile.mcp.event.service.UserPromotionSvc;
import com.ktmmobile.mcp.join.dto.JoinDto;
import com.ktmmobile.mcp.join.dto.UserAgentDto;
import com.ktmmobile.mcp.join.service.JoinSvc;
import com.ktmmobile.mcp.mcash.service.McashService;
import com.ktmmobile.mcp.mypage.dto.CommendStateDto;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;
import com.ktmmobile.mcp.point.service.MstoreService;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;
import com.ktmmobile.mcp.requestReview.service.RequestReviewService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ktmmobile.mcp.common.constants.Constants.*;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class MypageUserController {
    private static Logger logger = LoggerFactory.getLogger(MypageUserController.class);

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    private RequestReviewService requestReviewService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private MstoreService mstoreService;

    @Autowired
    private McashService mcashService;

    @Autowired
    private CertService certService;

    @Autowired
    private UserPromotionSvc userPromotionSvc;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    CoEventSvc coEventSvc;

    private final static String PW_CHECK_YN = "PW_CHECK_YN";

    private final static String RECOMMEND_SELECT_HIS = "RECOMMEND_SELECT_HIS";

    @Autowired
    JoinSvc joinSvc;

    @Autowired
    private AppSvc appSvc;

    /**
     *
     * 설명 : 마이페이지 > 친구초대 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/mypage/reCommendState.do", "/m/mypage/reCommendState.do"}  )
    public String reCommendState(ModelMap model, HttpServletRequest request
        , @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 347
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }

        if(Optional.ofNullable(cntrList).isPresent()) {
            for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
                String ctn = mcpUserCntrMngDto.getCntrMobileNo();
                String ncn = mcpUserCntrMngDto.getSvcCntrNo();
                mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
                mcpUserCntrMngDto.setSvcCntrNo(ncn);
            }
        }

        if (Optional.ofNullable(cntrList).isEmpty() || cntrList.size() < 1 || userSession.getUserDivision().equals("00")) {
            checkOverlapDto.setRedirectUrl(request.getHeader("Referer"));
            checkOverlapDto.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
            model.addAttribute("responseSuccessDto", checkOverlapDto);

            return "/common/successRedirect";
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

        model.addAttribute("cntrList", cntrList);
        model.addAttribute("mobileNo", userSession.getMobileNo());
        model.addAttribute("searchVO", searchVO);

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return"mobile/mypage/reCommendState.view";
        } else {
            return "portal/mypage/reCommendState.view";
        }
    }

    /**
     *
     * 설명 : 마이페이지 > 친구초대 화면 로딩
     * @Author papier
     * @Date 2026.01.09
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/mypage/reCommendMng.do"  )
    public String reCommendLink(HttpServletRequest request,ModelMap model
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 347
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }  else {
            throw new McpCommonException(ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION);
        }

        if(Optional.ofNullable(cntrList).isPresent()) {
            for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
                String ctn = mcpUserCntrMngDto.getCntrMobileNo();
                String ncn = mcpUserCntrMngDto.getSvcCntrNo();
                mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
                mcpUserCntrMngDto.setSvcCntrNo(ncn);
            }
        }

        if (Optional.ofNullable(cntrList).isEmpty() || cntrList.size() < 1 || userSession.getUserDivision().equals("00")) {
            ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
            checkOverlapDto.setRedirectUrl(request.getHeader("Referer"));
            checkOverlapDto.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            return "/common/successRedirect";
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

        model.addAttribute("cntrList", cntrList);
        model.addAttribute("mobileNo", userSession.getMobileNo());
        model.addAttribute("searchVO", searchVO);

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return"mobile/mypage/reCommendMng.view";
        } else {
            return "portal/mypage/reCommendMng.view";
        }
    }

    /**
     * 설명 : 마이페이 친구초대 정보
     * @Author papier
     */
    @RequestMapping(value = "/mypage/getFrndRecommendAjax.do")
    @ResponseBody
    public Map<String, Object> getFrndRecommendAjax(@ModelAttribute("searchVO") MyPageSearchDto searchVO ){
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 347
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }  else {
            throw new McpCommonJsonException(ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION);
        }

        if(Optional.ofNullable(cntrList).isPresent()) {
            for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
                String ctn = mcpUserCntrMngDto.getCntrMobileNo();
                String ncn = mcpUserCntrMngDto.getSvcCntrNo();
                mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
                mcpUserCntrMngDto.setSvcCntrNo(ncn);
            }
        }

        if (Optional.ofNullable(cntrList).isEmpty() || cntrList.size() < 1 || userSession.getUserDivision().equals("00")) {
            throw new McpCommonJsonException(ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION);
        }

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException(ExceptionMsgConstant.OWN_EXCEPTION);
        }

        String contractNum = searchVO.getContractNum();
        boolean prePaymentFlag = mypageUserService.selectPrePayment(contractNum);

        if(prePaymentFlag){
            rtnMap.put("RESULT_CODE", "00004");
            return rtnMap;
        }

        FrndRecommendDto frndRecommendDto = new FrndRecommendDto();
        frndRecommendDto.setContractNum(contractNum);
        FrndRecommendDto resDto = coEventSvc.selectFrndId(frndRecommendDto);

        if (Optional.ofNullable(resDto).isEmpty() || org.apache.commons.lang3.StringUtils.isEmpty(resDto.getCommendId())) {
            rtnMap.put("COMMEND_OBJ", null);
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("RESULT_MSG", "친구초대 정보 없음");
        } else {
            resDto.setContractNum("");

            if("LOCAL".equals(serverName)) {
                rtnMap.put("LINK_URL", "https://local.portal.ktmmobile.com"+resDto.getEventLink());
            } else if("DEV".equals(serverName)) {
                rtnMap.put("LINK_URL", "https://dmcpdev.ktmmobile.com:8012"+resDto.getEventLink());
            } else if("STG".equals(serverName)) {
                rtnMap.put("LINK_URL", "https://dmcpstg.ktmmobile.com"+resDto.getEventLink());
            } else {
                rtnMap.put("LINK_URL", "https://www.ktmmobile.com"+resDto.getEventLink());
            }
            
            //고객명 추출
            McpUserCntrMngDto cntrMngObj = cntrList.stream()
                    .filter(dto -> dto.getSvcCntrNo().equals(searchVO.getNcn()))
                    .findFirst()
                    .orElse(null);

            if (cntrMngObj != null) {
                rtnMap.put("COMMEND_NM",MaskingUtil.getMaskedName(cntrMngObj.getUserName()));
            }

            rtnMap.put("COMMEND_OBJ", resDto);

            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        }
        return rtnMap;
    }



    /**
     * 설명 : 마이페이 친구초대 정보 변경
     * @Author papier
     */
    @RequestMapping(value = "/mypage/frndRecommendPrdAjax.do")
    @ResponseBody
    public Map<String, Object> frndRecommendPrd(@ModelAttribute("searchVO") MyPageSearchDto searchVO
                                     , FrndRecommendDto frndRecommendPra
                                     , @RequestParam(value = "mstoreTermAgree", required = false) String mstoreTermAgree){

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        // 20230828 신규추가
        // M스토어 이용약관 동의 필수 (M스토어 포인트 지급 목적)
        if(!"Y".equalsIgnoreCase(mstoreTermAgree)){
            throw new McpCommonJsonException("00009" ,"개인정보 제 3자 제공에 동의해주세요.");
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 347
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }  else {
            throw new McpCommonJsonException(ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION);
        }

        if(Optional.ofNullable(cntrList).isPresent()) {
            for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
                String ctn = mcpUserCntrMngDto.getCntrMobileNo();
                String ncn = mcpUserCntrMngDto.getSvcCntrNo();
                mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
                mcpUserCntrMngDto.setSvcCntrNo(ncn);
            }
        }

        if (Optional.ofNullable(cntrList).isEmpty() || cntrList.size() < 1 || userSession.getUserDivision().equals("00")) {
            throw new McpCommonJsonException(ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION);
        }

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonJsonException(ExceptionMsgConstant.OWN_EXCEPTION);
        }

        String contractNum = searchVO.getContractNum();
        boolean prePaymentFlag = mypageUserService.selectPrePayment(contractNum);

        if(prePaymentFlag){
            rtnMap.put("RESULT_CODE", "00004");
            return rtnMap;
        }


        FrndRecommendDto frndRecommendDto = new FrndRecommendDto();
        frndRecommendDto.setContractNum(contractNum);
        FrndRecommendDto resDto = coEventSvc.selectFrndIdUpdate(frndRecommendDto,frndRecommendPra);
        String frndId = "";
        if( resDto != null ){ // 기존
            frndId  = resDto.getCommendId();
        } else { // 신규
            frndId = coEventSvc.getRandCommendId();
            if (!org.springframework.util.StringUtils.hasText(frndId)) {
                rtnMap.put("RESULT_CODE", "00005");
                return rtnMap;
            }

            resDto  = new FrndRecommendDto(); // 호환을 위해.. 생성
            resDto.setCommendId(frndId);
            resDto.setContractNum(contractNum);
            resDto.setOpenMthdCd(frndRecommendPra.getOpenMthdCd());
            resDto.setCommendSocCode01(frndRecommendPra.getCommendSocCode01());
            resDto.setCommendSocCode02(frndRecommendPra.getCommendSocCode02());
            resDto.setCommendSocCode03(frndRecommendPra.getCommendSocCode03());
            resDto.setLinkTypeCd(frndRecommendPra.getLinkTypeCd());

            int cnt = coEventSvc.insertFrndId(resDto);
        }

        resDto.setContractNum("");
        if("LOCAL".equals(serverName)) {
            rtnMap.put("LINK_URL", "https://local.portal.ktmmobile.com"+resDto.getEventLink());
        } else if("DEV".equals(serverName)) {
            rtnMap.put("LINK_URL", "https://dmcpdev.ktmmobile.com:8012"+resDto.getEventLink());
        } else if("STG".equals(serverName)) {
            rtnMap.put("LINK_URL", "https://dmcpstg.ktmmobile.com"+resDto.getEventLink());
        } else {
            rtnMap.put("LINK_URL", "https://www.ktmmobile.com"+resDto.getEventLink());
        }

        rtnMap.put("COMMEND_OBJ", resDto);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        return rtnMap;

    }

    /**
     *
     * 설명 : 마이페이지 > 친구초대 현황 조회 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/mypage/getCommendStateAjax.do", "/m/mypage/getCommendStateAjax.do"})
    public Map<String, Object> getCommendStateAjax(@ModelAttribute("searchVO") MyPageSearchDto searchVO) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

       //계약번호 검증
        boolean isCheck = false;
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("0001" ,NO_SESSION_EXCEPTION);
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            rtnMap.put("RESULT_CODE", "0002");
            return rtnMap;
        }

        // 친구추천 아이디 조회
        /*
        * 등록을 계약변호 로... 해서 계약번호로...
        * select id="selectContractNum"
         */
        String commendId =   mypageService.getCommendId(searchVO.getContractNum()) ;

         if (StringUtils.isBlank(commendId)) {
             rtnMap.put("RESULT_CODE", "0004");
              return rtnMap;
        }

        // 친구 추천 현황 조회
        //---- API 호출 S ----//
        List<CommendStateDto> commendStateList = mypageService.getCommendStateList(commendId,searchVO);
        //---- API 호출 E ----//

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("COMMEND_ID", commendId);
        rtnMap.put("RESULT_SIZE", commendStateList.size());
        rtnMap.put("RESULT_DATE", commendStateList);
        return rtnMap;
    }

    /**
     * 설명 : 회원정보 조회 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param userid
     * @param pwChg
     * @param userVO
     * @param searchVO
     * @return
     */
    @RequestMapping(value = {"/m/mypage/updateForm.do", "/mypage/updateForm.do"})
    public String updateFormHandler(HttpServletRequest request, ModelMap model,
            @RequestParam(value = "userid", required = false) String userid,
            @RequestParam(value = "pwChg", required = false) String pwChg,
            @ModelAttribute("userVO") UserVO userVO,
            @ModelAttribute("userSearchVO") UserSearchVO searchVO)  {

            ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
            String returnUrl = "portal/mypage/userForm";
            checkOverlapDto.setRedirectUrl("/mypage/updateForm.do");
            if ("Y".equals(NmcpServiceUtils.isMobile())) {
                returnUrl = "mobile/mypage/userForm";
                checkOverlapDto.setRedirectUrl("/m/mypage/updateForm.do");
            }
            //중복요청 체크
            if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
                model.addAttribute("responseSuccessDto", checkOverlapDto);
                return "/common/successRedirect";
            }
            UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
            if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
                if ("Y".equals(NmcpServiceUtils.isMobile())) {
                    return "redirect:/m/loginForm.do";
                }else {
                    return "redirect:/loginForm.do";
                }
            }

            if(userSession.getStatus().equals("RF")) {
                model.addAttribute("status", "F");
            }else if(userSession.getStatus().equals("RS")) {
                model.addAttribute("status", "S");
            }else {
                model.addAttribute("status", "N");
            }
            userSession.setStatus("");
            Object pWCheckYnObj = request.getSession().getAttribute(PW_CHECK_YN);
            String pwChgtmp = pwChg;
            if (pWCheckYnObj != null) {
                String pWCheckYn = (String)pWCheckYnObj;
                if ("Y".equals(pWCheckYn) && pwChgtmp == null) {
                    pwChgtmp = "3";
                }
            }

            UserVO userVO2 = mypageUserService.selectUser(userSession.getUserId());

            // 마스킹해제
            if(SessionUtils.getMaskingSession() > 0 ) {
                model.addAttribute("maskingSession", "Y");
                MaskingDto maskingDto = new MaskingDto();

                long maskingRelSeq = SessionUtils.getMaskingSession();
                maskingDto.setMaskingReleaseSeq(maskingRelSeq);
                maskingDto.setUnmaskingInfo("이름,아이디,휴대폰번호,이메일");
                maskingDto.setAccessIp(ipstatisticService.getClientIp());
                maskingDto.setAccessUrl(request.getRequestURI());
                maskingDto.setUserId(userSession.getUserId());
                maskingDto.setCretId(userSession.getUserId());
                maskingDto.setAmdId(userSession.getUserId());
                maskingSvc.insertMaskingReleaseHist(maskingDto);

            }

            model.addAttribute("pwChg", pwChgtmp);
            model.addAttribute("codeEML", mypageUserService.selectCodeList("EML"));
            model.addAttribute("codeHPN", mypageUserService.selectCodeList("HPN"));
            model.addAttribute("codeUDV", mypageUserService.selectCodeList("UDV"));
            request.getSession().setAttribute(SessionUtils.USER_SESSION, userSession);
            model.addAttribute("userVO", userVO2);
            model.addAttribute("userMasking", userSession);
            return returnUrl;
    }

    /**
     * 설명 : 회원정보 수정 클릭시, 이동전 비밀번호 확인 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param response
     * @param model
     * @param id
     * @param pw
     * @param status
     * @param pwChange
     * @param pwChg
     * @return
     */
    @RequestMapping(value = {"/mypage/userPwCheckAjax.do", "/m/mypage/userPwCheckAjax.do"})
    @ResponseBody
    public JsonReturnDto userPwCheckAjax(HttpServletRequest request,HttpServletResponse response,
                                ModelMap model,@RequestParam(value = "id", required = false) String id
                                ,@RequestParam(value = "pw", required = false) String pw
                                ,@RequestParam(value = "status", required = false) String status
                                ,@RequestParam(value = "pwChange", required = false) String pwChange
                                ,String pwChg){
        JsonReturnDto result = new JsonReturnDto();
        String returnCode = "99";
        String message = "오류가 발생했습니다. 다시 시도해 주세요.";
        Object json = null;

        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            returnCode = "21";
            message = "로그인해 주세요.";
        } else {

            String userSessionUserId = userSession.getUserId() ;

//            if (!userSessionUserId.equals(id)) {
//                result.setReturnCode("98");
//                result.setMessage("로그인한 정보와 상이 합니다.");
//                return result;
//            }

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", userSessionUserId);
            map.put("pw", EncryptUtil.sha512HexEnc(pw));
            if(pwChange!=null && !pwChange.equals("")){
                map.put("pwChange", EncryptUtil.sha512HexEnc(pwChange));
            }

            int pwCh = mypageUserService.pwCheck(map);

            if(pwCh == 0){
                returnCode = "01";
                message = "비밀번호가 맞지 않습니다.";
            }else if(pwCh != 0 && !status.equals("update")){
                UserVO userVO2 = mypageUserService.selectUser(userSession.getUserId());
                userVO2.setAuthCode("");
                userVO2.setPassword("");
                userVO2.setPin("");
                String moblieFns[] = StringUtil.getMobileNum(userVO2.getMobileNo());
                userVO2.setPhone1(moblieFns[0]);
                userVO2.setPhone2(moblieFns[1]);
                userVO2.setPhone3(moblieFns[2]);
                returnCode = "00";
                message = "";
                json = userVO2;
                userSession.setStatus("T");
                request.getSession().setAttribute(SessionUtils.USER_SESSION, userSession);
            }

            if(pwCh != 0 && status.equals("update") && pwChange.equals(pw)){
                returnCode = "02";
                message = "현재비밀번호와 동일한 비밀번호로 수정하실 수 없습니다.";
            }else if(pwCh != 0 && status.equals("update") && !pwChange.equals(pw)){

                //비밀번호 체크 패턴 -S-
                boolean pwCheckFlag = StringUtil.passwordPatternCheck(pwChange);
                //비밀번호 체크 패턴 -E-
                //pwCheckFlag = true;
                if(pwCheckFlag){
                    returnCode = "00";
                    message = "정보수정 페이지에서 변경을 진행하셔야 변경된 비밀번호가 최종 반영됩니다.";
                }else{
                    returnCode = "01";
                    message = "비밀번호 패턴을 확인해 주시기 바랍니다.";
                }
            }
        }
        result.setReturnCode(returnCode);
        result.setMessage(message);
        result.setResult(json);

        return result;
    }

    /**
     * 설명 : 회원정보 수정 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/m/mypage/userInfoView.do", "/mypage/userInfoView.do", "/m/mypage/UserInfoView.do", "/mypage/UserInfoView.do"})
    public String userInfoView(HttpServletRequest request, ModelMap model, HttpSession session) {
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        String returnUrl;

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {

            returnUrl = "mobile/mypage/userInfoView";
            if(userSession.getStatus().equals("T")) {
                checkOverlapDto.setRedirectUrl("/m/mypage/UserInfoView.do");
                model.addAttribute("status", "T");
            }else {
                userSession.setStatus("RF");
                return "redirect:/m/mypage/updateForm.do";
            }
        }else {
            returnUrl = "portal/mypage/userInfoView";
            if(userSession.getStatus().equals("T")) {
                model.addAttribute("status", "T");
                checkOverlapDto.setRedirectUrl("/mypage/UserInfoView.do");
            }else {
                userSession.setStatus("RF");
                return "redirect:/mypage/updateForm.do";
            }
        }

        //중복요청 체크
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            return "/common/successRedirect";
        }


        UserVO userVO2 = mypageUserService.selectUser(userSession.getUserId());
        List<NmcpCdDtlDto> nmcpList = NmcpServiceUtils.getCodeList(TERMS_MEM_CD);
        model.addAttribute("userVO", userVO2);
        model.addAttribute("nmcpList", nmcpList);
        userSession.setStatus("F");
        try {
            //만 나이 확인
            String birthDate = userSession.getBirthday();
            int age = NmcpServiceUtils.getBirthDateToAge(birthDate, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            //회원가입 시, 만 14세 미만 이용자 법적대리인 동의 절차 적용
            if (Constants.AGREE_AUT_AGE > age) {
                model.addAttribute("checkKid", "Y");

                //페이지 진입시 법정대리인 인증 세션 초기화
                session.removeAttribute(SessionUtils.NICE_AGENT_AUT_COOKIE);
            }
        } catch (Exception e) {
            logger.error("Exception e : {}", e.getMessage());
        }
        //페이지 진입시 인증 세션 초기화
        session.removeAttribute(SessionUtils.NICE_AUT_COOKIE);

        return returnUrl;
    }

    /**
     * 설명 : 회원정보 변경 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param response
     * @param userVO
     * @return
     */
    @RequestMapping(value = {"/m/mypage/userUpdateAjax.do", "/mypage/userUpdateAjax.do"})
    @ResponseBody
    public Map<String, Object> userUpdate(HttpServletRequest request, ModelMap model,
            HttpServletResponse response,@ModelAttribute("userVO") UserVO userVO, HttpSession session
            ,@RequestParam(required=false, defaultValue="") String mktAgreeUpdate){
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
//        if(!userSession.getUserId().equals(userVO.getUserid()) ){
//            throw new McpCommonJsonException("xxxx","비정상적인 접근입니다.");
//        }
        userVO.setUserid(userSession.getUserId());

        if(!userSession.getStatus().equals("F") && !"Y".equals(mktAgreeUpdate)) {
            throw new McpCommonJsonException("0001","비밀번호를 다시 확인 하시기 바랍니다. ");
        }

        if (!StringUtils.isBlank(userVO.getPassword())) {
            boolean pwCheckFlag = StringUtil.passwordPatternCheck(userVO.getPassword());
            if (!pwCheckFlag) {
                throw new McpCommonJsonException("0002", NO_MATCHE_PASSWORD_PATTERN);
            }
        }
        boolean regularYn = false;
        int certStep= 1;
        NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);

        // 본인인증을 한 경우
        if(niceResDto != null){
            NiceResDto niceResAgent = null;
            JuoSubInfoDto juoSubInfoRtn = null;
            UserAgentDto userAgent = null;

            String birthDate = userSession.getBirthday();
            int age = NmcpServiceUtils.getBirthDateToAge(birthDate, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));

            // 미성년자 인 경우
            if (Constants.AGREE_AUT_AGE > age) {
                // ============ STEP START ===========
                niceResAgent = SessionUtils.getNiceAgentResCookieBean() ;
                if (niceResAgent == null) {
                    throw new McpCommonJsonException("0003", NICE_CERT_AGENT_EXCEPTION_INSR);
                }

                // 대리인 최종정보 검증
                // 고객유형, 이름, 생년월일, DI
                String[] certKey= {"urlType", "ncType", "name", "birthDate", "dupInfo"};
                String[] certValue= {"chkAgentUpdateForm", "1", userVO.getMinorAgentName(), niceResAgent.getBirthDate(), niceResAgent.getDupInfo()};

                Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    throw new McpCommonJsonException("0005", vldReslt.get("RESULT_DESC"));
                }
                certStep= 5; // 5개
                // ============ STEP END ============
            } else {
                certStep= 3; // 3개
            }
            // ============ STEP START ===========

            // 1. 최소 스텝 수 확인
            if(certService.getStepCnt() < certStep ){
                throw new McpCommonJsonException("0005", STEP_CNT_EXCEPTION);
            }

            // 2. 최종 데이터 검증 : 스텝종료여부, 고객유형, 휴대폰번호, Di
            String[] certKey= {"urlType", "stepEndYn", "ncType", "mobileNo", "dupInfo"};
            String[] certValue= {"chkUpdateForm", "Y", "0", userVO.getMobileNo(), niceResDto.getDupInfo()};

            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("0005", vldReslt.get("RESULT_DESC"));
            }
            // ============ STEP END ============
            // 회선 정보 조회
            try{
                //인증한 이름과 연락처로 계약정보 확인
                juoSubInfoRtn = mypageUserService.selectJuoSubInfo(niceResDto.getName(),niceResDto.getsMobileNo());
                if(juoSubInfoRtn != null && ("I".equals(juoSubInfoRtn.getCustomerType()) || "O".equals(juoSubInfoRtn.getCustomerType()))){
                    userVO.setPhone1(userVO.getMobileNo().substring(0, 3));
                    userVO.setPhone2(userVO.getMobileNo().substring(3, 7));
                    userVO.setPhone3(userVO.getMobileNo().substring(7, 11));
                    userVO.setContractNo(juoSubInfoRtn.getContractNum());
                    userVO.setCustomerId(juoSubInfoRtn.getCustomerId());
                    userVO.setRepNo("R");
                    userVO.setUserDivision("01");

                    // 해당 계약번호로 정회원 테이블에 등록여부 확인
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("userId", userSession.getUserId());
                    paramMap.put("ncn", juoSubInfoRtn.getContractNum());
                    paramMap.put("customerId", juoSubInfoRtn.getCustomerId());

                    int regularCnt = mypageUserService.getRegularCnt(paramMap);
                    // 해당 계약번호로 등록된게 없다면 정회원 테이블에 INSERT
                    if(regularCnt < 1){
                        mypageUserService.insertRegularUpdate(userVO);  //MCP_USER_CNTR_MNG INSERT
                        // 준회원인 경우  정회원여부 Y로 변경 (이미 정회원인 경우 상관없음)
                        // 아래 로직에서 화면에 정회원으로 확인되었다는 alert 및 userSession을 준회원 ->정회원으로 변경
                        if("00".equals(userSession.getUserDivision())){
                            regularYn = true;
                        }
                    }
                    try {
                        appSvc.userRepChange(paramMap);
                    } catch(DataAccessException e) {
                        logger.error(e.toString());
                    } catch(Exception e) {
                        logger.error(e.toString());
                    }
                }
            } catch (Exception e){
                logger.error("Exception e = {}", e.getMessage());
            }
            if (Constants.AGREE_AUT_AGE > age) {
                // MCP_USER_AGENT (법정대리인 정보 테이블)에 있는 ci값과 법정대리인 인증한 ci값이 다른 경우 테이블 현행화
                if("00".equals(userSession.getUserDivision()) && juoSubInfoRtn == null){
                    String agentCi = "";
                    Map<String, Object> agentInfo = mypageUserService.getAgentInfo(userSession.getUserId());

                    if(agentInfo != null) agentCi = (String) agentInfo.get("MINOR_AGENT_CI");
                    if(!niceResAgent.getConnInfo().equals(agentCi)){
                        userAgent = new UserAgentDto();
                        userAgent.setUserid(userSession.getUserId());
                        userAgent.setMinorAgentName(niceResAgent.getName());
                        userAgent.setMinorAgentRrn(niceResAgent.getBirthDate());
                        //userAgent.setMinorAgentRelation("");
                        userAgent.setMinorAgentCi(niceResAgent.getConnInfo());
                        userAgent.setAgentAuthInfo(niceResAgent.getReqSeq() + " " + niceResAgent.getResSeq());
                        userAgent.setMinorAgentAgree("Y");
                        niceResAgent.getName();
                        joinSvc.insetUserAgent(userAgent);
                        session.removeAttribute(SessionUtils.NICE_AGENT_AUT_COOKIE);
                    }
                }
            }
            //인증 세션 삭제
            session.removeAttribute(SessionUtils.NICE_AUT_COOKIE);
            //인증통신사
            userVO.setAuthTelCode(niceResDto.getsMobileCo());
        } else if ("Y".equals(mktAgreeUpdate)) {
            // 마케팅 동의약관 팝업에서 넘어온 경우
            userVO.setMobileNo(userSession.getMobileNo());
            userVO.setEmailRcvYn(userSession.getEmailRcvYn());
            userVO.setSmsRcvYn(userSession.getSmsRcvYn());
        } else {
            // 인증을 안한 경우 화면에서 넘어온 휴대폰번호와 세션에 있는 전화번호가 같지 않을경우
            if(!userVO.getMobileNo().equals(userSession.getMobileNo())) {
                throw new McpCommonJsonException("0006", "휴대폰번호 변경 시 휴대폰인증을 먼저 해 주세요");
            }
        }

        if(!"Y".equals(mktAgreeUpdate)) {
            userVO.setMtkAgrReferer("userUpdate");
        } else {
            userVO.setMtkAgrReferer("mkkAgrPop");
        }

        int updateS = mypageUserService.userUpdate(userVO);

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        if(updateS==1){
            // 마케팅동의 세션 최신화
            userSession.setPersonalInfoCollectAgree(userVO.getPersonalInfoCollectAgree());
            userSession.setClausePriAdFlag(userVO.getClausePriAdFlag());
            userSession.setOthersTrnsAgree(userVO.getOthersTrnsAgree());
            SessionUtils.saveUserSession(userSession);

            //준회원 -> 정회원 변경 시 userSession도 정회원 userSession 세션으로 변경
            if(regularYn){
                userSession.setMobileNo(userVO.getPhone1()+userVO.getPhone2()+userVO.getPhone3());
                userSession.setUserDivision("01");
                userSession.setCustomerId(userVO.getCustomerId());
                SessionUtils.saveUserSession(userSession);
            }
            if("Y".equals(mktAgreeUpdate)){
                rtnMap.put("personalInfoCollectAgree", userVO.getPersonalInfoCollectAgree());
                rtnMap.put("clausePriAdFlag", userVO.getClausePriAdFlag());
                rtnMap.put("othersTrnsAgree", userVO.getOthersTrnsAgree());
            }

            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_MSG", "수정을 완료하였습니다.");
            rtnMap.put("RESULT_REGULAR_YN", regularYn);
        }else{
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "수정을 완료하실 수 없습니다.");

        }

        return rtnMap;
    }

    /**
     * 설명 : sns 공유 팝업 호출
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @RequestMapping(value = {"/snsSharePop.do", "/m/snsSharePop.do"})
    public String snsSharePop() {
        String returnUrl = "portal/mypage/snsSharePop";

        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "mobile/mypage/snsSharePop";
        }
        return returnUrl;
    }

    /**
     * 설명 : 소셜 로그인 관리 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/m/mypage/userSnsList.do", "/mypage/userSnsList.do"})
    public String userSnsList(HttpServletRequest request, ModelMap model) {
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        String returnUrl;

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "mobile/mypage/userSnsList";
            if(userSession.getStatus().equals("T")) {
                model.addAttribute("status", "T");
                checkOverlapDto.setRedirectUrl("/m/mypage/userSnsList.do");
            }else {
                userSession.setStatus("RS");
                return "redirect:/m/mypage/updateForm.do";
            }
        }else {
            returnUrl = "portal/mypage/userSnsList";
            if(userSession.getStatus().equals("T")) {
                model.addAttribute("status", "T");
                checkOverlapDto.setRedirectUrl("/mypage/userSnsList.do");
            }else {
                userSession.setStatus("RS");
                return "redirect:/mypage/updateForm.do";
            }
        }
        //중복요청 체크
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            return "/common/successRedirect";
        }

        UserVO userVO2 = mypageUserService.selectUser(userSession.getUserId());
        userSession.setStatus("S");
        return returnUrl;
    }

    /**
     * 설명 : 소셜 로그인 정보 조회 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @return
     */
    @RequestMapping(value = {"/m/mypage/userSnsListAjax.do", "/mypage/userSnsListAjax.do"})
    @ResponseBody
    public List<String> userSnsListAjax(HttpServletRequest request){
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        List<String> list = mypageUserService.selectUserSnsList(userSession.getUserId());
        return list;
    }

    /**
     * 설명 : 소셜 로그인 연동 해제
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param joinDto
     * @return
     */
    @RequestMapping(value = {"/m/mypage/deleteUserSnsAjax.do" , "/mypage/deleteUserSnsAjax.do"})
    @ResponseBody
    public Map<String, Object> deleteUserSnsAjax(HttpServletRequest request,
            @ModelAttribute("joinDto") JoinDto joinDto){
        Map<String, Object> rtnmap = new HashMap<String, Object>();
        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        joinDto.setUserId(userSession.getUserId());
        try{
            mypageUserService.deleteUserSns(joinDto);
            rtnmap.put("msg", "000");
        } catch(DataAccessException e) {
            rtnmap.put("msg", "001");
        } catch(Exception e) {
            rtnmap.put("msg", "001");
        }
        return rtnmap;
    }

    /**
     * 설명 : 다회선 인증(정회원인증) 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param response
     * @param model
     * @param searchVO
     * @return
     */
    @RequestMapping(value = {"/m/mypage/multiPhoneLine.do", "/mypage/multiPhoneLine.do"})
    public String multiFormHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            ModelMap model,@ModelAttribute("userSearchVO") UserSearchVO searchVO){


        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        String returnUrl = "portal/mypage/multiPhoneLine";
        checkOverlapDto.setRedirectUrl("/mypage/multiPhoneLine.do");
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "mobile/mypage/multiPhoneLine";
            checkOverlapDto.setRedirectUrl("/m/mypage/multiPhoneLine.do");
        }
        //중복요청 체크
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        model.addAttribute("codeHPN", mypageUserService.selectCodeList("HPN"));
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";

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

        return returnUrl;

    }

    /**
     * 설명 : 다회선 정보 조회 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param session
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value= {"/m/mypage/MultiPhoneLineListAjax.do", "/mypage/MultiPhoneLineListAjax.do"})
    @ResponseBody
    public Map<String, Object> multiPhoneLineListAjax(HttpSession session, HttpServletRequest request,
            ModelMap model){
        Map<String, Object> rtnmap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<UserVO> cntrList = null;

        if(userSession != null && !"".equals(StringUtil.NVL(userSession.getUserId(),""))) {
            cntrList = mypageUserService.selectUserMultiLine(userSession.getUserId());
            if(Optional.ofNullable(userSession).isPresent()) {
              if(userSession.getUserDivision().equals("00")){
                  rtnmap.put("title", "정회원인증");
                  rtnmap.put("but", "정회원 인증");
              }else {
                  rtnmap.put("title", "다회선추가");
                  rtnmap.put("but", "다회선 추가");
              }
            }else {
                rtnmap.put("title", "정회원인증");
                rtnmap.put("but", "정회원 인증");
            }
        }

        rtnmap.put("userVO1", cntrList);

        return rtnmap;
    }

    /**
     * 설명 : 대표번호 변경 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param response
     * @param model
     * @param contractNum
     * @return
     */
    @RequestMapping(value = {"/m/mypage/userRepChange.do", "/mypage/userRepChange.do"})
    @ResponseBody
    public ResponseSuccessDto userRepChange(HttpServletRequest request,
            HttpServletResponse response, ModelMap model
            ,@RequestParam(value = "contractNum", required = false) String contractNum
            ){
        //UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (StringUtils.isBlank(contractNum)) {
            throw new McpCommonException(INVALID_PARAMATER_EXCEPTION);
        }
        if (userSession != null) { // 취약성 345

            String phone = "";
            //---- API 호출 S ----//
            List<UserVO> userVOList = mypageUserService.getUserMultiLineList(userSession.getUserId());
            //---- API 호출 E ----//

            if(Optional.ofNullable(userVOList).isPresent()) {
                for (UserVO userVO : userVOList) {
                    if (contractNum.equals(userVO.getContractNum())) {
                        phone = userVO.getPhone1();
                        break;
                    }
                }
            }

             if (StringUtil.isBlank(phone)) {
                 throw new McpCommonException(INVALID_PARAMATER_EXCEPTION);
             }

            int repChange = mypageUserService.userRepChange(userSession.getUserId(),phone);
            if (repChange == 0) {
                throw new McpCommonJsonException("0001" ,"수정 하실 수 없습니다.\n서버관리자에게 문의해 주세요.");
            }
        }
        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();

        responseSuccessDto.setRedirectUrl("/mypage/multiPhoneLine.do");
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            responseSuccessDto.setRedirectUrl("/m/mypage/multiPhoneLine.do");
        }

        responseSuccessDto.setSuccessMsg("등록되었습니다.");
        model.addAttribute("responseSuccessDto", responseSuccessDto);
        return responseSuccessDto;

    }

    /**
     * 설명 : 다회선 추가 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param menuType
     * @return
     */
    @RequestMapping(value = { "/mypage/multiPhoneLineAddView.do", "/m/mypage/multiPhoneLineAddView.do" })
    public String multiPhoneLineAddView(HttpServletRequest request, Model model,
            @RequestParam(value="menuType", required = false) String menuType) {
        //UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        String returnUrl = "portal/mypage/multiPhoneLinePop";
        checkOverlapDto.setRedirectUrl("/mypage/multiPhoneLineAddView.do");
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "mobile/mypage/multiPhoneLinePop";
            checkOverlapDto.setRedirectUrl("/m/mypage/multiPhoneLineAddView.do");
        }
        //중복요청 체크
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            return "/common/successRedirect";
        }
        UserVO userVO = null;
        if (userSession != null) { // 취약성 346
            userVO = mypageUserService.selectUser(userSession.getUserId());
        }

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        }

        model.addAttribute("menuType", menuType);
        model.addAttribute("userVO", userVO);
        model.addAttribute("custNm", userVO != null ? userVO.getMkNm() : ""); // 취약성 346
        model.addAttribute("unMkNm", userVO != null ? userVO.getName() : ""); // 취약성 346
        model.addAttribute("popType", "nmIncPop");
        return returnUrl;

    }

    /**
     * 설명 : 회선 추가 전 회선유효성체크
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param cntrMng
     * @param menuType
     * @param timeYn
     * @return
     */
    @RequestMapping(value = {"/m/mypage/userMultiSmsAjax.do",  "/mypage/userMultiSmsAjax.do"})
    @ResponseBody
    public Map<String, Object> userMultiSms(HttpServletRequest request
                                            ,@ModelAttribute McpUserCntrMngDto cntrMng
                                            ,@RequestParam(value = "menuType", required = false) String menuType
                                            ,@RequestParam(value = "timeYn", required = false) String timeYn) throws ParseException {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        // name, mobileNo 없을경우 진행불가 => 인자값 에러발생
        if (StringUtils.isBlank(cntrMng.getSubLinkName()) || StringUtils.isBlank(cntrMng.getCntrMobileNo())) {
            throw new McpCommonJsonException("0001" ,INVALID_PARAMATER_EXCEPTION);
        }
        String name = cntrMng.getSubLinkName();
        String phone = cntrMng.getCntrMobileNo();

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //등록된 회선 조회
        String userId = "";
        if(userSession != null) {
            userId = StringUtil.NVL(userSession.getUserId(), "");
            name = userSession.getName();
            cntrMng.setSubLinkName(name);
        }

        List<McpUserCntrMngDto> cntrList = null;
        if(!"".equals(userId)) {
            cntrList = mypageService.selectCntrList(userId);
        }

        //전산상 회선 조회
        McpUserCntrMngDto rtnCntrMng = mypageService.selectCntrListNoLogin(cntrMng);
        if(Optional.ofNullable(rtnCntrMng).isEmpty()) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("msg", "자사 회선이 아닙니다.");
            return rtnMap;
        }
        if(Optional.ofNullable(rtnCntrMng.getSubStatus()).isPresent()) {
            if("S".equals(rtnCntrMng.getSubStatus())) {
                rtnMap.put("RESULT_CODE", "0003");
                rtnMap.put("msg", "정지 회선 입니다.");
                return rtnMap;
            }
        }

        if(Optional.ofNullable(cntrList).isPresent()) {
            for(int i = 0; i < cntrList.size(); i ++) {
                if(cntrList.get(i).getCntrMobileNo().trim().equals(rtnCntrMng.getCntrMobileNo().trim())) {
                    rtnMap.put("RESULT_CODE", "0004");
                    rtnMap.put("msg", "이미 등록된 회선 입니다.");
                    return rtnMap;
                }
            }
        }

        String contractNum = mypageUserService.selectContractNum(name,phone);

        if(Optional.ofNullable(contractNum).isEmpty()){
            rtnMap.put("RESULT_CODE", "0002");//등록되어 있지 않은 경우
            rtnMap.put("msg", "사용자 정보가 일치하지 않습니다.");
            return rtnMap;
        }else {
            boolean prePaymentFlag = mypageUserService.selectPrePayment(contractNum);

            if(Optional.ofNullable(prePaymentFlag).isPresent()){
                if(prePaymentFlag) {
                    rtnMap.put("RESULT_CODE", "0001");
                    return rtnMap;
                }
            }
        }

        // ============ STEP START ============
        // sms팝업 호출이력 확인
        if (0 < certService.getModuTypeStepCnt("smsPop", "")) {
            // sms팝업 호출 관련 스텝 초기화
            CertDto certDto = new CertDto();
            certDto.setModuType("smsPop");
            certDto.setCompType("G");
            certService.getCertInfo(certDto);
        }

        // 인증종류, 이름, 계약번호, 핸드폰번호
        String[] certKey = {"urlType", "moduType", "name", "contractNum", "mobileNo"};
        String[] certValue = {"reqSmsAuthNum", "smsPop", name, contractNum, phone};
        certService.vdlCertInfo("C", certKey, certValue);
        // ============ STEP END ============

        if("Y".equals(timeYn)) {
             AuthSmsDto authSmsDto = new AuthSmsDto();
             authSmsDto.setMenu(menuType);
             AuthSmsDto resultAuthSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);

             if(resultAuthSmsDto == null) {
                 throw new McpCommonJsonException("0001" ,INVALID_PARAMATER_EXCEPTION);
             }

             String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");

             authSmsDto.setStartDate(today);
             authSmsDto.setPhoneNum(phone);
             authSmsDto.setMenu(menuType);
             authSmsDto.setAuthNum(resultAuthSmsDto.getAuthNum());

             //관리자 정보 session 저장
             SessionUtils.setAuthSmsSession(authSmsDto);
             rtnMap.put("RESULT_CODE", "00010");
        } else {
            AuthSmsDto authSmsDto = new AuthSmsDto();
            authSmsDto.setPhoneNum(phone.toString());
            authSmsDto.setMenu(menuType);
            authSmsDto.setReserved02(menuType);
            authSmsDto.setReserved03((userId == null || userId.isEmpty()) ? "nonMember" : userId);

//            boolean check = fCommonSvc.sendAuthSms(authSmsDto);
            Map<String, Object> reMap = this.fCommonSvc.sendAuthSms2(authSmsDto);
            boolean check = (boolean) reMap.get("result");

            if (check) {
                request.getSession().setAttribute("authNum", authSmsDto.getAuthNum());	//sms 인증번호를 session에 담아 비회원 로그인시 검증값으로 사용
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            }  else if (StringUtils.isNotEmpty(reMap.get("message").toString())) {
                rtnMap.put("RESULT_CODE", "00011");
                rtnMap.put("message", reMap.get("message"));
            } else {
                rtnMap.put("RESULT_CODE", "0001");//sms전송실패
            }
            if ( "LOCAL".equals(serverName) || "DEV".equals(serverName) ) {
                rtnMap.put("AUTH_NUM", authSmsDto.getAuthNum());
            }
        }

        rtnMap.put("contractNum", contractNum);
        rtnMap.put("svcCntrNo", EncryptUtil.ace256Enc(phone));

        return rtnMap;
    }

    /**
     * 설명 : 회선 추가 처리
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param response
     * @param userVO
     * @param menuType
     * @param certifySms
     * @return
     */
    @RequestMapping(value = {"/m/mypage/userMultiReg.do", "/mypage/userMultiReg.do" })
    @ResponseBody
    public ResponseSuccessDto userMultiReg(HttpServletRequest request, ModelMap model,HttpServletResponse response
          ,@ModelAttribute UserVO userVO
          ,@RequestParam(value = "menuType", required = false) String menuType
          ,@RequestParam(value = "certifySms", required = false) String certifySms){

        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if (StringUtils.isBlank(userVO.getName())||StringUtils.isBlank(userVO.getPhone1()) ) {
            throw new McpCommonJsonException("0001" ,INVALID_PARAMATER_EXCEPTION);
        }

        ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
        responseSuccessDto.setRedirectUrl("/mypage/multiPhoneLine.do");
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            responseSuccessDto.setRedirectUrl("/m/mypage/multiPhoneLine.do");
        }

        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setCntrMobileNo(userVO.getPhone1());

        AuthSmsDto tmp = new AuthSmsDto();
        tmp.setPhoneNum(userVO.getPhone1());
        tmp.setAuthNum(certifySms);
        tmp.setMenu(menuType);
        SessionUtils.checkAuthSmsSession(tmp);

        if (!tmp.isResult()) {
            responseSuccessDto.setSuccessMsg("SMS 인증 후 추가 가능합니다.");
            return responseSuccessDto;
        }

        // ============ STEP START ============
        // 이름 변조 방지 로직 추가
        UserVO tempUserVo = new UserVO();
        if (userSession != null) {
            tempUserVo = mypageUserService.selectUser(userSession.getUserId());
            userVO.setName(userSession.getName());
        }

        if(StringUtils.isEmpty(tempUserVo.getName())){
            responseSuccessDto.setSuccessMsg("사용자 정보를 찾을 수 없습니다.");
            return responseSuccessDto;
        }

        String tempUserNm= tempUserVo.getName().toUpperCase().replaceAll(" ", "");
        String reqUserNm= userVO.getName().toUpperCase().replaceAll(" ", "");
        if(!tempUserNm.equals(reqUserNm)){
            responseSuccessDto.setSuccessMsg("로그인 정보와 일치하지 않습니다.");
            return responseSuccessDto;
        }
        // ============ STEP END ============

        // 등록된 번호인지 조회
        McpUserCntrMngDto cntr = mypageService.selectCntrListNoLogin(userCntrMngDto);
        StringBuilder phone  = new StringBuilder("");
        if(Optional.ofNullable(userVO.getPhone1()).isPresent()) {
            phone.append(userVO.getPhone1());
        }
        if(Optional.ofNullable(userVO.getPhone2()).isPresent()) {
            phone.append(userVO.getPhone2());
        }
        if(Optional.ofNullable(userVO.getPhone3()).isPresent()) {
            phone.append(userVO.getPhone3());
        }
        String contractNum = mypageUserService.selectContractNum(userVO.getName(),phone.toString());
        if(contractNum == null) {
            throw new McpCommonJsonException("xxxx","비정상적인 접근입니다.");
        }
        userVO.setContractNo(contractNum);
        userVO.setCustomerId(userSession.getCustomerId());

        // ============ STEP START ============
        // 1. 최소 스텝 수 확인
        if(certService.getStepCnt() < 2 ){
            responseSuccessDto.setSuccessMsg(STEP_CNT_EXCEPTION);
            return responseSuccessDto;
        }

        // 2. 최종 데이터 검증: 스텝종료여부, 계약번호, 이름, 핸드폰번호
        String[] certKey = {"urlType", "stepEndYn", "contractNum", "name", "mobileNo"};
        String[] certValue = {"regRegularUser", "Y", contractNum, userVO.getName(), userVO.getPhone1()};
        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            responseSuccessDto.setSuccessMsg(vldReslt.get("RESULT_DESC"));
            return responseSuccessDto;
        }
        // ============ STEP END ============

        StringBuffer phoneNum = new StringBuffer();
        phoneNum.append(userVO.getPhone1()).append(userVO.getPhone2()).append(userVO.getPhone3());
        userVO.setMobileNo(phoneNum.toString());
        if(phoneNum.length() > 10) {
            userVO.setPhone1(phoneNum.substring(0, 3));
            userVO.setPhone2(phoneNum.substring(3, 7));
            userVO.setPhone3(phoneNum.substring(7, 11));
        }else {
            userVO.setPhone1(phoneNum.substring(0, 3));
            userVO.setPhone2(phoneNum.substring(3, 6));
            userVO.setPhone3(phoneNum.substring(6, 10));
        }

        userVO.setUserid(userSession.getUserId());
        userVO.setRepNo("");

        if(Optional.ofNullable(cntr).isEmpty()) {
            responseSuccessDto.setSuccessMsg("등록된 번호가 아닙니다. 확인 후 다시 시도해 주세요.");
            return responseSuccessDto;
        }
        try {
            if("I".equals(cntr.getCustomerType()) || "O".equals(cntr.getCustomerType())) {
                userVO.setCustomerId(cntr.getCustomerId());
                if(userSession.getUserDivision().equals("00")) {
                    userVO.setRepNo("R");
                    mypageUserService.userRegularUpdate(userVO);
                }
                this.mypageUserService.insertRegularUpdate(userVO);
                responseSuccessDto.setSuccessMsg("등록되었습니다.");
                return responseSuccessDto;
            }else {
                responseSuccessDto.setSuccessMsg("법인/공공기관은 추가하실 수 없습니다.");
                return responseSuccessDto;
            }
        } catch(DataAccessException e){
            responseSuccessDto.setSuccessMsg("등록 중 에러가 발생했습니다. 다시 시도해 주세요.");
            return responseSuccessDto;
        } catch(Exception e) {
            responseSuccessDto.setSuccessMsg("등록 중 에러가 발생했습니다. 다시 시도해 주세요.");
            return responseSuccessDto;
        }
    }

    /**
     * 설명 : 마이페이지 > 사용 후기 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/mypage/reView.do" , "/m/mypage/reView.do"})
    public String reView(HttpServletRequest request, Model model) {
        UserSessionDto userSession = SessionUtils.getUserCookieBean();


        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        String returnUrl = "portal/mypage/reView";
        checkOverlapDto.setRedirectUrl("/mypage/reView.do");
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) {  // 취약성 342
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "mobile/mypage/reView";
            checkOverlapDto.setRedirectUrl("/m/mypage/reView.do");
        }
        if (Optional.ofNullable(cntrList).isEmpty() || cntrList.size() <= 0 || userSession.getUserDivision().equals("00")) {
            checkOverlapDto.setRedirectUrl(request.getHeader("Referer"));
            checkOverlapDto.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            return "/common/successRedirect";
        }



        return returnUrl;
    }

    /**
     * 설명 : 마이페이지 > 사용후기 리스트 조회
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param pageInfoBean
     * @return
     */
    @RequestMapping(value = {"/m/mypage/reViewListAjax.do", "/mypage/reViewListAjax.do"})
    @ResponseBody
    public Map<String, Object> getReViewListAjax(HttpServletRequest request, PageInfoBean pageInfoBean){

        Map<String,Object> rtnMap = new HashMap<String, Object>();
        int total = 0;

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        List<String> contractList = new ArrayList<String>();

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId()); // 3개
        if(Optional.ofNullable(cntrList).isPresent()) {
            for(McpUserCntrMngDto dto : cntrList){ // 3개

                String svcCntrNo = StringUtil.NVL(dto.getSvcCntrNo(),"");

                contractList.add(dto.getContractNum());
            }
        }

      List<RequestReviewDto> requestReviewDtoList = new ArrayList<RequestReviewDto>();
      RequestReviewDto requestReviewDto = new RequestReviewDto();
      if(contractList==null || contractList.isEmpty()){
          requestReviewDtoList = null;
      } else {
          requestReviewDto.setContractNumList(contractList);
          /*현재 페이지 번호 초기화*/
          if(pageInfoBean.getPageNo() == 0){
              pageInfoBean.setPageNo(1);
          }

          /*한페이지당 보여줄 리스트 수*/
          pageInfoBean.setRecordCount(10);

          /*카운터 조회*/
          total = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto);
          pageInfoBean.setTotalCount(total);

          int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
          int maxResult = pageInfoBean.getRecordCount();	// Pagesize
          requestReviewDtoList = requestReviewService.selectMypageReviewList(requestReviewDto,skipResult,maxResult);

          if(requestReviewDtoList !=null && !requestReviewDtoList.isEmpty()){

              for(RequestReviewDto dto : requestReviewDtoList){
                  dto.setMkRegNm(dto.getRegNm());
                  dto.setRegNm("");
              }
          }


      }
      rtnMap.put("list", requestReviewDtoList);
      rtnMap.put("total", total);
      rtnMap.put("pageInfoBean", pageInfoBean);
        return rtnMap;
    }

      /**
     * 설명 : 사용후기 삭제 ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param requestReviewDto
     * @return
     */
    @RequestMapping(value = {"/mypage/reviewDeleteAjax.do","/m/mypage/reviewDeleteAjax.do"})
    @ResponseBody
    public Map<String, Object> reviewMyDeleteAjax(@ModelAttribute RequestReviewDto requestReviewDto ){

      Map<String, Object> rtnMap = new HashMap<String,Object>();
      String contractNum = requestReviewDto.getContractNum();
      if ( StringUtils.isBlank(contractNum) || StringUtils.isBlank(contractNum) ) {
          rtnMap.put("RESULT_CODE", "0001");
          return rtnMap;
      }

      // 로그인한 고객의 회선번호와 체크
      boolean isChk = false;
      UserSessionDto userSession = SessionUtils.getUserCookieBean();
      if(userSession==null || StringUtils.isEmpty(userSession.getUserId())){
          rtnMap.put("RESULT_CODE", "0001");
          return rtnMap;
      }
      List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId()); // 3개
      if(Optional.ofNullable(cntrList).isPresent()) {
          for(McpUserCntrMngDto dto : cntrList){
              if(contractNum.equals(dto.getSvcCntrNo())){
                  isChk = true;
                  break;
              }
          }
      }

      if(!isChk){
          rtnMap.put("RESULT_CODE", "0001");
          return rtnMap;
      }
      // 로그인한 고객의 회선번호와 체크 끝

      if (requestReviewService.deleteMcpRequestReview(requestReviewDto) ) {
          rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
          rtnMap.put("RESULT_DESC", "성공");
      } else {
          rtnMap.put("RESULT_CODE", "9999");
          rtnMap.put("RESULT_DESC", "DB처리 오류");
      }

     return rtnMap;
  }

  /**
     * 설명 : 회원 탈퇴 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */
    @RequestMapping(value = {"/mypage/recForm.do","/m/mypage/recForm.do"})
    public String recForm(HttpServletRequest request, ModelMap model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {


        String pageUrl = "/portal/mypage/recForm";
        String redirectUrl = "/mypage/recForm.do";
        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            pageUrl = "/mobile/mypage/recForm";
            redirectUrl = "/m/mypage/recForm.do";
        }
        checkOverlapDto.setRedirectUrl(redirectUrl);
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        searchVO.setUserName(StringMakerUtil.getUserId(userSession.getUserId()));
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("userId", userSession.getUserId());

        return pageUrl;
    }

  /**
     * 설명 : 회원탈퇴 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param response
     * @param model
     * @param searchVO
     * @param pw
     * @param secedeReason
     * @param secedeReasonDesc
     * @return
     */
    @RequestMapping(value={"/mypage/deleteUserAjax.do", "/m/mypage/deleteUserAjax.do"})
    @ResponseBody
      public JsonReturnDto deleteUserAjax(HttpServletRequest request,HttpServletResponse response, ModelMap model
          , @ModelAttribute("searchVO") MyPageSearchDto searchVO, String pw, String secedeReason, String secedeReasonDesc){
      JsonReturnDto result = new JsonReturnDto();
      String returnCode = "99";
      String message = "오류가 밸생했습니다. 다시 시도해 주세요.";

      UserSessionDto userSession = SessionUtils.getUserCookieBean();
      if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
          returnCode = "21";
          message = "로그인해 주세요.";
      } else {
              HashMap<String, String> map = new HashMap<String, String>();
              map.put("id", userSession.getUserId());
              map.put("pw", EncryptUtil.sha512HexEnc(pw));

              UserVO userVO1 = mypageUserService.selectUserByUseridAndPassword(map);
              if(userVO1 != null && userVO1.getStatus().equals("1")){
                  UserVO userVO = new UserVO();
                  userVO.setSecedeReason(secedeReason);
                  userVO.setSecedeReasonDesc(secedeReasonDesc);
                  userVO.setUserid(userSession.getUserId());
                  int resultCd = 1;

                  try {
                      if(userSession.getUserDivision().equals("00")){
                          mypageUserService.insertRec2(userVO);
                      }else{
                          //정회원
                          mypageUserService.insertRec(userVO);
                      }

                      // Mstore 탈퇴 연동 대상 insert
                      userVO1.setContractNum(userVO.getContractNum());
                      mstoreService.insertMstoreCanTrg(userVO1);

                      // M캐시 탈퇴 연동
                      mcashService.cancelMcashUser(userSession.getUserId(), MCASH_EVENT_CANCEL_PORTAL);

                  } catch(DataAccessException e) {
                      returnCode = "41";
                      message = "오류입니다. 다시 시도해 주세요.";
                  } catch(Exception e) {
                      returnCode = "41";
                      message = "오류입니다. 다시 시도해 주세요.";
                  }

                  mypageUserService.deleteUserHst(userVO1);

                  //sns 로그인 연동 테이블 삭제
                  mypageUserService.deleteSnsLoginTxn(userSession.getUserId());

                  //로그인 history 테이블 수정(이름,휴대폰 변경 처리)
                  mypageUserService.updateLoginHistory(userSession.getUserId());

                  //회원가입 프로모션 내역이 있으면 같이 삭제
                  UserPromotionDto userPromotionDto = new UserPromotionDto();
                  userPromotionDto.setUserId(userSession.getUserId());
                  int promoCnt = userPromotionSvc.selectUserPromoCnt(userPromotionDto);

                  if(promoCnt > 0) {
                      userPromotionSvc.deleteUserPromotion(userPromotionDto);
                  }

                  returnCode = "00";
                  message = "";


              } else if(userVO1 == null){
                  returnCode = "-1";
                  message = "일치하는 아이디와 패스워드가 없습니다.";
              }else if(userVO1.getStatus().equals("2")){
                  returnCode = "2";
                  message = "정지된 아이디입니다.";
              }else if(userVO1.getStatus().equals("3")){
                  returnCode = "3";
                  message = "탈퇴한 아이디입니다.";
              }else if(userVO1.getStatus().equals("0")){
                  returnCode = "0";
                  message = "미승인된 아이디입니다";
              }else{
                  returnCode = "41";
                  message = "오류입니다. 다시 시도해 주세요.";
              }
          }
      result.setReturnCode(returnCode);
      result.setMessage(message);
      return result;
    }

    /**
     * 설명 : 정/준회원 확인 메소드
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param searchVO
     * @param cntrList
     * @param userSession
     * @return
     */
    @Deprecated
    private boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession){
        if( ! StringUtil.equals(userSession.getUserDivision(), "01") ){
            return false;
        }

        if(cntrList == null) {
            return false;
        }

        if(cntrList.size() <= 0){
            return false;
        }

        //logger.debug("param searchVO.getMenuKey():"+searchVO.getMenuKey());
        //logger.debug("param searchVO.getNcn():"+searchVO.getNcn());

        if( StringUtil.isEmpty(searchVO.getNcn()) ){
            searchVO.setNcn(cntrList.get(0).getSvcCntrNo() );
            searchVO.setCtn(cntrList.get(0).getCntrMobileNo() );
            searchVO.setCustId(cntrList.get(0).getCustId());
            searchVO.setModelName(cntrList.get(0).getModelName());
            searchVO.setContractNum(cntrList.get(0).getContractNum());
        }

        for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
            String ctn = mcpUserCntrMngDto.getCntrMobileNo();
            String ncn = mcpUserCntrMngDto.getSvcCntrNo();
            String custId = mcpUserCntrMngDto.getCustId();
            String modelName = mcpUserCntrMngDto.getModelName();
            String contractNum = mcpUserCntrMngDto.getContractNum();

            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            mcpUserCntrMngDto.setSvcCntrNo(ncn);
            mcpUserCntrMngDto.setCustId(custId);
            mcpUserCntrMngDto.setModelName(modelName);
            mcpUserCntrMngDto.setContractNum(contractNum);
            if(StringUtil.equals(searchVO.getNcn(), String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))){
                searchVO.setNcn( ncn );
                searchVO.setCtn( ctn );
                searchVO.setCustId(custId);
                searchVO.setModelName(modelName);
                searchVO.setContractNum(contractNum);
            }
        }

        /*
         * 확인용도 로그
        logger.debug("searchVO.getNcn():"+searchVO.getNcn());
        logger.debug("searchVO.getCtn():"+searchVO.getCtn());
        logger.debug("searchVO.getCustId():"+searchVO.getCustId());
        logger.debug("searchVO.getModelName():"+searchVO.getModelName());
        logger.debug("searchVO.getContractNum():"+searchVO.getContractNum());
        */

        return true;
    }

    @Deprecated
    private ResponseSuccessDto getMessageBox(){
        ResponseSuccessDto mbox = new ResponseSuccessDto();
        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }
        mbox.setRedirectUrl(url);
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");

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

    @Deprecated
    private String getErrCd(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        result = arg[0]; //N:성공, S:시스템에서, E:Biz 에러
        return result;
    }


    @RequestMapping(value = {"/m/mypage/getMktAgrInfoAjax.do"})
    @ResponseBody
    public Map<String, Object> getMktAgrInfoAjax() {
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("returnCode", "01");

        if(userSessionDto == null) {
            return returnMap;
        }
        boolean isMktAgreePop = mypageUserService.isOpenMktAgreePop(userSessionDto.getUserId());
        if (!isMktAgreePop) {
            return returnMap;
        }

        UserVO userVo = mypageUserService.selectUser(userSessionDto.getUserId());
        returnMap.put("returnCode", "00");
        returnMap.put("mktPersonalInfoCollectAgree", userVo.getPersonalInfoCollectAgree());
        returnMap.put("mktClausePriAdFlag", userVo.getClausePriAdFlag());
        returnMap.put("mktOthersTrnsAgree", userVo.getOthersTrnsAgree());

        return returnMap;
    }
}
