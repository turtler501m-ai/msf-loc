package com.ktmmobile.mcp.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.board.dao.BoardCommonDao;
import com.ktmmobile.mcp.board.dto.BoardDto;

@Service
public class BoardCommonSvcImpl implements BoardCommonSvc{

	@Autowired
	BoardCommonDao boardCommonDao;

	@Override
	public int getTotalCount(BoardDto boardDto) {
		int getTotalCount = boardCommonDao.getTotalCount(boardDto);
		return getTotalCount;
	}



	@Override
	public List<BoardDto> selectNoticeBoard(BoardDto boardDto) {
		List<BoardDto> selectNoticeBoard = boardCommonDao.selectNoticeBoard(boardDto);
		return selectNoticeBoard;
	}


	@Override
	public BoardDto viewBoard(int boardDto){
		BoardDto viewNoticeBoard = boardCommonDao.viewBoard(boardDto);
		return viewNoticeBoard;
	}

	@Override
	public int updateHit(int boardSeq) {
		int updateHit = boardCommonDao.updateHit(boardSeq);
		return updateHit;
	}

	@Override
	public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult) {
		List<BoardDto> selectBoardList = boardCommonDao.selectBoardList(boardDto, skipResult, maxResult);
		return selectBoardList;
	}



	@Override
	public BoardDto selectIndex(BoardDto boardDto) {
		// TODO Auto-generated method stub
		return boardCommonDao.selectIndex(boardDto);
	}


}
