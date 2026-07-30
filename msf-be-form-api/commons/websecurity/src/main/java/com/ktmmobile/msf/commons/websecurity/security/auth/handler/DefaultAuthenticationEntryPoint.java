package com.ktmmobile.msf.commons.websecurity.security.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.security.auth.exception.ExternalServiceAuthenticationException;
import com.ktmmobile.msf.commons.websecurity.security.auth.exception.MemberAuthenticationException;
import com.ktmmobile.msf.commons.websecurity.security.auth.service.LoginJwtTokenValidator;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponseType;
import com.ktmmobile.msf.commons.websecurity.web.util.response.FilterExceptionResponseUtils;

public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        FilterExceptionResponseUtils.handle(response, e, CommonResponseType.UNAUTHORIZED, responseMessage(e));
    }

    private String responseMessage(AuthenticationException e) {
        if (hasPublicMessage(e)) {
            return e.getMessage();
        }
        // Access Token 만료는 Spring Security JWT decode/validator 예외로 전달된다.
        if (isExpiredToken(e)) {
            return LoginJwtTokenValidator.TOKEN_EXPIRED_MESSAGE;
        }
        return CommonResponseType.UNAUTHORIZED.message();
    }

    private boolean hasPublicMessage(AuthenticationException e) {
        return (e instanceof MemberAuthenticationException || e instanceof ExternalServiceAuthenticationException)
            && StringUtils.hasText(e.getMessage());
    }

    private boolean isExpiredToken(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            String message = current.getMessage();
            if (message != null && message.toLowerCase().contains("expired")) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
