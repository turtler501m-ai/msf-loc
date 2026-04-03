package com.ktmmobile.msf.main.service;

import java.util.List;

import com.ktmmobile.msf.board.dto.BoardDto;

public interface CsReqFormSvc {

	List<BoardDto> selectNoticeBoard(BoardDto boardDto);
	int getTotalCount(BoardDto boardDto);
	List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult);

	void reqFormDownloadCountUpdate(BoardDto boardDto);




}
