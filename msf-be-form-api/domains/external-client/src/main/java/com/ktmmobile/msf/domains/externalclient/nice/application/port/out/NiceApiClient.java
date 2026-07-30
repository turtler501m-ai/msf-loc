package com.ktmmobile.msf.domains.externalclient.nice.application.port.out;

import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiAccountCheckRequest;
import com.ktmmobile.msf.domains.externalclient.nice.application.dto.NiceApiAccountCheckResponse;

/**
 * NICE API 외부 연동 포트
 */
public interface NiceApiClient {

    NiceApiAccountCheckResponse checkAccount(NiceApiAccountCheckRequest request);
}
