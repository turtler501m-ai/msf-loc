package com.ktmmobile.msf.domains.form.login.domain.strategy;

import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;

public interface AuthStrategy {
    boolean supports(String authType);
    LoginResponse authenticate(LoginRequest request);
}
