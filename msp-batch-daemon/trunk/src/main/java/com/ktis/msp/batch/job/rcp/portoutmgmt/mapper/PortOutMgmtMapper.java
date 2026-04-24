package com.ktis.msp.batch.job.rcp.portoutmgmt.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("portOutMgmtMapper")
public interface PortOutMgmtMapper {
	
	/**
	 * 대리점 연동 프로시저 호출
	 * @return
	 */
	void callPMdsPortOutInfo(Map<String, Object> param);
	
}
