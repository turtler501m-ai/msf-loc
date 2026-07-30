package com.ktmmobile.msf.domains.eformsign.file.application.dto;

/**
 * eFormSign 파일 업로드 URL 응답
 */
public record EFormSignFileUploadUrlResponse(
    String filePath,
    String uploadSignedUrl
) {
}
