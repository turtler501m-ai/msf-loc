package com.ktmmobile.mcp.search.dao;

import java.util.List;

import com.ktmmobile.mcp.search.dto.SearchCrrlDto;
import com.ktmmobile.mcp.search.dto.SearchParamDto;
import com.ktmmobile.mcp.search.dto.SearchPplrDto;
import com.ktmmobile.mcp.search.dto.SearchRecomDto;

public interface SearchDao {

	/**
	 * 연관검색어 검색순위가 높은 검색어 10개, 자동완성 목록
	 * @param srchText
	 * @return
	 */
	public List<SearchCrrlDto> getRelationWordListAjax(String srchText);

	/**
     * 조회_추천검색어 10개만 조회
     * @return
     */
	public List<SearchRecomDto> getRecommendWordListAjax();

	/**
	 * 인기검색어 10개만 조회 (전날과 비교 해당검색어 상승,하강 표시)
	 * @return
	 */
	public List<SearchPplrDto> getPopularWordListAjax();
	
	/**
	 * 검색이력 쌓기
	 * @return
	 */
	public int insertSearchTxt(SearchParamDto searchParamDto);

}
