package com.ktmmobile.mcp.cs.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.cs.dao.TransferDao;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.cs.controller.CsFaqController;
import com.ktmmobile.mcp.cs.dao.CsTransferDao;
import com.ktmmobile.mcp.cs.dto.CsMcpUserCntrDto;
import com.ktmmobile.mcp.cs.dto.NflChgTrnsDto;
import com.ktmmobile.mcp.cs.dto.NflChgTrnsfeDto;

@Service
public class CsTransferSvcImpl implements CsTransferSvc {
	private final Logger logger  = LoggerFactory.getLogger(CsFaqController.class);

	@Autowired
	CsTransferDao csTransferDao;

	@Autowired
	TransferDao transferDao;

	@Override
	public List<CsMcpUserCntrDto> telNoList(String userId) {
		// TODO Auto-generated method stub
		transferDao.trnsStausEdit();
		return csTransferDao.telNoList(userId);
	}

	@Override
	public int grantorRegSave(NflChgTrnsDto nflChgTrnsDto) {
		return csTransferDao.grantorRegSave(nflChgTrnsDto);
	}

	@Override
	public NflChgTrnsfeDto assigneeReg(UserSessionDto userSessionDto) {
		return csTransferDao.assigneeReg(userSessionDto);
	}

	@Override
	public NflChgTrnsDto checkTrnsApyNoAjax(NflChgTrnsfeDto nflChgTrnsfeDto) {
		return csTransferDao.checkTrnsApyNoAjax(nflChgTrnsfeDto);
	}

	@Override
	public int assigneeRegSave(NflChgTrnsfeDto nflChgTrnsfeDto) {
		int result;
		result = csTransferDao.assigneeRegSave(nflChgTrnsfeDto);

		nflChgTrnsfeDto.setTmpStatusVal("2");
		csTransferDao.statusEdit(nflChgTrnsfeDto);	//명의변경신청 상태값(1:대기중, 2:신청완료, 3:처리완료, 4:부재중, 5:기한초과)
		return result;
	}

	@Override
	public int checkDupl(NflChgTrnsfeDto nflChgTrnsfeDto) {
		return csTransferDao.checkDupl(nflChgTrnsfeDto);
	}

}
