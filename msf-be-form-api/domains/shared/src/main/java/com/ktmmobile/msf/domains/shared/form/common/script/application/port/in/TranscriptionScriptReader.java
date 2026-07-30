package com.ktmmobile.msf.domains.shared.form.common.script.application.port.in;

import java.util.List;

import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptRequest;

public interface TranscriptionScriptReader {

    List<String> getTranscriptionScript(TranscriptionScriptRequest request);
}
