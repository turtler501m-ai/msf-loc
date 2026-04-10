package com.ktmmobile.mcp.board.service;

import java.util.List;

import com.ktmmobile.mcp.board.dto.BoardDto;

public interface BoardCommonSvc {

	int getTotalCount(BoardDto boardDto);


	List<BoardDto> selectNoticeBoard(BoardDto boardDto);

	BoardDto viewBoard(int boardSeq);
	int updateHit(int boardSeq);
	List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult);

	BoardDto selectIndex(BoardDto boardDto);

}
