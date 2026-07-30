package com.ktmmobile.msf.domains.externalclient.nice.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.externalclient.nice.adapter.repository.mybatis.smartform.mapper.NiceApiLogMapper;
import com.ktmmobile.msf.domains.externalclient.nice.application.port.out.NiceApiLogRepository;
import com.ktmmobile.msf.domains.externalclient.nice.domain.entity.NiceApiAccountCheckLog;

/**
 * NICE API 이력 저장소 구현체
 */
@Repository
@RequiredArgsConstructor
class NiceApiLogRepositoryImpl implements NiceApiLogRepository {

    private final NiceApiLogMapper mapper;

    /** NICE 계좌인증 결과를 SmartForm DB에 저장 */
    @Override
    public void saveAccountCheckLog(NiceApiAccountCheckLog log) {
        mapper.insertAccountCheckLog(log);
    }
}
