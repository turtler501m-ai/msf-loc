package com.ktmmobile.mcp.board.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.board.dto.BoardDto;

@Repository
public class CsBorderDaoImpl implements CsBorderDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int getTotalCount(BoardDto boardDto) {
		return (Integer)sqlSessionTemplate.selectOne("CsBorderMapper.getTotalCount",boardDto);
	}

	@Override
	public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult) {
		return sqlSessionTemplate.selectList("CsBorderMapper.selectBoardList",boardDto,new RowBounds(skipResult, maxResult));
	}

	@Override
	public int updateHit(int boardSeq) {
		return sqlSessionTemplate.update("CsBorderMapper.updateHit", boardSeq);
	}

	@Override
	public BoardDto selectBoardArticle(BoardDto boardDto) {
		return sqlSessionTemplate.selectOne("CsBorderMapper.selectBoardArticle",boardDto);
	}

	@Override
	public BoardDto selectIndex(BoardDto boardDto) {
		return sqlSessionTemplate.selectOne("CsBorderMapper.selectIndex",boardDto);
	}
}
