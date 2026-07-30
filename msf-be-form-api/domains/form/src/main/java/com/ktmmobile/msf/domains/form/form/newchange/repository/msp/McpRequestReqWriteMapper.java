package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestReqVo;

@Mapper
public interface McpRequestReqWriteMapper {

    //MCP_REQUEST_REQ
    void insertMcpRequestReq(McpRequestReqVo mcpRequestReqVo);

    void updateMcpRequestReq(McpRequestReqVo mcpRequestReqVo);
}
