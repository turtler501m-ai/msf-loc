package com.ktmmobile.msf.commons.client.support.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.boot.http.client.autoconfigure.HttpClientProperties;
import org.springframework.boot.http.client.autoconfigure.HttpClientSettingsPropertyMapper;
import org.springframework.boot.http.client.autoconfigure.service.HttpServiceClientProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;

import com.ktmmobile.msf.commons.client.application.port.out.CommonGroupHttpClientInterceptorFactory;
import com.ktmmobile.msf.commons.client.application.port.out.CommonHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.application.port.out.GroupHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.application.port.out.HttpClientLogRecorder;
import com.ktmmobile.msf.commons.client.application.service.HttpClientLogFactory;
import com.ktmmobile.msf.commons.client.support.interceptor.ErrorHandlingHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.support.interceptor.HttpClientLoggingInterceptor;
import com.ktmmobile.msf.commons.client.support.properties.HttpClientLoggingProperties;

@Configuration(proxyBeanMethods = false)
public class HttpClientConfig {

    private static final String DEFAULT_ERROR_MESSAGE = "외부 서비스 연동 오류";

    @Bean
    HttpClientLogFactory httpClientLogFactory(
        ObjectProvider<ObjectMapper> objectMapperProvider,
        HttpClientLoggingProperties loggingProperties
    ) {
        ObjectMapper objectMapper = objectMapperProvider.getIfAvailable(ObjectMapper::new);
        return new HttpClientLogFactory(objectMapper, loggingProperties);
    }

    @Bean
    CommonHttpClientInterceptor errorHandlingHttpClientInterceptor(ObjectProvider<ObjectMapper> objectMapperProvider) {
        ObjectMapper objectMapper = objectMapperProvider.getIfAvailable(ObjectMapper::new);
        return new ErrorHandlingHttpClientInterceptor(objectMapper, DEFAULT_ERROR_MESSAGE);
    }

    @Bean
    CommonGroupHttpClientInterceptorFactory httpClientLoggingInterceptorFactory(
        HttpClientLoggingProperties loggingProperties,
        HttpClientLogFactory httpClientLogFactory,
        HttpClientLogRecorder httpClientLogRecorder
    ) {
        return groupName -> new HttpClientLoggingInterceptor(groupName, loggingProperties, httpClientLogFactory, httpClientLogRecorder);
    }

    @Bean
    RestClientHttpServiceGroupConfigurer httpClientConfigurer(
        HttpClientSettings httpClientSettings,
        HttpServiceClientProperties httpServiceClientProperties,
        ObjectProvider<SslBundles> sslBundles,
        ObjectProvider<CommonHttpClientInterceptor> commonHttpClientInterceptors,
        ObjectProvider<CommonGroupHttpClientInterceptorFactory> commonGroupHttpClientInterceptorFactories,
        ObjectProvider<GroupHttpClientInterceptor> groupHttpClientInterceptors
    ) {
        return groups -> groups.forEachClient((group, clientBuilder) -> {
            HttpClientProperties clientProperties = httpServiceClientProperties.get(group.name());
            HttpClientSettingsPropertyMapper settingsMapper = new HttpClientSettingsPropertyMapper(
                sslBundles.getIfAvailable(),
                httpClientSettings
            );
            HttpClientSettings clientSettings = settingsMapper.map(clientProperties);
            clientBuilder.requestFactory(ClientHttpRequestFactoryBuilder.detect().build(clientSettings));

            if (clientProperties != null) {
                if (StringUtils.hasText(clientProperties.getBaseUrl())) {
                    clientBuilder.baseUrl(clientProperties.getBaseUrl());
                }
                if (!CollectionUtils.isEmpty(clientProperties.getDefaultHeader())) {
                    clientProperties.getDefaultHeader()
                        .forEach((headerName, headerValues) -> clientBuilder.defaultHeader(headerName, headerValues.toArray(String[]::new)));
                }
            }

            commonHttpClientInterceptors.orderedStream()
                .forEach(clientBuilder::requestInterceptor);

            commonGroupHttpClientInterceptorFactories.orderedStream()
                .map(factory -> factory.create(group.name()))
                .forEach(clientBuilder::requestInterceptor);

            groupHttpClientInterceptors.orderedStream()
                .filter(interceptor -> interceptor.supports(group.name()))
                .forEach(clientBuilder::requestInterceptor);
        });
    }
}
