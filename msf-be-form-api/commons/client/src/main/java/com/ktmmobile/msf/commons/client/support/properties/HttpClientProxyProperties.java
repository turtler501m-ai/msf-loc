package com.ktmmobile.msf.commons.client.support.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * HTTP client forward proxy 정책
 */
@ConfigurationProperties(prefix = "http-client.proxy")
public record HttpClientProxyProperties(
    boolean enabled,
    String host,
    Integer port,
    List<String> groups
) {

    /**
     * HTTP client proxy 정책 기본값 및 필수값 검증
     */
    public HttpClientProxyProperties {
        groups = groups == null ? List.of() : List.copyOf(groups);
        if (enabled) {
            if (!StringUtils.hasText(host)) {
                throw new IllegalArgumentException("http-client.proxy.host is required when proxy is enabled");
            }
            if (port == null || port < 1 || port > 65535) {
                throw new IllegalArgumentException("http-client.proxy.port must be between 1 and 65535 when proxy is enabled");
            }
        }
    }

    /**
     * HTTP client group이 proxy 적용 대상인지 확인
     */
    public boolean supports(String groupName) {
        return enabled && groups.contains(groupName);
    }
}
