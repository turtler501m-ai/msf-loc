package com.ktmmobile.msf.commons.client.application.port.out;

import org.springframework.core.Ordered;
import org.springframework.http.client.ClientHttpRequestInterceptor;

public interface GroupHttpClientInterceptor extends ClientHttpRequestInterceptor, Ordered {

    boolean supports(String groupName);

    @Override
    default int getOrder() {
        return 0;
    }
}
