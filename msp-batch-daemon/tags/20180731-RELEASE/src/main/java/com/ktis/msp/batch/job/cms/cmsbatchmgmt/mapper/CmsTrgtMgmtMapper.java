package com.ktis.msp.batch.job.cms.cmsbatchmgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("cmsTrgtMgmtMapper")
public interface CmsTrgtMgmtMapper {
	
	/**
	 * 수수료집계정보생성
	 */
	void callCmsTrgtInfoProc(String yyyyMM);
}

