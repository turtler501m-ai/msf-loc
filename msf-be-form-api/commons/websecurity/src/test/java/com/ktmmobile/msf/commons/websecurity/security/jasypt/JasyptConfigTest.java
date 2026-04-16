package com.ktmmobile.msf.commons.websecurity.security.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

// @Disabled
@ActiveProfiles("local")
@TestPropertySource(properties = {"jasypt.encryptor.password=test1234"})
@SpringJUnitConfig(JasyptConfig.class)
class JasyptConfigTest {

    @Autowired
    StringEncryptor jasyptStringEncryptor;

    @DisplayName("Jasypt 암호화 출력")
    @Test
    void encryptTest() {
        String encrypted = jasyptStringEncryptor.encrypt("test");
        System.out.println(">>> encrypted: ENC(" + encrypted + ")");
    }
}
