package com.ktmmobile.msf.domains.koiocr.adapter.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.CaptureDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.CaptureDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentRequest;
import com.ktmmobile.msf.domains.koiocr.application.dto.document.IdDocumentResponse;
import com.ktmmobile.msf.domains.koiocr.application.port.in.DocumentReader;

@RestController
@RequestMapping("/api/koi-ocr")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentReader documentReader;

    @PostMapping("/document/scan")
    public CommonResponse<IdDocumentResponse> scanIdDocument(@Valid IdDocumentRequest request) {
        return ResponseUtils.ok(documentReader.scanIdDocument(request));
    }

    @PostMapping("/document/capture")
    public CommonResponse<CaptureDocumentResponse> captureDocument(CaptureDocumentRequest request) {
        return ResponseUtils.ok(documentReader.captureDocument(request));
    }

}
