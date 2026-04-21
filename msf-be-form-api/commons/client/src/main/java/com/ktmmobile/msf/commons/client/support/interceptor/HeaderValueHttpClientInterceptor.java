package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

public class HeaderValueHttpClientInterceptor implements ClientHttpRequestInterceptor {

    private final String headerName;
    private final Supplier<String> headerValueSupplier;
    private final boolean overrideExisting;

    public HeaderValueHttpClientInterceptor(
        String headerName,
        Supplier<String> headerValueSupplier
    ) {
        this(headerName, headerValueSupplier, false);
    }

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
