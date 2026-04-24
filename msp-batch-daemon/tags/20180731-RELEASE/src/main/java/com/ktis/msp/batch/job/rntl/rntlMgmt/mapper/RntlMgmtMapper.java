package com.ktis.msp.batch.job.rntl.rntlMgmt.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rntlMgmtMapper")
public interface RntlMgmtMapper {
	
	/**
	 * 무료렌탈정산내역생성
	 */
	void callFreeRntlCalcInfo(Map<String, Object> param);
}

