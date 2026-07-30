package com.ktmmobile.msf.domains.externalclient.imagesystem.adapter.client.httpclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

import static com.ktmmobile.msf.domains.externalclient.common.code.ClientConst.SERVICE_NAME_IMAGE_SYSTEM;

/**
 * 이미지 시스템 선언형 HTTP client 등록 설정
 */
@ImportHttpServices(group = SERVICE_NAME_IMAGE_SYSTEM, types = ImageSystemHttpClient.class)
@Configuration(proxyBeanMethods = false)
class ImageSystemClientConfig {
}
