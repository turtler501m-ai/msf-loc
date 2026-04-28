package com.ktmmobile.msf.domains.shared.common.sms.adapter.repository.mybatis.msp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.shared.common.sms.application.dto.MspSmsData;

@Mapper
public interface MspSmsMapper {

    Integer insertSmsData(MspSmsData mspSmsData);
}
