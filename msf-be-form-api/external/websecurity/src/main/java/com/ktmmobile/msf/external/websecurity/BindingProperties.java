package com.ktmmobile.msf.external.websecurity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "spring.binding")
public record BindingProperties(
    @DefaultValue("true") boolean stringTrim,
    @DefaultValue("false") boolean stringEmptyAsNull
) {
}
