package com.ktmmobile.msf.commons.masking.support.processor;

import java.lang.reflect.Modifier;
import java.util.EnumMap;
import java.util.Map;

import com.ktmmobile.msf.commons.common.utils.reflections.ReflectionsUtils;
import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

/**
 * MaskingProcessor 구현체를 Reflections로 찾아 MaskingType별 Processor로 등록
 */
public class MaskingProcessorRegistry {

    private final Map<MaskingType, MaskingProcessor> processors;

    public MaskingProcessorRegistry() {
        this.processors = createProcessors();
    }

    /** Classpath의 MaskingProcessor 구현체 자동 수집 */
    private Map<MaskingType, MaskingProcessor> createProcessors() {
        EnumMap<MaskingType, MaskingProcessor> processorMap = new EnumMap<>(MaskingType.class);
        ReflectionsUtils.getSubTypeOf(MaskingProcessor.class)
            .stream()
            .filter(type -> !type.isInterface())
            .filter(type -> !Modifier.isAbstract(type.getModifiers()))
            .filter(type -> !type.isAnonymousClass())
            .map(this::newProcessor)
            .forEach(processor -> register(processorMap, processor));
        validateAllTypesRegistered(processorMap);
        return Map.copyOf(processorMap);
    }

    /** Reflections 방식 생성을 위한 기본 생성자 필요 */
    private MaskingProcessor newProcessor(Class<? extends MaskingProcessor> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Masking processor cannot be created: " + type.getName(), ex);
        }
    }

    private void register(EnumMap<MaskingType, MaskingProcessor> processors, MaskingProcessor processor) {
        MaskingProcessor previous = processors.put(processor.type(), processor);
        if (previous != null) {
            throw new IllegalStateException("Duplicated masking processor: " + processor.type());
        }
    }

    /** MaskingType만 추가하고 Processor 구현을 누락한 경우 초기화 시 실패 처리 */
    private void validateAllTypesRegistered(EnumMap<MaskingType, MaskingProcessor> processors) {
        for (MaskingType type: MaskingType.values()) {
            if (!processors.containsKey(type)) {
                throw new IllegalStateException("Masking processor is not registered: " + type);
            }
        }
    }

    public String mask(String value, MaskingType type) {
        if (value == null) {
            return null;
        }
        return get(type).mask(value);
    }

    MaskingProcessor get(MaskingType type) {
        MaskingProcessor processor = processors.get(type);
        if (processor == null) {
            throw new IllegalArgumentException("Unsupported masking type: " + type);
        }
        return processor;
    }
}
