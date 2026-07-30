package com.ktmmobile.msf.commons.logincore.application.service;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.common.service.InMemoryCacheService;
import com.ktmmobile.msf.commons.crypto.support.processor.LegacyAes256CbcTextEncryptor;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginBiometricChallenge;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoginBiometricChallengeServiceTest {

    @Test
    @DisplayName("deviceUuid에 바인딩된 암호화 nonce를 1회 검증한다")
    void verifyEncryptedChallengeOnce() throws Exception {
        LoginBiometricChallengeService service = service();

        LoginBiometricChallenge challenge = service.createChallenge("device-1");
        String encryptedNonce = encrypt(challenge.nonce());

        assertThat(challenge.nonce()).isNotBlank();
        service.verifyEncryptedChallenge("device-1", "bio-key-1", encryptedNonce);
        assertThatThrownBy(() -> service.verifyEncryptedChallenge("device-1", "bio-key-1", encryptedNonce))
            .isInstanceOf(LoginException.class);
    }

    @Test
    @DisplayName("다른 deviceUuid로는 암호화 nonce를 검증할 수 없다")
    void rejectDifferentBinding() throws Exception {
        LoginBiometricChallengeService service = service();

        LoginBiometricChallenge challenge = service.createChallenge("device-1");
        String encryptedNonce = encrypt(challenge.nonce());

        assertThatThrownBy(() -> service.verifyEncryptedChallenge("device-2", "bio-key-1", encryptedNonce))
            .isInstanceOf(LoginException.class);
    }

    @Test
    @DisplayName("AES-256-CBC로 암호화된 nonce를 복호화해 검증한다")
    void verifyEncryptedChallenge() throws Exception {
        LoginBiometricChallengeService service = service();
        LoginBiometricChallenge challenge = service.createChallenge("device-1");
        String encryptedNonce = encrypt(challenge.nonce());

        service.verifyEncryptedChallenge("device-1", "bio-key-1", encryptedNonce);

        assertThatThrownBy(() -> service.verifyEncryptedChallenge("device-1", "bio-key-1", encryptedNonce))
            .isInstanceOf(LoginException.class);
    }

    @Test
    @DisplayName("생체인증 challenge 검증 실패가 3회 미만이면 정상 nonce로 다시 검증할 수 있다")
    void verifyAfterFailuresUnderLimit() throws Exception {
        LoginBiometricChallengeService service = service();
        LoginBiometricChallenge challenge = service.createChallenge("device-1");

        assertThatThrownBy(() -> service.verifyEncryptedChallenge("device-1", "bio-key-1", encrypt("invalid-nonce-1")))
            .isInstanceOf(LoginException.class);
        assertThatThrownBy(() -> service.verifyEncryptedChallenge("device-1", "bio-key-1", encrypt("invalid-nonce-2")))
            .isInstanceOf(LoginException.class);

        service.verifyEncryptedChallenge("device-1", "bio-key-1", encrypt(challenge.nonce()));
    }

    @Test
    @DisplayName("생체인증 challenge 검증 실패가 3회이면 challenge를 삭제한다")
    void removeChallengeAfterThreeFailures() throws Exception {
        LoginBiometricChallengeService service = service();
        LoginBiometricChallenge challenge = service.createChallenge("device-1");

        assertThatThrownBy(() -> service.verifyEncryptedChallenge("device-1", "bio-key-1", encrypt("invalid-nonce-1")))
            .isInstanceOf(LoginException.class);
        assertThatThrownBy(() -> service.verifyEncryptedChallenge("device-1", "bio-key-1", encrypt("invalid-nonce-2")))
            .isInstanceOf(LoginException.class);
        assertThatThrownBy(() -> service.verifyEncryptedChallenge("device-1", "bio-key-1", encrypt("invalid-nonce-3")))
            .isInstanceOf(LoginException.class);

        assertThatThrownBy(() -> service.verifyEncryptedChallenge("device-1", "bio-key-1", encrypt(challenge.nonce())))
            .isInstanceOf(LoginException.class);
    }

    private LoginBiometricChallengeService service() {
        return new LoginBiometricChallengeService(new BiometricChallengeProcessor(new InMemoryCacheService<>(), properties()));
    }

    private LoginCoreProperties properties() {
        return new LoginCoreProperties(
            new LoginCoreProperties.Failure(3),
            new LoginCoreProperties.TwoFactor(Duration.ofMinutes(3), Duration.ofMinutes(10)),
            new LoginCoreProperties.UserInfoCache(
                Duration.ofDays(1),
                Duration.ofHours(1),
                Duration.ofSeconds(3),
                Duration.ofSeconds(1),
                Duration.ofMillis(100)
            ),
            new LoginCoreProperties.Biometric(
                Duration.ofMinutes(3),
                new LoginCoreProperties.ChallengeCrypto("12345678901234567890123456789012", "1234567890123456")
            ),
            new LoginCoreProperties.Token(Duration.ofMinutes(10), Duration.ofHours(1)),
            new LoginCoreProperties.Cookie("refreshToken", "Strict", "/", true),
            new LoginCoreProperties.Policy(null, null, null)
        );
    }

    private String encrypt(String plainText) throws Exception {
        return new LegacyAes256CbcTextEncryptor("12345678901234567890123456789012", "1234567890123456")
            .encrypt(plainText);
    }
}
