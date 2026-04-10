package com.ktmmobile.mcp.usermanage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.usermanage.dto.McpUserCertDto;

@Mapper
public interface UserManageMapper {
	
	/**
	 * 
	 * @param mcpUserCertDto
	 * @return
	 * @throws Exception
	 */
	McpUserCertDto selectMspJuoSubInfo(McpUserCertDto mcpUserCertDto);
	
	/**
	 * 
	 * @param cntrUserId
	 * @return
	 * @throws Exception
	 */
	List<McpUserCertDto> selectMcpUserCntrDtoList(String cntrUserId);

    List<McpUserCertDto> selectMspJuoSubStopInfo(McpUserCertDto mcpUserCertDto);

	McpUserCertDto selectMspJuoSubActive(McpUserCertDto mcpUserCertDto);
	
	/**
     * @Description : 홈페이지 가입 고객이 사용중인 회선 리스트 조회(SUB_STATUS가 A인것만)
     * @param McpUserCertDto
     * @return List<McpUserCertDto>
     * @Author : wooki
     * @CreateDate : 2023.04.05
	 */
    List<McpUserCertDto> getMcpUserCntrInfoA(McpUserCertDto mcpUserCertDto);
    
    /**
     * @Description : 홈페이지 가입 고객의 userId로 사용중인 회선 리스트 조회(SUB_STATUS가 A인것만)
     * @param McpUserCertDto
     * @return List<McpUserCertDto>
     * @Author : wooki
     * @CreateDate : 2023.04.05
	 */
    List<McpUserCertDto> getMcpUserCntrInfoAByUserId(McpUserCertDto mcpUserCertDto);
}
