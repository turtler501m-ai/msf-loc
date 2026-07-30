package com.ktmmobile.msf.commons.common.messagesender.support.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "message-sender")
public record MessageSenderProperties(
    Kakao kakao
) {

    public record Kakao(String senderKey) {
    }
}
