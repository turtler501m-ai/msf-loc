package com.ktmmobile.mcp.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.search.dto.SearchCrrlDto;
import com.ktmmobile.mcp.search.dto.SearchParamDto;
import com.ktmmobile.mcp.search.dto.SearchPplrDto;
import com.ktmmobile.mcp.search.dto.SearchRecomDto;
import com.ktmmobile.mcp.search.service.SearchService;


@Controller
public class SearchController {

	private static Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private SearchService searchService;

	@Autowired
    private IpStatisticService ipstatisticService;

    static final String[] ALL_MENU = new String[] {"menu", "phone", "rate", "adsvc", "event", "board"};

	@RequestMapping(value = {"/m/search/commonSearchAjax.do", "/search/commonSearchAjax.do"})
    @ResponseBody
    public Map<String, Object> commonSearchAjax(HttpServletRequest request, HttpServletResponse response, SearchParamDto searchParamDto) {

    	if ("".equals(searchParamDto.getSearchKeyword())) return null;
    	Map<String, Object> result = new HashMap<String, Object>();
    	searchParamDto.setXmlYn("N");
    	if("adsvc".equals(searchParamDto.getSearchCategory())) {
    		Object searchResult = searchService.getSearchResult(searchParamDto,request);
    		searchParamDto.setXmlYn("Y");
    		Object adsvcXml = searchService.getSearchResult(searchParamDto,request);
    		result.put("data", searchResult);
    		result.put("adsvcXml", adsvcXml);
    		return result;
    	}else {
    		Object searchResult = searchService.getSearchResult(searchParamDto,request);
    		result.put("data", searchResult);
    		return result;
    	}
    }

	/**
	 * 통합검색
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/m/search/searchView.do", "/search/searchView.do"}
			       ,method = {RequestMethod.GET, RequestMethod.POST})
	public String searchView(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		String returnUrl = "/portal/search/searchView";
        if(request.getRequestURI().equals("/m/search/searchView.do")){
            returnUrl="/mobile/popup/searchView";
        }
		return returnUrl;
	}

	/**
	 * 통합검색 결과
	 * @param request
	 * @param response
	 * @param model
	 * @throws ParseException
	 * @returns
	 */
	@RequestMapping(value = {"/m/search/searchResultView.do", "/search/searchResultView.do"}
			       ,method = {RequestMethod.GET, RequestMethod.POST})
	public String searchResultView(HttpServletRequest request,
			                       HttpServletResponse response,
								   Model model, SearchParamDto searchParamDto) {

		String returnUrl = "/portal/search/searchResultView";
        if(request.getRequestURI().equals("/m/search/searchResultView.do")){
            returnUrl="/mobile/search/searchResultView";
        }

	    try {
	    	if("ALL".equals(searchParamDto.getSearchCategory()) && !"".equals(searchParamDto.getSearchKeyword())) {
	    		for(String cate : ALL_MENU) {
	    			searchParamDto.setXmlYn("N");
	    			searchParamDto.setSearchCategory(cate);
	    			if("phone".equals(cate)) {
	    				model.addAttribute("searchPhone", searchService.getSearchResult(searchParamDto,request));
 	    			}else if("event".equals(cate)) {
	    				model.addAttribute("searchEvent", searchService.getSearchResult(searchParamDto,request));
	    			}else if("board".equals(cate)) {
	    				model.addAttribute("searchBoard", searchService.getSearchResult(searchParamDto,request));
	    			}else if("rate".equals(cate)) {
	    				model.addAttribute("searchRate", searchService.getSearchResult(searchParamDto,request));
	    			}else if("adsvc".equals(cate)) {
	    				model.addAttribute("searchAdsvc", searchService.getSearchResult(searchParamDto,request));
	    				searchParamDto.setXmlYn("Y");
	    				model.addAttribute("searchAdsvcXml", searchService.getSearchResult(searchParamDto,request));
	    			}else if("menu".equals(cate)) {
	    				model.addAttribute("searchMenu", searchService.getSearchResult(searchParamDto,request));
	    			}
	    		}
	    	}
	    } catch(DataAccessException e) {
			logger.debug("searchResultView 호출");
		} catch(Exception e) {
	    	logger.debug("searchResultView 호출");
	    }
	    model.addAttribute("searchKey", searchParamDto.getSearchKeyword());

		return returnUrl;
	}

	/**
	 * 전일 인기검색어 인서트
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/m/search/insertSearchTxtAjax.do", "/search/insertSearchTxtAjax.do"} )
	@ResponseBody
	public Map<String, Object> insertSearchTxtAjax(
			  HttpServletRequest request
			, HttpServletResponse response
			, Model model, SearchParamDto searchParamDto) {

		HashMap<String, Object> rtnMap = new HashMap<String, Object>();

		if(ipstatisticService.getClientIp() != null) {
			searchParamDto.setCretIp(ipstatisticService.getClientIp());
			searchParamDto.setAmdIp(ipstatisticService.getClientIp());
		}

		//검색어 Insert
	    searchService.insertSearchTxt(searchParamDto);
		rtnMap.put("resultCode","P0000"); // 처리 성공

		return rtnMap;
	}

	/**
	 * 연관검색어 검색순위가 높은 검색어 10개, 자동완성 목록
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/m/search/relationWordListAjax.do", "/search/relationWordListAjax.do"} )
	@ResponseBody
	public List<SearchCrrlDto> relationWordListAjax(
			@RequestParam(value="srchText") String srchText
			, HttpServletRequest request
			, HttpServletResponse response, Model model) {

		List<SearchCrrlDto> result = new ArrayList<SearchCrrlDto>();
		result = searchService.getRelationWordListAjax(srchText);
		List<SearchCrrlDto> newList = new ArrayList<SearchCrrlDto>(new HashSet<SearchCrrlDto>(result));
		return newList;
	}

	/**
	 * 조회_추천검색어 10개만 조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/m/search/recommendWordListAjax.do", "/search/recommendWordListAjax.do"})
	@ResponseBody
	public List<SearchRecomDto> recommendWordListAjax(
			HttpServletRequest request,
			HttpServletResponse response, Model model) {

		List<SearchRecomDto> result = new ArrayList<SearchRecomDto>();
		result = searchService.getRecommendWordListAjax();

		return result;
	}

	/**
	 * 인기검색어 10개만 조회 (전날과 비교 해당검색어 상승,하강 표시)
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/m/search/popularWordListAjax.do", "/search/popularWordListAjax.do"})
	@ResponseBody
	public List<SearchPplrDto> popularWordListAjax(HttpServletRequest request, HttpServletResponse response, Model model) {

		List<SearchPplrDto> result = new ArrayList<SearchPplrDto>();
		result = searchService.getPopularWordListAjax();
		return result;
	}


}
