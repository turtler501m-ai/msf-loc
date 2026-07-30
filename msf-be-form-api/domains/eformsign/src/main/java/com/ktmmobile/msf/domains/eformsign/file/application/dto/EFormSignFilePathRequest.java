package com.ktmmobile.msf.domains.eformsign.file.application.dto;

import jakarta.validation.constraints.NotBlank;

import com.ktmmobile.msf.commons.file.support.util.FileUtils;

/**
 * eFormSign 파일 경로 요청
 */
public record EFormSignFilePathRequest(
    @NotBlank String filePath
) {

    /**
     * 파일 경로 정규화
     */
    public EFormSignFilePathRequest {
        filePath = FileUtils.normalizeFilePath(filePath);
    }
}
