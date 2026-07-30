package com.ktmmobile.msf.domains.eformsign.file.application.dto;

import jakarta.validation.constraints.NotBlank;

import com.ktmmobile.msf.commons.file.support.util.FileUtils;

/**
 * eFormSign 파일 업로드 준비 요청
 */
public record EFormSignFileUploadPrepareRequest(
    @NotBlank String filePath
) {

    /**
     * 파일 경로 정규화
     */
    public EFormSignFileUploadPrepareRequest {
        filePath = FileUtils.normalizeFilePath(filePath);
    }
}
