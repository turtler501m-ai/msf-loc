package com.ktmmobile.msf.domains.form.login.application.port.out;

import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;

public interface LoginRepository {

    LoginResponse getUserInfo(LoginRequest request);

    LoginResponse getUserAppInfo(LoginRequest request);

    Integer updateLoginFail(LoginRequest request);

    Integer updateLoginSucc(LoginRequest request);

    Integer insertUserHistory(LoginRequest request);

    Integer updateBioLoginSucc(LoginRequest request);
}
