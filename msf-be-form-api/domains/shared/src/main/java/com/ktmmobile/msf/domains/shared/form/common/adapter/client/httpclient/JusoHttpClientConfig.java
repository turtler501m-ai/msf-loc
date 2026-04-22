package com.ktmmobile.msf.domains.shared.form.common.adapter.client.httpclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@ImportHttpServices(group = JusoHttpClientConst.GROUP_NAME, basePackageClasses = {
    JusoHttpClient.class
})
@Configuration(proxyBeanMethods = false)
public class JusoHttpClientConfig {
}
