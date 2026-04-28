package com.ktmmobile.msf.domains.commoncode.application.dto;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.EqualsAndHashCode;

import com.ktmmobile.msf.domains.commoncode.domain.dto.CommonCodeGroups;

@EqualsAndHashCode(callSuper = false)
public class CommonCodesResponse extends AbstractMap<String, List<CommonCodeItemResponse>> {

    private final Map<String, List<CommonCodeItemResponse>> commonCodesByGroupId;

    public CommonCodesResponse(Map<String, List<CommonCodeItemResponse>> commonCodesByGroupId) {
        this.commonCodesByGroupId = Map.copyOf(commonCodesByGroupId);
    }

    public static CommonCodesResponse toResponse(
        CommonCodeGroups commonCodeGroups,
        boolean includeDetail
    ) {
        Map<String, List<CommonCodeItemResponse>> response = commonCodeGroups.values()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue()
                    .stream()
                    .map(commonCode -> CommonCodeItemResponse.toResponse(commonCode, includeDetail))
                    .toList(),
                (left, _) -> left,
                LinkedHashMap::new
            ));
        return new CommonCodesResponse(response);
    }

    @JsonAnyGetter
    public Map<String, List<CommonCodeItemResponse>> asMap() {
        return commonCodesByGroupId;
    }

    @Override
    public List<CommonCodeItemResponse> get(Object key) {
        return commonCodesByGroupId.get(key);
    }

    @Override
    public java.util.Set<Entry<String, List<CommonCodeItemResponse>>> entrySet() {
        return commonCodesByGroupId.entrySet();
    }
}
