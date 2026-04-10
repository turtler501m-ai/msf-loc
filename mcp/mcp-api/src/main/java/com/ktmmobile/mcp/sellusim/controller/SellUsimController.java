package com.ktmmobile.mcp.sellusim.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.sellusim.dto.SellUsimDto;
import com.ktmmobile.mcp.sellusim.mapper.SellUsimMapper;

@RestController
public class SellUsimController {
	
	
	@Autowired
	SellUsimMapper sellUsimMapper;
	
	/**
	 * 전화번호(개통, 일시정지), ICC_ID 해당하는 개수 조회
	 * @param SellUsimDto
	 * @return int
	 */
	@RequestMapping(value = "/sellUsim/checkUsimRegCount", method = RequestMethod.POST)
	public int checkUsimRegCount(SellUsimDto param) {
		
		int count = 0;
		
		try {
			
			// Database 에서 조회함.
			count = sellUsimMapper.selectCheckUsimRegCount(param);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return count;
	}

}
