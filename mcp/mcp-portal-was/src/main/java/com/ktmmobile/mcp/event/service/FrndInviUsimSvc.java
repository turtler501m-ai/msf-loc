package com.ktmmobile.mcp.event.service;

import com.ktmmobile.mcp.event.dto.FrndInviUsimDto;

public interface FrndInviUsimSvc {

	public int chkCstmrInfo(FrndInviUsimDto frndInviUsimDto);

	public int frndInviUsimReg(FrndInviUsimDto frndInviUsimDto);


}
