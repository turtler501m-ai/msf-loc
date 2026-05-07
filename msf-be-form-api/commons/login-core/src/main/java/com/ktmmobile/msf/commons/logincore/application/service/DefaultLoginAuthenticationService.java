package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.logincore.application.port.out.LoginAuthenticator;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginAuthenticationCredential;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionContext;
import com.ktmmobile.msf.commons.logincore.domain.policy.completion.LoginCompletionPolicyComposite;
import com.ktmmobile.msf.commons.logincore.domain.policy.failure.LoginFailureContext;
import com.ktmmobile.msf.commons.logincore.domain.policy.failure.LoginFailurePolicyComposite;
import com.ktmmobile.msf.commons.logincore.domain.policy.requiredaction.LoginRequiredActionPolicyComposite;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

@RequiredArgsConstructor
@Service
public class DefaultLoginAuthenticationService<C extends LoginAuthenticationCredential> implements LoginAuthenticator<C> {

    private final LoginUserFinder<C> loginUserFinder;
    private final PasswordEncoder passwordEncoder;
    private final LoginCompletionPolicyComposite loginCompletionPolicyComposite;
    private final LoginFailurePolicyComposite loginFailurePolicyComposite;
    private final LoginRequiredActionPolicyComposite loginRequiredActionPolicyComposite;
    private final LoginSessionService loginSessionService;
    private final LoginUserInfoCacheService loginUserInfoCacheService;

    @Override
    @Transactional(noRollbackFor = LoginException.class)
    public LoginResult authenticate(C credential) {
        LoginUser user = loginUserFinder.findByCredential(credential)
            .orElseThrow(() -> new LoginException("아이디 또는 비밀번호가 불일치합니다."));

        LoginCompletionContext<C> context = new LoginCompletionContext<>(user, credential);
        loginCompletionPolicyComposite.verify(context);

        if (credential.isPasswordAuth() && !matches(credential.password(), user.encodedPassword())) {
            boolean shouldLock = loginFailurePolicyComposite.shouldLock(new LoginFailureContext<>(user, credential));
            loginUserFinder.recordLoginFailure(user, credential, shouldLock);
            throw new LoginException("아이디 또는 비밀번호가 불일치합니다.");
        }

        loginUserFinder.recordLoginSuccess(user, credential);
        LoginUserInfo userInfo = loginUserFinder.findUserInfo(user, credential)
            .orElseGet(() -> LoginUserInfo.of(user, credential.userType()));
        userInfo = withClientIp(userInfo, credential.clientIp());
        LoginCompletionContext<C> requiredActionContext = new LoginCompletionContext<>(user, credential, userInfo);
        List<LoginRequiredAction> requiredActions = loginRequiredActionPolicyComposite.resolve(requiredActionContext);
        LoginSessionUser principal = new LoginSessionUser(
            userInfo.userId(),
            userInfo.userType(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            userInfo.clientIp(),
            userInfo.attributes(),
            requiredActions
        );
        loginUserInfoCacheService.save(userInfo);
        List<LoginRequiredAction> actionsBeforeTokenIssue = actionsBeforeTokenIssue(requiredActions);
        if (actionsBeforeTokenIssue.isEmpty()) {
            return loginSessionService.createReadySession(principal);
        }
        if (actionsBeforeTokenIssue.getFirst().isVerifyTwoFactor()) {
            return loginSessionService.createTwoFactorSession(principal);
        }
        return loginSessionService.createActionSession(principal, actionsBeforeTokenIssue);
    }

    private boolean matches(String rawPassword, String savedPassword) {
        return passwordEncoder.matches(rawPassword, savedPassword);
    }

    private List<LoginRequiredAction> actionsBeforeTokenIssue(List<LoginRequiredAction> requiredActions) {
        return requiredActions.stream()
            .filter(action -> !action.tokenIssuable())
            .toList();
    }

    private LoginUserInfo withClientIp(LoginUserInfo userInfo, String clientIp) {
        return new LoginUserInfo(
            userInfo.userId(),
            userInfo.userName(),
            userInfo.phoneNumber(),
            userInfo.userType(),
            clientIp,
            userInfo.attributes()
        );
    }
}
