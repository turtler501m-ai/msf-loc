package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;

@Mapper
public interface McpRequestWriteMapper {

    int insertMcpCancelRequest(McpCancelRequestVo vo);
}
