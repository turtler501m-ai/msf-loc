package com.ktmmobile.mcp.bank.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.bank.dto.McpAccessTokenDto;

@Mapper
public interface BankMapper {

	public McpAccessTokenDto selectAccessToken(McpAccessTokenDto mcpAccesstokenDto);

	public int deleteAccessToken(McpAccessTokenDto mcpAccesstokenDto);

	public int insertAccessToken(McpAccessTokenDto mcpAccesstokenDto);

	public McpAccessTokenDto selectAccessTokenWithTime(McpAccessTokenDto mcpAccessTokenDto);


}
