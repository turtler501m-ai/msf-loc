package com.ktmmobile.msf.external.websecurity.security.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.ktmmobile.msf.external.websecurity.web.dto.response.CommonResponseType;
import com.ktmmobile.msf.external.websecurity.web.util.response.FilterExceptionResponseUtils;

public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        FilterExceptionResponseUtils.handle(response, e, CommonResponseType.FORBIDDEN);
    }
}
