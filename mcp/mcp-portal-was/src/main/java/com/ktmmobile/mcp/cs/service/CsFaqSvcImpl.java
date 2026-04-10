package com.ktmmobile.mcp.cs.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.common.util.ParseHtmlTagUtil;
import com.ktmmobile.mcp.cs.controller.CsFaqController;
import com.ktmmobile.mcp.cs.dao.CsFaqDao;

@Service
public class CsFaqSvcImpl implements CsFaqSvc {

	private static Logger logger = LoggerFactory.getLogger(CsFaqController.class);

	@Autowired
	CsFaqDao csFaqDao;

	/**
	 * FAQ 게시판 리스트
	 */
	@Override
	public List<BoardDto> getList(BoardDto boardDto, int skipResult, int maxResult) {
		List<BoardDto> list = csFaqDao.getList(boardDto, skipResult, maxResult);
		for(int i =0;i<list.size();i++) {
			((BoardDto)list.get(i)).setBoardContents(ParseHtmlTagUtil.convertHtmlchars(((BoardDto)list.get(i)).getBoardContents()));
		}
		return list;
	}

	/**
	 * FAQ 질문에 대한 답변 get
	 */
	@Override
	public String getAnswer(int boardSeq) {
		return csFaqDao.getAnswer(boardSeq);
	}

	/**
	 * FAQ 게시글 총 개수
	 */
	@Override
	public int getTotalCount(BoardDto boardDto) {
		return csFaqDao.getTotalCount(boardDto);
	}
	/**
	 * FAQ top10
	 */
	@Override
	public List<BoardDto> getTopFormList(BoardDto boardDto){

		List<BoardDto> list = null;
		try {
			list = csFaqDao.getTopFormList(boardDto);
			for(int i =0;i<list.size();i++) {
				((BoardDto)list.get(i)).setBoardContents(ParseHtmlTagUtil.convertHtmlchars(((BoardDto)list.get(i)).getBoardContents()));
			}
		} catch(DataAccessException e) {
			logger.debug("DataAccessException error");
		} catch(Exception e) {
			logger.debug("error");
		}
		return list;

	}

	/**
	 * FAQ 게시글 총 개수
	 */
	@Override
	public int getNotInTotalCount(BoardDto boardDto) {
		return csFaqDao.getNotInTotalCount(boardDto);
	}

	@Override
	public List<BoardDto> getNotInFormList(BoardDto boardDto, int skipResult, int maxResult) {
		List<BoardDto> list = csFaqDao.getNotInFormList(boardDto, skipResult, maxResult);
		for(int i =0;i<list.size();i++) {
			((BoardDto)list.get(i)).setBoardContents(ParseHtmlTagUtil.convertHtmlchars(((BoardDto)list.get(i)).getBoardContents()));
		}
		return list;
	}

	@Override
	public BoardDto getBoardHitCnt(int boardSeq) {
		return csFaqDao.getBoardHitCnt(boardSeq);
	}

}
