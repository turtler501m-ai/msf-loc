package com.ktmmobile.msf.commons.crypto.support.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.crypto.support.properties.CryptoProperties;

/**
 * Spring 프로퍼티로 설정된 KISA SEED-CBC 키와 IV를 KisaSeedUtils에 초기화
 */
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "crypto.kisa-seed", name = {"key", "iv"})
@Component
public class KisaSeedUtilsInitializer implements InitializingBean {

    private final CryptoProperties properties;

    @Override
    public void afterPropertiesSet() {
        CryptoProperties.KisaSeed kisaSeed = properties.kisaSeed();
        KisaSeedUtils.initialize(kisaSeed.key(), kisaSeed.iv());
    }
}
