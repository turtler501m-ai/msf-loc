package com.ktmmobile.msf.commons.common.utils.tracing;

import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@ConditionalOnBean(Tracer.class)
@Component
public class TracingUtilsInitializer implements InitializingBean {

    private final Tracer tracer;

    @Override
    public void afterPropertiesSet() throws Exception {
        TracingUtils.initialize(tracer);
    }
}
