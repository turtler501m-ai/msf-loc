package com.ktmmobile.msf.commons.websecurity.security.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@Disabled
@ActiveProfiles("local")
@TestPropertySource(properties = {"jasypt.encryptor.password=test1234!@#$"})
@SpringJUnitConfig(JasyptConfig.class)
class JasyptConfigTest {

    private static final String PLAIN_TEXT = "plainSecretKey";
    private static final String ENCRYPTED = "vIctW7yf9ubAdCI+8SXidMGeocavaGNOQ6xdxxuFAcKPwUEYd9RvMi94VU+6ns0t";

    @Autowired
    StringEncryptor jasyptStringEncryptor;

    @DisplayName("Jasypt 암호화")
    @Test
    void encrypt() {
        assertThatCode(() -> {
            String encrypted = jasyptStringEncryptor.encrypt(PLAIN_TEXT);
            System.out.println(">>> encrypted: ENC(" + encrypted + ")");
        }).doesNotThrowAnyException();
    }

    @DisplayName("Jasypt 복호화")
    @Test
    void decrypt() {
        assertThatCode(() -> {
            String decrypted = jasyptStringEncryptor.decrypt(ENCRYPTED);
            System.out.println(">>> decrypted: " + decrypted);
        }).doesNotThrowAnyException();
    }

    @DisplayName("Jasypt 복호화 및 비교")
    @Test
    void decryptAndCompare() {
        String decrypted = jasyptStringEncryptor.decrypt(ENCRYPTED);
        System.out.println(">>> decrypted: " + decrypted);
        System.out.println(">>> plainText: " + PLAIN_TEXT);
        assertThat(decrypted).isEqualTo(PLAIN_TEXT);
    }
}
