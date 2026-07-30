package com.ktmmobile.msf.domains.eformsign.file.application.port.in;

import java.util.List;

import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileListRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileListResponse;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFilePathRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileResponse;

/**
 * eFormSign 파일 조회 포트
 */
public interface EFormSignFileReader {

    /**
     * 파일 정보 조회
     */
    EFormSignFileResponse getFile(EFormSignFilePathRequest request);

    /**
     * 파일 목록 조회
     */
    List<EFormSignFileListResponse> listFiles(EFormSignFileListRequest request);
}
