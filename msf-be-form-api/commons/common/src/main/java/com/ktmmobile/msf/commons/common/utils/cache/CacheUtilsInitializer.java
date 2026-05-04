package com.ktmmobile.msf.commons.common.utils.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.utils.env.SpringCustomProperties;

@RequiredArgsConstructor
@Component
public class CacheUtilsInitializer implements InitializingBean {

    private final SpringCustomProperties springCustomProperties;

    @Override
    public void afterPropertiesSet() {
        CacheUtils.initialize(springCustomProperties);
    }
}
