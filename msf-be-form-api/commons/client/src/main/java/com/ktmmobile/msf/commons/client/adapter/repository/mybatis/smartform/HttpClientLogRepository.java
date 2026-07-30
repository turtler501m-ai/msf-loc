package com.ktmmobile.msf.commons.client.adapter.repository.mybatis.smartform;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.client.adapter.repository.mybatis.smartform.mapper.HttpClientLogMapper;

/**
 * HTTP Client 호출 이력 DB 저장소
 */
@RequiredArgsConstructor
@Repository
public class HttpClientLogRepository {

    private final HttpClientLogMapper mapper;

    /**
     * 별도 트랜잭션으로 HTTP Client 호출 이력 저장
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(HttpClientLogRow log) {
        mapper.insertHttpClientLog(log);
    }
}
