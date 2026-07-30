package com.ktmmobile.msf.domains.cache.worknotice.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReader;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.cache.worknotice.application.dto.WorkNoticeCacheRequest;
import com.ktmmobile.msf.domains.cache.worknotice.application.dto.WorkNoticeCacheResponse;
import com.ktmmobile.msf.domains.cache.worknotice.application.port.in.WorkNoticeCacheReader;
import com.ktmmobile.msf.domains.cache.worknotice.application.port.out.WorkNoticeCacheRepository;
import com.ktmmobile.msf.domains.cache.worknotice.domain.code.WorkNoticeFormType;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.AccessAllowedIp;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.WorkNoticeCache;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.WorkNoticeCategoryCache;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WorkNoticeCacheService implements WorkNoticeCacheReader {

    private final CacheReader cacheReader;
    private final WorkNoticeCacheLoader workNoticeCacheLoader;
    private final CommonCodeReader commonCodeReader;
    private final WorkNoticeCacheRepository workNoticeCacheRepository;

    @Override
    public WorkNoticeCacheResponse getListWorkNotice(WorkNoticeCacheRequest request) {
        List<AccessAllowedIp> ipList = workNoticeCacheRepository.getAccessAllowedIp(RequestUtils.getClientIp());
        if (ipList != null && !ipList.isEmpty()) {
            return null;
        }

        Map<String, WorkNoticeCategoryCache> map = cacheReader.getAll(
            workNoticeCacheLoader.cacheName(),
            List.of(WorkNoticeFormType.ALL.getCode(), request.formType().getCode()),
            WorkNoticeCategoryCache.class);

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        List<WorkNoticeCache> cacheList = new ArrayList<>();
        for (WorkNoticeCategoryCache categoryCache: map.values()) {
            List<WorkNoticeCache> filteredList = categoryCache.getList()
                .stream()
                .filter(v -> v.getStartDate().compareTo(now) <= 0 && v.getEndDate().compareTo(now) >= 0)
                .toList();
            if (!filteredList.isEmpty()) {
                cacheList.addAll(filteredList);
            }
        }
        if (!cacheList.isEmpty()) {
            CommonCodesRequest groupList = CommonCodesRequest.of(List.of("SBST_CTG_CD2"), true, false);
            CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(groupList);
            return WorkNoticeCacheResponse.of(cacheList.getFirst(), commonCodeGroups);
        }

        return null;
    }
}
