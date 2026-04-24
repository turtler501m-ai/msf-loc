package com.ktis.msp.batch.job.cmn.smsmgmt.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("osstStatChkMapper")
public interface OsstStatChkMapper {
	List<Map<String, Object>> getErrCdList();
	String getErrMsg(String errCd);
	List<String> getSendTargetInfo();
}
