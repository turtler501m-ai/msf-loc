package com.ktmmobile.msf.commons.login.adapter.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.external.websecurity.security.auth.exception.MemberAuthenticationException;

@Component
@RequiredArgsConstructor
public class SmartFormJwtTokenReader implements com.ktmmobile.msf.commons.login.application.port.out.JwtTokenReader {

    private final JwtDecoder jwtDecoder;

    @Override
    public DecodedJwtToken read(String tokenValue) {
        Jwt jwt = jwtDecoder.decode(tokenValue);
        String userId = jwt.getSubject();
        String jti = jwt.getId();
        String type = jwt.getClaimAsString("type");

        if (!StringUtils.hasText(userId)) {
            throw new MemberAuthenticationException("JWT subject(sub) claim is required");
        }
        if (!StringUtils.hasText(jti)) {
            throw new MemberAuthenticationException("JWT jti claim is required");
        }
        if (!StringUtils.hasText(type)) {
            throw new MemberAuthenticationException("JWT type claim is required");
        }

        return new DecodedJwtToken(tokenValue, userId, jti, type, jwt.getExpiresAt());
    }
}
