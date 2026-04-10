package com.ktmmobile.mcp.search.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.search.dto.SearchDto;


@Repository
public class SearchDao {

	@Autowired
	@Qualifier(value="bootoraSqlSession")
	private SqlSession bootoraSqlSession;

	@Autowired
	@Qualifier(value="booteventSqlSession")
	private SqlSession booteventSqlSession;

	public Map<String, String> selectBoardList() {
		return booteventSqlSession.selectOne("EventTestMapper.selectBoardList");
	}

	public List<SearchDto> selectMenuIndexing() {
		return bootoraSqlSession.selectList("SearchMapper.selectMenuIndexing");
	}

	public List<SearchDto> selectRateIndexing() {
		return bootoraSqlSession.selectList("SearchMapper.selectRateIndexing");
	}

	public List<SearchDto> selectAdsvcIndexing() {
		return bootoraSqlSession.selectList("SearchMapper.selectAdsvcIndexing");
	}

	public List<SearchDto> selectEventIndexing() {
		//return bootoraSqlSession.selectList("SearchMapper.selectEventIndexing");
		return booteventSqlSession.selectList("SearchEventMapper.selectEventIndexing");
	}

	public List<SearchDto> selectBoardIndexing() {
		return bootoraSqlSession.selectList("SearchMapper.selectBoardIndexing");
	}

	public int deleteSearchPplrWord() {
		return bootoraSqlSession.delete("SearchMapper.deleteSearchPplrWord");
	}

	public int insertSearchPplrWord() {
		return bootoraSqlSession.insert("SearchMapper.insertSearchPplrWord");
	}

}
