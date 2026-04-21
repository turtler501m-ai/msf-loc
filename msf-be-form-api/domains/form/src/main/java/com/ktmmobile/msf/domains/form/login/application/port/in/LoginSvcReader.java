package com.ktmmobile.msf.domains.form.login.application.port.in;

import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;

public interface LoginSvcReader {

    LoginResponse login(LoginRequest request);
}
