package com.ktmmobile.msf.commons.file.application.dto;

import com.ktmmobile.msf.commons.file.support.util.FileUtils;

// 전체 파일 경로 요청
public record FilePathRequest(
    String filePath
) {

    public FilePathRequest {
        filePath = FileUtils.normalizeFilePath(filePath);
    }
}
