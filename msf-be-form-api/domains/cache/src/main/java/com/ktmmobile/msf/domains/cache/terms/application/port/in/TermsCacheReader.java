package com.ktmmobile.msf.domains.cache.terms.application.port.in;

import java.util.List;

import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheRequest;
import com.ktmmobile.msf.domains.cache.terms.application.dto.TermsCacheResponse;

/**
 * 약관 캐시 조회 인바운드 포트
 */
public interface TermsCacheReader {

    /** 요청 조건 기준 약관 목록 조회 */
    List<TermsCacheResponse> getListTerms(TermsCacheRequest request);

    /** 요청 조건 기준 약관 본문 조회 */
    TermsCacheResponse getTermsContent(TermsCacheRequest request);
}
