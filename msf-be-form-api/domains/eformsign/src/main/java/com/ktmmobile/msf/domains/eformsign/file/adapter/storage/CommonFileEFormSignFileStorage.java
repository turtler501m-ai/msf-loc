package com.ktmmobile.msf.domains.eformsign.file.adapter.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.file.application.port.in.CommonFileService;
import com.ktmmobile.msf.commons.file.application.service.ObjectStorageFileService;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.domains.eformsign.file.application.port.out.EFormSignFileStorage;

/**
 * eFormSign 공통 파일 저장소
 */
@Component
public class CommonFileEFormSignFileStorage implements EFormSignFileStorage {

    private final CommonFileService objectStorageFileService;

    public CommonFileEFormSignFileStorage(
        @Qualifier(ObjectStorageFileService.BEAN_NAME) CommonFileService objectStorageFileService
    ) {
        this.objectStorageFileService = objectStorageFileService;
    }

    /**
     * 파일 업로드 URL 생성
     */
    @Override
    public String generateUploadSignedUrl(String filePath) {
        return objectStorageFileService.generateUploadSignedUrl(filePath);
    }

    /**
     * 파일 정보 조회
     */
    @Override
    public Optional<CommonFile> getFile(String filePath) {
        return objectStorageFileService.getFile(filePath);
    }

    /**
     * 파일 목록 조회
     */
    @Override
    public List<CommonFile> listFiles(String directoryPath, int limit) {
        return objectStorageFileService.listFiles(directoryPath, limit);
    }

    /**
     * 파일 삭제
     */
    @Override
    public void removeFile(String filePath) {
        objectStorageFileService.removeFile(filePath);
    }
}
