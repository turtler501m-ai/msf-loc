package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.ktmmobile.msf.commons.client.application.port.out.GroupHttpClientInterceptor;

/**
 * 특정 그룹에만 공통 interceptor 구현을 연결할 때 사용할 베이스 클래스
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
