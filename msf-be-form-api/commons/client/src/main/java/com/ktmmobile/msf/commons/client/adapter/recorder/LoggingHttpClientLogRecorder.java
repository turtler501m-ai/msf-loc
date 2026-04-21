package com.ktmmobile.msf.commons.client.adapter.recorder;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.client.application.port.out.HttpClientLogRecorder;
import com.ktmmobile.msf.commons.client.domain.dto.HttpClientLog;

@Slf4j
@Component
public class LoggingHttpClientLogRecorder implements HttpClientLogRecorder {

    @Override
    public void recordLog(HttpClientLog httpClientLog) {
        String formattedMessage = format(httpClientLog);

        if (httpClientLog.success()) {
            log.info(formattedMessage);
            return;
        }

        log.error(formattedMessage);
    }

    private String format(HttpClientLog httpClientLog) {
        StringJoiner lines = new StringJoiner(System.lineSeparator());

        lines.add("HTTP Client Log");
        lines.add("  Summary");
        lines.add("    group      : " + nullSafe(httpClientLog.groupName()));
        lines.add("    method     : " + nullSafe(httpClientLog.method()));
        lines.add("    uri        : " + nullSafe(httpClientLog.uri()));
        lines.add("    durationMs : " + httpClientLog.durationMs());
        lines.add("    success    : " + httpClientLog.success());

        if (httpClientLog.errorMessage() != null && !httpClientLog.errorMessage().isBlank()) {
            lines.add("    error      : " + httpClientLog.errorMessage());
        }

        lines.add("  Request");
        lines.add("    headers    : " + formatHeaders(httpClientLog.request().headers()));
        lines.add("    body       : " + formatBody(httpClientLog.request().body()));

        if (httpClientLog.response() != null) {
            lines.add("  Response");
            lines.add("    status     : " + httpClientLog.response().status());
            lines.add("    headers    : " + formatHeaders(httpClientLog.response().headers()));
            lines.add("    body       : " + formatBody(httpClientLog.response().body()));
        } else {
            lines.add("  Response");
            lines.add("    status     : null");
            lines.add("    headers    : {}");
            lines.add("    body       : <empty>");
        }

        return lines.toString();
    }

    private String formatHeaders(Map<String, List<String>> headers) {
        if (headers == null || headers.isEmpty()) {
            return "{}";
        }
        return headers.toString();
    }

    private String formatBody(String body) {
        if (body == null || body.isBlank()) {
            return "<empty>";
        }

        if (!body.contains(System.lineSeparator())) {
            return body;
        }

        String indentedBody = body.replace(System.lineSeparator(), System.lineSeparator() + "                 ");
        return System.lineSeparator() + "                 " + indentedBody;
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }
}
