package com.ktmmobile.msf.commons.file.application.dto;

import org.springframework.util.Assert;

import com.ktmmobile.msf.commons.file.support.util.FileUtils;

import static com.ktmmobile.msf.commons.file.application.port.in.CommonFileService.DEFAULT_FILE_LIMIT;

// 파일 목록 요청
public record FileListRequest(
    String directoryPath,
    Integer limit
) {

    public FileListRequest {
        directoryPath = FileUtils.normalizeDirectoryPath(directoryPath);
        if (limit == null) {
            limit = DEFAULT_FILE_LIMIT;
        }
        Assert.isTrue(limit >= 1, "limit은 1 이상이어야 합니다.");
        Assert.isTrue(limit <= DEFAULT_FILE_LIMIT, "limit은 1000 이하여야 합니다.");
    }
}
