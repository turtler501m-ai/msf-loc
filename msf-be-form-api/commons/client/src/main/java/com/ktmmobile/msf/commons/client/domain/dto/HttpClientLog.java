package com.ktmmobile.msf.commons.client.domain.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record HttpClientLog(
    String groupName,
    String method,
    String uri,
    Request request,
    Response response,
    Long durationMs,
    boolean success,
    String errorMessage,
    Instant requestedAt
) {

    public record Request(
        Map<String, List<String>> headers,
        String body
    ) {
    }

    public record Response(
        Integer status,
        Map<String, List<String>> headers,
        String body
    ) {
    }
}
