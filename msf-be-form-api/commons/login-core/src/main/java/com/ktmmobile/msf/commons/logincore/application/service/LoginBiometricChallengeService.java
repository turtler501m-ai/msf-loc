package com.ktmmobile.msf.commons.logincore.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.application.port.in.LoginBiometricChallengeManager;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginBiometricChallenge;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;

/**
 * 로그인 전 생체인증 nonce challenge 발급과 검증 서비스
 */
@RequiredArgsConstructor
@Service
public class LoginBiometricChallengeService implements LoginBiometricChallengeManager {

    private static final String BIOMETRIC_CHALLENGE_KEY_PREFIX = "auth-biometric:challenge:";
    private static final String BIOMETRIC_CHALLENGE_FAILURE_KEY_PREFIX = "auth-biometric:challenge-fail:";

    private final BiometricChallengeProcessor processor;

    /**
     * deviceUuid에 바인딩된 생체인증 랜덤 키 생성
     *
     * @param deviceUuid 기기 UUID
     * @return 생체인증 challenge
     */
    @Override
    public LoginBiometricChallenge createChallenge(String deviceUuid) {
        validateRequired(deviceUuid, "deviceUuid는 필수 입력 값입니다.");

        return processor.issue(
            BIOMETRIC_CHALLENGE_KEY_PREFIX,
            BIOMETRIC_CHALLENGE_FAILURE_KEY_PREFIX,
            "Biometric challenge issued.",
            deviceUuid
        );
    }

    /**
     * AES-256-CBC로 암호화된 생체인증 nonce 검증
     *
     * @param deviceUuid 기기 UUID
     * @param bioKey 생체인증 키
     * @param encryptedNonce 암호화된 nonce
     */
    @Override
    public void verifyEncryptedChallenge(String deviceUuid, String bioKey, String encryptedNonce) {
        validateRequired(deviceUuid, "deviceUuid는 필수 입력 값입니다.");
        validateRequired(bioKey, "bioKey는 필수 입력 값입니다.");

        processor.verifyEncrypted(
            BIOMETRIC_CHALLENGE_KEY_PREFIX,
            BIOMETRIC_CHALLENGE_FAILURE_KEY_PREFIX,
            "Biometric challenge verified.",
            "Biometric challenge verification failed.",
            encryptedNonce,
            new String[] {deviceUuid},
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
