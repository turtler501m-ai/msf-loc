package com.ktis.msp.base.interceptor.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("errMapper")
public interface ErrMapper {
	int insertLog(Map<String, Object> pReqParamMap);
}
