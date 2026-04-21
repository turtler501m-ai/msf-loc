package com.ktmmobile.msf.commons.websecurity.web.exhandler;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.ktmmobile.msf.commons.common.exception.DomainException;
import com.ktmmobile.msf.commons.common.exception.DuplicateDataException;
import com.ktmmobile.msf.commons.common.exception.NotFoundException;
import com.ktmmobile.msf.commons.common.utils.log.LogUtils;
import com.ktmmobile.msf.commons.websecurity.BindingProperties;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.BindErrorResponse;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponseType;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;

@Slf4j
@Order(0)
@RequiredArgsConstructor
@RestControllerAdvice
public class CommonControllerAdvice {

    private final BindingProperties bindingProperties;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        if (bindingProperties.stringTrim()) {
            StringTrimmerEditor editor = new StringTrimmerEditor(bindingProperties.stringEmptyAsNull());
            binder.registerCustomEditor(String.class, editor);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<Void>> illegalArgumentException(IllegalArgumentException e) {
        return ResponseUtils.badRequest(e);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<Void>> bindException(BindException e) {
        List<BindErrorResponse> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(BindErrorResponse::of)
            .toList();
        return ResponseUtils.badRequest(errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CommonResponse<Void>> handlerMethodValidationException(HandlerMethodValidationException e) {
        List<BindErrorResponse> errors = e.getParameterValidationResults()
            .stream()
            .map(BindErrorResponse::of)
            .toList();
        return ResponseUtils.badRequest(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse<Void>> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable cause = e.getMostSpecificCause();
        if (cause instanceof DomainException domainEx) {
            return ResponseUtils.badRequest(domainEx);
        }
        if (cause instanceof Exception ex && StringUtils.hasText(ex.getMessage())) {
            return ResponseUtils.badRequest(ex);
        }
        return ResponseUtils.badRequest(e);
    }

    //@ExceptionHandler(MissingServletRequestParameterException.class)
    // public ResponseEntity<CommonResponse<Void>> missingServletRequestParameterException(MissingServletRequestParameterException e) {
    //    return ResponseUtils.badRequest(List.of(BindErrorResponse.of(e)));
    //}

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<CommonResponse<Void>> duplicateDataException(DuplicateDataException e) {
        return ResponseUtils.responseOf(CommonResponseType.CONFLICT, e);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> notFoundException(NotFoundException e) {
        return ResponseUtils.responseOf(CommonResponseType.RESOURCE_NOT_FOUND, e);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<CommonResponse<Void>> domainException(DomainException e) {
        CommonResponseType responseType = CommonResponseType.DOMAIN_ERROR;
        LogUtils.logException(log, e, responseType.message());
        return ResponseUtils.responseOf(responseType, e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> globalException(Exception e) {
        log.error("알 수 없는 오류 발생", e);
        return ResponseUtils.responseOf(CommonResponseType.UNKNOWN_ERROR, e);
    }

}
