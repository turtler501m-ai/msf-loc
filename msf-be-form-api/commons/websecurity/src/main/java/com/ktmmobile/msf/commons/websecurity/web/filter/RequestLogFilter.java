package com.ktmmobile.msf.commons.websecurity.web.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
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

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
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
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 10)  // ServerHttpObservationFilter보다 나중 순서로 지정
@Component
public class RequestLogFilter extends OncePerRequestFilter {

    private static final String MDC_CLIENT_ID = "clientId";
    private static final List<String> LOGGING_HEADER_NAMES = List.of(
        // HttpHeaders.ACCEPT,
        // HttpHeaders.USER_AGENT
    );

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final RequestLogProperties requestLogProperties;
    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            putClientIdToMdc(request);
            logRequestBasicInfo(request);
            logRequestHeaderInfo(request);

            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            authenticationEntryPoint.commence(request, response, e);
        } catch (Exception e) {
            FilterExceptionResponseUtils.handle(response, e);
        } finally {
            putClientIdToMdc(request);
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            logResponseBasicInfo(request, response, responseTime);
            clearMdc();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return requestLogProperties.excludeUrlPatterns().stream()
            .filter(StringUtils::hasText)
            .anyMatch(pattern -> pathMatcher.match(pattern, requestUri));
    }

    private static void putClientIdToMdc(HttpServletRequest request) {
        MDC.put(MDC_CLIENT_ID, resolveClientId(request));
    }

    private static String resolveClientId(HttpServletRequest request) {
        try {
            MsfUser user = AuthenticationUtils.getUser();
            if (user != null && StringUtils.hasText(user.getUserId())) {
                return user.getUserId();
            }
        } catch (RuntimeException _) {
            // 사용자 인증이 되지 않은 경우, Client IP로 Fallback
        }
        return RequestUtils.getClientIp(request);
    }

    private static void logRequestBasicInfo(HttpServletRequest request) {
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        log.info("[{}][{}][{}] Request", method, requestUri, queryString);
    }

    private static void logRequestHeaderInfo(HttpServletRequest request) {
        if (LOGGING_HEADER_NAMES.isEmpty()) {
            return;
        }
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        Map<String, String> headerNameValueMap = LOGGING_HEADER_NAMES.stream()
            .collect(LinkedHashMap::new, (map, name) -> map.put(name, request.getHeader(name)), LinkedHashMap::putAll);
        log.info("[{}][{}] [Request Headers] {}", method, requestUri, headerNameValueMap);
    }

    private static void logResponseBasicInfo(HttpServletRequest request, HttpServletResponse response, long responseTime) {
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        HttpStatus httpStatus = HttpStatus.resolve(response.getStatus());
        log.info("[{}][{}] Response {}ms ({})", method, requestUri, responseTime, httpStatus);
    }

    private static void clearMdc() {
        MDC.clear();
    }
}
