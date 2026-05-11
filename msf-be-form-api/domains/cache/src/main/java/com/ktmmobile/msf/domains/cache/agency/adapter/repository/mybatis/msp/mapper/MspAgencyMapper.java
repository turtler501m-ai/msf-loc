package com.ktmmobile.msf.domains.cache.agency.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.cache.agency.domain.entity.Agency;

@Mapper
public interface MspAgencyMapper {

    List<Agency> selectActiveAgencies();
}
