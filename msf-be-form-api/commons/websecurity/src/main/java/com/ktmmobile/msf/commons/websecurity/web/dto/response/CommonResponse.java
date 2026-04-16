package com.ktmmobile.msf.commons.websecurity.web.dto.response;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonResponse<T>(
    String code,
    String message,
    MetaData meta,
    T data,
    List<BindErrorResponse> errors,
    CustomErrorResponse error
) {

    public static <T> CommonResponse<T> of(String code, String message) {
        return new CommonResponse<>(code, message, null, null, null, null);
    }

    public static <T> CommonResponse<T> of(String code, String message, T data) {
        MetaData meta = data != null ? MetaData.of(DataType.valueOf(data), getDataCount(data)) : null;
        return new CommonResponse<>(code, message, meta, data, null, null);
    }

    public static <T> CommonResponse<T> of(String code, String message, T data, PageResponse page) {
        MetaData meta = data != null ? MetaData.of(DataType.valueOf(data), getDataCount(data), page) : null;
        return new CommonResponse<>(code, message, meta, data, null, null);
    }

    public static <T> CommonResponse<T> of(String code, String message, List<BindErrorResponse> errors) {
        return new CommonResponse<>(code, message, null, null, errors, null);
    }

    public static <T> CommonResponse<T> of(String code, String message, T data, List<BindErrorResponse> errors) {
        MetaData meta = data != null ? MetaData.of(DataType.valueOf(data), getDataCount(data)) : null;
        return new CommonResponse<>(code, message, meta, data, errors, null);
    }

    public CommonResponse(String code, String message, CustomErrorResponse error) {
        this(code, message, null, null, null, error);
    }

    private static <T> boolean isCollection(T data) {
        return data instanceof Collection<?>;
    }

    private static <T> Integer getDataCount(T data) {
        if (isCollection(data)) {
            return ((Collection<?>) data).size();
        }
        return data != null ? 1 : 0;
    }


    public record MetaData(
        DataType dataType,
        int dataCount,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        PageResponse page
    ) {

        public static MetaData of(DataType dataType, Integer dataCount) {
            return new MetaData(dataType, dataCount, null);
        }

        public static MetaData of(DataType dataType, Integer dataCount, PageResponse page) {
            if (dataType == null) {
                return null;
            }
            return new MetaData(dataType, dataCount, page);
        }
    }


    public enum DataType {
        SINGLE, LIST;

        public static <T> DataType valueOf(T data) {
            if (data == null) {
                return null;
            }
            if (isCollection(data)) {
                return LIST;
            }
            return SINGLE;
        }
    }
}
