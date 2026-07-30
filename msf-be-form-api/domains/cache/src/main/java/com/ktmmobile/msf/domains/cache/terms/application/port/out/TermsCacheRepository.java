package com.ktmmobile.msf.domains.cache.terms.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsDetail;
import com.ktmmobile.msf.domains.cache.terms.domain.entity.TermsType;

/**
 * 약관 캐시 원천 데이터 조회 아웃바운드 포트
 */
public interface TermsCacheRepository {

    /** 약관 그룹 목록 조회 */
    List<TermsType> getListTermsType(List<String> groupList);

    /** 약관 상세 목록 조회 */
    List<TermsDetail> getListTermsDetail(List<TermsDetail> detailList);
}
