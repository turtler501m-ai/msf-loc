package com.ktmmobile.msf.commons.logincore.application.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginPrincipal;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.JwtClaim;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.LoginJwtClaims;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.TokenType;
import com.ktmmobile.msf.commons.websecurity.security.auth.exception.MemberAuthenticationException;
import com.ktmmobile.msf.commons.websecurity.security.auth.properties.JwtSecurityProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.service.LoginJwtTokenValidator;

@RequiredArgsConstructor
@Service
public class LoginTokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final LoginCoreProperties properties;
    private final JwtSecurityProperties jwtSecurityProperties;
    private final LoginTokenStore tokenStore;
    private final LoginUserInfoCacheService loginUserInfoCacheService;
    private final LoginJwtTokenValidator loginJwtTokenValidator;

    public LoginTokenPair issue(LoginSessionUser user) {
        Instant now = Instant.now();
        String accessJti = createJti();
        String refreshJti = createJti();
        LoginPrincipal principal = LoginPrincipal.from(user);

        String accessToken = createToken(principal, accessJti, TokenType.ACCESS, now, now.plus(properties.token().accessTimeToLive()));
        String refreshToken = createToken(principal, refreshJti, TokenType.REFRESH, now, now.plus(properties.token().refreshTimeToLive()));

        tokenStore.saveTokenJti(TokenType.ACCESS, principal.userType(), principal.userId(), accessJti, properties.token().accessTimeToLive());
        tokenStore.saveTokenJti(TokenType.REFRESH, principal.userType(), principal.userId(), refreshJti, properties.token().refreshTimeToLive());

        return new LoginTokenPair(
            user.userId(),
            user.userType(),
            user.userName(),
            user.phoneNumber(),
            user.clientIp(),
            user.agentCode(),
            user.agentName(),
            user.shopCode(),
            user.shopName(),
            user.attributes(),
            user.requiredActions(),
            accessToken,
            refreshToken,
            now.plus(properties.token().accessTimeToLive()),
            now.plus(properties.token().refreshTimeToLive())
        );
    }

    public LoginTokenPair refresh(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        LoginJwtClaims claims = validateRefreshToken(jwt);
        if (!tokenStore.exists(claims.tokenType(), claims.userType(), claims.userId(), claims.jti())) {
            tokenStore.deleteTokens(claims.userType(), claims.userId());
            loginUserInfoCacheService.delete(claims.userType(), claims.userId());
            throw new LoginException("RefreshToken이 유효하지 않습니다.");
        }

        LoginSessionUser user = loginUserInfoCacheService.get(claims.userType(), claims.userId())
            .map(this::toSessionUser)
            .orElseGet(() -> new LoginSessionUser(
                claims.userId(),
                claims.userType(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                java.util.Map.of(),
                List.of()
            ));
        return issue(user);
    }

    public void logout(UserType userType, String userId) {
        deleteAuthentication(userType, userId);
    }

    public void revokeAuthentication(UserType userType, String userId) {
        deleteAuthentication(userType, userId);
    }

    private void deleteAuthentication(UserType userType, String userId) {
        tokenStore.deleteTokens(userType, userId);
        loginUserInfoCacheService.delete(userType, userId);
    }

    private String createToken(LoginPrincipal principal, String jti, TokenType tokenType, Instant issuedAt, Instant expiresAt) {
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
            .subject(principal.userId())
            .claim(JwtClaim.TOKEN_TYPE.key(), tokenType.getClaimValue())
            .claim(JwtClaim.USER_TYPE.key(), principal.userType().getCode());
        claimsBuilder.issuer(jwtSecurityProperties.issuer())
            .id(jti)
            .expiresAt(expiresAt)
            .issuedAt(issuedAt);
        JwtClaimsSet claims = claimsBuilder.build();

        JwsHeader headers = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(headers, claims)).getTokenValue();
    }

    private LoginJwtClaims validateRefreshToken(Jwt jwt) {
        try {
            return loginJwtTokenValidator.validate(jwt, TokenType.REFRESH);
        } catch (MemberAuthenticationException e) {
            throw new LoginException(e.getMessage());
        }
    }

    private String createJti() {
        return UUID.randomUUID().toString();
    }

    private LoginSessionUser toSessionUser(LoginUserInfo userInfo) {
        return new LoginSessionUser(
            userInfo.userId(),
            userInfo.userType(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            userInfo.clientIp(),
            userInfo.agentCode(),
            userInfo.agentName(),
            userInfo.shopCode(),
            userInfo.shopName(),
            userInfo.attributes(),
            List.of()
        );
    }

}
