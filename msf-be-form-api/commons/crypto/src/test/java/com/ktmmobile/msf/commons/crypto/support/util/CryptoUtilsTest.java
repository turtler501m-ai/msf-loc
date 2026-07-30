package com.ktmmobile.msf.commons.crypto.support.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.processor.AesGcmTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.LegacyAes256CbcTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.LegacyKisaSeedCbcTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.SearchableAesGcmTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.TextEncryptorRegistry;

class CryptoUtilsTest {

    private static final String TEST_LEGACY_KEY = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";
    private static final String TEST_LEGACY_IV = "YWJjZGVmOTg3NjU0MzIxMA==";
    private static final String TEST_LEGACY_ENCRYPTED_PHONE_NUMBER = "7vQbNbpVVeQpcSwmNSF4Ow==";
    private static final String TEST_KISA_SEED_KEY = "0123456789abcdef";
    private static final String TEST_KISA_SEED_IV = "MDEyMzQ1Njc4OWFiY2RlZg==";

    @Test
    @DisplayName("CryptoUtils는 기본 AES_GCM 암복호화를 위임한다")
    void encryptAndDecryptByStaticUtils() {
        CryptoUtils.initialize(new AesGcmTextEncryptor("12345678901234567890123456789012", "ENC:"));

        String encrypted = CryptoUtils.encrypt("plain");

        assertThat(encrypted).startsWith("ENC:");
        assertThat(CryptoUtils.isEncrypted(encrypted)).isTrue();
        assertThat(CryptoUtils.decrypt(encrypted)).isEqualTo("plain");
    }

    @Test
    @DisplayName("CryptoUtils는 LEGACY_AES256_CBC 알고리즘 암복호화를 위임한다")
    void encryptAndDecryptLegacyAes256ByStaticUtils() {
        CryptoUtils.initialize(new TextEncryptorRegistry(Map.of(
            FieldCryptoAlgorithm.AES_GCM,
            new AesGcmTextEncryptor("12345678901234567890123456789012", "ENC:"),
            FieldCryptoAlgorithm.AES_GCM_SEARCHABLE,
            new SearchableAesGcmTextEncryptor("12345678901234567890123456789012", "ENC:", "aes-gcm-v1"),
            FieldCryptoAlgorithm.LEGACY_AES256_CBC,
            new LegacyAes256CbcTextEncryptor(
                TEST_LEGACY_KEY,
                TEST_LEGACY_IV
            )
        )));

        String encrypted = CryptoUtils.encrypt("01012345678", FieldCryptoAlgorithm.LEGACY_AES256_CBC);

        assertThat(encrypted).isEqualTo(TEST_LEGACY_ENCRYPTED_PHONE_NUMBER);
        assertThat(CryptoUtils.decrypt(encrypted, FieldCryptoAlgorithm.LEGACY_AES256_CBC))
            .isEqualTo("01012345678");
    }

    @Test
    @DisplayName("CryptoUtils는 LEGACY_KISA_SEED_CBC 알고리즘 암복호화를 위임한다")
    void encryptAndDecryptKisaSeedCbcByStaticUtils() {
        KisaSeedUtils.initialize(TEST_KISA_SEED_KEY, TEST_KISA_SEED_IV);
        CryptoUtils.initialize(new TextEncryptorRegistry(Map.of(
            FieldCryptoAlgorithm.AES_GCM,
            new AesGcmTextEncryptor("12345678901234567890123456789012", "ENC:"),
            FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC,
            new LegacyKisaSeedCbcTextEncryptor()
        )));

        String encrypted = CryptoUtils.encrypt("01012345678", FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC);

        assertThat(encrypted).isNotEqualTo("01012345678");
        assertThat(CryptoUtils.decrypt(encrypted, FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC))
            .isEqualTo("01012345678");
    }

    @Test
    @DisplayName("CryptoUtils는 AES_GCM_SEARCHABLE 알고리즘 암복호화를 위임한다")
    void encryptAndDecryptSearchableAesGcmByStaticUtils() {
        CryptoUtils.initialize(new TextEncryptorRegistry(Map.of(
            FieldCryptoAlgorithm.AES_GCM,
            new AesGcmTextEncryptor("12345678901234567890123456789012", "ENC:"),
            FieldCryptoAlgorithm.AES_GCM_SEARCHABLE,
            new SearchableAesGcmTextEncryptor("12345678901234567890123456789012", "ENC:", "aes-gcm-v1")
        )));

        String first = CryptoUtils.encrypt("01012345678", FieldCryptoAlgorithm.AES_GCM_SEARCHABLE);
        String second = CryptoUtils.encrypt("01012345678", FieldCryptoAlgorithm.AES_GCM_SEARCHABLE);

        assertThat(first).isEqualTo(second);
        assertThat(CryptoUtils.decrypt(first, FieldCryptoAlgorithm.AES_GCM_SEARCHABLE))
            .isEqualTo("01012345678");
    }
}
