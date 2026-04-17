package com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.SmartCd;

@Mapper
public interface SmartCdMapper {

    List<SmartCd> selectList(SmartCdCondition condition);
}
