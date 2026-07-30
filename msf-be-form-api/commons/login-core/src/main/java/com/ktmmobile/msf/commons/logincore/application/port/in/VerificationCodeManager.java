package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.dto.VerificationCodeIssue;

/**
 * 인증번호 생성, 발급, 검증 공통 포트
 */
public interface VerificationCodeManager {

    /**
     * 6자리 인증번호 생성
     *
     * @return 인증번호
     */
    String createVerificationCode();

    /**
     * 목적과 대상 기준 인증번호 발급
     *
     * @param purpose 인증 목적
     * @param subject 인증 대상
     * @return 인증번호 발급 결과
     */
    VerificationCodeIssue issue(String purpose, String subject);

    /**
     * 발급 ID 기반 인증번호 검증
     *
     * @param purpose 인증 목적
     * @param verificationId 인증 ID
     * @param verificationCode 입력 인증번호
     */
    void verify(String purpose, String verificationId, String verificationCode);

    /**
     * 저장된 인증번호와 입력 인증번호 직접 비교
     *
     * @param savedVerificationCode 저장된 인증번호
     * @param verificationCode 입력 인증번호
     */
    void verify(String savedVerificationCode, String verificationCode);

    /**
     * 인증번호 형식 검증
     *
     * @param verificationCode 입력 인증번호
     */
    void validateFormat(String verificationCode);
}
