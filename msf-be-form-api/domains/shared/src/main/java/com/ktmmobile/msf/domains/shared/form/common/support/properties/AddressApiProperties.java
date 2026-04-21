package com.ktmmobile.msf.domains.shared.form.common.support.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "address.api")
public record AddressApiProperties(
    String url,
    String key,
    String coordKey
) {
}
