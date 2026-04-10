package com.ktmmobile.msf.commons.login.application.port.in;

import com.ktmmobile.msf.commons.login.domain.dto.LoginTokens;

public interface LoginTokenRefresher {

    LoginTokens refresh(String refreshToken);
}
