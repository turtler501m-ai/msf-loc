package com.ktmmobile.msf.commons.logincore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.application.port.in.BiometricChallengeManager;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginBiometricChallenge;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

/**
 * 인증 사용자 생체인증 nonce challenge 발급과 검증 서비스
 */
@RequiredArgsConstructor
@Service
public class BiometricChallengeService implements BiometricChallengeManager {

    private static final String USER_BIOMETRIC_CHALLENGE_KEY_PREFIX = "auth-biometric:authenticated:challenge:";
    private static final String USER_BIOMETRIC_CHALLENGE_FAILURE_KEY_PREFIX = "auth-biometric:authenticated:challenge-fail:";

    private final BiometricChallengeProcessor processor;

    /**
     * 인증 사용자와 deviceUuid에 바인딩된 생체인증 랜덤 키 생성
     *
     * @param userId 인증 사용자 ID
     * @param deviceUuid 기기 UUID
     * @return 생체인증 challenge
     */
    @Override
    public LoginBiometricChallenge createChallenge(String userId, String deviceUuid) {
        validateRequired(userId, "userId는 필수 입력 값입니다.");
        validateRequired(deviceUuid, "deviceUuid는 필수 입력 값입니다.");

        return processor.issue(
            USER_BIOMETRIC_CHALLENGE_KEY_PREFIX,
            USER_BIOMETRIC_CHALLENGE_FAILURE_KEY_PREFIX,
            "User biometric challenge issued.",
            userId,
            deviceUuid
        );
    }

    /**
     * AES-256-CBC로 암호화된 인증 사용자 생체인증 nonce 검증
     *
     * @param userId 인증 사용자 ID
     * @param deviceUuid 기기 UUID
     * @param bioKey 생체인증 키
     * @param encryptedNonce 암호화된 nonce
     */
    @Override
    public void verifyEncryptedChallenge(String userId, String deviceUuid, String bioKey, String encryptedNonce) {
        validateRequired(userId, "userId는 필수 입력 값입니다.");
        validateRequired(deviceUuid, "deviceUuid는 필수 입력 값입니다.");
        validateRequired(bioKey, "bioKey는 필수 입력 값입니다.");

        processor.verifyEncrypted(
            USER_BIOMETRIC_CHALLENGE_KEY_PREFIX,
            USER_BIOMETRIC_CHALLENGE_FAILURE_KEY_PREFIX,
            "User biometric challenge verified.",
            "User biometric challenge verification failed.",
            encryptedNonce,
            new String[] {userId, deviceUuid},
            userId,
            deviceUuid,
            bioKey
        );
    }

    private void validateRequired(String value, String message) {
        if (!StringUtils.hasText(value)) {
            throw new LoginException(message);
        }
    }
}
