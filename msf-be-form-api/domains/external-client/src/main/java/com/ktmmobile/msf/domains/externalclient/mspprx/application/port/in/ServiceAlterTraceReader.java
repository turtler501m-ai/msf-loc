package com.ktmmobile.msf.domains.externalclient.mspprx.application.port.in;

/**
 * 서비스 변경 이력 조회 포트
 */
public interface ServiceAlterTraceReader {

    /**
     * 최근 1시간 요금제 변경 성공 이력 수
     */
    int countRecentSuccessfulPlanChange(String ncn, String targetSocCode);
}
