package com.ktmmobile.msf.commons.file.application.dto;

import java.util.List;

import lombok.Builder;

import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.commons.file.domain.vo.RawFile;

@Builder
public record FileResponse(
    String filePathName,
    String filePath,
    String fileName,
    RawFile rawFile,
    String downloadSignedUrl
) {

    public static FileResponse of(CommonFile file) {
        return of(file, file.signedUrl());
    }

    public static FileResponse of(CommonFile file, String signedUrl) {
        return FileResponse.builder()
            .filePathName(resolveFilePathName(file.rawFile()))
            .filePath(resolveFilePath(file.rawFile()))
            .fileName(file.rawFile().name())
            .rawFile(file.rawFile())
            .downloadSignedUrl(signedUrl)
            .build();
    }

    public static List<FileResponse> of(List<CommonFile> files) {
        return files.stream()
            .map(FileResponse::of)
            .toList();
    }

    private static String resolveFilePathName(RawFile rawFile) {
        String path = resolveFilePath(rawFile);
        if ("/".equals(path)) {
            return path + rawFile.name();
        }
        return path + "/" + rawFile.name();
    }

    private static String resolveFilePath(RawFile rawFile) {
        if (rawFile.path() == null || rawFile.path().isBlank()) {
            return "/";
        }
        return "/" + rawFile.path();
    }
}
