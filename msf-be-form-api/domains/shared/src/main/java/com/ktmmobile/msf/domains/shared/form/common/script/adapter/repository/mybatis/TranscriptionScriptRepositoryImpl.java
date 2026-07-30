package com.ktmmobile.msf.domains.shared.form.common.script.adapter.repository.mybatis;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.shared.form.common.script.adapter.repository.mybatis.smartform.mapper.TranscriptionScriptMapper;
import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptRequest;
import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptVariable;
import com.ktmmobile.msf.domains.shared.form.common.script.application.port.out.TranscriptionScriptRepository;

@RequiredArgsConstructor
@Repository
public class TranscriptionScriptRepositoryImpl implements TranscriptionScriptRepository {

    private final TranscriptionScriptMapper transcriptionScriptMapper;

    @Override
    public List<String> getTranscriptionScript(TranscriptionScriptRequest request) {
        return transcriptionScriptMapper.getTranscriptionScript(request);
    }

    @Override
    public TranscriptionScriptVariable getNewChangeVariable(Long requestKey) {
        return transcriptionScriptMapper.getNewChangeVariable(requestKey);
    }
}
