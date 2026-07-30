package com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId;

public record MobileIdCardQrResponse(
    String type,
    String version,
    String cmd,
    String trxcode,
    String idType,
    String mode,
    String qrData
) {
}