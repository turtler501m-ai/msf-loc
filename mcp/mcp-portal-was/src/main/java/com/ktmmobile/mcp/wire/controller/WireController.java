package com.ktmmobile.mcp.wire.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.wire.dto.WireBannerDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.db.NmcpEventBoardBasDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.event.dto.EventBoardDto;
import com.ktmmobile.mcp.event.dto.WinnerBoardDto;
import com.ktmmobile.mcp.event.service.CoEventSvc;
import com.ktmmobile.mcp.ktPolicy.dto.PolicyDto;
import com.ktmmobile.mcp.ktPolicy.service.PolicySvc;
import com.ktmmobile.mcp.wire.dto.NmcpWireCounselDto;
import com.ktmmobile.mcp.wire.dto.NmcpWireProdDtlDto;
import com.ktmmobile.mcp.wire.service.BannerSvc;
import com.ktmmobile.mcp.wire.service.WireService;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : WireController.java
 * 날짜     : 2018. 5. 15
 * 작성자   :
 * 설명     :
 * </pre>
 */
@Controller
public class WireController {

    private static final Logger logger = LoggerFactory.getLogger(WireController.class);

	private static final String SITE_BANNER_WIRE_GROUP_ID = "WireMbn";
	private static final String SITE_CODE_WIRE = "WR";

    @Autowired
    CoEventSvc coEventSvc;

    @Autowired
    WireService wireService;

    @Autowired
    BannerSvc bannerSvc;

    @Autowired
    PolicySvc policySvc;

    /**
     * 유선 상품 메인
     * @return
     */
    @RequestMapping(value = {"/wire/wireMain.do"})
    public String wireMain(Model model, HttpServletResponse response, HttpServletRequest request) {

        WireBannerDto bannerReqDto = new WireBannerDto();

        bannerReqDto.setCdGroupId(SITE_BANNER_WIRE_GROUP_ID);

        //메인 상단 롤링 배너 조회
        bannerReqDto.setBannCtg("42");
        List<WireBannerDto> topRollBannerDtoList = bannerSvc.listBannerBySubCtg(bannerReqDto);

        //추천상품 배너 조회
        bannerReqDto.setBannCtg("43");
        List<WireBannerDto> recomBannerDtoList = bannerSvc.listBannerBySubCtg(bannerReqDto);

        //제휴카드 배너 조회
        bannerReqDto.setBannCtg("47");
        List<WireBannerDto> eventBannerDtoList = bannerSvc.listBannerBySubCtg(bannerReqDto);


        model.addAttribute("topRollBannerDtoList", topRollBannerDtoList);
        model.addAttribute("recomBannerDtoList", recomBannerDtoList);
        model.addAttribute("topRollBannerDtoList", topRollBannerDtoList);
        model.addAttribute("eventBannerDtoList", eventBannerDtoList);

        return  "/wire/wireMain";
    }

    /**
     * 유선 > 이벤트 목록
     * @return
     */
    @RequestMapping(value = {"/wire/eventList.do"})
    public String eventList(NmcpEventBoardBasDto nmcpEventBoardBasDto,
            PageInfoBean pageInfoBean, Model model) {

        nmcpEventBoardBasDto.setSbstCtg(Constants.WIRE_EVENT_SBSTCTG_CD); //유선 이벤트 코드

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        pageInfoBean.setRecordCount(9);

        nmcpEventBoardBasDto.setNtcartSbst(ParseHtmlTagUtil.percentToEscape(nmcpEventBoardBasDto.getNtcartSbst()));

        /*카운터 조회*/
        int total = coEventSvc.countByListNmcpEventBoard(nmcpEventBoardBasDto);
        pageInfoBean.setTotalCount(total);
        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        List<NmcpEventBoardBasDto> eventList = coEventSvc.listNmcpEventBoard(nmcpEventBoardBasDto, skipResult, maxResult);
        nmcpEventBoardBasDto.setNtcartSbst(ParseHtmlTagUtil.escapeToPercent(nmcpEventBoardBasDto.getNtcartSbst()));

        model.addAttribute("PageInfoBean", pageInfoBean);
        model.addAttribute("EventList", eventList);
        model.addAttribute("NmcpEventBoardBas", nmcpEventBoardBasDto);

        return  "/wire/eventList";
    }

    /**
     * 유선 > 이벤트 목록 > 이벤트 상세
     * @return
     */
    @RequestMapping(value = {"/wire/eventDetail.do"})
    public String eventDetail(HttpServletRequest request,
            EventBoardDto eventBoardDto, PageInfoBean pageInfoBean, Model model) {

        eventBoardDto.setSbstCtg(Constants.WIRE_EVENT_SBSTCTG_CD); //유선 이벤트 코드

        String ua = request.getHeader("User-Agent");

        EventBoardDto eventDto = coEventSvc.eventDetailSelect(eventBoardDto);

        if (eventDto == null) {
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            responseSuccessDto.setSuccessMsg("종료된 이벤트 입니다.");
            responseSuccessDto.setRedirectUrl("/wire/eventList.do");
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        model.addAttribute("eventDto", eventDto);

        return "/wire/eventDetail";
       }

    /**
     * 유선 > 이벤트 > 당첨자 발표
     * @return
     */
    @RequestMapping(value = {"/wire/winnerDetail.do"})
    public String winnerDetail(HttpServletRequest request,
            @ModelAttribute WinnerBoardDto winnerBoardDto,
            PageInfoBean pageInfoBean, Model model) {

         WinnerBoardDto winnerDto = coEventSvc.winnerDetailSelect(winnerBoardDto);

         if (winnerDto != null && winnerDto.getPwnrSbst() != null ) {
             winnerDto.setPwnrSbst(ParseHtmlTagUtil.convertHtmlchars(winnerDto.getPwnrSbst()));
         }

         model.addAttribute("winnerDto", winnerDto);
         model.addAttribute("searchDto", winnerBoardDto);
         model.addAttribute("pageInfoBean", pageInfoBean);

         return "/wire/winnerDetail";
    }

    /**
     * 유선 > 이벤트 > 당첨자 목록
     * @return
     */
    @RequestMapping(value = {"/wire/winnerList.do"})
    public String winnerList(
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "pageNo", required = false) String pageNo,
            Model model) {

        String pageNoTem = pageNo ;
        if (StringUtils.isBlank(pageNo)) {
            pageNoTem = "1";
        }

        model.addAttribute("pageNo", pageNoTem);
        model.addAttribute("searchValue", searchValue);

        return "/wire/winnerList";
    }


    /**
     * 유선 > FAQ
     * @return
     */
    @RequestMapping(value = {"/wire/faqList.do"})
    public String faqList(){

        return  "/wire/faqList";
    }

    /**
     * 유선 > 유선 상품 목록
     * @return
     */
    @RequestMapping(value = { "/wire/internetList.do", "/wire/tvList.do", "/wire/combiList.do", "/wire/additionList.do", "/wire/itTvList.do", "/wire/cctvList.do"})
    public String wireProdCombi(HttpServletRequest request, HttpServletResponse response, Model model, NmcpWireProdDtlDto nmcpWireProdDtlDto) {

        String uri = request.getRequestURI();
        String wireProdCd = "";
        String bannCtg = "";
        if ("/wire/internetList.do".equals(uri)) {
            wireProdCd = "IT01";
            bannCtg = "45";
        } else if ("/wire/tvList.do".equals(uri)) {
            wireProdCd = "TV01";
            bannCtg = "44";
        } else if ("/wire/combiList.do".equals(uri)) {
            wireProdCd = "CB01";
            bannCtg = "46";
        } else if ("/wire/additionList.do".equals(uri)) {
            wireProdCd = "AD01";
            bannCtg = "48";
        }  else if ("/wire/itTvList.do".equals(uri)) {
            wireProdCd = "ITTV01";
            bannCtg = "49";
        } else if ("/wire/cctvList.do".equals(uri)) {
            wireProdCd = "CCTV01";
            bannCtg = "65";
        }

        if (StringUtil.isNotBlank(bannCtg)) {
            //메인 상단 롤링 배너 조회
            WireBannerDto bannerReqDto = new WireBannerDto();
            bannerReqDto.setCdGroupId(SITE_BANNER_WIRE_GROUP_ID);
            bannerReqDto.setBannCtg(bannCtg);
            List<WireBannerDto> topRollBannerDtoList = bannerSvc.listBannerBySubCtg(bannerReqDto);
            model.addAttribute("topRollBannerDtoList", topRollBannerDtoList);
        }

        //한눈에 보기 조회
        NmcpWireProdDtlDto wireProdContDto = null;
        try {
            wireProdContDto= wireService.selectWireProdCont(wireProdCd);
            wireProdContDto.setWireProdPcSbst(ParseHtmlTagUtil.convertHtmlchars(wireProdContDto.getWireProdPcSbst()));
        } catch (Exception e) {
            logger.debug("한눈에 보기 조회 실패 :: "+e.getMessage());
        }

        model.addAttribute("wireProdContDto", wireProdContDto);
        model.addAttribute("wireProdCd", wireProdCd);

        return  "/wire/wireProdList";
    }

    /**
     * 유선 > 유선 상품 목록
     */
    @RequestMapping(value={"/wire/wireProdListAjax.do"})
    @ResponseBody
    public Map<String, Object> wireProdListAjax(HttpServletRequest request, HttpServletResponse response, Model model, NmcpWireProdDtlDto nmcpWireProdDtlDto) {

        HashMap<String, Object> hm = new HashMap<String,Object>();

        List<NmcpWireProdDtlDto> nmcpWireProdDtlDtoList = wireService.selectWireProdDtlList(nmcpWireProdDtlDto);

        hm.put("nmcpWireProdDtlDtoList", nmcpWireProdDtlDtoList);

        return  hm;
    }

    /**
     * 유선 > 유선 상품 상세보기
     */
    @RequestMapping(value={"/wire/wireProdDtlAjax.do"})
    @ResponseBody
    public Map<String, Object> wireProdDtlAjax(HttpServletRequest request, HttpServletResponse response, Model model, NmcpWireProdDtlDto nmcpWireProdDtlDto) {

        HashMap<String, Object> hm = new HashMap<String,Object>();

        NmcpWireProdDtlDto wireProdDtlDto = wireService.selectWireProdDtl(nmcpWireProdDtlDto);

        hm.put("wireProdDtlDto", wireProdDtlDto);

        return  hm;
    }

    /**
     * 전체상품 한눈에 보기
     */
    @RequestMapping(value={"/wire/wireProdCdViewAjax.do"})
    @ResponseBody
    public Map<String, Object> wireProdCdViewAjax(HttpServletRequest request, HttpServletResponse response, Model model, NmcpWireProdDtlDto nmcpWireProdDtlDto) {

        HashMap<String, Object> hm = new HashMap<String,Object>();

        List<NmcpWireProdDtlDto> nmcpWireProdDtlDtoList = wireService.selectWireProdDtlList(nmcpWireProdDtlDto);

        hm.put("nmcpWireProdDtlDtoList", nmcpWireProdDtlDtoList);

        return  hm;
    }

    /**
     * 유선 > 간편가입 상담 신청
     */
    @RequestMapping(value={"/wire/wireProdJoinAjax.do"})
    @ResponseBody
    public Map<String, Object> wireProdJoinAjax(HttpServletRequest request,  Model model, NmcpWireCounselDto nmcpWireCounselDto, String secureChr) {

        HashMap<String, Object> hm = new HashMap<String,Object>();
        try {
            HttpSession session = request.getSession(true);
             String answer = (String)session.getAttribute("getAnswer");
             if (secureChr.equals(answer)) {
                 wireService.insertNmcpWireCounsel(nmcpWireCounselDto);
                 hm.put("RESULT_CODE", Constants.AJAX_SUCCESS);
             } else {
                 hm.put("RESULT_MSG", "보안 문자가 일치하지 않습니다.");
             }
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return  hm;
    }


    /**
     * 유선 > 간편가입 상담 신청화면
     */
    @RequestMapping(value={"/wire/wireProdJoinForm.do"})
    public String wireProdJoinForm(HttpServletRequest request, HttpServletResponse response, Model model, NmcpWireProdDtlDto nmcpWireProdDtlDto) {
        model.addAttribute("pJoinProdCorp", request.getParameter("joinProdCorp"));
        model.addAttribute("pJoinProdCd", request.getParameter("joinProdCd"));
        model.addAttribute("pJoinProdDtlSeq", request.getParameter("joinProdDtlSeq"));
        model.addAttribute("pWireProdCd", request.getParameter("wireProdCd"));
        return  "/wire/wireProdJoinForm";
    }
    /**
     * 유선 > 간편가입 상담 신청완료화면
     */
    @RequestMapping(value={"/wire/wireProdJoinComplete.do"})
    public String wireProdJoinComplete(HttpServletRequest request, HttpServletResponse response, Model model, NmcpWireProdDtlDto nmcpWireProdDtlDto) {


        return  "/wire/wireProdJoinComplete";
    }

    /**
     * @Description : 개인정보 취급방침 상세
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 25
     */
    @RequestMapping(value="/wire/privacyView.do")
    public String privacyView(@ModelAttribute PolicyDto policyDto
            ,@RequestParam(value="stpltSeq" ,required=false) String stpltSeq
            ,@RequestParam(value="pageNo", required=false) String pageNo
            , Model model) {

        /*개인정보 취급방침 상세가져오기*/
        policyDto.setSiteCode(SITE_CODE_WIRE);
        policyDto.setStpltSeq(Integer.parseInt(StringUtil.NVL(stpltSeq, "0")));
        PolicyDto viewResult = policySvc.viewPrivacyBoard(policyDto);


        if(null != viewResult.getStpltSbst()){
            viewResult.setStpltSbst(ParseHtmlTagUtil.convertHtmlchars(viewResult.getStpltSbst()));
        }

        model.addAttribute("stpltSeq", viewResult.getStpltSeq());
        model.addAttribute("viewResult",viewResult);
        model.addAttribute("policyDto",policyDto);
        model.addAttribute("isDetail",true);
        model.addAttribute("pageNo",pageNo);

        return "/wire/privacyList";
    }

    /**
     * @Description : 개인정보 취급 방침
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 25
     */
    @RequestMapping(value="/wire/privacyList.do")
    public String privacyList(@ModelAttribute PageInfoBean pageInfoBean, @ModelAttribute PolicyDto policyDto, Model model) {

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*임시 한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(10);

        /*페이지 카운트*/
        policyDto.setSiteCode(SITE_CODE_WIRE);
        int totalCount = policySvc.getPriTotalCount(policyDto);
        pageInfoBean.setTotalCount(totalCount);

        /*RowBound 필수값 세팅*/
        int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult   = pageInfoBean.getRecordCount();  // Pagesize

        /*개인정보 취급방침 리스트 가져오기*/
        List<PolicyDto> privacyList = policySvc.privacyBoardList(policyDto,skipResult,maxResult);

        model.addAttribute("privacyListSize",privacyList.size());
        model.addAttribute("privacyList",privacyList);
        model.addAttribute("pageInfoBean",pageInfoBean);
        model.addAttribute("policyDto",policyDto);

        return "/wire/privacyList";
    }


    /**
     * @Description : 간편가입상담신청
     * @param model
     * @return
     * @Author : ant
     * @Create Date : 2016. 1. 25
     */
    @RequestMapping(value={"/wire/wireProdSimpleJoinAjax.do"})
    @ResponseBody
    public Map<String, Object> wireProdSimpleJoinAjax(HttpServletRequest request,  Model model, NmcpWireCounselDto nmcpWireCounselDto, String secureChr) {

        HashMap<String, Object> hm = new HashMap<String,Object>();
        try {
        	nmcpWireCounselDto.setWireProdDtlSeq("0");
        	wireService.insertNmcpWireCounsel(nmcpWireCounselDto);
        	hm.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return  hm;
    }

}
