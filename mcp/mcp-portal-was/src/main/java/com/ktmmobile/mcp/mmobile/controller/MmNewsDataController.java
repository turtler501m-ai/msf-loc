package com.ktmmobile.mcp.mmobile.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.mmobile.dto.NewsDataBasDto;
import com.ktmmobile.mcp.mmobile.dto.NewsDataLinkDto;
import com.ktmmobile.mcp.mmobile.service.MmNewsDataSvc;

@Controller
public class MmNewsDataController {

	private static Logger logger = LoggerFactory.getLogger(MmNewsDataController.class);

	@Autowired
	MmNewsDataSvc mmNewsDataSvc;

	/*뉴스데이터 리스트*/
	@RequestMapping(value = "/mmobile/newsDataList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String newsDataList(@ModelAttribute("newsDataBasDto") NewsDataBasDto newsDataBasDto, PageInfoBean pageInfoBean, Model model, BindingResult bind) {
		
		/*현재 페이지 번호 초기화*/
		if(pageInfoBean.getPageNo() == 0){
			pageInfoBean.setPageNo(1);
		}
		/*한페이지당 보여줄 리스트 수*/

		pageInfoBean.setRecordCount(5);

		newsDataBasDto.setSearchValue(ParseHtmlTagUtil.percentToEscape(newsDataBasDto.getSearchValue()));		// 예약어 %,_ 검색시 Escape처리

		/*카운터 조회*/
		int total = mmNewsDataSvc.countNewsDataList(newsDataBasDto);
		
		pageInfoBean.setTotalCount(total);

		int skipResult = (pageInfoBean.getPageNo() - 1) * pageInfoBean.getRecordCount();	//셀렉트 하지 않고 뛰어넘을 만큼의 rownum
		int maxResult = pageInfoBean.getRecordCount();	// Pagesize
		
		List<NewsDataBasDto> newsNotiList = mmNewsDataSvc.newsNotiList(newsDataBasDto);
		List<NewsDataBasDto> newsDataList = mmNewsDataSvc.newsDataList(newsDataBasDto, skipResult, maxResult);

		newsDataBasDto.setSearchValue(ParseHtmlTagUtil.escapeToPercent(newsDataBasDto.getSearchValue()));		// 예약어 %,_ 검색시 ReEscape처리

		model.addAttribute("searchDto", newsDataBasDto);
		model.addAttribute("newsNotiList", newsNotiList);
		model.addAttribute("newsDataList", newsDataList);
		model.addAttribute("pageInfoBean", pageInfoBean);
		
		return "/mmobile/news/newsList";
	}

	/*뉴스데이터 상세*/
	@RequestMapping(value = "/mmobile/newsDetail.do", method={RequestMethod.GET, RequestMethod.POST})
	public String newsDetailSelect(@RequestParam("newsDatSeq") int newsDatSeq, @ModelAttribute("newsDataBasDto") NewsDataBasDto newsDataBasDto, Model model) {
		
		if("Y".equals(newsDataBasDto.getNotiYn())){	//공지사항이면 쿼리를 별도로 돌린다(기존것은 이전,다음글때문에 공지사항을 NOT IN 처리함
			NewsDataBasDto newsDto = mmNewsDataSvc.newsDetailNotiSelect(newsDataBasDto);

			if(newsDto != null){
				newsDto.setNewsDatSbst(ParseHtmlTagUtil.convertHtmlchars(newsDto.getNewsDatSbst()));	// HTML 코드가 섞여서 나와 추가 처리함

				model.addAttribute("newsDto", newsDto);

				List<NewsDataLinkDto> linkList = mmNewsDataSvc.linkListSelect(newsDatSeq);

				model.addAttribute("searchDto", newsDataBasDto);
				model.addAttribute("linkList", linkList);
				model.addAttribute("newsDataBasDto", newsDataBasDto);
			}
		}else{
			newsDataBasDto.setSearchValue(ParseHtmlTagUtil.percentToEscape(newsDataBasDto.getSearchValue()));		// 예약어 %,_ 검색시 Escape처리

			NewsDataBasDto newsDto = mmNewsDataSvc.newsDetailSelect(newsDataBasDto);

			newsDataBasDto.setSearchValue(ParseHtmlTagUtil.escapeToPercent(newsDataBasDto.getSearchValue()));		// 예약어 %,_ 검색시 ReEscape처리

			if(newsDto != null){
				newsDto.setNewsDatSbst(ParseHtmlTagUtil.convertHtmlchars(newsDto.getNewsDatSbst()));	// HTML 코드가 섞여서 나와 추가 처리함

				model.addAttribute("newsDto", newsDto);

				List<NewsDataLinkDto> linkList = mmNewsDataSvc.linkListSelect(newsDatSeq);

				model.addAttribute("searchDto", newsDataBasDto);
				model.addAttribute("linkList", linkList);
				model.addAttribute("newsDataBasDto", newsDataBasDto);
			}
		}

		return "/mmobile/news/newsDetail";
	}
}
