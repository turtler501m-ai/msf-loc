package com.ktmmobile.msf.domains.eformsign.file.application.port.out;

import java.util.List;
import java.util.Optional;

import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;

/**
 * eFormSign 파일 저장소 포트
 */
public interface EFormSignFileStorage {

    /**
     * 파일 업로드 URL 생성
     */
    String generateUploadSignedUrl(String filePath);

    /**
     * 파일 정보 조회
     */
    Optional<CommonFile> getFile(String filePath);

    /**
     * 파일 목록 조회
     */
    List<CommonFile> listFiles(String directoryPath, int limit);

    /**
     * 파일 삭제
     */
    void removeFile(String filePath);
}
