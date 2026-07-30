package com.ktmmobile.msf.commons.client.application.port.out;

import com.ktmmobile.msf.commons.client.domain.dto.HttpClientLog;

/**
 * HTTP client 기록 저장/출력 포트
 */
public interface HttpClientLogRecorder {

    /**
     * HTTP client 호출 기록 저장 또는 출력
     */
    void recordLog(HttpClientLog httpClientLog);
}
