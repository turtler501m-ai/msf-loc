package com.ktmmobile.msf.domains.form.main.application.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Builder;

import com.ktmmobile.msf.domains.form.main.domain.code.PublicStatus;

@Builder(toBuilder = true)
public record QnaRequest(
    @NotBlank String category,
    @NotBlank String title,
    @NotBlank String contents,
    PublicStatus publicStatus
) {
}
