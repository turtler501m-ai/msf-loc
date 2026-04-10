package com.ktmmobile.mcp.search.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.search.dto.SearchCrrlDto;
import com.ktmmobile.mcp.search.dto.SearchParamDto;
import com.ktmmobile.mcp.search.dto.SearchPplrDto;
import com.ktmmobile.mcp.search.dto.SearchRecomDto;

@Repository
public class SearchDaoImpl implements SearchDao {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<SearchCrrlDto> getRelationWordListAjax(String srchText) {
		return sqlSessionTemplate.selectList("SearchPopupMapper.getRelationWordListAjax", srchText);
	}

	@Override
	public List<SearchRecomDto> getRecommendWordListAjax() {
		return sqlSessionTemplate.selectList("SearchPopupMapper.getRecommendWordListAjax");
	}

	@Override
	public List<SearchPplrDto> getPopularWordListAjax() {
		return sqlSessionTemplate.selectList("SearchPopupMapper.getPopularWordListAjax");
	}
	
	@Override
	public int insertSearchTxt(SearchParamDto searchParamDto) {
		return (Integer)sqlSessionTemplate.insert("SearchPopupMapper.insertSearchTxt", searchParamDto);
	}

}
