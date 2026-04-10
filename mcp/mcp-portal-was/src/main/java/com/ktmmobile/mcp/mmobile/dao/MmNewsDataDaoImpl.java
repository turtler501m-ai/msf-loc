package com.ktmmobile.mcp.mmobile.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.mmobile.dto.NewsDataBasDto;
import com.ktmmobile.mcp.mmobile.dto.NewsDataLinkDto;

@Repository
public class MmNewsDataDaoImpl implements MmNewsDataDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int countNewsDataList(NewsDataBasDto newsDataBasDto) {
		// TODO Auto-generated method stub
		return (Integer)sqlSessionTemplate.selectOne("NewsDataMapper.countNewsDataList", newsDataBasDto);
	}

	@Override
	public List<NewsDataBasDto> newsNotiList(NewsDataBasDto newsDataBasDto) {
		return sqlSessionTemplate.selectList("NewsDataMapper.newsNotiList", newsDataBasDto);
	}

	@Override
	public List<NewsDataBasDto> newsDataList(NewsDataBasDto newsDataBasDto,	int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("NewsDataMapper.newsDataList", newsDataBasDto, new RowBounds(skipResult, maxResult));
	}

	@Override
	public NewsDataBasDto newsDetailNotiSelect(NewsDataBasDto newsDataBasDto) {
		return sqlSessionTemplate.selectOne("NewsDataMapper.newsDetailNotiSelect", newsDataBasDto);
	}

	@Override
	public List<NewsDataLinkDto> linkListSelect(int newsDatSeq) {
		return sqlSessionTemplate.selectList("NewsDataMapper.linkListSelect", newsDatSeq);
	}

	@Override
	public NewsDataBasDto newsDetailSelect(NewsDataBasDto newsDataBasDto) {
		return sqlSessionTemplate.selectOne("NewsDataMapper.newsDetailSelect", newsDataBasDto);
	}

	@Override
	public int updateHit(NewsDataBasDto newsDataBasDto) {
		return sqlSessionTemplate.update("NewsDataMapper.updateHit", newsDataBasDto);
	}

}
