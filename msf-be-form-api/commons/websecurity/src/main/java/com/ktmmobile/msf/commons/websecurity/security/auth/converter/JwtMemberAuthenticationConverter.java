package com.ktmmobile.msf.commons.websecurity.security.auth.converter;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.entity.user.AdminUser;
import com.ktmmobile.msf.commons.common.data.entity.user.FormUser;
import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.AdminUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.FormUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.memberdetails.MsfUserDetails;
import com.ktmmobile.msf.commons.websecurity.security.auth.exception.MemberAuthenticationException;
import com.ktmmobile.msf.commons.websecurity.security.auth.port.ActiveAccessTokenPort;

@Component
public class JwtMemberAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final ActiveAccessTokenPort activeAccessTokenPort;

    public JwtMemberAuthenticationConverter(ObjectProvider<ActiveAccessTokenPort> activeAccessTokenPortProvider) {
        Assert.notNull(activeAccessTokenPortProvider, "activeAccessTokenPortProvider is required");
        this.activeAccessTokenPort = activeAccessTokenPortProvider.getIfAvailable();
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String memberId = jwt.getSubject();
        String jti = jwt.getId();
        String type = jwt.getClaimAsString("type");
        UserType userType = UserType.valueOfCode(jwt.getClaimAsString("userType"));
        String agentCode = jwt.getClaimAsString("agentCode");
        String shopCode = jwt.getClaimAsString("shopCode");

        if (!StringUtils.hasText(memberId)) {
            throw new MemberAuthenticationException("JWT subject(sub) claim is required");
        }
        if (!StringUtils.hasText(jti)) {
            throw new MemberAuthenticationException("JWT jti claim is required");
        }
        if (!"access".equals(type)) {
            throw new MemberAuthenticationException("AccessToken이 아닙니다.");
        }
        if (!userType.isValid()) {
            throw new MemberAuthenticationException("JWT userType claim is invalid");
        }
        if (activeAccessTokenPort != null && !activeAccessTokenPort.exists(userType, memberId, jti)) {
            throw new MemberAuthenticationException("AccessToken이 유효하지 않습니다.");
        }

        MsfUserDetails userDetails = createUserDetails(memberId, userType, agentCode, shopCode);
        return new UsernamePasswordAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
    }

    private MsfUserDetails createUserDetails(String userId, UserType userType, String agentCode, String shopCode) {
        if (userType.isFormUser()) {
            return new FormUserDetails(new FormUser(userId, null, userType, agentCode, shopCode));
        }
        if (userType.isAdminUser()) {
            return new AdminUserDetails(new AdminUser(userId, null, userType));
        }
        throw new MemberAuthenticationException("JWT userType claim is invalid");
    }
}
