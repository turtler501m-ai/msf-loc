package com.ktmmobile.msf.domains.shared.form.common.generate.adapter.repository.mybatis.msp.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface McpGenerateKeyMapper {

    String selectGeneratedResNo();

    long selectGeneratedRequestKey();

    long selectGeneratedCustRequestSeq();

    long selectGeneratedRequestStateSeq();
}
