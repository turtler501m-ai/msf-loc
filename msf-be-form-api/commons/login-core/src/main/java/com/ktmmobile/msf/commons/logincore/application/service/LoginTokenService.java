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
import com.ktmmobile.msf.commons.logincore.domain.code.TokenType;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginPrincipal;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.properties.JwtSecurityProperties;

@RequiredArgsConstructor
@Service
public class LoginTokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final LoginCoreProperties properties;
    private final JwtSecurityProperties jwtSecurityProperties;
    private final LoginTokenStore tokenStore;
    private final LoginUserInfoCacheService loginUserInfoCacheService;

    public LoginTokenPair issue(LoginSessionUser user) {
        Instant now = Instant.now();
        String accessJti = createJti();
        String refreshJti = createJti();
        LoginPrincipal principal = LoginPrincipal.from(user);

        String accessToken = createToken(principal, accessJti, TokenType.ACCESS, now, now.plus(properties.token().accessTimeToLive()));
        String refreshToken = createToken(principal, refreshJti, TokenType.REFRESH, now, now.plus(properties.token().refreshTimeToLive()));

        tokenStore.saveAccessTokenJti(principal.userType(), principal.userId(), accessJti, properties.token().accessTimeToLive());
        tokenStore.saveRefreshTokenJti(principal.userType(), principal.userId(), refreshJti, properties.token().refreshTimeToLive());

        return new LoginTokenPair(
            user.userId(),
            user.userType(),
            user.userName(),
            user.phoneNumber(),
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
        validateRefreshToken(jwt);

        String userId = jwt.getSubject();
        String refreshJti = jwt.getId();
        UserType userType = UserType.valueOfCode(jwt.getClaimAsString("userType"));
        if (!tokenStore.matchesRefreshToken(userType, userId, refreshJti)) {
            tokenStore.deleteTokens(userType, userId);
            loginUserInfoCacheService.delete(userType, userId);
            throw new LoginException("RefreshToken이 유효하지 않습니다.");
        }

        LoginSessionUser user = loginUserInfoCacheService.get(userType, userId)
            .map(this::toSessionUser)
            .orElseGet(() -> new LoginSessionUser(
                userId,
                userType,
                null,
                null,
                java.util.Map.of(),
                List.of()
            ));
        return issue(user);
    }

    public void logout(String refreshToken) {
        Jwt jwt = jwtDecoder.decode(refreshToken);
        UserType userType = UserType.valueOfCode(jwt.getClaimAsString("userType"));
        tokenStore.deleteTokens(userType, jwt.getSubject());
        loginUserInfoCacheService.delete(userType, jwt.getSubject());
    }

    private String createToken(LoginPrincipal principal, String jti, TokenType tokenType, Instant issuedAt, Instant expiresAt) {
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
            .subject(principal.userId())
            .claim("type", tokenType.getClaimValue())
            .claim("userType", principal.userType().getCode());
        claimsBuilder.issuer(jwtSecurityProperties.issuer())
            .id(jti)
            .expiresAt(expiresAt)
            .issuedAt(issuedAt);
        JwtClaimsSet claims = claimsBuilder.build();

        JwsHeader headers = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(headers, claims)).getTokenValue();
    }

    private void validateRefreshToken(Jwt jwt) {
        String type = jwt.getClaimAsString("type");
        if (!TokenType.REFRESH.getClaimValue().equals(type)) {
            throw new LoginException("RefreshToken이 아닙니다.");
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
            userInfo.attributes(),
            List.of()
        );
    }

}
