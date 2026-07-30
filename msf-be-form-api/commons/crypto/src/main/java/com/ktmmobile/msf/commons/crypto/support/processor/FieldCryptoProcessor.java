package com.ktmmobile.msf.commons.crypto.support.processor;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoMode;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;
import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;

/**
 * 객체 그래프 {@link Encrypted @Encrypted} 필드 암복호화
 *
 * <p>DTO, record, Map, Collection, 배열 혼합 구조 재귀 순회
 * MyBatis 파라미터 원본 보호용 복사 처리 제공</p>
 */
public class FieldCryptoProcessor {

    private static final Set<Class<?>> SIMPLE_TYPES = Set.of(
        String.class,
        Boolean.class,
        Byte.class,
        Short.class,
        Integer.class,
        Long.class,
        Float.class,
        Double.class,
        Character.class
    );

    private final TextEncryptorRegistry textEncryptors;
    private final Map<Class<?>, Field[]> encryptedFieldsCache = new ConcurrentHashMap<>();
    private final Map<Class<?>, Field[]> instanceFieldsCache = new ConcurrentHashMap<>();

    public FieldCryptoProcessor(TextEncryptor textEncryptor) {
        this(new TextEncryptorRegistry(Map.of(FieldCryptoAlgorithm.AES_GCM, textEncryptor)));
    }

    public FieldCryptoProcessor(TextEncryptorRegistry textEncryptors) {
        this.textEncryptors = textEncryptors;
    }

    /** @Encrypted 필드 값을 암호화 */
    public void encrypt(Object target) {
        process(target, FieldCryptoMode.ENCRYPT);
    }

    /** @Encrypted 필드 값을 암호화한 복사본 */
    public Object encryptCopy(Object target) {
        return processCopy(target, FieldCryptoMode.ENCRYPT, null);
    }

    /** 동등 조건 검색용 @Encrypted 필드 값을 암호화 */
    public void encryptSearchable(Object target) {
        process(target, FieldCryptoMode.ENCRYPT, EnumSet.of(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE));
    }

    /** 동등 조건 검색용 @Encrypted 필드 값을 암호화한 복사본 */
    public Object encryptSearchableCopy(Object target) {
        return processCopy(target, FieldCryptoMode.ENCRYPT, EnumSet.of(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE));
    }

    /** @Encrypted 처리 대상 필드 포함 여부 */
    public boolean hasEncryptedValue(Object target) {
        return hasEncryptedValue(target, null);
    }

    /** 동등 조건 검색용 @Encrypted 처리 대상 필드 포함 여부 */
    public boolean hasSearchableEncryptedValue(Object target) {
        return hasEncryptedValue(target, EnumSet.of(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE));
    }

    /** @Encrypted 필드 값을 복호화 */
    public void decrypt(Object target) {
        process(target, FieldCryptoMode.DECRYPT);
    }

    /** 동등 조건 검색용 @Encrypted 필드 값을 복호화 */
    public void decryptSearchable(Object target) {
        process(target, FieldCryptoMode.DECRYPT, EnumSet.of(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE));
    }

    /** 지정한 방향으로 @Encrypted 필드 값 처리 */
    public void process(Object target, FieldCryptoMode mode) {
        process(target, mode, null);
    }

    /** 지정한 알고리즘의 @Encrypted 필드 값만 처리 */
    public void process(Object target, FieldCryptoMode mode, Set<FieldCryptoAlgorithm> algorithms) {
        process(target, mode, algorithms, newIdentitySet());
    }

    private void process(Object target, FieldCryptoMode mode, Set<FieldCryptoAlgorithm> algorithms, Set<Object> visited) {
        if (target == null || isSimpleValueType(target.getClass()) || !visited.add(target)) {
            return;
        }
        // 컨테이너 내부 값 순회와 순환 참조 차단
        if (target instanceof Map<?, ?> map) {
            processMap(map, mode, algorithms, visited);
            return;
        }
        if (target instanceof Collection<?> collection) {
            collection.forEach(value -> process(value, mode, algorithms, visited));
            return;
        }
        if (target.getClass().isArray()) {
            processArray(target, mode, algorithms, visited);
            return;
        }

        for (Field field: encryptedFields(target.getClass())) {
            processField(target, field, mode, algorithms);
        }
    }

    private Object processCopy(Object target, FieldCryptoMode mode, Set<FieldCryptoAlgorithm> algorithms) {
        return processCopy(target, mode, algorithms, new IdentityHashMap<>());
    }

    private Object processCopy(
        Object target,
        FieldCryptoMode mode,
        Set<FieldCryptoAlgorithm> algorithms,
        IdentityHashMap<Object, Object> visited
    ) {
        if (target == null || isSimpleValueType(target.getClass())) {
            return target;
        }
        Object visitedCopy = visited.get(target);
        if (visitedCopy != null) {
            return visitedCopy;
        }
        // SELECT 검색 조건 원본 보호용 복사본 생성
        if (target instanceof Map<?, ?> map) {
            return copyMap(map, mode, algorithms, visited);
        }
        if (target instanceof Collection<?> collection) {
            return copyCollection(collection, mode, algorithms, visited);
        }
        if (target.getClass().isArray()) {
            return copyArray(target, mode, algorithms, visited);
        }
        if (target.getClass().isRecord()) {
            return copyRecord(target, mode, algorithms, visited);
        }
        return copyObject(target, mode, algorithms, visited);
    }

    private boolean hasEncryptedValue(Object target, Set<FieldCryptoAlgorithm> algorithms) {
        return hasEncryptedValue(target, algorithms, newIdentitySet());
    }

    private boolean hasEncryptedValue(Object target, Set<FieldCryptoAlgorithm> algorithms, Set<Object> visited) {
        if (target == null || isSimpleValueType(target.getClass()) || isJdkValueType(target.getClass()) || !visited.add(target)) {
            return false;
        }
        // 암호화 대상 없는 파라미터 복사 생략
        if (target instanceof Map<?, ?> map) {
            return map.values()
                .stream()
                .anyMatch(value -> hasEncryptedValue(value, algorithms, visited));
        }
        if (target instanceof Collection<?> collection) {
            return collection.stream()
                .anyMatch(value -> hasEncryptedValue(value, algorithms, visited));
        }
        if (target.getClass().isArray()) {
            int length = Array.getLength(target);
            for (int i = 0; i < length; i++) {
                if (hasEncryptedValue(Array.get(target, i), algorithms, visited)) {
                    return true;
                }
            }
            return false;
        }
        if (target.getClass().isRecord()) {
            return hasEncryptedRecordComponent(target, algorithms, visited);
        }
        return hasEncryptedObjectField(target, algorithms, visited);
    }

    private boolean hasEncryptedRecordComponent(Object target, Set<FieldCryptoAlgorithm> algorithms, Set<Object> visited) {
        for (RecordComponent component: target.getClass().getRecordComponents()) {
            Encrypted encrypted = encryptedAnnotation(component);
            if (encrypted != null && containsAlgorithm(encrypted.algorithm(), algorithms)) {
                return true;
            }
            if (encrypted == null && hasEncryptedValue(invokeRecordAccessor(target, component), algorithms, visited)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEncryptedObjectField(Object target, Set<FieldCryptoAlgorithm> algorithms, Set<Object> visited) {
        for (Field field: instanceFields(target.getClass())) {
            Encrypted encrypted = encryptedAnnotation(field);
            if (encrypted != null && containsAlgorithm(encrypted.algorithm(), algorithms)) {
                return true;
            }
            if (encrypted == null) {
                ReflectionUtils.makeAccessible(field);
                if (hasEncryptedValue(ReflectionUtils.getField(field, target), algorithms, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsAlgorithm(FieldCryptoAlgorithm algorithm, Set<FieldCryptoAlgorithm> algorithms) {
        return algorithms == null || algorithms.contains(algorithm);
    }

    private Map<Object, Object> copyMap(
        Map<?, ?> map,
        FieldCryptoMode mode,
        Set<FieldCryptoAlgorithm> algorithms,
        IdentityHashMap<Object, Object> visited
    ) {
        Map<Object, Object> copied = new LinkedHashMap<>();
        visited.put(map, copied);
        map.forEach((key, value) -> copied.put(key, processCopy(value, mode, algorithms, visited)));
        return copied;
    }

    private Collection<Object> copyCollection(
        Collection<?> collection,
        FieldCryptoMode mode,
        Set<FieldCryptoAlgorithm> algorithms,
        IdentityHashMap<Object, Object> visited
    ) {
        List<Object> copied = new ArrayList<>(collection.size());
        visited.put(collection, copied);
        collection.forEach(value -> copied.add(processCopy(value, mode, algorithms, visited)));
        return copied;
    }

    private Object copyArray(
        Object array,
        FieldCryptoMode mode,
        Set<FieldCryptoAlgorithm> algorithms,
        IdentityHashMap<Object, Object> visited
    ) {
        int length = Array.getLength(array);
        Object copied = Array.newInstance(array.getClass().getComponentType(), length);
        visited.put(array, copied);
        for (int i = 0; i < length; i++) {
            Array.set(copied, i, processCopy(Array.get(array, i), mode, algorithms, visited));
        }
        return copied;
    }

    private Object copyRecord(
        Object target,
        FieldCryptoMode mode,
        Set<FieldCryptoAlgorithm> algorithms,
        IdentityHashMap<Object, Object> visited
    ) {
        Class<?> targetType = target.getClass();
        RecordComponent[] components = targetType.getRecordComponents();
        Object[] args = new Object[components.length];
        Class<?>[] parameterTypes = new Class<?>[components.length];
        // record canonical constructor 인자 재구성
        for (int i = 0; i < components.length; i++) {
            RecordComponent component = components[i];
            parameterTypes[i] = component.getType();
            Object value = invokeRecordAccessor(target, component);
            args[i] = processRecordComponent(component, value, mode, algorithms);
        }
        try {
            Constructor<?> constructor = targetType.getDeclaredConstructor(parameterTypes);
            ReflectionUtils.makeAccessible(constructor);
            Object copied = constructor.newInstance(args);
            visited.put(target, copied);
            return copied;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new CryptoException("Encrypted field record copy failed: " + targetType.getName(), e);
        }
    }

    private Object invokeRecordAccessor(Object target, RecordComponent component) {
        try {
            var accessor = component.getAccessor();
            ReflectionUtils.makeAccessible(accessor);
            return accessor.invoke(target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CryptoException("Encrypted field record accessor failed: " + component.getName(), e);
        }
    }

    private Object processRecordComponent(
        RecordComponent component,
        Object value,
        FieldCryptoMode mode,
        Set<FieldCryptoAlgorithm> algorithms
    ) {
        Encrypted encrypted = encryptedAnnotation(component);
        if (encrypted == null) {
            return value;
        }
        validateEncryptedType(component.getType(), component.toString());
        return processValue((String) value, encrypted.algorithm(), mode, algorithms);
    }

    private Object copyObject(
        Object target,
        FieldCryptoMode mode,
        Set<FieldCryptoAlgorithm> algorithms,
        IdentityHashMap<Object, Object> visited
    ) {
        Class<?> targetType = target.getClass();
        Object copied = newInstance(targetType);
        visited.put(target, copied);
        // 기본 생성자 기반 일반 객체 복사와 mutable instance field 값 이전
        for (Field field: instanceFields(targetType)) {
            ReflectionUtils.makeAccessible(field);
            Object value = ReflectionUtils.getField(field, target);
            Object copiedValue = processObjectField(field, value, mode, algorithms);
            ReflectionUtils.setField(field, copied, copiedValue);
        }
        return copied;
    }

    private Object processObjectField(
        Field field,
        Object value,
        FieldCryptoMode mode,
        Set<FieldCryptoAlgorithm> algorithms
    ) {
        Encrypted encrypted = encryptedAnnotation(field);
        if (encrypted == null) {
            return value;
        }
        validateEncryptedField(field);
        return processValue((String) value, encrypted.algorithm(), mode, algorithms);
    }

    private void processMap(Map<?, ?> map, FieldCryptoMode mode, Set<FieldCryptoAlgorithm> algorithms, Set<Object> visited) {
        map.values().forEach(value -> process(value, mode, algorithms, visited));
    }

    private void processArray(Object array, FieldCryptoMode mode, Set<FieldCryptoAlgorithm> algorithms, Set<Object> visited) {
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            process(Array.get(array, i), mode, algorithms, visited);
        }
    }

    private Field[] encryptedFields(Class<?> targetType) {
        return encryptedFieldsCache.computeIfAbsent(targetType, type -> {
            var fields = new java.util.ArrayList<Field>();
            ReflectionUtils.doWithFields(type, field -> {
                if (encryptedAnnotation(field) != null) {
                    validateEncryptedField(field);
                    ReflectionUtils.makeAccessible(field);
                    fields.add(field);
                }
            }, this::isMutableInstanceField);
            return fields.toArray(Field[]::new);
        });
    }

    private Field[] instanceFields(Class<?> targetType) {
        return instanceFieldsCache.computeIfAbsent(targetType, type -> {
            var fields = new java.util.ArrayList<Field>();
            ReflectionUtils.doWithFields(type, field -> {
                ReflectionUtils.makeAccessible(field);
                fields.add(field);
            }, this::isMutableInstanceField);
            return fields.toArray(Field[]::new);
        });
    }

    private void processField(Object target, Field field, FieldCryptoMode mode, Set<FieldCryptoAlgorithm> algorithms) {
        Encrypted encrypted = encryptedAnnotation(field);
        if (encrypted == null) {
            return;
        }
        FieldCryptoAlgorithm algorithm = encrypted.algorithm();
        if (algorithms != null && !algorithms.contains(algorithm)) {
            return;
        }
        // String 전용 검증 이후 안전 캐스팅
        String value = (String) ReflectionUtils.getField(field, target);
        String processed = processValue(value, algorithm, mode, null);
        ReflectionUtils.setField(field, target, processed);
    }

    /** @Encrypted는 DB 저장 가능한 문자열 필드만 지원 */
    private void validateEncryptedField(Field field) {
        validateEncryptedType(field.getType(), field.toString());
    }

    private void validateEncryptedType(Class<?> type, String source) {
        if (!String.class.equals(type)) {
            throw new CryptoException("@Encrypted supports String fields only: " + source);
        }
    }

    private String processValue(
        String value,
        FieldCryptoAlgorithm algorithm,
        FieldCryptoMode mode,
        Set<FieldCryptoAlgorithm> algorithms
    ) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        if (algorithms != null && !algorithms.contains(algorithm)) {
            return value;
        }
        // enum별 TextEncryptor 암복호화 정책 위임
        TextEncryptor textEncryptor = textEncryptor(algorithm);
        return switch (mode) {
            case ENCRYPT -> textEncryptor.encrypt(value);
            case DECRYPT -> textEncryptor.decrypt(value);
        };
    }

    private TextEncryptor textEncryptor(FieldCryptoAlgorithm algorithm) {
        return textEncryptors.get(algorithm);
    }

    private Encrypted encryptedAnnotation(java.lang.reflect.AnnotatedElement element) {
        var annotation = MergedAnnotations.from(element).get(Encrypted.class);
        return annotation.isPresent() ? annotation.synthesize() : null;
    }

    private Object newInstance(Class<?> targetType) {
        try {
            Constructor<?> constructor = targetType.getDeclaredConstructor();
            ReflectionUtils.makeAccessible(constructor);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new CryptoException("Encrypted field object copy failed: " + targetType.getName(), e);
        }
    }

    private boolean isMutableInstanceField(Field field) {
        int modifiers = field.getModifiers();
        return !Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers);
    }

    private boolean isSimpleValueType(Class<?> type) {
        Class<?> resolvedType = ClassUtils.resolvePrimitiveIfNecessary(type);
        return SIMPLE_TYPES.contains(resolvedType)
            || resolvedType.isEnum()
            || resolvedType.getName().startsWith("java.time.")
            || resolvedType.getName().startsWith("java.math.");
    }

    private boolean isJdkValueType(Class<?> type) {
        return type.getName().startsWith("java.")
            && !Map.class.isAssignableFrom(type)
            && !Collection.class.isAssignableFrom(type);
    }

    private Set<Object> newIdentitySet() {
        return java.util.Collections.newSetFromMap(new IdentityHashMap<>());
    }
}
