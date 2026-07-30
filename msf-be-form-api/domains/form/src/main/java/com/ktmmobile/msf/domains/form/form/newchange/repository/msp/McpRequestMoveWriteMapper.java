package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestMoveVo;

@Mapper
public interface McpRequestMoveWriteMapper {

    //MCP_REQUEST_MOVE
    void insertMcpRequestMove(McpRequestMoveVo mcpRequestMoveVo);

    void updateMcpRequestMove(McpRequestMoveVo mcpRequestMoveVo);
}
