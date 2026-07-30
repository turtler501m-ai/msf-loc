package com.ktmmobile.msf.domains.shared.form.common.script.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptRequest;
import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptVariable;
import com.ktmmobile.msf.domains.shared.form.common.script.application.port.out.TranscriptionScriptRepository;

@Service
@RequiredArgsConstructor
public class FormScriptService {

    private final TranscriptionScriptRepository transcriptionScriptRepository;

    public TranscriptionScriptVariable createNewChangeScript(TranscriptionScriptRequest request) {
        if (request.requestKey() == null || request.requestKey().isBlank()) {
            return TranscriptionScriptVariable.empty();
        }

        try {
            Long requestKey = Long.valueOf(request.requestKey());
            TranscriptionScriptVariable variable = transcriptionScriptRepository.getNewChangeVariable(requestKey);
            return variable != null ? variable : TranscriptionScriptVariable.empty();
        } catch (NumberFormatException e) {
            return TranscriptionScriptVariable.empty();
        }
    }
}
