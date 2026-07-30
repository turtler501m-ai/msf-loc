package com.ktmmobile.msf.commons.crypto.support.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KisaSeedUtilsTest {

    private static final String TEST_KEY = "0123456789abcdef";
    private static final String TEST_IV = "MDEyMzQ1Njc4OWFiY2RlZg==";

    @BeforeEach
    void setUp() {
        KisaSeedUtils.initialize(TEST_KEY, TEST_IV);
    }

    @Test
    @DisplayName("MSP PRX 레거시 SEED-CBC 방식으로 문자열을 암복호화한다")
    void encryptAndDecryptByLegacySeedCbc() {
        String encrypted = KisaSeedUtils.encrypt("01012345678");

        assertThat(encrypted).isNotEqualTo("01012345678");
        assertThat(KisaSeedUtils.decrypt(encrypted)).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("MSP PRX 레거시 호환을 위해 같은 평문은 같은 암호문으로 만든다")
    void encryptIsDeterministicForLegacyCompatibility() {
        String first = KisaSeedUtils.encrypt("01012345678");
        String second = KisaSeedUtils.encrypt("01012345678");

        assertThat(first).isEqualTo(second);
    }

    @Test
    @DisplayName("null 또는 빈 문자열은 암복호화하지 않고 그대로 반환한다")
    void keepNullAndEmptyValues() {
        assertThat(KisaSeedUtils.encrypt(null)).isNull();
        assertThat(KisaSeedUtils.encrypt("")).isEmpty();
        assertThat(KisaSeedUtils.decrypt(null)).isNull();
        assertThat(KisaSeedUtils.decrypt("")).isEmpty();
    }

    @Test
    @DisplayName("encryptYn이 Y이면 암호화하고 아니면 원본을 유지한다")
    void encryptValueByEncryptYn() {
        String encrypted = KisaSeedUtils.encryptValue("01012345678", "Y");

        assertThat(encrypted).isNotEqualTo("01012345678");
        assertThat(KisaSeedUtils.decrypt(encrypted)).isEqualTo("01012345678");
        assertThat(KisaSeedUtils.encryptValue("01012345678", "N")).isEqualTo("01012345678");
        assertThat(KisaSeedUtils.encryptValue(null, "N")).isEmpty();
    }
}
