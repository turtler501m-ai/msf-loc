package com.ktmmobile.msf.commons.client.support.config;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.boot.http.client.autoconfigure.HttpClientProperties;
import org.springframework.boot.http.client.autoconfigure.HttpClientSettingsPropertyMapper;
import org.springframework.boot.http.client.autoconfigure.service.HttpServiceClientProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.client.application.port.out.CommonGroupHttpClientInterceptorFactory;
import com.ktmmobile.msf.commons.client.application.port.out.GroupHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.application.port.out.HttpClientLogRecorder;
import com.ktmmobile.msf.commons.client.application.service.HttpClientLogFactory;
import com.ktmmobile.msf.commons.client.support.interceptor.ErrorHandlingHttpClientInterceptor;
import com.ktmmobile.msf.commons.client.support.interceptor.HttpClientRecordingInterceptor;
import com.ktmmobile.msf.commons.client.support.properties.HttpClientProxyProperties;
import com.ktmmobile.msf.commons.client.support.properties.HttpClientRecordingProperties;
import com.ktmmobile.msf.commons.common.logging.http.HttpLogProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 선언형 HTTP client 공통 설정
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class HttpClientConfig {

    private static final String DEFAULT_ERROR_MESSAGE = "외부 서비스 연동 오류";

    /**
     * HTTP client 호출 기록 생성기 Bean
     */
    @Bean
    HttpClientLogFactory httpClientLogFactory(
        ObjectProvider<ObjectMapper> objectMapperProvider,
        HttpLogProperties httpLogProperties,
        HttpClientRecordingProperties recordingProperties
    ) {
        ObjectMapper objectMapper = objectMapperProvider.getIfAvailable(ObjectMapper::new);
        return new HttpClientLogFactory(objectMapper, httpLogProperties, recordingProperties);
    }

    /**
     * HTTP client groupName을 포함한 오류 응답/예외 변환 interceptor factory Bean
     */
    @Bean
    CommonGroupHttpClientInterceptorFactory errorHandlingHttpClientInterceptorFactory(ObjectProvider<ObjectMapper> objectMapperProvider) {
        ObjectMapper objectMapper = objectMapperProvider.getIfAvailable(ObjectMapper::new);
        return groupName -> new ErrorHandlingHttpClientInterceptor(objectMapper, DEFAULT_ERROR_MESSAGE, groupName);
    }

    /**
     * HTTP client groupName을 포함한 요청/응답 기록 interceptor factory Bean
     */
    @Bean
    CommonGroupHttpClientInterceptorFactory httpClientRecordingInterceptorFactory(
        HttpClientRecordingProperties recordingProperties,
        HttpClientLogFactory httpClientLogFactory,
        ObjectProvider<HttpClientLogRecorder> httpClientLogRecorders
    ) {
        return groupName -> new HttpClientRecordingInterceptor(groupName, recordingProperties, httpClientLogFactory, httpClientLogRecorders);
    }

    /**
     * 선언형 HTTP service group별 RestClient 공통 설정 적용 Bean
     */
    @Bean
    RestClientHttpServiceGroupConfigurer httpClientConfigurer(
        HttpClientSettings httpClientSettings,
        HttpServiceClientProperties httpServiceClientProperties,
        ObjectProvider<SslBundles> sslBundles,
        HttpClientProxyProperties proxyProperties,
        ObjectProvider<CommonGroupHttpClientInterceptorFactory> commonGroupHttpClientInterceptorFactories,
        ObjectProvider<GroupHttpClientInterceptor> groupHttpClientInterceptors
    ) {
        return groups -> groups.forEachClient((group, clientBuilder) -> {
            // 전역 기본 설정에 그룹별 설정을 덮어써서 request factory 구성
            HttpClientProperties clientProperties = httpServiceClientProperties.get(group.name());
            HttpClientSettingsPropertyMapper settingsMapper = new HttpClientSettingsPropertyMapper(
                sslBundles.getIfAvailable(),
                httpClientSettings
            );
            HttpClientSettings clientSettings = settingsMapper.map(clientProperties);
            clientBuilder.requestFactory(createRequestFactory(group.name(), clientSettings, proxyProperties));
            addProxyLoggingInterceptor(clientBuilder, group.name(), proxyProperties);

            if (clientProperties != null) {
                if (StringUtils.hasText(clientProperties.getBaseUrl())) {
                    clientBuilder.baseUrl(clientProperties.getBaseUrl());
                }
                if (!CollectionUtils.isEmpty(clientProperties.getDefaultHeader())) {
                    clientProperties.getDefaultHeader()
                        .forEach((headerName, headerValues) -> clientBuilder.defaultHeader(headerName, headerValues.toArray(String[]::new)));
                }
            }

            // 그룹 전용 interceptor -> 그룹명을 아는 공통 interceptor 순서
            groupHttpClientInterceptors.orderedStream()
                .filter(interceptor -> interceptor.supports(group.name()))
                .forEach(clientBuilder::requestInterceptor);

            commonGroupHttpClientInterceptorFactories.orderedStream()
                .map(factory -> factory.create(group.name()))
                .forEach(clientBuilder::requestInterceptor);
        });
    }

    private ClientHttpRequestFactory createRequestFactory(
        String groupName,
        HttpClientSettings clientSettings,
        HttpClientProxyProperties proxyProperties
    ) {
        if (proxyProperties.supports(groupName)) {
            ProxySelector proxySelector = ProxySelector.of(new InetSocketAddress(proxyProperties.host(), proxyProperties.port()));
            return ClientHttpRequestFactoryBuilder.jdk()
                .withProxySelector(proxySelector)
                .build(clientSettings);
        }
        return ClientHttpRequestFactoryBuilder.detect().build(clientSettings);
    }

    private void addProxyLoggingInterceptor(
        RestClient.Builder clientBuilder,
        String groupName,
        HttpClientProxyProperties proxyProperties
    ) {
        if (!proxyProperties.supports(groupName)) {
            return;
        }

        clientBuilder.requestInterceptor((request, body, execution) -> {
            log.info("[HTTP-PROXY] group={}, method={}, target={}, proxy={}:{}",
                groupName,
                request.getMethod(),
                targetWithoutQuery(request.getURI()),
                proxyProperties.host(),
                proxyProperties.port()
            );
            return execution.execute(request, body);
        });
    }

    private String targetWithoutQuery(URI uri) {
        int port = uri.getPort();
        String host = port == -1 ? uri.getHost() : uri.getHost() + ":" + port;
        return uri.getScheme() + "://" + host + uri.getPath();
    }
}
