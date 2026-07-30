package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestClauseVo;

@Mapper
public interface McpRequestClauseWriteMapper {

    //MCP_REQUEST_AGENT
    void insertMcpRequestClause(McpRequestClauseVo mcpRequestClauseVo);

    void updateMcpRequestClause(McpRequestClauseVo mcpRequestClauseVo);
}
