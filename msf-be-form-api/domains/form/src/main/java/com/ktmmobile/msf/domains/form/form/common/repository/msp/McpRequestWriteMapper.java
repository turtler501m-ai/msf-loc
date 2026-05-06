package com.ktmmobile.msf.domains.form.form.common.repository.msp;

import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface McpRequestWriteMapper {

    int insertMcpCancelRequest(McpCancelRequestVo vo);

    int insertMcpRequestCstmr(McpRequestCstmrVo vo);

    int insertMcpRequest(McpRequestVo vo);
}
