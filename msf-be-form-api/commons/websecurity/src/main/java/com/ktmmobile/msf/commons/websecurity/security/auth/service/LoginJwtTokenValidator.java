package com.ktmmobile.msf.commons.websecurity.security.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.JwtClaim;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.LoginJwtClaims;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.TokenType;
import com.ktmmobile.msf.commons.websecurity.security.auth.exception.MemberAuthenticationException;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.ActiveTokenChecker;

@RequiredArgsConstructor
@Component
public class LoginJwtTokenValidator {

    private final ActiveTokenChecker activeTokenChecker;

    public LoginJwtClaims validate(Jwt jwt, TokenType expectedTokenType) {
        String userId = jwt.getSubject();
        String jti = jwt.getId();
        TokenType tokenType = TokenType.valueOfClaim(jwt.getClaimAsString(JwtClaim.TOKEN_TYPE.key()));
        UserType userType = UserType.valueOfCode(jwt.getClaimAsString(JwtClaim.USER_TYPE.key()));

        if (!StringUtils.hasText(userId)) {
            throw new MemberAuthenticationException("JWT subject(sub) claim is required");
        }
        if (!StringUtils.hasText(jti)) {
            throw new MemberAuthenticationException("JWT jti claim is required");
        }
        if (tokenType == null) {
            throw new MemberAuthenticationException("JWT type claim is invalid");
        }
        if (tokenType != expectedTokenType) {
            throw new MemberAuthenticationException(expectedTokenType.getDisplayName() + "이 아닙니다.");
        }
        if (!userType.isValid()) {
            throw new MemberAuthenticationException("JWT userType claim is invalid");
        }
        return new LoginJwtClaims(userId, userType, jti, tokenType);
    }

    public LoginJwtClaims validateActive(Jwt jwt, TokenType expectedTokenType) {
        LoginJwtClaims claims = validate(jwt, expectedTokenType);
        if (!activeTokenChecker.exists(claims.tokenType(), claims.userType(), claims.userId(), claims.jti())) {
            throw new MemberAuthenticationException(expectedTokenType.getDisplayName() + "이 유효하지 않습니다.");
        }
        return claims;
    }
}
