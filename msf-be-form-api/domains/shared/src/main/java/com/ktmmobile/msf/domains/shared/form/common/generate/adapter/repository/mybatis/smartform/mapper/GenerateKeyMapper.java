package com.ktmmobile.msf.domains.shared.form.common.generate.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenerateKeyMapper {

    String selectGeneratedResNo();

    long selectGeneratedRequestKey();

    long selectGeneratedCustRequestSeq();

    long selectGeneratedRequestStateSeq();
}
