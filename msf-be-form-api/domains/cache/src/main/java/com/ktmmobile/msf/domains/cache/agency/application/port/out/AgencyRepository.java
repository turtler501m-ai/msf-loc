package com.ktmmobile.msf.domains.cache.agency.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

/**
 * 대리점 조직 캐시 원천 데이터 조회 아웃바운드 포트
 */
public interface AgencyRepository {

    /** 사용 중인 조직 목록 조회 */
    List<Agency> findAllActiveAgencies();
}
