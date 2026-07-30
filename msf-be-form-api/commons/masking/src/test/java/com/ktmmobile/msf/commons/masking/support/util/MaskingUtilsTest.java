package com.ktmmobile.msf.commons.masking.support.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MaskingUtils")
class MaskingUtilsTest {

    @Test
    @DisplayName("문자열 자릿수와 동일한 길이로 전체 마스킹한다")
    void mask() {
        assertThat(MaskingUtils.mask("12345")).isEqualTo("*****");
        assertThat(MaskingUtils.mask("홍길동")).isEqualTo("***");
        assertThat(MaskingUtils.mask("A😀B")).isEqualTo("***");
        assertThat(MaskingUtils.mask("")).isEmpty();
        assertThat(MaskingUtils.mask(null)).isNull();
    }

    @Test
    @DisplayName("최대 자릿수를 넘지 않도록 전체 마스킹한다")
    void maskWithMaxLength() {
        assertThat(MaskingUtils.mask("12345", 3)).isEqualTo("***");
        assertThat(MaskingUtils.mask("12345", 10)).isEqualTo("*****");
        assertThat(MaskingUtils.mask("A😀B", 2)).isEqualTo("**");
        assertThat(MaskingUtils.mask("", 3)).isEmpty();
        assertThat(MaskingUtils.mask(null, 3)).isNull();
    }

    @Test
    @DisplayName("지정한 타입의 마스킹 정책을 적용한다")
    void maskByType() {
        assertThat(MaskingUtils.mask("123-45-67890", MaskingType.BUSINESS_REGISTRATION_NUMBER))
            .isEqualTo("123-**-*****");
        assertThat(MaskingUtils.mask("110111-1234567", MaskingType.CORPORATE_REGISTRATION_NUMBER))
            .isEqualTo("110111-*******");
        assertThat(MaskingUtils.mask("12-34-567890-12", MaskingType.DRIVER_LICENSE_NUMBER))
            .isEqualTo("12-**-******-12");
        assertThat(MaskingUtils.mask(null, MaskingType.NAME)).isNull();
    }
}
