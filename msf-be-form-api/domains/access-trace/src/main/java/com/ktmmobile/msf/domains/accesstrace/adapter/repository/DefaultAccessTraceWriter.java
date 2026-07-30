package com.ktmmobile.msf.domains.accesstrace.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTrace;
import com.ktmmobile.msf.commons.websecurity.web.accesstrace.AccessTraceWriter;
import com.ktmmobile.msf.domains.accesstrace.adapter.repository.mybatis.smartform.mapper.AccessTraceMapper;

/**
 * 기본 API 요청 이력 저장 구현체
 */
@RequiredArgsConstructor
@Component
public class DefaultAccessTraceWriter implements AccessTraceWriter {

    private final AccessTraceMapper accessTraceMapper;

    /**
     * 접속 이력 테이블 저장
     */
    @Override
    public void write(AccessTrace accessTrace) {
        accessTraceMapper.insertAccessTrace(
            accessTrace.userId(),
            accessTrace.accessIp(),
            accessTrace.processModuleCode(),
            accessTrace.processContent(),
            accessTrace.resultContent(),
            accessTrace.accessUrlAddress(),
            accessTrace.parameter(),
            accessTrace.extensionValue1(),
            "",
            ""
        );
    }
}
