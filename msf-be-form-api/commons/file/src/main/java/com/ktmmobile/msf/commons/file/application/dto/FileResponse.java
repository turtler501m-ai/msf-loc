package com.ktmmobile.msf.commons.file.application.dto;

import java.util.List;

import lombok.Builder;

import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.commons.file.domain.vo.RawFile;

@Builder
public record FileResponse(
    String filePath,
    String directoryPath,
    String fileName,
    RawFile rawFile,
    String downloadSignedUrl
) {

    public static FileResponse of(CommonFile file) {
        return of(file, file.signedUrl());
    }

    public static FileResponse of(CommonFile file, String signedUrl) {
        return FileResponse.builder()
            .filePath(resolveFilePath(file.rawFile()))
            .directoryPath(resolveDirectoryPath(file.rawFile()))
            .fileName(file.rawFile().fileName())
            .rawFile(file.rawFile())
            .downloadSignedUrl(signedUrl)
            .build();
    }

    public static List<FileResponse> of(List<CommonFile> files) {
        return files.stream()
            .map(FileResponse::of)
            .toList();
    }

    private static String resolveFilePath(RawFile rawFile) {
        String directoryPath = resolveDirectoryPath(rawFile);
        if ("/".equals(directoryPath)) {
            return directoryPath + rawFile.fileName();
        }
        return directoryPath + "/" + rawFile.fileName();
    }

    private static String resolveDirectoryPath(RawFile rawFile) {
        if (rawFile.directoryPath() == null || rawFile.directoryPath().isBlank()) {
            return "/";
        }
        return "/" + rawFile.directoryPath();
    }
}
