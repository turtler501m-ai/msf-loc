package com.ktis.msp.rcp.courtMgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CourtSttcMapper")
public interface CourtSttcMapper {
	
	List<?> getCourtSttcList(Map<String, Object> param);
	
	List<?> getCourtSttcListByExcel(Map<String, Object> param);

}
