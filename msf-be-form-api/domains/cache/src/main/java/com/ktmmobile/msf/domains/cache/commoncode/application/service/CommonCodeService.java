package com.ktmmobile.msf.domains.cache.commoncode.application.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.cachecore.application.port.in.CacheReader;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommonCodeService implements CommonCodeReader {

    private final CacheReader cacheReader;

    @Override
    public CommonCodeGroups getCommonCodes(CommonCodesRequest request) {
        Map<String, List<CommonCodeData>> commonCodeGroups = new LinkedHashMap<>();
        getCommonCodes(request.groupIds(), request.shouldIncludeAll())
            .forEach((groupId, commonCodes) -> commonCodeGroups.put(groupId,
                commonCodes.stream()
                    .map(CommonCodeData::from)
                    .toList()
            ));

        return new CommonCodeGroups(commonCodeGroups);
    }

    @SuppressWarnings("unchecked")
    private Map<String, List<CommonCode>> getCommonCodes(List<String> groupIds, boolean includeAllUseYn) {
        Map<String, List<CommonCode>> filteredCommonCodes = new LinkedHashMap<>();

        for (String groupId: groupIds) {
            List<CommonCode> groupCommonCodes = cacheReader.get(CommonCodeCacheLoader.CACHE_NAME, groupId, List.class)
                .map(commonCodes -> (List<CommonCode>) commonCodes)
                .orElse(List.of());
            if (!includeAllUseYn) {
                groupCommonCodes = groupCommonCodes.stream()
                    .filter(commonCode -> commonCode.getUseYn().isUsed())
                    .toList();
            }

            filteredCommonCodes.put(groupId, groupCommonCodes);
        }

        return Collections.unmodifiableMap(filteredCommonCodes);
    }
}
