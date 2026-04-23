package com.ktmmobile.msf.domains.shared.form.common.ocr.adapter.controller;


import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.shared.form.common.ocr.application.dto.IdDocumentCondition;
import com.ktmmobile.msf.domains.shared.form.common.ocr.application.dto.IdDocumentResponse;
import com.ktmmobile.msf.domains.shared.form.common.ocr.application.port.in.DocumentReader;

@RestController
@RequestMapping("/api/shared/common/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentReader documentReader;

    @PostMapping("/scan")
    public CommonResponse<IdDocumentResponse> scanIdDocument() {
        return ResponseUtils.ok(documentReader.scanIdDocument());
    }
}
