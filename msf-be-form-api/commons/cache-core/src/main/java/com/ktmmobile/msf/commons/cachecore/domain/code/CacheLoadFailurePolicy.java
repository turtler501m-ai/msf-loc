package com.ktmmobile.msf.commons.cachecore.domain.code;

/**
 * 캐시 적재 실패 처리 정책
 */
public enum CacheLoadFailurePolicy {
    // 실패 즉시 예외 발생
    FAIL_FAST,

    // 경고 로그 후 다음 캐시 적재 계속 진행
    WARN_AND_CONTINUE
}
