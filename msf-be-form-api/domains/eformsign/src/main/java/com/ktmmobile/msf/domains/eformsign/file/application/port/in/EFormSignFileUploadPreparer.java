package com.ktmmobile.msf.domains.eformsign.file.application.port.in;

import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileUploadPrepareRequest;
import com.ktmmobile.msf.domains.eformsign.file.application.dto.EFormSignFileUploadUrlResponse;

/**
 * eFormSign 파일 업로드 준비 포트
 */
public interface EFormSignFileUploadPreparer {

    /**
     * 파일 업로드 URL 발급
     */
    EFormSignFileUploadUrlResponse issueUploadUrl(EFormSignFileUploadPrepareRequest request);
}
