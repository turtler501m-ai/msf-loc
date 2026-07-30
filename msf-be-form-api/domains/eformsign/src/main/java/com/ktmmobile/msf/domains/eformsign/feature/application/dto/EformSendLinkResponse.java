package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

public record EformSendLinkResponse(
    String code,
    String message,
    String url
) {
}
