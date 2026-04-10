package com.ktmmobile.mcp.cs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.board.service.BoardCommonSvc;
import com.ktmmobile.mcp.board.service.BoardService;
import com.ktmmobile.mcp.common.dto.db.NmcpBoardBasDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.cs.service.CsFaqSvc;

@Controller
public class CsFaqController {

	private static Logger logger = LoggerFactory.getLogger(CsFaqController.class);

	@Autowired
	CsFaqSvc csFaqSvc;
	@Autowired
	FCommonSvc commonSvc;
	@Autowired
	BoardService boardService;
	@Autowired
    BoardCommonSvc boardCommonSvc;


	/**
	 * 고객센터 > 자주묻는 질문
	 *
	 * @param boardDto
	 * @param pageInfoBean
	 * @param model
	 * @param bind
	 * @return /cs/faq/faqList
	 */
	@RequestMapping(value = {"/m/cs/faqList.do"})
	public String faqList(@ModelAttribute("boardDto") BoardDto boardDto, PageInfoBean pageInfoBean, Model model,	BindingResult bind) {

		if(!"Y".equals(NmcpServiceUtils.isMobile())){
			return "redirect:/cs/faqList.do";
		}
		if("P".equals(NmcpServiceUtils.getPlatFormCd()) ) {
			return "redirect:/cs/faqList.do";
		}

    	String sbstCtg = StringUtil.NVL(boardDto.getSbstCtg(),"00");
    	boardDto.setSbstCtg(sbstCtg);
    	List<BoardDto> faqTopList = null;
    	if("00".equals(sbstCtg) || "100".equals(sbstCtg)) {
    		boardDto.setSelectCnt(10);
    		faqTopList = csFaqSvc.getTopFormList(boardDto);
    	}
		model.addAttribute("faqTopList", faqTopList);
		model.addAttribute("boardDto",boardDto);
		model.addAttribute("pageNo",1);

		return "/mobile/cs/faq/faqList";
	}

	@RequestMapping(value = {"/m/cs/faqListAjax.do"})
	@ResponseBody
	public Map<String, Object> faqGetPagingAjax(@ModelAttribute("boardDto") BoardDto boardDto, PageInfoBean pageInfoBean, Model model) {

		Map<String, Object> rtnJsonMap = new HashMap<String, Object>();

		List<BoardDto> faqList = null;
		int totalCount = 0;
		String sbstCtg = boardDto.getSbstCtg();

		if("00".equals(sbstCtg)) {
			totalCount = csFaqSvc.getNotInTotalCount(boardDto);
		} else {
			totalCount = csFaqSvc.getTotalCount(boardDto);
		}

		pageInfoBean.setTotalCount(totalCount);

		if (pageInfoBean.getPageNo() == 0) {
			pageInfoBean.setPageNo(1);
		}
		// 임시 한페이지당 보여줄 리스트 수
		pageInfoBean.setRecordCount(10);

		// RowBound 필수값 세팅
		int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
		int maxResult = pageInfoBean.getRecordCount(); // Pagesize

		if("00".equals(sbstCtg)) {
			faqList = csFaqSvc.getNotInFormList(boardDto, skipResult, maxResult);
		} else {
			faqList = csFaqSvc.getList(boardDto, skipResult, maxResult);
		}

		// 목록리스트 개수 계산
		int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + faqList.size();

		rtnJsonMap.put("faqList", faqList);
		rtnJsonMap.put("totalCount", totalCount);
		rtnJsonMap.put("listCount", listCount);
		rtnJsonMap.put("pageNo",pageInfoBean.getPageNo());
		rtnJsonMap.put("pageInfoBean", pageInfoBean);

		return rtnJsonMap;
	}

	/**
	 * 고객센터 > 자주묻는 질문
	 *
	 * @param boardDto
	 * @param pageInfoBean
	 * @param model
	 * @param bind
	 * @return /cs/faq/faqList
	 */
	@RequestMapping(value = {"/cs/faqList.do"})
	public String pcFaqList(@ModelAttribute("boardDto") BoardDto boardDto, PageInfoBean pageInfoBean, Model model,	BindingResult bind) {

		if("Y".equals(NmcpServiceUtils.isMobile())){
			return "redirect:/m/cs/faqList.do";
		}
		if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			return "redirect:/m/cs/faqList.do";
		}

    	String sbstCtg = StringUtil.NVL(boardDto.getSbstCtg(),"00");
    	boardDto.setSbstCtg(sbstCtg);
    	List<BoardDto> faqTopList = null;
    	if("00".equals(sbstCtg) || "100".equals(sbstCtg)) {
    		boardDto.setSelectCnt(10);
    		faqTopList = csFaqSvc.getTopFormList(boardDto);
    	}

		// 현재 페이지 번호 초기화
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        //임시 한페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(10);

		//페이지 카운트
		int totalCount = 0;
		if("00".equals(sbstCtg)){
			totalCount = csFaqSvc.getNotInTotalCount(boardDto);
		} else if("100".equals(sbstCtg)){
			totalCount = 0;
		} else {
			totalCount = csFaqSvc.getTotalCount(boardDto);
		}

	    pageInfoBean.setTotalCount(totalCount);

        //RowBound 필수값 세팅
    	int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
        int maxResult  = pageInfoBean.getRecordCount();  // Pagesize

        //게시판 리스트
        List<BoardDto> resultList = null;
        boardDto.setSearchNm(ParseHtmlTagUtil.percentToEscape(boardDto.getSearchNm()));
        if("00".equals(sbstCtg)){
        	resultList = csFaqSvc.getNotInFormList(boardDto,skipResult,maxResult);
		} else if("100".equals(sbstCtg)){
			resultList = null;
		} else {
			resultList = csFaqSvc.getList(boardDto,skipResult,maxResult);
		}
        boardDto.setSearchNm(ParseHtmlTagUtil.escapeToPercent(boardDto.getSearchNm()));

        model.addAttribute("faqTopList", faqTopList);
        model.addAttribute("resultList",resultList);
       	model.addAttribute("boardDto",boardDto);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("totalCount", totalCount);
		return "/portal/cs/faq/faqList";
	}



	/**
	    * <pre>
	    * 설명     : FAQ 조회 증가 AJAX
	    * @return
	    * </pre>
	    */
	   @RequestMapping(value = "/m/cs/updateFaqHitAjax.do")
	   @ResponseBody
	   public Map<String, Object> updateHit(@ModelAttribute BoardDto boardDto) {
		   Map<String, Object> rtnJsonMap = new HashMap<String, Object>();
		   int updateResult = boardCommonSvc.updateHit(boardDto.getBoardSeq());
		   int boardHitCnt = 0;
		   if(updateResult > 0) {
			   BoardDto resDto = csFaqSvc.getBoardHitCnt(boardDto.getBoardSeq());
				if( resDto !=null ) {
					boardHitCnt = resDto.getBoardHitCnt();
				}
		   }

		   rtnJsonMap.put("updateResult", updateResult);
		   rtnJsonMap.put("boardHitCnt", boardHitCnt);

		   return rtnJsonMap;

	   }

	   //wire 사용
	    /**
	     * <pre>
	     * 설명     : Board 리스트
	     * @param BoardDto
	     * @return
	     * @return: Map<String,Object>
	     * </pre>
	     */
	    @RequestMapping(value ="/cs/getBoardListAjax.do")
	    @ResponseBody
	    public Map<String, Object> getBoardList(NmcpBoardBasDto nmcpBoardBasDto
	            , PageInfoBean pageInfoBean){
	        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

	        // 현재 페이지 번호 초기화
	        if(pageInfoBean.getPageNo() == 0){
	            pageInfoBean.setPageNo(1);
	        }
	        //임시 한페이지당 보여줄 리스트 수
	        pageInfoBean.setRecordCount(10);


	        nmcpBoardBasDto.setViewYn("Y");

	        nmcpBoardBasDto.setSearchNm(ParseHtmlTagUtil.percentToEscape(nmcpBoardBasDto.getSearchNm()));

	        //페이지 카운트
	        int totalCount = boardService.countBylistBoardBas(nmcpBoardBasDto);
	        pageInfoBean.setTotalCount(totalCount);

	        //RowBound 필수값 세팅
	        int skipResult  = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
	        int maxResult   = pageInfoBean.getRecordCount();  // Pagesize

	        List<NmcpBoardBasDto> boardList = boardService.listBoardBas(nmcpBoardBasDto,skipResult,maxResult);
	        nmcpBoardBasDto.setSearchNm(ParseHtmlTagUtil.escapeToPercent(nmcpBoardBasDto.getSearchNm()));

	        rtnMap.put("DATA_LIST", boardList);
	        rtnMap.put("PAGE_OBJ", pageInfoBean);

	        return rtnMap ;
	    }

	    @RequestMapping(value="/cs/faqGetAnswer.do")
	    @ResponseBody
	    public Map<String,Object> faqGetAnswerAjax(@ModelAttribute("boardDto") BoardDto boardDto, Model model
	    		, BindingResult bind ,@RequestParam(value="boardSeq") int boardSeq) {
	        Map<String,Object> rtnJsonMap = new HashMap<String,Object>();
	        String answer = csFaqSvc.getAnswer(boardSeq);

	        /* 컨텐츠 변환 */
	        answer = ParseHtmlTagUtil.convertHtmlchars(answer);
	        rtnJsonMap.put("answer", answer);
	        return rtnJsonMap;
	    }




}
