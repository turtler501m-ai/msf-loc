package com.ktis.msp.batch.job.cmn.cmnmgmt.mapper;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("juiceOnlineChkMapper")
public interface JuiceOnlineChkMapper {
	int getJuiceOnlineCnt();
	List<String> getSendTargetInfo();
}
