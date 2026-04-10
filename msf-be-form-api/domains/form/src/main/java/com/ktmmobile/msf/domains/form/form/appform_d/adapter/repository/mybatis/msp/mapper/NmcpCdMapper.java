package com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis.msp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.NmcpCd;

@Mapper
public interface NmcpCdMapper {

    List<NmcpCd> selectList(NmcpCdCondition condition);
}
