package com.ktis.msp.rcp.rcpMgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("RcpCustPointMapper")
public interface RcpCustPointMapper {
	
	List<?> getRcpCustPointList(Map<String, Object> param);
	
	List<?> getRcpCustPointListByExcel(Map<String, Object> param);

}
