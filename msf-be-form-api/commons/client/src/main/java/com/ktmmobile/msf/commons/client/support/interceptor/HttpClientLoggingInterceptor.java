package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.time.Instant;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import com.ktmmobile.msf.commons.client.application.dto.BufferedClientHttpResponse;
import com.ktmmobile.msf.commons.client.application.port.out.CommonHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.application.port.out.HttpClientLogRecorder;
import com.ktmmobile.msf.commons.client.application.service.HttpClientLogFactory;
import com.ktmmobile.msf.commons.client.domain.dto.HttpClientLog;
import com.ktmmobile.msf.commons.client.support.properties.HttpClientLoggingProperties;

/**
 * 모든 선언형 HTTP 클라이언트 호출을 가로채서 요청/응답 정보를 구조화 로그 객체로 만들고
 * 지정된 writer로 위임한다.
 */
@RequiredArgsConstructor
public class HttpClientLoggingInterceptor implements CommonHttpClientInterceptor {

    private final String groupName;
    private final HttpClientLoggingProperties loggingProperties;
    private final HttpClientLogFactory httpClientLogFactory;
    private final HttpClientLogRecorder httpClientLogRecorder;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (!loggingProperties.enabled()) {
            return execution.execute(request, body);
        }

        // 요청 시각과 소요 시간을 함께 남겨야 하므로 interceptor 진입 시점을 기준으로 기록한다.
        long startTime = System.currentTimeMillis();
        Instant requestedAt = Instant.now();

        try {
            ClientHttpResponse response = execution.execute(request, body);
            // 응답 바디 로깅을 위해 스트림을 읽어 버퍼링한다.
            byte[] responseBody = StreamUtils.copyToByteArray(response.getBody());
            BufferedClientHttpResponse bufferedResponse = new BufferedClientHttpResponse(response, responseBody);
            long duration = System.currentTimeMillis() - startTime;

            // 로그 생성과 출력은 별도 객체에 위임해서, 이후 DB 적재나 마스킹 로직을 쉽게 교체할 수 있게 분리했다.
            HttpClientLog httpClientLog = httpClientLogFactory.createSuccessLog(
                groupName,
                request,
                body,
                bufferedResponse,
                responseBody,
                duration,
                requestedAt
            );
            httpClientLogRecorder.recordLog(httpClientLog);

            return bufferedResponse;
        } catch (IOException | RuntimeException e) {
            long duration = System.currentTimeMillis() - startTime;
            HttpClientLog httpClientLog = httpClientLogFactory.createErrorLog(
                groupName,
                request,
                body,
                duration,
                requestedAt,
                e
            );
            httpClientLogRecorder.recordLog(httpClientLog);
            throw e;
        }
    }
}
