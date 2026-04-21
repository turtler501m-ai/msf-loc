package com.ktmmobile.msf.commons.client.support.util;

import java.util.List;

import org.springframework.http.client.ClientHttpRequestInterceptor;

import com.ktmmobile.msf.commons.client.application.port.out.GroupHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.support.interceptor.AbstractGroupHttpClientInterceptor;

/**
 * 업무 모듈에서 그룹 전용 interceptor를 짧고 직관적으로 등록할 수 있게 돕는 팩토리 유틸이다.
 */
public final class GroupHttpClientInterceptors {

    private GroupHttpClientInterceptors() {
    }

    public static GroupHttpClientInterceptor forGroup(
        String groupName,
        ClientHttpRequestInterceptor delegate
    ) {
        return new AbstractGroupHttpClientInterceptor(List.of(groupName), delegate) {
        };
    }

    public static GroupHttpClientInterceptor forGroup(
        String groupName,
        ClientHttpRequestInterceptor delegate,
        int order
    ) {
        return new AbstractGroupHttpClientInterceptor(List.of(groupName), delegate, order) {
        };
    }
}
