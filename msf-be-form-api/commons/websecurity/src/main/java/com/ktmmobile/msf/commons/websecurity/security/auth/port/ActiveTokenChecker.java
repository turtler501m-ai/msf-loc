package com.ktmmobile.msf.commons.websecurity.security.auth.port;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.TokenType;

public interface ActiveTokenChecker {

    boolean exists(TokenType tokenType, UserType userType, String userId, String jti);
}
