package com.ktmmobile.msf.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ktmmobile.msf.board.dto.BoardDto;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.ObjectUtils;
import com.ktmmobile.msf.system.common.util.PageInfoBean;
import com.ktmmobile.msf.system.common.util.ParseHtmlTagUtil;
import com.ktmmobile.msf.system.common.util.StringUtil;
import com.ktmmobile.msf.main.service.CsReqFormSvc;

@Controller
public class CsReqFormController {

	private static Logger logger = LoggerFactory.getLogger(CsReqFormController.class);

	@Autowired
	private CsReqFormSvc csReqFormSvc;


	/**
	 * 고객센터 > 고객지원 > 신청서식
	 * @param boardDto
	 * @param model
	 * @param bind
	 * @return
	 */
	@RequestMapping({"/m/cs/reqFormList.do"})
	public String reqFormList(@ModelAttribute BoardDto boardDto, PageInfoBean pageInfoBean, Model model, BindingResult bind,RedirectAttributes redirect) {

		if(!"Y".equals(NmcpServiceUtils.isMobile())){
			return "redirect:/cs/reqFormList.do";
		}
		if("P".equals(NmcpServiceUtils.getPlatFormCd()) ) {
			return "redirect:/cs/reqFormList.do";
		}

    	int pageNo = boardDto.getPageNo();
    	if(pageNo==0) {
    		boardDto.setPageNo(1);
    	}

    	List<BoardDto> noticeList = csReqFormSvc.selectNoticeBoard(boardDto);

    	if(noticeList !=null && !noticeList.isEmpty()) {
        	for(BoardDto dto : noticeList) {
        		String boardContents = StringUtil.NVL(dto.getBoardContents(),"");
        		dto.setBoardContents(ParseHtmlTagUtil.convertHtmlchars(boardContents));
        	}
        }

    	model.addAttribute("noticeList",noticeList);
    	model.addAttribute("boardDto",boardDto);

        return "/mobile/cs/reqForm/reqFormList";
	}

	/**
	 * 고객센터 > 고객지원 > 신청서식 조회
	 * @param boardDto
	 * @param model
	 * @param bind
	 * @return
	 */
	@RequestMapping(value = {"/cs/reqFormListAjax.do"})
	@ResponseBody
	public Map<String, Object> faqGetPagingAjax(@ModelAttribute("boardDto") BoardDto boardDto, PageInfoBean pageInfoBean, Model model) {

		Map<String, Object> rtnJsonMap = new HashMap<String, Object>();

		if (pageInfoBean.getPageNo() == 0) {
			pageInfoBean.setPageNo(1);
		}
		// 임시 한페이지당 보여줄 리스트 수
		pageInfoBean.setRecordCount(10);

		int totalCount = csReqFormSvc.getTotalCount(boardDto);
		pageInfoBean.setTotalCount(totalCount);

		// RowBound 필수값 세팅
		int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
		int maxResult = pageInfoBean.getRecordCount(); // Pagesize

		List<BoardDto> reqFormList = csReqFormSvc.selectBoardList(boardDto, skipResult, maxResult);
		// 목록리스트 개수 계산
		int listCount = (pageInfoBean.getPageNo()-1) * pageInfoBean.getRecordCount() + reqFormList.size();

		if(reqFormList !=null && !reqFormList.isEmpty()) {
        	for(BoardDto dto : reqFormList) {
        		String boardContents = StringUtil.NVL(dto.getBoardContents(),"");
        		dto.setBoardContents(ParseHtmlTagUtil.convertHtmlchars(boardContents));
        	}
        }

		rtnJsonMap.put("reqFormList", reqFormList);
		rtnJsonMap.put("totalCount", totalCount);
		rtnJsonMap.put("listCount", listCount);
		rtnJsonMap.put("pageNo",pageInfoBean.getPageNo());
		return rtnJsonMap;
	}

	/**
	 * 고객센터 > 고객지원 > 신청서식
	 * @param boardDto
	 * @param model
	 * @param bind
	 * @return
	 */
	@RequestMapping(value = {"/cs/reqFormList.do"})
	public String pcReqFormList(@ModelAttribute BoardDto boardDto, @ModelAttribute PageInfoBean pageInfoBean, Model model, BindingResult bind, RedirectAttributes redirect) {

		if("Y".equals(NmcpServiceUtils.isMobile())){
			return "redirect:/m/cs/reqFormList.do";
		}
		if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
			return "redirect:/m/cs/reqFormList.do";
		}
		// 현재 페이지 번호 초기화
        if(pageInfoBean.getPageNo() == 0){
            pageInfoBean.setPageNo(1);
        }
        //임시 한페이지당 보여줄 리스트 수
        pageInfoBean.setRecordCount(10);

		//페이지 카운트
        int totalCount = csReqFormSvc.getTotalCount(boardDto);
	    pageInfoBean.setTotalCount(totalCount);

        //RowBound 필수값 세팅
	    int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount(); // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
		int maxResult = pageInfoBean.getRecordCount(); // Pagesize

        //게시판 리스트
        boardDto.setSearchNm(ParseHtmlTagUtil.percentToEscape(boardDto.getSearchNm()));
        List<BoardDto> reqFormList = csReqFormSvc.selectBoardList(boardDto, skipResult, maxResult);
        boardDto.setSearchNm(ParseHtmlTagUtil.escapeToPercent(boardDto.getSearchNm()));

        // 고정 공지 3개
        List<BoardDto> noticeList = csReqFormSvc.selectNoticeBoard(boardDto);

        if(reqFormList !=null && !reqFormList.isEmpty()) {
        	for(BoardDto dto : reqFormList) {
        		String boardContents = StringUtil.NVL(dto.getBoardContents(),"");
        		dto.setBoardContents(ParseHtmlTagUtil.convertHtmlchars(boardContents));
        	}
        }

        if(noticeList !=null && !noticeList.isEmpty()) {
        	for(BoardDto dto : noticeList) {
        		String boardContents = StringUtil.NVL(dto.getBoardContents(),"");
        		dto.setBoardContents(ParseHtmlTagUtil.convertHtmlchars(boardContents));
        	}
        }

        model.addAttribute("reqFormList",reqFormList);
        model.addAttribute("noticeList",noticeList);
       	model.addAttribute("boardDto",boardDto);
        model.addAttribute("pageInfoBean", pageInfoBean);
        model.addAttribute("totalCount", totalCount);

		return "/portal/cs/reqForm/reqFormList";
	}

	@RequestMapping("/cs/reqFormDownloadCountUpdateAjax.do")
	@ResponseBody
	public Map<String,Object> reqFormDownloadCountUpdateAjax(@ModelAttribute BoardDto boardDto) {

		try {
			csReqFormSvc.reqFormDownloadCountUpdate(boardDto);
		} catch(DataAccessException e) {
			logger.debug("DataAccessException error");
		} catch(Exception e) {
			logger.debug("error");
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("SUCCESS", "Y");
		return map;
	}

}
