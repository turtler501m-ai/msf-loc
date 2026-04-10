package com.ktmmobile.mcp.cs.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ktmmobile.mcp.board.dto.BoardDto;
import com.ktmmobile.mcp.common.util.PageInfoBean;
import com.ktmmobile.mcp.cs.dao.CsReqFormDao;

@Service
public class CsReqFormSvcImpl implements CsReqFormSvc {

	private static Logger logger = LoggerFactory.getLogger(CsReqFormSvcImpl.class);

	@Autowired
	CsReqFormDao csReqFormDao;

	@Override
	public List<BoardDto> selectNoticeBoard(BoardDto boardDto) {
		return csReqFormDao.selectNoticeBoard(boardDto);
	}

	@Override
	public List<BoardDto> selectBoardList(BoardDto boardDto, int skipResult, int maxResult) {
		return csReqFormDao.selectBoardList(boardDto,skipResult,maxResult);
	}

	@Override
	public void reqFormDownloadCountUpdate(BoardDto boardDto) {
		csReqFormDao.reqFormDownloadCountUpdate(boardDto);
	}

	@Override
	public int getTotalCount(BoardDto boardDto) {
		return csReqFormDao.getTotalCount(boardDto);
	}


}
