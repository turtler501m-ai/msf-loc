package com.ktmmobile.msf.domains.shared.form.common.script.adapter.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.form.common.script.application.dto.TranscriptionScriptRequest;
import com.ktmmobile.msf.domains.shared.form.common.script.application.port.in.TranscriptionScriptReader;

@RestController
@RequestMapping("/api/form/common")
@RequiredArgsConstructor
public class TranscriptionScriptController {

    private final TranscriptionScriptReader reader;

    @PostMapping("/transcription-script/get")
    public CommonResponse<List<String>> getTranscriptionScript(@RequestBody TranscriptionScriptRequest request) {
        return ResponseUtils.ok(reader.getTranscriptionScript(request));
    }
}
