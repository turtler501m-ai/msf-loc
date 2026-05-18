package com.ktmmobile.msf.domains.koiocr.adapter.client.httpclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(group = "koi-ocr", basePackageClasses = {
    KoiOcrHttpClient.class
})
@Configuration(proxyBeanMethods = false)
public class KoiOcrHttpClientConfig {
}
