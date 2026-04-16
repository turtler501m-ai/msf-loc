package com.ktmmobile.msf.domains.commoncode.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.commoncode.domain.entity.CommonCode;

@Mapper
public interface SmartFormCommonCodeMapper {

    List<CommonCode> selectList();
}
