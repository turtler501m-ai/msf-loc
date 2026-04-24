package com.ktis.msp.batch.job.rcp.courtmgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CourtRcvSttcMapper")
public interface CourtRcvSttcMapper {
	void deleteCrSttc();
	
	Integer insertCrSttc();
}