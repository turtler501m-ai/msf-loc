/**
 *
 */
package com.ktmmobile.mcp.common.dao;

import com.ktmmobile.mcp.common.dto.NaverDto;

public interface NaverCertfyDao {

	public boolean updateMcpAuthData(NaverDto NaverDto);

    public NaverDto getMcpAuthData(NaverDto NaverDto);
}
