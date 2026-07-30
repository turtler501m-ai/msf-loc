package com.ktmmobile.msf.commons.logincore.application.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginPrincipal;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.JwtClaim;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.LoginJwtClaims;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.TokenType;
import com.ktmmobile.msf.commons.websecurity.security.auth.exception.MemberAuthenticationException;
import com.ktmmobile.msf.commons.websecurity.security.auth.property.JwtSecurityProperties;
import com.ktmmobile.msf.commons.websecurity.security.auth.service.LoginJwtTokenValidator;

/**
 * Access Token, Refresh Token 발급과 rotation 처리 서비스
 */
@RequiredArgsConstructor
@Service
public class LoginTokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final LoginCoreProperties properties;
    private final JwtSecurityProperties jwtSecurityProperties;
    private final LoginTokenStore tokenStore;
    private final LoginUserInfoCacheService loginUserInfoCacheService;
    private final LoginUserInfoResolver loginUserInfoResolver;
    private final LoginJwtTokenValidator loginJwtTokenValidator;

    /**
     * Access Token과 Refresh Token 발급
     *
     * @param user 로그인 세션 사용자
     * @return 토큰 쌍
     */
    public LoginTokenPair issue(LoginSessionUser user) {
        Instant now = Instant.now();
        String accessJti = createJti();
        String refreshJti = createJti();
        LoginPrincipal principal = LoginPrincipal.from(user);

        // JWT에는 사용자 식별에 필요한 최소 클레임만 포함
        String accessToken = createToken(principal, accessJti, TokenType.ACCESS, now, now.plus(properties.token().accessTimeToLive()));
        String refreshToken = createToken(principal, refreshJti, TokenType.REFRESH, now, now.plus(properties.token().refreshTimeToLive()));

        // 단일 기기/단일 세션 정책을 위해 사용자별 최신 JTI만 whitelist로 보관
        tokenStore.saveTokenJti(TokenType.ACCESS, principal.userType(), principal.userId(), accessJti, properties.token().accessTimeToLive());
        tokenStore.saveTokenJti(TokenType.REFRESH, principal.userType(), principal.userId(), refreshJti, properties.token().refreshTimeToLive());

        return new LoginTokenPair(
            user.userId(),
            user.userType(),
            user.userName(),
            user.phoneNumber(),
            user.clientIp(),
            user.organization(),
            user.attributes(),
            user.requiredActions(),
            accessToken,
            refreshToken,
            now.plus(properties.token().accessTimeToLive()),
            now.plus(properties.token().refreshTimeToLive())
        );
    }

    /**
     * Refresh Token 검증 후 토큰 재발급
     *
     * @param refreshToken Refresh Token
     * @return 토큰 쌍
     */
    public LoginTokenPair refresh(String refreshToken) {
        Jwt jwt = decodeRefreshToken(refreshToken);
        LoginJwtClaims claims = validateRefreshToken(jwt);
        verifyAllowedUserType(claims.userType());
        String activeRefreshJti = tokenStore.getActiveTokenJti(claims.tokenType(), claims.userType(), claims.userId());
        // 로그아웃, 강제 인증 해제, 캐시 만료 등으로 활성 Refresh Token JTI가 없는 경우
        if (!StringUtils.hasText(activeRefreshJti)) {
            throw new MemberAuthenticationException(LoginJwtTokenValidator.TOKEN_LOGGED_OUT_MESSAGE);
        }
        // 다른 기기 로그인으로 활성 Refresh Token JTI가 교체된 경우
        if (!Objects.equals(activeRefreshJti, claims.jti())) {
            throw new MemberAuthenticationException(LoginJwtTokenValidator.TOKEN_REPLACED_MESSAGE);
        }

        LoginSessionUser user = loginUserInfoResolver.resolve(claims.userType(), claims.userId())
            .map(this::toSessionUser)
            .orElseThrow(() -> new MemberAuthenticationException("사용자 인증 정보를 조회할 수 없습니다."));
        return issue(user);
    }

    /**
     * 사용자 로그아웃 처리
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    public void logout(UserType userType, String userId) {
        deleteAuthentication(userType, userId);
    }

    /**
     * 사용자 인증 해제 처리
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    public void revokeAuthentication(UserType userType, String userId) {
        deleteAuthentication(userType, userId);
    }

    /**
     * 사용자 인증 정보 삭제
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    private void deleteAuthentication(UserType userType, String userId) {
        tokenStore.deleteTokens(userType, userId);
        loginUserInfoCacheService.delete(userType, userId);
    }

    /**
     * JWT 생성
     *
     * @param principal 로그인 Principal
     * @param jti JWT ID
     * @param tokenType 토큰 유형
     * @param issuedAt 발급 일시
     * @param expiresAt 만료 일시
     * @return JWT 문자열
     */
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

    /**
     * Refresh Token 디코딩
     *
     * @param refreshToken Refresh Token
     * @return JWT
     */
    private Jwt decodeRefreshToken(String refreshToken) {
        try {
            return jwtDecoder.decode(refreshToken);
        } catch (JwtException e) {
            // JWT 자체가 만료되어 decode/validator 단계에서 거부된 경우
            if (isExpiredToken(e)) {
                throw new MemberAuthenticationException(LoginJwtTokenValidator.TOKEN_EXPIRED_MESSAGE, e);
            }
            throw new MemberAuthenticationException(LoginJwtTokenValidator.TOKEN_LOGGED_OUT_MESSAGE, e);
        }
    }

    /**
     * Refresh Token 클레임 검증
     *
     * @param jwt JWT
     * @return 로그인 JWT 클레임
     */
    private LoginJwtClaims validateRefreshToken(Jwt jwt) {
        try {
            return loginJwtTokenValidator.validate(jwt, TokenType.REFRESH);
        } catch (MemberAuthenticationException e) {
            throw new MemberAuthenticationException(e.getMessage(), e);
        }
    }

    /**
     * JWT ID 생성
     *
     * @return JWT ID
     */
    private String createJti() {
        return UUID.randomUUID().toString();
    }

    /**
     * JWT 만료 예외 여부 확인
     *
     * @param e JWT 예외
     * @return 만료 예외 여부
     */
    private boolean isExpiredToken(JwtException e) {
        String message = e.getMessage();
        return message != null && message.toLowerCase().contains("expired");
    }

    /**
     * 사용자 정보 기준 로그인 세션 사용자 변환
     *
     * @param userInfo 사용자 정보
     * @return 로그인 세션 사용자
     */
    private LoginSessionUser toSessionUser(LoginUserInfo userInfo) {
        return new LoginSessionUser(
            userInfo.userId(),
            userInfo.userType(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            userInfo.clientIp(),
            userInfo.organization(),
            userInfo.attributes(),
            List.of()
        );
    }

    private void verifyAllowedUserType(UserType userType) {
        if (!jwtSecurityProperties.allows(userType)) {
            throw new MemberAuthenticationException("허용되지 않은 사용자 유형입니다.");
        }
    }

}
