package com.ktmmobile.msf.domains.form.login.application.port.out;

import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;
import com.ktmmobile.msf.domains.form.login.application.dto.PassChangeRequest;

public interface LoginRepository {

    LoginResponse getUserInfo(LoginRequest request);

    LoginResponse getUserAppInfo(LoginRequest request);

    Integer updateLoginFail(LoginRequest request);

    Integer updateLoginSucc(LoginRequest request);

    Integer insertUserHistory(String userId);

    Integer updateBioLoginSucc(LoginRequest request);

    Integer modifyPass(PassChangeRequest request);
}
