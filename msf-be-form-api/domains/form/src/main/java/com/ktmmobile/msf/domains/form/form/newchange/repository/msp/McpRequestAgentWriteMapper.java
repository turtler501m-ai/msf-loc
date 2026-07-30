package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestAgentVo;

@Mapper
public interface McpRequestAgentWriteMapper {

    //MCP_REQUEST_AGENT
    void insertMcpRequestAgent(McpRequestAgentVo mcpRequestAgentVo);

    void updateMcpRequestAgent(McpRequestAgentVo mcpRequestAgentVo);
}
