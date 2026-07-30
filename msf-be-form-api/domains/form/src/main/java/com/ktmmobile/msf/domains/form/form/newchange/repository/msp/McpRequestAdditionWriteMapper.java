package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestAdditionVo;

@Mapper
public interface McpRequestAdditionWriteMapper {

    //MCP_REQUEST_ADDITION
    void insertMcpRequestAddition(List<McpRequestAdditionVo> mcpRequestAdditionVoList);

    //void updateMcpRequestAddition(McpRequestAdditionVo mcpRequestAdditionVo);
}
