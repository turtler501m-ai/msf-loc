package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

import java.util.List;

public record EformSendLinkRequest(
    String requestKey,
    String documentId,
    List<SendInfo> mobiles,
    String pwd,
    String hint
) {

    public record SendInfo(
        String name,
        String mobile
    ) { }
}
