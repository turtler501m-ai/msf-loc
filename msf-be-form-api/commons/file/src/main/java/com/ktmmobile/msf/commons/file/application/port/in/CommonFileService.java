package com.ktmmobile.msf.commons.file.application.port.in;

import java.time.Duration;
import java.util.Optional;

import com.ktmmobile.msf.commons.file.application.dto.FileRequest;
import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.commons.file.domain.dto.FileContent;
import com.ktmmobile.msf.commons.file.support.properties.FilePolicy;

public interface CommonFileService {

    /**
     * 파일 저장
     */
    CommonFile writeFile(FileRequest fileRequest);

    /**
     * 파일 저장
     * (커스텀 파일 정책을 적용)
     */
    CommonFile writeFile(FileRequest fileRequest, FilePolicy filePolicy);

    /**
     * 파일 존재 여부 확인
     */
    boolean fileExists(String pathFileName);

    /**
     * 파일 정보 조회
     */
    Optional<CommonFile> getFile(String pathFileName);

    /**
     * 파일 바이너리 조회
     */
    Optional<FileContent> readFile(String pathFileName);

    /**
     * 파일 삭제
     */
    void removeFile(String pathFileName);

    /**
     * 파일의 Signed URL 생성/조회
     * (Signed URL의 만료시간은 1시간으로 설정)
     */
    String generateSignedUrl(String pathFileName);

    /**
     * 파일의 Signed URL 생성/조회
     * (Signed URL의 만료시간은 지정한 duration으로 설정)
     */
    String generateSignedUrl(String pathFileName, Duration duration);
}
