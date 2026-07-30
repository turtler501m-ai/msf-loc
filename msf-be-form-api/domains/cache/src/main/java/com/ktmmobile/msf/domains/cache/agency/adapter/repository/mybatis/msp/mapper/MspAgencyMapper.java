package com.ktmmobile.msf.domains.cache.agency.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

/**
 * MSP 조직 정보 조회 MyBatis Mapper
 */
@Mapper
public interface MspAgencyMapper {

    /** 사용 중인 조직 목록 조회 */
    List<Agency> selectActiveAgencies();
}
