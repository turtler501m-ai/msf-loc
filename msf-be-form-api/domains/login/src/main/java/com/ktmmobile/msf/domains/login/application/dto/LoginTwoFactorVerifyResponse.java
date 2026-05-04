package com.ktmmobile.msf.domains.login.application.dto;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginActionRequired;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginRequiredAction;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginResult;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionReady;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTokenIssued;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorRequired;

public record LoginTwoFactorVerifyResponse(
    boolean verified,
    boolean tokenIssuable,
    String loginSessionId,
    String requiredActionCode,
    String requiredActionMessage
) {

    public static LoginTwoFactorVerifyResponse from(LoginResult result) {
        return switch (result) {
            case LoginSessionReady ready -> new LoginTwoFactorVerifyResponse(true, true, ready.loginSessionId(), null, null);
            case LoginActionRequired required -> from(required.loginSessionId(), required.requiredActions());
            case LoginTwoFactorRequired required -> from(required.loginSessionId(), required.requiredActions());
            case LoginTokenIssued ignored -> new LoginTwoFactorVerifyResponse(true, true, null, null, null);
        };
    }

    private static LoginTwoFactorVerifyResponse from(String loginSessionId, java.util.List<LoginRequiredAction> actions) {
        LoginRequiredAction action = actions == null || actions.isEmpty() ? null : actions.getFirst();
        return new LoginTwoFactorVerifyResponse(
            true,
            actions == null || actions.stream().allMatch(LoginRequiredAction::tokenIssuable),
            loginSessionId,
            action == null ? null : action.code(),
            action == null ? null : action.message()
        );
    }
}
