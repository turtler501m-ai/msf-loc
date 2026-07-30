package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.context.LoginContextHolder;
import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginAuthenticationInvalidator;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginFlowProcessor;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginAuthenticationRecorder;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginAuthenticator;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionReady;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionState;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenIssued;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCompletionResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorRequired;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorStatus;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;
import com.ktmmobile.msf.commons.logincore.support.context.LoginSessionContext;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTraceRequestAttributes;

/**
 * 로그인 시작, 세션 진행, 토큰 발급, 로그아웃을 조율하는 login-core 대표 서비스
 *
 * @param <C> 앱별 로그인 credential 타입
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoginCoreService<C extends LoginAuthenticationCredential> implements LoginFlowProcessor<C>, LoginAuthenticationInvalidator {

    private final LoginAuthenticator<C> loginAuthenticator;
    private final LoginUserFinder<C> loginUserFinder;
    private final LoginAuthenticationRecorder<C> loginAuthenticationRecorder;
    private final LoginTokenService loginTokenService;
    private final LoginSessionService loginSessionService;
    private final LoginUserInfoCacheService loginUserInfoCacheService;

    /**
     * ID/PW 로그인 흐름 시작
     *
     * @param credential 로그인 인증 정보
     * @return 로그인 진행 결과
     */
    @Override
    public LoginResult loginWithIdPw(C credential) {
        return loginAuthenticator.authenticate(credential);
    }

    /**
     * 생체인증 로그인 흐름 시작
     *
     * @param credential 로그인 인증 정보
     * @return 로그인 진행 결과
     */
    @Override
    public LoginResult loginWithBiometric(C credential) {
        return loginAuthenticator.authenticate(credential);
    }

    /**
     * 로그인 세션 2FA 완료 처리
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 2FA 완료 결과
     */
    @LoginSessionContext
    @Override
    public LoginTwoFactorCompletionResult completeTwoFactor(String loginSessionId) {
        loginSessionService.completeTwoFactor(loginSessionId);
        log.info("Login 2FA completion updated. loginSessionId={}", mask(loginSessionId));
        return new LoginTwoFactorCompletionResult(loginSessionId, true);
    }

    /**
     * 로그인 필수 조치 완료 처리
     *
     * @param loginSessionId 로그인 세션 ID
     * @param actionCode 조치 코드
     * @return 다음 로그인 진행 결과
     */
    @LoginSessionContext
    @Override
    public LoginResult completeAction(String loginSessionId, String actionCode) {
        // 조치 완료 후 앱별 DB를 다시 조회하여 세션 사용자 정보를 최신 상태로 갱신
        LoginSessionUser principal = reload(loginSessionService.completeAction(loginSessionId, actionCode));
        loginUserInfoCacheService.save(principal);
        return next(loginSessionId, principal);
    }

    /**
     * 로그인 세션 재개 처리
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 진행 결과
     */
    @LoginSessionContext
    @Override
    public LoginResult resume(String loginSessionId) {
        // 로그인 중간 화면 재진입 시 캐시 정보를 병합하고 다음 진행 상태 재계산
        LoginSessionUser principal = enrich(loginSessionService.getVerifiedPrincipal(loginSessionId));
        loginUserInfoCacheService.save(principal);
        LoginResult result = next(loginSessionId, principal);
        log.info(
            "Login session resumed. loginSessionId={}, userId={}, result={}",
            mask(loginSessionId),
            principal.userId(),
            resultName(result)
        );
        return result;
    }

    /**
     * 로그인 세션 진행 상태 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 진행 결과
     */
    @Override
    public LoginResult getSessionProgress(String loginSessionId) {
        LoginSessionState state = loginSessionService.getState(loginSessionId);
        return toSessionProgress(loginSessionId, state);
    }

    /**
     * 로그인 세션 진행 상태 Optional 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 진행 결과
     */
    @Override
    public Optional<LoginResult> findSessionProgress(String loginSessionId) {
        return loginSessionService.findState(loginSessionId)
            .map(state -> toSessionProgress(loginSessionId, state));
    }

    /**
     * 로그인 세션 2FA 상태 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 2FA 상태
     */
    @Override
    public LoginTwoFactorStatus getTwoFactorStatus(String loginSessionId) {
        return loginSessionService.getTwoFactorStatus(loginSessionId);
    }

    /**
     * 로그인 세션 사용자 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 세션 사용자
     */
    @Override
    public LoginSessionUser getSessionUser(String loginSessionId) {
        LoginSessionUser principal = enrich(loginSessionService.getPrincipal(loginSessionId));
        loginUserInfoCacheService.save(principal);
        return principal;
    }

    /**
     * 로그인 세션 사용자 기준 토큰 발급
     *
     * @param principal 로그인 세션 사용자
     * @return 로그인 진행 결과
     */
    @Override
    public LoginResult issue(LoginSessionUser principal) {
        return LoginContextHolder.withUserId(principal.userId(), () -> {
            LoginTokenPair tokenPair = loginTokenService.issue(principal);
            loginAuthenticationRecorder.recordAccessTokenIssueSuccess(principal);
            log.info(
                "Login token issued. userId={}, accessTokenExpiresAt={}, refreshTokenExpiresAt={}",
                principal.userId(),
                tokenPair.accessTokenExpiresAt(),
                tokenPair.refreshTokenExpiresAt()
            );
            return new LoginTokenIssued(tokenPair);
        });
    }

    /**
     * 로그인 세션 ID 기준 토큰 발급
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 토큰 쌍
     */
    @LoginSessionContext
    @Override
    public LoginTokenPair issue(String loginSessionId) {
        log.info("Login token issue requested. loginSessionId={}", mask(loginSessionId));
        LoginSessionUser principal = enrich(loginSessionService.getVerifiedPrincipal(loginSessionId));
        AccessTraceRequestAttributes.setUserId(principal.userId());
        List<LoginRequiredAction> actionsBeforeTokenIssue = actionsBeforeTokenIssue(principal);
        // 토큰 발급 전 필수 조치가 하나라도 남아 있으면 Access Token 발급 차단
        if (!actionsBeforeTokenIssue.isEmpty()) {
            log.info(
                "Login token issue blocked. loginSessionId={}, userId={}, remainingActions={}",
                mask(loginSessionId),
                principal.userId(),
                actionsBeforeTokenIssue.stream().map(LoginRequiredAction::code).toList()
            );
            throw new LoginException("로그인 완료 전 필수 조치가 남아 있습니다.");
        }
        LoginTokenPair tokenPair = loginTokenService.issue(principal);
        loginAuthenticationRecorder.recordAccessTokenIssueSuccess(principal);
        loginSessionService.delete(loginSessionId);
        loginUserInfoCacheService.save(principal);
        log.info(
            "Login token issue completed. loginSessionId={}, userId={}, accessTokenExpiresAt={}, refreshTokenExpiresAt={}",
            mask(loginSessionId),
            principal.userId(),
            tokenPair.accessTokenExpiresAt(),
            tokenPair.refreshTokenExpiresAt()
        );
        return tokenPair;
    }

    /**
     * Refresh Token 기반 토큰 재발급
     *
     * @param refreshToken Refresh Token
     * @return 토큰 쌍
     */
    @Override
    public LoginTokenPair refresh(String refreshToken) {
        return loginTokenService.refresh(refreshToken);
    }

    /**
     * 사용자 로그아웃 처리
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    @Override
    public void logout(UserType userType, String userId) {
        loginTokenService.logout(userType, userId);
    }

    /**
     * 사용자 인증 해제 처리
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    @Override
    public void revokeAuthentication(UserType userType, String userId) {
        loginTokenService.revokeAuthentication(userType, userId);
    }

    /**
     * 사용자 정보 캐시 삭제
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    @Override
    public void evictUserInfoCache(UserType userType, String userId) {
        loginUserInfoCacheService.delete(userType, userId);
    }

    /**
     * 로그인 세션 상태를 진행 결과로 변환
     *
     * @param loginSessionId 로그인 세션 ID
     * @param state 로그인 세션 상태
     * @return 로그인 진행 결과
     */
    private LoginResult toSessionProgress(String loginSessionId, LoginSessionState state) {
        LoginSessionUser principal = enrich(state.principal());
        loginUserInfoCacheService.save(principal);
        return current(loginSessionId, state, principal);
    }

    /**
     * 다음 로그인 진행 결과 계산
     *
     * @param loginSessionId 로그인 세션 ID
     * @param principal 로그인 세션 사용자
     * @return 다음 로그인 진행 결과
     */
    private LoginResult next(String loginSessionId, LoginSessionUser principal) {
        List<LoginRequiredAction> actionsBeforeTokenIssue = actionsBeforeTokenIssue(principal);
        // 필수 조치가 모두 끝난 상태는 토큰 발급 가능한 ready 상태로 응답
        if (actionsBeforeTokenIssue.isEmpty()) {
            return new LoginSessionReady(
                loginSessionId,
                principal.userId(),
                principal.userType(),
                principal.userName(),
                principal.phoneNumber(),
                principal.clientIp(),
                principal.organization(),
                principal.attributes()
            );
        }
        // 2FA는 별도 API/서비스가 완료 여부를 업데이트하므로 별도 응답 타입 사용
        if (actionsBeforeTokenIssue.getFirst().isVerifyTwoFactor()) {
            return loginSessionService.requireTwoFactor(loginSessionId, principal);
        }
        return loginSessionService.toActionRequired(loginSessionId, principal, actionsBeforeTokenIssue);
    }

    /**
     * 현재 로그인 진행 결과 계산
     *
     * @param loginSessionId 로그인 세션 ID
     * @param state 로그인 세션 상태
     * @param principal 로그인 세션 사용자
     * @return 현재 로그인 진행 결과
     */
    private LoginResult current(String loginSessionId, LoginSessionState state, LoginSessionUser principal) {
        List<LoginRequiredAction> actionsBeforeTokenIssue = actionsBeforeTokenIssue(principal);
        // 상태 조회 API에서는 현재 세션의 2FA 완료 여부를 기준으로 응답 타입 결정
        if (actionsBeforeTokenIssue.isEmpty()) {
            return new LoginSessionReady(
                loginSessionId,
                principal.userId(),
                principal.userType(),
                principal.userName(),
                principal.phoneNumber(),
                principal.clientIp(),
                principal.organization(),
                principal.attributes()
            );
        }
        if (!state.twoFactorCompleted() && actionsBeforeTokenIssue.getFirst().isVerifyTwoFactor()) {
            return new LoginTwoFactorRequired(
                loginSessionId,
                null,
                principal.userId(),
                principal.userType(),
                principal.userName(),
                principal.phoneNumber(),
                principal.clientIp(),
                principal.organization(),
                principal.attributes(),
                actionsBeforeTokenIssue
            );
        }
        return loginSessionService.toActionRequired(loginSessionId, principal, actionsBeforeTokenIssue);
    }

    /**
     * 토큰 발급 전 필수 조치 목록 추출
     *
     * @param principal 로그인 세션 사용자
     * @return 토큰 발급 전 필수 조치 목록
     */
    private List<LoginRequiredAction> actionsBeforeTokenIssue(LoginSessionUser principal) {
        return principal.requiredActions().stream()
            .filter(action -> !action.tokenIssuable())
            .toList();
    }

    /**
     * 캐시 정보 기준 로그인 세션 사용자 보강
     *
     * @param principal 로그인 세션 사용자
     * @return 보강된 로그인 세션 사용자
     */
    private LoginSessionUser enrich(LoginSessionUser principal) {
        // 일반 API 인증 후 사용자 정보 보강은 캐시 우선 사용
        return loginUserInfoCacheService.get(principal.userType(), principal.userId())
            .map(userInfo -> merge(principal, userInfo))
            .orElse(principal);
    }

    /**
     * 앱별 저장소 기준 로그인 세션 사용자 재조회
     *
     * @param principal 로그인 세션 사용자
     * @return 재조회된 로그인 세션 사용자
     */
    private LoginSessionUser reload(LoginSessionUser principal) {
        // 로그인 조치 완료 직후에는 앱별 저장소에서 사용자 정보를 다시 읽어 세션 갱신
        return loginUserFinder.findUserInfo(principal)
            .map(userInfo -> merge(principal, userInfo))
            .orElseGet(() -> enrich(principal));
    }

    /**
     * 로그인 세션 사용자와 사용자 정보 병합
     *
     * @param principal 로그인 세션 사용자
     * @param userInfo 사용자 정보
     * @return 병합된 로그인 세션 사용자
     */
    private LoginSessionUser merge(LoginSessionUser principal, LoginUserInfo userInfo) {
        return new LoginSessionUser(
            principal.userId(),
            principal.userType(),
            coalesce(userInfo.userName(), principal.userName()),
            coalesce(userInfo.phoneNumber(), principal.phoneNumber()),
            coalesce(userInfo.clientIp(), principal.clientIp()),
            userInfo.organization(),
            mergeAttributes(principal, userInfo),
            principal.requiredActions()
        );
    }

    /**
     * 로그인 세션 속성과 사용자 정보 속성 병합
     *
     * @param principal 로그인 세션 사용자
     * @param userInfo 사용자 정보
     * @return 병합 속성
     */
    private java.util.Map<String, Object> mergeAttributes(LoginSessionUser principal, LoginUserInfo userInfo) {
        java.util.Map<String, Object> attributes = new java.util.LinkedHashMap<>(principal.attributes());
        attributes.putAll(userInfo.attributes());
        return attributes;
    }

    /**
     * 우선 값 선택
     *
     * @param primary 우선 값
     * @param fallback 대체 값
     * @return 선택 값
     */
    private String coalesce(String primary, String fallback) {
        return primary == null ? fallback : primary;
    }

    /**
     * 로그인 결과 클래스명 조회
     *
     * @param result 로그인 결과
     * @return 결과 클래스명
     */
    private String resultName(LoginResult result) {
        return result.getClass().getSimpleName();
    }

    /**
     * 로그 출력용 문자열 마스킹
     *
     * @param value 원본 문자열
     * @return 마스킹 문자열
     */
    private String mask(String value) {
        if (value == null || value.length() <= 8) {
            return "****";
        }
        return value.substring(0, 8) + "****";
    }
}
