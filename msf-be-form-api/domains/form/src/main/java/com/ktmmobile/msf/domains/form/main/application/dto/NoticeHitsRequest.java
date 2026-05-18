package com.ktmmobile.msf.domains.form.main.application.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;

@Builder(toBuilder = true)
public record NoticeHitsRequest(
    @NotEmpty List<@NotNull Long> ids
) {
}
