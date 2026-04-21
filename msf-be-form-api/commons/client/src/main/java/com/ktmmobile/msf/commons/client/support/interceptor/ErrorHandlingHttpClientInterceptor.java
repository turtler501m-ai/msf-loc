package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.client.application.port.out.CommonHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.support.exception.ClientException;

/**
 * HTTP 호출 중 발생한 기술 예외를 공통 ClientException으로 변환하는 재사용 interceptor다.
 */
@RequiredArgsConstructor
public class ErrorHandlingHttpClientInterceptor implements CommonHttpClientInterceptor {

    private final ObjectMapper objectMapper;
    private final String message;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        try {
            ClientHttpResponse response = execution.execute(request, body);
            int status = response.getStatusCode().value();
            if (status >= 400) {
                throw new ClientException(buildErrorMessage(response, status));
            }
            return response;
        } catch (ClientException e) {
            throw e;
        } catch (IOException | RuntimeException e) {
            throw new ClientException(message, e);
        }
    }

    private String buildErrorMessage(ClientHttpResponse response, int status) throws IOException {
        String responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        String responseMessage = extractResponseMessage(responseBody);
        if (StringUtils.hasText(responseMessage)) {
            return message + ": " + responseMessage;
        }
        return message + " (status=" + status + ")";
    }

    private String extractResponseMessage(String responseBody) {
        if (!StringUtils.hasText(responseBody)) {
            return null;
        }

        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode messageNode = root.get("message");
            if (messageNode != null && !messageNode.isNull()) {
                return messageNode.asText();
            }
            return responseBody;
        } catch (Exception _) {
            return responseBody;
        }
    }
}
