package com.ktmmobile.msf.commons.websecurity.security.auth.port;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public interface ActiveAccessTokenPort {

    boolean exists(UserType userType, String userId, String jti);
}
