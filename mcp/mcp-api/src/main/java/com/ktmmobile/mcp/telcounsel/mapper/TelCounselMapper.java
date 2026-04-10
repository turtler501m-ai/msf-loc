package com.ktmmobile.mcp.telcounsel.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.telcounsel.dto.TelCounselDto;

@Mapper
public interface TelCounselMapper {

	/**
	 * @param TelCounselDto telCounselDto
	 * @return
	 * @throws Exception
	 */
	void telCounsel(TelCounselDto telCounselDto);

}
