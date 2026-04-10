package com.ktmmobile.msf.external.websecurity.web.util;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {

    public static final String LOCALHOST_IPV4 = "127.0.0.1";
    public static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    public static String getHeader(String key) {
        return getRequest().getHeader(key);
    }

    public static String getRequestUri() {
        return getRequest().getRequestURI();
    }

    public static String getRemoteAddr() {
        String ipAddress = getRequest().getRemoteAddr();
        if (LOCALHOST_IPV6.equals(ipAddress)) {
            return LOCALHOST_IPV4;
        }
        return ipAddress;
    }

    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
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
