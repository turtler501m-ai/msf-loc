package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 기본 쿼리 스트링을 공통 방식으로 주입하는 재사용 interceptor다.
 * 같은 이름의 파라미터가 이미 있으면 기본값은 유지하고, overrideExisting=true일 때만 덮어쓴다.
 */
public class QueryParamHttpClientInterceptor implements ClientHttpRequestInterceptor {

    private final String queryParamName;
    private final Supplier<String> queryParamValueSupplier;
    private final boolean overrideExisting;

    public QueryParamHttpClientInterceptor(
        String queryParamName,
        Supplier<String> queryParamValueSupplier
    ) {
        this(queryParamName, queryParamValueSupplier, false);
    }

    public QueryParamHttpClientInterceptor(
        String queryParamName,
        Supplier<String> queryParamValueSupplier,
        boolean overrideExisting
    ) {
        this.queryParamName = queryParamName;
        this.queryParamValueSupplier = queryParamValueSupplier;
        this.overrideExisting = overrideExisting;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String queryParamValue = queryParamValueSupplier.get();
        if (!StringUtils.hasText(queryParamValue)) {
            return execution.execute(request, body);
        }

        URI requestUri = request.getURI();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(requestUri);
        MultiValueMap<String, String> queryParams = uriBuilder.build(true).getQueryParams();
        boolean hasQueryParam = !queryParams.getOrDefault(queryParamName, List.of()).isEmpty();

        if (overrideExisting) {
            uriBuilder.replaceQueryParam(queryParamName, queryParamValue);
        } else if (!hasQueryParam) {
            uriBuilder.queryParam(queryParamName, queryParamValue);
        } else {
            return execution.execute(request, body);
        }

        URI updatedUri = uriBuilder.build(true).toUri();
        HttpRequest wrappedRequest = new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return updatedUri;
            }
        };
        return execution.execute(wrappedRequest, body);
    }
}
