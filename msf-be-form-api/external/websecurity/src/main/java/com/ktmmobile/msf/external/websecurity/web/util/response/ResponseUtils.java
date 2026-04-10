package com.ktmmobile.msf.external.websecurity.web.util.response;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.StringJoiner;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.exception.CommonException;
import com.ktmmobile.msf.commons.common.exception.CustomErrorCode;
import com.ktmmobile.msf.external.websecurity.web.dto.response.BindErrorResponse;
import com.ktmmobile.msf.external.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.external.websecurity.web.dto.response.CommonResponseType;
import com.ktmmobile.msf.external.websecurity.web.dto.response.CreatedResponse;
import com.ktmmobile.msf.external.websecurity.web.dto.response.CustomErrorResponse;
import com.ktmmobile.msf.external.websecurity.web.dto.response.PageResponse;
import com.ktmmobile.msf.external.websecurity.web.dto.response.PagedDataResponse;
import com.ktmmobile.msf.external.websecurity.web.dto.response.SingleValueResponse;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

    private static ObjectMapper objectMapper;
    private static ResponseHandlingProperties responseHandlingProperties;

    public static void initialize(
        ObjectMapper objectMapper,
        ResponseHandlingProperties responseHandlingProperties
    ) {
        ResponseUtils.objectMapper = objectMapper;
        ResponseUtils.responseHandlingProperties = responseHandlingProperties;
    }


    /**
     * CommonResponseType.OK 응답 반환
     */

    public static CommonResponse<Void> ok() {
        return ok(null, null);
    }

    public static <T> CommonResponse<T> ok(T data) {
        return ok(data, null);
    }

    public static <T> CommonResponse<T> ok(T data, PageResponse page) {
        CommonResponseType type = CommonResponseType.OK;
        return CommonResponse.of(type.code(), type.message(), data, page);
    }

    public static <T> CommonResponse<List<T>> ok(PagedDataResponse<T> pagedList) {
        return ok(pagedList.data(), pagedList.page());
    }

    public static <V> CommonResponse<SingleValueResponse<V>> okSingleValue(String fieldName, V value) {
        return ok(SingleValueResponse.of(fieldName, value));
    }


    /**
     * CommonResponseType.CREATE 응답 반환
     */

    public static <K> ResponseEntity<CommonResponse<CreatedResponse<K>>> created(String location, String fieldName, K domainKey) {
        return created(location, CreatedResponse.of(fieldName, domainKey));
    }

    /** data 필드 없이 응답 바디 반환 - 권장하는 메서드는 아니므로 꼭 필요한 경우에만 사용할 것 */
    public static ResponseEntity<CommonResponse<Void>> created(String location) {
        return created(location, null);
    }

    public static <T> ResponseEntity<CommonResponse<T>> created(String location, T data) {
        CommonResponseType type = CommonResponseType.CREATED;
        CommonResponse<T> response = CommonResponse.of(type.code(), type.message(), data);
        return ResponseEntity.created(URI.create(location)).body(response);
    }


    /**
     * CommonResponseType.BAD_REQUEST 응답 반환
     */

    public static ResponseEntity<CommonResponse<Void>> badRequest() {
        return badRequest((List<BindErrorResponse>) null);
    }

    public static ResponseEntity<CommonResponse<Void>> badRequest(List<BindErrorResponse> errors) {
        CommonResponseType type = CommonResponseType.BAD_REQUEST;
        CommonResponse<Void> response = CommonResponse.of(type.code(), type.message(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    public static ResponseEntity<CommonResponse<Void>> badRequest(Exception e) {
        CommonResponseType type = CommonResponseType.BAD_REQUEST;
        CommonResponse<Void> response = CommonResponse.of(type.code(), e.getMessage());
        if (e instanceof CommonException commonEx) {
            CustomErrorCode customErrorCode = commonEx.getCode();
            if (customErrorCode != null) {
                response = new CommonResponse<>(type.code(), e.getMessage(), new CustomErrorResponse(customErrorCode));
            }
        }
        return ResponseEntity.badRequest().body(response);
    }


    /**
     * CommonResponseType.UNKNOWN_ERROR 응답 반환
     */

    public static <T> ResponseEntity<CommonResponse<T>> unknownError(T data) {
        return unknownError(data, CommonResponseType.UNKNOWN_ERROR.httpStatus());
    }

    public static <T> ResponseEntity<CommonResponse<T>> unknownError(T data, HttpStatus status) {
        CommonResponseType type = CommonResponseType.UNKNOWN_ERROR;
        CommonResponse<T> response = CommonResponse.of(type.code(), type.message(), data);
        return ResponseEntity.status(status.value()).body(response);
    }


    /**
     * 지정한 CommonResponseType 응답 반환
     */

    public static <T> ResponseEntity<CommonResponse<T>> responseOf(CommonResponseType type) {
        return responseOf(type, (T) null);
    }

    public static <T> ResponseEntity<CommonResponse<T>> responseOf(CommonResponseType type, T data) {
        CommonResponse<T> response = CommonResponse.of(type.code(), type.message(), data);
        return ResponseEntity.status(type.httpStatus()).body(response);
    }

    public static <T> ResponseEntity<CommonResponse<T>> responseOf(CommonResponseType type, Exception e) {
        String message = getResponseMessage(type, e);
        CommonResponse<T> response = CommonResponse.of(type.code(), message);
        if (e instanceof CommonException commonEx) {
            CustomErrorCode customErrorCode = commonEx.getCode();
            if (customErrorCode != null) {
                response = new CommonResponse<>(type.code(), e.getMessage(), new CustomErrorResponse(customErrorCode));
            }
        }
        return ResponseEntity.status(type.httpStatus()).body(response);
    }

    private static String getResponseMessage(CommonResponseType type, Exception e) {
        if (responseHandlingProperties.exceptionEnabled()) {
            return getExceptionMessage(e);
        }
        if (isCommonException(e)) {
            return e.getMessage();
        }
        return type.message();
    }

    private static String getExceptionMessage(Exception e) {
        String message;
        StringJoiner stringJoiner = new StringJoiner(": ");
        if (responseHandlingProperties.exceptionClassName()) {
            stringJoiner.add(e.getClass().getName());
        }
        if (responseHandlingProperties.exceptionMessage()) {
            stringJoiner.add(e.getMessage());
        }
        message = stringJoiner.toString();
        return message;
    }

    private static boolean isCommonException(Exception e) {
        return CommonException.class.isAssignableFrom(e.getClass());
    }


    /**
     * HttpServletResponse으로 직접 반환
     */

    public static <T> void setResponse(HttpServletResponse response, HttpStatus status, T body) {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(objectMapper.writeValueAsString(body));
        } catch (IOException e) {
            throw new IllegalArgumentException(body.getClass().getSimpleName() + " JSON 직렬화 실패");
        }
    }
}
