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
@DisplayName("내부 연동 서비스 설정")
@SpringJUnitConfig(
    classes = InternalServicePropertiesTest.TestConfiguration.class,
    initializers = ConfigDataApplicationContextInitializer.class
)
class InternalServicePropertiesTest {

    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(InternalServiceProperties.class)
    static class TestConfiguration {
    }

    @Autowired
    private InternalServiceProperties properties;

    @Test
    @DisplayName("services가 null이면 빈 맵으로 초기화한다")
    void initializeEmptyServicesWhenNull() {
        InternalServiceProperties manualProperties = new InternalServiceProperties(null);

        assertThat(manualProperties.services()).isEmpty();
        assertThat(manualProperties.service("form-api")).isNull();
    }

    @Test
    @DisplayName("서비스 이름으로 내부 서비스 설정을 조회한다")
    void findServiceByName() {
        ServiceProperties service = new ServiceProperties(
            "http://tasks.msf-form-api:8080",
            Map.of("health-path", "/actuator/health")
        );
        InternalServiceProperties manualProperties = new InternalServiceProperties(Map.of("form-api", service));

        assertThat(manualProperties.service("form-api")).isSameAs(service);
        assertThat(manualProperties.service("form-api").baseUrl()).isEqualTo("http://tasks.msf-form-api:8080");
        assertThat(manualProperties.service("form-api").property("healthPath")).isEqualTo("/actuator/health");
        assertThat(manualProperties.service("admin-api")).isNull();
    }

    @Test
    @DisplayName("services는 방어 복사되어 외부 변경과 직접 수정을 허용하지 않는다")
    void servicesAreDefensivelyCopied() {
        Map<String, ServiceProperties> services = new HashMap<>();
        services.put("form-api", new ServiceProperties("http://form-api", Map.of()));

        InternalServiceProperties manualProperties = new InternalServiceProperties(services);
        services.put("admin-api", new ServiceProperties("http://admin-api", Map.of()));

        assertThat(manualProperties.services()).containsOnlyKeys("form-api");
        assertThatThrownBy(() -> manualProperties.services().put("batch", new ServiceProperties("http://batch", Map.of())))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("내부 서비스 설정을 바인딩한다")
    void bindInternalServicesFromYaml() {
        assertThat(properties.services()).containsKeys("form-api", "admin-api", "batch", "koi-ocr", "koi-idcard", "eformsign");
        assertThat(properties.service("form-api").baseUrl()).isEqualTo("http://localhost:8080");
        assertThat(properties.service("admin-api").baseUrl()).isEqualTo("http://localhost:8180");
        assertThat(properties.service("batch").baseUrl()).isEqualTo("http://localhost:8280");
        assertThat(properties.service("koi-ocr").baseUrl()).isEqualTo("https://d-smartform.ktmmobile.com/_ocr");
        assertThat(properties.service("koi-idcard").baseUrl()).isEqualTo("https://d-smartform-idcard.ktmmobile.com");
    }

    @Test
    @DisplayName("내부 eformsign 암호화 속성을 바인딩한다")
    void bindInternalEformsignPropertiesFromYaml() {
        ServiceProperties eformsign = properties.service("eformsign");

        assertThat(eformsign).isNotNull();
        assertThat(eformsign.baseUrl()).isEqualTo("https://d-smartform-eform.ktmmobile.com");
        assertThat(eformsign.properties()).containsKeys("member-id", "api-key", "private-key");
        assertThat(eformsign.property("memberId")).isEqualTo("test@forcs.com");
        assertThat(eformsign.property("apiKey")).startsWith("ENC(");
        assertThat(eformsign.property("privateKey")).startsWith("ENC(");
    }
}
