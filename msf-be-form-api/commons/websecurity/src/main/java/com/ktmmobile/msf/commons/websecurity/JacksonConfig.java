package com.ktmmobile.msf.commons.websecurity;

import java.util.function.Predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer;
import tools.jackson.databind.module.SimpleModule;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.serializer.CommonEnumDeserializer;
import com.ktmmobile.msf.commons.common.utils.DateTimeUtils;
import com.ktmmobile.msf.commons.common.utils.reflections.ReflectionsUtils;

@RequiredArgsConstructor
@Configuration
public class JacksonConfig {

    private final BindingProperties bindingProperties;

    @Bean
    public JsonMapperBuilderCustomizer objectMapperBuilderCustomizer() {
        return builder -> {
            SimpleModule module = new SimpleModule("common-web-jackson-module");
            addCustomSerializers(module);
            addCustomDeserializers(module);
            addDeserializersForCommonEnum(module);
            addDeserializerForString(module);
            builder.addModule(module);
        };
    }

    private static void addCustomSerializers(SimpleModule module) {
        module.addSerializer(new LocalDateSerializer(DateTimeUtils.DEFAULT_DATE));
        module.addSerializer(new LocalTimeSerializer(DateTimeUtils.DEFAULT_TIME));
        module.addSerializer(new LocalDateTimeSerializer(DateTimeUtils.DEFAULT_DATE_TIME));
    }

    private static void addCustomDeserializers(SimpleModule module) {
        module.addDeserializer(java.time.LocalDate.class, new LocalDateDeserializer(DateTimeUtils.DEFAULT_DATE));
        module.addDeserializer(java.time.LocalTime.class, new LocalTimeDeserializer(DateTimeUtils.DEFAULT_TIME));
        module.addDeserializer(java.time.LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtils.DEFAULT_DATE_TIME));
    }

    private static void addDeserializersForCommonEnum(SimpleModule module) {
        ReflectionsUtils.getSubTypeOf(CommonEnum.class)
            .stream()
            .filter(Predicate.not(Class::isAnonymousClass))
            .forEach(type -> addCommonEnumDeserializer(module, type));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void addCommonEnumDeserializer(SimpleModule module, Class<? extends CommonEnum> type) {
        module.addDeserializer((Class) type, new CommonEnumDeserializer(type));
    }

    private void addDeserializerForString(SimpleModule module) {
        if (bindingProperties.stringTrim()) {
            module.addDeserializer(String.class, new StringStripJsonDeserializer(bindingProperties.stringEmptyAsNull()));
        }
    }

    @RequiredArgsConstructor
    public static class StringStripJsonDeserializer extends ValueDeserializer<String> {

        private final boolean emptyAsNull;

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
            String value = p.getValueAsString();
            if (value == null) {
                return null;
            }

            String stripped = value.strip();
            if (StringUtils.hasText(stripped)) {
                return stripped;
            }
            return emptyAsNull ? null : "";
        }
    }
}
