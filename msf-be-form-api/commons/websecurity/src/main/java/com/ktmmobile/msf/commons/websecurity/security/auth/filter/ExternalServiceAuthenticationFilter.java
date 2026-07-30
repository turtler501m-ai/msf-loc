package com.ktmmobile.msf.commons.websecurity.security.auth.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ktmmobile.msf.commons.common.data.entity.user.ExternalServiceUser;
import com.ktmmobile.msf.commons.common.data.entity.user.UserOrganization;
import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.ExternalServiceAuthenticationToken;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.ExternalServiceUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.MsfUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.exception.ExternalServiceAuthenticationException;
import com.ktmmobile.msf.commons.websecurity.security.auth.property.ExternalServiceAuthenticationProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.property.ExternalServiceAuthenticationServiceProperties;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;

/**
 * 외부 서비스 호출 전용 API key + IP allowlist 인증 필터
 */
@Slf4j
public class ExternalServiceAuthenticationFilter extends OncePerRequestFilter {

    private static final String EXTERNAL_SERVICE_API_PATH_PREFIX = "/api/external-services/";
    private static final String INVALID_AUTHENTICATION_MESSAGE = "API Key가 유효하지 않습니다.";
    private static final String NOT_ALLOWED_IP_ADDRESS_MESSAGE = "허용되지 않은 IP입니다.";
    private static final List<SimpleGrantedAuthority> AUTHORITIES = List.of(
        new SimpleGrantedAuthority(MsfUserDetails.roleAuthority(UserType.EXTERNAL_SERVICE_USER))
    );

    private final ExternalServiceAuthenticationProperties properties;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final WebAuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

    public ExternalServiceAuthenticationFilter(
        ExternalServiceAuthenticationProperties properties,
        AuthenticationEntryPoint authenticationEntryPoint
    ) {
        this.properties = properties;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        if (!requiresAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            ExternalServiceAuthenticationToken authentication = authenticate(request);
            authentication.setDetails(authenticationDetailsSource.buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, e);
        }
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        if (!properties.requiresAuthentication()) {
            return false;
        }

        return isExternalServiceApiPath(RequestUtils.getRequestPath(request));
    }

    private ExternalServiceAuthenticationToken authenticate(HttpServletRequest request) {
        String apiKey = request.getHeader(properties.apiKeyHeaderName());
        if (!StringUtils.hasText(apiKey)) {
            throw new ExternalServiceAuthenticationException("API Key 헤더가 없습니다.");
        }

        String serviceName = serviceNameFromPath(RequestUtils.getRequestPath(request));
        if (!StringUtils.hasText(serviceName)) {
            throw new ExternalServiceAuthenticationException("확인되지 않은 클라이언트입니다.");
        }

        ExternalServiceAuthenticationServiceProperties serviceProperties = properties.services().get(serviceName);
        if (serviceProperties == null) {
            throw new ExternalServiceAuthenticationException("확인되지 않은 클라이언트입니다.");
        }

        if (!matchesApiKey(apiKey, serviceProperties.apiKey())) {
            throw new ExternalServiceAuthenticationException(INVALID_AUTHENTICATION_MESSAGE);
        }

        String clientIp = RequestUtils.getClientIp(request);
        if (!matchesClientIp(clientIp, serviceProperties.allowedIpAddresses())) {
            log.warn("외부 서비스 인증 허용 IP 불일치 serviceName={}, clientIp={}, requestPath={}, allowedIpAddresses={}",
                serviceName, clientIp, RequestUtils.getRequestPath(request), serviceProperties.allowedIpAddresses());
            throw new ExternalServiceAuthenticationException(NOT_ALLOWED_IP_ADDRESS_MESSAGE);
        }

        String userName = StringUtils.hasText(serviceProperties.userName()) ? serviceProperties.userName() : serviceName;
        ExternalServiceUser serviceUser = new ExternalServiceUser(UserType.EXTERNAL_SERVICE_USER, serviceName, userName, UserOrganization.empty());
        ExternalServiceUserDetails userDetails = new ExternalServiceUserDetails(serviceUser, AUTHORITIES);
        return new ExternalServiceAuthenticationToken(serviceName, userDetails, AUTHORITIES);
    }

    private boolean isExternalServiceApiPath(String requestPath) {
        return StringUtils.hasText(requestPath) && requestPath.startsWith(EXTERNAL_SERVICE_API_PATH_PREFIX);
    }

    private String serviceNameFromPath(String requestPath) {
        if (!isExternalServiceApiPath(requestPath)) {
            return null;
        }

        String remainingPath = requestPath.substring(EXTERNAL_SERVICE_API_PATH_PREFIX.length());
        int separatorIndex = remainingPath.indexOf('/');
        String serviceName = separatorIndex < 0 ? remainingPath : remainingPath.substring(0, separatorIndex);
        return StringUtils.hasText(serviceName) ? serviceName : null;
    }

    private boolean matchesApiKey(String actual, String expected) {
        if (!StringUtils.hasText(actual) || !StringUtils.hasText(expected)) {
            return false;
        }
        return MessageDigest.isEqual(
            actual.getBytes(StandardCharsets.UTF_8),
            expected.getBytes(StandardCharsets.UTF_8)
        );
    }

    private boolean matchesClientIp(String clientIp, List<String> allowedIpAddresses) {
        if (!StringUtils.hasText(clientIp) || allowedIpAddresses.isEmpty()) {
            return false;
        }

        return allowedIpAddresses.stream()
            .filter(StringUtils::hasText)
            .anyMatch(allowedIpAddress -> matchesClientIp(clientIp, allowedIpAddress));
    }

    private boolean matchesClientIp(String clientIp, String allowedIpAddress) {
        return new IpAddressMatcher(allowedIpAddress).matches(clientIp);
    }

}
