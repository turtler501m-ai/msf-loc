package com.ktis.msp.rcp.courtMgmt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CourtRcvCstmrMapper")
public interface CourtRcvCstmrMapper {

    List<?> getCrCstmrList(Map<String, Object> param);

    List<?> getCrCstmrListByExcel(Map<String, Object> param);
}