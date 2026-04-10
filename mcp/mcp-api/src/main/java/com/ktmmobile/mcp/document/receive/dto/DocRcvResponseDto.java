package com.ktmmobile.mcp.document.receive.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class DocRcvResponseDto<T> {

    private String code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public DocRcvResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public DocRcvResponseDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
