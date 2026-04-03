package com.ktmmobile.msf.main.dao;

import java.util.List;

import com.ktmmobile.msf.board.dto.BoardDto;
import com.ktmmobile.msf.main.dto.InquiryBoardDto;

public interface CsReqFormDao {

	List<BoardDto> selectNoticeBoard(BoardDto boardDto);
	int getTotalCount(BoardDto boardDto);
	List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult);
	public void reqFormDownloadCountUpdate(BoardDto boardDto);

}
