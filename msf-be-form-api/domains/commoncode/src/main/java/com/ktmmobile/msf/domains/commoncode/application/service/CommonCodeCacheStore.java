package com.ktmmobile.msf.domains.commoncode.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.commoncode.application.port.out.CommonCodeRepository;
import com.ktmmobile.msf.domains.commoncode.domain.entity.CommonCode;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommonCodeCacheStore {

    private static Integer resolveSortOrder(CommonCode commonCode) {
        return commonCode.getDetail() != null ? commonCode.getDetail().getSortOrder() : null;
    }

    private static final Comparator<CommonCode> COMMON_CODE_COMPARATOR = Comparator
        .comparing(CommonCode::getGroupId, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCodeCacheStore::resolveSortOrder, Comparator.nullsLast(Integer::compareTo))
        .thenComparing(CommonCode::getCode, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCode::getCodeName, Comparator.nullsLast(String::compareTo));

    private final CommonCodeRepository commonCodeRepository;

    private final AtomicReference<CommonCodeCache> cacheRef = new AtomicReference<>(CommonCodeCache.empty());

    @PostConstruct
    void initialize() {
        CommonCodeCache commonCodeCache = loadCache();
        cacheRef.set(commonCodeCache);

        log.info(">>> 공통코드 캐싱 완료. groupCount={}, totalCount={}",
            commonCodeCache.commonCodesByGroupId().size(), commonCodeCache.totalCount());
    }

    private CommonCodeCache loadCache() {
        List<CommonCode> loadedCommonCodes = commonCodeRepository.findAllCommonCodes()
            .stream()
            .sorted(COMMON_CODE_COMPARATOR)
            .toList();

        Map<String, List<CommonCode>> commonCodesByGroupId = new LinkedHashMap<>();
        for (CommonCode commonCode : loadedCommonCodes) {
            commonCodesByGroupId.computeIfAbsent(commonCode.getGroupId(), key -> new ArrayList<>())
                .add(commonCode);
        }

        Map<String, List<CommonCode>> immutableCommonCodesByGroupId = new LinkedHashMap<>();
        commonCodesByGroupId.forEach((groupId, codes) -> immutableCommonCodesByGroupId.put(groupId, List.copyOf(codes)));
        return new CommonCodeCache(
            Collections.unmodifiableMap(new LinkedHashMap<>(immutableCommonCodesByGroupId)),
            loadedCommonCodes.size()
        );
    }

    public Map<String, List<CommonCode>> getCommonCodes(List<String> groupIds) {
        Map<String, List<CommonCode>> cachedCommonCodes = cacheRef.get().commonCodesByGroupId();
        Map<String, List<CommonCode>> filteredCommonCodes = new LinkedHashMap<>();

        for (String groupId : groupIds) {
            filteredCommonCodes.put(groupId, cachedCommonCodes.getOrDefault(groupId, List.of()));
        }

        return Collections.unmodifiableMap(filteredCommonCodes);
    }

    public List<CommonCode> getCommonCodeGroup(String groupId) {
        return cacheRef.get().commonCodesByGroupId().getOrDefault(groupId, List.of());
    }

    private record CommonCodeCache(
        Map<String, List<CommonCode>> commonCodesByGroupId,
        int totalCount
    ) {

        private static CommonCodeCache empty() {
            return new CommonCodeCache(Map.of(), 0);
        }
    }
}
