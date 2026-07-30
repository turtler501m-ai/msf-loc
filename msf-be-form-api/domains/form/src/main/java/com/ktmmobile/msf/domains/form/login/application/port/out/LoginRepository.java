package com.ktmmobile.msf.domains.form.login.application.port.out;

import com.ktmmobile.msf.domains.form.login.application.dto.LoginRequest;
import com.ktmmobile.msf.domains.form.login.application.dto.LoginResponse;
import com.ktmmobile.msf.domains.form.login.application.dto.PassChangeRequest;

public interface LoginRepository {

    LoginResponse getUserInfo(LoginRequest request);

    Integer insertUserHistory(String userId);

    Integer modifyPass(PassChangeRequest request);

    LoginResponse getUserHstInfo(LoginRequest loginRequest);

    Integer insertAdminUserHistory(String strUserId);

    Integer modifyAdminPass(PassChangeRequest request);
}
