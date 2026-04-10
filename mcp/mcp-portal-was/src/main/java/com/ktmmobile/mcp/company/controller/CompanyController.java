package com.ktmmobile.mcp.company.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.PopupDto;
import com.ktmmobile.mcp.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.McpErropPageException;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.SmsSvc;
import com.ktmmobile.mcp.common.util.FileUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.company.dto.ReportDto;
import com.ktmmobile.mcp.company.service.CompanyService;
import com.ktmmobile.mcp.event.dto.EventBoardDto;
import com.ktmmobile.mcp.event.service.CoEventSvc;
import com.ktmmobile.mcp.mmobile.dto.NewsDataBasDto;
import com.ktmmobile.mcp.mmobile.service.MmNewsDataSvc;



@Controller
public class CompanyController {
    private static Logger logger = LoggerFactory.getLogger(CompanyController.class);



    @Autowired
    FileUtil fileUtil;

    @Autowired
    CompanyService companyService;

    @Autowired
    SmsSvc smsSvc ;

    @Autowired
    FCommonSvc fCommonSvc;

    @Autowired
    MmNewsDataSvc mmNewsDataSvc;

    @Autowired
    CoEventSvc coEventSvc;

    //메인
    @RequestMapping(value = "/company/main.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String mainPage(Model model) {

        String popupUrl = "/company/main.do";

        List<PopupDto> popupList = NmcpServiceUtils.getPopupList(popupUrl);

        PopupDto popup = null;

        if (popupList != null && !popupList.isEmpty()) {
            popup = popupList.get(0);
            popup.setPopupSbst(HtmlUtils.htmlUnescape(popup.getPopupSbst()));
        }

        model.addAttribute("popup", popup);

        return "/company/companyMain";
    }

    //회사소개
    @RequestMapping(value = "/company/company.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String companyPage() {
        return "/company/companyIntro";
        //return "/company/company";
    }

    //인재채용
    @RequestMapping(value = "/company/recruit.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String recruitPage() {
        return "/company/companyRecruit";
        //return "/company/recruit";
    }

    //이벤트 목록
    @RequestMapping(value = "/company/event.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String event(@ModelAttribute EventBoardDto eventBoardDto, PageInfoBean pageInfoBean, Model model) {

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        /*카운터 조회*/
        int total = 0;
        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); //셀렉트 하지 않고 뛰어넘을 만큼의 rownum

        eventBoardDto.setEventBranch("E");
        eventBoardDto.setEventStatus("ing");

        // 이벤트 진행중 카운터
        total = coEventSvc.countPlanListSelect(eventBoardDto);

        // 이벤트 진행중 리스트
        List<EventBoardDto> eventList = coEventSvc.planListSelect(eventBoardDto,skipResult);
        model.addAttribute("eventList", eventList);
        model.addAttribute("pageInfoBean", pageInfoBean);

        return "/company/companyEvent";
    }
    @RequestMapping(value="/company/reportSubmitAjax.do")
    @ResponseBody
    public Map<String, Object> reportSubmitAjax(MultipartHttpServletRequest fileRequest, ReportDto reportDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String fileName = "";      //암호화 파일명
        String orgFileName = ""; //원본 파일명
        String certNum = "";       //인증번호

        try {

            MultipartFile multipartFile = fileRequest.getFile("file");

            //인증번호 생성 (10자리 영문 + 숫자 조합 랜덤)
            StringBuffer certNumBuf =new StringBuffer();
            Random rnd;
            try {
                rnd = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                throw new McpErropPageException(COMMON_EXCEPTION);
            }

            for(int i=0;i<10;i++){
                rnd.nextBoolean();
                if(rnd.nextBoolean()){
                    try {
                        certNumBuf.append((char)((int)(rnd.nextInt(26))+97));
                    }catch (ArithmeticException e) {
                        logger.error("ArithmeticException e : {}", e.getMessage());
                    }catch (Exception e) {
                        logger.error("Exception e : {}", e.getMessage());
                    }
                }else{
                    certNumBuf.append((rnd.nextInt(10)));
                }
            }
            certNum = certNumBuf.toString();

            //첨부파일 업로드
            if (multipartFile != null && multipartFile.getSize() > 0) {
                fileName = fileUtil.fileTrans(multipartFile, "report");	  //ISMP 소스수정 <===
                orgFileName = multipartFile.getOriginalFilename();
            }

            reportDto.setReportFileName(fileName);
            reportDto.setReportOrgFileName(orgFileName);
            reportDto.setReportCertNumber(certNum);

            int rsCnt = companyService.insertNmcpViolationReport(reportDto);

            if (rsCnt > 0) {
                rtnMap.put("certNumber", certNum);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

                //신고완료 sms발송
                //SMS 템플릿 조회 - 40 : 제보자 등록 완료
                MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_TEMPLATE_ID_VIOLATION_REPORT_SAVE_CPL);
                //SMS 템플릿 조회 - 41 : 제보 관리자 등록 알림
                MspSmsTemplateMstDto mspSmsTemplateMstDto2 = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_TEMPLATE_ID_VIOLATION_REPORT_SAVE_ADMIN_INFO);

                //윤리위반신고 CALLBACK 번호 - agent 등록되어 있어야 하는 번호임 확인꼭 할것!
                NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto("VIOLATIONREPORTADMIN", "CALLBACK");
                String callBack = "";
                if (nmcpCdDtlDto != null) {
                    callBack = nmcpCdDtlDto.getDtlCdNm();
                }

                //윤리위반신고 담당자 핸드폰 번호
                NmcpCdDtlDto nmcpCdDtlDto2 = NmcpServiceUtils.getCodeNmDto("VIOLATIONREPORTADMIN", "ADMIN");
                String adminPhone = "";
                if (nmcpCdDtlDto2 != null) {
                    adminPhone = nmcpCdDtlDto2.getDtlCdNm();
                }

                if (mspSmsTemplateMstDto != null) {
                    mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{certNum}", certNum));
                    //smsSvc.sendLms(mspSmsTemplateMstDto.getSubject(), reportDto.getReportMobileNo(), mspSmsTemplateMstDto.getText(), callBack);
                    smsSvc.sendLms(mspSmsTemplateMstDto.getSubject(), reportDto.getReportMobileNo(), mspSmsTemplateMstDto.getText(),
                            callBack,String.valueOf(Constants.SMS_TEMPLATE_ID_VIOLATION_REPORT_SAVE_CPL),"SYSTEM");
                }
                if (mspSmsTemplateMstDto2 != null) {
                    //smsSvc.sendLms(mspSmsTemplateMstDto2.getSubject(), adminPhone, mspSmsTemplateMstDto2.getText(), callBack);
                    smsSvc.sendLms(mspSmsTemplateMstDto2.getSubject(), adminPhone, mspSmsTemplateMstDto2.getText(),
                            callBack,String.valueOf(Constants.SMS_TEMPLATE_ID_VIOLATION_REPORT_SAVE_ADMIN_INFO),"SYSTEM");
                }

            } else {
                throw new McpCommonJsonException(COMMON_EXCEPTION);
            }

        } catch (McpCommonException e) {
            throw new McpCommonJsonException(e.getErrorMsg());
        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rtnMap;

    }

    @RequestMapping(value="/company/reportSearchAjax.do")
    @ResponseBody
    public Map<String, Object> reportSearchAjax(ReportDto reportDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        try {

            ReportDto reportResultDto = companyService.selectNmcpViolationReport(reportDto);

            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("reportResultDto", reportResultDto);

        } catch (Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return rtnMap;
    }

    /*홍보채널 리스트*/
    @RequestMapping(value = "/company/mediaList.do", method = RequestMethod.GET)
    public String newsDataList(@RequestParam(value= "pageNo", required = false, defaultValue = "1") int pageNo, @ModelAttribute("newsDataBasDto") NewsDataBasDto newsDataBasDto, PageInfoBean pageInfoBean, Model model) {
        /*현재 페이지 번호 초기화*/

        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1); }


            pageInfoBean.setPageNo(pageNo);


        /*한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(6);

        /*카운터 조회*/
        int total = mmNewsDataSvc.countNewsDataList(newsDataBasDto);

        pageInfoBean.setTotalCount(total);

        int totalPage = (total == 0) ? 1 : (int) Math.ceil((double) total / pageInfoBean.getRecordCount());
          // 시작 페이지: 현재 페이지가 13이면, 11
          int startPage = ((pageNo - 1) / 5) * 5 + 1;
          // 끝 페이지: 최대 10개만 보여주되, 실제 전체 페이지 수보다 크면 안 됨
          int endPage = Math.min(startPage + 4, totalPage);


        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize



        List<NewsDataBasDto> newsDataList = mmNewsDataSvc.newsDataList(newsDataBasDto, skipResult, maxResult);

        for (int i= 0; i < newsDataList.size(); i++) {
            String original = newsDataList.get(i).getNewsDatSbst();
            if(original!=null && !"".equals(original)) {
                String cleaned = cleanText(original);
                newsDataList.get(i).setNewsDatSbst(cleaned);
            }
        }


        // 다시 필터링된 리스트 조회
        model.addAttribute("newsDataList", newsDataList);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage);

        return "/company/mediaList";
    }

    public static String cleanText(String input) {
        // 각종 태그 제거
        String noXssTag = StringUtil.decodeXssTag(input);
        String noTags = noXssTag.replaceAll("<[^>]*>", "");
        String noRegexChars = noTags.replaceAll("[*+?()\\[\\]{}|.^$]", "");
        String cleanTxt = StringEscapeUtils.unescapeHtml(noRegexChars);
        return cleanTxt;
    }


    /*홍보채널 더보기*/
    @RequestMapping(value = { "/company/moreMediaList.do"})
    @ResponseBody
    public HashMap<String, Object> moreMediaList(@ModelAttribute("newsDataBasDto") NewsDataBasDto newsDataBasDto,
                                                  PageInfoBean pageInfoBean,
                                                  @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();


        // 페이지 번호 설정
        pageInfoBean.setPageNo(pageNo);

        // 한 페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(6);

        // 전체 데이터 수 카운트
        int total = mmNewsDataSvc.countNewsDataList(newsDataBasDto);
        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();  // 시작점
        int maxResult = pageInfoBean.getRecordCount();  // 페이지당 보여줄 수

        // 뉴스 데이터 리스트 조회
        List<NewsDataBasDto> newsDataList = mmNewsDataSvc.newsDataList(newsDataBasDto, skipResult, maxResult);

        for (int i= 0; i < newsDataList.size(); i++) {
            String original = newsDataList.get(i).getNewsDatSbst();
            if(original!=null && !"".equals(original) ) {
                String cleaned = 	cleanText(original);
                newsDataList.get(i).setNewsDatSbst(cleaned);
            }
        }


        rtnMap.put("newsDataList", newsDataList);
        rtnMap.put("totalPageCount", pageInfoBean.getTotalPageCount());  // 전체 페이지 수
        rtnMap.put("currentPage", pageInfoBean.getPageNo());  // 현재 페이지
        rtnMap.put("RESULT_CODE", "SUCCESS");

        return rtnMap;
    }

    /*홍보채널 상세*/
    @RequestMapping(value = "/company/mediaDetail.do", method= RequestMethod.GET)
    public String mediaDetail(@RequestParam("newsDatSeq") int newsDatSeq,
            @RequestParam("pageNo") int pageNo,
            @RequestParam(value = "direction", required = false) String direction,/*@RequestParam("linkTarget") String linkTarget, */
            @ModelAttribute("newsDataBasDto") NewsDataBasDto newsDataBasDto,PageInfoBean pageInfoBean, Model model) {


        NewsDataBasDto newsDetail = mmNewsDataSvc.newsDetailSelect(newsDataBasDto);


        int recordCount = pageInfoBean.getRecordCount();  // 예: 5
        int total = mmNewsDataSvc.countNewsDataList(newsDataBasDto);  // 전체 게시물 수

        if(newsDetail != null){
            newsDetail.setNewsDatSbst(ParseHtmlTagUtil.convertHtmlchars(newsDetail.getNewsDatSbst()));
            model.addAttribute("pageInfoBean", pageInfoBean);
            model.addAttribute("newsDetail", newsDetail);
            model.addAttribute("newsDataBasDto", newsDataBasDto);
            model.addAttribute("newsDatSeq", newsDatSeq);
            model.addAttribute("pageNo", pageNo);
            /* model.addAttribute("linkTarget", linkTarget); */
        }


      /*  if ("N".equals(linkTarget)) {
            return "redirect:/company/mediaDetail.do?newsDatSeq=" + newsDatSeq;
        } else {*/
            return "/company/mediaDetail";

    }

    //상품 · 서비스 히스토리
    @RequestMapping(value = "/company/prodSvcHistory.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String prodSvcHistory() {
        return "/company/prodSvcHistory";
    }

    //수상내역
    @RequestMapping(value = "/company/awardDetails.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String awardDetails() {
        return "/company/awardDetails";
    }


    //홍보영상
    @RequestMapping(value = "/company/promoVideo.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String promoVideo() {
        return "/company/promoVideo";
    }

    //경영철학
    @RequestMapping(value = "/company/mgtPhilosophy.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String mgtPhilosophy() {
        return "/company/mgtPhilosophy";
    }

    //공지사항 리스트
    @RequestMapping(value = "/company/noticeList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String noticeList(@RequestParam(value= "pageNo", required = false, defaultValue = "1") int pageNo, @ModelAttribute("boardDto") BoardDto boardDto, PageInfoBean pageInfoBean, Model model) {

        /*현재 페이지 번호 초기화*/
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }

        pageInfoBean.setPageNo(pageNo);

        /*중요게시글 카운터 조회*/
        int Ntotal = companyService.getNTotalCount(boardDto);

        /*한페이지당 보여줄 리스트 수*/
        pageInfoBean.setRecordCount(10-Ntotal); //총 10개로 요청(중요+일반게시글)

        /*일반게시글 카운터 조회*/
        int total = companyService.getTotalCount(boardDto);

        pageInfoBean.setTotalCount(total);

        int totalPage = (total == 0) ? 1 : (int) Math.ceil((double) total / pageInfoBean.getRecordCount());
          // 시작 페이지: 현재 페이지가 13이면, 11
          int startPage = ((pageNo - 1) / 5) * 5 + 1;
          // 끝 페이지: 최대 10개만 보여주되, 실제 전체 페이지 수보다 크면 안 됨
          int endPage = Math.min(startPage + 4, totalPage);


        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult = pageInfoBean.getRecordCount();	// Pagesize

        //일반게시글리스트
        List<BoardDto> boardList = companyService.selectBoardList(boardDto, skipResult, maxResult);
        //중요게시글리스트
        List<BoardDto> noticeList = companyService.selectNoticeList(boardDto,0,999);




        // 다시 필터링된 리스트 조회
        model.addAttribute("boardList", boardList);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage);
        return "/company/noticeList";
    }


    /*공지사항 상세*/
    @RequestMapping(value = "/company/noticeDetail.do", method= RequestMethod.GET)
    public String noticeDetail(@RequestParam("boardSeq") int boardSeq,
            @RequestParam("pageNo") int pageNo,
            @RequestParam(value = "direction", required = false) String direction,
            @ModelAttribute("boardDto") BoardDto boardDto,PageInfoBean pageInfoBean, Model model) {


        //공지 글 상세
        BoardDto viewResult = companyService.viewNoticeBoard(boardDto);
        viewResult.setBoardContents(ParseHtmlTagUtil.convertHtmlchars(viewResult.getBoardContents()));

        //이전글 다음글 가져오기
        BoardDto notiDto = companyService.selectNotiIndex(boardDto);

        if(viewResult != null){
            model.addAttribute("pageInfoBean", pageInfoBean);
            model.addAttribute("viewResult", viewResult);
            model.addAttribute("boardDto", boardDto);
            model.addAttribute("boardSeq", boardSeq);
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("notiDto",notiDto);
        }

        return "/company/noticeDetail";

    }

    /*공지사항 더보기*/
    @RequestMapping(value = { "/company/moreNoticeList.do"})
    @ResponseBody
    public HashMap<String, Object> moreNoticeList(@ModelAttribute("boardDto") BoardDto boardDto,
                                                  PageInfoBean pageInfoBean,
                                                  @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();


        // 페이지 번호 설정
        pageInfoBean.setPageNo(pageNo);

        /*중요게시글 카운터 조회*/
        int Ntotal = companyService.getNTotalCount(boardDto);

        /*일반게시글 카운터 조회*/
        int total = companyService.getTotalCount(boardDto);

        // 한 페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(10-Ntotal);

        // 전체 데이터 수 카운트
        pageInfoBean.setTotalCount(total);

        int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();  // 시작점
        int maxResult = pageInfoBean.getRecordCount();  // 페이지당 보여줄 수

        List<BoardDto> boardList = companyService.selectBoardList(boardDto, skipResult, maxResult);

        rtnMap.put("boardList", boardList);
        rtnMap.put("totalPageCount", pageInfoBean.getTotalPageCount());  // 전체 페이지 수
        rtnMap.put("currentPage", pageInfoBean.getPageNo());  // 현재 페이지
        rtnMap.put("RESULT_CODE", "SUCCESS");

        return rtnMap;
    }


}
