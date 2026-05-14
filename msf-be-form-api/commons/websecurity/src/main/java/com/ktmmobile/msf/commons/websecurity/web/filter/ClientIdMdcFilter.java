package com.ktmmobile.msf.commons.websecurity.web.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Spring Security 인증이 끝난 뒤의 로그에서는 clientId가 인증 사용자 ID를 바라보도록 갱신한다.
 */
public class ClientIdMdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String previousClientId = MDC.get(RequestLogFilter.MDC_CLIENT_ID);
        boolean managedByRequestLogFilter = request.getAttribute(RequestLogFilter.CLIENT_ID_REQUEST_ATTRIBUTE) != null;

        try {
            putAuthenticatedClientId(request);
            filterChain.doFilter(request, response);
        } finally {
            if (!managedByRequestLogFilter) {
                restorePreviousClientId(previousClientId);
            }
        }
    }

    private static void putAuthenticatedClientId(HttpServletRequest request) {
        String clientId = RequestLogFilter.resolveAuthenticatedClientId();
        if (StringUtils.hasText(clientId)) {
            RequestLogFilter.putClientIdToMdc(request, clientId);
        }
    }

    private static void restorePreviousClientId(String previousClientId) {
        if (StringUtils.hasText(previousClientId)) {
            MDC.put(RequestLogFilter.MDC_CLIENT_ID, previousClientId);
            return;
        }
        MDC.remove(RequestLogFilter.MDC_CLIENT_ID);
    }
}
