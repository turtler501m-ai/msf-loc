package com.ktmmobile.msf.commons.file.support.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "file.policy")
public record FilePolicyProperties(
    DataSize maxFileSize,
    List<String> allowedExtensions,
    List<String> allowedMimeTypes
) implements FilePolicy {
}
