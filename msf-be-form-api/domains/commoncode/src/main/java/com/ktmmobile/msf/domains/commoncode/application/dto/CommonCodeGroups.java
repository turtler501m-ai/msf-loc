package com.ktmmobile.msf.domains.commoncode.application.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record CommonCodeGroups(
    Map<String, List<CommonCodeData>> values
) {

    public CommonCodeGroups {
        Map<String, List<CommonCodeData>> immutableValues = new LinkedHashMap<>();
        values.forEach((groupId, commonCodes) -> immutableValues.put(groupId, List.copyOf(commonCodes)));
        values = Map.copyOf(immutableValues);
    }

    public List<CommonCodeData> get(String groupId) {
        return values.getOrDefault(groupId, List.of());
    }

    public Optional<CommonCodeData> get(String groupId, String code) {
        return CommonCodeData.get(get(groupId), code);
    }

    public SimpleCommonCode getSimple(String groupId, String code) {
        return get(groupId, code)
            .map(SimpleCommonCode::from)
            .orElse(null);
    }

    public SimpleCommonCode getSimple(String groupId, String code, String defaultTitle) {
        return get(groupId, code)
            .map(SimpleCommonCode::from)
            .orElseGet(() -> SimpleCommonCode.of(code, defaultTitle));
    }

    public SimpleCommonCode getSimple(String groupId, String code, Map<String, String> fallbackCodeMap) {
        Objects.requireNonNull(fallbackCodeMap, "fallbackCodeMap must not be null");
        return get(groupId, code)
            .map(SimpleCommonCode::from)
            .orElseGet(() -> {
                String title = fallbackCodeMap.get(code);
                return title != null ? SimpleCommonCode.of(code, title) : null;
            });
    }
}
