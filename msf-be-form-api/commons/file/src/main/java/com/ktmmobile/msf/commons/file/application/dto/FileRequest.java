package com.ktmmobile.msf.commons.file.application.dto;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.support.util.FileUtils;

public record FileRequest(
    MultipartFile file,
    String fileCategory,
    String resolvedFilePath,
    String resolvedFileName
) {

    public static FileRequest of(
        MultipartFile file,
        String fileCategory
    ) {
        validateFile(file);
        String resolvedFilePath = resolveFilePath(fileCategory);
        String resolvedFileName = FileUtils.generateStoredFileName(file.getOriginalFilename());
        return new FileRequest(file, fileCategory, resolvedFilePath, resolvedFileName);
    }

    private static String resolveFilePath(String fileCategory) {
        if (StringUtils.hasText(fileCategory)) {
            return FileUtils.concat(
                FileUtils.normalizeDirectoryPath(fileCategory),
                FileUtils.getDirNameUsingDate()
            );
        }
        return FileUtils.getDirNameUsingDate();
    }

    public String getPathFileName() {
        return FileUtils.concat(resolvedFilePath(), resolvedFileName());
    }

    private static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new SimpleDomainException("업로드할 파일을 선택해주세요.");
        }
    }
}
