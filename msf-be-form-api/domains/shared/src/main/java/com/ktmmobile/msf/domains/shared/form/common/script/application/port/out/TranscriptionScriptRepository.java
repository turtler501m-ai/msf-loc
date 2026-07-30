package com.ktmmobile.msf.domains.shared.form.common.script.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptRequest;
import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptVariable;

public interface TranscriptionScriptRepository {

    List<String> getTranscriptionScript(TranscriptionScriptRequest request);

    TranscriptionScriptVariable getNewChangeVariable(Long requestKey);
}
