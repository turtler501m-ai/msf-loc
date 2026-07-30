package com.ktmmobile.msf.commons.client.support.interceptor;

import java.util.function.Supplier;

import org.springframework.http.HttpHeaders;

/**
 * Authorization 헤더 전용 재사용 interceptor
 */
public class AuthorizationHeaderHttpClientInterceptor extends HeaderValueHttpClientInterceptor {

    public AuthorizationHeaderHttpClientInterceptor(Supplier<String> authorizationSupplier) {
        super(HttpHeaders.AUTHORIZATION, authorizationSupplier);
    }

    public AuthorizationHeaderHttpClientInterceptor(
        Supplier<String> authorizationSupplier,
        boolean overrideExisting
    ) {
        super(HttpHeaders.AUTHORIZATION, authorizationSupplier, overrideExisting);
    }
}
