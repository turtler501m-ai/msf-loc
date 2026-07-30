package com.ktmmobile.msf.domains.externalclient.imagesystem.application.port.out;

import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadRequest;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadResponse;

/**
 * 이미지 시스템 외부 연동 포트
 */
public interface ImageSystemClient {

    ImageSystemPdfUploadResponse uploadPdf(ImageSystemPdfUploadRequest request);
}
