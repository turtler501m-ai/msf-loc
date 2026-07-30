package com.ktmmobile.msf.domains.externalclient.mspprx.application.port.in;

import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceId;

/**
 * 서비스 변경 이력 저장 포트
 */
public interface ServiceAlterTraceRecorder {

    /**
     * 서비스 변경 이력 저장
     */
    ServiceAlterTraceId recordTrace(ServiceAlterTraceRequest request);
}
