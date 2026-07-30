package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.time.Instant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import com.ktmmobile.msf.commons.client.application.dto.BufferedClientHttpResponse;
import com.ktmmobile.msf.commons.client.application.port.out.CommonHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.application.port.out.HttpClientLogRecorder;
import com.ktmmobile.msf.commons.client.application.service.HttpClientLogFactory;
import com.ktmmobile.msf.commons.client.domain.dto.HttpClientLog;
import com.ktmmobile.msf.commons.client.support.properties.HttpClientRecordingProperties;

/**
 * 선언형 HTTP 클라이언트 요청/응답 기록
 */
@Slf4j
@RequiredArgsConstructor
public class HttpClientRecordingInterceptor implements CommonHttpClientInterceptor {

    private final String groupName;
    private final HttpClientRecordingProperties recordingProperties;
    private final HttpClientLogFactory httpClientLogFactory;
    private final ObjectProvider<HttpClientLogRecorder> httpClientLogRecorders;

    @Override
    @SuppressWarnings("PMD.CloseResource")
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (!recordingProperties.enabled()) {
            return execution.execute(request, body);
        }

        long startTime = System.currentTimeMillis();
        Instant requestedAt = Instant.now();

        try {
            ClientHttpResponse response = execution.execute(request, body);
            // 응답 바디 재사용을 위한 버퍼링
            byte[] responseBody = StreamUtils.copyToByteArray(response.getBody());
            BufferedClientHttpResponse bufferedResponse = new BufferedClientHttpResponse(response, responseBody);
            long duration = System.currentTimeMillis() - startTime;

            HttpClientLog httpClientLog = httpClientLogFactory.createSuccessLog(
                groupName,
                request,
                body,
                bufferedResponse,
                responseBody,
                duration,
                requestedAt
            );
            recordLog(httpClientLog);

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
            recordLog(httpClientLog);
            throw e;
        }
    }

    private void recordLog(HttpClientLog httpClientLog) {
        httpClientLogRecorders.orderedStream()
            .forEach(recorder -> recordLog(recorder, httpClientLog));
    }

    private void recordLog(HttpClientLogRecorder recorder, HttpClientLog httpClientLog) {
        try {
            recorder.recordLog(httpClientLog);
        } catch (RuntimeException e) {
            log.warn(
                "HTTP client log recorder failed. recorder={}, groupName={}, uri={}",
                recorder.getClass().getSimpleName(),
                httpClientLog.groupName(),
                httpClientLog.uri(),
                e
            );
        }
    }
}
