package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestStateVo;

@Mapper
public interface McpRequestStateWriteMapper {

    //MCP_REQUEST_STATE
    void insertMcpRequestState(McpRequestStateVo mcpRequestStateVo);

    void updateMcpRequestState(McpRequestStateVo mcpRequestStateVo);
}
