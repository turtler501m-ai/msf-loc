package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestOsstVo;

@Mapper
public interface McpRequestOsstWriteMapper {

    //MCP_REQUEST_OSST
    void insertMcpRequestOsst(McpRequestOsstVo mcpRequestOsstVo);

    void updateMcpRequestOsst(McpRequestOsstVo mcpRequestOsstVo);
}
