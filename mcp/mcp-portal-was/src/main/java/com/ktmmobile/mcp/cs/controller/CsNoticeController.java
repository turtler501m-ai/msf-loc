package com.ktmmobile.mcp.cs.controller;

import static com.ktmmobile.mcp.common.constants.Constants.BOARD_NOTICE_CTG;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.SAMPLE_EXCEPTION;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.board.service.CsNoticeService;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CsNoticeController {

	private static final Logger logger = LoggerFactory.getLogger(CsNoticeController.class);


    @Autowired
    private CsNoticeService csNoticeService;

    /**
     * 모바일버전 공지사항 리스트페이지 이동
     * @param
     * @param model
     * @param bind
     * @return /cs/notice/m_noticeList
     */
    @RequestMapping(value = {"/m/cs/noticeBoardList.do"})
    public String noticeBoardList(@ModelAttribute BoardDto boardDto, BindingResult bind, @ModelAttribute  PageInfoBean pageInfoBean, Model model) {

        String url = "/mobile/cs/notice/noticeList";
        if(!"Y".equals(NmcpServiceUtils.isMobile())) {
            return "forward:"+"/portal/cs/notice/noticeList";

        }
//        else if (!"APP".equals(NmcpServiceUtils.getDeviceType())) {
//            return "forward:"+"/portal/cs/notice/noticeList";
//        }
//        else if (!"MOB".equals(NmcpServiceUtils.getDeviceType())){
//            logger.info("모바일 공지 !MOB " + NmcpServiceUtils.getDeviceType());
//            return "forward:"+"/portal/cs/notice/noticeList";
//        }


    	// 현재 페이지 번호 초기화
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        //임시 한페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(10);

		//페이지 카운트
		int totalCount = csNoticeService.getTotalCount(boardDto);
	    pageInfoBean.setTotalCount(totalCount);

        //RowBound 필수값 세팅
    	int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult  = pageInfoBean.getRecordCount();  // Pagesize

        //게시판 리스트
        boardDto.setSearchNm(ParseHtmlTagUtil.percentToEscape(boardDto.getSearchNm()));
        List<BoardDto> resultList = csNoticeService.selectBoardList(boardDto,0,999);
        boardDto.setSearchNm(ParseHtmlTagUtil.escapeToPercent(boardDto.getSearchNm()));

        //공지 게시판 리스트
        List<BoardDto> noticeList = csNoticeService.selectNoticeBoard(boardDto);

        model.addAttribute("resultList",resultList);
        model.addAttribute("noticeList",noticeList);
       	model.addAttribute("boardDto",boardDto);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("totalCount", totalCount);
        return "/mobile/cs/notice/noticeList";
    }

    /**
     * 모바일버전 공지사항 상세페이지 이동
     * @param
     * @param model
     * @param bind
     * @return /cs/notice/m_noticeList
    */
    @RequestMapping(value = {"/cs/boardView.do","/m/cs/boardView.do"})
    public String boardView(HttpServletRequest request, @ModelAttribute BoardDto boardDto,Model model ) {

    	String rtnPageNm = "";
    	String redirect = "";
        if("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnPageNm = "/mobile/cs/notice/noticeView";
            redirect = "/m/cs/noticeBoardList.do";
        } else {
            rtnPageNm = "/portal/cs/notice/noticeView";
            redirect = "/cs/csNoticeList.do";
        }

//        if ("MOB".equals(NmcpServiceUtils.getDeviceType())){
//            rtnPageNm = "/mobile/cs/notice/noticeView";
//            redirect = "/m/cs/noticeBoardList.do";
//        } else {
//        	rtnPageNm = "/portal/cs/notice/noticeView";
//        	redirect = "/cs/csNoticeList.do";
//        }

        if (boardDto.getBoardSeq()==0) {
        	return "redirect:"+redirect;

        }
        boardDto.setBoardCtgSeq(BOARD_NOTICE_CTG);

        //조회수
        csNoticeService.updateHit(boardDto.getBoardSeq());

        //공지 글 상세
        BoardDto viewResult = csNoticeService.viewNoticeBoard(boardDto.getBoardSeq());
        viewResult.setBoardContents(ParseHtmlTagUtil.convertHtmlchars(viewResult.getBoardContents()));

        //이전글 다음글 가져오기
        boardDto.setSearchNm(ParseHtmlTagUtil.percentToEscape(boardDto.getSearchNm()));
        BoardDto notiDto = csNoticeService.selectNotiIndex(boardDto);
        boardDto.setSearchNm(ParseHtmlTagUtil.escapeToPercent(boardDto.getSearchNm()));

        model.addAttribute("viewResult",viewResult);
        model.addAttribute("notiDto",notiDto);
        model.addAttribute("boardDto",boardDto);
        return rtnPageNm;
    }


    /**
     * 모바일버전 공지사항 리스트페이지 이동
     * @param
     * @param model
     * @param bind
     * @return /cs/notice/m_noticeList
     */
    @RequestMapping(value = {"/cs/csNoticeList.do", "/m/cs/csNoticeList.do"})
    public String csNoticeList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute BoardDto boardDto, BindingResult bind, @ModelAttribute  PageInfoBean pageInfoBean, Model model) {

        String url = "/portal/cs/notice/noticeList";
        if("Y".equals(NmcpServiceUtils.isMobile())) {
            return "forward:"+"/m/cs/noticeBoardList.do";

        } else if ("MOB".equals(NmcpServiceUtils.getDeviceType())){
            return "forward:"+"/m/cs/noticeBoardList.do";
        }


    	// 현재 페이지 번호 초기화
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        //임시 한페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(10);

		//페이지 카운트
		int totalCount = csNoticeService.getTotalCount(boardDto);
	    pageInfoBean.setTotalCount(totalCount);

        //RowBound 필수값 세팅
    	int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult  = pageInfoBean.getRecordCount();  // Pagesize

        //게시판 리스트
        boardDto.setSearchNm(ParseHtmlTagUtil.percentToEscape(boardDto.getSearchNm()));
        List<BoardDto> resultList = csNoticeService.selectBoardList(boardDto,skipResult,maxResult);
        boardDto.setSearchNm(ParseHtmlTagUtil.escapeToPercent(boardDto.getSearchNm()));

        //공지사항 게시판 리스트
        List<BoardDto> noticeList = csNoticeService.selectNoticeBoard(boardDto);

        model.addAttribute("resultList",resultList);
        model.addAttribute("noticeList",noticeList);
       	model.addAttribute("boardDto",boardDto);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("totalCount", totalCount);

//        return "/portal/cs/notice/noticeList";
        return url;
    }


}