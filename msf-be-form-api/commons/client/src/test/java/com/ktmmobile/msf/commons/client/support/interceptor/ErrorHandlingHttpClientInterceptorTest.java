package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.client.support.exception.ClientException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ErrorHandlingHttpClientInterceptorTest {

    private static final String MESSAGE = "외부 서비스 연동 오류";
    private static final String GROUP_NAME = "msp-prx";
    private static final URI URI_VALUE = URI.create("https://example.com/api/test");

    private final ErrorHandlingHttpClientInterceptor interceptor = new ErrorHandlingHttpClientInterceptor(
        new ObjectMapper(),
        MESSAGE,
        GROUP_NAME
    );

    @Test
    @DisplayName("오류 응답 메시지에 groupName과 호출 URL을 포함한다")
    void includeGroupNameAndUrlWhenResponseHasErrorStatus() {
        ClientHttpRequestExecution execution = (_, _) -> jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, """
            {"message":"처리 실패"}
            """);

        assertThatThrownBy(() -> interceptor.intercept(request(), new byte[0], execution))
            .isInstanceOf(ClientException.class)
            .hasMessage("외부 서비스 연동 오류 (groupName=msp-prx) (url=https://example.com/api/test): 처리 실패");
    }

    @Test
    @DisplayName("요청 실행 예외 메시지에 groupName과 호출 URL을 포함한다")
    void includeGroupNameAndUrlWhenExecutionFails() {
        ClientHttpRequestExecution execution = (_, _) -> {
            throw new IOException("connection refused");
        };

        assertThatThrownBy(() -> interceptor.intercept(request(), new byte[0], execution))
            .isInstanceOf(ClientException.class)
            .hasMessage("외부 서비스 연동 오류 (groupName=msp-prx) (url=https://example.com/api/test): IOException: connection refused")
            .hasNoCause()
            .satisfies(e -> assertThat(e.getStackTrace()).isEmpty());
    }

    private static ClientHttpResponse jsonResponse(HttpStatus status, String body) {
        MockClientHttpResponse response = new MockClientHttpResponse(body.getBytes(), status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        return response;
    }

    private static org.springframework.http.HttpRequest request() {
        return new org.springframework.http.HttpRequest() {

            @Override
            public HttpMethod getMethod() {
                return HttpMethod.GET;
            }

            @Override
            public URI getURI() {
                return URI_VALUE;
            }

            @Override
            public HttpHeaders getHeaders() {
                return HttpHeaders.EMPTY;
            }

            @Override
            public Map<String, Object> getAttributes() {
                return Map.of();
            }
        };
    }
}
