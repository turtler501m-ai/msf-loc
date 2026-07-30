package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpUploadPhoneInfoVo;

@Mapper
public interface McpUploadPhoneInfoWriteMapper {

    //MCP_UPLOAD_PHONE_INFO
    void insertMcpUploadPhoneInfo(McpUploadPhoneInfoVo mcpUploadPhoneInfoVo);
}

