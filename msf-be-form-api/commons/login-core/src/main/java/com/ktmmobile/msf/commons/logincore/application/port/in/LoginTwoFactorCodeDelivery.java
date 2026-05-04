package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginTwoFactorCodeIssue;

@FunctionalInterface
public interface LoginTwoFactorCodeDelivery {

    void send(LoginTwoFactorCodeIssue issue);
}
