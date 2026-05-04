package com.ktmmobile.msf.commons.logincore.application.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.logincore.application.port.in.VerificationCodeManager;
import com.ktmmobile.msf.commons.logincore.domain.dto.VerificationCodeIssue;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

@RequiredArgsConstructor
@Service
public class VerificationCodeService implements VerificationCodeManager {

    private static final int CODE_BOUND = 1_000_000;
    private static final String DELIMITER = "\t";

    private final CacheService<String> cacheService;
    private final LoginCoreProperties properties;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String createVerificationCode() {
        return String.format("%06d", secureRandom.nextInt(CODE_BOUND));
    }

    @Override
    public VerificationCodeIssue issue(String purpose, String subject) {
        String verificationId = UUID.randomUUID().toString();
        String verificationCode = createVerificationCode();
        cacheService.setValue(
            verificationKey(purpose, verificationId),
            serialize(subject, verificationCode, false),
            properties.twoFactor().challengeTimeToLive()
        );
        return new VerificationCodeIssue(
            verificationId,
            verificationCode,
            expiresAt()
        );
    }

    @Override
    public void verify(String purpose, String verificationId, String verificationCode) {
        validateFormat(verificationCode);
        String value = cacheService.getValue(verificationKey(purpose, verificationId));
        if (value == null || value.isBlank()) {
            throw new LoginException("인증번호가 유효하지 않거나 유효시간이 종료되었습니다.");
        }
        StoredVerificationCode stored = deserialize(value);
        if (!stored.verificationCode().equals(verificationCode)) {
            throw new LoginException("인증번호가 일치하지 않습니다.");
        }
        cacheService.setValue(
            verificationKey(purpose, verificationId),
            serialize(stored.subject(), "", true),
            properties.twoFactor().challengeTimeToLive()
        );
    }

    @Override
    public void verify(String savedVerificationCode, String verificationCode) {
        validateFormat(verificationCode);
        if (!savedVerificationCode.equals(verificationCode)) {
            throw new LoginException("인증번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void validateFormat(String verificationCode) {
        if (verificationCode == null || !verificationCode.matches("\\d{6}")) {
            throw new LoginException("인증번호는 6자리 숫자로 입력해 주세요.");
        }
    }

    private String serialize(String subject, String verificationCode, boolean verified) {
        return nullToEmpty(subject) + DELIMITER
            + nullToEmpty(verificationCode) + DELIMITER
            + verified;
    }

    private StoredVerificationCode deserialize(String value) {
        String[] values = value.split(DELIMITER, -1);
        if (values.length != 3) {
            throw new LoginException("인증번호가 유효하지 않거나 유효시간이 종료되었습니다.");
        }
        return new StoredVerificationCode(values[0], values[1], Boolean.parseBoolean(values[2]));
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private String verificationKey(String purpose, String verificationId) {
        return "verification-code:" + purpose + ":" + verificationId;
    }

    private LocalDateTime expiresAt() {
        return LocalDateTime.now(ZoneId.systemDefault())
            .plus(properties.twoFactor().challengeTimeToLive())
            .withNano(0);
    }

    private record StoredVerificationCode(
        String subject,
        String verificationCode,
        boolean verified
    ) {
    }
}
