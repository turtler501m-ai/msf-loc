package com.ktmmobile.msf.commons.login.application.port.out;

import java.time.Duration;

public interface RefreshTokenStore {

    void saveRefreshToken(String userId, String refreshToken, Duration timeToLive);

    String getRefreshToken(String userId);

    void deleteRefreshToken(String userId);
}
