package com.ktmmobile.msf.domains.externalclient.nice.application.port.out;

import com.ktmmobile.msf.domains.externalclient.nice.domain.entity.NiceApiAccountCheckLog;

/**
 * NICE API 이력 저장 포트
 */
public interface NiceApiLogRepository {

    /** NICE 계좌인증 결과 이력 저장 */
    void saveAccountCheckLog(NiceApiAccountCheckLog log);
}
