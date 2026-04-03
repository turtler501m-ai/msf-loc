package com.ktmmobile.msf.form.servicechange.dao;

import java.util.List;

import com.ktmmobile.msf.form.servicechange.dto.McpReqCombineDto;

public interface MyCombinationDao {

    public boolean insertMcpReqCombine(McpReqCombineDto mcpReqCombineDto);

    public List<McpReqCombineDto> selectMcpReqCombine(McpReqCombineDto mcpReqCombineDto);


}
