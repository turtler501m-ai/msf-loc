package com.ktmmobile.msf.domains.externalclient.nice.adapter.client.httpclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

import static com.ktmmobile.msf.domains.externalclient.common.code.ClientConst.SERVICE_NAME_NICE_API;

/**
 * NICE API 선언형 HTTP client 등록 설정
 */
@Configuration(proxyBeanMethods = false)
@ImportHttpServices(group = SERVICE_NAME_NICE_API, types = NiceApiHttpClient.class)
class NiceApiClientConfig {
}
