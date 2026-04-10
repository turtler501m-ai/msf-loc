package com.ktmmobile.mcp.appform.dao;

import com.ktmmobile.mcp.appform.dto.AbuseImeiHistDto;
import com.ktmmobile.mcp.appform.dto.McpEsimOmdTraceDto;
import com.ktmmobile.mcp.appform.dto.McpUploadPhoneInfoDto;

public interface EsimDao {

	public int insertMcpUploadPhoneInfoDto(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto);
	public int updateMcpUploadPhoneInfoDto(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto);
	public int insertMcpEsimOmdTrace(McpEsimOmdTraceDto mcpEsimOmdTraceDto);
	public int updateServiceRst(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto);
	public McpUploadPhoneInfoDto doubleChk(int uploadPhoneSrlNo);

	int insertAbuseImeiHist(AbuseImeiHistDto abuseImeiHistDto);
}
