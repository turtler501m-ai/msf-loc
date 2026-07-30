package com.ktmmobile.msf.commons.masking.support.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MaskingProcessorUtils")
class MaskingProcessorUtilsTest {

    @Test
    @DisplayName("문자열 자릿수와 동일한 길이로 전체 마스킹한다")
    void mask() {
        assertThat(MaskingProcessorUtils.maskAll("12345")).isEqualTo("*****");
        assertThat(MaskingProcessorUtils.maskAll("홍길동")).isEqualTo("***");
        assertThat(MaskingProcessorUtils.maskAll("A😀B")).isEqualTo("***");
        assertThat(MaskingProcessorUtils.maskAll("")).isEmpty();
    }

    @Test
    @DisplayName("최대 자릿수를 넘지 않도록 전체 마스킹한다")
    void maskWithMaxLength() {
        assertThat(MaskingProcessorUtils.maskAll("12345", 3)).isEqualTo("***");
        assertThat(MaskingProcessorUtils.maskAll("12345", 10)).isEqualTo("*****");
        assertThat(MaskingProcessorUtils.maskAll("A😀B", 2)).isEqualTo("**");
        assertThat(MaskingProcessorUtils.maskAll("", 3)).isEmpty();
    }
}
