package com.ktmmobile.mcp.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.board.dao.CsNoticeDao;
import com.ktmmobile.mcp.board.dto.BoardDto;

@Service
public class CsNoticeServiceImpl implements CsNoticeService {

	@Autowired
	private CsNoticeDao csNoticeDao;

	@Override
	public int getTotalCount(BoardDto boardDto) {
		int getTotalCount = csNoticeDao.getTotalCount(boardDto);
		return getTotalCount;
	}

	@Override
	public List<BoardDto> selectNoticeBoard(BoardDto boardDto) {
		List<BoardDto> selectNoticeBoard = csNoticeDao.selectNoticeBoard(boardDto);
		return selectNoticeBoard;
	}

	@Override
	public BoardDto viewNoticeBoard(int boardDto){
		BoardDto viewNoticeBoard = csNoticeDao.viewNoticeBoard(boardDto);
		return viewNoticeBoard;
	}

	@Override
	public int updateHit(int boardSeq) {
		int updateHit = csNoticeDao.updateHit(boardSeq);
		return updateHit;
	}

	@Override
	public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult) {
		List<BoardDto> selectBoardList = csNoticeDao.selectBoardList(boardDto, skipResult, maxResult);
		return selectBoardList;
	}

	@Override
	public BoardDto selectNotiIndex(BoardDto boardDto) {
		// TODO Auto-generated method stub
		return csNoticeDao.selectNotiIndex(boardDto);
	}


}
