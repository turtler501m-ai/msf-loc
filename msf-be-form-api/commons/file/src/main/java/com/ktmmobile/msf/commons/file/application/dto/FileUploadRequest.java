package com.ktmmobile.msf.commons.file.application.dto;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequest(
    MultipartFile file,
    String fileCategory
) {

    public FileRequest toFileRequest() {
        return FileRequest.of(file, fileCategory);
    }
}
