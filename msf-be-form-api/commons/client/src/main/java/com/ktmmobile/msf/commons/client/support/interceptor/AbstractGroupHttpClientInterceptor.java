package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.ktmmobile.msf.commons.client.application.port.out.GroupHttpClientInterceptor;

/**
 * 공통 interceptor 구현을 특정 그룹들에만 적용하고 싶을 때 사용하는 베이스 클래스다.
 * 업무 모듈에서는 그룹명 목록과 delegate interceptor만 지정해 얇게 확장하면 된다.
 */
public abstract class AbstractGroupHttpClientInterceptor implements GroupHttpClientInterceptor {

    private final List<String> groupNames;
    private final ClientHttpRequestInterceptor delegate;
    private final int order;

    protected AbstractGroupHttpClientInterceptor(
        List<String> groupNames,
        ClientHttpRequestInterceptor delegate
    ) {
        this(groupNames, delegate, 0);
    }

    protected AbstractGroupHttpClientInterceptor(
        List<String> groupNames,
        ClientHttpRequestInterceptor delegate,
        int order
    ) {
        this.groupNames = List.copyOf(groupNames);
        this.delegate = delegate;
        this.order = order;
    }

    @Override
    public boolean supports(String groupName) {
        return groupNames.contains(groupName);
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        return delegate.intercept(request, body, execution);
    }
}
