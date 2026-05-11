package com.ktmmobile.msf.domains.eformsign.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.eformsign.adapter.repository.mybatis.smartform.mapper.EformMapper;
import com.ktmmobile.msf.domains.eformsign.application.dto.VerifyFormPwRequest;
import com.ktmmobile.msf.domains.eformsign.application.port.out.EformRepository;

@RequiredArgsConstructor
@Repository
public class EformRepositoryImpl implements EformRepository {

    private final EformMapper eformMapper;

    @Override
    public Boolean verifyFormPw(VerifyFormPwRequest request, String authField, String requestResource) {
        return eformMapper.verifyFormPw(request, authField, requestResource);
    }
}
