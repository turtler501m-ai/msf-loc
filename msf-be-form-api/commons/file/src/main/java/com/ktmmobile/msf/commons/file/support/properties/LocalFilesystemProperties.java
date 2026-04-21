package com.ktmmobile.msf.commons.file.support.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file.local-filesystem")
public record LocalFilesystemProperties(
    String basePath
) {
}
