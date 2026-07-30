package com.ktmmobile.msf.domains.eformsign.feature.adapter.repository.mybatis.smartform.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.commons.mybatis.annotation.AutoAuditing;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestJoinForm;

@AutoAuditing
@Mapper
public interface EformMapper {

    Boolean verifyNewChangeFormPw(VerifyFormPwRequest request);

    Boolean verifyServiceChangeFormPw(VerifyFormPwRequest request);

    Boolean verifyOwnerChangeFormPw(VerifyFormPwRequest request);

    Boolean verifyTerminationFormPw(VerifyFormPwRequest request);

    Integer insertRequestJoinForm(CompletedRequestJoinForm joinForm);

    Integer updateRequestJoinForm(CompletedRequestJoinForm joinForm);

    String selectR14ScanIdOfSvcChgDtl(Long requestKey);
}
