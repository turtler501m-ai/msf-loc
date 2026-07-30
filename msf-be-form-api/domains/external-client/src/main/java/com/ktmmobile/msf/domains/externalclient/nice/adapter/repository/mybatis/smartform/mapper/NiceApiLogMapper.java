package com.ktmmobile.msf.domains.externalclient.nice.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.externalclient.nice.domain.entity.NiceApiAccountCheckLog;

/**
 * NICE API 이력 저장 MyBatis Mapper
 */
@AutoAuditing
@Mapper
public interface NiceApiLogMapper {

    /** MSF_NICE_LOG 계좌인증 결과 이력 저장 */
    int insertAccountCheckLog(NiceApiAccountCheckLog log);
}
