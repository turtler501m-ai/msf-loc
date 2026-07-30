package com.ktmmobile.msf.domains.externalclient.common.property;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("local")
@TestPropertySource(properties = "spring.config.import=classpath:application-common.yaml,classpath:application-client.yaml,classpath:application-external-client.yaml")
@DisplayName("외부 연동 서비스 설정")
@SpringJUnitConfig(
    classes = ExternalServicePropertiesTest.TestConfiguration.class,
    initializers = ConfigDataApplicationContextInitializer.class
)
class ExternalServicePropertiesTest {

    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(ExternalServiceProperties.class)
    static class TestConfiguration {
    }

    @Autowired
    private ExternalServiceProperties properties;

    @Test
    @DisplayName("services가 null이면 빈 맵으로 초기화한다")
    void initializeEmptyServicesWhenNull() {
        ExternalServiceProperties manualProperties = new ExternalServiceProperties(null);

        assertThat(manualProperties.services()).isEmpty();
        assertThat(manualProperties.service("msp-prx")).isNull();
    }

    @Test
    @DisplayName("서비스 이름으로 외부 서비스 설정을 조회한다")
    void findServiceByName() {
        ServiceProperties service = new ServiceProperties(
            "https://d-smartform.ktmmobile.com/_prx",
            Map.of("health-path", "/actuator/health")
        );
        ExternalServiceProperties manualProperties = new ExternalServiceProperties(Map.of("msp-prx", service));

        assertThat(manualProperties.service("msp-prx")).isSameAs(service);
        assertThat(manualProperties.service("msp-prx").baseUrl()).isEqualTo("https://d-smartform.ktmmobile.com/_prx");
        assertThat(manualProperties.service("msp-prx").property("healthPath")).isEqualTo("/actuator/health");
        assertThat(manualProperties.service("nice-api")).isNull();
    }

    @Test
    @DisplayName("services는 방어 복사되어 외부 변경과 직접 수정을 허용하지 않는다")
    void servicesAreDefensivelyCopied() {
        Map<String, ServiceProperties> services = new HashMap<>();
        services.put("msp-prx", new ServiceProperties("https://d-smartform.ktmmobile.com/_prx", Map.of()));

        ExternalServiceProperties manualProperties = new ExternalServiceProperties(services);
        services.put("nice-api", new ServiceProperties("https://secure.nuguya.com", Map.of()));

        assertThat(manualProperties.services()).containsOnlyKeys("msp-prx");
        assertThatThrownBy(() -> manualProperties.services().put("juso", new ServiceProperties("https://business.juso.go.kr", Map.of())))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("외부 서비스 설정을 바인딩한다")
    void bindExternalServicesFromYaml() {
        assertThat(properties.services()).containsKeys("msp-prx", "nice-api", "juso");
        assertThat(properties.service("msp-prx").baseUrl()).isEqualTo("https://d-smartform.ktmmobile.com/_prx");
        assertThat(properties.service("nice-api").baseUrl()).isEqualTo("https://secure.nuguya.com");
        assertThat(properties.service("juso").baseUrl()).isEqualTo("https://business.juso.go.kr");
    }

    @Test
    @DisplayName("외부 서비스 암호화 속성을 바인딩한다")
    void bindExternalEncryptedPropertiesFromYaml() {
        ServiceProperties niceApi = properties.service("nice-api");
        ServiceProperties juso = properties.service("juso");

        assertThat(niceApi).isNotNull();
        assertThat(niceApi.properties()).containsKeys("svc-pwd");
        assertThat(niceApi.property("svcPwd")).startsWith("ENC(");

        assertThat(juso).isNotNull();
        assertThat(juso.properties()).containsKeys("api-key", "coord-api-key");
        assertThat(juso.property("apiKey")).startsWith("ENC(");
        assertThat(juso.property("coordApiKey")).startsWith("ENC(");
    }
}
