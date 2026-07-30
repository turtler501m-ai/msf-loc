package com.ktmmobile.msf.commons.client.adapter.recorder;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.client.adapter.repository.mybatis.smartform.HttpClientLogRepository;
import com.ktmmobile.msf.commons.client.adapter.repository.mybatis.smartform.HttpClientLogRow;
import com.ktmmobile.msf.commons.client.application.port.out.HttpClientLogRecorder;
import com.ktmmobile.msf.commons.client.domain.dto.HttpClientLog;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;

/**
 * HTTP Client 기록의 SmartForm DB 저장 구현
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MyBatisHttpClientLogRecorder implements HttpClientLogRecorder {

    private static final String DIRECTION_OUTBOUND = "OUTBOUND";
    private static final String SUCCESS_Y = "Y";
    private static final String SUCCESS_N = "N";
    private static final String OMITTED = "<omitted>";
    private static final String RESPONSE_BODY_OMITTED = "<response body omitted>";

    private final HttpClientLogRepository repository;
    private final Environment environment;

    /**
     * HTTP Client 호출 기록 저장
     */
    @Override
    public void recordLog(HttpClientLog httpClientLog) {
        try {
            repository.save(toRow(httpClientLog));
        } catch (RuntimeException e) {
            log.warn("HTTP client MyBatis log recording failed. groupName={}, uri={}", httpClientLog.groupName(), httpClientLog.uri(), e);
        }
    }

    private HttpClientLogRow toRow(HttpClientLog httpClientLog) {
        String appName = applicationName();
        URI uri = uri(httpClientLog.uri());
        String targetSystem = host(uri);
        String clientHost = localHostName();
        String clientIp = sourceIp(uri);
        Instant startedAt = startedAt(httpClientLog);
        int durationMs = durationMs(httpClientLog);

        return new HttpClientLogRow(
            fit(httpClientLog.traceId(), 64),
            fitOrEmpty(httpClientLog.groupName(), 100),
            fitOrEmpty(targetSystem, 100),
            DIRECTION_OUTBOUND,
            fitOrEmpty(httpClientLog.method(), 10),
            fitOrEmpty(requestUrl(httpClientLog.uri()), 500),
            textOrEmpty(httpClientLog.path()),
            textOrEmpty(httpClientLog.query()),
            headersToText(httpClientLog.request().headers()),
            textOrEmpty(httpClientLog.request().retainedBody()),
            responseStatus(httpClientLog),
            headersToText(responseHeaders(httpClientLog)),
            responseBody(httpClientLog),
            httpClientLog.success() ? SUCCESS_Y : SUCCESS_N,
            errorMessage(httpClientLog),
            Timestamp.from(startedAt),
            Timestamp.from(startedAt.plusMillis(durationMs)),
            durationMs,
            fit(clientIp, 45),
            fitOrEmpty(clientHost, 255),
            fitOrEmpty(activeProfiles(), 30),
            fitOrEmpty(appName, 100),
            parentScanId()
        );
    }

    private String parentScanId() {
        return BusinessContextHolder.getParentScanId().orElse(null);
    }

    private Integer responseStatus(HttpClientLog httpClientLog) {
        if (httpClientLog.response() == null) {
            return null;
        }
        return httpClientLog.response().status();
    }

    private Map<String, List<String>> responseHeaders(HttpClientLog httpClientLog) {
        if (httpClientLog.response() == null) {
            return Map.of();
        }
        return httpClientLog.response().headers();
    }

    private String responseBody(HttpClientLog httpClientLog) {
        if (httpClientLog.response() == null) {
            return "";
        }
        return retainedBodyText(httpClientLog.response().retainedBody());
    }

    private String errorMessage(HttpClientLog httpClientLog) {
        if (httpClientLog.success()) {
            return null;
        }
        // 응답을 받은 실패는 resp_body에만 남기고, 응답을 못 받은 예외 상황만 err_msg에 기록
        if (httpClientLog.response() != null) {
            return null;
        }
        return httpClientLog.errorMessage();
    }

    private String retainedBodyText(String body) {
        if (body == null) {
            return "";
        }
        if (RESPONSE_BODY_OMITTED.equals(body)) {
            return OMITTED;
        }
        return body;
    }

    private String headersToText(Map<String, List<String>> headers) {
        if (headers == null || headers.isEmpty()) {
            return "";
        }
        return headers.toString();
    }

    private String requestUrl(String uri) {
        if (uri == null || uri.isBlank()) {
            return "";
        }

        int queryIndex = uri.indexOf('?');
        if (queryIndex < 0) {
            return uri;
        }
        return uri.substring(0, queryIndex);
    }

    private URI uri(String uri) {
        if (uri == null || uri.isBlank()) {
            return null;
        }

        try {
            return URI.create(uri);
        } catch (IllegalArgumentException _) {
            return null;
        }
    }

    private String host(URI uri) {
        if (uri == null) {
            return "";
        }
        String host = uri.getHost();
        return host == null ? "" : host;
    }

    private String sourceIp(URI uri) {
        String host = host(uri);
        if (host.isBlank()) {
            return localHostAddress();
        }

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(new InetSocketAddress(host, port(uri)));
            InetAddress localAddress = socket.getLocalAddress();
            return localAddress == null ? localHostAddress() : localAddress.getHostAddress();
        } catch (Exception e) {
            return localHostAddress();
        }
    }

    private int port(URI uri) {
        int port = uri.getPort();
        if (port > 0) {
            return port;
        }
        String scheme = uri.getScheme();
        if ("https".equalsIgnoreCase(scheme)) {
            return 443;
        }
        return 80;
    }

    private String localHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception _) {
            return null;
        }
    }

    private String localHostName() {
        String hostName = environment.getProperty("HOSTNAME", "");
        if (!hostName.isBlank()) {
            return hostName;
        }
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception _) {
            return "";
        }
    }

    private Instant startedAt(HttpClientLog httpClientLog) {
        return httpClientLog.requestedAt() == null ? Instant.now() : httpClientLog.requestedAt();
    }

    private int durationMs(HttpClientLog httpClientLog) {
        Long durationMs = httpClientLog.durationMs();
        if (durationMs == null || durationMs < 0L) {
            return 0;
        }
        if (durationMs > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return durationMs.intValue();
    }

    private String activeProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            return "";
        }
        return String.join(",", activeProfiles);
    }

    private String applicationName() {
        return environment.getProperty("spring.application.name", "");
    }

    private String fit(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }

    private String fitOrEmpty(String value, int maxLength) {
        return fit(textOrEmpty(value), maxLength);
    }

    private String textOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
