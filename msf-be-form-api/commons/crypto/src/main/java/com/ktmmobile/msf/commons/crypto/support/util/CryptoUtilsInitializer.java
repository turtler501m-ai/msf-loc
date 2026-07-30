package com.ktmmobile.msf.commons.crypto.support.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.crypto.support.processor.TextEncryptorRegistry;

/**
 * Spring Bean으로 등록된 TextEncryptorRegistry를 CryptoUtils에 초기화
 */
@RequiredArgsConstructor
@Component
@ConditionalOnBean(TextEncryptorRegistry.class)
public class CryptoUtilsInitializer implements InitializingBean {

    private final TextEncryptorRegistry textEncryptors;

    @Override
    public void afterPropertiesSet() {
        CryptoUtils.initialize(textEncryptors);
    }
}
