package com.ktmmobile.msf.domains.form.form.common.repository.smartform;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;

@Mapper
public interface MsfRequestReadMapper {

    McpCancelRequestVo selectMcpCancelRequest(@Param("requestKey") Long requestKey);
}
