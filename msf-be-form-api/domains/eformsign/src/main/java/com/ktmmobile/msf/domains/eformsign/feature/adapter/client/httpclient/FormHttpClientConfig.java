package com.ktmmobile.msf.domains.eformsign.feature.adapter.client.httpclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(group = "eformsign", basePackageClasses = FormHttpClient.class)
@Configuration(proxyBeanMethods = false)
public class FormHttpClientConfig {
}
