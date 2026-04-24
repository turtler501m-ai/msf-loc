package com.ktis.msp.batch.job.rsk.retention.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rskRetentionMapper")
public interface RskRetentionMapper {
	
	/**
	 * 해지후재가입정보생성
	 */
	void callRetentionProc(Map<String, Object> param);
}

