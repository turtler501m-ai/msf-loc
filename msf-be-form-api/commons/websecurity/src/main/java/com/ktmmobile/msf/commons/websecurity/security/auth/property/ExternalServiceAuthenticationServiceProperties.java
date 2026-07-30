package com.ktmmobile.msf.commons.websecurity.security.auth.property;

import java.util.List;

/**
 * 외부 서비스 인증 호출 주체별 설정
 */
public record ExternalServiceAuthenticationServiceProperties(
    String apiKey,
    String userName,
    List<String> allowedIpAddresses
) {

    public ExternalServiceAuthenticationServiceProperties {
        allowedIpAddresses = allowedIpAddresses == null ? List.of() : List.copyOf(allowedIpAddresses);
    }
}
