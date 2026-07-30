package com.ktmmobile.msf.commons.client.application.port.out;

import org.springframework.core.Ordered;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * 모든 HTTP client 그룹에 공통 적용할 interceptor 계약
 */
public interface CommonHttpClientInterceptor extends ClientHttpRequestInterceptor, Ordered {

    @Override
    default int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
