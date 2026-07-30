package com.ktmmobile.msf.commons.common.messagesender.support.property;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class MessageSenderPropertiesTest {

    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(MessageSenderProperties.class)
    static class TestConfiguration {
    }

    ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withUserConfiguration(TestConfiguration.class);

    @Test
    void bindKakaoSenderKey() {
        contextRunner.withPropertyValues("message-sender.kakao.sender-key=test-sender-key")
            .run(context -> {
                MessageSenderProperties properties = context.getBean(MessageSenderProperties.class);

                assertThat(properties.kakao()).isNotNull();
                assertThat(properties.kakao().senderKey()).isEqualTo("test-sender-key");
            });
    }
}
