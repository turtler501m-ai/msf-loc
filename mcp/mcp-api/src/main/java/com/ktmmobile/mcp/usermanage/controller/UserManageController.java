package com.ktmmobile.mcp.usermanage.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.usermanage.dto.McpUserCertDto;
import com.ktmmobile.mcp.usermanage.mapper.UserManageMapper;

@RestController
public class UserManageController {

    private static Logger logger = LoggerFactory.getLogger(UserManageController.class);

	@Autowired
	UserManageMapper userManageMapper;
	
	/**
	 * 전화번호(개통) 정보 조회
	 * @param mcpUserCertDto
	 * @return
	 */
	@RequestMapping(value = "/usermanage/mspJuoSubInfo", method = RequestMethod.POST)
	public McpUserCertDto mspJuoSubInfo(@RequestBody McpUserCertDto mcpUserCertDto) {

		McpUserCertDto mspJuoSubInfo = null;
		
		try {
			mspJuoSubInfo = userManageMapper.selectMspJuoSubInfo(mcpUserCertDto);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return mspJuoSubInfo;	
	}

	/**
	 * 전화번호(개통) 정지 상태 조회
	 * @param mcpUserCertDto
	 * @return
	 */
	@RequestMapping(value = "/usermanage/mspJuoSubStopInfo", method = RequestMethod.POST)
	public List<McpUserCertDto> mspJuoSubStopInfo(@RequestBody McpUserCertDto mcpUserCertDto) {

 		List<McpUserCertDto> mspJuoSubInfo = new ArrayList<>();
		try {
			mspJuoSubInfo = this.userManageMapper.selectMspJuoSubStopInfo(mcpUserCertDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);

		}
		return mspJuoSubInfo;
	}
	
	/**
	 * 회원아이디 정회원 정보 조회
	 * @param cntrUserId
	 * @return
	 */
	@RequestMapping(value = "/usermanage/mcpUserCntrDtoList", method = RequestMethod.POST)
	public List<McpUserCertDto> mcpUserCntrDtoList(@RequestBody String cntrUserId) {

		List<McpUserCertDto> mcpUserCntrDtoList = null;
		
		try {
			mcpUserCntrDtoList = userManageMapper.selectMcpUserCntrDtoList(cntrUserId);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return mcpUserCntrDtoList;	
	}

	/**
	 * 전화번호(개통) 상태 조회
	 * @param mcpUserCertDto
	 * @return
	 */
	@RequestMapping(value = "/usermanage/mspJuoSubActiveInfo", method = RequestMethod.POST)
	public McpUserCertDto mspJuoSubActiveInfo(@RequestBody McpUserCertDto mcpUserCertDto) {

		McpUserCertDto mspJuoSubInfo = new McpUserCertDto();
		try {
			mspJuoSubInfo = this.userManageMapper.selectMspJuoSubActive(mcpUserCertDto);

		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);

		}
		return mspJuoSubInfo;
	}
	
	/**
     * @Description : 홈페이지 가입 고객이 사용중인 회선 리스트 조회(SUB_STATUS가 A인것만)
     * @param McpUserCertDto
     * @return List<McpUserCertDto>
     * @Author : wooki
     * @CreateDate : 2023.04.05
	 */
	@RequestMapping(value = "/usermanage/getMcpUserCntrInfoA", method = RequestMethod.POST)
	public List<McpUserCertDto> getMcpUserCntrInfoA(@RequestBody McpUserCertDto mcpUserCertDto) {

		List<McpUserCertDto> mcpUserCertDtoList = null;
		
		try {
			mcpUserCertDtoList = userManageMapper.getMcpUserCntrInfoA(mcpUserCertDto);
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return mcpUserCertDtoList;	
	}
	
	/**
     * @Description : 홈페이지 가입 고객이 사용중인 회선 리스트 조회(SUB_STATUS가 A인것만)
     * @param McpUserCertDto
     * @return List<McpUserCertDto>
     * @Author : wooki
     * @CreateDate : 2023.04.05
	 */
	@RequestMapping(value = "/usermanage/getMcpUserCntrInfoAByUserId", method = RequestMethod.POST)
	public List<McpUserCertDto> getMcpUserCntrInfoAByUserId(@RequestBody McpUserCertDto mcpUserCertDto) {

		List<McpUserCertDto> mcpUserCertDtoList = null;
		
		try {
			mcpUserCertDtoList = userManageMapper.getMcpUserCntrInfoAByUserId(mcpUserCertDto);
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return mcpUserCertDtoList;	
	}
}
