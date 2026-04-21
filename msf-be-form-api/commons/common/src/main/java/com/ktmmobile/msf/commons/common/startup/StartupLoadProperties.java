package com.ktmmobile.msf.commons.common.startup;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "startup.load")
public class StartupLoadProperties {

    private Set<String> exclude = new LinkedHashSet<>();

    public boolean isExcluded(String key) {
        return exclude.stream()
            .anyMatch(excludedKey -> excludedKey != null && excludedKey.equalsIgnoreCase(key));
    }
}
