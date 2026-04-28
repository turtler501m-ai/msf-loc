package com.ktmmobile.msf.domains.mobileapp.app.application.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class IntroRequest {
    @NotBlank(groups = {OnSelect.class}, message = "OS는 필수 입력 값입니다.")
    private String os;
    @NotBlank(groups = {OnSelect.class}, message = "Version은 필수 입력 값입니다.")
    private String version;
    private String uuid;
}
