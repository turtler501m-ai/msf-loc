package com.ktmmobile.msf.commons.logincore.domain.dto;

public sealed interface LoginResult permits LoginActionRequired, LoginSessionReady, LoginTokenIssued, LoginTwoFactorRequired {
}
