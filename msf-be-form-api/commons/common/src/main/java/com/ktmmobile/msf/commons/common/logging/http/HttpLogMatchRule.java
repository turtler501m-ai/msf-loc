package com.ktmmobile.msf.commons.common.logging.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * HTTP 로그 include/exclude 매칭 규칙과 축약 기본값
 */
public record HttpLogMatchRule(
    List<String> include,
    Map<String, List<String>> additionalInclude,
    List<String> exclude,
    Map<String, List<String>> additionalExclude,
    Integer defaultTruncatedSize
) {

    public HttpLogMatchRule {
        include = immutableListOrNull(include);
        additionalInclude = immutableListMapOrNull(additionalInclude);
        exclude = immutableListOrNull(exclude);
        additionalExclude = immutableListMapOrNull(additionalExclude);
    }

    /**
     * 빈 매칭 규칙 생성
     */
    public static HttpLogMatchRule empty() {
        return new HttpLogMatchRule(null, null, null, null, null);
    }

    /**
     * 기본 규칙에 override 규칙 병합
     */
    public HttpLogMatchRule merge(HttpLogMatchRule override) {
        if (override == null) {
            return this;
        }
        return new HttpLogMatchRule(
            override.include == null ? include : override.include,
            mergeMap(additionalInclude, override.additionalInclude),
            override.exclude == null ? exclude : override.exclude,
            mergeMap(additionalExclude, override.additionalExclude),
            override.defaultTruncatedSize == null ? defaultTruncatedSize : override.defaultTruncatedSize
        );
    }

    /**
     * 기본 include와 추가 include를 합친 최종 include 목록 반환
     */
    public List<String> mergedInclude() {
        List<String> merged = new ArrayList<>();
        additionalInclude().values().forEach(merged::addAll);
        merged.addAll(include());
        return List.copyOf(merged);
    }

    /**
     * 기본 exclude와 추가 exclude를 합친 최종 exclude 목록 반환
     */
    public List<String> mergedExclude() {
        List<String> merged = new ArrayList<>();
        additionalExclude().values().forEach(merged::addAll);
        merged.addAll(exclude());
        return List.copyOf(merged);
    }

    @Override
    public List<String> include() {
        return include == null ? List.of() : include;
    }

    @Override
    public Map<String, List<String>> additionalInclude() {
        return additionalInclude == null ? Map.of() : additionalInclude;
    }

    @Override
    public List<String> exclude() {
        return exclude == null ? List.of() : exclude;
    }

    @Override
    public Map<String, List<String>> additionalExclude() {
        return additionalExclude == null ? Map.of() : additionalExclude;
    }

    @Override
    public Integer defaultTruncatedSize() {
        return defaultTruncatedSize == null ? 0 : defaultTruncatedSize;
    }

    private static List<String> immutableListOrNull(List<String> source) {
        return source == null ? null : List.copyOf(source);
    }

    private static Map<String, List<String>> immutableListMapOrNull(Map<String, List<String>> source) {
        if (source == null) {
            return null;
        }
        if (source.isEmpty()) {
            return Map.of();
        }

        Map<String, List<String>> copied = new LinkedHashMap<>();
        source.forEach((key, value) ->
            copied.put(key, List.copyOf(Objects.requireNonNull(value, "additional include/exclude values are required"))));
        return Collections.unmodifiableMap(copied);
    }

    private static Map<String, List<String>> mergeMap(
        Map<String, List<String>> base,
        Map<String, List<String>> override
    ) {
        if (base == null && override == null) {
            return null;
        }
        Map<String, List<String>> merged = new LinkedHashMap<>();
        if (base != null) {
            merged.putAll(base);
        }
        if (override != null) {
            merged.putAll(override);
        }
        return immutableListMapOrNull(merged);
    }
}
