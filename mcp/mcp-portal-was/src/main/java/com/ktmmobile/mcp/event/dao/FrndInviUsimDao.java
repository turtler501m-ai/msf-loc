package com.ktmmobile.mcp.event.dao;

import com.ktmmobile.mcp.event.dto.FrndInviUsimDto;

public interface FrndInviUsimDao {

	public int chkDup(FrndInviUsimDto frndInviUsimDto);

	public int frndInviUsimReg(FrndInviUsimDto frndInviUsimDto);

}
