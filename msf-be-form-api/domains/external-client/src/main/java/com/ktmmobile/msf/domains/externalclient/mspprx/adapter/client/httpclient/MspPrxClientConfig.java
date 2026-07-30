package com.ktmmobile.msf.domains.externalclient.mspprx.adapter.client.httpclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

import static com.ktmmobile.msf.domains.externalclient.common.code.ClientConst.SERVICE_NAME_MSP_PRX;

@Configuration(proxyBeanMethods = false)
@ImportHttpServices(group = SERVICE_NAME_MSP_PRX, types = MspPrxHttpClient.class)
class MspPrxClientConfig {
}
