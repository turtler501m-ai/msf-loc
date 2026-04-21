package com.ktmmobile.msf.domains.form.form.servicechange.repository.msp;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChangPageMapper {

    MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo);

    List<McpUserCntrMngDto> selectCntrList(Map<String, String> params);

    McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto);
}
