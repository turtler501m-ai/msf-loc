package com.ktmmobile.msf.domains.koiidcard.adapter.client.httpClient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

import com.ktmmobile.msf.domains.externalclient.common.code.ClientConst;


@ImportHttpServices(group = ClientConst.SERVICE_NAME_KOI_IDCARD, types = {
    KoiIdCardHttpClient.class
})
@Configuration(proxyBeanMethods = false)
public class KoiIdCardHttpClientConfig {
}
