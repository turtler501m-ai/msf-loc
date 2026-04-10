package com.ktmmobile.mcp.board.dao;

import java.util.List;

import org.apache.commons.collections.map.HashedMap;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.common.dto.db.NmcpBoardBasDto;

public interface BoardCommonDao {
	int getTotalCount(BoardDto boardDto);


	List<BoardDto> selectNoticeBoard(BoardDto boardDto);

	BoardDto viewBoard(int boardDto);

	int updateHit(int boardSeq);

	List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult);


	BoardDto selectIndex(BoardDto boardDto);

}
