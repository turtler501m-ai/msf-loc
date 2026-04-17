package com.ktmmobile.msf.domains.form.form.newchange.repository.msp;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoDto;

@Mapper
public interface FormCommMapper {

    String generateResNo();

    long generateRequestKey();

    long getCustRequestSeq();

    AgentInfoDto selectAgentInfo(String cntpntCd);


}
