package com.ktmmobile.msf.commons.crypto.support.config;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ktmmobile.msf.commons.crypto.adapter.repository.mybatis.interceptor.EncryptedFieldMyBatisInterceptor;
import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.processor.AesGcmTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.FieldCryptoProcessor;
import com.ktmmobile.msf.commons.crypto.support.processor.LegacyAes256CbcTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.LegacyKisaSeedCbcTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.SearchableAesGcmTextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.TextEncryptor;
import com.ktmmobile.msf.commons.crypto.support.processor.TextEncryptorRegistry;
import com.ktmmobile.msf.commons.crypto.support.properties.CryptoProperties;

/**
 * crypto 모듈 암복호화 Bean 구성
 *
 * <p>{@code crypto.field.enabled=true} 기준 필드 암복호화 처리기 등록
 * AES-GCM 기본 등록
 * 키/IV 설정 기반 레거시 알고리즘 추가 등록</p>
 */
@Configuration(proxyBeanMethods = false)
public class CryptoConfig {

    /** 문자열 필드 암복호화 처리기 모음 */
    @Bean
    @ConditionalOnProperty(prefix = "crypto.field", name = "enabled", havingValue = "true")
    public TextEncryptorRegistry textEncryptorRegistry(CryptoProperties properties) {
        CryptoProperties.Field field = properties.field();
        var textEncryptors = new EnumMap<FieldCryptoAlgorithm, TextEncryptor>(FieldCryptoAlgorithm.class);
        textEncryptors.put(FieldCryptoAlgorithm.AES_GCM,
            new AesGcmTextEncryptor(field.key(), field.prefix(), field.keyId(), previousAesGcmKeys(field)));
        textEncryptors.put(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE,
            new SearchableAesGcmTextEncryptor(field.key(), field.prefix(), field.keyId(), previousAesGcmKeys(field)));
        if (field.legacyAes256().configured()) {
            textEncryptors.put(FieldCryptoAlgorithm.LEGACY_AES256_CBC,
                new LegacyAes256CbcTextEncryptor(field.legacyAes256().key(), field.legacyAes256().iv()));
        }
        if (properties.kisaSeed().configured()) {
            textEncryptors.put(FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC,
                new LegacyKisaSeedCbcTextEncryptor());
        }
        return new TextEncryptorRegistry(textEncryptors);
    }

    /** 신규 필드 암복호화 기본 처리기 */
    @Bean
    @ConditionalOnProperty(prefix = "crypto.field", name = "enabled", havingValue = "true")
    public TextEncryptor fieldTextEncryptor(TextEncryptorRegistry textEncryptorRegistry) {
        return textEncryptorRegistry.defaultEncryptor();
    }

    /** @Encrypted 필드 탐색 및 암복호화 처리기 */
    @Bean
    @ConditionalOnProperty(prefix = "crypto.field", name = "enabled", havingValue = "true")
    public FieldCryptoProcessor fieldCryptoProcessor(TextEncryptorRegistry textEncryptorRegistry) {
        return new FieldCryptoProcessor(textEncryptorRegistry);
    }

    /** MyBatis 파라미터/조회 결과 필드 암복호화 인터셉터 */
    @Bean
    @ConditionalOnProperty(prefix = "crypto.field", name = "enabled", havingValue = "true")
    public Interceptor encryptedFieldMyBatisInterceptor(FieldCryptoProcessor fieldCryptoProcessor) {
        return new EncryptedFieldMyBatisInterceptor(fieldCryptoProcessor);
    }

    private Map<String, String> previousAesGcmKeys(CryptoProperties.Field field) {
        return field.previousKeys()
            .stream()
            // keyId와 key가 모두 있는 이전 키만 사용
            .filter(CryptoProperties.AesGcmKey::configured)
            .collect(Collectors.toUnmodifiableMap(
                CryptoProperties.AesGcmKey::keyId,
                CryptoProperties.AesGcmKey::key));
    }
}
