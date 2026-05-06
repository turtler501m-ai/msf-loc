package com.ktmmobile.msf.commons.logincore.application.port.in;

import java.util.Optional;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCompletionResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorStatus;

public interface LoginSessionFlowProcessor {

    LoginTwoFactorCompletionResult completeTwoFactor(String loginSessionId);

    LoginResult completeAction(String loginSessionId, String actionCode);

    LoginResult resume(String loginSessionId);

    LoginResult getSessionProgress(String loginSessionId);

    Optional<LoginResult> findSessionProgress(String loginSessionId);

    LoginTwoFactorStatus getTwoFactorStatus(String loginSessionId);

    LoginSessionUser getSessionUser(String loginSessionId);

    LoginResult issue(LoginSessionUser principal);

    LoginTokenPair issue(String loginSessionId);

    LoginTokenPair refresh(String refreshToken);

    void logout(String refreshToken);
}
