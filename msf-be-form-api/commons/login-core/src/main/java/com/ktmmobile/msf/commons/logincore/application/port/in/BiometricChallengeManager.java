package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.dto.LoginBiometricChallenge;

/**
 * 인증 사용자 생체인증 challenge 생성과 검증 포트
 */
public interface BiometricChallengeManager {

    /**
     * 인증 사용자 생체인증 검증용 랜덤 키 생성
     *
     * @param userId 인증 사용자 ID
     * @param deviceUuid 기기 UUID
     * @return 랜덤 키 정보
     */
    LoginBiometricChallenge createChallenge(String userId, String deviceUuid);

    /**
     * AES-256-CBC로 암호화된 인증 사용자 생체인증 nonce 검증
     *
     * @param userId 인증 사용자 ID
     * @param deviceUuid 기기 UUID
     * @param bioKey 생체인증 키
     * @param encryptedNonce 암호화된 nonce
     */
    void verifyEncryptedChallenge(String userId, String deviceUuid, String bioKey, String encryptedNonce);
}
