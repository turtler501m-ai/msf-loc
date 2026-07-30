package com.ktmmobile.msf.commons.websecurity.web.dto.response;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;

import com.ktmmobile.msf.commons.websecurity.web.validation.BindRejectable;

public record BindErrorResponse(
    String field,
    String message,
    String rejectedValue
) {

    public static final String DEFAULT_INVALID_PARAMETER_MESSAGE = "Invalid parameter";

    public static BindErrorResponse of(FieldError error) {
        return new BindErrorResponse(error.getField(), error.getDefaultMessage(), getRejectedValue(error));
    }

    private static String getRejectedValue(Object argument) {
        if (argument instanceof FieldError fieldError) {
            return getRejectedValueOfFieldError(fieldError);
        }
        if (argument instanceof BindRejectable bindRejectable) {
            return bindRejectable.rejectedValue();
        }
        return null;
    }

    private static String getRejectedValueOfFieldError(FieldError argument) {
        Object rejectedValue = argument.getRejectedValue();
        if (rejectedValue != null) {
            if (rejectedValue instanceof BindRejectable bindRejectable) {
                return bindRejectable.rejectedValue();
            }
            return rejectedValue.toString();
        }
        return null;
    }

    public static BindErrorResponse of(ParameterValidationResult result) {
        String field = result.getMethodParameter().getParameterName();
        String message = getMessage(result);
        String rejectedValue = getRejectedValue(result.getArgument());
        return new BindErrorResponse(field, message, rejectedValue);
    }

    private static String getMessage(ParameterValidationResult result) {
        return result.getResolvableErrors()
            .stream()
            .map(MessageSourceResolvable::getDefaultMessage)
            .findFirst()
            .orElse(DEFAULT_INVALID_PARAMETER_MESSAGE);
    }
}
