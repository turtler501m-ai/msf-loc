package com.ktmmobile.msf.domains.eformsign.feature.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.eformsign.feature.adapter.repository.mybatis.msp.mapper.MspEformMapper;
import com.ktmmobile.msf.domains.eformsign.feature.adapter.repository.mybatis.smartform.mapper.EformMapper;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.port.out.EformRepository;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity.CompletedRequestJoinForm;

@RequiredArgsConstructor
@Repository
public class EformRepositoryImpl implements EformRepository {

    private final EformMapper eformMapper;
    private final MspEformMapper mspEformMapper;

    @Override
    public Boolean verifyNewChangeFormPw(VerifyFormPwRequest request) {
        return eformMapper.verifyNewChangeFormPw(request);
    }

    @Override
    public Boolean verifyServiceChangeFormPw(VerifyFormPwRequest request) {
        return eformMapper.verifyServiceChangeFormPw(request);
    }

    @Override
    public Boolean verifyOwnerChangeFormPw(VerifyFormPwRequest request) {
        return eformMapper.verifyOwnerChangeFormPw(request);
    }

    @Override
    public Boolean verifyTerminationFormPw(VerifyFormPwRequest request) {
        return eformMapper.verifyTerminationFormPw(request);
    }

    @Override public Integer registryRequestJoinForm(CompletedRequestJoinForm joinForm) {
        return eformMapper.insertRequestJoinForm(joinForm);
    }

    @Override public Integer modifyRequestJoinForm(CompletedRequestJoinForm joinForm) {
        return eformMapper.updateRequestJoinForm(joinForm);
    }

    @Override public String getR14DocumentIdOfServiceChange(Long requestKey) {
        return eformMapper.selectR14ScanIdOfSvcChgDtl(requestKey);
    }
}
