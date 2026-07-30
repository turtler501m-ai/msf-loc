package com.ktmmobile.msf.commons.client.support.properties;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("HTTP client proxy 설정")
class HttpClientProxyPropertiesTest {

    @Test
    @DisplayName("proxy가 비활성화되어 있으면 어떤 group도 지원하지 않는다")
    void supportNoGroupWhenDisabled() {
        HttpClientProxyProperties properties = new HttpClientProxyProperties(false, null, null, List.of("nice-api"));

        assertThat(properties.supports("nice-api")).isFalse();
    }

    @Test
    @DisplayName("proxy가 활성화되어 있으면 설정된 group만 지원한다")
    void supportConfiguredGroupsWhenEnabled() {
        HttpClientProxyProperties properties = new HttpClientProxyProperties(
            true,
            "172.28.130.8",
            3128,
            List.of("nice-api", "juso")
        );

        assertThat(properties.supports("nice-api")).isTrue();
        assertThat(properties.supports("juso")).isTrue();
        assertThat(properties.supports("form-api")).isFalse();
    }

    @Test
    @DisplayName("groups는 방어 복사한다")
    void groupsAreDefensivelyCopied() {
        List<String> groups = new ArrayList<>();
        groups.add("nice-api");

        HttpClientProxyProperties properties = new HttpClientProxyProperties(true, "172.28.130.8", 3128, groups);
        groups.add("juso");

        assertThat(properties.groups()).containsOnly("nice-api");
        assertThatThrownBy(() -> properties.groups().add("juso"))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("proxy 활성화 시 host와 port를 필수로 검증한다")
    void validateHostAndPortWhenEnabled() {
        assertThatThrownBy(() -> new HttpClientProxyProperties(true, null, 3128, List.of("nice-api")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("http-client.proxy.host is required when proxy is enabled");

        assertThatThrownBy(() -> new HttpClientProxyProperties(true, "172.28.130.8", 0, List.of("nice-api")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("http-client.proxy.port must be between 1 and 65535 when proxy is enabled");
    }
}
