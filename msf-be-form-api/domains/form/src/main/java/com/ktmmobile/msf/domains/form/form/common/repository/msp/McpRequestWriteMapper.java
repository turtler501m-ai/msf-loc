package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;

@Mapper
public interface McpRequestWriteMapper {

    int insertMcpCancelRequest(McpCancelRequestVo vo);

    int insertMcpRequestCstmr(McpRequestCstmrVo vo);
}
