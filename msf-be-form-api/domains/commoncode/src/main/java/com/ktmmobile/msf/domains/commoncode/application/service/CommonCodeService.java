package com.ktmmobile.msf.domains.commoncode.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodeResponse;
import com.ktmmobile.msf.domains.commoncode.application.dto.CommonCodesResponse;
import com.ktmmobile.msf.domains.commoncode.application.port.in.GroupCommonCodeReader;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommonCodeService implements GroupCommonCodeReader {

    private final CommonCodeCacheStore commonCodeCacheStore;

    @Override
    public List<CommonCodeResponse> getCommonCodes(String groupId) {
        return commonCodeCacheStore.getCommonCodeGroup(groupId)
            .stream()
            .map(CommonCodeResponse::of)
            .toList();
    }

    @Override
    public CommonCodesResponse getCommonCodes(List<String> groupIds) {
        return CommonCodesResponse.from(commonCodeCacheStore.getCommonCodes(groupIds));
    }
}
