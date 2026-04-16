package com.ktmmobile.msf.domains.commoncode.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.commoncode.domain.entity.CommonCode;

@Mapper
public interface MspCommonCodeMapper {

    List<CommonCode> selectList();
}
