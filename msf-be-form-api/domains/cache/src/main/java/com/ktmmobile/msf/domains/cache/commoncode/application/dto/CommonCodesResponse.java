package com.ktmmobile.msf.domains.cache.commoncode.application.dto;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.EqualsAndHashCode;

import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;

/**
 * groupId를 JSON 필드명으로 사용하는 공통코드 목록 응답
 */
@EqualsAndHashCode(callSuper = false)
public class CommonCodesResponse extends AbstractMap<String, List<CommonCodeItemResponse>> {

    private final Map<String, List<CommonCodeItemResponse>> commonCodesByGroupId;

    public CommonCodesResponse(Map<String, List<CommonCodeItemResponse>> commonCodesByGroupId) {
        this.commonCodesByGroupId = Map.copyOf(commonCodesByGroupId);
    }

    /** 공통코드 그룹 데이터의 API 응답 변환 */
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

    /** JSON 직렬화용 공통코드 그룹 맵 반환 */
    @JsonAnyGetter
    public Map<String, List<CommonCodeItemResponse>> asMap() {
        return commonCodesByGroupId;
    }

    /** groupId 기준 코드 항목 목록 반환 */
    @Override
    public List<CommonCodeItemResponse> get(Object key) {
        return commonCodesByGroupId.get(key);
    }

    /** 공통코드 그룹 엔트리 목록 반환 */
    @Override
    public java.util.Set<Entry<String, List<CommonCodeItemResponse>>> entrySet() {
        return commonCodesByGroupId.entrySet();
    }
}
