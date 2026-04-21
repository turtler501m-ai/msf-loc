package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FormCommReadMapper {

    String generateResNo();

    long generateRequestKey();

    long getCustRequestSeq();

    AgentInfoDto selectAgentInfo(String cntpntCd);


}
