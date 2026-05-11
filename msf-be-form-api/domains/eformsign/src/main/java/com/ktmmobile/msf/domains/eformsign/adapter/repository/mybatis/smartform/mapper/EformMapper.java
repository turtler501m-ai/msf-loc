package com.ktmmobile.msf.domains.eformsign.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.eformsign.application.dto.VerifyFormPwRequest;

@Mapper
public interface EformMapper {

    Boolean verifyFormPw(VerifyFormPwRequest request, String authField, String requestResource);
}
