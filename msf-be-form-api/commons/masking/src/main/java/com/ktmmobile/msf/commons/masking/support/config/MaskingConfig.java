package com.ktmmobile.msf.commons.masking.support.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ktmmobile.msf.commons.masking.support.processor.MaskingProcessorRegistry;
import com.ktmmobile.msf.commons.masking.support.serializer.MaskingJacksonModule;

@Configuration
public class MaskingConfig {

    /** 기존 ObjectMapper 설정 유지 및 마스킹용 Jackson 모듈 추가 */
    @Bean
    public JsonMapperBuilderCustomizer maskingObjectMapperBuilderCustomizer() {
        return builder -> builder.addModule(new MaskingJacksonModule(new MaskingProcessorRegistry()));
    }
}
