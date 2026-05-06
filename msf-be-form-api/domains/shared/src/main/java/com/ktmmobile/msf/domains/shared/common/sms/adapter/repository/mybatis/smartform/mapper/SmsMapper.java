package com.ktmmobile.msf.domains.shared.common.sms.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.shared.common.sms.domain.entity.IdVerifValidationDetail;

@AutoAuditing
@Mapper
public interface SmsMapper {

    Integer insertMsfCrtVldDtl(IdVerifValidationDetail idVerifValidationDetail);
}
