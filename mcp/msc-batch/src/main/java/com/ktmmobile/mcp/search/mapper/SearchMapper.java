package com.ktmmobile.mcp.search.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.search.dto.SearchDto;

@Mapper
public interface SearchMapper {

	List<SearchDto> selectMenuIndexing();

	List<SearchDto> selectRateIndexing();

	List<SearchDto> selectAdsvcIndexing();

	List<SearchDto> selectEventIndexing();

	List<SearchDto> selectBoardIndexing();

	int deleteSearchPplrWord();

    int insertSearchPplrWord();

}
