package com.ktmmobile.msf.domains.form.form.appform_d.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.JuoSubInfoResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.application.fieldmapper.JuoSubInfoFieldMapper;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.in.JuoSubInfoReader;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.out.JuoSubInfoRepository;

@Service
@RequiredArgsConstructor
public class JuoSubInfoService implements JuoSubInfoReader {

    private final JuoSubInfoRepository repository;

    public JuoSubInfoResponse getJuoSubInfo(JuoSubInfoCondition condition) {
        return repository.getJuoSubInfo(condition)
            .map(JuoSubInfoFieldMapper.INSTANCE::toResponse)
            .orElse(null);
    }
}
