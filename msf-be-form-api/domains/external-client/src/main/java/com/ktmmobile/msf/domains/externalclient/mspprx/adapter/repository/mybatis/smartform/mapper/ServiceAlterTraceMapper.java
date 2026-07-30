package com.ktmmobile.msf.domains.externalclient.mspprx.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceId;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.entity.ServiceAlterTrace;

/**
 * 서비스 변경 이력 MyBatis Mapper
 */
@Mapper
public interface ServiceAlterTraceMapper {

    /**
     * 서비스 변경 이력 저장
     */
    ServiceAlterTraceId insert(ServiceAlterTrace serviceAlterTrace);

    /**
     * 최근 1시간 요금제 변경 성공 이력 수
     */
    int countRecentSuccessfulPlanChange(
        @Param("ncn") String ncn,
        @Param("targetSocCode") String targetSocCode
    );
}
