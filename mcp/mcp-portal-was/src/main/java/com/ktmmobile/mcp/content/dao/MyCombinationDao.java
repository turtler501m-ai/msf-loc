package com.ktmmobile.mcp.content.dao;

import java.util.List;

import com.ktmmobile.mcp.content.dto.McpReqCombineDto;

public interface MyCombinationDao {

    public boolean insertMcpReqCombine(McpReqCombineDto mcpReqCombineDto);

    public List<McpReqCombineDto> selectMcpReqCombine(McpReqCombineDto mcpReqCombineDto);


}
