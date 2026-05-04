package com.ktmmobile.msf.commons.logincore.application.port.in;

public interface LoginBiometricChallengeManager {

    com.ktmmobile.msf.commons.logincore.domain.dto.LoginBiometricChallenge createChallenge();

    String consumeChallenge(String challengeId);
}
