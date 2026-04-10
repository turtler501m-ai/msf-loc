package com.ktmmobile.mcp.event.controller;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;
import com.ktmmobile.mcp.appform.service.FormDtlSvc;
import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.*;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.dto.db.NmcpEventBoardBasDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mplatform.vo.UserVO;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.event.dto.*;
import com.ktmmobile.mcp.event.service.CoEventSvc;
import com.ktmmobile.mcp.event.service.EventJoinService;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageUserService;
import com.ktmmobile.mcp.point.dto.CustPointGiveTgtBasDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.service.PointService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.constants.Constants.EVENT_SBST_CTG_EVENT;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class CoEventController {

    private static Logger logger = LoggerFactory.getLogger(CoEventController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    CoEventSvc coEventSvc;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private MypageUserService mypageUserService;

    @Autowired
    private PointService pointSvc;

    @Autowired
    SmsSvc smsSvc ;

    @Autowired
    CertService certService;

    @Autowired
    private EventJoinService eventJoinService;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    FormDtlSvc formDtlSvc;

    public static final String COMM_AUTH_SMS_INFO = "COMM_AUTH_SMS_INFO";

    @RequestMapping(value = {"/event/hotList.do" , "/m/event/hotList.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String hotListSelect(HttpServletRequest request, @ModelAttribute EventBoardDto eventBoardDto, Model model, BindingResult bind) {

        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            return "redirect:/m/event/eventBoardList.do?sbstCtg=E";
        } else {
            return "redirect:/event/eventBoardList.do?sbstCtg=E";
        }
    }

    // pc/모바일 이번달/제휴 이벤트 리스트
    @RequestMapping(value = {"/event/eventBoardList.do", "/event/cprtEventBoardList.do",
                             "/m/event/eventList.do", "/m/event/eventBoardList.do", "/m/event/cprtEventBoardList.do"}
                   ,method = {RequestMethod.GET, RequestMethod.POST})
    public String eventList(@ModelAttribute NmcpEventBoardBasDto nmcpEventBoardBasDto
            , HttpServletRequest request
            , PageInfoBean pageInfoBean
            , Model model
            , BindingResult bind) {

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }
        if (StringUtils.isBlank(nmcpEventBoardBasDto.getSbstCtg())) {
            nmcpEventBoardBasDto.setSbstCtg(Constants.EVENT_SBST_CTG_JEHU);
        }

        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.Event_Category);
        List<NmcpCdDtlDto> eventCategoryList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

        //이벤트 분기 - E: 이번달이벤트, J: 제휴이벤트
        String[] eventBranch = {"E","J"};
        String returnUrl = "";

        //이번달 이벤트
        if(request.getRequestURI().equals("/m/event/eventList.do") || request.getRequestURI().equals("/m/event/eventBoardList.do") ||
           request.getRequestURI().equals("/event/eventBoardList.do")){

            if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                returnUrl =  "/mobile/event/eventList";
            }else {
                returnUrl = "/portal/event/event.list";
            }

            model.addAttribute("eventBranch","E");
        }
        //제휴 이벤트
        else if(request.getRequestURI().equals("/m/event/cprtEventBoardList.do") ||
                request.getRequestURI().equals("/event/cprtEventBoardList.do")) {

            if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                returnUrl =  "/mobile/event/eventList";
            }else {
                returnUrl = "/portal/event/event.list";
            }
            model.addAttribute("eventBranch","J");
        }

        StringBuilder sbReturnUrl = new StringBuilder(returnUrl);
        nmcpEventBoardBasDto.setNtcartSbst(ParseHtmlTagUtil.percentToEscape(nmcpEventBoardBasDto.getNtcartSbst()));
        String category = StringUtil.NVL(request.getParameter("category"),"");
        model.addAttribute("category", category);
        model.addAttribute("serverName",serverName);
        model.addAttribute("PageInfoBean", pageInfoBean);
        model.addAttribute("NmcpEventBoardBas", nmcpEventBoardBasDto);
        model.addAttribute("eventCategoryList", eventCategoryList);

        return sbReturnUrl.toString();
    }

    /**
     * <pre>
     * 설명     : 이벤트 리스트
     * @param nmcpEventBoardBasDto
     * @return
     * @return: Map<String,Object>
     * </pre>
     */
    @RequestMapping(value =  "/event/eventBoardListAjax.do")
    @ResponseBody
    public Map<String, Object> eventBoardListAjax(NmcpEventBoardBasDto nmcpEventBoardBasDto
            , PageInfoBean pageInfoBean
            , BindingResult bind){
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (bind.hasErrors()) {
            throw new McpCommonJsonException(BIDING_EXCEPTION);
        }

        if (StringUtils.isBlank(nmcpEventBoardBasDto.getSbstCtg())) {
            throw new McpCommonJsonException(INVALID_PARAMATER_EXCEPTION);
        }

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*카운터 조회*/
        int total = coEventSvc.countByListNmcpEventBoard(nmcpEventBoardBasDto);
        pageInfoBean.setTotalCount(total);
        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize
        List<NmcpEventBoardBasDto> eventList = coEventSvc.listNmcpEventBoard(nmcpEventBoardBasDto, skipResult, maxResult);

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("OBJECT_LIST", eventList);
        rtnMap.put("PAGE_OBJECT", pageInfoBean);

        return rtnMap;
    }

    // pc/모바일 이번달/제휴 종료 이벤트 리스트
    @RequestMapping(value = {"/event/eventBoardEndList.do", "/event/cprtEventBoardEndList.do",
                             "/m/event/eventBoardEndList.do", "/m/event/cprtEventBoardEndList.do"}
                   ,method = {RequestMethod.GET, RequestMethod.POST})
    public String eventBoardListSelect(@ModelAttribute NmcpEventBoardBasDto nmcpEventBoardBasDto
            , HttpServletRequest request
            , PageInfoBean pageInfoBean
            , Model model) {

        //이벤트 분기 - E: 이번달이벤트, J: 제휴이벤트
        String[] eventBranch = {"E","J"};
        String returnUrl = "/portal/event/eventBoardEndList";

        //이번달 이벤트
        if(request.getRequestURI().equals("/m/event/eventBoardEndList.do") ||
           request.getRequestURI().equals("/event/eventBoardEndList.do")) {

            if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                returnUrl =  "/mobile/event/eventBoardEndList";
            }else {
                returnUrl = "/portal/event/eventBoardEndList";
            }

            model.addAttribute("eventBranch","E");
        }
        //제휴 이벤트
        else if(request.getRequestURI().equals("/m/event/cprtEventBoardEndList.do") ||
                request.getRequestURI().equals("/event/cprtEventBoardEndList.do")) {

            if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                returnUrl =  "/mobile/event/eventBoardEndList";
            }else {
                returnUrl = "/portal/event/eventBoardEndList";
            }
            model.addAttribute("eventBranch","J");
        }

        StringBuilder sbReturnUrl = new StringBuilder(returnUrl);
        model.addAttribute("serverName",serverName);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("NmcpEventBoardBas", nmcpEventBoardBasDto);

        return sbReturnUrl.toString();
    }

    //모바일/제휴 이번달/종료 이벤트 목록보여주기
    @RequestMapping(value={"/m/event/eventListAjax.do", "/event/eventListAjax.do"},
                    method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> eventListAjax(HttpServletRequest request,
            @ModelAttribute EventBoardDto eventBoardDto,
            PageInfoBean pageInfoBean, Model model, BindingResult bind) {

        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*카운터 조회*/
        int total = 0;
        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); //셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize
        List<EventBoardDto> eventList;
        Map<String,Object> resultData = new HashMap<String,Object>();

        //이번달 이벤트
        if("E".equals(eventBoardDto.getEventBranch())) {
            if("ing".equals(eventBoardDto.getEventStatus())) {
                // 이벤트 진행중 카운터
                total = coEventSvc.countPlanListSelect(eventBoardDto);

                // 이벤트 진행중 리스트
                eventList = coEventSvc.planListSelect(eventBoardDto,skipResult);
                resultData.put("eventList", eventList);
                resultData.put("pageInfoBean", pageInfoBean);
            }

            else if("end".equals(eventBoardDto.getEventStatus())) {
                // 이벤트 종료 카운터
                total = coEventSvc.countEventListSelect(eventBoardDto);

                // 이벤트 종료 리스트
                eventList = coEventSvc.eventListSelect(eventBoardDto, skipResult, maxResult);
                resultData.put("eventList", eventList);
                resultData.put("pageInfoBean", pageInfoBean);
            }
        }
        //제휴 이벤트
        else if("J".equals(eventBoardDto.getEventBranch())) {

            if("ing".equals(eventBoardDto.getEventStatus())) {
                // 이벤트 진행중 카운터
                total = coEventSvc.countPlanListSelect(eventBoardDto);

                // 이벤트 진행중 리스트
                eventList = coEventSvc.planListSelect(eventBoardDto,skipResult);
                resultData.put("eventList", eventList);
                resultData.put("pageInfoBean", pageInfoBean);
            }
            else if("end".equals(eventBoardDto.getEventStatus())) {
                // 이벤트 종료 카운터
                total = coEventSvc.countEventListSelect(eventBoardDto);

                // 이벤트 종료 리스트
                eventList = coEventSvc.eventListSelect(eventBoardDto, skipResult, maxResult);
                resultData.put("eventList", eventList);
                resultData.put("pageInfoBean", pageInfoBean);
            }
        }
        pageInfoBean.setTotalCount(total);

        return resultData;
    }

    //당첨자 리스트
    @RequestMapping(value = {"/event/winnerList.do", "/event/cprtEventWinnerList.do",
                             "/m/event/winnerList.do","/m/event/cprtEventWinnerList.do"}
                   ,method = {RequestMethod.GET, RequestMethod.POST})
    public String winnerListSelect(@ModelAttribute WinnerBoardDto winnerBoardDto
            , HttpServletRequest request
            , PageInfoBean pageInfoBean
            , Model model
            , BindingResult bind) {

        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        /*한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(10);
        //이벤트 분기 - E: 이번달이벤트, J: 제휴이벤트
        String[] eventBranch = {"E","J"};
        String returnUrl = "/portal/event/winnerList";

        //이번달 이벤트
        if(request.getRequestURI().equals("/m/event/winnerList.do") ||
           request.getRequestURI().equals("/event/winnerList.do")) {

            if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                returnUrl =  "/mobile/event/winnerList";
            }else {
                returnUrl = "/portal/event/winnerList";
            }
            model.addAttribute("eventBranch","E");
        }
        //제휴 이벤트
        else if(request.getRequestURI().equals("/m/event/cprtEventWinnerList.do") ||
                request.getRequestURI().equals("/event/cprtEventWinnerList.do")) {

            if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                returnUrl =  "/mobile/event/winnerList";
            }else {
                returnUrl = "/portal/event/winnerList";
            }
            model.addAttribute("eventBranch","J");
        }

        StringBuilder sbReturnUrl = new StringBuilder(returnUrl);
        model.addAttribute("searchDto", winnerBoardDto);
        model.addAttribute("pageInfoBean", pageInfoBean);

        return sbReturnUrl.toString();
    }

    //당첨자 리스트
    @RequestMapping(value={"/m/event/winnerListAjax.do","/event/winnerListAjax.do"}
                   ,method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> winnerListAjax (HttpServletRequest request
            , @ModelAttribute WinnerBoardDto winnerBoardDto
            , PageInfoBean pageInfoBean
            , Model model
            , BindingResult bind){

        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize
        Map<String,Object> resultData = new HashMap<String,Object>();

        //이번달 이벤트
        if("E".equals(winnerBoardDto.getEventBranch())) {
            /*카운터 조회*/
            int total = coEventSvc.countWinnerListSelect(winnerBoardDto);
            pageInfoBean.setTotalCount(total);
            List<WinnerBoardDto> winnerList = coEventSvc.winnerListSelect(winnerBoardDto, skipResult, maxResult);
            resultData.put("winnerList", winnerList);
            resultData.put("pageInfoBean", pageInfoBean);
        }
        else if("J".equals(winnerBoardDto.getEventBranch())) {
            /*카운터 조회*/
            int total = coEventSvc.countWinnerListSelect(winnerBoardDto);
            pageInfoBean.setTotalCount(total);
            List<WinnerBoardDto> winnerList = coEventSvc.winnerListSelect(winnerBoardDto, skipResult, maxResult);
            resultData.put("winnerList", winnerList);
            resultData.put("pageInfoBean", pageInfoBean);
        }

        winnerBoardDto.setSearchValue(ParseHtmlTagUtil.percentToEscape(winnerBoardDto.getSearchValue()));		// 예약어 %,_ 검색시 Escape처리

        return resultData;
    }

    //제휴/이벤트 상세 pc/mobile
    @RequestMapping(value = {"/m/event/eventDetail.do", "/event/eventDetail.do"}
                   ,method = {RequestMethod.GET, RequestMethod.POST})
    public String eventDetailMSelect(
            HttpServletRequest request
            , @ModelAttribute("eventBoardDto") EventBoardDto eventBoardDto
            , PageInfoBean pageInfoBean
            , Model model){

        String returnUrl = "/portal/event/";

        if("GET".equals(request.getMethod()) || "POST".equals(request.getMethod())) {
//            if(!Optional.ofNullable(eventBoardDto.getNtcartSeq()).isPresent()){
            if (ObjectUtils.isEmpty(eventBoardDto.getNtcartSeq())) {
                throw new McpCommonException("잘못된 접근입니다.",
                        request.getRequestURI().equals("/m/event/eventDetail.do") ? "/m/event/eventList.do" : "/event/eventBoardList.do");
            }
        }

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl =  "/mobile/event/eventDetail";
            eventBoardDto.setProModule("m");
        }else {
            returnUrl = "/portal/event/eventDetail";
        }

        EventBoardDto eventDto = coEventSvc.eventDetailSelect(eventBoardDto);             //이벤트 기본정보테이블

        model.addAttribute("searchDto", eventBoardDto);
        model.addAttribute("eventDto", eventDto);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("eventBranch",eventBoardDto.getEventBranch());

        //이벤트 플로팅 배너
        model.addAttribute("bannerFloatInfo", NmcpServiceUtils.getBannerFloatList(eventBoardDto.getNtcartSeq()));

        // 이벤트 카테고리 자동 판단
        String listViewYn = "";
        if(eventDto != null) {
            listViewYn = eventDto.getListViewYn();
            eventBoardDto.setEventBranch(eventDto.getSbstCtg());
            model.addAttribute("sbstCtg",eventDto.getSbstCtg());
            model.addAttribute("eventBranch",eventDto.getSbstCtg());
        }

        StringBuilder sbReturnUrl = new StringBuilder(returnUrl);
        if ("I".equals(listViewYn)) {
            sbReturnUrl.append(".nonHeader");
         }

        //이벤트 공유하기 링크
        String recommend = request.getParameter("recommend");

        if(recommend != null && !recommend.equals(SessionUtils.getFriendInvitation())) { //중복방지
            SessionUtils.saveFriendInvitation(recommend);  // 친구초대 ID값 세션저장
            //이력 정보 저장 처리(친구초대 링크 타고 접속 한경우)
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("INVITATION");
            mcpIpStatisticDto.setPrcsSbst("INIT");
            mcpIpStatisticDto.setParameter(recommend);
            mcpIpStatisticDto.setTrtmRsltSmst("");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        }

        return sbReturnUrl.toString();

    }

    //당첨자 상세
    @RequestMapping(value = {"/event/winnerDetail.do" , "/m/event/winnerDetail.do"
                           , "/event/cprtEventWinnerDetail.do" , "/m/event/cprtEventWinnerDetail.do"}
                   ,method = {RequestMethod.GET, RequestMethod.POST})
    public String winnerDetailSelect(HttpServletRequest request,
             @ModelAttribute WinnerBoardDto winnerBoardDto,
             PageInfoBean pageInfoBean, Model model) {

        if(request.getMethod().equals("GET") || request.getMethod().equals("POST")) {
            if(Optional.ofNullable(winnerBoardDto.getNtcartSeq()).isPresent()
               && Optional.ofNullable(winnerBoardDto.getPageNo()).isPresent()
               && Optional.ofNullable(winnerBoardDto.getEventBranch()).isPresent()) {
                logger.debug("정상접근");
            }
            else {
                if(request.getRequestURI().equals("/m/event/winnerDetail.do") || request.getRequestURI().equals("/event/winnerDetail.do")) {
                    throw new McpCommonException("잘못된 접근입니다.",
                    request.getRequestURI().equals("/m/event/winnerDetail.do") ? "/m/event/winnerList.do" : "/event/winnerList.do");
                }else {
                    throw new McpCommonException("잘못된 접근입니다.",
                    request.getRequestURI().equals("/m/event/cprtEventWinnerDetail.do") ? "/m/event/cprtEventWinnerList.do" : "/event/cprtEventWinnerList.do");
                }
            }
        }

        String returnUrl = "/portal/event/winnerDetail";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl =  "/mobile/event/winnerDetail";
        }

        WinnerBoardDto winnerDto = coEventSvc.winnerDetailSelect(winnerBoardDto);

        if (winnerDto != null && winnerDto.getPwnrSbst() != null ) {
            winnerDto.setPwnrSbst(ParseHtmlTagUtil.convertHtmlchars(winnerDto.getPwnrSbst()));
        }

        //pageInfoBean.setPageNo(pageNo); // 화면 내 페이징 처리 추가시 필요
        model.addAttribute("winnerDto", winnerDto);
        model.addAttribute("searchDto", winnerBoardDto);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("eventBranch",winnerBoardDto.getEventBranch());

        return returnUrl;
    }

    /**
     * <pre>
     * 설명     : 이벤트/제휴 상세 조회 Ajax
     * @param
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = {"/m/event/eventDetailViewAjax.do", "/event/eventDetailViewAjax.do"})
    public String getEventDetailViewAjax(
            @ModelAttribute("eventBoardDto") EventBoardDto eventBoardDto,
            @ModelAttribute("eventPopTitle") String eventPopTitle,
            @RequestParam("ntcartSeq") int ntcartSeq,
            HttpServletRequest request,
            Model model){

        String returnUrl = "";
        model.addAttribute("eventDetail", coEventSvc.selectEventDetailView(ntcartSeq));
        model.addAttribute("eventPopTitle", eventPopTitle);
        if(request.getRequestURI().equals("/m/event/eventDetailViewAjax.do")){
            returnUrl = "mobile/event/eventDetailPop";
            model.addAttribute("dstnctKey", "mobile");
        }else {
            returnUrl = "portal/event/eventDetailPop";
            model.addAttribute("dstnctKey", "pc");
        }
        return returnUrl;
    }

    //PC 제휴/이벤트 리스트
    @RequestMapping(value = "/event/eventList.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String eventListSelect(HttpServletRequest request,
                                  @ModelAttribute EventBoardDto eventBoardDto,
                                  PageInfoBean pageInfoBean, Model model,
                                  BindingResult bind,
                                  @RequestParam (value = "selectEvt", required=false) String selectEvt) {

        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }
        String returnUrl = "/event/eventList";
        if(request.getRequestURI().equals("/m/event/eventList.do")){
            returnUrl="/mobile/event/eventList";
        }

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        String mapping = request.getServletPath();

        if(mapping != null && !"".equals(mapping)){
            if("/m/event/eventList.do".equals(mapping)){
                pageInfoBean.setRecordCount(10);	//mobile버전
            }else{
                pageInfoBean.setRecordCount(6);		//pc버전
            }
        }else{
        /*한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(6);		//pc버전
        }

        //eventBoardDto.setSearchValue(ParseHtmlTagUtil.percentToEscape(eventBoardDto.getSearchValue()));

        /*카운터 조회*/
        int total = coEventSvc.countEventListSelect(eventBoardDto);

        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        //	List<ComCdDto> comCdList = coEventSvc.eventComCd();

        List<EventBoardDto> eventList = coEventSvc.eventListSelect(eventBoardDto, skipResult, maxResult);

        //eventBoardDto.setSearchValue(ParseHtmlTagUtil.escapeToPercent(eventBoardDto.getSearchValue())); //검색어 있을 경우 사용

        model.addAttribute("total", total);
        model.addAttribute("searchDto", eventBoardDto);
        model.addAttribute("eventList", eventList);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("selectEvt", selectEvt);

        return returnUrl;
    }

    //조회수 증가
    @RequestMapping(value = {"/event/updateHit.do" , "/m/event/updateHit.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> updateHit(
            HttpServletRequest request
            , @ModelAttribute("eventBoardDto") EventBoardDto eventBoardDto
            , @RequestParam ("ntcartSeq") int ntcartSeq
            , PageInfoBean pageInfoBean
            , Model model) {
        eventBoardDto.setNtcartSeq(ntcartSeq);

        coEventSvc.updateHit(eventBoardDto);

        Map<String, Object> rtnJson = new HashMap<String, Object>();

        return rtnJson;
    }

    //제휴 리스트
    @RequestMapping(value = {"/event/jehuList.do" , "/m/event/jehuList.do"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String jehuListSelect(HttpServletRequest request, @ModelAttribute EventBoardDto eventBoardDto, PageInfoBean pageInfoBean, Model model, BindingResult bind, @RequestParam (value = "selectEvt", required=false) String selectEvt) {

        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }
        String returnUrl = "/event/jehuList";
        if(request.getRequestURI().equals("/m/event/jehuList.do")){
            returnUrl="/mobile/event/jehuList";
        }

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        String mapping = request.getServletPath();

        if(mapping != null && !"".equals(mapping)){
            if("/m/event/jehuList.do".equals(mapping)){
                pageInfoBean.setRecordCount(5);	//mobile버전
            }else{
                pageInfoBean.setRecordCount(6);		//pc버전
            }
        }else{
        /*한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(6);		//pc버전
        }

        //eventBoardDto.setSearchValue(ParseHtmlTagUtil.percentToEscape(eventBoardDto.getSearchValue()));		// 예약어 %,_ 검색시 Escape처리

        /*카운터 조회*/
        int total = coEventSvc.countJehuListSelect(eventBoardDto);

        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

    //	List<ComCdDto> comCdList = coEventSvc.eventComCd();

        List<EventBoardDto> jehuList = coEventSvc.jehuListSelect(eventBoardDto, skipResult, maxResult);

        //eventBoardDto.setSearchValue(ParseHtmlTagUtil.escapeToPercent(eventBoardDto.getSearchValue()));		// 예약어 %,_ 검색시 ReEscape처리

        model.addAttribute("total", total);
        model.addAttribute("searchDto", eventBoardDto);
        model.addAttribute("jehuList", jehuList);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("selectEvt", selectEvt);

        return returnUrl;
    }

    /**
     * 설명 : '친구초대란?' 페이지 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param nmcpEventBoardBasDto
     * @param request
     * @param pageInfoBean
     * @param model
     * @param bind
     * @return
     */
    @RequestMapping(value = {"/event/frndRecommend.do","/m/event/frndRecommend.do"})
    public String fndRecommend(NmcpEventBoardBasDto nmcpEventBoardBasDto
            , HttpServletRequest request
            , PageInfoBean pageInfoBean
            , Model model
            , BindingResult bind) {

        String returnUrl = "/portal/event/frndRecommend";
        FormDtlDTO noticeContent;

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl="/mobile/event/frndRecommend";
            noticeContent = formDtlSvc.FormDtlView(new FormDtlDTO("frndRecommendTemp", "frndRecommendNoticeMo"));
        } else {
            noticeContent = formDtlSvc.FormDtlView(new FormDtlDTO("frndRecommendTemp", "frndRecommendNoticePc"));
        }

        if (noticeContent != null && noticeContent.getUnescapedDocContent() != null) {
            model.addAttribute("noticeContent", noticeContent.getUnescapedDocContent());
        } else {
            model.addAttribute("noticeContent", "");
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(Optional.ofNullable(userSession).isPresent()) {
            UserVO userVO2 = mypageUserService.selectUser(userSession.getUserId());
            model.addAttribute("userDivision", userVO2.getUserDivision());
            model.addAttribute("custNm", userVO2.getName());

            // 마스킹해제
            if(SessionUtils.getMaskingSession() > 0 ) {
                model.addAttribute("maskedcustNm", userVO2.getName());
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
            }else {
                // 마스킹처리 된 고객명 추가 2022.10.05
                model.addAttribute("maskedcustNm", userVO2.getMkNm());
            }
        }
        model.addAttribute("menuType", "frndRegNe");
        model.addAttribute("popType", "frndRegNe");

        return returnUrl;
    }

    /**
     * 설명 : '친구초대란?' list 조회 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @return
     */
    @RequestMapping(value = {"/m/event/eventBoardListAjax.do", "/event/eventBoardListAjax.do"})
    @ResponseBody
    public Map<String, Object> getEventBoardListAjax(){
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        rtnMap.put("eventList", coEventSvc.selectEventBoardList());

        return rtnMap;
    }

    /**
     * 설명 : '친구초대하기' 화면 로딩
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/m/event/frndRecommendReqView.do", "/event/frndRecommendReqView.do"})
    public String frndRecommendReqView(HttpServletRequest request, Model model) {
        UserSessionDto userSession = SessionUtils.getUserCookieBean();


        String returnUrl = "/portal/event/frndRecommendReqView";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/event/frndRecommendReqView";
        }
        if(Optional.ofNullable(userSession).isPresent()) {
            UserVO userVO2 = mypageUserService.selectUser(userSession.getUserId());
            model.addAttribute("userDivision", userVO2.getUserDivision());
            model.addAttribute("custNm", userVO2.getName());

            // 마스킹해제
            if(SessionUtils.getMaskingSession() > 0 ) {
                 model.addAttribute("maskedcustNm", userVO2.getName());
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

            }else {
                // 마스킹처리 된 고객명 추가 2022.10.05
                model.addAttribute("maskedcustNm", userVO2.getMkNm());
            }
        }
        model.addAttribute("menuType", "frndReg");
        model.addAttribute("popType", "frndReg");
        return returnUrl;
    }

    /**
     * 설명 : SMS 발송 처리
     *           ID , 패스원스 사용자 인증 후 관리자 전화번호로 SMS 인증 문자 전송 처리
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param name
     * @param phone
     * @return
     */
    @RequestMapping(value = "/event/userCertSmsAjax.do")
    @ResponseBody
    public Map<String, Object> prodUserSms(HttpServletRequest request
            ,@RequestParam(value = "name", required = false) String name
            ,@RequestParam(value = "phone", required = false) String phone
            ){

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if ( StringUtils.isBlank(name) || StringUtils.isBlank(phone) ) {
            rtnMap.put("RESULT_CODE", "00001");
            return rtnMap;
        }

        String contractNum = mypageUserService.selectContractNum(name,phone,"","");

        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setPhoneNum(phone);
        authSmsDto.setMenu("frndReg");
        boolean check = fCommonSvc.sendAuthSms(authSmsDto);

        if (check) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        } else {
            rtnMap.put("RESULT_CODE", "0002");
        }

        return rtnMap;
    }

    /**
     * 설명 : sms인증번호 체크
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param certifySms
     * @param phone
     * @return
     */
    @RequestMapping(value = "/event/checkCertSmsAjax.do")
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
        tmp.setMenu("frndReg");
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
     * 설명 : 친구초대 sns, sms 공유하기 Ajax
     * @Author SeongminHong
     * @Date 2021.12.30
     * @param request
     * @param name
     * @param phone
     * @param send
     * @return
     */
    @RequestMapping(value = {"/event/frndSendAjax.do", "/m/event/frndSendAjax.do"})
    @ResponseBody
    public Map<String, Object> frndSmsSendAjax(HttpServletRequest request,
             @RequestParam(value = "name", required = false) String name
            ,@RequestParam(value = "phone", required = false) String phone
            ,@RequestParam(value = "send", required = false) String send
            ,@RequestParam(value = "mstoreTermAgree", required = false) String mstoreTermAgree){


        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 20230828 신규추가
        // M스토어 이용약관 동의 필수 (M스토어 포인트 지급 목적)
        if(!"Y".equalsIgnoreCase(mstoreTermAgree)){
            throw new McpCommonJsonException("00009" ,"개인정보 제 3자 제공에 동의해주세요.");
        }

        try{

            HttpSession session = request.getSession();
            StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
            atr.append("_").append("frndReg");

            AuthSmsDto sessionAuthSmsDto = (AuthSmsDto)session.getAttribute(atr.toString());
            if( sessionAuthSmsDto == null ) {
                rtnMap.put("RESULT_CODE", "00001");
                return rtnMap;
            }

            String sesPhoneNum = StringUtil.NVL(sessionAuthSmsDto.getPhoneNum(),"");
            if( !sesPhoneNum.equals(phone) ){
                rtnMap.put("RESULT_CODE", "00002");
                return rtnMap;
            }


            String contractNum = mypageUserService.selectContractNum(name,phone,"","");
            // 임시 주석  처리(contractNum 조회 불가)
            if(contractNum==null ){
                rtnMap.put("RESULT_CODE", "00003");
                return rtnMap;
            } else {

                // ============ STEP START ============
                // 1. 최소 스텝 수 체크
                if(certService.getStepCnt() < 2 ){
                    throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
                }

                // 2. 최종 데이터 체크: step종료 여부, 이름, 핸드폰번호, 계약번호
                String[] certKey = {"urlType", "stepEndYn", "name", "mobileNo", "contractNum"};
                String[] certValue = {"inviteFrnd", "Y", name, phone, contractNum};
                Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
                if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
                // ============ STEP END ============

                boolean prePaymentFlag = mypageUserService.selectPrePayment(contractNum);

                if(prePaymentFlag){
                    rtnMap.put("RESULT_CODE", "00004");
                    return rtnMap;
                }
            }

            MspSmsTemplateMstDto mspSmsTemplateMstDto = null;
            int key = Constants.SMS_FRND_RECOMMEND_ID;
            mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(Constants.SMS_FRND_RECOMMEND_ID);
            if (mspSmsTemplateMstDto != null) {

                FrndRecommendDto frndRecommendDto = new FrndRecommendDto();
                frndRecommendDto.setContractNum(contractNum);

                FrndRecommendDto resDto = coEventSvc.selectFrndId(frndRecommendDto);
                String frndId = "";
                if( resDto != null ){ // 기존
                    frndId  = resDto.getCommendId();
                } else { // 신규
                    frndId = coEventSvc.getRandCommendId();
                    if("".equals(frndId)){
                        rtnMap.put("RESULT_CODE", "00005");
                        return rtnMap;
                    }
                    frndRecommendDto.setCommendId(frndId);
                    int cnt = coEventSvc.insertFrndId(frndRecommendDto);
                }

                String userPhoneNum = phone;
                String userName = name;
                String text = mspSmsTemplateMstDto.getText();
                String smsMsg = String.format(text, userName,userName,userName,frndId,frndId);

                String url = "";
                if("LOCAL".equals(serverName)) {
                    url = "https://local.portal.ktmmobile.com";
                } else if("DEV".equals(serverName)) {
                    url = "https://dmcpdev.ktmmobile.com:8012";
                } else if("STG".equals(serverName)) {
                    url = "https://dmcpstg.ktmmobile.com";
                } else {
                    url = "https://www.ktmmobile.com";
                }

                if("sms".equals(send)){
                    //smsSvc.sendLms( mspSmsTemplateMstDto.getSubject(), userPhoneNum, smsMsg , mspSmsTemplateMstDto.getCallback() );
                    smsSvc.sendLms(mspSmsTemplateMstDto.getSubject(), userPhoneNum, smsMsg ,
                            mspSmsTemplateMstDto.getCallback(),String.valueOf(Constants.SMS_FRND_RECOMMEND_ID),"SYSTEM");
                } else if("kakao".equals(send)){
                    rtnMap.put("kakaoMsg", frndId);
                } else if("confirm".equals(send)) { //인증번호 확인 후 나의 친구초대 추천 URL
                    if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                        rtnMap.put("recommend",url+"/m/event/eventDetail.do?ntcartSeq=866&sbstCtg=E&pageNo=1&recommend="+frndId);
                    }else {
                        rtnMap.put("recommend",url+"/event/eventDetail.do?ntcartSeq=866&sbstCtg=E&pageNo=1&recommend="+frndId);
                    }
                } else {
                    rtnMap.put("RESULT_CODE", "00006");
                    return rtnMap;
                }
            }
        }catch(Exception e){
            logger.debug("error in coEventController");
            logger.error(e.getMessage());
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        return rtnMap;

    }


    /**
     * 설명 : 친구초대 등록 , 수정
     * @Author papier
     * @Date 2026.01.26

     * @return
     */
    @RequestMapping(value = "/event/frndRecommendPrdAjax.do")
    @ResponseBody
    public Map<String, Object> frndRecommendPrd(
            FrndRecommendDto frndRecommendPra
            , HttpServletRequest request
            ,@RequestParam(value = "name", required = false) String name
            ,@RequestParam(value = "phone", required = false) String phone
            ,@RequestParam(value = "mstoreTermAgree", required = false) String mstoreTermAgree){


        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 20230828 신규추가
        // M스토어 이용약관 동의 필수 (M스토어 포인트 지급 목적)
        if(!"Y".equalsIgnoreCase(mstoreTermAgree)){
            throw new McpCommonJsonException("00009" ,"개인정보 제 3자 제공에 동의해주세요.");
        }

        try {

            HttpSession session = request.getSession();
            StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
            atr.append("_").append("frndRegNe");

            AuthSmsDto sessionAuthSmsDto = (AuthSmsDto)session.getAttribute(atr.toString());
            if( sessionAuthSmsDto == null ) {
                rtnMap.put("RESULT_CODE", "00001");
                return rtnMap;
            }

            String sesPhoneNum = StringUtil.NVL(sessionAuthSmsDto.getPhoneNum(),"");
            if( !sesPhoneNum.equals(phone) ){
                rtnMap.put("RESULT_CODE", "00002");
                return rtnMap;
            }


            String contractNum = mypageUserService.selectContractNum(name,phone,"","");
            // 임시 주석  처리(contractNum 조회 불가)
            if(contractNum==null ){
                rtnMap.put("RESULT_CODE", "00003");
                return rtnMap;
            } else {

                // ============ STEP START ============
                // 1. 최소 스텝 수 체크
                if(certService.getStepCnt() < 2 ){
                    throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
                }

                // 2. 최종 데이터 체크: step종료 여부, 이름, 핸드폰번호, 계약번호
                String[] certKey = {"urlType", "stepEndYn", "name", "mobileNo", "contractNum"};
                String[] certValue = {"inviteFrnd", "Y", name, phone, contractNum};
                Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
                if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
                // ============ STEP END ============

                boolean prePaymentFlag = mypageUserService.selectPrePayment(contractNum);

                if(prePaymentFlag){
                    rtnMap.put("RESULT_CODE", "00004");
                    return rtnMap;
                }
            }

            FrndRecommendDto paramDto  = new FrndRecommendDto();
            paramDto .setContractNum(contractNum);
            FrndRecommendDto resDto = coEventSvc.selectFrndIdUpdate(paramDto ,frndRecommendPra);
            if( resDto == null ){
                /** 신규 */
                String frndId = coEventSvc.getRandCommendId();
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
                coEventSvc.insertFrndId(resDto);
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


        } catch(Exception e){
            logger.debug("error in coEventController");
            logger.error(e.getMessage());
        }
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        return rtnMap;

    }



    /**
     * 설명 : 친구초대 정보 확인
     * @Author papier
     * @Date 2026.01.26

     * @return
     */
    @RequestMapping(value = "/event/getFrndRecommendAjax.do")
    @ResponseBody
    public Map<String, Object> getFrndRecommend(
            FrndRecommendDto frndRecommendPra
            , HttpServletRequest request
            ,@RequestParam(value = "name", required = false) String name
            ,@RequestParam(value = "phone", required = false) String phone){


        HashMap<String, Object> rtnMap = new HashMap<String, Object>();


        try {

            HttpSession session = request.getSession();
            StringBuffer atr = new StringBuffer(COMM_AUTH_SMS_INFO);
            atr.append("_").append("frndRegNe");

            AuthSmsDto sessionAuthSmsDto = (AuthSmsDto)session.getAttribute(atr.toString());
            if( sessionAuthSmsDto == null ) {
                rtnMap.put("RESULT_CODE", "00001");
                return rtnMap;
            }

            String sesPhoneNum = StringUtil.NVL(sessionAuthSmsDto.getPhoneNum(),"");
            if( !sesPhoneNum.equals(phone) ){
                rtnMap.put("RESULT_CODE", "00002");
                return rtnMap;
            }


            String contractNum = mypageUserService.selectContractNum(name,phone,"","");
            // 임시 주석  처리(contractNum 조회 불가)
            if(contractNum==null ){
                rtnMap.put("RESULT_CODE", "00003");
                return rtnMap;
            } else {

                // ============ STEP START ============
                // 1. 최소 스텝 수 체크
                if(certService.getStepCnt() < 2 ){
                    throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
                }

                // 2. 최종 데이터 체크: step종료 여부, 이름, 핸드폰번호, 계약번호
                String[] certKey = {"urlType", "stepEndYn", "name", "mobileNo", "contractNum"};
                String[] certValue = {"inviteFrnd", "Y", name, phone, contractNum};
                Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
                if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
                // ============ STEP END ============

                boolean prePaymentFlag = mypageUserService.selectPrePayment(contractNum);

                if(prePaymentFlag){
                    rtnMap.put("RESULT_CODE", "00004");
                    return rtnMap;
                }
            }


            FrndRecommendDto frndRecommendDto = new FrndRecommendDto();
            frndRecommendDto.setContractNum(contractNum);
            FrndRecommendDto resDto = coEventSvc.selectFrndId(frndRecommendDto);
            if( resDto != null ){ // 기존
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
            } else { // 신규
                rtnMap.put("COMMEND_OBJ", null);
            }

            rtnMap.put("COMMEND_NM",MaskingUtil.getMaskedName(name));


        } catch(Exception e){
            logger.debug("error in coEventController");
            logger.error(e.getMessage());
            rtnMap.put("RESULT_CODE", "99999");
            return rtnMap;
        }
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        return rtnMap;

    }



    /**
     * <pre>
     * 설명     : 포인트 적립 모듈
     * @return
     * @return: Map<String,Object>
     * </pre>
     */
    @RequestMapping(value =  "/savePointAjax.do")
    @ResponseBody
    public Map<String, Object> savePointAjax(CustPointTxnDto pointTxnDto
            , PageInfoBean pageInfoBean
            , BindingResult bind){
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (bind.hasErrors()) {
            throw new McpCommonJsonException(BIDING_EXCEPTION);
        }

        String curIp = getIp();

        // 파라미터로 받거나 세션으로 세팅
        String userId = pointTxnDto.getUserId();
        String svcCntrNo = pointTxnDto.getSvcCntrNo();

        // 파라미터로 받는 정보
        int point = pointTxnDto.getPoint(); // int 포인트
        String pointTxnRsnCd = pointTxnDto.getPointTxnRsnCd(); // String 포인트처리사유코드
        String pointTxnDtlRsnDesc = pointTxnDto.getPointTxnDtlRsnDesc(); // String 포인트처리상세사유설명
        String pointTrtMemo = pointTxnDto.getPointTrtMemo(); // String 포인트처리메모

        // 테스트 정보 세팅
        /**
         * 테스트위한 로그
        String userId = 회원아이디;
        String svcCntrNo = 계약번호;

        int point = 1000; // int 포인트
        String pointTxnRsnCd = "S41"; // String 포인트처리사유코드
        String pointTxnDtlRsnDesc = "프런트 이벤트 적립모듈 테스트"; // String 포인트처리상세사유설명
        String pointTrtMemo = "프런트 이벤트 적립모듈 테스트 메모"; // String 포인트처리메모
        **/

        // 세팅하거나 파라미터로 받거나 해야함
        pointTxnDto.setPoint(point); // int 포인트
        pointTxnDto.setPointTxnRsnCd(pointTxnRsnCd); // String 포인트처리사유코드
        pointTxnDto.setPointTxnDtlRsnDesc(pointTxnDtlRsnDesc); // String 포인트처리상세사유설명
        pointTxnDto.setPointTrtMemo(pointTrtMemo); // String 포인트처리메모


        // 포인트 지급대상 고객정보
        pointTxnDto.setSvcCntrNo(svcCntrNo); // String 서비스계약번호
        pointTxnDto.setUserId(userId); // String 사용자ID
        pointTxnDto.setCretId(userId); // String 생성자ID
        pointTxnDto.setAmdId(userId); // String 생성자ID


        // 고정값
        pointTxnDto.setPointDivCd("MP"); // String 포인트분류코드
        pointTxnDto.setPointTrtCd("S"); // String 포인트처리코드
        pointTxnDto.setPointUsePosblStDate(DateTimeUtil.getFormatString("yyyyMMddHHmmss")); // String 포인트사용가능시작일자
        pointTxnDto.setPointUsePosblEndDate(DateTimeUtil.getFormatString("yyyyMMddHHmmss")); // String 포인트사용가능종료일자 - 시작일+1년 --> 사용 용도에 맞춰서 설정해야함
        pointTxnDto.setCretIp(curIp); // String 생성IP
        pointTxnDto.setAmdIp(curIp); // String 생성IP


        // 추후 사용시 세팅 값
        pointTxnDto.setPointGiveBaseSeq(0); // String 포인트지급기준일련번호 - 포인트지급기준 있을 경우 해당 번호 세팅
        pointTxnDto.setPointAllGiveSeq(0); // String 포인트일괄지급일련번호
        pointTxnDto.setUserPointTrtSeq(0); // String 고객포인트처리일련번호
        pointTxnDto.setRequestKey(0); // String 가입신청_키


        // 포인트 적립 처리
        pointSvc.editPointSUE(pointTxnDto);

        rtnMap.put("resultCode","P0000"); // 처리 성공

        return rtnMap;
    }



    /**
     * <pre>
     * 설명     : 포인트 적립 배치 적재
     * @return
     * @return: Map<String,Object>
     * </pre>
     */
    @RequestMapping(value =  "/savePointTgtAjax.do")
    @ResponseBody
    public Map<String, Object> savePointTgtAjax(CustPointGiveTgtBasDto custPointGiveTgtBasDto
            , PageInfoBean pageInfoBean
            , BindingResult bind){
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (bind.hasErrors()) {
            throw new McpCommonJsonException(BIDING_EXCEPTION);
        }

        String curIp = getIp();


        // 파라미터로 받거나 세션으로 세팅
        String userId = custPointGiveTgtBasDto.getUserId();
        String svcCntrNo = custPointGiveTgtBasDto.getSvcCntrNo();


        // 파라미터로 받는 정보
        int orderNo = custPointGiveTgtBasDto.getOrdrNo(); // int 포인트
        int point = custPointGiveTgtBasDto.getPoint(); // int 포인트
        String pointTxnRsnCd = custPointGiveTgtBasDto.getPointTxnRsnCd(); // String 포인트처리사유코드
        String pointTxnDtlRsnDesc = custPointGiveTgtBasDto.getPointTxnDtlRsnDesc(); // String 포인트처리상세사유설명


        // 테스트 정보 세팅
        /**
         * 테스트위한 로그
        String userId = 회원아이디;
        String svcCntrNo = 계약번호;

        int orderNo = 0; // int 서식지주문번호
        int point = 1000; // int 포인트
        String pointTxnRsnCd = "S41"; // String 포인트처리사유코드
        String pointTxnDtlRsnDesc = "프런트 이벤트 적립모듈 테스트"; // String 포인트처리상세사유설명
        **/


        // 포인트 지급대상 고객정보
        custPointGiveTgtBasDto.setSvcCntrNo(svcCntrNo);
        custPointGiveTgtBasDto.setCretId(userId); // String 생성자ID
        custPointGiveTgtBasDto.setAmdId(userId); // String 생성자ID


        // 적립 정보 세팅 --> 파라미터로 받아야함
        custPointGiveTgtBasDto.setOrdrNo(orderNo); // int 서식지주문번호
        custPointGiveTgtBasDto.setPoint(point); // int 포인트
        custPointGiveTgtBasDto.setPointTxnRsnCd(pointTxnRsnCd); // String 포인트처리사유코드
        custPointGiveTgtBasDto.setPointTxnDtlRsnDesc(pointTxnDtlRsnDesc); // String 포인트처리상세사유설명


        // 고정값
        custPointGiveTgtBasDto.setPointGiveDate(DateTimeUtil.getFormatString("yyyyMMdd")+"000000"); //포인트지급일자 - 현재일자
        custPointGiveTgtBasDto.setPointGiveValidYn("Y"); // 포인트지급유효여부
        custPointGiveTgtBasDto.setPointDivCd("MP"); // String 포인트분류코드
        custPointGiveTgtBasDto.setPointTrtCd("S"); // String 포인트처리코드
        custPointGiveTgtBasDto.setPointUsePosblStDate(DateTimeUtil.getFormatString("yyyyMMddHHmmss")); // String 포인트사용가능시작일자
        custPointGiveTgtBasDto.setPointUsePosblEndDate(DateTimeUtil.getFormatString("yyyyMMddHHmmss")); // String 포인트사용가능종료일자 - 시작일+1년--> 사용 용도에 맞춰서 설정해야함
        custPointGiveTgtBasDto.setPointGiveCpltYn("N"); // String 포인트지급완료여부
        custPointGiveTgtBasDto.setPointGiveCpltDate(""); // String 포인트지급완료일자
        custPointGiveTgtBasDto.setCretIp(curIp); // String 생성IP
        custPointGiveTgtBasDto.setAmdIp(curIp); // String 생성IP


        // 추후 사용시 세팅 값
        custPointGiveTgtBasDto.setPointGiveBaseSeq(0); // int 포인트지급기준일련번호
        custPointGiveTgtBasDto.setPointGiveBaseDtlSeq(0); // int 포인트지급기준상세일련번호
        custPointGiveTgtBasDto.setPointGiveBaseRateCd(""); // String 포인트지급기준요금제코드
        custPointGiveTgtBasDto.setPointGiveBaseRateAmt(0); // int 포인트지급기준요금제금액


        // 등록 처리
        pointSvc.insertPointTgtBas(custPointGiveTgtBasDto);

        rtnMap.put("resultCode","P0000"); // 처리 성공

        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 이벤트 혜택 리스트 조회
     * </pre>
     */
    @RequestMapping(value = "/event/getEventBenefitAjax.do")
    @ResponseBody
    public EventBenefitBasDto getEventBenefitAjax(EventBenefitRelationDto eventBenefitRelation){
        if (eventBenefitRelation.getNtcartSeq() < 1 ) {
            throw new McpCommonJsonException(F_BIND_EXCEPTION);
        }

        EventBenefitBasDto eventBenefitBasDto = eventJoinService.getEventBenefit(eventBenefitRelation);

        if (eventBenefitBasDto == null) {
            eventBenefitBasDto = new EventBenefitBasDto();
            eventBenefitBasDto.setEventBenefitSeq(0);
        }
        return  eventBenefitBasDto;

    }

    /*
     * 이벤트 팝업
     */
    @RequestMapping(value = "/event/getEventPopupAjax.do")
    @ResponseBody
    public PopupDto getEventPopupAjax(PopupDto popupDto) {

        PopupEditorDto popupEditorDto = fCommonSvc.getPopupEditor(popupDto.getPopupSeq());
        if (popupEditorDto == null) {
            return popupDto;
        }

        String popupSbst = popupEditorDto.getPopupSbst();

        popupSbst = popupSbst.replaceAll("&lt;", "<");
        popupSbst = popupSbst.replaceAll("&gt;", ">");
        popupSbst = popupSbst.replaceAll("&quot;", "\"");
        popupSbst = popupSbst.replaceAll("&#39;", "'");
        popupSbst = popupSbst.replaceAll("&amp;", "&");
        popupSbst = popupSbst.replaceAll("amp;", "");

        popupDto.setPopupSbst(popupSbst);

        return popupDto;
    }


    @RequestMapping(value="/event/setKaKaoShareEventAjax.do")
    @ResponseBody
    public Map<String, Object> setUserEventTrace(UserEventTraceDto userEventTraceDto){

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto ==null) {
            rtnMap.put("UET_SEQ", -1);
            return rtnMap;
        }

        userEventTraceDto.setPrcsMdlMain("SNS_SHARE");
        userEventTraceDto.setPrcsMdlMid("KAKAO");
        userEventTraceDto.setPrcsMdlSub("INIT");
        userEventTraceDto.setRegstId(userSessionDto.getUserId());
        userEventTraceDto.setRvisnId(userSessionDto.getUserId());

        if (fCommonSvc.insertUserEventTrace(userEventTraceDto)) {
            rtnMap.put("UET_SEQ", userEventTraceDto.getUetSeq());
        } else {
            rtnMap.put("RESULT_CODE", "-1");
        }


        return rtnMap;

    }



    //kakaoWebhook
    @RequestMapping(value = "/kakaoWebhook.do")
    @ResponseBody
    public Map<String, Object> kakaoWebhook(@RequestHeader("Authorization") String authrozitaion,
                                             @RequestHeader("X-Kakao-Resource-ID") String kakaoResourceId,
                                             @RequestHeader("User-Agent") String userAgent,
                                            @RequestBody KakaoWebhookDto kakaoWebhookDto ){
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();


        String strUetSeq = kakaoWebhookDto.getStrUetSeq();
        String chatType = kakaoWebhookDto.getChatType() ;
        String hashChatId = kakaoWebhookDto.getHashChatId();


        McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
        mcpIpStatisticDto.setPrcsMdlInd("kakaoWebhook");
        mcpIpStatisticDto.setPrcsSbst("init2");
        mcpIpStatisticDto.setParameter("["+strUetSeq+"]["+chatType+"]["+hashChatId+"]");
        mcpIpStatisticDto.setTrtmRsltSmst("");
        ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);


        if (strUetSeq != null && !"".equals(strUetSeq) &&  !"-1".equals(strUetSeq)) {
            //UPDATE 처리

            long uetSeq;
            try {
                uetSeq = Long.parseLong(strUetSeq);
            } catch (NumberFormatException e) {
                rtnMap.put("RESULT_CODE", "-1");
                return rtnMap;
            }


            UserEventTraceDto userEventTraceDto = new UserEventTraceDto();
            userEventTraceDto.setUetSeq(uetSeq);
            userEventTraceDto.setRvisnId("webHook");
            userEventTraceDto.setPrcsMdlMain("SNS_SHARE");
            userEventTraceDto.setPrcsMdlMid("KAKAO");
            userEventTraceDto.setPrcsMdlSub(chatType);
            userEventTraceDto.setDtlDesc(hashChatId);
            fCommonSvc.updateUserEventTrace(userEventTraceDto);
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        return rtnMap;
    }

    // pc/모바일 이번달/제휴 이벤트 리스트
    @RequestMapping(value = {"/event/eventListAdminView.do", "/m/event/eventListAdminView.do"}
            ,method = {RequestMethod.GET})
    public String eventListAdminView(@ModelAttribute NmcpEventBoardBasDto nmcpEventBoardBasDto
            , HttpServletRequest request
            , PageInfoBean pageInfoBean
            , Model model
            , BindingResult bind
            , @RequestParam String adminEventDate
            , @RequestParam(required = false, defaultValue = EVENT_SBST_CTG_EVENT) String sbstCtg) {

        if (adminEventDate == null || adminEventDate.isEmpty()) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        if (!DateTimeUtil.isValid(adminEventDate, "yyyyMMddHH")) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto ==null) {
            throw new McpCommonException("로그인이 필요한 메뉴입니다.", "/loginForm.do");
        }

        String userId = userSessionDto.getUserId();
        NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("EventPageTestUsers", userId);
        if (nmcpCdDtlDto == null) {
            throw new McpCommonException("접근 불가능한 메뉴입니다.", "/main.do");
        }

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.Event_Category);
        List<NmcpCdDtlDto> eventCategoryList = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

        String returnUrl = "";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl =  "/mobile/event/eventListAdminView";
        }else {
            returnUrl = "/portal/event/eventListAdminView";
        }

        StringBuilder sbReturnUrl = new StringBuilder(returnUrl);
        nmcpEventBoardBasDto.setNtcartSbst(ParseHtmlTagUtil.percentToEscape(nmcpEventBoardBasDto.getNtcartSbst()));
        String category = StringUtil.NVL(request.getParameter("category"),"");
        model.addAttribute("category", category);
        model.addAttribute("serverName",serverName);
        model.addAttribute("PageInfoBean", pageInfoBean);
        model.addAttribute("NmcpEventBoardBas", nmcpEventBoardBasDto);
        model.addAttribute("eventCategoryList", eventCategoryList);
        model.addAttribute("adminEventDate", nmcpEventBoardBasDto.getAdminEventDate());
        model.addAttribute("eventBranch", sbstCtg);

        return sbReturnUrl.toString();
    }


    //모바일/제휴 이번달/종료 이벤트 목록보여주기
    @RequestMapping(value={"/m/event/eventListAdminViewAjax.do", "/event/eventListAdminViewAjax.do"},
            method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> eventListAdminViewAjax(HttpServletRequest request,
                                            @ModelAttribute EventBoardDto eventBoardDto,
                                            PageInfoBean pageInfoBean, Model model, BindingResult bind) {
        if (eventBoardDto.getAdminEventDate() == null || "".equals(eventBoardDto.getAdminEventDate())) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        if (!DateTimeUtil.isValid(eventBoardDto.getAdminEventDate(), "yyyyMMddHH")) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto ==null) {
            throw new McpCommonException("로그인이 필요한 메뉴입니다.", "/loginForm.do");
        }

        String userId = userSessionDto.getUserId();
        NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("EventPageTestUsers", userId);
        if (nmcpCdDtlDto == null) {
            throw new McpCommonException("접근 불가능한 메뉴입니다.", "/main.do");
        }

        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*카운터 조회*/
        int total = 0;
        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); //셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        List<EventBoardDto> eventList;
        Map<String,Object> resultData = new HashMap<String,Object>();

        // 입력받은 날짜의 이벤트 카운터
        if (eventBoardDto.getEventBranch() == null || eventBoardDto.getEventBranch().isEmpty()) {
            eventBoardDto.setEventBranch(EVENT_SBST_CTG_EVENT);
        }
        total = coEventSvc.countPlanListSelectByDate(eventBoardDto);

        // 입력받은 날짜의 이벤트 리스트
        eventList = coEventSvc.planListSelectByDate(eventBoardDto,skipResult);
        resultData.put("eventList", eventList);
        resultData.put("pageInfoBean", pageInfoBean);

        pageInfoBean.setTotalCount(total);

        return resultData;
    }

    //제휴/이벤트 상세 pc/mobile
    @RequestMapping(value = {"/m/event/eventDetailAdminView.do", "/event/eventDetailAdminView.do"}
            ,method = {RequestMethod.GET, RequestMethod.POST})
    public String eventDetailAdminView(
            HttpServletRequest request
            , @ModelAttribute("eventBoardDto") EventBoardDto eventBoardDto
            , PageInfoBean pageInfoBean
            , Model model){

        String returnUrl = "";

        if (eventBoardDto.getAdminEventDate() == null || "".equals(eventBoardDto.getAdminEventDate())) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        if (!DateTimeUtil.isValid(eventBoardDto.getAdminEventDate(), "yyyyMMddHH")) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto ==null) {
            throw new McpCommonException("로그인이 필요한 메뉴입니다.", "/loginForm.do");
        }

        String userId = userSessionDto.getUserId();
        NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("EventPageTestUsers", userId);
        if (nmcpCdDtlDto == null) {
            throw new McpCommonException("접근 불가능한 메뉴입니다.", "/main.do");
        }

        if("GET".equals(request.getMethod()) || "POST".equals(request.getMethod())) {
//            if(!Optional.ofNullable(eventBoardDto.getNtcartSeq()).isPresent()){
            if (ObjectUtils.isEmpty(eventBoardDto.getNtcartSeq())) {
                throw new McpCommonException("잘못된 접근입니다.",
                        request.getRequestURI().equals("/m/event/eventDetailAdminView.do") ? "/m/event/eventListAdminView.do" : "/event/eventListAdminView.do");
            }
        }

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl =  "/mobile/event/eventDetailAdminView";
            eventBoardDto.setProModule("m");
        }else {
            returnUrl = "/portal/event/eventDetailAdminView";
        }

        EventBoardDto eventDto = coEventSvc.getEventDetailByDate(eventBoardDto);             //이벤트 기본정보테이블

        model.addAttribute("searchDto", eventBoardDto);
        model.addAttribute("eventDto", eventDto);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("eventBranch", eventBoardDto.getEventBranch());
        model.addAttribute("adminEventDate", eventBoardDto.getAdminEventDate());

        //이벤트 플로팅 배너
        model.addAttribute("bannerFloatInfo", NmcpServiceUtils.getBannerFloatList(eventBoardDto.getNtcartSeq()));

        // 이벤트 카테고리 자동 판단
        String listViewYn = "";
        if(eventDto != null) {
            listViewYn = eventDto.getListViewYn();
            eventBoardDto.setEventBranch(eventDto.getSbstCtg());
            model.addAttribute("sbstCtg",eventDto.getSbstCtg());
            model.addAttribute("eventBranch",eventDto.getSbstCtg());
        }

        StringBuilder sbReturnUrl = new StringBuilder(returnUrl);
        if ("I".equals(listViewYn)) {
            sbReturnUrl.append(".nonHeader");
        }

        //이벤트 공유하기 링크
        String recommend = request.getParameter("recommend");

        if(recommend != null && !recommend.equals(SessionUtils.getFriendInvitation())) { //중복방지
            SessionUtils.saveFriendInvitation(recommend);  // 친구초대 ID값 세션저장
            //이력 정보 저장 처리(친구초대 링크 타고 접속 한경우)
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("INVITATION");
            mcpIpStatisticDto.setPrcsSbst("INIT");
            mcpIpStatisticDto.setParameter(recommend);
            mcpIpStatisticDto.setTrtmRsltSmst("");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        }
        return sbReturnUrl.toString();
    }

    /**
     * 설명 : 친구초대  URL을 통해 링크로 들어갔을때의 상세페이지 화면
     * @Author papier
     * @Date 2026.01.28
     * @return
     */
    @RequestMapping(value = "/event/frndRecommendView.do")
    public String frndRecommendView(@RequestParam(required = false, defaultValue = "") String recommend,Model model) {
        UserSessionDto userSession = SessionUtils.getUserCookieBean();


        //기본 값으로 연결..
        if (!org.springframework.util.StringUtils.hasText(recommend)){
            return "redirect:/event/eventDetail.do?ntcartSeq=866&sbstCtg=E" ;
        }


        FrndRecommendDto frndRecommendDto = new FrndRecommendDto();
        frndRecommendDto.setCommendId(recommend);
        FrndRecommendDto resDto = coEventSvc.selectFrndId(frndRecommendDto);

        //추천인 정보가 없었도 기본값..
        if (resDto == null) {
            return "redirect:/event/eventDetail.do?ntcartSeq=866&sbstCtg=E" ;
        }

        if ("01".equals(resDto.getLinkTypeCd())) {
            return "redirect:/event/eventDetail.do?ntcartSeq=866&sbstCtg=E&recommend="+recommend ;
        }

        //계약 정보 확인
        Map<String, String> resOjb = Optional.ofNullable(mypageUserService.selectContractObj("", "", resDto.getContractNum()))
                        .orElseThrow(() -> new IllegalArgumentException("계약 정보 없음"));

        String custNM = Optional.ofNullable(resOjb.get("SUB_LINK_NAME"))
                        .orElseThrow(() -> new IllegalArgumentException("고객명 없음"));

        model.addAttribute("recommend",resDto);
        model.addAttribute("custNM",MaskingUtil.getMaskedName(custNM));

        String returnUrl = "/portal/event/frndRecommendView";
        FormDtlDTO allInOneContent;

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/event/frndRecommendView";
            allInOneContent = formDtlSvc.FormDtlView(new FormDtlDTO("frndRecommendTemp", "allInOneContentMo"));
        } else {
            allInOneContent = formDtlSvc.FormDtlView(new FormDtlDTO("frndRecommendTemp", "allInOneContentPc"));
        }

        if (allInOneContent != null && allInOneContent.getUnescapedDocContent() != null) {
            model.addAttribute("allInOneContent", allInOneContent.getUnescapedDocContent());
        } else {
            model.addAttribute("allInOneContent", "");
        }

        if(!StringUtils.isBlank(recommend) && !recommend.equals(SessionUtils.getFriendInvitation())) { //중복방지
            SessionUtils.saveFriendInvitation(recommend);  // 친구초대 ID값 세션저장
            //이력 정보 저장 처리(친구초대 링크 타고 접속 한경우)
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("INVITATION");
            mcpIpStatisticDto.setPrcsSbst("INIT");
            mcpIpStatisticDto.setParameter(recommend);
            mcpIpStatisticDto.setTrtmRsltSmst("");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        }


        return returnUrl;
    }






    private String getIp() {

        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
        }
        catch ( UnknownHostException e ) {
            logger.error("Exception e : {}", e.getMessage());
        }

        if( local == null ) {
            return "";
        }
        else {
            String ip = local.getHostAddress();
            return ip;
        }

    }


}
