package com.ktmmobile.msf.commons.file.support.properties;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file.object-storage")
public record ObjectStorageProperties(
    String endpoint,
    String region,
    String basePath,
    Credentials credentials,
    String bucket,
    Configurations configurations
) {

    public record Credentials(
        String accessKey,
        String secretKey
    ) {
    }

    public record Configurations(
        int maxConnections,
        Duration connectionTimeout,
        Duration socketTimeout,
        Duration apiCallTimeout
    ) {
    }
}
