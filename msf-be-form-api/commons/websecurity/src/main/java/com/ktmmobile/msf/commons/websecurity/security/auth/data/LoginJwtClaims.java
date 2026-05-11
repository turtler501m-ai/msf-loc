package com.ktmmobile.msf.commons.websecurity.security.auth.data;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public record LoginJwtClaims(
    String userId,
    UserType userType,
    String jti,
    TokenType tokenType
) {
}
