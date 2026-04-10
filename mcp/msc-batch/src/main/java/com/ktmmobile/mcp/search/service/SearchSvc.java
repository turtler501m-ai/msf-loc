package com.ktmmobile.mcp.search.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.search.dao.SearchDao;
import com.ktmmobile.mcp.search.dto.SearchDto;

@Service
public class SearchSvc {

    private static final Logger logger = LoggerFactory.getLogger(SearchSvc.class);

	@Autowired
	private SearchDao searchDao;

	public List<SearchDto> selectMenuIndexing() {
		return searchDao.selectMenuIndexing();
	}

	public List<SearchDto> selectRateIndexing() {
		return searchDao.selectRateIndexing();
	}

	public List<SearchDto> selectAdsvcIndexing() {
		return searchDao.selectAdsvcIndexing();
	}

	public List<SearchDto> selectEventIndexing() {
		return searchDao.selectEventIndexing();
	}

	public List<SearchDto> selectBoardIndexing() {
		return searchDao.selectBoardIndexing();
	}

	public int deleteSearchPplrWord() {
		return searchDao.deleteSearchPplrWord();
	}

	public int insertSearchPplrWord() {
		return searchDao.insertSearchPplrWord();
	}

}
