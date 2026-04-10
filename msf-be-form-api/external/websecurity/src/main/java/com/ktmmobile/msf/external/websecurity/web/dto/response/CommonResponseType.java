package com.ktmmobile.msf.external.websecurity.web.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CommonResponseType {
    OK("0000", "성공", HttpStatus.OK, StatusCodeRelated.RELATED),
    CREATED("0201", "등록 성공", HttpStatus.CREATED, StatusCodeRelated.RELATED),
    ACCEPTED("0202", "접수 완료", HttpStatus.ACCEPTED, StatusCodeRelated.RELATED),
    BAD_REQUEST("0400", "요청이 올바르지 않음", HttpStatus.BAD_REQUEST, StatusCodeRelated.RELATED),
    UNAUTHORIZED("0401", "인증 오류", HttpStatus.UNAUTHORIZED, StatusCodeRelated.RELATED),
    FORBIDDEN("0403", "인가 오류", HttpStatus.FORBIDDEN, StatusCodeRelated.RELATED),
    API_NOT_FOUND("0404", "API 없음", HttpStatus.NOT_FOUND, StatusCodeRelated.RELATED),
    RESOURCE_NOT_FOUND("1404", "리소스 없음", HttpStatus.NOT_FOUND, StatusCodeRelated.NOT_RELATED),
    METHOD_NOT_ALLOWED("0405", "해당 HTTP 메서드를 허용하지 않음", HttpStatus.METHOD_NOT_ALLOWED, StatusCodeRelated.RELATED),
    CONFLICT("0409", "충돌 발생", HttpStatus.CONFLICT, StatusCodeRelated.RELATED),
    UNSUPPORTED_MEDIA_TYPE("0415", "미디어 타입을 지원하지 않음", HttpStatus.UNSUPPORTED_MEDIA_TYPE, StatusCodeRelated.RELATED),
    DOMAIN_ERROR("6000", "도메인 처리 오류", HttpStatus.INTERNAL_SERVER_ERROR, StatusCodeRelated.NOT_RELATED),
    UNKNOWN_ERROR("9999", "알 수 없는 오류", HttpStatus.INTERNAL_SERVER_ERROR, StatusCodeRelated.RELATED);

    private final String code;
    private final String message;
    private final HttpStatus status;
    private final StatusCodeRelated statusRelated;

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public HttpStatus httpStatus() {
        return status;
    }

    public static CommonResponseType valueOfHttpStatus(HttpStatus status) {
        for (CommonResponseType value: values()) {
            if (value.statusRelated.isRelated() && value.status == status) {
                return value;
            }
        }
        return null;
    }


    @Getter
    @RequiredArgsConstructor
    private enum StatusCodeRelated {
        RELATED(true),
        NOT_RELATED(false);

        private final boolean related;
    }
}
