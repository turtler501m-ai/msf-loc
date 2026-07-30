package com.ktmmobile.msf.commons.file.application.port.in;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.ktmmobile.msf.commons.file.application.dto.FileBase64UploadRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileRequest;
import com.ktmmobile.msf.commons.file.application.dto.FileVariantOptions;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.commons.file.domain.dto.FileContent;
import com.ktmmobile.msf.commons.file.domain.dto.FileVariantResult;
import com.ktmmobile.msf.commons.file.support.properties.FilePolicy;

public interface CommonFileService {

    int DEFAULT_FILE_LIMIT = 1_000;

    /**
     * 파일 저장
     */
    CommonFile writeFile(FileRequest fileRequest);

    /**
     * 파일과 변형 파일 저장
     */
    FileVariantResult writeFile(FileRequest fileRequest, FileVariantOptions variantOptions);

    /**
     * 직접 지정 경로 파일 저장
     */
    default CommonFile writeFile(MultipartFile file, String filePath) {
        return writeFile(FileRequest.ofFilePath(file, filePath));
    }

    /**
     * Base64 인코딩 파일 저장
     */
    default CommonFile writeFile(FileBase64UploadRequest request) {
        return writeFile(request.toFileRequest());
    }

    /**
     * 파일 저장
     * (커스텀 파일 정책 적용)
     */
    CommonFile writeFile(FileRequest fileRequest, FilePolicy filePolicy);

    /**
     * 직접 지정 경로 파일 저장
     * (커스텀 파일 정책 적용)
     */
    default CommonFile writeFile(MultipartFile file, String filePath, FilePolicy filePolicy) {
        return writeFile(FileRequest.ofFilePath(file, filePath), filePolicy);
    }

    /**
     * Base64 인코딩 파일 저장
     * (커스텀 파일 정책 적용)
     */
    default CommonFile writeFile(FileBase64UploadRequest request, FilePolicy filePolicy) {
        return writeFile(request.toFileRequest(), filePolicy);
    }

    /**
     * 파일 존재 여부 확인
     */
    boolean fileExists(String filePath);

    /**
     * 파일 정보 조회
     */
    Optional<CommonFile> getFile(String filePath);

    /**
     * 디렉토리 하위 파일 목록 조회
     */
    default List<CommonFile> listFiles(String directoryPath) {
        return listFiles(directoryPath, DEFAULT_FILE_LIMIT);
    }

    /**
     * 디렉토리 하위 파일 목록 조회
     */
    List<CommonFile> listFiles(String directoryPath, int limit);

    /**
     * 파일 바이너리 조회
     */
    Optional<FileContent> readFile(String filePath);

    /**
     * 파일 삭제
     */
    void removeFile(String filePath);

    /**
     * 기준시각 이전 파일 삭제
     *
     * @param directoryPath 탐색 시작 디렉토리 경로
     * @param baseDateTime 삭제 기준시각
     * @return 삭제 파일 개수
     */
    default int removeFilesModifiedBefore(String directoryPath, Instant baseDateTime) {
        return removeFilesModifiedBefore(directoryPath, baseDateTime, DEFAULT_FILE_LIMIT);
    }

    /**
     * 기준날짜 이전 파일 삭제
     *
     * @param directoryPath 탐색 시작 디렉토리 경로
     * @param baseDate 삭제 기준날짜
     * @return 삭제 파일 개수
     */
    default int removeFilesModifiedBefore(String directoryPath, LocalDate baseDate) {
        return removeFilesModifiedBefore(directoryPath, baseDate, DEFAULT_FILE_LIMIT);
    }

    /**
     * 기준날짜 이전 파일 삭제 (개수 제한)
     *
     * @param directoryPath 탐색 시작 디렉토리 경로
     * @param baseDate 삭제 기준날짜
     * @param limit 최대 삭제 개수
     * @return 삭제 파일 개수
     */
    default int removeFilesModifiedBefore(String directoryPath, LocalDate baseDate, int limit) {
        return removeFilesModifiedBefore(directoryPath, baseDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), limit);
    }

    /**
     * 보관기간 단위 파일 삭제
     *
     * @param directoryPath 탐색 시작 디렉토리 경로
     * @param retentionDuration 보관기간
     * @param truncateUnit 기준시각 조정 단위
     * @return 삭제 파일 개수
     */
    default int removeFilesModifiedBefore(String directoryPath, Duration retentionDuration, ChronoUnit truncateUnit) {
        return removeFilesModifiedBefore(directoryPath, retentionDuration, truncateUnit, DEFAULT_FILE_LIMIT);
    }

    /**
     * 보관기간 단위 파일 삭제 (개수 제한)
     *
     * @param directoryPath 탐색 시작 디렉토리 경로
     * @param retentionDuration 보관기간
     * @param truncateUnit 기준시각 조정 단위
     * @param limit 최대 삭제 개수
     * @return 삭제 파일 개수
     */
    default int removeFilesModifiedBefore(String directoryPath, Duration retentionDuration, ChronoUnit truncateUnit, int limit) {
        Assert.isTrue(truncateUnit == ChronoUnit.DAYS || truncateUnit.isTimeBased(), "truncateUnit은 일 또는 시간 기반 단위여야 합니다.");
        Instant retentionDateTime = Instant.now().minus(retentionDuration);
        Instant baseDateTime = truncateUnit == ChronoUnit.DAYS
            ? retentionDateTime.atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()
            : retentionDateTime.truncatedTo(truncateUnit);
        return removeFilesModifiedBefore(directoryPath, baseDateTime, limit);
    }

    /**
     * 기준시각 이전 파일 삭제 (개수 제한)
     *
     * @param directoryPath 탐색 시작 디렉토리 경로
     * @param baseDateTime 삭제 기준시각
     * @param limit 최대 삭제 개수
     * @return 삭제 파일 개수
     */
    int removeFilesModifiedBefore(String directoryPath, Instant baseDateTime, int limit);

    /**
     * 파일의 Signed URL 생성/조회
     * (Signed URL의 만료시간은 기본값으로 설정)
     */
    String generateSignedUrl(String filePath);

    /**
     * 파일의 Signed URL 생성/조회
     * (Signed URL의 만료시간은 지정한 duration으로 설정)
     */
    String generateSignedUrl(String filePath, Duration duration);

    /**
     * 파일 업로드 Signed URL 생성
     */
    String generateUploadSignedUrl(String filePath);

    /**
     * 파일 업로드 Signed URL 생성
     */
    String generateUploadSignedUrl(String filePath, Duration duration);

    /**
     * 파일 업로드 Signed URL 생성
     */
    String generateUploadSignedUrl(String filePath, String contentType);

    /**
     * 파일 업로드 Signed URL 생성
     */
    String generateUploadSignedUrl(String filePath, String contentType, Duration duration);
}
