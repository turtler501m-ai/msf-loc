package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;

@Mapper
public interface McpRequestCstmrWriteMapper {

    //MCP_REQUEST_CSTMR
    void insertMcpRequestCstmr(McpRequestCstmrVo mcpRequestCstmrVo);

    void updateMcpRequestCstmr(McpRequestCstmrVo mcpRequestCstmrVo);
}
