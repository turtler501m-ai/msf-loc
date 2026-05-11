package com.ktmmobile.msf.domains.form.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.code.ResTermMessage;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FormResponse<T>(
    String resCode,
    String resMessage,
    T resData
) {

    //신규/변경
    public static <T> FormResponse<T> of(ResponseMessage message, T data) {
        return new FormResponse<>(message.getCode(), message.getMessage(), data);
    }

    public static <T> FormResponse<T> of(ResponseMessage message) {
        return new FormResponse<>(message.getCode(), message.getMessage(), null);
    }

    public static <T> FormResponse<T> ok(T data) {
        return new FormResponse<>(ResponseMessage.SUCCESS.getCode(), ResponseMessage.SUCCESS.getMessage(), data);
    }

    //명의변경

    //서비스해지
    public static <T> FormResponse<T> of(ResTermMessage message, T data) {
        return new FormResponse<>(message.getCode(), message.getMessage(), data);
    }

    public static <T> FormResponse<T> of(ResTermMessage message) {
        return new FormResponse<>(message.getCode(), message.getMessage(), null);
    }

    public static <T> FormResponse<T> of(ResTermMessage message, String resMessage, T data) {
        return new FormResponse<>(message.getCode(), resMessage, data);
    }

    //서비스변경
    public static <T> FormResponse<T> of(ResSvcChgMessage message, T data) {
        return new FormResponse<>(message.getCode(), message.getMessage(), data);
    }

    public static <T> FormResponse<T> of(ResSvcChgMessage message) {
        return new FormResponse<>(message.getCode(), message.getMessage(), null);
    }

    public static <T> FormResponse<T> of(ResSvcChgMessage message, String resMessage, T data) {
        return new FormResponse<>(message.getCode(), resMessage, data);
    }
}
