package com.ktmmobile.msf.domains.shared.form.common.script.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptRequest;
import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptVariable;
import com.ktmmobile.msf.domains.shared.form.common.script.application.port.in.TranscriptionScriptReader;
import com.ktmmobile.msf.domains.shared.form.common.script.application.port.out.TranscriptionScriptRepository;
import com.ktmmobile.msf.domains.shared.form.common.script.domain.code.FormType;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TranscriptionScriptService implements TranscriptionScriptReader {

    private final TranscriptionScriptRepository transcriptionScriptRepository;
    private final FormScriptService formScriptService;

    @Override
    public List<String> getTranscriptionScript(TranscriptionScriptRequest request) {
        return getTranscriptionScript(request, null);
    }

    /**
     * 서식지별 치환 변수 전달용
     */
    public List<String> getTranscriptionScript(
        TranscriptionScriptRequest request,
        TranscriptionScriptVariable variable
    ) {

        TranscriptionScriptVariable scriptVariable = variable;

        if (scriptVariable == null) {
            FormType formType = FormType.valueOfCode(request.formTypeCd());

            scriptVariable = switch (formType) {
                case NEWCHANGE -> formScriptService.createNewChangeScript(request);
                case OWNERCHANGE -> request.toOwnerChangeVariable();
                default -> TranscriptionScriptVariable.empty();
            };
        }

        return transcriptionScriptRepository.getTranscriptionScript(request)
            .stream()
            .map(scriptVariable::replace)
            .map(script -> script.replaceFirst("\\s*@[^\\s]+$", ""))
            .toList();
    }

}
