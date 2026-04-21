package com.ktmmobile.msf.commons.file.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.file.application.dto.FileRequest;
import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.commons.file.domain.dto.FileContent;
import com.ktmmobile.msf.commons.file.domain.vo.RawFile;
import com.ktmmobile.msf.commons.file.support.exception.FileServiceException;
import com.ktmmobile.msf.commons.file.support.properties.FilePolicy;
import com.ktmmobile.msf.commons.file.support.properties.LocalFilesystemProperties;
import com.ktmmobile.msf.commons.file.support.util.FileUtils;
import com.ktmmobile.msf.commons.file.support.validator.FilePolicyValidator;

@Slf4j
@RequiredArgsConstructor
@Primary
@Component(LocalFilesystemFileService.BEAN_NAME)
public class LocalFilesystemFileService implements CommonFileService {

    public static final String BEAN_NAME = "localFilesystemFileService";

    private static final Tika TIKA = new Tika();

    private final LocalFilesystemProperties properties;
    private final FilePolicyValidator filePolicyValidator;

    @Override
    public CommonFile writeFile(FileRequest fileRequest) {
        return writeFile(fileRequest, null);
    }

    @Override
    public CommonFile writeFile(FileRequest fileRequest, FilePolicy filePolicy) {
        CommonFile commonFile = createFile(fileRequest, filePolicy);
        Path targetPath = resolvePhysicalPath(commonFile.pathFileName());

        try {
            Files.createDirectories(targetPath.getParent());
            try (InputStream inputStream = fileRequest.file().getInputStream()) {
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
            log.info("파일 업로드: basePath={}, pathFileName={}, size={}, mimeType={}",
                properties.basePath(),
                commonFile.pathFileName(),
                commonFile.rawFile().size(),
                commonFile.rawFile().getMimeType());
            return commonFile;
        } catch (IOException e) {
            throw new FileServiceException("파일을 저장하는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public boolean fileExists(String pathFileName) {
        Path filePath = resolvePhysicalPath(pathFileName);
        logFilePath(filePath);
        return Files.exists(filePath);
    }

    private static void logFilePath(Path filePath) {
        log.info("파일 경로: {}", filePath);
    }

    @Override
    public Optional<CommonFile> getFile(String pathFileName) {
        Path filePath = resolvePhysicalPath(pathFileName);
        logFilePath(filePath);
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }

        try {
            return Optional.of(toCommonFile(pathFileName, Files.size(filePath), detectMediaType(filePath)));
        } catch (IOException e) {
            throw new FileServiceException("파일을 조회하는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Optional<FileContent> readFile(String pathFileName) {
        Path filePath = resolvePhysicalPath(pathFileName);
        logFilePath(filePath);
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }

        try {
            CommonFile commonFile = toCommonFile(pathFileName, Files.size(filePath), detectMediaType(filePath));
            return Optional.of(new FileContent(commonFile, Files.readAllBytes(filePath)));
        } catch (IOException e) {
            throw new FileServiceException("파일을 읽는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void removeFile(String pathFileName) {
        Path filePath = resolvePhysicalPath(pathFileName);
        logFilePath(filePath);
        try {
            boolean deleted = Files.deleteIfExists(filePath);
            if (!deleted) {
                log.warn("삭제 대상 파일이 없습니다: {}", filePath);
            }
        } catch (IOException e) {
            throw new FileServiceException("파일을 삭제하는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    public String generateSignedUrl(String pathFileName) {
        return generateSignedUrl(pathFileName, Duration.ZERO);
    }

    @Override
    public String generateSignedUrl(String pathFileName, Duration duration) {
        throw new FileServiceException("로컬 파일시스템은 Signed URL을 지원하지 않습니다.");
    }

    private CommonFile createFile(FileRequest fileRequest, FilePolicy filePolicy) {
        return CommonFile.builder()
            .pathFileName(fileRequest.getPathFileName())
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
                .path(fileRequest.resolvedFilePath())
                .name(fileRequest.resolvedFileName())
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

    private CommonFile toCommonFile(String pathFileName, long size, MediaType mediaType) {
        String normalizedPathFileName = FileUtils.normalizePathFileName(pathFileName);
        int separatorIndex = normalizedPathFileName.lastIndexOf('/');
        String path = separatorIndex < 0 ? "" : normalizedPathFileName.substring(0, separatorIndex);
        String name = separatorIndex < 0 ? normalizedPathFileName : normalizedPathFileName.substring(separatorIndex + 1);

        return CommonFile.builder()
            .pathFileName(normalizedPathFileName)
            .rawFile(RawFile.builder()
                .path(path)
                .name(name)
                .extension(StringUtils.getFilenameExtension(name))
                .size(size)
                .mediaType(mediaType)
                .build())
            .build();
    }

    private MediaType detectMediaType(Path filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(filePath)) {
            return MediaType.valueOf(TIKA.detect(inputStream));
        }
    }

    private Path resolvePhysicalPath(String pathFileName) {
        String normalizedPathFileName = FileUtils.normalizePathFileName(pathFileName);
        return Path.of(properties.basePath()).resolve(normalizedPathFileName);
    }

}
