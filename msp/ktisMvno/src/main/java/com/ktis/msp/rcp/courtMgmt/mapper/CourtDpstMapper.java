package com.ktis.msp.rcp.courtMgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("CourtDpstMapper")
public interface CourtDpstMapper {
	
	List<?> getCourtDpstList(Map<String, Object> param);
	
	List<?> getCourtDpstListByExcel(Map<String, Object> param);

}
