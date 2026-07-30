package com.ktmmobile.msf.commons.client.application.port.out;

import org.springframework.core.Ordered;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * 특정 HTTP client 그룹에 선택 적용할 interceptor 계약
 */
public interface GroupHttpClientInterceptor extends ClientHttpRequestInterceptor, Ordered {

    /** 적용 대상 그룹 여부 */
    boolean supports(String groupName);

    @Override
    default int getOrder() {
        return 0;
    }
}
