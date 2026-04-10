package com.ktmmobile.msf.commons.common.utils.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventsInitializer implements InitializingBean {

    private final ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        Events.initialize(applicationContext);
    }
}
