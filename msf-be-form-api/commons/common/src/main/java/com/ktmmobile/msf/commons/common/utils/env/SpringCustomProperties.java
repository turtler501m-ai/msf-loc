package com.ktmmobile.msf.commons.common.utils.env;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring")
public record SpringCustomProperties(
    String basePackage,
    Application application
) {

    public String applicationName() {
        return application.name();
    }

    public String applicationNameAbbreviated() {
        return application.nameAbbr();
    }

    public record Application(
        String name,
        String nameAbbr
    ) {
    }
}
