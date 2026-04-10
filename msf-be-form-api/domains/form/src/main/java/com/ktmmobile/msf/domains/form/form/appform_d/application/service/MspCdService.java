package com.ktmmobile.msf.domains.form.form.appform_d.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.MspCdResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.application.fieldmapper.MspCdFieldMapper;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.in.MspCdReader;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.out.MspCdRepository;

@Service
@RequiredArgsConstructor
public class MspCdService implements MspCdReader {

    private final MspCdRepository mspCdRepository;

    @Override
    public List<MspCdResponse> getCodeList(MspCdCondition condition) {
        return mspCdRepository.getCodeList(condition)
            .stream()
            .map(MspCdFieldMapper.INSTANCE::toResponse)
            .toList();
    }
}
