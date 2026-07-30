package com.ktmmobile.msf.domains.externalclient.mspprx.application.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Builder;
import lombok.Singular;

import org.springframework.util.StringUtils;

@Builder
public record MspPrxJsonRequest(
    @Singular("property")
    Map<String, Object> properties,
    ServiceAlterTraceRequest serviceAlterTrace
) {

    public MspPrxJsonRequest {
        Map<String, Object> filteredProperties = new LinkedHashMap<>();
        if (properties != null) {
            properties.forEach((key, value) -> {
                if (StringUtils.hasText(key) && value != null) {
                    filteredProperties.put(key, value);
                }
            });
        }
        properties = Collections.unmodifiableMap(filteredProperties);
    }

    public Map<String, Object> toJsonBody() {
        return properties;
    }

    public Optional<Object> property(String name) {
        return Optional.ofNullable(properties.get(name));
    }
}
