package com.ktmmobile.msf.domains.commoncode.application.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CommonCodesRequest(
    @NotEmpty List<@NotBlank String> groupIds
) {
}
