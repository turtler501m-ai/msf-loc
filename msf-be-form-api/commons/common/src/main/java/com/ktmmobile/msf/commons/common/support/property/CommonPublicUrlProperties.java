package com.ktmmobile.msf.commons.common.support.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 공통 공개 URL 설정
 *
 * @param formService 스마트서식지 서비스 Public URL
 * @param adminService 관리자 서비스 Public URL
 */
@ConfigurationProperties(prefix = "common.public-url")
public record CommonPublicUrlProperties(
    String formService,
    String adminService
) {
}
