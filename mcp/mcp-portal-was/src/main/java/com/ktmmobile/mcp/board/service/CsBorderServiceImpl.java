package com.ktmmobile.mcp.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.board.dao.CsBorderDao;
import com.ktmmobile.mcp.board.dto.BoardDto;

@Service
public class CsBorderServiceImpl implements CsBorderService {

	@Autowired
	CsBorderDao csBorderDao;

	@Override
	public int getTotalCount(BoardDto boardDto) {
		int getTotalCount = csBorderDao.getTotalCount(boardDto);
		return getTotalCount;
	}

	@Override
	public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult) {
		List<BoardDto> selectBoardList = csBorderDao.selectBoardList(boardDto, skipResult, maxResult);
		return selectBoardList;
	}

	@Override
	public int updateHit(int boardSeq) {
		int updateHit = csBorderDao.updateHit(boardSeq);
		return updateHit;
	}

	@Override
    public BoardDto selectBoardArticle(BoardDto boardDto) {
        return csBorderDao.selectBoardArticle(boardDto);
    }

    @Override
	public BoardDto selectIndex(BoardDto boardDto) {
		// TODO Auto-generated method stub
		return csBorderDao.selectIndex(boardDto);
	}


}
