package com.ktmmobile.msf.domains.commoncode.application.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.commoncode.domain.dto.CommonCodeGroups;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommonCodeService implements CommonCodeReader {

    private final CommonCodeCacheStore commonCodeCacheStore;

    @Override
    public CommonCodeGroups getCommonCodes(CommonCodesRequest request) {
        Map<String, List<CommonCodeData>> commonCodeGroups = new LinkedHashMap<>();
        commonCodeCacheStore.getCommonCodes(request.groupIds(), request.shouldIncludeAll())
            .forEach((groupId, commonCodes) -> commonCodeGroups.put(
                groupId,
                commonCodes.stream()
                    .map(CommonCodeData::from)
                    .toList()
            ));

        return new CommonCodeGroups(commonCodeGroups);
    }
}
