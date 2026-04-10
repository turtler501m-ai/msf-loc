package com.ktmmobile.mcp.telcounsel.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.telcounsel.dto.TelCounselDto;
import com.ktmmobile.mcp.telcounsel.mapper.TelCounselMapper;

@RestController
public class TelCounselController {


	@Autowired
	TelCounselMapper telCounselMapper;

	/**
	 * P_MSP_VOC_LINK_REG 전화상담 등록
	 * @param TelCounselDto telCounselDto
	 * @return
	 */
	@RequestMapping(value = "/telCounsel/telCounsel", method = RequestMethod.GET)
	public void telCounsel(TelCounselDto telCounselDto) {

		try {
			// Database 에서 조회함.
			telCounselMapper.telCounsel(telCounselDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

	}


}
