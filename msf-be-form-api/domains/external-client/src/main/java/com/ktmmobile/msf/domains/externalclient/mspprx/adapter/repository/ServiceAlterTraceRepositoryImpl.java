package com.ktmmobile.msf.domains.externalclient.mspprx.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.domains.externalclient.mspprx.adapter.repository.mybatis.smartform.mapper.ServiceAlterTraceMapper;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceId;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.ServiceAlterTraceRepository;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.entity.ServiceAlterTrace;

/**
 * smartform 서비스 변경 이력 저장소
 */
@Component
@RequiredArgsConstructor
public class ServiceAlterTraceRepositoryImpl implements ServiceAlterTraceRepository {

    private final ServiceAlterTraceMapper serviceAlterTraceMapper;

    @Override
    public ServiceAlterTraceId recordTrace(ServiceAlterTrace serviceAlterTrace) {
        return serviceAlterTraceMapper.insert(serviceAlterTrace);
    }

    @Override
    public int countRecentSuccessfulPlanChange(String ncn, String targetSocCode) {
        return serviceAlterTraceMapper.countRecentSuccessfulPlanChange(ncn, targetSocCode);
    }
}
