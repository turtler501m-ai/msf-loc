package com.ktmmobile.msf.commons.file.application.dto;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequest(
    MultipartFile file,
    String fileCategory,
    FileVariantOptions variants
) {

    public FileRequest toFileRequest() {
        return FileRequest.of(file, fileCategory);
    }

    public FileVariantOptions toVariantOptions() {
        return variants == null ? FileVariantOptions.empty() : variants;
    }
}
