package com.ktmmobile.msf.commons.file.support.config;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import com.ktmmobile.msf.commons.file.support.properties.ObjectStorageProperties;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "file.object-storage", name = "endpoint")
@Configuration(proxyBeanMethods = false)
public class ObjectStorageConfig {

    private final ObjectStorageProperties objectStorageProperties;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
            .endpointOverride(URI.create(objectStorageProperties.endpoint()))
            .region(Region.of(objectStorageProperties.region()))
            .credentialsProvider(createCredentialsProvider())
            .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
            .overrideConfiguration(ClientOverrideConfiguration.builder()
                .apiCallTimeout(objectStorageProperties.configurations().apiCallTimeout())
                .build())
            .httpClient(ApacheHttpClient.builder()
                .maxConnections(objectStorageProperties.configurations().maxConnections())
                .connectionTimeout(objectStorageProperties.configurations().connectionTimeout())
                .socketTimeout(objectStorageProperties.configurations().socketTimeout())
                .build())
            .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
            .endpointOverride(URI.create(objectStorageProperties.endpoint()))
            .region(Region.of(objectStorageProperties.region()))
            .credentialsProvider(createCredentialsProvider())
            .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
            .build();
    }

    private StaticCredentialsProvider createCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(
            objectStorageProperties.credentials().accessKey(),
            objectStorageProperties.credentials().secretKey())
        );
    }
}
