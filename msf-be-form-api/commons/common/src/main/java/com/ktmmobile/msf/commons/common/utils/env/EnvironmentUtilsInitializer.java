package com.ktmmobile.msf.commons.common.utils.env;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EnvironmentUtilsInitializer implements InitializingBean {

    private final Environment environment;

    @Override
    public void afterPropertiesSet() {
        EnvironmentUtils.initialize(environment);
    }
}
