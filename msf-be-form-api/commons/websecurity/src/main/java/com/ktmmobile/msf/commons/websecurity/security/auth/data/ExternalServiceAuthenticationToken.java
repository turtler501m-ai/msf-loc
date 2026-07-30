package com.ktmmobile.msf.commons.websecurity.security.auth.data;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.ExternalServiceUserDetails;

/**
 * 외부 서비스 호출 인증 토큰
 */
public class ExternalServiceAuthenticationToken extends AbstractAuthenticationToken {

    private final String serviceName;
    private final ExternalServiceUserDetails principal;

    public ExternalServiceAuthenticationToken(
        String serviceName,
        ExternalServiceUserDetails principal,
        Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.serviceName = serviceName;
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public String getName() {
        return serviceName;
    }
}
