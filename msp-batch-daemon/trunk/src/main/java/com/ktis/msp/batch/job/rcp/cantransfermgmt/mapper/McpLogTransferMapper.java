package com.ktis.msp.batch.job.rcp.cantransfermgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("McpLogTransferMapper")
public interface McpLogTransferMapper {
	
	String getTrgtYm(int diff);
	
	int insertMcpLogTransfer(String trgtYm);
	
	void deleteMcpLogTransfer(String trgtYm);
	
	void deleteCanMcpLog(String trgtYm);
}
