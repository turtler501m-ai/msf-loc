package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.common.data.type.UserType;

/**
 * 로그인 인증 상태와 사용자 정보 캐시 무효화 처리 포트
 */
public interface LoginAuthenticationInvalidator {

    /**
     * 사용자 인증 정보 강제 폐기
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    void revokeAuthentication(UserType userType, String userId);

    /**
     * 사용자 정보 캐시 삭제
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    void evictUserInfoCache(UserType userType, String userId);
}
