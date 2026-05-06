package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.logincore.application.port.in.LoginFlowProcessor;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginAuthenticator;
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
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginCoreService<C extends LoginAuthenticationCredential> implements LoginFlowProcessor<C> {

    private final LoginAuthenticator<C> loginAuthenticator;
    private final LoginTokenService loginTokenService;
    private final LoginSessionService loginSessionService;
    private final LoginUserInfoCacheService loginUserInfoCacheService;

    @Override
    public LoginResult loginWithIdPw(C credential) {
        return loginAuthenticator.authenticate(credential);
    }

    public LoginTwoFactorCompletionResult completeTwoFactor(String loginSessionId) {
        loginSessionService.completeTwoFactor(loginSessionId);
        log.info("Login 2FA completion updated. loginSessionId={}", mask(loginSessionId));
        return new LoginTwoFactorCompletionResult(loginSessionId, true);
    }

    @Override
    public LoginResult completeAction(String loginSessionId, String actionCode) {
        LoginSessionUser principal = enrich(loginSessionService.completeAction(loginSessionId, actionCode));
        loginUserInfoCacheService.save(principal);
        return next(loginSessionId, principal);
    }

    @Override
    public LoginResult resume(String loginSessionId) {
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

    @Override
    public LoginResult getSessionProgress(String loginSessionId) {
        LoginSessionState state = loginSessionService.getState(loginSessionId);
        return toSessionProgress(loginSessionId, state);
    }

    @Override
    public Optional<LoginResult> findSessionProgress(String loginSessionId) {
        return loginSessionService.findState(loginSessionId)
            .map(state -> toSessionProgress(loginSessionId, state));
    }

    @Override
    public LoginTwoFactorStatus getTwoFactorStatus(String loginSessionId) {
        return loginSessionService.getTwoFactorStatus(loginSessionId);
    }

    @Override
    public LoginSessionUser getSessionUser(String loginSessionId) {
        LoginSessionUser principal = enrich(loginSessionService.getPrincipal(loginSessionId));
        loginUserInfoCacheService.save(principal);
        return principal;
    }

    @Override
    public LoginResult issue(LoginSessionUser principal) {
        LoginTokenPair tokenPair = loginTokenService.issue(principal);
        log.info(
            "Login token issued. userId={}, accessTokenExpiresAt={}, refreshTokenExpiresAt={}",
            principal.userId(),
            tokenPair.accessTokenExpiresAt(),
            tokenPair.refreshTokenExpiresAt()
        );
        return new LoginTokenIssued(tokenPair);
    }

    @Override
    public LoginTokenPair issue(String loginSessionId) {
        log.info("Login token issue requested. loginSessionId={}", mask(loginSessionId));
        LoginSessionUser principal = enrich(loginSessionService.getVerifiedPrincipal(loginSessionId));
        List<LoginRequiredAction> actionsBeforeTokenIssue = actionsBeforeTokenIssue(principal);
        if (!actionsBeforeTokenIssue.isEmpty()) {
            log.info(
                "Login token issue blocked. loginSessionId={}, userId={}, remainingActions={}",
                mask(loginSessionId),
                principal.userId(),
                actionsBeforeTokenIssue.stream().map(LoginRequiredAction::code).toList()
            );
            throw new LoginException("로그인 완료 전 필수 조치가 남아 있습니다.");
        }
        loginSessionService.delete(loginSessionId);
        loginUserInfoCacheService.save(principal);
        LoginTokenPair tokenPair = loginTokenService.issue(principal);
        log.info(
            "Login token issue completed. loginSessionId={}, userId={}, accessTokenExpiresAt={}, refreshTokenExpiresAt={}",
            mask(loginSessionId),
            principal.userId(),
            tokenPair.accessTokenExpiresAt(),
            tokenPair.refreshTokenExpiresAt()
        );
        return tokenPair;
    }

    @Override
    public LoginTokenPair refresh(String refreshToken) {
        return loginTokenService.refresh(refreshToken);
    }

    @Override
    public void logout(String refreshToken) {
        loginTokenService.logout(refreshToken);
    }

    private LoginResult toSessionProgress(String loginSessionId, LoginSessionState state) {
        LoginSessionUser principal = enrich(state.principal());
        loginUserInfoCacheService.save(principal);
        return current(loginSessionId, state, principal);
    }

    private LoginResult next(String loginSessionId, LoginSessionUser principal) {
        List<LoginRequiredAction> actionsBeforeTokenIssue = actionsBeforeTokenIssue(principal);
        if (actionsBeforeTokenIssue.isEmpty()) {
            return new LoginSessionReady(
                loginSessionId,
                principal.userId(),
                principal.userType(),
                principal.userName(),
                principal.phoneNumber(),
                principal.attributes()
            );
        }
        if (actionsBeforeTokenIssue.getFirst().isVerifyTwoFactor()) {
            return loginSessionService.requireTwoFactor(loginSessionId, principal);
        }
        return loginSessionService.toActionRequired(loginSessionId, principal, actionsBeforeTokenIssue);
    }

    private LoginResult current(String loginSessionId, LoginSessionState state, LoginSessionUser principal) {
        List<LoginRequiredAction> actionsBeforeTokenIssue = actionsBeforeTokenIssue(principal);
        if (actionsBeforeTokenIssue.isEmpty()) {
            return new LoginSessionReady(
                loginSessionId,
                principal.userId(),
                principal.userType(),
                principal.userName(),
                principal.phoneNumber(),
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
                principal.attributes(),
                actionsBeforeTokenIssue
            );
        }
        return loginSessionService.toActionRequired(loginSessionId, principal, actionsBeforeTokenIssue);
    }

    private List<LoginRequiredAction> actionsBeforeTokenIssue(LoginSessionUser principal) {
        return principal.requiredActions().stream()
            .filter(action -> !action.tokenIssuable())
            .toList();
    }

    private LoginSessionUser enrich(LoginSessionUser principal) {
        return loginUserInfoCacheService.get(principal.userType(), principal.userId())
            .map(userInfo -> merge(principal, userInfo))
            .orElse(principal);
    }

    private LoginSessionUser merge(LoginSessionUser principal, LoginUserInfo userInfo) {
        return new LoginSessionUser(
            principal.userId(),
            principal.userType(),
            coalesce(userInfo.userName(), principal.userName()),
            coalesce(userInfo.phoneNumber(), principal.phoneNumber()),
            mergeAttributes(principal, userInfo),
            principal.requiredActions()
        );
    }

    private java.util.Map<String, Object> mergeAttributes(LoginSessionUser principal, LoginUserInfo userInfo) {
        java.util.Map<String, Object> attributes = new java.util.LinkedHashMap<>(principal.attributes());
        attributes.putAll(userInfo.attributes());
        return attributes;
    }

    private String coalesce(String primary, String fallback) {
        return primary == null ? fallback : primary;
    }

    private String resultName(LoginResult result) {
        return result.getClass().getSimpleName();
    }

    private String mask(String value) {
        if (value == null || value.length() <= 8) {
            return "****";
        }
        return value.substring(0, 8) + "****";
    }
}
