package com.ktmmobile.msf.domains.koiocr.adapter.client.httpclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

import com.ktmmobile.msf.domains.externalclient.common.code.ClientConst;

@ImportHttpServices(group = ClientConst.SERVICE_NAME_KOI_OCR, types = {
    KoiOcrHttpClient.class
})
@Configuration(proxyBeanMethods = false)
public class KoiOcrHttpClientConfig {
}
