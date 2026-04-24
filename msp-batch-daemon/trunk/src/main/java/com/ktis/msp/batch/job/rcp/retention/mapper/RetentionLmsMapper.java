package com.ktis.msp.batch.job.rcp.retention.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("retentionLmsMapper")
public interface RetentionLmsMapper {

	// 대상 고객 추출
	int insertRetentionLmsTrgt();
	
	// LMS 전송
	List<Map<String, Object>> selectLmsSendList(int maxCnt);	
	
	int updateLmsSendList(SmsSendVO paramVO) throws Exception;
}
