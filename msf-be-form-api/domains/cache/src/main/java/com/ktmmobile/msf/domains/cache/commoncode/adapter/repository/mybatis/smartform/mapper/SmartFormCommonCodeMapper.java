package com.ktmmobile.msf.domains.cache.commoncode.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

/**
 * SmartForm 공통코드 조회 MyBatis Mapper
 */
@Mapper
public interface SmartFormCommonCodeMapper {

    /** SmartForm 공통코드 목록 조회 */
    List<CommonCode> selectList();
}
