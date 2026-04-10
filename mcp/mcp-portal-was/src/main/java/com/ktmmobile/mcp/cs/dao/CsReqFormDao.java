package com.ktmmobile.mcp.cs.dao;

import java.util.List;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.cs.dto.InquiryBoardDto;

public interface CsReqFormDao {

	List<BoardDto> selectNoticeBoard(BoardDto boardDto);
	int getTotalCount(BoardDto boardDto);
	List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult);
	public void reqFormDownloadCountUpdate(BoardDto boardDto);

}
