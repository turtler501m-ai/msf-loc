package com.ktmmobile.mcp.board.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.board.dto.BoardDto;

@Repository
public class BoardCommonDaoImpl implements BoardCommonDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int getTotalCount(BoardDto boardDto) {
		// TODO Auto-generated method stub
		return (Integer)sqlSessionTemplate.selectOne("BoardCommonMapper.getTotalCount",boardDto);
	}


	@Override
	public List<BoardDto> selectNoticeBoard(BoardDto boardDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("BoardCommonMapper.selectNoticeBoard",boardDto);
	}



	@Override
	public BoardDto viewBoard(int boardDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("BoardCommonMapper.viewBoard", boardDto);
	}

	@Override
	public int updateHit(int boardSeq) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("BoardCommonMapper.updateHit", boardSeq);
	}

	@Override
	public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("BoardCommonMapper.selectBoardList",boardDto,new RowBounds(skipResult, maxResult));
	}


	@Override
	public BoardDto selectIndex(BoardDto boardDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("BoardCommonMapper.selectIndex",boardDto);
	}



}
