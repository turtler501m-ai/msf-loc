package com.ktmmobile.mcp.bank.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.bank.dto.McpAccessTokenDto;
import com.ktmmobile.mcp.bank.mapper.BankMapper;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;

@RestController
public class BankController {
	
	@Autowired
	BankMapper bankMapper;
	
	/**
	 * 컨텐츠 이미지 배너 선택
	 * @param bannerDto
	 * @return
	 */
	@RequestMapping(value = "/bank/selectAccessToken", method = RequestMethod.POST)
	public McpAccessTokenDto contentBanner(@RequestBody McpAccessTokenDto mcpAccessTokenDto) {

		McpAccessTokenDto result = null;
		
		try {
			result = bankMapper.selectAccessToken(mcpAccessTokenDto);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return result;
	}

	@RequestMapping(value = "/bank/deleteAccessToken", method = RequestMethod.POST)
	public int deleteAccessToken(@RequestBody McpAccessTokenDto mcpAccessTokenDto) {

		int result = 0;
		
		try {
			result = bankMapper.deleteAccessToken(mcpAccessTokenDto);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/bank/insertAccessToken", method = RequestMethod.POST)
	public int insertAccessToken(@RequestBody McpAccessTokenDto mcpAccessTokenDto) {

		int result = 0;
		
		try {
			result = bankMapper.insertAccessToken(mcpAccessTokenDto);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return result;
	}

	/**
	 * 토큰 가져오기 - 유효시간 1시간
	 * @param mcpAccessTokenDto
	 * @return
	*/
	@RequestMapping(value = "/bank/selectAccessTokenWithTime", method = RequestMethod.POST)
	public McpAccessTokenDto selectAccessTokenWithTime(@RequestBody McpAccessTokenDto mcpAccessTokenDto) {

		McpAccessTokenDto result = null;

		try {
			result = bankMapper.selectAccessTokenWithTime(mcpAccessTokenDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return result;
	}

}
