package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

import com.ktmmobile.msf.commons.file.application.dto.FileResponse;

public record EformFileDownloadResponse(
    String documentId,
    String fileName,
    String fileCategory,
    String contentType,
    byte[] srcFile,
    FileResponse file
) {
}
