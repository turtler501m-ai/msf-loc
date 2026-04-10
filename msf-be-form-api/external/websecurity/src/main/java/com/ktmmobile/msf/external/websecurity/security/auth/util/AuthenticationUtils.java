package com.ktmmobile.msf.external.websecurity.security.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.ktmmobile.msf.external.websecurity.security.auth.data.JwtAuthenticatedMember;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationUtils {

    public static JwtAuthenticatedMember getAuthenticatedMember() {
        return getAuthenticatedMember(getAuthentication());
    }

    public static String getUserId() {
        return getAuthenticatedMember().getUserId();
    }

    public static JwtAuthenticatedMember getAuthenticatedMemberOrNull() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        return authentication == null ? null : getAuthenticatedMember(authentication);
    }

    private static JwtAuthenticatedMember getAuthenticatedMember(Authentication authentication) {
        if (authentication.getPrincipal() instanceof JwtAuthenticatedMember member) {
            return member;
        }
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return toAuthenticatedMember(jwtAuthenticationToken.getToken());
        }
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return toAuthenticatedMember(jwt);
        }

        throw new AuthenticationCredentialsNotFoundException("인증 객체를 조회할 수 없습니다.");
    }

    private static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        if (authentication != null) {
            return authentication;
        }
        throw new AuthenticationCredentialsNotFoundException("인증 객체를 조회할 수 없습니다.");
    }

    private static JwtAuthenticatedMember toAuthenticatedMember(Jwt jwt) {
        Integer userKey = jwt.getClaims().containsKey("memberKey")
            ? jwt.getClaim("memberKey")
            : jwt.getClaims().containsKey("member_key") ? jwt.getClaim("member_key") : null;

        return new JwtAuthenticatedMember(
            userKey == null ? 0 : userKey,
            jwt.getSubject(),
            jwt.getClaimAsString("email")
        );
    }
}
