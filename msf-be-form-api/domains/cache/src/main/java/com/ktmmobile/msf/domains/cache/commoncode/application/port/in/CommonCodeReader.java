package com.ktmmobile.msf.domains.cache.commoncode.application.port.in;

import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;

/**
 * 공통코드 캐시 조회 인바운드 포트
 */
public interface CommonCodeReader {

    /** 요청 조건 기준 공통코드 그룹 목록 조회 */
    CommonCodeGroups getCommonCodes(CommonCodesRequest request);
}
