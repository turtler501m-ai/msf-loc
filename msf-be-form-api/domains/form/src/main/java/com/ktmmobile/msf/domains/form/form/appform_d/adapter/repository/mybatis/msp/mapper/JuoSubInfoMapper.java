package com.ktmmobile.msf.domains.form.form.appform_d.adapter.repository.mybatis.msp.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.domain.entity.JuoSubInfo;


@Mapper
public interface JuoSubInfoMapper {

    Optional<JuoSubInfo> selectKtmCustomer(JuoSubInfoCondition condition);

}
