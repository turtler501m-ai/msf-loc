package com.ktmmobile.msf.commons.crypto.support.processor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SearchableAesGcmTextEncryptorTest {

    private static final String KEY = Base64.getEncoder()
        .encodeToString("12345678901234567890123456789012".getBytes(StandardCharsets.UTF_8));
    private static final String NEXT_KEY = Base64.getEncoder()
        .encodeToString("abcdefghijklmnopqrstuvwxzy123456".getBytes(StandardCharsets.UTF_8));

    private final SearchableAesGcmTextEncryptor encryptor =
        new SearchableAesGcmTextEncryptor(KEY, "ENC:", "aes-gcm-v1");

    @Test
    @DisplayName("AES_GCM_SEARCHABLE은 같은 평문을 같은 암호문으로 만든다")
    void encryptSamePlainTextToSameCipherText() {
        String first = encryptor.encrypt("01012345678");
        String second = encryptor.encrypt("01012345678");

        assertThat(first).startsWith("ENC:sgcm1:aes-gcm-v1:").isEqualTo(second);
        assertThat(encryptor.decrypt(first)).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("AES_GCM_SEARCHABLE은 다른 평문을 다른 암호문으로 만든다")
    void encryptDifferentPlainTextToDifferentCipherText() {
        String first = encryptor.encrypt("01012345678");
        String second = encryptor.encrypt("01087654321");

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    @DisplayName("AES_GCM_SEARCHABLE 암호문도 keyId로 이전 키 복호화를 지원한다")
    void decryptPreviousKeyPayloadByKeyId() {
        SearchableAesGcmTextEncryptor previousEncryptor =
            new SearchableAesGcmTextEncryptor(KEY, "ENC:", "aes-gcm-v1");
        SearchableAesGcmTextEncryptor currentEncryptor =
            new SearchableAesGcmTextEncryptor(NEXT_KEY, "ENC:", "aes-gcm-v2", Map.of("aes-gcm-v1", KEY));
        String encrypted = previousEncryptor.encrypt("01012345678");

        assertThat(currentEncryptor.decrypt(encrypted)).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("AES_GCM_SEARCHABLE에서 등록되지 않은 keyId는 복호화하지 않는다")
    void rejectUnknownKeyId() {
        SearchableAesGcmTextEncryptor previousEncryptor =
            new SearchableAesGcmTextEncryptor(KEY, "ENC:", "aes-gcm-v1");
        SearchableAesGcmTextEncryptor currentEncryptor =
            new SearchableAesGcmTextEncryptor(NEXT_KEY, "ENC:", "aes-gcm-v2");
        String encrypted = previousEncryptor.encrypt("01012345678");

        assertThatThrownBy(() -> currentEncryptor.decrypt(encrypted))
            .isInstanceOf(CryptoException.class)
            .hasMessageContaining("key-id: aes-gcm-v1");
    }
}
