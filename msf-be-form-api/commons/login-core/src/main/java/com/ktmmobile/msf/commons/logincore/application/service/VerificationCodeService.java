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

/**
 * 업무 공통 인증번호 발급과 검증 서비스
 */
@RequiredArgsConstructor
@Service
public class VerificationCodeService implements VerificationCodeManager {

    private static final int CODE_BOUND = 1_000_000;
    private static final String DELIMITER = "\t";

    private final CacheService<String> cacheService;
    private final LoginCoreProperties properties;
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * 6자리 인증번호 생성
     *
     * @return 인증번호
     */
    @Override
    public String createVerificationCode() {
        return String.format("%06d", secureRandom.nextInt(CODE_BOUND));
    }

    /**
     * 인증번호 발급
     *
     * @param purpose 인증 목적
     * @param subject 인증 대상
     * @return 인증번호 발급 결과
     */
    @Override
    public VerificationCodeIssue issue(String purpose, String subject) {
        String verificationId = UUID.randomUUID().toString();
        String verificationCode = createVerificationCode();
        // 인증 대상, 인증번호, 검증 완료 여부를 하나의 값으로 저장
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

    /**
     * 저장 인증번호 검증
     *
     * @param purpose 인증 목적
     * @param verificationId 인증 ID
     * @param verificationCode 인증번호
     */
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
        // 검증 완료 후 인증번호 원문은 제거하고 완료 여부만 보관
        cacheService.setValue(
            verificationKey(purpose, verificationId),
            serialize(stored.subject(), "", true),
            properties.twoFactor().challengeTimeToLive()
        );
    }

    /**
     * 인증번호 값 직접 검증
     *
     * @param savedVerificationCode 저장 인증번호
     * @param verificationCode 입력 인증번호
     */
    @Override
    public void verify(String savedVerificationCode, String verificationCode) {
        validateFormat(verificationCode);
        if (!savedVerificationCode.equals(verificationCode)) {
            throw new LoginException("인증번호가 일치하지 않습니다.");
        }
    }

    /**
     * 인증번호 형식 검증
     *
     * @param verificationCode 인증번호
     */
    @Override
    public void validateFormat(String verificationCode) {
        if (verificationCode == null || !verificationCode.matches("\\d{6}")) {
            throw new LoginException("인증번호는 6자리 숫자로 입력해 주세요.");
        }
    }

    /**
     * 인증번호 저장 값 직렬화
     *
     * @param subject 인증 대상
     * @param verificationCode 인증번호
     * @param verified 검증 완료 여부
     * @return 직렬화 문자열
     */
    private String serialize(String subject, String verificationCode, boolean verified) {
        return nullToEmpty(subject) + DELIMITER
            + nullToEmpty(verificationCode) + DELIMITER
            + verified;
    }

    /**
     * 인증번호 저장 값 역직렬화
     *
     * @param value 저장 값
     * @return 저장 인증번호
     */
    private StoredVerificationCode deserialize(String value) {
        String[] values = value.split(DELIMITER, -1);
        if (values.length != 3) {
            throw new LoginException("인증번호가 유효하지 않거나 유효시간이 종료되었습니다.");
        }
        return new StoredVerificationCode(values[0], values[1], Boolean.parseBoolean(values[2]));
    }

    /**
     * null 문자열 빈 문자열 변환
     *
     * @param value 원본 문자열
     * @return 변환 문자열
     */
    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    /**
     * 인증번호 캐시 키 생성
     *
     * @param purpose 인증 목적
     * @param verificationId 인증 ID
     * @return 인증번호 캐시 키
     */
    private String verificationKey(String purpose, String verificationId) {
        return "verification-code:" + purpose + ":" + verificationId;
    }

    /**
     * 인증번호 만료 일시 계산
     *
     * @return 인증번호 만료 일시
     */
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
