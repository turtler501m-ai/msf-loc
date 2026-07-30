package com.ktmmobile.msf.commons.logincore.application.service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;
import com.ktmmobile.msf.commons.crypto.support.processor.LegacyAes256CbcTextEncryptor;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginBiometricChallenge;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

@Slf4j
@RequiredArgsConstructor
@Component
class BiometricChallengeProcessor {

    private static final String BIOMETRIC_AUTHENTICATION_FAILED_MESSAGE = "생체인증에 실패했습니다.";
    private static final int NONCE_BYTE_SIZE = 32;
    private static final int MAX_VERIFY_FAILURE_COUNT = 3;
    private static final int LOG_HASH_PREFIX_LENGTH = 12;
    private static final char[] HEX = "0123456789abcdef".toCharArray();

    private final CacheService<String> cacheService;
    private final LoginCoreProperties properties;
    private final SecureRandom secureRandom = new SecureRandom();

    LoginBiometricChallenge issue(String challengeKeyPrefix, String failureKeyPrefix, String logMessage, String... bindingValues) {
        String nonce = createNonce();
        cacheService.setValue(
            challengeKey(challengeKeyPrefix, bindingValues),
            challengeHash(bindingValues, nonce),
            properties.biometric().challengeTimeToLive()
        );
        cacheService.delete(challengeKey(failureKeyPrefix, bindingValues));
        log.info("{} bindingKey={}, ttl={}", logMessage, challengeLogKey(bindingValues), properties.biometric().challengeTimeToLive());
        return new LoginBiometricChallenge(
            null,
            nonce,
            Instant.now().plus(properties.biometric().challengeTimeToLive())
        );
    }

    void verifyEncrypted(
        String challengeKeyPrefix,
        String failureKeyPrefix,
        String successLogMessage,
        String failureLogMessage,
        String encryptedNonce,
        String[] bindingValues,
        String... requiredValues
    ) {
        String bindingLogKey = challengeLogKeySafely(bindingValues);
        try {
            validateRequired(encryptedNonce, "encryptedNonce는 필수 입력 값입니다.");
            verify(challengeKeyPrefix, failureKeyPrefix, decryptNonce(encryptedNonce), bindingValues);
            log.info("{} bindingKey={}", successLogMessage, bindingLogKey);
        } catch (LoginException ex) {
            recordVerificationFailure(challengeKeyPrefix, failureKeyPrefix, bindingLogKey, bindingValues, requiredValues);
            log.warn("{} bindingKey={}, reason={}", failureLogMessage, bindingLogKey, ex.getMessage());
            throw ex;
        }
    }

    private void verify(String challengeKeyPrefix, String failureKeyPrefix, String nonce, String... bindingValues) {
        String challengeKey = challengeKey(challengeKeyPrefix, bindingValues);
        String expectedHash = cacheService.getValue(challengeKey);
        if (!StringUtils.hasText(expectedHash)) {
            throw new LoginException(BIOMETRIC_AUTHENTICATION_FAILED_MESSAGE);
        }

        String actualHash = challengeHash(bindingValues, nonce);
        if (!MessageDigest.isEqual(expectedHash.getBytes(StandardCharsets.UTF_8), actualHash.getBytes(StandardCharsets.UTF_8))) {
            throw new LoginException(BIOMETRIC_AUTHENTICATION_FAILED_MESSAGE);
        }

        if (!cacheService.deleteIfValueEquals(challengeKey, expectedHash)) {
            throw new LoginException(BIOMETRIC_AUTHENTICATION_FAILED_MESSAGE);
        }
        cacheService.delete(challengeKey(failureKeyPrefix, bindingValues));
    }

    private void recordVerificationFailure(
        String challengeKeyPrefix,
        String failureKeyPrefix,
        String bindingLogKey,
        String[] bindingValues,
        String... requiredValues
    ) {
        if (!hasTextAll(requiredValues)) {
            return;
        }

        long failureCount = cacheService.increment(
            challengeKey(failureKeyPrefix, bindingValues),
            properties.biometric().challengeTimeToLive()
        );
        if (failureCount < MAX_VERIFY_FAILURE_COUNT) {
            log.info(
                "Biometric challenge verification failure recorded. bindingKey={}, failureCount={}",
                bindingLogKey,
                failureCount
            );
            return;
        }

        cacheService.delete(challengeKey(challengeKeyPrefix, bindingValues));
        cacheService.delete(challengeKey(failureKeyPrefix, bindingValues));
        log.warn(
            "Biometric challenge removed after verification failures. bindingKey={}, failureCount={}",
            bindingLogKey,
            failureCount
        );
    }

    private String createNonce() {
        byte[] bytes = new byte[NONCE_BYTE_SIZE];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String challengeKey(String keyPrefix, String... bindingValues) {
        return keyPrefix + sha256(bindingValues);
    }

    private String challengeLogKeySafely(String... bindingValues) {
        if (!hasTextAll(bindingValues)) {
            return "missing";
        }
        return challengeLogKey(bindingValues);
    }

    private String challengeLogKey(String... bindingValues) {
        String hash = sha256(bindingValues);
        return hash.substring(0, Math.min(LOG_HASH_PREFIX_LENGTH, hash.length()));
    }

    private String challengeHash(String[] bindingValues, String nonce) {
        String[] values = new String[bindingValues.length + 1];
        System.arraycopy(bindingValues, 0, values, 0, bindingValues.length);
        values[bindingValues.length] = nonce;
        return sha256(values);
    }

    private void validateRequired(String value, String message) {
        if (!StringUtils.hasText(value)) {
            throw new LoginException(message);
        }
    }

    private boolean hasTextAll(String... values) {
        for (String value: values) {
            if (!StringUtils.hasText(value)) {
                return false;
            }
        }
        return true;
    }

    private String decryptNonce(String encryptedNonce) {
        LoginCoreProperties.ChallengeCrypto challengeCrypto = properties.biometric().challengeCrypto();
        try {
            return new LegacyAes256CbcTextEncryptor(challengeCrypto.key(), challengeCrypto.iv())
                .decrypt(encryptedNonce);
        } catch (CryptoException ex) {
            throw new LoginException(BIOMETRIC_AUTHENTICATION_FAILED_MESSAGE, ex);
        }
    }

    private String sha256(String... values) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (String value: values) {
                byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
                digest.update(ByteBuffer.allocate(Integer.BYTES).putInt(bytes.length).array());
                digest.update(bytes);
            }
            return toHex(digest.digest());
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 algorithm is not available.", ex);
        }
    }

    private String toHex(byte[] bytes) {
        char[] chars = new char[bytes.length * 2];
        for (int index = 0; index < bytes.length; index++) {
            int value = bytes[index] & 0xff;
            chars[index * 2] = HEX[value >>> 4];
            chars[index * 2 + 1] = HEX[value & 0x0f];
        }
        return new String(chars);
    }
}
