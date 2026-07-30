package com.ktmmobile.msf.commons.websecurity.web.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTraceExcludeUrlMatcher;
import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTraceRequestParameterExtractor;
import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTraceRecorder;
import com.ktmmobile.msf.commons.websecurity.web.util.ContentTypeUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;

/**
 * API 요청 완료 후 응답 상태와 실패 메시지를 수집해 접속 이력으로 기록하는 필터
 */
@Slf4j
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE - 10)
@Component
public class AccessTraceFilter extends OncePerRequestFilter {

    private static final String RESPONSE_MESSAGE_FIELD = "message";

    private final AccessTraceRecorder accessTraceRecorder;
    private final ObjectProvider<AccessTraceExcludeUrlMatcher> excludeUrlMatcherProvider;
    private final BodyCacheProperties bodyCacheProperties;
    private final ObjectMapper objectMapper;

    /**
     * 응답 본문 캐싱 후 후처리에서 메시지 추출 및 원래 응답으로 복사
     */
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        HttpServletRequest requestToUse = wrapRequestIfNecessary(request);
        LimitedContentCachingResponseWrapper cachingResponse = wrapResponseIfNecessary(response);
        RequestUtils.setCurrentRequest(request, requestToUse);
        try {
            filterChain.doFilter(requestToUse, cachingResponse);
        } finally {
            // 이력 저장 실패가 실제 API 응답 전송을 방해하지 않도록 finally에서 처리
            cachingResponse.flushCachedContent();
            recordAccessTrace(requestToUse, cachingResponse);
        }
    }

    /**
     * API 경로가 아닌 요청의 접속 이력 대상 제외
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        AccessTraceExcludeUrlMatcher excludeUrlMatcher = excludeUrlMatcherProvider.getIfAvailable();
        return excludeUrlMatcher == null
            || !excludeUrlMatcher.supports(request.getRequestURI())
            || excludeUrlMatcher.matches(request.getMethod(), request.getRequestURI());
    }

    /**
     * 필터에서 수집한 요청/응답 정보의 recorder 전달
     */
    private void recordAccessTrace(HttpServletRequest request, LimitedContentCachingResponseWrapper response) {
        try {
            accessTraceRecorder.recordTrace(
                request,
                statusText(response),
                responseMessage(response)
            );
        } catch (Exception e) {
            // 이력 저장은 부가 기능이므로 실패해도 본 요청 흐름 유지
            log.warn("Access trace recording failed. uri={}", request.getRequestURI(), e);
        }
    }

    /**
     * HTTP 상태 코드의 "200 OK" 형식 문자열 변환
     */
    private String statusText(HttpServletResponse response) {
        HttpStatus status = HttpStatus.resolve(response.getStatus());
        if (status == null) {
            return String.valueOf(response.getStatus());
        }
        return response.getStatus() + " " + status.getReasonPhrase();
    }

    /**
     * 실패 응답 JSON에서 message 필드만 추출
     */
    private String responseMessage(LimitedContentCachingResponseWrapper response) {
        if (response.getStatus() == HttpStatus.OK.value()) {
            return "";
        }
        byte[] body = response.getContentAsByteArray();
        if (body.length == 0) {
            return "";
        }
        try {
            Charset charset = responseCharset(response);
            JsonNode root = objectMapper.readTree(new String(body, charset));
            JsonNode message = root.path(RESPONSE_MESSAGE_FIELD);
            // Jackson 버전에서 deprecated 된 textual/asText 계열 대신 string 전용 API 사용
            return message.isString() ? message.asString() : "";
        } catch (RuntimeException e) {
            log.debug("Access trace response message parsing failed. status={}", response.getStatus(), e);
            return "";
        }
    }

    /**
     * 응답 Content-Type의 charset 확인
     */
    private Charset responseCharset(LimitedContentCachingResponseWrapper response) {
        return ContentTypeUtils.contentCharset(response.getContentType());
    }

    /**
     * 요청 본문 캐싱 wrapper 적용
     */
    private HttpServletRequest wrapRequestIfNecessary(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return request;
        }
        return new LimitedContentCachingRequestWrapper(request, requestBodyCacheLimit());
    }

    /**
     * 요청 본문 캐시 제한 조회
     */
    private int requestBodyCacheLimit() {
        return bodyCacheProperties.request().limit();
    }

    private LimitedContentCachingResponseWrapper wrapResponseIfNecessary(HttpServletResponse response) {
        if (response instanceof LimitedContentCachingResponseWrapper wrapper) {
            return wrapper;
        }
        return new LimitedContentCachingResponseWrapper(response, responseBodyCacheLimit());
    }

    private int responseBodyCacheLimit() {
        return bodyCacheProperties.response().limit();
    }

    /**
     * 요청 본문 캐싱 초과 여부 보관 wrapper
     */
    private static class LimitedContentCachingRequestWrapper extends ContentCachingRequestWrapper
        implements AccessTraceRequestParameterExtractor.LimitedRequestBodyCache {

        private boolean overflowed;

        LimitedContentCachingRequestWrapper(HttpServletRequest request, int cacheLimit) {
            super(request, cacheLimit);
        }

        @Override
        protected void handleContentOverflow(int contentCacheLimit) {
            this.overflowed = true;
        }

        public boolean isOverflowed() {
            return overflowed;
        }
    }
}
