package com.ktmmobile.msf.domains.eformsign.file.application.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.exception.NotFoundException;
import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;
import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.support.util.FileUtils;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileListRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileListResponse;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFilePathRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileResponse;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileUploadPrepareRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileUploadUrlResponse;
import com.ktmmobile.msf.domains.eformsign.file.application.port.in.EFormSignFileReader;
import com.ktmmobile.msf.domains.eformsign.file.application.port.in.EFormSignFileRemover;
import com.ktmmobile.msf.domains.eformsign.file.application.port.in.EFormSignFileUploadPreparer;
import com.ktmmobile.msf.domains.eformsign.file.application.port.out.EFormSignFileStorage;

/**
 * eFormSign 파일 서비스
 */
@RequiredArgsConstructor
@Service
public class EFormSignFileUploadService implements EFormSignFileUploadPreparer, EFormSignFileReader, EFormSignFileRemover {

    private static final Pattern CONTROL_CHARACTERS = Pattern.compile("\\p{Cntrl}");

    private static final String STORAGE_FILE_ROOT = "eformsign";
    private static final int MAX_STORAGE_FILE_PATH_BYTES = 1024;
    private static final int DEFAULT_FILE_LIST_LIMIT = CommonFileService.DEFAULT_FILE_LIMIT;

    private final EFormSignFileStorage eformSignFileStorage;

    /**
     * 파일 업로드 URL 발급
     */
    @Override
    public EFormSignFileUploadUrlResponse issueUploadUrl(EFormSignFileUploadPrepareRequest request) {
        String filePath = validatePublicFilePath(request.filePath());
        return new EFormSignFileUploadUrlResponse(
            filePath,
            eformSignFileStorage.generateUploadSignedUrl(toStorageFilePath(filePath))
        );
    }

    /**
     * 파일 정보 조회
     */
    @Override
    public EFormSignFileResponse getFile(EFormSignFilePathRequest request) {
        return eformSignFileStorage.getFile(toStorageFilePath(request.filePath()))
            .map(file -> EFormSignFileResponse.of(file, toPublicFilePath(file.filePath())))
            .orElseThrow(() -> new NotFoundException("파일을 찾을 수 없습니다."));
    }

    /**
     * 파일 목록 조회
     */
    @Override
    public List<EFormSignFileListResponse> listFiles(EFormSignFileListRequest request) {
        String directoryPath = validatePublicDirectoryPath(request.directoryPath());
        return eformSignFileStorage.listFiles(toStorageFilePath(directoryPath), DEFAULT_FILE_LIST_LIMIT)
            .stream()
            .map(file -> new EFormSignFileListResponse(toPublicFilePath(file.filePath())))
            .toList();
    }

    /**
     * 파일 삭제
     */
    @Override
    public void removeFile(EFormSignFilePathRequest request) {
        String storageFilePath = toStorageFilePath(request.filePath());
        if (eformSignFileStorage.getFile(storageFilePath).isEmpty()) {
            throw new NotFoundException("삭제 대상 파일을 찾을 수 없습니다.");
        }
        eformSignFileStorage.removeFile(storageFilePath);
    }

    private String validatePublicFilePath(String filePath) {
        String normalizedFilePath = toPublicFilePath(filePath);
        if (!StringUtils.hasText(normalizedFilePath)) {
            throw new SimpleDomainException("파일 경로가 유효하지 않습니다.");
        }
        if (normalizedFilePath.endsWith("/")) {
            throw new SimpleDomainException("파일 경로는 파일명을 포함해야 합니다.");
        }
        if (normalizedFilePath.contains("//")) {
            throw new SimpleDomainException("파일 경로에 빈 경로 구간은 사용할 수 없습니다.");
        }
        if (CONTROL_CHARACTERS.matcher(normalizedFilePath).find()) {
            throw new SimpleDomainException("파일 경로에 제어문자는 사용할 수 없습니다.");
        }
        for (String filePathPart: normalizedFilePath.split("/")) {
            if (!StringUtils.hasText(filePathPart) || ".".equals(filePathPart) || "..".equals(filePathPart)) {
                throw new SimpleDomainException("파일 경로가 유효하지 않습니다.");
            }
        }
        validateStorageFilePathLength(normalizedFilePath);
        return normalizedFilePath;
    }

    private String validatePublicDirectoryPath(String directoryPath) {
        String normalizedDirectoryPath = toPublicDirectoryPath(directoryPath);
        if (!StringUtils.hasText(normalizedDirectoryPath)) {
            throw new SimpleDomainException("디렉토리 경로가 유효하지 않습니다.");
        }
        if (normalizedDirectoryPath.contains("//")) {
            throw new SimpleDomainException("디렉토리 경로에 빈 경로 구간은 사용할 수 없습니다.");
        }
        if (CONTROL_CHARACTERS.matcher(normalizedDirectoryPath).find()) {
            throw new SimpleDomainException("디렉토리 경로에 제어문자는 사용할 수 없습니다.");
        }
        for (String directoryPathPart: normalizedDirectoryPath.split("/")) {
            if (!StringUtils.hasText(directoryPathPart) || ".".equals(directoryPathPart) || "..".equals(directoryPathPart)) {
                throw new SimpleDomainException("디렉토리 경로가 유효하지 않습니다.");
            }
        }
        validateStorageFilePathLength(normalizedDirectoryPath);
        return normalizedDirectoryPath;
    }

    private void validateStorageFilePathLength(String filePath) {
        int storageFilePathBytes = toStorageFilePath(filePath).getBytes(StandardCharsets.UTF_8).length;
        if (storageFilePathBytes > MAX_STORAGE_FILE_PATH_BYTES) {
            throw new SimpleDomainException("파일 경로는 UTF-8 기준 1024 bytes를 초과할 수 없습니다.");
        }
    }

    private String toStorageFilePath(String filePath) {
        return FileUtils.concat(STORAGE_FILE_ROOT, toPublicFilePath(filePath));
    }

    private String toPublicFilePath(String filePath) {
        String normalizedFilePath = FileUtils.normalizeFilePath(filePath);
        if (normalizedFilePath.startsWith(STORAGE_FILE_ROOT + "/")) {
            return normalizedFilePath.substring(STORAGE_FILE_ROOT.length() + 1);
        }
        int storageFileRootIndex = normalizedFilePath.indexOf("/" + STORAGE_FILE_ROOT + "/");
        if (storageFileRootIndex >= 0) {
            return normalizedFilePath.substring(storageFileRootIndex + STORAGE_FILE_ROOT.length() + 2);
        }
        return normalizedFilePath;
    }

    private String toPublicDirectoryPath(String directoryPath) {
        return FileUtils.normalizeDirectoryPath(toPublicFilePath(directoryPath));
    }
}
