package com.ktmmobile.msf.domains.form.form.appform_d.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.SmartCdResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.application.fieldmapper.SmartCdFieldMapper;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.in.SmartCdReader;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.out.SmartCdRepository;

@Service
@RequiredArgsConstructor
public class SmartCdService implements SmartCdReader {

    private final SmartCdRepository smartCdRepository;

    @Override
    public List<SmartCdResponse> getCodeList(SmartCdCondition condition) {
        return smartCdRepository.getCodeList(condition)
            .stream()
            .map(SmartCdFieldMapper.INSTANCE::toResponse)
            .toList();

    }
}
