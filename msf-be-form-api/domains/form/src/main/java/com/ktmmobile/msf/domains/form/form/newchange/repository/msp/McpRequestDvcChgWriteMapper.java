package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestDvcChgVo;

@Mapper
public interface McpRequestDvcChgWriteMapper {

    //MCP_REQUEST_DVC_CHG
    void insertMcpRequestDvcChg(McpRequestDvcChgVo mcpRequestDvcChgVo);

    void updateMcpRequestDvcChg(McpRequestDvcChgVo mcpRequestDvcChgVo);
}
