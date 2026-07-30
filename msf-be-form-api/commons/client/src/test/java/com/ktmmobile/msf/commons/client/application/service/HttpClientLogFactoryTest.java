package com.ktmmobile.msf.commons.client.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.client.domain.dto.HttpClientLog;
import com.ktmmobile.msf.commons.client.support.properties.HttpClientRecordingProperties;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogMatchRule;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogProperties;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogRules;

class HttpClientLogFactoryTest {

    private final HttpClientLogFactory factory = new HttpClientLogFactory(
        new ObjectMapper(),
        new HttpLogProperties(new HttpLogRules(
            HttpLogMatchRule.empty(),
            rule(List.of("password"), 0),
            rule(List.of("content[3]"), 0)
        )),
        new HttpClientRecordingProperties(true, 1000, null, null, null)
    );

    @Test
    void keepsMaskedFullBodyForDatabaseAndTruncatedBodyForConsole() {
        MockClientHttpRequest request = new MockClientHttpRequest(HttpMethod.POST, URI.create("https://example.com/documents"));
        request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] responseBody = "{\"content\":\"response content full\",\"password\":\"response-password\"}".getBytes(StandardCharsets.UTF_8);
        MockClientHttpResponse response = new MockClientHttpResponse(responseBody, 200);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        HttpClientLog log = factory.createSuccessLog(
            "test",
            request,
            "{\"content\":\"request content full\",\"password\":\"request-password\"}".getBytes(StandardCharsets.UTF_8),
            response,
            responseBody,
            10,
            Instant.now()
        );

        assertThat(log.request().sanitizedBody()).doesNotContain("request content full", "request-password");
        assertThat(log.request().retainedBody()).contains("request content full").doesNotContain("request-password");
        assertThat(log.response().sanitizedBody()).doesNotContain("response content full", "response-password");
        assertThat(log.response().retainedBody()).contains("response content full").doesNotContain("response-password");
    }

    private static HttpLogMatchRule rule(List<String> include, int defaultTruncatedSize) {
        return new HttpLogMatchRule(include, Map.of(), List.of(), Map.of(), defaultTruncatedSize);
    }
}
