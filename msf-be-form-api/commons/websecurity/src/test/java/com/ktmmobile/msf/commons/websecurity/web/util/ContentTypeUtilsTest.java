package com.ktmmobile.msf.commons.websecurity.web.util;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Content-Type 유틸")
class ContentTypeUtilsTest {

    @Test
    @DisplayName("Content-Type charset을 조회한다")
    void resolveCharsetFromContentType() {
        assertThat(ContentTypeUtils.contentCharset("application/json; charset=EUC-KR")).hasToString("EUC-KR");
    }

    @Test
    @DisplayName("charset이 없거나 유효하지 않으면 UTF-8을 사용한다")
    void useUtf8WhenCharsetIsMissingOrInvalid() {
        assertThat(ContentTypeUtils.contentCharset("application/json")).isEqualTo(StandardCharsets.UTF_8);
        assertThat(ContentTypeUtils.contentCharset("application/json; charset=unknown-charset")).isEqualTo(StandardCharsets.UTF_8);
        assertThat(ContentTypeUtils.contentCharset(null)).isEqualTo(StandardCharsets.UTF_8);
    }
}
