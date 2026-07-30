package com.ktmmobile.msf.domains.cache.agency.adapter.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.cache.agency.adapter.repository.mybatis.msp.mapper.MspAgencyMapper;
import com.ktmmobile.msf.domains.cache.agency.application.port.out.AgencyRepository;
import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

/**
 * MSP 데이터소스 대리점 조직 캐시 원천 데이터 조회
 */
@RequiredArgsConstructor
@Repository
public class AgencyRepositoryImpl implements AgencyRepository {

    private final MspAgencyMapper mspAgencyMapper;

    /** 사용 중인 조직 목록 조회 */
    @Override
    public List<Agency> findAllActiveAgencies() {
        return mspAgencyMapper.selectActiveAgencies();
    }
}
