package com.ktmmobile.msf.domains.login.application.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.LoginUserMapper;
import com.ktmmobile.msf.domains.login.application.port.in.BiometricCredentialValidator;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BiometricCredentialService implements BiometricCredentialValidator {

    private static final int LOG_HASH_PREFIX_LENGTH = 12;

    private final LoginUserMapper loginUserMapper;

    /**
     * 생체인증 가능 단말 유효성 검증
     *
     * @param deviceUuid 단말 UUID
     */
    @Override
    public void validateDevice(String deviceUuid) {
        String bindingKey = logBindingKey(deviceUuid);
        if (!loginUserMapper.existsApprovedBiometricDevice(deviceUuid)) {
            log.warn("Biometric device validation failed. bindingKey={}", bindingKey);
            throw new LoginException("생체인증에 실패했습니다.");
        }
        log.info("Biometric device validation succeeded. bindingKey={}", bindingKey);
    }

    /**
     * 인증 사용자 생체인증 가능 단말 유효성 검증
     *
     * @param userId 인증 사용자 ID
     * @param deviceUuid 단말 UUID
     */
    @Override
    public void validateDevice(String userId, String deviceUuid) {
        String bindingKey = logBindingKey(userId, deviceUuid);
        if (!loginUserMapper.existsApprovedBiometricDeviceByUserId(userId, deviceUuid)) {
            log.warn("Authenticated biometric device validation failed. bindingKey={}", bindingKey);
            throw new LoginException("생체인증에 실패했습니다.");
        }
        log.info("Authenticated biometric device validation succeeded. bindingKey={}, userId={}", bindingKey, userId);
    }

    /**
     * 생체인증 등록 정보 유효성 검증
     *
     * @param deviceUuid 단말 UUID
     * @param bioKey 생체인증 키
     * @return 생체인증 등록 사용자 ID
     */
    @Override
    public String validate(String deviceUuid, String bioKey) {
        String bindingKey = logBindingKey(deviceUuid, bioKey);
        String userId = loginUserMapper.selectApprovedBiometricCredentialUserId(deviceUuid, bioKey);
        if (!StringUtils.hasText(userId)) {
            log.warn("Biometric credential validation failed. bindingKey={}", bindingKey);
            throw new LoginException("생체인증에 실패했습니다.");
        }
        log.info("Biometric credential validation succeeded. bindingKey={}, userId={}", bindingKey, userId);
        return userId;
    }

    /**
     * 인증 사용자 생체인증 등록 정보 유효성 검증
     *
     * @param userId 인증 사용자 ID
     * @param deviceUuid 단말 UUID
     * @param bioKey 생체인증 키
     */
    @Override
    public void validate(String userId, String deviceUuid, String bioKey) {
        String bindingKey = logBindingKey(userId, deviceUuid, bioKey);
        if (!loginUserMapper.existsApprovedBiometricCredential(userId, deviceUuid, bioKey)) {
            log.warn("Authenticated biometric credential validation failed. bindingKey={}", bindingKey);
            throw new LoginException("생체인증에 실패했습니다.");
        }
        log.info("Authenticated biometric credential validation succeeded. bindingKey={}, userId={}", bindingKey, userId);
    }

    /**
     * 로그 식별용 바인딩 키 생성
     *
     * @param values 바인딩 값
     * @return SHA-256 해시 앞자리
     */
    private String logBindingKey(String... values) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (String value: values) {
                digest.update(value.getBytes(StandardCharsets.UTF_8));
                digest.update((byte) ':');
            }
            return HexFormat.of()
                .formatHex(digest.digest())
                .substring(0, LOG_HASH_PREFIX_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 digest is not available.", e);
        }
    }
}
