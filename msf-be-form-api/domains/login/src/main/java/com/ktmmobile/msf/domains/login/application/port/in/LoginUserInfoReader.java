package com.ktmmobile.msf.domains.login.application.port.in;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.domains.login.application.dto.LoginUserInfoResponse;

public interface LoginUserInfoReader {

    LoginUserInfoResponse getUserInfo(MsfUser authenticatedUser);
}
