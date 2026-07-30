package com.ktmmobile.msf.commons.logincore.application.port.in;

import java.util.Optional;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCompletionResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorStatus;

/**
 * 로그인 세션 기반 후속 인증과 토큰 발급 처리 포트
 */
public interface LoginSessionFlowProcessor {

    /**
     * 외부 2FA 검증 완료 후 로그인 세션의 2FA 완료 상태 반영
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 2FA 완료 결과
     */
    LoginTwoFactorCompletionResult completeTwoFactor(String loginSessionId);

    /**
     * 비밀번호 변경, 단말 인증 등 필수 조치 완료 반영
     *
     * @param loginSessionId 로그인 세션 ID
     * @param actionCode 완료된 조치 코드
     * @return 로그인 진행 결과
     */
    LoginResult completeAction(String loginSessionId, String actionCode);

    /**
     * 로그인 세션의 현재 조건을 재평가하고 다음 진행 상태 반환
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 진행 결과
     */
    LoginResult resume(String loginSessionId);

    /**
     * 로그인 세션 진행 상태 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 진행 결과
     */
    LoginResult getSessionProgress(String loginSessionId);

    /**
     * 로그인 세션 진행 상태 Optional 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 진행 결과 Optional
     */
    Optional<LoginResult> findSessionProgress(String loginSessionId);

    /**
     * 로그인 세션 존재 여부와 2FA 완료 여부 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 2FA 상태
     */
    LoginTwoFactorStatus getTwoFactorStatus(String loginSessionId);

    /**
     * 로그인 세션에 저장된 사용자 정보 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 세션 사용자
     */
    LoginSessionUser getSessionUser(String loginSessionId);

    /**
     * 검증 완료된 사용자 정보 기반 토큰 발급
     *
     * @param principal 로그인 세션 사용자
     * @return 로그인 진행 결과
     */
    LoginResult issue(LoginSessionUser principal);

    /**
     * 로그인 세션 ID 기반 토큰 발급
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 토큰 쌍
     */
    LoginTokenPair issue(String loginSessionId);

    /**
     * Refresh Token 기반 토큰 재발급
     *
     * @param refreshToken Refresh Token
     * @return 토큰 쌍
     */
    LoginTokenPair refresh(String refreshToken);

    /**
     * 로그아웃 처리
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    void logout(UserType userType, String userId);
}
