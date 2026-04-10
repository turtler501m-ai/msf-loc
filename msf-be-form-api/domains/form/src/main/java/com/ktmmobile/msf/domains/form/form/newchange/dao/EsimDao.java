package com.ktmmobile.msf.domains.form.form.newchange.dao;

import com.ktmmobile.msf.domains.form.form.newchange.dto.AbuseImeiHistDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.McpEsimOmdTraceDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.McpUploadPhoneInfoDto;

public interface EsimDao {

    public int insertMcpUploadPhoneInfoDto(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto);

    public int updateMcpUploadPhoneInfoDto(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto);

    public int insertMcpEsimOmdTrace(McpEsimOmdTraceDto mcpEsimOmdTraceDto);

    public int updateServiceRst(McpUploadPhoneInfoDto mcpUploadPhoneInfoDto);

    public McpUploadPhoneInfoDto doubleChk(int uploadPhoneSrlNo);

    int insertAbuseImeiHist(AbuseImeiHistDto abuseImeiHistDto);
}
