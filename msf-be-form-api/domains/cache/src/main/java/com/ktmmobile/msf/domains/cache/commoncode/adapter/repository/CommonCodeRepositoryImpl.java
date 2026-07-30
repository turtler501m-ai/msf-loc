package com.ktmmobile.msf.domains.cache.commoncode.adapter.repository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.cache.commoncode.adapter.repository.mybatis.msp.mapper.McpCommonCodeMapper;
import com.ktmmobile.msf.domains.cache.commoncode.adapter.repository.mybatis.msp.mapper.MspCommonCodeMapper;
import com.ktmmobile.msf.domains.cache.commoncode.adapter.repository.mybatis.smartform.mapper.SmartFormCommonCodeMapper;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.out.CommonCodeRepository;
import com.ktmmobile.msf.domains.cache.commoncode.domain.code.CommonCodeTargetGroupIds;
import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

/**
 * 데이터소스별 공통코드 캐시 원천 데이터 조회
 */
@RequiredArgsConstructor
@Repository
public class CommonCodeRepositoryImpl implements CommonCodeRepository {

    private final MspCommonCodeMapper mspCommonCodeMapper;
    private final McpCommonCodeMapper mcpCommonCodeMapper;
    private final SmartFormCommonCodeMapper smartFormCommonCodeMapper;

    /** MSP 공통코드 목록 조회 */
    @Override
    public List<CommonCode> findMspCommonCodes() {
        return findCommonCodes(CommonCodeTargetGroupIds.mspGroupIds(), mspCommonCodeMapper::selectList);
    }

    /** MCP 공통코드 목록 조회 */
    @Override
    public List<CommonCode> findMcpCommonCodes() {
        return Stream.of(
                findCommonCodes(CommonCodeTargetGroupIds.mcpCodeGroupIds(), mcpCommonCodeMapper::selectCodeList).stream(),
                findCommonCodes(CommonCodeTargetGroupIds.mcpDetailGroupIds(), mcpCommonCodeMapper::selectDetailList).stream())
            .flatMap(stream -> stream)
            .toList();
    }

    private List<CommonCode> findCommonCodes(
        List<String> groupIds,
        Function<List<String>, List<CommonCode>> commonCodeFinder
    ) {
        if (groupIds.isEmpty()) {
            return List.of();
        }
        return commonCodeFinder.apply(groupIds);
    }

    /** SmartForm 공통코드 목록 조회 */
    @Override
    public List<CommonCode> findSmartFormCommonCodes() {
        return smartFormCommonCodeMapper.selectList();
    }
}
