package com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out;

import com.ktmmobile.msf.domains.externalclient.mspprx.domain.entity.ServiceAlterTrace;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceId;

/**
 * 서비스 변경 이력 저장소 포트
 */
public interface ServiceAlterTraceRepository {

    /**
     * 서비스 변경 이력 저장
     */
    ServiceAlterTraceId recordTrace(ServiceAlterTrace serviceAlterTrace);

    /**
     * 최근 1시간 요금제 변경 성공 이력 수
     */
    int countRecentSuccessfulPlanChange(String ncn, String targetSocCode);
}
