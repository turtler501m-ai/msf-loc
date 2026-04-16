package com.ktmmobile.msf.commons.websecurity.web.util.response;

import jakarta.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

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
        log.error(e.getMessage(), e);
        CommonResponseType responseType = resolveResponseType(response, defaultResponseType);
        CommonResponse<?> body = CommonResponse.of(responseType.code(), responseType.message());
        ResponseUtils.setResponse(response, responseType.httpStatus(), body);
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
