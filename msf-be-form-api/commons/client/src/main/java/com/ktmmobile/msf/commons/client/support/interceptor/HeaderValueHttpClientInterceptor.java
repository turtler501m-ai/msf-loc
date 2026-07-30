package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

/**
 * 지정한 헤더 값을 동적으로 주입하는 재사용 interceptor
 */
public class HeaderValueHttpClientInterceptor implements ClientHttpRequestInterceptor {

    private final String headerName;
    private final Supplier<String> headerValueSupplier;
    private final boolean overrideExisting;

    /**
     * 기존 헤더 값 유지 방식의 동적 헤더 interceptor 생성
     */
    public HeaderValueHttpClientInterceptor(
        String headerName,
        Supplier<String> headerValueSupplier
    ) {
        this(headerName, headerValueSupplier, false);
    }

    /**
     * 기존 헤더 값 덮어쓰기 여부를 지정하는 동적 헤더 interceptor 생성
     */
    public HeaderValueHttpClientInterceptor(
        String headerName,
        Supplier<String> headerValueSupplier,
        boolean overrideExisting
    ) {
        this.headerName = headerName;
        this.headerValueSupplier = headerValueSupplier;
        this.overrideExisting = overrideExisting;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        if (overrideExisting || !StringUtils.hasText(headers.getFirst(headerName))) {
            String headerValue = headerValueSupplier.get();
            if (StringUtils.hasText(headerValue)) {
                headers.set(headerName, headerValue);
            }
        }
        return execution.execute(request, body);
    }
}
