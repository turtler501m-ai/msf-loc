package com.ktmmobile.mcp.requestReview.controller;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.AuthSmsDto;
import com.ktmmobile.mcp.common.dto.BannerDto;
import com.ktmmobile.mcp.common.dto.FileBoardDTO;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.McpErropPageException;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.event.dto.EventJoinDto;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.MypageUserService;
import com.ktmmobile.mcp.requestReview.dto.McpRequestReviewImgDto;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;
import com.ktmmobile.mcp.requestReview.dto.ReviewDataInfo;
import com.ktmmobile.mcp.requestReview.service.RequestReviewService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.Console;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class RequestReviewController {

    private static Logger logger = LoggerFactory.getLogger(RequestReviewController.class);

    @Autowired
    private RequestReviewService requestReviewService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private FormDtlSvc formDtlSvc;

    @Autowired
    private CertService certService;


    /** 파일업로드 경로 */
    @Value("${common.fileupload.base}")
    private String fileuploadBase;

    /** 파일업로드 접근가능한 웹경로 */
    @Value("${common.fileupload.web}")
    private String fileuploadWeb;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private MaskingSvc maskingSvc;

    /**
     * 설명 : 사용 후기 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param requestReviewDto
     * @param pageInfoBean
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/requestReView/requestReView.do","/m/requestReView/requestReView.do"})
    public String requestReView(@ModelAttribute RequestReviewDto requestReviewDto, PageInfoBean pageInfoBean,Model model,HttpServletRequest request) {

        String returnUrl = "portal/requestReview/requestReView";

//        if("Y".equals(NmcpServiceUtils.isMobile())){
//        	returnUrl = "mobile/requestReview/requestReView";
//        }

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "mobile/requestReview/requestReView";
        }

        // 이벤트 코드
        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.REVIEW_EVENT_CD);
        List<NmcpCdDtlDto> nmcpMainCdDtlDtoList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);
        int eventTotal = 0;
        if(nmcpMainCdDtlDtoList !=null && !nmcpMainCdDtlDtoList.isEmpty()){
            for(NmcpCdDtlDto dto : nmcpMainCdDtlDtoList){
                RequestReviewDto reqDto = new RequestReviewDto();
                reqDto.setEventCd(dto.getDtlCd());
                int total = requestReviewService.selectUsimRequestReviewTotalCnt(reqDto);
                eventTotal += total;
                dto.setEventCdCtn(total);
            }
        }

        //사용후기 질문
        List<RequestReviewDto> reviewCodeList = requestReviewService.reviewCodeList(requestReviewDto);

        if(reviewCodeList != null && !reviewCodeList.isEmpty()) {
            int questId = reviewCodeList.get(0).getQuestionId();
            model.addAttribute("questId", "review"+questId);
            model.addAttribute("reviewCodeList", reviewCodeList);
        }

        //이번달 우수 사용후기
        List<RequestReviewDto> requestReviewDtoList = requestReviewService.selectBestReviewList(requestReviewDto);
        if(requestReviewDtoList != null && !requestReviewDtoList.isEmpty()) {
            model.addAttribute("reviewList", "reviewList");
        }else {
            model.addAttribute("reviewList", "noReviewList");
        }

        //메인 사용후기에서 타고 들어올경우
        String reviewMoveVal =  request.getParameter("choice");

        if(reviewMoveVal != null) {
            try {
                int reviewVal = Integer.parseInt(reviewMoveVal)+1;
                model.addAttribute("reviewVal", reviewVal);
            } catch (ArithmeticException e) {
                 logger.error("ArithmeticException e : {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Exception e : {}", e.getMessage());
           }
        }

        // %퍼이상일때만 노출
        String strLimitPer = StringUtil.NVL(NmcpServiceUtils.getCodeNm("Constant",Constants.REVIEW_PERCENT),"0");
        int limitPer = Integer.parseInt(strLimitPer);
        RequestReviewDto reqDto = new RequestReviewDto();
        reqDto.setLimitPer(limitPer);
        NmcpServiceUtils nmcpServiceUtils = new NmcpServiceUtils();
        List<BannerDto> bannerList = nmcpServiceUtils.getBannerList("E001");
        // 보여질지 말지 결정
        RequestReviewDto resDto =  requestReviewService.selectReviewLimit(reqDto);
        model.addAttribute("resDto", resDto);
        model.addAttribute("nmcpMainCdDtlDtoList", nmcpMainCdDtlDtoList);
        model.addAttribute("eventTotal", eventTotal);
        model.addAttribute("menuType", "reviewDel");

        return returnUrl;
    }


    /**
     * 설명 : 사용 후기 리스트 호출Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param requestReviewDto
     * @param onlyMine
     * @param pageInfoBean
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/m/requestReView/reviewDataAjax.do", "/requestReView/reviewDataAjax.do"})
    @ResponseBody
    public Map<String,Object> reviewMAjax(@ModelAttribute RequestReviewDto requestReviewDto, @RequestParam("onlyMine") boolean onlyMine, PageInfoBean pageInfoBean,Model model,HttpServletRequest request) {

        HashMap<String,Object> map = new HashMap<String, Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        //로그인 여부, 내 후기만 보기 설정
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())){
            map.put("isLogin", false);

        }else {
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            List<String> conNumList = new ArrayList<String>();
            if(Optional.ofNullable(cntrList).isPresent()) {
                for(int i = 0; i < cntrList.size(); i ++) {
                    conNumList.add(cntrList.get(i).getContractNum());
                }
            }
            map.put("isLogin", true);
            map.put("conNumList", conNumList);
            if(onlyMine) {
                requestReviewDto.setContractNumList(conNumList);
            }
        }

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(request.getParameter("recordCount") == null ? 20 : pageInfoBean.getRecordCount()); //폰상세보기 리뷰에서 10개씩 보여줘야 해서 수정함

        /*카운터 조회*/
        int total = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto);
        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        List<RequestReviewDto> requestReviewDtoList = requestReviewService.selectRequestReviewList(requestReviewDto,skipResult,maxResult);

        if(requestReviewDtoList !=null && !requestReviewDtoList.isEmpty()){

            for(RequestReviewDto dto : requestReviewDtoList){
                dto.setSysRdateDd(DateTimeUtil.changeFormat(dto.getSysRdate(),"yyyy.MM.dd"));
                dto.setMkRegNm(dto.getRegNm());
                dto.setRegNm("");
            }


            requestReviewDtoList = requestReviewDtoList.stream()
                    .sorted(Comparator.comparing(RequestReviewDto::getPrizeSbst, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
                            .thenComparing(RequestReviewDto::getNtfYn).reversed())
                    .collect(Collectors.toList());
        }



     // 이벤트 코드
        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.REVIEW_EVENT_CD);
        List<NmcpCdDtlDto> nmcpMainCdDtlDtoList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);
        int eventTotal = 0;
        if(nmcpMainCdDtlDtoList !=null && !nmcpMainCdDtlDtoList.isEmpty()){
            for(NmcpCdDtlDto dto : nmcpMainCdDtlDtoList){
                requestReviewDto.setEventCd(dto.getDtlCd());
                 int cnt = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto);
                eventTotal += cnt;
                dto.setEventCdCtn(cnt);
            }
            requestReviewDto.setEventCd(null);
        }

        map.put("eventTotal", eventTotal);
        map.put("eventList", nmcpMainCdDtlDtoList);
        map.put("pageInfoBean", pageInfoBean);
        map.put("total", total);
        map.put("requestReviewDtoList", requestReviewDtoList);

        return map;
    }


    /**
     * 설명 : 리뷰 data 추출 Ajax
     * @return
     */
    @RequestMapping(value = "/requestReView/allReviewDataAjax.do")
    @ResponseBody
    public Map<String,Object> allReviewMAjax(@ModelAttribute RequestReviewDto requestReviewDto, PageInfoBean pageInfoBean) {

        HashMap<String,Object> map = new HashMap<String, Object>();

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        logger.info("pageInfoBean.getRecordCount()===>" + pageInfoBean.getRecordCount());

        /*카운터 조회*/
        int total = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto);
        pageInfoBean.setTotalCount(total);



        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        ReviewDataInfo reviewDataInfo = new ReviewDataInfo();
        List<RequestReviewDto> requestReviewDtoList = requestReviewService.selectReviewDataList(reviewDataInfo , skipResult,maxResult);

        map.put("pageInfoBean", pageInfoBean);
        map.put("total", total);
        map.put("requestReviewDtoList", requestReviewDtoList);

        return map;
    }


    /**
     * 설명 : 사용 후기 조회수 증가 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param requestKey
     * @return
     */
    @RequestMapping(value = {"/m/requestReView/modifyReviewHitAjax.do", "/requestReView/modifyReviewHitAjax.do"})
    @ResponseBody
    public Map<String, Object> modifyReviewHitAjax(
            @RequestParam(value = "requestKey", required = false) Integer requestKey,
            @RequestParam(value = "reviewId", required = false) Integer reviewId) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        RequestReviewDto requestReviewDto = new RequestReviewDto();
        requestReviewDto.setRequestKey(requestKey);
        requestReviewDto.setReviewId(reviewId);

        try {
            requestReviewService.modifyReviewHit(requestReviewDto);
            rtnMap.put("RETURN_CODE", "0000");
        } catch(DataAccessException e) {
            rtnMap.put("RETURN_CODE", "0001");
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        } catch(Exception e) {
            rtnMap.put("RETURN_CODE", "0001");
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return rtnMap;
    }

    /**
     * 설명 : 사용 후기 전용 sms 인증번호 받기 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param name
     * @param phone
     * @return
     */
    @RequestMapping(value = "/requestReView/userSmsAjax.do")
    @ResponseBody
    public Map<String, Object> prodUserSms(HttpServletRequest request
            ,@RequestParam(value = "name", required = false) String name
            ,@RequestParam(value = "phone", required = false) String phone
            ){

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();


        if ( StringUtils.isBlank(name) || StringUtils.isBlank(phone) ) {
            rtnMap.put("RESULT_CODE", "0001");
            return rtnMap;
        }

        // 1.M전산에 등록되지 않은 경우
        String contractNum = mypageUserService.selectContractNum(name,phone,"","");
        if(contractNum==null ){
            rtnMap.put("RESULT_CODE", "0002");
            return rtnMap;
        }

        // 1.1 선불
        boolean prePaymentFlag= mypageUserService.selectPrePayment(contractNum);
        if(prePaymentFlag){
             rtnMap.put("RESULT_CODE", "0003");
             return rtnMap;
        }
        // 2. MCP_REQUEST 조회
        RequestReviewDto mcpRequestData = requestReviewService.selectMcpRequestData(contractNum);

        // 2-1. 개통 됐는지 확인
        if(mcpRequestData == null){
            rtnMap.put("RESULT_CODE", "0002");
            return rtnMap;
        }

        // 2-2. 직영대리점 개통인지 확인
        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.REVIEW_AGENT_CD);
        List<NmcpCdDtlDto> reviewAgentCdList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

        String agentCode = StringUtil.NVL(mcpRequestData.getAgentCode(),"");
        boolean isAgent = false;
        for(NmcpCdDtlDto dto:reviewAgentCdList){
            String reviewAgentCd = dto.getDtlCd();
            if(agentCode.equals(reviewAgentCd)){
                isAgent = true;
                break;
            }
        }
        if(!isAgent){
            rtnMap.put("RESULT_CODE", "0004");
            return rtnMap;
        }


        // 2-3. 가입날짜 90일이전인지 확인 나중에 수정해야될 필요 있음
        RequestReviewDto mspJuoSubInfoData = requestReviewService.selectMspJuoSubInfo(contractNum);
        String dayOver = mspJuoSubInfoData.getDayOver();
        if("N".equals(dayOver)){
            rtnMap.put("RESULT_CODE", "0005");
            return rtnMap;
        }

        // 3. 리뷰 등록된 회선인지 확인
        int custReviewCnt = requestReviewService.selectCustReviewCnt(contractNum);
        if( custReviewCnt > 0 ){
            rtnMap.put("RESULT_CODE", "0006");
            return rtnMap;
        }

        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setPhoneNum(phone);
        authSmsDto.setMenu("reviewReg");
        boolean check = fCommonSvc.sendAuthSms(authSmsDto);

        if (check) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        } else {
            rtnMap.put("RESULT_CODE", "0007");
        }

        if ("DEV".equals(serverName) || "LOCAL".equals(serverName)) {
            authSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);
            String authNum = authSmsDto.getAuthNum();
            rtnMap.put("authNum", authNum);
        } else {
           rtnMap.put("authNum", "");
        }

        return rtnMap;
    }

    /**
     * 설명 : 사용 후기 삭제 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param requestReviewDto
     * @return
     */
    @RequestMapping(value = {"/requestReView/reviewDeleteAjax.do", "/m/requestReView/reviewDeleteAjax.do"})
    @ResponseBody
    public Map<String, Object> reviewDeleteAjax(@ModelAttribute RequestReviewDto requestReviewDto){
        Map<String, Object> rtnMap = new HashMap<String,Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        //로그인 여부
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())){

            String phone = requestReviewDto.getPhone();
            String name = requestReviewDto.getName();

            // ============ STEP START ============
            // 1. 최소 스텝 수 체크
            if(certService.getStepCnt() < 2 ){
                throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
            }

            // 2. 최종 데이터 체크: 스텝종료여부, 이름, 핸드폰번호, 계약번호
            String[] certKey= {"urlType", "stepEndYn", "name", "mobileNo", "contractNum"};
            String[] certValue= {"deleteReview", "Y", name, phone, requestReviewDto.getContractNum()};

            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
            }
            // ============ STEP END ============

             // 1.인증 받고 넘어왔는지 확인
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setCntrMobileNo(phone);
            userCntrMngDto.setSubLinkName(name);

            String contNum = null;
            McpUserCntrMngDto dto = mypageService.selectCntrListNoLogin(userCntrMngDto);
            if(Optional.ofNullable(dto).isPresent()) {
                contNum = dto.getContractNum();
            }

            if(contNum==null ){
                rtnMap.put("RESULT_CODE", "0002");
                return rtnMap;
            }

            String contractNum = requestReviewDto.getContractNum();
            if(!contNum.equals(contractNum)){
                rtnMap.put("RESULT_CODE", "0003");
                return rtnMap;
            }

            List<String> contractNumList = new ArrayList<String>();
            contractNumList.add(contractNum);
            requestReviewDto.setContractNumList(contractNumList);
            List<RequestReviewDto> requestReviewDtoList = requestReviewService.selectRequestReviewList(requestReviewDto, 0, 99999);
            int requestKey = 0;
            if(requestReviewDtoList !=null && !requestReviewDtoList.isEmpty()){
                for(int i = 0; i < requestReviewDtoList.size(); i++) {
                    if(requestReviewDto.getRequestKey() == requestReviewDtoList.get(i).getRequestKey()) {
                         requestKey = requestReviewDtoList.get(i).getRequestKey();
                         break;
                    }
                }
            }
            if (requestKey < 1) {
                rtnMap.put("RESULT_CODE", "0002");
                return rtnMap;
            }

            //requestReviewDto.setRequestKey(requestKey);
            if (requestReviewService.deleteMcpRequestReview(requestReviewDto) ) {
                if(requestReviewService.deleteAnswerReview(requestReviewDto)) {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("RESULT_DESC", "성공");
                }else {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("RESULT_DESC", "성공");
                }

            } else {
                rtnMap.put("RESULT_CODE", "9999");
                rtnMap.put("RESULT_DESC", "DB처리 오류");
            }
        }else {
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            RequestReviewDto dto = requestReviewService.selectRequestReview(requestReviewDto);
            if(Optional.ofNullable(cntrList).isPresent()) {
                for(int i = 0; i < cntrList.size(); i ++) {
                    if(dto.getContractNum().equals(cntrList.get(i).getContractNum())) {
                        requestReviewService.deleteMcpRequestReview(requestReviewDto);
                        requestReviewService.deleteMcpRequestReviewImg(requestReviewDto);
                        if(requestReviewService.deleteAnswerReview(requestReviewDto)) {
                            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                        }else {
                            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                        }
                        break;
                    }else {
                        rtnMap.put("RESULT_CODE", "00002");
                    }
                }
            }
        }
        return rtnMap;
    }

    /**
     * 설명 : 사용 후기 sms 인증 팝업 호출Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @param menuType
     * @param popType
     * @param buttonType
     * @return
     */
    @RequestMapping(value = {"/requestReview/requestReviewCertifyPop.do", "/m/requestReview/requestReviewCertifyPop.do"})
    public String requestReviewCertifyPop(HttpServletRequest request, Model model,
            @RequestParam(value="menuType", required=false) String menuType,
             @RequestParam(value="popType", required=false) String popType,
             @RequestParam(value="buttonType", required=true) String buttonType) {
        String returnUrl = "portal/requestReview/requestReviewCertifyPop";

        if("Y".equals(NmcpServiceUtils.isMobile())){
            returnUrl = "mobile/requestReview/requestReviewCertifyPop";
        }
        model.addAttribute("menuType", menuType);
        model.addAttribute("popType", popType);
        model.addAttribute("buttonType", buttonType);
        return returnUrl;
    }


    /**
     * 설명 : 사용 후기 sms 인증번호 확인Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param certifySms
     * @param phone
     * @return
     */
    @RequestMapping(value = "/requestReView/userSmsCheckAjax.do")
    @ResponseBody
    public Map<String, Object> userSmsCheckAjax(HttpServletRequest request
            ,@RequestParam(value = "certifySms", required = false) String certifySms
            ,@RequestParam(value = "phone", required = false) String phone
            ){

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if ( StringUtils.isBlank(certifySms) || StringUtils.isBlank(phone) ) {
            rtnMap.put("RESULT_CODE", "00001");
            return rtnMap;
        }


        AuthSmsDto tmp = new AuthSmsDto();
        tmp.setPhoneNum(phone.toString());
        tmp.setAuthNum(certifySms);
        tmp.setMenu("reviewReg");
        SessionUtils.checkAuthSmsSession(tmp);

        if (tmp.isResult()) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        } else {
            rtnMap.put("RESULT_CODE", "00002");
            rtnMap.put("MSG",tmp.getMessage());
        }

        return rtnMap;
    }

    /**
     * 설명 : 사용 후기 작성하기 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param model
     * @return
     */
    @RequestMapping(value = {"/requestReView/requestReviewForm.do", "/m/requestReView/requestReviewForm.do"})
    public String requestReviewForm(Model model,HttpServletRequest request) {
        String returnUrl = "portal/requestReview/requestReviewForm";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "mobile/requestReview/requestReviewForm";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        //로그인 여부, 내 후기만 보기 설정
        if(Optional.ofNullable(userSession).isPresent() && Optional.ofNullable(userSession.getUserId()).isPresent()){
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            model.addAttribute("cntrList", cntrList);
        }

        // 이벤트 코드
        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.REVIEW_EVENT_CD);
        nmcpMainCdDtlDto.setExpnsnStrVal1("Y");
        List<NmcpCdDtlDto> nmcpMainCdDtlDtoList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

        FormDtlDTO formDtlDTO = new FormDtlDTO();
        formDtlDTO.setCdGroupId1("INFOPRMT");
        formDtlDTO.setCdGroupId2("INSTRUCTION001");
        FormDtlDTO eventHolder = formDtlSvc.FormDtlView(formDtlDTO);


        FormDtlDTO formDtlDTO2 = new FormDtlDTO();
        formDtlDTO2.setCdGroupId1("FORMSELECT");
        formDtlDTO2.setCdGroupId2("05");
        FormDtlDTO formSelect = formDtlSvc.FormDtlView(formDtlDTO2);


        RequestReviewDto requestReviewDto = new RequestReviewDto();
        List<RequestReviewDto> makeReviewList = requestReviewService.reviewMakeList(requestReviewDto);

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

        model.addAttribute("eventList", nmcpMainCdDtlDtoList);
        model.addAttribute("menuType", "reviewReg");
        model.addAttribute("eventHolder", eventHolder);
        model.addAttribute("formSelect", formSelect);
        model.addAttribute("makeReviewList", makeReviewList);

        return returnUrl;
    }


    /**
     * 설명 : 사용 후기 등록하기 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param mcpRequestReviewImgDto
     * @param requestReviewDto
     * @param isLogin
     * @return
     */
    @RequestMapping(value = {"/requestReView/reviewRegAjax.do", "/m/requestReView/reviewRegAjax.do"})
    @ResponseBody
    public Map<String, Object> reviewRegAjax(HttpServletRequest request	,
            @ModelAttribute McpRequestReviewImgDto mcpRequestReviewImgDto,
            @ModelAttribute RequestReviewDto requestReviewDto,
            @RequestParam(value = "isLogin", required = false) String isLogin){

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 로그인 회원일시 계약번호로 전화번호 가져오기
        if (!StringUtils.isBlank(requestReviewDto.getContractNum())) {
            McpUserCntrMngDto cntrMngDto = mypageService.getCntrInfoByContNum(requestReviewDto.getContractNum());
            if (cntrMngDto != null) {
                requestReviewDto.setPhone(cntrMngDto.getCntrMobileNo());
            } else {
                rtnMap.put("RESULT_CODE", "0004");
                return rtnMap;
            }
        }

        // 1.인증 받고 넘어왔는지 확인
        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setMenu("reviewReg");
        authSmsDto = SessionUtils.getAuthSmsBean(authSmsDto);

        String phone = requestReviewDto.getPhone();
        String name = requestReviewDto.getName();
        if(authSmsDto==null){
            rtnMap.put("RESULT_CODE", "0001");
            return rtnMap;
        }

        boolean result = authSmsDto.isResult();
        String smsPhone = StringUtil.NVL(authSmsDto.getPhoneNum(), "");
        if(!result || (!smsPhone.equals(phone))){
            rtnMap.put("RESULT_CODE", "0001");
            return rtnMap;
        }

        // m모바일 회선 조회
        String contractNum = mypageUserService.selectContractNum(name,phone,"","");
        if(contractNum==null ){
            rtnMap.put("RESULT_CODE", "0003");
            return rtnMap;
        }


        // 사용후기 질문 답변 정보 insert
        String[] deQuestions = request.getParameterValues("deQuestions");

        if (deQuestions == null || deQuestions.length == 0 || (deQuestions.length == 1 && deQuestions[0].isEmpty())) {
            rtnMap.put("RESULT_CODE", "0008");
            return rtnMap;
        }

        // 정회원 여부 확인
        String reUserId = requestReviewService.selectUserInfo(contractNum);
        if(reUserId == null){
            rtnMap.put("RESULT_CODE", "0009");
            return rtnMap;
        }


        //============ STEP START ============
        // 1. 최소 스텝 수 확인
        if(certService.getStepCnt() < 2 ){
            throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
        }

        // 2. 최종 데이터 확인: 스텝종료여부, 이름, 핸드폰번호, 계약번호
        String[] certKey = {"urlType", "stepEndYn", "name", "mobileNo", "contractNum"};
        String[] certValue = {"regReview", "Y", name, phone, contractNum};
        Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))){
            throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
        }
        //============ STEP END ============

     // 정지회선 여부 확인
        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setCntrMobileNo(phone);
        userCntrMngDto.setSvcCntrNo(contractNum);
         McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectCntrListNoLogin(userCntrMngDto);
         if (mcpUserCntrMngDto != null) {
            if (contractNum.equals(mcpUserCntrMngDto.getContractNum())) {
                if("S".equals(mcpUserCntrMngDto.getSubStatus())) {//일시정지
                    rtnMap.put("RESULT_CODE", "0006");
                    return rtnMap;
                }
            }
         }

        //로그인한 고객의 회선번호와 체크
        if(Optional.ofNullable(isLogin).isPresent() && isLogin.equals("Y")) {
            boolean isChk = false;
            UserSessionDto userSession = SessionUtils.getUserCookieBean();

            if(userSession==null || StringUtils.isEmpty(userSession.getUserId())){
                rtnMap.put("RESULT_CODE", "0005");
                return rtnMap;
            }
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId()); // 3개
            if(Optional.ofNullable(cntrList).isPresent()) {
                for(McpUserCntrMngDto dto : cntrList){
                    logger.debug("svcCntrNo?{}", dto.getSvcCntrNo());
                    if(contractNum.equals(dto.getSvcCntrNo())){
                        isChk = true;
                        break;
                    }
                }
            }

            if(!isChk){
                rtnMap.put("RESULT_CODE", "0005");
                return rtnMap;
            }
        }

        //M프리퀀시로 인한 사용후기 재작성 수정 요청의 건(90일이내 재작성 조건 해제), 추후 원복은 미정
        /*
        requestReviewDto.setContractNum(contractNum);
        requestReviewDto.setMustVal("mustVal");
        int total = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto);
        if(total > 0) {
            rtnMap.put("RESULT_CODE", "0007");
            return rtnMap;
        }*/

        // 2. 파일 형식이랑 용량
        boolean exe = false;
        int cnt = 0;
        if(Optional.ofNullable(mcpRequestReviewImgDto.getFile()).isPresent()) {
            MultipartFile[] arrFile = mcpRequestReviewImgDto.getFile();
            if(arrFile !=null && arrFile.length > 0){
                for(int i=0; i<arrFile.length; i++){
                    MultipartFile file = arrFile[i];
                    long maxSize = 2*1024*1024;
                    long size = file.getSize(); // 1*1024*1024 ==> 1MB
                    String fileNm = file.getOriginalFilename();

                    // A. 확장자체크
                    String filenameExt = fileNm.substring(fileNm.lastIndexOf(".")+1);
                    filenameExt = filenameExt.toLowerCase();
                    String[] allowFile = {"jpg","jpeg","png","bmp","gif"};
                    for(int j=0; j<allowFile.length; j++) {
                        if(filenameExt.equals(allowFile[j])){
                            exe = true;
                            break;
                        }
                    }
                    if(!exe){
                        cnt++;
                    }

                    // B. 용량체크
                    if(size > maxSize){
                        cnt++;
                    }
                }

                // 확장자 및 용량 에러
                if( cnt > 0 ){
                    rtnMap.put("RESULT_CODE", "0002");
                    return rtnMap;
                }
            }
        }


        int requestKey = 0;
        int reviewId = 0;
        try{

            RequestReviewDto mcpRequestData = requestReviewService.selectMcpRequestData(contractNum);

            if( mcpRequestData == null ){
                rtnMap.put("RESULT_CODE", "0003");
                return rtnMap;
            }else {

                // 등록
                RequestReviewDto reviewDto = new RequestReviewDto();

                String reqBuyType = "";
                String modelId = "";
                String prodId = "";

                requestKey = mcpRequestData.getRequestKey();
                reqBuyType = mcpRequestData.getReqBuyType();
                modelId = mcpRequestData.getModelId();
                prodId = mcpRequestData.getProdId();

                String reviewSbst = requestReviewDto.getReviewSbst();
                reviewSbst = ParseHtmlTagUtil.parseTag(filterText(reviewSbst));
                String commendYn = requestReviewDto.getCommendYn();
                String eventCd = requestReviewDto.getEventCd();
                String snsInfo = requestReviewDto.getSnsInfo();
                snsInfo = ParseHtmlTagUtil.parseTag(snsInfo);

                reviewDto.setRequestKey(requestKey);
                reviewDto.setContractNum(contractNum);
                reviewDto.setReqBuyType(reqBuyType);
                reviewDto.setModelId(modelId);
                reviewDto.setProdId(prodId);
                reviewDto.setReviewSbst(reviewSbst);
                reviewDto.setCommendYn(commendYn);
                reviewDto.setEventCd(eventCd);
                reviewDto.setSnsInfo(snsInfo);
                reviewDto.setNtfYn("0");
                reviewDto.setPrizeSbst("");
                reviewDto.setSttusVal("1");
                reviewDto.setrIP(ipstatisticService.getClientIp());
                reviewDto.setRegNm(name);

                requestReviewDto.setRequestKey(requestKey);
                // insert
                int resultCnt = requestReviewService.insertMcpRequestReview(reviewDto);
                List<RequestReviewDto> reviewIdList = requestReviewService.reviewIdList(requestReviewDto);
                reviewId = reviewIdList.get(0).getReviewId();

                String[] splitData = deQuestions[0].split("[/,]");
                Map<String, List<String>> map = new HashMap<>();

                for (int i = 0; i < splitData.length; i++) {
                    if (i % 2 == 0) {
                        String key = splitData[i];
                        String value = (i + 1 < splitData.length) ? splitData[i + 1] : null;
                        if (value != null) {
                            String[] values = value.split(",");
                            for (String val : values) {
                                map.computeIfAbsent(key, k -> new ArrayList<>()).add(val);
                            }
                        }
                    }
                }

                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    requestReviewDto.setRequestKey(requestKey);
                    requestReviewDto.setQuestionId(Integer.parseInt(entry.getKey()));
                    List<String> values = entry.getValue();
                    for (String val : values) {
                        requestReviewDto.setAnswerId(Integer.parseInt(val));
                        requestReviewDto.setCretId(reUserId);
                        requestReviewDto.setAmdId(reUserId);
                        requestReviewDto.setrIP(ipstatisticService.getClientIp());
                        requestReviewDto.setReviewId(reviewId);

                        requestReviewService.insertReviewMake(requestReviewDto);
                    }
                }

            }
        }catch(Exception e){
            rtnMap.put("RESULT_CODE", "0004");
            return rtnMap;
        }

        if(Optional.ofNullable(mcpRequestReviewImgDto.getFile()).isPresent()) {
            // 이미지 업로드 실패로 게시물 등록을 못하게 처리
            try{

                // 3.파일업로드

                FileBoardDTO fileBoardDTO = null;
                MultipartFile[] file = mcpRequestReviewImgDto.getFile();
                String[] fileAltList = null;
                if(Optional.ofNullable(mcpRequestReviewImgDto.getFileAlt()).isPresent()) {
                    fileAltList = mcpRequestReviewImgDto.getFileAlt().split(",");
                }
                String fileDir = "review";

                if(file !=null){
                    McpRequestReviewImgDto requestReviewImgDto = null;
                    for(int i=0; i<file.length; i++){
                        FileUploadUtil fileUploadUtill = new FileUploadUtil(fileuploadBase, fileuploadWeb);
                        requestReviewImgDto = new McpRequestReviewImgDto();
                        MultipartFile multipartFile = file[i];

                        if (multipartFile !=null && !"".equals(multipartFile.getOriginalFilename()) && Optional.ofNullable(multipartFile).isPresent()) { // 파일유효성 체크
                            fileBoardDTO = new FileBoardDTO();
                            fileBoardDTO = fileUploadUtill.upload(multipartFile, request, fileDir); // 파일업로드
                            String fileNm = multipartFile.getOriginalFilename();
                            String filenameExt = fileNm.substring(fileNm.lastIndexOf(".")+1);
                            String filePathNm = fileBoardDTO.getFilePathNM();
                            if(fileAltList.length > 0) {
                                if(!"".equals(fileAltList[i])) {
                                    String fileAlt = fileAltList[i];
                                    requestReviewImgDto.setFileAlt(fileAlt);
                                }
                            }
                            String fileCapa = StringUtil.NVL(fileBoardDTO.getFileCapa(),"0");
                            int intFileCapa = Integer.parseInt(fileCapa);

                            requestReviewImgDto.setRequestKey(requestKey);
                            requestReviewImgDto.setImdSeq(i);
                            requestReviewImgDto.setFilePathNm(filePathNm);
                            requestReviewImgDto.setFileType(filenameExt);
                            requestReviewImgDto.setFileCapa(intFileCapa);
                            requestReviewImgDto.setReviewId(reviewId);

                            // insert 끝
                            int resultCnt = requestReviewService.insertMcpRequestReviewImg(requestReviewImgDto);
                        }
                    }
                }

            }catch(Exception e){
                logger.error("exception e=>"+e.getMessage());
            }
        }
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }





    /**
     * 유심 > 후불유심 > 개통후기
     */
    @RequestMapping(value = {"/review/getRequestReviewDataListAjax.do"})
    public String getUsimRequestReviewDataListAjax(@ModelAttribute RequestReviewDto requestReviewDto, PageInfoBean pageInfoBean,Model model,HttpServletRequest request) {

        String returnUrl = "portal/requestReview/requestReviewHtml";

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(10);

        try{

            /*카운터 조회*/
            int total = requestReviewService.selectUsimRequestReviewTotalCnt(requestReviewDto);
            pageInfoBean.setTotalCount(total);

            int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
            int maxResult = pageInfoBean.getRecordCount();	// Pagesize

            List<RequestReviewDto> requestReviewDtoList = requestReviewService.selectUsimRequestReviewList(requestReviewDto,skipResult,maxResult);

            // %퍼이상일때만 노출
            String strLimitPer = StringUtil.NVL(NmcpServiceUtils.getCodeNm("Constant",Constants.REVIEW_PERCENT),"0");
            int limitPer = Integer.parseInt(strLimitPer);

            RequestReviewDto reqDto = new RequestReviewDto();
            reqDto.setLimitPer(limitPer);
            reqDto.setReqBuyType(requestReviewDto.getReqBuyType());

            // 보여질지 말지 결정
            RequestReviewDto resDto = requestReviewService.selectReviewLimit(reqDto);
            model.addAttribute("resDto", resDto);
            model.addAttribute("requestReviewDtoList", requestReviewDtoList);
            model.addAttribute("pageInfoBean", pageInfoBean);

        }catch(Exception e){
            logger.error(e.toString());
        }


        return returnUrl;
    }

    /**
     * M > phone/usim 하단 상세보기
     */
    @RequestMapping(value = {"/m/review/getRequestReviewDataListAjax.do"})
    public String getMUsimRequestReviewDataListAjax(@ModelAttribute RequestReviewDto requestReviewDto, PageInfoBean pageInfoBean,Model model,HttpServletRequest request) {

        String returnUrl = "mobile/requestReview/requestReviewHtml";

        try{

            String reqBuyType = requestReviewDto.getReqBuyType();
            RequestReviewDto reqDto = new RequestReviewDto();
            reqDto.setReqBuyType(reqBuyType);
            // 보여질지 말지 결정
            RequestReviewDto resDto = requestReviewService.selectReviewLimit(reqDto);

            model.addAttribute("resDto", resDto);
            model.addAttribute("reqBuyType", reqBuyType);

        }catch(Exception e){
            logger.debug("error");
        }

        return returnUrl;
    }



    /**
     * 설명 : 사용후기 review 차트 Ajax
     */
    @RequestMapping(value = {"/requestReView/reviewAnswerAjax.do", "/m/requestReView/reviewAnswerAjax.do"})
    @ResponseBody
    public Map<String, Object> reviewAnswerAjax(@ModelAttribute RequestReviewDto requestReviewDto){
        Map<String, Object> rtnMap = new HashMap<String,Object>();

        //사용후기 차트 값
        List<RequestReviewDto> reviewAnswerList = requestReviewService.reviewAnswerList(requestReviewDto);

        if (reviewAnswerList == null) {
            rtnMap.put("RESULT_CODE", "9999");
            return rtnMap;
        }else {
             rtnMap.put("RESULT_CODE", "00000");
             rtnMap.put("reviewAnswerList",reviewAnswerList);
        }

        return rtnMap;
    }


    /**
     * 설명 : 우수 사용 후기 리스트 호출Ajax
     */
    @RequestMapping(value = {"/m/requestReView/bestReviewAjax.do", "/requestReView/bestReviewAjax.do"})
    @ResponseBody
    public Map<String,Object> bestReviewAjax(@ModelAttribute RequestReviewDto requestReviewDto) {

        HashMap<String,Object> map = new HashMap<String, Object>();

        List<RequestReviewDto> requestReviewDtoList = requestReviewService.selectBestReviewList(requestReviewDto);

        for(RequestReviewDto dto : requestReviewDtoList){
            dto.setRegNm("");
        }

        map.put("requestReviewDtoList", requestReviewDtoList);

        return map;
    }


    private String filterText(String sText){

        StringBuffer str = new StringBuffer();
        str.append("10새|10새기|10새리|10세리|10쉐이|10쉑|10스|10쌔| 10쌔기|10쎄|10알|10창|10탱|18것|18넘|18년|18노|18놈|18뇬|18럼|18롬|18새|18새끼|18색|18세끼|18세리|18섹|18쉑|18스|18아|")
        .append("ㄱㅐ|ㄲㅏ|ㄲㅑ|ㄲㅣ|ㅅㅂㄹㅁ|ㅅㅐ|ㅆㅂㄹㅁ|ㅆㅍ|ㅆㅣ|ㅆ앙|ㅍㅏ|凸| 갈보|갈보년|강아지|같은년|같은뇬|개같은|개구라|개년|개놈|개뇬|개대중|개독|개돼중|개랄|개보지|개뻥|개뿔|개새|개새기|개새끼|")
        .append("개새키|개색기|개색끼|개색키|개색히|개섀끼|개세|개세끼|개세이|개소리|개쑈| 개쇳기|개수작|개쉐|개쉐리|개쉐이|개쉑|개쉽|개스끼|개시키|개십새기| 개십새끼|개쐑|개씹|개아들|개자슥|개자지|개접|개좆|개좌식|")
        .append("개허접|걔새|걔수작|걔시끼|걔시키|걔썌|걸레|게색기|게색끼|광뇬|구녕|구라|구멍|그년|그새끼|냄비|놈현|뇬|눈깔|뉘미럴|니귀미|니기미|니미|니미랄|니미럴|니미씹|니아배|니아베|니아비|니어매|니어메|")
        .append("니어미|닝기리|닝기미|대가리|뎡신|도라이|돈놈|돌아이|돌은놈|되질래|뒈져|뒈져라|뒈진|뒈진다|뒈질| 뒤질래|등신|디져라|디진다|디질래|딩시|따식|때놈|또라이|똘아이|똘아이|뙈놈|뙤놈|뙨넘|뙨놈|")
        .append("뚜쟁|띠바|띠발|띠불|띠팔|메친넘|메친놈|미췬| 미췬|미친|미친넘|미친년|미친놈|미친새끼|미친스까이|미틴|미틴넘|미틴년| 미틴놈|바랄년|병자|뱅마|뱅신|벼엉신|병쉰|병신|부랄|부럴|불알|불할|붕가|붙어먹|")
        .append("뷰웅|븅|븅신|빌어먹|빙시|빙신|빠가|빠구리|빠굴|빠큐|뻐큐|뻑큐|뽁큐|상넘이|상놈을|상놈의|상놈이|새갸|새꺄|새끼|새새끼|새키|색끼|생쑈|세갸|세꺄|세끼|섹스|쇼하네|쉐|쉐기|쉐끼|쉐리|쉐에기|쉐키|")
        .append("쉑|쉣|쉨|쉬발|쉬밸|쉬벌|쉬뻘|쉬펄|쉽알|스패킹|스팽|시궁창|시끼|시댕|시뎅|시랄|시발|시벌|시부랄|시부럴|시부리|시불|시브랄|시팍|시팔|시펄|신발끈|심발끈|심탱|십8|십라|십새|십새끼|십세|")
        .append("십쉐|십쉐이|십스키|십쌔|십창|십탱|싶알|싸가지|싹아지|쌉년|쌍넘|쌍년|쌍놈|쌍뇬|쌔끼| 쌕|쌩쑈|쌴년|썅|썅년|썅놈|썡쇼|써벌|썩을년|썩을놈|쎄꺄|쎄엑|쒸벌|쒸뻘|쒸팔|쒸펄|쓰바|쓰박|쓰발|쓰벌|")
        .append("쓰팔|씁새|씁얼|씌파|씨8|씨끼|씨댕|씨뎅|씨바|씨바랄|씨박|씨발|씨방|씨방새|씨방세|씨밸|씨뱅|씨벌|씨벨|씨봉|씨봉알|씨부랄|씨부럴|씨부렁|씨부리|씨불|씨붕|씨브랄| 씨빠|씨빨|씨뽀랄|씨앙|씨파|씨팍|")
        .append("씨팔|씨펄|씸년|씸뇬|씸새끼|씹같|씹년|씹뇬|씹보지|씹새|씹새기|씹새끼|씹새리|씹세|씹쉐|씹스키|씹쌔|씹이|씹자지|씹질|씹창|씹탱|씹퇭|씹팔|씹할|씹헐|아가리|아갈|아갈이|아갈통|아구창|아구통|아굴|얌마|")
        .append("양넘|양년|양놈|엄창|엠병|여물통|염병|엿같|옘병|옘빙|오입|왜년|왜놈|욤병|육갑|은년|을년|이년|이새끼|이새키|이스끼|이스키|임마|자슥|잡것|잡넘|잡년|잡놈|저년|저새끼|접년|젖밥|조까|조까치|조낸|조또|")
        .append("조랭|조빠|조쟁이|조지냐|조진다|조찐|조질래|존나|존나게|존니|존만|존만한|좀물|좁년|좆|좁밥|좃까|좃또|좃만|좃밥|좃이|좃찐|좆같|좆까|좆나|좆또|좆만|좆밥|좆이|좆찐|좇같|좇이|좌식|주글|주글래|주데이|")
        .append("주뎅|주뎅이|주둥아리|주둥이|주접|주접떨|죽고잡|죽을래|죽통|쥐랄|쥐롤|쥬디|지랄|지럴|지롤|지미랄|짜식|짜아식|쪼다|쫍빱|찌랄|창녀|캐년|캐놈|캐스끼|캐스키|캐시키|탱구|팔럼|퍽큐|호로|호로놈|호로새끼|")
        .append("호로색|호로쉑|호로스까이|호로스키|후라들|후래자식|후레|후뢰|씨ㅋ발|ㅆ1발|씌발|띠발|띄발|뛰발|띠ㅋ발|뉘뮈");

        String strString = str.toString();
        Pattern p = Pattern.compile(strString, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sText);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, this.maskWord(m.group()));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String maskWord(String word) {
        StringBuffer buff = new StringBuffer();
        char[] ch = word.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            buff.append("*");
        }
        return buff.toString();
    }


}
