package com.ktmmobile.msf.commons.logincore.domain.dto;

public record LoginTokenIssued(
    LoginTokenPair tokenPair
) implements LoginResult {
}
