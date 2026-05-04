package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.logincore.application.port.in.LoginFlowProcessor;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginTwoFactorCodeSender;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginAuthenticator;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionReady;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenIssued;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCodeIssue;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCodeResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorRequired;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorVerifyResult;
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

    @Override
    public LoginTwoFactorVerifyResult verifyTwoFactor(String loginSessionId, String verificationCode) {
        log.info("Login 2FA verification requested. loginSessionId={}", mask(loginSessionId));
        loginSessionService.verifyTwoFactor(loginSessionId, verificationCode);
        log.info("Login 2FA verification completed. loginSessionId={}", mask(loginSessionId));
        return new LoginTwoFactorVerifyResult(loginSessionId, true);
    }

    @Override
    public LoginTwoFactorCodeIssue issueTwoFactorCode(String loginSessionId) {
        log.info("Login 2FA verification code issue requested. loginSessionId={}", mask(loginSessionId));
        LoginTwoFactorCodeIssue issue = loginSessionService.issueTwoFactorCode(loginSessionId);
        log.info("Login 2FA verification code issued. loginSessionId={}, userId={}", mask(loginSessionId), issue.principal().userId());
        return issue;
    }

    @Override
    public LoginTwoFactorRequired updateTwoFactorCodeExpiresAt(String loginSessionId) {
        log.info("Login 2FA verification code expiration update requested. loginSessionId={}", mask(loginSessionId));
        LoginTwoFactorRequired result = loginSessionService.updateTwoFactorCodeExpiresAt(loginSessionId);
        log.info(
            "Login 2FA verification code expiration updated. loginSessionId={}, userId={}, expiresAt={}",
            mask(loginSessionId),
            result.userId(),
            result.expiresAt()
        );
        return result;
    }

    @Override
    public LoginTwoFactorCodeResult issueAndSendTwoFactorCode(String loginSessionId, LoginTwoFactorCodeSender sender) {
        LoginTwoFactorCodeIssue issue = issueTwoFactorCode(loginSessionId);
        sender.send(issue);
        LoginTwoFactorRequired required = updateTwoFactorCodeExpiresAt(loginSessionId);
        return new LoginTwoFactorCodeResult(
            loginSessionId,
            required.expiresAt(),
            issue.verificationCode()
        );
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
