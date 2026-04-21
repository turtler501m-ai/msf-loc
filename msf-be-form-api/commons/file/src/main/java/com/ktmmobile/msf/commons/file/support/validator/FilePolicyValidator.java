package com.ktmmobile.msf.commons.file.support.validator;

import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.support.properties.FilePolicy;
import com.ktmmobile.msf.commons.file.support.properties.FilePolicyProperties;

@RequiredArgsConstructor
@Component
public class FilePolicyValidator {

    private static final Pattern INVALID_FILE_NAME_CHARACTERS = Pattern.compile("[\\\\/:*?\"<>|]");
    private static final Pattern CONTROL_CHARACTERS = Pattern.compile("\\p{Cntrl}");

    private final FilePolicyProperties filePolicyProperties;

    public void validate(String originalFileName, String detectedMimeType) {
        validate(originalFileName, detectedMimeType, null);
    }

    public void validate(String originalFileName, String detectedMimeType, Long fileSize) {
        validate(filePolicyProperties, originalFileName, detectedMimeType, fileSize);
    }

    public void validate(FilePolicy filePolicy, String originalFileName, String detectedMimeType, Long fileSize) {
        validateOriginalFileName(filePolicy, originalFileName);
        validateFileSize(filePolicy, fileSize);

        String extension = StringUtils.getFilenameExtension(originalFileName);
        String normalizedExtension = extension == null ? "" : extension.toLowerCase();

        boolean allowedExtension = filePolicy.allowedExtensions() != null
            && filePolicy.allowedExtensions().stream()
            .map(String::toLowerCase)
            .anyMatch(normalizedExtension::equals);
        boolean allowedMimeType = filePolicy.allowedMimeTypes() != null
            && filePolicy.allowedMimeTypes().stream()
            .anyMatch(detectedMimeType::equalsIgnoreCase);

        if (!allowedExtension || !allowedMimeType) {
            throw new SimpleDomainException(String.format("허용하지 않는 파일 형식입니다. extension=%s, mimeType=%s",
                normalizedExtension, detectedMimeType
            ));
        }
    }

    private void validateFileSize(FilePolicy filePolicy, Long fileSize) {
        if (filePolicy.maxFileSize() == null || fileSize == null) {
            return;
        }
        if (fileSize > filePolicy.maxFileSize().toBytes()) {
            throw new SimpleDomainException(String.format("파일 용량은 %s 이하여야 합니다.",
                filePolicy.maxFileSize()));
        }
    }

    private void validateOriginalFileName(FilePolicy filePolicy, String originalFileName) {
        if (!StringUtils.hasText(originalFileName)) {
            throw new SimpleDomainException("원본 파일명이 유효하지 않습니다.");
        }
        if (!originalFileName.equals(originalFileName.trim())) {
            throw new SimpleDomainException("원본 파일명 앞뒤 공백은 허용되지 않습니다.");
        }
        if (".".equals(originalFileName) || "..".equals(originalFileName)) {
            throw new SimpleDomainException("원본 파일명이 유효하지 않습니다.");
        }
        if (CONTROL_CHARACTERS.matcher(originalFileName).find()) {
            throw new SimpleDomainException("원본 파일명에 제어문자는 사용할 수 없습니다.");
        }
        if (INVALID_FILE_NAME_CHARACTERS.matcher(originalFileName).find()) {
            throw new SimpleDomainException("원본 파일명에 사용할 수 없는 특수문자가 포함되어 있습니다.");
        }

    }
}
