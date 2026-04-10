package com.ktmmobile.msf.commons.login.application.service;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.login.application.port.in.LoginTokenIssuer;
import com.ktmmobile.msf.commons.login.application.port.in.LoginTokenRefresher;
import com.ktmmobile.msf.commons.login.application.port.in.Logout;
import com.ktmmobile.msf.commons.login.application.port.out.AccessTokenStore;
import com.ktmmobile.msf.commons.login.application.port.out.JwtTokenIssuer;
import com.ktmmobile.msf.commons.login.application.port.out.JwtTokenReader;
import com.ktmmobile.msf.commons.login.application.port.out.RefreshTokenStore;
import com.ktmmobile.msf.commons.login.domain.dto.LoginTokens;
import com.ktmmobile.msf.external.websecurity.security.auth.exception.MemberAuthenticationException;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginTokenIssuer, LoginTokenRefresher, Logout {

    private static final Duration ACCESS_TOKEN_TTL = Duration.ofMinutes(30);
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(14);

    private final JwtTokenIssuer jwtTokenIssuer;
    private final JwtTokenReader jwtTokenReader;
    private final AccessTokenStore accessTokenStore;
    private final RefreshTokenStore refreshTokenStore;

    @Override
    public LoginTokens issue(String userId) {
        Assert.hasText(userId, "userId is required");

        return issueTokens(userId);
    }

    @Override
    public LoginTokens refresh(String refreshToken) {
        Assert.hasText(refreshToken, "refreshToken is required");

        JwtTokenReader.DecodedJwtToken decodedRefreshToken = jwtTokenReader.read(refreshToken);
        validateRefreshToken(decodedRefreshToken);

        String savedRefreshToken = refreshTokenStore.getRefreshToken(decodedRefreshToken.userId());
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new MemberAuthenticationException("RefreshToken이 유효하지 않습니다.");
        }

        return issueTokens(decodedRefreshToken.userId());
    }

    @Override
    public void logout(String accessToken) {
        Assert.hasText(accessToken, "accessToken is required");

        JwtTokenReader.DecodedJwtToken decodedAccessToken = jwtTokenReader.read(accessToken);
        validateAccessToken(decodedAccessToken);

        accessTokenStore.deleteAccessToken(decodedAccessToken.jti());
        refreshTokenStore.deleteRefreshToken(decodedAccessToken.userId());
    }

    private LoginTokens issueTokens(String userId) {
        JwtTokenIssuer.IssuedJwtToken accessToken = jwtTokenIssuer.issueAccessToken(userId, ACCESS_TOKEN_TTL);
        JwtTokenIssuer.IssuedJwtToken refreshToken = jwtTokenIssuer.issueRefreshToken(userId, REFRESH_TOKEN_TTL);

        accessTokenStore.saveAccessToken(accessToken.jti(), userId, ACCESS_TOKEN_TTL);
        refreshTokenStore.saveRefreshToken(userId, refreshToken.tokenValue(), REFRESH_TOKEN_TTL);

        return new LoginTokens(
            accessToken.tokenValue(),
            accessToken.expiresAt(),
            refreshToken.tokenValue(),
            refreshToken.expiresAt()
        );
    }

    private static void validateRefreshToken(JwtTokenReader.DecodedJwtToken decodedRefreshToken) {
        if (!StringUtils.hasText(decodedRefreshToken.userId())) {
            throw new MemberAuthenticationException("RefreshToken subject(sub) claim is required");
        }
        if (!StringUtils.hasText(decodedRefreshToken.jti())) {
            throw new MemberAuthenticationException("RefreshToken jti claim is required");
        }
        if (!"refresh".equals(decodedRefreshToken.type())) {
            throw new MemberAuthenticationException("RefreshToken이 아닙니다.");
        }
    }

    private static void validateAccessToken(JwtTokenReader.DecodedJwtToken decodedAccessToken) {
        if (!StringUtils.hasText(decodedAccessToken.userId())) {
            throw new MemberAuthenticationException("AccessToken subject(sub) claim is required");
        }
        if (!StringUtils.hasText(decodedAccessToken.jti())) {
            throw new MemberAuthenticationException("AccessToken jti claim is required");
        }
        if (!"access".equals(decodedAccessToken.type())) {
            throw new MemberAuthenticationException("AccessToken이 아닙니다.");
        }
    }
}
