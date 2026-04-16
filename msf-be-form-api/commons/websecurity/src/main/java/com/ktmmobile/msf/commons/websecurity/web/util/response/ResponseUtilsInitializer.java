package com.ktmmobile.msf.commons.websecurity.web.util.response;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Component
public class ResponseUtilsInitializer implements InitializingBean {

    private final ObjectMapper objectMapper;
    private final ResponseHandlingProperties responseHandlingProperties;

    @Override
    public void afterPropertiesSet() {
        ResponseUtils.initialize(objectMapper, responseHandlingProperties);
    }
}
