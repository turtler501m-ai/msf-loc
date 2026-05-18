package com.ktmmobile.msf.domains.koiocr.application.dto.document;

import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.domains.koiocr.domain.code.DocumentType;

public record IdDocumentRequest(

    @NotNull
    MultipartFile srcFile,
    @NotNull
    DocumentType ocrType

) {
}