package com.ktmmobile.msf.domains.cache.commoncode.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

/**
 * MCP 공통코드 조회 MyBatis Mapper
 */
@Mapper
public interface McpCommonCodeMapper {

    /** MCP_CODE 공통코드 목록 조회 */
    List<CommonCode> selectCodeList(@Param("groupIds") List<String> groupIds);

    /** NMCP_CD_DTL 공통코드 목록 조회 */
    List<CommonCode> selectDetailList(@Param("groupIds") List<String> groupIds);
}
