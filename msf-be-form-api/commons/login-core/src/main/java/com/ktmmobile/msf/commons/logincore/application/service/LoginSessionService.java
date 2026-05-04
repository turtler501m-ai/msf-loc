package com.ktmmobile.msf.commons.logincore.application.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginActionRequired;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionReady;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCodeIssue;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorRequired;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

@RequiredArgsConstructor
@Service
public class LoginSessionService {

    private final CacheService<StoredSession> cacheService;
    private final LoginCoreProperties properties;
    private final VerificationCodeService verificationCodeService;

    public LoginTwoFactorRequired createTwoFactorSession(LoginSessionUser principal) {
        String loginSessionId = createLoginSessionId();
        return requireTwoFactor(loginSessionId, principal);
    }

    public LoginTwoFactorRequired requireTwoFactor(String loginSessionId, LoginSessionUser principal) {
        create(loginSessionId, principal, "", false);
        return toTwoFactorRequired(loginSessionId, principal, null);
    }

    public LoginTwoFactorCodeIssue issueTwoFactorCode(String loginSessionId) {
        StoredSession session = get(loginSessionId);
        if (session.verificationCompleted()) {
            throw new LoginException("이미 추가 인증이 완료되었습니다.");
        }
        String verificationCode = verificationCodeService.createVerificationCode();
        update(loginSessionId, session, session.principal(), verificationCode, false, null);
        return new LoginTwoFactorCodeIssue(session.principal(), verificationCode);
    }

    public LoginTwoFactorRequired updateTwoFactorCodeExpiresAt(String loginSessionId) {
        StoredSession session = get(loginSessionId);
        if (session.verificationCompleted()) {
            throw new LoginException("이미 추가 인증이 완료되었습니다.");
        }
        if (!StringUtils.hasText(session.verificationCode())) {
            throw new LoginException("인증번호 생성 후 만료시간을 갱신해주세요.");
        }
        LocalDateTime verificationCodeExpiresAt = expiresAt();
        update(loginSessionId, session, session.principal(), session.verificationCode(), false, verificationCodeExpiresAt);
        return toTwoFactorRequired(
            loginSessionId,
            session.principal(),
            verificationCodeExpiresAt
        );
    }

    private LoginSessionUser withoutVerifyTwoFactor(LoginSessionUser principal) {
        return new LoginSessionUser(
            principal.userId(),
            principal.userType(),
            principal.userName(),
            principal.phoneNumber(),
            principal.attributes(),
            principal.requiredActions().stream()
                .filter(action -> !action.isVerifyTwoFactor())
                .toList()
        );
    }

    public LoginActionRequired createActionSession(LoginSessionUser principal, List<LoginRequiredAction> requiredActions) {
        String loginSessionId = createLoginSessionId();
        create(loginSessionId, principal, "", true);
        return toActionRequired(loginSessionId, principal, requiredActions);
    }

    public LoginSessionReady createReadySession(LoginSessionUser principal) {
        String loginSessionId = createLoginSessionId();
        create(loginSessionId, principal, "", true);
        return new LoginSessionReady(
            loginSessionId,
            principal.userId(),
            principal.userType(),
            principal.userName(),
            principal.phoneNumber(),
            principal.attributes()
        );
    }

    public void verifyTwoFactor(String loginSessionId, String verificationCode) {
        StoredSession session = get(loginSessionId);
        if (session.verificationCompleted()) {
            throw new LoginException("인증번호가 유효하지 않거나 유효시간이 종료되었습니다.");
        }
        if (!StringUtils.hasText(session.verificationCode())) {
            throw new LoginException("인증번호 발송 후 인증을 진행해주세요.");
        }
        if (session.verificationCodeExpiresAt() == null || !now().isBefore(session.verificationCodeExpiresAt())) {
            throw new LoginException("인증번호 유효시간이 종료되었습니다.");
        }
        verificationCodeService.verify(session.verificationCode(), verificationCode);
        update(loginSessionId, session, withoutVerifyTwoFactor(session.principal()), "", true, null);
    }

    public LoginSessionUser getVerifiedPrincipal(String loginSessionId) {
        StoredSession session = get(loginSessionId);
        if (!session.verificationCompleted()) {
            throw new LoginException("추가 인증이 완료되지 않았습니다.");
        }
        return session.principal();
    }

    public LoginSessionUser completeAction(String loginSessionId, String actionCode) {
        StoredSession session = get(loginSessionId);
        if (!session.verificationCompleted()) {
            throw new LoginException("추가 인증이 완료되지 않았습니다.");
        }
        List<LoginRequiredAction> remainingActions = session.principal().requiredActions().stream()
            .filter(action -> !action.code().equals(actionCode))
            .toList();
        LoginSessionUser principal = new LoginSessionUser(
            session.principal().userId(),
            session.principal().userType(),
            session.principal().userName(),
            session.principal().phoneNumber(),
            session.principal().attributes(),
            remainingActions
        );
        update(loginSessionId, session, principal, "", true, null);
        return principal;
    }

    public LoginActionRequired toActionRequired(String loginSessionId, LoginSessionUser principal, List<LoginRequiredAction> requiredActions) {
        return new LoginActionRequired(
            loginSessionId,
            principal.userId(),
            principal.userType(),
            principal.userName(),
            principal.phoneNumber(),
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

    private void create(String loginSessionId, LoginSessionUser principal, String verificationCode, boolean verificationCompleted) {
        LocalDateTime now = now();
        save(loginSessionId, new StoredSession(principal, verificationCode, null, verificationCompleted, now, now));
    }

    private void update(
        String loginSessionId,
        StoredSession previous,
        LoginSessionUser principal,
        String verificationCode,
        boolean verificationCompleted,
        LocalDateTime verificationCodeExpiresAt
    ) {
        save(loginSessionId, new StoredSession(
            principal,
            verificationCode,
            verificationCodeExpiresAt,
            verificationCompleted,
            previous.createdAt(),
            now()
        ));
    }

    private void save(String loginSessionId, StoredSession session) {
        cacheService.setValue(sessionKey(loginSessionId), session, properties.twoFactor().sessionTimeToLive());
    }

    private LocalDateTime expiresAt() {
        return now().plus(properties.twoFactor().challengeTimeToLive()).withNano(0);
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
        Verification verification,
        LoginSessionUser principal,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {

        public StoredSession(
            LoginSessionUser principal,
            String verificationCode,
            LocalDateTime verificationCodeExpiresAt,
            boolean verificationCompleted,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
        ) {
            this(
                principal.requiredActions().stream().allMatch(LoginRequiredAction::tokenIssuable),
                new Verification(verificationCode, verificationCodeExpiresAt, verificationCompleted),
                principal,
                createdAt,
                updatedAt
            );
        }

        public String verificationCode() {
            return verification.code();
        }

        public LocalDateTime verificationCodeExpiresAt() {
            return verification.codeExpiresAt();
        }

        public boolean verificationCompleted() {
            return verification.completed();
        }
    }

    public record Verification(
        String code,
        LocalDateTime codeExpiresAt,
        boolean completed
    ) {
    }
}
