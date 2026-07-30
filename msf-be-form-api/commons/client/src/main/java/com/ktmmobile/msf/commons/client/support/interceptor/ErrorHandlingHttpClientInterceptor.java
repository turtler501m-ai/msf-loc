package com.ktmmobile.msf.commons.client.support.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.client.application.port.out.CommonHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.support.exception.ClientException;

/**
 * HTTP 호출 기술 예외의 공통 ClientException 변환
 */
@RequiredArgsConstructor
public class ErrorHandlingHttpClientInterceptor implements CommonHttpClientInterceptor {

    private final ObjectMapper objectMapper;
    private final String message;
    private final String groupName;

    public ErrorHandlingHttpClientInterceptor(ObjectMapper objectMapper, String message) {
        this(objectMapper, message, null);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        try {
            ClientHttpResponse response = execution.execute(request, body);
            int status = response.getStatusCode().value();
            // 4xx/5xx는 공통 예외로 변환해서 상위 계층에서 동일하게 처리
            if (status >= 400) {
                throw new ClientException(buildErrorMessage(request, response, status));
            }
            return response;
        } catch (ClientException e) {
            throw e;
        } catch (IOException | RuntimeException e) {
            throw new ClientException(buildExecutionFailureMessage(request, e));
        }
    }

    private String buildErrorMessage(HttpRequest request, ClientHttpResponse response, int status) throws IOException {
        String responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        String responseMessage = extractResponseMessage(responseBody);
        if (StringUtils.hasText(responseMessage)) {
            return buildMessagePrefix(request) + ": " + responseMessage;
        }
        return buildMessagePrefix(request) + " (status=" + status + ")";
    }

    private String buildMessagePrefix(HttpRequest request) {
        StringBuilder builder = new StringBuilder(message);
        if (StringUtils.hasText(groupName)) {
            builder.append(" (groupName=").append(groupName).append(")");
        }
        builder.append(" (url=").append(request.getURI()).append(")");
        return builder.toString();
    }

    private String buildExecutionFailureMessage(HttpRequest request, Exception e) {
        StringBuilder builder = new StringBuilder(buildMessagePrefix(request));
        builder.append(": ").append(e.getClass().getSimpleName());
        if (StringUtils.hasText(e.getMessage())) {
            builder.append(": ").append(e.getMessage());
        }
        return builder.toString();
    }

    private String extractResponseMessage(String responseBody) {
        if (!StringUtils.hasText(responseBody)) {
            return null;
        }

        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode messageNode = root.get("message");
            if (messageNode != null && !messageNode.isNull()) {
                return messageNode.asString();
            }
            return responseBody;
        } catch (Exception _) {
            return responseBody;
        }
    }
}
