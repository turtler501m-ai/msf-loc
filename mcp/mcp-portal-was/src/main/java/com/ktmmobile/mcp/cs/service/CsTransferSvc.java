package com.ktmmobile.mcp.cs.service;

import java.util.List;

import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.cs.dto.CsMcpUserCntrDto;
import com.ktmmobile.mcp.cs.dto.NflChgTrnsDto;
import com.ktmmobile.mcp.cs.dto.NflChgTrnsfeDto;

public interface CsTransferSvc {

	public List<CsMcpUserCntrDto> telNoList(String userId);
	
	public int grantorRegSave(NflChgTrnsDto nflChgTrnsDto);

	public NflChgTrnsfeDto assigneeReg(UserSessionDto userSessionDto);
	
	public NflChgTrnsDto checkTrnsApyNoAjax(NflChgTrnsfeDto nflChgTrnsfeDto);
	
	public int assigneeRegSave(NflChgTrnsfeDto nflChgTrnsfeDto);
	
	public int checkDupl(NflChgTrnsfeDto nflChgTrnsfeDto);
}
