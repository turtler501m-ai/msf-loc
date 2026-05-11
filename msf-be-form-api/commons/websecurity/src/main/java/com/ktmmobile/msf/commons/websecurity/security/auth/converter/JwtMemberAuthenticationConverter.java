package com.ktmmobile.msf.commons.websecurity.security.auth.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.data.entity.user.AdminUser;
import com.ktmmobile.msf.commons.common.data.entity.user.FormUser;
import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.LoginJwtClaims;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.TokenType;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.AdminUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.FormUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.MsfUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.exception.MemberAuthenticationException;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.AuthenticatedUserFinder;
import com.ktmmobile.msf.commons.websecurity.security.auth.service.LoginJwtTokenValidator;

@Component
public class JwtMemberAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final LoginJwtTokenValidator loginJwtTokenValidator;
    private final AuthenticatedUserFinder authenticatedUserFinder;

    public JwtMemberAuthenticationConverter(
        LoginJwtTokenValidator loginJwtTokenValidator,
        AuthenticatedUserFinder authenticatedUserFinder
    ) {
        this.loginJwtTokenValidator = loginJwtTokenValidator;
        this.authenticatedUserFinder = authenticatedUserFinder;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        LoginJwtClaims claims = loginJwtTokenValidator.validateActive(jwt, TokenType.ACCESS);
        MsfUser user = findUser(claims.userType(), claims.userId());
        MsfUserDetails userDetails = createUserDetails(user);

        return new UsernamePasswordAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
    }

    private MsfUser findUser(UserType userType, String userId) {
        return authenticatedUserFinder.findUser(userType, userId)
            .orElseThrow(() -> new MemberAuthenticationException("인증 사용자 정보를 조회할 수 없습니다."));
    }

    private MsfUserDetails createUserDetails(MsfUser user) {
        if (user instanceof FormUser formUser) {
            return new FormUserDetails(formUser);
        }
        if (user instanceof AdminUser adminUser) {
            return new AdminUserDetails(adminUser);
        }
        throw new MemberAuthenticationException("JWT userType claim is invalid");
    }
}
