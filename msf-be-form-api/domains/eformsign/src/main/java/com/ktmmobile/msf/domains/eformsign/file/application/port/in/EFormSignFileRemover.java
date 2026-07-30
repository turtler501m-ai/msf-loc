package com.ktmmobile.msf.domains.eformsign.file.application.port.in;

import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFilePathRequest;

/**
 * eFormSign 파일 삭제 포트
 */
public interface EFormSignFileRemover {

    /**
     * 파일 삭제
     */
    void removeFile(EFormSignFilePathRequest request);
}
