package com.ktmmobile.mcp.nstep.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NstepMapper {

	public int getNstepConnectTimeout();

	public int getOrgCnt(String orgnId);
	public String getOrgnId(String orgnId);

}
