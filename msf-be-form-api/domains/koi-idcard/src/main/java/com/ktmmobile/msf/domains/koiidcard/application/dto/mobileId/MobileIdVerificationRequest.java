package com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId;

import jakarta.validation.constraints.NotBlank;

public record MobileIdVerificationRequest(

    @NotBlank
    String trxcode
) {
}
