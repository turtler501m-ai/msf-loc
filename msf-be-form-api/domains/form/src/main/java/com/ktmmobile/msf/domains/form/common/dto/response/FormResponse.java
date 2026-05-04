package com.ktmmobile.msf.domains.form.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FormResponse<T>(
        String resCode,
        String resMessage,
        T resData
) {
    public static <T> FormResponse<T> of(ResponseMessage message, T data) {
        return new FormResponse<>(message.getCode(), message.getMessage(), data);
    }

    public static <T> FormResponse<T> of(ResponseMessage message) {
        return new FormResponse<>(message.getCode(), message.getMessage(), null);
    }

    public static <T> FormResponse<T> ok(T data) {
        return new FormResponse<>(ResponseMessage.SUCCESS.getCode(), ResponseMessage.SUCCESS.getMessage(), data);
    }
}
