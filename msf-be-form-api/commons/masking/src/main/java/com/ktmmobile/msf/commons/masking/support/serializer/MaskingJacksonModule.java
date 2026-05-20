package com.ktmmobile.msf.commons.masking.support.serializer;

import tools.jackson.databind.module.SimpleModule;

import com.ktmmobile.msf.commons.masking.support.processor.MaskingProcessorRegistry;

/**
 * <code>@Masked</code> 애너테이션이 붙은 문자열 필드에 마스킹 Serializer 적용
 */
public class MaskingJacksonModule extends SimpleModule {

    public MaskingJacksonModule(MaskingProcessorRegistry maskingProcessorRegistry) {
        super("common-masking-jackson-module");
        setSerializerModifier(new MaskingValueSerializerModifier(maskingProcessorRegistry));
    }
}
