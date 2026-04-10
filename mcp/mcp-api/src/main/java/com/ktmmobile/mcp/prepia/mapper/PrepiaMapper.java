package com.ktmmobile.mcp.prepia.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.msp.dto.MspSaleSubsdMstDto;

@Mapper
public interface PrepiaMapper {

	/**
	 * @param
	 * @return
	 * @throws Exception
	 */
	List<MspSaleSubsdMstDto> selectRatePrepia();
	
	/**
	 * @param
	 * @return
	 * @throws Exception
	 */
	List<MspSaleSubsdMstDto> getRateList(Map<String, String> map);	
	
	
	
}
