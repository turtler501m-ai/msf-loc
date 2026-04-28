package com.ktmmobile.msf.domains.form.form.servicechange.repository.msp;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;

@Mapper
public interface ApiMypageMapper {

    List<McpUserCntrMngDto> selectCntrList(HashMap<String, String> params);
}
