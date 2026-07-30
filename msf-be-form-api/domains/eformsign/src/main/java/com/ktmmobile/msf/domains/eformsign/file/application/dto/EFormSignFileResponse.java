package com.ktmmobile.msf.domains.eformsign.file.application.dto;

import com.ktmmobile.msf.commons.file.domain.dto.CommonFile;
import com.ktmmobile.msf.commons.file.domain.vo.RawFile;

/**
 * eFormSign 파일 응답
 */
public record EFormSignFileResponse(
    String filePath,
    RawFile rawFile,
    String downloadSignedUrl
) {

    /**
     * 공통 파일 응답 변환
     */
    public static EFormSignFileResponse of(CommonFile file, String filePath) {
        return new EFormSignFileResponse(
            filePath,
            file.rawFile(),
            file.signedUrl()
        );
    }
}
