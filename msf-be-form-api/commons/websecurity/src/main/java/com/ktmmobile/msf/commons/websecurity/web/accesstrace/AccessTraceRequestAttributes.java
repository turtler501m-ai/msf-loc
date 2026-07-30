package com.ktmmobile.msf.commons.websecurity.web.accesstrace;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;

/**
 * API 요청 이력 기록에 사용할 요청 단위 속성 관리
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccessTraceRequestAttributes {

    private static final String USER_ID_ATTRIBUTE = AccessTraceRequestAttributes.class.getName() + ".userId";

    /**
     * 현재 요청에 이력 기록용 사용자 ID 저장
     */
    public static void setUserId(String userId) {
        if (!StringUtils.hasText(userId)) {
            return;
        }
        HttpServletRequest request = RequestUtils.getRequestIfNoRequest();
        if (request != null) {
            request.setAttribute(USER_ID_ATTRIBUTE, userId);
        }
    }

    /**
     * 요청에 저장된 이력 기록용 사용자 ID 조회
     */
    public static String getUserId(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        Object userId = request.getAttribute(USER_ID_ATTRIBUTE);
        return userId instanceof String value && StringUtils.hasText(value) ? value : "";
    }
}
