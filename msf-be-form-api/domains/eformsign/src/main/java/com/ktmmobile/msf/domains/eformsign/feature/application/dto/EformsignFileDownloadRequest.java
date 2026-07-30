package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

public record EformsignFileDownloadRequest(
    String accessToken,
    String documentId,
    Integer retryCount,
    String fileCategory,
    String requestKey,
    String fileType,
    String fileName
) {
}
