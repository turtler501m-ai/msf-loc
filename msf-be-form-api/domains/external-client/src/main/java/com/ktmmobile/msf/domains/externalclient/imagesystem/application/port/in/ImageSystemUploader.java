package com.ktmmobile.msf.domains.externalclient.imagesystem.application.port.in;

import java.util.List;

import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemFileUploadRequest;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadResponse;

public interface ImageSystemUploader {

    List<ImageSystemPdfUploadResponse> uploadPdf(ImageSystemFileUploadRequest request);
}
