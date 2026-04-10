package com.ktmmobile.mcp.cs.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.cs.controller.CsFaqController;

@Repository
public class CsFaqDaoImpl implements CsFaqDao {

	private static Logger logger = LoggerFactory.getLogger(CsFaqDaoImpl.class);

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 총 페이징 개수
	 */
	@Override
	public int getTotalCount(BoardDto boardDto) {
		return (Integer)sqlSessionTemplate.selectOne("CsFaqMapper.getTotalCount", boardDto);
	}

	/**
	 * FAQ 리스트
	 */
	@Override
	public List<BoardDto> getList(BoardDto boardDto, int skipResult,
			int maxResult) {
		return sqlSessionTemplate.selectList("CsFaqMapper.getFormList", boardDto, new RowBounds(skipResult, maxResult));
	}

	/**
	 * Faq 질문에 대한 답변 get
	 */
	@Override
	public String getAnswer(int boardSeq) {
		return sqlSessionTemplate.selectOne("CsFaqMapper.getAnswer", boardSeq);
	}
	/**
	 * Faq TOP10
	 */
	@Override
	public List<BoardDto> getTopFormList(BoardDto boardDto){
		return sqlSessionTemplate.selectList("CsFaqMapper.getTopFormList",boardDto);
	}

	/**
	 * 총 페이징 개수
	 */
	@Override
	public int getNotInTotalCount(BoardDto boardDto) {
		return (Integer)sqlSessionTemplate.selectOne("CsFaqMapper.getNotInTotalCount", boardDto);
	}

	/**
	 * FAQ 리스트
	 */
	@Override
	public List<BoardDto> getNotInFormList(BoardDto boardDto, int skipResult, int maxResult) {
		return sqlSessionTemplate.selectList("CsFaqMapper.getNotInFormList", boardDto, new RowBounds(skipResult, maxResult));
	}

	@Override
	public BoardDto getBoardHitCnt(int boardSeq) {
		return sqlSessionTemplate.selectOne("CsFaqMapper.getBoardHitCnt", boardSeq);
	}

}
