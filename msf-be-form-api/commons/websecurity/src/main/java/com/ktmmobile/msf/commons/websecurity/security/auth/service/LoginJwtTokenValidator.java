package com.ktmmobile.msf.commons.websecurity.security.auth.service;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
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

    public static final String TOKEN_EXPIRED_MESSAGE = "로그인 시간이 만료되었습니다.";
    public static final String TOKEN_LOGGED_OUT_MESSAGE = "로그인이 해제되었습니다.";
    public static final String TOKEN_REPLACED_MESSAGE = "다른 기기(브라우저)에서 로그인되어 현재 기기의 로그인이 해제되었습니다.";

    private final ObjectProvider<ActiveTokenChecker> activeTokenCheckerProvider;

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
        ActiveTokenChecker activeTokenChecker = activeTokenCheckerProvider.getIfAvailable();
        if (activeTokenChecker == null) {
            return claims;
        }
        String activeJti = activeTokenChecker.getActiveTokenJti(claims.tokenType(), claims.userType(), claims.userId());
        // 로그아웃, 강제 인증 해제, 캐시 만료 등으로 활성 토큰 JTI가 없는 경우
        if (!StringUtils.hasText(activeJti)) {
            throw new MemberAuthenticationException(TOKEN_LOGGED_OUT_MESSAGE);
        }
        // 다른 기기 로그인으로 활성 토큰 JTI가 교체된 경우
        if (!Objects.equals(activeJti, claims.jti())) {
            throw new MemberAuthenticationException(TOKEN_REPLACED_MESSAGE);
        }
        return claims;
    }
}
