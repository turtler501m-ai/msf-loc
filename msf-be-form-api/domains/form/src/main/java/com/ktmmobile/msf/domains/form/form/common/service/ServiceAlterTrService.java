package com.ktmmobile.msf.domains.form.form.common.service;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.SvcChgPageRepositoryImpl;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceAlterTrService {

    private final SvcChgPageRepositoryImpl svcChgPageRepository;

    public boolean insertServiceAlterTrace(McpServiceAlterTraceDto serviceAlterTrace) {
        try {
            setRequestInfo(serviceAlterTrace);
            return svcChgPageRepository.insertServiceAlterTrace(serviceAlterTrace);
        } catch (Exception e) {
            log.warn("Failed to insert service alter trace. Business flow will continue.", e);
            return false;
        }
    }

    private void setRequestInfo(McpServiceAlterTraceDto serviceAlterTrace) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            return;
        }

        HttpServletRequest request = servletRequestAttributes.getRequest();
        try {
            serviceAlterTrace.setUserId(AuthenticationUtils.getUser().getUserId());
        } catch (RuntimeException e) {
            log.debug("Authenticated user is unavailable for service alter trace: {}", e.getMessage());
        }
        serviceAlterTrace.setAccessUrl(request.getRequestURI());
    }
}
