package com.ktmmobile.msf.commons.client.adapter.repository.mybatis.smartform;

import java.sql.Timestamp;

/**
 * HTTP Client 호출 이력 DB INSERT row
 */
public record HttpClientLogRow(
    String traceId,
    String groupName,
    String targetSystem,
    String directionCode,
    String requestMethodCode,
    String requestUrl,
    String requestPath,
    String requestQuery,
    String requestHeaders,
    String requestBody,
    Integer responseStatus,
    String responseHeaders,
    String responseBody,
    String successYn,
    String errorMessage,
    Timestamp startedAt,
    Timestamp endedAt,
    Integer durationMs,
    String clientIp,
    String clientHost,
    String environmentCode,
    String applicationName,
    String parentScanId
) {
}
