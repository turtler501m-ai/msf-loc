package com.ktmmobile.msf.commons.websecurity.security.auth.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UserType;

@ConfigurationProperties(prefix = "spring.security.jwt")
public record JwtSecurityProperties(
    String secretKey,
    String issuer,
    List<String> allowedUserTypes
) {

    public JwtSecurityProperties {
        allowedUserTypes = allowedUserTypes == null ? List.of() : List.copyOf(allowedUserTypes);
    }

    public boolean allows(UserType userType) {
        if (allowedUserTypes.isEmpty()) {
            return true;
        }
        return allowedUserTypes.stream()
            .filter(StringUtils::hasText)
            .anyMatch(allowedUserType -> matches(allowedUserType, userType));
    }

    private boolean matches(String allowedUserType, UserType userType) {
        return allowedUserType.equalsIgnoreCase(userType.name())
            || allowedUserType.equalsIgnoreCase(userType.getCode())
            || allowedUserType.equalsIgnoreCase(userType.getSimpleCode());
    }
}
