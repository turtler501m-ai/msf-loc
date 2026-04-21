package com.ktmmobile.msf.commons.client.application.port.out;

import org.springframework.core.Ordered;

/**
 * 모든 그룹에 공통 적용되지만, 그룹 이름 같은 컨텍스트가 필요해 그룹별 인스턴스를 생성해야 하는 interceptor 팩토리다.
 */
public interface CommonGroupHttpClientInterceptorFactory extends Ordered {

    CommonHttpClientInterceptor create(String groupName);

    @Override
    default int getOrder() {
        return 0;
    }
}
