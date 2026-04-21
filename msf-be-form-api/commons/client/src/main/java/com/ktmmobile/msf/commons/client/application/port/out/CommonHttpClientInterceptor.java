package com.ktmmobile.msf.commons.client.application.port.out;

import org.springframework.core.Ordered;
import org.springframework.http.client.ClientHttpRequestInterceptor;

public interface CommonHttpClientInterceptor extends ClientHttpRequestInterceptor, Ordered {

    @Override
    default int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
