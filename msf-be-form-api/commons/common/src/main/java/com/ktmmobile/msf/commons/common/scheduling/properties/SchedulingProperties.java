package com.ktmmobile.msf.commons.common.scheduling.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "scheduling")
public record SchedulingProperties(
    @DefaultValue("true") boolean enabled,
    @DefaultValue Map<String, Enabled> groups
) {

    public boolean containsGroup(String name) {
        return groups.containsKey(name);
    }

    public boolean isGroupEnabled(String name) {
        if (!containsGroup(name)) {
            return true;
        }
        Enabled group = groups.get(name);
        return group.enabled;
    }


    public record Enabled(boolean enabled) { }
}
