package com.ktmmobile.msf.commons.logincore.application.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginActionRequired;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionReady;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionState;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorRequired;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorStatus;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

/**
 * 로그인 완료 전 임시 세션 상태를 캐시에 저장하고 변경하는 서비스
 */
@RequiredArgsConstructor
@Service
public class LoginSessionService {

    private final CacheService<StoredSession> cacheService;
    private final LoginCoreProperties properties;

    /**
     * 2FA 필요 로그인 세션 생성
     *
     * @param principal 로그인 세션 사용자
     * @return 2FA 필요 결과
     */
    public LoginTwoFactorRequired createTwoFactorSession(LoginSessionUser principal) {
        String loginSessionId = createLoginSessionId();
        return requireTwoFactor(loginSessionId, principal);
    }

    /**
     * 기존 로그인 세션을 2FA 필요 상태로 저장
     *
     * @param loginSessionId 로그인 세션 ID
     * @param principal 로그인 세션 사용자
     * @return 2FA 필요 결과
     */
    public LoginTwoFactorRequired requireTwoFactor(String loginSessionId, LoginSessionUser principal) {
        create(loginSessionId, principal, false);
        return toTwoFactorRequired(loginSessionId, principal, null);
    }

    /**
     * 2FA 조치가 제거된 로그인 세션 사용자 생성
     *
     * @param principal 로그인 세션 사용자
     * @return 2FA 조치 제거 사용자
     */
    private LoginSessionUser withoutVerifyTwoFactor(LoginSessionUser principal) {
        return new LoginSessionUser(
            principal.userId(),
            principal.userType(),
            principal.userName(),
            principal.phoneNumber(),
            principal.clientIp(),
            principal.organization(),
            principal.attributes(),
            principal.requiredActions().stream()
                .filter(action -> !action.isVerifyTwoFactor())
                .toList()
        );
    }

    /**
     * 필수 조치 필요 로그인 세션 생성
     *
     * @param principal 로그인 세션 사용자
     * @param requiredActions 필수 조치 목록
     * @return 필수 조치 필요 결과
     */
    public LoginActionRequired createActionSession(LoginSessionUser principal, List<LoginRequiredAction> requiredActions) {
        String loginSessionId = createLoginSessionId();
        create(loginSessionId, principal, true);
        return toActionRequired(loginSessionId, principal, requiredActions);
    }

    /**
     * 토큰 발급 가능 로그인 세션 생성
     *
     * @param principal 로그인 세션 사용자
     * @return 토큰 발급 가능 결과
     */
    public LoginSessionReady createReadySession(LoginSessionUser principal) {
        String loginSessionId = createLoginSessionId();
        create(loginSessionId, principal, true);
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

    /**
     * 로그인 세션 2FA 완료 처리
     *
     * @param loginSessionId 로그인 세션 ID
     */
    public void completeTwoFactor(String loginSessionId) {
        StoredSession session = get(loginSessionId);
        if (session.twoFactorCompleted()) {
            throw new LoginException("이미 추가 인증이 완료되었습니다.");
        }
        update(loginSessionId, session, withoutVerifyTwoFactor(session.principal()), true);
    }

    /**
     * 로그인 세션 상태 Optional 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 세션 상태
     */
    public Optional<LoginSessionState> findState(String loginSessionId) {
        StoredSession session = cacheService.getValue(sessionKey(loginSessionId));
        if (session == null) {
            return Optional.empty();
        }
        return Optional.of(new LoginSessionState(
            session.principal(),
            session.twoFactorCompleted()
        ));
    }

    /**
     * 로그인 세션 2FA 상태 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 2FA 상태
     */
    public LoginTwoFactorStatus getTwoFactorStatus(String loginSessionId) {
        return findState(loginSessionId)
            .map(state -> new LoginTwoFactorStatus(true, state.twoFactorCompleted()))
            .orElseGet(LoginTwoFactorStatus::notFound);
    }

    /**
     * 2FA 완료 로그인 세션 사용자 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 세션 사용자
     */
    public LoginSessionUser getVerifiedPrincipal(String loginSessionId) {
        StoredSession session = get(loginSessionId);
        if (!session.twoFactorCompleted()) {
            throw new LoginException("추가 인증이 완료되지 않았습니다.");
        }
        return session.principal();
    }

    /**
     * 로그인 세션 사용자 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 세션 사용자
     */
    public LoginSessionUser getPrincipal(String loginSessionId) {
        return get(loginSessionId).principal();
    }

    /**
     * 로그인 세션 상태 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 세션 상태
     */
    public LoginSessionState getState(String loginSessionId) {
        StoredSession session = get(loginSessionId);
        return new LoginSessionState(
            session.principal(),
            session.twoFactorCompleted()
        );
    }

    /**
     * 로그인 필수 조치 완료 처리
     *
     * @param loginSessionId 로그인 세션 ID
     * @param actionCode 조치 코드
     * @return 갱신된 로그인 세션 사용자
     */
    public LoginSessionUser completeAction(String loginSessionId, String actionCode) {
        if (LoginRequiredAction.VERIFY_TWO_FACTOR_CODE.equals(actionCode)) {
            throw new LoginException("추가 인증은 추가 인증 완료 메서드로 처리해야 합니다.");
        }
        StoredSession session = get(loginSessionId);
        // 완료된 조치를 제거하고 남은 requiredActions 기준으로 다음 진행 상태 계산
        List<LoginRequiredAction> remainingActions = session.principal().requiredActions().stream()
            .filter(action -> !action.code().equals(actionCode))
            .toList();
        LoginSessionUser principal = new LoginSessionUser(
            session.principal().userId(),
            session.principal().userType(),
            session.principal().userName(),
            session.principal().phoneNumber(),
            session.principal().clientIp(),
            session.principal().organization(),
            session.principal().attributes(),
            remainingActions
        );
        update(loginSessionId, session, principal, session.twoFactorCompleted());
        return principal;
    }

    /**
     * 필수 조치 필요 결과 변환
     *
     * @param loginSessionId 로그인 세션 ID
     * @param principal 로그인 세션 사용자
     * @param requiredActions 필수 조치 목록
     * @return 필수 조치 필요 결과
     */
    public LoginActionRequired toActionRequired(String loginSessionId, LoginSessionUser principal, List<LoginRequiredAction> requiredActions) {
        return new LoginActionRequired(
            loginSessionId,
            principal.userId(),
            principal.userType(),
            principal.userName(),
            principal.phoneNumber(),
            principal.clientIp(),
            principal.organization(),
            principal.attributes(),
            requiredActions
        );
    }

    /**
     * 로그인 세션 삭제
     *
     * @param loginSessionId 로그인 세션 ID
     */
    public void delete(String loginSessionId) {
        cacheService.delete(sessionKey(loginSessionId));
    }

    /**
     * 로그인 세션 캐시 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 저장 세션
     */
    private StoredSession get(String loginSessionId) {
        StoredSession session = cacheService.getValue(sessionKey(loginSessionId));
        if (session == null) {
            throw new LoginException("로그인 세션이 유효하지 않거나 유효시간이 종료되었습니다.");
        }
        return session;
    }

    /**
     * 로그인 세션 캐시 생성
     *
     * @param loginSessionId 로그인 세션 ID
     * @param principal 로그인 세션 사용자
     * @param twoFactorCompleted 2FA 완료 여부
     */
    private void create(String loginSessionId, LoginSessionUser principal, boolean twoFactorCompleted) {
        LocalDateTime now = now();
        save(loginSessionId, new StoredSession(principal, twoFactorCompleted, now, now));
    }

    /**
     * 로그인 세션 캐시 갱신
     *
     * @param loginSessionId 로그인 세션 ID
     * @param previous 이전 저장 세션
     * @param principal 로그인 세션 사용자
     * @param twoFactorCompleted 2FA 완료 여부
     */
    private void update(
        String loginSessionId,
        StoredSession previous,
        LoginSessionUser principal,
        boolean twoFactorCompleted
    ) {
        save(loginSessionId, new StoredSession(
            principal,
            twoFactorCompleted,
            previous.createdAt(),
            now()
        ));
    }

    /**
     * 로그인 세션 캐시 저장
     *
     * @param loginSessionId 로그인 세션 ID
     * @param session 저장 세션
     */
    private void save(String loginSessionId, StoredSession session) {
        // 로그인 세션은 토큰 발급 전까지만 유지되는 임시 상태
        cacheService.setValue(sessionKey(loginSessionId), session, properties.twoFactor().sessionTimeToLive());
    }

    /**
     * 현재 일시 조회
     *
     * @return 현재 일시
     */
    private LocalDateTime now() {
        return LocalDateTime.now(ZoneId.systemDefault()).withNano(0);
    }

    /**
     * 2FA 필요 결과 변환
     *
     * @param loginSessionId 로그인 세션 ID
     * @param principal 로그인 세션 사용자
     * @param expiresAt 만료 일시
     * @return 2FA 필요 결과
     */
    private LoginTwoFactorRequired toTwoFactorRequired(
        String loginSessionId,
        LoginSessionUser principal,
        LocalDateTime expiresAt
    ) {
        return new LoginTwoFactorRequired(
            loginSessionId,
            expiresAt,
            principal.userId(),
            principal.userType(),
            principal.userName(),
            principal.phoneNumber(),
            principal.clientIp(),
            principal.organization(),
            principal.attributes(),
            List.of(LoginRequiredAction.verifyTwoFactor())
        );
    }

    /**
     * 로그인 세션 ID 생성
     *
     * @return 로그인 세션 ID
     */
    private String createLoginSessionId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 로그인 세션 캐시 키 생성
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 세션 캐시 키
     */
    private String sessionKey(String loginSessionId) {
        return "login-session:" + loginSessionId;
    }

    /**
     * Redis에 저장되는 로그인 세션 스냅샷
     */
    public record StoredSession(
        boolean tokenIssuable,
        boolean twoFactorCompleted,
        LoginSessionUser principal,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {

        /**
         * 저장 세션 생성
         *
         * @param principal 로그인 세션 사용자
         * @param twoFactorCompleted 2FA 완료 여부
         * @param createdAt 생성 일시
         * @param updatedAt 수정 일시
         */
        public StoredSession(
            LoginSessionUser principal,
            boolean twoFactorCompleted,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
        ) {
            this(
                principal.requiredActions().stream().allMatch(LoginRequiredAction::tokenIssuable),
                twoFactorCompleted,
                principal,
                createdAt,
                updatedAt
            );
        }
    }
}
