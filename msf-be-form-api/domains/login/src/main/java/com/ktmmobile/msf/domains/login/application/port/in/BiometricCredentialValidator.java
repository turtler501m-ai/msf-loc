package com.ktmmobile.msf.domains.login.application.port.in;

public interface BiometricCredentialValidator {

    /**
     * 생체인증 가능 단말 유효성 검증
     *
     * @param deviceUuid 단말 UUID
     */
    void validateDevice(String deviceUuid);

    /**
     * 인증 사용자 생체인증 가능 단말 유효성 검증
     *
     * @param userId 인증 사용자 ID
     * @param deviceUuid 단말 UUID
     */
    void validateDevice(String userId, String deviceUuid);

    /**
     * 생체인증 등록 정보 유효성 검증
     *
     * @param deviceUuid 단말 UUID
     * @param bioKey 생체인증 키
     * @return 생체인증 등록 사용자 ID
     */
    String validate(String deviceUuid, String bioKey);

    /**
     * 인증 사용자 생체인증 등록 정보 유효성 검증
     *
     * @param userId 인증 사용자 ID
     * @param deviceUuid 단말 UUID
     * @param bioKey 생체인증 키
     */
    void validate(String userId, String deviceUuid, String bioKey);
}
