package com.ktmmobile.msf.domains.shared.common.sms.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsMapper {

    String selectUserPhone(String userId);
}
