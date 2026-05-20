package com.ktmmobile.msf.commons.masking.support.serializer;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdScalarSerializer;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;
import com.ktmmobile.msf.commons.masking.support.processor.MaskingProcessorRegistry;

class MaskingValueSerializer extends StdScalarSerializer<Object> {

    private final MaskingType type;
    private final MaskingProcessorRegistry maskingProcessorRegistry;

    MaskingValueSerializer(MaskingType type, MaskingProcessorRegistry maskingProcessorRegistry) {
        super(Object.class, false);
        this.type = type;
        this.maskingProcessorRegistry = maskingProcessorRegistry;
    }

    /** 원본 객체 값 변경 없이 JSON 응답에 쓰는 값만 마스킹 */
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        gen.writeString(maskingProcessorRegistry.mask(value.toString(), type));
    }
}
