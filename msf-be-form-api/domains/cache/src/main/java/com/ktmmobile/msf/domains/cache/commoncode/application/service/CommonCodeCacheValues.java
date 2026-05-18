package com.ktmmobile.msf.domains.cache.commoncode.application.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

/**
 * 데이터소스별 로더와 통합 조회 서비스가 공유하는 공통코드 정렬/그룹핑 규칙
 */
final class CommonCodeCacheValues {

    private static final Comparator<CommonCode> COMMON_CODE_COMPARATOR = Comparator
        .comparing(CommonCode::getGroupId, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCodeCacheValues::resolveSortOrder, Comparator.nullsLast(Integer::compareTo))
        .thenComparing(CommonCode::getCode, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCode::getTitle, Comparator.nullsLast(String::compareTo));

    private CommonCodeCacheValues() {
    }

    /** 데이터소스가 달라도 같은 기준으로 응답 순서를 맞춘다. */
    static List<CommonCode> sort(List<CommonCode> commonCodes) {
        return commonCodes.stream()
            .sorted(COMMON_CODE_COMPARATOR)
            .toList();
    }

    /** Redis Hash field를 groupId로 사용하기 위해 공통코드를 그룹별 목록으로 변환한다. */
    static Map<String, List<CommonCode>> groupByGroupId(List<CommonCode> commonCodes) {
        Map<String, List<CommonCode>> commonCodesByGroupId = new LinkedHashMap<>();
        for (CommonCode commonCode: sort(commonCodes)) {
            commonCodesByGroupId.computeIfAbsent(commonCode.getGroupId(), _ -> new ArrayList<>())
                .add(commonCode);
        }

        Map<String, List<CommonCode>> immutableCommonCodesByGroupId = new LinkedHashMap<>();
        commonCodesByGroupId.forEach((groupId, groupCommonCodes) ->
            immutableCommonCodesByGroupId.put(groupId, List.copyOf(groupCommonCodes)));
        return Map.copyOf(immutableCommonCodesByGroupId);
    }

    private static Integer resolveSortOrder(CommonCode commonCode) {
        return commonCode.getDetail() != null ? commonCode.getDetail().getSortOrder() : null;
    }
}
