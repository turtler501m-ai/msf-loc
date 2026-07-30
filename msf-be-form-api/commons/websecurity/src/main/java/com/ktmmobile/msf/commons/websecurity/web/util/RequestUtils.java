package com.ktmmobile.msf.commons.websecurity.web.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {

    private static final String CURRENT_REQUEST_ATTRIBUTE = RequestUtils.class.getName() + ".currentRequest";

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    public static final String LOCALHOST_IPV4 = "127.0.0.1";
    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    public static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    public static String getHeader(String key) {
        return getRequest().getHeader(key);
    }

    public static String getRequestUri() {
        return getRequest().getRequestURI();
    }

    public static String getRequestPath(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (StringUtils.hasText(contextPath) && requestUri.startsWith(contextPath)) {
            String path = requestUri.substring(contextPath.length());
            return StringUtils.hasText(path) ? path : "/";
        }
        return requestUri;
    }

    public static String getClientIp() {
        return getClientIp(getRequest());
    }

    public static String getClientIp(HttpServletRequest request) {
        return normalizeIp(request.getRemoteAddr());
    }

    public static String getServerIp() throws UnknownHostException {
        return normalizeIp(InetAddress.getLocalHost().getHostAddress());
    }

    private static String normalizeIp(String ipAddress) {
        if (LOCALHOST_IPV6.equals(ipAddress)) {
            return LOCALHOST_IPV4;
        }
        return ipAddress;
    }

    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletRequest getRequestIfNoRequest() {
        ServletRequestAttributes requestAttributes = getServletRequestAttributesIfNoRequest();
        if (requestAttributes == null) {
            return null;
        }
        return currentRequest(requestAttributes.getRequest());
    }

    public static void setCurrentRequest(HttpServletRequest request, HttpServletRequest currentRequest) {
        request.setAttribute(CURRENT_REQUEST_ATTRIBUTE, currentRequest);
    }

    private static HttpServletRequest currentRequest(HttpServletRequest request) {
        Object currentRequest = request.getAttribute(CURRENT_REQUEST_ATTRIBUTE);
        if (currentRequest instanceof HttpServletRequest httpServletRequest) {
            return httpServletRequest;
        }
        return request;
    }

    /**
     * Request가 없는 상황에서 IllegalStateException return
     * Test Code, Scheduler 등에서 호출 시 예외 발생
     */
    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

    /**
     * Request가 없는 상황(e.g., scheduler)에서 null로 리턴
     */
    public static ServletRequestAttributes getServletRequestAttributesIfNoRequest() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
