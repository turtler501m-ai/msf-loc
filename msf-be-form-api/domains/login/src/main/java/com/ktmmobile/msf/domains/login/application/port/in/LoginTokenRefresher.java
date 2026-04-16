package com.ktmmobile.msf.domains.login.application.port.in;

import com.ktmmobile.msf.domains.login.domain.dto.LoginTokens;

public interface LoginTokenRefresher {

    LoginTokens refresh(String refreshToken);
}
