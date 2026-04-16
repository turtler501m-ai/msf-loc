package com.ktmmobile.msf.commons.websecurity.security.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponseType;
import com.ktmmobile.msf.commons.websecurity.web.util.response.FilterExceptionResponseUtils;

public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        FilterExceptionResponseUtils.handle(response, e, CommonResponseType.UNAUTHORIZED);
    }
}
