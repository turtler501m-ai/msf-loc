package com.ktmmobile.msf.domains.shared.common.address.adapter.client.httpclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

import static com.ktmmobile.msf.domains.externalclient.common.code.ClientConst.SERVICE_NAME_JUSO;

@ImportHttpServices(group = SERVICE_NAME_JUSO, types = JusoHttpClient.class)
@Configuration(proxyBeanMethods = false)
public class JusoHttpClientConfig {
}
