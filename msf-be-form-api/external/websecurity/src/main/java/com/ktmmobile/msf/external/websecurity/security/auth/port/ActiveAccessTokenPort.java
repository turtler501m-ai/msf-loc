package com.ktmmobile.msf.external.websecurity.security.auth.port;

public interface ActiveAccessTokenPort {

    boolean exists(String jti);
}
