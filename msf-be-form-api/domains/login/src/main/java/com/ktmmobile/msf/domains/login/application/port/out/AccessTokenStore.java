package com.ktmmobile.msf.domains.login.application.port.out;

import java.time.Duration;

public interface AccessTokenStore {

    void saveAccessToken(String jti, String userId, Duration timeToLive);

    boolean existsAccessToken(String jti);

    void deleteAccessToken(String jti);
}
