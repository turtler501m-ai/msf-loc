package com.ktmmobile.msf.commons.masking.support.serializer;

import java.util.List;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.ValueSerializerModifier;

import com.ktmmobile.msf.commons.masking.support.annotation.Masked;
import com.ktmmobile.msf.commons.masking.support.processor.MaskingProcessorRegistry;

class MaskingValueSerializerModifier extends ValueSerializerModifier {

    private final transient MaskingProcessorRegistry maskingProcessorRegistry;

    MaskingValueSerializerModifier(MaskingProcessorRegistry maskingProcessorRegistry) {
        this.maskingProcessorRegistry = maskingProcessorRegistry;
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(
        SerializationConfig config,
        BeanDescription.Supplier beanDesc,
        List<BeanPropertyWriter> beanProperties
    ) {
        beanProperties.forEach(this::assignMaskingSerializerIfNecessary);
        return beanProperties;
    }

    /** <code>@Masked</code>가 붙은 <code>CharSequence</code> 계열 필드에만 마스킹 Serializer 적용 */
    private void assignMaskingSerializerIfNecessary(BeanPropertyWriter writer) {
        Masked masked = writer.getAnnotation(Masked.class);
        if (masked == null || !writer.getType().isTypeOrSubTypeOf(CharSequence.class)) {
            return;
        }
        writer.assignSerializer(new MaskingValueSerializer(masked.type(), maskingProcessorRegistry));
    }
}
