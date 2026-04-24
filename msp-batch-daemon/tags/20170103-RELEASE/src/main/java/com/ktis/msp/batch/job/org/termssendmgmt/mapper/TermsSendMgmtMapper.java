package com.ktis.msp.batch.job.org.termssendmgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("termsSendMgmtMapper")
public interface TermsSendMgmtMapper {
	
	/**
	 * 약관메일발송대상자 추출
	 * @return
	 */
	int insertTermsSendTrgt();
	
}
