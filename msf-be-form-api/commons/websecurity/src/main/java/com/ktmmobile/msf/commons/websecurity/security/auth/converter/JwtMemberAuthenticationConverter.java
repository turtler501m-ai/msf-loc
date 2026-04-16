package com.ktmmobile.msf.commons.websecurity.security.auth.converter;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.security.auth.exception.MemberAuthenticationException;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.ActiveAccessTokenPort;

@Component
public class JwtMemberAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final ActiveAccessTokenPort activeAccessTokenPort;

    public JwtMemberAuthenticationConverter(ObjectProvider<ActiveAccessTokenPort> activeAccessTokenPortProvider) {
        Assert.notNull(activeAccessTokenPortProvider, "activeAccessTokenPortProvider is required");
        this.activeAccessTokenPort = activeAccessTokenPortProvider.getIfAvailable();
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String memberId = jwt.getSubject();
        String jti = jwt.getId();
        String type = jwt.getClaimAsString("type");

        if (!StringUtils.hasText(memberId)) {
            throw new MemberAuthenticationException("JWT subject(sub) claim is required");
        }
        if (!StringUtils.hasText(jti)) {
            throw new MemberAuthenticationException("JWT jti claim is required");
        }
        if (!"access".equals(type)) {
            throw new MemberAuthenticationException("AccessToken이 아닙니다.");
        }
        if (activeAccessTokenPort != null && !activeAccessTokenPort.exists(jti)) {
            throw new MemberAuthenticationException("AccessToken이 유효하지 않습니다.");
        }

        return new JwtAuthenticationToken(jwt, List.of(), memberId);
    }
}
