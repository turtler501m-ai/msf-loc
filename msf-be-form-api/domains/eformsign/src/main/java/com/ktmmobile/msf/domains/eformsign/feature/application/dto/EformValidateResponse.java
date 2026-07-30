package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

import java.util.List;

public record EformValidateResponse(
    boolean signed,
    List<String> signatureValues
) { }
