package com.ktmmobile.msf.domains.cache.commoncode.domain.code;

import java.util.Arrays;
import java.util.List;

/**
 * 공통코드를 물리 캐시로 분리하는 데이터소스 그룹
 */
public enum CommonCodeSourceGroup {
    MSP("common-codes-msp"),
    MCP("common-codes-mcp"),
    SMARTFORM("common-codes-smartform");

    private final String cacheName;

    CommonCodeSourceGroup(String cacheName) {
        this.cacheName = cacheName;
    }

    /** 데이터소스 그룹의 물리 캐시 이름 반환 */
    public String cacheName() {
        return cacheName;
    }

    /** 모든 데이터소스 그룹의 물리 캐시 이름 목록 반환 */
    public static List<String> cacheNames() {
        return Arrays.stream(values())
            .map(CommonCodeSourceGroup::cacheName)
            .toList();
    }
}
