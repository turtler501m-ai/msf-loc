package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestVo;

@Mapper
public interface McpRequestNewChangeWriteMapper {

    //MCP_REQUEST
    void insertMcpRequest(McpRequestVo mcpRequestVo);

    void updateMcpRequest(McpRequestVo mcpRequestVo);
}
