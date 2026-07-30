package com.ktmmobile.msf.domains.accesstrace.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("AccessTrace 설정")
class AccessTracePropertiesTest {

    @Test
    @DisplayName("API 경로 prefix 앞뒤 슬래시를 보정한다")
    void normalizeApiPathPrefix() {
        AccessTraceProperties properties = new AccessTraceProperties(
            true,
            "api",
            null,
            null,
            null
        );

        assertThat(properties.apiPathPrefix()).isEqualTo("/api/");
    }

    @Test
    @DisplayName("API 경로 prefix는 필수이다")
    void requiredApiPathPrefix() {
        assertThatThrownBy(() -> new AccessTraceProperties(
            true,
            null,
            null,
            null,
            null
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("access-trace.api-path-prefix is required");
    }
}
