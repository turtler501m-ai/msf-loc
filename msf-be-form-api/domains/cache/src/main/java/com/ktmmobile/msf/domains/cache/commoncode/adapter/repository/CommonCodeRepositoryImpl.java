package com.ktmmobile.msf.domains.cache.commoncode.adapter.repository;

import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.cache.commoncode.adapter.repository.mybatis.msp.mapper.McpCommonCodeMapper;
import com.ktmmobile.msf.domains.cache.commoncode.adapter.repository.mybatis.msp.mapper.MspCommonCodeMapper;
import com.ktmmobile.msf.domains.cache.commoncode.adapter.repository.mybatis.smartform.mapper.SmartFormCommonCodeMapper;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.out.CommonCodeRepository;
import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

@RequiredArgsConstructor
@Repository
public class CommonCodeRepositoryImpl implements CommonCodeRepository {

    private final MspCommonCodeMapper mspCommonCodeMapper;
    private final McpCommonCodeMapper mcpCommonCodeMapper;
    private final SmartFormCommonCodeMapper smartFormCommonCodeMapper;

    @Override
    public List<CommonCode> findMspCommonCodes() {
        return mspCommonCodeMapper.selectList();
    }

    @Override
    public List<CommonCode> findMcpCommonCodes() {
        return Stream.of(
                mcpCommonCodeMapper.selectCodeList().stream(),
                mcpCommonCodeMapper.selectDetailList().stream())
            .flatMap(stream -> stream)
            .toList();
    }

    @Override
    public List<CommonCode> findSmartFormCommonCodes() {
        return smartFormCommonCodeMapper.selectList();
    }
}
