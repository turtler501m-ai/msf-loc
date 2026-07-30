package com.ktmmobile.msf.commons.crypto.support.processor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AesGcmTextEncryptorTest {

    private static final byte[] KEY_BYTES = "12345678901234567890123456789012".getBytes(StandardCharsets.UTF_8);
    private static final String KEY = Base64.getEncoder().encodeToString(KEY_BYTES);
    private static final String NEXT_KEY = Base64.getEncoder()
        .encodeToString("abcdefghijklmnopqrstuvwxzy123456".getBytes(StandardCharsets.UTF_8));

    private final AesGcmTextEncryptor encryptor = new AesGcmTextEncryptor(KEY, "ENC:");

    @Test
    @DisplayName("AES_GCM 암호문은 복호화하면 원문으로 돌아온다")
    void encryptAndDecrypt() {
        String encrypted = encryptor.encrypt("01012345678");

        assertThat(encrypted).startsWith("ENC:gcm1:default:").isNotEqualTo("01012345678");
        assertThat(encryptor.decrypt(encrypted)).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("AES_GCM은 랜덤 IV를 사용해 같은 평문도 매번 다른 암호문으로 만든다")
    void encryptUsesRandomIv() {
        String first = encryptor.encrypt("01012345678");
        String second = encryptor.encrypt("01012345678");

        assertThat(first).isNotEqualTo(second);
        assertThat(encryptor.decrypt(first)).isEqualTo("01012345678");
        assertThat(encryptor.decrypt(second)).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("변조된 AES_GCM 암호문은 복호화할 수 없다")
    void rejectTamperedCipherText() {
        String encrypted = encryptor.encrypt("01012345678");
        String tampered = encrypted.substring(0, encrypted.length() - 1)
            + (encrypted.endsWith("A") ? "B" : "A");

        assertThatThrownBy(() -> encryptor.decrypt(tampered))
            .isInstanceOf(CryptoException.class);
    }

    @Test
    @DisplayName("암호문 keyId가 이전 키를 가리키면 previousKeys로 복호화한다")
    void decryptPreviousKeyPayloadByKeyId() {
        AesGcmTextEncryptor previousEncryptor = new AesGcmTextEncryptor(KEY, "ENC:", "aes-gcm-v1");
        AesGcmTextEncryptor currentEncryptor = new AesGcmTextEncryptor(
            NEXT_KEY,
            "ENC:",
            "aes-gcm-v2",
            Map.of("aes-gcm-v1", KEY)
        );
        String encrypted = previousEncryptor.encrypt("01012345678");

        assertThat(encrypted).startsWith("ENC:gcm1:aes-gcm-v1:");
        assertThat(currentEncryptor.decrypt(encrypted)).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("등록되지 않은 keyId의 암호문은 복호화하지 않는다")
    void rejectUnknownKeyId() {
        AesGcmTextEncryptor previousEncryptor = new AesGcmTextEncryptor(KEY, "ENC:", "aes-gcm-v1");
        AesGcmTextEncryptor currentEncryptor = new AesGcmTextEncryptor(NEXT_KEY, "ENC:", "aes-gcm-v2");
        String encrypted = previousEncryptor.encrypt("01012345678");

        assertThatThrownBy(() -> currentEncryptor.decrypt(encrypted))
            .isInstanceOf(CryptoException.class)
            .hasMessageContaining("key-id: aes-gcm-v1");
    }

    @Test
    @DisplayName("이미 암호화된 값은 다시 암호화하지 않는다")
    void encryptDoesNotEncryptAlreadyEncryptedValue() {
        String encrypted = encryptor.encrypt("01012345678");

        assertThat(encryptor.encrypt(encrypted)).isEqualTo(encrypted);
    }

    @Test
    @DisplayName("AES 키 길이가 올바르지 않으면 생성에 실패한다")
    void rejectInvalidKeyLength() {
        assertThatThrownBy(() -> new AesGcmTextEncryptor("short", "ENC:"))
            .isInstanceOf(CryptoException.class);
    }

    @Test
    @DisplayName("keyId에는 구분자 ':'를 사용할 수 없다")
    void rejectInvalidKeyId() {
        assertThatThrownBy(() -> new AesGcmTextEncryptor(KEY, "ENC:", "key:1"))
            .isInstanceOf(CryptoException.class);
    }

}
