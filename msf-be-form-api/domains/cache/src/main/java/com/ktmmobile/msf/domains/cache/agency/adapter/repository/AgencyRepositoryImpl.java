package com.ktmmobile.msf.domains.cache.agency.adapter.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.cache.agency.adapter.repository.mybatis.msp.mapper.MspAgencyMapper;
import com.ktmmobile.msf.domains.cache.agency.application.port.out.AgencyRepository;
import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

@RequiredArgsConstructor
@Repository
public class AgencyRepositoryImpl implements AgencyRepository {

    private final MspAgencyMapper mspAgencyMapper;

    @Override
    public List<Agency> findAllActiveAgencies() {
        return mspAgencyMapper.selectActiveAgencies();
    }
}
