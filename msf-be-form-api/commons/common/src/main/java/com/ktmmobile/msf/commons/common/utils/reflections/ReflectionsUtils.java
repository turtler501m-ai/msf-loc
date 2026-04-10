package com.ktmmobile.msf.commons.common.utils.reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.ktmmobile.msf.commons.common.data.constant.CommonBaseConst;
import com.ktmmobile.msf.commons.common.exception.CommonException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionsUtils {

    private static final Reflections reflections;

    static {
        ConfigurationBuilder reflectionConfig = new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage(CommonBaseConst.BASE_PACKAGE));
        reflections = new Reflections(reflectionConfig);
    }

    public static <T> Set<Class<? extends T>> getSubTypeOf(Class<T> baseType) {
        return reflections.getSubTypesOf(baseType);
    }

    public static Object getFieldAsObjectByFieldType(Object object, Class<?> fieldType) {
        return Arrays.stream(object.getClass().getDeclaredFields())
            .filter(field -> fieldType.isAssignableFrom(field.getType()))
            .map(field -> getObject(object, field))
            .findAny()
            .orElseThrow(() -> new CommonException(String.format("field as %s not found", fieldType.getSimpleName())));
    }

    public static Object getObject(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException ex) {
            throw new CommonException("illegal access", ex);
        }
    }
}
