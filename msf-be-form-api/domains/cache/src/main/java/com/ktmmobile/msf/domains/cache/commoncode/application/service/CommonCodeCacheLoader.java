package com.ktmmobile.msf.domains.cache.commoncode.application.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.out.CommonCodeRepository;
import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

@RequiredArgsConstructor
@Component
public class CommonCodeCacheLoader implements CacheLoader<List<CommonCode>> {

    static final String CACHE_NAME = "common-codes";

    private static Integer resolveSortOrder(CommonCode commonCode) {
        return commonCode.getDetail() != null ? commonCode.getDetail().getSortOrder() : null;
    }

    private static final Comparator<CommonCode> COMMON_CODE_COMPARATOR = Comparator
        .comparing(CommonCode::getGroupId, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCodeCacheLoader::resolveSortOrder, Comparator.nullsLast(Integer::compareTo))
        .thenComparing(CommonCode::getCode, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCode::getTitle, Comparator.nullsLast(String::compareTo));

    private final CommonCodeRepository commonCodeRepository;

    @Override
    public String cacheName() {
        return CACHE_NAME;
    }

    @Override
    public Map<String, List<CommonCode>> load() {
        List<CommonCode> loadedCommonCodes = commonCodeRepository.findAllCommonCodes()
            .stream()
            .sorted(COMMON_CODE_COMPARATOR)
            .toList();

        Map<String, List<CommonCode>> commonCodesByGroupId = new LinkedHashMap<>();
        for (CommonCode commonCode: loadedCommonCodes) {
            commonCodesByGroupId.computeIfAbsent(commonCode.getGroupId(), _ -> new ArrayList<>())
                .add(commonCode);
        }

        Map<String, List<CommonCode>> immutableCommonCodesByGroupId = new LinkedHashMap<>();
        commonCodesByGroupId.forEach((groupId, commonCodes) ->
            immutableCommonCodesByGroupId.put(groupId, List.copyOf(commonCodes)));
        return Map.copyOf(immutableCommonCodesByGroupId);
    }
}
