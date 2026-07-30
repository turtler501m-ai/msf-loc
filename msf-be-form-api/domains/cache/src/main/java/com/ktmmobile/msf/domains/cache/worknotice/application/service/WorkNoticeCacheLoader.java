package com.ktmmobile.msf.domains.cache.worknotice.application.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.cachecore.application.port.out.CacheLoader;
import com.ktmmobile.msf.domains.cache.worknotice.application.port.out.WorkNoticeCacheRepository;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.WorkNoticeCache;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.WorkNoticeCategoryCache;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorkNoticeCacheLoader implements CacheLoader<WorkNoticeCategoryCache> {

    public static final String CACHE_NAME = "work-notice";

    private final WorkNoticeCacheRepository workNoticeCacheRepository;

    @Override
    public String cacheName() {
        return CACHE_NAME;
    }

    @Override
    public Map<String, WorkNoticeCategoryCache> load() {
        List<WorkNoticeCache> list = workNoticeCacheRepository.getListWorkNoticeCache();
        Map<String, WorkNoticeCategoryCache> map = new LinkedHashMap<>();

        for (WorkNoticeCache workNoticeCache: list) {
            WorkNoticeCategoryCache categoryCache = map.get(workNoticeCache.getSbstSubCtgCd());
            if (categoryCache == null) {
                categoryCache = WorkNoticeCategoryCache.builder()
                    .category(workNoticeCache.getSbstCtgCd())
                    .list(new ArrayList<>())
                    .build();
                map.put(workNoticeCache.getSbstSubCtgCd(), categoryCache);
            }
            categoryCache.getList().add(workNoticeCache);
        }

        return Map.copyOf(map);
    }
}
