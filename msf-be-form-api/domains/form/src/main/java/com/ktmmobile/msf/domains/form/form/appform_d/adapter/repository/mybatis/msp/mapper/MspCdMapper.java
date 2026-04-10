package com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.MspCd;

@Mapper
public interface MspCdMapper {

    List<MspCd> selectList(MspCdCondition condition);
}
