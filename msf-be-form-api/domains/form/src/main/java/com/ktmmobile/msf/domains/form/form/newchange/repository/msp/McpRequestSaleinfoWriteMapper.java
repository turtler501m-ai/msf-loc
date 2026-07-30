package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestSaleinfoVo;

@Mapper
public interface McpRequestSaleinfoWriteMapper {

    //MCP_REQUEST_SALEINFO
    void insertMcpRequestSaleinfo(McpRequestSaleinfoVo mcpRequestSaleinfoVo);

    void updateMcpRequestSaleinfo(McpRequestSaleinfoVo mcpRequestSaleinfoVo);
}
