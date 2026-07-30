package com.ktmmobile.msf.commons.file.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
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
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

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
import com.ktmmobile.msf.commons.file.support.properties.ObjectStorageProperties;
import com.ktmmobile.msf.commons.file.support.util.FileImagePathUtils;
import com.ktmmobile.msf.commons.file.support.util.FileImageResizeUtils;
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
    private final FileImageVariantProperties imageVariantProperties;
    private final FilePolicyValidator filePolicyValidator;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Override
    public CommonFile writeFile(FileRequest fileRequest) {
        return writeFile(fileRequest, (FilePolicy) null);
    }

    @Override
    public CommonFile writeFile(FileRequest fileRequest, FilePolicy filePolicy) {
        try {
            CommonFile commonFile = createFile(fileRequest, filePolicy);
            uploadFileToStorage(fileRequest, commonFile);
            log.info("파일 업로드: bucket={}, filePath={}", properties.bucket(), commonFile.filePath());

            return commonFile.withSignedUrl(generateSignedUrl(commonFile.filePath()));
        } catch (IOException | S3Exception | SdkClientException e) {
            deleteWrittenFileOnFailure(resolveStorageFilePath(fileRequest));
            throw new FileServiceException(createErrorMessage("쓰기"), e);
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
            throw new FileServiceException(createErrorMessage("변형 파일 생성"), e);
        }
    }

    private void uploadFileToStorage(FileRequest fileRequest, CommonFile commonFile) throws IOException, S3Exception, SdkClientException {
        try (InputStream inputStream = fileRequest.file().getInputStream()) {
            PutObjectRequest request = PutObjectRequest.builder()
                .bucket(properties.bucket())
                .key(commonFile.filePath())
                .contentType(fileRequest.file().getContentType())
                .build();
            s3Client.putObject(request, RequestBody.fromInputStream(inputStream, fileRequest.file().getSize()));
        }
    }

    private void deleteWrittenFileOnFailure(String filePath) {
        try {
            deleteFileBy(filePath);
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
                .directoryPath(resolveStorageDirectoryPath(fileRequest))
                .fileName(fileRequest.resolvedFileName())
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
            .filePath(rawFile.getFilePath())
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

    private String resolveStorageDirectoryPath(FileRequest fileRequest) {
        return resolveStorageDirectoryPath(fileRequest.resolvedFilePath());
    }

    private String resolveStorageFilePath(FileRequest fileRequest) {
        return resolveStorageFilePath(fileRequest.getFilePath());
    }

    private String resolveStorageDirectoryPath(String directoryPath) {
        return resolveStoragePath(FileUtils.normalizeDirectoryPath(directoryPath));
    }

    private String resolveStorageFilePath(String filePath) {
        return resolveStoragePath(FileUtils.normalizeFilePath(filePath));
    }

    private String resolveStoragePath(String filePath) {
        String normalizedRootPath = FileUtils.normalizeDirectoryPath(properties.basePath());
        validateStoragePath(filePath);

        if (!StringUtils.hasText(normalizedRootPath)) {
            return filePath;
        }
        if (!StringUtils.hasText(filePath)) {
            return normalizedRootPath;
        }
        if (filePath.equals(normalizedRootPath) || filePath.startsWith(normalizedRootPath + "/")) {
            return filePath;
        }
        return FileUtils.concat(normalizedRootPath, filePath);
    }

    private static void validateStoragePath(String filePath) {
        if (!StringUtils.hasText(filePath)) {
            return;
        }
        if (filePath.contains("//")) {
            throw new IllegalArgumentException("파일 경로는 기본 경로 하위여야 합니다.");
        }
        for (String filePathPart: filePath.split("/")) {
            if (!StringUtils.hasText(filePathPart) || ".".equals(filePathPart) || "..".equals(filePathPart)) {
                throw new IllegalArgumentException("파일 경로는 기본 경로 하위여야 합니다.");
            }
        }
    }

    @Override
    public boolean fileExists(String filePath) {
        String normalizedFilePath = resolveStorageFilePath(filePath);
        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(properties.bucket())
                .key(normalizedFilePath)
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

    private void logFilePath(String normalizedFilePath) {
        log.info("파일 경로: bucket={}, filePath={}", properties.bucket(), normalizedFilePath);
    }

    private static boolean isStatusCodeNotFound(S3Exception e) {
        return e.statusCode() == HttpStatus.NOT_FOUND.value();
    }

    @Override
    public Optional<CommonFile> getFile(String filePath) {
        String normalizedFilePath = resolveStorageFilePath(filePath);
        logFilePath(normalizedFilePath);
        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(properties.bucket())
                .key(normalizedFilePath)
                .build();
            HeadObjectResponse response = s3Client.headObject(request);
            CommonFile commonFile = toCommonFile(normalizedFilePath, response.contentType(), response.contentLength());
            return Optional.of(commonFile.withSignedUrl(generateSignedUrl(normalizedFilePath)));
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
    public List<CommonFile> listFiles(String directoryPath, int limit) {
        validateLimit(limit);
        String prefix = resolveListPrefix(directoryPath);
        log.info("파일 목록 조회: bucket={}, prefix={}, limit={}", properties.bucket(), prefix, limit);
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(properties.bucket())
                .prefix(prefix)
                .build();
            return s3Client.listObjectsV2Paginator(request)
                .contents()
                .stream()
                .filter(object -> StringUtils.hasText(object.key()))
                .filter(object -> !object.key().endsWith("/"))
                .limit(limit)
                .map(this::toCommonFile)
                .toList();
        } catch (S3Exception | SdkClientException e) {
            throw new FileServiceException(createErrorMessage("목록 조회"), e);
        }
    }

    private String resolveListPrefix(String directoryPath) {
        String normalizedDirectoryPath = resolveStorageDirectoryPath(directoryPath);
        if (!StringUtils.hasText(normalizedDirectoryPath)) {
            return "";
        }
        return normalizedDirectoryPath + "/";
    }

    @Override
    public Optional<FileContent> readFile(String filePath) {
        String normalizedFilePath = resolveStorageFilePath(filePath);
        logFilePath(normalizedFilePath);
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                .bucket(properties.bucket())
                .key(normalizedFilePath)
                .build();
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(request);
            CommonFile commonFile = toCommonFile(
                normalizedFilePath,
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
    public void removeFile(String filePath) {
        String normalizedFilePath = resolveStorageFilePath(filePath);
        logFilePath(normalizedFilePath);
        try {
            for (String deleteTarget: FileImagePathUtils.resolveImageFilePaths(normalizedFilePath)) {
                if (!fileExists(deleteTarget)) {
                    log.warn("삭제 대상 파일이 없습니다: bucket={}, filePath={}", properties.bucket(), deleteTarget);
                    continue;
                }
                deleteFileBy(deleteTarget);
            }
        } catch (S3Exception | SdkClientException e) {
            throw new FileServiceException(createErrorMessage("삭제"), e);
        }
    }

    @Override
    public int removeFilesModifiedBefore(String directoryPath, Instant baseDateTime, int limit) {
        validateLimit(limit);
        String prefix = resolveListPrefix(directoryPath);
        log.info("기준일 이전 파일 삭제 시작: bucket={}, prefix={}, baseDateTime={}, limit={}", properties.bucket(), prefix, baseDateTime, limit);
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(properties.bucket())
                .prefix(prefix)
                .build();
            AtomicInteger deletedCount = new AtomicInteger();
            s3Client.listObjectsV2Paginator(request)
                .contents()
                .stream()
                .filter(object -> StringUtils.hasText(object.key()))
                .filter(object -> !object.key().endsWith("/"))
                .filter(object -> isModifiedBefore(object, baseDateTime))
                .limit(limit)
                .map(S3Object::key)
                .forEach(deleteTarget -> deleteObjectBeforeBaseDateTime(deleteTarget, deletedCount));
            log.info("기준일 이전 파일 삭제 완료: bucket={}, prefix={}, count={}", properties.bucket(), prefix, deletedCount.get());
            return deletedCount.get();
        } catch (S3Exception | SdkClientException e) {
            throw new FileServiceException(createErrorMessage("기준일 이전 파일 삭제"), e);
        }
    }

    private void deleteObjectBeforeBaseDateTime(String deleteTarget, AtomicInteger deletedCount) {
        deleteFileBy(deleteTarget);
        deletedCount.incrementAndGet();
        log.info("기준일 이전 파일 삭제: bucket={}, filePath={}", properties.bucket(), deleteTarget);
    }

    private static boolean isModifiedBefore(S3Object object, Instant baseDateTime) {
        return object.lastModified() != null && object.lastModified().isBefore(baseDateTime);
    }

    private static void validateLimit(int limit) {
        Assert.isTrue(limit >= 1, "limit은 1 이상이어야 합니다.");
        Assert.isTrue(limit <= DEFAULT_FILE_LIMIT, "limit은 1000 이하여야 합니다.");
    }

    private void deleteFileBy(String filePath) throws S3Exception, SdkClientException {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
            .bucket(properties.bucket())
            .key(filePath)
            .build();
        s3Client.deleteObject(request);
    }

    @Override
    public String generateSignedUrl(String filePath) {
        return generateSignedUrl(filePath, DEFAULT_SIGNED_URL_EXPIRATION);
    }

    @Override
    public String generateSignedUrl(String filePath, Duration duration) {
        String normalizedFilePath = resolveStorageFilePath(filePath);
        logFilePath(normalizedFilePath);
        try {
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duration)
                .getObjectRequest(builder -> builder
                    .bucket(properties.bucket())
                    .key(normalizedFilePath))
                .build();
            PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
            return presignedRequest.url().toString();
        } catch (S3Exception | SdkClientException e) {
            throw new FileServiceException(createErrorMessage("조회"), e);
        }
    }

    @Override
    public String generateUploadSignedUrl(String filePath) {
        return generateUploadSignedUrl(filePath, DEFAULT_SIGNED_URL_EXPIRATION);
    }

    @Override
    public String generateUploadSignedUrl(String filePath, Duration duration) {
        String normalizedFilePath = resolveStorageFilePath(filePath);
        logFilePath(normalizedFilePath);
        try {
            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(duration)
                .putObjectRequest(builder -> builder
                    .bucket(properties.bucket())
                    .key(normalizedFilePath))
                .build();
            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
            return presignedRequest.url().toString();
        } catch (S3Exception | SdkClientException e) {
            throw new FileServiceException(createErrorMessage("업로드 URL 생성"), e);
        }
    }

    @Override
    public String generateUploadSignedUrl(String filePath, String contentType) {
        return generateUploadSignedUrl(filePath, contentType, DEFAULT_SIGNED_URL_EXPIRATION);
    }

    @Override
    public String generateUploadSignedUrl(String filePath, String contentType, Duration duration) {
        String normalizedFilePath = resolveStorageFilePath(filePath);
        logFilePath(normalizedFilePath);
        try {
            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(duration)
                .putObjectRequest(builder -> builder
                    .bucket(properties.bucket())
                    .key(normalizedFilePath)
                    .contentType(contentType))
                .build();
            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
            return presignedRequest.url().toString();
        } catch (S3Exception | SdkClientException e) {
            throw new FileServiceException(createErrorMessage("업로드 URL 생성"), e);
        }
    }

    private CommonFile toCommonFile(String filePath, String contentType, Long size) {
        String[] filePathParts = splitFilePath(filePath);

        return CommonFile.builder()
            .filePath(filePath)
            .rawFile(RawFile.builder()
                .directoryPath(filePathParts[0])
                .fileName(filePathParts[1])
                .extension(StringUtils.getFilenameExtension(filePathParts[1]))
                .size(size == null ? 0L : size)
                .mediaType(resolveMediaType(contentType))
                .build())
            .build();
    }

    private CommonFile toCommonFile(S3Object object) {
        return toCommonFile(object.key(), null, object.size());
    }

    private static MediaType resolveMediaType(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
        return MediaType.parseMediaType(contentType);
    }

    private static String[] splitFilePath(String filePath) {
        int separatorIndex = filePath.lastIndexOf('/');
        if (separatorIndex < 0) {
            return new String[] {"", filePath};
        }
        return new String[] {
            filePath.substring(0, separatorIndex),
            filePath.substring(separatorIndex + 1)
        };
    }

}
