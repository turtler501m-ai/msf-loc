/**
 *
 */
package com.ktmmobile.mcp.common.dao;

import java.util.List;

import com.ktmmobile.mcp.common.dto.SmartroDto;

public interface SmartroDao {

	public int insertSmartroOrder(SmartroDto smartroDto);

	public List<SmartroDto> getSmartroDataList(String orderno);
}
