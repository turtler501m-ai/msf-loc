package com.ktmmobile.msf.domains.login.application.port.in;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.domains.login.application.dto.LoginUserInfoResponse;

public interface LoginUserInfoReader {

    /**
     * 인증 사용자 정보 조회
     *
     * @param authenticatedUser 인증 사용자
     * @return 사용자 정보 응답
     */
    LoginUserInfoResponse getUserInfo(MsfUser authenticatedUser);
}
