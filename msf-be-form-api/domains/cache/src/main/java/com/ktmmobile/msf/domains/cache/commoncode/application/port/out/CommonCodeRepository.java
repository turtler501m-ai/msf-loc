package com.ktmmobile.msf.domains.cache.commoncode.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

/**
 * 공통코드 캐시 원천 데이터 조회 아웃바운드 포트
 */
public interface CommonCodeRepository {

    /** MSP 공통코드 목록 조회 */
    List<CommonCode> findMspCommonCodes();

    /** MCP 공통코드 목록 조회 */
    List<CommonCode> findMcpCommonCodes();

    /** SmartForm 공통코드 목록 조회 */
    List<CommonCode> findSmartFormCommonCodes();
}
