package com.ktis.msp.batch.job.rcp.cantransfermgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper.McpLogTransferMapper;

@Service
public class McpLogTransferService extends BaseService {

	@Autowired
	private McpLogTransferMapper mcpMapper;
	
	/**
	 * MCP LOG 데이터 이관
	 * MCP 에는 6개월치 보관하고, CAN 에는 2년치 보관 ( 총 로그 보관기간 2년 6개월 )
	 * @return
	 * @throws MvnoServiceException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int setMcpLogTransfer() throws MvnoServiceException {
		int procCnt = 0;
		
		// 이관대상월 조회
		String trgtYm = mcpMapper.getTrgtYm(-7);
		LOGGER.debug("trgtYm=" + trgtYm);
		
		// MCP -> CAN 으로 로그데이터 이관
		procCnt = mcpMapper.insertMcpLogTransfer(trgtYm);
		LOGGER.debug("procCnt=" + procCnt);
		
		// MCP 로그데이터 삭제
		mcpMapper.deleteMcpLogTransfer(trgtYm);
		
		// CAN 로그삭제 대상월 조회
		String canTrgtYm = mcpMapper.getTrgtYm(-31);
		LOGGER.debug("canTrgtYm=" + canTrgtYm);
		
		// CAN 로그데이터 삭제
		mcpMapper.deleteCanMcpLog(canTrgtYm);
		
		return procCnt;
	}
	
}
