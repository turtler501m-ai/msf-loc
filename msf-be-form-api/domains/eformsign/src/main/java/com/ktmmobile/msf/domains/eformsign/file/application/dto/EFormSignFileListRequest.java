package com.ktmmobile.msf.domains.eformsign.file.application.dto;

import jakarta.validation.constraints.NotBlank;

import com.ktmmobile.msf.commons.file.support.util.FileUtils;

/**
 * eFormSign 파일 목록 조회 요청
 */
public record EFormSignFileListRequest(
    @NotBlank String directoryPath
) {

    /**
     * 디렉토리 경로 정규화
     */
    public EFormSignFileListRequest {
        directoryPath = FileUtils.normalizeDirectoryPath(directoryPath);
    }
}
