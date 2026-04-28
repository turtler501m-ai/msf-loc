package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;

@Mapper
public interface MsfRequestReadMapper {

    McpCancelRequestVo selectMcpCancelRequest(@Param("requestKey") Long requestKey);

    McpRequestCstmrVo selectMcpCancelRequestCstmr(@Param("requestKey") Long requestKey);
}
