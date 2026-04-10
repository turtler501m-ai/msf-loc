package com.ktmmobile.msf.domains.form.form.appform_d.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdCondition;
import com.ktmmobile.msf.domains.form.form.appform_d.application.dto.NmcpCdResponse;
import com.ktmmobile.msf.domains.form.form.appform_d.application.fieldmapper.NmcpCdFieldMapper;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.in.NmcpCdReader;
import com.ktmmobile.msf.domains.form.form.appform_d.application.port.out.NmcpCdRepository;

@Service
@RequiredArgsConstructor
public class NmcpCdService implements NmcpCdReader {

    private final NmcpCdRepository nmcpCdRepository;

    @Override
    public List<NmcpCdResponse> getCodeList(NmcpCdCondition condition) {
        return nmcpCdRepository.getCodeList(condition)
            .stream()
            .map(NmcpCdFieldMapper.INSTANCE::toResponse)
            //                .map(NmcpCdResponse::of)
            .toList();

        /*.map(SampleForm1FieldMapper.INSTANCE::toForm1Response)
                .orElseThrow(() -> new NotFoundException("지정한 서식지를 찾을 수 없습니다. formId:" + formId));*/
    }
}
