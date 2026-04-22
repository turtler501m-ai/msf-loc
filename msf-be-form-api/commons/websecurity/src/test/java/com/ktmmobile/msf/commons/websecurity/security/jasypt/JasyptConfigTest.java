package com.ktmmobile.msf.commons.websecurity.security.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

// @Disabled
@ActiveProfiles("local")
@TestPropertySource(properties = {"jasypt.encryptor.password=form#$%msf321smart"})
@SpringJUnitConfig(JasyptConfig.class)
class JasyptConfigTest {

    private static final String PLAIN_TEXT = "U01TX0FVVEgyMDIxMDcyNzIwMTExMTExMTQ1OTg=";
    private static final String ENCRYPTED = "lL2k/kadYrZ88Yo46pp5OcD0ia8J6lek6QqJ83A897D80gVkeuZk4kjBrxfhaxJU+S+ATvu7N28HiPddbe/YV+G/LOHdcCyoWFDzU20K4os=";

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
