package com.ktmmobile.msf.commons.client.domain.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * HTTP client 요청/응답 기록 데이터
 */
public record HttpClientLog(
    String traceId,
    String groupName,
    String method,
    String uri,
    String path,
    String query,
    Request request,
    Response response,
    Long durationMs,
    boolean success,
    String errorMessage,
    Instant requestedAt
) {

    /**
     * HTTP client 요청 기록 데이터
     */
    public record Request(
        Map<String, List<String>> headers,
        String sanitizedBody, // 정제용 바디 (마스킹 및 축약 적용)
        String retainedBody   // 원문 바디 (마스킹 적용)
    ) {
    }

    /**
     * HTTP client 응답 기록 데이터
     */
    public record Response(
        Integer status,
        Map<String, List<String>> headers,
        String sanitizedBody, // 정제용 바디 (마스킹 및 축약 적용)
        String retainedBody   // 원문 바디 (마스킹 적용)
    ) {
    }
}
