package com.ktmmobile.msf.commons.logincore.application.port.out;

import java.util.Optional;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;

/**
 * 앱별 사용자 조회와 인증 전후 검증을 담당하는 아웃바운드 포트
 *
 * @param <C> 앱별 로그인 credential 타입
 */
public interface LoginUserFinder<C extends LoginAuthenticationCredential> {

    /**
     * 로그인 credential 기반 최소 사용자 정보 조회
     *
     * @param credential 로그인 credential
     * @return 인증 정책 검증에 필요한 사용자 정보
     */
    Optional<LoginUser> findByCredential(C credential);

    /**
     * 로그인 성공 직후 세션과 캐시에 저장할 사용자 상세 정보 조회
     *
     * @param user 인증 대상 사용자
     * @param credential 로그인 credential
     * @return 사용자 상세 정보
     */
    default Optional<LoginUserInfo> findUserInfo(LoginUser user, C credential) {
        return Optional.of(LoginUserInfo.of(user, credential.userType()));
    }

    /**
     * 로그인 세션 또는 토큰 갱신 흐름에서 사용할 사용자 상세 정보 재조회
     *
     * @param sessionUser 로그인 세션 사용자
     * @return 사용자 상세 정보
     */
    default Optional<LoginUserInfo> findUserInfo(LoginSessionUser sessionUser) {
        return Optional.empty();
    }

    /**
     * 토큰 발급 전 추가 검증
     *
     * @param user 인증 대상 사용자
     * @param credential 로그인 credential
     */
    void verifyAuthenticatedUser(LoginUser user, C credential);
}
