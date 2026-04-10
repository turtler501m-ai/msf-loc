package com.ktmmobile.mcp.nstep.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.nstep.mapper.NstepMapper;
import com.ktmmobile.mcp.phone.dto.CommonSearchDto;

@RestController
public class NstepController {

	private static final Logger logger = LoggerFactory.getLogger(NstepController.class);

	@Autowired
	NstepMapper nstepMapper;

	/**
     * @Description : 커넥션 타임 기준 정보 조회
     * @param CommonSearchDto
     * @return int
     */
	@RequestMapping(value = "/nstep/getNstepConnectTimeout", method = RequestMethod.POST)
	public int getNstepConnectTimeout(@RequestBody CommonSearchDto commonSearchDto) {

		int result = 0;

		try {
			// Database 에서 조회함.
			result = nstepMapper.getNstepConnectTimeout();

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return result;
	}

	/**
     * @Description : 조직정보 갯수 조회
     * @param String
     * @return int
     */
	@RequestMapping(value = "/nstep/getOrgCnt", method = RequestMethod.POST)
	public int getOrgCnt(@RequestBody String orgnId) {

		int result = 0;

		try {
			// Database 에서 조회함.
			result = nstepMapper.getOrgCnt(orgnId);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return result;
	}

	/**
     * @Description : 조직정보 조회
     * @param String
     * @return String
     */
	@RequestMapping(value = "/nstep/getOrgnId", method = RequestMethod.POST)
	public String getOrgnId(@RequestBody String orgnId) {

		String result = null;

		try {
			// Database 에서 조회함.
			result = nstepMapper.getOrgnId(orgnId);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}

		return result;
	}


}
