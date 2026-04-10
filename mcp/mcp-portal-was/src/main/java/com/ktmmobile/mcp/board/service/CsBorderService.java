package com.ktmmobile.mcp.board.service;

import java.util.List;

import com.ktmmobile.mcp.board.dto.BoardDto;

public interface CsBorderService {

	// 일반
	int getTotalCount(BoardDto boardDto);
	List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult);

	// 상세보기
	int updateHit(int boardSeq);
	BoardDto selectBoardArticle(BoardDto boardDto);
	BoardDto selectIndex(BoardDto boardDto);




}
