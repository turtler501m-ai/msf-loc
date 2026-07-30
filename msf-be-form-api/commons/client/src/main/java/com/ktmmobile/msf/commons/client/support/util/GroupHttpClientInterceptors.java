package com.ktmmobile.msf.commons.client.support.util;

import java.util.List;

import org.springframework.http.client.ClientHttpRequestInterceptor;

import com.ktmmobile.msf.commons.client.application.port.out.GroupHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.support.interceptor.AbstractGroupHttpClientInterceptor;

/**
 * 그룹 전용 interceptor 등록용 팩토리 유틸
 */
public final class GroupHttpClientInterceptors {

    private GroupHttpClientInterceptors() {
    }

    /**
     * 특정 그룹에만 적용되는 기본 order interceptor 생성
     */
    public static GroupHttpClientInterceptor forGroup(
        String groupName,
        ClientHttpRequestInterceptor delegate
    ) {
        return new AbstractGroupHttpClientInterceptor(List.of(groupName), delegate) {
        };
    }

    /**
     * 특정 그룹에만 적용되는 지정 order interceptor 생성
     */
    public static GroupHttpClientInterceptor forGroup(
        String groupName,
        ClientHttpRequestInterceptor delegate,
        int order
    ) {
        return new AbstractGroupHttpClientInterceptor(List.of(groupName), delegate, order) {
        };
    }
}
