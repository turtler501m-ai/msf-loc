package com.ktmmobile.msf.commons.crypto.support.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LegacyAes256CbcTextEncryptorTest {

    private static final String TEST_LEGACY_KEY = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";
    private static final String TEST_LEGACY_IV = "YWJjZGVmOTg3NjU0MzIxMA==";
    private static final String TEST_LEGACY_ENCRYPTED_PHONE_NUMBER = "7vQbNbpVVeQpcSwmNSF4Ow==";

    private final LegacyAes256CbcTextEncryptor encryptor =
        new LegacyAes256CbcTextEncryptor(TEST_LEGACY_KEY, TEST_LEGACY_IV);

    @Test
    @DisplayName("레거시 AES-256-CBC는 기존 MSP/MCP 암호문과 동일한 값을 만든다")
    void encryptCompatibleWithLegacyAes256() {
        assertThat(encryptor.encrypt("01012345678")).isEqualTo(TEST_LEGACY_ENCRYPTED_PHONE_NUMBER);
    }

    @Test
    @DisplayName("레거시 AES-256-CBC 암호문은 복호화하면 원문으로 돌아온다")
    void encryptAndDecrypt() {
        String encrypted = encryptor.encrypt("01012345678");

        assertThat(encryptor.decrypt(encrypted)).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("레거시 호환을 위해 같은 평문은 항상 같은 암호문으로 만든다")
    void encryptIsDeterministicForLegacyCompatibility() {
        String first = encryptor.encrypt("01012345678");
        String second = encryptor.encrypt("01012345678");

        assertThat(first).isEqualTo(second);
    }

    @Test
    @DisplayName("빈 문자열은 암복호화해도 빈 문자열로 유지한다")
    void keepBlankValueBlank() {
        assertThat(encryptor.encrypt("")).isEmpty();
        assertThat(encryptor.decrypt("")).isEmpty();
    }

    @Test
    @DisplayName("레거시 키 또는 IV 길이가 올바르지 않으면 생성에 실패한다")
    void rejectInvalidKeyOrIv() {
        assertThatThrownBy(() -> new LegacyAes256CbcTextEncryptor("short", TEST_LEGACY_IV))
            .isInstanceOf(CryptoException.class);
        assertThatThrownBy(() -> new LegacyAes256CbcTextEncryptor(TEST_LEGACY_KEY, "short"))
            .isInstanceOf(CryptoException.class);
    }
}
