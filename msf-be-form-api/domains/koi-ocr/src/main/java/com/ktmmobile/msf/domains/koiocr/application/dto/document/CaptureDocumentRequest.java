package com.ktmmobile.msf.domains.koiocr.application.dto.document;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.commons.file.application.dto.FileUploadRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileVariantOptions;

public record CaptureDocumentRequest(
    MultipartFile srcFile,
    String fileCategory,
    FileVariantOptions variantOptions
) {

    public FileUploadRequest toFileUploadRequest() {
        return new FileUploadRequest(
            srcFile,
            fileCategory,
            new FileVariantOptions(
                false,
                false,
                List.of("tif")
            )
        );
    }
}
