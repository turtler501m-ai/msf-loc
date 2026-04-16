package com.ktmmobile.msf.commons.websecurity.security.auth.data.header;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UserType;

@Slf4j
public record IdRoleAuthorizationHeader(
    String id,
    UserType role
) {

    public static final String AUTHORIZATION_ID = "X-Authorization-Id";
    public static final String AUTHORIZATION_ROLE = "X-Authorization-Role";

    private static final String ATTRIBUTE_NAME = "authorizationHeader";

    public static IdRoleAuthorizationHeader getFromAttributeOrCreate(HttpServletRequest request) {
        IdRoleAuthorizationHeader authHeader = (IdRoleAuthorizationHeader) request.getAttribute(ATTRIBUTE_NAME);
        if (authHeader == null) {
            authHeader = IdRoleAuthorizationHeader.of(request);
            request.setAttribute(ATTRIBUTE_NAME, authHeader);
        }
        return authHeader;
    }

    public static IdRoleAuthorizationHeader of(HttpServletRequest request) {
        String id = request.getHeader(AUTHORIZATION_ID);
        String role = request.getHeader(AUTHORIZATION_ROLE);
        UserType userType = UserType.valueOfCode(role);
        return new IdRoleAuthorizationHeader(id, userType);
    }

    public boolean isValid() {
        return StringUtils.hasText(this.id)
            && this.role.isValid();
    }
}
