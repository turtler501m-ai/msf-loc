package com.ktmmobile.mcp.cs.service;

import java.util.List;

import com.ktmmobile.mcp.board.dto.BoardDto;

public interface CsFaqSvc {

	public List<BoardDto> getList(BoardDto boardDto, int skipResult, int maxResult);

	public String getAnswer(int boardSeq);

	public int getTotalCount(BoardDto boardDto);

	public List<BoardDto> getTopFormList(BoardDto boardDto);

	public int getNotInTotalCount(BoardDto boardDto);

	public List<BoardDto> getNotInFormList(BoardDto boardDto, int skipResult, int maxResult);

	public BoardDto getBoardHitCnt(int boardSeq);



}
