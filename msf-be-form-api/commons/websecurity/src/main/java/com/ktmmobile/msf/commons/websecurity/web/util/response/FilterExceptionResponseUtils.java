package com.ktmmobile.msf.commons.websecurity.web.util.response;

import jakarta.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.utils.log.LogUtils;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponseType;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterExceptionResponseUtils {

    private static final CommonResponseType DEFAULT_RESPONSE_TYPE = CommonResponseType.UNKNOWN_ERROR;

    public static void handle(HttpServletResponse response, Exception e) {
        handle(response, e, DEFAULT_RESPONSE_TYPE);
    }

    public static void handle(HttpServletResponse response, Exception e, CommonResponseType defaultResponseType) {
        handle(response, e, defaultResponseType, null);
    }

    public static void handle(HttpServletResponse response, Exception e, CommonResponseType defaultResponseType, String responseMessage) {
        CommonResponseType responseType = resolveResponseType(response, defaultResponseType);
        logException(e, responseType);
        CommonResponse<?> body = CommonResponse.of(responseType.code(), StringUtils.hasText(responseMessage) ? responseMessage : responseType.message());
        ResponseUtils.setResponse(response, responseType.httpStatus(), body);
    }

    private static void logException(Exception e, CommonResponseType responseType) {
        if (e instanceof AuthenticationException || e instanceof AccessDeniedException) {
            LogUtils.warnSimpleException(log, "Security filter exception. status=" + responseType.httpStatus().value(), e);
            return;
        }
        log.error(e.getMessage(), e);
    }

    private static CommonResponseType resolveResponseType(HttpServletResponse response, CommonResponseType defaultResponseType) {
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        CommonResponseType responseType = CommonResponseType.valueOfHttpStatus(httpStatus);
        if (responseType == null || responseType.httpStatus() == HttpStatus.OK) {
            return defaultResponseType;
        }
        return responseType;
    }
}
