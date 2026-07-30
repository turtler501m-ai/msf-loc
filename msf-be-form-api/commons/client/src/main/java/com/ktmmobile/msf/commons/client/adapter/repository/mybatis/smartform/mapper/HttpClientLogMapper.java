package com.ktmmobile.msf.commons.client.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.client.adapter.repository.mybatis.smartform.HttpClientLogRow;
import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;

/**
 * HTTP client 호출 이력 저장 MyBatis Mapper
 */
@AutoAuditing(includeAmendColumns = false, modifier = "SYSTEM", fallbackClientIp = true)
@Mapper
public interface HttpClientLogMapper {

    /** 외부 API 호출 이력 저장 */
    int insertHttpClientLog(HttpClientLogRow log);
}
