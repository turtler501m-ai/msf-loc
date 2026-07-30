package com.ktmmobile.msf.commons.file.application.dto;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.support.util.FileUtils;

public record FileRequest(
    MultipartFile file,
    String fileCategory,
    String resolvedFilePath,
    String resolvedFileName,
    boolean directFilePath
) {

    public FileRequest(
        MultipartFile file,
        String fileCategory,
        String resolvedFilePath,
        String resolvedFileName
    ) {
        this(file, fileCategory, resolvedFilePath, resolvedFileName, false);
    }

    public static FileRequest of(
        MultipartFile file,
        String fileCategory
    ) {
        validateFile(file);
        String resolvedFileCategory = resolveRequiredFileCategory(fileCategory);
        String resolvedFilePath = resolveFilePath(resolvedFileCategory);
        String resolvedFileName = FileUtils.generateStoredFileName(file.getOriginalFilename());
        return new FileRequest(file, resolvedFileCategory, resolvedFilePath, resolvedFileName);
    }

    public static FileRequest ofFilePath(
        MultipartFile file,
        String filePath
    ) {
        validateFile(file);
        String normalizedFilePath = normalizeRequiredFilePath(filePath);
        int separatorIndex = normalizedFilePath.lastIndexOf('/');
        String resolvedFilePath = separatorIndex < 0 ? "" : normalizedFilePath.substring(0, separatorIndex);
        String resolvedFileName = separatorIndex < 0 ? normalizedFilePath : normalizedFilePath.substring(separatorIndex + 1);
        return new FileRequest(file, null, resolvedFilePath, resolvedFileName, true);
    }

    public static FileRequest of(
        MultipartFile file,
        String fileCategory,
        String resolvedFilePath,
        String resolvedFileName
    ) {
        validateFile(file);
        String resolvedFileCategory = resolveRequiredFileCategory(fileCategory);
        return new FileRequest(file, resolvedFileCategory, resolvedFilePath, resolvedFileName);
    }

    private static String resolveFilePath(String fileCategory) {
        return FileUtils.concat(fileCategory, FileUtils.getDirNameUsingDate());
    }

    public String getFilePath() {
        return FileUtils.concat(resolvedFilePath(), resolvedFileName());
    }

    private static String normalizeRequiredFilePath(String filePath) {
        String normalizedFilePath = filePath == null ? "" : filePath.trim().replace("\\", "/");
        if (!StringUtils.hasText(normalizedFilePath)) {
            throw new SimpleDomainException("파일 경로는 필수입니다.");
        }
        if (!StringUtils.hasText(StringUtils.getFilename(normalizedFilePath))) {
            throw new SimpleDomainException("파일명을 확인할 수 없습니다.");
        }
        return normalizedFilePath;
    }

    private static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new SimpleDomainException("업로드할 파일을 선택해주세요.");
        }
    }

    // 파일 분류 경로 검증
    private static String resolveRequiredFileCategory(String fileCategory) {
        String normalizedFileCategory = FileUtils.normalizeDirectoryPath(fileCategory);
        if (!StringUtils.hasText(normalizedFileCategory)) {
            throw new SimpleDomainException("파일 분류는 필수입니다.");
        }
        if (normalizedFileCategory.length() < 2) {
            throw new SimpleDomainException("파일 분류는 2글자 이상이어야 합니다.");
        }
        if (!isValidDirectoryPath(normalizedFileCategory)) {
            throw new SimpleDomainException("파일 분류는 디렉토리 경로로 사용할 수 있는 문자열이어야 합니다.");
        }
        return normalizedFileCategory;
    }

    private static boolean isValidDirectoryPath(String directoryPath) {
        if (directoryPath.contains("//")) {
            return false;
        }
        for (String directoryName: directoryPath.split("/")) {
            if (!StringUtils.hasText(directoryName) || ".".equals(directoryName) || "..".equals(directoryName) || containsInvalidDirectoryPathCharacter(directoryName)) {
                return false;
            }
        }
        return true;
    }

    private static boolean containsInvalidDirectoryPathCharacter(String directoryName) {
        return directoryName.chars().anyMatch(character -> Character.isISOControl(character) || "<>:\"|?*".indexOf(character) >= 0);
    }
}
