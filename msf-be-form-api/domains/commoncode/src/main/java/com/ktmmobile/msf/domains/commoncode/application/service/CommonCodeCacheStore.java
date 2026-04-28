package com.ktmmobile.msf.domains.commoncode.application.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.startup.StartupLoadTask;
import com.ktmmobile.msf.domains.commoncode.application.port.out.CommonCodeRepository;
import com.ktmmobile.msf.domains.commoncode.domain.entity.CommonCode;

@Slf4j
@RequiredArgsConstructor
@Order(0)
@Component
public class CommonCodeCacheStore implements StartupLoadTask {

    public static final String STARTUP_LOAD_KEY = "common-codes";

    private static Integer resolveSortOrder(CommonCode commonCode) {
        return commonCode.getDetail() != null ? commonCode.getDetail().getSortOrder() : null;
    }

    private static final Comparator<CommonCode> COMMON_CODE_COMPARATOR = Comparator
        .comparing(CommonCode::getGroupId, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCodeCacheStore::resolveSortOrder, Comparator.nullsLast(Integer::compareTo))
        .thenComparing(CommonCode::getCode, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCode::getTitle, Comparator.nullsLast(String::compareTo));

    private final CommonCodeRepository commonCodeRepository;

    private final AtomicReference<CommonCodeCache> cacheRef = new AtomicReference<>(CommonCodeCache.empty());

    @Override
    public String key() {
        return STARTUP_LOAD_KEY;
    }

    @Override
    public void load() {
        refreshCache();
    }

    private CommonCodeCache refreshCache() {
        CommonCodeCache commonCodeCache = loadCache();
        cacheRef.set(commonCodeCache);

        log.info(">>> 공통코드 캐싱 완료. groupCount={}, totalCount={}",
            commonCodeCache.commonCodesByGroupId().size(), commonCodeCache.totalCount());

        return commonCodeCache;
    }

    private CommonCodeCache getOrLoadCache() {
        CommonCodeCache commonCodeCache = cacheRef.get();
        if (commonCodeCache.initialized()) {
            return commonCodeCache;
        }

        synchronized (this) {
            CommonCodeCache currentCache = cacheRef.get();
            if (currentCache.initialized()) {
                return currentCache;
            }

            log.info(">>> 공통코드 캐시가 비어 있어 즉시 로딩을 수행합니다.");
            return refreshCache();
        }
    }

    private CommonCodeCache loadCache() {
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
        commonCodesByGroupId.forEach((groupId, codes) -> immutableCommonCodesByGroupId.put(groupId, List.copyOf(codes)));
        return new CommonCodeCache(
            Collections.unmodifiableMap(new LinkedHashMap<>(immutableCommonCodesByGroupId)),
            loadedCommonCodes.size(),
            true
        );
    }

    public Map<String, List<CommonCode>> getCommonCodes(List<String> groupIds, boolean includeAllUseYn) {
        Map<String, List<CommonCode>> cachedCommonCodes = getOrLoadCache().commonCodesByGroupId();
        Map<String, List<CommonCode>> filteredCommonCodes = new LinkedHashMap<>();

        for (String groupId: groupIds) {
            List<CommonCode> groupCommonCodes = cachedCommonCodes.getOrDefault(groupId, List.of());
            if (!includeAllUseYn) {
                groupCommonCodes = groupCommonCodes.stream()
                    .filter(commonCode -> commonCode.getUseYn().isUsed())
                    .toList();
            }

            filteredCommonCodes.put(groupId, groupCommonCodes);
        }

        return Collections.unmodifiableMap(filteredCommonCodes);
    }

    private record CommonCodeCache(
        Map<String, List<CommonCode>> commonCodesByGroupId,
        int totalCount,
        boolean initialized
    ) {

        private static CommonCodeCache empty() {
            return new CommonCodeCache(Map.of(), 0, false);
        }
    }
}
