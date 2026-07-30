package com.ktmmobile.msf.commons.file.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.file.application.dto.FileRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileVariantOptions;
import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.commons.file.domain.dto.FileContent;
import com.ktmmobile.msf.commons.file.domain.dto.FileVariantResult;
import com.ktmmobile.msf.commons.file.domain.vo.RawFile;
import com.ktmmobile.msf.commons.file.support.exception.FileServiceException;
import com.ktmmobile.msf.commons.file.support.properties.FileImageVariantProperties;
import com.ktmmobile.msf.commons.file.support.properties.FilePolicy;
import com.ktmmobile.msf.commons.file.support.properties.LocalFilesystemProperties;
import com.ktmmobile.msf.commons.file.support.util.FileImagePathUtils;
import com.ktmmobile.msf.commons.file.support.util.FileImageResizeUtils;
import com.ktmmobile.msf.commons.file.support.validator.FilePolicyValidator;

@Slf4j
@RequiredArgsConstructor
@Primary
@Component(LocalFilesystemFileService.BEAN_NAME)
public class LocalFilesystemFileService implements CommonFileService {

    public static final String BEAN_NAME = "localFilesystemFileService";

    private static final Tika TIKA = new Tika();

    private final FileImageVariantProperties imageVariantProperties;
    private final LocalFilesystemProperties properties;
    private final FilePolicyValidator filePolicyValidator;

    @Override
    public CommonFile writeFile(FileRequest fileRequest) {
        return writeFile(fileRequest, (FilePolicy) null);
    }

    @Override
    public CommonFile writeFile(FileRequest fileRequest, FilePolicy filePolicy) {
        CommonFile commonFile = createFile(fileRequest, filePolicy);
        Path targetPath = resolvePhysicalPath(commonFile.filePath());

        try {
            Files.createDirectories(targetPath.getParent());
            try (InputStream inputStream = fileRequest.file().getInputStream()) {
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
            log.info("파일 업로드: basePath={}, filePath={}", properties.basePath(), commonFile.filePath());

            return commonFile;
        } catch (IOException e) {
            throw new FileServiceException("파일을 저장하는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public FileVariantResult writeFile(FileRequest fileRequest, FileVariantOptions variantOptions) {
        if (variantOptions == null || !variantOptions.hasVariants()) {
            return new FileVariantResult(writeFile(fileRequest), Map.of());
        }
        try {
            Map<String, FileRequest> variantRequests = FileImageResizeUtils.createVariantRequests(fileRequest, variantOptions, imageVariantProperties);
            CommonFile original = writeFile(fileRequest);
            Map<String, CommonFile> variants = new LinkedHashMap<>();
            for (Map.Entry<String, FileRequest> entry: variantRequests.entrySet()) {
                variants.put(entry.getKey(), writeFile(entry.getValue()));
            }
            return new FileVariantResult(original, variants);
        } catch (IOException e) {
            throw new FileServiceException("변형 파일을 생성하는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public boolean fileExists(String filePath) {
        Path physicalFilePath = resolvePhysicalPath(filePath);
        logFilePath(physicalFilePath);
        return Files.exists(physicalFilePath);
    }

    private static void logFilePath(Path filePath) {
        log.info("파일 경로: {}", filePath);
    }

    @Override
    public Optional<CommonFile> getFile(String filePath) {
        Path physicalFilePath = resolvePhysicalPath(filePath);
        logFilePath(physicalFilePath);
        if (!Files.exists(physicalFilePath)) {
            return Optional.empty();
        }

        try {
            return Optional.of(toCommonFile(filePath, Files.size(physicalFilePath), detectMediaType(physicalFilePath)));
        } catch (IOException e) {
            throw new FileServiceException("파일을 조회하는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public List<CommonFile> listFiles(String directoryPath, int limit) {
        validateLimit(limit);
        Path directory = resolvePhysicalDirectoryPath(directoryPath);
        logFilePath(directory);
        if (!Files.isDirectory(directory)) {
            return List.of();
        }

        try (Stream<Path> paths = Files.walk(directory)) {
            return paths.filter(Files::isRegularFile)
                .limit(limit)
                .map(this::toCommonFile)
                .toList();
        } catch (IOException e) {
            throw new FileServiceException("파일 목록을 조회하는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Optional<FileContent> readFile(String filePath) {
        Path physicalFilePath = resolvePhysicalPath(filePath);
        logFilePath(physicalFilePath);
        if (!Files.exists(physicalFilePath)) {
            return Optional.empty();
        }

        try {
            CommonFile commonFile = toCommonFile(filePath, Files.size(physicalFilePath), detectMediaType(physicalFilePath));
            return Optional.of(new FileContent(commonFile, Files.readAllBytes(physicalFilePath)));
        } catch (IOException e) {
            throw new FileServiceException("파일을 읽는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void removeFile(String filePath) {
        Path physicalFilePath = resolvePhysicalPath(filePath);
        logFilePath(physicalFilePath);
        try {
            for (String deleteTarget: FileImagePathUtils.resolveImageFilePaths(filePath)) {
                Path deletePath = resolvePhysicalPath(deleteTarget);
                boolean deleted = Files.deleteIfExists(deletePath);
                if (!deleted) {
                    log.warn("삭제 대상 파일이 없습니다: {}", deletePath);
                }
            }
        } catch (IOException e) {
            throw new FileServiceException("파일을 삭제하는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public int removeFilesModifiedBefore(String directoryPath, Instant baseDateTime, int limit) {
        validateLimit(limit);
        Path directory = resolvePhysicalDirectoryPath(directoryPath);
        log.info("기준일 이전 파일 삭제 시작: basePath={}, directoryPath={}, baseDateTime={}, limit={}", properties.basePath(), directory, baseDateTime, limit);
        if (!Files.isDirectory(directory)) {
            log.warn("삭제 대상 디렉토리가 없습니다: {}", directory);
            return 0;
        }

        try (Stream<Path> paths = Files.walk(directory)) {
            AtomicInteger deletedCount = new AtomicInteger();
            paths.filter(Files::isRegularFile)
                .filter(filePath -> isModifiedBefore(filePath, baseDateTime))
                .limit(limit)
                .forEach(deleteTarget -> deleteFileBeforeBaseDateTime(deleteTarget, deletedCount));
            log.info("기준일 이전 파일 삭제 완료: directoryPath={}, count={}", directory, deletedCount.get());
            return deletedCount.get();
        } catch (IOException e) {
            throw new FileServiceException("기준일 이전 파일을 삭제하는 중에 오류가 발생했습니다.", e);
        }
    }

    private void deleteFileBeforeBaseDateTime(Path deleteTarget, AtomicInteger deletedCount) {
        try {
            if (Files.deleteIfExists(deleteTarget)) {
                deletedCount.incrementAndGet();
                log.info("기준일 이전 파일 삭제: filePath={}", deleteTarget);
            }
        } catch (IOException e) {
            throw new FileServiceException("기준일 이전 파일을 삭제하는 중에 오류가 발생했습니다.", e);
        }
    }

    private boolean isModifiedBefore(Path filePath, Instant baseDateTime) {
        try {
            return Files.getLastModifiedTime(filePath).toInstant().isBefore(baseDateTime);
        } catch (IOException e) {
            throw new FileServiceException("파일 수정일을 조회하는 중에 오류가 발생했습니다.", e);
        }
    }

    private static void validateLimit(int limit) {
        Assert.isTrue(limit >= 1, "limit은 1 이상이어야 합니다.");
        Assert.isTrue(limit <= DEFAULT_FILE_LIMIT, "limit은 1000 이하여야 합니다.");
    }

    @Override
    public String generateSignedUrl(String filePath) {
        return generateSignedUrl(filePath, Duration.ZERO);
    }

    @Override
    public String generateSignedUrl(String filePath, Duration duration) {
        throw new FileServiceException("로컬 파일시스템은 Signed URL을 지원하지 않습니다.");
    }

    @Override
    public String generateUploadSignedUrl(String filePath) {
        return generateUploadSignedUrl(filePath, Duration.ZERO);
    }

    @Override
    public String generateUploadSignedUrl(String filePath, Duration duration) {
        throw new FileServiceException("로컬 파일시스템은 Signed URL을 지원하지 않습니다.");
    }

    @Override
    public String generateUploadSignedUrl(String filePath, String contentType) {
        return generateUploadSignedUrl(filePath, contentType, Duration.ZERO);
    }

    @Override
    public String generateUploadSignedUrl(String filePath, String contentType, Duration duration) {
        throw new FileServiceException("로컬 파일시스템은 Signed URL을 지원하지 않습니다.");
    }

    private CommonFile createFile(FileRequest fileRequest, FilePolicy filePolicy) {
        return CommonFile.builder()
            .filePath(fileRequest.getFilePath())
            .rawFile(createRawFile(fileRequest, filePolicy))
            .build();
    }

    private static String resolveUploadFileName(FileRequest fileRequest) {
        if (StringUtils.hasText(fileRequest.file().getOriginalFilename())) {
            return fileRequest.file().getOriginalFilename();
        }
        return fileRequest.resolvedFileName();
    }

    private RawFile createRawFile(FileRequest fileRequest, FilePolicy filePolicy) {
        try (InputStream inputStream = fileRequest.file().getInputStream()) {
            String originalFileName = resolveUploadFileName(fileRequest);
            String detectedMimeType = TIKA.detect(inputStream);
            validateFilePolicy(filePolicy, originalFileName, detectedMimeType, fileRequest.file().getSize());

            return RawFile.builder()
                .directoryPath(fileRequest.resolvedFilePath())
                .fileName(fileRequest.resolvedFileName())
                .extension(StringUtils.getFilenameExtension(originalFileName))
                .size(fileRequest.file().getSize())
                .mediaType(MediaType.valueOf(detectedMimeType))
                .build();
        } catch (IOException e) {
            throw new FileServiceException("파일 메타데이터를 생성하는 중에 오류가 발생했습니다.", e);
        }
    }

    private void validateFilePolicy(FilePolicy filePolicy, String originalFileName, String detectedMimeType, long fileSize) {
        if (filePolicy == null) {
            filePolicyValidator.validate(originalFileName, detectedMimeType, fileSize);
            return;
        }
        filePolicyValidator.validate(filePolicy, originalFileName, detectedMimeType, fileSize);
    }

    private CommonFile toCommonFile(String filePath, long size, MediaType mediaType) {
        String normalizedFilePath = normalizePhysicalFilePath(filePath);
        int separatorIndex = normalizedFilePath.lastIndexOf('/');
        String directoryPath = separatorIndex < 0 ? "" : normalizedFilePath.substring(0, separatorIndex);
        String fileName = separatorIndex < 0 ? normalizedFilePath : normalizedFilePath.substring(separatorIndex + 1);

        return CommonFile.builder()
            .filePath(normalizedFilePath)
            .rawFile(RawFile.builder()
                .directoryPath(directoryPath)
                .fileName(fileName)
                .extension(StringUtils.getFilenameExtension(fileName))
                .size(size)
                .mediaType(mediaType)
                .build())
            .build();
    }

    private CommonFile toCommonFile(Path filePath) {
        String logicalFilePath = resolveLogicalFilePath(filePath);
        try {
            return toCommonFile(logicalFilePath, Files.size(filePath), detectMediaType(filePath));
        } catch (IOException e) {
            throw new FileServiceException("파일 메타데이터를 조회하는 중에 오류가 발생했습니다.", e);
        }
    }

    private MediaType detectMediaType(Path filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(filePath)) {
            return MediaType.valueOf(TIKA.detect(inputStream));
        }
    }

    private Path resolvePhysicalPath(String filePath) {
        Path requestedPath = Path.of(removeRootPath(normalizePhysicalFilePath(filePath)));
        return resolveBasePathChild(requestedPath);
    }

    private Path resolvePhysicalDirectoryPath(String directoryPath) {
        Path requestedPath = Path.of(removeRootPath(normalizePhysicalDirectoryPath(directoryPath)));
        return resolveBasePathChild(requestedPath);
    }

    private Path resolveBasePathChild(Path requestedPath) {
        Path basePath = Path.of(properties.basePath()).normalize();
        Path resolvedPath = basePath.resolve(requestedPath).normalize();
        if (!resolvedPath.startsWith(basePath)) {
            throw new IllegalArgumentException("파일 경로는 기본 경로 하위여야 합니다.");
        }
        return resolvedPath;
    }

    private String resolveLogicalFilePath(Path filePath) {
        Path normalizedFilePath = filePath.normalize();
        Path basePath = Path.of(properties.basePath()).normalize();
        if (normalizedFilePath.startsWith(basePath)) {
            return normalizePhysicalFilePath(basePath.relativize(normalizedFilePath).toString());
        }
        return normalizePhysicalFilePath(normalizedFilePath.toString());
    }

    private static String normalizePhysicalFilePath(String filePath) {
        if (!StringUtils.hasText(filePath)) {
            return "";
        }
        return filePath.trim().replace("\\", "/");
    }

    private static String normalizePhysicalDirectoryPath(String directoryPath) {
        if (!StringUtils.hasText(directoryPath)) {
            return "";
        }
        String normalizedDirectoryPath = directoryPath.trim().replace("\\", "/");
        if ("/".equals(normalizedDirectoryPath)) {
            return "";
        }
        return normalizedDirectoryPath.replaceAll("/+$", "");
    }

    private static String removeRootPath(String path) {
        return path.replaceFirst("^/+", "");
    }

}
