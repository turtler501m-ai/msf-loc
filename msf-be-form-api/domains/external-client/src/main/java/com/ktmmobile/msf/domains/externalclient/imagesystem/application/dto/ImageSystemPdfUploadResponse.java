package com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto;

/**
 * 이미지 시스템 PDF 업로드 응답값
 */
public record ImageSystemPdfUploadResponse(
    String result,   // result: 성공 Y, 실패 N
    String filepath  // filepath: 성공 시 파일 경로
) {

    private static final String SUCCESS_RESULT = "Y";

    public boolean success() {
        return SUCCESS_RESULT.equals(result);
    }
}
