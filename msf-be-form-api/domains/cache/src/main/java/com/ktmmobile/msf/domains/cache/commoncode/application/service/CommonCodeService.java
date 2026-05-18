package com.ktmmobile.msf.domains.cache.commoncode.application.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import com.ktmmobile.msf.domains.cache.commoncode.domain.code.CommonCodeSourceGroup;
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
        getCommonCodes(request.groupIds(), request.shouldIncludeAll(), request.shouldCheckValidDate())
            .forEach((groupId, commonCodes) -> commonCodeGroups.put(groupId,
                commonCodes.stream()
                    .map(CommonCodeData::from)
                    .toList()
            ));

        return new CommonCodeGroups(commonCodeGroups);
    }

    private Map<String, List<CommonCode>> getCommonCodes(
        List<String> groupIds,
        boolean includeAll,
        boolean checkValidDate
    ) {
        Map<String, List<CommonCode>> commonCodesByGroupId = getCommonCodes(groupIds);
        Map<String, List<CommonCode>> filteredCommonCodes = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());

        for (String groupId: groupIds) {
            List<CommonCode> groupCommonCodes = commonCodesByGroupId.getOrDefault(groupId, List.of());
            if (!includeAll) {
                groupCommonCodes = groupCommonCodes.stream()
                    .filter(commonCode -> commonCode.getUseYn().isUsed())
                    .filter(commonCode -> !checkValidDate || isWithinValidPeriod(commonCode, currentDate))
                    .toList();
            }

            filteredCommonCodes.put(groupId, groupCommonCodes);
        }

        return Collections.unmodifiableMap(filteredCommonCodes);
    }

    /** 데이터소스 그룹별 물리 캐시를 조회하고, 응답은 groupId 기준으로 통합해서 제공한다. */
    @SuppressWarnings("unchecked")
    private Map<String, List<CommonCode>> getCommonCodes(List<String> groupIds) {
        Map<String, List<CommonCode>> commonCodesByGroupId = new LinkedHashMap<>();
        for (String groupId: groupIds) {
            commonCodesByGroupId.put(groupId, new ArrayList<>());
        }

        // source group별 HMGET으로 Redis 왕복을 groupId 개수와 무관하게 3회로 제한한다.
        for (CommonCodeSourceGroup sourceGroup: CommonCodeSourceGroup.values()) {
            Map<String, List> cachedCommonCodesByGroupId = cacheReader.getAll(
                sourceGroup.cacheName(),
                groupIds,
                List.class
            );
            cachedCommonCodesByGroupId.forEach((groupId, commonCodes) ->
                commonCodesByGroupId.computeIfAbsent(groupId, _ -> new ArrayList<>())
                    .addAll((List<CommonCode>) commonCodes)
            );
        }

        Map<String, List<CommonCode>> sortedCommonCodesByGroupId = new LinkedHashMap<>();
        commonCodesByGroupId.forEach((groupId, commonCodes) ->
            sortedCommonCodesByGroupId.put(groupId, CommonCodeCacheValues.sort(commonCodes)));
        return Collections.unmodifiableMap(sortedCommonCodesByGroupId);
    }

    private boolean isWithinValidPeriod(CommonCode commonCode, LocalDate currentDate) {
        CommonCode.Detail detail = commonCode.getDetail();
        return detail == null || detail.isWithinValidPeriod(currentDate);
    }
}
