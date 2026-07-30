package com.ktmmobile.msf.commons.websecurity.web.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.logging.http.HttpExchangeLogSanitizer;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogProperties;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogRules;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.response.FilterExceptionResponseUtils;

/**
 * 이 필터를 높은 우선순위로 등록하되, TraceId 등의 Observation 로그를 남길 수 있도록
 * {@code ServerHttpObservationFilter}보다 나중 순서로 지정해야 함.
 * @see org.springframework.web.filter.ServerHttpObservationFilter
 * @see org.springframework.boot.actuate.autoconfigure.observation.web.servlet.WebMvcObservationAutoConfiguration
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 10)  // ServerHttpObservationFilter보다 나중 순서로 지정
@Component
public class RequestLogFilter extends OncePerRequestFilter {

    static final String MDC_CLIENT_ID = "clientId";
    static final String CLIENT_ID_REQUEST_ATTRIBUTE = RequestLogFilter.class.getName() + ".clientId";

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final RequestLogProperties requestLogProperties;
    private final BodyCacheProperties bodyCacheProperties;
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final HttpExchangeLogSanitizer logSanitizer;

    public RequestLogFilter(
        AuthenticationEntryPoint authenticationEntryPoint,
        RequestLogProperties requestLogProperties,
        BodyCacheProperties bodyCacheProperties,
        HttpLogProperties httpLogProperties,
        ObjectMapper objectMapper
    ) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.requestLogProperties = requestLogProperties;
        this.bodyCacheProperties = bodyCacheProperties;
        HttpLogRules httpLogRules = httpLogProperties.defaultRules().merge(requestLogProperties.httpLogRules());
        this.logSanitizer = new HttpExchangeLogSanitizer(
            objectMapper,
            httpLogRules.headerNames(),
            httpLogRules.bodyMaskedFields(),
            httpLogRules.bodyTruncatedFields()
        );
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest requestToUse = wrapRequestIfNecessary(request);
        HttpServletResponse responseToUse = wrapResponseIfNecessary(response);
        RequestUtils.setCurrentRequest(request, requestToUse);
        try {
            putClientIdToMdc(requestToUse);
            logRequestBasicInfo(requestToUse);
            logRequestQueryInfo(requestToUse);
            logRequestHeaderInfo(requestToUse);

            filterChain.doFilter(requestToUse, responseToUse);
        } catch (AuthenticationException e) {
            authenticationEntryPoint.commence(requestToUse, responseToUse, e);
        } catch (Exception e) {
            FilterExceptionResponseUtils.handle(responseToUse, e);
        } finally {
            try {
                putClientIdToMdc(requestToUse);
                long endTime = System.currentTimeMillis();
                long responseTime = endTime - startTime;
                logRequestBodyInfo(requestToUse);
                logResponseHeaderInfo(responseToUse);
                flushResponseBody(responseToUse);
                logResponseBodyInfo(responseToUse);
                logResponseBasicInfo(requestToUse, responseToUse, responseTime);
            } finally {
                clearMdc();
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return requestLogProperties.excludeUrlPatterns().stream()
            .filter(StringUtils::hasText)
            .anyMatch(pattern -> pathMatcher.match(pattern, requestUri));
    }

    static void putClientIdToMdc(HttpServletRequest request) {
        putClientIdToMdc(request, resolveClientId(request));
    }

    static void putClientIdToMdc(HttpServletRequest request, String clientId) {
        String resolvedClientId = StringUtils.hasText(clientId) ? clientId : RequestUtils.getClientIp(request);
        request.setAttribute(CLIENT_ID_REQUEST_ATTRIBUTE, resolvedClientId);
        MDC.put(MDC_CLIENT_ID, resolvedClientId);
    }

    static String resolveClientId(HttpServletRequest request) {
        String authenticatedClientId = resolveAuthenticatedClientId();
        if (StringUtils.hasText(authenticatedClientId)) {
            return authenticatedClientId;
        }
        Object clientId = request.getAttribute(CLIENT_ID_REQUEST_ATTRIBUTE);
        if (clientId instanceof String value && StringUtils.hasText(value)) {
            return value;
        }
        return RequestUtils.getClientIp(request);
    }

    static String resolveAuthenticatedClientId() {
        try {
            MsfUser user = AuthenticationUtils.getUser();
            if (user != null && StringUtils.hasText(user.getUserId())) {
                return user.getUserId();
            }
        } catch (RuntimeException e) {
            log.trace("Authenticated client id is unavailable. Falling back to client IP.", e);
        }
        return null;
    }

    private static void logRequestBasicInfo(HttpServletRequest request) {
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        log.info("--> [{}][{}] Request", method, requestUri);
    }

    private static void logRequestQueryInfo(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (!StringUtils.hasText(queryString)) {
            return;
        }

        log.info("--> [Query] {}", queryString);
    }

    private void logRequestHeaderInfo(HttpServletRequest request) {
        if (Boolean.FALSE.equals(requestLogProperties.enabled().requestHeaders())) {
            return;
        }
        Map<String, List<String>> headerNameValueMap = logSanitizer.copyHeaders(copyHeaders(request));
        log.info("--> [Headers] {}", headerNameValueMap);
    }

    private void logResponseHeaderInfo(HttpServletResponse response) {
        if (Boolean.FALSE.equals(requestLogProperties.enabled().responseHeaders())) {
            return;
        }

        Map<String, List<String>> headerNameValueMap = logSanitizer.copyHeaders(copyHeaders(response));
        log.info("<-- [Headers] {}", headerNameValueMap);
    }

    private void logRequestBodyInfo(HttpServletRequest request) {
        if (!requestLogProperties.enabled().requestBody() || !(request instanceof LimitedContentCachingRequestWrapper wrapper)) {
            return;
        }

        byte[] body = wrapper.getContentAsByteArray();
        if (body.length == 0) {
            return;
        }

        String bodyText = toLimitedBodyText(copyHeaders(request), body, wrapper.isOverflowed());
        log.info("--> [Body] {}", bodyText);
    }

    private void logResponseBodyInfo(HttpServletResponse response) {
        if (!requestLogProperties.enabled().responseBody() || !(response instanceof LimitedContentCachingResponseWrapper wrapper)) {
            return;
        }

        byte[] body = wrapper.getContentAsByteArray();
        if (body.length == 0) {
            return;
        }

        Map<String, List<String>> headers = copyHeaders(response);
        if (!isAllowedResponseBodyContentType(headers)) {
            return;
        }

        String bodyText = toLimitedBodyText(headers, body, wrapper.isOverflowed());
        log.info("<-- [Body] {} (size={} bytes)", bodyText, body.length);
    }

    private static void logResponseBasicInfo(HttpServletRequest request, HttpServletResponse response, long responseTime) {
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        HttpStatus httpStatus = HttpStatus.resolve(response.getStatus());
        log.info("<-- [{}][{}] Response {}ms ({})", method, requestUri, responseTime, httpStatus);
    }

    private HttpServletRequest wrapRequestIfNecessary(HttpServletRequest request) {
        if (!requestLogProperties.enabled().requestBody() || request instanceof ContentCachingRequestWrapper) {
            return request;
        }
        return new LimitedContentCachingRequestWrapper(request, requestBodyCacheLimit());
    }

    private int requestBodyCacheLimit() {
        return bodyCacheProperties.request().limit();
    }

    private HttpServletResponse wrapResponseIfNecessary(HttpServletResponse response) {
        if (!requestLogProperties.enabled().responseBody() || response instanceof LimitedContentCachingResponseWrapper) {
            return response;
        }
        return new LimitedContentCachingResponseWrapper(response, responseBodyCacheLimit());
    }

    private static Map<String, List<String>> copyHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = new LinkedHashMap<>();
        Collections.list(request.getHeaderNames())
            .forEach(name -> headers.put(name, Collections.list(request.getHeaders(name))));
        return headers;
    }

    private static Map<String, List<String>> copyHeaders(HttpServletResponse response) {
        Map<String, List<String>> headers = new LinkedHashMap<>();
        response.getHeaderNames()
            .forEach(name -> headers.put(name, List.copyOf(response.getHeaders(name))));
        if (StringUtils.hasText(response.getContentType())) {
            headers.putIfAbsent("Content-Type", List.of(response.getContentType()));
        }
        return headers;
    }

    private int responseBodyCacheLimit() {
        return bodyCacheProperties.response().limit();
    }

    private static void flushResponseBody(HttpServletResponse response) throws IOException {
        if (response instanceof LimitedContentCachingResponseWrapper wrapper) {
            wrapper.flushCachedContent();
        }
    }

    private String toLimitedBodyText(Map<String, List<String>> headers, byte[] body, boolean alreadyTruncated) {
        String bodyText = logSanitizer.toText(headers, body);
        return limitBodyText(bodyText, alreadyTruncated);
    }

    private String limitBodyText(String bodyText, boolean alreadyTruncated) {
        int maxLength = requestLogProperties.maxBodyLength();
        if (bodyText.length() <= maxLength && !alreadyTruncated) {
            return bodyText;
        }

        String limitedBodyText = bodyText.substring(0, Math.min(bodyText.length(), maxLength));
        return limitedBodyText + "...<truncated: max-body-length=" + maxLength + ">";
    }

    private boolean isAllowedResponseBodyContentType(Map<String, List<String>> headers) {
        return headers.entrySet().stream()
            .filter(entry -> "content-type".equalsIgnoreCase(entry.getKey()))
            .map(Map.Entry::getValue)
            .flatMap(List::stream)
            .map(RequestLogFilter::normalizeContentType)
            .anyMatch(contentType -> requestLogProperties.responseBodyContentTypes().stream()
                .map(RequestLogFilter::normalizeContentType)
                .anyMatch(contentType::equals));
    }

    private static String normalizeContentType(String value) {
        if (!StringUtils.hasText(value)) {
            return "";
        }
        int parameterIndex = value.indexOf(';');
        String mediaType = parameterIndex >= 0 ? value.substring(0, parameterIndex) : value;
        return mediaType.trim().toLowerCase();
    }

    private static void clearMdc() {
        MDC.clear();
    }


    private static class LimitedContentCachingRequestWrapper extends ContentCachingRequestWrapper {

        private boolean overflowed;

        LimitedContentCachingRequestWrapper(HttpServletRequest request, int cacheLimit) {
            super(request, cacheLimit);
        }

        @Override
        protected void handleContentOverflow(int contentCacheLimit) {
            this.overflowed = true;
        }

        boolean isOverflowed() {
            return overflowed;
        }
    }
}
