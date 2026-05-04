package com.ktmmobile.msf.commons.logincore.application.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.logincore.application.port.in.LoginBiometricChallengeManager;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginBiometricChallenge;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

@RequiredArgsConstructor
@Service
public class LoginBiometricChallengeService implements LoginBiometricChallengeManager {

    private static final int NONCE_BYTE_SIZE = 32;

    private final CacheService<String> cacheService;
    private final LoginCoreProperties properties;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public LoginBiometricChallenge createChallenge() {
        String challengeId = UUID.randomUUID().toString();
        String nonce = createNonce();
        cacheService.setValue(challengeKey(challengeId), nonce, properties.biometric().challengeTimeToLive());
        return new LoginBiometricChallenge(
            challengeId,
            nonce,
            Instant.now().plus(properties.biometric().challengeTimeToLive())
        );
    }

    @Override
    public String consumeChallenge(String challengeId) {
        String nonce = cacheService.getValue(challengeKey(challengeId));
        if (nonce == null || nonce.isBlank()) {
            throw new LoginException("생체인증 challenge가 유효하지 않습니다.");
        }
        cacheService.delete(challengeKey(challengeId));
        return nonce;
    }

    private String createNonce() {
        byte[] bytes = new byte[NONCE_BYTE_SIZE];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String challengeKey(String challengeId) {
        return "auth-challenge:" + challengeId;
    }
}
