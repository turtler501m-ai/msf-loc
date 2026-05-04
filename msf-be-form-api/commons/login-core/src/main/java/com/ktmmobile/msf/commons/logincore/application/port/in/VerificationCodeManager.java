package com.ktmmobile.msf.commons.logincore.application.port.in;

import com.ktmmobile.msf.commons.logincore.domain.dto.VerificationCodeIssue;

public interface VerificationCodeManager {

    String createVerificationCode();

    VerificationCodeIssue issue(String purpose, String subject);

    void verify(String purpose, String verificationId, String verificationCode);

    void verify(String savedVerificationCode, String verificationCode);

    void validateFormat(String verificationCode);
}
