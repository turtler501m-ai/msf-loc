package com.ktmmobile.msf.domains.externalclient.common.property;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ktmmobile.msf.commons.client.support.properties.HttpClientProxyProperties;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("prd")
@TestPropertySource(properties = "spring.config.import=classpath:application-common.yaml,classpath:application-client.yaml,classpath:application-external-client.yaml")
@DisplayName("외부 연동 HTTP client proxy 설정")
@SpringJUnitConfig(
    classes = ExternalClientHttpClientProxyPropertiesTest.TestConfiguration.class,
    initializers = ConfigDataApplicationContextInitializer.class
)
class ExternalClientHttpClientProxyPropertiesTest {

    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(HttpClientProxyProperties.class)
    static class TestConfiguration {
    }

    @Autowired
    private HttpClientProxyProperties properties;

    @Test
    @DisplayName("PRD 환경에서 주소 검색 API group은 proxy 대상이다")
    void jusoUsesProxyInPrd() {
        assertThat(properties.enabled()).isTrue();
        assertThat(properties.host()).isEqualTo("172.28.10.183");
        assertThat(properties.port()).isEqualTo(3128);
        assertThat(properties.groups()).contains("nice-api", "juso");
        assertThat(properties.supports("juso")).isTrue();
    }
}
