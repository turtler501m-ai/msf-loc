package com.ktmmobile.msf.domains.login.adapter.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.websecurity.security.auth.properties.JwtSecurityProperties;

@Component
@RequiredArgsConstructor
public class JwtTokenIssuer implements com.ktmmobile.msf.domains.login.application.port.out.JwtTokenIssuer {

    private final JwtEncoder jwtEncoder;
    private final JwtSecurityProperties jwtSecurityProperties;

    @Override
    public IssuedJwtToken issueAccessToken(String userId, Duration timeToLive) {
        return issue(userId, timeToLive, "access");
    }

    @Override
    public IssuedJwtToken issueRefreshToken(String userId, Duration timeToLive) {
        return issue(userId, timeToLive, "refresh");
    }

    private IssuedJwtToken issue(String userId, Duration timeToLive, String tokenType) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(timeToLive);
        String jti = UUID.randomUUID().toString();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer(jwtSecurityProperties.issuer())
            .issuedAt(issuedAt)
            .expiresAt(expiresAt)
            .subject(userId)
            .id(jti)
            .claim("type", tokenType)
            .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        String tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
        return new IssuedJwtToken(tokenValue, jti, expiresAt);
    }
}
