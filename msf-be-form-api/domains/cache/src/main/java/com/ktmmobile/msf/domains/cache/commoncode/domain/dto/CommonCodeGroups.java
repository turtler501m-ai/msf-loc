package com.ktmmobile.msf.domains.cache.commoncode.domain.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.ktmmobile.msf.domains.cache.commoncode.application.dto.SimpleCommonCode;

/**
 * groupId 기준으로 묶은 공통코드 캐시 데이터
 */
public record CommonCodeGroups(
    Map<String, List<CommonCodeData>> values
) {

    public CommonCodeGroups {
        Map<String, List<CommonCodeData>> immutableValues = new LinkedHashMap<>();
        values.forEach((groupId, commonCodes) -> immutableValues.put(groupId, List.copyOf(commonCodes)));
        values = Map.copyOf(immutableValues);
    }

    /** groupId 기준 공통코드 목록 반환 */
    public List<CommonCodeData> get(String groupId) {
        return values.getOrDefault(groupId, List.of());
    }

    /** groupId와 code 기준 공통코드 조회 */
    public Optional<CommonCodeData> get(String groupId, String code) {
        return CommonCodeData.get(get(groupId), code);
    }

    /** 단일 그룹 조회 결과의 코드 목록 반환 */
    public List<CommonCodeData> getSingleGroup() {
        if (values.size() != 1) {
            throw new IllegalStateException("CommonCodeGroups must contain exactly one group.");
        }
        return values.values().iterator().next();
    }

    /** 단일 그룹 조회 결과의 code 기준 공통코드 조회 */
    public Optional<CommonCodeData> getSingleGroup(String code) {
        return CommonCodeData.get(getSingleGroup(), code);
    }

    /** groupId와 code 기준 단순 공통코드 조회 */
    public SimpleCommonCode getSimple(String groupId, String code) {
        if (code == null) {
            return SimpleCommonCode.empty();
        }
        return get(groupId, code)
            .map(SimpleCommonCode::from)
            .orElseGet(SimpleCommonCode::empty);
    }

    /** groupId와 code 기준 단순 공통코드 조회와 기본 코드명 반환 */
    public SimpleCommonCode getSimple(String groupId, String code, String defaultTitle) {
        if (code == null) {
            return SimpleCommonCode.empty();
        }
        return get(groupId, code)
            .map(SimpleCommonCode::from)
            .orElseGet(() -> SimpleCommonCode.of(code, defaultTitle));
    }

    /** groupId와 code 기준 단순 공통코드 조회와 fallback 코드명 탐색 */
    public SimpleCommonCode getSimple(String groupId, String code, Map<String, String> fallbackCodeMap) {
        Objects.requireNonNull(fallbackCodeMap, "fallbackCodeMap must not be null");
        if (code == null) {
            return SimpleCommonCode.empty();
        }
        return get(groupId, code)
            .map(SimpleCommonCode::from)
            .orElseGet(() -> {
                String title = fallbackCodeMap.get(code);
                return title != null ? SimpleCommonCode.of(code, title) : SimpleCommonCode.empty();
            });
    }
}
