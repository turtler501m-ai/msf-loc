package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenPair;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCodeIssue;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCodeResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorRequired;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorVerifyResult;

public interface LoginSessionFlowProcessor {

    LoginTwoFactorVerifyResult verifyTwoFactor(String loginSessionId, String verificationCode);

    LoginTwoFactorCodeIssue issueTwoFactorCode(String loginSessionId);

    LoginTwoFactorRequired updateTwoFactorCodeExpiresAt(String loginSessionId);

    LoginTwoFactorCodeResult issueAndSendTwoFactorCode(String loginSessionId, LoginTwoFactorCodeSender sender);

    LoginResult completeAction(String loginSessionId, String actionCode);

    LoginResult resume(String loginSessionId);

    LoginResult issue(LoginSessionUser principal);

    LoginTokenPair issue(String loginSessionId);

    LoginTokenPair refresh(String refreshToken);

    void logout(String refreshToken);
}
