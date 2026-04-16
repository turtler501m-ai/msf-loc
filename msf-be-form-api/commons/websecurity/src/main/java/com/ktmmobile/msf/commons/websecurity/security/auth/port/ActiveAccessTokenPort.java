package com.ktmmobile.msf.commons.websecurity.security.auth.port;

public interface ActiveAccessTokenPort {

    boolean exists(String jti);
}
