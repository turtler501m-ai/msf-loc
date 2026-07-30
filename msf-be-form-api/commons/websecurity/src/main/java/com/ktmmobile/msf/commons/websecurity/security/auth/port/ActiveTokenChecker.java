package com.ktmmobile.msf.commons.websecurity.security.auth.port;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.websecurity.security.auth.data.TokenType;

public interface ActiveTokenChecker {

    String getActiveTokenJti(TokenType tokenType, UserType userType, String userId);

    boolean exists(TokenType tokenType, UserType userType, String userId, String jti);
}
