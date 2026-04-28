package com.ktmmobile.msf.domains.shared.form.common.ocr.adapter.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.form.common.ocr.application.dto.document.IdDocumentResponse;
import com.ktmmobile.msf.domains.shared.form.common.ocr.application.port.in.DocumentReader;

@RestController
@RequestMapping("/api/shared/common/")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentReader documentReader;

    @PostMapping("/document/scan")
    public CommonResponse<IdDocumentResponse> scanIdDocument() {
        return ResponseUtils.ok(documentReader.scanIdDocument());
    }

    /*@PostMapping("/document/capture")
    public CommonResponse<CaptureDocumentResponse> captureDocument() {
        return ResponseUtils.ok(documentReader.captureDocument());
    }

    @PostMapping("/usim/scan")
    public CommonResponse<ScanUsimResponse> scanUsim() {
        return ResponseUtils.ok(documentReader.captureDocument());
    }

    @PostMapping("/esim/scan")
    public CommonResponse<ScanEsimResponse> scanEsim() {
        return ResponseUtils.ok(documentReader.captureDocument());
    }*/


}
