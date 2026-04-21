package com.ktmmobile.msf.commons.file.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import com.ktmmobile.msf.commons.file.application.dto.FileRequest;
import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.commons.file.domain.dto.FileContent;
import com.ktmmobile.msf.commons.file.domain.vo.RawFile;
import com.ktmmobile.msf.commons.file.support.exception.FileServiceException;
import com.ktmmobile.msf.commons.file.support.properties.FilePolicy;
import com.ktmmobile.msf.commons.file.support.properties.ObjectStorageProperties;
import com.ktmmobile.msf.commons.file.support.util.FileUtils;
import com.ktmmobile.msf.commons.file.support.validator.FilePolicyValidator;

@Slf4j
@RequiredArgsConstructor
@Component(ObjectStorageFileService.BEAN_NAME)
public class ObjectStorageFileService implements CommonFileService {

    public static final String BEAN_NAME = "objectStorageFileService";

    private static final String ERROR_MESSAGE_FORMAT = "파일 %s 중에 오류가 발생했습니다.";

    private static final Duration DEFAULT_SIGNED_URL_EXPIRATION = Duration.ofHours(1L);
    private static final Tika TIKA = new Tika();

    private final ObjectStorageProperties properties;
    private final FilePolicyValidator filePolicyValidator;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Override
    public CommonFile writeFile(FileRequest fileRequest) {
        return writeFile(fileRequest, null);
    }

    @Override
    public CommonFile writeFile(FileRequest fileRequest, FilePolicy filePolicy) {
        try {
            CommonFile commonFile = createFile(fileRequest, filePolicy);
            uploadFileToStorage(fileRequest, commonFile);
            log.info("파일 업로드: bucket={}, pathFileName={}, size={}, mimeType={}",
                properties.bucket(),
                commonFile.pathFileName(),
                commonFile.rawFile().size(),
                commonFile.rawFile().getMimeType());
            return commonFile.withSignedUrl(generateSignedUrl(commonFile.pathFileName()));
        } catch (IOException | S3Exception | SdkClientException e) {
            deleteWrittenFileOnFailure(resolveStoragePath(fileRequest.getPathFileName()));
            throw new FileServiceException(createErrorMessage("쓰기"), e);
        }
    }

    private void uploadFileToStorage(FileRequest fileRequest, CommonFile commonFile) throws IOException, S3Exception, SdkClientException {
        try (InputStream inputStream = fileRequest.file().getInputStream()) {
            PutObjectRequest request = PutObjectRequest.builder()
                .bucket(properties.bucket())
                .key(commonFile.pathFileName())
                .contentType(fileRequest.file().getContentType())
                .build();
            s3Client.putObject(request, RequestBody.fromInputStream(inputStream, fileRequest.file().getSize()));
        }
    }

    private void deleteWrittenFileOnFailure(String pathFileName) {
        try {
            deleteFileBy(pathFileName);
        } catch (Exception e) {
            log.warn("{}", createErrorMessage("쓰기 실패로 인한 파일 삭제"), e);
        }
    }

    private RawFile createRawFile(FileRequest fileRequest, FilePolicy filePolicy) throws IOException {
        try (InputStream inputStream = fileRequest.file().getInputStream()) {
            String originalFileName = resolveUploadFileName(fileRequest);
            String detectedMimeType = getMimeType(inputStream);
            validateFilePolicy(filePolicy, originalFileName, detectedMimeType, fileRequest.file().getSize());

            return RawFile.builder()
                .path(resolveStoragePath(fileRequest.resolvedFilePath()))
                .name(fileRequest.resolvedFileName())
                .extension(StringUtils.getFilenameExtension(originalFileName))
                .size(fileRequest.file().getSize())
                .mediaType(MediaType.valueOf(detectedMimeType))
                .build();
        }
    }

    private static String getMimeType(InputStream inputStream) throws IOException {
        return TIKA.detect(inputStream);
    }

    private CommonFile createFile(FileRequest fileRequest, FilePolicy filePolicy) throws IOException {
        RawFile rawFile = createRawFile(fileRequest, filePolicy);
        return CommonFile.builder()
            .pathFileName(rawFile.getPathFileName())
            .rawFile(rawFile)
            .build();
    }

    private void validateFilePolicy(FilePolicy filePolicy, String originalFileName, String detectedMimeType, long fileSize) {
        if (filePolicy == null) {
            filePolicyValidator.validate(originalFileName, detectedMimeType, fileSize);
            return;
        }
        filePolicyValidator.validate(filePolicy, originalFileName, detectedMimeType, fileSize);
    }

    private static String resolveUploadFileName(FileRequest fileRequest) {
        if (StringUtils.hasText(fileRequest.file().getOriginalFilename())) {
            return fileRequest.file().getOriginalFilename();
        }
        return fileRequest.resolvedFileName();
    }

    private String createErrorMessage(String workName) {
        return String.format(ERROR_MESSAGE_FORMAT, workName);
    }

    private String resolveStoragePath(String path) {
        String normalizedRootPath = FileUtils.normalizeDirectoryPath(properties.basePath());
        String normalizedPath = FileUtils.normalizeDirectoryPath(path);

        if (!StringUtils.hasText(normalizedRootPath)) {
            return normalizedPath;
        }
        if (!StringUtils.hasText(normalizedPath)) {
            return normalizedRootPath;
        }
        return FileUtils.concat(normalizedRootPath, normalizedPath);
    }

    @Override
    public boolean fileExists(String pathFileName) {
        String normalizedPathFileName = FileUtils.normalizePathFileName(pathFileName);
        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(properties.bucket())
                .key(normalizedPathFileName)
                .build();
            s3Client.headObject(request);
            return true;
        } catch (NoSuchKeyException _) {
            return false;
        } catch (S3Exception e) {
            if (isStatusCodeNotFound(e)) {
                return false;
            }
            throw new FileServiceException(createErrorMessage("조회"), e);
        } catch (SdkClientException e) {
            throw new FileServiceException(createErrorMessage("조회"), e);
        }
    }

    private void logFilePath(String normalizedPathFileName) {
        log.info("파일 경로: bucket={}, path={}", properties.bucket(), normalizedPathFileName);
    }

    private static boolean isStatusCodeNotFound(S3Exception e) {
        return e.statusCode() == HttpStatus.NOT_FOUND.value();
    }

    @Override
    public Optional<CommonFile> getFile(String pathFileName) {
        String normalizedPathFileName = FileUtils.normalizePathFileName(pathFileName);
        logFilePath(normalizedPathFileName);
        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(properties.bucket())
                .key(normalizedPathFileName)
                .build();
            HeadObjectResponse response = s3Client.headObject(request);
            CommonFile commonFile = toCommonFile(normalizedPathFileName, response.contentType(), response.contentLength());
            return Optional.of(commonFile.withSignedUrl(generateSignedUrl(normalizedPathFileName)));
        } catch (NoSuchKeyException _) {
            return Optional.empty();
        } catch (S3Exception e) {
            if (isStatusCodeNotFound(e)) {
                return Optional.empty();
            }
            throw new FileServiceException(createErrorMessage("조회"), e);
        } catch (SdkClientException e) {
            throw new FileServiceException(createErrorMessage("조회"), e);
        }
    }

    @Override
    public Optional<FileContent> readFile(String pathFileName) {
        String normalizedPathFileName = FileUtils.normalizePathFileName(pathFileName);
        logFilePath(normalizedPathFileName);
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                .bucket(properties.bucket())
                .key(normalizedPathFileName)
                .build();
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(request);
            CommonFile commonFile = toCommonFile(
                normalizedPathFileName,
                objectBytes.response().contentType(),
                objectBytes.response().contentLength()
            );
            return Optional.of(new FileContent(commonFile, objectBytes.asByteArray()));
        } catch (S3Exception e) {
            if (isStatusCodeNotFound(e)) {
                return Optional.empty();
            }
            throw new FileServiceException(createErrorMessage("읽기"), e);
        } catch (SdkClientException e) {
            throw new FileServiceException(createErrorMessage("읽기"), e);
        }
    }

    @Override
    public void removeFile(String pathFileName) {
        String normalizedPathFileName = FileUtils.normalizePathFileName(pathFileName);
        logFilePath(normalizedPathFileName);
        if (!fileExists(normalizedPathFileName)) {
            log.warn("삭제 대상 파일이 없습니다: bucket={}, path={}", properties.bucket(), normalizedPathFileName);
            return;
        }
        try {
            deleteFileBy(normalizedPathFileName);
        } catch (S3Exception | SdkClientException e) {
            throw new FileServiceException(createErrorMessage("삭제"), e);
        }
    }

    private void deleteFileBy(String pathFileName) throws S3Exception, SdkClientException {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
            .bucket(properties.bucket())
            .key(pathFileName)
            .build();
        s3Client.deleteObject(request);
    }

    @Override
    public String generateSignedUrl(String pathFileName) {
        return generateSignedUrl(pathFileName, DEFAULT_SIGNED_URL_EXPIRATION);
    }

    @Override
    public String generateSignedUrl(String pathFileName, Duration duration) {
        String normalizedPathFileName = FileUtils.normalizePathFileName(pathFileName);
        logFilePath(normalizedPathFileName);
        try {
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duration)
                .getObjectRequest(builder -> builder
                    .bucket(properties.bucket())
                    .key(normalizedPathFileName))
                .build();
            PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
            return presignedRequest.url().toString();
        } catch (S3Exception | SdkClientException e) {
            throw new FileServiceException(createErrorMessage("조회"), e);
        }
    }

    private CommonFile toCommonFile(String pathFileName, String contentType, Long size) {
        String[] pathParts = splitPathFileName(pathFileName);

        return CommonFile.builder()
            .pathFileName(pathFileName)
            .rawFile(RawFile.builder()
                .path(pathParts[0])
                .name(pathParts[1])
                .extension(StringUtils.getFilenameExtension(pathParts[1]))
                .size(size == null ? 0L : size)
                .mediaType(resolveMediaType(contentType))
                .build())
            .build();
    }

    private static MediaType resolveMediaType(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
        return MediaType.parseMediaType(contentType);
    }

    private static String[] splitPathFileName(String pathFileName) {
        int separatorIndex = pathFileName.lastIndexOf('/');
        if (separatorIndex < 0) {
            return new String[] {"", pathFileName};
        }
        return new String[] {
            pathFileName.substring(0, separatorIndex),
            pathFileName.substring(separatorIndex + 1)
        };
    }

}
