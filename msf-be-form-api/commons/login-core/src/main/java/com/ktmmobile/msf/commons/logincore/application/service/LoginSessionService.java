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

@RequiredArgsConstructor
@Service
public class LoginSessionService {

    private final CacheService<StoredSession> cacheService;
    private final LoginCoreProperties properties;

    public LoginTwoFactorRequired createTwoFactorSession(LoginSessionUser principal) {
        String loginSessionId = createLoginSessionId();
        return requireTwoFactor(loginSessionId, principal);
    }

    public LoginTwoFactorRequired requireTwoFactor(String loginSessionId, LoginSessionUser principal) {
        create(loginSessionId, principal, false);
        return toTwoFactorRequired(loginSessionId, principal, null);
    }

    private LoginSessionUser withoutVerifyTwoFactor(LoginSessionUser principal) {
        return new LoginSessionUser(
            principal.userId(),
            principal.userType(),
            principal.userName(),
            principal.phoneNumber(),
            principal.clientIp(),
            principal.agentCode(),
            principal.agentName(),
            principal.shopCode(),
            principal.shopName(),
            principal.attributes(),
            principal.requiredActions().stream()
                .filter(action -> !action.isVerifyTwoFactor())
                .toList()
        );
    }

    public LoginActionRequired createActionSession(LoginSessionUser principal, List<LoginRequiredAction> requiredActions) {
        String loginSessionId = createLoginSessionId();
        create(loginSessionId, principal, true);
        return toActionRequired(loginSessionId, principal, requiredActions);
    }

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
            principal.agentCode(),
            principal.agentName(),
            principal.shopCode(),
            principal.shopName(),
            principal.attributes()
        );
    }

    public void completeTwoFactor(String loginSessionId) {
        StoredSession session = get(loginSessionId);
        if (session.twoFactorCompleted()) {
            throw new LoginException("이미 추가 인증이 완료되었습니다.");
        }
        update(loginSessionId, session, withoutVerifyTwoFactor(session.principal()), true);
    }

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

    public LoginTwoFactorStatus getTwoFactorStatus(String loginSessionId) {
        return findState(loginSessionId)
            .map(state -> new LoginTwoFactorStatus(true, state.twoFactorCompleted()))
            .orElseGet(LoginTwoFactorStatus::notFound);
    }

    public LoginSessionUser getVerifiedPrincipal(String loginSessionId) {
        StoredSession session = get(loginSessionId);
        if (!session.twoFactorCompleted()) {
            throw new LoginException("추가 인증이 완료되지 않았습니다.");
        }
        return session.principal();
    }

    public LoginSessionUser getPrincipal(String loginSessionId) {
        return get(loginSessionId).principal();
    }

    public LoginSessionState getState(String loginSessionId) {
        StoredSession session = get(loginSessionId);
        return new LoginSessionState(
            session.principal(),
            session.twoFactorCompleted()
        );
    }

    public LoginSessionUser completeAction(String loginSessionId, String actionCode) {
        if (LoginRequiredAction.VERIFY_TWO_FACTOR_CODE.equals(actionCode)) {
            throw new LoginException("추가 인증은 추가 인증 완료 메서드로 처리해야 합니다.");
        }
        StoredSession session = get(loginSessionId);
        List<LoginRequiredAction> remainingActions = session.principal().requiredActions().stream()
            .filter(action -> !action.code().equals(actionCode))
            .toList();
        LoginSessionUser principal = new LoginSessionUser(
            session.principal().userId(),
            session.principal().userType(),
            session.principal().userName(),
            session.principal().phoneNumber(),
            session.principal().clientIp(),
            session.principal().agentCode(),
            session.principal().agentName(),
            session.principal().shopCode(),
            session.principal().shopName(),
            session.principal().attributes(),
            remainingActions
        );
        update(loginSessionId, session, principal, session.twoFactorCompleted());
        return principal;
    }

    public LoginActionRequired toActionRequired(String loginSessionId, LoginSessionUser principal, List<LoginRequiredAction> requiredActions) {
        return new LoginActionRequired(
            loginSessionId,
            principal.userId(),
            principal.userType(),
            principal.userName(),
            principal.phoneNumber(),
            principal.clientIp(),
            principal.agentCode(),
            principal.agentName(),
            principal.shopCode(),
            principal.shopName(),
            principal.attributes(),
            requiredActions
        );
    }

    public void delete(String loginSessionId) {
        cacheService.delete(sessionKey(loginSessionId));
    }

    private StoredSession get(String loginSessionId) {
        StoredSession session = cacheService.getValue(sessionKey(loginSessionId));
        if (session == null) {
            throw new LoginException("로그인 세션이 유효하지 않거나 유효시간이 종료되었습니다.");
        }
        return session;
    }

    private void create(String loginSessionId, LoginSessionUser principal, boolean twoFactorCompleted) {
        LocalDateTime now = now();
        save(loginSessionId, new StoredSession(principal, twoFactorCompleted, now, now));
    }

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

    private void save(String loginSessionId, StoredSession session) {
        cacheService.setValue(sessionKey(loginSessionId), session, properties.twoFactor().sessionTimeToLive());
    }

    private LocalDateTime now() {
        return LocalDateTime.now(ZoneId.systemDefault()).withNano(0);
    }

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
            principal.agentCode(),
            principal.agentName(),
            principal.shopCode(),
            principal.shopName(),
            principal.attributes(),
            List.of(LoginRequiredAction.verifyTwoFactor())
        );
    }

    private String createLoginSessionId() {
        return UUID.randomUUID().toString();
    }

    private String sessionKey(String loginSessionId) {
        return "login-session:" + loginSessionId;
    }

    public record StoredSession(
        boolean tokenIssuable,
        boolean twoFactorCompleted,
        LoginSessionUser principal,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {

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
