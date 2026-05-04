package com.ktmmobile.msf.domains.login.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.domains.login.application.dto.LoginPasswordChangeRequest;

public interface LoginPasswordChanger {

    LoginResult changePassword(LoginPasswordChangeRequest request);
}
